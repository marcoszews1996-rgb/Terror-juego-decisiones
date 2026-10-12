package com.example.ui.phone

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.R
import com.example.model.CallRecord
import com.example.model.ChatMessage
import com.example.model.NoteItem
import com.example.model.PhoneContact
import com.example.ui.components.PixelBox
import com.example.ui.theme.AmberGlow
import com.example.ui.theme.BloodRed
import com.example.ui.theme.ColdCyan
import com.example.ui.theme.HorrorBlack
import com.example.ui.theme.HorrorBorder
import com.example.ui.theme.HorrorCardSurface
import com.example.ui.theme.HorrorDarkSurface
import com.example.ui.theme.PhosphorGreen

enum class PhoneAppTab(val title: String) {
    CHAT("CHAT//"),
    CONTACTS("CONTACTOS"),
    CALLS("LLAMADAS"),
    PHOTOS("FOTOS"),
    NOTES("NOTAS"),
    MAP("MAPA")
}

@Composable
fun InGamePhoneView(
    messages: List<ChatMessage>,
    contacts: List<PhoneContact>,
    onClosePhone: () -> Unit,
    onPlayVoiceNote: () -> Unit,
    onMakeCall: (PhoneContact) -> Unit,
    onInspectPhotoDetail: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(PhoneAppTab.CHAT) }
    var selectedContactThread by remember { mutableStateOf<PhoneContact?>(null) }
    var inspectingPhoto by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HorrorBlack)
    ) {
        // Status bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF06070A))
                .padding(horizontal = 14.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "03:33 AM",
                color = Color.LightGray,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "SIN SEÑAL ⚠",
                    color = BloodRed,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "13% 🗲",
                    color = AmberGlow,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Phone App Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(HorrorDarkSurface)
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    if (selectedContactThread != null) {
                        selectedContactThread = null
                    } else if (inspectingPhoto) {
                        inspectingPhoto = false
                    } else {
                        onClosePhone()
                    }
                },
                modifier = Modifier.testTag("phone_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Atrás",
                    tint = PhosphorGreen
                )
            }
            Text(
                text = when {
                    selectedContactThread != null -> selectedContactThread?.name ?: "CHAT"
                    inspectingPhoto -> "FOTO RECIBIDA // EXAMINAR"
                    else -> "OS NOCTURNO // ${selectedTab.title}"
                },
                color = PhosphorGreen,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
        }

        // Tab bar (when not inside deep conversation or photo inspect)
        if (selectedContactThread == null && !inspectingPhoto) {
            TabRow(
                selectedTabIndex = selectedTab.ordinal,
                containerColor = HorrorCardSurface,
                contentColor = PhosphorGreen,
                divider = {}
            ) {
                PhoneAppTab.values().forEach { tab ->
                    Tab(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        text = {
                            Text(
                                text = tab.title,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = if (selectedTab == tab) PhosphorGreen else Color.Gray
                            )
                        }
                    )
                }
            }
        }

        // Body content
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(8.dp)
        ) {
            when {
                inspectingPhoto -> {
                    PhotoInspectionView(
                        onInspectClue = onInspectPhotoDetail
                    )
                }
                selectedContactThread != null -> {
                    ChatThreadView(
                        contact = selectedContactThread!!,
                        messages = messages.filter { it.threadId == selectedContactThread!!.id },
                        onPlayVoiceNote = onPlayVoiceNote,
                        onOpenPhoto = { inspectingPhoto = true }
                    )
                }
                selectedTab == PhoneAppTab.CHAT -> {
                    ChatInboxView(
                        contacts = contacts,
                        onSelectContact = { selectedContactThread = it }
                    )
                }
                selectedTab == PhoneAppTab.CONTACTS -> {
                    ContactsListView(
                        contacts = contacts,
                        onCallContact = onMakeCall
                    )
                }
                selectedTab == PhoneAppTab.CALLS -> {
                    CallsHistoryView(
                        onCallUnknown = {
                            contacts.firstOrNull { it.isUnknown }?.let { onMakeCall(it) }
                        }
                    )
                }
                selectedTab == PhoneAppTab.PHOTOS -> {
                    PhotosGalleryView(
                        onSelectPhoto = { inspectingPhoto = true }
                    )
                }
                selectedTab == PhoneAppTab.NOTES -> {
                    NotesListView()
                }
                selectedTab == PhoneAppTab.MAP -> {
                    InteractiveMapPhoneView()
                }
            }
        }
    }
}

@Composable
private fun ChatInboxView(
    contacts: List<PhoneContact>,
    onSelectContact: (PhoneContact) -> Unit
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(contacts) { contact ->
            PixelBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectContact(contact) }
                    .testTag("chat_contact_${contact.id}"),
                borderColor = if (contact.isUnknown) BloodRed else HorrorBorder,
                backgroundColor = if (contact.isUnknown) Color(0xFF1E0E12) else HorrorDarkSurface
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .background(Color(contact.avatarColorHex), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = contact.name.take(1),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = contact.name,
                                color = if (contact.isUnknown) BloodRed else Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = contact.lastSeen,
                                color = Color.Gray,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        Text(
                            text = if (contact.isUnknown) "«¿Ya llegaste?»" else "Toca para abrir la conversación...",
                            color = Color.LightGray,
                            fontSize = 11.sp,
                            maxLines = 1,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    if (contact.unreadCount > 0) {
                        Box(
                            modifier = Modifier
                                .padding(start = 6.dp)
                                .size(18.dp)
                                .background(BloodRed, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = contact.unreadCount.toString(),
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ChatThreadView(
    contact: PhoneContact,
    messages: List<ChatMessage>,
    onPlayVoiceNote: () -> Unit,
    onOpenPhoto: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(messages) { msg ->
                val align = if (msg.isMe) Alignment.End else Alignment.Start
                val bubbleColor = if (msg.isMe) Color(0xFF1E3A2F) else HorrorDarkSurface
                val borderCol = if (msg.isMe) PhosphorGreen else HorrorBorder

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = align
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .background(bubbleColor, RoundedCornerShape(4.dp))
                            .border(1.dp, borderCol, RoundedCornerShape(4.dp))
                            .padding(10.dp)
                    ) {
                        Column {
                            Text(
                                text = msg.sender,
                                color = if (msg.isMe) PhosphorGreen else BloodRed,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = msg.text,
                                color = Color.White,
                                fontSize = 13.sp,
                                fontFamily = FontFamily.Monospace
                            )
                            if (msg.hasPhoto) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(130.dp)
                                        .background(Color.Black)
                                        .clickable { onOpenPhoto() },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.img_photo_bedroom),
                                        contentDescription = "Foto adjunta",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                    Text(
                                        text = "🔍 TOCAR PARA EXAMINAR",
                                        color = ColdCyan,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace,
                                        modifier = Modifier
                                            .background(Color.Black.copy(alpha = 0.7f))
                                            .padding(4.dp)
                                    )
                                }
                            }
                            if (msg.isVoiceNote) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(2.dp))
                                        .border(1.dp, AmberGlow.copy(alpha = 0.5f), RoundedCornerShape(2.dp))
                                        .clickable { onPlayVoiceNote() }
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Reproducir audio",
                                        tint = AmberGlow,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = "MENSAJE DE VOZ — 0:07",
                                            color = AmberGlow,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Monospace
                                        )
                                        Text(
                                            text = "▶ Tocar para reproducir",
                                            color = Color.LightGray,
                                            fontSize = 9.sp,
                                            fontFamily = FontFamily.Monospace
                                        )
                                    }
                                }
                            }
                            Text(
                                text = msg.time,
                                color = Color.Gray,
                                fontSize = 9.sp,
                                fontFamily = FontFamily.Monospace,
                                modifier = Modifier.align(Alignment.End)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ContactsListView(
    contacts: List<PhoneContact>,
    onCallContact: (PhoneContact) -> Unit
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(contacts) { contact ->
            PixelBox(
                modifier = Modifier.fillMaxWidth(),
                borderColor = HorrorBorder
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = contact.name,
                            color = if (contact.isUnknown) BloodRed else Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = contact.number,
                            color = Color.Gray,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    IconButton(
                        onClick = { onCallContact(contact) },
                        modifier = Modifier.testTag("call_btn_${contact.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "Llamar",
                            tint = PhosphorGreen
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CallsHistoryView(
    onCallUnknown: () -> Unit
) {
    val callLogs = remember {
        listOf(
            CallRecord("cl1", "Número Desconocido", "+54 9 11 ????-????", true, "02:15 AM"),
            CallRecord("cl2", "Mateo", "+54 9 11 4821-9032", false, "01:50 AM"),
            CallRecord("cl3", "Lucía", "+54 9 11 5512-4419", false, "01:10 AM"),
            CallRecord("cl4", "Don Horacio", "+54 9 11 3190-7722", true, "Ayer")
        )
    }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(callLogs) { log ->
            PixelBox(
                modifier = Modifier.fillMaxWidth(),
                borderColor = if (log.isMissed) BloodRed else HorrorBorder
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = log.contactName,
                            color = if (log.isMissed) BloodRed else Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = if (log.isMissed) "✖ Llamada perdida" else "✔ Llamada saliente",
                            color = if (log.isMissed) BloodRed else Color.Gray,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Text(
                        text = log.timestamp,
                        color = Color.Gray,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}

@Composable
private fun PhotosGalleryView(
    onSelectPhoto: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        PixelBox(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onSelectPhoto() },
            borderColor = BloodRed
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "IMG_0331_NOCHE.JPG",
                        color = BloodRed,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "03:31 AM",
                        color = Color.Gray,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(2.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_photo_bedroom),
                        contentDescription = "Foto recibida",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "⚠ Archivo sospechoso recibido por Bluetooth. Toca para examinar pistas.",
                    color = AmberGlow,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

@Composable
private fun PhotoInspectionView(
    onInspectClue: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .border(2.dp, BloodRed, RoundedCornerShape(2.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_photo_bedroom),
                contentDescription = "Inspección detallada",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            // Pin on mirror
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(20.dp)
                    .size(24.dp)
                    .background(BloodRed, CircleShape)
                    .clickable { onInspectClue() },
                contentAlignment = Alignment.Center
            ) {
                Text("1", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }

        PixelBox(
            modifier = Modifier.fillMaxWidth(),
            borderColor = ColdCyan
        ) {
            Column {
                Text(
                    text = "ANÁLISIS DE IMAGEN:",
                    color = ColdCyan,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "• Ángulo: Cenital oblicuo (sobre el armario).\n• Iluminación: Infrarrojo nocturno sin flash visible.\n• Anomalía detectada en pin [1]: Reflejo de pupilas en la sombra.",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun NotesListView() {
    val notes = remember {
        listOf(
            NoteItem("n1", "CONSIGNA DE SUPERVIVENCIA", "1. No abrir la puerta después de las 02:00.\n2. Si el teléfono pierde señal de golpe, apagar la pantalla.\n3. Mateo tiene la llave duplicada.", "Hoy"),
            NoteItem("n2", "CÓDIGO DE SALIDA DE INCENDIOS", "El candado de la reja clausurada del 4to piso tiene clave '4091'.", "Semana pasada")
        )
    }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(notes) { note ->
            PixelBox(
                modifier = Modifier.fillMaxWidth(),
                borderColor = AmberGlow
            ) {
                Column {
                    Text(
                        text = note.title,
                        color = AmberGlow,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = note.content,
                        color = Color.LightGray,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}

@Composable
private fun InteractiveMapPhoneView() {
    val locations = remember {
        listOf(
            "TU DEPARTAMENTO (Piso 4)" to "Ubicación actual. Puerta reforzada.",
            "CALLE DE LOS SAUCES" to "Pasaje oscuro sin alumbrado público. Peligroso.",
            "KIOSCO 24 HORAS" to "Punto seguro. Luces de neón y presencia policial.",
            "ESCALERA CLAUSURADA" to "Acceso antiguo al subsuelo -1. Desconocido.",
            "COMISARÍA COMUNAL" to "A 4 cuadras. Refugio oficial."
        )
    }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(locations) { (loc, desc) ->
            PixelBox(
                modifier = Modifier.fillMaxWidth(),
                borderColor = ColdCyan
            ) {
                Column {
                    Text(
                        text = "📍 $loc",
                        color = ColdCyan,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = desc,
                        color = Color.LightGray,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}
