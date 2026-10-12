package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sin
import kotlin.random.Random

/**
 * Sistema integral de gestión de audio cinemático y tensión psicológica para "NO CONTESTES".
 * Extiende [AudioManager] para integrar el mezclador dinámico de 4 capas y la jerarquía de prioridades,
 * reproduciendo desde assets organizados (assets/audio/) con respaldo procedural en tiempo real.
 */
class SoundManager(context: Context) : AudioManager(context) {

    private var voiceMessageJob: Job? = null
    private var story1NotificationCount: Int = 0

    // ==========================================
    // 1. ATENUACIÓN Y SILENCIO COMO RECURSO DRAMÁTICO
    // ==========================================

    fun duckAmbient(durationMs: Long, targetFactor: Float = 0.12f) {
        requestPriorityDucking(SoundPriority.DECISION_STINGER, durationMs, targetFactor)
    }

    fun createDramaticPreSilence(preSilenceMs: Long = 320, onSoundTrigger: () -> Unit) {
        scope.launch {
            requestPriorityDucking(SoundPriority.CRITICAL_JUMPSCARE, preSilenceMs + 600, 0.02f)
            delay(preSilenceMs)
            onSoundTrigger()
        }
    }

    // ==========================================
    // 2. SONIDOS PARA DECISIONES PELIGROSAS & VARIABILIDAD
    // ==========================================

    fun playDecisionStinger(dangerType: DecisionDangerType) {
        when (dangerType) {
            DecisionDangerType.NORMAL -> {
                playSubtleClick()
            }
            DecisionDangerType.SOSPECHOSA -> {
                // Musical Horror Music Sting Uneasy Piano Tension (Pixabay #545298)
                playUneasyPianoSting()
                playSuspiciousNote()
            }
            DecisionDangerType.PELIGROSA -> {
                val variant = StingerVariant.values()[Random.nextInt(StingerVariant.values().size)]
                playDangerousStingerVariant(variant)
            }
            DecisionDangerType.MUY_PELIGROSA -> {
                playSoundAsset("stingers/stinger_dangerous.wav", SoundPriority.DECISION_STINGER)
                playHeavyDissonantImpact()
            }
            DecisionDangerType.CRITICA -> {
                // Musical Cinematic Piano Note (Pixabay #362716) con silencio previo dramático
                createDramaticPreSilence(preSilenceMs = 350) {
                    playCinematicPianoNote()
                    playCriticalBoom()
                }
            }
        }
    }

    private fun playSubtleClick() {
        if (sfxVolume <= 0f || masterVolume <= 0f) return
        scope.launch {
            val sampleRate = 22050
            val duration = 0.03
            val numSamples = (sampleRate * duration).toInt()
            val buffer = ShortArray(numSamples)
            val vol = (masterVolume * sfxVolume * 7000).toInt()
            for (i in 0 until numSamples) {
                val decay = 1.0 - (i.toDouble() / numSamples)
                val s = sin(2 * PI * 1800 * i / sampleRate) * 0.4 + (Random.nextFloat() * 2 - 1) * 0.6
                buffer[i] = (s * vol * decay).toInt().toShort()
            }
            playShortBuffer(buffer, sampleRate, SoundPriority.DECISION_STINGER)
        }
    }

    private fun playSuspiciousNote() {
        if (sfxVolume <= 0f || masterVolume <= 0f) return
        scope.launch {
            vibrate(40, 80)
            val sampleRate = 22050
            val duration = 0.55
            val numSamples = (sampleRate * duration).toInt()
            val buffer = ShortArray(numSamples)
            val vol = (masterVolume * sfxVolume * 15000).toInt()
            val freq = 65.4

            for (i in 0 until numSamples) {
                val t = i.toDouble() / sampleRate
                val envelope = exp(-t * 5.5)
                val s = sin(2 * PI * freq * t) * 0.7 + sin(2 * PI * freq * 2.01 * t) * 0.3
                buffer[i] = (s * vol * envelope).toInt().toShort()
            }
            playShortBuffer(buffer, sampleRate, SoundPriority.DECISION_STINGER)
        }
    }

    private fun playDangerousStingerVariant(variant: StingerVariant) {
        val assetName = when (variant) {
            StingerVariant.STINGER_01 -> "stingers/stinger_01.wav"
            StingerVariant.STINGER_02 -> "stingers/stinger_02.wav"
            StingerVariant.STINGER_03 -> "stingers/stinger_03.wav"
            StingerVariant.STINGER_04 -> "stingers/stinger_04.wav"
            StingerVariant.STINGER_05 -> "stingers/stinger_05.wav"
            StingerVariant.STINGER_06 -> "stingers/stinger_06.wav"
            StingerVariant.STINGER_07 -> "stingers/stinger_07.wav"
            StingerVariant.STINGER_08 -> "stingers/stinger_08.wav"
        }
        playSoundAsset(assetName, SoundPriority.DECISION_STINGER)

        if (sfxVolume <= 0f || masterVolume <= 0f) return
        scope.launch {
            vibrate(90, 150)
            val sampleRate = 22050
            val (baseFreq, clashFreq, duration, decayRate) = when (variant) {
                StingerVariant.STINGER_01 -> Quad(58.27, 61.74, 1.1, 3.2)
                StingerVariant.STINGER_02 -> Quad(65.41, 69.30, 0.9, 4.0)
                StingerVariant.STINGER_03 -> Quad(73.42, 103.83, 1.4, 2.5)
                StingerVariant.STINGER_04 -> Quad(55.00, 58.27, 1.3, 2.8)
                StingerVariant.STINGER_05 -> Quad(61.74, 87.31, 1.0, 3.6)
                StingerVariant.STINGER_06 -> Quad(82.41, 87.31, 0.8, 4.5)
                StingerVariant.STINGER_07 -> Quad(49.00, 51.91, 1.6, 2.2)
                StingerVariant.STINGER_08 -> Quad(69.30, 73.42, 1.2, 3.0)
            }

            val numSamples = (sampleRate * duration).toInt()
            val buffer = ShortArray(numSamples)
            val vol = (masterVolume * sfxVolume * 24000).toInt()

            for (i in 0 until numSamples) {
                val t = i.toDouble() / sampleRate
                val envelope = exp(-t * decayRate)
                val pitchSlide = 1.0 - (t * 0.04)
                val s1 = sin(2 * PI * baseFreq * pitchSlide * t) * 0.65
                val s2 = sin(2 * PI * clashFreq * pitchSlide * t) * 0.35
                val subBass = sin(2 * PI * (baseFreq * 0.5) * t) * 0.35
                val noiseTail = (Random.nextFloat() * 2 - 1) * 0.08 * exp(-t * 6.0)
                buffer[i] = ((s1 + s2 + subBass + noiseTail) * vol * envelope).toInt().toShort()
            }
            playShortBuffer(buffer, sampleRate, SoundPriority.DECISION_STINGER)
        }
    }

    private fun playHeavyDissonantImpact() {
        if (sfxVolume <= 0f || masterVolume <= 0f) return
        scope.launch {
            vibratePattern(longArrayOf(0, 120, 60, 180))
            duckAmbient(durationMs = 900, targetFactor = 0.1f)
            val sampleRate = 22050
            val duration = 1.8
            val numSamples = (sampleRate * duration).toInt()
            val buffer = ShortArray(numSamples)
            val vol = (masterVolume * sfxVolume * 27000).toInt()

            for (i in 0 until numSamples) {
                val t = i.toDouble() / sampleRate
                val norm = i.toDouble() / numSamples
                val envelope = exp(-norm * 2.8)
                val transient = exp(-t * 28.0) * (Random.nextFloat() * 2 - 1) * 0.5
                val n1 = sin(2 * PI * 46.2 * t)
                val n2 = sin(2 * PI * 49.0 * t)
                val n3 = sin(2 * PI * 65.4 * t)
                val sub = sin(2 * PI * 36.0 * t) * 0.7
                val tremolo = 0.85 + 0.15 * sin(2 * PI * 9.0 * t)
                buffer[i] = (((n1 + n2 + n3) * 0.33 + sub + transient) * vol * envelope * tremolo).toInt().toShort()
            }
            playShortBuffer(buffer, sampleRate, SoundPriority.DECISION_STINGER)
        }
    }

    private fun playCriticalBoom() {
        if (sfxVolume <= 0f || masterVolume <= 0f) return
        scope.launch {
            vibratePattern(longArrayOf(0, 200, 80, 300))
            val sampleRate = 22050
            val duration = 2.6
            val numSamples = (sampleRate * duration).toInt()
            val buffer = ShortArray(numSamples)
            val vol = (masterVolume * sfxVolume * 29000).toInt()

            for (i in 0 until numSamples) {
                val t = i.toDouble() / sampleRate
                val envelope = exp(-t * 1.4)
                val currentFreq = 32.0 + 53.0 * exp(-t * 3.5)
                val boom = sin(2 * PI * currentFreq * t) * 0.75
                val dissonantEcho = sin(2 * PI * 61.74 * t) * 0.25 * exp(-t * 1.8)
                val rumbleNoise = (Random.nextFloat() * 2 - 1) * 0.15 * exp(-t * 4.0)
                buffer[i] = ((boom + dissonantEcho + rumbleNoise) * vol * envelope).toInt().toShort()
            }
            playShortBuffer(buffer, sampleRate, SoundPriority.CRITICAL_JUMPSCARE)
        }
    }

    // ==========================================
    // 3. HISTORIA 1 — "ÚLTIMA CONEXIÓN"
    // ==========================================

    fun playStory1Notification(isLocationRevealed: Boolean = false) {
        if (sfxVolume <= 0f || masterVolume <= 0f) return
        story1NotificationCount++
        scope.launch {
            if (isLocationRevealed) {
                playSoundAsset("notifications/notif_location.wav", SoundPriority.CRITICAL_JUMPSCARE)
                createDramaticPreSilence(preSilenceMs = 280) {
                    playLocationRevealedSound()
                }
                return@launch
            }

            val asset = when {
                story1NotificationCount <= 1 -> "notifications/notif_normal.wav"
                story1NotificationCount == 2 -> "notifications/notif_low.wav"
                else -> "notifications/notif_glitch.wav"
            }
            playSoundAsset(asset, SoundPriority.NARRATIVE_SFX)

            val sampleRate = 22050
            val vol = (masterVolume * sfxVolume * 23000).toInt()

            when {
                story1NotificationCount <= 1 -> {
                    vibrate(70, 100)
                    val duration = 0.18
                    val numSamples = (sampleRate * duration).toInt()
                    val buffer = ShortArray(numSamples)
                    val half = numSamples / 2
                    for (i in 0 until half) {
                        val env = 1.0 - (i.toDouble() / half)
                        buffer[i] = (sin(2 * PI * 880 * i / sampleRate) * vol * env).toInt().toShort()
                    }
                    for (i in half until numSamples) {
                        val env = 1.0 - ((i - half).toDouble() / (numSamples - half))
                        buffer[i] = (sin(2 * PI * 1320 * (i - half) / sampleRate) * vol * env).toInt().toShort()
                    }
                    playShortBuffer(buffer, sampleRate, SoundPriority.NARRATIVE_SFX)
                }
                story1NotificationCount == 2 -> {
                    vibrate(100, 140)
                    val duration = 0.28
                    val numSamples = (sampleRate * duration).toInt()
                    val buffer = ShortArray(numSamples)
                    val half = numSamples / 2
                    for (i in 0 until half) {
                        val env = 1.0 - (i.toDouble() / half)
                        buffer[i] = (sin(2 * PI * 440 * i / sampleRate) * vol * env).toInt().toShort()
                    }
                    for (i in half until numSamples) {
                        val env = 1.0 - ((i - half).toDouble() / (numSamples - half))
                        buffer[i] = (sin(2 * PI * 587 * (i - half) / sampleRate) * vol * env).toInt().toShort()
                    }
                    playShortBuffer(buffer, sampleRate, SoundPriority.NARRATIVE_SFX)
                }
                else -> {
                    vibratePattern(longArrayOf(0, 80, 50, 120))
                    val duration = 0.4
                    val numSamples = (sampleRate * duration).toInt()
                    val buffer = ShortArray(numSamples)
                    for (i in 0 until numSamples) {
                        val t = i.toDouble() / sampleRate
                        val env = exp(-t * 6.0)
                        val chime = sin(2 * PI * 330 * t) * 0.5
                        val glitch = (Random.nextFloat() * 2 - 1) * 0.35
                        val sub = sin(2 * PI * 55 * t) * 0.4
                        buffer[i] = ((chime + glitch + sub) * vol * env).toInt().toShort()
                    }
                    playShortBuffer(buffer, sampleRate, SoundPriority.NARRATIVE_SFX)
                }
            }
        }
    }

    private fun playLocationRevealedSound() {
        vibratePattern(longArrayOf(0, 150, 80, 220))
        val sampleRate = 22050
        val duration = 1.8
        val numSamples = (sampleRate * duration).toInt()
        val buffer = ShortArray(numSamples)
        val vol = (masterVolume * sfxVolume * 26000).toInt()

        for (i in 0 until numSamples) {
            val t = i.toDouble() / sampleRate
            val env = exp(-t * 2.2)
            val n1 = sin(2 * PI * 55.0 * t) * 0.65
            val n2 = sin(2 * PI * 77.78 * t) * 0.35
            buffer[i] = ((n1 + n2) * vol * env).toInt().toShort()
        }
        playShortBuffer(buffer, sampleRate, SoundPriority.CRITICAL_JUMPSCARE)
    }

    // ==========================================
    // 4. HISTORIA 2 — "EL DEPARTAMENTO"
    // ==========================================

    fun playAsymmetricKnockPattern() {
        if (sfxVolume <= 0f || masterVolume <= 0f) return
        scope.launch {
            val sampleRate = 22050
            val vol = (masterVolume * sfxVolume * 28000).toInt()
            val patternType = Random.nextInt(4)
            val timingsMs = when (patternType) {
                0 -> listOf(0)
                1 -> listOf(0, 220)
                2 -> listOf(0, 480)
                else -> listOf(0, 200, 420)
            }

            val vibPattern = mutableListOf<Long>(0)
            timingsMs.forEach { _ ->
                vibPattern.add(80)
                vibPattern.add(140)
            }
            vibratePattern(vibPattern.toLongArray())

            val totalDuration = (timingsMs.last() + 250) / 1000.0
            val numSamples = (sampleRate * totalDuration).toInt()
            val buffer = ShortArray(numSamples)

            timingsMs.forEach { timeMs ->
                val startIdx = (timeMs * sampleRate / 1000).toInt()
                val knockLen = (sampleRate * 0.12).toInt()
                for (i in 0 until knockLen) {
                    val idx = startIdx + i
                    if (idx < numSamples) {
                        val t = i.toDouble() / knockLen
                        val decay = exp(-t * 22.0)
                        val s = sin(2 * PI * 92 * i / sampleRate) * 0.7 +
                                sin(2 * PI * 210 * i / sampleRate) * 0.2 +
                                (Random.nextFloat() * 2 - 1) * 0.1
                        buffer[idx] = (s * vol * decay).toInt().toShort()
                    }
                }
            }
            playShortBuffer(buffer, sampleRate, SoundPriority.NARRATIVE_SFX)
        }
    }

    fun playPeepholeNothingSequence(onBehindThump: () -> Unit = {}) {
        scope.launch {
            duckAmbient(durationMs = 2800, targetFactor = 0.0f)
            delay(2200)
            playBehindPlayerThump()
            onBehindThump()
        }
    }

    private fun playBehindPlayerThump() {
        playSoundAsset("footsteps/step_behind.wav", SoundPriority.CRITICAL_JUMPSCARE)
        if (sfxVolume <= 0f || masterVolume <= 0f) return
        scope.launch {
            vibrate(120, 180)
            val sampleRate = 22050
            val duration = 0.45
            val numSamples = (sampleRate * duration).toInt()
            val buffer = ShortArray(numSamples)
            val vol = (masterVolume * sfxVolume * 26000).toInt()

            for (i in 0 until numSamples) {
                val t = i.toDouble() / sampleRate
                val env = exp(-t * 14.0)
                val thud = sin(2 * PI * 68.0 * t) * 0.7 + (Random.nextFloat() * 2 - 1) * 0.3
                buffer[i] = (thud * vol * env).toInt().toShort()
            }
            playShortBuffer(buffer, sampleRate, SoundPriority.CRITICAL_JUMPSCARE)
        }
    }

    // ==========================================
    // 5. HISTORIA 3 — "LA FOTO"
    // ==========================================

    fun playPhotoInspectSound(hasAnomaly: Boolean = false) {
        if (sfxVolume <= 0f || masterVolume <= 0f) return
        scope.launch {
            vibrate(45, 100)
            val sampleRate = 22050
            val duration = if (hasAnomaly) 1.2 else 0.3
            val numSamples = (sampleRate * duration).toInt()
            val buffer = ShortArray(numSamples)
            val vol = (masterVolume * sfxVolume * 24000).toInt()

            val clickSamples = (sampleRate * 0.15).toInt()
            for (i in 0 until clickSamples) {
                val decay = exp(- (i.toDouble() / clickSamples) * 16.0)
                buffer[i] = ((Random.nextFloat() * 2 - 1) * vol * decay).toInt().toShort()
            }

            if (hasAnomaly) {
                val offset = (sampleRate * 0.16).toInt()
                for (i in offset until numSamples) {
                    val t = (i - offset).toDouble() / sampleRate
                    val env = exp(-t * 2.8)
                    val eerieTone = sin(2 * PI * 138.59 * t) * 0.45
                    val subDrone = sin(2 * PI * 69.29 * t) * 0.35
                    buffer[i] = ((eerieTone + subDrone) * vol * env).toInt().toShort()
                }
            }
            playShortBuffer(buffer, sampleRate, SoundPriority.NARRATIVE_SFX)
        }
    }

    fun playPhotoChangedUnexpectedly() {
        scope.launch {
            playSoundAsset("stingers/stinger_critical.wav", SoundPriority.CRITICAL_JUMPSCARE)
            duckAmbient(durationMs = 1800, targetFactor = 0.0f)
            delay(300)
            vibrate(100, 200)

            val sampleRate = 22050
            val duration = 1.4
            val numSamples = (sampleRate * duration).toInt()
            val buffer = ShortArray(numSamples)
            val vol = (masterVolume * sfxVolume * 25000).toInt()

            val glitchSamples = (sampleRate * 0.2).toInt()
            for (i in 0 until glitchSamples) {
                val t = i.toDouble() / glitchSamples
                val env = 1.0 - t
                val noise = (Random.nextFloat() * 2 - 1) * 0.7
                val buzz = sin(2 * PI * 120 * i / sampleRate) * 0.3
                buffer[i] = ((noise + buzz) * vol * env).toInt().toShort()
            }

            for (i in glitchSamples until numSamples) {
                val t = (i - glitchSamples).toDouble() / sampleRate
                val env = exp(-t * 3.0)
                val piano = sin(2 * PI * 51.91 * t) * 0.75
                val sub = sin(2 * PI * 25.95 * t) * 0.35
                buffer[i] = ((piano + sub) * vol * env).toInt().toShort()
            }

            playShortBuffer(buffer, sampleRate, SoundPriority.CRITICAL_JUMPSCARE)
        }
    }

    // ==========================================
    // 6. MENSAJES DE VOZ (0:07)
    // ==========================================

    fun playVoiceMessageAudio(onProgress: (Float) -> Unit, onFinished: () -> Unit) {
        if (voiceVolume <= 0f || masterVolume <= 0f) {
            onFinished()
            return
        }
        voiceMessageJob?.cancel()
        voiceMessageJob = scope.launch {
            playSoundAsset("voices/voice_message_07s.wav", SoundPriority.VOICE, duckTimeMs = 7500)
            duckAmbient(durationMs = 7500, targetFactor = 0.08f)
            vibrate(50)

            val totalSeconds = 7.0
            val sampleRate = 22050
            val numSamples = (sampleRate * totalSeconds).toInt()
            val buffer = ShortArray(numSamples)
            val vol = (masterVolume * voiceVolume * 23000).toInt()

            for (i in 0 until numSamples) {
                val t = i.toDouble() / sampleRate
                val s = when {
                    t < 1.8 -> (Random.nextFloat() * 2 - 1) * 0.12
                    t < 3.5 -> {
                        val breathPhase = sin(2 * PI * 0.7 * (t - 1.8))
                        (Random.nextFloat() * 2 - 1) * 0.18 * (breathPhase + 1.0) * 0.5
                    }
                    t < 5.8 -> {
                        val speech1 = sin(2 * PI * (320 + 60 * sin(2 * PI * 4 * t)) * t) * 0.3
                        val speech2 = sin(2 * PI * (740 + 100 * sin(2 * PI * 3 * t)) * t) * 0.2
                        val whisper = (Random.nextFloat() * 2 - 1) * 0.22
                        speech1 + speech2 + whisper
                    }
                    else -> {
                        val sub = sin(2 * PI * 48.0 * (t - 5.8)) * 0.4
                        val tail = (Random.nextFloat() * 2 - 1) * 0.08
                        sub + tail
                    }
                }
                buffer[i] = (s * vol).toInt().toShort()
            }

            playShortBuffer(buffer, sampleRate, SoundPriority.VOICE)

            val startTime = System.currentTimeMillis()
            while (isActive) {
                val elapsed = (System.currentTimeMillis() - startTime) / 1000f
                val progress = (elapsed / totalSeconds.toFloat()).coerceIn(0f, 1f)
                onProgress(progress)
                if (progress >= 1f) break
                delay(100)
            }
            onFinished()
        }
    }

    // ==========================================
    // 7. PASOS DIFERENCIADOS & MOTIVO DE CRIATURA
    // ==========================================

    fun playFootsteps(type: FootstepType) {
        val asset = when (type) {
            FootstepType.NORMAL -> "footsteps/step_normal.wav"
            FootstepType.DISTANT -> "footsteps/step_distant.wav"
            FootstepType.RAPID -> "footsteps/step_rapid.wav"
            FootstepType.CREAKING_WOOD -> "footsteps/step_wood.wav"
            FootstepType.BEHIND_STOPPED -> "footsteps/step_behind.wav"
        }
        playSoundAsset(asset, SoundPriority.NARRATIVE_SFX)

        if (sfxVolume <= 0f || masterVolume <= 0f) return
        scope.launch {
            val sampleRate = 22050
            val vol = (masterVolume * sfxVolume * 24000).toInt()

            when (type) {
                FootstepType.NORMAL -> {
                    vibrate(40, 80)
                    val numSamples = (sampleRate * 0.2).toInt()
                    val buffer = ShortArray(numSamples)
                    for (i in 0 until numSamples) {
                        val t = i.toDouble() / numSamples
                        val env = exp(-t * 18.0)
                        val thud = sin(2 * PI * 75 * i / sampleRate) * 0.6 + (Random.nextFloat() * 2 - 1) * 0.4
                        buffer[i] = (thud * vol * env).toInt().toShort()
                    }
                    playShortBuffer(buffer, sampleRate, SoundPriority.NARRATIVE_SFX)
                }
                FootstepType.DISTANT -> {
                    val numSamples = (sampleRate * 0.35).toInt()
                    val buffer = ShortArray(numSamples)
                    for (i in 0 until numSamples) {
                        val t = i.toDouble() / numSamples
                        val env = exp(-t * 8.0)
                        val lowEcho = sin(2 * PI * 55 * i / sampleRate) * 0.4
                        buffer[i] = (lowEcho * vol * 0.5 * env).toInt().toShort()
                    }
                    playShortBuffer(buffer, sampleRate, SoundPriority.NARRATIVE_SFX)
                }
                FootstepType.RAPID -> {
                    vibratePattern(longArrayOf(0, 30, 90, 30, 90, 30))
                    val numSamples = (sampleRate * 0.6).toInt()
                    val buffer = ShortArray(numSamples)
                    val stepSamples = (sampleRate * 0.12).toInt()
                    for (s in 0 until 4) {
                        val start = s * (sampleRate * 0.14).toInt()
                        for (i in 0 until stepSamples) {
                            val idx = start + i
                            if (idx < numSamples) {
                                val t = i.toDouble() / stepSamples
                                val env = exp(-t * 16.0)
                                val thud = sin(2 * PI * 85 * i / sampleRate) * 0.7 + (Random.nextFloat() * 2 - 1) * 0.3
                                buffer[idx] = (thud * vol * env).toInt().toShort()
                            }
                        }
                    }
                    playShortBuffer(buffer, sampleRate, SoundPriority.NARRATIVE_SFX)
                }
                FootstepType.CREAKING_WOOD -> {
                    vibrate(70, 110)
                    val numSamples = (sampleRate * 0.45).toInt()
                    val buffer = ShortArray(numSamples)
                    for (i in 0 until numSamples) {
                        val t = i.toDouble() / sampleRate
                        val env = exp(-t * 7.0)
                        val creak = sin(2 * PI * (280 + sin(2 * PI * 8 * t) * 80) * t) * 0.5
                        val footThud = sin(2 * PI * 60 * t) * 0.5
                        buffer[i] = ((creak + footThud) * vol * env).toInt().toShort()
                    }
                    playShortBuffer(buffer, sampleRate, SoundPriority.NARRATIVE_SFX)
                }
                FootstepType.BEHIND_STOPPED -> {
                    vibratePattern(longArrayOf(0, 50, 220, 60, 220, 80))
                    val duration = 1.3
                    val numSamples = (sampleRate * duration).toInt()
                    val buffer = ShortArray(numSamples)
                    val starts = listOf(0.0, 0.35, 0.70)
                    starts.forEachIndexed { idx, st ->
                        val startIdx = (st * sampleRate).toInt()
                        val stepLen = (sampleRate * 0.16).toInt()
                        for (i in 0 until stepLen) {
                            val cur = startIdx + i
                            if (cur < numSamples) {
                                val t = i.toDouble() / stepLen
                                val env = exp(-t * 14.0)
                                val gain = 0.6f + (idx * 0.2f)
                                val s = (sin(2 * PI * 65 * i / sampleRate) * 0.65 + (Random.nextFloat() * 2 - 1) * 0.35) * gain
                                buffer[cur] = (s * vol * env).toInt().toShort()
                            }
                        }
                    }
                    playShortBuffer(buffer, sampleRate, SoundPriority.NARRATIVE_SFX)
                }
            }
        }
    }

    fun playCreatureLeitmotif() {
        playSoundAsset("creature/creature_presence_motif.wav", SoundPriority.NARRATIVE_SFX)
        if (sfxVolume <= 0f || masterVolume <= 0f) return
        scope.launch {
            vibrate(180, 110)
            duckAmbient(durationMs = 2500, targetFactor = 0.15f)
            val sampleRate = 22050
            val duration = 2.4
            val numSamples = (sampleRate * duration).toInt()
            val buffer = ShortArray(numSamples)
            val vol = (masterVolume * sfxVolume * 22000).toInt()

            for (i in 0 until numSamples) {
                val t = i.toDouble() / sampleRate
                val norm = i.toDouble() / numSamples
                val env = sin(norm * PI)
                val freq = 110.0 - 32.22 * norm
                val flutter = sin(2 * PI * 6.5 * t) * 0.25
                val s = sin(2 * PI * freq * t) * (0.75 + flutter)
                val sub = sin(2 * PI * (freq * 0.5) * t) * 0.35
                buffer[i] = ((s + sub) * vol * env).toInt().toShort()
            }
            playShortBuffer(buffer, sampleRate, SoundPriority.NARRATIVE_SFX)
        }
    }

    // ==========================================
    // 8. FINALES
    // ==========================================

    fun playEndingTheme(type: EndingAudioType) {
        val asset = when (type) {
            EndingAudioType.GOOD -> "music/minimal_piano_underscore.wav" // Pixabay #456148
            EndingAudioType.BAD -> "music/ending_bad.wav"
            EndingAudioType.SECRET -> "music/ending_secret.wav"
            EndingAudioType.TRUE -> "music/ending_true.wav"
        }
        playSoundAsset(asset, SoundPriority.CRITICAL_JUMPSCARE, duckTimeMs = 4000)

        if (musicVolume <= 0f || masterVolume <= 0f) return
        scope.launch {
            stopAmbient()
            val sampleRate = 22050
            val duration = 3.5
            val numSamples = (sampleRate * duration).toInt()
            val buffer = ShortArray(numSamples)
            val vol = (masterVolume * musicVolume * 24000).toInt()

            when (type) {
                EndingAudioType.GOOD -> {
                    for (i in 0 until numSamples) {
                        val t = i.toDouble() / sampleRate
                        val env = sin((i.toDouble() / numSamples) * PI)
                        val chord = sin(2 * PI * 130.81 * t) * 0.4 +
                                sin(2 * PI * 164.81 * t) * 0.3 +
                                sin(2 * PI * 196.00 * t) * 0.3
                        buffer[i] = (chord * vol * env).toInt().toShort()
                    }
                }
                EndingAudioType.BAD -> {
                    for (i in 0 until numSamples) {
                        val t = i.toDouble() / sampleRate
                        val env = exp(-t * 0.9)
                        val drone = sin(2 * PI * 43.65 * t) * 0.7 + sin(2 * PI * 46.25 * t) * 0.3
                        buffer[i] = (drone * vol * env).toInt().toShort()
                    }
                }
                EndingAudioType.SECRET -> {
                    val silenceSamples = (sampleRate * 0.8).toInt()
                    for (i in silenceSamples until numSamples) {
                        val t = (i - silenceSamples).toDouble() / sampleRate
                        val env = sin(((i - silenceSamples).toDouble() / (numSamples - silenceSamples)) * PI)
                        val melody = sin(2 * PI * (220.0 + 40.0 * sin(2 * PI * 0.5 * t)) * t) * 0.6
                        buffer[i] = (melody * vol * env).toInt().toShort()
                    }
                }
                EndingAudioType.TRUE -> {
                    for (i in 0 until numSamples) {
                        val t = i.toDouble() / sampleRate
                        val env = sin((i.toDouble() / numSamples) * PI)
                        val fullChord = sin(2 * PI * 65.41 * t) * 0.35 +
                                sin(2 * PI * 98.00 * t) * 0.35 +
                                sin(2 * PI * 146.83 * t) * 0.30
                        buffer[i] = (fullChord * vol * env).toInt().toShort()
                    }
                }
            }
            playShortBuffer(buffer, sampleRate, SoundPriority.CRITICAL_JUMPSCARE)
        }
    }

    // ==========================================
    // 8.1. SONIDOS PIXABAY ESPECÍFICOS
    // ==========================================

    /**
     * Pixabay #545298: Musical Horror Music Sting Uneasy Piano Tension
     * Utilizado para tensión psicológica y decisiones sospechosas.
     */
    fun playUneasyPianoSting() {
        playSoundAsset("stingers/stinger_uneasy_piano.wav", SoundPriority.DECISION_STINGER, duckTimeMs = 2400)
    }

    /**
     * Pixabay #456148: Musical Minimal Piano Underscore
     * Utilizado para momentos introspectivos, finales buenos y calma melancólica.
     */
    fun playMinimalPianoUnderscore() {
        playSoundAsset("music/minimal_piano_underscore.wav", SoundPriority.MUSIC, duckTimeMs = 7000)
    }

    /**
     * Pixabay #362716: Musical Cinematic Piano Note
     * Nota grave cinematográfica de piano para revelaciones impactantes y decisiones críticas.
     */
    fun playCinematicPianoNote() {
        playSoundAsset("stingers/cinematic_piano_note.wav", SoundPriority.CRITICAL_JUMPSCARE, duckTimeMs = 3200)
    }

    /**
     * Grito aterrador, sonido repentino de tensión disonante e interferencia estática
     * para Jumpscare en pantalla completa (2 segundos).
     */
    fun playTerrifyingJumpscareScream() {
        vibratePattern(longArrayOf(0, 180, 40, 250, 30, 300, 40, 450))
        playSoundAsset("stingers/scream_terrifying_jumpscare.wav", SoundPriority.CRITICAL_JUMPSCARE, duckTimeMs = 2500)
        // Impacto procedural inmediato: acorde de tensión repentino (Trítono) y ráfaga de estática violenta
        scope.launch {
            val sampleRate = 22050
            val duration = 1.9
            val numSamples = (sampleRate * duration).toInt()
            val buffer = ShortArray(numSamples)
            val vol = (masterVolume * sfxVolume * 30000).toInt()
            var p1 = 0.0
            var p2 = 0.0
            var p3 = 0.0
            var noiseFilter = 0.0
            for (i in 0 until numSamples) {
                val t = i.toDouble() / numSamples
                val env = Math.exp(-2.8 * t)
                // Ráfaga de estática analógica
                val whiteNoise = Random.nextFloat() * 2 - 1
                noiseFilter += 0.35 * (whiteNoise - noiseFilter)
                // Acorde de tensión repentina disonante (Mi bemol y La)
                val tone1 = Math.sin(p1) * 0.35
                val tone2 = Math.sin(p2) * 0.35
                val screech = Math.sin(p3) * 0.30
                p1 += 2 * Math.PI * 311.1 / sampleRate
                p2 += 2 * Math.PI * 440.0 / sampleRate
                p3 += 2 * Math.PI * 880.0 * (1.0 + 0.12 * Math.sin(2 * Math.PI * 18.0 * t)) / sampleRate
                val mixed = (noiseFilter * 0.55 + tone1 + tone2 + screech) * env
                buffer[i] = (mixed * vol).toInt().coerceIn(-32767, 32767).toShort()
            }
            playShortBuffer(buffer, sampleRate, SoundPriority.CRITICAL_JUMPSCARE)
        }
    }

    // ==========================================
    // 9. MÁQUINA DE ESCRIBIR CALIBRADA
    // ==========================================

    fun playTypewriterTick() {
        if (sfxVolume <= 0f || masterVolume <= 0f) return
        scope.launch {
            val sampleRate = 22050
            val duration = 0.015
            val numSamples = (sampleRate * duration).toInt()
            val buffer = ShortArray(numSamples)
            val vol = (masterVolume * sfxVolume * 8500).toInt()
            for (i in 0 until numSamples) {
                val decay = 1.0 - (i.toDouble() / numSamples)
                val noise = Random.nextFloat() * 2 - 1
                buffer[i] = (noise * vol * decay).toInt().toShort()
            }
            playShortBuffer(buffer, sampleRate, SoundPriority.NARRATIVE_SFX)
        }
    }

    // ==========================================
    // 10. MÚSICA AMBIENTAL Y MOTOR DE CAPAS
    // ==========================================

    private var userAmbientPlayer: android.media.MediaPlayer? = null
    private var currentAmbientAsset: String? = null

    /**
     * Establece la música ambiental y composición procedural de terror
     * personalizada para cada una de las 8 historias del juego.
     */
    fun setStoryTheme(storyId: com.example.model.StoryId) {
        val themeKey = when (storyId) {
            com.example.model.StoryId.ULTIMA_CONEXION -> "STORY_1_RAIN_STALKER"
            com.example.model.StoryId.EL_DEPARTAMENTO -> "STORY_2_APARTMENT_CREAK"
            com.example.model.StoryId.LA_FOTO -> "STORY_3_CAMERA_STATIC"
            com.example.model.StoryId.EL_ULTIMO_TREN -> "STORY_4_SUBWAY_RUMBLE"
            com.example.model.StoryId.EL_TURNO_NOCHE -> "STORY_5_HOSPITAL_DEATH"
            com.example.model.StoryId.EL_TRANSMISOR -> "STORY_6_DYATLOV_VORTEX"
            com.example.model.StoryId.LA_CABANA -> "STORY_7_HINTERKAIFECK_FARM"
            com.example.model.StoryId.EL_ESPEJO -> "STORY_8_FLANNAN_OCEAN"
        }
        val assetPath = when (storyId) {
            com.example.model.StoryId.ULTIMA_CONEXION -> "audio/ambient/ambient_rain_street.wav"
            com.example.model.StoryId.EL_DEPARTAMENTO -> "audio/ambient/ambient_static_room.wav"
            com.example.model.StoryId.LA_FOTO -> "audio/ambient/ambient_static_room.wav"
            com.example.model.StoryId.EL_ULTIMO_TREN -> "audio/ambient/ambient_dark_subway.wav"
            com.example.model.StoryId.EL_TURNO_NOCHE -> "audio/ambient/ambient_user_background.wav"
            com.example.model.StoryId.EL_TRANSMISOR -> "audio/ambient/ambient_rain_street.wav"
            com.example.model.StoryId.LA_CABANA -> "audio/ambient/ambient_user_background.wav"
            com.example.model.StoryId.EL_ESPEJO -> "audio/ambient/ambient_dark_subway.wav"
        }
        playStoryBackgroundAmbient(assetPath)
        startMusicLayerMixer(themeKey)
    }

    fun playStoryBackgroundAmbient(assetPath: String) {
        if (ambientVolume <= 0f || masterVolume <= 0f) return
        if (userAmbientPlayer?.isPlaying == true && currentAmbientAsset == assetPath) return
        stopUserBackgroundAmbient()
        try {
            val afd = context.assets.openFd(assetPath)
            val mp = android.media.MediaPlayer()
            mp.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            afd.close()
            mp.isLooping = true
            val vol = (masterVolume * ambientVolume * 0.95f).coerceIn(0f, 1f)
            mp.setVolume(vol, vol)
            mp.prepare()
            mp.start()
            userAmbientPlayer = mp
            currentAmbientAsset = assetPath
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Reproduce en bucle continuo el audio ambiental oscuro subido por el usuario
     * (ambient_user_background.wav) con drones subsónicos, resonancias metálicas y tensión.
     */
    fun playUserBackgroundAmbient() {
        playStoryBackgroundAmbient("audio/ambient/ambient_user_background.wav")
    }

    fun stopUserBackgroundAmbient() {
        try {
            userAmbientPlayer?.stop()
            userAmbientPlayer?.release()
        } catch (_: Exception) {}
        userAmbientPlayer = null
        currentAmbientAsset = null
    }

    fun startAmbient(type: String) {
        startMusicLayerMixer(type)
        playUserBackgroundAmbient()
    }

    fun stopAmbient() {
        stopMusicMixer()
        stopUserBackgroundAmbient()
    }

    // ==========================================
    // 11. UTILIDADES Y CONVENIENCIA
    // ==========================================

    fun playNotificationBeep() {
        playSoundAsset("notifications/notif_normal.wav", SoundPriority.NARRATIVE_SFX)
        if (sfxVolume <= 0f || masterVolume <= 0f) return
        scope.launch {
            vibrate(70)
            val sampleRate = 22050
            val duration = 0.18
            val numSamples = (sampleRate * duration).toInt()
            val buffer = ShortArray(numSamples)
            val vol = (masterVolume * sfxVolume * 22000).toInt()
            val half = numSamples / 2
            for (i in 0 until half) {
                val env = 1.0 - (i.toDouble() / half)
                buffer[i] = (sin(2 * PI * 880 * i / sampleRate) * vol * env).toInt().toShort()
            }
            for (i in half until numSamples) {
                val env = 1.0 - ((i - half).toDouble() / (numSamples - half))
                buffer[i] = (sin(2 * PI * 1320 * (i - half) / sampleRate) * vol * env).toInt().toShort()
            }
            playShortBuffer(buffer, sampleRate, SoundPriority.NARRATIVE_SFX)
        }
    }

    fun playStaticGlitch() {
        playSoundAsset("notifications/notif_glitch.wav", SoundPriority.NARRATIVE_SFX)
        if (sfxVolume <= 0f || masterVolume <= 0f) return
        scope.launch {
            vibrate(120, 190)
            val sampleRate = 22050
            val duration = 0.22
            val numSamples = (sampleRate * duration).toInt()
            val buffer = ShortArray(numSamples)
            val vol = (masterVolume * sfxVolume * 24000).toInt()
            for (i in 0 until numSamples) {
                val env = 1.0 - (i.toDouble() / numSamples)
                val noise = (Random.nextFloat() * 2 - 1) * 0.75
                val buzz = sin(2 * PI * 180 * i / sampleRate) * 0.25
                buffer[i] = ((noise + buzz) * vol * env).toInt().toShort()
            }
            playShortBuffer(buffer, sampleRate, SoundPriority.NARRATIVE_SFX)
        }
    }

    fun playPhoneRing() {
        if (sfxVolume <= 0f || masterVolume <= 0f) return
        scope.launch {
            vibratePattern(longArrayOf(0, 250, 150, 250))
            val sampleRate = 22050
            val duration = 0.75
            val numSamples = (sampleRate * duration).toInt()
            val buffer = ShortArray(numSamples)
            val vol = (masterVolume * sfxVolume * 21000).toInt()
            for (i in 0 until numSamples) {
                val t = i.toDouble() / sampleRate
                val vibrato = 1.0 + 0.1 * sin(2 * PI * 15 * t)
                val s1 = sin(2 * PI * 700 * vibrato * t)
                val s2 = sin(2 * PI * 940 * vibrato * t)
                val pulse = if ((t % 0.25) < 0.18) 1.0 else 0.0
                buffer[i] = ((s1 + s2) * 0.5 * vol * pulse).toInt().toShort()
            }
            playShortBuffer(buffer, sampleRate, SoundPriority.NARRATIVE_SFX)
        }
    }

    fun playCameraShutter() {
        playPhotoInspectSound(hasAnomaly = false)
    }

    fun playKnockSound() {
        playAsymmetricKnockPattern()
    }

    fun playHorrorStinger() {
        playDangerousStingerVariant(StingerVariant.STINGER_01)
    }

    fun playHeartbeatSound(fearLevel: Int = 20) {
        if (sfxVolume <= 0f || masterVolume <= 0f) return
        scope.launch {
            val beats = if (fearLevel > 60) 3 else 2
            vibratePattern(longArrayOf(0, 60, 90, 80))
            val sampleRate = 22050
            val beatInterval = if (fearLevel > 60) 0.38 else 0.58
            val duration = beatInterval * beats
            val numSamples = (sampleRate * duration).toInt()
            val buffer = ShortArray(numSamples)
            val vol = (masterVolume * sfxVolume * 28000).toInt()

            val singleThump = (sampleRate * 0.14).toInt()
            for (b in 0 until beats) {
                val start = (b * beatInterval * sampleRate).toInt()
                for (i in 0 until singleThump) {
                    val idx = start + i
                    if (idx < numSamples) {
                        val t = i.toDouble() / singleThump
                        val decay = exp(-t * 14.0)
                        val s = sin(2 * PI * 54 * i / sampleRate)
                        buffer[idx] = (s * vol * decay).toInt().toShort()
                    }
                }
            }
            playShortBuffer(buffer, sampleRate, SoundPriority.NARRATIVE_SFX)
        }
    }

    fun triggerNarrativeSound(cue: String?, fearLevel: Int = 10) {
        if (cue.isNullOrEmpty()) return
        when (cue.uppercase()) {
            "STINGER" -> playHorrorStinger()
            "KNOCK" -> playAsymmetricKnockPattern()
            "HEARTBEAT" -> playHeartbeatSound(fearLevel)
            "FOOTSTEP" -> playFootsteps(FootstepType.NORMAL)
            "GLITCH" -> playStaticGlitch()
            "RING" -> playPhoneRing()
            "NOTIFICATION" -> playNotificationBeep()
            "CREATURE" -> playCreatureLeitmotif()
            "UNEASY_PIANO" -> playUneasyPianoSting()
            "MINIMAL_PIANO" -> playMinimalPianoUnderscore()
            "CINEMATIC_PIANO" -> playCinematicPianoNote()
            "SCREAM_JUMPSCARE" -> playTerrifyingJumpscareScream()
            else -> playHorrorStinger()
        }
    }

    private fun playShortBuffer(buffer: ShortArray, sampleRate: Int, priority: SoundPriority) {
        try {
            val track = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(
                            if (priority == SoundPriority.VOICE) AudioAttributes.CONTENT_TYPE_SPEECH
                            else AudioAttributes.CONTENT_TYPE_SONIFICATION
                        )
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(buffer.size * 2)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            track.write(buffer, 0, buffer.size)
            track.play()
            scope.launch {
                val delayMs = ((buffer.size.toDouble() / sampleRate) * 1000).toLong() + 100
                delay(delayMs)
                try {
                    track.stop()
                    track.release()
                } catch (_: Exception) {}
            }
        } catch (_: Exception) {}
    }

    override fun release() {
        super.release()
        voiceMessageJob?.cancel()
    }

    private data class Quad(val baseFreq: Double, val clashFreq: Double, val duration: Double, val decayRate: Double)
}
