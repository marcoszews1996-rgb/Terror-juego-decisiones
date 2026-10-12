package com.example.audio

/**
 * Modelos para el sistema dinámico de audio, tensión y prioridades sonoras de "NO CONTESTES".
 */
enum class TensionLevel(val level: Int, val label: String) {
    TENSION_0(0, "Calma"),
    TENSION_1(1, "Inquietud"),
    TENSION_2(2, "Peligro"),
    TENSION_3(3, "Terror"),
    TENSION_4(4, "Amenaza Extrema")
}

enum class DecisionDangerType(val label: String) {
    NORMAL("Normal"),
    SOSPECHOSA("Sospechosa"),
    PELIGROSA("Peligrosa"),
    MUY_PELIGROSA("Muy Peligrosa"),
    CRITICA("Crítica")
}

enum class SoundPriority(val rank: Int) {
    CRITICAL_JUMPSCARE(1),
    DECISION_STINGER(2),
    VOICE(3),
    NARRATIVE_SFX(4),
    AMBIENT(5),
    MUSIC(6)
}

enum class FootstepType {
    NORMAL,
    DISTANT,
    RAPID,
    CREAKING_WOOD,
    BEHIND_STOPPED
}

enum class StingerVariant {
    STINGER_01,
    STINGER_02,
    STINGER_03,
    STINGER_04,
    STINGER_05,
    STINGER_06,
    STINGER_07,
    STINGER_08
}

enum class EndingAudioType {
    GOOD,
    BAD,
    SECRET,
    TRUE
}
