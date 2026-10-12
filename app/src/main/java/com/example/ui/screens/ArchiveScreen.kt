package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.data.AchievementEntity
import com.example.data.DiscoveredClueEntity
import com.example.data.UnlockedEndingEntity
import com.example.story.StoryContent
import com.example.ui.components.CRTScanlinesOverlay
import com.example.ui.components.PixelBox
import com.example.ui.components.StatusMeter
import com.example.ui.theme.AmberGlow
import com.example.ui.theme.BloodRed
import com.example.ui.theme.ColdCyan
import com.example.ui.theme.HorrorBlack
import com.example.ui.theme.HorrorBorder
import com.example.ui.theme.HorrorCardSurface
import com.example.ui.theme.PhosphorGreen

@Composable
fun ArchiveScreen(
    unlockedEndings: List<UnlockedEndingEntity>,
    discoveredClues: List<DiscoveredClueEntity>,
    achievements: List<AchievementEntity>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalEndings = StoryContent.ALL_ENDINGS.size
    val totalAchievements = achievements.size.coerceAtLeast(1)
    val unlockedAchCount = achievements.count { it.isUnlocked }

    // Progress percentage calculation
    val progressPercent = (
        (unlockedEndings.size.toFloat() / totalEndings * 60f) +
        (unlockedAchCount.toFloat() / totalAchievements * 30f) +
        (discoveredClues.size.coerceAtMost(7) / 7f * 10f)
    ).toInt().coerceIn(0, 100)

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
                .alpha(0.45f),
            contentScale = ContentScale.Crop
        )

        // Vignette gradient for readability
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
                IconButton(onClick = onBack, modifier = Modifier.testTag("archive_back_btn")) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = PhosphorGreen
                    )
                }
                Text(
                    text = "ARCHIVO Y PROGRESIÓN",
                    color = PhosphorGreen,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Overall Progress Card
            PixelBox(
                modifier = Modifier.fillMaxWidth(),
                borderColor = ColdCyan,
                backgroundColor = HorrorCardSurface
            ) {
                Column {
                    StatusMeter(
                        label = "PROGRESO TOTAL DEL JUEGO",
                        value = progressPercent,
                        color = ColdCyan
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "• Finales: ${unlockedEndings.size}/$totalEndings",
                            color = Color.LightGray,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = "• Pistas: ${discoveredClues.size}",
                            color = Color.LightGray,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = "• Logros: $unlockedAchCount/${achievements.size}",
                            color = Color.LightGray,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "LOGROS DEL JUGADOR:",
                color = Color.Gray,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(achievements) { ach ->
                    PixelBox(
                        modifier = Modifier.fillMaxWidth(),
                        borderColor = if (ach.isUnlocked) PhosphorGreen else HorrorBorder,
                        backgroundColor = if (ach.isUnlocked) Color(0xFF0F1E16) else HorrorCardSurface
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (ach.isUnlocked) "🏆" else "🔒",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(end = 12.dp)
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = ach.title,
                                    color = if (ach.isUnlocked) PhosphorGreen else Color.LightGray,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = ach.description,
                                    color = if (ach.isUnlocked) Color.White else Color.Gray,
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }
                }
            }
        }

        CRTScanlinesOverlay()
    }
}
