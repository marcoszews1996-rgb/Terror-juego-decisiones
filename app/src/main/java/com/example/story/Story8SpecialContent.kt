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
 * La Desaparición de los Fareros de las Islas Flannan (Diciembre de 1900 - Escocia).
 * Tres guardianes experimentados (James Ducat, Thomas Marshall y Donald MacArthur) desaparecieron sin
 * dejar rastro del faro situado en la solitaria isla de Eilean Mòr.
 * En la cocina hallaron la mesa servida, una silla volcada, el reloj parado y el diario de bitácora
 * con anotaciones sobre veteranos marineros llorando por una terrible tormenta divina...
 * aunque los registros meteorológicos de toda Escocia demostraron que el mar estuvo en calma absoluta.
 */
object Story8SpecialContent {

    val ITEM_LOGBOOK = InventoryItem(
        id = "item_flannan_log",
        name = "Bitácora de Eilean Mòr (1900)",
        description = "Última anotación: «15 de Diciembre: La tormenta ha cesado. El mar está en calma. Dios está sobre todas las cosas».",
        icon = "ic_note"
    )

    val ITEM_BRASS_KEY = InventoryItem(
        id = "item_flannan_key",
        name = "Llave Maestra de la Linterna",
        description = "Llave de bronce que abre la cúpula giratoria del faro a 45 metros de altura.",
        icon = "ic_key"
    )

    val CLUE_STOPPED_CLOCK = Clue(
        id = "clue_flannan_clock",
        storyId = StoryId.EL_ESPEJO,
        title = "Reloj Detenido a las 09:12",
        description = "El péndulo de la cocina se detuvo exactamente a las nueve y doce minutos. La tetera estaba fría sobre el fuego apagado.",
        details = "Informe oficial del superintendente Robert Muirhead",
        icon = "ic_info"
    )

    val CLUE_OILSKINS = Clue(
        id = "clue_flannan_oilskins",
        storyId = StoryId.EL_ESPEJO,
        title = "El Traje Abandonado",
        description = "Dos impermeables faltaban en el perchero; el tercer traje de agua quedó colgado, indicando que uno de los tres salió sin abrigo bajo la noche.",
        details = "Registro del capitán Harvie del buque Hesperus",
        icon = "ic_box"
    )

    private const val HISTORICAL_FACT =
        "CASO HISTÓRICO REAL (Diciembre de 1900):\n" +
        "El buque de suministros Hesperus arribó a la desolada isla de Eilean Mòr y encontró el faro apagado y desierto. Ningún cuerpo fue encontrado jamás. El diario de bitácora relataba que fareros curtidos lloraban y rezaban por una 'tormenta imposible', a pesar de que el clima en las islas británicas estuvo sereno. En la cocina se halló una silla volcada y la puerta de entrada cerrada con pestillo."

    val ENDINGS = listOf(
        Ending(
            id = "end_8_good",
            storyId = StoryId.EL_ESPEJO,
            number = 36,
            title = "LA SEÑAL DEL HESPERUS",
            badge = EndingBadge.BUENO,
            description = "Subiste a la cúpula, encendiste la mecha de queroseno de emergencia e hiciste girar los prismas ópticos, guiando al barco de auxilio antes de ser arrastrado por la marea.",
            quote = "«La luz brilló una vez más en las Hébridas, desafiando a las aguas oscuras.»",
            newspaperClippingResId = R.drawable.img_newspaper_flannan,
            historicalCaseTitle = "CASO REAL: EL FARO DE FLANNAN (1900)",
            historicalFactSnippet = HISTORICAL_FACT
        ),
        Ending(
            id = "end_8_bad",
            storyId = StoryId.EL_ESPEJO,
            number = 37,
            title = "EL ABISMO DEL EMBARCADERO OESTE",
            badge = EndingBadge.MALO,
            description = "Descendiste los 160 escalones esculpidos en la roca hacia el embarcadero. Una ola gigantesca anómala de cuarenta metros arrancó las amarras de hierro y te sumergió en el Atlántico.",
            quote = "«El mar no devuelve a quienes bajan a buscar lo que no pertenece a este mundo.»",
            newspaperClippingResId = R.drawable.img_newspaper_flannan,
            historicalCaseTitle = "CASO REAL: EL FARO DE FLANNAN (1900)",
            historicalFactSnippet = HISTORICAL_FACT
        ),
        Ending(
            id = "end_8_tragic",
            storyId = StoryId.EL_ESPEJO,
            number = 38,
            title = "LA SILLA VOLCADA",
            badge = EndingBadge.TRAGICO,
            description = "Te sentaste en la cocina desierta frente a los platos servidos. El reloj se detuvo a las 09:12. Al darte vuelta, la puerta exterior se cerró sola con un golpe seco.",
            quote = "«La bitácora anotó tu nombre entre los hombres que nunca regresaron a puerto.»",
            newspaperClippingResId = R.drawable.img_newspaper_flannan,
            historicalCaseTitle = "CASO REAL: EL FARO DE FLANNAN (1900)",
            historicalFactSnippet = HISTORICAL_FACT
        ),
        Ending(
            id = "end_8_secret",
            storyId = StoryId.EL_ESPEJO,
            number = 39,
            title = "LA CANCIÓN DE LAS HÉBRIDAS",
            badge = EndingBadge.SECRETO,
            description = "En lo alto de la galería exterior, escuchaste cánticos en gaélico antiguo flotando sobre el viento sereno. No había tormenta, solo una voz milenaria que llamaba desde el arrecife.",
            quote = "«Los tres hombres no huyeron del faro: respondieron al llamado del océano profundo.»",
            newspaperClippingResId = R.drawable.img_newspaper_flannan,
            historicalCaseTitle = "CASO REAL: EL FARO DE FLANNAN (1900)",
            historicalFactSnippet = HISTORICAL_FACT
        ),
        Ending(
            id = "end_8_true",
            storyId = StoryId.EL_ESPEJO,
            number = 40,
            title = "LOS GUARDIANES ETERNOS",
            badge = EndingBadge.VERDADERO,
            description = "Aseguraste la bitácora y los archivos de bitácora en la caja impermeable de cobre. Cuando el Hesperus desembarcó, tus informes revelaron la histeria climática que consumió a la isla.",
            quote = "«El mayor enigma marítimo de Escocia quedó grabado para siempre en la piedra de Eilean Mòr.»",
            newspaperClippingResId = R.drawable.img_newspaper_flannan,
            historicalCaseTitle = "CASO REAL: EL FARO DE FLANNAN (1900)",
            historicalFactSnippet = HISTORICAL_FACT
        )
    )

    val SCENES = listOf(
        Scene(
            id = "s8_intro",
            storyId = StoryId.EL_ESPEJO,
            chapterIndex = 1,
            chapterTitle = "Hecho Real: Eilean Mòr, 15 de Diciembre de 1900",
            speaker = "Narrador",
            text = "Medianoche. Te encuentras en la torre del faro de Eilean Mòr, una roca solitaria azotada por el oleaje del Atlántico Norte. La gran lámpara de petróleo se ha extinguido. Entras a la cocina de los fareros: el reloj de péndulo marca las 09:12 detenido. Hay una silla tirada en el suelo y dos platos de carne con papas enfriándose en la mesa.",
            visualType = VisualSceneType.BASEMENT_MIRROR,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c8_read_log", "Abrir la bitácora encuadernada sobre el escritorio", "s8_log_inspect", effect = ChoiceEffect(fearDelta = 20, curiosityDelta = 30, itemGained = ITEM_LOGBOOK)),
                Choice("c8_check_pegs", "Revisar los percheros de abrigos impermeables", "s8_oilskins_check", effect = ChoiceEffect(fearDelta = 15, clueGained = CLUE_OILSKINS))
            )
        ),
        Scene(
            id = "s8_log_inspect",
            storyId = StoryId.EL_ESPEJO,
            chapterIndex = 1,
            chapterTitle = "Capítulo 1: La Bitácora del Terror",
            speaker = "Diario del Faro",
            text = "Lees la caligrafía temblorosa de Thomas Marshall:\n\n«12 de Dic: Viento feroz del noroeste. Nunca vi una tormenta como esta. James Ducat está muy callado. Donald MacArthur llora como un niño...»\n\nTiemblas al recordar que en esa fecha los barcos en el continente registraron un cielo despejado sin una sola ráfaga.",
            visualType = VisualSceneType.BASEMENT_MIRROR,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c8_climb_tower", "Subir a la galería de la linterna con la llave de bronce", "s8_lantern_climb", effect = ChoiceEffect(fearDelta = 25, itemGained = ITEM_BRASS_KEY, soundTrigger = "FOOTSTEP")),
                Choice("c8_western_haven", "Bajar hacia el embarcadero oeste donde guardaban las amarras", "s8_dock_descent", effect = ChoiceEffect(dangerDelta = 30, fearDelta = 20))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s8_oilskins_check",
            storyId = StoryId.EL_ESPEJO,
            chapterIndex = 1,
            chapterTitle = "Capítulo 1: El Tercer Traje",
            speaker = "Narrador",
            text = "En el perchero solo cuelga un impermeable de hule negro: el de MacArthur. Los otros dos fareros salieron protegidos, pero MacArthur debió salir corriendo desabrigado en mangas de camisa en medio de la noche helada. ¿Qué pudo aterrorizar a un marinero experto para no ponerse su abrigo?",
            visualType = VisualSceneType.BASEMENT_MIRROR,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c8_inspect_clock", "Examinar el péndulo detenido de la pared", "s8_clock_scene", effect = ChoiceEffect(fearDelta = 25, clueGained = CLUE_STOPPED_CLOCK, soundTrigger = "STINGER")),
                Choice("c8_outside_crags", "Salir al exterior y recorrer los acantilados rocosos", "s8_dock_descent", effect = ChoiceEffect(dangerDelta = 35, fearDelta = 25))
            )
        ),
        Scene(
            id = "s8_lantern_climb",
            storyId = StoryId.EL_ESPEJO,
            chapterIndex = 2,
            chapterTitle = "Capítulo 2: La Cúpula en Tinieblas",
            speaker = "Narrador",
            text = "Subes la escalera de caracol de 160 peldaños de hierro. El viento silba en la cúpula vidriada. La gigantesca lente Fresnel está paralizada. Afuera, en la galería circular, hay huellas húmedas que dan vueltas y vueltas alrededor de la barandilla.",
            visualType = VisualSceneType.BASEMENT_MIRROR,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c8_light_lamp", "Restablecer el combustible y encender la mecha Fresnel", "s8_end_good", effect = ChoiceEffect(endingTriggered = "end_8_good")),
                Choice("c8_listen_wind", "Asomarte a la barandilla hacia las olas que rompen abajo", "s8_gallery_whisper", effect = ChoiceEffect(fearDelta = 40, soundTrigger = "CREATURE"))
            )
        ),
        Scene(
            id = "s8_dock_descent",
            storyId = StoryId.EL_ESPEJO,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: El Embarcadero Destrozado",
            speaker = "Narrador",
            text = "Bajas por la hendidura de piedra hacia el atracadero oeste a 30 metros sobre el mar. Las barandas de hierro macizo están retorcidas como alambre fino. La caja de suministros de una tonelada fue arrancada de sus cimientos de hormigón. De pronto, el horizonte se eleva como un muro negro de agua.",
            visualType = VisualSceneType.BASEMENT_MIRROR,
            ambientSound = AmbientSoundType.STATIC_ROOM,
            choices = listOf(
                Choice("c8_rogue_wave", "Intentar trepar los escalones empinados de regreso", "s8_end_bad", effect = ChoiceEffect(endingTriggered = "end_8_bad", soundTrigger = "SCREAM_JUMPSCARE"))
            ),
            autoGlitch = true
        ),
        Scene(
            id = "s8_clock_scene",
            storyId = StoryId.EL_ESPEJO,
            chapterIndex = 3,
            chapterTitle = "Capítulo 3: La Cocina Fría",
            speaker = "Narrador",
            text = "Miras fijamente las manecillas quietas a las 09:12. Un crujido se produce detrás de ti: la silla que estaba tirada se levanta sola con lentitud espectral y se acomoda en la mesa. La bitácora abierta pasa de página sin que haya brisa.",
            visualType = VisualSceneType.BASEMENT_MIRROR,
            ambientSound = AmbientSoundType.SILENCE,
            choices = listOf(
                Choice("c8_tragic_trigger", "Girar la perilla de la puerta trabada", "s8_end_tragic", effect = ChoiceEffect(endingTriggered = "end_8_tragic", soundTrigger = "STINGER")),
                Choice("c8_true_trigger", "Recoger la bitácora y documentar cada prueba", "s8_end_true", effect = ChoiceEffect(endingTriggered = "end_8_true"))
            )
        ),
        Scene(
            id = "s8_gallery_whisper",
            storyId = StoryId.EL_ESPEJO,
            chapterIndex = 4,
            chapterTitle = "Capítulo 4: El Cántico Marino",
            speaker = "Narrador",
            text = "En la pasarela de la torre, el aire se llena de armonías vocales ancestrales que surgen del oleaje. Tres siluetas humanas vestidas con impermeables oscuros flotan inmóviles sobre la superficie de las olas, mirándote hacia arriba.",
            visualType = VisualSceneType.BASEMENT_MIRROR,
            ambientSound = AmbientSoundType.HUM_DRONE,
            choices = listOf(
                Choice("c8_secret_trigger", "Responder al llamado del océano", "s8_end_secret", effect = ChoiceEffect(endingTriggered = "end_8_secret", soundTrigger = "STINGER"))
            ),
            autoGlitch = true
        ),
        // Endings scenes
        Scene(id = "s8_end_good", storyId = StoryId.EL_ESPEJO, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #36: LA SEÑAL DEL HESPERUS\n\nEncendiste la luz que salvó a las tripulaciones de las rocas traicioneras.", isEndingScene = true, endingId = "end_8_good", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s8_end_bad", storyId = StoryId.EL_ESPEJO, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #37: EL ABISMO DEL EMBARCADERO OESTE\n\nUna ola anómala borró tu existencia en los confines del océano atlántico.", isEndingScene = true, endingId = "end_8_bad", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s8_end_tragic", storyId = StoryId.EL_ESPEJO, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #38: LA SILLA VOLCADA\n\nQuedaste atrapado en la cocina eterna donde el tiempo se detuvo a las 09:12.", isEndingScene = true, endingId = "end_8_tragic", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s8_end_secret", storyId = StoryId.EL_ESPEJO, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #39: LA CANCIÓN DE LAS HÉBRIDAS\n\nLos guardianes del faro se unieron al canto milenario que habita en las profundidades.", isEndingScene = true, endingId = "end_8_secret", visualType = VisualSceneType.ENDING_SCREEN),
        Scene(id = "s8_end_true", storyId = StoryId.EL_ESPEJO, chapterIndex = 5, chapterTitle = "Final Desbloqueado", text = "FINAL #40: LOS GUARDIANES ETERNOS\n\nPreservaste el testimonio forense que inmortalizó el mayor enigma naval de Escocia.", isEndingScene = true, endingId = "end_8_true", visualType = VisualSceneType.ENDING_SCREEN)
    )
}
