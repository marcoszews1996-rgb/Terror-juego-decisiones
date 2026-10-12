package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.GameSettingsEntity
import com.example.ui.components.CRTScanlinesOverlay
import com.example.ui.components.PixelBox
import com.example.ui.components.RetroButton
import com.example.ui.theme.AmberGlow
import com.example.ui.theme.BloodRed
import com.example.ui.theme.ColdCyan
import com.example.ui.theme.HorrorBlack
import com.example.ui.theme.HorrorBorder
import com.example.ui.theme.PhosphorGreen

@Composable
fun SettingsScreen(
    currentSettings: GameSettingsEntity,
    onSaveSettings: (Float, Float, Float, Float, Float, Boolean, Boolean, Boolean) -> Unit,
    onResetAllData: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var masterVol by remember(currentSettings) { mutableFloatStateOf(currentSettings.masterVolume) }
    var musicVol by remember(currentSettings) { mutableFloatStateOf(currentSettings.musicVolume) }
    var sfxVol by remember(currentSettings) { mutableFloatStateOf(currentSettings.sfxVolume) }
    var ambientVol by remember(currentSettings) { mutableFloatStateOf(currentSettings.ambientVolume) }
    var voiceVol by remember(currentSettings) { mutableFloatStateOf(currentSettings.voiceVolume) }
    var vibration by remember(currentSettings) { mutableStateOf(currentSettings.vibrationEnabled) }
    var crtScanlines by remember(currentSettings) { mutableStateOf(currentSettings.crtScanlinesEnabled) }
    var headphoneMode by remember(currentSettings) { mutableStateOf(currentSettings.headphoneModeEnabled) }
    var showResetDialog by remember { mutableStateOf(false) }

    val triggerSave = {
        onSaveSettings(masterVol, musicVol, sfxVol, ambientVol, voiceVol, vibration, crtScanlines, headphoneMode)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(HorrorBlack)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack, modifier = Modifier.testTag("settings_back_btn")) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = PhosphorGreen
                    )
                }
                Text(
                    text = "AJUSTES DEL SISTEMA",
                    color = PhosphorGreen,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Audio Box
            PixelBox(modifier = Modifier.fillMaxWidth(), borderColor = HorrorBorder) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "AUDIO Y SONIDO CINEMÁTICO",
                        color = ColdCyan,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace
                    )

                    // Master
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Volumen General", color = Color.White, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                            Text("${(masterVol * 100).toInt()}%", color = ColdCyan, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                        }
                        Slider(
                            value = masterVol,
                            onValueChange = {
                                masterVol = it
                                triggerSave()
                            },
                            colors = SliderDefaults.colors(thumbColor = ColdCyan, activeTrackColor = ColdCyan)
                        )
                    }

                    // Music
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Música y Drones de Tensión", color = Color.White, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                            Text("${(musicVol * 100).toInt()}%", color = ColdCyan, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                        }
                        Slider(
                            value = musicVol,
                            onValueChange = {
                                musicVol = it
                                triggerSave()
                            },
                            colors = SliderDefaults.colors(thumbColor = PhosphorGreen, activeTrackColor = PhosphorGreen)
                        )
                    }

                    // SFX (Efectos)
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Efectos de Sonido (SFX)", color = Color.White, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                            Text("${(sfxVol * 100).toInt()}%", color = ColdCyan, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                        }
                        Slider(
                            value = sfxVol,
                            onValueChange = {
                                sfxVol = it
                                triggerSave()
                            },
                            colors = SliderDefaults.colors(thumbColor = BloodRed, activeTrackColor = BloodRed)
                        )
                    }

                    // Ambient (Ambiente)
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Sonido Ambiente (Lluvia, Viento, Crujidos)", color = Color.White, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                            Text("${(ambientVol * 100).toInt()}%", color = AmberGlow, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                        }
                        Slider(
                            value = ambientVol,
                            onValueChange = {
                                ambientVol = it
                                triggerSave()
                            },
                            colors = SliderDefaults.colors(thumbColor = AmberGlow, activeTrackColor = AmberGlow)
                        )
                    }

                    // Voice (Voces y Mensajes de Audio)
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Voces y Mensajes de Audio", color = Color.White, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                            Text("${(voiceVol * 100).toInt()}%", color = ColdCyan, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                        }
                        Slider(
                            value = voiceVol,
                            onValueChange = {
                                voiceVol = it
                                triggerSave()
                            },
                            colors = SliderDefaults.colors(thumbColor = ColdCyan, activeTrackColor = ColdCyan)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Haptics & Spatial Audio
            PixelBox(modifier = Modifier.fillMaxWidth(), borderColor = HorrorBorder) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "RESPUESTA HÁPTICA Y EXPERIENCIA INMERSIVA",
                        color = ColdCyan,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace
                    )

                    // Modo Auriculares
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Modo Auriculares", color = Color.White, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                            Text("Realce espacial de frecuencias y paneo binaural para máxima inmersión", color = Color.Gray, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                        }
                        Switch(
                            checked = headphoneMode,
                            onCheckedChange = {
                                headphoneMode = it
                                triggerSave()
                            },
                            colors = SwitchDefaults.colors(checkedThumbColor = PhosphorGreen)
                        )
                    }

                    // Vibración del Teléfono
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Vibración del Teléfono", color = Color.White, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                            Text("Vibra ante mensajes, golpes y momentos de peligro", color = Color.Gray, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                        }
                        Switch(
                            checked = vibration,
                            onCheckedChange = {
                                vibration = it
                                triggerSave()
                            },
                            colors = SwitchDefaults.colors(checkedThumbColor = AmberGlow)
                        )
                    }

                    // Filtro CRT
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Filtro Retro CRT Scanlines", color = Color.White, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                            Text("Efecto de monitor antiguo de vigilancia", color = Color.Gray, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                        }
                        Switch(
                            checked = crtScanlines,
                            onCheckedChange = {
                                crtScanlines = it
                                triggerSave()
                            },
                            colors = SwitchDefaults.colors(checkedThumbColor = ColdCyan)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Reset Data Button
            RetroButton(
                text = "⚠ REINICIAR PROGRESO DEL JUEGO",
                onClick = { showResetDialog = true },
                isDanger = true,
                testTag = "btn_reset_data"
            )
        }

        if (showResetDialog) {
            AlertDialog(
                onDismissRequest = { showResetDialog = false },
                title = {
                    Text(
                        text = "CONFIRMAR REINICIO",
                        color = BloodRed,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        text = "¿Estás seguro de que deseas borrar todas las pistas, finales y partidas guardadas? Esta acción no se puede deshacer.",
                        color = Color.LightGray,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showResetDialog = false
                            onResetAllData()
                        }
                    ) {
                        Text("BORRAR TODO", color = BloodRed, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showResetDialog = false }) {
                        Text("CANCELAR", color = Color.Gray, fontFamily = FontFamily.Monospace)
                    }
                },
                containerColor = Color(0xFF140709)
            )
        }

        CRTScanlinesOverlay()
    }
}
