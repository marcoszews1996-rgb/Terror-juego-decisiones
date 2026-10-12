package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DiscoveredClueEntity
import com.example.data.InventoryItemEntity
import com.example.model.Choice
import com.example.ui.theme.AmberGlow
import com.example.ui.theme.BloodRed
import com.example.ui.theme.ColdCyan
import com.example.ui.theme.HorrorBorder
import com.example.ui.theme.HorrorCardSurface
import com.example.ui.theme.HorrorDarkSurface
import com.example.ui.theme.PhosphorGreen
import kotlinx.coroutines.delay

/**
 * Componente modular de interfaz para cuadros de diálogo narrativos con:
 * - Efecto de máquina de escribir procedural
 * - Cursor titilante estilo terminal retro
 * - Salto de tipeo al tocar el cuadro
 * - Selección de opciones con verificación de objetos requeridos del inventario
 */
@Composable
fun NarrativeDialogueBox(
    speaker: String?,
    dialogueText: String,
    choices: List<Choice>,
    inventoryItems: List<InventoryItemEntity>,
    discoveredClues: List<DiscoveredClueEntity> = emptyList(),
    onSelectChoice: (Choice) -> Unit,
    onCharTyped: () -> Unit = {},
    typingSpeedMs: Long = 20L,
    modifier: Modifier = Modifier
) {
    var displayedChars by remember(dialogueText) { mutableIntStateOf(0) }
    var isTypingComplete by remember(dialogueText) { mutableStateOf(false) }

    // Blinking cursor transition
    val cursorTransition = rememberInfiniteTransition(label = "cursor_blink")
    val cursorAlpha by cursorTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(450),
            repeatMode = RepeatMode.Reverse
        ),
        label = "cursor_alpha"
    )

    // Typewriter coroutine effect
    LaunchedEffect(dialogueText) {
        displayedChars = 0
        isTypingComplete = false
        for (i in 1..dialogueText.length) {
            displayedChars = i
            if (i % 2 == 0) {
                onCharTyped()
            }
            delay(typingSpeedMs)
        }
        isTypingComplete = true
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Speaker Badge
        speaker?.let { name ->
            val (badgeBg, badgeBorder, badgeTextCol) = when (name.lowercase()) {
                "narrador" -> Triple(Color(0xFF161E2E), ColdCyan, ColdCyan)
                "protagonista" -> Triple(Color(0xFF10281C), PhosphorGreen, PhosphorGreen)
                "chat//", "teléfono", "conmutador" -> Triple(Color(0xFF281E10), AmberGlow, AmberGlow)
                else -> Triple(Color(0xFF330E14), BloodRed, BloodRed)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(badgeBg, RoundedCornerShape(2.dp))
                    .border(1.dp, badgeBorder, RoundedCornerShape(2.dp))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(6.dp)
                        .height(6.dp)
                        .background(badgeTextCol, RoundedCornerShape(1.dp))
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = name.uppercase(),
                    color = badgeTextCol,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.sp
                )
            }
        }

        // Narrative Box with Typewriter Effect
        PixelBox(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    // Tap to skip typewriter and instantly reveal complete text
                    if (!isTypingComplete) {
                        displayedChars = dialogueText.length
                        isTypingComplete = true
                    }
                }
                .testTag("dialogue_narrative_box"),
            borderColor = HorrorBorder,
            backgroundColor = HorrorDarkSurface
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "REGISTRO // TRANSMISIÓN",
                        color = Color.DarkGray,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace
                    )
                    if (!isTypingComplete) {
                        Text(
                            text = "[TOCA PARA SALTAR]",
                            color = ColdCyan.copy(alpha = 0.6f),
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                val visibleText = dialogueText.take(displayedChars)
                Text(
                    text = buildString {
                        append(visibleText)
                        if (!isTypingComplete) {
                            append(" ")
                        }
                    },
                    color = Color(0xFFE2E8F0),
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.testTag("typewriter_dialogue_text")
                )

                // Blinking cursor
                if (!isTypingComplete) {
                    Text(
                        text = "█",
                        color = PhosphorGreen,
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.alpha(cursorAlpha)
                    )
                }
            }
        }

        // Choices Section (animated appearance upon typewriter completion)
        AnimatedVisibility(
            visible = isTypingComplete && choices.isNotEmpty(),
            enter = fadeIn(tween(250)) + slideInVertically(initialOffsetY = { 20 })
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "DECISIONES DISPONIBLES:",
                        color = PhosphorGreen,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 1.sp
                    )
                }

                choices.forEachIndexed { index, choice ->
                    val hasRequiredClue = choice.requiredClueId == null ||
                            discoveredClues.any { it.clueId == choice.requiredClueId }
                    val hasRequiredItem = choice.requiredItemId == null ||
                            inventoryItems.any { it.itemId == choice.requiredItemId }
                    val isAvailable = hasRequiredClue && hasRequiredItem

                    val badge = when {
                        !hasRequiredClue -> "REQUIERE PISTA"
                        !hasRequiredItem -> "REQUIERE OBJETO"
                        choice.requiredClueId != null -> "PISTA REUNIDA"
                        else -> null
                    }

                    RetroButton(
                        text = "${index + 1}. ${choice.text}",
                        onClick = {
                            if (isAvailable) {
                                onSelectChoice(choice)
                            }
                        },
                        enabled = isAvailable,
                        isPrimary = index == 0 && isAvailable,
                        isDanger = choice.effect.dangerDelta > 15 || choice.effect.fearDelta > 20,
                        testTag = "choice_option_${choice.id}",
                        badgeText = badge
                    )
                }
            }
        }
    }
}
