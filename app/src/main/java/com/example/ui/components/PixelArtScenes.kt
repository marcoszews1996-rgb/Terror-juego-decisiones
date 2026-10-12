package com.example.ui.components

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.VisualSceneType
import com.example.ui.theme.AmberGlow
import com.example.ui.theme.BloodRed
import com.example.ui.theme.ColdCyan
import com.example.ui.theme.HorrorBlack
import com.example.ui.theme.HorrorBorder
import com.example.ui.theme.HorrorDarkSurface
import com.example.ui.theme.PhosphorGreen
import kotlin.random.Random

@Composable
fun PixelArtSceneViewer(
    sceneType: VisualSceneType,
    fearLevel: Int,
    isGlitching: Boolean,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "scene_anim")

    // Rain drop animation phase
    val rainPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rain"
    )

    // Streetlamp flicker
    val lampFlicker by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(150, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "lamp"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(230.dp)
            .clip(RoundedCornerShape(4.dp))
            .border(2.dp, HorrorBorder, RoundedCornerShape(4.dp))
            .background(HorrorBlack)
    ) {
        when (sceneType) {
            VisualSceneType.STREET_NIGHT, VisualSceneType.ALLEYWAY -> {
                Image(
                    painter = painterResource(id = R.drawable.img_street_night),
                    contentDescription = "Calle oscura con lluvia",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(lampFlicker),
                    contentScale = ContentScale.Crop
                )
                // Rain streaks overlay
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val dropCount = 45
                    for (i in 0 until dropCount) {
                        val seed = (i * 997)
                        val startX = (seed % size.width.toInt()).toFloat()
                        val speedMult = 1f + (seed % 3) * 0.4f
                        val yProg = ((rainPhase * speedMult + (seed % 100) / 100f) % 1f)
                        val startY = yProg * (size.height + 60f) - 30f
                        drawLine(
                            color = Color(0x77A0C4E2),
                            start = Offset(startX, startY),
                            end = Offset(startX - 6f, startY + 22f),
                            strokeWidth = 1.5f
                        )
                    }
                }
            }
            VisualSceneType.APARTMENT_DOOR, VisualSceneType.CORRIDOR -> {
                Image(
                    painter = painterResource(id = R.drawable.img_apt_door),
                    contentDescription = "Puerta del departamento",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Red emergency hallway light pulse
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, BloodRed.copy(alpha = 0.15f * lampFlicker))
                            )
                        )
                )
            }
            VisualSceneType.BEDROOM_DARK, VisualSceneType.BALCONY -> {
                Image(
                    painter = painterResource(id = R.drawable.img_photo_bedroom),
                    contentDescription = "Habitación a oscuras a las 03:33",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            VisualSceneType.CONVENIENCE_STORE -> {
                // Neon convenience store interior canvas
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // White fluorescent glow ceiling
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFFE8EEF5), Color(0xFF1E2638)),
                            startY = 0f,
                            endY = size.height
                        )
                    )
                    // Store shelves pixel silhouettes
                    drawRect(
                        color = Color(0xFF0F1420),
                        topLeft = Offset(20f, size.height * 0.4f),
                        size = Size(size.width * 0.35f, size.height * 0.6f)
                    )
                    drawRect(
                        color = Color(0xFF151C2C),
                        topLeft = Offset(size.width * 0.65f, size.height * 0.35f),
                        size = Size(size.width * 0.35f, size.height * 0.65f)
                    )
                    // Fluorescent tube lights
                    drawRect(
                        color = Color.White.copy(alpha = 0.9f * lampFlicker),
                        topLeft = Offset(size.width * 0.2f, 15f),
                        size = Size(size.width * 0.6f, 8f)
                    )
                    // Neon open sign in window
                    drawCircle(
                        color = PhosphorGreen.copy(alpha = 0.8f),
                        radius = 18f,
                        center = Offset(size.width * 0.85f, 40f)
                    )
                }
            }
            VisualSceneType.PEEPHOLE -> {
                // Peephole circular lens
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawRect(color = Color.Black)
                    val radius = (size.height * 0.42f)
                    val center = Offset(size.width / 2f, size.height / 2f)

                    // Peephole interior view
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF2B1015), BloodRed.copy(alpha = 0.35f), Color.Black),
                            center = center,
                            radius = radius
                        ),
                        radius = radius,
                        center = center
                    )
                    // Fish-eye lens distortion ring
                    drawCircle(
                        color = Color(0xFF90A4AE).copy(alpha = 0.5f),
                        radius = radius,
                        center = center,
                        style = Stroke(width = 6f)
                    )
                    // Distant shadow silhouette standing in hallway
                    drawRect(
                        color = Color.Black,
                        topLeft = Offset(center.x - 14f, center.y - 30f),
                        size = Size(28f, 65f)
                    )
                    drawCircle(
                        color = Color.Black,
                        radius = 10f,
                        center = Offset(center.x, center.y - 40f)
                    )
                }
            }
            VisualSceneType.SUBWAY_PLATFORM -> {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Dark subway platform with yellow hazard line and tunnel opening
                    drawRect(color = Color(0xFF0D0F14))
                    // Tunnel arch
                    drawArc(
                        brush = Brush.radialGradient(listOf(Color(0xFF1B0507), Color.Black)),
                        startAngle = 180f,
                        sweepAngle = 180f,
                        useCenter = true,
                        topLeft = Offset(size.width * 0.15f, -size.height * 0.2f),
                        size = Size(size.width * 0.7f, size.height * 1.2f)
                    )
                    // Yellow platform hazard stripe
                    drawRect(
                        color = AmberGlow.copy(alpha = 0.85f),
                        topLeft = Offset(0f, size.height * 0.78f),
                        size = Size(size.width, 10f)
                    )
                    // Flickering emergency tube light
                    drawRect(
                        color = Color.White.copy(alpha = 0.8f * lampFlicker),
                        topLeft = Offset(size.width * 0.3f, 15f),
                        size = Size(size.width * 0.4f, 6f)
                    )
                }
            }
            VisualSceneType.HOSPITAL_CORRIDOR -> {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // White sterilized cold corridor with green emergency signs
                    drawRect(
                        brush = Brush.verticalGradient(listOf(Color(0xFF0F151B), Color(0xFF070A0E)))
                    )
                    // Floor tiles perspective lines
                    drawLine(
                        color = Color(0xFF1E293B),
                        start = Offset(size.width * 0.5f, size.height * 0.3f),
                        end = Offset(0f, size.height),
                        strokeWidth = 2f
                    )
                    drawLine(
                        color = Color(0xFF1E293B),
                        start = Offset(size.width * 0.5f, size.height * 0.3f),
                        end = Offset(size.width, size.height),
                        strokeWidth = 2f
                    )
                    // Room 404 door silhouette
                    drawRect(
                        color = Color(0xFF140709),
                        topLeft = Offset(size.width * 0.42f, size.height * 0.25f),
                        size = Size(size.width * 0.16f, size.height * 0.55f)
                    )
                    // Emergency red exit sign
                    drawRect(
                        color = BloodRed.copy(alpha = 0.9f * lampFlicker),
                        topLeft = Offset(size.width * 0.44f, size.height * 0.18f),
                        size = Size(size.width * 0.12f, 8f)
                    )
                }
            }
            VisualSceneType.RADIO_HIGHWAY -> {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Dark highway in fog with car dashboard glow
                    drawRect(color = Color(0xFF060709))
                    // Headlight beams in heavy fog
                    drawArc(
                        brush = Brush.radialGradient(listOf(Color(0x99FFF9C4), Color.Transparent)),
                        startAngle = 200f,
                        sweepAngle = 140f,
                        useCenter = true,
                        topLeft = Offset(size.width * 0.1f, size.height * 0.2f),
                        size = Size(size.width * 0.8f, size.height * 0.8f)
                    )
                    // Dashboard radio glow
                    drawRect(
                        color = PhosphorGreen.copy(alpha = 0.75f * lampFlicker),
                        topLeft = Offset(size.width * 0.35f, size.height * 0.85f),
                        size = Size(size.width * 0.3f, 18f)
                    )
                }
            }
            VisualSceneType.FOREST_CABIN -> {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Cabin wood interior with dark rain outside window
                    drawRect(color = Color(0xFF120C0A))
                    // Window overlooking black lake
                    drawRect(
                        brush = Brush.verticalGradient(listOf(Color(0xFF03070E), Color(0xFF091422))),
                        topLeft = Offset(size.width * 0.2f, size.height * 0.15f),
                        size = Size(size.width * 0.6f, size.height * 0.55f)
                    )
                    // Window frame cross
                    drawLine(color = Color(0xFF231610), start = Offset(size.width * 0.5f, size.height * 0.15f), end = Offset(size.width * 0.5f, size.height * 0.7f), strokeWidth = 5f)
                    drawLine(color = Color(0xFF231610), start = Offset(size.width * 0.2f, size.height * 0.42f), end = Offset(size.width * 0.8f, size.height * 0.42f), strokeWidth = 5f)
                    // Kerosene flame warm glow
                    drawCircle(
                        brush = Brush.radialGradient(listOf(AmberGlow.copy(alpha = 0.6f * lampFlicker), Color.Transparent)),
                        radius = 80f,
                        center = Offset(size.width * 0.8f, size.height * 0.75f)
                    )
                }
            }
            VisualSceneType.BASEMENT_MIRROR -> {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Dusty basement with ornate antique mirror frame
                    drawRect(color = Color(0xFF07080B))
                    val mirrorWidth = size.width * 0.45f
                    val mirrorHeight = size.height * 0.75f
                    val mirrorLeft = (size.width - mirrorWidth) / 2f
                    val mirrorTop = (size.height - mirrorHeight) / 2f
                    // Mirror reflection glow
                    drawRect(
                        brush = Brush.radialGradient(listOf(ColdCyan.copy(alpha = 0.35f), Color(0xFF0C141E))),
                        topLeft = Offset(mirrorLeft, mirrorTop),
                        size = Size(mirrorWidth, mirrorHeight)
                    )
                    // Ornate frame
                    drawRect(
                        color = AmberGlow.copy(alpha = 0.7f),
                        topLeft = Offset(mirrorLeft, mirrorTop),
                        size = Size(mirrorWidth, mirrorHeight),
                        style = Stroke(width = 6f)
                    )
                    // Shadow silhouette inside mirror
                    drawCircle(
                        color = Color.Black.copy(alpha = 0.9f),
                        radius = 16f,
                        center = Offset(size.width * 0.5f, mirrorTop + 50f)
                    )
                    drawRect(
                        color = Color.Black.copy(alpha = 0.9f),
                        topLeft = Offset(size.width * 0.44f, mirrorTop + 65f),
                        size = Size(size.width * 0.12f, 60f)
                    )
                }
            }
            VisualSceneType.ENDING_SCREEN -> {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawRect(
                        brush = Brush.radialGradient(
                            colors = listOf(BloodRed.copy(alpha = 0.3f), Color.Black),
                            radius = size.width * 0.7f
                        )
                    )
                }
            }
        }

        // Momentary glitch chromatic slice
        if (isGlitching) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val sliceY = Random.nextFloat() * size.height
                val sliceHeight = Random.nextFloat() * 40f + 10f
                drawRect(
                    color = BloodRed.copy(alpha = 0.65f),
                    topLeft = Offset(0f, sliceY),
                    size = Size(size.width, sliceHeight)
                )
                drawRect(
                    color = ColdCyan.copy(alpha = 0.5f),
                    topLeft = Offset(15f, (sliceY + 15f) % size.height),
                    size = Size(size.width, 6f)
                )
            }
        }

        // Scene metadata watermark (location stamp)
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
                .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(2.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Text(
                text = "REC // 02:40 AM",
                color = if (isGlitching) BloodRed else ColdCyan,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
