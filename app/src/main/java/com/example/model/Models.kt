package com.example.model

enum class StoryId(
    val code: String,
    val title: String,
    val subtitle: String,
    val isSpecialUnlockable: Boolean = false
) {
    ULTIMA_CONEXION("story_1", "HISTORIA 1", "ÚLTIMA CONEXIÓN", false),
    EL_DEPARTAMENTO("story_2", "HISTORIA 2", "EL DEPARTAMENTO", false),
    LA_FOTO("story_3", "HISTORIA 3", "LA FOTO", false),
    EL_ULTIMO_TREN("story_4", "HISTORIA 4", "EL ÚLTIMO TREN", false),
    EL_TURNO_NOCHE("story_5", "HISTORIA 5", "EL TURNO NOCHE", false),
    EL_TRANSMISOR("story_6", "HISTORIA 6 [CASO REAL]", "EL PASO DIÁTLOV (1959)", true),
    LA_CABANA("story_7", "HISTORIA 7 [CASO REAL]", "HINTERKAIFECK (1922)", true),
    EL_ESPEJO("story_8", "HISTORIA 8 [CASO REAL]", "EL FARO DE FLANNAN (1900)", true)
}

enum class VisualSceneType {
    STREET_NIGHT,
    APARTMENT_DOOR,
    BEDROOM_DARK,
    CONVENIENCE_STORE,
    PEEPHOLE,
    ALLEYWAY,
    CORRIDOR,
    BALCONY,
    SUBWAY_PLATFORM,
    HOSPITAL_CORRIDOR,
    RADIO_HIGHWAY,
    FOREST_CABIN,
    BASEMENT_MIRROR,
    ENDING_SCREEN
}

enum class AmbientSoundType {
    RAIN_STREET,
    HUM_DRONE,
    STATIC_ROOM,
    SILENCE
}

enum class EndingBadge(val label: String, val colorHex: Long) {
    BUENO("FINAL BUENO", 0xFF00E676),
    MALO("FINAL MALO", 0xFFFF5252),
    TRAGICO("FINAL TRÁGICO", 0xFFFF9100),
    SECRETO("FINAL SECRETO", 0xFFE040FB),
    VERDADERO("FINAL VERDADERO", 0xFF00E5FF)
}

data class GameVariables(
    val fear: Int = 10,
    val trust: Int = 50,
    val curiosity: Int = 10,
    val evidence: Int = 0,
    val danger: Int = 5,
    val relationship: Int = 50
)

data class InventoryItem(
    val id: String,
    val name: String,
    val description: String,
    val icon: String,
    val isUsable: Boolean = true
)

data class Clue(
    val id: String,
    val storyId: StoryId,
    val title: String,
    val description: String,
    val details: String,
    val icon: String
)

data class PhoneNotification(
    val title: String,
    val text: String,
    val sender: String
)

data class PhoneCall(
    val callerName: String,
    val number: String,
    val transcript: String
)

data class ChoiceEffect(
    val fearDelta: Int = 0,
    val trustDelta: Int = 0,
    val curiosityDelta: Int = 0,
    val evidenceDelta: Int = 0,
    val dangerDelta: Int = 0,
    val relDelta: Int = 0,
    val itemGained: InventoryItem? = null,
    val itemUsed: String? = null,
    val clueGained: Clue? = null,
    val endingTriggered: String? = null,
    val soundTrigger: String? = null, // "KNOCK", "RING", "GLITCH", "STINGER", "HEARTBEAT"
    val glitchTrigger: Boolean = false,
    val phoneNotification: PhoneNotification? = null,
    val phoneCall: PhoneCall? = null
)

data class Choice(
    val id: String,
    val text: String,
    val targetSceneId: String,
    val requiredClueId: String? = null,
    val requiredItemId: String? = null,
    val effect: ChoiceEffect = ChoiceEffect()
)

data class Scene(
    val id: String,
    val storyId: StoryId,
    val chapterIndex: Int,
    val chapterTitle: String,
    val speaker: String? = null,
    val text: String,
    val visualType: VisualSceneType = VisualSceneType.STREET_NIGHT,
    val ambientSound: AmbientSoundType = AmbientSoundType.HUM_DRONE,
    val choices: List<Choice> = emptyList(),
    val isEndingScene: Boolean = false,
    val endingId: String? = null,
    val autoGlitch: Boolean = false
)

data class Ending(
    val id: String,
    val storyId: StoryId,
    val number: Int,
    val title: String,
    val badge: EndingBadge,
    val description: String,
    val quote: String,
    val newspaperClippingResId: Int? = null,
    val historicalCaseTitle: String? = null,
    val historicalFactSnippet: String? = null,
    val cinematicMovieEpilogue: String? = null
) {
    val fullCinematicEpilogue: String
        get() = cinematicMovieEpilogue ?: com.example.story.CinematicEndingsData.getEpilogue(id, storyId)

    val vhsImageResId: Int
        get() = com.example.story.CinematicEndingsData.getVhsEndingImageRes(id, storyId)

    val vhsTimestamp: String
        get() = com.example.story.CinematicEndingsData.getVhsTimestamp(storyId)
}

// Phone app data structures
data class ChatMessage(
    val id: String,
    val threadId: String,
    val sender: String,
    val isMe: Boolean,
    val text: String,
    val time: String,
    val hasPhoto: Boolean = false,
    val photoDrawableRes: Int? = null,
    val isVoiceNote: Boolean = false,
    val voiceDuration: String? = null
)

data class PhoneContact(
    val id: String,
    val name: String,
    val number: String,
    val avatarColorHex: Long,
    val lastSeen: String,
    val isUnknown: Boolean = false,
    val unreadCount: Int = 0
)

data class CallRecord(
    val id: String,
    val contactName: String,
    val number: String,
    val isMissed: Boolean,
    val timestamp: String,
    val isIncoming: Boolean = true
)

data class PhotoItem(
    val id: String,
    val title: String,
    val timestamp: String,
    val drawableRes: Int,
    val inspectionClue: String,
    val hasSuspiciousDetail: Boolean = false
)

data class NoteItem(
    val id: String,
    val title: String,
    val content: String,
    val timestamp: String
)

data class MapPoint(
    val id: String,
    val name: String,
    val description: String,
    val isCurrent: Boolean = false,
    val isUnlocked: Boolean = true,
    val isDanger: Boolean = false
)
