package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.StoryId
import com.example.ui.GameViewModel
import com.example.ui.Screen
import com.example.ui.components.FullscreenJumpscareOverlay
import com.example.ui.screens.ArchiveScreen
import com.example.ui.screens.EndingsScreen
import com.example.ui.screens.GameScreen
import com.example.ui.screens.InventoryCluesScreen
import com.example.ui.screens.MainMenuScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.StorySelectScreen
import com.example.ui.theme.HorrorBlack
import com.example.ui.theme.NoContestesTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            NoContestesTheme {
                val viewModel: GameViewModel = viewModel()
                val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
                val activeSave by viewModel.activeSave.collectAsStateWithLifecycle()
                val currentStoryId by viewModel.currentStoryId.collectAsStateWithLifecycle()
                val currentScene by viewModel.currentScene.collectAsStateWithLifecycle()
                val variables by viewModel.variables.collectAsStateWithLifecycle()
                val isGlitching by viewModel.isGlitching.collectAsStateWithLifecycle()
                val isPhoneOpen by viewModel.isPhoneOpen.collectAsStateWithLifecycle()
                val isWatchingAd by viewModel.isWatchingRewardedAd.collectAsStateWithLifecycle()
                val adSecondsLeft by viewModel.adSecondsLeft.collectAsStateWithLifecycle()
                val adStoryTarget by viewModel.adStoryTarget.collectAsStateWithLifecycle()
                val activeNotification by viewModel.activeNotification.collectAsStateWithLifecycle()
                val activeEnding by viewModel.activeEndingUnlocked.collectAsStateWithLifecycle()
                val unlockedEndings by viewModel.unlockedEndings.collectAsStateWithLifecycle()
                val discoveredClues by viewModel.discoveredClues.collectAsStateWithLifecycle()
                val inventoryItems by viewModel.inventoryItems.collectAsStateWithLifecycle()
                val achievements by viewModel.achievements.collectAsStateWithLifecycle()
                val gameSettings by viewModel.gameSettings.collectAsStateWithLifecycle()
                val activeJumpscare by viewModel.activeJumpscare.collectAsStateWithLifecycle()
                val unlockedStoryCodes by viewModel.unlockedStoryCodes.collectAsStateWithLifecycle()

                DisposableEffect(Unit) {
                    viewModel.soundManager.startAmbient("RAIN_STREET")
                    viewModel.soundManager.playUserBackgroundAmbient()
                    onDispose {
                        viewModel.soundManager.stopAmbient()
                        viewModel.soundManager.stopUserBackgroundAmbient()
                    }
                }

                // Sub-screen BackHandler navigation
                if (currentScreen != Screen.MainMenu) {
                    BackHandler {
                        if (currentScreen == Screen.Game && isPhoneOpen) {
                            viewModel.togglePhone()
                        } else if (currentScreen == Screen.StorySelect && isWatchingAd) {
                            viewModel.cancelAd()
                        } else {
                            viewModel.navigateTo(Screen.MainMenu)
                        }
                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .safeDrawingPadding(),
                        color = HorrorBlack
                    ) {
                        when (currentScreen) {
                            Screen.MainMenu -> {
                                MainMenuScreen(
                                    activeSave = activeSave,
                                    onContinueGame = { viewModel.continueGame() },
                                    onNewGame = { viewModel.startNewGame(StoryId.ULTIMA_CONEXION) },
                                    onOpenStories = { viewModel.navigateTo(Screen.StorySelect) },
                                    onOpenEndings = { viewModel.navigateTo(Screen.EndingsGallery) },
                                    onOpenInventory = { viewModel.navigateTo(Screen.InventoryClues) },
                                    onOpenArchive = { viewModel.navigateTo(Screen.Archive) },
                                    onOpenSettings = { viewModel.navigateTo(Screen.Settings) }
                                )
                            }
                            Screen.Game -> {
                                GameScreen(
                                    scene = currentScene,
                                    storyId = currentStoryId,
                                    variables = variables,
                                    isGlitching = isGlitching,
                                    isPhoneOpen = isPhoneOpen,
                                    activeNotification = activeNotification,
                                    activeEnding = activeEnding,
                                    inventoryItems = inventoryItems,
                                    discoveredClues = discoveredClues,
                                    onSelectChoice = { viewModel.selectChoice(it) },
                                    onTogglePhone = { viewModel.togglePhone() },
                                    onBackToMenu = { viewModel.navigateTo(Screen.MainMenu) },
                                    onTypewriterTick = { viewModel.soundManager.playTypewriterTick() },
                                    onDismissNotification = { viewModel.dismissNotification() },
                                    onDismissEnding = { viewModel.dismissEndingDialog() },
                                    onRestartStory = { viewModel.restartStoryFromBeginning() },
                                    onPlayVoiceNote = { viewModel.playVoiceNoteSound() },
                                    onMakeCall = { viewModel.makePhoneCall(it) },
                                    onInspectPhoto = { viewModel.inspectPhotoDetail() }
                                )
                            }
                            Screen.StorySelect -> {
                                StorySelectScreen(
                                    unlockedEndings = unlockedEndings,
                                    isStoryUnlocked = { storyId ->
                                        !storyId.isSpecialUnlockable || unlockedStoryCodes.contains(storyId.code)
                                    },
                                    isWatchingAd = isWatchingAd,
                                    adSecondsLeft = adSecondsLeft,
                                    adStoryTarget = adStoryTarget,
                                    onSelectStory = { storyId ->
                                        if (storyId.isSpecialUnlockable) {
                                            viewModel.watchAdToUnlockStory(storyId)
                                        } else {
                                            viewModel.startNewGame(storyId)
                                        }
                                    },
                                    onWatchAdToUnlock = { storyId -> viewModel.watchAdToUnlockStory(storyId) },
                                    onCancelAd = { viewModel.cancelAd() },
                                    onBack = { viewModel.navigateTo(Screen.MainMenu) }
                                )
                            }
                            Screen.EndingsGallery -> {
                                EndingsScreen(
                                    unlockedEndings = unlockedEndings,
                                    onBack = { viewModel.navigateTo(Screen.MainMenu) }
                                )
                            }
                            Screen.InventoryClues -> {
                                InventoryCluesScreen(
                                    itemsList = inventoryItems,
                                    cluesList = discoveredClues,
                                    onBack = { viewModel.navigateTo(Screen.MainMenu) }
                                )
                            }
                            Screen.Archive -> {
                                ArchiveScreen(
                                    unlockedEndings = unlockedEndings,
                                    discoveredClues = discoveredClues,
                                    achievements = achievements,
                                    onBack = { viewModel.navigateTo(Screen.MainMenu) }
                                )
                            }
                            Screen.Settings -> {
                                SettingsScreen(
                                    currentSettings = gameSettings,
                                    onSaveSettings = { master, music, sfx, ambient, voice, vib, crt, headphone ->
                                        viewModel.updateSettings(master, music, sfx, ambient, voice, vib, crt, headphone)
                                    },
                                    onResetAllData = { viewModel.resetAllData() },
                                    onBack = { viewModel.navigateTo(Screen.MainMenu) }
                                )
                            }
                        }
                    }

                    // Jumpscare en pantalla completa durante 2 segundos con grito e interferencia estática
                    FullscreenJumpscareOverlay(
                        isVisible = activeJumpscare?.isVisible == true,
                        drawableResId = activeJumpscare?.drawableResId
                    )
                }
            }
        }
    }
}
