package com.deutschb1.data

import com.deutschb1.R

// ─── Exam Provider ───────────────────────────────────────────────────────────

enum class ExamProvider(val displayName: String, val description: String) {
    GOETHE("Goethe-Institut", "Offizielles Goethe-Zertifikat B1"),
    OESD("ÖSD", "Österreichisches Sprachdiplom Deutsch"),
    TELC("TELC", "The European Language Certificates")
}

@androidx.annotation.DrawableRes
fun ExamProvider.toIconRes(): Int = when (this) {
    ExamProvider.GOETHE -> R.drawable.ic_goethe
    ExamProvider.OESD   -> R.drawable.ic_osd
    ExamProvider.TELC   -> R.drawable.ic_telc
}

fun ExamProvider.toBrandColor(): androidx.compose.ui.graphics.Color = when (this) {
    ExamProvider.GOETHE -> androidx.compose.ui.graphics.Color(0xFF00A550)   // Goethe green
    ExamProvider.OESD   -> androidx.compose.ui.graphics.Color(0xFF0070C0)   // ÖSD blue
    ExamProvider.TELC   -> androidx.compose.ui.graphics.Color(0xFFE2001A)   // TELC red
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
    val id: String,
    val text: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String = ""
)

typealias MCQuestion = MultipleChoiceQuestion
typealias LesenPart = ReadingPart


data class TrueFalseQuestion(
    val id: String,
    val statement: String,
    val isTrue: Boolean,
    val explanation: String = ""
)

data class MatchingItem(val left: String, val right: String)

data class ReadingPart(
    val partNumber: Int = 0,
    val title: String,
    val instruction: String,
    val text: String = "",
    val contextText: String? = null,
    val questions: List<MultipleChoiceQuestion>
)

data class HoerenPart(
    val partNumber: Int = 0,
    val title: String,
    val instruction: String,
    val transcript: String,
    val questions: List<MultipleChoiceQuestion>,
    val audioAssetPath: String = ""
)

data class SchreibenTask(
    val taskNumber: Int = 0,
    val title: String,
    val instruction: String,
    val wordCount: String,
    val keyPoints: List<String>
)

data class SprechenTask(
    val taskNumber: Int = 0,
    val title: String,
    val instruction: String,
    val topics: List<String> = emptyList(),
    val prepTimeSec: Int = 0,
    val speakTimeSec: Int = 0,
    val tips: List<String> = emptyList()
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
                MultipleChoiceQuestion("1", "Berlin will seinen öffentlichen Nahverkehr bis 2030 komplett elektrifizieren.", listOf("Richtig", "Falsch"), 0, "Im Text steht: 'bis 2030 den öffentlichen Nahverkehr vollständig auf elektrische Busse umzustellen'."),
                MultipleChoiceQuestion("2", "Die E-Busse werden vollständig aus städtischen Mitteln finanziert.", listOf("Richtig", "Falsch"), 1, "Der Text sagt, dass europäische Fördermittel genutzt werden sollen."),
                MultipleChoiceQuestion("3", "Die BVG plant, bis 2025 mehr als die Hälfte ihrer Busse zu elektrifizieren.", listOf("Richtig", "Falsch"), 0, "BVG will bis Ende 2025 mindestens 50 Prozent elektrifizieren.")
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
                MultipleChoiceQuestion("4", "Sven lernt Deutsch und kann gut rechnen.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D"), 0, "Er kann Mathe beibringen und sucht Deutschkurs = Anzeige A passt."),
                MultipleChoiceQuestion("5", "Maria braucht jemanden, der nachmittags auf ihre Kinder aufpasst.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D"), 3, "Anzeige D sucht Babysitter nachmittags mittwochs."),
                MultipleChoiceQuestion("6", "Thomas zieht nach Berlin und sucht eine günstige Unterkunft.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D"), 1, "Anzeige B bietet Zimmer in WG an.")
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
                MultipleChoiceQuestion("7", "Wie viele Deutsche essen laut dem Text kein Fleisch?",
                    listOf("Fast 2 Millionen", "Fast 9 Millionen", "Fast 10 Millionen"), 1,
                    "10% der Deutschen = fast neun Millionen Menschen."),
                MultipleChoiceQuestion("8", "Was ist der häufigste Grund für Vegetarismus in Deutschland?",
                    listOf("Gesundheit", "Tierschutz", "Umweltschutz"), 1,
                    "Tierschutz wird von 68% der Befragten als Hauptgrund genannt."),
                MultipleChoiceQuestion("9", "Wie hat sich die Zahl der veganen Restaurants in München entwickelt?",
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
                MultipleChoiceQuestion("1", "Wann fährt der nächste Zug nach München?",
                    listOf("14:23 Uhr", "14:32 Uhr", "15:05 Uhr"), 1,
                    "Der Zug fährt um 14:32 Uhr."),
                MultipleChoiceQuestion("2", "Wann muss Herr Müller den Bericht fertigstellen?",
                    listOf("In zwei Stunden", "Um 16 Uhr", "Morgen früh"), 1,
                    "Der Chef braucht den Bericht bis 16 Uhr."),
                MultipleChoiceQuestion("3", "Was nimmt der Patient gegen Kopfschmerzen?",
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
                MultipleChoiceQuestion("4", "Was empfiehlt Dr. Schmidt als erste Maßnahme?",
                    listOf("Sport treiben", "Das Handy ausschalten", "Mehr Urlaub nehmen"), 1,
                    "Sie empfiehlt, das Handy nach 19 Uhr auszuschalten."),
                MultipleChoiceQuestion("5", "Wie lange soll man laut Dr. Schmidt täglich Sport machen?",
                    listOf("15 Minuten", "30 Minuten", "Eine Stunde"), 1,
                    "Sie empfiehlt mindestens 30 Minuten Bewegung täglich.")
            )
        )
    ),
    schreibenTasks = listOf(
        SchreibenTask(
            taskNumber = 1,
            title = "Teil 1 – Mitteilung schreiben",
            instruction = """
                Sie haben gehört, dass Ihr Nachbar Markus Heizungsprobleme hat und für zwei Wochen zu seiner Familie verreist. 
                Er bat Sie, seine Wohnung im Blick zu haben.
                
                Schreiben Sie Ihrem Nachbarn Markus eine E-Mail (circa 80 Wörter):
                • Erklären Sie, wann Sie vorbeischauen
                • Teilen Sie mit, was Sie festgestellt haben
                • Fragen Sie, was Sie tun sollen, wenn ein Problem auftritt
            """.trimIndent(),
            wordCount = "80",
            keyPoints = listOf(
                "Benutzen Sie eine höfliche Anrede (Lieber Markus, ...)",
                "Verwenden Sie Konnektoren: zunächst, dann, außerdem, abschließend",
                "Achten Sie auf Modalverben: können, sollen, müssen",
                "Schreiben Sie zum Schluss eine freundliche Verabschiedung"
            )
        ),
        SchreibenTask(
            taskNumber = 2,
            title = "Teil 2 – Aufsatz",
            instruction = """
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
            wordCount = "150",
            keyPoints = listOf(
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
            topics = listOf("Erzählen Sie über sich: Herkunft, Beruf/Studium, Familie, Hobbys und warum Sie Deutsch lernen."),
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
            topics = listOf("Sie und ein Freund/eine Freundin wollen zusammen Urlaub machen. Einigen Sie sich auf: Reiseziel, Unterkunft, Transport und Aktivitäten."),
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
            topics = listOf("Das Bild zeigt Menschen in einem Café, die auf ihre Smartphones schauen, anstatt miteinander zu reden. Beschreiben Sie die Szene und sagen Sie, was Sie darüber denken."),
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
                MultipleChoiceQuestion("1", "Die Vier-Tage-Woche senkt laut Studie die Produktivität massiv.", listOf("Richtig", "Falsch"), 1, "Text sagt: Produktivität blieb gleich oder verbesserte sich."),
                MultipleChoiceQuestion("2", "80% der Firmen sind zufriedener mit dem neuen Modell.", listOf("Richtig", "Falsch"), 0, "Text sagt: 80% berichten von höherer Zufriedenheit."),
                MultipleChoiceQuestion("3", "Junge Fachkräfte finden die Vier-Tage-Woche unattraktiv.", listOf("Richtig", "Falsch"), 1, "Experten glauben, es ist ein wichtiges Argument für junge Fachkräfte.")
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
                MultipleChoiceQuestion("4", "Lukas studiert in Köln und sucht eine günstige Bleibe.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D"), 0, "Anzeige A ist ein WG-Zimmer in Köln für 350€."),
                MultipleChoiceQuestion("5", "Familie Müller plant einen Sommerurlaub am Meer mit ihrem Hund.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D"), 1, "Anzeige B bietet eine Ferienwohnung an der Ostsee, Haustiere erlaubt."),
                MultipleChoiceQuestion("6", "Frau Schmidt wohnt in Steglitz und braucht einen Parkplatz für ihr neues Auto.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D"), 2, "Anzeige C bietet eine Garage in Steglitz an.")
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
                MultipleChoiceQuestion("7", "Was machen fast die Hälfte der Befragten für den Klimaschutz?",
                    listOf("Sie fliegen weniger", "Sie fahren mehr Fahrrad", "Sie kaufen in Unverpackt-Läden"), 1,
                    "45% nutzen öfter das Fahrrad statt das Auto."),
                MultipleChoiceQuestion("8", "Warum fliegen 30% der Befragten nicht mehr innerhalb Europas?",
                    listOf("Es ist zu teuer", "Wegen der Umweltbelastung", "Weil sie lieber im Inland bleiben"), 1,
                    "Sie verzichten darauf, um CO2-Emissionen zu vermeiden."),
                MultipleChoiceQuestion("9", "Was ist das Hauptmerkmal von Unverpackt-Läden?",
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
                MultipleChoiceQuestion("1", "Wo kann der Mann Schmuck finden?",
                    listOf("Hinter der Kasse", "In der ersten Etage", "Im Nebengebäude"), 1,
                    "Die Verkäuferin sagt: 'In der ersten Etage'."),
                MultipleChoiceQuestion("2", "Wann treffen sie sich vor dem Kino?",
                    listOf("19:00 Uhr", "19:30 Uhr", "20:00 Uhr"), 1,
                    "Sie treffen sich um halb acht, also 19:30 Uhr."),
                MultipleChoiceQuestion("3", "Wohin fährt dieser Bus?",
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
                MultipleChoiceQuestion("4", "Was ist ein Vorteil von Bäumen in der Stadt laut Herrn Weber?",
                    listOf("Sie spenden Schatten für Autos", "Sie kühlen die Luft ab", "Sie locken Vögel an"), 1,
                    "Er sagt, sie kühlen die Luft um bis zu 5 Grad ab."),
                MultipleChoiceQuestion("5", "Wie viele Bäume will der Verein insgesamt dieses Jahr pflanzen?",
                    listOf("50 Bäume", "100 Bäume", "1000 Bäume"), 2,
                    "Er hofft auf 1000 neue Bäume bis Jahresende.")
            )
        )
    ),
    schreibenTasks = listOf(
        SchreibenTask(
            taskNumber = 1,
            title = "Teil 1 – Einladung",
            instruction = """
                Sie haben nächste Woche Geburtstag und möchten eine Party feiern.
                Schreiben Sie eine E-Mail an Ihre Freunde (circa 80 Wörter):
                • Laden Sie sie zur Party ein
                • Sagen Sie, wann und wo die Party stattfindet
                • Erklären Sie, was die Gäste mitbringen sollen
                • Bitten Sie um eine Antwort bis Freitag
            """.trimIndent(),
            wordCount = "80",
            keyPoints = listOf(
                "Informelle Anrede",
                "Datum und Uhrzeit nennen",
                "Getränke oder Essen vorschlagen",
                "Frist für die Rückmeldung setzen"
            )
        ),
        SchreibenTask(
            taskNumber = 2,
            title = "Teil 2 – Meinung äußern",
            instruction = """
                Schreiben Sie einen kurzen Aufsatz (circa 150 Wörter) zum Thema:
                „Ist es sinnvoll, im Ausland zu studieren?"
                
                Berücksichtigen Sie folgende Punkte:
                • Warum entscheiden sich viele Studenten für ein Auslandssemester?
                • Was sind die Herausforderungen (z.B. Sprache, Heimweh)?
                • Ihre eigene Meinung dazu.
            """.trimIndent(),
            wordCount = "150",
            keyPoints = listOf(
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
            topics = listOf("Stellen Sie sich vor: Name, Alter, Wohnort, Familie, Hobbys und Ihre Pläne für die Zukunft."),
            prepTimeSec = 60,
            speakTimeSec = 120,
            tips = listOf("Flüssig sprechen", "Details zu Hobbys nennen", "Zukunftspläne (Beruf/Reise) erläutern")
        ),
        SprechenTask(
            taskNumber = 2,
            title = "Teil 2 – Planung",
            instruction = "Planen Sie mit Ihrem Partner eine Überraschung.",
            topics = listOf("Ein gemeinsamer Freund hat das B1-Zertifikat bestanden. Planen Sie ein kleines Geschenk und eine Feier."),
            prepTimeSec = 60,
            speakTimeSec = 180,
            tips = listOf("Vorschläge machen", "Auf Partner eingehen", "Termin und Budget klären")
        ),
        SprechenTask(
            taskNumber = 3,
            title = "Teil 3 – Präsentation",
            instruction = "Beschreiben Sie ein Bild und geben Sie Ihre Meinung.",
            topics = listOf("Thema: Homeschooling. Das Bild zeigt ein Kind, das allein am Laptop lernt. Beschreiben Sie die Situation und diskutieren Sie Vor- und Nachteile."),
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
                MultipleChoiceQuestion("1", "Clara schreibt über ihren ersten Schultag.", listOf("Richtig", "Falsch"), 1, "Sie schreibt über ihren ersten Tag in der Firma, die Schule beginnt erst übermorgen."),
                MultipleChoiceQuestion("2", "Die Ausbildung zur Bürokauffrau dauert 3 Jahre.", listOf("Richtig", "Falsch"), 0, "Text: 'Nach drei Jahren praktischer Ausbildung...'"),
                MultipleChoiceQuestion("3", "Clara und Piet waren rechtzeitig in der Firma.", listOf("Richtig", "Falsch"), 0, "Text: '...war ich pünktlich um 8 in der Firma.'"),
                MultipleChoiceQuestion("4", "Der Chef zeigt ihnen alles im Haus.", listOf("Richtig", "Falsch"), 1, "Frau Mellert aus der Personalabteilung führte sie durch die Firma."),
                MultipleChoiceQuestion("5", "Frau Lenzig organisiert Geschäftsreisen.", listOf("Richtig", "Falsch"), 1, "Herr Wagner ist ständig auf Reisen; Frau Lenzig ist für Clara zuständig."),
                MultipleChoiceQuestion("6", "Die Firma hat sechs AZUBIs.", listOf("Richtig", "Falsch"), 0, "Clara, Piet, Leon, Sandra, Tina und Vero = 6 AZUBIs."),
                MultipleChoiceQuestion("7", "In zwei Tagen ist der erste Schultag im dualen Ausbildungssystem.", listOf("Richtig", "Falsch"), 0, "Text: '...übermorgen beginnt dann auch die Schule.'")
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
                MultipleChoiceQuestion("8", "Frau Scheffer sagt, ...", listOf("dass man die Liebe zum Tier pflegen muss.", "dass die Liebe zum Tier ein Leben lang hält.", "dass die Freude über das Tier oft schnell vergeht."), 2),
                MultipleChoiceQuestion("9", "Wer ein Tier möchte, ...", listOf("muss die Bedürfnisse des Tieres berücksichtigen.", "muss die Bedürfnisse des Besitzers kennen.", "braucht eine hohe Lebenserwartung."), 0),
                MultipleChoiceQuestion("10", "Wer ein Tier schenken möchte, ...", listOf("muss sich rechtzeitig melden.", "sollte das vorher mit den Besitzern klären.", "kann es unter den Weihnachtsbaum legen."), 1)
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
                MultipleChoiceQuestion("11", "Der Busfernverkehr ...", listOf("soll neu organisiert werden.", "ist ein Monopol der Bahn.", "ist nicht mehr rentabel."), 0),
                MultipleChoiceQuestion("12", "Private Busunternehmen ...", listOf("planen ein landesweites Streckennetz.", "wollen mit der Bahn zusammen arbeiten.", "haben nach 2013 keine Chance."), 0),
                MultipleChoiceQuestion("13", "Europäische Verkehrskonzerne ...", listOf("wollen mit der Deutschen Post zusammen arbeiten.", "können ihre Erfahrungen einbringen.", "ordnen sich regionalen Interessen unter."), 1)
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
                MultipleChoiceQuestion("14", "Jens hat ein altes Haus geerbt und möchte es renovieren lassen.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende Anzeige)"), 10),
                MultipleChoiceQuestion("15", "Frau Scheidt ist das Einfamilienhaus zu groß und sie möchte es vermieten.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende Anzeige)"), 0),
                MultipleChoiceQuestion("16", "Frau Wenzel ist Architektin und sucht ein größeres Wohnbüro.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende Anzeige)"), 9),
                MultipleChoiceQuestion("17", "Freunde aus Holland wollen sich eine kleine Wohnung in Stuttgart kaufen.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende Anzeige)"), 3),
                MultipleChoiceQuestion("18", "Sarah ist Studentin und sucht ein Zimmer in München.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende Anzeige)"), 6),
                MultipleChoiceQuestion("19", "Familie Walter möchte ihre Wohnung für drei Monate im Sommer vermieten.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende Anzeige)"), 10),
                MultipleChoiceQuestion("20", "Herr Rollberg will sich eine schöne und bequeme Wohnung in München kaufen.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende Anzeige)"), 2)
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
                MultipleChoiceQuestion("21", "Lena, 18, Berlin", listOf("Ja", "Nein"), 0),
                MultipleChoiceQuestion("22", "Nickel, 26, Wallis", listOf("Ja", "Nein"), 1),
                MultipleChoiceQuestion("23", "Ferdinand, 48, Graz", listOf("Ja", "Nein"), 1),
                MultipleChoiceQuestion("24", "Steiner, 39, Köln", listOf("Ja", "Nein"), 0),
                MultipleChoiceQuestion("25", "Dr. Turm, 61, München", listOf("Ja", "Nein"), 1),
                MultipleChoiceQuestion("26", "Sarah Wick, 23, Rostock", listOf("Ja", "Nein"), 1),
                MultipleChoiceQuestion("27", "Schwester M., 44, Kehl", listOf("Ja", "Nein"), 0)
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
                MultipleChoiceQuestion("28", "Auf dem Gelände der Museumsbahn ...", listOf("dürfen keine Autos fahren.", "gelten die allgemeinen Verkehrsregeln.", "dürfen nur Züge fahren."), 1),
                MultipleChoiceQuestion("29", "Es ist nicht erlaubt, ...", listOf("die Fahrzeuge anzufassen.", "auf die Fahrzeugen zu klettern.", "Sport zu treiben."), 1),
                MultipleChoiceQuestion("30", "Es ist verboten, ...", listOf("während der Fahrt aufzustehen.", "Hunde mitzuführen.", "bei fahrendem Zug Blumen zu pflücken."), 2),
                MultipleChoiceQuestion("31", "Fotos ...", listOf("kann man am Kiosk kaufen.", "dürfen keine gemacht werden.", "dürfen nur für den privaten Gebrauch gemacht werden."), 2)
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
                MultipleChoiceQuestion("101", "Text 1: Die andalusischen Apfelsinen kosten 34 Cent das Kilo.", listOf("Richtig", "Falsch"), 0),
                MultipleChoiceQuestion("102", "Text 1: Das Angebot gilt ...", listOf("bis nächste Woche.", "solange es diese Produkte noch gibt.", "nur für Kunden mit der Bonuskarte."), 0),
                MultipleChoiceQuestion("103", "Text 2: Die Maschine ist in der Luft und fliegt nach Frankfurt.", listOf("Richtig", "Falsch"), 0),
                MultipleChoiceQuestion("104", "Text 2: Der Flug dauert heute länger aufgrund ...", listOf("des starken Flugverkehrs.", "des Gegenwinds.", "der verspäteten Starterlaubnis."), 0),
                MultipleChoiceQuestion("105", "Text 3: Der Zug fährt nicht bis Venedig.", listOf("Richtig", "Falsch"), 0),
                MultipleChoiceQuestion("106", "Text 3: Passagiere müssen in Turin ...", listOf("in einen Bus umsteigen.", "einen anderen Zug nehmen.", "auf Anweisungen warten."), 0)
            )
        ),
        HoerenPart(
            partNumber = 2,
            title = "Teil 2 – Uni-Rundgang",
            instruction = "Wählen Sie die richtige Lösung a, b oder c.",
            transcript = "Audiomaterial wird benötigt.",
            questions = listOf(
                MultipleChoiceQuestion("201", "Die Person, die die Einführung macht, ist ...", listOf("Student.", "vom Bibliothekspersonal.", "Mitarbeiter der Universität."), 0),
                MultipleChoiceQuestion("202", "Im Leseraum kann man ...", listOf("Magazine aus dem Sortiment lesen.", "nur nach Anmeldung lesen.", "nur Material aus dem Archiv lesen."), 0),
                MultipleChoiceQuestion("203", "Bücher über Partneruniversitäten erhält man ...", listOf("über das OPAC-Programm.", "mit einem Aufpreis.", "bei Frau Mertens."), 0)
            )
        ),
        HoerenPart(
            partNumber = 3,
            title = "Teil 3 – Gespräch im Café",
            instruction = "Sind die Aufgaben richtig oder falsch?",
            transcript = "Audiomaterial wird benötigt.",
            questions = listOf(
                MultipleChoiceQuestion("301", "Hannelore kam über eine Recherche zu ihrem Job.", listOf("Richtig", "Falsch"), 0),
                MultipleChoiceQuestion("302", "Hannelore war von der Familie begeistert.", listOf("Richtig", "Falsch"), 0),
                MultipleChoiceQuestion("303", "Hannelores Reise nach Australien war anstrengend.", listOf("Richtig", "Falsch"), 0)
            )
        ),
        HoerenPart(
            partNumber = 4,
            title = "Teil 4 – Diskussion: Englisch in der Gesellschaft",
            instruction = "Ordnen Sie die Aussagen zu: Wer sagt was?",
            transcript = "Moderatorin, Anna Wenz und Anton Grubauer diskutieren.",
            questions = listOf(
                MultipleChoiceQuestion("401", "Die deutsche Sprache hat auch die englische Sprache beeinflusst.", listOf("Moderatorin", "Anna Wenz", "Anton Grubauer"), 0),
                MultipleChoiceQuestion("402", "Nicht alle Bundesbürger sprechen Englisch.", listOf("Moderatorin", "Anna Wenz", "Anton Grubauer"), 0),
                MultipleChoiceQuestion("403", "Man braucht Zeit, ein neues Wort zu verstehen und zu benutzen.", listOf("Moderatorin", "Anna Wenz", "Anton Grubauer"), 0)
            )
        )
    ),
    schreibenTasks = listOf(
        SchreibenTask(
            taskNumber = 1,
            title = "Aufgabe 1 – E-Mail über einen Ausflug",
            instruction = """
                Sie haben am Wochenende einen Ausflug gemacht. 
                Beschreiben Sie: Wo waren Sie und wie ist die Stadt?
                Begründen Sie: Wie war der Ausflug?
                Empfehlen Sie Ihrem Freund/Ihrer Freundin, diese Stadt zu besuchen.
            """.trimIndent(),
            wordCount = "80",
            keyPoints = listOf("Anrede", "Einleitung", "Hauptteil", "Schluss")
        ),
        SchreibenTask(
            taskNumber = 2,
            title = "Aufgabe 2 – Blogbeitrag: Kinos sterben",
            instruction = """
                Gästebuch-Meinung: „Ich finde es schade, dass Kinos schließen. Kinobesuch mit Freunden ist besser als allein fernsehen.“
                Schreiben Sie Ihre Meinung dazu.
            """.trimIndent(),
            wordCount = "80",
            keyPoints = listOf("Eigene Meinung", "Begründung")
        ),
        SchreibenTask(
            taskNumber = 3,
            title = "Aufgabe 3 – Besichtigungstermin",
            instruction = """
                Der Vermieter, Herr Schneider, hat Ihnen einen Besichtigungstermin vorgeschlagen. 
                Bedanken Sie sich und schreiben Sie, ob Ihnen der Termin passt.
            """.trimIndent(),
            wordCount = "40",
            keyPoints = listOf("Höfliche Form", "Zusage/Absage")
        )
    ),
    sprechenTasks = listOf(
        SprechenTask(
            taskNumber = 1,
            title = "Teil 1 – Gemeinsam etwas planen",
            instruction = "Planen Sie den Besuch eines Freundes am Wochenende.",
            topics = listOf("Treffpunkt, Verkehrsmittel, Sehenswürdigkeiten, Abendgestaltung."),
            prepTimeSec = 60,
            speakTimeSec = 180,
            tips = listOf("Vorschläge machen", "Reagieren", "Einigung finden")
        ),
        SprechenTask(
            taskNumber = 2,
            title = "Teil 2 – Thema präsentieren",
            instruction = "Präsentieren Sie das Thema: „Schokolade macht glücklich!“",
            topics = listOf("Inhalt/Struktur, persönliche Erfahrungen, Situation im Heimatland, Vor-/Nachteile, Abschluss."),
            prepTimeSec = 120,
            speakTimeSec = 180,
            tips = listOf("Foliengliederung folgen", "Beispiele nennen")
        ),
        SprechenTask(
            taskNumber = 3,
            title = "Teil 3 – Über ein Thema sprechen",
            instruction = "Reagieren Sie auf Rückmeldungen und stellen Sie Fragen.",
            topics = listOf("Feedback geben, eine Frage zur Präsentation des Partners stellen."),
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
                MultipleChoiceQuestion("1", "Der Bericht handelt von einem Reise-Souvenir aus Deutschland.", listOf("Richtig", "Falsch"), 1),
                MultipleChoiceQuestion("2", "Der Schreiber war zuerst in großen Städten.", listOf("Richtig", "Falsch"), 0),
                MultipleChoiceQuestion("3", "Ein Verwandter holt ihn ab.", listOf("Richtig", "Falsch"), 0),
                MultipleChoiceQuestion("4", "Nach der Ankunft gibt es etwas zu Essen.", listOf("Richtig", "Falsch"), 0),
                MultipleChoiceQuestion("5", "Sie trinken Wasser.", listOf("Richtig", "Falsch"), 1),
                MultipleChoiceQuestion("6", "Die Kollegen mögen den süddeutschen Dialekt.", listOf("Richtig", "Falsch"), 1),
                MultipleChoiceQuestion("7", "Sie wollen zu Fuß gehen und nicht mit dem Auto fahren.", listOf("Richtig", "Falsch"), 0)
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
                MultipleChoiceQuestion("8", "Es wurde festgestellt,...", listOf("a) dass Bio-Äpfel besser schmecken.", "b) dass Bio-Bauern gesund leben.", "c) dass „Bio“ nicht selbstverständlich auch gesünder ist."), 2),
                MultipleChoiceQuestion("9", "Wer Bio-Qualität sucht,...", listOf("a) muss die Produkte genau prüfen.", "b) muss Glück haben.", "c) hat eine hohe Lebenserwartung."), 0),
                MultipleChoiceQuestion("10", "Der Preis für Bio-Produkte ...", listOf("a) wird streng kontrolliert.", "b) ist oft hoch.", "c) häng von der Natur ab."), 1)
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
                MultipleChoiceQuestion("11", "Die Besucher ...", listOf("a) verloren auf dem Fest die Orientierung.", "b) beschwerten sich über den großen Andrang auf dem Fest.", "c) konnten sich über die Europaschule in Köln informieren."), 2),
                MultipleChoiceQuestion("12", "Die Schüler ...", listOf("a) zeigten Beispiele aus dem täglichen Schulleben.", "b) lasen Texte des Schriftstellers Alexander Rothe.", "c) machten Werbung für die Schülerfirma „milchig“."), 0),
                MultipleChoiceQuestion("13", "Der Dank gilt ...", listOf("a) der Schulleitung.", "b) allen, die mitgeholfen haben.", "c) dem Programm-Team."), 1)
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
                MultipleChoiceQuestion("14", "Herr Berger möchte mittags preiswert und gut essen gehen.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende)"), 3),
                MultipleChoiceQuestion("15", "Franz möchte einen richtig guten italienischen Espresso trinken.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende)"), 5),
                MultipleChoiceQuestion("16", "Herr Wengert möchte nach der Arbeit mit Kollegen Bier trinken und eine Kleinigkeit essen.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende)"), 0),
                MultipleChoiceQuestion("17", "Frau Ehlert sucht ein Restaurant mit Musik aus der Jugend ihrer Eltern.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende)"), 10),
                MultipleChoiceQuestion("18", "Frau Bär sucht ein Restaurant, wo sie vegetarisch essen kann.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende)"), 2),
                MultipleChoiceQuestion("19", "Frau Sulcher möchte ausländisch essen gehen und dabei draußen sitzen.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende)"), 1),
                MultipleChoiceQuestion("20", "Herr Thomas sucht einen Partyservice für eine Gartenparty.", listOf("Anzeige A", "Anzeige B", "Anzeige C", "Anzeige D", "Anzeige E", "Anzeige F", "Anzeige G", "Anzeige H", "Anzeige I", "Anzeige J", "0 (Keine passende)"), 10)
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
                MultipleChoiceQuestion("21", "U. Filsmann, 46, Landshut", listOf("Ja", "Nein"), 0),
                MultipleChoiceQuestion("22", "Norbert P., 52, Konstanz", listOf("Ja", "Nein"), 0),
                MultipleChoiceQuestion("23", "Wilma J., 39, Leipzig", listOf("Ja", "Nein"), 1),
                MultipleChoiceQuestion("24", "Nicole, 39, Freiburg", listOf("Ja", "Nein"), 1),
                MultipleChoiceQuestion("25", "Walther, 42, Baden-Baden", listOf("Ja", "Nein"), 1),
                MultipleChoiceQuestion("26", "Dr. Rosner, 56, Wien", listOf("Ja", "Nein"), 0),
                MultipleChoiceQuestion("27", "Svenja R., 29, Flensburg", listOf("Ja", "Nein"), 1)
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
                MultipleChoiceQuestion("28", "Der Berliner Zoo ...", listOf("a) feiert Geburtstag.", "b) feiert mit den Tieren Geburtstag.", "c) bietet Geburtstagsführungen an."), 2),
                MultipleChoiceQuestion("29", "Das Programm ...", listOf("a) beinhaltet meistens auch eine Fütterung.", "b) ist bei Regen riskant.", "c) wird in der Regel bei schönem Wetter geplant."), 0),
                MultipleChoiceQuestion("30", "Zielgruppen sind ...", listOf("a) Besucher unter fünf Jahren.", "b) Erwachsene mit Kindern.", "c) Kinder im Alter ab fünf."), 2),
                MultipleChoiceQuestion("31", "Die Führung ...", listOf("a) dauert den ganzen Tag.", "b) ist an Wochentagen nur nachmittags möglich.", "c) kann man Samstag und Sonntag nur vormittags buchen."), 1)
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
                MultipleChoiceQuestion("101", "Text 1: Man kann ab dem 12. April indische Tiger und Löwen sehen.", listOf("Richtig", "Falsch"), 0),
                MultipleChoiceQuestion("102", "Text 1: Der Tierpark ist ...", listOf("täglich geöffnet.", "bis zum 12. April geöffnet.", "erst ab dem 12. April geöffnet."), 0),
                MultipleChoiceQuestion("103", "Text 2: Das Möbelhaus verschenkt nur 200 Euro-Wertgutscheine.", listOf("Richtig", "Falsch"), 0),
                MultipleChoiceQuestion("104", "Text 2: Die Wertgutscheine ...", listOf("gelten nur für die Sommerkollektion.", "gelten nur für Möbelstücke mit dem roten Punkt.", "gelten für alle Möbelstücke im ganzen Möbelhaus."), 0),
                MultipleChoiceQuestion("105", "Text 3: Gaby bekommt Besuch von ihrer Mutter.", listOf("Richtig", "Falsch"), 0)
            )
        ),
        HoerenPart(
            partNumber = 2,
            title = "Teil 2 – Burgführung",
            instruction = "Wählen Sie die richtige Lösung a, b oder c.",
            transcript = "Audiomaterial wird benötigt.",
            questions = listOf(
                MultipleChoiceQuestion("201", "Die Führung erzählt das Leben ...", listOf("der Ritter und Krieger.", "des Grafen Friedbergs.", "des Grafen Wertburgs."), 0),
                MultipleChoiceQuestion("202", "Die Burg entstand ...", listOf("im 6. Jahrhundert.", "im Jahr 1545.", "in der Neuzeit."), 0),
                MultipleChoiceQuestion("203", "Warum schrieb Graf Friedberg Märchen?", listOf("Es machte ihm Spaß.", "Er wollte seinem Sohn eine Freude machen.", "Er wollte ein Märchenbuch schreiben."), 0)
            )
        ),
        HoerenPart(
            partNumber = 3,
            title = "Teil 3 – Dschungelwanderer",
            instruction = "Sind die Aufgaben Richtig oder Falsch?",
            transcript = "Audiomaterial wird benötigt.",
            questions = listOf(
                MultipleChoiceQuestion("301", "Die Dschungelwanderung war eine spontane Entscheidung.", listOf("Richtig", "Falsch"), 0),
                MultipleChoiceQuestion("302", "Die Dschungelwanderer waren nicht immer in Begleitung.", listOf("Richtig", "Falsch"), 0),
                MultipleChoiceQuestion("303", "Der Sonnenuntergang in den Anden war wunderschön.", listOf("Richtig", "Falsch"), 0)
            )
        ),
        HoerenPart(
            partNumber = 4,
            title = "Teil 4 – Deutsche im Ausland",
            instruction = "Ordnen Sie die Aussagen zu: Wer sagt was?",
            transcript = "Moderatorin, Bernd Bechstein und Ulrike Meyer diskutieren.",
            questions = listOf(
                MultipleChoiceQuestion("401", "Nur ältere Menschen verbinden die Deutschen noch mit dem Krieg.", listOf("Moderatorin", "Ulrike Meyer", "Bernd Bechstein"), 0),
                MultipleChoiceQuestion("402", "Es gibt Menschen, die mehr als Deutsche arbeiten.", listOf("Moderatorin", "Ulrike Meyer", "Bernd Bechstein"), 0),
                MultipleChoiceQuestion("403", "Ausländer sind Deutschen gegenüber sehr freundlich.", listOf("Moderatorin", "Ulrike Meyer", "Bernd Bechstein"), 0)
            )
        )
    ),
    schreibenTasks = listOf(
        SchreibenTask(
            taskNumber = 1,
            title = "Aufgabe 1 – Italienischkurs",
            instruction = """
                Sie machen einen Italienischkurs für Ihren Urlaub.
                Begründen Sie die Wahl des Instituts.
                Beschreiben Sie den Unterricht.
                Schlagen Sie Ihrem Freund vor, auch zu lernen.
            """.trimIndent(),
            wordCount = "80",
            keyPoints = listOf("Begründung", "Unterricht", "Vorschlag")
        ),
        SchreibenTask(
            taskNumber = 2,
            title = "Aufgabe 2 – Blog: Make-up für 10-Jährige",
            instruction = """
                Meinung: „Schminken für kleine Mädchen unmöglich! Zeitungen wissen nicht mehr was sie schreiben sollen.“
                Schreiben Sie Ihre Meinung dazu.
            """.trimIndent(),
            wordCount = "80",
            keyPoints = listOf("Eigene Meinung")
        ),
        SchreibenTask(
            taskNumber = 3,
            title = "Aufgabe 3 – Absage Goldene Hochzeit",
            instruction = """
                Einladung zur goldenen Hochzeit von Herrn/Frau Sanders. Sie sind verreist.
                Bedanken Sie sich und sagen Sie ab.
            """.trimIndent(),
            wordCount = "40",
            keyPoints = listOf("Dank", "Absage")
        )
    ),
    sprechenTasks = listOf(
        SprechenTask(
            taskNumber = 1,
            title = "Teil 1 – Überraschungsparty organisieren",
            instruction = "Planen Sie eine Überraschungsparty für einen Freund.",
            topics = listOf("Ort, Gäste, Einkauf, Information."),
            prepTimeSec = 60,
            speakTimeSec = 180,
            tips = listOf("Vorschläge machen", "Entscheiden")
        ),
        SprechenTask(
            taskNumber = 2,
            title = "Teil 2 – Thema präsentieren",
            instruction = "Präsentieren Sie das Thema: „Ist Facebook nützlich?“",
            topics = listOf("Erfahrung, Situation im Heimatland, Vor-/Nachteile, Abschluss."),
            prepTimeSec = 120,
            speakTimeSec = 180,
            tips = listOf("Struktur folgen", "Beispiele")
        ),
        SprechenTask(
            taskNumber = 3,
            title = "Teil 3 – Über ein Thema sprechen",
            instruction = "Reagieren Sie auf Rückmeldungen und stellen Sie Fragen.",
            topics = listOf("Rückmeldung geben und Frage stellen zur Partner-Präsentation."),
            prepTimeSec = 30,
            speakTimeSec = 60,
            tips = listOf("Feedback", "Frage")
        )
    )
)

// ─── All Exams Map ────────────────────────────────────────────────────────────

// ═══════════════════════════════════════════════════════════════════════
// TELC MODELLTEST 1 — COMPLETE (from alejandrob-dev/telc-b1-trainer)
// ═══════════════════════════════════════════════════════════════════════

val TelcExam1LesenParts = listOf(

    // ── LESEN TEIL 1: Überschriften zuordnen ──────────────────────────
    LesenPart(
        title = "Lesen Teil 1 – Überschriften zuordnen",
        instruction = """Lesen Sie die fünf Texte (1–5) und wählen Sie die passende Überschrift (a–j).
Fünf Überschriften passen nicht. Markieren Sie Ihre Lösungen auf dem Antwortbogen.""",
        questions = listOf(
            MCQuestion(
                id = "t1_l1_1",
                text = """Text 1:
„Seit diesem Jahr können Besucher des Berliner Zoos die Tiere nicht nur sehen, sondern auch mehr über sie erfahren. An jedem Gehege gibt es jetzt QR-Codes. Wer sein Smartphone zückt, bekommt spannende Infos über das Tier, seinen Lebensraum und Schutzmaßnahmen."

Welche Überschrift passt?""",
                options = listOf(
                    "a) Technik macht Zoobesuch informativer",
                    "b) Tierrechte werden gestärkt",
                    "c) Neue Tiere im Berliner Zoo",
                    "d) Smartphones verboten im Zoo"
                ),
                correctIndex = 0,
                explanation = "QR-Codes mit Infos über Tiere = Technik macht den Zoobesuch informativer."
            ),
            MCQuestion(
                id = "t1_l1_2",
                text = """Text 2:
„Das Jobcenter Hamburg bietet ab Oktober kostenlose Computerkurse für ältere Arbeitssuchende an. In den Kursen lernen die Teilnehmer, wie sie Online-Bewerbungen schreiben und Videointerviews führen können. Die Nachfrage ist bereits jetzt sehr groß."

Welche Überschrift passt?""",
                options = listOf(
                    "a) Senioren sollen mehr Sport treiben",
                    "b) Digitale Weiterbildung für Ältere",
                    "c) Neue Büros für das Jobcenter",
                    "d) Videospiele für Arbeitssuchende"
                ),
                correctIndex = 1,
                explanation = "Computerkurse für ältere Arbeitssuchende = Digitale Weiterbildung für Ältere."
            ),
            MCQuestion(
                id = "t1_l1_3",
                text = """Text 3:
„In vielen deutschen Städten entstehen sogenannte ‚Repair Cafés'. Dort bringen Bürgerinnen und Bürger kaputte Geräte, Kleidung oder Möbel mit. Freiwillige Helfer zeigen, wie man diese Gegenstände repariert, statt sie wegzuwerfen."

Welche Überschrift passt?""",
                options = listOf(
                    "a) Kaffeehaus mit Werkzeug",
                    "b) Nachhaltigkeit durch Reparieren statt Wegwerfen",
                    "c) Elektriker suchen Arbeit",
                    "d) Mode aus alten Stoffen"
                ),
                correctIndex = 1,
                explanation = "Reparieren statt wegwerfen in der Gemeinschaft = Nachhaltigkeit durch Reparieren."
            ),
            MCQuestion(
                id = "t1_l1_4",
                text = """Text 4:
„Die Deutsche Bahn testet in Bayern einen neuen Zug, der mit Wasserstoff fährt. Das Fahrzeug stößt dabei nur Wasserdampf aus — kein CO₂. Wenn die Tests erfolgreich sind, soll der Zug ab 2026 regulär im Einsatz sein."

Welche Überschrift passt?""",
                options = listOf(
                    "a) Bahn erhöht Ticketpreise",
                    "b) Wasserstoffzug als umweltfreundliche Alternative",
                    "c) Bayern baut neue Schienen",
                    "d) Elektroautos auf Schienen"
                ),
                correctIndex = 1,
                explanation = "Wasserstoffzug stößt nur Wasserdampf aus = umweltfreundliche Alternative."
            ),
            MCQuestion(
                id = "t1_l1_5",
                text = """Text 5:
„Immer mehr junge Familien ziehen aus den teuren Großstädten in kleinere Gemeinden auf dem Land. Dort sind Wohnungen günstiger, die Schulen besser besetzt und die Natur ist nah. Das Homeoffice macht diesen Schritt möglich."

Welche Überschrift passt?""",
                options = listOf(
                    "a) Landleben dank Homeoffice attraktiver",
                    "b) Schulen auf dem Land schlechter",
                    "c) Städte werden größer",
                    "d) Bauern suchen Mitarbeiter"
                ),
                correctIndex = 0,
                explanation = "Familien ziehen aufs Land dank Homeoffice = Landleben dank Homeoffice attraktiver."
            )
        )
    ),

    // ── LESEN TEIL 2: Detailverstehen ─────────────────────────────────
    LesenPart(
        title = "Lesen Teil 2 – Welcher Satz passt zum Text?",
        instruction = """Lesen Sie den Text und die Aufgaben 6–10.
Welcher Satz (a, b oder c) gibt den Text am genauesten wieder?""",
        contextText = """
Freiwilligenarbeit in Deutschland

In Deutschland engagieren sich rund 28 Millionen Menschen ehrenamtlich — das entspricht etwa einem Drittel der Bevölkerung. Sie helfen in Sportvereinen, Feuerwehren, sozialen Einrichtungen und Schulen. Ohne dieses Engagement würde das gesellschaftliche Leben deutlich ärmer.

Besonders beliebt ist das Ehrenamt bei Menschen über 60 Jahren. Viele Rentnerinnen und Rentner finden im freiwilligen Engagement eine sinnvolle Aufgabe und neue soziale Kontakte. Aber auch unter Jugendlichen steigt das Interesse: Laut einer Studie von 2023 möchten 42% der 14- bis 25-Jährigen sich mehr ehrenamtlich engagieren.

Die häufigste Hürde für mehr Engagement ist Zeitmangel. Berufstätige Menschen haben oft kaum Zeit, neben Job und Familie zusätzliche Aufgaben zu übernehmen. Deshalb fordern viele Organisationen, dass Arbeitgeber ihren Mitarbeitern bezahlte Freistellungen für ehrenamtliche Tätigkeiten ermöglichen.

Für Geflüchtete und Zugewanderte kann Ehrenamt ein wichtiger Weg zur Integration sein. Wer sich engagiert, knüpft Kontakte, verbessert seine Deutschkenntnisse und lernt die Gesellschaft besser kennen. Einige Kommunen bieten spezielle Einstiegsprogramme an, die Neuankömmlinge mit passenden Ehrenämtern verbinden.
        """.trimIndent(),
        questions = listOf(
            MCQuestion(
                id = "t1_l2_6",
                text = "Aufgabe 6: Wie viele Menschen engagieren sich in Deutschland ehrenamtlich?",
                options = listOf(
                    "a) Fast die Hälfte der Bevölkerung",
                    "b) Etwa ein Drittel der Bevölkerung",
                    "c) Weniger als 10 Millionen Menschen"
                ),
                correctIndex = 1,
                explanation = "„rund 28 Millionen... etwa ein Drittel der Bevölkerung\""
            ),
            MCQuestion(
                id = "t1_l2_7",
                text = "Aufgabe 7: Wer engagiert sich am häufigsten ehrenamtlich?",
                options = listOf(
                    "a) Besonders junge Menschen unter 25",
                    "b) Vor allem Schülerinnen und Schüler",
                    "c) Besonders Menschen über 60 Jahren"
                ),
                correctIndex = 2,
                explanation = "„Besonders beliebt ist das Ehrenamt bei Menschen über 60 Jahren.\""
            ),
            MCQuestion(
                id = "t1_l2_8",
                text = "Aufgabe 8: Was hindert viele Berufstätige am Ehrenamt?",
                options = listOf(
                    "a) Kein Interesse an sozialen Themen",
                    "b) Zu wenig Zeit neben Beruf und Familie",
                    "c) Fehlende finanzielle Unterstützung"
                ),
                correctIndex = 1,
                explanation = "„Die häufigste Hürde für mehr Engagement ist Zeitmangel.\""
            ),
            MCQuestion(
                id = "t1_l2_9",
                text = "Aufgabe 9: Was fordern viele Organisationen von Arbeitgebern?",
                options = listOf(
                    "a) Mehr Gehalt für Ehrenamtliche",
                    "b) Kürzere Arbeitszeiten generell",
                    "c) Bezahlte Freistellungen für ehrenamtliche Tätigkeiten"
                ),
                correctIndex = 2,
                explanation = "„...dass Arbeitgeber ihren Mitarbeitern bezahlte Freistellungen für ehrenamtliche Tätigkeiten ermöglichen.\""
            ),
            MCQuestion(
                id = "t1_l2_10",
                text = "Aufgabe 10: Warum kann Ehrenamt für Zugewanderte wichtig sein?",
                options = listOf(
                    "a) Sie verdienen damit Geld",
                    "b) Es hilft bei der Integration und dem Deutschlernen",
                    "c) Sie müssen es laut Gesetz leisten"
                ),
                correctIndex = 1,
                explanation = "„Wer sich engagiert, knüpft Kontakte, verbessert seine Deutschkenntnisse und lernt die Gesellschaft besser kennen.\""
            )
        )
    ),

    // ── LESEN TEIL 3: Anzeigen und Situationen ────────────────────────
    LesenPart(
        title = "Lesen Teil 3 – Welche Anzeige passt?",
        instruction = """Lesen Sie die Situationen 11–15 und die Anzeigen a–l.
Welche Anzeige passt zu welcher Situation? Für eine Situation gibt es keine passende Anzeige — markieren Sie dann „x".""",
        questions = listOf(
            MCQuestion(
                id = "t1_l3_11",
                text = """Situation 11: Mia sucht einen Deutschkurs für Erwachsene, der abends stattfindet, weil sie tagsüber arbeitet.

Anzeigen:
a) Deutsch A1–B2: Abendkurse Mo+Mi 18–20 Uhr. VHS München. Tel. 089-445566
b) Kinderdeutsch: Samstag 10–12 Uhr für Kinder 6–12 Jahre. Sprachschule Nord
c) Online-Deutsch für Berufstätige: täglich verfügbar. www.deutschonline.de
d) Tagesintensivkurs Deutsch B1: Mo–Fr 9–14 Uhr. Nur für Vollzeit-Lernende.""",
                options = listOf("Anzeige a", "Anzeige b", "Anzeige c", "Anzeige d"),
                correctIndex = 0,
                explanation = "Anzeige a bietet Abendkurse für Erwachsene — passt zu Mias Situation als Berufstätige."
            ),
            MCQuestion(
                id = "t1_l3_12",
                text = """Situation 12: Thomas möchte mit seiner Familie am Wochenende etwas unternehmen. Er sucht eine Aktivität, die für Kinder und Erwachsene geeignet ist.

Anzeigen:
e) Familienwanderung im Stadtwald: jeden Sonntag 10 Uhr. Kostenlos. Für alle Altersgruppen.
f) Business-Networking: Freitagabend 19 Uhr. Nur für Erwachsene. Anzug erwünscht.
g) Kinderfußball: Samstag 9 Uhr. Nur für Kinder 7–14 Jahre.
h) Weinprobe im Weinkeller: Sa+So ab 15 Uhr. Mindestalter 18 Jahre.""",
                options = listOf("Anzeige e", "Anzeige f", "Anzeige g", "Anzeige h"),
                correctIndex = 0,
                explanation = "Anzeige e: Familienwanderung für alle Altersgruppen am Sonntag — passt perfekt."
            ),
            MCQuestion(
                id = "t1_l3_13",
                text = """Situation 13: Sara hat ihre Brille verloren und braucht dringend eine neue Sehhilfe. Sie sucht einen Optiker mit schnellem Service.

Anzeigen:
i) Brillen Müller: Neufertigung in 24 Stunden. Alle Kassen. Innenstadt, Hauptstr. 12.
j) Kontaktlinsen-Abo: monatliche Lieferung nach Hause. Nur für Kontaktlinsen-Träger.
k) Augenarzt Dr. Schmidt: Termin frühestens in 4 Wochen möglich. Spezialpraxis.
l) Secondhand-Brillen: günstige gebrauchte Fassungen. Kein Sehtest möglich.""",
                options = listOf("Anzeige i", "Anzeige j", "Anzeige k", "Anzeige l"),
                correctIndex = 0,
                explanation = "Anzeige i: Neufertigung in 24 Stunden — schneller Service, genau was Sara braucht."
            ),
            MCQuestion(
                id = "t1_l3_14",
                text = """Situation 14: Klaus sucht eine günstige Unterkunft für eine Nacht in Hamburg. Er reist allein und hat kein Auto.

Anzeigen:
a) Hostel Hamburg-Mitte: Einzelzimmer ab 29 €, zentrale Lage, 5 Min. zum Hbf.
b) Campingplatz Alster: Stellplatz für Wohnmobile. 15 km vom Zentrum.
c) Luxushotel Elbe: Doppelzimmer ab 180 €, Parkplatz inklusive.
d) Ferienwohnung Blankenese: 3-Zimmer, Mindestaufenthalt 3 Nächte, Bahn 40 Min.""",
                options = listOf("Anzeige a", "Anzeige b", "Anzeige c", "Anzeige d"),
                correctIndex = 0,
                explanation = "Anzeige a: günstig, Einzelzimmer, zentral und nah am Hauptbahnhof — ideal für Klaus."
            ),
            MCQuestion(
                id = "t1_l3_15",
                text = """Situation 15: Lena möchte einen Kurs besuchen, um besser kochen zu lernen. Speziell interessiert sie sich für vegetarische Küche.

Anzeigen:
e) Backkurs: Brot und Kuchen selbst backen. Sa 10–13 Uhr. VHS Köln.
f) Fleischküche für Profis: Intensivkurs Fleischverarbeitung. Fr 14–18 Uhr.
g) Vegetarisch & vegan kochen: Grundkurs Mi 18:30 Uhr. Anmeldung erforderlich.
h) Sushi-Kurs: Japanische Fischgerichte. Do 17 Uhr. Für Anfänger geeignet.""",
                options = listOf("Anzeige e", "Anzeige f", "Anzeige g", "Anzeige h"),
                correctIndex = 2,
                explanation = "Anzeige g: Vegetarisch & vegan kochen — genau das, was Lena sucht."
            )
        )
    ),

    // ── SPRACHBAUSTEINE TEIL 1: Lückentext mit MC ─────────────────────
    LesenPart(
        title = "Sprachbausteine Teil 1 – Lückentext",
        instruction = """Lesen Sie den Brief und füllen Sie die Lücken 16–25.
Welches Wort (a, b oder c) passt in die Lücke?""",
        contextText = """
Betreff: Anfrage wegen Sprachkurs

Sehr geehrte Damen und Herren,

ich habe Ihre Anzeige in der Zeitung gelesen und interessiere mich _16_ Ihren Deutschkurs auf dem Niveau B1. Ich lerne seit zwei Jahren Deutsch und möchte meine Kenntnisse _17_ verbessern. 

Könnten Sie mir bitte _18_ mitteilen, wann der nächste Kurs beginnt? Ich würde _19_ gerne wissen, wie viele Stunden pro Woche der Kurs hat. _20_ ich berufstätig bin, kann ich nur abends oder am Wochenende teilnehmen.

Außerdem möchte ich fragen, _21_ der Kurs auch online möglich ist. Falls ja, _22_ ich sehr interessiert. Bitte schicken Sie mir auch Informationen _23_ die Kosten.

Ich freue _24_ auf Ihre Antwort und stehe für Rückfragen _25_ Verfügung.

Mit freundlichen Grüßen
        """.trimIndent(),
        questions = listOf(
            MCQuestion(id="t1_sb1_16", text="Lücke 16: ich interessiere mich ___ Ihren Deutschkurs",
                options=listOf("a) für","b) an","c) mit"), correctIndex=0,
                explanation="'sich interessieren für' — feste Verbindung mit Akkusativ."),
            MCQuestion(id="t1_sb1_17", text="Lücke 17: möchte meine Kenntnisse ___ verbessern",
                options=listOf("a) weiter","b) zurück","c) oben"), correctIndex=0,
                explanation="'weiter verbessern' = continue to improve."),
            MCQuestion(id="t1_sb1_18", text="Lücke 18: Könnten Sie mir bitte ___ mitteilen",
                options=listOf("a) kurz","b) lang","c) groß"), correctIndex=0,
                explanation="'kurz mitteilen' = briefly let me know — idiomatisch."),
            MCQuestion(id="t1_sb1_19", text="Lücke 19: Ich würde ___ gerne wissen",
                options=listOf("a) außerdem","b) doch","c) jedoch"), correctIndex=0,
                explanation="'außerdem' = additionally — verbindet zwei Fragen."),
            MCQuestion(id="t1_sb1_20", text="Lücke 20: ___ ich berufstätig bin, kann ich nur abends teilnehmen",
                options=listOf("a) Wenn","b) Da","c) Obwohl"), correctIndex=1,
                explanation="'Da' = since/because — gibt den Grund an (kausal)."),
            MCQuestion(id="t1_sb1_21", text="Lücke 21: möchte ich fragen, ___ der Kurs online möglich ist",
                options=listOf("a) wenn","b) ob","c) dass"), correctIndex=1,
                explanation="Indirekter Fragesatz mit 'ob' (yes/no indirect question)."),
            MCQuestion(id="t1_sb1_22", text="Lücke 22: Falls ja, ___ ich sehr interessiert",
                options=listOf("a) bin","b) wäre","c) war"), correctIndex=1,
                explanation="'wäre' = Konjunktiv II — höfliche/konditionale Aussage."),
            MCQuestion(id="t1_sb1_23", text="Lücke 23: Informationen ___ die Kosten",
                options=listOf("a) für","b) über","c) von"), correctIndex=1,
                explanation="'Informationen über etwas' — über + Akkusativ."),
            MCQuestion(id="t1_sb1_24", text="Lücke 24: Ich freue ___ auf Ihre Antwort",
                options=listOf("a) mir","b) mich","c) sich"), correctIndex=1,
                explanation="'sich freuen auf' — Reflexivpronomen Akkusativ: mich."),
            MCQuestion(id="t1_sb1_25", text="Lücke 25: stehe für Rückfragen ___ Verfügung",
                options=listOf("a) zu","b) zur","c) in"), correctIndex=1,
                explanation="'zur Verfügung stehen' — feste Wendung.")
        )
    )
)

// ── TELC MODELLTEST 1: HÖREN ──────────────────────────────────────────

val TelcExam1HoerenParts = listOf(
    HoerenPart(
        title = "Hören Teil 1 – Fünf kurze Texte",
        instruction = "Sie hören fünf Gespräche. Entscheiden Sie nach jedem Gespräch: Ist die Aussage richtig oder falsch?",
        audioAssetPath = "audio/telc1_hoeren1.mp3",
        transcript = """
Gespräch 1:
Frau: Guten Tag, ich möchte ein Ticket nach Frankfurt kaufen.
Herr: Einfach oder hin und zurück?
Frau: Einfach, bitte. Für den Zug um 14:20 Uhr.
Herr: Das kostet 39 Euro mit der BahnCard 25.
Frau: Gut, ich nehme es.

Gespräch 2:
A: Du siehst müde aus. Alles in Ordnung?
B: Ja, aber ich habe gestern bis Mitternacht gearbeitet. Wir haben ein neues Projekt bekommen.
A: Das klingt stressig. Hast du heute wenigstens frei?
B: Leider nein. Heute ist auch noch eine Besprechung um drei.

Gespräch 3:
Ansage: Achtung, Achtung. Der Intercity 4412 nach Hamburg hat heute etwa 20 Minuten Verspätung. Reisende werden gebeten, auf dem Bahnsteig 6 zu warten. Wir bitten um Entschuldigung für die Unannehmlichkeiten.

Gespräch 4:
A: Hast du schon das neue Restaurant in der Innenstadt probiert?
B: Ja, letzte Woche! Das Essen war fantastisch, aber die Preise sind ziemlich hoch.
A: Wie hoch denn ungefähr?
B: So um die 25 Euro für ein Hauptgericht. Für einen besonderen Anlass aber wirklich empfehlenswert.

Gespräch 5:
A: Ich suche das Stadtmuseum. Können Sie mir helfen?
B: Ja, natürlich. Gehen Sie hier die Hauptstraße entlang, dann links in die Schillerstraße. Das Museum ist dann auf der rechten Seite, direkt neben der Post.
A: Ungefähr wie weit ist das zu Fuß?
B: Vielleicht zehn Minuten.
        """.trimIndent(),
        questions = listOf(
            MCQuestion(id="t1_h1_1", text="Die Frau kauft ein Rückfahrticket nach Frankfurt.",
                options=listOf("Richtig","Falsch"), correctIndex=1,
                explanation="Sie kauft ein einfaches Ticket, nicht hin und zurück."),
            MCQuestion(id="t1_h1_2", text="Die Person B hatte gestern viel zu tun und arbeitete spät.",
                options=listOf("Richtig","Falsch"), correctIndex=0,
                explanation="'bis Mitternacht gearbeitet' bestätigt: richtig."),
            MCQuestion(id="t1_h1_3", text="Der Zug nach Hamburg fährt pünktlich ab.",
                options=listOf("Richtig","Falsch"), correctIndex=1,
                explanation="Der Zug hat 20 Minuten Verspätung — nicht pünktlich."),
            MCQuestion(id="t1_h1_4", text="Das neue Restaurant ist teuer, aber für besondere Anlässe empfehlenswert.",
                options=listOf("Richtig","Falsch"), correctIndex=0,
                explanation="'Preise sind hoch... für einen besonderen Anlass empfehlenswert' — richtig."),
            MCQuestion(id="t1_h1_5", text="Das Stadtmuseum ist etwa 10 Minuten zu Fuß entfernt.",
                options=listOf("Richtig","Falsch"), correctIndex=0,
                explanation="'Vielleicht zehn Minuten' — richtig.")
        )
    ),

    HoerenPart(
        title = "Hören Teil 2 – Längeres Interview",
        instruction = "Sie hören ein Interview. Entscheiden Sie: Sind die Aussagen 6–15 richtig oder falsch?",
        audioAssetPath = "audio/telc1_hoeren2.mp3",
        transcript = """
Moderator: Herzlich willkommen zu unserem Podcast „Arbeitswelt heute". Heute spreche ich mit Dr. Anna Weber, Karriereberaterin aus München. Guten Tag, Frau Weber.

Dr. Weber: Guten Tag. Ich freue mich, dabei zu sein.

Moderator: Frau Weber, das Homeoffice ist seit der Pandemie ein großes Thema. Wie hat sich die Arbeitswelt verändert?

Dr. Weber: Enorm. Vor 2020 arbeiteten in Deutschland nur etwa 5% der Arbeitnehmer regelmäßig von zu Hause. Heute sind es fast 30%. Das ist eine Revolution.

Moderator: Ist das nur positiv?

Dr. Weber: Nein, definitiv nicht. Einerseits sparen viele Pendler Zeit und Geld. Eine Studie zeigt, dass Homeoffice-Arbeitnehmer im Durchschnitt 45 Minuten pro Tag sparen, weil der Weg zur Arbeit wegfällt. Andererseits leiden viele unter Einsamkeit und haben Schwierigkeiten, Arbeit und Privatleben zu trennen.

Moderator: Was empfehlen Sie Unternehmen?

Dr. Weber: Das hybride Modell hat sich bewährt: zwei bis drei Tage Homeoffice, den Rest im Büro. So bleibt der soziale Kontakt erhalten, aber man hat auch Flexibilität.

Moderator: Was ist mit jungen Berufseinsteigern?

Dr. Weber: Das ist ein wichtiger Punkt. Berufseinsteiger brauchen mehr Präsenz im Büro. Wer neu in einem Job ist, lernt am meisten durch direkte Beobachtung und informelle Gespräche. Homeoffice ist für sie weniger ideal.

Moderator: Welche Berufsgruppen profitieren am meisten vom Homeoffice?

Dr. Weber: IT-Fachkräfte und Kreative — also Grafiker, Texter, Programmierer. Sie brauchen oft Konzentration und können gut alleine arbeiten. Dagegen brauchen Lehrer, Ärzte oder Handwerker natürlich Präsenz.

Moderator: Vielen Dank, Frau Weber!

Dr. Weber: Gerne. Auf Wiederhören!
        """.trimIndent(),
        questions = listOf(
            MCQuestion(id="t1_h2_6", text="Vor der Pandemie arbeiteten fast 30% der Deutschen im Homeoffice.",
                options=listOf("Richtig","Falsch"), correctIndex=1,
                explanation="Vor 2020 waren es nur 5%, heute sind es 30%."),
            MCQuestion(id="t1_h2_7", text="Homeoffice-Arbeitnehmer sparen laut Studie durchschnittlich 45 Minuten pro Tag.",
                options=listOf("Richtig","Falsch"), correctIndex=0,
                explanation="'im Durchschnitt 45 Minuten pro Tag sparen' — richtig."),
            MCQuestion(id="t1_h2_8", text="Alle Aspekte des Homeoffice sind positiv.",
                options=listOf("Richtig","Falsch"), correctIndex=1,
                explanation="Dr. Weber sagt: 'Nein, definitiv nicht' — nicht nur positiv."),
            MCQuestion(id="t1_h2_9", text="Das hybride Modell bedeutet: manchmal Homeoffice, manchmal Büro.",
                options=listOf("Richtig","Falsch"), correctIndex=0,
                explanation="'zwei bis drei Tage Homeoffice, den Rest im Büro' — richtig."),
            MCQuestion(id="t1_h2_10", text="Berufseinsteiger sollten laut Dr. Weber mehr Zeit im Homeoffice verbringen.",
                options=listOf("Richtig","Falsch"), correctIndex=1,
                explanation="Das Gegenteil: sie brauchen mehr Präsenz im Büro."),
            MCQuestion(id="t1_h2_11", text="IT-Fachkräfte und Kreative profitieren besonders vom Homeoffice.",
                options=listOf("Richtig","Falsch"), correctIndex=0,
                explanation="'IT-Fachkräfte und Kreative... Sie brauchen oft Konzentration' — richtig."),
            MCQuestion(id="t1_h2_12", text="Lehrer können gut im Homeoffice arbeiten.",
                options=listOf("Richtig","Falsch"), correctIndex=1,
                explanation="Lehrer 'brauchen Präsenz' — Homeoffice ist nicht ideal für sie."),
            MCQuestion(id="t1_h2_13", text="Ein Problem des Homeoffice ist, dass manche sich einsam fühlen.",
                options=listOf("Richtig","Falsch"), correctIndex=0,
                explanation="'leiden viele unter Einsamkeit' — richtig."),
            MCQuestion(id="t1_h2_14", text="Dr. Weber kommt aus Berlin.",
                options=listOf("Richtig","Falsch"), correctIndex=1,
                explanation="Sie ist 'Karriereberaterin aus München', nicht Berlin."),
            MCQuestion(id="t1_h2_15", text="Das Interview findet in einem Podcast statt.",
                options=listOf("Richtig","Falsch"), correctIndex=0,
                explanation="'Herzlich willkommen zu unserem Podcast' — richtig.")
        )
    ),

    HoerenPart(
        title = "Hören Teil 3 – Kurze Ansagen",
        instruction = "Sie hören fünf kurze Texte. Entscheiden Sie: Ist die Aussage richtig oder falsch?",
        audioAssetPath = "audio/telc1_hoeren3.mp3",
        transcript = """
Text 1 (Ansage Supermarkt):
Liebe Kundinnen und Kunden, der REWE in der Hauptstraße ist von Montag bis Samstag von 7 bis 22 Uhr geöffnet. Sonntags bleibt unser Geschäft geschlossen. Bei Fragen wenden Sie sich bitte an unser Personal.

Text 2 (Arztpraxis-Ansage):
Sie haben die Praxis Dr. Schuster erreicht. Unsere Öffnungszeiten sind Montag, Dienstag und Donnerstag von 8 bis 12 und 14 bis 18 Uhr. Mittwoch und Freitag nur von 8 bis 13 Uhr. Für Notfälle wählen Sie bitte den ärztlichen Bereitschaftsdienst unter 116 117.

Text 3 (Radio-Werbung):
Neu in München: das FitPlus Fitnessstudio in der Leopoldstraße 45. Jetzt im Juli testen — der erste Monat ist völlig kostenlos! Danach monatlich nur 29,90 Euro. Kommen Sie vorbei oder besuchen Sie uns unter fitplus-muenchen.de.

Text 4 (Bahnhofsdurchsage):
Ihre Aufmerksamkeit bitte. Aufgrund von Bauarbeiten ist das Gleis 3 heute von 10 bis 16 Uhr gesperrt. Züge, die normalerweise von Gleis 3 abfahren, nutzen heute Gleis 5. Wir bitten um Ihr Verständnis.

Text 5 (Stadtbibliothek-Ansage):
Liebe Besucherinnen und Besucher, die Stadtbibliothek ist ab nächster Woche montags bis freitags bis 20 Uhr geöffnet — eine Stunde länger als bisher. Samstags bleibt die Öffnungszeit von 10 bis 15 Uhr unverändert.
        """.trimIndent(),
        questions = listOf(
            MCQuestion(id="t1_h3_16", text="Der REWE-Supermarkt ist auch sonntags geöffnet.",
                options=listOf("Richtig","Falsch"), correctIndex=1,
                explanation="'Sonntags bleibt unser Geschäft geschlossen' — falsch."),
            MCQuestion(id="t1_h3_17", text="Die Praxis Dr. Schuster ist am Mittwoch nur morgens geöffnet.",
                options=listOf("Richtig","Falsch"), correctIndex=0,
                explanation="Mittwoch: nur 8 bis 13 Uhr = nur morgens — richtig."),
            MCQuestion(id="t1_h3_18", text="Der erste Monat im FitPlus Fitnessstudio ist kostenlos.",
                options=listOf("Richtig","Falsch"), correctIndex=0,
                explanation="'der erste Monat ist völlig kostenlos' — richtig."),
            MCQuestion(id="t1_h3_19", text="Züge fahren heute wegen Bauarbeiten von Gleis 3 statt Gleis 5.",
                options=listOf("Richtig","Falsch"), correctIndex=1,
                explanation="Es ist umgekehrt: Züge von Gleis 3 nutzen heute Gleis 5."),
            MCQuestion(id="t1_h3_20", text="Die Stadtbibliothek öffnet ab nächster Woche werktags eine Stunde länger.",
                options=listOf("Richtig","Falsch"), correctIndex=0,
                explanation="'eine Stunde länger als bisher' — richtig.")
        )
    )
)

// ── TELC MODELLTEST 1: SCHREIBEN ──────────────────────────────────────

val TelcExam1SchreibenTasks = listOf(
    SchreibenTask(
        title = "Schriftlicher Ausdruck – Persönlicher Brief",
        instruction = """Sie haben folgenden Brief von Ihrer Freundin Birgit bekommen:

„...Ich habe gehört, dass du umgezogen bist! Erzähl mir doch bitte:
Wie ist deine neue Wohnung? Wie ist die Nachbarschaft?
Was machst du in deiner Freizeit in der neuen Stadt?
Wann können wir uns besuchen?"

Schreiben Sie Birgit einen Brief. Antworten Sie auf alle vier Punkte.
Schreiben Sie etwa 100 Wörter.""",
        wordCount = "ca. 100 Wörter",
        keyPoints = listOf(
            "Neue Wohnung beschreiben (Größe, Lage, was gefällt?)",
            "Nachbarschaft beschreiben (ruhig/laut, nett/anonym?)",
            "Freizeit in der neuen Stadt (was unternimmst du?)",
            "Besuchsplan vorschlagen (wann, wie lange?)",
            "Persönliche Anrede: 'Liebe Birgit,'",
            "Persönlicher Abschluss: 'Liebe Grüße,' oder ähnlich"
        )
    )
)

// ── TELC MODELLTEST 1: SPRECHEN ───────────────────────────────────────

val TelcExam1SprechenTasks = listOf(
    SprechenTask(
        title = "Sprechen Teil 1 – Kontaktaufnahme",
        instruction = """Sprechen Sie mit Ihrem Partner/Ihrer Partnerin. Stellen Sie sich vor und stellen Sie Fragen.
Themen: Name · Herkunft · Wohnort · Beruf oder Studium · Hobbys · Warum lernen Sie Deutsch?""",
        topics = listOf(
            "Wie heißen Sie? Woher kommen Sie?",
            "Wo wohnen Sie gerade? Seit wann?",
            "Was machen Sie beruflich oder was studieren Sie?",
            "Was sind Ihre Hobbys?",
            "Warum lernen Sie Deutsch? Was sind Ihre Ziele?"
        )
    ),
    SprechenTask(
        title = "Sprechen Teil 2 – Thema besprechen",
        instruction = """Sehen Sie sich die zwei Bilder an und sprechen Sie darüber:
Was sehen Sie auf den Bildern?
Was sind die Vor- und Nachteile?
Was meinen Sie persönlich?""",
        topics = listOf(
            "Bild A: Einkaufen im Supermarkt vs. Bild B: Online bestellen — Vor- und Nachteile beider Wege",
            "Bild A: Auto fahren vs. Bild B: Mit dem Fahrrad fahren — Was ist besser für die Stadt?",
            "Bild A: Allein wohnen vs. Bild B: In einer WG wohnen — Was passt besser zu Ihnen?"
        )
    ),
    SprechenTask(
        title = "Sprechen Teil 3 – Gemeinsam planen",
        instruction = """Sie und Ihr Partner/Ihre Partnerin möchten für Ihren Freund Lars eine Geburtstagsfeier organisieren.
Einigen Sie sich auf:
• Wo soll die Party stattfinden? (zu Hause, Restaurant, Park?)
• Was soll es zu essen und zu trinken geben?
• Welche Aktivitäten soll es geben? (Musik, Spiele, Fotos?)
• Wer bringt was mit?

Machen Sie Vorschläge und reagieren Sie auf die Ideen Ihres Partners.""",
        topics = listOf(
            "Ort: zu Hause / Restaurant / Garten / Park",
            "Essen & Trinken: selbst kochen / bestellen / Potluck",
            "Aktivitäten: Musik, Karaoke, Spiele, Fotobox",
            "Aufgabenverteilung: wer kauft was ein"
        )
    )
)


// ═══════════════════════════════════════════════════════════════════════
// TELC MODELLTEST 2 — COMPLETE
// ═══════════════════════════════════════════════════════════════════════

val TelcExam2LesenParts = listOf(

    LesenPart(
        title = "Lesen Teil 1 – Überschriften zuordnen",
        instruction = "Lesen Sie die fünf Texte und wählen Sie die passende Überschrift (a–j). Fünf passen nicht.",
        questions = listOf(
            MCQuestion(
                id = "t2_l1_1",
                text = """Text 1:
„Eine Bäckerei in Freiburg hat ein ungewöhnliches Konzept: Sie öffnet jeden Tag um vier Uhr morgens und schließt, sobald alle Brote verkauft sind — manchmal schon um neun Uhr. Die Schlange vor dem Laden beginnt oft schon um drei Uhr dreißig."

Welche Überschrift passt?""",
                options = listOf(
                    "a) Bäckerei mit ungewöhnlichen Öffnungszeiten — immer ausverkauft",
                    "b) Brot wird in Deutschland immer teurer",
                    "c) Neue Backrezepte aus Freiburg",
                    "d) Bäckerei sucht Nachtschichtarbeiter"
                ),
                correctIndex = 0,
                explanation = "Die Bäckerei öffnet sehr früh und ist oft schnell ausverkauft — passt zu Option a."
            ),
            MCQuestion(
                id = "t2_l1_2",
                text = """Text 2:
„Forscher der Universität Bonn haben entdeckt, dass regelmäßiges Singen in einem Chor das Immunsystem stärkt. Bei Probanden, die ein Jahr lang einmal wöchentlich sangen, wurden deutlich mehr Abwehrzellen gemessen als bei der Kontrollgruppe."

Welche Überschrift passt?""",
                options = listOf(
                    "a) Neue Studie: Musik macht krank",
                    "b) Chorgesang stärkt das Immunsystem",
                    "c) Universität Bonn eröffnet neuen Chor",
                    "d) Abwehrzellen durch Sport"
                ),
                correctIndex = 1,
                explanation = "Die Studie zeigt: Chorgesang stärkt das Immunsystem."
            ),
            MCQuestion(
                id = "t2_l1_3",
                text = """Text 3:
„Seit Januar bietet die Stadt Wien kostenlose Fahrradhelme für Kinder unter 12 Jahren an. Eltern können den Helm im Rathaus abholen. Das Ziel: die Zahl der Fahrradunfälle mit schweren Kopfverletzungen zu reduzieren."

Welche Überschrift passt?""",
                options = listOf(
                    "a) Wien verbietet Radfahren für Kinder",
                    "b) Kostenlose Fahrradhelme für Kinder in Wien",
                    "c) Neue Radwege in Wien gebaut",
                    "d) Unfälle in Wien stark gestiegen"
                ),
                correctIndex = 1,
                explanation = "Kostenlose Helme für Kinder zur Unfallprävention — Option b."
            ),
            MCQuestion(
                id = "t2_l1_4",
                text = """Text 4:
„Ein Berliner Start-up hat eine App entwickelt, die Lebensmittel-Überreste von Restaurants sammelt und an Bedürftige verteilt. Über 300 Restaurants nehmen bereits teil. Pro Monat werden so etwa 15 Tonnen Essen gerettet."

Welche Überschrift passt?""",
                options = listOf(
                    "a) App rettet Essen und hilft Bedürftigen",
                    "b) Restaurants in Berlin schließen",
                    "c) Neue Diät-App aus Berlin",
                    "d) Start-ups suchen Investoren"
                ),
                correctIndex = 0,
                explanation = "App rettet Lebensmittel und verteilt sie an Bedürftige — Option a."
            ),
            MCQuestion(
                id = "t2_l1_5",
                text = """Text 5:
„Das Statistische Bundesamt meldet: In Deutschland werden jährlich rund 3,6 Millionen Tonnen Plastik produziert. Davon werden nur 49% recycelt. Der Rest landet auf Deponien oder in der Umwelt. Experten fordern strengere Gesetze."

Welche Überschrift passt?""",
                options = listOf(
                    "a) Deutschland Weltmeister im Recycling",
                    "b) Plastikproblem: Nur die Hälfte wird recycelt",
                    "c) Neue Plastikfabrik in Deutschland",
                    "d) Strengere Umweltgesetze bereits in Kraft"
                ),
                correctIndex = 1,
                explanation = "Nur 49% Recyclingquote — 'Nur die Hälfte wird recycelt'."
            )
        )
    ),

    LesenPart(
        title = "Lesen Teil 2 – Detailverstehen",
        instruction = "Lesen Sie den Text. Welcher Satz (a, b oder c) gibt die Aussage des Textes am genauesten wieder?",
        contextText = """
Schlaf und Gesundheit

Wer zu wenig schläft, riskiert mehr als nur Müdigkeit am nächsten Tag. Wissenschaftler der Universität Heidelberg haben herausgefunden, dass chronischer Schlafmangel das Risiko für Herzerkrankungen um bis zu 40% erhöht. Außerdem steigt bei Menschen, die regelmäßig weniger als sechs Stunden schlafen, das Risiko für Typ-2-Diabetes und Übergewicht deutlich an.

Besonders alarmierend: Viele Menschen unterschätzen ihren eigenen Schlafmangel. In einer Befragung von 2.000 Deutschen gaben 71% an, gut zu schlafen. Tests zeigten jedoch, dass tatsächlich nur 43% die empfohlenen sieben bis neun Stunden erreichten.

Die Ursachen für schlechten Schlaf sind vielfältig. Stress, Bildschirmnutzung vor dem Schlafengehen und unregelmäßige Schlafzeiten sind die häufigsten Faktoren. Experten empfehlen, jeden Tag zur gleichen Zeit ins Bett zu gehen, das Schlafzimmer abzudunkeln und Smartphones mindestens eine Stunde vor dem Schlafen wegzulegen.

Positiv: Wer seinen Schlaf verbessert, bemerkt oft schnell eine Wirkung. Schon nach zwei Wochen regelmäßigem, ausreichendem Schlaf berichten viele Menschen über mehr Energie, bessere Konzentration und eine positivere Stimmung.
        """.trimIndent(),
        questions = listOf(
            MCQuestion(
                id = "t2_l2_6",
                text = "Aufgabe 6: Was erhöht chronischer Schlafmangel laut Studie?",
                options = listOf(
                    "a) Das Risiko für Herzerkrankungen um bis zu 40%",
                    "b) Die Schlafqualität nach einigen Wochen",
                    "c) Die tägliche Produktivität"
                ),
                correctIndex = 0,
                explanation = "'erhöht das Risiko für Herzerkrankungen um bis zu 40%'"
            ),
            MCQuestion(
                id = "t2_l2_7",
                text = "Aufgabe 7: Was ergab die Befragung der 2.000 Deutschen?",
                options = listOf(
                    "a) Alle schlafen ausreichend",
                    "b) Die meisten schätzen ihren Schlaf falsch ein",
                    "c) Niemand schläft genug"
                ),
                correctIndex = 1,
                explanation = "71% dachten, sie schlafen gut — aber nur 43% erreichten wirklich genug Stunden."
            ),
            MCQuestion(
                id = "t2_l2_8",
                text = "Aufgabe 8: Was empfehlen Experten gegen schlechten Schlaf?",
                options = listOf(
                    "a) Mehr Kaffee am Abend trinken",
                    "b) Regelmäßige Schlafzeiten und kein Smartphone vor dem Schlafen",
                    "c) Tagsüber schlafen statt nachts"
                ),
                correctIndex = 1,
                explanation = "'gleiche Zeit ins Bett, Smartphones mindestens eine Stunde vorher weglegen'"
            ),
            MCQuestion(
                id = "t2_l2_9",
                text = "Aufgabe 9: Wann bemerken Menschen Verbesserungen durch besseren Schlaf?",
                options = listOf(
                    "a) Erst nach einem Jahr",
                    "b) Schon nach zwei Wochen",
                    "c) Erst nach mehreren Monaten"
                ),
                correctIndex = 1,
                explanation = "'Schon nach zwei Wochen regelmäßigem Schlaf...'"
            ),
            MCQuestion(
                id = "t2_l2_10",
                text = "Aufgabe 10: Welche Wirkung hat ausreichend Schlaf laut Text?",
                options = listOf(
                    "a) Man braucht weniger Essen",
                    "b) Man wird automatisch schlanker",
                    "c) Mehr Energie, bessere Konzentration und positivere Stimmung"
                ),
                correctIndex = 2,
                explanation = "'mehr Energie, bessere Konzentration und eine positivere Stimmung'"
            )
        )
    ),

    LesenPart(
        title = "Sprachbausteine Teil 1 – Lückentext",
        instruction = "Welches Wort (a, b oder c) passt in die Lücke?",
        contextText = """
Betreff: Beschwerde wegen beschädigter Lieferung

Sehr geehrte Damen und Herren,

am 15. März habe ich _16_ Ihrem Online-Shop eine Kamera bestellt (Bestellnummer: K-44219). Das Paket ist zwar _17_ zwei Tagen angekommen, _18_ leider war die Kamera beim Öffnen der Verpackung beschädigt.

Das Gehäuse war gebrochen und der Bildschirm hatte einen Riss. Ich habe das Gerät daher sofort _19_ benutzt. Ich habe die Schäden mit Fotos dokumentiert und sende diese _20_ Anhang mit.

Ich bitte Sie _21_ dringend, mir entweder ein funktionsfähiges Ersatzgerät zu schicken _22_ den Kaufpreis vollständig zu erstatten. Da ich die Kamera für eine Reise am 28. März benötige, bitte ich um eine _23_ Antwort bis spätestens 22. März.

Sollte ich _24_ diesem Datum keine Rückmeldung erhalten, werde ich mich _25_ die Verbraucherzentrale wenden.

Mit freundlichen Grüßen
        """.trimIndent(),
        questions = listOf(
            MCQuestion(id="t2_sb1_16", text="Lücke 16: habe ich ___ Ihrem Online-Shop bestellt",
                options=listOf("a) in","b) bei","c) von"), correctIndex=1,
                explanation="'bei einem Shop bestellen' — feste Präposition."),
            MCQuestion(id="t2_sb1_17", text="Lücke 17: Das Paket ist zwar ___ zwei Tagen angekommen",
                options=listOf("a) vor","b) seit","c) in"), correctIndex=0,
                explanation="'vor zwei Tagen' = two days ago (abgeschlossene Vergangenheit)."),
            MCQuestion(id="t2_sb1_18", text="Lücke 18: angekommen, ___ leider war die Kamera beschädigt",
                options=listOf("a) aber","b) weil","c) damit"), correctIndex=0,
                explanation="'aber' = Gegensatz/Kontrast zwischen Lieferung und Schaden."),
            MCQuestion(id="t2_sb1_19", text="Lücke 19: habe das Gerät daher sofort ___ benutzt",
                options=listOf("a) nicht","b) noch","c) kaum"), correctIndex=0,
                explanation="Sofort nicht benutzt = das Gerät nicht verwendet."),
            MCQuestion(id="t2_sb1_20", text="Lücke 20: sende diese ___ Anhang mit",
                options=listOf("a) als","b) im","c) mit"), correctIndex=1,
                explanation="'im Anhang' — feste Briefwendung."),
            MCQuestion(id="t2_sb1_21", text="Lücke 21: Ich bitte Sie ___ dringend",
                options=listOf("a) daher","b) deshalb","c) darum"), correctIndex=2,
                explanation="'jemanden um etwas bitten' → 'darum bitten'."),
            MCQuestion(id="t2_sb1_22", text="Lücke 22: Ersatzgerät schicken ___ den Kaufpreis erstatten",
                options=listOf("a) und","b) oder","c) denn"), correctIndex=1,
                explanation="'entweder... oder' — zwei Alternativen."),
            MCQuestion(id="t2_sb1_23", text="Lücke 23: bitte ich um eine ___ Antwort",
                options=listOf("a) schnelle","b) lange","c) schriftliche"), correctIndex=0,
                explanation="Wegen des Zeitdrucks (bis 22. März): schnelle Antwort."),
            MCQuestion(id="t2_sb1_24", text="Lücke 24: Sollte ich ___ diesem Datum keine Rückmeldung erhalten",
                options=listOf("a) bis","b) nach","c) an"), correctIndex=0,
                explanation="'bis zu einem Datum' — Zeitgrenze."),
            MCQuestion(id="t2_sb1_25", text="Lücke 25: werde ich mich ___ die Verbraucherzentrale wenden",
                options=listOf("a) bei","b) an","c) zu"), correctIndex=1,
                explanation="'sich an jemanden wenden' — feste Verbindung.")
        )
    )
)

val TelcExam2HoerenParts = listOf(
    HoerenPart(
        title = "Hören Teil 1 – Fünf kurze Gespräche",
        instruction = "Sie hören fünf kurze Gespräche. Richtig oder Falsch?",
        audioAssetPath = "audio/telc2_hoeren1.mp3",
        transcript = """
Gespräch 1:
A: Entschuldigung, ist dieser Platz noch frei?
B: Nein, tut mir leid. Mein Kollege kommt gleich.
A: Ach so, dann suche ich mir einen anderen. Danke.

Gespräch 2:
A: Du, ich brauche deine Hilfe. Ich verstehe diese Grammatikregel nicht.
B: Welche meinst du?
A: Den Unterschied zwischen 'seit' und 'vor'.
B: Ah ja, das ist eigentlich ganz einfach. 'Seit' benutzt man bei andauernden Situationen, 'vor' bei abgeschlossenen Ereignissen.
A: Oh, jetzt verstehe ich es! Danke!

Gespräch 3:
Sekretärin: Praxis Dr. Huber, guten Morgen.
Patient: Guten Morgen. Ich habe morgen einen Termin um 10 Uhr. Ich muss leider absagen.
Sekretärin: Das ist schade. Kann ich Ihnen einen neuen Termin geben?
Patient: Ja, bitte. Ich hätte nächste Woche Donnerstag Zeit.
Sekretärin: Donnerstag, 14 Uhr — passt das?
Patient: Perfekt, danke.

Gespräch 4:
A: Was für ein Wetter heute! Ich bin völlig nass.
B: Ja, es hat seit heute Morgen nicht aufgehört zu regnen. Hast du keinen Regenschirm dabei?
A: Doch, aber der Wind hat ihn kaputt gemacht.

Gespräch 5:
Lehrerin: So, für nächste Woche: Lesen Sie bitte Kapitel 7 und machen Sie die Übungen auf Seite 94.
Schüler: Müssen wir auch den Aufsatz abgeben?
Lehrerin: Nein, der Aufsatz ist erst in zwei Wochen fällig.
        """.trimIndent(),
        questions = listOf(
            MCQuestion(id="t2_h1_1", text="Der Platz ist noch frei.",
                options=listOf("Richtig","Falsch"), correctIndex=1,
                explanation="'Nein, tut mir leid. Mein Kollege kommt gleich' — der Platz ist belegt."),
            MCQuestion(id="t2_h1_2", text="Person A hat den Grammatikunterschied zwischen 'seit' und 'vor' verstanden.",
                options=listOf("Richtig","Falsch"), correctIndex=0,
                explanation="'Jetzt verstehe ich es!' — richtig."),
            MCQuestion(id="t2_h1_3", text="Der Patient möchte seinen Termin auf nächsten Donnerstag verschieben.",
                options=listOf("Richtig","Falsch"), correctIndex=0,
                explanation="Er sagt nächste Woche Donnerstag und bestätigt 14 Uhr."),
            MCQuestion(id="t2_h1_4", text="Person A hat keinen Regenschirm mitgenommen.",
                options=listOf("Richtig","Falsch"), correctIndex=1,
                explanation="'Doch' — er hatte einen, aber der Wind hat ihn kaputt gemacht."),
            MCQuestion(id="t2_h1_5", text="Die Schüler müssen nächste Woche den Aufsatz abgeben.",
                options=listOf("Richtig","Falsch"), correctIndex=1,
                explanation="Der Aufsatz ist erst in zwei Wochen fällig, nicht nächste Woche.")
        )
    ),
    HoerenPart(
        title = "Hören Teil 2 – Interview: Sprachen lernen",
        instruction = "Sie hören ein Interview. Sind die Aussagen 6–15 richtig oder falsch?",
        audioAssetPath = "audio/telc2_hoeren2.mp3",
        transcript = """
Moderator: Willkommen bei Radio Sprache. Ich spreche heute mit Lukas Bauer, einem Sprachlehrer und Polyglotten, der acht Sprachen spricht. Guten Tag, Lukas!

Lukas: Guten Tag! Ich freue mich, hier zu sein.

Moderator: Acht Sprachen — wie schafft man das?

Lukas: Ich habe früh angefangen. Mit vier Jahren lernte ich Englisch, weil meine Mutter Engländerin ist. Mit zwölf kam Französisch in der Schule dazu. Dann habe ich in meiner Freizeit Spanisch und Italienisch selbst gelernt — vor allem durch Filme und Musik.

Moderator: Und die anderen vier?

Lukas: Japanisch und Mandarin habe ich während eines Auslandsjahres in Asien gelernt. Arabisch und Russisch erst als Erwachsener — das war deutlich schwieriger als die anderen.

Moderator: Was ist Ihrer Meinung nach die beste Methode zum Sprachlernen?

Lukas: Immersion — also in die Sprache eintauchen. Wenn man täglich mit der Sprache konfrontiert wird, lernt man viel schneller. Aber das bedeutet nicht unbedingt, im Ausland zu leben. Man kann auch zu Hause viel erreichen: Serien schauen, Bücher lesen, mit Muttersprachlern online chatten.

Moderator: Wie viel Zeit sollte man täglich investieren?

Lukas: Für Anfänger reichen 20–30 Minuten täglich völlig aus, wenn man es konsequent macht. Qualität ist wichtiger als Quantität.

Moderator: Haben Sie Tipps für Leute, die Angst haben, Fehler zu machen?

Lukas: Fehler machen ist ein Zeichen, dass man lernt. Wer nie Fehler macht, spricht zu wenig. Ich mache auch heute noch Fehler in meinen Sprachen. Das ist normal und wichtig.

Moderator: Vielen Dank, Lukas!

Lukas: Gern geschehen. Viel Spaß beim Sprachenlernen!
        """.trimIndent(),
        questions = listOf(
            MCQuestion(id="t2_h2_6", text="Lukas spricht insgesamt acht Sprachen.",
                options=listOf("Richtig","Falsch"), correctIndex=0,
                explanation="'acht Sprachen spricht' — bestätigt im Text."),
            MCQuestion(id="t2_h2_7", text="Lukas hat Englisch in der Schule gelernt.",
                options=listOf("Richtig","Falsch"), correctIndex=1,
                explanation="Er lernte Englisch mit vier Jahren — wegen seiner englischen Mutter, nicht in der Schule."),
            MCQuestion(id="t2_h2_8", text="Spanisch und Italienisch hat Lukas sich selbst beigebracht.",
                options=listOf("Richtig","Falsch"), correctIndex=0,
                explanation="'in meiner Freizeit selbst gelernt' — richtig."),
            MCQuestion(id="t2_h2_9", text="Japanisch und Mandarin hat er in Europa gelernt.",
                options=listOf("Richtig","Falsch"), correctIndex=1,
                explanation="Er lernte sie 'während eines Auslandsjahres in Asien'."),
            MCQuestion(id="t2_h2_10", text="Arabisch und Russisch waren für ihn schwieriger als die anderen Sprachen.",
                options=listOf("Richtig","Falsch"), correctIndex=0,
                explanation="'deutlich schwieriger als die anderen' — richtig."),
            MCQuestion(id="t2_h2_11", text="Lukas empfiehlt Immersion als beste Lernmethode.",
                options=listOf("Richtig","Falsch"), correctIndex=0,
                explanation="'Immersion — also in die Sprache eintauchen' — richtig."),
            MCQuestion(id="t2_h2_12", text="Laut Lukas muss man im Ausland leben, um Immersion zu erleben.",
                options=listOf("Richtig","Falsch"), correctIndex=1,
                explanation="'Das bedeutet nicht unbedingt, im Ausland zu leben' — falsch."),
            MCQuestion(id="t2_h2_13", text="Lukas empfiehlt 20–30 Minuten täglich für Anfänger.",
                options=listOf("Richtig","Falsch"), correctIndex=0,
                explanation="'20–30 Minuten täglich... wenn man es konsequent macht' — richtig."),
            MCQuestion(id="t2_h2_14", text="Laut Lukas sollte man Fehler vermeiden.",
                options=listOf("Richtig","Falsch"), correctIndex=1,
                explanation="Das Gegenteil: 'Fehler machen ist ein Zeichen, dass man lernt'."),
            MCQuestion(id="t2_h2_15", text="Lukas macht selbst keine Fehler mehr in seinen Sprachen.",
                options=listOf("Richtig","Falsch"), correctIndex=1,
                explanation="'Ich mache auch heute noch Fehler' — er macht noch Fehler.")
        )
    ),
    HoerenPart(
        title = "Hören Teil 3 – Ansagen und kurze Texte",
        instruction = "Sie hören fünf kurze Texte. Richtig oder Falsch?",
        audioAssetPath = "audio/telc2_hoeren3.mp3",
        transcript = """
Text 1 (Kinoveranstaltung):
Herzlich willkommen im CineMax München. Bitte beachten Sie: Das Fotografieren und Filmen während der Vorstellung ist strengstens verboten. Bitte schalten Sie Ihre Mobiltelefone lautlos. Die heutige Abendvorstellung beginnt um 20:15 Uhr.

Text 2 (Stadtbus-Ansage):
Achtung Fahrgäste: Die Linie 18 fährt ab heute eine neue Route. Sie hält jetzt auch am Krankenhaus Nord. Die Fahrtzeit verlängert sich dadurch um circa fünf Minuten. Weitere Informationen finden Sie auf der Website des Stadtverkehrs.

Text 3 (Schwimmbad-Ansage):
Das Freibad Sonnenpark öffnet diese Saison am 1. Mai. Eintrittskarten können bereits jetzt online zum Vorzugspreis gekauft werden. Für Kinder unter 6 Jahren ist der Eintritt frei. Für Senioren über 65 gilt ein ermäßigter Tarif.

Text 4 (Supermarkt-Lautsprecheransage):
Liebe Kunden, heute von 15 bis 17 Uhr: Degustationsstand in der Käseabteilung. Probieren Sie unsere neuen Spezialitäten aus der Schweiz. Der Kauf ist natürlich freiwillig.

Text 5 (Sprachschule-Telefon):
Guten Tag, Sie haben die Sprachschule Lingua erreicht. Leider ist momentan niemand erreichbar. Unsere Bürozeiten sind Montag bis Freitag, 9 bis 17 Uhr. Bitte hinterlassen Sie nach dem Ton eine Nachricht mit Ihrem Namen und Ihrer Telefonnummer. Wir rufen so schnell wie möglich zurück.
        """.trimIndent(),
        questions = listOf(
            MCQuestion(id="t2_h3_16", text="Im Kino ist es erlaubt, mit dem Handy zu fotografieren.",
                options=listOf("Richtig","Falsch"), correctIndex=1,
                explanation="'Fotografieren... ist strengstens verboten' — falsch."),
            MCQuestion(id="t2_h3_17", text="Die Buslinie 18 hält jetzt auch am Krankenhaus.",
                options=listOf("Richtig","Falsch"), correctIndex=0,
                explanation="'hält jetzt auch am Krankenhaus Nord' — richtig."),
            MCQuestion(id="t2_h3_18", text="Kinder unter 6 Jahren müssen keinen Eintritt bezahlen.",
                options=listOf("Richtig","Falsch"), correctIndex=0,
                explanation="'Für Kinder unter 6 Jahren ist der Eintritt frei' — richtig."),
            MCQuestion(id="t2_h3_19", text="Im Supermarkt muss man die Käsespezialitäten kaufen.",
                options=listOf("Richtig","Falsch"), correctIndex=1,
                explanation="'Der Kauf ist natürlich freiwillig' — man muss nicht kaufen."),
            MCQuestion(id="t2_h3_20", text="Die Sprachschule Lingua hat montags bis freitags ab 9 Uhr geöffnet.",
                options=listOf("Richtig","Falsch"), correctIndex=0,
                explanation="'Montag bis Freitag, 9 bis 17 Uhr' — richtig.")
        )
    )
)

val TelcExam2SchreibenTasks = listOf(
    SchreibenTask(
        title = "Schriftlicher Ausdruck – Halbformeller Brief",
        instruction = """Sie haben diese Anzeige in einer Zeitung gelesen:

„Suche Mitbewohnerin/Mitbewohner für 3-Zimmer-Wohnung in München-Schwabing.
2 Zimmer bereits belegt. Freies Zimmer: 18 m², ruhige Lage, gute Verkehrsanbindung.
Miete: 650 € warm. Kontakt: zimmer.muenchen@email.de"

Schreiben Sie eine E-Mail an den Vermieter/die Vermieterin.
Schreiben Sie zu folgenden Punkten (ca. 100 Wörter):
• Stellen Sie sich kurz vor
• Warum interessiert Sie das Zimmer?
• Wann könnten Sie einziehen?
• Fragen Sie nach einem Besichtigungstermin""",
        wordCount = "ca. 100 Wörter",
        keyPoints = listOf(
            "Anrede: 'Sehr geehrte Damen und Herren,' oder 'Guten Tag,'",
            "Kurze Vorstellung: Name, Alter, Beruf/Studium",
            "Warum dieses Zimmer? (Lage, Größe, Preis, WG-Leben)",
            "Möglicher Einzugstermin nennen",
            "Um einen Besichtigungstermin bitten",
            "Abschluss: 'Mit freundlichen Grüßen' oder 'Liebe Grüße'"
        )
    )
)

val TelcExam2SprechenTasks = listOf(
    SprechenTask(
        title = "Sprechen Teil 1 – Kontaktaufnahme",
        instruction = "Sprechen Sie mit Ihrem Partner. Stellen Sie sich gegenseitig Fragen zu Alltag und Erfahrungen.",
        topics = listOf(
            "Was haben Sie zuletzt im Urlaub gemacht?",
            "Kochen Sie gerne? Was ist Ihr Lieblingsessen?",
            "Welche Sprachen sprechen Sie? Wie haben Sie sie gelernt?",
            "Was machen Sie am Wochenende normalerweise?",
            "Haben Sie Geschwister? Wie ist Ihre Familie?"
        )
    ),
    SprechenTask(
        title = "Sprechen Teil 2 – Bild beschreiben und diskutieren",
        instruction = """Beschreiben Sie das Bild und diskutieren Sie das Thema mit Ihrem Partner.
Was sehen Sie? Was sind Vor- und Nachteile? Was ist Ihre Meinung?""",
        topics = listOf(
            "Bild: Schüler lernen an Tablets in der Schule vs. mit Büchern — Digitalisierung in der Bildung: Fluch oder Segen?",
            "Bild: Stadtmenschen vs. Landleben — Wo möchten Sie lieber wohnen und warum?",
            "Bild: Großfamilie zusammen essen vs. alleine essen — Haben Familien heute noch Zeit füreinander?"
        )
    ),
    SprechenTask(
        title = "Sprechen Teil 3 – Gemeinsam planen",
        instruction = """Ihre Deutschkurs-Gruppe möchte einen gemeinsamen Ausflug machen.
Einigen Sie sich auf:
• Wohin? (Stadtbesichtigung, Museum, Wanderung, Strand, Freizeitpark?)
• Wann? (Wochentag oder Wochenende? Wie lange?)
• Wie fahren Sie hin? (öffentliche Verkehrsmittel, Auto, Fahrrad?)
• Was brauchen Sie mit? (Essen, Kleidung, Geld?)

Machen Sie Vorschläge, begründen Sie sie und reagieren Sie auf die Ideen Ihres Partners.""",
        topics = listOf(
            "Reiseziel: Museum / Natur / Stadt / Freizeitpark",
            "Wochentag vs. Wochenende",
            "Verkehrsmittel: Bahn / Auto / Fahrrad",
            "Verpflegung: selbst mitbringen / Restaurant"
        )
    )
)


val telcExam1 = ExamContent(
    id = "telc_1",
    name = "Modelltest 1",
    provider = ExamProvider.TELC,
    lesenParts = TelcExam1LesenParts,
    hoerenParts = TelcExam1HoerenParts,
    schreibenTasks = TelcExam1SchreibenTasks,
    sprechenTasks = TelcExam1SprechenTasks
)

val telcExam2 = ExamContent(
    id = "telc_2",
    name = "Modelltest 2",
    provider = ExamProvider.TELC,
    lesenParts = TelcExam2LesenParts,
    hoerenParts = TelcExam2HoerenParts,
    schreibenTasks = TelcExam2SchreibenTasks,
    sprechenTasks = TelcExam2SprechenTasks
)

val allExams: Map<ExamProvider, List<ExamContent>> = mapOf(
    ExamProvider.GOETHE to listOf(GoetheExam1, GoetheExam2),
    ExamProvider.OESD to listOf(OesdExam1, OesdExam2),
    ExamProvider.TELC to listOf(telcExam1, telcExam2)
)
