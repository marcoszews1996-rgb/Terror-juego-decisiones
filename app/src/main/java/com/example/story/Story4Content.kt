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

object Story4Content {

    val ITEM_TRAIN_TICKET = InventoryItem("item_ticket", "Boleto Picado", "Boleto magnético amarillo. El sello tiene una fecha ilegible y marcas de hollín.", "ic_receipt")
    val ITEM_EMERGENCY_KEY = InventoryItem("item_train_key", "Llave Triangular de Emergencia", "Llave metálica de ferrocarril encontrada en la caja del extintor.", "ic_key")
    val ITEM_NEWSPAPER = InventoryItem("item_newspaper", "Diario de la Mañana", "Un ejemplar del periódico de hoy aún húmedo por la tinta.", "ic_note")

    val CLUE_GHOST_STATION = Clue("clue_ghost_station", StoryId.EL_ULTIMO_TREN, "Estación Desconocida", "El mapa lumínico del vagón enciende luces en estaciones que no figuran en ninguna guía oficial.", "Letrero digital", "ic_search")
    val CLUE_OBITUARY = Clue("clue_obituary", StoryId.EL_ULTIMO_TREN, "Obituario en el Periódico", "En la página 14 hay una fotografía tuya con el título: 'Búsqueda de sobrevivientes en el túnel norte'.", "Diario encontrado", "ic_info")

    val ENDINGS = listOf(
        Ending("end_4_good", StoryId.EL_ULTIMO_TREN, 16, "ESTACIÓN TERMINAL", EndingBadge.BUENO, "Accionaste el freno neumático auxiliar y saltaste al andén de servicio antes del túnel profundo.", "«El aire frío del exterior nunca supo tan dulce.»"),
        Ending("end_4_bad", StoryId.EL_ULTIMO_TREN, 17, "BOLETO SIN RETORNO", EndingBadge.MALO, "Le entregaste tu billete al revisor. Al perforarlo, tu reflejo en la ventanilla se desvaneció para siempre.", "«Próxima parada: ninguna parte.»"),
        Ending("end_4_tragic", StoryId.EL_ULTIMO_TREN, 18, "NOTICIA DE MAÑANA", EndingBadge.TRAGICO, "Terminaste de leer el periódico: el descarrilamiento ocurrió hace media hora. Eres un eco en los restos metálicos.", "«Nadie sobrevive a lo que ya sucedió.»"),
        Ending("end_4_secret", StoryId.EL_ULTIMO_TREN, 19, "LA LÍNEA FANTASMA", EndingBadge.SECRETO, "Llegaste a la cabina del maquinista: los controles conducían hacia un abismo de luces espectrales.", "«Las vías se bifurcan donde la realidad termina.»"),
        Ending("end_4_true", StoryId.EL_ULTIMO_TREN, 20, "EL CAMBIO DE VÍAS", EndingBadge.VERDADERO, "Conectaste la llave triangular al panel de derivación manual y redirigiste el convoy a la red urbana.", "«La pesadilla subterránea quedó sellada tras las compuertas.»")
    )

    val SCENES = listOf(
        Scene(
            id = "s4_intro",
            storyId = StoryId.EL_ULTIMO_TREN,
            chapterIndex = 1,
            chapterTitle = "Capítulo 1: El Andén Cero",
            speaker = "Narrador",
            text = "00:45 AM. Bajas las escaleras húmedas de la estación subterránea. El andén está desierto bajo tubos fluorescentes que zumban con interferencia eléctrica. Un convoy antiguo frena con chirridos metálicos insoportables. Las puertas se abren expulsando una ráfaga de aire helado.",
            visualType = VisualSceneType.SUBWAY_PLATFORM,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c4_enter_front", "Subir al primer vagón cerca de la cabina", "s4_vagon_front", effect = ChoiceEffect(fearDelta = 5, curiosityDelta = 10)),
                Choice("c4_enter_back", "Subir al último vagón para viajar solo", "s4_vagon_back", effect = ChoiceEffect(fearDelta = 15, dangerDelta = 10, itemGained = ITEM_TRAIN_TICKET)),
                Choice("c4_wait_next", "Dudar y retroceder hacia la salida", "s4_hesitate_exit", effect = ChoiceEffect(fearDelta = 20, soundTrigger = "KNOCK"))
            )
        ),
        Scene(
            id = "s4_hesitate_exit",
            storyId = StoryId.EL_ULTIMO_TREN,
            chapterIndex = 1,
            chapterTitle = "Capítulo 1: Sin Salida",
            speaker = "Narrador",
            text = "Das media vuelta para volver a la calle, pero la reja metálica de la entrada de la estación ha bajado sola con un estruendo pesado. Un candado oxidado sella el acceso. La única luz que queda proviene del interior del tren.",
            visualType = VisualSceneType.SUBWAY_PLATFORM,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c4_he_board", "No queda otra opción: subir al vagón antes de que cierre puertas", "s4_vagon_back", effect = ChoiceEffect(fearDelta = 25, dangerDelta = 15, soundTrigger = "STINGER"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s4_vagon_back",
            storyId = StoryId.EL_ULTIMO_TREN,
            chapterIndex = 2,
            chapterTitle = "Capítulo 2: Pasajeros Silenciosos",
            speaker = "Protagonista",
            text = "Las puertas se cierran de golpe. El tren arranca acelerando a una velocidad inusual. Miras a tu alrededor: en los asientos opuestos hay tres personas sentadas con abrigos oscuros. Ninguna de ellas se mueve, ninguna pestañea. La pantalla de paradas parpadea en rojo:\n«PARADA ACTUAL: ??? — PRÓXIMA: ABISMO»",
            visualType = VisualSceneType.SUBWAY_PLATFORM,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c4_vb_msg", "Mirar tu teléfono que empieza a vibrar intensamente", "s4_phone_conductor", effect = ChoiceEffect(soundTrigger = "RING", phoneNotification = PhoneNotification("CHAT//", "«No mires a los pasajeros a los ojos.»", "Guarda"))),
                Choice("c4_vb_approach_passenger", "Acercarte a hablar con la mujer del periódico", "s4_passenger_newspaper", effect = ChoiceEffect(fearDelta = 25, curiosityDelta = 30, soundTrigger = "FOOTSTEP")),
                Choice("c4_vb_pull_brake", "Tirar de la palanca roja de emergencia en el techo", "s4_emergency_handle", effect = ChoiceEffect(dangerDelta = 20, fearDelta = 20, soundTrigger = "STINGER"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s4_vagon_front",
            storyId = StoryId.EL_ULTIMO_TREN,
            chapterIndex = 2,
            chapterTitle = "Capítulo 2: La Puerta Blindada",
            speaker = "Narrador",
            text = "El primer coche huele a ozono quemado y cables recalentados. La puerta que comunica con la cabina del maquinista es de hierro macizo, pero tiene una ventanilla redonda. Te asomas: el asiento del conductor está vacío, pero los aceleradores avanzan por sí mismos con chirridos rítmicos.",
            visualType = VisualSceneType.SUBWAY_PLATFORM,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c4_vf_inspect_panel", "Revisar el panel de fusibles junto a la puerta", "s4_inspect_fuses", effect = ChoiceEffect(evidenceDelta = 20, itemGained = ITEM_EMERGENCY_KEY, clueGained = CLUE_GHOST_STATION)),
                Choice("c4_vf_back_cars", "Regresar asustado hacia los vagones traseros", "s4_vagon_back", effect = ChoiceEffect(fearDelta = 20, dangerDelta = 10))
            )
        ),
        Scene(
            id = "s4_phone_conductor",
            storyId = StoryId.EL_ULTIMO_TREN,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: La Advertencia del Guarda",
            speaker = "CHAT//",
            text = "Un número con prefijo interno de ferrocarriles te envía un mensaje urgente:\n\n«El revisor viene por el pasillo. No tiene ojos pero escucha el latido de tu pecho. Si te pide el boleto, salta al baño o serás parte del convoy para siempre.»",
            visualType = VisualSceneType.SUBWAY_PLATFORM,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c4_pc_bathroom", "Correr al cubículo del baño y trabar el cerrojo", "s4_bathroom_hide", effect = ChoiceEffect(fearDelta = 30, dangerDelta = -10, soundTrigger = "HEARTBEAT")),
                Choice("c4_pc_stay", "Quedarte en el asiento con el boleto en la mano", "s4_conductor_encounter", effect = ChoiceEffect(dangerDelta = 40, soundTrigger = "STINGER"))
            )
        ),
        Scene(
            id = "s4_passenger_newspaper",
            storyId = StoryId.EL_ULTIMO_TREN,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: El Periódico Húmedo",
            speaker = "Protagonista",
            text = "Te paras frente a la pasajera. No responde. Miras el diario en su regazo: la fecha es de hoy. En la página central hay una foto tuya en blanco y negro: «Tragedia en la Línea Subterránea: Desaparición de pasajero en el túnel abandonado a las 00:45».",
            visualType = VisualSceneType.SUBWAY_PLATFORM,
            ambientSound = AmbientSoundType.SILENCE,
            choices = listOf(
                Choice("c4_pn_take_paper", "Tomar el periódico temblando para leer los detalles", "s4_read_tragic", effect = ChoiceEffect(evidenceDelta = 40, itemGained = ITEM_NEWSPAPER, clueGained = CLUE_OBITUARY)),
                Choice("c4_pn_run_away", "Apartarte aterrorizado y buscar una salida", "s4_vagon_front", effect = ChoiceEffect(fearDelta = 35, dangerDelta = 20))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s4_bathroom_hide",
            storyId = StoryId.EL_ULTIMO_TREN,
            chapterIndex = 4,
            chapterTitle = "Capítulo 4: El Cubículo de Acero",
            speaker = "Narrador",
            text = "Te encierras en el baño. Los pasos pesados del revisor se detienen justo frente a la puerta delgada. Escuchas el chasquido metálico de su perforadora de boletos... y una respiración ronca que roza la rendija del picaporte. Afuera, la velocidad del tren disminuye por una curva cerrada.",
            visualType = VisualSceneType.CORRIDOR,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c4_bh_window", "Forzar la pequeña ventanilla de ventilación para saltar al andén", "s4_jump_platform", effect = ChoiceEffect(dangerDelta = 30, soundTrigger = "HEARTBEAT")),
                Choice("c4_bh_key_switch", "Usar la llave triangular en la válvula de desvío del baño", "s4_switch_tracks", requiredItemId = "item_train_key", effect = ChoiceEffect(evidenceDelta = 30))
            )
        ),
        Scene(
            id = "s4_conductor_encounter",
            storyId = StoryId.EL_ULTIMO_TREN,
            chapterIndex = 4,
            chapterTitle = "Capítulo 4: El Revisor",
            speaker = "Narrador",
            text = "Una figura alta vestida con uniforme de gala descolorido se detiene a tu lado. Debajo de la gorra no hay rostro: solo una niebla oscura donde dos puntos de luz roja titilan. Estira una mano enguantada esperando tu billete.",
            visualType = VisualSceneType.SUBWAY_PLATFORM,
            ambientSound = AmbientSoundType.SILENCE,
            choices = listOf(
                Choice("c4_ce_give_ticket", "Entregarle el boleto picado", "s4_end_bad", effect = ChoiceEffect(endingTriggered = "end_4_bad", soundTrigger = "STINGER")),
                Choice("c4_ce_fight", "Empujarlo y correr hacia la cabina del maquinista", "s4_cabin_rush", effect = ChoiceEffect(fearDelta = 40, dangerDelta = 30, soundTrigger = "HEARTBEAT"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s4_emergency_handle",
            storyId = StoryId.EL_ULTIMO_TREN,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: Freno Manual",
            speaker = "Narrador",
            text = "Tiras con todas tus fuerzas de la palanca roja. Chispas incandescentes iluminan la penumbra del coche. Las ruedas rechinan bloqueándose con un alarido de metal. El tren se detiene a escasos metros de la boca de un túnel tenebroso.",
            visualType = VisualSceneType.SUBWAY_PLATFORM,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c4_eh_force_door", "Hacer palanca en las puertas y saltar al andén", "s4_end_good", effect = ChoiceEffect(endingTriggered = "end_4_good"))
            )
        ),
        Scene(
            id = "s4_read_tragic",
            storyId = StoryId.EL_ULTIMO_TREN,
            chapterIndex = 4,
            chapterTitle = "Capítulo 4: La Crónica",
            speaker = "Protagonista",
            text = "Lees la noticia con lágrimas en los ojos: la colisión del tren con un muro de contención ocurrió hace 45 minutos. Los bomberos trabajan en la remoción. Te tocas el pecho y no sientes latidos. El vagón comienza a disolverse en cenizas.",
            visualType = VisualSceneType.SUBWAY_PLATFORM,
            ambientSound = AmbientSoundType.SILENCE,
            choices = listOf(
                Choice("c4_rt_accept", "Dejar caer el periódico y cerrar los ojos", "s4_end_tragic", effect = ChoiceEffect(endingTriggered = "end_4_tragic", soundTrigger = "HEARTBEAT"))
            )
        ),
        Scene(
            id = "s4_cabin_rush",
            storyId = StoryId.EL_ULTIMO_TREN,
            chapterIndex = 4,
            chapterTitle = "Capítulo 4: Hacia el Vacío",
            speaker = "Narrador",
            text = "Irrumpes en la cabina del maquinista. Frente a ti no hay túneles de hormigón: el tren viaja flotando sobre un vacío cósmico de luces violetas y frecuencias electrónicas. Los indicadores marcan velocidades infinitas.",
            visualType = VisualSceneType.SUBWAY_PLATFORM,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c4_cr_take_controls", "Tocar los controles luminosos", "s4_end_secret", effect = ChoiceEffect(endingTriggered = "end_4_secret", soundTrigger = "GLITCH"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s4_jump_platform",
            storyId = StoryId.EL_ULTIMO_TREN,
            chapterIndex = 5,
            chapterTitle = "Capítulo 5: Escape al Andén",
            speaker = "Narrador",
            text = "Te escurres por la ventanilla y caes rodando sobre el cemento mojado de un andén de servicio. El convoy fantasma continúa su marcha hacia la oscuridad del túnel hasta desvanecerse en el silencio.",
            visualType = VisualSceneType.SUBWAY_PLATFORM,
            ambientSound = AmbientSoundType.RAIN_STREET,
            choices = listOf(
                Choice("c4_jp_end", "Caminar hacia las luces de la superficie", "s4_end_good", effect = ChoiceEffect(endingTriggered = "end_4_good"))
            )
        ),
        Scene(
            id = "s4_switch_tracks",
            storyId = StoryId.EL_ULTIMO_TREN,
            chapterIndex = 5,
            chapterTitle = "Capítulo 5: El Mecanismo Manual",
            speaker = "Protagonista",
            text = "Giras la válvula triangular en el panel secundario. Un rugido neumático resuena bajo el piso. Las vías cambian mecánicamente de posición y el tren emerge a toda velocidad por la rampa hacia la estación central iluminada.",
            visualType = VisualSceneType.SUBWAY_PLATFORM,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c4_st_end", "Abrir las compuertas y salir al andén concurrido", "s4_end_true", effect = ChoiceEffect(endingTriggered = "end_4_true"))
            )
        ),
        // Endings
        Scene(id = "s4_end_good", storyId = StoryId.EL_ULTIMO_TREN, chapterIndex = 6, chapterTitle = "Final Desbloqueado", text = "FINAL #16: ESTACIÓN TERMINAL\n\nLograste escapar antes del túnel sin retorno. Subiste las escaleras y respiraste el aire fresco de la ciudad. El tren 00:45 nunca volvió a figurar en los horarios.", isEndingScene = true, endingId = "end_4_good", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s4_end_bad", storyId = StoryId.EL_ULTIMO_TREN, chapterIndex = 6, chapterTitle = "Final Desbloqueado", text = "FINAL #17: BOLETO SIN RETORNO\n\nTu boleto fue marcado con la fecha de la eternidad. Ahora viajas sentado en la penumbra del vagón junto a los demás que nunca llegaron a su destino.", isEndingScene = true, endingId = "end_4_bad", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s4_end_tragic", storyId = StoryId.EL_ULTIMO_TREN, chapterIndex = 6, chapterTitle = "Final Desbloqueado", text = "FINAL #18: NOTICIA DE MAÑANA\n\nLa muerte ocurrió antes de que abordaras. El viaje en tren fue solo el tránsito de una mente que se resistía a aceptar su trágico desenlace.", isEndingScene = true, endingId = "end_4_tragic", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s4_end_secret", storyId = StoryId.EL_ULTIMO_TREN, chapterIndex = 6, chapterTitle = "Final Desbloqueado", text = "FINAL #19: LA LÍNEA FANTASMA\n\nTomaste el mando del convoy y cruzaste la frontera de la realidad subterránea. Te convertiste en el nuevo maquinista de la línea espectral.", isEndingScene = true, endingId = "end_4_secret", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s4_end_true", storyId = StoryId.EL_ULTIMO_TREN, chapterIndex = 6, chapterTitle = "Final Desbloqueado", text = "FINAL #20: EL CAMBIO DE VÍAS\n\nComprendiste el mecanismo de las vías y desactivaste la trampa temporal. El convoy volvió a la superficie y las autoridades clausuraron definitivamente el túnel.", isEndingScene = true, endingId = "end_4_true", visualType = VisualSceneType.ENDING_SCREEN)
    )
}
