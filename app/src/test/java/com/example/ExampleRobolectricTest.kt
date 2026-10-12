package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.AppDatabase
import com.example.data.DiscoveredClueEntity
import com.example.data.GameRepository
import com.example.data.GameSaveEntity
import com.example.data.UnlockedEndingEntity
import com.example.model.StoryId
import com.example.story.StoryContent
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("NO CONTESTES", appName)
    }

    @Test
    fun `verify all eight stories have scenes and endings`() {
        StoryId.values().forEach { storyId ->
            val scenes = StoryContent.getAllScenesForStory(storyId)
            assertTrue("Story $storyId should have scenes", scenes.isNotEmpty())

            val endingsForStory = StoryContent.ALL_ENDINGS.filter { it.storyId == storyId }
            assertEquals("Story $storyId must have 5 distinct endings", 5, endingsForStory.size)
        }
        assertEquals("Total endings must be 40 across 8 stories", 40, StoryContent.ALL_ENDINGS.size)
    }

    @Test
    fun `test game repository persistence and retrieval`() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = AppDatabase.getDatabase(context)
        val repository = GameRepository(db.gameDao())

        // Save a test game progress
        val save = GameSaveEntity(
            id = 1,
            storyId = StoryId.ULTIMA_CONEXION.code,
            sceneId = "s1_phone_buzz",
            chapterIndex = 2,
            chapterTitle = "Capítulo 2: Remitente Desconocido",
            fear = 35,
            trust = 40,
            curiosity = 50,
            evidence = 20,
            danger = 15,
            relationship = 50
        )
        repository.saveGame(save)

        val retrievedSave = repository.getSave()
        assertNotNull(retrievedSave)
        assertEquals("s1_phone_buzz", retrievedSave?.sceneId)
        assertEquals(35, retrievedSave?.fear)

        // Test unlocking ending
        val ending = StoryContent.ALL_ENDINGS.first()
        repository.unlockEnding(
            UnlockedEndingEntity(
                endingId = ending.id,
                storyId = ending.storyId.code,
                title = ending.title,
                badge = ending.badge.label,
                description = ending.description
            )
        )

        // Test saving clue
        repository.saveClue(
            DiscoveredClueEntity(
                clueId = "test_clue_1",
                storyId = StoryId.ULTIMA_CONEXION.code,
                title = "Pista de prueba",
                description = "Descripción de prueba",
                details = "Detalle",
                icon = "ic_search"
            )
        )

        // Test unlocking special story
        repository.unlockSpecialStory(StoryId.EL_TRANSMISOR.code)
    }

    @Test
    fun `test sound manager dynamic tension and stinger classifications`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val soundManager = com.example.audio.SoundManager(context)

        // Verify initial tension
        assertEquals(com.example.audio.TensionLevel.TENSION_0, soundManager.currentTension)

        // Verify tension updates from fear and danger
        soundManager.updateTensionFromVariables(fear = 30, danger = 10)
        assertEquals(com.example.audio.TensionLevel.TENSION_1, soundManager.currentTension)

        soundManager.updateTensionFromVariables(fear = 50, danger = 20)
        assertEquals(com.example.audio.TensionLevel.TENSION_2, soundManager.currentTension)

        soundManager.updateTensionFromVariables(fear = 70, danger = 30)
        assertEquals(com.example.audio.TensionLevel.TENSION_3, soundManager.currentTension)

        soundManager.updateTensionFromVariables(fear = 90, danger = 85)
        assertEquals(com.example.audio.TensionLevel.TENSION_4, soundManager.currentTension)

        // Verify all 8 stinger variants exist
        assertEquals(8, com.example.audio.StingerVariant.values().size)

        // Test calling decision stingers
        com.example.audio.DecisionDangerType.values().forEach { dangerType ->
            soundManager.playDecisionStinger(dangerType)
        }

        // Test story specific audio triggers
        soundManager.playStory1Notification(isLocationRevealed = false)
        soundManager.playStory1Notification(isLocationRevealed = true)
        soundManager.playAsymmetricKnockPattern()
        soundManager.playPhotoInspectSound(hasAnomaly = true)
        soundManager.playCreatureLeitmotif()
        soundManager.playTypewriterTick()
        soundManager.playUneasyPianoSting()
        soundManager.playMinimalPianoUnderscore()
        soundManager.playCinematicPianoNote()
        soundManager.playTerrifyingJumpscareScream()

        soundManager.release()
    }

    @Test
    fun `test audio manager layer mixer and asset structure`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val audioManager = com.example.audio.AudioManager(context)

        // Test tension levels
        audioManager.setTensionLevel(com.example.audio.TensionLevel.TENSION_0)
        assertEquals(com.example.audio.TensionLevel.TENSION_0, audioManager.currentTension)

        audioManager.setTensionLevel(com.example.audio.TensionLevel.TENSION_2)
        assertEquals(com.example.audio.TensionLevel.TENSION_2, audioManager.currentTension)

        audioManager.setTensionLevel(com.example.audio.TensionLevel.TENSION_4)
        assertEquals(com.example.audio.TensionLevel.TENSION_4, audioManager.currentTension)

        // Test layer mixer start and stop
        audioManager.startMusicLayerMixer("RAIN_STREET")
        audioManager.stopMusicMixer()

        // Test priority ducking
        audioManager.requestPriorityDucking(com.example.audio.SoundPriority.CRITICAL_JUMPSCARE, 500, 0.05f)

        // Test asset folder structure in assets/audio/
        val audioDir = java.io.File("src/main/assets/audio").takeIf { it.exists() }
            ?: java.io.File("app/src/main/assets/audio")
        assertTrue(audioDir.exists())
        val folders = audioDir.list()?.toList() ?: emptyList()
        assertTrue(folders.contains("tension"))
        assertTrue(folders.contains("stingers"))
        assertTrue(folders.contains("ambient"))
        assertTrue(folders.contains("notifications"))
        assertTrue(folders.contains("footsteps"))
        assertTrue(folders.contains("creature"))
        assertTrue(folders.contains("music"))
        assertTrue(folders.contains("voices"))

        audioManager.release()
    }
}
