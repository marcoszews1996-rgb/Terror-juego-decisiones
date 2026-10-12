package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AmberGlow
import com.example.ui.theme.BloodRed
import com.example.ui.theme.ColdCyan
import com.example.ui.theme.HorrorBlack
import com.example.ui.theme.HorrorBorder
import com.example.ui.theme.HorrorCardSurface
import com.example.ui.theme.HorrorDarkSurface
import com.example.ui.theme.PhosphorGreen
import kotlinx.coroutines.delay

@Composable
fun CRTScanlinesOverlay(
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    if (!enabled) return

    val infiniteTransition = rememberInfiniteTransition(label = "crt")
    val flicker by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(
            animation = tween(120, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flicker"
    )

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .alpha(flicker)
    ) {
        val scanlineSpacing = 4.dp.toPx()
        val lineCount = (size.height / scanlineSpacing).toInt()
        val scanlineColor = Color.Black.copy(alpha = 0.18f)

        for (i in 0..lineCount) {
            val y = i * scanlineSpacing
            drawLine(
                color = scanlineColor,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 1.5f
            )
        }
    }
}

@Composable
fun HeartbeatVignette(
    fearLevel: Int,
    dangerLevel: Int,
    modifier: Modifier = Modifier
) {
    val totalThreat = ((fearLevel * 0.6f + dangerLevel * 0.4f) / 100f).coerceIn(0f, 1f)
    if (totalThreat <= 0.15f) return

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.15f * totalThreat,
        targetValue = 0.55f * totalThreat,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = (1000 - (totalThreat * 500)).toInt().coerceAtLeast(300),
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "heartbeat"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(Color.Transparent, BloodRed.copy(alpha = pulseAlpha)),
                    radius = 850f
                )
            )
    )
}

@Composable
fun PixelBox(
    modifier: Modifier = Modifier,
    borderColor: Color = HorrorBorder,
    backgroundColor: Color = HorrorCardSurface,
    borderWidth: Dp = 2.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(2.dp))
            .border(borderWidth, borderColor, RoundedCornerShape(2.dp))
            .padding(12.dp)
    ) {
        content()
    }
}

@Composable
fun RetroButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = "retro_button",
    enabled: Boolean = true,
    isDanger: Boolean = false,
    isPrimary: Boolean = false,
    badgeText: String? = null
) {
    val borderColor = when {
        !enabled -> Color.DarkGray
        isDanger -> BloodRed
        isPrimary -> PhosphorGreen
        else -> HorrorBorder
    }

    val textColor = when {
        !enabled -> Color.Gray
        isDanger -> BloodRed
        isPrimary -> PhosphorGreen
        else -> Color.White
    }

    val backgroundColor = when {
        isDanger -> HorrorDarkSurface
        isPrimary -> Color(0xFF0F2618)
        else -> HorrorDarkSurface
    }

    Box(
        modifier = modifier
            .testTag(testTag)
            .fillMaxWidth()
            .height(52.dp)
            .background(backgroundColor, RoundedCornerShape(2.dp))
            .border(2.dp, borderColor, RoundedCornerShape(2.dp))
            .clickable(
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = textColor),
                onClick = onClick
            )
            .padding(horizontal = 14.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                color = textColor,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                ),
                maxLines = 2,
                modifier = Modifier.weight(1f)
            )
            if (badgeText != null) {
                Text(
                    text = badgeText,
                    color = ColdCyan,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(Color(0xFF00363A), RoundedCornerShape(2.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
fun TypewriterText(
    fullText: String,
    modifier: Modifier = Modifier,
    speedMs: Long = 20,
    onCharTyped: (() -> Unit)? = null,
    onComplete: (() -> Unit)? = null
) {
    var revealedCount by remember(fullText) { mutableIntStateOf(0) }
    var isDone by remember(fullText) { mutableStateOf(false) }

    LaunchedEffect(fullText) {
        revealedCount = 0
        isDone = false
        val length = fullText.length
        while (revealedCount < length) {
            revealedCount++
            if (revealedCount % 4 == 0) {
                onCharTyped?.invoke()
            }
            delay(speedMs)
        }
        isDone = true
        onComplete?.invoke()
    }

    Text(
        text = fullText.take(revealedCount) + (if (!isDone) "▮" else ""),
        color = Color(0xFFE2E8F0),
        style = MaterialTheme.typography.bodyLarge.copy(
            lineHeight = 23.sp,
            letterSpacing = 0.5.sp
        ),
        modifier = modifier.clickable {
            // Tap to reveal instantly
            revealedCount = fullText.length
            isDone = true
            onComplete?.invoke()
        }
    )
}

@Composable
fun StatusMeter(
    label: String,
    value: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                color = color,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$value%",
                color = color,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        }
        LinearProgressIndicator(
            progress = { (value / 100f).coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .padding(top = 2.dp),
            color = color,
            trackColor = HorrorDarkSurface,
            strokeCap = StrokeCap.Square
        )
    }
}
