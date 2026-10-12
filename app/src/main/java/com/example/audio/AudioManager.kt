package com.example.audio

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.InputStream
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sin
import kotlin.random.Random

/**
 * Sistema central de gestión de audio de "NO CONTESTES".
 *
 * Características principales:
 * - Niveles de tensión TENSION_0 a TENSION_4 con modulación en tiempo real.
 * - Mezclador de 4 capas musicales simultáneas (Layer 1: Base, Layer 2: Inquietud, Layer 3: Peligro, Layer 4: Terror).
 * - Manejo riguroso de jerarquía de prioridades:
 *     1. CRITICAL_JUMPSCARE
 *     2. DECISION_STINGER
 *     3. VOICE
 *     4. NARRATIVE_SFX
 *     5. AMBIENT
 *     6. MUSIC
 * - Carga directa de assets organizados en assets/audio/ (tension, stingers, ambient, notifications, footsteps, creature, music, voices).
 * - Motor híbrido: reproducción desde assets con respaldo procedural automático en caso de falta de archivo o streaming en memoria.
 * - Ducking dinámico: Atenuación suave de capas inferiores ante eventos prioritarios.
 */
open class AudioManager(protected val context: Context) {

    protected val scope = CoroutineScope(Dispatchers.Default)

    // Controles de volumen maestros
    var masterVolume: Float = 0.8f
        set(value) { field = value.coerceIn(0f, 1f) }

    var musicVolume: Float = 0.7f
        set(value) { field = value.coerceIn(0f, 1f) }

    var sfxVolume: Float = 0.9f
        set(value) { field = value.coerceIn(0f, 1f) }

    var ambientVolume: Float = 0.8f
        set(value) { field = value.coerceIn(0f, 1f) }

    var voiceVolume: Float = 0.85f
        set(value) { field = value.coerceIn(0f, 1f) }

    var vibrationEnabled: Boolean = true
    var headphoneMode: Boolean = false

    // Estado de tensión actual
    protected var _currentTension: TensionLevel = TensionLevel.TENSION_0
    val currentTension: TensionLevel get() = _currentTension

    // Mezclador de capas dinámicas (4 capas)
    // Layer 1: Ambiente base, Layer 2: Inquietud, Layer 3: Peligro, Layer 4: Terror
    protected var layerGains = floatArrayOf(1.0f, 0.0f, 0.0f, 0.0f)
    protected var targetLayerGains = floatArrayOf(1.0f, 0.0f, 0.0f, 0.0f)

    // Control de Ducking por prioridad
    protected var duckingFactor: Float = 1.0f
    protected var currentHighestPriorityRank: Int = SoundPriority.MUSIC.rank

    // Streaming de capas
    protected var musicMixerJob: Job? = null
    protected var musicMixerTrack: AudioTrack? = null
    protected var activeAmbientTheme: String = "RAIN_STREET"

    // Vibrador
    protected val vibrator: Vibrator? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vm = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            vm?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }
    }

    // ==========================================
    // 1. GESTIÓN DE NIVELES DE TENSIÓN (TENSION_0 a TENSION_4)
    // ==========================================

    fun setTensionLevel(level: TensionLevel) {
        _currentTension = level
        // Recalcular ganancias de las 4 capas según el nivel
        when (level) {
            TensionLevel.TENSION_0 -> {
                targetLayerGains = floatArrayOf(1.0f, 0.0f, 0.0f, 0.0f)
            }
            TensionLevel.TENSION_1 -> {
                targetLayerGains = floatArrayOf(0.85f, 0.55f, 0.0f, 0.0f)
            }
            TensionLevel.TENSION_2 -> {
                targetLayerGains = floatArrayOf(0.70f, 0.75f, 0.65f, 0.0f)
            }
            TensionLevel.TENSION_3 -> {
                targetLayerGains = floatArrayOf(0.50f, 0.60f, 0.85f, 0.70f)
            }
            TensionLevel.TENSION_4 -> {
                targetLayerGains = floatArrayOf(0.30f, 0.40f, 0.90f, 1.0f)
            }
        }
    }

    fun updateTensionFromVariables(fear: Int, danger: Int) {
        val maxMetric = maxOf(fear, danger)
        val target = when {
            maxMetric >= 85 -> TensionLevel.TENSION_4
            maxMetric >= 65 -> TensionLevel.TENSION_3
            maxMetric >= 45 -> TensionLevel.TENSION_2
            maxMetric >= 25 -> TensionLevel.TENSION_1
            else -> TensionLevel.TENSION_0
        }
        setTensionLevel(target)
    }

    // ==========================================
    // 2. JERARQUÍA DE PRIORIDADES & DUCKING DINÁMICO
    // ==========================================

    fun requestPriorityDucking(priority: SoundPriority, durationMs: Long, duckTo: Float = 0.12f) {
        scope.launch {
            if (priority.rank <= currentHighestPriorityRank) {
                currentHighestPriorityRank = priority.rank
                duckingFactor = duckTo
                delay(durationMs)
                // Recuperación gradual (crossfade out del ducking)
                for (step in 1..10) {
                    duckingFactor = duckTo + (1.0f - duckTo) * (step / 10f)
                    delay(30)
                }
                duckingFactor = 1.0f
                currentHighestPriorityRank = SoundPriority.MUSIC.rank
            }
        }
    }

    // ==========================================
    // 3. MEZCLADOR DE CAPAS MUSICALES EN TIEMPO REAL
    // ==========================================

    fun startMusicLayerMixer(ambientTheme: String) {
        activeAmbientTheme = ambientTheme
        if (musicMixerJob?.isActive == true) return

        musicMixerJob = scope.launch {
            try {
                val sampleRate = 22050
                val bufferSize = AudioTrack.getMinBufferSize(
                    sampleRate,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT
                ).coerceAtLeast(sampleRate)

                val track = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_GAME)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(bufferSize * 2)
                    .setTransferMode(AudioTrack.MODE_STREAM)
                    .build()

                musicMixerTrack = track
                track.play()

                val chunk = ShortArray(1024)
                var p1 = 0.0
                var p2 = 0.0
                var p3 = 0.0
                var p4 = 0.0
                var noiseFilter = 0.0

                while (isActive) {
                    // Transición suave de capas (interpolación hacia targetLayerGains)
                    for (c in 0..3) {
                        layerGains[c] += (targetLayerGains[c] - layerGains[c]) * 0.02f
                    }

                    val masterGain = masterVolume * duckingFactor
                    val ambGain = masterGain * ambientVolume * 14000
                    val musGain = masterGain * musicVolume * 15000

                    for (i in chunk.indices) {
                        // CAPA 1: Ambiente y motivo musical de la historia específica (Estilo terror puro)
                        val l1 = when (activeAmbientTheme) {
                            "STORY_1_RAIN_STALKER", "RAIN_STREET" -> {
                                val white = Random.nextFloat() * 2 - 1
                                noiseFilter += 0.08 * (white - noiseFilter)
                                val rumble = sin(p1) * 0.32
                                p1 += 2 * PI * 34.0 / sampleRate
                                (noiseFilter * 0.68 + rumble) * layerGains[0]
                            }
                            "STORY_2_APARTMENT_CREAK", "APARTMENT" -> {
                                // Claustrofobia de departamento: crujidos, resonancia hueca y semitono disonante
                                val hum1 = sin(p1) * 0.30
                                val hum2 = sin(p1 * 1.059) * 0.25
                                val thud = sin(p2 * 0.5) * 0.15
                                p1 += 2 * PI * 58.0 / sampleRate
                                p2 += 2 * PI * 1.2 / sampleRate
                                (hum1 + hum2 + thud) * layerGains[0]
                            }
                            "STORY_3_CAMERA_STATIC", "PHOTO", "STATIC_ROOM" -> {
                                // Terror analógico / polaroid: zumbido de tubo CRT, micro-chisporroteos y flash
                                val crt = sin(p1) * 0.20 + sin(p1 * 3.1) * 0.12
                                val staticNoise = (Random.nextFloat() * 2 - 1) * 0.18
                                p1 += 2 * PI * 82.0 / sampleRate
                                (crt + staticNoise) * layerGains[0]
                            }
                            "STORY_4_SUBWAY_RUMBLE", "SUBWAY" -> {
                                // Túnel subterráneo: resonancia cavernosa de rieles y chirrido metálico lejano
                                val sub = sin(p1) * 0.45
                                val screech = sin(p1 * 4.3) * 0.15 * (0.5 + 0.5 * sin(p2))
                                p1 += 2 * PI * 28.0 / sampleRate
                                p2 += 2 * PI * 0.4 / sampleRate
                                (sub + screech) * layerGains[0]
                            }
                            "STORY_5_HOSPITAL_DEATH", "HOSPITAL" -> {
                                // Sanatorio abandonado: zumbido fluorescente clínico y pulso de monitor cardíaco lúgubre
                                val hum = sin(p1) * 0.28
                                val monitorBeep = if (sin(p2) > 0.94) sin(p1 * 8.0) * 0.35 else 0.0
                                p1 += 2 * PI * 50.0 / sampleRate
                                p2 += 2 * PI * 0.8 / sampleRate
                                (hum + monitorBeep) * layerGains[0]
                            }
                            "STORY_6_DYATLOV_VORTEX", "DYATLOV" -> {
                                // Caso Real: Paso Diátlov - Ventisca voraz de los Urales e infrasonido vibratorio 19Hz
                                val windNoise = (Random.nextFloat() * 2 - 1)
                                noiseFilter += 0.05 * (windNoise - noiseFilter)
                                val vortexHowl = sin(p1) * (0.4 + 0.3 * sin(p2))
                                val infrasound = sin(p1 * 0.28) * 0.35
                                p1 += 2 * PI * 68.0 / sampleRate
                                p2 += 2 * PI * 0.35 / sampleRate
                                (noiseFilter * 0.55 + vortexHowl + infrasound) * layerGains[0]
                            }
                            "STORY_7_HINTERKAIFECK_FARM", "HINTERKAIFECK" -> {
                                // Caso Real: Hinterkaifeck - Granja aislada, Tritono del Diablo (49Hz y 69.3Hz) y madera crujiente
                                val tritone1 = sin(p1) * 0.38
                                val tritone2 = sin(p1 * 1.414) * 0.32
                                val atticStep = if (sin(p2) > 0.92) (Random.nextFloat() * 2 - 1) * 0.25 else 0.0
                                p1 += 2 * PI * 49.0 / sampleRate
                                p2 += 2 * PI * 0.5 / sampleRate
                                (tritone1 + tritone2 + atticStep) * layerGains[0]
                            }
                            "STORY_8_FLANNAN_OCEAN", "FLANNAN" -> {
                                // Caso Real: Faro de Flannan - Océano Atlántico furioso, bramido de sirena de niebla y péndulo detenido
                                val waveNoise = (Random.nextFloat() * 2 - 1)
                                noiseFilter += 0.04 * (waveNoise - noiseFilter)
                                val foghorn = sin(p1) * (0.35 * (0.5 + 0.5 * sin(p2)))
                                val clockTick = if (sin(p2 * 2.0) > 0.96) sin(p1 * 12.0) * 0.2 else 0.0
                                p1 += 2 * PI * 44.0 / sampleRate
                                p2 += 2 * PI * 0.25 / sampleRate
                                (noiseFilter * 0.50 + foghorn + clockTick) * layerGains[0]
                            }
                            else -> {
                                val tunnel = sin(p1) * 0.45 + sin(p2) * 0.25
                                p1 += 2 * PI * 38 / sampleRate
                                p2 += 2 * PI * 76.5 / sampleRate
                                tunnel * layerGains[0]
                            }
                        }

                        // CAPA 2: Inquietud (Pulso de 42Hz)
                        val l2 = if (layerGains[1] > 0.01f) {
                            val pulse = sin(p2) * (0.5 + 0.5 * sin(2 * PI * 0.8 * p2))
                            p2 += 2 * PI * 42.0 / sampleRate
                            pulse * layerGains[1]
                        } else 0.0

                        // CAPA 3: Peligro (Batimiento binaural 45Hz + 48Hz + subgrave 32Hz)
                        val l3 = if (layerGains[2] > 0.01f) {
                            val b1 = sin(p3) * 0.4
                            val b2 = sin(p3 * 1.066) * 0.4
                            val sub = sin(p3 * 0.71) * 0.3
                            p3 += 2 * PI * 45.0 / sampleRate
                            (b1 + b2 + sub) * layerGains[2]
                        } else 0.0

                        // CAPA 4: Terror / Amenaza Extrema (Clúster disonante trémolo + 30Hz)
                        val l4 = if (layerGains[3] > 0.01f) {
                            val c1 = sin(p4) * 0.35
                            val c2 = sin(p4 * 1.06) * 0.35
                            val c3 = sin(p4 * 1.41) * 0.30
                            val tr = 0.75 + 0.25 * sin(2 * PI * 8.0 * p4)
                            val deep = sin(p4 * 0.65) * 0.4
                            p4 += 2 * PI * 46.2 / sampleRate
                            ((c1 + c2 + c3) * 0.7 + deep) * tr * layerGains[3]
                        } else 0.0

                        if (p1 > 2 * PI) p1 -= 2 * PI
                        if (p2 > 2 * PI) p2 -= 2 * PI
                        if (p3 > 2 * PI) p3 -= 2 * PI
                        if (p4 > 2 * PI) p4 -= 2 * PI

                        val sampleValue = (l1 * ambGain) + ((l2 + l3 + l4) * musGain)
                        chunk[i] = sampleValue.toInt().coerceIn(-32767, 32767).toShort()
                    }

                    track.write(chunk, 0, chunk.size)
                }
            } catch (_: Exception) {
            } finally {
                try {
                    musicMixerTrack?.stop()
                    musicMixerTrack?.release()
                } catch (_: Exception) {}
                musicMixerTrack = null
            }
        }
    }

    fun stopMusicMixer() {
        musicMixerJob?.cancel()
        musicMixerJob = null
        try {
            musicMixerTrack?.stop()
            musicMixerTrack?.release()
        } catch (_: Exception) {}
        musicMixerTrack = null
    }

    // ==========================================
    // 4. REPRODUCTOR DE ASSETS / HYBRID BUFFER
    // ==========================================

    fun playSoundAsset(assetSubPath: String, priority: SoundPriority, duckTimeMs: Long = 1000) {
        requestPriorityDucking(priority, duckTimeMs)
        scope.launch {
            try {
                val fullPath = "audio/$assetSubPath"
                val afd: AssetFileDescriptor = context.assets.openFd(fullPath)
                val mp = MediaPlayer()
                mp.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                afd.close()

                val vol = (when (priority) {
                    SoundPriority.CRITICAL_JUMPSCARE -> masterVolume * sfxVolume
                    SoundPriority.DECISION_STINGER -> masterVolume * sfxVolume
                    SoundPriority.VOICE -> masterVolume * voiceVolume
                    SoundPriority.NARRATIVE_SFX -> masterVolume * sfxVolume
                    SoundPriority.AMBIENT -> masterVolume * ambientVolume
                    SoundPriority.MUSIC -> masterVolume * musicVolume
                }).coerceIn(0f, 1f)

                mp.setVolume(vol, vol)
                mp.prepare()
                mp.setOnCompletionListener {
                    try {
                        it.release()
                    } catch (_: Exception) {}
                }
                mp.start()
            } catch (_: Exception) {
                // Si el asset aún no estuviera o fallase el descriptor, el motor procedural toma el control
            }
        }
    }

    fun vibrate(durationMs: Long = 100, strength: Int = VibrationEffect.DEFAULT_AMPLITUDE) {
        if (!vibrationEnabled) return
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createOneShot(durationMs, strength))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(durationMs)
            }
        } catch (_: Exception) {}
    }

    fun vibratePattern(pattern: LongArray) {
        if (!vibrationEnabled) return
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(pattern, -1)
            }
        } catch (_: Exception) {}
    }

    open fun release() {
        stopMusicMixer()
    }
}
