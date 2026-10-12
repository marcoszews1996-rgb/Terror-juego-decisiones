package com.example.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.GameSaveEntity
import com.example.model.StoryId
import com.example.ui.Screen
import com.example.ui.components.CRTScanlinesOverlay
import com.example.ui.components.RetroButton
import com.example.ui.theme.BloodRed
import com.example.ui.theme.ColdCyan
import com.example.ui.theme.HorrorBlack
import com.example.ui.theme.HorrorBorder
import com.example.ui.theme.PhosphorGreen

@Composable
fun MainMenuScreen(
    activeSave: GameSaveEntity?,
    onContinueGame: () -> Unit,
    onNewGame: () -> Unit,
    onOpenStories: () -> Unit,
    onOpenEndings: () -> Unit,
    onOpenInventory: () -> Unit,
    onOpenArchive: () -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "title_glitch")
    val titleGlow by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(HorrorBlack)
    ) {
        // Background image: Mujer pálida asomándose de manera terrorífica por una pared
        Image(
            painter = painterResource(id = R.drawable.img_horror_woman_wall),
            contentDescription = "Mujer pálida asomándose en la penumbra de una pared",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.65f),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.35f),
                            HorrorBlack.copy(alpha = 0.70f),
                            HorrorBlack.copy(alpha = 0.92f)
                        )
                    )
                )
        )

        // Menu content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Subtitle tag
            Text(
                text = "TERROR PSICOLÓGICO INTERACTIVO",
                color = ColdCyan,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Main Title
            Text(
                text = "NO CONTESTES",
                color = BloodRed,
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 34.sp,
                    letterSpacing = 4.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .alpha(titleGlow)
                    .testTag("main_title_text")
            )

            Text(
                text = "«Las decisiones tienen consecuencias. El silencio también.»",
                color = Color.LightGray,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 6.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Continue Button (dynamic based on activeSave)
            RetroButton(
                text = if (activeSave != null) "▶ CONTINUAR (${activeSave.chapterTitle.take(18)}...)" else "▶ CONTINUAR",
                onClick = onContinueGame,
                enabled = activeSave != null,
                isPrimary = activeSave != null,
                testTag = "btn_continue",
                badgeText = if (activeSave != null) "GUARDADO" else null
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Nueva Partida
            RetroButton(
                text = "▶ NUEVA PARTIDA",
                onClick = onNewGame,
                isPrimary = activeSave == null,
                testTag = "btn_new_game"
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Historias
            RetroButton(
                text = "▶ SELECCIÓN DE HISTORIAS",
                onClick = onOpenStories,
                testTag = "btn_stories"
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Finales desbloqueados
            RetroButton(
                text = "▶ FINALES DESBLOQUEADOS",
                onClick = onOpenEndings,
                testTag = "btn_endings"
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Inventario & Pistas
            RetroButton(
                text = "▶ PISTAS E INVENTARIO",
                onClick = onOpenInventory,
                testTag = "btn_clues"
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Archivo & Logros
            RetroButton(
                text = "▶ ARCHIVO Y LOGROS",
                onClick = onOpenArchive,
                testTag = "btn_archive"
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Ajustes
            RetroButton(
                text = "▶ AJUSTES DE AUDIO Y FX",
                onClick = onOpenSettings,
                testTag = "btn_settings"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Version info footer
            Text(
                text = "v1.0.0 // NO CONTESTES STUDIO",
                color = Color.DarkGray,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace
            )
        }

        // CRT Scanlines
        CRTScanlinesOverlay()
    }
}
