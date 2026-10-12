package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlin.random.Random

/**
 * Superposición en pantalla completa para Jumpscares con duración de 2 segundos.
 * Muestra cualquiera de las 3 imágenes aterradoras seleccionadas de forma aleatoria,
 * acompañada de vibración, grito ensordecedor y severa interferencia estática analógica.
 */
@Composable
fun FullscreenJumpscareOverlay(
    isVisible: Boolean,
    drawableResId: Int?,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible && drawableResId != null,
        enter = fadeIn(animationSpec = tween(50, easing = LinearEasing)),
        exit = fadeOut(animationSpec = tween(150, easing = FastOutSlowInEasing)),
        modifier = modifier.zIndex(999f)
    ) {
        if (drawableResId == null) return@AnimatedVisibility

        val infiniteTransition = rememberInfiniteTransition(label = "JumpscareGlitch")
        val glitchPhase by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(80, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "glitchPhase"
        )

        val pulseAlpha by infiniteTransition.animateFloat(
            initialValue = 0.85f,
            targetValue = 1.0f,
            animationSpec = infiniteRepeatable(
                animation = tween(60, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "pulseAlpha"
        )

        val jitterX = ((Random.nextFloat() - 0.5f) * 16 * glitchPhase).toInt()
        val jitterY = ((Random.nextFloat() - 0.5f) * 16 * glitchPhase).toInt()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .testTag("fullscreen_jumpscare_overlay")
        ) {
            // Chromatic aberration ghost layer (Cyan / Red shift)
            Image(
                painter = painterResource(id = drawableResId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .offset { IntOffset(jitterX + 14, jitterY - 6) },
                contentScale = ContentScale.Crop,
                alpha = 0.35f,
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                    Color.Red,
                    BlendMode.Screen
                )
            )

            // Main Jumpscare Image with chromatic shake
            Image(
                painter = painterResource(id = drawableResId),
                contentDescription = "Jumpscare Aterrador",
                modifier = Modifier
                    .fillMaxSize()
                    .offset { IntOffset(jitterX, jitterY) },
                contentScale = ContentScale.Crop,
                alpha = pulseAlpha
            )

            // Blood red flashing vignette
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0x66990000),
                                Color(0xDD330000)
                            )
                        )
                    )
            )

            // Heavy Static Noise & TV Glitch Bands
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height

                // Dense TV static noise snow (400 grains)
                val noiseGrains = 400
                for (i in 0 until noiseGrains) {
                    val nx = Random.nextFloat() * width
                    val ny = Random.nextFloat() * height
                    val nWidth = Random.nextFloat() * 6f + 2f
                    val nHeight = Random.nextFloat() * 3f + 1f
                    val alpha = Random.nextFloat() * 0.65f + 0.15f
                    val isReddish = Random.nextFloat() < 0.20f
                    val color = if (isReddish) Color.Red.copy(alpha = alpha) else Color.White.copy(alpha = alpha)
                    drawRect(
                        color = color,
                        topLeft = Offset(nx, ny),
                        size = Size(nWidth, nHeight)
                    )
                }

                // Horizontal tracking error glitch bars
                val barCount = 7
                for (b in 0 until barCount) {
                    val barY = (Random.nextFloat() * height)
                    val barHeight = Random.nextFloat() * 32f + 8f
                    val barAlpha = Random.nextFloat() * 0.40f + 0.15f
                    drawRect(
                        color = if (b % 2 == 0) Color.White.copy(alpha = barAlpha) else Color.Red.copy(alpha = barAlpha),
                        topLeft = Offset(0f, barY),
                        size = Size(width, barHeight),
                        blendMode = BlendMode.Screen
                    )
                }

                // CRT Horizontal scanlines
                val lineSpacing = 3.5f
                var y = 0f
                while (y < height) {
                    drawLine(
                        color = Color.Black.copy(alpha = 0.42f),
                        start = Offset(0f, y),
                        end = Offset(width, y),
                        strokeWidth = 1.4f
                    )
                    y += lineSpacing
                }
            }
        }
    }
}
