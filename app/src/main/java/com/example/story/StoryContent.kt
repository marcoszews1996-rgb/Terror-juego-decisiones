package com.example.story

import com.example.R
import com.example.model.AmbientSoundType
import com.example.model.ChatMessage
import com.example.model.Choice
import com.example.model.ChoiceEffect
import com.example.model.Clue
import com.example.model.Ending
import com.example.model.EndingBadge
import com.example.model.InventoryItem
import com.example.model.PhoneCall
import com.example.model.PhoneContact
import com.example.model.PhoneNotification
import com.example.model.Scene
import com.example.model.StoryId
import com.example.model.VisualSceneType

object StoryContent {

    // All 40 Endings across 8 stories
    val ALL_ENDINGS: List<Ending> = listOf(
        // HISTORIA 1
        Ending("end_1_good", StoryId.ULTIMA_CONEXION, 1, "SANTUARIO ILUMINADO", EndingBadge.BUENO, "Te refugiaste a tiempo en la tienda 24h junto a una patrulla policial. El acosador desapareció en la niebla nocturna.", "«La luz no detuvo al monstruo, pero le impidió acercarse.»"),
        Ending("end_1_bad", StoryId.ULTIMA_CONEXION, 2, "EL CALLEJÓN SIN SALIDA", EndingBadge.MALO, "Decidiste cortar camino por el pasaje oscuro ignorando las advertencias. Nadie te vio entrar, y nadie te vio salir.", "«Un mensaje no contestado fue la última señal de vida.»"),
        Ending("end_1_tragic", StoryId.ULTIMA_CONEXION, 3, "ÚLTIMO VISTO", EndingBadge.TRAGICO, "Lograste llegar a la puerta de tu edificio jadeando de alivio, sin advertir que el cerrojo ya estaba destrabado desde adentro.", "«Tu última conexión marcó: hace 1 minuto.»"),
        Ending("end_1_secret", StoryId.ULTIMA_CONEXION, 4, "LA LLAMADA ESPEJO", EndingBadge.SECRETO, "Decidiste devolver la llamada al número desconocido... y el teléfono en tu propio bolsillo empezó a contestarse a sí mismo.", "«¿Quién contesta cuando llamas a tu propia sombra?»"),
        Ending("end_1_true", StoryId.ULTIMA_CONEXION, 5, "CÍRCULO CERCANO", EndingBadge.VERDADERO, "Reuniste todas las pistas del mensaje y confrontaste a Mateo. Supiste la escalofriante verdad de quién te vigilaba desde el principio.", "«Los peores fantasmas no necesitan esconderse en la oscuridad.»"),

        // HISTORIA 2
        Ending("end_2_good", StoryId.EL_DEPARTAMENTO, 6, "AMANECER EN EL BALCÓN", EndingBadge.BUENO, "Bloqueaste la puerta del balcón y pediste auxilio a gritos hasta que la policía rompió la cerradura exterior y aseguró el lugar.", "«El sol de las 6 AM nunca se había sentido tan cálido.»"),
        Ending("end_2_bad", StoryId.EL_DEPARTAMENTO, 7, "DETRÁS DE TI", EndingBadge.MALO, "Abriste la puerta creyendo que el peligro venía de afuera. El intruso ya estaba parado en tu propio pasillo interno.", "«Tres golpes eran una advertencia, no una petición para entrar.»"),
        Ending("end_2_tragic", StoryId.EL_DEPARTAMENTO, 8, "EL ROSTRO EN LA MIRILLA", EndingBadge.TRAGICO, "Te acercaste a la mirilla por tercera vez. Un ojo inyectado en sangre pegado al lente exterior te miró fijamente.", "«No mires a la oscuridad si no quieres que la oscuridad te devuelva la mirada.»"),
        Ending("end_2_secret", StoryId.EL_DEPARTAMENTO, 9, "EL PISO INEXISTENTE", EndingBadge.SECRETO, "Huiste despavorido escaleras abajo y saliste a un rellano idéntico marcado con el piso -1, un nivel que el plano nunca tuvo.", "«Las paredes de este edificio recuerdan cosas que nunca existieron.»"),
        Ending("end_2_true", StoryId.EL_DEPARTAMENTO, 10, "EL INQUILINO ANTERIOR", EndingBadge.VERDADERO, "Encontraste los recortes en el conducto de ventilación: el antiguo dueño nunca se mudó. Vivió entre tus muros todo este tiempo.", "«Nunca estuviste solo en este departamento.»"),

        // HISTORIA 3
        Ending("end_3_good", StoryId.LA_FOTO, 11, "LA FALSA ALARMA", EndingBadge.BUENO, "Tomaste tus llaves, saliste a toda prisa a la calle y entregaste el teléfono intacto a la fiscalía como prueba pericial.", "«A veces la cobardía es la forma más sabia de supervivencia.»"),
        Ending("end_3_bad", StoryId.LA_FOTO, 12, "SONRÍE A LA CÁMARA", EndingBadge.MALO, "Intentaste investigar el armario a oscuras. Un flash cegador estalló a centímetros de tus ojos mientras dos manos te jalaban hacia el fondo.", "«Tu última fotografía no la tomaste tú.»"),
        Ending("end_3_tragic", StoryId.LA_FOTO, 13, "SIN RECUERDOS", EndingBadge.TRAGICO, "Revisaste los metadatos y videos de la cámara: fuiste tú mismo sonámbulo, dejando marcas en las paredes y hablando en lenguas extrañas.", "«El peor monstruo vive dormido en tu propia mente.»"),
        Ending("end_3_secret", StoryId.LA_FOTO, 14, "LA DIMENSIÓN DE LA LENTE", EndingBadge.SECRETO, "Hiciste zoom al máximo en la pupila del reflejo en el espejo. El rostro en la pantalla parpadeó y respiró.", "«La imagen digital comenzó a sangrar a través de los píxeles.»"),
        Ending("end_3_true", StoryId.LA_FOTO, 15, "EL INTRUSO DEL ÁTICO", EndingBadge.VERDADERO, "Subiste al cielorraso con la linterna y descubriste el nido clandestino con tus fotos de los últimos seis meses.", "«El techo crujía por las noches no por el viento, sino por sus pasos.»")
    ) + Story4Content.ENDINGS + Story5Content.ENDINGS + Story6SpecialContent.ENDINGS + Story7SpecialContent.ENDINGS + Story8SpecialContent.ENDINGS

    // Items
    val ITEM_KEYS = InventoryItem("item_keys", "Llaves del Edificio", "Llavero metálico con la llave dorada del portal y la llave del departamento.", "ic_key")
    val ITEM_FLASHLIGHT = InventoryItem("item_flashlight", "Linterna con Poca Batería", "Linterna táctica pequeña. La luz parpadea cuando se agita.", "ic_flashlight")
    val ITEM_POCKET_KNIFE = InventoryItem("item_knife", "Navaja de Bolsillo", "Hoja afilada multiuso plegable.", "ic_knife")
    val ITEM_POLAROID = InventoryItem("item_polaroid", "Foto Polaroid Borrosa", "Encontrada bajo la alfombra de la entrada. Muestra la ventana de tu cuarto.", "ic_photo")
    val ITEM_RECEIPT = InventoryItem("item_receipt", "Recibo Arrugado", "Ticket de compra de hace 15 minutos en el kiosco de la esquina.", "ic_receipt")
    val ITEM_NOTE_VENT = InventoryItem("item_note_vent", "Manifiesto del Inquilino", "Hojas manchadas arrancadas de una libreta con fechas y horarios de tu rutina.", "ic_note")
    val ITEM_SECURITY_KEY = InventoryItem("item_sec_key", "Llave de Salida de Incendio", "Llave antigua oxidada colgada detrás del cuadro del pasillo.", "ic_lock")

    // Clues
    val CLUE_UNKNOWN_NUMBER = Clue("clue_number", StoryId.ULTIMA_CONEXION, "Número Oculto", "El número que te escribió no tiene foto de perfil ni nombre registrado, pero su código de área es idéntico al de tu barrio.", "Revisado en CHAT//", "ic_search")
    val CLUE_STALKER_VISION = Clue("clue_stalker_vision", StoryId.ULTIMA_CONEXION, "Descripción Exacta", "El remitente sabe el color de tu campera y que estás doblando hacia la calle de los sauces.", "Mensaje entrante", "ic_eye")
    val CLUE_MATEO_ALIBI = Clue("clue_mateo_alibi", StoryId.ULTIMA_CONEXION, "Coartada de Mateo", "Mateo dijo que estaba durmiendo en su casa, pero se escucha el zumbido del semáforo averiado de tu cuadra de fondo en su mensaje de voz.", "Llamada sospechosa", "ic_call")
    val CLUE_WET_FOOTPRINT = Clue("clue_wet_footprint", StoryId.EL_DEPARTAMENTO, "Huella Mojada en Parquet", "Una marca de zapato húmeda en el piso entre el baño y tu habitación. Tú no entraste con zapatos.", "Inspección de pasillo", "ic_footprint")
    val CLUE_DOOR_SCRATCHES = Clue("clue_door_scratches", StoryId.EL_DEPARTAMENTO, "Marcas en el Cerrojo", "Rayones metálicos profundos y recientes alrededor de la cerradura principal.", "Inspección de puerta", "ic_door")
    val CLUE_PHOTO_EXIF = Clue("clue_photo_exif", StoryId.LA_FOTO, "Metadatos 03:31 AM", "La foto fue tomada a las 03:31 AM con la cámara frontal sincronizada por Bluetooth a otro dispositivo.", "Análisis de galería", "ic_info")
    val CLUE_ATTIC_LATCH = Clue("clue_attic_latch", StoryId.LA_FOTO, "Trampilla Entreabierta", "La tapa de acceso al cielorraso dentro del armario tiene polvo removido recientemente.", "Inspección de armario", "ic_box")

    // Contacts
    val CONTACT_UNKNOWN = PhoneContact("c_unknown", "Número Desconocido", "+54 9 11 ????-????", 0xFFE63946, "En línea", isUnknown = true, unreadCount = 1)
    val CONTACT_MATEO = PhoneContact("c_mateo", "Mateo", "+54 9 11 4821-9032", 0xFF457B9D, "hace 25 min", unreadCount = 0)
    val CONTACT_LUCIA = PhoneContact("c_lucia", "Lucía", "+54 9 11 5512-4419", 0xFF2A9D8F, "hace 1 hora", unreadCount = 0)
    val CONTACT_HORACIO = PhoneContact("c_horacio", "Don Horacio (Conserje)", "+54 9 11 3190-7722", 0xFFE76F51, "ayer", unreadCount = 0)
    val CONTACT_EMERGENCY = PhoneContact("c_911", "911 Emergencias", "911", 0xFFE63946, "Disponible", unreadCount = 0)

    val ALL_CONTACTS = listOf(CONTACT_UNKNOWN, CONTACT_MATEO, CONTACT_LUCIA, CONTACT_HORACIO, CONTACT_EMERGENCY)

    // Initial Chat messages
    val INITIAL_CHAT_MESSAGES = listOf(
        ChatMessage("m1", "c_unknown", "Número Desconocido", false, "¿Ya llegaste?", "02:18", false),
        ChatMessage("m2", "c_mateo", "Mateo", false, "Avisá cuando estés adentro che, está horrible la noche.", "02:04", false),
        ChatMessage("m3", "c_mateo", "Tú", true, "Sí, voy cruzando la avenida. Diez minutos.", "02:05", false),
        ChatMessage("m4", "c_lucia", "Lucía", false, "Nos vemos mañana en el café, descansá!", "01:30", false)
    )

    // ==========================================
    // SCENES: STORY 1 — ÚLTIMA CONEXIÓN
    // ==========================================
    private val SCENES_STORY_1 = listOf(
        Scene(
            id = "s1_intro",
            storyId = StoryId.ULTIMA_CONEXION,
            chapterIndex = 1,
            chapterTitle = "Capítulo 1: La Niebla Nocturna",
            speaker = "Narrador",
            text = "Son las 02:18 de la madrugada. La lluvia cae constante sobre el asfalto gastado. Caminas apresurado con el cuello de la campera levantado. La calle está en completo silencio, salvo por el goteo del agua en los toldos oxidados.",
            visualType = VisualSceneType.STREET_NIGHT,
            ambientSound = AmbientSoundType.RAIN_STREET,
            choices = listOf(
                Choice("c1_walk", "Apurar el paso hacia tu edificio", "s1_phone_buzz", effect = ChoiceEffect(fearDelta = 5, dangerDelta = 5)),
                Choice("c1_check", "Mirar discretamente hacia atrás", "s1_look_back", effect = ChoiceEffect(fearDelta = 10, curiosityDelta = 15, soundTrigger = "UNEASY_PIANO")),
                Choice("c1_music", "Ponerte los auriculares para no pensar", "s1_headphones", effect = ChoiceEffect(fearDelta = -5, dangerDelta = 15, trustDelta = 10))
            )
        ),
        Scene(
            id = "s1_look_back",
            storyId = StoryId.ULTIMA_CONEXION,
            chapterIndex = 1,
            chapterTitle = "Capítulo 1: Sombras en la Niebla",
            speaker = "Protagonista",
            text = "Te detienes bajo una farola intermitente y giras la cabeza. A cincuenta metros, detrás de una columna de ladrillos, parece haber una silueta inmóvil. Parpadeas. El reflejo de la lluvia dificulta ver con claridad.",
            visualType = VisualSceneType.STREET_NIGHT,
            ambientSound = AmbientSoundType.RAIN_STREET,
            choices = listOf(
                Choice("c_lb_continue", "Seguir caminando fingiendo que no viste nada", "s1_phone_buzz", effect = ChoiceEffect(fearDelta = 15, curiosityDelta = 5)),
                Choice("c_lb_shout", "¿Quién anda ahí?", "s1_shout_street", effect = ChoiceEffect(fearDelta = 20, dangerDelta = 20, soundTrigger = "STINGER"))
            )
        ),
        Scene(
            id = "s1_shout_street",
            storyId = StoryId.ULTIMA_CONEXION,
            chapterIndex = 1,
            chapterTitle = "Capítulo 1: Ecos en la Noche",
            speaker = "Narrador",
            text = "Tu propia voz suena quebrada y frágil entre los edificios vacíos. La silueta no se mueve... pero de repente la luz de la farola de esa esquina se apaga de golpe con un chasquido eléctrico.",
            visualType = VisualSceneType.STREET_NIGHT,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c_shout_run", "Dar media vuelta y avanzar sin mirar", "s1_phone_buzz", effect = ChoiceEffect(fearDelta = 25, dangerDelta = 15, soundTrigger = "HEARTBEAT"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s1_headphones",
            storyId = StoryId.ULTIMA_CONEXION,
            chapterIndex = 1,
            chapterTitle = "Capítulo 1: Aislamiento",
            speaker = "Narrador",
            text = "Intentas conectar los auriculares, pero una extraña interferencia estática zumba en tus oídos. Un murmullo metálico parece pronunciar tu nombre antes de cortarse bruscamente.",
            visualType = VisualSceneType.STREET_NIGHT,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c_hp_remove", "Quitártelos sobresaltado", "s1_phone_buzz", effect = ChoiceEffect(fearDelta = 20, soundTrigger = "GLITCH"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s1_phone_buzz",
            storyId = StoryId.ULTIMA_CONEXION,
            chapterIndex = 2,
            chapterTitle = "Capítulo 2: Remitente Desconocido",
            speaker = "CHAT//",
            text = "Tu teléfono vibra en el bolsillo. Sacas la pantalla luminosa. Hay una notificación nueva de CHAT//.\n\nRemitente: Número Desconocido (+54 9 11 ????-????)\nMensaje: «¿Ya llegaste?»",
            visualType = VisualSceneType.STREET_NIGHT,
            ambientSound = AmbientSoundType.RAIN_STREET,
            choices = listOf(
                Choice("c_buzz_reply", "[ RESPONDER ]: «¿Quién es?»", "s1_reply_who", effect = ChoiceEffect(trustDelta = 10, curiosityDelta = 15, relDelta = 10, soundTrigger = "BEEP", phoneNotification = PhoneNotification("CHAT//", "¿Quién es?", "Tú"))),
                Choice("c_buzz_ignore", "[ IGNORAR ]: Guardar el teléfono y apurar el paso", "s1_ignore_msg", effect = ChoiceEffect(fearDelta = 10, dangerDelta = 10)),
                Choice("c_buzz_block", "[ BLOQUEAR ]: Bloquear el número inmediatamente", "s1_block_number", effect = ChoiceEffect(fearDelta = 5, trustDelta = -20, clueGained = CLUE_UNKNOWN_NUMBER))
            )
        ),
        Scene(
            id = "s1_reply_who",
            storyId = StoryId.ULTIMA_CONEXION,
            chapterIndex = 2,
            chapterTitle = "Capítulo 2: Contacto",
            speaker = "Número Desconocido",
            text = "La respuesta llega tres segundos después, casi instantánea:\n\n«Alguien que no quiere que te mojes esa campera negra.»\n\nUn escalofrío te recorre la espina dorsal. Llevas puesta exactamente esa campera.",
            visualType = VisualSceneType.STREET_NIGHT,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c_rw_look", "Mirar los balcones y ventanas cercanas", "s1_check_windows", effect = ChoiceEffect(fearDelta = 25, evidenceDelta = 15, clueGained = CLUE_STALKER_VISION)),
                Choice("c_rw_run_store", "Correr hacia el kiosco 24 horas abierto en la esquina", "s1_convenience_store", effect = ChoiceEffect(fearDelta = 10, dangerDelta = -10, soundTrigger = "FOOTSTEP")),
                Choice("c_rw_call_mateo", "Llamar a Mateo para pedirle ayuda", "s1_call_mateo_scene", effect = ChoiceEffect(fearDelta = 10, trustDelta = 15))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s1_ignore_msg",
            storyId = StoryId.ULTIMA_CONEXION,
            chapterIndex = 2,
            chapterTitle = "Capítulo 2: Ojos Invisibles",
            speaker = "Narrador",
            text = "Guardas el teléfono. No diste tres pasos cuando vuelve a vibrar, dos veces seguidas:\n\n«No me ignores cuando te estoy mirando.»\n«Por qué doblas hacia el pasaje oscuro si sabes que ahí no hay luz?»",
            visualType = VisualSceneType.STREET_NIGHT,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c_im_main_st", "Cambiar de dirección e ir por la avenida ancha e iluminada", "s1_main_avenue", effect = ChoiceEffect(dangerDelta = -15, fearDelta = 10)),
                Choice("c_im_shortcut", "Arriesgarte por el pasaje oscuro: llegas a tu casa en 1 minuto", "s1_dark_alley", effect = ChoiceEffect(dangerDelta = 35, fearDelta = 30, soundTrigger = "STINGER"))
            )
        ),
        Scene(
            id = "s1_block_number",
            storyId = StoryId.ULTIMA_CONEXION,
            chapterIndex = 2,
            chapterTitle = "Capítulo 2: Bloqueo Inútil",
            speaker = "CHAT//",
            text = "Pulsas 'Bloquear contacto'. La pantalla titila en negro por una fracción de segundo. La barra de señal cae a cero.\n\nAl instante, un mensaje de texto SMS tradicional entra a la bandeja:\n\n«Bloquearme no hace que deje de estar detrás de ti.»",
            visualType = VisualSceneType.STREET_NIGHT,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c_bn_run", "Comenzar a correr a toda velocidad", "s1_chase_scene", effect = ChoiceEffect(fearDelta = 30, dangerDelta = 20, soundTrigger = "HEARTBEAT")),
                Choice("c_bn_call_911", "Marcar el 911 de emergencias con manos temblorosas", "s1_call_police", effect = ChoiceEffect(evidenceDelta = 20, trustDelta = 10))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s1_check_windows",
            storyId = StoryId.ULTIMA_CONEXION,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: La Silueta en el Tercer Piso",
            speaker = "Protagonista",
            text = "Alzas la vista hacia los viejos edificios de ladrillo. En el tercer piso de una fachada gris, una cortina se mueve. Hay una figura recortada contra una pantalla de computadora encendida. En la mano sostiene un teléfono cuya luz roja parpadea.",
            visualType = VisualSceneType.STREET_NIGHT,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c_cw_photo", "Sacarle una foto con el zoom de tu teléfono", "s1_take_photo_stalker", effect = ChoiceEffect(evidenceDelta = 30, curiosityDelta = 25, itemGained = ITEM_RECEIPT, soundTrigger = "CAMERA")),
                Choice("c_cw_flee", "Bajar la cabeza y correr hacia la avenida comercial", "s1_convenience_store", effect = ChoiceEffect(fearDelta = 15, dangerDelta = -5))
            )
        ),
        Scene(
            id = "s1_take_photo_stalker",
            storyId = StoryId.ULTIMA_CONEXION,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: La Prueba",
            speaker = "Narrador",
            text = "El flash de tu teléfono se dispara por accidente. En la pantalla ves la foto guardada: la figura en la ventana lleva un buzo con un estampado inconfundible. Es exactamente el mismo buzo que Mateo llevaba en el bar hace una hora.",
            visualType = VisualSceneType.STREET_NIGHT,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c_tps_call_mateo", "Marcar inmediatamente a Mateo para confrontarlo", "s1_confront_mateo", effect = ChoiceEffect(evidenceDelta = 35, relDelta = -40, clueGained = CLUE_MATEO_ALIBI)),
                Choice("c_tps_hide", "Buscar refugio en la tienda 24h antes de que baje", "s1_convenience_store", effect = ChoiceEffect(dangerDelta = -10, fearDelta = 20))
            )
        ),
        Scene(
            id = "s1_call_mateo_scene",
            storyId = StoryId.ULTIMA_CONEXION,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: Sonido de Fondo",
            speaker = "Llamada Saliente",
            text = "Llamas a Mateo. Atiende al cuarto tono con voz cansada:\n«Hola... che, ¿qué pasó? Me estaba quedando dormido en la cama.»\n\nPero detrás de su voz no hay silencio de dormitorio: se escucha claramente el zumbido del semáforo en corto de la esquina donde estás parado.",
            visualType = VisualSceneType.STREET_NIGHT,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c_cm_ask", "«Mateo... ¿dónde carajo estás realmente?»", "s1_confront_mateo", effect = ChoiceEffect(fearDelta = 25, relDelta = -30, clueGained = CLUE_MATEO_ALIBI, soundTrigger = "CINEMATIC_PIANO")),
                Choice("c_cm_hangup", "Cortar la llamada sin decir nada y entrar a la tienda", "s1_convenience_store", effect = ChoiceEffect(fearDelta = 15, dangerDelta = -10))
            )
        ),
        Scene(
            id = "s1_confront_mateo",
            storyId = StoryId.ULTIMA_CONEXION,
            chapterIndex = 4,
            chapterTitle = "Capítulo 4: Máscaras Caídas",
            speaker = "Mateo",
            text = "Silencio denso en la línea. Luego, un suspiro amargo:\n\n«Nunca debiste mirar hacia arriba. Siempre quisiste saber demasiado... Te dije hace dos semanas que no debías volver solo por acá.»\n\nEscuchas pasos apresurados bajando la escalera del edificio contiguo.",
            visualType = VisualSceneType.STREET_NIGHT,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c_confront_run_home", "Correr con todas tus fuerzas hacia tu portal", "s1_arrive_home", effect = ChoiceEffect(dangerDelta = 25, fearDelta = 20, itemGained = ITEM_KEYS)),
                Choice("c_confront_store_police", "Meterte al kiosco 24h gritando por auxilio a la policía", "s1_store_police", effect = ChoiceEffect(dangerDelta = -30, evidenceDelta = 20))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s1_convenience_store",
            storyId = StoryId.ULTIMA_CONEXION,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: Oasis de Neón",
            speaker = "Narrador",
            text = "Empujas la puerta vidriada del 'Kiosco 24 Horas'. Una campana metálica suena alegremente. La luz fluorescente blanca te encandila tras tanta oscuridad. En el mostrador está el cajero y, comiendo un alfajor, dos oficiales de policía de la comisaría comunal.",
            visualType = VisualSceneType.CONVENIENCE_STORE,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c_cs_approach_police", "Acercarte a los oficiales y mostrar los mensajes amenazantes", "s1_store_police", effect = ChoiceEffect(fearDelta = -30, dangerDelta = -40, trustDelta = 30)),
                Choice("c_cs_wait", "Comprar un café caliente y esperar adentro hasta que amanezca", "s1_good_ending_shelter", effect = ChoiceEffect(fearDelta = -20, dangerDelta = -30))
            )
        ),
        Scene(
            id = "s1_store_police",
            storyId = StoryId.ULTIMA_CONEXION,
            chapterIndex = 4,
            chapterTitle = "Capítulo 4: Denuncia y Respaldo",
            speaker = "Oficial Romero",
            text = "El oficial observa los mensajes de tu pantalla y la foto de la figura en el tercer piso con rostro serio:\n«Quédese acá con nosotros, muchacho. Vamos a inspeccionar esa esquina ahora mismo y pedir un móvil de apoyo.»\n\nPor el ventanal ves las luces azules de la sirena iluminando la lluvia.",
            visualType = VisualSceneType.CONVENIENCE_STORE,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c_sp_finish", "Esperar en seguridad con los agentes", "s1_end_good", effect = ChoiceEffect(endingTriggered = "end_1_good"))
            )
        ),
        Scene(
            id = "s1_dark_alley",
            storyId = StoryId.ULTIMA_CONEXION,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: La Trampa de Hormigón",
            speaker = "Narrador",
            text = "Doblas en el pasaje de los sauces. El pavimento está lleno de charcos y basura acumulada. Los contenedores de metal proyectan sombras gigantescas. De pronto escuchas un golpe seco detrás tuyo: alguien ha cerrado la reja metálica del pasaje con candado.",
            visualType = VisualSceneType.ALLEYWAY,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c_da_run_ahead", "Correr hacia la salida de la otra cuadra", "s1_alley_dead_end", effect = ChoiceEffect(dangerDelta = 50, fearDelta = 40, soundTrigger = "STINGER")),
                Choice("c_da_call_mirror", "Marcar furioso al número desconocido para exigir que te dejen salir", "s1_call_mirror_scene", effect = ChoiceEffect(fearDelta = 50, curiosityDelta = 40))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s1_alley_dead_end",
            storyId = StoryId.ULTIMA_CONEXION,
            chapterIndex = 4,
            chapterTitle = "Capítulo 4: Sin Salida",
            speaker = "Narrador",
            text = "Llegas al final del callejón... pero hay una pared de ladrillos tapiada por obras que no recordabas. El agua de lluvia te empapa los ojos. Una silueta con capucha dobla la esquina lentamente con algo brillante en la mano.",
            visualType = VisualSceneType.ALLEYWAY,
            ambientSound = AmbientSoundType.SILENCE,
            choices = listOf(
                Choice("c_ade_end", "Apretar los puños contra la pared fría", "s1_end_bad", effect = ChoiceEffect(endingTriggered = "end_1_bad", soundTrigger = "HEARTBEAT"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s1_call_mirror_scene",
            storyId = StoryId.ULTIMA_CONEXION,
            chapterIndex = 4,
            chapterTitle = "Capítulo 4: La Paradoja",
            speaker = "Narrador",
            text = "Marcas el número oculto. Suena un tono... dos tonos... y entonces, el sonido de llamada sale exactamente de tu propio altavoz frontal. Al atenderse la línea, escuchas tu propia respiración acelerada y tu propia voz diciendo:\n«¿Por qué volviste a llamar? Ya estoy adentro tuyo.»",
            visualType = VisualSceneType.ALLEYWAY,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c_cm_end", "Dejar caer el teléfono al charco de agua", "s1_end_secret", effect = ChoiceEffect(endingTriggered = "end_1_secret", soundTrigger = "GLITCH"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s1_arrive_home",
            storyId = StoryId.ULTIMA_CONEXION,
            chapterIndex = 4,
            chapterTitle = "Capítulo 4: El Umbral",
            speaker = "Protagonista",
            text = "Llegas asfixiado a la puerta de vidrio de tu edificio. Sacas las llaves con manos que no paran de temblar. Pero al tocar el picaporte... la puerta cede sola. El cerrojo está abierto. En el vestíbulo oscuro, la luz del ascensor marca el piso 4: tu piso.",
            visualType = VisualSceneType.APARTMENT_DOOR,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c_ah_enter", "Entrar y empujar la puerta tras de ti", "s1_end_tragic", effect = ChoiceEffect(endingTriggered = "end_1_tragic", soundTrigger = "STINGER")),
                Choice("c_ah_confront_evidence", "Retroceder y mostrar las pruebas guardadas de Mateo a la policía que llega", "s1_end_true", requiredClueId = "clue_mateo_alibi", effect = ChoiceEffect(endingTriggered = "end_1_true"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s1_main_avenue",
            storyId = StoryId.ULTIMA_CONEXION,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: Avenida de Luces",
            speaker = "Narrador",
            text = "Eliges la avenida transitada. Pasan un par de taxis y el colectivo nocturno. El teléfono no vuelve a vibrar, pero sientes una pesadez inexplicable en el pecho. A media cuadra ves la marquesina brillante del kiosco 24h.",
            visualType = VisualSceneType.STREET_NIGHT,
            ambientSound = AmbientSoundType.RAIN_STREET,
            choices = listOf(
                Choice("c_ma_store", "Entrar a resguardarte en el kiosco 24 horas", "s1_convenience_store", effect = ChoiceEffect(dangerDelta = -20)),
                Choice("c_ma_home", "Seguir directo a tu departamento por la acera ancha", "s1_arrive_home", effect = ChoiceEffect(fearDelta = 10, itemGained = ITEM_KEYS))
            )
        ),
        Scene(
            id = "s1_chase_scene",
            storyId = StoryId.ULTIMA_CONEXION,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: Persecución a Ciegas",
            speaker = "Narrador",
            text = "Tus zapatillas golpean el agua acumulada con violencia. Detrás de ti, a no más de diez metros, se escuchan pasos pesados acelerando el ritmo. Una respiración agitada parece rozarte la nuca en la niebla.",
            visualType = VisualSceneType.STREET_NIGHT,
            ambientSound = AmbientSoundType.RAIN_STREET,
            choices = listOf(
                Choice("c_cs_turn_corner", "Doblar desesperado hacia la luz del kiosco 24h", "s1_convenience_store", effect = ChoiceEffect(dangerDelta = -20, soundTrigger = "HEARTBEAT")),
                Choice("c_cs_shortcut", "Meterte por el pasaje oscuro", "s1_dark_alley", effect = ChoiceEffect(dangerDelta = 30, soundTrigger = "STINGER"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s1_call_police",
            storyId = StoryId.ULTIMA_CONEXION,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: Línea de Emergencia",
            speaker = "Operador 911",
            text = "«911 Emergencias, ¿cuál es su ubicación?»\nExplicas con desesperación que un sujeto te está persiguiendo a pie.\n«Manténgase en la vía pública concurrida. Un móvil se encuentra a trescientos metros por la avenida.»",
            visualType = VisualSceneType.STREET_NIGHT,
            ambientSound = AmbientSoundType.RAIN_STREET,
            choices = listOf(
                Choice("c_cp_run_store", "Dirigirte al encuentro del móvil policial en el kiosco", "s1_convenience_store", effect = ChoiceEffect(fearDelta = -20, dangerDelta = -30))
            )
        ),
        Scene(
            id = "s1_good_ending_shelter",
            storyId = StoryId.ULTIMA_CONEXION,
            chapterIndex = 4,
            chapterTitle = "Capítulo 4: Refugio Seguro",
            speaker = "Narrador",
            text = "Te quedas sentado junto a la ventana iluminada con los oficiales mientras la lluvia va perdiendo fuerza. A lo lejos, la silueta encapuchada se aleja derrotada hacia la avenida principal.",
            visualType = VisualSceneType.CONVENIENCE_STORE,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c_ges_end", "Respirar aliviado al amanecer", "s1_end_good", effect = ChoiceEffect(endingTriggered = "end_1_good", soundTrigger = "MINIMAL_PIANO"))
            )
        ),
        // Ending Scenes Story 1
        Scene(id = "s1_end_good", storyId = StoryId.ULTIMA_CONEXION, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #01: SANTUARIO ILUMINADO\n\nLograste sortear la paranoia y encontrar resguardo real. La policía detuvo al sospechoso dos cuadras más adelante. Pasarán meses antes de que te atrevas a caminar de noche otra vez, pero esta noche sobreviviste.", isEndingScene = true, endingId = "end_1_good", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s1_end_bad", storyId = StoryId.ULTIMA_CONEXION, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #02: EL CALLEJÓN SIN SALIDA\n\nTomaste el camino más corto sin pensar en las consecuencias. El teléfono cayó al barro con la pantalla astillada. Tu última conexión quedó congelada para siempre en la madrugada.", isEndingScene = true, endingId = "end_1_bad", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s1_end_tragic", storyId = StoryId.ULTIMA_CONEXION, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #03: ÚLTIMO VISTO\n\nCreíste que el peligro estaba afuera. Al cruzar la puerta de tu departamento descubriste que alguien te estaba esperando adentro con la luz apagada.", isEndingScene = true, endingId = "end_1_tragic", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s1_end_secret", storyId = StoryId.ULTIMA_CONEXION, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #04: LA LLAMADA ESPEJO\n\nLa entidad que te acosaba nunca estuvo detrás de ti: vivía dentro de la señal del aparato telefónico. Al contestar la llamada, le diste permiso para ocupar tu cuerpo.", isEndingScene = true, endingId = "end_1_secret", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s1_end_true", storyId = StoryId.ULTIMA_CONEXION, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #05: CÍRCULO CERCANO\n\nReuniste las pistas y el audio grabado: Mateo había estado planeando esto durante meses impulsado por una obsesión enfermiza. Gracias a tu cautela, fue interceptado en el vestíbulo.", isEndingScene = true, endingId = "end_1_true", visualType = VisualSceneType.ENDING_SCREEN)
    )

    // ==========================================
    // SCENES: STORY 2 — EL DEPARTAMENTO
    // ==========================================
    private val SCENES_STORY_2 = listOf(
        Scene(
            id = "s2_intro",
            storyId = StoryId.EL_DEPARTAMENTO,
            chapterIndex = 1,
            chapterTitle = "Capítulo 1: Tres Golpes en la Madera",
            speaker = "Narrador",
            text = "Son las 02:40 AM. Estás solo en tu departamento del 4to piso, trabajando frente a la laptop en penumbras. Afuera no corre ni una gota de viento.\n\nDe repente, rompiendo el silencio sepulcral:\nTOC. TOC. TOC.\nTres golpes secos y pausados retumban directamente en la puerta principal.",
            visualType = VisualSceneType.APARTMENT_DOOR,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c2_peephole", "Caminar despacio y mirar por la mirilla", "s2_look_peephole", effect = ChoiceEffect(curiosityDelta = 15, fearDelta = 10, soundTrigger = "FOOTSTEP")),
                Choice("c2_ask_who", "Preguntar con voz firme: «¿Quién es a esta hora?»", "s2_ask_who_scene", effect = ChoiceEffect(trustDelta = 10, dangerDelta = 10)),
                Choice("c2_ignore", "Quedarte completamente quieto conteniendo la respiración", "s2_stay_silent", effect = ChoiceEffect(fearDelta = 20, soundTrigger = "HEARTBEAT"))
            )
        ),
        Scene(
            id = "s2_look_peephole",
            storyId = StoryId.EL_DEPARTAMENTO,
            chapterIndex = 1,
            chapterTitle = "Capítulo 1: El Ojo de Cristal",
            speaker = "Protagonista",
            text = "Apoyas la frente contra la madera fría y miras por el lente curvo de la mirilla.\n\nEl pasillo exterior está en penumbras, bañado por la luz roja de la salida de incendios. El descansillo está totalmente desierto. No hay nadie frente a la puerta.",
            visualType = VisualSceneType.PEEPHOLE,
            ambientSound = AmbientSoundType.SILENCE,
            choices = listOf(
                Choice("c_lp_wait_msg", "Apartar la vista desconcertado", "s2_phone_warning", effect = ChoiceEffect(curiosityDelta = 10, soundTrigger = "BEEP")),
                Choice("c_lp_inspect_lock", "Examinar el cerrojo de la puerta", "s2_inspect_lock", effect = ChoiceEffect(evidenceDelta = 20, clueGained = CLUE_DOOR_SCRATCHES))
            )
        ),
        Scene(
            id = "s2_inspect_lock",
            storyId = StoryId.EL_DEPARTAMENTO,
            chapterIndex = 1,
            chapterTitle = "Capítulo 1: Marcas en el Cerrojo",
            speaker = "Protagonista",
            text = "Enciendes la linterna del teléfono. En el marco metálico de la cerradura hay virutas frescas de metal y rayones profundos de palanca. Alguien intentó forzarla hace muy pocos minutos.",
            visualType = VisualSceneType.APARTMENT_DOOR,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c_il_continue", "Dar un paso atrás horrorizado", "s2_phone_warning", effect = ChoiceEffect(fearDelta = 25, dangerDelta = 15, soundTrigger = "HEARTBEAT"))
            )
        ),
        Scene(
            id = "s2_ask_who_scene",
            storyId = StoryId.EL_DEPARTAMENTO,
            chapterIndex = 1,
            chapterTitle = "Capítulo 1: Sin Respuesta Exterior",
            speaker = "Narrador",
            text = "«¿Quién es?», preguntas hacia la puerta.\n\nNadie responde del otro lado. Pero bajo la rendija inferior de la puerta, la sombra de dos pies proyectada contra la luz roja del pasillo desaparece despacio, como si quien estaba parado allí hubiera flotado o dado un paso hacia atrás sin hacer ruido.",
            visualType = VisualSceneType.APARTMENT_DOOR,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c_aw_phone", "Mirar la pantalla de tu celular que vibra", "s2_phone_warning", effect = ChoiceEffect(fearDelta = 20, soundTrigger = "BEEP"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s2_stay_silent",
            storyId = StoryId.EL_DEPARTAMENTO,
            chapterIndex = 1,
            chapterTitle = "Capítulo 1: Inmovilidad",
            speaker = "Narrador",
            text = "Permaneces como una estatua en la silla. Pasan diez segundos eternos. De pronto, el picaporte de metal comienza a girar lentamente hacia abajo, milímetro a milímetro, comprobando si el pestillo está trabado.",
            visualType = VisualSceneType.APARTMENT_DOOR,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c_ss_lock", "Correr a pasar el pasador de seguridad", "s2_phone_warning", effect = ChoiceEffect(dangerDelta = -10, fearDelta = 30, soundTrigger = "STINGER"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s2_phone_warning",
            storyId = StoryId.EL_DEPARTAMENTO,
            chapterIndex = 2,
            chapterTitle = "Capítulo 2: «No abras»",
            speaker = "CHAT//",
            text = "Tu teléfono vibra en la mesa. Un mensaje breve de un número desconocido:\n\n«No abras.»\n\nDos segundos después, entra otro mensaje:\n«Mira el piso de tu baño.»",
            visualType = VisualSceneType.APARTMENT_DOOR,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c_pw_bathroom", "Caminar cautelosamente hacia el baño", "s2_check_bathroom", effect = ChoiceEffect(fearDelta = 25, curiosityDelta = 20, soundTrigger = "FOOTSTEP")),
                Choice("c_pw_window", "Ir a la ventana del living a mirar hacia la calle", "s2_check_window", effect = ChoiceEffect(curiosityDelta = 15, dangerDelta = -5)),
                Choice("c_pw_open_door", "Harto de los juegos: abrir la puerta de golpe para atrapar al bromista", "s2_open_door_rash", effect = ChoiceEffect(dangerDelta = 60, fearDelta = 40, soundTrigger = "STINGER")),
                Choice("c_pw_call_horacio", "Llamar a Don Horacio, el conserje del edificio", "s2_call_horacio_scene", effect = ChoiceEffect(trustDelta = 20, evidenceDelta = 10))
            )
        ),
        Scene(
            id = "s2_check_bathroom",
            storyId = StoryId.EL_DEPARTAMENTO,
            chapterIndex = 2,
            chapterTitle = "Capítulo 2: El Intruso Adentro",
            speaker = "Protagonista",
            text = "Avanzas por el pasillo interior hacia el baño. La puerta está entornada. Al empujarla, ves una toalla en el suelo empapada de agua negra y una huella de calzado pesado marcada en el parquet que se dirige hacia tu dormitorio.\n\nQuien golpeó la puerta no estaba afuera tratando de entrar... quería que salieras.",
            visualType = VisualSceneType.CORRIDOR,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c_cb_kitchen_knife", "Ir a la cocina a buscar un cuchillo", "s2_kitchen_knife", effect = ChoiceEffect(itemGained = ITEM_POCKET_KNIFE, dangerDelta = -10, fearDelta = 20, clueGained = CLUE_WET_FOOTPRINT)),
                Choice("c_cb_balcony", "Correr directo al balcón a encerrarte y pedir socorro", "s2_balcony_escape", effect = ChoiceEffect(dangerDelta = -20, fearDelta = 15)),
                Choice("c_cb_bedroom", "Acercarte despacio a tu dormitorio a investigar", "s2_bedroom_confrontation", effect = ChoiceEffect(dangerDelta = 40, fearDelta = 35, soundTrigger = "STINGER"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s2_kitchen_knife",
            storyId = StoryId.EL_DEPARTAMENTO,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: Acero y Sombras",
            speaker = "Protagonista",
            text = "Tomas un cuchillo de cocina del bloque de madera. Sientes el peso del acero. En el mueble sobre la heladera notas algo extraño: la rejilla de ventilación del cielorraso está desatornillada y colgando.",
            visualType = VisualSceneType.CORRIDOR,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c_kk_vent", "Inspeccionar el conducto de ventilación con la linterna", "s2_inspect_vent", effect = ChoiceEffect(evidenceDelta = 40, curiosityDelta = 30, itemGained = ITEM_NOTE_VENT, soundTrigger = "CAMERA")),
                Choice("c_kk_balcony", "Ignorar la rejilla y correr a asegurar el balcón", "s2_balcony_escape", effect = ChoiceEffect(dangerDelta = -20))
            )
        ),
        Scene(
            id = "s2_inspect_vent",
            storyId = StoryId.EL_DEPARTAMENTO,
            chapterIndex = 4,
            chapterTitle = "Capítulo 4: El Nido en los Muros",
            speaker = "Protagonista",
            text = "Alumbras dentro del conducto. Hay una pequeña frazada, botellas vacías y hojas manchadas arrancadas de una libreta: contienen tus horarios exactos, fotos tuyas saliendo a comprar y una anotación que dice:\n«El departamento es mío. Esta noche recupero mi cama.»",
            visualType = VisualSceneType.CORRIDOR,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c_iv_confront_true", "Atrincherarte en el living con las pruebas y llamar al 911", "s2_end_true", effect = ChoiceEffect(endingTriggered = "end_2_true")),
                Choice("c_iv_bedroom", "Buscarlo en la habitación para terminar con esto", "s2_bedroom_confrontation", effect = ChoiceEffect(dangerDelta = 40, soundTrigger = "STINGER"))
            )
        ),
        Scene(
            id = "s2_check_window",
            storyId = StoryId.EL_DEPARTAMENTO,
            chapterIndex = 2,
            chapterTitle = "Capítulo 2: El Edificio de Enfrente",
            speaker = "Narrador",
            text = "Apartas apenas las persianas del living. La calle abajo está vacía bajo la niebla. Pero en el edificio de enfrente, en el cuarto piso a tu misma altura, una persona está de pie en el alféizar mirándote fijamente a través de unos prismáticos. Sostiene un cartel de cartón escrito con marcador rojo:\n«DETRÁS TUYO»",
            visualType = VisualSceneType.BALCONY,
            ambientSound = AmbientSoundType.SILENCE,
            choices = listOf(
                Choice("c_cw_turn", "Girar en redondo a toda velocidad", "s2_check_bathroom", effect = ChoiceEffect(fearDelta = 40, dangerDelta = 20, soundTrigger = "HEARTBEAT"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s2_open_door_rash",
            storyId = StoryId.EL_DEPARTAMENTO,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: El Error Fatal",
            speaker = "Narrador",
            text = "Giras la llave y abres la puerta de par en par con furia:\n«¡Ya basta, sal de acá!»\n\nEl pasillo exterior está completamente desierto y silencioso. Pero justo a tus espaldas, desde la penumbra de tu propio living, se escucha el chasquido del cerrojo cerrándose por dentro.",
            visualType = VisualSceneType.APARTMENT_DOOR,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c_od_end", "Intentar girar el rostro en la oscuridad", "s2_end_bad", effect = ChoiceEffect(endingTriggered = "end_2_bad", soundTrigger = "STINGER"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s2_call_horacio_scene",
            storyId = StoryId.EL_DEPARTAMENTO,
            chapterIndex = 2,
            chapterTitle = "Capítulo 2: La Llamada del Conserje",
            speaker = "Llamada Entrante",
            text = "Llamas a la portería. Don Horacio atiende con voz temblorosa y lejana:\n«Pibe... ¿estás en tu piso? Por favor decime que no abriste la puerta de salida a la escalera vieja.»\n«Don Horacio, ¿de qué habla?»\n«Esa escalera está clausurada desde 1994... alguien rompió el candado desde el sótano.»",
            visualType = VisualSceneType.APARTMENT_DOOR,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c_ch_stairs", "Salir al pasillo con la llave de incendio a investigar la escalera clausurada", "s2_secret_stairs", effect = ChoiceEffect(curiosityDelta = 40, dangerDelta = 30, itemGained = ITEM_SECURITY_KEY)),
                Choice("c_ch_balcony", "Trabar la puerta de inmediato y correr al balcón", "s2_balcony_escape", effect = ChoiceEffect(dangerDelta = -20))
            )
        ),
        Scene(
            id = "s2_secret_stairs",
            storyId = StoryId.EL_DEPARTAMENTO,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: El Rellano Prohibido",
            speaker = "Narrador",
            text = "Abres con cuidado y sales al rellano. La reja de la escalera de incendios cuelga abierta. Bajas en penumbras siguiendo un aroma a moho antiguo. Llegas a un descanso marcado con una placa de bronce descolorida: 'PISO -1'. Las puertas de este nivel están soldadas con cadenas oxidadas.",
            visualType = VisualSceneType.CORRIDOR,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c_ss_explore", "Avanzar por el pasillo interminable del subsuelo", "s2_end_secret", effect = ChoiceEffect(endingTriggered = "end_2_secret", soundTrigger = "GLITCH"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s2_balcony_escape",
            storyId = StoryId.EL_DEPARTAMENTO,
            chapterIndex = 4,
            chapterTitle = "Capítulo 4: Atrincherado",
            speaker = "Protagonista",
            text = "Corres al balcón, cierras el ventanal de vidrio reforzado y trabas el pasador superior. Desde afuera, a través del vidrio, ves una figura alta y demacrada parada inmóvil en medio de tu living mirándote. Comienzas a gritar pidiendo auxilio hacia la avenida.",
            visualType = VisualSceneType.BALCONY,
            ambientSound = AmbientSoundType.RAIN_STREET,
            choices = listOf(
                Choice("c_be_shout", "Seguir gritando y encender la linterna hacia la calle", "s2_end_good", effect = ChoiceEffect(endingTriggered = "end_2_good"))
            )
        ),
        Scene(
            id = "s2_bedroom_confrontation",
            storyId = StoryId.EL_DEPARTAMENTO,
            chapterIndex = 4,
            chapterTitle = "Capítulo 4: La Habitación Oscura",
            speaker = "Narrador",
            text = "Entras a tu dormitorio. Las puertas del armario están abiertas de par en par. La cama está destendida. De pronto, un golpe seco suena en la puerta principal nuevamente: TOC TOC TOC.\n\nCaminas hacia la mirilla para mirar una última vez...",
            visualType = VisualSceneType.PEEPHOLE,
            ambientSound = AmbientSoundType.SILENCE,
            choices = listOf(
                Choice("c_bc_look_peephole_final", "Pegar el ojo al lente de vidrio", "s2_end_tragic", effect = ChoiceEffect(endingTriggered = "end_2_tragic", soundTrigger = "STINGER"))
            ),
            autoGlitch = true
        ),
        // Endings Story 2
        Scene(id = "s2_end_good", storyId = StoryId.EL_DEPARTAMENTO, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #06: AMANECER EN EL BALCÓN\n\nTus gritos despertaron a los vecinos del 3er piso, quienes llamaron a la patrulla. La policía forzó la puerta y redujo al intruso armado que se escondía en tu comedor. Saliste ileso al primer rayo de sol.", isEndingScene = true, endingId = "end_2_good", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s2_end_bad", storyId = StoryId.EL_DEPARTAMENTO, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #07: DETRÁS DE TI\n\nCaíste en la trampa de abrir la puerta al exterior. Quien golpeaba nunca quiso entrar: quería que le dieras la espalda.", isEndingScene = true, endingId = "end_2_bad", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s2_end_tragic", storyId = StoryId.EL_DEPARTAMENTO, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #08: EL ROSTRO EN LA MIRILLA\n\nMiras por la mirilla. Del otro lado, a un milímetro del cristal, un ojo ensangrentado te devuelve la mirada antes de que la puerta se abra con violencia.", isEndingScene = true, endingId = "end_2_tragic", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s2_end_secret", storyId = StoryId.EL_DEPARTAMENTO, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #09: EL PISO INEXISTENTE\n\nDescubriste el subsuelo clausurado que ningún inquilino conoció jamás. Tus pasos se perdieron en pasillos que la arquitectura oficial nunca registró.", isEndingScene = true, endingId = "end_2_secret", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s2_end_true", storyId = StoryId.EL_DEPARTAMENTO, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #10: EL INQUILINO ANTERIOR\n\nLas pruebas halladas en los conductos revelaron la pesadilla: el anterior inquilino, dado por desaparecido hace dos años, habitaba los huecos entre las paredes observando cada uno de tus movimientos.", isEndingScene = true, endingId = "end_2_true", visualType = VisualSceneType.ENDING_SCREEN)
    )

    // ==========================================
    // SCENES: STORY 3 — LA FOTO
    // ==========================================
    private val SCENES_STORY_3 = listOf(
        Scene(
            id = "s3_intro",
            storyId = StoryId.LA_FOTO,
            chapterIndex = 1,
            chapterTitle = "Capítulo 1: La Notificación a las 03:33",
            speaker = "CHAT//",
            text = "Te despiertas sobresaltado por el resplandor blanco de tu teléfono sobre la mesa de luz. El reloj digital marca exactamente las 03:33 AM.\n\nHay un mensaje nuevo de CHAT// con un archivo adjunto:\n«Foto recibida de Remitente Oculto»",
            visualType = VisualSceneType.BEDROOM_DARK,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c3_open_photo", "Descargar y abrir la fotografía", "s3_inspect_photo_scene", effect = ChoiceEffect(curiosityDelta = 20, fearDelta = 15, soundTrigger = "BEEP")),
                Choice("c3_check_room", "No abrirla y encender inmediatamente la luz de la habitación", "s3_turn_on_light", effect = ChoiceEffect(fearDelta = 20, dangerDelta = 10, soundTrigger = "HEARTBEAT")),
                Choice("c3_delete_msg", "Borrar el mensaje y volver a taparte", "s3_ignore_photo", effect = ChoiceEffect(fearDelta = 25, dangerDelta = 25))
            )
        ),
        Scene(
            id = "s3_inspect_photo_scene",
            storyId = StoryId.LA_FOTO,
            chapterIndex = 1,
            chapterTitle = "Capítulo 1: El Ángulo Imposible",
            speaker = "Protagonista",
            text = "La fotografía se abre en pantalla completa. Sientes que la sangre se te congela:\n\nEs una foto tuya durmiendo en esa misma cama, tapado con la frazada azul. La toma fue capturada desde lo alto del armario de tu cuarto... ¡hace exactamente 3 minutos!\n\nAl fondo de la imagen, la puerta que dejaste con pasador antes de acostarte se ve abierta.",
            visualType = VisualSceneType.BEDROOM_DARK,
            ambientSound = AmbientSoundType.SILENCE,
            choices = listOf(
                Choice("c_ip_zoom", "Hacer zoom en el reflejo del espejo del armario", "s3_zoom_mirror", effect = ChoiceEffect(evidenceDelta = 25, curiosityDelta = 30, soundTrigger = "CAMERA")),
                Choice("c_ip_look_door", "Girar la cabeza para comprobar la puerta real de tu cuarto", "s3_check_real_door", effect = ChoiceEffect(fearDelta = 30, dangerDelta = 15, soundTrigger = "HEARTBEAT")),
                Choice("c_ip_exif", "Examinar los detalles técnicos y metadatos del archivo", "s3_check_metadata", effect = ChoiceEffect(evidenceDelta = 30, clueGained = CLUE_PHOTO_EXIF))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s3_zoom_mirror",
            storyId = StoryId.LA_FOTO,
            chapterIndex = 2,
            chapterTitle = "Capítulo 2: El Reflejo",
            speaker = "Protagonista",
            text = "Amplías la foto pixel a pixel en la esquina del espejo. Entre las sombras se distinguen dos ojos pálidos y una sonrisa torcida asomándose desde el espacio estrecho entre el techo y la parte superior del placard.",
            visualType = VisualSceneType.BEDROOM_DARK,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c_zm_look_up", "Alzar lentamente la mirada hacia el techo del armario", "s3_look_attic", effect = ChoiceEffect(fearDelta = 40, dangerDelta = 30, clueGained = CLUE_ATTIC_LATCH, soundTrigger = "STINGER")),
                Choice("c_zm_run_hall", "Saltar de la cama y salir corriendo hacia la puerta de entrada", "s3_escape_rush", effect = ChoiceEffect(dangerDelta = -10, fearDelta = 20, itemGained = ITEM_KEYS))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s3_check_real_door",
            storyId = StoryId.LA_FOTO,
            chapterIndex = 2,
            chapterTitle = "Capítulo 2: La Puerta Abierta",
            speaker = "Narrador",
            text = "Giras la cabeza despacio hacia el marco de la puerta. Tal como mostraba la fotografía, la puerta de tu dormitorio está abierta cuatro dedos. La luz rojiza de la calle dibuja una línea sobre el suelo del pasillo.",
            visualType = VisualSceneType.BEDROOM_DARK,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c_crd_flashlight", "Tomar la linterna y alumbrar hacia el pasillo", "s3_flashlight_hall", effect = ChoiceEffect(itemGained = ITEM_FLASHLIGHT, fearDelta = 20, curiosityDelta = 15)),
                Choice("c_crd_closet", "Comprobar primero el armario antes de salir", "s3_investigate_closet", effect = ChoiceEffect(dangerDelta = 40, fearDelta = 30, soundTrigger = "STINGER"))
            )
        ),
        Scene(
            id = "s3_turn_on_light",
            storyId = StoryId.LA_FOTO,
            chapterIndex = 1,
            chapterTitle = "Capítulo 1: El Interruptor Muerto",
            speaker = "Protagonista",
            text = "Estiras el brazo y pulsas el interruptor de la lámpara. Nada. Ni un destello. Pruebas la llave de luz principal de la pared: un chisporroteo sordo en el disyuntor confirma que alguien cortó la fase eléctrica desde la caja general.",
            visualType = VisualSceneType.BEDROOM_DARK,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c_tol_photo", "Usar el brillo del celular para mirar la foto recibida", "s3_inspect_photo_scene", effect = ChoiceEffect(fearDelta = 20))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s3_ignore_photo",
            storyId = StoryId.LA_FOTO,
            chapterIndex = 1,
            chapterTitle = "Capítulo 1: Negación",
            speaker = "Narrador",
            text = "Borras la foto con el dedo temblando y cierras los ojos con fuerza. 'Es solo una pesadilla'.\n\nDos segundos después, el flash de una cámara fotográfica estalla a treinta centímetros por encima de tu rostro, iluminando las sábanas por una milésima de segundo.",
            visualType = VisualSceneType.BEDROOM_DARK,
            ambientSound = AmbientSoundType.SILENCE,
            choices = listOf(
                Choice("c_ip_panic", "Abrir los ojos aterrorizado", "s3_end_bad", effect = ChoiceEffect(endingTriggered = "end_3_bad", soundTrigger = "STINGER"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s3_check_metadata",
            storyId = StoryId.LA_FOTO,
            chapterIndex = 2,
            chapterTitle = "Capítulo 2: Los Metadatos Ocultos",
            speaker = "Protagonista",
            text = "Revisas la información EXIF de la imagen:\n• Fecha: Hoy - 03:31:04 AM\n• Dispositivo: Cámara Frontal de TU propia laptop, vinculada vía WiFi local.\n• Carpeta de origen: 'Grabaciones_Nocturnas_03'\n\nHay decenas de videos automáticos grabados en las últimas tres semanas mientras dormías.",
            visualType = VisualSceneType.BEDROOM_DARK,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c_cmd_watch_video", "Reproducir el último video guardado en la laptop", "s3_watch_recordings", effect = ChoiceEffect(evidenceDelta = 40, curiosityDelta = 35, clueGained = CLUE_PHOTO_EXIF)),
                Choice("c_cmd_escape", "No mirar más y huir de inmediato", "s3_escape_rush", effect = ChoiceEffect(dangerDelta = -15, itemGained = ITEM_KEYS))
            )
        ),
        Scene(
            id = "s3_watch_recordings",
            storyId = StoryId.LA_FOTO,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: La Grabación Reveladora",
            speaker = "Protagonista",
            text = "Pones reproducir al video de las 03:20 AM.\nEn la pantalla te ves a ti mismo levantándote de la cama con los ojos completamente blancos, caminando como un autómata hacia el espejo, apoyando la laptop sobre el armario y disparando la foto con una sonrisa siniestra antes de volver a acostarte.",
            visualType = VisualSceneType.BEDROOM_DARK,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c_wr_somnambulism", "Mirarte las manos temblando de incredulidad", "s3_end_tragic", effect = ChoiceEffect(endingTriggered = "end_3_tragic", soundTrigger = "HEARTBEAT")),
                Choice("c_wr_inspect_glitch", "Notar que en el segundo 14 del video aparece otra figura detrás tuyo", "s3_look_attic", effect = ChoiceEffect(curiosityDelta = 40, fearDelta = 30))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s3_look_attic",
            storyId = StoryId.LA_FOTO,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: La Trampilla del Techo",
            speaker = "Protagonista",
            text = "Subes a una silla y alumbras la esquina superior del armario. La tapa cuadrada de madera que da acceso al entretecho está desplazada diez centímetros. Cuelgan unos cabellos húmedos y un trozo de cinta aisladora.",
            visualType = VisualSceneType.BEDROOM_DARK,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c_la_push_lid", "Empujar la tapa con la linterna para ver el interior del ático", "s3_attic_inside", effect = ChoiceEffect(evidenceDelta = 50, curiosityDelta = 40, dangerDelta = 20, soundTrigger = "CAMERA")),
                Choice("c_la_flee_call_cops", "Bajar de la silla y salir volando hacia la comisaría", "s3_escape_rush", effect = ChoiceEffect(dangerDelta = -30, itemGained = ITEM_KEYS))
            )
        ),
        Scene(
            id = "s3_attic_inside",
            storyId = StoryId.LA_FOTO,
            chapterIndex = 4,
            chapterTitle = "Capítulo 4: El Nido Clandestino",
            speaker = "Protagonista",
            text = "La luz de la linterna corta el polvo del ático. Hay un colchón inflable desinflado, latas de comida vacías, una cámara montada en un trípode miniatura apuntando a través de una perforación en el cielorraso... y una pared empapelada con fotos tuyas desde que te mudaste.",
            visualType = VisualSceneType.BEDROOM_DARK,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c_ai_true_end", "Bajar a toda velocidad, cerrar con traba y presentar las fotos a la fiscalía", "s3_end_true", effect = ChoiceEffect(endingTriggered = "end_3_true")),
                Choice("c_ai_secret_zoom", "Examinar la última foto del rollo pegado a la viga", "s3_end_secret", effect = ChoiceEffect(endingTriggered = "end_3_secret", soundTrigger = "GLITCH"))
            )
        ),
        Scene(
            id = "s3_escape_rush",
            storyId = StoryId.LA_FOTO,
            chapterIndex = 4,
            chapterTitle = "Capítulo 4: Huida Desesperada",
            speaker = "Narrador",
            text = "Tomas las llaves de la mesa, abres la puerta del departamento y bajas las escaleras de a cuatro peldaños sin mirar atrás hasta cruzar el portal hacia la calle concurrida.",
            visualType = VisualSceneType.STREET_NIGHT,
            ambientSound = AmbientSoundType.RAIN_STREET,
            choices = listOf(
                Choice("c_er_safe", "Llegar a la comisaría comunal a salvo con el teléfono en mano", "s3_end_good", effect = ChoiceEffect(endingTriggered = "end_3_good"))
            )
        ),
        Scene(
            id = "s3_investigate_closet",
            storyId = StoryId.LA_FOTO,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: Dentro del Placard",
            speaker = "Narrador",
            text = "Abres las puertas corredizas del armario. Tu ropa colgada oscila suavemente como si alguien acabara de apartarla. Al fondo, entre los abrigos de invierno, se asoman dos piernas inmóviles.",
            visualType = VisualSceneType.BEDROOM_DARK,
            ambientSound = AmbientSoundType.SILENCE,
            choices = listOf(
                Choice("c_ic_bad", "Extender la mano para apartar los abrigos", "s3_end_bad", effect = ChoiceEffect(endingTriggered = "end_3_bad", soundTrigger = "STINGER"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s3_flashlight_hall",
            storyId = StoryId.LA_FOTO,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: El Pasillo en Sombras",
            speaker = "Protagonista",
            text = "Alumbras el pasillo. La puerta de entrada al departamento está entreabierta con la llave colocada desde el exterior. El camino está despejado hacia la salida.",
            visualType = VisualSceneType.CORRIDOR,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c_fh_run", "Aprovechar la oportunidad y correr hacia la salida", "s3_escape_rush", effect = ChoiceEffect(dangerDelta = -20, itemGained = ITEM_KEYS)),
                Choice("c_fh_back", "Volver al cuarto a recuperar la laptop con las pruebas", "s3_check_metadata", effect = ChoiceEffect(curiosityDelta = 30, dangerDelta = 20))
            )
        ),
        // Endings Story 3
        Scene(id = "s3_end_good", storyId = StoryId.LA_FOTO, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #11: LA FALSA ALARMA\n\nNo dudaste ni perdiste tiempo buscando explicaciones. Saliste a la calle y acudiste a las autoridades. Las pericias confirmaron la intrusión y te asignaron custodia.", isEndingScene = true, endingId = "end_3_good", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s3_end_bad", storyId = StoryId.LA_FOTO, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #12: SONRÍE A LA CÁMARA\n\nEl flash estalló frente a tus ojos. La última fotografía tomada dentro de esa habitación nunca fue subida a ninguna red.", isEndingScene = true, endingId = "end_3_bad", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s3_end_tragic", storyId = StoryId.LA_FOTO, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #13: SIN RECUERDOS\n\nEl intruso no era una persona ajena: eras tú mismo, sufriendo episodios disociativos de sonambulismo severo inducidos por el aislamiento. El terror estaba dentro de ti.", isEndingScene = true, endingId = "end_3_tragic", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s3_end_secret", storyId = StoryId.LA_FOTO, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #14: LA DIMENSIÓN DE LA LENTE\n\nAl mirar la última fotografía de la viga, descubriste que mostraba un evento que todavía no había sucedido. La pantalla del teléfono empezó a refractar una realidad alterna.", isEndingScene = true, endingId = "end_3_secret", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s3_end_true", storyId = StoryId.LA_FOTO, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #15: EL INTRUSO DEL ÁTICO\n\nLa policía allanó el entretecho y encontró a un desconocido que llevaba seis meses viviendo sobre tu cielorraso observándote dormir cada noche.", isEndingScene = true, endingId = "end_3_true", visualType = VisualSceneType.ENDING_SCREEN)
    )

    // Lookup functions
    fun getAllScenesForStory(storyId: StoryId): List<Scene> {
        return when (storyId) {
            StoryId.ULTIMA_CONEXION -> SCENES_STORY_1
            StoryId.EL_DEPARTAMENTO -> SCENES_STORY_2
            StoryId.LA_FOTO -> SCENES_STORY_3
            StoryId.EL_ULTIMO_TREN -> Story4Content.SCENES
            StoryId.EL_TURNO_NOCHE -> Story5Content.SCENES
            StoryId.EL_TRANSMISOR -> Story6SpecialContent.SCENES
            StoryId.LA_CABANA -> Story7SpecialContent.SCENES
            StoryId.EL_ESPEJO -> Story8SpecialContent.SCENES
        }
    }

    fun getScene(storyId: StoryId, sceneId: String): Scene? {
        return getAllScenesForStory(storyId).find { it.id == sceneId }
    }

    fun getEnding(endingId: String): Ending? {
        return ALL_ENDINGS.find { it.id == endingId }
    }
}
