package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    // Save
    @Query("SELECT * FROM game_save WHERE id = 1 LIMIT 1")
    fun getGameSaveFlow(): Flow<GameSaveEntity?>

    @Query("SELECT * FROM game_save WHERE id = 1 LIMIT 1")
    suspend fun getGameSave(): GameSaveEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGame(save: GameSaveEntity)

    @Query("DELETE FROM game_save WHERE id = 1")
    suspend fun deleteSave()

    // Endings
    @Query("SELECT * FROM unlocked_endings ORDER BY unlockedAt DESC")
    fun getAllUnlockedEndings(): Flow<List<UnlockedEndingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun unlockEnding(ending: UnlockedEndingEntity)

    // Clues
    @Query("SELECT * FROM discovered_clues ORDER BY discoveredAt ASC")
    fun getAllDiscoveredClues(): Flow<List<DiscoveredClueEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveClue(clue: DiscoveredClueEntity)

    // Inventory
    @Query("SELECT * FROM inventory_items")
    fun getAllInventoryItems(): Flow<List<InventoryItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItem(item: InventoryItemEntity)

    @Query("DELETE FROM inventory_items WHERE itemId = :itemId")
    suspend fun removeItem(itemId: String)

    @Query("DELETE FROM inventory_items")
    suspend fun clearInventory()

    // Achievements
    @Query("SELECT * FROM achievements")
    fun getAllAchievements(): Flow<List<AchievementEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDefaultAchievements(achievements: List<AchievementEntity>)

    @Query("UPDATE achievements SET isUnlocked = 1, unlockedAt = :timestamp WHERE id = :id")
    suspend fun unlockAchievement(id: String, timestamp: Long = System.currentTimeMillis())

    // Settings
    @Query("SELECT * FROM game_settings WHERE id = 1 LIMIT 1")
    fun getSettingsFlow(): Flow<GameSettingsEntity?>

    @Query("SELECT * FROM game_settings WHERE id = 1 LIMIT 1")
    suspend fun getSettings(): GameSettingsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSettings(settings: GameSettingsEntity)

    // Unlocked Special Stories
    @Query("SELECT * FROM unlocked_special_stories")
    fun getAllUnlockedSpecialStories(): Flow<List<UnlockedStoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun unlockSpecialStory(story: UnlockedStoryEntity)

    // Wipe whole game data if user requests in settings
    @Query("DELETE FROM discovered_clues")
    suspend fun clearAllClues()

    @Query("DELETE FROM unlocked_endings")
    suspend fun clearAllEndings()

    @Query("UPDATE achievements SET isUnlocked = 0, unlockedAt = NULL")
    suspend fun resetAchievements()
}
