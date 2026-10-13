package com.example.ui.screens

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.UnlockedEndingEntity
import com.example.story.StoryContent
import com.example.ui.components.CRTScanlinesOverlay
import com.example.ui.components.PixelBox
import com.example.ui.theme.AmberGlow
import com.example.ui.theme.ColdCyan
import com.example.ui.theme.HorrorBlack
import com.example.ui.theme.HorrorBorder
import com.example.ui.theme.HorrorCardSurface
import com.example.ui.theme.PhosphorGreen

@Composable
fun EndingsScreen(
    unlockedEndings: List<UnlockedEndingEntity>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalEndings = StoryContent.ALL_ENDINGS.size
    val unlockedCount = unlockedEndings.size

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(HorrorBlack)
    ) {
        // Background: Mujer pálida asomándose de manera terrorífica por una pared
        Image(
            painter = painterResource(id = R.drawable.img_horror_woman_wall),
            contentDescription = "Mujer pálida asomándose en la penumbra de una pared",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.42f),
            contentScale = ContentScale.Crop
        )

        // Vignette gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.50f),
                            HorrorBlack.copy(alpha = 0.82f),
                            HorrorBlack.copy(alpha = 0.95f)
                        )
                    )
                )
        )
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
                IconButton(onClick = onBack, modifier = Modifier.testTag("endings_back_btn")) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = PhosphorGreen
                    )
                }
                Column {
                    Text(
                        text = "FINALES DESBLOQUEADOS",
                        color = PhosphorGreen,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Descubiertos: $unlockedCount de $totalEndings",
                        color = Color.LightGray,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(StoryContent.ALL_ENDINGS) { ending ->
                    val isUnlocked = unlockedEndings.any { it.endingId == ending.id }

                    PixelBox(
                        modifier = Modifier.fillMaxWidth(),
                        borderColor = if (isUnlocked) Color(ending.badge.colorHex) else HorrorBorder,
                        backgroundColor = if (isUnlocked) HorrorCardSurface else Color(0xFF0D0F16)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "FINAL #${ending.number.toString().padStart(2, '0')}",
                                    color = if (isUnlocked) Color(ending.badge.colorHex) else Color.DarkGray,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = if (isUnlocked) ending.badge.label else "BLOQUEADO",
                                    color = if (isUnlocked) Color(ending.badge.colorHex) else Color.DarkGray,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace,
                                    modifier = Modifier
                                        .background(Color.Black, RoundedCornerShape(2.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = if (isUnlocked) ending.title else "??? (Final No Descubierto)",
                                color = if (isUnlocked) Color.White else Color.Gray,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                fontFamily = FontFamily.Monospace
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = if (isUnlocked) ending.description else "Explora diferentes decisiones en '${ending.storyId.subtitle}' para desbloquear este desenlace.",
                                color = if (isUnlocked) Color.LightGray else Color.DarkGray,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace
                            )

                            if (isUnlocked) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                     text = ending.quote,
                                     color = ColdCyan,
                                     fontSize = 10.sp,
                                     fontFamily = FontFamily.Monospace
                                )

                                if (ending.newspaperClippingResId != null) {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color(0xFF14110A), RoundedCornerShape(2.dp))
                                            .border(1.dp, AmberGlow, RoundedCornerShape(2.dp))
                                            .padding(6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(72.dp, 52.dp)
                                                .clip(RoundedCornerShape(2.dp))
                                        ) {
                                            Image(
                                                painter = painterResource(id = ending.newspaperClippingResId),
                                                contentDescription = "Recorte de diario de época",
                                                modifier = Modifier.fillMaxSize(),
                                                contentScale = ContentScale.Crop
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(
                                                text = "📰 RECORTE DE DIARIO REAL",
                                                color = AmberGlow,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = FontFamily.Monospace
                                            )
                                            ending.historicalCaseTitle?.let { hTitle ->
                                                Text(
                                                    text = hTitle,
                                                    color = Color(0xFFD4C5A9),
                                                    fontSize = 9.sp,
                                                    fontFamily = FontFamily.Monospace
                                                )
                                            }
                                        }
                                    }
                                }

                                // Registro Perturbador en Cinta VHS y Explicación del Final
                                val movieEpilogue = ending.fullCinematicEpilogue
                                if (movieEpilogue.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color(0xFF0F0C0A), RoundedCornerShape(2.dp))
                                            .border(1.dp, Color(0xFF661111), RoundedCornerShape(2.dp))
                                            .padding(8.dp)
                                    ) {
                                        Text(
                                            text = "📼 REGISTRO VHS // EVIDENCIA FINAL",
                                            color = Color(0xFFFF4D4D),
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Monospace,
                                            modifier = Modifier.padding(bottom = 6.dp)
                                        )

                                        com.example.ui.components.VHSEndingFrame(
                                            imageResId = ending.vhsImageResId,
                                            timestampText = ending.vhsTimestamp
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))

                                        Text(
                                            text = movieEpilogue,
                                            color = Color(0xFFD6D0C7),
                                            fontSize = 9.sp,
                                            lineHeight = 13.sp,
                                            fontFamily = FontFamily.Monospace
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        CRTScanlinesOverlay()
    }
}
