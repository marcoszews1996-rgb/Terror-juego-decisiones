package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.DecisionDangerType
import com.example.audio.EndingAudioType
import com.example.audio.SoundManager
import com.example.audio.TensionLevel
import com.example.data.AppDatabase
import com.example.data.DiscoveredClueEntity
import com.example.data.GameRepository
import com.example.data.GameSaveEntity
import com.example.data.GameSettingsEntity
import com.example.data.InventoryItemEntity
import com.example.data.UnlockedEndingEntity
import com.example.model.ChatMessage
import com.example.model.Choice
import com.example.model.Clue
import com.example.R
import com.example.model.Ending
import com.example.model.GameVariables
import com.example.model.InventoryItem
import com.example.model.PhoneContact
import com.example.model.PhoneNotification
import com.example.model.Scene
import com.example.model.StoryId
import com.example.story.StoryContent
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random

data class JumpscareState(
    val isVisible: Boolean = false,
    val drawableResId: Int = R.drawable.img_jumpscare_stitched,
    val timestamp: Long = System.currentTimeMillis()
)

sealed interface Screen {
    data object MainMenu : Screen
    data object Game : Screen
    data object StorySelect : Screen
    data object EndingsGallery : Screen
    data object Archive : Screen
    data object InventoryClues : Screen
    data object Settings : Screen
}

class GameViewModel(application: Application) : AndroidViewModel(application) {

    val soundManager = SoundManager(application)
    private val repository: GameRepository

    // Jumpscares: 3 nuevas imágenes aterradoras seleccionables de forma aleatoria
    private val JUMPSCARE_DRAWABLES = listOf(
        R.drawable.img_jumpscare_stitched,
        R.drawable.img_jumpscare_spider,
        R.drawable.img_jumpscare_screamer
    )

    private val _activeJumpscare = MutableStateFlow<JumpscareState?>(null)
    val activeJumpscare: StateFlow<JumpscareState?> = _activeJumpscare.asStateFlow()

    private var jumpscareJob: Job? = null
    private var ambientJumpscareJob: Job? = null

    // Historias especiales desbloqueadas reactivas en memoria y base de datos
    private val _unlockedStoryCodes = MutableStateFlow<Set<String>>(emptySet())
    val unlockedStoryCodes: StateFlow<Set<String>> = _unlockedStoryCodes.asStateFlow()

    /**
     * Inicia una rutina en segundo plano que detona jumpscares en momentos aleatorios e impredecibles
     * durante la partida (entre 18 y 38 segundos), asegurando que puedan ocurrir en cualquier momento.
     */
    fun startAmbientJumpscareRoutine() {
        ambientJumpscareJob?.cancel()
        ambientJumpscareJob = viewModelScope.launch {
            while (isActive) {
                val delayTime = Random.nextLong(18_000L, 38_000L)
                delay(delayTime)
                if (_currentScreen.value == Screen.Game && _activeJumpscare.value == null && !_isPhoneOpen.value) {
                    triggerJumpscare()
                }
            }
        }
    }

    fun stopAmbientJumpscareRoutine() {
        ambientJumpscareJob?.cancel()
    }

    /**
     * Dispara un jumpscare en pantalla completa durante exactamente 2 segundos,
     * eligiendo aleatoriamente entre las 3 imágenes aterradoras, con grito ensordecedor e interferencia.
     */
    fun triggerJumpscare(overrideDrawableId: Int? = null) {
        jumpscareJob?.cancel()
        jumpscareJob = viewModelScope.launch {
            val selectedDrawable = overrideDrawableId ?: JUMPSCARE_DRAWABLES.random()
            _activeJumpscare.value = JumpscareState(isVisible = true, drawableResId = selectedDrawable)
            soundManager.playTerrifyingJumpscareScream()
            delay(2000L) // 2 segundos exactos
            _activeJumpscare.value = null
        }
    }

    // Screen State
    private val _currentScreen = MutableStateFlow<Screen>(Screen.MainMenu)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    // Rewarded Ad state for special stories
    private val _isWatchingRewardedAd = MutableStateFlow(false)
    val isWatchingRewardedAd: StateFlow<Boolean> = _isWatchingRewardedAd.asStateFlow()

    private val _adStoryTarget = MutableStateFlow<StoryId?>(null)
    val adStoryTarget: StateFlow<StoryId?> = _adStoryTarget.asStateFlow()

    private val _adSecondsLeft = MutableStateFlow(5)
    val adSecondsLeft: StateFlow<Int> = _adSecondsLeft.asStateFlow()

    // Game variables
    private val _variables = MutableStateFlow(GameVariables())
    val variables: StateFlow<GameVariables> = _variables.asStateFlow()

    // Active story & scene
    private val _currentStoryId = MutableStateFlow(StoryId.ULTIMA_CONEXION)
    val currentStoryId: StateFlow<StoryId> = _currentStoryId.asStateFlow()

    private val _currentScene = MutableStateFlow<Scene?>(null)
    val currentScene: StateFlow<Scene?> = _currentScene.asStateFlow()

    // Screen glitch state
    private val _isGlitching = MutableStateFlow(false)
    val isGlitching: StateFlow<Boolean> = _isGlitching.asStateFlow()

    // In-game Phone open state
    private val _isPhoneOpen = MutableStateFlow(false)
    val isPhoneOpen: StateFlow<Boolean> = _isPhoneOpen.asStateFlow()

    // Notification toast
    private val _activeNotification = MutableStateFlow<PhoneNotification?>(null)
    val activeNotification: StateFlow<PhoneNotification?> = _activeNotification.asStateFlow()

    // Ending dialog popup
    private val _activeEndingUnlocked = MutableStateFlow<Ending?>(null)
    val activeEndingUnlocked: StateFlow<Ending?> = _activeEndingUnlocked.asStateFlow()

    // Voice note playback state
    val voiceMessageProgress = MutableStateFlow(0f)
    val isVoiceMessagePlaying = MutableStateFlow(false)

    // Chat messages list in phone
    private val _phoneMessages = MutableStateFlow<List<ChatMessage>>(StoryContent.INITIAL_CHAT_MESSAGES)
    val phoneMessages: StateFlow<List<ChatMessage>> = _phoneMessages.asStateFlow()

    // Database Flows
    val activeSave = MutableStateFlow<GameSaveEntity?>(null)
    val unlockedEndings: StateFlow<List<UnlockedEndingEntity>>
    val discoveredClues: StateFlow<List<DiscoveredClueEntity>>
    val inventoryItems: StateFlow<List<InventoryItemEntity>>
    val achievements: StateFlow<List<com.example.data.AchievementEntity>>
    val gameSettings: StateFlow<GameSettingsEntity>
    val unlockedSpecialStories: StateFlow<List<com.example.data.UnlockedStoryEntity>>

    init {
        val db = AppDatabase.getDatabase(application)
        repository = GameRepository(db.gameDao())

        unlockedSpecialStories = repository.unlockedSpecialStoriesFlow.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        unlockedEndings = repository.unlockedEndingsFlow.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        discoveredClues = repository.discoveredCluesFlow.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        inventoryItems = repository.inventoryItemsFlow.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        achievements = repository.achievementsFlow.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        gameSettings = MutableStateFlow(GameSettingsEntity())

        viewModelScope.launch {
            repository.initializeDefaultAchievements()
            repository.activeSaveFlow.collect { save ->
                activeSave.value = save
            }
        }

        viewModelScope.launch {
            repository.unlockedSpecialStoriesFlow.collect { list ->
                _unlockedStoryCodes.value = _unlockedStoryCodes.value + list.map { it.storyCode }.toSet()
            }
        }

        viewModelScope.launch {
            repository.settingsFlow.collect { settings ->
                if (settings != null) {
                    (gameSettings as MutableStateFlow).value = settings
                    soundManager.masterVolume = settings.masterVolume
                    soundManager.musicVolume = settings.musicVolume
                    soundManager.sfxVolume = settings.sfxVolume
                    soundManager.ambientVolume = settings.ambientVolume
                    soundManager.voiceVolume = settings.voiceVolume
                    soundManager.vibrationEnabled = settings.vibrationEnabled
                    soundManager.headphoneMode = settings.headphoneModeEnabled
                }
            }
        }
    }

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
        if (screen == Screen.MainMenu) {
            stopAmbientJumpscareRoutine()
            soundManager.startAmbient("RAIN_STREET")
        } else if (screen == Screen.Game) {
            startAmbientJumpscareRoutine()
        } else {
            stopAmbientJumpscareRoutine()
        }
    }

    fun isStoryUnlocked(story: StoryId): Boolean {
        if (!story.isSpecialUnlockable) return true
        return _unlockedStoryCodes.value.contains(story.code)
    }

    fun watchAdToUnlockStory(story: StoryId) {
        _adStoryTarget.value = story
        _isWatchingRewardedAd.value = true
        _adSecondsLeft.value = 5
        viewModelScope.launch {
            soundManager.playStaticGlitch()
            for (sec in 5 downTo 1) {
                _adSecondsLeft.value = sec
                kotlinx.coroutines.delay(1000)
            }
            _adSecondsLeft.value = 0
            // Desbloqueo inmediato en memoria reactiva y almacenamiento Room
            _unlockedStoryCodes.value = _unlockedStoryCodes.value + story.code
            repository.unlockSpecialStory(story.code)
            soundManager.playNotificationBeep()
            kotlinx.coroutines.delay(500)
            _isWatchingRewardedAd.value = false
            _adStoryTarget.value = null
            // Inicia la historia de inmediato para que el usuario no tenga que buscarla ni vuelva a ver candado
            startNewGame(story)
        }
    }

    fun unlockAllStories() {
        viewModelScope.launch {
            val specialCodes = StoryId.values().filter { it.isSpecialUnlockable }.map { it.code }.toSet()
            _unlockedStoryCodes.value = _unlockedStoryCodes.value + specialCodes
            specialCodes.forEach { code ->
                repository.unlockSpecialStory(code)
            }
            soundManager.playNotificationBeep()
        }
    }

    fun cancelAd() {
        _isWatchingRewardedAd.value = false
        _adStoryTarget.value = null
    }

    fun startNewGame(storyId: StoryId) {
        viewModelScope.launch {
            _currentStoryId.value = storyId
            _variables.value = GameVariables()
            val firstScene = StoryContent.getAllScenesForStory(storyId).firstOrNull()
            _currentScene.value = firstScene
            _isPhoneOpen.value = false
            _activeEndingUnlocked.value = null

            // Initial items for story
            repository.clearInventory()
            if (storyId == StoryId.ULTIMA_CONEXION) {
                repository.saveItem(InventoryItemEntity(StoryContent.ITEM_KEYS.id, StoryContent.ITEM_KEYS.name, StoryContent.ITEM_KEYS.description, StoryContent.ITEM_KEYS.icon))
            }

            firstScene?.let { scene ->
                soundManager.setStoryTheme(storyId)
                saveCurrentProgress(scene)
            }
            _currentScreen.value = Screen.Game
            startAmbientJumpscareRoutine()
        }
    }

    fun continueGame() {
        val save = activeSave.value ?: return
        viewModelScope.launch {
            val story = StoryId.values().find { it.code == save.storyId } ?: StoryId.ULTIMA_CONEXION
            _currentStoryId.value = story
            _variables.value = GameVariables(
                fear = save.fear,
                trust = save.trust,
                curiosity = save.curiosity,
                evidence = save.evidence,
                danger = save.danger,
                relationship = save.relationship
            )
            val scene = StoryContent.getScene(story, save.sceneId) ?: StoryContent.getAllScenesForStory(story).first()
            _currentScene.value = scene
            soundManager.setStoryTheme(story)
            _isPhoneOpen.value = false
            _activeEndingUnlocked.value = null
            _currentScreen.value = Screen.Game
            startAmbientJumpscareRoutine()
        }
    }

    fun selectChoice(choice: Choice) {
        val current = _currentScene.value ?: return
        val effect = choice.effect

        // Update variables
        val v = _variables.value
        val newFear = (v.fear + effect.fearDelta).coerceIn(0, 100)
        val newTrust = (v.trust + effect.trustDelta).coerceIn(0, 100)
        val newCuriosity = (v.curiosity + effect.curiosityDelta).coerceIn(0, 100)
        val newEvidence = (v.evidence + effect.evidenceDelta).coerceIn(0, 100)
        val newDanger = (v.danger + effect.dangerDelta).coerceIn(0, 100)
        val newRel = (v.relationship + effect.relDelta).coerceIn(0, 100)

        _variables.value = GameVariables(newFear, newTrust, newCuriosity, newEvidence, newDanger, newRel)

        // Check fear achievement
        if (newFear >= 80) {
            viewModelScope.launch { repository.unlockAchievement("ach_pure_terror") }
        }

        // 1. Dynamic Tension Update
        soundManager.updateTensionFromVariables(newFear, newDanger)

        // 2. Classify Decision Danger Type and Trigger Stinger
        val dangerType = when {
            effect.endingTriggered != null || effect.dangerDelta >= 30 -> DecisionDangerType.CRITICA
            effect.dangerDelta >= 20 || effect.fearDelta >= 25 -> DecisionDangerType.MUY_PELIGROSA
            effect.dangerDelta >= 10 || effect.fearDelta >= 15 -> DecisionDangerType.PELIGROSA
            effect.dangerDelta > 0 || effect.curiosityDelta >= 20 -> DecisionDangerType.SOSPECHOSA
            else -> DecisionDangerType.NORMAL
        }
        soundManager.playDecisionStinger(dangerType)

        // 3. Story-Specific Audio Identities
        when (_currentStoryId.value) {
            StoryId.ULTIMA_CONEXION -> {
                if (effect.phoneNotification != null) {
                    val isLocation = effect.clueGained?.id == "clue_stalker_vision" ||
                            choice.id.contains("location") || choice.id.contains("alibi")
                    soundManager.playStory1Notification(isLocationRevealed = isLocation)
                }
            }
            StoryId.EL_DEPARTAMENTO -> {
                if (choice.id.contains("peephole") || choice.targetSceneId.contains("peephole")) {
                    soundManager.playPeepholeNothingSequence()
                } else if (effect.soundTrigger == "KNOCK") {
                    soundManager.playAsymmetricKnockPattern()
                }
            }
            StoryId.LA_FOTO -> {
                if (choice.id.contains("inspect") || choice.id.contains("photo")) {
                    soundManager.playPhotoInspectSound(hasAnomaly = true)
                }
            }
            else -> {
                if (effect.soundTrigger != null) {
                    when (effect.soundTrigger) {
                        "FOOTSTEP" -> soundManager.playFootsteps(com.example.audio.FootstepType.NORMAL)
                        "CREATURE" -> soundManager.playCreatureLeitmotif()
                        else -> soundManager.triggerNarrativeSound(effect.soundTrigger, newFear)
                    }
                }
            }
        }

        // Glitch trigger
        if (effect.glitchTrigger || current.autoGlitch) {
            triggerGlitchMoment()
        }

        // Handle inventory items
        effect.itemGained?.let { item ->
            viewModelScope.launch {
                repository.saveItem(InventoryItemEntity(item.id, item.name, item.description, item.icon))
            }
        }

        // Handle clues gained
        effect.clueGained?.let { clue ->
            viewModelScope.launch {
                repository.saveClue(DiscoveredClueEntity(clue.id, clue.storyId.code, clue.title, clue.description, clue.details, clue.icon))
                repository.unlockAchievement("ach_curious")
            }
        }

        // Handle phone notification
        effect.phoneNotification?.let { notif ->
            _activeNotification.value = notif
            if (_currentStoryId.value != StoryId.ULTIMA_CONEXION) {
                soundManager.playNotificationBeep()
            }
            val newMsg = ChatMessage(
                id = "m_${System.currentTimeMillis()}",
                threadId = "c_unknown",
                sender = notif.sender,
                isMe = notif.sender == "Tú",
                text = notif.text,
                time = "02:22"
            )
            _phoneMessages.value = _phoneMessages.value + newMsg
            viewModelScope.launch { repository.unlockAchievement("ach_first_message") }
        }

        // Transition to next scene
        val nextScene = StoryContent.getScene(_currentStoryId.value, choice.targetSceneId)
        if (nextScene != null) {
            _currentScene.value = nextScene
            soundManager.startAmbient(nextScene.ambientSound.name)

            if (nextScene.isEndingScene || effect.endingTriggered != null) {
                val endingId = nextScene.endingId ?: effect.endingTriggered
                if (endingId != null) {
                    val ending = StoryContent.getEnding(endingId)
                    if (ending != null) {
                        recordEndingUnlocked(ending)
                    }
                }
            } else {
                saveCurrentProgress(nextScene)
                // Disparo de Jumpscare aleatorio en cualquier momento del juego
                if (shouldTriggerRandomJumpscare(newFear, effect.dangerDelta, choice)) {
                    triggerJumpscare()
                }
            }
        }
    }

    private fun shouldTriggerRandomJumpscare(fear: Int, dangerDelta: Int, choice: Choice): Boolean {
        val chance = when {
            dangerDelta >= 20 || fear >= 75 -> 0.35f
            dangerDelta >= 10 || fear >= 50 -> 0.22f
            choice.id.contains("danger") || choice.id.contains("look") || choice.id.contains("back") || choice.id.contains("run") -> 0.20f
            else -> 0.12f
        }
        return Random.nextFloat() < chance
    }

    private fun recordEndingUnlocked(ending: Ending) {
        _activeEndingUnlocked.value = ending
        val endingAudio = when (ending.badge) {
            com.example.model.EndingBadge.BUENO -> EndingAudioType.GOOD
            com.example.model.EndingBadge.MALO, com.example.model.EndingBadge.TRAGICO -> EndingAudioType.BAD
            com.example.model.EndingBadge.SECRETO -> EndingAudioType.SECRET
            com.example.model.EndingBadge.VERDADERO -> EndingAudioType.TRUE
        }
        soundManager.playEndingTheme(endingAudio)

        // Jumpscare en los finales malos (Malo o Trágico) en pantalla completa por 2 segundos
        if (ending.badge == com.example.model.EndingBadge.MALO || ending.badge == com.example.model.EndingBadge.TRAGICO) {
            triggerJumpscare()
        }

        viewModelScope.launch {
            repository.unlockEnding(
                UnlockedEndingEntity(
                    endingId = ending.id,
                    storyId = ending.storyId.code,
                    title = ending.title,
                    badge = ending.badge.label,
                    description = ending.description
                )
            )
            // Delete save on reaching ending so continue resets to chapter select
            repository.deleteSave()

            // Unlock achievements
            if (ending.badge == com.example.model.EndingBadge.BUENO) {
                repository.unlockAchievement("ach_survivor")
            }
            if (ending.badge == com.example.model.EndingBadge.SECRETO) {
                repository.unlockAchievement("ach_not_alone")
            }
        }
    }

    private fun saveCurrentProgress(scene: Scene) {
        viewModelScope.launch {
            val v = _variables.value
            repository.saveGame(
                GameSaveEntity(
                    id = 1,
                    storyId = _currentStoryId.value.code,
                    sceneId = scene.id,
                    chapterIndex = scene.chapterIndex,
                    chapterTitle = scene.chapterTitle,
                    fear = v.fear,
                    trust = v.trust,
                    curiosity = v.curiosity,
                    evidence = v.evidence,
                    danger = v.danger,
                    relationship = v.relationship
                )
            )
        }
    }

    fun dismissEndingDialog() {
        _activeEndingUnlocked.value = null
        navigateTo(Screen.MainMenu)
    }

    fun restartStoryFromBeginning() {
        _activeEndingUnlocked.value = null
        val current = _currentStoryId.value
        if (current.isSpecialUnlockable) {
            watchAdToUnlockStory(current)
        } else {
            startNewGame(current)
        }
    }

    fun dismissNotification() {
        _activeNotification.value = null
    }

    fun togglePhone() {
        val willOpen = !_isPhoneOpen.value
        _isPhoneOpen.value = willOpen
        if (willOpen) {
            soundManager.vibrate(40)
            dismissNotification()
        }
    }

    fun triggerGlitchMoment() {
        viewModelScope.launch {
            _isGlitching.value = true
            soundManager.playStaticGlitch()
            kotlinx.coroutines.delay(220)
            _isGlitching.value = false
        }
    }

    fun playVoiceNoteSound() {
        isVoiceMessagePlaying.value = true
        soundManager.playVoiceMessageAudio(
            onProgress = { p -> voiceMessageProgress.value = p },
            onFinished = {
                isVoiceMessagePlaying.value = false
                voiceMessageProgress.value = 0f
            }
        )
    }

    fun makePhoneCall(contact: PhoneContact) {
        soundManager.playPhoneRing()
        viewModelScope.launch {
            repository.unlockAchievement("ach_unanswered")
        }
    }

    fun inspectPhotoDetail() {
        soundManager.playPhotoInspectSound(hasAnomaly = true)
        viewModelScope.launch {
            repository.unlockAchievement("ach_shutter")
            repository.saveClue(
                DiscoveredClueEntity(
                    StoryContent.CLUE_PHOTO_EXIF.id,
                    StoryContent.CLUE_PHOTO_EXIF.storyId.code,
                    StoryContent.CLUE_PHOTO_EXIF.title,
                    StoryContent.CLUE_PHOTO_EXIF.description,
                    StoryContent.CLUE_PHOTO_EXIF.details,
                    StoryContent.CLUE_PHOTO_EXIF.icon
                )
            )
        }
    }

    fun updateSettings(
        master: Float,
        music: Float,
        sfx: Float,
        ambient: Float,
        voice: Float,
        vibration: Boolean,
        crt: Boolean,
        headphone: Boolean
    ) {
        val updated = GameSettingsEntity(
            masterVolume = master,
            musicVolume = music,
            sfxVolume = sfx,
            ambientVolume = ambient,
            voiceVolume = voice,
            vibrationEnabled = vibration,
            crtScanlinesEnabled = crt,
            headphoneModeEnabled = headphone
        )
        (gameSettings as MutableStateFlow).value = updated
        soundManager.masterVolume = master
        soundManager.musicVolume = music
        soundManager.sfxVolume = sfx
        soundManager.ambientVolume = ambient
        soundManager.voiceVolume = voice
        soundManager.vibrationEnabled = vibration
        soundManager.headphoneMode = headphone

        viewModelScope.launch {
            repository.updateSettings(updated)
        }
    }

    fun resetAllData() {
        viewModelScope.launch {
            repository.resetAllGameData()
            _phoneMessages.value = StoryContent.INITIAL_CHAT_MESSAGES
            _variables.value = GameVariables()
            _activeEndingUnlocked.value = null
            navigateTo(Screen.MainMenu)
        }
    }

    override fun onCleared() {
        super.onCleared()
        soundManager.release()
    }
}
