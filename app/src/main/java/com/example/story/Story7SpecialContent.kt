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
 * El Asesinato Masivo de Hinterkaifeck (31 de Marzo de 1922 - Baviera, Alemania).
 * Seis personas (la familia Gruber y su nueva empleada) fueron brutalmente asesinadas en una granja aislada.
 * Días antes, el patriarca Andreas Gruber halló huellas en la nieve fresca que venían del bosque hacia
 * la granja, pero NINGUNA que saliera. Se escuchaban pasos crujir en el ático por las noches.
 * El asesino permaneció varios días viviendo en la casa junto a los cadáveres, alimentando al ganado y comiendo en la cocina.
 */
object Story7SpecialContent {

    val ITEM_KEYRING = InventoryItem(
        id = "item_keyring_gruber",
        name = "Manojo de Llaves Perdido",
        description = "El manojo de llaves de la casa que desapareció misteriosamente hace tres días.",
        icon = "ic_key"
    )

    val ITEM_LANTERN = InventoryItem(
        id = "item_farm_lantern",
        name = "Farol de Queroseno",
        description = "Farol de latón utilizado para cruzar el patio oscuro hacia el establo.",
        icon = "ic_flashlight"
    )

    val CLUE_FOOTPRINTS = Clue(
        id = "clue_footprints_snow",
        storyId = StoryId.LA_CABANA,
        title = "Huellas que Llegan, Ninguna que Sale",
        description = "Rastros de botas en la nieve fresca que van desde el espeso pinar directo a la puerta trasera.",
        details = "Testimonio registrado por los vecinos de Gröbern en 1922",
        icon = "ic_footprint"
    )

    val CLUE_ATTIC_HAY = Clue(
        id = "clue_attic_hay",
        storyId = StoryId.LA_CABANA,
        title = "El Nido en el Heno",
        description = "Paja aplastada en el ático donde alguien durmió observando a la familia a través de las tablas del suelo.",
        details = "Informe forense del inspector Reingruber",
        icon = "ic_eye"
    )

    private const val HISTORICAL_FACT =
        "CASO POLICIAL REAL (31 de Marzo de 1922):\n" +
        "En la granja aislada de Hinterkaifeck, Baviera, seis personas fueron asesinadas con una piqueta de labranza. Días antes, Andreas Gruber escuchó pisadas en el ático y halló huellas en la nieve que entraban pero nunca salían. El homicida vivió en el ático varios días después de la matanza, alimentó a las vacas y encendió fuego en la cocina mientras los cuerpos yacían cubiertos de heno. El caso continúa sin resolver tras más de un siglo."

    val ENDINGS = listOf(
        Ending(
            id = "end_7_good",
            storyId = StoryId.LA_CABANA,
            number = 31,
            title = "HUIDA AL AMANECER DE GRÖBERN",
            badge = EndingBadge.BUENO,
            description = "Escuchaste los crujidos en el techo, no saliste al patio oscuro y escapaste a través del trigal hacia el pueblo vecino para alertar a las autoridades antes de la medianoche.",
            quote = "«La desconfianza fue el único cerrojo que te salvó de la piqueta de Hinterkaifeck.»",
            newspaperClippingResId = R.drawable.img_newspaper_hinterkaifeck,
            historicalCaseTitle = "CASO REAL: EL CRIMEN DE HINTERKAIFECK (1922)",
            historicalFactSnippet = HISTORICAL_FACT
        ),
        Ending(
            id = "end_7_bad",
            storyId = StoryId.LA_CABANA,
            number = 32,
            title = "EL CEBO DEL ESTABLO",
            badge = EndingBadge.MALO,
            description = "Atraído por el mugido angustiado de las vacas, cruzaste el umbral del establo en tinieblas. Una silueta descendió silenciosamente con el azadón en alto.",
            quote = "«Fueron llamados uno a uno hacia el establo; ninguno logró pedir auxilio.»",
            newspaperClippingResId = R.drawable.img_newspaper_hinterkaifeck,
            historicalCaseTitle = "CASO REAL: EL CRIMEN DE HINTERKAIFECK (1922)",
            historicalFactSnippet = HISTORICAL_FACT
        ),
        Ending(
            id = "end_7_tragic",
            storyId = StoryId.LA_CABANA,
            number = 33,
            title = "BAJO EL HENO ENSANGRENTADO",
            badge = EndingBadge.TRAGICO,
            description = "Te escondiste en el granero conteniendo la respiración mientras el intruso cerraba con pestillo las puertas exteriores desde afuera.",
            quote = "«Durante cuatro días la chimenea de la casa humeó, mientras el asesino cenaba en soledad.»",
            newspaperClippingResId = R.drawable.img_newspaper_hinterkaifeck,
            historicalCaseTitle = "CASO REAL: EL CRIMEN DE HINTERKAIFECK (1922)",
            historicalFactSnippet = HISTORICAL_FACT
        ),
        Ending(
            id = "end_7_secret",
            storyId = StoryId.LA_CABANA,
            number = 34,
            title = "EL HUÉSPED DE LAS VIGAS",
            badge = EndingBadge.SECRETO,
            description = "Subiste sigilosamente al ático con el farol: hallaste un plato con restos de comida caliente y dos agujeros en el piso alineados exactamente sobre tu cama.",
            quote = "«Llevaba semanas mirándote dormir a través de las rendijas.»",
            newspaperClippingResId = R.drawable.img_newspaper_hinterkaifeck,
            historicalCaseTitle = "CASO REAL: EL CRIMEN DE HINTERKAIFECK (1922)",
            historicalFactSnippet = HISTORICAL_FACT
        ),
        Ending(
            id = "end_7_true",
            storyId = StoryId.LA_CABANA,
            number = 35,
            title = "EL EXPEDIENTE DE MÚNICH (1922)",
            badge = EndingBadge.VERDADERO,
            description = "Recopilaste las huellas de calzado y el periódico foráneo de Múnich, entregando la pista definitiva a la policía de homicidios antes del crimen.",
            quote = "«El misterio más perturbador del crimen rural europeo perdura en los archivos policiales.»",
            newspaperClippingResId = R.drawable.img_newspaper_hinterkaifeck,
            historicalCaseTitle = "CASO REAL: EL CRIMEN DE HINTERKAIFECK (1922)",
            historicalFactSnippet = HISTORICAL_FACT
        )
    )

    val SCENES = listOf(
        Scene(
            id = "s7_intro",
            storyId = StoryId.LA_CABANA,
            chapterIndex = 1,
            chapterTitle = "Hecho Real: Granja Hinterkaifeck, 31 de Marzo de 1922",
            speaker = "Narrador",
            text = "21:00 horas. La noche cae sobre la solitaria granja de Hinterkaifeck, a un kilómetro de los bosques bávaros. Hace dos días que Andreas Gruber camina intranquilo: halló huellas de botas en la nieve que venían del bosque hacia la casa, pero ninguna que saliera. Hace una hora se escucharon ruidos de pisadas lentas sobre el techo de madera.",
            visualType = VisualSceneType.FOREST_CABIN,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c7_attic_ladder", "Subir con el farol a inspeccionar el ático", "s7_attic_climb", effect = ChoiceEffect(fearDelta = 25, curiosityDelta = 30, soundTrigger = "FOOTSTEP")),
                Choice("c7_check_barn", "Asegurar las puertas del establo y revisar el ganado", "s7_barn_entrance", effect = ChoiceEffect(dangerDelta = 20, fearDelta = 15, itemGained = ITEM_LANTERN))
            )
        ),
        Scene(
            id = "s7_attic_climb",
            storyId = StoryId.LA_CABANA,
            chapterIndex = 1,
            chapterTitle = "Capítulo 1: Crujidos en la Paja",
            speaker = "Explorador",
            text = "Asciendes por la vieja escalera de mano. El aire huele a polvo seco y sudor humano reciente. Alumbras las vigas del techo: el heno está acomodado en forma de cama. Hay colillas de tabaco recién apagadas y un diario de Múnich que nadie en la granja compró.",
            visualType = VisualSceneType.FOREST_CABIN,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c7_inspect_hay", "Examinar el lecho clandestino en el heno", "s7_hay_nest", effect = ChoiceEffect(fearDelta = 35, clueGained = CLUE_ATTIC_HAY, soundTrigger = "STINGER")),
                Choice("c7_retreat_down", "Bajar rápidamente en silencio y trabar la trampilla", "s7_barricade_house", effect = ChoiceEffect(fearDelta = 20, dangerDelta = -10))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s7_barn_entrance",
            storyId = StoryId.LA_CABANA,
            chapterIndex = 2,
            chapterTitle = "Capítulo 2: El Eco del Ganado",
            speaker = "Narrador",
            text = "Cruzas el patio embarrado. La nieve alrededor de los corrales tiene surcos frescos. El perro guardián, que siempre ladraba furioso a los extraños, está atado dentro del cobertizo y tiene el hocico ensangrentado. Desde el fondo del establo de piedras, una vaca muge con insistencia espeluznante.",
            visualType = VisualSceneType.FOREST_CABIN,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c7_enter_dark_barn", "Entrar al establo a tientas para ver qué le pasa al ganado", "s7_barn_ambush", effect = ChoiceEffect(dangerDelta = 40, fearDelta = 30, soundTrigger = "CREATURE")),
                Choice("c7_run_to_village", "Ignorar el establo y correr por el camino vecinal hacia Gröbern", "s7_flee_woods", effect = ChoiceEffect(dangerDelta = -20, fearDelta = 25, clueGained = CLUE_FOOTPRINTS))
            )
        ),
        Scene(
            id = "s7_hay_nest",
            storyId = StoryId.LA_CABANA,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: Dos Ojos en el Suelo",
            speaker = "Narrador",
            text = "Te arrodillas junto al heno aplastado. Retiras unas briznas de paja y descubres dos agujeros taladrados a mano en las tablas del piso. Al asomarte, ves directamente la cama matrimonial de la habitación inferior. Un aliento cálido roza tu nuca desde la viga superior.",
            visualType = VisualSceneType.FOREST_CABIN,
            ambientSound = AmbientSoundType.SILENCE,
            choices = listOf(
                Choice("c7_secret_confront", "Girar lentamente el farol hacia arriba", "s7_end_secret", effect = ChoiceEffect(endingTriggered = "end_7_secret", soundTrigger = "SCREAM_JUMPSCARE"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s7_barricade_house",
            storyId = StoryId.LA_CABANA,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: La Casa Cerrada",
            speaker = "Narrador",
            text = "Trancan las puertas de la casona con maderas. Desde afuera se escucha cómo alguien intenta introducir una llave en la cerradura principal con suavidad metálica: CLICK... CLICK. Alguien que tiene el manojo que se perdió hace tres días.",
            visualType = VisualSceneType.FOREST_CABIN,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c7_flee_window", "Escapar por la ventana trasera que da al sembradío", "s7_flee_woods", effect = ChoiceEffect(dangerDelta = -15, itemGained = ITEM_KEYRING)),
                Choice("c7_hide_cellar", "Refugiarte bajo la alacena de embutidos", "s7_end_tragic", effect = ChoiceEffect(endingTriggered = "end_7_tragic", soundTrigger = "STINGER"))
            )
        ),
        Scene(
            id = "s7_barn_ambush",
            storyId = StoryId.LA_CABANA,
            chapterIndex = 4,
            chapterTitle = "Capítulo 4: El Pasillo de Paja",
            speaker = "Narrador",
            text = "Avanzas entre los establos de madera. El farol proyecta sombras gigantescas. Entre las sombras del fondo, un brazo robusto empuña la piqueta de labranza. No hay palabras, solo el silbido del acero cortando el aire.",
            visualType = VisualSceneType.FOREST_CABIN,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c7_end_bad_trigger", "Levantar los brazos en defensa desesperada", "s7_end_bad", effect = ChoiceEffect(endingTriggered = "end_7_bad", soundTrigger = "SCREAM_JUMPSCARE"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s7_flee_woods",
            storyId = StoryId.LA_CABANA,
            chapterIndex = 4,
            chapterTitle = "Capítulo 4: El Sendero Helado",
            speaker = "Narrador",
            text = "Corres sin mirar atrás por el sendero cubierto de nieve. Las luces de las granjas de Gröbern brillan a lo lejos. Llegas exhausto tocando las campanas del campanario parroquial para pedir ayuda armada.",
            visualType = VisualSceneType.FOREST_CABIN,
            ambientSound = AmbientSoundType.RAIN_STREET,
            choices = listOf(
                Choice("c7_end_good_trigger", "Guiar a los gendarmes de regreso a la granja", "s7_end_good", effect = ChoiceEffect(endingTriggered = "end_7_good")),
                Choice("c7_end_true_trigger", "Entregar las huellas y documentos a la fiscalía de Múnich", "s7_end_true", effect = ChoiceEffect(endingTriggered = "end_7_true"))
            )
        ),
        // Ending Scenes
        Scene(id = "s7_end_good", storyId = StoryId.LA_CABANA, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #31: HUIDA AL AMANECER DE GRÖBERN\n\nTu intuición quebró el ciclo del asesino de la piqueta de Hinterkaifeck.", isEndingScene = true, endingId = "end_7_good", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s7_end_bad", storyId = StoryId.LA_CABANA, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #32: EL CEBO DEL ESTABLO\n\nEl silencio de la granja se cobró otra víctima en la oscuridad del heno.", isEndingScene = true, endingId = "end_7_bad", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s7_end_tragic", storyId = StoryId.LA_CABANA, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #33: BAJO EL HENO ENSANGRENTADO\n\nDurante cuatro días nadie supo lo que ocurría en el interior de los muros.", isEndingScene = true, endingId = "end_7_tragic", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s7_end_secret", storyId = StoryId.LA_CABANA, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #34: EL HUÉSPED DE LAS VIGAS\n\nDescubriste la mirada del hombre que vivió en tu propio techo sin ser visto.", isEndingScene = true, endingId = "end_7_secret", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s7_end_true", storyId = StoryId.LA_CABANA, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #35: EL EXPEDIENTE DE MÚNICH (1922)\n\nLas pruebas preservaron el mayor misterio sin resolver de la criminología bávara.", isEndingScene = true, endingId = "end_7_true", visualType = VisualSceneType.ENDING_SCREEN)
    )
}
