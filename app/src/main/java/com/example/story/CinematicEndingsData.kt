package com.example.story

import com.example.R
import com.example.model.StoryId

/**
 * Explicaciones finales inmersivas de cada historia (sin lenguaje técnico de rodaje ni tomas de cámara),
 * revelando la verdad cruda y explícita de lo sucedido, acompañadas de su correspondiente
 * imagen perturbadora en formato de cinta analógica VHS de archivo.
 */
object CinematicEndingsData {

    fun getEpilogue(endingId: String, storyId: StoryId): String {
        return EPILOGUES[endingId] ?: getDefaultEpilogueForStory(storyId)
    }

    fun getVhsEndingImageRes(endingId: String, storyId: StoryId): Int {
        return when (storyId) {
            StoryId.ULTIMA_CONEXION -> R.drawable.img_vhs_tape_crime
            StoryId.EL_DEPARTAMENTO -> R.drawable.img_horror_woman_wall
            StoryId.LA_FOTO -> R.drawable.img_photo_bedroom
            StoryId.EL_ULTIMO_TREN -> R.drawable.img_jumpscare_spider
            StoryId.EL_TURNO_NOCHE -> R.drawable.img_vhs_hospital_entity
            StoryId.EL_TRANSMISOR -> R.drawable.img_newspaper_dyatlov
            StoryId.LA_CABANA -> R.drawable.img_newspaper_hinterkaifeck
            StoryId.EL_ESPEJO -> R.drawable.img_newspaper_flannan
        }
    }

    fun getVhsTimestamp(storyId: StoryId): String {
        return when (storyId) {
            StoryId.ULTIMA_CONEXION -> "REC ● 02:48:19 AM - 12/OCT/1999"
            StoryId.EL_DEPARTAMENTO -> "PLAY ▶ 03:15:02 AM - 04/NOV/1997"
            StoryId.LA_FOTO -> "REC ● 03:31:00 AM - 19/AGO/1998"
            StoryId.EL_ULTIMO_TREN -> "PLAY ▶ 00:45:11 AM - LÍNEA B_1987"
            StoryId.EL_TURNO_NOCHE -> "REC ● 04:12:55 AM - SAN_JUDAS_1974"
            StoryId.EL_TRANSMISOR -> "EXPEDIENTE_URSS ● 01/FEB/1959"
            StoryId.LA_CABANA -> "POLIZEI_MÜNCHEN ● 31/MAR/1922"
            StoryId.EL_ESPEJO -> "EILEAN_MÒR_NLB ● 15/DIC/1900"
        }
    }

    private val EPILOGUES: Map<String, String> = mapOf(
        // ==========================================
        // HISTORIA 1: ÚLTIMA CONEXIÓN
        // ==========================================
        "end_1_good" to """
EXPLICACIÓN DEL FINAL:
Lograste sobrevivir porque rompiste la rutina predecible que el acosador había registrado durante semanas en tu teléfono. El agresor no era una entidad intangible; era un individuo obsesivo que instaló una aplicación espía en tu móvil para monitorear tu geolocalización por GPS. Sabía que cada noche de lluvia doblabas por el pasaje oscuro para ahorrar tiempo, y allí te esperaba con una lona plástica y cuerdas en un contenedor de basura. Al desviarte hacia la avenida iluminada y refugiarte junto a la patrulla policial, frustraste la emboscada a escasos metros de tu puerta. El atacante huyó entre las sombras, pero su última notificación en tu pantalla dejó claro que tu dirección ya no volverá a ser un lugar seguro.
""".trimIndent(),

        "end_1_bad" to """
EXPLICACIÓN DEL FINAL:
El callejón nunca fue un atajo fortuito; fue el embudo táctico donde el acosador te arreó deliberadamente mediante los mensajes intimidatorios. Cada notificación («Sé que vas a doblar», «No mires atrás») estaba calculada para provocarte un pánico ciego que anulara tu orientación espacial y te empujara hacia la única calle cortada por rejas del barrio. Al llegar al muro ciego, el agresor ya bloqueaba la salida con una navaja automática. Nadie escuchó tus alaridos porque el estruendo de los truenos ahogó los gritos mientras tu cuerpo era arrastrado hacia la oscuridad. El acosador tomó tu teléfono ensangrentado y escribió el último mensaje a tus contactos: «Ya llegó».
""".trimIndent(),

        "end_1_tragic" to """
EXPLICACIÓN DEL FINAL:
El acosador nunca caminó detrás de ti en la calle: estuvo esperándote dentro de tu propio departamento desde las nueve de la noche, utilizando una copia de la llave que te había sustraído semanas atrás. Los mensajes en tiempo real que describían tus pasos no provenían de alguien en la acera, sino de una cámara espía que instaló sobre el dintel de la entrada del edificio, conectada a su pantalla mientras te aguardaba a oscuras en el pasillo de tu cuarto. Cuando abriste la puerta creyendo haber escapado de la tormenta, te entregaste directamente a sus manos. Un paño con éter sofocó tu respiración antes de que pudieras pedir auxilio.
""".trimIndent(),

        "end_1_secret" to """
EXPLICACIÓN DEL FINAL:
Al marcar de regreso al número desconocido, provocaste una anomalía cuántica de retroalimentación electromagnética. La llamada que entró en tu propio bolsillo con tu mismo número no era un truco telefónico: era una transmisión agónica emitida por una versión futura de ti mismo, asesinada 40 minutos después en esa misma cuadra, intentando advertirte desesperadamente a través de la red celular. Al contestar tu propia voz exhalando en el auricular, cerraste la paradoja temporal que te condenó a revivir la cacería una y otra vez en un bucle infinito de terror analógico.
""".trimIndent(),

        "end_1_true" to """
EXPLICACIÓN DEL FINAL:
El monstruo que te vigilaba no era un desconocido en la niebla, sino Mateo, tu amigo de confianza. Mateo padecía un trastorno obsesivo erotomaníaco homicida: clonó tu tarjeta SIM y creó los números descartables para aterrorizarte sistemáticamente y presentarse ante ti como tu único protector y salvador nocturno, forzándote a mudarte a su casa para aislarte del mundo. Cuando encontraste el duplicado de tu llave en su vehículo y lo confrontaste, toda su máscara de empatía se desmoronó al instante. Trancó la puerta por dentro y desenvainó un sedante, decidido a que jamás volvieras a salir de su custodia.
""".trimIndent(),

        // ==========================================
        // HISTORIA 2: EL DEPARTAMENTO
        // ==========================================
        "end_2_good" to """
EXPLICACIÓN DEL FINAL:
Lograste salvar la vida porque aseguraste la única zona del departamento con visibilidad directa a la calle. Los tres golpes secos que retumbaban en las paredes no eran un fenómeno paranormal: eran el código acústico con el que el antiguo inquilino desquiciado comprobaba si te habías dormido antes de deslizarse por los paneles falsos del armario. Al atrincherarte en el balcón y gritar pidiendo auxilio hasta alertar a todo el vecindario, obligaste a la policía a derribar la puerta principal, atrapando al intruso cuando intentaba huir por la claraboya del tejado.
""".trimIndent(),

        "end_2_bad" to """
EXPLICACIÓN DEL FINAL:
El peligro jamás estuvo afuera en el pasillo común. El intruso nunca necesitó forzar la cerradura exterior porque vivía dentro de la cavidad hueca de 80 centímetros entre el tabique de yeso y el hormigón del edificio. Cuando abriste la puerta principal armado creyendo que el atacante golpeaba desde el rellano, la criatura esquelética ya colgaba boca abajo desde la trampilla del cielorraso directamente sobre tu cabeza. Te atrapó por el cuello desde el aire, quebró tu tráquea de un solo golpe seco y arrastró tu cuerpo inerte hacia el interior del falso techo antes de que nadie pudiera notar tu desaparición.
""".trimIndent(),

        "end_2_tragic" to """
EXPLICACIÓN DEL FINAL:
Al asomarte por tercera vez a la mirilla de la puerta, caíste en la trampa calculada de un asesino ritualista. El intruso había perforado un orificio milimétrico en el marco de la mirilla exterior y esperaba con un punzón de acero alineado exactamente a la altura de los ojos. El rostro ensangrentado que viste pegado al cristal no era una ilusión: era el ojo dilatado del propio homicida observándote a centímetros de distancia antes de empujar el punzón a través del visor. Tus ojos fueron arrancados para sumarse a la macabra colección de órganos preservados en frascos que la policía descubriría meses después en las calderas del subsuelo.
""".trimIndent(),

        "end_2_secret" to """
EXPLICACIÓN DEL FINAL:
El edificio fue construido en 1932 sobre las ruinas de un pabellón psiquiátrico de diseño no euclidiano. Cuando el pánico psicomotor alcanza el límite en sus habitantes, la estructura espacial colapsa sobre sí misma, atrapando a la víctima en una cinta topológica de Moebius. El descanso marcado como «Piso -1» no existe en los planos municipales; es el eco dimensional donde convergen todas las muertes violentas ocurridas en el departamento 4B. Al descender las escaleras, corriste en círculos para encontrarte cara a cara con tu propio cadáver en el rellano.
""".trimIndent(),

        "end_2_true" to """
EXPLICACIÓN DEL FINAL:
El inquilino anterior, a quien el conserje dio por «desaparecido» hace dos años tras una ejecución hipotecaria, jamás abandonó el inmueble. Fingió su muerte arrojando sus ropas a las vías del tren y regresó la misma noche para habitar los espacios muertos entre los muros de cuatro departamentos. Vivía alimentándose de restos de comida que te robaba de noche, observando cada uno de tus movimientos íntimos a través de los orificios de los enchufes y durmiendo en el conducto de ventilación sobre tu cama. Los tres golpes en la pared eran el sonido de sus rodillas arrastrándose sobre el yeso mientras seguía tus pasos por el departamento.
""".trimIndent(),

        // ==========================================
        // HISTORIA 3: LA FOTO
        // ==========================================
        "end_3_good" to """
EXPLICACIÓN DEL FINAL:
Sobreviviste porque tomaste la decisión más lúcida frente al terror: no investigaste el armario a oscuras ni intentaste hacerte el héroe en solitario. Saliste descalzo a la calle gritando por ayuda y entregaste el teléfono intacto a la fiscalía como prueba pericial. Los peritos forenses analizaron los metadatos de la fotografía y detectaron la señal de Bluetooth de una cámara remota sincronizada desde el cielorraso. La policía allanó la vivienda y arrestó a un prófugo armado oculto en el entretecho con cloroformo y rollos de cinta adhesiva, neutralizando el ataque antes de que pudiera consumarse.
""".trimIndent(),

        "end_3_bad" to """
EXPLICACIÓN DEL FINAL:
La fotografía que recibiste en tu teléfono era un cebo psicológico para atraerte al punto exacto donde la trampa estaba montada. El armario del dormitorio había sido vaciado y acondicionado por el intruso como un cubículo de captura fotográfica y ejecución clandestina. Cuando abriste las puertas corredizas en la penumbra, el destello enceguecedor de un flash profesional a quemarropa quemó tus retinas por completo. Antes de que pudieras reaccionar, dos manos con uñas desfiguradas te aferraron de la mandíbula y los ojos, arrastrándote hacia el fondo del armario donde fuiste asfixiado para capturar la imagen exacta del horror antes de morir.
""".trimIndent(),

        "end_3_tragic" to """
EXPLICACIÓN DEL FINAL:
La persona que tomó las fotografías nocturnas fuiste tú mismo. Afectado por un trastorno disociativo severo con parasomnia inducida por medicamentos, tu personalidad se fracturó tras un trauma reprimido. Durante las fases de sueño profundo te levantabas en trance sonámbulo, manipulabas la cámara con temporizadores para retratar tu propio cuerpo indefenso en la cama y luego programabas el envío de los mensajes al despertar para aterrorizar a tu propia mente consciente. Sin embargo, en la última fotografía descubriste con horror una segunda silueta parada a tus espaldas, revelando que algo más te acompañaba en tu trance.
""".trimIndent(),

        "end_3_secret" to """
EXPLICACIÓN DEL FINAL:
La lente de la cámara fue contaminada por una radiación electromagnética anómala que perforó el tejido dimensional. El archivo digital en la galería no era una imagen pasiva; era un portal de condensación ectoplasmática que utilizó la energía del procesador y la batería de litio para manifestar una entidad parasitaria en el plano físico. Al aplicar zoom al máximo sobre la pupila del reflejo, activaste la frecuencia de sincronización: los dedos de la entidad quebraron el cristal templado de la pantalla desde el interior del teléfono, drenando tu energía vital hasta dejar tu cuerpo momificado en la cama.
""".trimIndent(),

        "end_3_true" to """
EXPLICACIÓN DEL FINAL:
En el entretecho del ático se descubrió un santuario perverso con más de dos mil fotografías tuyas durmiendo, duchándote y comiendo, acumuladas a lo largo de seis meses. El intruso era tu antiguo compañero de universidad que ingresó por las tejas comunales del edificio tras una reforma. Había fabricado un nido clandestino con colchones sucios y una mirilla telescópica apuntando directamente a tu almohada a través de una ranura en el cielorraso. Cada crujido nocturno que atribuías al viento eran sus pasos caminando a escasos centímetros por encima de tu cabeza mientras dormías.
""".trimIndent(),

        // ==========================================
        // HISTORIA 4: EL ÚLTIMO TREN
        // ==========================================
        "end_4_good" to """
EXPLICACIÓN DEL FINAL:
Comprendiste a tiempo la naturaleza espectral del convoy subterráneo: la ausencia de nombres en las estaciones y el silencio cadavérico de los pasajeros indicaban una trampa dimensional sin retorno. Al accionar el freno neumático auxiliar manual de la cabina trasera, forzaste una desaceleración violenta que te permitió saltar por la ventanilla hacia el andén de servicio antes de que el tren cruzara la falla geológica profunda. Saliste por los respiraderos de ventilación hacia la superficie urbana justo cuando el convoy desaparecía en la oscuridad eterna de las vías clausuradas.
""".trimIndent(),

        "end_4_bad" to """
EXPLICACIÓN DEL FINAL:
El boleto que entregaste al revisor no era un pasaje ordinario; era la cesión voluntaria de tu alma como combustible para la caldera espectral de la línea abandonada. Al perforar el cartón, tu anclaje con el mundo de los vivos quedó disuelto para siempre: tu reflejo en la ventanilla desapareció y tu cuerpo se transformó en una sombra translúcida condenada a viajar sentada junto a las decenas de pasajeros que abordaron el tren en distintas décadas (1964, 1978, 1999) y jamás volvieron a ver la luz del sol.
""".trimIndent(),

        "end_4_tragic" to """
EXPLICACIÓN DEL FINAL:
El viaje completo en el tren 00:45 nunca ocurrió en el plano físico. Fue la alucinación agónica de tu cerebro en sus últimos segundos de actividad eléctrica tras haber tropezado en el andén y caído a las vías electrizadas. El periódico arrugado en el suelo con la noticia del descarrilamiento fatal reflejaba tu propia muerte violenta: tu mente se negaba a aceptar su destrucción instantánea y construyó una pesadilla ferroviaria de veinte minutos para retrasar el impacto ineludible de la muerte cerebral.
""".trimIndent(),

        "end_4_secret" to """
EXPLICACIÓN DEL FINAL:
El convoy 00:45 es en realidad un acelerador de partículas móvil construido en secreto durante la Guerra Fría bajo las líneas del metro militar. Al penetrar la cabina del maquinista y accionar los controles de cristales electromagnéticos, tomaste el mando de un vehículo interdimensional que navega entre los pliegues de la realidad y el abismo, asumiendo la condena de convertirte en el nuevo conductor espectral que guiará el tren a través de las pesadillas de los viajeros perdidos.
""".trimIndent(),

        "end_4_true" to """
EXPLICACIÓN DEL FINAL:
Una red clandestina operada por funcionarios corruptos utilizaba el convoy fuera de servicio para trasladar a indigentes y transeúntes solitarios hacia túneles clausurados de experimentación química militar bajo la ciudad. El maquinista era un operario drogado para no desviar la ruta. Al cambiar las agujas de las vías con la llave triangular manual, redirigiste el convoy a la estación terminal pública iluminada, exponiendo el crimen ante decenas de testigos y forzando la clausura judicial definitiva de las líneas secretas.
""".trimIndent(),

        // ==========================================
        // HISTORIA 5: EL TURNO NOCHE
        // ==========================================
        "end_5_good" to """
EXPLICACIÓN DEL FINAL:
Sobreviviste aplicando la regla de oro de la supervivencia en instalaciones malditas: no investigar ruidos inexplicables en soledad y huir ante la primera señal de peligro. Comprendiste que las alarmas de Código Azul que sonaban en habitaciones deshabitadas eran trampas mecánicas activadas por los pacientes residuales para atraer al vigilante hacia cuartos con cerrojos ciegos. Corriste al portón principal sin mirar atrás, abandonando el trabajo antes de que las criaturas del subsuelo pudieran cerrar las compuertas de la salida.
""".trimIndent(),

        "end_5_bad" to """
EXPLICACIÓN DEL FINAL:
En 1974, el sanatorio fue clausurado tras descubrirse que el director realizaba experimentos de animación cataléptica mediante toxinas de ranas amazónicas en pacientes terminales. Los sujetos nunca murieron biológicamente; cayeron en un estado de hibernación insensible al dolor y rabia caníbal permanente dentro de las cámaras de la morgue. La empresa de seguridad contrataba vigilantes novatos con la única intención de utilizarlos como carnada biológica fresca para alimentar a los pacientes confinados bajo tierra y evitar que escaparan a la ciudad.
""".trimIndent(),

        "end_5_tragic" to """
EXPLICACIÓN DEL FINAL:
El vigilante novato nunca existió: tú eras el paciente número 402 del pabellón psiquiátrico, internado en 1973 tras un brote homicida. Aquejado por demencia senil y un derrame cerebral inminente en tu celda acolchada, tu mente en agonía construyó la fantasía de que eras un guardia joven recorriendo pasillos abandonados. Las voces de auxilio y los pasos en el techo eran los médicos reales aplicando reanimación cardiopulmonar en el hospital moderno antes de desconectar el monitor de signos vitales.
""".trimIndent(),

        "end_5_secret" to """
EXPLICACIÓN DEL FINAL:
La máquina de electrochoques del sótano actuó durante cuatro décadas como un acumulador de sufrimiento humano que transformó el dolor de cientos de pacientes lobotomizados en una entidad psíquica colectiva. Al sentarte en la silla de electrodos, la descarga masiva fusionó tu sistema nervioso con la mente colmena del sanatorio, disolviendo tu identidad individual para sumarte como una voz más al coro de almas que habita para siempre en los circuitos de la clínica.
""".trimIndent(),

        "end_5_true" to """
EXPLICACIÓN DEL FINAL:
Encontraste las cintas magnéticas originales del Dr. Steiner ocultas en la caja fuerte del archivo: 18 niños habían sido sometidos a ensayos farmacológicos ilegales para corporaciones farmacéuticas extranjeras. Las sombras y alarmas eran manipuladas por dos cuidadores contratados para ahuyentar a curiosos hasta que la causa penal prescribiera en 2026. Al entregar las grabaciones a periodistas independientes, las fosas comunes del patio fueron exhumadas y los responsables enfrentaron juicio penal por crímenes de lesa humanidad.
""".trimIndent(),

        // ==========================================
        // HISTORIA 6 [CASO REAL]: EL PASO DIÁTLOV
        // ==========================================
        "end_6_good" to """
EXPLICACIÓN DEL FINAL:
La supervivencia en el Paso Diátlov dependía exclusivamente de evitar acampar en la ladera pelada de Kholat Syakhl («Montaña de los Muertos»). Al ordenar descender al valle boscoso del río Auspiya, los árboles amortiguaron las ráfagas huracanadas y disiparon las ondas de infrasonido inaudible de 18 Hertzios que provocaron el brote psicótico masivo del grupo real. Además, el bosque eliminó el riesgo del aluvión de nieve compactada que fracturó sus huesos, permitiendo a los nueve estudiantes regresar a salvo a sus hogares.
""".trimIndent(),

        "end_6_bad" to """
EXPLICACIÓN DEL FINAL:
Los excursionistas rasgaron la tienda desde adentro en ropa interior enloquecidos por el terror claustrofóbico que les provocó el Infrasonido generado por el viento sobre la cumbre. En la noche a -30°C, la hipotermia severa dilató sus vasos sanguíneos periféricos provocando el fenómeno de «desnudamiento paradójico»: creían que sus cuerpos ardían en llamas y se quitaron la ropa. En la hondonada del arroyo, una avalancha de cuatro toneladas de nieve dura cayó sobre ellos rompiendo sus costillas por aplastamiento hidráulico, mientras la fauna acuática y la putrefacción consumieron los ojos y la lengua de Liudmila Dubínina antes del deshielo de mayo.
""".trimIndent(),

        "end_6_tragic" to """
EXPLICACIÓN DEL FINAL:
El grupo cometió el error fatal de dispersarse en la tormenta sin sus abrigos ni botas de montaña. Al pie del cedro centenario, dos de los jóvenes murieron congelados mientras intentaban encender fuego con ramas secas, arrancándose la piel de las manos al trepar la corteza helada. Diátlov y sus compañeros intentaron arrastrarse de regreso a la tienda colapsada pero sus corazones se detuvieron por hipotermia fulminante en la nieve, quedando congelados en posturas agónicas de súplica hacia la montaña.
""".trimIndent(),

        "end_6_secret" to """
EXPLICACIÓN DEL FINAL:
El grupo de Diátlov fue víctima colateral de un lanzamiento militar fallido de un misil soviético R-12 Dvina propulsado por combustible hipergólico tóxico. La explosión en el aire liberó una nube de gas corrosivo y radiación que asfixió a los jóvenes obligándolos a romper la tienda para respirar. La onda de choque compresiva en altitud fracturó sus cajas torácicas sin causar hematomas externos, explicando las esferas de fuego anaranjadas vistas en el cielo por otros testigos y la radiación hallada en sus ropas.
""".trimIndent(),

        "end_6_true" to """
EXPLICACIÓN DEL FINAL:
La ciencia moderna de 2021 resolvió el mayor enigma soviético del siglo XX: una combinación de tres factores físicos mortales. El corte que hicieron en la nieve para colocar la tienda desestabilizó una placa de nieve compacta que cayó sobre ellos como un bloque de hormigón mientras dormían, fracturando cráneos y costillas. Simultáneamente, el viento ártico generó un Vórtice Kármán de infrasonidos inaudibles que desató ataques de pánico y asfixia percibida. Huyendo semidesnudos y heridos en una noche polar a -30°C, la hipotermia los liquidó en menos de dos horas.
""".trimIndent(),

        // ==========================================
        // HISTORIA 7 [CASO REAL]: HINTERKAIFECK
        // ==========================================
        "end_7_good" to """
EXPLICACIÓN DEL FINAL:
Andreas Gruber no ignoró las señales de peligro: las huellas en la nieve que entraban al granero pero no salían, el periódico desconocido de Múnich en el ático y los ruidos en el techo. En lugar de entrar solo a oscuras, cabalgó al pueblo vecino y regresó con vecinos armados y perros de presa. Rodearon el pajar y acorralaron al intruso escondido en el heno antes de que pudiera desenvainar la piqueta mattock, desarticulando la masacre que en la vida real acabó con toda la familia Gruber.
""".trimIndent(),

        "end_7_bad" to """
EXPLICACIÓN DEL FINAL:
El asesino atrajo a los miembros de la familia uno por uno al granero haciendo ruidos con las cadenas del ganado para amortiguar los golpes. En la oscuridad, la piqueta de desbroce de nogal (*Reuthaue*) destrozó sus cráneos de forma limpia e instantánea. La pequeña Cäzilia de 7 años no murió de inmediato: la autopsia histórica reveló que agonizó durante horas junto a los cadáveres de sus abuelos, arrancándose mechones enteros de su propio cabello de dolor antes de que el homicida regresara al establo a rematarla junto a la bebé Josefina en su cuna.
""".trimIndent(),

        "end_7_tragic" to """
EXPLICACIÓN DEL FINAL:
La tragedia más injusta de Hinterkaifeck fue la de Maria Baumgartner, la nueva mucama. Había llegado a la granja apenas tres horas antes del ataque, contratada porque la empleada anterior había renunciado aterrorizada por los ruidos de pasos en el ático. Maria desempacaba su maleta en su primer día de trabajo cuando el asesino entró a su habitación y le partió el cráneo con la piqueta. No tenía ningún vínculo con los Gruber ni con sus disputas de dinero; fue asesinada simplemente por estar en el lugar equivocado en el momento fatal.
""".trimIndent(),

        "end_7_secret" to """
EXPLICACIÓN DEL FINAL:
El detalle más siniestro y verídico del caso: tras masacrar a las seis personas, el asesino no escapó de inmediato. Convivió tranquilamente durante tres días enteros con los cadáveres en la granja. Alimentó y ordeñó a las vacas a diario para que no mugieran y alertaran a los vecinos, encendió fuego en la estufa para simular vida cotidiana con el humo de la chimenea, comió de la despensa de las víctimas y durmió en la cama de Andreas antes de esfumarse en el bosque sin robar los fajos de marcos de oro guardados en los cajones.
""".trimIndent(),

        "end_7_true" to """
EXPLICACIÓN DEL FINAL:
La perfilación criminal moderna de 2007 de la Academia de Policía de Fürstenfeldbruck concluyó que el culpable fue Lorenz Schlittenbauer, el vecino terrateniente que mantenía una disputa por la paternidad del pequeño Josef. Schlittenbauer fue quien lideró el grupo de búsqueda vecinal, rompió la puerta de la granja sin mostrar sorpresa, caminó directamente hacia el establo y manipuló los cadáveres antes de la llegada de la policía judicial para contaminar intencionalmente la escena del crimen y evitar que lo incriminaran.
""".trimIndent(),

        // ==========================================
        // HISTORIA 8 [CASO REAL]: EL FARO DE FLANNAN
        // ==========================================
        "end_8_good" to """
EXPLICACIÓN DEL FINAL:
Los fareros sobrevivieron porque respetaron el reglamento estricto del Northern Lighthouse Board: jamás descender a las plataformas bajas durante alertas de oleaje violento del Atlántico Norte. James Ducat ordenó sellar las compuertas de hierro de la torre y permanecer los tres juntos cuidando la lámpara de vapor de mercurio. La luz nunca se apagó, guiando a los barcos en la tormenta y salvando sus vidas de las olas colosales que barrieron los muelles exteriores de la isla.
""".trimIndent(),

        "end_8_bad" to """
EXPLICACIÓN DEL FINAL:
Una gigantesca Ola Vagabunda (*Rogue Wave*) de más de 35 metros de altura trepó por el acantilado vertical de Eilean Mòr. Los tres fareros estaban en el desembarcadero oeste intentando amarrar la caja de grúas cuando la pared de agua negra los sorprendió por la espalda. Cientos de toneladas de agua salada arrancaron las barandillas de hierro de cuajo y barrieron a los tres hombres instantáneamente hacia fosas marinas de 150 metros de profundidad, donde la hipotermia fulminante paralizó sus corazones en menos de 90 segundos. Sus cuerpos jamás fueron devueltos por el océano.
""".trimIndent(),

        "end_8_tragic" to """
EXPLICACIÓN DEL FINAL:
El farero Donald MacArthur sufrió un brote psicótico severo inducido por la reclusión claustrofóbica y la privación sensorial extrema de la roca solitaria. Su delirio paranoide se contagió por histeria colectiva a Marshall y Ducat. La «tormenta monstruosa» que anotaron en el diario de bitácora jamás existió en la realidad: los registros meteorológicos de toda Escocia confirmaron que el cielo estuvo despejado y calmo. En un trance de locura compartida para «acallar las voces del viento», los tres hombres saltaron de los acantilados hacia el mar helado.
""".trimIndent(),

        "end_8_secret" to """
EXPLICACIÓN DEL FINAL:
En el folclore gaélico de las islas Hébridas, Eilean Mòr era llamada «La Isla de los Siete Cazadores», un santuario pagano donde los humanos tenían prohibido pernoctar desde tiempos celtas. La construcción del faro en 1899 profanó los túmulos sagrados de la isla. La desaparición de los tres hombres sin dejar ropas, cartas ni rastro biológico alguno fue interpretada por los marineros como el cobro del mar y los espíritus ancestrales reclamando la soledad eterna de la roca.
""".trimIndent(),

        "end_8_true" to """
EXPLICACIÓN DEL FINAL:
La reconstrucción pericial de Robert Muirhead reveló la secuencia exacta del accidente: Ducat y Marshall bajaron al muelle oeste para amarrar la grúa que estaba siendo golpeada por el oleaje. MacArthur quedó de guardia en la torre. Al divisar desde la ventana una secuencia de olas anómalas descomunales trepando por las piedras, MacArthur salió corriendo a toda prisa sin su impermeable para advertir a sus compañeros. La ola gigante los alcanzó a mitad de la escalera de roca y los arrastró a los tres mar adentro, dejando la cocina del faro vacía, con una silla volcada en el suelo y el reloj detenido a las 09:12.
""".trimIndent()
    )

    private fun getDefaultEpilogueForStory(storyId: StoryId): String {
        return """
EXPLICACIÓN DEL FINAL:
Los acontecimientos de '${storyId.subtitle}' concluyeron revelando la fragilidad de la razón humana frente al horror. Cada pista recolectada y cada mensaje recibido formaban parte del rompecabezas que condujo a este desenlace ineludible. El misterio quedó sellado en el expediente oficial, pero la sombra de lo ocurrido permanece viva en el lugar aguardando a la próxima víctima.
""".trimIndent()
    }
}
