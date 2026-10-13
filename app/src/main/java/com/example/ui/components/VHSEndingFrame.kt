package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.PhosphorGreen
import kotlin.random.Random

/**
 * Cuadro visual estilo cinta de video analógica VHS para el final de cada historia.
 * Incluye OSD (On-Screen Display: PLAY, REC, timestamp), líneas de escaneo CRT y distorsión de tracking.
 */
@Composable
fun VHSEndingFrame(
    imageResId: Int,
    timestampText: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(190.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(Color.Black)
            .border(1.dp, Color(0xFF661111), RoundedCornerShape(3.dp))
    ) {
        // Imagen perturbadora de fondo
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Registro analógico VHS de la escena",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Degradado de sombra para lectura del OSD
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.55f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.70f)
                        )
                    )
                )
        )

        // Líneas de escaneo CRT y ruido de cinta analógica
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // Líneas de escaneo horizontales
            val lineSpacing = 3.5f
            var y = 0f
            while (y < height) {
                drawLine(
                    color = Color.Black.copy(alpha = 0.40f),
                    start = Offset(0f, y),
                    end = Offset(width, y),
                    strokeWidth = 1.2f
                )
                y += lineSpacing
            }

            // Barra inferior de desgarro de tracking de cabezal VHS
            val trackingY = height - 20f
            drawRect(
                color = Color.White.copy(alpha = 0.22f),
                topLeft = Offset(0f, trackingY),
                size = Size(width, 9f)
            )

            // Puntos de estática y grano magnético
            for (i in 0 until 50) {
                val nx = Random.nextFloat() * width
                val ny = Random.nextFloat() * height
                drawRect(
                    color = Color.White.copy(alpha = 0.30f),
                    topLeft = Offset(nx, ny),
                    size = Size(2f, 1.5f)
                )
            }
        }

        // OSD Superior (PLAY ▶ SP / REC)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp)
                .align(Alignment.TopStart),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "PLAY ▶ SP",
                color = PhosphorGreen,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text = "REC ● TAPE_ARCHIVE",
                color = Color(0xFFFF3333),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
        }

        // OSD Inferior (Timestamp y clasificación)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp)
                .align(Alignment.BottomStart),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "AUTO-TRACKING",
                color = Color.LightGray,
                fontSize = 9.sp,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text = timestampText,
                color = Color(0xFFF0F0F0),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}
