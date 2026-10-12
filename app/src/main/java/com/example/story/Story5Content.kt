package com.example.story

import com.example.model.AmbientSoundType
import com.example.model.Choice
import com.example.model.ChoiceEffect
import com.example.model.Clue
import com.example.model.Ending
import com.example.model.EndingBadge
import com.example.model.InventoryItem
import com.example.model.PhoneNotification
import com.example.model.Scene
import com.example.model.StoryId
import com.example.model.VisualSceneType

object Story5Content {

    val ITEM_HOSPITAL_BADGE = InventoryItem("item_badge", "Tarjeta de Acceso Nivel 2", "Tarjeta plástica con banda magnética perteneciente al Dr. Peralta.", "ic_key")
    val ITEM_SEDATIVE_VIAL = InventoryItem("item_vial", "Frasco de Sedante Vencido", "Ampolla pequeña con etiqueta amarilla descolorida de 2014.", "ic_receipt")
    val ITEM_SECURITY_TAPE = InventoryItem("item_tape", "Cinta VHS Cámara 4", "Grabación de seguridad de la habitación 404.", "ic_photo")

    val CLUE_ROOM_404 = Clue("clue_room_404", StoryId.EL_TURNO_NOCHE, "Clausura de la 404", "El ala oeste sufrió un siniestro hace una década. Los planos indican que no hay cableado telefónico activo allí.", "Expediente del conmutador", "ic_search")
    val CLUE_PATIENT_FILE = Clue("clue_patient_file", StoryId.EL_TURNO_NOCHE, "Expediente Confidencial", "La ficha médica archivada en el sótano coincide con tu fecha de nacimiento y tipo sanguíneo.", "Archivo del sótano", "ic_info")

    val ENDINGS = listOf(
        Ending("end_5_good", StoryId.EL_TURNO_NOCHE, 21, "ALTA MÉDICA", EndingBadge.BUENO, "Te atrincheraste en la sala de seguridad hasta la llegada del relevo de las 07:00 AM y la policía.", "«El turno noche ha terminado.»"),
        Ending("end_5_bad", StoryId.EL_TURNO_NOCHE, 22, "LA HABITACIÓN 404", EndingBadge.MALO, "Entraste a la habitación prohibida. El pestillo cayó por fuera y las correas de la camilla te sujetaron en la oscuridad.", "«La medicación de medianoche nunca se suspende.»"),
        Ending("end_5_tragic", StoryId.EL_TURNO_NOCHE, 23, "EL EXPEDIENTE PERDIDO", EndingBadge.TRAGICO, "Leíste tu propia historia clínica: nunca fuiste el vigilante, eras el último paciente en coma profundo.", "«Despertar a veces es el peor de los castigos.»"),
        Ending("end_5_secret", StoryId.EL_TURNO_NOCHE, 24, "LA FRECUENCIA DEL ELECTRO", EndingBadge.SECRETO, "Los monitores cardíacos del hospital se sincronizaron con tu respiración hasta apagar las luces del edificio.", "«Un pitido continuo marcó el silencio absoluto.»"),
        Ending("end_5_true", StoryId.EL_TURNO_NOCHE, 25, "EL SECRETO DEL SANATORIO", EndingBadge.VERDADERO, "Recuperaste los registros de negligencia médica de la caldera y revelaste los crímenes del antiguo director.", "«Las almas del ala oeste finalmente tuvieron justicia.»")
    )

    val SCENES = listOf(
        Scene(
            id = "s5_intro",
            storyId = StoryId.EL_TURNO_NOCHE,
            chapterIndex = 1,
            chapterTitle = "Capítulo 1: Guardia Solitaria",
            speaker = "Narrador",
            text = "03:00 AM. La lluvia azota los ventanales del Sanatorio San Gabriel. Estás en tu primera semana como vigilante del turno nocturno. Los pasillos de baldosas blancas reflejan la luz fría de las lámparas de emergencia. En el escritorio central, los 8 monitores CCTV muestran corredores en completa calma.",
            visualType = VisualSceneType.HOSPITAL_CORRIDOR,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c5_check_monitors", "Revisar las cámaras de seguridad con detenimiento", "s5_check_cctv", effect = ChoiceEffect(curiosityDelta = 15, fearDelta = 10)),
                Choice("c5_rounds", "Hacer la ronda programada por el pasillo norte", "s5_walk_hall", effect = ChoiceEffect(fearDelta = 20, dangerDelta = 15, soundTrigger = "FOOTSTEP")),
                Choice("c5_coffee", "Ir a la sala de descanso por un café caliente", "s5_break_room", effect = ChoiceEffect(fearDelta = 5, itemGained = ITEM_HOSPITAL_BADGE))
            )
        ),
        Scene(
            id = "s5_check_cctv",
            storyId = StoryId.EL_TURNO_NOCHE,
            chapterIndex = 1,
            chapterTitle = "Capítulo 1: Cámara Cuatro",
            speaker = "Protagonista",
            text = "Miras el monitor de la Cámara 4 (Ala Oeste, clausurada desde 2014). La pantalla tiene estática gris... y de pronto, una camilla con sábanas revueltas rueda lentamente hacia la lente. Al fondo del pasillo desierto se distingue la silueta de una enfermera con cofia antigua parada de espaldas.",
            visualType = VisualSceneType.HOSPITAL_CORRIDOR,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c5_cctv_phone", "El intercomunicador del escritorio comienza a sonar con un pitido estridente", "s5_intercom_call", effect = ChoiceEffect(soundTrigger = "RING"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s5_walk_hall",
            storyId = StoryId.EL_TURNO_NOCHE,
            chapterIndex = 1,
            chapterTitle = "Capítulo 1: Ecos de Pisadas",
            speaker = "Narrador",
            text = "Caminas con la linterna por el pasillo del ala médica. Un olor agrio a desinfectante y éter inunda el aire. Al pasar frente a la puerta del ascensor de camillas, las puertas metálicas se abren solas con un rechinido. El interior está sumido en una oscuridad impenetrable.",
            visualType = VisualSceneType.HOSPITAL_CORRIDOR,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c5_wh_elevator", "Alumbrar el interior del hueco del ascensor", "s5_elevator_shaft", effect = ChoiceEffect(fearDelta = 25, dangerDelta = 20, soundTrigger = "STINGER")),
                Choice("c5_wh_back", "Retroceder apresurado hacia la cabina de vigilancia", "s5_check_cctv", effect = ChoiceEffect(fearDelta = 15))
            )
        ),
        Scene(
            id = "s5_break_room",
            storyId = StoryId.EL_TURNO_NOCHE,
            chapterIndex = 1,
            chapterTitle = "Capítulo 1: Sala de Guardia",
            speaker = "Protagonista",
            text = "En la sala de médicos encuentras la bata del Dr. Peralta abandonada sobre una silla. En el bolsillo cuelga su tarjeta de acceso nivel 2 y un frasco de sedante antiguo con fecha de hace diez años.",
            visualType = VisualSceneType.HOSPITAL_CORRIDOR,
            ambientSound = AmbientSoundType.SILENCE,
            choices = listOf(
                Choice("c5_br_take_items", "Guardar la tarjeta y el frasco para investigar", "s5_check_cctv", effect = ChoiceEffect(evidenceDelta = 20, itemGained = ITEM_HOSPITAL_BADGE, itemUsed = "item_vial"))
            )
        ),
        Scene(
            id = "s5_intercom_call",
            storyId = StoryId.EL_TURNO_NOCHE,
            chapterIndex = 2,
            chapterTitle = "Capítulo 2: Llamada de la 404",
            speaker = "Intercomunicador",
            text = "Levantas el auricular del conmutador. Una voz jadeante de mujer susurra entre interferencias:\n«Por favor... la aguja del goteo está tapada... hace diez años que nadie viene a cambiar el suero... vení a la 404...»\n\nEl tablero parpadea con la luz roja: HABITACIÓN 404.",
            visualType = VisualSceneType.HOSPITAL_CORRIDOR,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c5_ic_stairs_404", "Tomar las llaves y subir las escaleras hacia la habitación 404", "s5_stairs_404", effect = ChoiceEffect(fearDelta = 30, dangerDelta = 25, clueGained = CLUE_ROOM_404)),
                Choice("c5_ic_basement_archive", "Bajar al sótano a revisar los expedientes históricos del incendio", "s5_basement_archive", effect = ChoiceEffect(curiosityDelta = 35, evidenceDelta = 25, soundTrigger = "FOOTSTEP")),
                Choice("c5_ic_lock_booth", "Trabar la puerta de la sala de seguridad y llamar a emergencias", "s5_lock_booth", effect = ChoiceEffect(fearDelta = 15, dangerDelta = -15))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s5_stairs_404",
            storyId = StoryId.EL_TURNO_NOCHE,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: El Ala Quemada",
            speaker = "Narrador",
            text = "Subes al cuarto piso. Las paredes están ennegrecidas por el hollín del fuego antiguo. El suelo cruje bajo una capa de ceniza y medicamentos esparcidos. Al final del corredor está la puerta de la 404 con cintas policiales rotas y el número torcido en bronce.",
            visualType = VisualSceneType.HOSPITAL_CORRIDOR,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c5_s4_enter", "Empujar la puerta y entrar a la habitación", "s5_inside_404", effect = ChoiceEffect(fearDelta = 40, dangerDelta = 40, soundTrigger = "STINGER")),
                Choice("c5_s4_look_vent", "Mirar a través de la mirilla rota de la puerta", "s5_peep_404", effect = ChoiceEffect(fearDelta = 30, curiosityDelta = 20))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s5_basement_archive",
            storyId = StoryId.EL_TURNO_NOCHE,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: El Archivo Subterráneo",
            speaker = "Protagonista",
            text = "Llegas al depósito subterráneo. Filas de carpetas médicas amarillentas se extienden en estanterías metálicas húmedas. Buscas la sección del año 2014. Encuentras un sobre sellado que lleva tu propio nombre y apellido en letras de molde.",
            visualType = VisualSceneType.CORRIDOR,
            ambientSound = AmbientSoundType.SILENCE,
            choices = listOf(
                Choice("c5_ba_read_file", "Abrir el expediente y leer el diagnóstico", "s5_read_patient_file", effect = ChoiceEffect(evidenceDelta = 50, clueGained = CLUE_PATIENT_FILE)),
                Choice("c5_ba_boiler_room", "Investigar la sala de calderas contigua de donde sale humo", "s5_boiler_room", effect = ChoiceEffect(evidenceDelta = 40, curiosityDelta = 30))
            )
        ),
        Scene(
            id = "s5_lock_booth",
            storyId = StoryId.EL_TURNO_NOCHE,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: Asedio en la Garita",
            speaker = "Narrador",
            text = "Pasas los cerrojos de la cabina de vigilancia. Marcas el 911 pero la línea emite un tono continuo de electrocardiograma plano. En los 8 monitores CCTV, las imágenes se alteran simultáneamente: en todas las cámaras aparece la figura de la enfermera caminando hacia tu garita.",
            visualType = VisualSceneType.HOSPITAL_CORRIDOR,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c5_lb_resist", "Atrincherarte con el extintor y no abrir a nadie", "s5_end_good", effect = ChoiceEffect(endingTriggered = "end_5_good", soundTrigger = "KNOCK")),
                Choice("c5_lb_sync", "Escuchar el sonido del monitor cardíaco que resuena en tu cabeza", "s5_end_secret", effect = ChoiceEffect(endingTriggered = "end_5_secret", soundTrigger = "GLITCH"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s5_inside_404",
            storyId = StoryId.EL_TURNO_NOCHE,
            chapterIndex = 4,
            chapterTitle = "Capítulo 4: El Lecho Vacío",
            speaker = "Narrador",
            text = "Entras a la habitación 404. El aire huele a humo reciente. En el centro hay una cama con correas de cuero desgastadas. La puerta se cierra con un portazo violento detrás tuyo. Unas manos frías y huesudas te sujetan los hombros mientras la aguja de un gotero se clava en tu brazo.",
            visualType = VisualSceneType.HOSPITAL_CORRIDOR,
            ambientSound = AmbientSoundType.SILENCE,
            choices = listOf(
                Choice("c5_in_end", "Perder el conocimiento en la penumbra", "s5_end_bad", effect = ChoiceEffect(endingTriggered = "end_5_bad", soundTrigger = "STINGER"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s5_read_patient_file",
            storyId = StoryId.EL_TURNO_NOCHE,
            chapterIndex = 4,
            chapterTitle = "Capítulo 4: El Despertar Imposible",
            speaker = "Protagonista",
            text = "Lees la ficha médica atónito: «Ingreso 14/10/2014. Diagnóstico: Coma inducido tras inhalación de monóxido en el incendio del sanatorio. Paciente no responde a estímulos. Pronóstico: Irreversible». Miras tus manos: la piel se torna translúcida.",
            visualType = VisualSceneType.CORRIDOR,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c5_rpf_end", "Aceptar la verdad de tu propio sueño eterno", "s5_end_tragic", effect = ChoiceEffect(endingTriggered = "end_5_tragic", soundTrigger = "HEARTBEAT"))
            )
        ),
        Scene(
            id = "s5_boiler_room",
            storyId = StoryId.EL_TURNO_NOCHE,
            chapterIndex = 4,
            chapterTitle = "Capítulo 4: La Verdad Oculta",
            speaker = "Protagonista",
            text = "Dentro de la caldera apagada encuentras una caja fuerte oxidada. Adentro están las pólizas de seguro falsificadas y las órdenes del antiguo director para iniciar el incendio intencional en 2014. Tomas los documentos originales intactos.",
            visualType = VisualSceneType.CORRIDOR,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c5_br_expose", "Salir del sanatorio y entregar las pruebas al fiscal de turno", "s5_end_true", effect = ChoiceEffect(endingTriggered = "end_5_true"))
            )
        ),
        Scene(
            id = "s5_peep_404",
            storyId = StoryId.EL_TURNO_NOCHE,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: A través de la Mirilla",
            speaker = "Protagonista",
            text = "Te asomas al vidrio roto. Adentro ves una figura sentada en la cama balanceándose rítmicamente hacia adelante y atrás mientras canta una canción de cuna distorsionada.",
            visualType = VisualSceneType.HOSPITAL_CORRIDOR,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c5_p4_run_booth", "Dar la vuelta y correr a encerrarte en la garita", "s5_lock_booth", effect = ChoiceEffect(dangerDelta = -10, fearDelta = 25))
            )
        ),
        Scene(
            id = "s5_elevator_shaft",
            storyId = StoryId.EL_TURNO_NOCHE,
            chapterIndex = 2,
            chapterTitle = "Capítulo 2: El Pozo de la Torre",
            speaker = "Narrador",
            text = "Alumbras el hueco del ascensor. Los cables cuelgan cortados. Desde el fondo del pozo, diez metros abajo, una docena de ojos blancos te devuelven la mirada desde la oscuridad.",
            visualType = VisualSceneType.HOSPITAL_CORRIDOR,
            ambientSound = AmbientSoundType.SILENCE,
            choices = listOf(
                Choice("c5_es_retreat", "Retroceder antes de que escalen por las vigas", "s5_check_cctv", effect = ChoiceEffect(fearDelta = 35, soundTrigger = "STINGER"))
            ),
            autoGlitch = true
        ),
        // Endings
        Scene(id = "s5_end_good", storyId = StoryId.EL_TURNO_NOCHE, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #21: ALTA MÉDICA\n\nNo caíste en la trampa de las llamadas del pasado. Te mantuviste a salvo hasta las 07:00 AM cuando llegó el sol y el personal diurno. Renunciaste esa misma mañana.", isEndingScene = true, endingId = "end_5_good", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s5_end_bad", storyId = StoryId.EL_TURNO_NOCHE, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #22: LA HABITACIÓN 404\n\nRespondiste al llamado del ala maldita. Ahora tu nombre figura en la ficha de la puerta 404 para el próximo vigilante que acepte el turno noche.", isEndingScene = true, endingId = "end_5_bad", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s5_end_tragic", storyId = StoryId.EL_TURNO_NOCHE, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #23: EL EXPEDIENTE PERDIDO\n\nNunca saliste del sanatorio en 2014. El turno noche fue la última proyección de una mente en coma antes de que la máquina marcara la línea recta.", isEndingScene = true, endingId = "end_5_tragic", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s5_end_secret", storyId = StoryId.EL_TURNO_NOCHE, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #24: LA FRECUENCIA DEL ELECTRO\n\nTe fundiste con los circuitos del edificio. Ahora tu voz habita el conmutador telefónico esperando al próximo incauto.", isEndingScene = true, endingId = "end_5_secret", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s5_end_true", storyId = StoryId.EL_TURNO_NOCHE, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #25: EL SECRETO DEL SANATORIO\n\nLas pruebas de la caldera cerraron el caso policial diez años después. El sanatorio fue demolido y la pesadilla del turno noche terminó para siempre.", isEndingScene = true, endingId = "end_5_true", visualType = VisualSceneType.ENDING_SCREEN)
    )
}
