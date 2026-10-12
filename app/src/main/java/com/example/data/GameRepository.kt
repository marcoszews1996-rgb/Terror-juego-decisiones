package com.example.data

import kotlinx.coroutines.flow.Flow

class GameRepository(private val dao: GameDao) {

    val activeSaveFlow: Flow<GameSaveEntity?> = dao.getGameSaveFlow()
    val unlockedEndingsFlow: Flow<List<UnlockedEndingEntity>> = dao.getAllUnlockedEndings()
    val discoveredCluesFlow: Flow<List<DiscoveredClueEntity>> = dao.getAllDiscoveredClues()
    val inventoryItemsFlow: Flow<List<InventoryItemEntity>> = dao.getAllInventoryItems()
    val achievementsFlow: Flow<List<AchievementEntity>> = dao.getAllAchievements()
    val settingsFlow: Flow<GameSettingsEntity?> = dao.getSettingsFlow()
    val unlockedSpecialStoriesFlow: Flow<List<UnlockedStoryEntity>> = dao.getAllUnlockedSpecialStories()

    suspend fun getSave(): GameSaveEntity? = dao.getGameSave()

    suspend fun saveGame(save: GameSaveEntity) {
        dao.saveGame(save)
    }

    suspend fun deleteSave() {
        dao.deleteSave()
    }

    suspend fun unlockEnding(ending: UnlockedEndingEntity) {
        dao.unlockEnding(ending)
    }

    suspend fun saveClue(clue: DiscoveredClueEntity) {
        dao.saveClue(clue)
    }

    suspend fun saveItem(item: InventoryItemEntity) {
        dao.saveItem(item)
    }

    suspend fun removeItem(itemId: String) {
        dao.removeItem(itemId)
    }

    suspend fun clearInventory() {
        dao.clearInventory()
    }

    suspend fun initializeDefaultAchievements() {
        val defaults = listOf(
            AchievementEntity("ach_first_message", "PRIMER MENSAJE", "Recibe tu primer mensaje de un número desconocido."),
            AchievementEntity("ach_dont_open", "NO DEBISTE ABRIR", "Abre una puerta que todas las advertencias te decían que ignoraras."),
            AchievementEntity("ach_curious", "CURIOSO", "Inspecciona una pista secreta escondida."),
            AchievementEntity("ach_not_alone", "NO ESTÁS SOLO", "Desbloquea tu primer final secreto."),
            AchievementEntity("ach_all_answers", "TODAS LAS RESPUESTAS", "Encuentra al menos 5 pistas determinantes."),
            AchievementEntity("ach_pure_terror", "PARANOIA TOTAL", "Permite que tu nivel de miedo alcance el máximo."),
            AchievementEntity("ach_survivor", "SUPERVIVIENTE", "Logra sobrevivir con un Final Bueno."),
            AchievementEntity("ach_shutter", "FOTÓGRAFO NOCTURNO", "Inspecciona y analiza la fotografía siniestra."),
            AchievementEntity("ach_unanswered", "LLAMADA DESCONOCIDA", "Atiende una llamada imposible a mitad de la noche."),
            AchievementEntity("ach_archivist", "ARCHIVISTA DEL HORROR", "Descubre 5 finales diferentes.")
        )
        dao.insertDefaultAchievements(defaults)
    }

    suspend fun unlockAchievement(id: String) {
        dao.unlockAchievement(id)
    }

    suspend fun unlockSpecialStory(storyCode: String) {
        dao.unlockSpecialStory(UnlockedStoryEntity(storyCode))
    }

    suspend fun getSettings(): GameSettingsEntity {
        return dao.getSettings() ?: GameSettingsEntity().also {
            dao.saveSettings(it)
        }
    }

    suspend fun updateSettings(settings: GameSettingsEntity) {
        dao.saveSettings(settings)
    }

    suspend fun resetAllGameData() {
        dao.deleteSave()
        dao.clearInventory()
        dao.clearAllClues()
        dao.clearAllEndings()
        dao.resetAchievements()
    }
}
