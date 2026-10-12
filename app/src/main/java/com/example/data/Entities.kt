package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_save")
data class GameSaveEntity(
    @PrimaryKey val id: Int = 1,
    val storyId: String,
    val sceneId: String,
    val chapterIndex: Int,
    val chapterTitle: String,
    val fear: Int,
    val trust: Int,
    val curiosity: Int,
    val evidence: Int,
    val danger: Int,
    val relationship: Int,
    val lastUpdated: Long = System.currentTimeMillis()
)

@Entity(tableName = "unlocked_endings")
data class UnlockedEndingEntity(
    @PrimaryKey val endingId: String,
    val storyId: String,
    val title: String,
    val badge: String, // BUENO, MALO, TRÁGICO, SECRETO, VERDADERO
    val description: String,
    val unlockedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "discovered_clues")
data class DiscoveredClueEntity(
    @PrimaryKey val clueId: String,
    val storyId: String,
    val title: String,
    val description: String,
    val details: String,
    val icon: String,
    val discoveredAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "inventory_items")
data class InventoryItemEntity(
    @PrimaryKey val itemId: String,
    val name: String,
    val description: String,
    val icon: String,
    val isUsable: Boolean = true,
    val count: Int = 1
)

@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val isUnlocked: Boolean = false,
    val unlockedAt: Long? = null
)

@Entity(tableName = "game_settings")
data class GameSettingsEntity(
    @PrimaryKey val id: Int = 1,
    val masterVolume: Float = 0.8f,
    val musicVolume: Float = 0.7f,
    val sfxVolume: Float = 0.9f,
    val ambientVolume: Float = 0.8f,
    val voiceVolume: Float = 0.85f,
    val vibrationEnabled: Boolean = true,
    val crtScanlinesEnabled: Boolean = true,
    val headphoneModeEnabled: Boolean = false,
    val textSpeed: Int = 25 // ms per character
)

@Entity(tableName = "unlocked_special_stories")
data class UnlockedStoryEntity(
    @PrimaryKey val storyCode: String,
    val unlockedAt: Long = System.currentTimeMillis()
)
