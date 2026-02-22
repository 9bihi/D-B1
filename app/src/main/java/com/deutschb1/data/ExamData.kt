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
    val questions: List<MultipleChoiceQuestion>
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
    val provider: ExamProvider,
    val lesenParts: List<ReadingPart>,
    val hoerenParts: List<HoerenPart>,
    val schreibenTasks: List<SchreibenTask>,
    val sprechenTasks: List<SprechenTask>
)

// ─── Goethe B1 Content ───────────────────────────────────────────────────────

val GoetheExam = ExamContent(
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

// ─── ÖSD B1 Content ──────────────────────────────────────────────────────────

val OesdExam = ExamContent(
    provider = ExamProvider.OESD,
    lesenParts = listOf(
        ReadingPart(
            partNumber = 1,
            title = "Teil 1 – Globales Leseverstehen",
            instruction = "Lesen Sie den Text und beantworten Sie die Fragen.",
            text = """
                Wien, eine Stadt für alle
                
                Wien gilt seit Jahren als eine der lebenswertesten Städte der Welt. 
                Jedes Jahr strömen Millionen von Touristen in die österreichische Hauptstadt, aber auch immer mehr Menschen entscheiden sich, dauerhaft hier zu leben. 
                
                Was macht Wien so besonders? Die Stadt bietet eine einzigartige Kombination aus imperialem Erbe und moderner Lebensqualität. 
                Die U-Bahn fährt bis Mitternacht, Kaffeehaus-Kultur ist UNESCO-Kulturerbe und das Gesundheitssystem gilt als eines der besten weltweit. 
                
                Besonders attraktiv für internationale Zuwanderer ist das hohe Sicherheitsniveau und die gute Vereinbarkeit von Familie und Beruf. 
                Die Stadt bietet umfangreiche Kinderbetreuungsmöglichkeiten und viele Betriebe ermöglichen flexible Arbeitszeiten. 
                Laut einer aktuellen Studie würden 92% der Wien-Bewohner wieder in die Stadt ziehen, wenn sie die Wahl hätten.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(1, "Warum siedeln sich immer mehr Menschen dauerhaft in Wien an?",
                    listOf("Wegen der niedrigen Mieten", "Wegen der hohen Lebensqualität", "Wegen des Klimas"), 1,
                    "Wien bietet hohe Lebensqualität mit Sicherheit, Gesundheit und Familienfreundlichkeit."),
                MultipleChoiceQuestion(2, "Was ist laut Text ein UNESCO-Kulturerbe?",
                    listOf("Die Wiener U-Bahn", "Die Kaffeehaus-Kultur", "Das Gesundheitssystem"), 1,
                    "Die Kaffeehaus-Kultur ist laut Text UNESCO-Kulturerbe."),
                MultipleChoiceQuestion(3, "Was zeigt die Studie am Ende des Textes?",
                    listOf("Die meisten Bewohner würden wieder nach Wien ziehen", "92% wollen Wien verlassen", "Weniger Menschen mögen Wien"), 0,
                    "Laut Studie würden 92% der Wien-Bewohner wieder hierher ziehen.")
            )
        ),
        ReadingPart(
            partNumber = 2,
            title = "Teil 2 – Selektives Lesen",
            instruction = "Welche Information finden Sie in welchem Text? Ordnen Sie zu.",
            text = """
                Text A – Jobangebot: Wir suchen ab sofort einen Kundenberater (m/w/d) für unser Team in Graz. 
                Voraussetzungen: Abgeschlossene kaufmännische Ausbildung, sehr gute Deutschkenntnisse (min. C1), 
                Erfahrung im Kundenkontakt. Wir bieten: 38,5 Std./Woche, 2.400€ brutto, betriebliche Altersvorsorge.
                Bewerbungen an: jobs@firma-graz.at
                
                Text B – Stellenangebot: Gasthaus Zum Goldenen Hirschen sucht Koch/Köchin.
                Anforderungen: Berufsausbildung als Koch, Bereitschaft zu Wochenendarbeit, Teamgeist.
                Arbeitszeit: variabel, ca. 40 Std./Woche. Gehalt: nach KV Gastronomie.
                Kontakt: goldener.hirsch@gasthaus.at
                
                Text C – Praktikum: Das österreichische Rote Kreuz bietet Praktikumsplätze für engagierte 
                junge Menschen (18-25 J.). Dauer: 3 Monate, Aufwandsentschädigung 400€/Monat. 
                Geeignet für Studierende der Sozialarbeit, Psychologie oder Pädagogik.
                Infos: praktikum@roteskreuz.at
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(4, "Wo kann man sich für eine unbezahlte Stelle mit geringer Aufwandsentschädigung bewerben?",
                    listOf("Text A", "Text B", "Text C"), 2,
                    "Das Rote Kreuz bietet Praktikum mit Aufwandsentschädigung."),
                MultipleChoiceQuestion(5, "Welche Stelle erfordert Kenntnisse im Umgang mit Kunden?",
                    listOf("Text A", "Text B", "Text C"), 0,
                    "Text A verlangt Erfahrung im Kundenkontakt.")
            )
        )
    ),
    hoerenParts = listOf(
        HoerenPart(
            partNumber = 1,
            title = "Teil 1 – Radiobericht",
            instruction = "Sie hören einen Radiobericht. Was ist richtig?",
            transcript = """
                Guten Morgen, hier ist Ö1. Es folgen die Nachrichten.
                
                Das österreichische Parlament hat gestern ein neues Klimaschutzgesetz verabschiedet. 
                Das Gesetz sieht vor, dass Österreich bis 2040 klimaneutral sein soll – zehn Jahre früher als von der EU gefordert. 
                Dafür werden die Investitionen in erneuerbare Energien um 30 Prozent erhöht. 
                Wirtschaftsverbände kritisieren das Gesetz als zu ambitioniert und befürchten höhere Kosten für Unternehmen. 
                Umweltorganisationen begrüßen den Schritt, fordern aber noch schnellere Maßnahmen.
                
                Zum Wetter: In Wien bleibt es heute bewölkt bei 12 Grad. Ab morgen wird es wärmer.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(1, "Wann soll Österreich klimaneutral sein?",
                    listOf("2030", "2040", "2050"), 1,
                    "Das Ziel ist 2040 – zehn Jahre früher als EU-Vorgabe."),
                MultipleChoiceQuestion(2, "Wie reagieren Wirtschaftsverbände auf das Gesetz?",
                    listOf("Positiv", "Neutral", "Kritisch"), 2,
                    "Wirtschaftsverbände kritisieren das Gesetz als zu ambitioniert.")
            )
        )
    ),
    schreibenTasks = listOf(
        SchreibenTask(
            taskNumber = 1,
            title = "Schreiben 1 – Formeller Brief",
            prompt = """
                Sie haben in einem Geschäft ein Produkt gekauft (z.B. Smartphone, Laptop), 
                das nach einer Woche nicht mehr funktioniert. Sie sind sehr unzufrieden.
                
                Schreiben Sie einen formellen Beschwerdebrief (circa 150 Wörter):
                • Erklären Sie das Problem genau
                • Schildern Sie, wann und wo Sie das Produkt gekauft haben
                • Formulieren Sie Ihre Forderung (Reparatur, Ersatz oder Rückerstattung)
                • Setzen Sie eine Frist für die Antwort
            """.trimIndent(),
            minWords = 150,
            hints = listOf(
                "Formelle Anrede: 'Sehr geehrte Damen und Herren,'",
                "Einleitung: Nennen Sie den Kaufzeitpunkt und das Produkt",
                "Problembeschreibung: Was genau funktioniert nicht?",
                "Forderung: klar und höflich formulieren",
                "Frist setzen: 'Ich erwarte Ihre Antwort bis zum...'",
                "Formeller Abschluss: 'Mit freundlichen Grüßen, ...'"
            )
        )
    ),
    sprechenTasks = listOf(
        SprechenTask(
            taskNumber = 1,
            title = "Sprechen 1 – Informationen erfragen",
            instruction = "Sie sehen eine Anzeige für einen Deutschkurs. Fragen Sie den Kursanbieter nach Details.",
            topic = "Kurs: 'Deutsch Intensiv B1 → B2' – Details unklar. Fragen Sie nach: Kursdauer, Kurszeiten, Kosten, Prüfungsvorbereitung, Möglichkeit Online/Präsenz.",
            prepTimeSec = 90,
            speakTimeSec = 180,
            tips = listOf(
                "W-Fragen: Wann? Wo? Wie viel? Wie lange?",
                "Höflich fragen: 'Könnten Sie mir sagen...? / Darf ich fragen...?'",
                "Nachfragen: 'Könnten Sie das bitte wiederholen?'",
                "Interesse zeigen: 'Das klingt interessant!'",
                "Abschlussfrage: 'Wie kann ich mich anmelden?'"
            )
        ),
        SprechenTask(
            taskNumber = 2,
            title = "Sprechen 2 – Diskussion",
            instruction = "Diskutieren Sie mit Ihrem Partner über ein aktuelles Thema.",
            topic = "Homeoffice: Soll das Arbeiten von zuhause Pflicht oder Kür sein? Diskutieren Sie Pro und Contra und kommen Sie zu einer gemeinsamen Einigung.",
            prepTimeSec = 60,
            speakTimeSec = 240,
            tips = listOf(
                "Position klar machen: 'Ich bin dafür/dagegen, weil...'",
                "Auf Partner eingehen: 'Du hast recht, aber...'",
                "Beispiele nennen: 'Zum Beispiel...'",
                "Einigung suchen: 'Vielleicht können wir uns darauf einigen, dass...'"
            )
        )
    )
)

// ─── TELC B1 Content ─────────────────────────────────────────────────────────

val TelcExam = ExamContent(
    provider = ExamProvider.TELC,
    lesenParts = listOf(
        ReadingPart(
            partNumber = 1,
            title = "Leseverstehen 1 – Textrekonstruktion",
            instruction = "Welcher Satz passt in welche Lücke? Es gibt mehr Sätze als Lücken.",
            text = """
                Die Geschichte des Fahrrades
                
                Das Fahrrad ist eine der wichtigsten Erfindungen der Menschheit. ___[1]___
                Es wurde im Jahr 1817 von Karl von Drais erfunden und hieß damals „Laufmaschine". 
                ___[2]___ Man musste also noch mit den Füßen abstoßen, um voranzukommen.
                
                Erst ca. 50 Jahre später wurde das Pedal erfunden. ___[3]___
                Seitdem hat sich das Fahrrad stark weiterentwickelt. Heute gibt es Rennräder, Mountainbikes und E-Bikes.
                ___[4]___ Besonders in Städten setzt man auf das Rad, um Staus zu vermeiden und die Umwelt zu schonen.
                
                Fehlende Sätze:
                A) Damals hatte es noch keine Pedale.
                B) Heutzutage ist das Fahrrad weltweit das meistgenutzte Fortbewegungsmittel.
                C) Es ist umweltfreundlich, günstig und hält fit.
                D) Das Fahrrad revolutionierte die Mobilität der Menschen grundlegend.
                E) Dadurch wurde das Radfahren viel einfacher und schneller.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(1, "Welcher Satz passt in Lücke [1]?", listOf("A", "B", "C", "D", "E"), 3,
                    "Satz D ('revolutionierte die Mobilität') passt als allgemeine Einleitung nach der ersten Aussage."),
                MultipleChoiceQuestion(2, "Welcher Satz passt in Lücke [2]?", listOf("A", "B", "C", "D", "E"), 0,
                    "Satz A erklärt, dass es damals keine Pedale gab – passt zum Kontext."),
                MultipleChoiceQuestion(3, "Welcher Satz passt in Lücke [3]?", listOf("A", "B", "C", "D", "E"), 4,
                    "Satz E ('Dadurch wurde das Radfahren viel einfacher') passt nach der Erfindung des Pedals."),
                MultipleChoiceQuestion(4, "Welcher Satz passt in Lücke [4]?", listOf("A", "B", "C", "D", "E"), 1,
                    "Satz B ('meistgenutztes Fortbewegungsmittel') passt als Überleitung zu heutiger Nutzung.")
            )
        ),
        ReadingPart(
            partNumber = 2,
            title = "Leseverstehen 2 – Detailverstehen",
            instruction = "Lesen Sie die Stellenanzeige und beantworten Sie die Fragen.",
            text = """
                STELLENANZEIGE – Softwareentwickler/in (m/w/d)
                
                Die TechStart GmbH in Frankfurt sucht ab sofort eine/n engagierte/n Entwickler/in.
                
                IHR PROFIL:
                • Abgeschlossenes Studium der Informatik oder vergleichbare Ausbildung
                • Mindestens 2 Jahre Erfahrung in der Softwareentwicklung
                • Sehr gute Kenntnisse in Java oder Python
                • Teamfähigkeit und selbstständige Arbeitsweise
                • Deutsch C1, Englisch B2
                
                WIR BIETEN:
                • Unbefristete Stelle, 40 Stunden/Woche
                • Gehalt: 55.000–70.000 €/Jahr (je nach Erfahrung)
                • 30 Tage Urlaub + Weihnachtsgeld
                • Homeoffice bis zu 3 Tage pro Woche möglich
                • Firmenkantine und Sportangebote
                
                BEWERBUNG: Senden Sie Ihre Unterlagen an hr@techstart.de
                Bewerbungsschluss: 31. März 2024
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(5, "Was ist an dieser Stelle besonders flexibel?",
                    listOf("Die Arbeitszeit ist flexibel", "Homeoffice ist teilweise möglich", "Man kann die Sprache wählen"), 1,
                    "Bis zu 3 Tage Homeoffice/Woche sind möglich."),
                MultipleChoiceQuestion(6, "Welches Sprachniveau wird für Englisch verlangt?",
                    listOf("B1", "B2", "C1"), 1,
                    "Englisch B2 wird laut Stellenanzeige verlangt."),
                MultipleChoiceQuestion(7, "Wie viele Erfahrungsjahre werden mindestens gefordert?",
                    listOf("1 Jahr", "2 Jahre", "3 Jahre"), 1,
                    "Mindestens 2 Jahre Erfahrung werden gefordert.")
            )
        )
    ),
    hoerenParts = listOf(
        HoerenPart(
            partNumber = 1,
            title = "Hörverstehen 1 – Dialoge",
            instruction = "Sie hören Gespräche. Entscheiden Sie: Was ist richtig?",
            transcript = """
                [Dialog 1 – Im Reisebüro]
                Kundin: Guten Tag, ich möchte gerne eine Reise nach Griechenland buchen.
                Berater: Sehr gerne. Wann möchten Sie reisen?
                Kundin: Am liebsten im Juli, für zwei Wochen.
                Berater: Haben Sie ein Budget?
                Kundin: So um die 1.500 Euro pro Person, alles inklusive wenn möglich.
                Berater: Das ist machbar. Wir haben ein schönes Angebot auf Kreta – Halbpension, Flug inklusive, für 1.380 Euro.
                Kundin: Klingt gut! Aber ist WLAN im Preis inbegriffen?
                Berater: Ja, im Hotelzimmer kostenlos.
                
                [Dialog 2 – Beim Arzt]
                Arzt: Was fehlt Ihnen denn?
                Patient: Ich habe seit drei Tagen starke Kopfschmerzen und bin sehr müde.
                Arzt: Haben Sie Fieber gemessen?
                Patient: Ja, 38,2 Grad heute Morgen.
                Arzt: Das klingt nach einem grippalen Infekt. Ich schreibe Sie heute und morgen krank.
            """.trimIndent(),
            questions = listOf(
                MultipleChoiceQuestion(1, "Was möchte die Kundin für ihre Griechenlandreise?",
                    listOf("Nur Frühstück", "Halbpension", "Vollpension"), 1,
                    "Der Berater bietet Halbpension an, und die Kundin akzeptiert das Angebot."),
                MultipleChoiceQuestion(2, "Wie lange ist der Patient krank geschrieben?",
                    listOf("Einen Tag", "Zwei Tage", "Drei Tage"), 1,
                    "Der Arzt schreibt ihn heute und morgen krank = 2 Tage.")
            )
        )
    ),
    schreibenTasks = listOf(
        SchreibenTask(
            taskNumber = 1,
            title = "Schreiben – Forumsbeitrag",
            prompt = """
                In einem Online-Forum wurde die Frage gestellt: 
                „Sollte das Rauchen in öffentlichen Bereichen wie Parks und Fußgängerzonen verboten werden?"
                
                Schreiben Sie einen Forumsbeitrag (circa 120 Wörter):
                • Stellen Sie Ihre Meinung klar dar (dafür oder dagegen)
                • Nennen Sie mindestens 2 Gründe
                • Beziehen Sie sich auf die Meinung anderer
                • Schreiben Sie einen abschließenden Appell
            """.trimIndent(),
            minWords = 120,
            hints = listOf(
                "Beginnen Sie mit Ihrer Position: 'Ich bin der Meinung, dass...'",
                "Gründe nennen: 'Erstens... Zweitens...'",
                "Auf andere eingehen: 'Manche sagen... Aber...'",
                "Schluss: 'Deshalb appelliere ich...'",
                "Benutzen Sie Konjunktiv für Meinungen: 'Es wäre besser, wenn...'"
            )
        )
    ),
    sprechenTasks = listOf(
        SprechenTask(
            taskNumber = 1,
            title = "Sprechen – Präsentation",
            instruction = "Halten Sie eine kurze Präsentation zu einem Thema.",
            topic = "Thema: 'Meine Heimatstadt / mein Heimatland' – Stellen Sie Ihre Heimat vor: Lage, Besonderheiten, Sehenswürdigkeiten, Kultur, Essen.",
            prepTimeSec = 120,
            speakTimeSec = 180,
            tips = listOf(
                "Struktur: Einleitung → Hauptteil → Schluss",
                "Einleitung: 'Ich möchte Ihnen heute... vorstellen'",
                "Fachvokabular: Lage, Bevölkerung, Klima, Sehenswürdigkeiten",
                "Bilder beschreiben falls vorhanden",
                "Schluss: Persönliche Empfehlung oder Einladung"
            )
        )
    )
)

// ─── All Exams Map ────────────────────────────────────────────────────────────

val allExams: Map<ExamProvider, ExamContent> = mapOf(
    ExamProvider.GOETHE to GoetheExam,
    ExamProvider.OESD to OesdExam,
    ExamProvider.TELC to TelcExam
)
