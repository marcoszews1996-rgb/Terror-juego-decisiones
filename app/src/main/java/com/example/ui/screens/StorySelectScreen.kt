package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.data.UnlockedEndingEntity
import com.example.model.StoryId
import com.example.ui.components.CRTScanlinesOverlay
import com.example.ui.components.PixelBox
import com.example.ui.components.RetroButton
import com.example.ui.theme.AmberGlow
import com.example.ui.theme.BloodRed
import com.example.ui.theme.ColdCyan
import com.example.ui.theme.HorrorBlack
import com.example.ui.theme.HorrorBorder
import com.example.ui.theme.HorrorCardSurface
import com.example.ui.theme.HorrorDarkSurface
import com.example.ui.theme.PhosphorGreen

data class StoryCardData(
    val storyId: StoryId,
    val synopsis: String,
    val endingsInfo: String
)

@Composable
fun StorySelectScreen(
    unlockedEndings: List<UnlockedEndingEntity>,
    isStoryUnlocked: (StoryId) -> Boolean,
    isWatchingAd: Boolean,
    adSecondsLeft: Int,
    adStoryTarget: StoryId?,
    onSelectStory: (StoryId) -> Unit,
    onWatchAdToUnlock: (StoryId) -> Unit,
    onCancelAd: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val mainStories = listOf(
        StoryCardData(
            StoryId.ULTIMA_CONEXION,
            "Vuelves caminando a casa por calles oscuras bajo la niebla a las 02:18 AM. Un mensaje desconocido llega: «¿Ya llegaste?». La paranoia, la persecución y los secretos de tu entorno comienzan.",
            "5 Finales (Bueno, Malo, Trágico, Secreto, Verdadero)"
        ),
        StoryCardData(
            StoryId.EL_DEPARTAMENTO,
            "Tres golpes secos en tu puerta: TOC TOC TOC. Miras por la mirilla y el pasillo está desierto. Luego un mensaje: «No abras». Descubres huellas frescas dentro de tu propio pasillo.",
            "5 Finales (Bueno, Malo, Trágico, Secreto, Verdadero)"
        ),
        StoryCardData(
            StoryId.LA_FOTO,
            "Te despiertas a las 03:33 AM con una foto en tu celular. Es una foto tuya durmiendo tomada desde lo alto del armario hace 3 minutos... mientras estabas completamente solo.",
            "5 Finales (Bueno, Malo, Trágico, Secreto, Verdadero)"
        ),
        StoryCardData(
            StoryId.EL_ULTIMO_TREN,
            "00:45 AM en la estación subterránea. Abordas el último convoy. Las puertas se sellan y el tablero anuncia paradas en estaciones que no figuran en ningún mapa del mundo.",
            "5 Finales (Bueno, Malo, Trágico, Secreto, Verdadero)"
        ),
        StoryCardData(
            StoryId.EL_TURNO_NOCHE,
            "03:00 AM en el sanatorio provincial en plena tormenta. En las cámaras ves una camilla rodar sola. El intercomunicador suena con un pedido de auxilio de la habitación 404, clausurada hace diez años.",
            "5 Finales (Bueno, Malo, Trágico, Secreto, Verdadero)"
        )
    )

    val specialStories = listOf(
        StoryCardData(
            StoryId.EL_TRANSMISOR,
            "CASO REAL (1959): La tragedia del Paso Diátlov en los Montes Urales. Nueve excursionistas experimentados murieron de forma inexplicable. La tienda fue rajada desde adentro a -30°C y huyeron descalzos. Hallaron traumatismos masivos sin heridas externas.",
            "5 Finales Reales + Recorte de Diario Histórico Oficial"
        ),
        StoryCardData(
            StoryId.LA_CABANA,
            "CASO REAL (1922): La masacre de la granja bávara de Hinterkaifeck. Huellas en la nieve fresca que entraban al predio pero ninguna salía. Ruidos de pisadas en el ático. El homicida vivió días en la casa con los cuerpos alimentando al ganado.",
            "5 Finales Reales + Recorte de Diario Histórico Oficial"
        ),
        StoryCardData(
            StoryId.EL_ESPEJO,
            "CASO REAL (1900): El enigma del faro de las Islas Flannan (Eilean Mòr). Tres fareros veteranos desaparecieron sin dejar rastro de una roca desierta. La mesa servida, una silla volcada, el reloj detenido y un diario con una tormenta inexistente.",
            "5 Finales Reales + Recorte de Diario Histórico Oficial"
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(HorrorBlack)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack, modifier = Modifier.testTag("stories_back_btn")) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = PhosphorGreen
                    )
                }
                Column {
                    Text(
                        text = "EXPEDIENTES Y PESADILLAS",
                        color = PhosphorGreen,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "8 Historias // 40 Desenlaces posibles",
                        color = Color.LightGray,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Main Stories Header
                item {
                    Text(
                        text = "HISTORIAS PRINCIPALES (1 — 5):",
                        color = ColdCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
                    )
                }

                items(mainStories) { data ->
                    StoryCardItem(
                        data = data,
                        isUnlocked = true,
                        unlockedEndings = unlockedEndings,
                        onPlay = { onSelectStory(data.storyId) },
                        onWatchAd = {}
                    )
                }

                // Special Stories Header
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "HISTORIAS REALES (6 — 8) [DESBLOQUEABLES CON ANUNCIO]:",
                        color = AmberGlow,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(top = 8.dp, bottom = 2.dp)
                    )
                    Text(
                        text = "Expedientes reales no resueltos. Mira la emisión de 5 segundos para desbloquear y jugar el caso con su recorte de diario histórico.",
                        color = Color.LightGray,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                items(specialStories) { data ->
                    val unlocked = isStoryUnlocked(data.storyId)
                    StoryCardItem(
                        data = data,
                        isUnlocked = unlocked,
                        unlockedEndings = unlockedEndings,
                        onPlay = { onSelectStory(data.storyId) },
                        onWatchAd = { onWatchAdToUnlock(data.storyId) }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }

        // Rewarded Ad Simulation Dialog
        if (isWatchingAd) {
            Dialog(
                onDismissRequest = onCancelAd,
                properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
            ) {
                PixelBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("rewarded_ad_dialog"),
                    borderColor = AmberGlow,
                    backgroundColor = Color(0xFF0C0E14)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = "📺 TRANSMISIÓN PATROCINADA",
                            color = AmberGlow,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )

                        Text(
                            text = "DESBLOQUEANDO: ${adStoryTarget?.subtitle ?: "HISTORIA ESPECIAL"}",
                            color = ColdCyan,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            textAlign = TextAlign.Center
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(Color.Black, RoundedCornerShape(2.dp))
                                .border(1.dp, Color.DarkGray, RoundedCornerShape(2.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CircularProgressIndicator(
                                    color = PhosphorGreen,
                                    strokeWidth = 3.dp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = if (adSecondsLeft > 0) "Desbloqueando en $adSecondsLeft segundos..." else "¡RECOMPENSA CONCEDIDA!",
                                    color = if (adSecondsLeft > 0) Color.White else PhosphorGreen,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }

                        Text(
                            text = "Al finalizar esta breve emisión publicitaria, el episodio quedará guardado y desbloqueado permanentemente.",
                            color = Color.LightGray,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        CRTScanlinesOverlay()
    }
}

@Composable
private fun StoryCardItem(
    data: StoryCardData,
    isUnlocked: Boolean,
    unlockedEndings: List<UnlockedEndingEntity>,
    onPlay: () -> Unit,
    onWatchAd: () -> Unit
) {
    val unlockedCount = unlockedEndings.count { it.storyId == data.storyId.code }

    PixelBox(
        modifier = Modifier.fillMaxWidth(),
        borderColor = if (isUnlocked) HorrorBorder else AmberGlow,
        backgroundColor = if (isUnlocked) HorrorCardSurface else Color(0xFF131008)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = data.storyId.title,
                    color = if (data.storyId.isSpecialUnlockable) AmberGlow else ColdCyan,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = if (isUnlocked) "Finales: $unlockedCount/5" else "🔒 BLOQUEADA",
                    color = if (!isUnlocked) AmberGlow else if (unlockedCount > 0) PhosphorGreen else Color.Gray,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }

            Text(
                text = data.storyId.subtitle,
                color = BloodRed,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 2.dp)
            )

            // Visual preview of the story setting
            val previewDrawable = when (data.storyId) {
                StoryId.ULTIMA_CONEXION -> R.drawable.img_street_night
                StoryId.EL_DEPARTAMENTO -> R.drawable.img_apt_door
                StoryId.LA_FOTO -> R.drawable.img_photo_bedroom
                StoryId.EL_TRANSMISOR -> R.drawable.img_newspaper_dyatlov
                StoryId.LA_CABANA -> R.drawable.img_newspaper_hinterkaifeck
                StoryId.EL_ESPEJO -> R.drawable.img_newspaper_flannan
                else -> null
            }
            if (previewDrawable != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .border(1.dp, HorrorBorder, RoundedCornerShape(3.dp))
                ) {
                    Image(
                        painter = painterResource(id = previewDrawable),
                        contentDescription = data.storyId.subtitle,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.45f)
                                    )
                                )
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = data.synopsis,
                color = Color.LightGray,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = data.endingsInfo,
                color = Color.Gray,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (data.storyId.isSpecialUnlockable) {
                RetroButton(
                    text = "🎬 VER ANUNCIO Y JUGAR (5s)",
                    onClick = onWatchAd,
                    isDanger = false,
                    isPrimary = true,
                    testTag = "btn_ad_${data.storyId.code}",
                    badgeText = "CADA PARTIDA"
                )
            } else {
                RetroButton(
                    text = "▶ JUGAR EPISODIO",
                    onClick = onPlay,
                    isPrimary = true,
                    testTag = "btn_play_${data.storyId.code}"
                )
            }
        }
    }
}
