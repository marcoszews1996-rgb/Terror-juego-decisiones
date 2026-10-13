package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.data.DiscoveredClueEntity
import com.example.data.InventoryItemEntity
import com.example.model.Choice
import com.example.model.Ending
import com.example.model.GameVariables
import com.example.model.PhoneContact
import com.example.model.PhoneNotification
import com.example.model.Scene
import com.example.model.StoryId
import com.example.story.StoryContent
import com.example.ui.components.CRTScanlinesOverlay
import com.example.ui.components.HeartbeatVignette
import com.example.ui.components.NarrativeDialogueBox
import com.example.ui.components.PixelArtSceneViewer
import com.example.ui.components.PixelBox
import com.example.ui.components.RetroButton
import com.example.ui.components.StatusMeter
import com.example.ui.components.TypewriterText
import com.example.ui.phone.InGamePhoneView
import com.example.ui.theme.AmberGlow
import com.example.ui.theme.BloodRed
import com.example.ui.theme.ColdCyan
import com.example.ui.theme.HorrorBlack
import com.example.ui.theme.HorrorBorder
import com.example.ui.theme.HorrorCardSurface
import com.example.ui.theme.HorrorDarkSurface
import com.example.ui.theme.PhosphorGreen

@Composable
fun GameScreen(
    scene: Scene?,
    storyId: StoryId,
    variables: GameVariables,
    isGlitching: Boolean,
    isPhoneOpen: Boolean,
    activeNotification: PhoneNotification?,
    activeEnding: Ending?,
    inventoryItems: List<InventoryItemEntity>,
    discoveredClues: List<DiscoveredClueEntity>,
    onSelectChoice: (Choice) -> Unit,
    onTogglePhone: () -> Unit,
    onBackToMenu: () -> Unit,
    onTypewriterTick: () -> Unit,
    onDismissNotification: () -> Unit,
    onDismissEnding: () -> Unit,
    onRestartStory: () -> Unit,
    onPlayVoiceNote: () -> Unit,
    onMakeCall: (PhoneContact) -> Unit,
    onInspectPhoto: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(HorrorBlack)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            // Header bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onBackToMenu,
                    modifier = Modifier.testTag("game_back_to_menu")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Menú Principal",
                        tint = Color.LightGray
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = storyId.subtitle,
                        color = ColdCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = scene?.chapterTitle ?: "Capítulo",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }

                // Phone quick toggle in header
                BadgedBox(
                    badge = {
                        if (activeNotification != null) {
                            Badge(
                                containerColor = BloodRed,
                                contentColor = Color.White
                            ) { Text("!") }
                        }
                    }
                ) {
                    IconButton(
                        onClick = onTogglePhone,
                        modifier = Modifier.testTag("btn_toggle_phone")
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhoneAndroid,
                            contentDescription = "Abrir teléfono",
                            tint = if (activeNotification != null) BloodRed else PhosphorGreen
                        )
                    }
                }
            }

            // Meters row: Miedo & Peligro
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatusMeter(
                    label = "MIEDO",
                    value = variables.fear,
                    color = if (variables.fear > 60) BloodRed else AmberGlow,
                    modifier = Modifier.weight(1f)
                )
                StatusMeter(
                    label = "PELIGRO",
                    value = variables.danger,
                    color = if (variables.danger > 60) BloodRed else PhosphorGreen,
                    modifier = Modifier.weight(1f)
                )
            }

            // Top visual scene (pixel art rendering)
            if (scene != null) {
                PixelArtSceneViewer(
                    sceneType = scene.visualType,
                    fearLevel = variables.fear,
                    isGlitching = isGlitching,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }

            // Scrollable Narrative & Choices container
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (scene != null) {
                    NarrativeDialogueBox(
                        speaker = scene.speaker,
                        dialogueText = scene.text,
                        choices = scene.choices,
                        inventoryItems = inventoryItems,
                        discoveredClues = discoveredClues,
                        onSelectChoice = onSelectChoice,
                        onCharTyped = onTypewriterTick
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))
            }
        }

        // Notification banner (top floating toast)
        AnimatedVisibility(
            visible = activeNotification != null,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut(),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 56.dp, start = 14.dp, end = 14.dp)
        ) {
            activeNotification?.let { notif ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFF2B0E14))
                        .border(2.dp, BloodRed, RoundedCornerShape(4.dp))
                        .clickable { onTogglePhone() }
                        .padding(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "📩 ${notif.title} // ${notif.sender}",
                                color = BloodRed,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = notif.text,
                                color = Color.White,
                                fontSize = 12.sp,
                                maxLines = 1,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        Text(
                            text = "[ ABRIR ]",
                            color = PhosphorGreen,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }

        // In-game Phone Overlay
        AnimatedVisibility(
            visible = isPhoneOpen,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            InGamePhoneView(
                messages = StoryContent.INITIAL_CHAT_MESSAGES,
                contacts = StoryContent.ALL_CONTACTS,
                onClosePhone = onTogglePhone,
                onPlayVoiceNote = onPlayVoiceNote,
                onMakeCall = onMakeCall,
                onInspectPhotoDetail = onInspectPhoto
            )
        }

        // Ending Unlocked Dialog
        if (activeEnding != null) {
            Dialog(
                onDismissRequest = onDismissEnding,
                properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)
            ) {
                PixelBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("dialog_ending_unlocked"),
                    borderColor = Color(activeEnding.badge.colorHex),
                    backgroundColor = HorrorDarkSurface
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = "⚠ FINAL DESBLOQUEADO ⚠",
                            color = Color(activeEnding.badge.colorHex),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )

                        Text(
                            text = activeEnding.badge.label,
                            color = Color(activeEnding.badge.colorHex),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier
                                .background(Color.Black, RoundedCornerShape(2.dp))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        )

                        Text(
                            text = activeEnding.title,
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = activeEnding.description,
                            color = Color.LightGray,
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = activeEnding.quote,
                            color = ColdCyan,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )

                        // Recorte de diario real y expediente histórico
                        if (activeEnding.newspaperClippingResId != null) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "📰 RECORTE DE DIARIO DE LA ÉPOCA (CASO REAL)",
                                color = AmberGlow,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                                textAlign = TextAlign.Center
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .border(1.dp, AmberGlow, RoundedCornerShape(3.dp))
                            ) {
                                Image(
                                    painter = painterResource(id = activeEnding.newspaperClippingResId),
                                    contentDescription = activeEnding.historicalCaseTitle ?: "Recorte de diario de época",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            activeEnding.historicalCaseTitle?.let { hTitle ->
                                Text(
                                    text = hTitle,
                                    color = AmberGlow,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace,
                                    textAlign = TextAlign.Center
                                )
                            }
                            activeEnding.historicalFactSnippet?.let { snippet ->
                                Text(
                                    text = snippet,
                                    color = Color(0xFFD8C7A5),
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier
                                        .background(Color(0xFF14110A), RoundedCornerShape(2.dp))
                                        .border(1.dp, Color(0xFF332B1A), RoundedCornerShape(2.dp))
                                        .padding(8.dp)
                                )
                            }
                        }

                        // Imagen Perturbadora Estilo VHS y Explicación del Final
                        val cinematicText = activeEnding.fullCinematicEpilogue
                        if (cinematicText.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFF0C0908), RoundedCornerShape(4.dp))
                                    .border(1.dp, BloodRed.copy(alpha = 0.85f), RoundedCornerShape(4.dp))
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = "📼 REGISTRO VHS // EVIDENCIA FINAL",
                                    color = BloodRed,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace,
                                    modifier = Modifier.padding(bottom = 6.dp)
                                )

                                // Imagen perturbadora acorde a la historia con marco analógico VHS
                                com.example.ui.components.VHSEndingFrame(
                                    imageResId = activeEnding.vhsImageResId,
                                    timestampText = activeEnding.vhsTimestamp
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = cinematicText,
                                    color = Color(0xFFDDD7CF),
                                    fontSize = 11.sp,
                                    lineHeight = 16.sp,
                                    fontFamily = FontFamily.Monospace,
                                    textAlign = TextAlign.Start
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        RetroButton(
                            text = "VOLVER A JUGAR ESTA HISTORIA",
                            onClick = onRestartStory,
                            isPrimary = true,
                            testTag = "btn_restart_story"
                        )

                        RetroButton(
                            text = "MENÚ PRINCIPAL",
                            onClick = onDismissEnding,
                            testTag = "btn_ending_menu"
                        )
                    }
                }
            }
        }

        // Heartbeat Vignette based on Fear / Danger
        HeartbeatVignette(fearLevel = variables.fear, dangerLevel = variables.danger)

        // CRT scanlines
        CRTScanlinesOverlay()
    }
}
