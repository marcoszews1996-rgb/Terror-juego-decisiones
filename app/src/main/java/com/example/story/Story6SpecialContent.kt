package com.example.story

import com.example.R
import com.example.model.AmbientSoundType
import com.example.model.Choice
import com.example.model.ChoiceEffect
import com.example.model.Clue
import com.example.model.Ending
import com.example.model.EndingBadge
import com.example.model.InventoryItem
import com.example.model.Scene
import com.example.model.StoryId
import com.example.model.VisualSceneType

/**
 * HISTORIA BASADA EN HECHOS REALES:
 * El Incidente del Paso Diátlov (Paso Dyatlov, 1959 - Montes Urales, URSS).
 * 9 jóvenes excursionistas experimentados murieron de forma inexplicable en la ladera de
 * Kholat Syakhl ("Montaña de los Muertos"). Su tienda fue rasgada desde el interior;
 * huyeron semidesnudos y descalzos en la nieve a -30°C; presentaban traumatismos internos severos
 * (fracturas masivas de tórax y cráneo sin heridas externas) y radiación inexplicable.
 */
object Story6SpecialContent {

    val ITEM_DIARY = InventoryItem(
        id = "item_diary_dyatlov",
        name = "Diario de Diátlov (1959)",
        description = "Última anotación manuscrita: «El viento aúlla como una turbina. Hay luces pulsando en la cumbre».",
        icon = "ic_note"
    )

    val ITEM_LANTERN = InventoryItem(
        id = "item_lantern_urals",
        name = "Linterna China Soviética",
        description = "Linterna metálica hallada sobre la nieve intacta pero apagada.",
        icon = "ic_flashlight"
    )

    val CLUE_TENT_CUT = Clue(
        id = "clue_tent_cut",
        storyId = StoryId.EL_TRANSMISOR,
        title = "Lona Cortada Desde el Interior",
        description = "Tres cortes limpios verticales de cuchillo en la tela de la tienda para salir desesperadamente hacia la ventisca.",
        details = "Evidencia forense oficial del perito Churkina",
        icon = "ic_knife"
    )

    val CLUE_ORBS = Clue(
        id = "clue_orbs",
        storyId = StoryId.EL_TRANSMISOR,
        title = "Esferas de Fuego Anómalas",
        description = "Registros de meteorólogos y excursionistas cercanos que avistaron esferas anaranjadas en el cielo esa misma noche.",
        details = "Informe de la fiscalía soviética de Sverdlovsk",
        icon = "ic_eye"
    )

    private const val HISTORICAL_FACT =
        "CASO POLICIAL REAL (Febrero de 1959):\n" +
        "Nueve estudiantes del Instituto Politécnico de los Urales liderados por Ígor Diátlov fallecieron misteriosamente en la ladera de la 'Montaña de los Muertos'. La tienda de campaña fue rajada desde adentro a -30°C. Varios cadáveres tenían fracturas craneales y torácicas comparables a un choque automovilístico brutal sin marcas de golpes externos. El fiscal Lev Ivanov concluyó oficialmente que murieron debido a 'una fuerza elemental irresistible desconocida'."

    val ENDINGS = listOf(
        Ending(
            id = "end_6_good",
            storyId = StoryId.EL_TRANSMISOR,
            number = 26,
            title = "EL REFUGIO DEL CEDRO",
            badge = EndingBadge.BUENO,
            description = "Lograste descender con calma hacia el bosque protegido por los árboles gigantes, manteniendo encendido el fuego comunal hasta la llegada de las patrullas de rescate.",
            quote = "«En la taiga helada, el pánico es más letal que el frío ártico.»",
            newspaperClippingResId = R.drawable.img_newspaper_dyatlov,
            historicalCaseTitle = "CASO REAL: EXPEDICIÓN PASO DIÁTLOV (1959)",
            historicalFactSnippet = HISTORICAL_FACT
        ),
        Ending(
            id = "end_6_bad",
            storyId = StoryId.EL_TRANSMISOR,
            number = 27,
            title = "LA FUERZA ELEMENTAL DESCONOCIDA",
            badge = EndingBadge.MALO,
            description = "Saliste corriendo en calcetines ladera abajo perseguido por el zumbido vibratorio. Tus pulmones colapsaron por la compresión y la onda expansiva invisible.",
            quote = "«El peritaje forense no halló huellas de terceros, solo una fuerza que aplastó el hueso como cáscara de huevo.»",
            newspaperClippingResId = R.drawable.img_newspaper_dyatlov,
            historicalCaseTitle = "CASO REAL: EXPEDICIÓN PASO DIÁTLOV (1959)",
            historicalFactSnippet = HISTORICAL_FACT
        ),
        Ending(
            id = "end_6_tragic",
            storyId = StoryId.EL_TRANSMISOR,
            number = 28,
            title = "CONGELADOS EN EL DESFILADERO",
            badge = EndingBadge.TRAGICO,
            description = "Caíste en la grieta profunda del arroyo tapado por la nieve. El frío paralizó tus músculos mientras intentabas excavar un pozo de nieve para proteger a tus compañeros.",
            quote = "«Fueron hallados cuatro meses después, bajo cuatro metros de hielo primaveral.»",
            newspaperClippingResId = R.drawable.img_newspaper_dyatlov,
            historicalCaseTitle = "CASO REAL: EXPEDICIÓN PASO DIÁTLOV (1959)",
            historicalFactSnippet = HISTORICAL_FACT
        ),
        Ending(
            id = "end_6_secret",
            storyId = StoryId.EL_TRANSMISOR,
            number = 29,
            title = "LAS ESFERAS ANARANJADAS",
            badge = EndingBadge.SECRETO,
            description = "Miraste hacia el cielo y contemplaste las luces pulsantes silenciosas flotando sobre la cumbre. El aire se ionizó con un destello rojizo antes del silencio absoluto.",
            quote = "«El expediente fue clasificado con el sello de secreto de estado por orden de Moscú.»",
            newspaperClippingResId = R.drawable.img_newspaper_dyatlov,
            historicalCaseTitle = "CASO REAL: EXPEDICIÓN PASO DIÁTLOV (1959)",
            historicalFactSnippet = HISTORICAL_FACT
        ),
        Ending(
            id = "end_6_true",
            storyId = StoryId.EL_TRANSMISOR,
            number = 30,
            title = "EL EXPEDIENTE DE SVERDLOVSK",
            badge = EndingBadge.VERDADERO,
            description = "Recuperaste los rollos de película y los diarios del campamento, documentando con precisión los infrasonidos del vórtice de Kárpinski que desataron la locura sensorial colectiva.",
            quote = "«60 años después, el enigma de los Urales sigue desafiando a la ciencia y la historia forense.»",
            newspaperClippingResId = R.drawable.img_newspaper_dyatlov,
            historicalCaseTitle = "CASO REAL: EXPEDICIÓN PASO DIÁTLOV (1959)",
            historicalFactSnippet = HISTORICAL_FACT
        )
    )

    val SCENES = listOf(
        Scene(
            id = "s6_intro",
            storyId = StoryId.EL_TRANSMISOR,
            chapterIndex = 1,
            chapterTitle = "Hecho Real: Kholat Syakhl, 1 de Febrero de 1959",
            speaker = "Diario de Expedición",
            text = "21:30 horas. Cordillera de los Urales del Norte. Estás dentro de la tienda de campaña con los nueve compañeros de la expedición. Afuera, la ventisca ruge a -28°C contra la ladera de la 'Montaña de los Muertos'. De pronto, un zumbido sordo de baja frecuencia hace vibrar la tierra y tus tímpanos con un dolor punzante intolerable.",
            visualType = VisualSceneType.RADIO_HIGHWAY,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c6_inspect_sound", "Asomarte por la entrada principal de la tienda", "s6_tent_entrance", effect = ChoiceEffect(fearDelta = 20, curiosityDelta = 25, soundTrigger = "CREATURE")),
                Choice("c6_take_knife", "Tomar el cuchillo de monte y permanecer alerta", "s6_knife_ready", effect = ChoiceEffect(dangerDelta = 10, fearDelta = 15, itemGained = ITEM_DIARY))
            )
        ),
        Scene(
            id = "s6_knife_ready",
            storyId = StoryId.EL_TRANSMISOR,
            chapterIndex = 1,
            chapterTitle = "Capítulo 1: El Pánico Inexplicable",
            speaker = "Ígor Diátlov",
            text = "El zumbido se convierte en una oscilación física que deforma el aire. Varios compañeros comienzan a ahogarse en seco, gritando con miradas desorbitadas de terror puro. La entrada de la carpa está bloqueada por el peso de la nieve acumulada. Algo gigantesco y pesado desciende ladera abajo.",
            visualType = VisualSceneType.RADIO_HIGHWAY,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c6_slash_tent", "Rasgar la lona con el cuchillo desde adentro para huir", "s6_cut_escape", effect = ChoiceEffect(dangerDelta = 25, fearDelta = 30, clueGained = CLUE_TENT_CUT, soundTrigger = "STINGER")),
                Choice("c6_stay_bunk", "Tratar de calmar al grupo y no abandonar el abrigo", "s6_tent_crush", effect = ChoiceEffect(dangerDelta = 40, fearDelta = 20))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s6_tent_entrance",
            storyId = StoryId.EL_TRANSMISOR,
            chapterIndex = 1,
            chapterTitle = "Capítulo 1: La Noche Roja",
            speaker = "Observador",
            text = "Apartas el faldón de la tienda. El cielo nocturno sobre el monte no es negro: tres esferas luminosas de tono anaranjado flotan suspendidas en silencio absoluto, reflejándose en el hielo. La brújula del bolsillo gira sin control. El aire huele a ozono quemado.",
            visualType = VisualSceneType.RADIO_HIGHWAY,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c6_watch_orbs", "Quedarte paralizado observando las esferas celestes", "s6_orbs_contact", effect = ChoiceEffect(fearDelta = 40, clueGained = CLUE_ORBS, soundTrigger = "GLITCH")),
                Choice("c6_warn_escape", "Gritar al grupo y ordenar el descenso inmediato hacia el bosque", "s6_cut_escape", effect = ChoiceEffect(dangerDelta = 20, fearDelta = 25))
            )
        ),
        Scene(
            id = "s6_cut_escape",
            storyId = StoryId.EL_TRANSMISOR,
            chapterIndex = 2,
            chapterTitle = "Capítulo 2: Descenso en la Ventisca",
            speaker = "Narrador",
            text = "Cortas tres tajos limpios en la pared de lona. Salen expulsados a la intemperie: están sin botas, en medias y ropa térmica. El viento helado corta la piel como hojas de afeitar. A un kilómetro y medio ladera abajo se divisa la silueta del bosque de cedros milenarios.",
            visualType = VisualSceneType.RADIO_HIGHWAY,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c6_run_woods", "Descender a toda prisa hacia el cedro gigante", "s6_cedar_tree", effect = ChoiceEffect(dangerDelta = 10, fearDelta = 20, itemGained = ITEM_LANTERN)),
                Choice("c6_stay_near_tent", "Intentar volver a gatas por los abrigos y botas", "s6_tent_freeze", effect = ChoiceEffect(dangerDelta = 45, fearDelta = 25))
            )
        ),
        Scene(
            id = "s6_cedar_tree",
            storyId = StoryId.EL_TRANSMISOR,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: Bajo el Cedro",
            speaker = "Expedicionarios",
            text = "Llegan al límite de los árboles. Dos compañeros juntan ramas con las manos desnudas y logran encender una precaria fogata bajo las ramas del gran cedro. Las ramas a cinco metros de altura están rotas, como si alguien hubiera intentado trepar desesperadamente para vigilar la cumbre.",
            visualType = VisualSceneType.RADIO_HIGHWAY,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c6_maintain_fire", "Alimentar el fuego y resguardarse entre las raíces", "s6_end_good", effect = ChoiceEffect(endingTriggered = "end_6_good")),
                Choice("c6_ravine_shelter", "Bajar al barranco del arroyo para cavar una cueva de nieve", "s6_ravine_choice", effect = ChoiceEffect(dangerDelta = 20, fearDelta = 30)),
                Choice("c6_return_climb", "Tratar de regresar a la carpa arrastrándote por la nieve", "s6_freeze_slope", effect = ChoiceEffect(dangerDelta = 50, fearDelta = 20))
            )
        ),
        Scene(
            id = "s6_ravine_choice",
            storyId = StoryId.EL_TRANSMISOR,
            chapterIndex = 4,
            chapterTitle = "Capítulo 4: El Lecho del Arroyo",
            speaker = "Narrador",
            text = "Descienden a la hondonada donde la nieve es blanda y profunda. De repente, una fuerza masiva invisible cae como un golpe de presión hidráulica desde arriba. La nieve cede y un impacto demoledor fractura las costillas de tus compañeros sin romperles la piel.",
            visualType = VisualSceneType.RADIO_HIGHWAY,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c6_end_bad_choice", "Gritar contra la oscuridad helada", "s6_end_bad", effect = ChoiceEffect(endingTriggered = "end_6_bad", soundTrigger = "SCREAM_JUMPSCARE")),
                Choice("c6_end_tragic_choice", "Abrazar a los heridos mientras la ventisca los cubre", "s6_end_tragic", effect = ChoiceEffect(endingTriggered = "end_6_tragic"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s6_orbs_contact",
            storyId = StoryId.EL_TRANSMISOR,
            chapterIndex = 4,
            chapterTitle = "Capítulo 4: El Vórtice Silencioso",
            speaker = "Narrador",
            text = "Las esferas descienden a escasos metros. Un silencio antinatural engulle todo sonido de la tormenta. Miras fijamente el núcleo de luz anaranjada antes de que la percepción se disuelva.",
            visualType = VisualSceneType.RADIO_HIGHWAY,
            ambientSound = AmbientSoundType.SILENCE,
            choices = listOf(
                Choice("c6_end_secret_trigger", "Entrar en el resplandor", "s6_end_secret", effect = ChoiceEffect(endingTriggered = "end_6_secret", soundTrigger = "STINGER"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s6_freeze_slope",
            storyId = StoryId.EL_TRANSMISOR,
            chapterIndex = 4,
            chapterTitle = "Capítulo 4: Hacia la Tienda",
            speaker = "Narrador",
            text = "Avanzas 300 metros cuesta arriba enfrentando vientos de 100 km/h. La temperatura cae a -35°C. Caes sobre la nieve con la linterna en mano y la mirada puesta en la tienda a lo lejos. Tus notas en el diario preservan el último testimonio.",
            visualType = VisualSceneType.RADIO_HIGHWAY,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c6_end_true_trigger", "Cerrar los ojos con el cuaderno en el pecho", "s6_end_true", effect = ChoiceEffect(endingTriggered = "end_6_true"))
            )
        ),
        // Endings scenes
        Scene(id = "s6_end_good", storyId = StoryId.EL_TRANSMISOR, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #26: EL REFUGIO DEL CEDRO\n\nLograste mantener viva la llama comunal hasta el amanecer ártico.", isEndingScene = true, endingId = "end_6_good", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s6_end_bad", storyId = StoryId.EL_TRANSMISOR, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #27: LA FUERZA ELEMENTAL DESCONOCIDA\n\nEl impacto brutal sin heridas externas selló el destino de la expedición.", isEndingScene = true, endingId = "end_6_bad", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s6_end_tragic", storyId = StoryId.EL_TRANSMISOR, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #28: CONGELADOS EN EL DESFILADERO\n\nLa nieve cubrió los cuerpos en el fondo del arroyo durante cuatro meses.", isEndingScene = true, endingId = "end_6_tragic", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s6_end_secret", storyId = StoryId.EL_TRANSMISOR, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #29: LAS ESFERAS ANARANJADAS\n\nUn fenómeno aéreo no identificado que los archivos clasificados ocultaron por 60 años.", isEndingScene = true, endingId = "end_6_secret", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s6_end_true", storyId = StoryId.EL_TRANSMISOR, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #30: EL EXPEDIENTE DE SVERDLOVSK\n\nLos registros forenses preservaron el mayor misterio no resuelto del siglo XX.", isEndingScene = true, endingId = "end_6_true", visualType = VisualSceneType.ENDING_SCREEN)
    )
}
