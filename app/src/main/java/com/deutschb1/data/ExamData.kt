package com.deutschb1.data

// ─── Exam Provider ───────────────────────────────────────────────────────────

enum class ExamProvider(val displayName: String, val description: String) {
    GOETHE("Goethe-Institut", "Offizielles Goethe-Zertifikat B1"),
    OESD("ÖSD", "Österreichisches Sprachdiplom Deutsch"),
    TELC("TELC", "The European Language Certificates")
}

// ─── Exam Skill ──────────────────────────────────────────────────────────────

enum class ExamSkill(val displayName: String, val icon: String, val durationMin: Int) {
    LESEN("Lesen", "📖", 65),
    HOEREN("Hören", "🎧", 40),
    SCHREIBEN("Schreiben", "✍️", 60),
    SPRECHEN("Sprechen", "🗣️", 15)
}

// ─── Question Types ──────────────────────────────────────────────────────────

data class MultipleChoiceQuestion(
    val id: Int,
    val text: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String = ""
)

data class TrueFalseQuestion(
    val id: Int,
    val statement: String,
    val isTrue: Boolean,
    val explanation: String = ""
)

data class MatchingItem(val left: String, val right: String)

data class ReadingPart(
    val partNumber: Int,
    val title: String,
    val instruction: String,
    val text: String,
    val questions: List<MultipleChoiceQuestion>
)

data class HoerenPart(
    val partNumber: Int,
    val title: String,
    val instruction: String,
    val transcript: String,
    val questions: List<MultipleChoiceQuestion>,
    val audioAssetPath: String = ""
)

data class SchreibenTask(
    val taskNumber: Int,
    val title: String,
    val prompt: String,
    val minWords: Int,
    val hints: List<String>
)

data class SprechenTask(
    val taskNumber: Int,
    val title: String,
    val instruction: String,
    val topic: String,
    val prepTimeSec: Int,
    val speakTimeSec: Int,
    val tips: List<String>
)

data class ExamContent(
    val id: String,
    val name: String,
    val provider: ExamProvider,
    val lesenParts: List<ReadingPart>,
    val hoerenParts: List<HoerenPart>,
    val schreibenTasks: List<SchreibenTask>,
    val sprechenTasks: List<SprechenTask>
)

// ─── Goethe B1 Content ───────────────────────────────────────────────────────

val GoetheExam1 = ExamContent(
    id = "goethe_1",
    name = "Modelltest 1",
    provider = ExamProvider.GOETHE,
    lesenParts = listOf(
        ReadingPart(
            partNumber = 1,
            title = "Teil 1 – Zeitungsartikel",
            instruction = "Lesen Sie den Text und entscheiden Sie, ob die Aussagen richtig (R) oder falsch (F) sind.",
            text = """
                Berlin, 15. März – Die Stadt Berlin plant, bis 2030 den öffentlichen Nahverkehr vollständig auf elektrische Busse umzustellen. 
                Der Bürgermeister erklärte gestern bei einer Pressekonferenz, dass bereits 200 E-Busse bestellt worden seien. 
                Die Kosten für das Projekt belaufen sich auf etwa 500 Millionen Euro, die zum Teil durch europäische Fördermittel finanziert werden sollen. 
                Kritiker bemängeln jedoch, dass die Ladeinfrastruktur noch nicht ausreichend ausgebaut sei. 
                Die Berliner Verkehrsbetriebe (BVG) zeigen sich trotzdem optimistisch und versprechen, bis Ende 2025 mindestens 50 Prozent der Busflotte zu elektrifizieren.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(1, "Berlin will seinen öffentlichen Nahverkehr bis 2030 komplett elektrifizieren.", listOf("Richtig", "Falsch"), 0, "Im Text steht: 'bis 2030 den öffentlichen Nahverkehr vollständig auf elektrische Busse umzustellen'."),
                MultipleChoiceQuestion(2, "Die E-Busse werden vollständig aus städtischen Mitteln finanziert.", listOf("Richtig", "Falsch"), 1, "Der Text sagt, dass europäische Fördermittel genutzt werden sollen."),
                MultipleChoiceQuestion(3, "Die BVG plant, bis 2025 mehr als die Hälfte ihrer Busse zu elektrifizieren.", listOf("Richtig", "Falsch"), 0, "BVG will bis Ende 2025 mindestens 50 Prozent elektrifizieren.")
            )
        ),
        ReadingPart(
            partNumber = 2,
            title = "Teil 2 – Anzeigen",
            instruction = "Welche Anzeige passt zu welcher Person? Ordnen Sie zu.",
            text = """
                Anzeige A: SPRACHKURS GESUCHT
                Biete Nachhilfe in Mathematik, suche im Austausch Deutschunterricht für Anfänger. 
                Bin Muttersprachler. Tel: 030-12345
                
                Anzeige B: WOHNGEMEINSCHAFT
                Suche ruhige/n MitbewohnerIn für 3-ZW in Prenzlauer Berg. 
                580€ warm, ab 1. April. Keine Haustiere. info@wg-pb.de
                
                Anzeige C: FAHRRAD ZU VERKAUFEN
                Gut erhaltenes Stadtrad, 3 Gänge, neu bereift. 
                VB 120€. Abholung in Mitte. m.mueller@gmail.com
                
                Anzeige D: BABYSITTER GESUCHT
                Familie sucht zuverlässige/n Babysitter für 2 Kinder (3 und 6 Jahre), 
                mittwochs 16-20 Uhr. 12€/Std. babysitter@familie-jung.de
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(4, "Sven lernt Deutsch und kann gut rechnen.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D"), 0, "Er kann Mathe beibringen und sucht Deutschkurs = Anzeige A passt."),
                MultipleChoiceQuestion(5, "Maria braucht jemanden, der nachmittags auf ihre Kinder aufpasst.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D"), 3, "Anzeige D sucht Babysitter nachmittags mittwochs."),
                MultipleChoiceQuestion(6, "Thomas zieht nach Berlin und sucht eine günstige Unterkunft.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D"), 1, "Anzeige B bietet Zimmer in WG an.")
            )
        ),
        ReadingPart(
            partNumber = 3,
            title = "Teil 3 – Informationstext",
            instruction = "Lesen Sie den Text. Welche Antwort (a, b oder c) passt am besten?",
            text = """
                Gesundheit und Ernährung
                
                In Deutschland ernähren sich immer mehr Menschen vegetarisch oder vegan. 
                Laut einer aktuellen Umfrage essen bereits 10% der Deutschen kein Fleisch mehr – das sind fast neun Millionen Menschen. 
                Weitere 2% leben vegan, verzichten also komplett auf tierische Produkte. 
                Als Hauptgründe nennen Befragte den Tierschutz (68%), die eigene Gesundheit (54%) und Umweltschutz (49%). 
                
                Vegetarische und vegane Restaurants boomen in deutschen Großstädten. 
                Besonders in Berlin, Hamburg und München hat sich in den letzten fünf Jahren die Zahl der rein pflanzlichen Restaurants verdreifacht. 
                Auch in Supermärkten nimmt das Angebot an Fleischersatzprodukten kontinuierlich zu – der Markt wächst jährlich um etwa 20 Prozent.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(7, "Wie viele Deutsche essen laut dem Text kein Fleisch?",
                    listOf("Fast 2 Millionen", "Fast 9 Millionen", "Fast 10 Millionen"), 1,
                    "10% der Deutschen = fast neun Millionen Menschen."),
                MultipleChoiceQuestion(8, "Was ist der häufigste Grund für Vegetarismus in Deutschland?",
                    listOf("Gesundheit", "Tierschutz", "Umweltschutz"), 1,
                    "Tierschutz wird von 68% der Befragten als Hauptgrund genannt."),
                MultipleChoiceQuestion(9, "Wie hat sich die Zahl der veganen Restaurants in München entwickelt?",
                    listOf("Sie hat sich verdoppelt", "Sie ist gleich geblieben", "Sie hat sich verdreifacht"), 2,
                    "Die Zahl der pflanzlichen Restaurants hat sich in den letzten fünf Jahren verdreifacht.")
            )
        )
    ),
    hoerenParts = listOf(
        HoerenPart(
            partNumber = 1,
            title = "Teil 1 – Kurze Gespräche",
            instruction = "Sie hören fünf kurze Gespräche. Welche Antwort passt?",
            transcript = """
                [Gespräch 1]
                Frau: Entschuldigung, wann fährt der nächste Zug nach München?
                Mann: Der nächste Zug fährt um 14:32 Uhr von Gleis 7.
                Frau: Und der übernächste?
                Mann: Dann um 15:05 Uhr, aber der hat meistens Verspätung.
                
                [Gespräch 2]
                Chef: Herr Müller, haben Sie den Bericht schon fertig?
                Müller: Fast. Ich brauche noch etwa zwei Stunden.
                Chef: Das ist zu lang. Ich brauche ihn bis 16 Uhr.
                Müller: Ich werde mein Bestes tun.
                
                [Gespräch 3]
                Arzt: Wie lange haben Sie schon diese Schmerzen?
                Patient: Seit ungefähr einer Woche.
                Arzt: Nehmen Sie zurzeit Medikamente?
                Patient: Nur gelegentlich Aspirin gegen die Kopfschmerzen.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(1, "Wann fährt der nächste Zug nach München?",
                    listOf("14:23 Uhr", "14:32 Uhr", "15:05 Uhr"), 1,
                    "Der Zug fährt um 14:32 Uhr."),
                MultipleChoiceQuestion(2, "Wann muss Herr Müller den Bericht fertigstellen?",
                    listOf("In zwei Stunden", "Um 16 Uhr", "Morgen früh"), 1,
                    "Der Chef braucht den Bericht bis 16 Uhr."),
                MultipleChoiceQuestion(3, "Was nimmt der Patient gegen Kopfschmerzen?",
                    listOf("Nichts", "Aspirin", "Ibuprofen"), 1,
                    "Er nimmt gelegentlich Aspirin.")
            )
        ),
        HoerenPart(
            partNumber = 2,
            title = "Teil 2 – Interview im Radio",
            instruction = "Sie hören ein Radio-Interview. Welche Aussagen sind richtig?",
            transcript = """
                Moderatorin: Willkommen bei Radio Berlin. Heute sprechen wir mit Dr. Anna Schmidt über Work-Life-Balance. 
                Frau Schmidt, was verstehen Sie darunter?
                
                Dr. Schmidt: Work-Life-Balance bedeutet für mich, dass man Beruf und Privatleben in einem gesunden Gleichgewicht hält. 
                Das ist heutzutage besonders schwierig, weil viele Menschen auch nach der Arbeit noch E-Mails checken.
                
                Moderatorin: Was empfehlen Sie unseren Zuhörern?
                
                Dr. Schmidt: Drei Dinge: Erstens, feste Arbeitszeiten einhalten und das Handy nach 19 Uhr ausschalten. 
                Zweitens, jeden Tag mindestens 30 Minuten Bewegung. Und drittens, Zeit für Familie und Freunde planen – das ist kein Luxus, das ist Notwendigkeit.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(4, "Was empfiehlt Dr. Schmidt als erste Maßnahme?",
                    listOf("Sport treiben", "Das Handy ausschalten", "Mehr Urlaub nehmen"), 1,
                    "Sie empfiehlt, das Handy nach 19 Uhr auszuschalten."),
                MultipleChoiceQuestion(5, "Wie lange soll man laut Dr. Schmidt täglich Sport machen?",
                    listOf("15 Minuten", "30 Minuten", "Eine Stunde"), 1,
                    "Sie empfiehlt mindestens 30 Minuten Bewegung täglich.")
            )
        )
    ),
    schreibenTasks = listOf(
        SchreibenTask(
            taskNumber = 1,
            title = "Teil 1 – Mitteilung schreiben",
            prompt = """
                Sie haben gehört, dass Ihr Nachbar Markus Heizungsprobleme hat und für zwei Wochen zu seiner Familie verreist. 
                Er bat Sie, seine Wohnung im Blick zu haben.
                
                Schreiben Sie Ihrem Nachbarn Markus eine E-Mail (circa 80 Wörter):
                • Erklären Sie, wann Sie vorbeischauen
                • Teilen Sie mit, was Sie festgestellt haben
                • Fragen Sie, was Sie tun sollen, wenn ein Problem auftritt
            """.trimIndent(),
            minWords = 80,
            hints = listOf(
                "Benutzen Sie eine höfliche Anrede (Lieber Markus, ...)",
                "Verwenden Sie Konnektoren: zunächst, dann, außerdem, abschließend",
                "Achten Sie auf Modalverben: können, sollen, müssen",
                "Schreiben Sie zum Schluss eine freundliche Verabschiedung"
            )
        ),
        SchreibenTask(
            taskNumber = 2,
            title = "Teil 2 – Aufsatz",
            prompt = """
                Wählen Sie ein Thema und schreiben Sie einen Aufsatz (circa 150 Wörter):
                
                THEMA A: „Soziale Medien verbinden Menschen – oder trennen sie?"
                Beschreiben Sie Vor- und Nachteile sozialer Medien in der heutigen Gesellschaft.
                
                THEMA B: „Öffentliche Verkehrsmittel oder Auto – was ist besser?"
                Diskutieren Sie die Vor- und Nachteile beider Transportmittel.
                
                Beachten Sie:
                • Einleitung (Thema vorstellen)
                • Hauptteil (Argumente pro und contra)
                • Schluss (Ihre Meinung)
            """.trimIndent(),
            minWords = 150,
            hints = listOf(
                "Einleitung: Stellen Sie das Thema vor",
                "Hauptteil: Mindestens 2 Argumente pro Seite",
                "Benutzen Sie Meinungsausdrücke: Meiner Meinung nach, Ich denke, dass...",
                "Schluss: Fassen Sie zusammen und geben Sie Ihre Meinung",
                "Verbinden Sie Sätze mit: jedoch, obwohl, deshalb, trotzdem"
            )
        )
    ),
    sprechenTasks = listOf(
        SprechenTask(
            taskNumber = 1,
            title = "Teil 1 – Sich vorstellen",
            instruction = "Stellen Sie sich Ihrem Gesprächspartner vor.",
            topic = "Erzählen Sie über sich: Herkunft, Beruf/Studium, Familie, Hobbys und warum Sie Deutsch lernen.",
            prepTimeSec = 60,
            speakTimeSec = 120,
            tips = listOf(
                "Beginnen Sie mit Ihrem Namen: 'Ich heiße / Mein Name ist...'",
                "Erklären Sie, woher Sie kommen: 'Ich komme aus...'",
                "Sprechen Sie über Ihre Arbeit/Ihr Studium",
                "Erwähnen Sie 1-2 Hobbys mit Details",
                "Erklären Sie Ihre Motivation für Deutsch"
            )
        ),
        SprechenTask(
            taskNumber = 2,
            title = "Teil 2 – Gemeinsam planen",
            instruction = "Sie wollen mit Ihrem Partner etwas gemeinsam planen. Diskutieren Sie und einigen Sie sich.",
            topic = "Sie und ein Freund/eine Freundin wollen zusammen Urlaub machen. Einigen Sie sich auf: Reiseziel, Unterkunft, Transport und Aktivitäten.",
            prepTimeSec = 60,
            speakTimeSec = 180,
            tips = listOf(
                "Machen Sie Vorschläge: 'Wie wäre es mit...? / Was hältst du von...?'",
                "Zustimmen: 'Das ist eine gute Idee! / Einverstanden!'",
                "Ablehnen höflich: 'Ich bin nicht sicher... / Das gefällt mir nicht so gut, weil...'",
                "Um Meinung bitten: 'Was meinst du? / Was denkst du?'",
                "Kompromiss anbieten: 'Können wir vielleicht...?'"
            )
        ),
        SprechenTask(
            taskNumber = 3,
            title = "Teil 3 – Bild beschreiben",
            instruction = "Beschreiben Sie das Bild und äußern Sie Ihre Meinung dazu.",
            topic = "Das Bild zeigt Menschen in einem Café, die auf ihre Smartphones schauen, anstatt miteinander zu reden. Beschreiben Sie die Szene und sagen Sie, was Sie darüber denken.",
            prepTimeSec = 60,
            speakTimeSec = 120,
            tips = listOf(
                "Beschreiben Sie, was Sie sehen: 'Auf dem Bild sehe ich...'",
                "Lage beschreiben: 'Im Vordergrund / Im Hintergrund / Links / Rechts...'",
                "Vermutungen: 'Es scheint, dass... / Wahrscheinlich...'",
                "Meinung äußern: 'Ich finde das... / Meiner Meinung nach...'",
                "Bezug zur Realität: 'Das erinnert mich an...'"
            )
        )
    )
)

val GoetheExam2 = ExamContent(
    id = "goethe_2",
    name = "Modelltest 2",
    provider = ExamProvider.GOETHE,
    lesenParts = listOf(
        ReadingPart(
            partNumber = 1,
            title = "Teil 1 – Arbeitswelt",
            instruction = "Lesen Sie den Text und entscheiden Sie: Richtig oder Falsch?",
            text = """
                München – Die Vier-Tage-Woche wird in Deutschland immer beliebter. 
                Nach einer Pilotstudie berichten 80% der teilnehmenden Unternehmen von einer höheren Mitarbeiterzufriedenheit. 
                Die Produktivität blieb in den meisten Fällen gleich oder verbesserte sich sogar leicht. 
                Gewerkschaften fordern nun eine gesetzliche Regelung, während Arbeitgeberverbände vor steigenden Lohnkosten warnen. 
                Experten glauben, dass dieses Modell besonders für junge Fachkräfte ein wichtiges Argument bei der Jobsuche ist.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(1, "Die Vier-Tage-Woche senkt laut Studie die Produktivität massiv.", listOf("Richtig", "Falsch"), 1, "Text sagt: Produktivität blieb gleich oder verbesserte sich."),
                MultipleChoiceQuestion(2, "80% der Firmen sind zufriedener mit dem neuen Modell.", listOf("Richtig", "Falsch"), 0, "Text sagt: 80% berichten von höherer Zufriedenheit."),
                MultipleChoiceQuestion(3, "Junge Fachkräfte finden die Vier-Tage-Woche unattraktiv.", listOf("Richtig", "Falsch"), 1, "Experten glauben, es ist ein wichtiges Argument für junge Fachkräfte.")
            )
        ),
        ReadingPart(
            partNumber = 2,
            title = "Teil 2 – Wohnungsmarkt",
            instruction = "Welche Anzeige passt zu welcher Situation?",
            text = """
                Anzeige A: ZIMMER FREI
                Helles 15qm Zimmer in Studenten-WG in Köln-Deutz ab sofort. 350€ warm. 
                Wir suchen jemanden, der gerne zusammen kocht. kontakt@wg-deutz.de
                
                Anzeige B: FERIENWOHNUNG AN DER OSTSEE
                Gemütliche Fewo für 4 Personen in Binz, ruhig gelegen, 5 Min. zum Strand. 
                Ab 80€ pro Nacht. Haustiere erlaubt. www.ostsee-traum.de
                
                Anzeige C: GARAGE ZU VERMIETEN
                Trockene Garage in Berlin-Steglitz, nähe U-Bahn. 100€ monatlich. 
                Sofort frei. Tel: 030-987654
                
                Anzeige D: UMZUGSHELFer GESUCHT
                Suche 3 kräftige Helfer für Umzug am Samstag, 20. April. 
                15€/Std + Pizza. Bitte melden bei jan.mayer@web.de
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(4, "Lukas studiert in Köln und sucht eine günstige Bleibe.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D"), 0, "Anzeige A ist ein WG-Zimmer in Köln für 350€."),
                MultipleChoiceQuestion(5, "Familie Müller plant einen Sommerurlaub am Meer mit ihrem Hund.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D"), 1, "Anzeige B bietet eine Ferienwohnung an der Ostsee, Haustiere erlaubt."),
                MultipleChoiceQuestion(6, "Frau Schmidt wohnt in Steglitz und braucht einen Parkplatz für ihr neues Auto.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D"), 2, "Anzeige C bietet eine Garage in Steglitz an.")
            )
        ),
        ReadingPart(
            partNumber = 3,
            title = "Teil 3 – Klimawandel",
            instruction = "Lesen Sie den Text. Welche Antwort (a, b oder c) passt?",
            text = """
                Klimaschutz im Alltag
                
                Immer mehr Deutsche versuchen, ihren ökologischen Fußabdruck zu verkleinern. 
                Eine Umfrage ergab, dass 45% der Teilnehmer öfter das Fahrrad statt das Auto nutzen. 
                Besonders in Städten wächst das Bewusstsein für nachhaltige Mobilität. 
                Zudem verzichten 30% bewusst auf Flugreisen innerhalb Europas, um CO2-Emissionen zu vermeiden. 
                
                Ein weiterer Trend ist das „Zero Waste“-Prinzip. In vielen Städten eröffnen Unverpackt-Läden, 
                in denen Kunden ihre eigenen Behälter mitbringen. Ziel ist es, Plastikmüll komplett zu vermeiden. 
                Obwohl die Preise oft etwas höher sind als im Discounter, schätzen die Kunden die Qualität und 
                das gute Gewissen beim Einkauf.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(7, "Was machen fast die Hälfte der Befragten für den Klimaschutz?",
                    listOf("Sie fliegen weniger", "Sie fahren mehr Fahrrad", "Sie kaufen in Unverpackt-Läden"), 1,
                    "45% nutzen öfter das Fahrrad statt das Auto."),
                MultipleChoiceQuestion(8, "Warum fliegen 30% der Befragten nicht mehr innerhalb Europas?",
                    listOf("Es ist zu teuer", "Wegen der Umweltbelastung", "Weil sie lieber im Inland bleiben"), 1,
                    "Sie verzichten darauf, um CO2-Emissionen zu vermeiden."),
                MultipleChoiceQuestion(9, "Was ist das Hauptmerkmal von Unverpackt-Läden?",
                    listOf("Sie sind sehr günstig", "Man muss eigene Gefäße mitbringen", "Sie verkaufen nur Bio-Obst"), 1,
                    "Kunden bringen ihre eigenen Behälter mit.")
            )
        )
    ),
    hoerenParts = listOf(
        HoerenPart(
            partNumber = 1,
            title = "Teil 1 – Alltagssituationen",
            instruction = "Sie hören kurze Gespräche. Wählen Sie die richtige Antwort.",
            transcript = """
                [Gespräch 1]
                Kunde: Guten Tag, ich suche ein Geschenk für meine Frau. Vielleicht ein Parfum?
                Verkäuferin: Da haben wir gerade dieses neue Set im Angebot für 45 Euro.
                Kunde: Das riecht gut. Haben Sie auch Schmuck?
                Verkäuferin: Ja, in der ersten Etage, aber die schließt in 10 Minuten.
                
                [Gespräch 2]
                Frau: Hallo Peter, kommst du heute Abend mit ins Kino?
                Peter: Gern, wann fängt der Film an?
                Frau: Um acht, wir treffen uns um halb acht vor dem Kino.
                Peter: Oh, ich muss bis sieben arbeiten. Das wird knapp, aber ich schaffe es.
                
                [Gespräch 3]
                Passagier: Entschuldigung, ist dieser Platz noch frei?
                Frau: Ja, bitte setzen Sie sich.
                Passagier: Wissen Sie, ob der Bus auch am Marktplatz hält?
                Frau: Nein, er fährt direkt zum Bahnhof. Zum Marktplatz müssen Sie die Linie 12 nehmen.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(1, "Wo kann der Mann Schmuck finden?",
                    listOf("Hinter der Kasse", "In der ersten Etage", "Im Nebengebäude"), 1,
                    "Die Verkäuferin sagt: 'In der ersten Etage'."),
                MultipleChoiceQuestion(2, "Wann treffen sie sich vor dem Kino?",
                    listOf("19:00 Uhr", "19:30 Uhr", "20:00 Uhr"), 1,
                    "Sie treffen sich um halb acht, also 19:30 Uhr."),
                MultipleChoiceQuestion(3, "Wohin fährt dieser Bus?",
                    listOf("Zum Marktplatz", "Zum Bahnhof", "Direkt in die Stadt"), 1,
                    "Die Frau sagt: 'Er fährt direkt zum Bahnhof'.")
            )
        ),
        HoerenPart(
            partNumber = 2,
            title = "Teil 2 – Radiobericht",
            instruction = "Wählen Sie die richtige Antwort.",
            transcript = """
                Moderator: Heute bei uns im Studio: Marc Weber vom Verein 'Stadtwald'. Herr Weber, warum pflanzen Sie Bäume in der Stadt?
                Marc Weber: Bäume sind die Lungen der Stadt. Sie kühlen die Luft im Sommer um bis zu 5 Grad ab und filtern Staub.
                Moderator: Wer hilft Ihnen dabei?
                Marc Weber: Hauptsächlich Freiwillige. Letztes Wochenende hatten wir über 50 Helfer, sogar viele Kinder waren dabei. Wir hoffen, bis Jahresende 1000 neue Bäume gepflanzt zu haben.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(4, "Was ist ein Vorteil von Bäumen in der Stadt laut Herrn Weber?",
                    listOf("Sie spenden Schatten für Autos", "Sie kühlen die Luft ab", "Sie locken Vögel an"), 1,
                    "Er sagt, sie kühlen die Luft um bis zu 5 Grad ab."),
                MultipleChoiceQuestion(5, "Wie viele Bäume will der Verein insgesamt dieses Jahr pflanzen?",
                    listOf("50 Bäume", "100 Bäume", "1000 Bäume"), 2,
                    "Er hofft auf 1000 neue Bäume bis Jahresende.")
            )
        )
    ),
    schreibenTasks = listOf(
        SchreibenTask(
            taskNumber = 1,
            title = "Teil 1 – Einladung",
            prompt = """
                Sie haben nächste Woche Geburtstag und möchten eine Party feiern.
                Schreiben Sie eine E-Mail an Ihre Freunde (circa 80 Wörter):
                • Laden Sie sie zur Party ein
                • Sagen Sie, wann und wo die Party stattfindet
                • Erklären Sie, was die Gäste mitbringen sollen
                • Bitten Sie um eine Antwort bis Freitag
            """.trimIndent(),
            minWords = 80,
            hints = listOf(
                "Informelle Anrede",
                "Datum und Uhrzeit nennen",
                "Getränke oder Essen vorschlagen",
                "Frist für die Rückmeldung setzen"
            )
        ),
        SchreibenTask(
            taskNumber = 2,
            title = "Teil 2 – Meinung äußern",
            prompt = """
                Schreiben Sie einen kurzen Aufsatz (circa 150 Wörter) zum Thema:
                „Ist es sinnvoll, im Ausland zu studieren?"
                
                Berücksichtigen Sie folgende Punkte:
                • Warum entscheiden sich viele Studenten für ein Auslandssemester?
                • Was sind die Herausforderungen (z.B. Sprache, Heimweh)?
                • Ihre eigene Meinung dazu.
            """.trimIndent(),
            minWords = 150,
            hints = listOf(
                "Vorteile: Sprache lernen, neue Kultur",
                "Nachteile: Kosten, Distanz zur Familie",
                "Verknüpfungswörter benutzen",
                "Klare Struktur (Einleitung, Hauptteil, Schluss)"
            )
        )
    ),
    sprechenTasks = listOf(
        SprechenTask(
            taskNumber = 1,
            title = "Teil 1 – Kennenlernen",
            instruction = "Sprechen Sie über sich.",
            topic = "Stellen Sie sich vor: Name, Alter, Wohnort, Familie, Hobbys und Ihre Pläne für die Zukunft.",
            prepTimeSec = 60,
            speakTimeSec = 120,
            tips = listOf("Flüssig sprechen", "Details zu Hobbys nennen", "Zukunftspläne (Beruf/Reise) erläutern")
        ),
        SprechenTask(
            taskNumber = 2,
            title = "Teil 2 – Planung",
            instruction = "Planen Sie mit Ihrem Partner eine Überraschung.",
            topic = "Ein gemeinsamer Freund hat das B1-Zertifikat bestanden. Planen Sie ein kleines Geschenk und eine Feier.",
            prepTimeSec = 60,
            speakTimeSec = 180,
            tips = listOf("Vorschläge machen", "Auf Partner eingehen", "Termin und Budget klären")
        ),
        SprechenTask(
            taskNumber = 3,
            title = "Teil 3 – Präsentation",
            instruction = "Beschreiben Sie ein Bild und geben Sie Ihre Meinung.",
            topic = "Thema: Homeschooling. Das Bild zeigt ein Kind, das allein am Laptop lernt. Beschreiben Sie die Situation und diskutieren Sie Vor- und Nachteile.",
            prepTimeSec = 60,
            speakTimeSec = 120,
            tips = listOf("Bilddetails nennen", "Bezug zum Thema Unterricht / Internet", "Eigene Meinung zum Online-Lernen")
        )
    )
)

// ─── ÖSD B1 Content ──────────────────────────────────────────────────────────

val OesdExam1 = ExamContent(
    id = "oesd_1",
    name = "Modelltest 1",
    provider = ExamProvider.OESD,
    lesenParts = listOf(
        ReadingPart(
            partNumber = 1,
            title = "Teil 1 – AZUBI Clara",
            instruction = "Lesen Sie den Text und die Aufgaben 1 bis 6 dazu. Wählen Sie: Sind die Aussagen Richtig oder Falsch?",
            text = """
                AZUBI Clara Mein neues Leben hat begonnen! Seit gestern bin ich nun also Auszubildende (AZUBI) in der Firma und lerne im dualen System. Nach drei Jahren praktischer Ausbildung im Betrieb plus Berufsschule bin ich dann hoffentlich am Ziel: Bürokauffrau. Ja, jetzt denke ich schon an das Ziel, dabei war gestern gerade mal mein erster Tag in der Firma. Ich hatte Riesenglück, dass ich so schnell eine Ausbildungsstelle gefunden habe. Heutzutage ist das nämlich gar nicht so einfach. Meine Mitschülerinnen können ein Lied davon singen. Aber ich bin ein echter Glückspilz und so war auch mein erster Tag ein Glückstag. Früh morgens nahm ich die Straßenbahn zum Marktplatz und nach 5 Minuten Fußmarsch war ich pünktlich um 8 in der Firma. Ich meldete mich gleich in der Personalabteilung im Büro von Frau Mellert, die ich schon vom Vorstellungsgespräch kannte. Da war auch schon Piet, der mit mir zusammen die Ausbildung macht und in meiner Parallelklasse war. Frau Mellert führte uns als erstes durch die Firma. Sie stellte uns allen Kollegen und Kolleginnen vor und zeigte uns sämtliche Räume. Puh! Das Gebäude ist ein Labyrinth, ich glaube, ich brauche einen Kompass, um meinen Arbeitsplatz zu finden. Zum Glück konnte sich Piet den Weg ganz gut merken. Dafür kann ich mich an die Namen der Leute besser erinnern. Am Infoschalter sitzt ein lustiger Mensch mit blonden Haaren, ich glaube, er heißt Zimmermann. In der Buchhaltung habe ich mir Frau Hentel merken können, weil wir da auch gleich Papiere ausfüllen mussten. Herr Wagner ist unser Chef, mit dem werden wir aber nicht viel Kontakt haben, denn er ist ständig auf Reisen. Frau Lenzig ist für mich zuständig und Piet ist gleich im Büro nebenan bei Frau Andersson, die mit einem Schweden verheiratet ist. OK, die anderen Namen kommen später, morgen ist auch noch ein Tag! Das Wichtigste ist, dass im Haus noch mehr AZUBIs sind, nämlich Leon, Sandra, Tina und Vero! Sie haben uns zur Frühstückspause in der Kantine mit Butterbrezeln und Kaffee überrascht. Leon und Sandra sind im dritten Lehrjahr und machen bald Prüfungen. Tina und Vero sind im zweiten Lehrjahr und haben uns versprochen, uns bei allem zu helfen. Wie wir so gemütlich beisammen saßen, haben wir ihnen auch gleich Löcher in den Bauch gefragt, denn für uns ist ja alles so neu. Wer kennt sich schon aus mit den zukünftigen Aufgaben, wie Bürokommunikation, Personalwirtschaft, Marketing usw. Jedenfalls war es ein guter Anfang und übermorgen beginnt dann auch die Schule. ☺
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(1, "Clara schreibt über ihren ersten Schultag.", listOf("Richtig", "Falsch"), 1, "Sie schreibt über ihren ersten Tag in der Firma, die Schule beginnt erst übermorgen."),
                MultipleChoiceQuestion(2, "Die Ausbildung zur Bürokauffrau dauert 3 Jahre.", listOf("Richtig", "Falsch"), 0, "Text: 'Nach drei Jahren praktischer Ausbildung...'"),
                MultipleChoiceQuestion(3, "Clara und Piet waren rechtzeitig in der Firma.", listOf("Richtig", "Falsch"), 0, "Text: '...war ich pünktlich um 8 in der Firma.'"),
                MultipleChoiceQuestion(4, "Der Chef zeigt ihnen alles im Haus.", listOf("Richtig", "Falsch"), 1, "Frau Mellert aus der Personalabteilung führte sie durch die Firma."),
                MultipleChoiceQuestion(5, "Frau Lenzig organisiert Geschäftsreisen.", listOf("Richtig", "Falsch"), 1, "Herr Wagner ist ständig auf Reisen; Frau Lenzig ist für Clara zuständig."),
                MultipleChoiceQuestion(6, "Die Firma hat sechs AZUBIs.", listOf("Richtig", "Falsch"), 0, "Clara, Piet, Leon, Sandra, Tina und Vero = 6 AZUBIs."),
                MultipleChoiceQuestion(7, "In zwei Tagen ist der erste Schultag im dualen Ausbildungssystem.", listOf("Richtig", "Falsch"), 0, "Text: '...übermorgen beginnt dann auch die Schule.'")
            )
        ),
        ReadingPart(
            partNumber = 2,
            title = "Teil 2a – Weihnachten und ein Heim für Tiere",
            instruction = "Lesen Sie den Text aus der Presse und die Aufgaben 7 bis 9 dazu. Wählen Sie bei jeder Aufgabe die richtige Lösung a, b oder c.",
            text = """
                Weihnachten und ein Heim für Tiere
                In eigener Sache meldete sich ein Tierheim in Dortmund. Es ist bekannt, dass gerade in der Vorweihnachtszeit viele ein Tier aus dem Tierheim holen wollen, um die Lieben damit zu beschenken. Aber gerade davor warnt das Tierheim. Nach einer Umfrage, die im Oktober am „Tag der offenen Tür“ durchgeführt wurde, stimmte die Mehrheit der Besucher sogar für einen Vermittlungsstopp, demnach soll die Abgabe von Tieren an Interessenten in der Weihnachtszeit nicht möglich sein. Frau Scheffer, Vorsitzende des Tierschutzvereins unterstützt diese Maßnahme. Sie meint, dass die Freude über das Tier oft nicht lange anhält. Spätestens wenn die Besitzer erkennen, dass ein Tier nicht nur Liebe braucht und die täglichen Pflichten unangenehm sind, landen die neuen Freunde schnell wieder im Tierheim oder gar auf der Straße. Die Anschaffung eines Tieres sollte gut überlegt werden. Jedes Tier hat seine eigenen Bedürfnisse, die den zukünftigen Besitzer Raum, Geld und Zeit kosten. Eine wichtige Rolle spielt dabei auch die Lebenserwartung, denn ein Tier kann ein Begleiter für viele Jahre sein. Man verschenkt besser ein Stofftier, aber wer unbedingt ein lebendiges Tier verschenken will, sollte das mit den zukünftigen Besitzern und Angehörigen unterm Weihnachtsbaum besprechen. Im Januar kann dann das Versprechen guten Gewissens eingelöst werden.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(8, "Frau Scheffer sagt, ...", listOf("dass man die Liebe zum Tier pflegen muss.", "dass die Liebe zum Tier ein Leben lang hält.", "dass die Freude über das Tier oft schnell vergeht."), 2),
                MultipleChoiceQuestion(9, "Wer ein Tier möchte, ...", listOf("muss die Bedürfnisse des Tieres berücksichtigen.", "muss die Bedürfnisse des Besitzers kennen.", "braucht eine hohe Lebenserwartung."), 0),
                MultipleChoiceQuestion(10, "Wer ein Tier schenken möchte, ...", listOf("muss sich rechtzeitig melden.", "sollte das vorher mit den Besitzern klären.", "kann es unter den Weihnachtsbaum legen."), 1)
            )
        ),
        ReadingPart(
            partNumber = 3,
            title = "Teil 2b – Gelbe Postbusse",
            instruction = "Lesen Sie den Text aus der Presse und die Aufgaben 10 bis 12 dazu. Wählen Sie bei jeder Aufgabe die richtige Lösung a, b oder c.",
            text = """
                Kommen die gelben Postbusse wieder?
                Post und ADAC planen mit modernem Fernbusnetz der Bahn Konkurrenz zu machen. Die Konkurrenz belebt den Markt. Poststationen, wie man sie von alten Bildern her kennt. Die Post beforderte nicht nur Briefe und Pakete, sondern war schon immer ein beliebtes Reisemittel. Ob mit der Postkutsche oder mit dem Postbus, mit der Post fuhr man durch das Land, um von einem Ort zum anderen zu kommen. Was in den Sechzigerjahren nicht mehr rentabel war und darum eingestellt wurde, soll jetzt wieder eingerichtet werden. Dahinter steckt keine neue Idee, sondern ein neuer Partner. Die Kooperation ist mit dem Automobilclub ADAC geplant. Der Grund dafür ist, dass ab 2013 private Firmen bundesweiten Linienverkehr anbieten durfen. Das Monopol der Bahn für den Langstreckenverkehr wurde aufgehoben und gibt der Konkurrenz eine Chance. Kaufen wir in Zukunft im Postamt zusammen mit den Briefmarken unsere Fahrschein für gelbe Busse im ganzen Land? Die Konkurrenz schläft nicht. Private Omnibusunternehmen können ihr Fernstreckennetz leicht umstellen. Im Fokus steht die Zusammenarbeit vieler regionaler Anbieter unter einer gemeinsamen Organisation. Das gilt auch für Firmen außerhalb der Landesgrenzen. Interesse zeigen bereits Verkehrskonzerne aus Frankreich und Großbritannien, die Erfahrung mit Fernlinien haben.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(11, "Der Busfernverkehr ...", listOf("soll neu organisiert werden.", "ist ein Monopol der Bahn.", "ist nicht mehr rentabel."), 0),
                MultipleChoiceQuestion(12, "Private Busunternehmen ...", listOf("planen ein landesweites Streckennetz.", "wollen mit der Bahn zusammen arbeiten.", "haben nach 2013 keine Chance."), 0),
                MultipleChoiceQuestion(13, "Europäische Verkehrskonzerne ...", listOf("wollen mit der Deutschen Post zusammen arbeiten.", "können ihre Erfahrungen einbringen.", "ordnen sich regionalen Interessen unter."), 1)
            )
        ),
        ReadingPart(
            partNumber = 4,
            title = "Teil 3 – Anzeigen",
            instruction = "Welche Anzeige passt zu welcher Situation? Wählen Sie 0, falls keine passt.",
            text = """
                Anzeigen:
                A: Wohnbüro, 5 Zimmer, in Stuttgart in ruhiger Lage zu vermieten.
                B: Günstige Eigentumswohnungen in Norditalien, Mallorca, Nordgriechenland.
                C: München, 5-Zi.-Maisonette, am Park, ruhig, 160 m2, Kaufpreis: 399 000 Euro.
                D: Schöne, helle 2 1/2- Zimmerwohnung in Stuttgart-Bonlanden zu verkaufen.
                E: Hamburger Familie sucht Haus/Wohnung in München (01.06 - 01.09).
                F: Arzt sucht große 4-5-Zimmer-Wohnung oder Haus mit Garten.
                G: Studentenzimmer in Wohnheim in München zu vermieten.
                H: Seniorenresidenz „Waldidyll“, Appartements ab 1424 Euro monatlich.
                I: Massivblockhäuser zum Kauf am Sulmsee, 40.000 Euro.
                J: Werbefachmann sucht Büroräume, ca. 60 Quadratmeter im Großraum Stuttgart.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(14, "Jens hat ein altes Haus geerbt und möchte es renovieren lassen.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende Anzeige)"), 10),
                MultipleChoiceQuestion(15, "Frau Scheidt ist das Einfamilienhaus zu groß und sie möchte es vermieten.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende Anzeige)"), 0),
                MultipleChoiceQuestion(16, "Frau Wenzel ist Architektin und sucht ein größeres Wohnbüro.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende Anzeige)"), 9),
                MultipleChoiceQuestion(17, "Freunde aus Holland wollen sich eine kleine Wohnung in Stuttgart kaufen.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende Anzeige)"), 3),
                MultipleChoiceQuestion(18, "Sarah ist Studentin und sucht ein Zimmer in München.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende Anzeige)"), 6),
                MultipleChoiceQuestion(19, "Familie Walter möchte ihre Wohnung für drei Monate im Sommer vermieten.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende Anzeige)"), 10),
                MultipleChoiceQuestion(20, "Herr Rollberg will sich eine schöne und bequeme Wohnung in München kaufen.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende Anzeige)"), 2)
            )
        ),
        ReadingPart(
            partNumber = 5,
            title = "Teil 4 – Alternative Heilmethoden",
            instruction = "Ist die Person für alternative Heilmethoden? Wählen Sie Ja oder Nein.",
            text = """
                Lesermeinungen zum Thema Naturmedizin:
                20: Lena (18) erinnert sich an Großmutters Hausmittel.
                21: Nickel (26), Medizinstudent, hält alternative Therapien für unseriös.
                22: Ferdinand (48) vertraut nur der Schulmedizin bei echten Krankheiten.
                23: Steiner (39), Heilpraktiker, ist begeistert von neuen Erkenntnissen.
                24: Dr. Turm (61) bleibt überzeugter Schulmediziner.
                25: Sarah Wick (23) warnt vor Leichtgläubigkeit.
                26: Schwester M. (44) sieht in der Natur die Lösung für viele Leiden.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(21, "Lena, 18, Berlin", listOf("Ja", "Nein"), 0),
                MultipleChoiceQuestion(22, "Nickel, 26, Wallis", listOf("Ja", "Nein"), 1),
                MultipleChoiceQuestion(23, "Ferdinand, 48, Graz", listOf("Ja", "Nein"), 1),
                MultipleChoiceQuestion(24, "Steiner, 39, Köln", listOf("Ja", "Nein"), 0),
                MultipleChoiceQuestion(25, "Dr. Turm, 61, München", listOf("Ja", "Nein"), 1),
                MultipleChoiceQuestion(26, "Sarah Wick, 23, Rostock", listOf("Ja", "Nein"), 1),
                MultipleChoiceQuestion(27, "Schwester M., 44, Kehl", listOf("Ja", "Nein"), 0)
            )
        ),
        ReadingPart(
            partNumber = 6,
            title = "Teil 5 – Regeln Museumsbahn",
            instruction = "Lesen Sie die Regeln und wählen Sie die richtige Lösung a, b oder c.",
            text = """
                Liebe Besucher der Museumsbahn, beachten Sie bitte folgende Regeln:
                1. Auf dem gesamten Museumsgelände gilt die allgemeine Straßenverkehrsordnung. Auch hier hat der Schienenverkehr Vorrang vor dem Straßenverkehr.
                2. Das Museumsgelände ist kein Spielplatz. Lokomotiven und Waggons sind keine Sportgeräte. Es ist verboten, darauf herum zu klettern.
                3. Betreten streng verboten für Gleisanlagen und abgesperrte Bereiche.
                4. Bei Personenzügen darf nur in den dafür vorgesehenen Wagen mitgefahren werden.
                5. Bei fahrenden Zügen ist es verboten auf- oder abzuspringen.
                6. Während der Fahrt darf man sich nicht hinauslehnen und nach Pflanzen greifen.
                7. Dampfloks verursachen Schmutz. Keine Haftung übernommen.
                8. Hunde an der Leine führen.
                9. Fotografieren nur für private Zwecke erlaubt.
                10. Anweisungen des Personals befolgen.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(28, "Auf dem Gelände der Museumsbahn ...", listOf("dürfen keine Autos fahren.", "gelten die allgemeinen Verkehrsregeln.", "dürfen nur Züge fahren."), 1),
                MultipleChoiceQuestion(29, "Es ist nicht erlaubt, ...", listOf("die Fahrzeuge anzufassen.", "auf die Fahrzeugen zu klettern.", "Sport zu treiben."), 1),
                MultipleChoiceQuestion(30, "Es ist verboten, ...", listOf("während der Fahrt aufzustehen.", "Hunde mitzuführen.", "bei fahrendem Zug Blumen zu pflücken."), 2),
                MultipleChoiceQuestion(31, "Fotos ...", listOf("kann man am Kiosk kaufen.", "dürfen keine gemacht werden.", "dürfen nur für den privaten Gebrauch gemacht werden."), 2)
            )
        )
    ),
    hoerenParts = listOf(
        HoerenPart(
            partNumber = 1,
            title = "Teil 1 – Kurztexte",
            instruction = "Sie hören fünf kurze Texte. Lösen Sie die Aufgaben.",
            transcript = "Audiomaterial wird benötigt.",
            questions = listOf(
                MultipleChoiceQuestion(101, "Text 1: Die andalusischen Apfelsinen kosten 34 Cent das Kilo.", listOf("Richtig", "Falsch"), 0),
                MultipleChoiceQuestion(102, "Text 1: Das Angebot gilt ...", listOf("bis nächste Woche.", "solange es diese Produkte noch gibt.", "nur für Kunden mit der Bonuskarte."), 0),
                MultipleChoiceQuestion(103, "Text 2: Die Maschine ist in der Luft und fliegt nach Frankfurt.", listOf("Richtig", "Falsch"), 0),
                MultipleChoiceQuestion(104, "Text 2: Der Flug dauert heute länger aufgrund ...", listOf("des starken Flugverkehrs.", "des Gegenwinds.", "der verspäteten Starterlaubnis."), 0),
                MultipleChoiceQuestion(105, "Text 3: Der Zug fährt nicht bis Venedig.", listOf("Richtig", "Falsch"), 0),
                MultipleChoiceQuestion(106, "Text 3: Passagiere müssen in Turin ...", listOf("in einen Bus umsteigen.", "einen anderen Zug nehmen.", "auf Anweisungen warten."), 0)
            )
        ),
        HoerenPart(
            partNumber = 2,
            title = "Teil 2 – Uni-Rundgang",
            instruction = "Wählen Sie die richtige Lösung a, b oder c.",
            transcript = "Audiomaterial wird benötigt.",
            questions = listOf(
                MultipleChoiceQuestion(201, "Die Person, die die Einführung macht, ist ...", listOf("Student.", "vom Bibliothekspersonal.", "Mitarbeiter der Universität."), 0),
                MultipleChoiceQuestion(202, "Im Leseraum kann man ...", listOf("Magazine aus dem Sortiment lesen.", "nur nach Anmeldung lesen.", "nur Material aus dem Archiv lesen."), 0),
                MultipleChoiceQuestion(203, "Bücher über Partneruniversitäten erhält man ...", listOf("über das OPAC-Programm.", "mit einem Aufpreis.", "bei Frau Mertens."), 0)
            )
        ),
        HoerenPart(
            partNumber = 3,
            title = "Teil 3 – Gespräch im Café",
            instruction = "Sind die Aufgaben richtig oder falsch?",
            transcript = "Audiomaterial wird benötigt.",
            questions = listOf(
                MultipleChoiceQuestion(301, "Hannelore kam über eine Recherche zu ihrem Job.", listOf("Richtig", "Falsch"), 0),
                MultipleChoiceQuestion(302, "Hannelore war von der Familie begeistert.", listOf("Richtig", "Falsch"), 0),
                MultipleChoiceQuestion(303, "Hannelores Reise nach Australien war anstrengend.", listOf("Richtig", "Falsch"), 0)
            )
        ),
        HoerenPart(
            partNumber = 4,
            title = "Teil 4 – Diskussion: Englisch in der Gesellschaft",
            instruction = "Ordnen Sie die Aussagen zu: Wer sagt was?",
            transcript = "Moderatorin, Anna Wenz und Anton Grubauer diskutieren.",
            questions = listOf(
                MultipleChoiceQuestion(401, "Die deutsche Sprache hat auch die englische Sprache beeinflusst.", listOf("Moderatorin", "Anna Wenz", "Anton Grubauer"), 0),
                MultipleChoiceQuestion(402, "Nicht alle Bundesbürger sprechen Englisch.", listOf("Moderatorin", "Anna Wenz", "Anton Grubauer"), 0),
                MultipleChoiceQuestion(403, "Man braucht Zeit, ein neues Wort zu verstehen und zu benutzen.", listOf("Moderatorin", "Anna Wenz", "Anton Grubauer"), 0)
            )
        )
    ),
    schreibenTasks = listOf(
        SchreibenTask(
            taskNumber = 1,
            title = "Aufgabe 1 – E-Mail über einen Ausflug",
            prompt = """
                Sie haben am Wochenende einen Ausflug gemacht. 
                Beschreiben Sie: Wo waren Sie und wie ist die Stadt?
                Begründen Sie: Wie war der Ausflug?
                Empfehlen Sie Ihrem Freund/Ihrer Freundin, diese Stadt zu besuchen.
            """.trimIndent(),
            minWords = 80,
            hints = listOf("Anrede", "Einleitung", "Hauptteil", "Schluss")
        ),
        SchreibenTask(
            taskNumber = 2,
            title = "Aufgabe 2 – Blogbeitrag: Kinos sterben",
            prompt = """
                Gästebuch-Meinung: „Ich finde es schade, dass Kinos schließen. Kinobesuch mit Freunden ist besser als allein fernsehen.“
                Schreiben Sie Ihre Meinung dazu.
            """.trimIndent(),
            minWords = 80,
            hints = listOf("Eigene Meinung", "Begründung")
        ),
        SchreibenTask(
            taskNumber = 3,
            title = "Aufgabe 3 – Besichtigungstermin",
            prompt = """
                Der Vermieter, Herr Schneider, hat Ihnen einen Besichtigungstermin vorgeschlagen. 
                Bedanken Sie sich und schreiben Sie, ob Ihnen der Termin passt.
            """.trimIndent(),
            minWords = 40,
            hints = listOf("Höfliche Form", "Zusage/Absage")
        )
    ),
    sprechenTasks = listOf(
        SprechenTask(
            taskNumber = 1,
            title = "Teil 1 – Gemeinsam etwas planen",
            instruction = "Planen Sie den Besuch eines Freundes am Wochenende.",
            topic = "Treffpunkt, Verkehrsmittel, Sehenswürdigkeiten, Abendgestaltung.",
            prepTimeSec = 60,
            speakTimeSec = 180,
            tips = listOf("Vorschläge machen", "Reagieren", "Einigung finden")
        ),
        SprechenTask(
            taskNumber = 2,
            title = "Teil 2 – Thema präsentieren",
            instruction = "Präsentieren Sie das Thema: „Schokolade macht glücklich!“",
            topic = "Inhalt/Struktur, persönliche Erfahrungen, Situation im Heimatland, Vor-/Nachteile, Abschluss.",
            prepTimeSec = 120,
            speakTimeSec = 180,
            tips = listOf("Foliengliederung folgen", "Beispiele nennen")
        ),
        SprechenTask(
            taskNumber = 3,
            title = "Teil 3 – Über ein Thema sprechen",
            instruction = "Reagieren Sie auf Rückmeldungen und stellen Sie Fragen.",
            topic = "Feedback geben, eine Frage zur Präsentation des Partners stellen.",
            prepTimeSec = 30,
            speakTimeSec = 60,
            tips = listOf("Interessantes hervorheben", "Anschlussfrage stellen")
        )
    )
)


val OesdExam2 = ExamContent(
    id = "oesd_2",
    name = "Modelltest 2",
    provider = ExamProvider.OESD,
    lesenParts = listOf(
        ReadingPart(
            partNumber = 1,
            title = "Teil 1 – Mein Reisemosaik",
            instruction = "Lesen Sie den Text und die Aufgaben 1 bis 6 dazu. Wählen Sie: Sind die Aussagen Richtig oder Falsch?",
            text = """
                Mein Reisemosaik von Deutschland
                7. Etappe: Die Entscheidung meiner Reise - Familienbesuch
                Nachdem ich nun Metropolen und Ballungsgebiete hinter mir gelassen habe, wende ich mich dem landlichen Sudwesten zu. Die Hälfte meiner Familie stammt aus dem Schwarzwald, darum fuhr ich mit der Bahn gleich mal mitten hinein, nämlich ins Kinzigtal. Noch während die Landschaft mit ihren idyllischen Tälern und Orten an mir vorbeigleitet, steigen Erinnerungen in mir hoch. Dann hält der Zug im Bahnhof und ich steige aus. Ist es die Luft, ist es der Geruch nach frisch geschnittenem Heu: gleich waren sie da, die Bilder aus Kindertagen. Ich lächle und winke meinem Cousin, der mich mit dem Auto abholt. Erst einmal begrüßen und ankommen. So viele Augen, so viele Hände und Arme, herzliche Wärme empfängt mich. Es gibt auch gleich Vesper mit Bauernbrot, Wurst und Speck vom „Brettle“. Mit scharfem Messer hauchdünn geschnitten, zergeht mir die Erinnerung auf der Zunge. Als Getränk kann ich wählen zwischen Most vom Bauern oder doch ein „Tannenzäpfle“, falls ich Lust auf ein Bier habe. Das Wasser ist schließlich zum Waschen und für die Tiere da. Und mit der Nahrung nehme ich auch gleich ein Sprachbad. Wie lange habe ich diesen Klang der Sprache nicht gehört! Auch das ist Deutsch, eine Behauptung, für die mich die meisten meiner Kollegen spöttisch belächeln, weil Alemannisch für sie eine Fremdsprache ist. Dabei ist Dialekt heutzutage wieder im Trend. Wie viel ärmer wäre die Sprache ohne die Mundart. Vielleicht ist die Sprache der Umgebung auch so ein bisschen etwas wie Heimat, die man mit sich im Herzen trägt. Beim Abendspaziergang um den Waldsee, besprechen wir Pläne für eine kleine Wanderung am nächsten Tag. Nach langer Diskussion, ob es auf den Brandenkopf, zu den Nillhöfen oder auf die Heidburg gehen soll, beschließen wir das Auto in der Garage stehen zu lassen und nur so weit zu gehen, wie uns die Füße von der Haustüre aus tragen. Das war eine kluge und pragmatische Entscheidung. Zum Einen hätten wir gar nicht alle in ein Auto gepasst und es wäre ein zweites Auto nötig gewesen. Zum Anderen wollte ich schließlich Natur pur genießen. Noch während ich hier in meinen Laptop tippe, erinnere ich mich an glückliche Kindertage, rieche die Waldluft und freue mich auf morgen.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(1, "Der Bericht handelt von einem Reise-Souvenir aus Deutschland.", listOf("Richtig", "Falsch"), 1),
                MultipleChoiceQuestion(2, "Der Schreiber war zuerst in großen Städten.", listOf("Richtig", "Falsch"), 0),
                MultipleChoiceQuestion(3, "Ein Verwandter holt ihn ab.", listOf("Richtig", "Falsch"), 0),
                MultipleChoiceQuestion(4, "Nach der Ankunft gibt es etwas zu Essen.", listOf("Richtig", "Falsch"), 0),
                MultipleChoiceQuestion(5, "Sie trinken Wasser.", listOf("Richtig", "Falsch"), 1),
                MultipleChoiceQuestion(6, "Die Kollegen mögen den süddeutschen Dialekt.", listOf("Richtig", "Falsch"), 1),
                MultipleChoiceQuestion(7, "Sie wollen zu Fuß gehen und nicht mit dem Auto fahren.", listOf("Richtig", "Falsch"), 0)
            )
        ),
        ReadingPart(
            partNumber = 2,
            title = "Teil 2a – Bio-Äpfel",
            instruction = "Lesen Sie den Text aus der Presse und die Aufgaben 7 bis 9 dazu. Wählen Sie bei jeder Aufgabe die richtige Lösung a, b oder c.",
            text = """
                Sind Bio-Äpfel wirklich besser?
                Huch, Bio-Produkte sind gar nicht gesünder als konventionelle? Ein Skandal? Was für ein Quatsch. Wer Bio für grundsätzlich gesünder hält, der kann Bio-Zigaretten rauchen und sich von Bio-Schokolade ernähren. Nein, der Öko-Landwirtschaft geht es um Nachhaltigkeit. Und skandalös ist dabei etwas ganz anderes. Eine Studie bestätigt, was sich jeder mit ein bisschen Verstand auch selbst denken kann. Ein Apfel ist nicht einfach gesünder, nur weil er vom Bio-Bauern kommt. Viele Bio-Produkte entsprechen nicht den Geschmacksvorstellungen der Konsumenten. Der Begriff „gesund“ ist schon fragwürdig. Eine mit viel Butter und Zucker angerührte Schokoladentorte macht auch dann nicht schlank, wenn sie aus Bio-Produkten hergestellt wird. Auch ein Bio-Lutscher kann Karies verursachen. Und, ja, auch Tabak aus Bio-Anbau ist krebserregend. Wie immer, wenn es um Ernährung geht, geht es um ausgewogene Ernährung. Bio ist nicht gleich Bio. Wer Wert darauf legt, dass ein Produkt nicht nur weitgehend pestizidfrei ist und umweltschonend angebaut wird, muss schon genau hinsehen. Auch aufgepasst, wer sicher sein will, Fleisch von halbwegs glücklichen Hühnern, Schweinen und Rindern zu essen. Für die strengen Kriterien muss man leider meist auch mehr bezahlen, aber es lohnt sich. Bio hat nun mal seinen Preis, wenn im Einklang mit der Natur angebaut wurde.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(8, "Es wurde festgestellt,...", listOf("a) dass Bio-Äpfel besser schmecken.", "b) dass Bio-Bauern gesund leben.", "c) dass „Bio“ nicht selbstverständlich auch gesünder ist."), 2),
                MultipleChoiceQuestion(9, "Wer Bio-Qualität sucht,...", listOf("a) muss die Produkte genau prüfen.", "b) muss Glück haben.", "c) hat eine hohe Lebenserwartung."), 0),
                MultipleChoiceQuestion(10, "Der Preis für Bio-Produkte ...", listOf("a) wird streng kontrolliert.", "b) ist oft hoch.", "c) häng von der Natur ab."), 1)
            )
        ),
        ReadingPart(
            partNumber = 3,
            title = "Teil 2b – Europaschule Köln",
            instruction = "Lesen Sie den Text aus der Presse und die Aufgaben 10 bis 12 dazu. Wählen Sie bei jeder Aufgabe die richtige Lösung a, b oder c.",
            text = """
                Infofest der Europaschule Köln
                Großer Andrang herrschte beim diesjährigen Informationsfest der Europaschule in Köln. Wie jedes Jahr öffnete die Europaschule ihre Pforten, um sich allen Interessierten vorzustellen. Grundschulkinder und -eltern nahmen die Gelegenheit zu Unterrichtsbesuchen im 5. und 6. Jahrgang wahr. In separaten Veranstaltungen wurde über die möglichen Schullaufbahnen sowie das Schulprogramm in beiden Sekundarstufen informiert. Führungen durch das Schulgebäude sorgten für die nötige Orientierung. Vielfältige Ausstellungen, Aufführungen und Aktionen zeigten einen repräsentativen Querschnitt des Schulalltags. Der Schriftsteller Alexander Rothe wurde eingeladen und feierlich zum offiziellen Lesepaten der Europaschule ernannt, natürlich mit einer kurzen Lesung im Theatersaal. In der Vorstellung der Wahlsprachen lag der besondere Fokus in diesem Jahr auf dem Fach Russisch. Hierzu gab es ein buntes Programm mit verschiedenen Aufführungen und russischen Spezialitäten. Das Mensa-Team und die Schülerfirma „milchig“ sorgten für weitere Stärkungen im England-Café. Auch die Gäste konnten aktiv werden, beim Märchenquiz raten, in der Disko tanzen, ein Tombola-Los kaufen oder die Spieler vom Kickerturnier unterstützen. Natürlich ist ein solch umfangreiches Programm nicht ohne die Hilfe aller möglich. Ein besonderer Dank geht an alle Eltern, die Schülerinnen und Schüler, die Kolleginnen und Kollegen sowie die Mitarbeiter der Europaschule, die in der Vorbereitung dieses Tages mit großem Einsatz bei der Sache waren.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(11, "Die Besucher ...", listOf("a) verloren auf dem Fest die Orientierung.", "b) beschwerten sich über den großen Andrang auf dem Fest.", "c) konnten sich über die Europaschule in Köln informieren."), 2),
                MultipleChoiceQuestion(12, "Die Schüler ...", listOf("a) zeigten Beispiele aus dem täglichen Schulleben.", "b) lasen Texte des Schriftstellers Alexander Rothe.", "c) machten Werbung für die Schülerfirma „milchig“."), 0),
                MultipleChoiceQuestion(13, "Der Dank gilt ...", listOf("a) der Schulleitung.", "b) allen, die mitgeholfen haben.", "c) dem Programm-Team."), 1)
            )
        ),
        ReadingPart(
            partNumber = 4,
            title = "Teil 3 – Restaurant-Anzeigen",
            instruction = "Welche Anzeige passt zu welcher Situation? Wählen Sie 0, falls keine passt.",
            text = """
                Anzeigen:
                A: Waldau-Stuben – Bier vom Fass, Kaffee, Wurstsalat. Mo-Fr 6-21 Uhr.
                B: DER GRIECHE IM GRÜNEN – Gartenwirtschaft, griechische Spezialitäten, Mittagstisch.
                C: Banyan – Asia-Restaurant, vegetarische Speisen (China-Schnitzel, Rindersteak ohne Fleisch).
                D: Gasthof zum Ochsen – Täglich leckere Mittagsmenüs von 11.30-14.00 Uhr.
                E: Till Eulenspiegel – Restaurant mit Biergarten und Räumen für Familienfeste.
                F: Café Bellini – Italienischer Espresso und Kaffeespezialitäten.
                G: Pronto Pronto online – Italienische und mexikanische Lieferung online.
                H: Sushi digital – Online-Bestellung für Sushi.
                I: Bäckerei Konditorei Café – Lemon Pie und feine Backwaren.
                J: (Keine passende Anzeige)
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(14, "Herr Berger möchte mittags preiswert und gut essen gehen.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende)"), 3),
                MultipleChoiceQuestion(15, "Franz möchte einen richtig guten italienischen Espresso trinken.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende)"), 5),
                MultipleChoiceQuestion(16, "Herr Wengert möchte nach der Arbeit mit Kollegen Bier trinken und eine Kleinigkeit essen.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende)"), 0),
                MultipleChoiceQuestion(17, "Frau Ehlert sucht ein Restaurant mit Musik aus der Jugend ihrer Eltern.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende)"), 10),
                MultipleChoiceQuestion(18, "Frau Bär sucht ein Restaurant, wo sie vegetarisch essen kann.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende)"), 2),
                MultipleChoiceQuestion(19, "Frau Sulcher möchte ausländisch essen gehen und dabei draußen sitzen.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende)"), 1),
                MultipleChoiceQuestion(20, "Herr Thomas sucht einen Partyservice für eine Gartenparty.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende)"), 10)
            )
        ),
        ReadingPart(
            partNumber = 5,
            title = "Teil 4 – Extremsport",
            instruction = "Ist die Person gegen Extremsport? Wählen Sie Ja oder Nein.",
            text = """
                Lesermeinungen zum Extremsport:
                20: U. Filsmann hält Extremsport-Wettkämpfe für Unsinn und fordert deren Abschaffung.
                21: Norbert P. sieht in misslungener Organisation ein großes Risiko und will das Einstellen des Laufen.
                22: Wilma J. hält Extremsport für legitim, solange man sich der Risiken bewusst ist.
                23: Nicole findet es wichtig, eigene Fähigkeiten einzuschätzen, und will Wettkämpfe nicht verteufeln.
                24: Walther meint, mit besserer Organisation und Prüfung der Sportler hat Extremsport eine Chance.
                25: Dr. Rosner warnt davor, dass Sport zur Droge werden kann und plädiert für Verzicht auf Wettkämpfe.
                26: Svenja R. findet das Streben nach Leistungsmessung nach jahrelangem Training ganz natürlich.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(21, "U. Filsmann, 46, Landshut", listOf("Ja", "Nein"), 0),
                MultipleChoiceQuestion(22, "Norbert P., 52, Konstanz", listOf("Ja", "Nein"), 0),
                MultipleChoiceQuestion(23, "Wilma J., 39, Leipzig", listOf("Ja", "Nein"), 1),
                MultipleChoiceQuestion(24, "Nicole, 39, Freiburg", listOf("Ja", "Nein"), 1),
                MultipleChoiceQuestion(25, "Walther, 42, Baden-Baden", listOf("Ja", "Nein"), 1),
                MultipleChoiceQuestion(26, "Dr. Rosner, 56, Wien", listOf("Ja", "Nein"), 0),
                MultipleChoiceQuestion(27, "Svenja R., 29, Flensburg", listOf("Ja", "Nein"), 1)
            )
        ),
        ReadingPart(
            partNumber = 6,
            title = "Teil 5 – Zooführungen",
            instruction = "Lesen Sie den Text und wählen Sie die richtige Lösung a, b oder c.",
            text = """
                Geburtstagsführungen im Zoo Berlin
                Wir bieten Zooführungen mit Einblicken in den Zooalltag und jede Menge spannende Aktionen rund um die Tiere! In der Regel kann man auf jeder Tour füttern und zumeist auch einen Blick hinter die Kulissen riskieren. Die Gestaltung der Tour hängt auch vom Wetter ab.
                - Für Kinder ab 5 Jahren (Geburtstagskind und Gäste)
                - Mindestens 1 Erwachsener als Begleitperson
                - Mo-Fr um 14.00 und 15.00 Uhr (Winter) / 15.30 Uhr (Sommer)
                - Wochenende um 11.00, 13.00 und 15.00 Uhr
                Dauer: Eine Stunde und etwas mehr.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(28, "Der Berliner Zoo ...", listOf("a) feiert Geburtstag.", "b) feiert mit den Tieren Geburtstag.", "c) bietet Geburtstagsführungen an."), 2),
                MultipleChoiceQuestion(29, "Das Programm ...", listOf("a) beinhaltet meistens auch eine Fütterung.", "b) ist bei Regen riskant.", "c) wird in der Regel bei schönem Wetter geplant."), 0),
                MultipleChoiceQuestion(30, "Zielgruppen sind ...", listOf("a) Besucher unter fünf Jahren.", "b) Erwachsene mit Kindern.", "c) Kinder im Alter ab fünf."), 2),
                MultipleChoiceQuestion(31, "Die Führung ...", listOf("a) dauert den ganzen Tag.", "b) ist an Wochentagen nur nachmittags möglich.", "c) kann man Samstag und Sonntag nur vormittags buchen."), 1)
            )
        )
    ),
    hoerenParts = listOf(
        HoerenPart(
            partNumber = 1,
            title = "Teil 1 – Kurztexte",
            instruction = "Fünf kurze Texte. Wählen Sie die richtige Lösung.",
            transcript = "Audiomaterial wird benötigt.",
            questions = listOf(
                MultipleChoiceQuestion(101, "Text 1: Man kann ab dem 12. April indische Tiger und Löwen sehen.", listOf("Richtig", "Falsch"), 0),
                MultipleChoiceQuestion(102, "Text 1: Der Tierpark ist ...", listOf("täglich geöffnet.", "bis zum 12. April geöffnet.", "erst ab dem 12. April geöffnet."), 0),
                MultipleChoiceQuestion(103, "Text 2: Das Möbelhaus verschenkt nur 200 Euro-Wertgutscheine.", listOf("Richtig", "Falsch"), 0),
                MultipleChoiceQuestion(104, "Text 2: Die Wertgutscheine ...", listOf("gelten nur für die Sommerkollektion.", "gelten nur für Möbelstücke mit dem roten Punkt.", "gelten für alle Möbelstücke im ganzen Möbelhaus."), 0),
                MultipleChoiceQuestion(105, "Text 3: Gaby bekommt Besuch von ihrer Mutter.", listOf("Richtig", "Falsch"), 0)
            )
        ),
        HoerenPart(
            partNumber = 2,
            title = "Teil 2 – Burgführung",
            instruction = "Wählen Sie die richtige Lösung a, b oder c.",
            transcript = "Audiomaterial wird benötigt.",
            questions = listOf(
                MultipleChoiceQuestion(201, "Die Führung erzählt das Leben ...", listOf("der Ritter und Krieger.", "des Grafen Friedbergs.", "des Grafen Wertburgs."), 0),
                MultipleChoiceQuestion(202, "Die Burg entstand ...", listOf("im 6. Jahrhundert.", "im Jahr 1545.", "in der Neuzeit."), 0),
                MultipleChoiceQuestion(203, "Warum schrieb Graf Friedberg Märchen?", listOf("Es machte ihm Spaß.", "Er wollte seinem Sohn eine Freude machen.", "Er wollte ein Märchenbuch schreiben."), 0)
            )
        ),
        HoerenPart(
            partNumber = 3,
            title = "Teil 3 – Dschungelwanderer",
            instruction = "Sind die Aufgaben Richtig oder Falsch?",
            transcript = "Audiomaterial wird benötigt.",
            questions = listOf(
                MultipleChoiceQuestion(301, "Die Dschungelwanderung war eine spontane Entscheidung.", listOf("Richtig", "Falsch"), 0),
                MultipleChoiceQuestion(302, "Die Dschungelwanderer waren nicht immer in Begleitung.", listOf("Richtig", "Falsch"), 0),
                MultipleChoiceQuestion(303, "Der Sonnenuntergang in den Anden war wunderschön.", listOf("Richtig", "Falsch"), 0)
            )
        ),
        HoerenPart(
            partNumber = 4,
            title = "Teil 4 – Deutsche im Ausland",
            instruction = "Ordnen Sie die Aussagen zu: Wer sagt was?",
            transcript = "Moderatorin, Bernd Bechstein und Ulrike Meyer diskutieren.",
            questions = listOf(
                MultipleChoiceQuestion(401, "Nur ältere Menschen verbinden die Deutschen noch mit dem Krieg.", listOf("Moderatorin", "Ulrike Meyer", "Bernd Bechstein"), 0),
                MultipleChoiceQuestion(402, "Es gibt Menschen, die mehr als Deutsche arbeiten.", listOf("Moderatorin", "Ulrike Meyer", "Bernd Bechstein"), 0),
                MultipleChoiceQuestion(403, "Ausländer sind Deutschen gegenüber sehr freundlich.", listOf("Moderatorin", "Ulrike Meyer", "Bernd Bechstein"), 0)
            )
        )
    ),
    schreibenTasks = listOf(
        SchreibenTask(
            taskNumber = 1,
            title = "Aufgabe 1 – Italienischkurs",
            prompt = """
                Sie machen einen Italienischkurs für Ihren Urlaub.
                Begründen Sie die Wahl des Instituts.
                Beschreiben Sie den Unterricht.
                Schlagen Sie Ihrem Freund vor, auch zu lernen.
            """.trimIndent(),
            minWords = 80,
            hints = listOf("Begründung", "Unterricht", "Vorschlag")
        ),
        SchreibenTask(
            taskNumber = 2,
            title = "Aufgabe 2 – Blog: Make-up für 10-Jährige",
            prompt = """
                Meinung: „Schminken für kleine Mädchen unmöglich! Zeitungen wissen nicht mehr was sie schreiben sollen.“
                Schreiben Sie Ihre Meinung dazu.
            """.trimIndent(),
            minWords = 80,
            hints = listOf("Eigene Meinung")
        ),
        SchreibenTask(
            taskNumber = 3,
            title = "Aufgabe 3 – Absage Goldene Hochzeit",
            prompt = """
                Einladung zur goldenen Hochzeit von Herrn/Frau Sanders. Sie sind verreist.
                Bedanken Sie sich und sagen Sie ab.
            """.trimIndent(),
            minWords = 40,
            hints = listOf("Dank", "Absage")
        )
    ),
    sprechenTasks = listOf(
        SprechenTask(
            taskNumber = 1,
            title = "Teil 1 – Überraschungsparty organisieren",
            instruction = "Planen Sie eine Überraschungsparty für einen Freund.",
            topic = "Ort, Gäste, Einkauf, Information.",
            prepTimeSec = 60,
            speakTimeSec = 180,
            tips = listOf("Vorschläge machen", "Entscheiden")
        ),
        SprechenTask(
            taskNumber = 2,
            title = "Teil 2 – Thema präsentieren",
            instruction = "Präsentieren Sie das Thema: „Ist Facebook nützlich?“",
            topic = "Erfahrung, Situation im Heimatland, Vor-/Nachteile, Abschluss.",
            prepTimeSec = 120,
            speakTimeSec = 180,
            tips = listOf("Struktur folgen", "Beispiele")
        ),
        SprechenTask(
            taskNumber = 3,
            title = "Teil 3 – Über ein Thema sprechen",
            instruction = "Reagieren Sie auf Rückmeldungen und stellen Sie Fragen.",
            topic = "Rückmeldung geben und Frage stellen zur Partner-Präsentation.",
            prepTimeSec = 30,
            speakTimeSec = 60,
            tips = listOf("Feedback", "Frage")
        )
    )
)

// ─── All Exams Map ────────────────────────────────────────────────────────────

val telcExam1 = ExamContent(
    id = "telc_1",
    provider = ExamProvider.TELC,
    name = "Modelltest 1",
    lesenParts = listOf(
        ReadingPart(
            partNumber = 1,
            title = "Teil 1 – Informationstexte",
            instruction = "Welche Überschrift passt zu welchem Text?",
            text = """
                Text 1: Die Deutschen reisen gerne. Letztes Jahr war Spanien wieder das beliebteste Ziel, gefolgt von Italien. Knapp 20% machten Urlaub im eigenen Land.
                Text 2: Das Internet verändert unser Kaufverhalten. Immer mehr Menschen bestellen Kleidung und Technik online, statt in die Stadt zu gehen.
                Text 3: Sportvereine haben Nachwuchssorgen. Viele Jugendliche spielen lieber Videospiele, als zum Fußballtraining zu gehen.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(1, "Text 1 handelt von...", listOf("Reisegewohnheiten", "Gesunde Ernährung", "Verkehrsmittel"), 0),
                MultipleChoiceQuestion(2, "Text 2 beschreibt...", listOf("Online-Shopping", "Den Buchhandel", "Mode-Trends"), 0),
                MultipleChoiceQuestion(3, "Text 3 thematisiert...", listOf("Probleme in Sportvereinen", "Neue Videospiele", "Berühmte Fußballer"), 0)
            )
        ),
        ReadingPart(
            partNumber = 2,
            title = "Teil 2 – Zeitungsartikel",
            instruction = "Lesen Sie den Text und beantworten Sie die Fragen.",
            text = """
                Erfolg durch Weiterbildung
                In der heutigen Arbeitswelt ist lebenslanges Lernen unerlässlich. Wer stehen bleibt, verliert den Anschluss. 
                Die Bundesregierung unterstützt deshalb Arbeitnehmer mit dem sogenannten 'Bildungsscheck'. 
                Damit können bis zu 50% der Kursgebühren für berufliche Fortbildungen financed werden.
                Besonders gefragt sind derzeit IT-Kenntnisse und Fremdsprachen. 
                Arbeitgeber schätzen Mitarbeiter, die Eigeninitiative zeigen und sich in ihrer Freizeit weiterbilden.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(4, "Was ist der 'Bildungsscheck'?", listOf("Ein Gehaltsbonus", "Eine finanzielle Hilfe für Kurse", "Ein Zeugnis"), 1),
                MultipleChoiceQuestion(5, "Welche Kurse sind besonders populär?", listOf("Kochen und Backen", "IT und Sprachen", "Sport und Tanz"), 1)
            )
        )
    ),
    hoerenParts = listOf(
        HoerenPart(
            partNumber = 1,
            title = "Teil 1 – Kurzmitteilungen",
            instruction = "Hören Sie die Kurzinformationen.",
            transcript = """
                Ansage 1: Verehrte Kunden, der Supermarkt schließt in 15 Minuten. Bitte kommen Sie zur Kasse.
                Ansage 2: Achtung am Gleis 3. Der Regionalexpress nach Köln hat heute 20 Minuten Verspätung.
                Ansage 3: Hier spricht die Polizei. Wegen eines Unfalls ist die Hauptstraße gesperrt. Bitte umfahren Sie den Bereich.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(1, "Der Supermarkt...", listOf("öffnet gerade", "schließt bald", "hat Sonderangebote"), 1),
                MultipleChoiceQuestion(2, "Der Zug nach Köln...", listOf("fällt aus", "ist pünktlich", "kommt später"), 2)
            )
        )
    ),
    schreibenTasks = listOf(
        SchreibenTask(
            taskNumber = 1,
            title = "Teil 1 – Beschwerdebrief",
            prompt = """
                Sie haben online ein Handy bestellt, aber es ist beschädigt angekommen. 
                Schreiben Sie an die Firma 'TechWorld':
                • Grund Ihres Schreibens
                • Beschreibung des Schadens
                • Forderung (Reparatur oder Ersatz)
                • Fristsetzung
            """.trimIndent(),
            minWords = 100,
            hints = listOf("Formelle Anrede", "Bestellnummer nennen", "Höflich aber bestimmt bleiben")
        )
    ),
    sprechenTasks = listOf(
        SprechenTask(
            taskNumber = 1,
            title = "Teil 1 – Kontaktaufnahme",
            instruction = "Sprechen Sie mit Ihrem Partner über ein Thema.",
            topic = "Thema: Kleidung und Mode. Tragen Sie gerne Markenkleidung? Wie wichtig ist Ihnen Ihr Aussehen?",
            prepTimeSec = 60,
            speakTimeSec = 120,
            tips = listOf("Eigene Meinung sagen", "Partner fragen", "Beispiele geben")
        ),
        SprechenTask(
            taskNumber = 2,
            title = "Teil 2 – Ein Thema präsentieren",
            instruction = "Präsentieren Sie: 'Brauchen Kinder ein eigenes Smartphone?'",
            topic = "Erfahrung, Situation im Heimatland, Vorteile/Nachteile, Meinung.",
            prepTimeSec = 60,
            speakTimeSec = 180,
            tips = listOf("Struktur einhalten", "Vor- und Nachteile abwägen")
        )
    )
)

val telcExam2 = ExamContent(
    id = "telc_2",
    provider = ExamProvider.TELC,
    name = "Modelltest 2",
    lesenParts = listOf(
        ReadingPart(
            partNumber = 1,
            title = "Teil 1 – Wohnen und Umwelt",
            instruction = "Welche Überschrift passt?",
            text = """
                Text 1: Solarstrom wird immer günstiger. Viele Hausbesitzer installieren Photovoltaik-Anlagen auf ihren Dächern, um Stromkosten zu sparen und die Umwelt zu schonen.
                Text 2: Die Mieten in Großstädten steigen weiter. Besonders junge Familien finden kaum noch bezahlbaren Wohnraum in zentrumsnahen Vierteln.
                Text 3: Urban Gardening ist im Trend. Bewohner von Großstädten pflanzen Gemüse und Kräuter in Kisten auf ihren Balkonen oder in Gemeinschaftsgärten.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(1, "Text 1 thematisiert...", listOf("Solarenergie privat nutzen", "Neue Fenster einbauen", "Strom sparen im Haushalt"), 0),
                MultipleChoiceQuestion(2, "Text 2 beschreibt...", listOf("Wohnungsnot in Städten", "Hausbau auf dem Land", "Umzugstipps für Familien"), 0),
                MultipleChoiceQuestion(3, "Text 3 handelt von...", listOf("Gärtnern in der Stadt", "Kochen mit Kräutern", "Urlaub auf dem Bauernhof"), 0)
            )
        )
    ),
    hoerenParts = listOf(
        HoerenPart(
            partNumber = 1,
            title = "Teil 1 – Nachrichten",
            instruction = "Was ist richtig?",
            transcript = """
                Meldung 1: Morgen gibt es im ganzen Land viel Sonnenschein und Temperaturen bis zu 25 Grad. Erst am Abend ziehen Gewitter auf.
                Meldung 2: Der Streik am Flughafen Frankfurt wurde beendet. Ab sofort starten und landen die Flugzeuge wieder planmäßig.
                Meldung 3: Die Autobahn A8 ist nach einem Unfall gesperrt. Reisende Richtung Stuttgart müssen mit langen Staus rechnen.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(1, "Wie wird das Wetter morgen?", listOf("Regnerisch", "Sonnig und warm", "Kalt und windig"), 1),
                MultipleChoiceQuestion(2, "Am Flughafen Frankfurt...", listOf("wird weiter gestreikt", "gibt es keine Flüge mehr", "läuft alles wieder normal"), 2)
            )
        )
    ),
    schreibenTasks = listOf(
        SchreibenTask(
            taskNumber = 1,
            title = "Teil 1 – Bewerbung um ein Praktikum",
            prompt = """
                Sie haben eine Anzeige für ein Praktikum im Hotel 'Sonnenblick' gesehen. 
                Schreiben Sie eine E-Mail an die Personalabteilung:
                • Warum interessieren Sie sich für das Praktikum?
                • Welche Kenntnisse bringen Sie mit?
                • Wann haben Sie Zeit?
            """.trimIndent(),
            minWords = 80,
            hints = listOf("Formelle Anrede", "Berufserfahrung erwähnen", "Höfliche Verabschiedung")
        )
    ),
    sprechenTasks = listOf(
        SprechenTask(
            taskNumber = 1,
            title = "Teil 1 – Über Erfahrungen sprechen",
            instruction = "Erzählen Sie Ihrem Partner von Ihrer letzten Reise.",
            topic = "Wohin sind Sie gereist? Was haben Sie erlebt? Was war das Highlight?",
            prepTimeSec = 60,
            speakTimeSec = 120,
            tips = listOf("Vergangenheit benutzen", "Emotionen einbauen")
        )
    )
)

val allExams: Map<ExamProvider, List<ExamContent>> = mapOf(
    ExamProvider.GOETHE to listOf(GoetheExam1, GoetheExam2),
    ExamProvider.OESD to listOf(OesdExam1, OesdExam2),
    ExamProvider.TELC to listOf(telcExam1, telcExam2)
)
