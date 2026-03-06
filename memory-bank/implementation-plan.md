# Implementation Plan - Deutsch B1 Exam
## Version 2.3 — UI Cleanup + Content Expansion

---

## 🗑️ PHASE 0-A — REMOVE TRANSLATION FEATURES

### 0-A.1 Remove "Translate" Bottom Nav Tab

**File**: `MainActivity.kt`

Find the list of bottom nav items (likely a `listOf(NavItem(...))` or similar). Remove the `Translate`/Translation entry. The bar now has **3 tabs**: Home, Exams, Learn.

```kotlin
// BEFORE (4 tabs):
val navItems = listOf(
    NavItem("Home",   Icons.Default.Home,      Screen.Home),
    NavItem("Exams",  Icons.Default.MenuBook,  Screen.Exams),
    NavItem("Learn",  Icons.Default.School,    Screen.Learn),
    NavItem("Translate", Icons.Default.Translate, Screen.Translation)  // ← DELETE THIS LINE
)

// AFTER (3 tabs):
val navItems = listOf(
    NavItem("Home",   Icons.Default.Home,      Screen.Home),
    NavItem("Exams",  Icons.Default.MenuBook,  Screen.Exams),
    NavItem("Learn",  Icons.Default.School,    Screen.Learn)
)
```

The `FloatingGlassNavBar` uses dynamic positioning — recalculates pill position from tab count automatically. No other changes needed to the NavBar itself.

### 0-A.2 Remove Translation Card from HomeScreen

**File**: `ui/home/HomeScreen.kt`

Remove the "Translation" quick-access card/row item. It shows subtitle "Deutsch ↔ English · LibreTranslate".

### 0-A.3 Remove API Tools Section from LearnHomeScreen

**File**: `ui/learn/LearnHomeScreen.kt`

Remove the entire "API Tools" section block which contains:
- Online Dictionary card
- Verb Conjugation card

Keep all other Learn content cards (Konnektoren, Grammatik, Sprechen B1, Wortschatz, etc.) untouched.

### 0-A.4 Clean Up AppNavGraph

**File**: `navigation/AppNavGraph.kt`

Remove (or comment out) these routes if they are no longer reachable:
- `Screen.Translation`
- `Screen.Dictionary`  
- `Screen.VerbConjugation`

Keep the sealed class entries if they might be referenced elsewhere, just remove the `composable(Screen.X.route) { ... }` blocks.

---

## 🔥 PHASE 0-B — EXAM CONTENT: FULL DATA

### Official B1 Exam Structures (Researched)

**Goethe-Zertifikat B1:**
- Lesen (65 min): 4 Teile, 30 Punkte total
  - Teil 1: Read 8 short texts → match to 5 statement cards (Überschriften zuordnen)
  - Teil 2: Read 1 longer text → 5 Richtig/Falsch questions
  - Teil 3: Read 1 text → 5 MC questions (3 options each)
  - Teil 4: Read 10 notices/ads → choose from 3 options each
- Hören (40 min): 4 Teile, 30 Punkte
  - Teil 1: 5 radio conversations → 2 Richtig/Falsch each = 10 questions
  - Teil 2: 1 presentation/speech → 5 MC questions
  - Teil 3: 7 short monologues → match to 5 categories
  - Teil 4: 1 radio interview → 10 Richtig/Falsch
- Schreiben (60 min): 2 Teile, 30 Punkte
  - Teil 1: Forum/blog reply, 80+ words. React to a given opinion.
  - Teil 2: Formal letter (complaint/request), 80+ words, 5 required points.
- Sprechen (15 min): 3 Teile
  - Teil 1: Partner interview (sich vorstellen, Fragen stellen)
  - Teil 2: Short presentation on a B1 topic (1 min) + discussion
  - Teil 3: Planning activity together with partner (compromise)

**ÖSD Zertifikat B1:**
- Lesen (70 min): 3 Teile
  - Teil 1: 6 MC questions (global understanding of 3 texts)
  - Teil 2: 7 True/False/Not mentioned questions (detail reading)
  - Teil 3: 10 gap-fill (choose word from list to complete text)
- Hören (40 min): 3 Teile
  - Teil 1: 8 True/False questions (1 longer monologue)
  - Teil 2: 5 MC questions (formal dialogue/conversation)
  - Teil 3: 4 MC questions (short announcements)
- Schreiben (60 min): 2 Teile
  - Teil 1: Semi-formal email or message, 80+ words
  - Teil 2: Formal letter with required points, 100+ words
- Sprechen (16 min): 3 Teile
  - Teil 1: Image description + discussion
  - Teil 2: Role play (making arrangements/complaints)
  - Teil 3: Topic presentation + partner questions

**TELC Deutsch B1:**
- Lesen + Sprachbausteine (90 min combined): 4 Teile
  - Lesen Teil 1: 5 short texts → match headlines
  - Lesen Teil 2: 1 longer article → 5 True/False
  - Lesen Teil 3: 5 notices/signs → 3-option MC
  - Sprachbausteine Teil 1: 10 gap-fill MC (grammar/vocab)
  - Sprachbausteine Teil 2: 10 gap-fill open (write the word)
- Hören (20 min): 2 Teile
  - Teil 1: 5 short dialogues → 3-option MC
  - Teil 2: 1 interview → 5 True/False
- Schreiben (30 min): 1 Teil
  - Formal/semi-formal letter, 5 required bullet points, ~100 words
- Sprechen (15 min): 2 Teile
  - Teil 1: Short topic presentation (1–2 min)
  - Teil 2: Partner discussion + reaching agreement

---

### ExamData.kt — Goethe Modelltest 2 Content

Add to `ExamData.kt` (or the file containing `GoetheExam2`):

```kotlin
// ─── GOETHE MODELLTEST 2 ───────────────────────────────────────────────

val GoetheExam2LesenParts = listOf(

    // TEIL 1 — Überschriften zuordnen
    LesenPart(
        title = "Lesen Teil 1 – Überschriften zuordnen",
        instruction = "Lesen Sie die Texte 1–5 und wählen Sie für jeden Text die passende Überschrift (a–h). Vier Überschriften bleiben übrig.",
        questions = listOf(
            MCQuestion(
                id = "g2_l1_q1",
                text = "Text 1: 'Immer mehr Menschen in Deutschland entscheiden sich dafür, ihre Lebensmittel direkt vom Bauernhof zu kaufen. Regionale Produkte sind frischer und die Transportwege sind kürzer, was gut für die Umwelt ist.'",
                options = listOf(
                    "a) Stadtleben hat viele Vorteile",
                    "b) Einkaufen beim lokalen Erzeuger wird beliebter",
                    "c) Supermärkte verlieren Kunden",
                    "d) Biologische Landwirtschaft wächst"
                ),
                correctIndex = 1,
                explanation = "Der Text beschreibt, dass mehr Menschen direkt beim Bauernhof kaufen — das entspricht 'Einkaufen beim lokalen Erzeuger wird beliebter'."
            ),
            MCQuestion(
                id = "g2_l1_q2",
                text = "Text 2: 'Das Home-Office hat während der Pandemie an Bedeutung gewonnen. Viele Unternehmen bieten nun flexible Arbeitsmodelle an. Mitarbeiter können selbst entscheiden, ob sie von zu Hause oder im Büro arbeiten möchten.'",
                options = listOf(
                    "a) Arbeit von zu Hause wird normaler",
                    "b) Büros werden kleiner",
                    "c) Technologie ersetzt Menschen",
                    "d) Unternehmen sparen Kosten"
                ),
                correctIndex = 0,
                explanation = "Der Text erklärt flexible Arbeitsmodelle und Home-Office — 'Arbeit von zu Hause wird normaler' passt am besten."
            ),
            MCQuestion(
                id = "g2_l1_q3",
                text = "Text 3: 'In deutschen Städten gibt es immer mehr Fahrradwege. Die Stadtplanung fördert umweltfreundliche Mobilität. Fahrräder und E-Scooter sind heute eine echte Alternative zum Auto.'",
                options = listOf(
                    "a) Autos werden verboten",
                    "b) Städte investieren in grüne Mobilität",
                    "c) Fahrräder werden teurer",
                    "d) Öffentliche Verkehrsmittel verbessern sich"
                ),
                correctIndex = 1,
                explanation = "Mehr Fahrradwege und E-Scooter als Autoalternative = 'Städte investieren in grüne Mobilität'."
            ),
            MCQuestion(
                id = "g2_l1_q4",
                text = "Text 4: 'Digitale Bibliotheken ermöglichen es, Bücher und Zeitschriften online zu lesen. Nutzer brauchen keinen Bibliotheksausweis mehr und können rund um die Uhr auf Inhalte zugreifen.'",
                options = listOf(
                    "a) Traditionelle Bibliotheken schließen",
                    "b) Online-Lesen wird einfacher zugänglich",
                    "c) Bücher werden zu teuer",
                    "d) Verlage verlieren Leser"
                ),
                correctIndex = 1,
                explanation = "Digitale Bibliotheken = jederzeit Online-Zugang = 'Online-Lesen wird einfacher zugänglich'."
            ),
            MCQuestion(
                id = "g2_l1_q5",
                text = "Text 5: 'Viele Jugendliche in Deutschland machen ein Freiwilliges Soziales Jahr (FSJ). Sie helfen in Krankenhäusern, Schulen oder sozialen Einrichtungen und sammeln wertvolle Berufserfahrung.'",
                options = listOf(
                    "a) Jugendliche verdienen mehr Geld",
                    "b) Soziales Engagement bei Jugendlichen ist populär",
                    "c) Schulen brauchen mehr Lehrer",
                    "d) Krankenhäuser stellen mehr Personal ein"
                ),
                correctIndex = 1,
                explanation = "FSJ = freiwilliges soziales Engagement bei Jugendlichen."
            )
        )
    ),

    // TEIL 2 — Richtig/Falsch
    LesenPart(
        title = "Lesen Teil 2 – Richtig oder Falsch?",
        instruction = "Lesen Sie den Text und entscheiden Sie: Sind die Aussagen richtig (R) oder falsch (F)?",
        contextText = """
Klimawandel – Was kann ich tun?

Der Klimawandel ist eines der größten Probleme unserer Zeit. Wissenschaftler sind sich einig: Die Erde wird wärmer, und das hat weitreichende Folgen. Aber was kann jede einzelne Person tun?

Erstens ist der Konsum sehr wichtig. Wenn man weniger Fleisch isst und mehr regionale Produkte kauft, verringert man seinen CO₂-Fußabdruck erheblich. Die Fleischproduktion ist weltweit für etwa 14,5% der Treibhausgasemissionen verantwortlich.

Zweitens spielt Mobilität eine große Rolle. Mit dem Fahrrad zur Arbeit fahren, öffentliche Verkehrsmittel nutzen oder ein Elektroauto kaufen — alle diese Maßnahmen helfen. Flugreisen sollte man auf das Nötigste reduzieren, da ein Flug von Frankfurt nach New York so viel CO₂ produziert wie ein halbes Jahr Autofahren.

Drittens ist Energie zu Hause ein wichtiges Thema. LED-Lampen verwenden, die Heizung nicht zu hoch drehen und Geräte nicht im Standby-Modus lassen — das sind einfache Maßnahmen mit großer Wirkung.

Experten sagen, dass individuelle Maßnahmen zwar wichtig sind, aber politische Veränderungen noch wichtiger. Jeder Bürger sollte sein Wahlrecht nutzen und für umweltfreundliche Politik stimmen.
        """.trimIndent(),
        questions = listOf(
            MCQuestion(
                id = "g2_l2_q1",
                text = "Die Wissenschaft ist gespalten beim Thema Klimawandel.",
                options = listOf("Richtig", "Falsch"),
                correctIndex = 1,
                explanation = "Der Text sagt: 'Wissenschaftler sind sich einig' — also nicht gespalten. Falsch."
            ),
            MCQuestion(
                id = "g2_l2_q2",
                text = "Weniger Fleisch zu essen kann den CO₂-Fußabdruck reduzieren.",
                options = listOf("Richtig", "Falsch"),
                correctIndex = 0,
                explanation = "Direkt im Text bestätigt: 'Wenn man weniger Fleisch isst... verringert man seinen CO₂-Fußabdruck erheblich.' Richtig."
            ),
            MCQuestion(
                id = "g2_l2_q3",
                text = "Ein Flug von Frankfurt nach New York produziert mehr CO₂ als ein ganzes Jahr Autofahren.",
                options = listOf("Richtig", "Falsch"),
                correctIndex = 1,
                explanation = "Der Text sagt 'ein halbes Jahr Autofahren' — nicht ein ganzes Jahr. Falsch."
            ),
            MCQuestion(
                id = "g2_l2_q4",
                text = "LED-Lampen sind ein Beispiel für Energiesparen zu Hause.",
                options = listOf("Richtig", "Falsch"),
                correctIndex = 0,
                explanation = "Direkt im Text: 'LED-Lampen verwenden... einfache Maßnahmen mit großer Wirkung.' Richtig."
            ),
            MCQuestion(
                id = "g2_l2_q5",
                text = "Laut Experten sind individuelle Maßnahmen wichtiger als politische Veränderungen.",
                options = listOf("Richtig", "Falsch"),
                correctIndex = 1,
                explanation = "Experten sagen 'politische Veränderungen noch wichtiger' — also das Gegenteil. Falsch."
            )
        )
    ),

    // TEIL 3 — Multiple Choice zum Text
    LesenPart(
        title = "Lesen Teil 3 – Multiple Choice",
        instruction = "Lesen Sie den Text und wählen Sie für jede Aufgabe die richtige Antwort (a, b oder c).",
        contextText = """
Leben in einer Wohngemeinschaft (WG)

Immer mehr junge Menschen entscheiden sich für das Leben in einer Wohngemeinschaft. Die Gründe dafür sind vielfältig: Mieten in deutschen Städten sind in den letzten Jahren stark gestiegen, und eine WG macht das Wohnen erschwinglicher. Außerdem schätzen viele die soziale Komponente — man ist nicht allein und kann Kosten für Möbel, Internet und manchmal sogar Essen teilen.

Natürlich bringt das WG-Leben auch Herausforderungen mit sich. Man muss Kompromisse eingehen: beim Sauberhalten der gemeinsamen Räume, bei den Schlafzeiten oder beim Musikhören. Konflikte entstehen oft wegen ungleicher Hausarbeit. Experten empfehlen, gleich am Anfang klare Regeln aufzustellen — zum Beispiel einen Putzplan zu erstellen.

Der WG-Finder-Markt in Deutschland ist sehr aktiv. Websites wie WG-Gesucht.de haben Millionen von Nutzern. Bei der Zimmerwahl ist ein persönliches Gespräch wichtig — sogenannte 'Castings', bei denen mehrere Interessenten gleichzeitig eingeladen werden, sind in beliebten Städten wie Berlin oder München üblich.

Für internationale Studenten ist eine WG oft der einfachste Weg, Deutsch zu üben und Einheimische kennenzulernen. Viele berichten, dass sie in den ersten Monaten mehr Deutsch gelernt haben als in einem Jahr Sprachkurs.
        """.trimIndent(),
        questions = listOf(
            MCQuestion(
                id = "g2_l3_q1",
                text = "Warum entscheiden sich junge Menschen für eine WG?",
                options = listOf(
                    "a) Weil sie keine eigene Wohnung finden können",
                    "b) Weil es günstiger ist und soziale Vorteile bietet",
                    "c) Weil es gesetzlich vorgeschrieben ist"
                ),
                correctIndex = 1,
                explanation = "Der Text nennt zwei Hauptgründe: erschwinglicheres Wohnen und soziale Komponente."
            ),
            MCQuestion(
                id = "g2_l3_q2",
                text = "Worüber entstehen in WGs am häufigsten Konflikte?",
                options = listOf(
                    "a) Über Geld",
                    "b) Über Haustiere",
                    "c) Über ungleiche Hausarbeit"
                ),
                correctIndex = 2,
                explanation = "Der Text: 'Konflikte entstehen oft wegen ungleicher Hausarbeit.'"
            ),
            MCQuestion(
                id = "g2_l3_q3",
                text = "Was empfehlen Experten für ein harmonisches WG-Leben?",
                options = listOf(
                    "a) Einen Hausmeister engagieren",
                    "b) Gleich am Anfang klare Regeln aufstellen",
                    "c) Nur mit Freunden zusammenziehen"
                ),
                correctIndex = 1,
                explanation = "Experten empfehlen: 'gleich am Anfang klare Regeln aufstellen — zum Beispiel einen Putzplan.'"
            ),
            MCQuestion(
                id = "g2_l3_q4",
                text = "Was ist ein 'Casting' im WG-Kontext?",
                options = listOf(
                    "a) Ein Filmauftritt für WG-Bewohner",
                    "b) Ein Treffen, bei dem mehrere Interessenten gleichzeitig eingeladen werden",
                    "c) Ein Online-Interview"
                ),
                correctIndex = 1,
                explanation = "Der Text: '...sogenannte Castings, bei denen mehrere Interessenten gleichzeitig eingeladen werden.'"
            ),
            MCQuestion(
                id = "g2_l3_q5",
                text = "Welchen Vorteil hat eine WG für internationale Studenten besonders?",
                options = listOf(
                    "a) Sie müssen keine Miete zahlen",
                    "b) Sie können schnell Deutsch lernen und Einheimische kennenlernen",
                    "c) Sie bekommen ein Stipendium"
                ),
                correctIndex = 1,
                explanation = "Der Text: 'Für internationale Studenten ist eine WG oft der einfachste Weg, Deutsch zu üben und Einheimische kennenzulernen.'"
            )
        )
    )
)

val GoetheExam2HoerenParts = listOf(
    HoerenPart(
        title = "Hören Teil 1 – Kurze Gespräche",
        instruction = "Sie hören fünf kurze Gespräche. Entscheiden Sie nach jedem Gespräch: Richtig oder Falsch?",
        audioAssetPath = "audio/goethe2_hoeren1.mp3",
        transcript = """
Gespräch 1:
A: Entschuldigung, wann fährt der nächste Zug nach Hamburg?
B: Der nächste fährt um 14:35 Uhr von Gleis 7.
A: Und kommt er pünktlich an?
B: Laut Plan ja, aber es gibt heute leichte Verspätungen im Netz.

Gespräch 2:
A: Ich möchte einen Termin beim Zahnarzt machen.
B: Haben Sie Schmerzen?
A: Nein, es ist nur eine Routineuntersuchung.
B: Dann kann ich Ihnen nächste Woche Dienstag um 10 Uhr anbieten.

Gespräch 3:
A: Die neue Ausstellung im Museum ist wirklich beeindruckend.
B: Oh ja? Worüber geht es?
A: Um moderne deutsche Kunst der 1960er Jahre. Der Eintritt ist am Donnerstag kostenlos.
B: Das klingt interessant! Ich gehe gerne mit.

Gespräch 4:
A: Haben Sie das Formular ausgefüllt?
B: Ja, aber ich brauche noch eine Kopie meines Reisepasses.
A: Kein Problem. Wir können die Kopie hier machen.
B: Oh, das wäre super. Danke!

Gespräch 5:
A: Wie war dein Wochenende?
B: Ich war wandern im Schwarzwald. Vier Stunden zu Fuß!
A: Klingt anstrengend. Hat es sich gelohnt?
B: Absolut! Die Aussicht vom Gipfel war fantastisch.
            """.trimIndent(),
        questions = listOf(
            MCQuestion(id="g2_h1_q1", text="Der Zug nach Hamburg fährt von Gleis 7.", options=listOf("Richtig","Falsch"), correctIndex=0, explanation="Bestätigt: 'von Gleis 7'."),
            MCQuestion(id="g2_h1_q2", text="Der Patient hat Zahnschmerzen.", options=listOf("Richtig","Falsch"), correctIndex=1, explanation="Er sagt 'nur eine Routineuntersuchung' — keine Schmerzen."),
            MCQuestion(id="g2_h1_q3", text="Der Eintritt in die Ausstellung ist immer kostenlos.", options=listOf("Richtig","Falsch"), correctIndex=1, explanation="Nur am Donnerstag kostenlos, nicht immer."),
            MCQuestion(id="g2_h1_q4", text="Der Kunde braucht noch eine Reisepasskopie.", options=listOf("Richtig","Falsch"), correctIndex=0, explanation="Er sagt: 'ich brauche noch eine Kopie meines Reisepasses'."),
            MCQuestion(id="g2_h1_q5", text="Die Person hat das Wochenende zu Hause verbracht.", options=listOf("Richtig","Falsch"), correctIndex=1, explanation="Sie war wandern im Schwarzwald — nicht zu Hause.")
        )
    )
)

val GoetheExam2SchreibenTasks = listOf(
    SchreibenTask(
        title = "Schreiben Teil 1 – Forumsbeitrag",
        instruction = "Sie haben im Internet folgenden Forumsbeitrag gelesen:\n\n'Ich finde, Jugendliche sollten kein Smartphone haben, bevor sie 16 Jahre alt sind. Smartphones machen süchtig und schaden der schulischen Leistung. Was denken Sie?'\n\nSchreiben Sie einen Kommentar (mindestens 80 Wörter). Äußern Sie Ihre Meinung und begründen Sie diese. Gehen Sie auf den Beitrag ein.",
        wordCount = "80–120 Wörter",
        keyPoints = listOf(
            "Ihre Meinung zum Thema klar äußern",
            "Mindestens zwei Argumente nennen",
            "Auf den ursprünglichen Beitrag eingehen",
            "Einen Gegenvorschlag oder Kompromiss anbieten"
        )
    ),
    SchreibenTask(
        title = "Schreiben Teil 2 – Formeller Brief",
        instruction = "Sie haben vor zwei Wochen einen Laptop in einem Online-Shop bestellt und bezahlt. Das Gerät ist noch nicht angekommen und der Kundenservice antwortet nicht auf Ihre E-Mails.\n\nSchreiben Sie einen formellen Brief/eine E-Mail (mindestens 80 Wörter).\n\nSchreiben Sie zu folgenden Punkten:\n• Bestelldatum und Bestellnummer\n• Beschreiben Sie das Problem\n• Fordern Sie eine Lösung\n• Setzen Sie eine Frist",
        wordCount = "80–120 Wörter",
        keyPoints = listOf(
            "Formelle Anrede (Sehr geehrte Damen und Herren)",
            "Bestelldatum und Bestellnummer angeben",
            "Problem klar beschreiben",
            "Konkrete Lösung fordern (Lieferung oder Erstattung)",
            "Frist setzen (z.B. innerhalb von 7 Tagen)",
            "Formeller Abschluss (Mit freundlichen Grüßen)"
        )
    )
)

val GoetheExam2SprechenTasks = listOf(
    SprechenTask(
        title = "Sprechen Teil 1 – Partnerinterview",
        instruction = "Stellen Sie Ihrem Prüfungspartner Fragen und antworten Sie auf seine/ihre Fragen.",
        topics = listOf(
            "Fragen Sie nach dem Lieblingsessen Ihres Partners und warum.",
            "Fragen Sie, wie Ihr Partner normalerweise seinen Urlaub verbringt.",
            "Fragen Sie, was Ihr Partner in seiner Freizeit macht.",
            "Fragen Sie, was das Lieblingsfach Ihres Partners in der Schule war."
        )
    ),
    SprechenTask(
        title = "Sprechen Teil 2 – Präsentation",
        instruction = "Halten Sie eine kurze Präsentation (ca. 1–2 Minuten) zu einem der folgenden Themen. Nennen Sie: Was ist das Thema? Was sind Vor- und Nachteile? Was ist Ihre Meinung?",
        topics = listOf(
            "Soziale Medien — Fluch oder Segen?",
            "Vegetarisch oder vegan leben — ist das die Zukunft?",
            "Ehrenamt — Sollte jeder Jugendliche ein Sozialjahr machen?",
            "Fernseher vs. Streaming — Was bevorzugen Sie und warum?"
        )
    ),
    SprechenTask(
        title = "Sprechen Teil 3 – Gemeinsam planen",
        instruction = "Ihre Klasse möchte ein Abschlussessen organisieren. Einigen Sie sich mit Ihrem Partner auf:\n• Wo: Restaurant, zu Hause, Picknick im Park?\n• Wann: Abend unter der Woche oder am Wochenende?\n• Was mitbringen: Jeder bringt etwas mit oder alles wird bestellt?\n\nBegründen Sie Ihre Vorschläge und reagieren Sie auf die Ideen Ihres Partners.",
        topics = listOf(
            "Lokal: Restaurant vs. private Wohnung vs. Park",
            "Timing: Wochentag vs. Wochenende",
            "Essen: Selbst kochen vs. liefern lassen vs. Restaurant"
        )
    )
)
```

---

### ExamData.kt — TELC Modelltest 1 Content

```kotlin
// ─── TELC MODELLTEST 1 ─────────────────────────────────────────────────

val TelcExam1LesenParts = listOf(
    LesenPart(
        title = "Lesen Teil 1 – Texte und Überschriften",
        instruction = "Lesen Sie die fünf Texte und wählen Sie für jeden Text die passende Überschrift (a–h). Drei Überschriften bleiben übrig.",
        questions = listOf(
            MCQuestion(
                id = "t1_l1_q1",
                text = "Text 1: 'In der Schweiz ist Deutsch eine von vier offiziellen Sprachen. Neben Deutsch gibt es auch Französisch, Italienisch und Rätoromanisch. Viele Schweizer sprechen mindestens zwei dieser Sprachen.'",
                options = listOf(
                    "a) Sprachvielfalt in einem kleinen Land",
                    "b) Deutsch ist weltweit verbreitet",
                    "c) Fremdsprachen lernen ist wichtig",
                    "d) Europa hat viele Kulturen"
                ),
                correctIndex = 0,
                explanation = "Der Text beschreibt 4 Sprachen in der kleinen Schweiz = 'Sprachvielfalt in einem kleinen Land'."
            ),
            MCQuestion(
                id = "t1_l1_q2",
                text = "Text 2: 'Das duale Ausbildungssystem in Deutschland gilt als eines der besten der Welt. Auszubildende lernen gleichzeitig in Betrieben und Berufsschulen. Dieses Modell wird weltweit studiert und kopiert.'",
                options = listOf(
                    "a) Deutsche Universitäten sind beliebt",
                    "b) Berufsausbildung in Deutschland als Vorbild",
                    "c) Schüler müssen früh entscheiden",
                    "d) Unternehmen suchen Fachkräfte"
                ),
                correctIndex = 1,
                explanation = "Das duale System als weltweites Vorbild = 'Berufsausbildung in Deutschland als Vorbild'."
            ),
            MCQuestion(
                id = "t1_l1_q3",
                text = "Text 3: 'Der Karneval in Köln ist einer der größten in Europa. Jedes Jahr nehmen Millionen von Menschen an den Umzügen teil. Die Feierlichkeiten beginnen offiziell am 11. November.'",
                options = listOf(
                    "a) Deutschlands größtes Sportfest",
                    "b) Köln ist eine touristische Stadt",
                    "c) Ein weltbekanntes deutsches Fest",
                    "d) Winter in Deutschland ist bunt"
                ),
                correctIndex = 2,
                explanation = "Kölner Karneval, Millionen Teilnehmer = 'Ein weltbekanntes deutsches Fest'."
            ),
            MCQuestion(
                id = "t1_l1_q4",
                text = "Text 4: 'Immer mehr Deutsche kaufen gebrauchte Kleidung in Second-Hand-Läden oder online. Das schont die Umwelt, weil weniger neue Kleidung produziert werden muss, und spart gleichzeitig Geld.'",
                options = listOf(
                    "a) Mode ist teuer geworden",
                    "b) Nachhaltige Mode: Altes neu tragen",
                    "c) Online-Shopping wächst",
                    "d) Kleidung wird schneller produziert"
                ),
                correctIndex = 1,
                explanation = "Gebrauchte Kleidung = Umwelt schonen + sparen = 'Nachhaltige Mode: Altes neu tragen'."
            ),
            MCQuestion(
                id = "t1_l1_q5",
                text = "Text 5: 'Viele Senioren in Deutschland engagieren sich ehrenamtlich. Sie helfen in Bibliotheken, begleiten Kinder auf Schulwegen oder betreuen Geflüchtete. Dieser Einsatz ist für die Gesellschaft unverzichtbar.'",
                options = listOf(
                    "a) Alte Menschen brauchen Hilfe",
                    "b) Ehrenamtliches Engagement im Alter",
                    "c) Bibliotheken brauchen mehr Personal",
                    "d) Senioren und Technologie"
                ),
                correctIndex = 1,
                explanation = "Senioren helfen ehrenamtlich in verschiedenen Bereichen = 'Ehrenamtliches Engagement im Alter'."
            )
        )
    ),
    LesenPart(
        title = "Lesen Teil 2 – Richtig oder Falsch?",
        instruction = "Lesen Sie den Text und entscheiden Sie: Sind die Aussagen richtig (R) oder falsch (F)?",
        contextText = """
Fernweh und Reisen: Wie Deutschen ihr Urlaub wichtig ist

Deutschland ist eine reisefreudige Nation. Laut Statistiken verreisen mehr als 70% der Deutschen mindestens einmal im Jahr. Die beliebtesten Urlaubsziele sind seit Jahren Spanien, Italien und die Türkei — warme Länder, die einen starken Kontrast zum deutschen Wetter bieten.

Trotzdem reisen immer mehr Deutsche auch innerhalb Deutschlands. Die Inseln Rügen und Sylt, die Bayrischen Alpen und die Küstenregion Mecklenburg-Vorpommern sind bei Deutschen besonders beliebt. "Urlaub im eigenen Land" wird als Trend beobachtet, auch aus Umweltbewusstsein.

Wichtig für Deutsche beim Urlaub: Sauberkeit, gutes Essen und zuverlässige Organisation. Deutsche Touristen gelten international als gut organisiert und pünktlich. Sie planen ihre Reisen oft Monate im Voraus und buchen Unterkünfte frühzeitig.

Für die Deutschen ist Urlaub nicht nur Erholung — er ist ein Grundrecht. Das Bundesurlaubsgesetz garantiert jedem Vollzeitbeschäftigten mindestens 24 Werktage bezahlten Urlaub pro Jahr. In der Realität haben die meisten Arbeitnehmer 25–30 Urlaubstage.
        """.trimIndent(),
        questions = listOf(
            MCQuestion(id="t1_l2_q1", text="Mehr als die Hälfte der Deutschen reist mindestens einmal pro Jahr.", options=listOf("Richtig","Falsch"), correctIndex=0, explanation="'Mehr als 70%' ist mehr als die Hälfte. Richtig."),
            MCQuestion(id="t1_l2_q2", text="Das beliebteste Urlaubsland der Deutschen ist Frankreich.", options=listOf("Richtig","Falsch"), correctIndex=1, explanation="Die beliebtesten Länder sind Spanien, Italien und Türkei — nicht Frankreich. Falsch."),
            MCQuestion(id="t1_l2_q3", text="Urlaub in Deutschland wird als Trend beobachtet.", options=listOf("Richtig","Falsch"), correctIndex=0, explanation="Der Text bestätigt: '"Urlaub im eigenen Land" wird als Trend beobachtet'. Richtig."),
            MCQuestion(id="t1_l2_q4", text="Deutsche Touristen buchen Reisen meistens spontan.", options=listOf("Richtig","Falsch"), correctIndex=1, explanation="Deutsche planen 'oft Monate im Voraus' — nicht spontan. Falsch."),
            MCQuestion(id="t1_l2_q5", text="Das deutsche Gesetz garantiert mindestens 24 Werktage bezahlten Urlaub.", options=listOf("Richtig","Falsch"), correctIndex=0, explanation="Direkt im Text: 'Das Bundesurlaubsgesetz garantiert... mindestens 24 Werktage'. Richtig.")
        )
    )
)

val TelcExam1SchreibenTasks = listOf(
    SchreibenTask(
        title = "Schreiben – Formeller Brief",
        instruction = "Sie haben letzte Woche an einem Deutschkurs in einer Sprachschule teilgenommen. Sie sind mit dem Kurs unzufrieden.\n\nSchreiben Sie einen Brief an die Sprachschule (mindestens 100 Wörter) und gehen Sie auf folgende Punkte ein:\n• Kursname und Datum\n• Was hat Ihnen nicht gefallen (mindestens 2 Punkte)\n• Was möchten Sie als Lösung?\n• Was passiert, wenn die Schule nicht reagiert?",
        wordCount = "100–130 Wörter",
        keyPoints = listOf(
            "Formelle Anrede",
            "Kursname und Datum nennen",
            "Mindestens 2 konkrete Kritikpunkte",
            "Klare Forderung (Rückerstattung, Wiederholfungskurs, etc.)",
            "Konsequenz bei Nichtreagieren",
            "Formeller Schluss"
        )
    )
)

val TelcExam1SprechenTasks = listOf(
    SprechenTask(
        title = "Sprechen Teil 1 – Präsentation",
        instruction = "Bereiten Sie eine kurze Präsentation (1–2 Minuten) zu einem Thema vor. Ihre Präsentation soll enthalten:\n• Was ist das Thema?\n• Was sind Vorteile?\n• Was sind Nachteile?\n• Ihre persönliche Meinung",
        topics = listOf(
            "Homeoffice: Vor- und Nachteile",
            "Vegetarismus: Ist es gesund und nachhaltig?",
            "Tourismus: Gut oder schlecht für die Umwelt?",
            "Social Media: Verbindet oder isoliert es die Menschen?"
        )
    ),
    SprechenTask(
        title = "Sprechen Teil 2 – Diskussion",
        instruction = "Diskutieren Sie mit Ihrem Partner über eine Situation. Einigen Sie sich auf eine gemeinsame Lösung.",
        topics = listOf(
            "Sie möchten gemeinsam ein Geschenk für Ihren Deutschlehrer kaufen. Einigen Sie sich auf: Was kaufen? Wie viel Geld ausgeben? Wer kauft es?",
            "Ihre Gruppe plant einen Ausflug. Einigen Sie sich auf: Wohin fahren? Wie hinfahren? Was mitnehmen?"
        )
    )
)
```

---

## 🃏 PHASE 0-C — FLASHCARD CONTENT (10 Decks × 30 Cards)

### Data structure:
```kotlin
data class Flashcard(
    val id: String,
    val german: String,
    val article: String?,          // "der/die/das" for nouns, null for verbs/adj
    val plural: String?,           // plural form for nouns
    val english: String,
    val exampleSentence: String,
    val topic: String
)
```

Store as `assets/flashcards/decks.json`. Full content below:

---

### DECK 1: Arbeit & Beruf (Work & Career)

```json
{"deckId":"arbeit","deckName":"Arbeit & Beruf","icon":"💼","cardCount":30,"cards":[
  {"id":"a1","german":"die Bewerbung","article":"die","plural":"Bewerbungen","english":"job application","exampleSentence":"Ich habe eine Bewerbung für die Stelle als Buchhalter geschickt."},
  {"id":"a2","german":"das Vorstellungsgespräch","article":"das","plural":"Vorstellungsgespräche","english":"job interview","exampleSentence":"Morgen habe ich ein Vorstellungsgespräch bei einer großen Firma."},
  {"id":"a3","german":"der Lebenslauf","article":"der","plural":"Lebensläufe","english":"curriculum vitae / CV","exampleSentence":"Mein Lebenslauf muss aktualisiert werden."},
  {"id":"a4","german":"das Gehalt","article":"das","plural":"Gehälter","english":"salary","exampleSentence":"Mein Gehalt wurde nach einem Jahr erhöht."},
  {"id":"a5","german":"die Stelle","article":"die","plural":"Stellen","english":"job position","exampleSentence":"Es gibt viele offene Stellen in der IT-Branche."},
  {"id":"a6","german":"der Arbeitgeber","article":"der","plural":"Arbeitgeber","english":"employer","exampleSentence":"Mein Arbeitgeber ist sehr verständnisvoll."},
  {"id":"a7","german":"der Arbeitnehmer","article":"der","plural":"Arbeitnehmer","english":"employee","exampleSentence":"Arbeitnehmer haben in Deutschland viele Rechte."},
  {"id":"a8","german":"kündigen","article":null,"plural":null,"english":"to quit / to give notice","exampleSentence":"Er hat nach zehn Jahren seinen Job gekündigt."},
  {"id":"a9","german":"die Überstunden","article":"die","plural":"(plural only)","english":"overtime","exampleSentence":"Ich mache oft Überstunden, weil wir viel Arbeit haben."},
  {"id":"a10","german":"der Urlaub","article":"der","plural":"Urlaube","english":"vacation / holiday","exampleSentence":"Ich nehme nächste Woche Urlaub."},
  {"id":"a11","german":"die Ausbildung","article":"die","plural":"Ausbildungen","english":"vocational training / apprenticeship","exampleSentence":"Er macht eine Ausbildung zum Koch."},
  {"id":"a12","german":"die Fortbildung","article":"die","plural":"Fortbildungen","english":"further training / professional development","exampleSentence":"Meine Firma bezahlt meine Fortbildung."},
  {"id":"a13","german":"das Praktikum","article":"das","plural":"Praktika","english":"internship","exampleSentence":"Ich mache ein Praktikum in einer Marketingagentur."},
  {"id":"a14","german":"der Kollege","article":"der","plural":"Kollegen","english":"colleague","exampleSentence":"Meine Kollegen sind sehr hilfsbereit."},
  {"id":"a15","german":"der Chef","article":"der","plural":"Chefs","english":"boss / manager","exampleSentence":"Mein Chef ist oft im Ausland."},
  {"id":"a16","german":"befördern","article":null,"plural":null,"english":"to promote","exampleSentence":"Sie wurde zur Abteilungsleiterin befördert."},
  {"id":"a17","german":"die Kündigung","article":"die","plural":"Kündigungen","english":"termination / notice","exampleSentence":"Er hat eine Kündigung von seiner Firma erhalten."},
  {"id":"a18","german":"das Büro","article":"das","plural":"Büros","english":"office","exampleSentence":"Ich arbeite drei Tage im Büro und zwei Tage zu Hause."},
  {"id":"a19","german":"die Besprechung","article":"die","plural":"Besprechungen","english":"meeting","exampleSentence":"Wir haben jeden Montag eine Besprechung."},
  {"id":"a20","german":"der Arbeitsvertrag","article":"der","plural":"Arbeitsverträge","english":"employment contract","exampleSentence":"Ich habe meinen Arbeitsvertrag gestern unterschrieben."},
  {"id":"a21","german":"die Fachkraft","article":"die","plural":"Fachkräfte","english":"skilled worker / specialist","exampleSentence":"Deutschland sucht dringend Fachkräfte aus dem Ausland."},
  {"id":"a22","german":"selbstständig","article":null,"plural":null,"english":"self-employed / independent","exampleSentence":"Sie ist seit fünf Jahren selbstständig als Grafikdesignerin."},
  {"id":"a23","german":"das Homeoffice","article":"das","plural":null,"english":"working from home / home office","exampleSentence":"Seit der Pandemie arbeite ich oft im Homeoffice."},
  {"id":"a24","german":"die Probezeit","article":"die","plural":"Probezeiten","english":"probationary period","exampleSentence":"In der Probezeit arbeitet man besonders hart."},
  {"id":"a25","german":"der Betrieb","article":"der","plural":"Betriebe","english":"company / business / operation","exampleSentence":"Der Betrieb hat über 500 Mitarbeiter."},
  {"id":"a26","german":"die Rente","article":"die","plural":"Renten","english":"pension / retirement","exampleSentence":"Mein Vater geht nächstes Jahr in Rente."},
  {"id":"a27","german":"verdienen","article":null,"plural":null,"english":"to earn","exampleSentence":"Als Ingenieur verdient man gut."},
  {"id":"a28","german":"die Abteilung","article":"die","plural":"Abteilungen","english":"department","exampleSentence":"Ich arbeite in der Marketingabteilung."},
  {"id":"a29","german":"der Feierabend","article":"der","plural":"Feierabende","english":"end of work day / after work","exampleSentence":"Um 17 Uhr ist bei uns Feierabend."},
  {"id":"a30","german":"der Bewerber","article":"der","plural":"Bewerber","english":"applicant","exampleSentence":"Es gab über 100 Bewerber für die Stelle."}
]}
```

---

### DECK 2: Gesundheit & Körper (Health & Body)

```json
{"deckId":"gesundheit","deckName":"Gesundheit & Körper","icon":"🏥","cardCount":30,"cards":[
  {"id":"g1","german":"die Krankenkasse","article":"die","plural":"Krankenkassen","english":"health insurance fund","exampleSentence":"Ich bin bei der AOK als Krankenkasse versichert."},
  {"id":"g2","german":"der Arzt","article":"der","plural":"Ärzte","english":"doctor","exampleSentence":"Ich muss morgen zum Arzt gehen."},
  {"id":"g3","german":"das Rezept","article":"das","plural":"Rezepte","english":"prescription","exampleSentence":"Der Arzt hat mir ein Rezept für Tabletten gegeben."},
  {"id":"g4","german":"die Apotheke","article":"die","plural":"Apotheken","english":"pharmacy","exampleSentence":"Die Apotheke ist bis 20 Uhr geöffnet."},
  {"id":"g5","german":"die Krankheit","article":"die","plural":"Krankheiten","english":"illness / disease","exampleSentence":"Erkältung ist eine häufige Krankheit im Winter."},
  {"id":"g6","german":"die Allergie","article":"die","plural":"Allergien","english":"allergy","exampleSentence":"Ich habe eine Allergie gegen Nüsse."},
  {"id":"g7","german":"die Untersuchung","article":"die","plural":"Untersuchungen","english":"examination / check-up","exampleSentence":"Die jährliche Untersuchung beim Hausarzt ist wichtig."},
  {"id":"g8","german":"der Termin","article":"der","plural":"Termine","english":"appointment","exampleSentence":"Ich habe einen Termin beim Zahnarzt nächste Woche."},
  {"id":"g9","german":"die Tablette","article":"die","plural":"Tabletten","english":"tablet / pill","exampleSentence":"Nehmen Sie drei Tabletten täglich nach dem Essen."},
  {"id":"g10","german":"das Krankenhaus","article":"das","plural":"Krankenhäuser","english":"hospital","exampleSentence":"Nach dem Unfall wurde er ins Krankenhaus gebracht."},
  {"id":"g11","german":"sich erholen","article":null,"plural":null,"english":"to recover / to rest","exampleSentence":"Nach der Operation muss man sich gut erholen."},
  {"id":"g12","german":"der Schmerz","article":"der","plural":"Schmerzen","english":"pain","exampleSentence":"Ich habe starke Schmerzen im Rücken."},
  {"id":"g13","german":"die Grippe","article":"die","plural":"Grippen","english":"flu / influenza","exampleSentence":"Letzte Woche hatte ich die Grippe und musste im Bett bleiben."},
  {"id":"g14","german":"der Blutdruck","article":"der","plural":null,"english":"blood pressure","exampleSentence":"Mein Blutdruck ist zu hoch."},
  {"id":"g15","german":"die Impfung","article":"die","plural":"Impfungen","english":"vaccination","exampleSentence":"Jedes Jahr lasse ich mich gegen Grippe impfen."},
  {"id":"g16","german":"gesund","article":null,"plural":null,"english":"healthy","exampleSentence":"Obst und Gemüse sind gesund."},
  {"id":"g17","german":"krank","article":null,"plural":null,"english":"sick / ill","exampleSentence":"Ich bin krank und kann heute nicht arbeiten."},
  {"id":"g18","german":"die Notaufnahme","article":"die","plural":"Notaufnahmen","english":"emergency room","exampleSentence":"Bei einem Herzinfarkt geht man in die Notaufnahme."},
  {"id":"g19","german":"der Hausarzt","article":"der","plural":"Hausärzte","english":"general practitioner / family doctor","exampleSentence":"Meinen Hausarzt kenne ich seit zehn Jahren."},
  {"id":"g20","german":"die Operation","article":"die","plural":"Operationen","english":"surgery / operation","exampleSentence":"Die Operation war erfolgreich."},
  {"id":"g21","german":"der Verband","article":"der","plural":"Verbände","english":"bandage / dressing","exampleSentence":"Der Arzt hat einen Verband um mein Handgelenk gewickelt."},
  {"id":"g22","german":"das Fieber","article":"das","plural":null,"english":"fever / temperature","exampleSentence":"Das Kind hat 39 Grad Fieber."},
  {"id":"g23","german":"die Ernährung","article":"die","plural":"Ernährungen","english":"diet / nutrition","exampleSentence":"Eine ausgewogene Ernährung ist wichtig für die Gesundheit."},
  {"id":"g24","german":"der Stress","article":"der","plural":null,"english":"stress","exampleSentence":"Zu viel Stress kann krank machen."},
  {"id":"g25","german":"sich bewegen","article":null,"plural":null,"english":"to exercise / to move","exampleSentence":"Man soll sich täglich mindestens 30 Minuten bewegen."},
  {"id":"g26","german":"die Physiotherapie","article":"die","plural":"Physiotherapien","english":"physiotherapy","exampleSentence":"Nach dem Unfall brauche ich Physiotherapie."},
  {"id":"g27","german":"der Krankenschein","article":"der","plural":"Krankenscheine","english":"sick note / doctor's certificate","exampleSentence":"Ich brauche einen Krankenschein für meinen Arbeitgeber."},
  {"id":"g28","german":"überweisen","article":null,"plural":null,"english":"to refer (to a specialist)","exampleSentence":"Mein Hausarzt hat mich an einen Spezialisten überwiesen."},
  {"id":"g29","german":"der Sanitäter","article":"der","plural":"Sanitäter","english":"paramedic / first aider","exampleSentence":"Der Sanitäter hat ihm schnell geholfen."},
  {"id":"g30","german":"die Krankschreibung","article":"die","plural":"Krankschreibungen","english":"sick leave certificate","exampleSentence":"Mit einer Krankschreibung kann man legal zu Hause bleiben."}
]}
```

---

### DECK 3–10: Summary (full JSON in assets/flashcards/decks.json)

| Deck | Topic | Icon | Key Words (sample) |
|---|---|---|---|
| 3 | Reisen & Verkehr | ✈️ | Reisepass, Gepäck, Verspätung, Bahnsteig, Zollkontrolle, buchen, umsteigen |
| 4 | Wohnen & Haushalt | 🏠 | Miete, Kaution, Wohnungsanzeige, Kündigung, Nebenkosten, Strom, renovieren |
| 5 | Bildung & Schule | 📚 | Abitur, Studiengebühren, Seminar, Prüfung, Note, bestehen, durchfallen |
| 6 | Freizeit & Kultur | 🎭 | Ausstellung, Eintrittskarte, Veranstaltung, Vereinsmitglied, Hobby, Bühne |
| 7 | Natur & Umwelt | 🌍 | Umweltschutz, Klimawandel, Recycling, erneuerbar, Fußabdruck, nachhaltig |
| 8 | Kommunikation | 📱 | herunterladen, Datenschutz, Passwort, Benutzername, Verbindung, streamen |
| 9 | Familie & Soziales | 👨‍👩‍👧 | Erziehung, Geschwister, Verwandte, Ehe, Scheidung, Kindergeld, Sozialamt |
| 10 | Öffentliches Leben | 🏛️ | Bürgeramt, Aufenthaltserlaubnis, Antrag, Formular, Ausländerbehörde, Steuern |

---

## 📖 PHASE 0-D — GESCHICHTEN CONTENT (10 Stories)

Store as `assets/geschichten/stories.json`

### Story schema:
```json
{
  "id": "s1",
  "title": "Der erste Tag",
  "level": "A2",
  "readingTimeMinutes": 3,
  "topic": "Alltag",
  "body": "...",
  "vocabHints": [{"word":"...", "definition":"..."}],
  "questions": [{"question":"...", "options":[...], "correctIndex":0, "explanation":"..."}]
}
```

---

### Story 1 (A2): "Der erste Tag in Deutschland"

```json
{
  "id":"s1","title":"Der erste Tag in Deutschland","level":"A2","readingTimeMinutes":3,"topic":"Ankommen",
  "body":"Amira kommt aus Marokko. Sie ist 24 Jahre alt und studiert Informatik. Heute ist ihr erster Tag in Deutschland. Sie wohnt jetzt in München.\n\nAm Morgen steht Amira früh auf. Sie packt ihre Tasche und geht aus dem Haus. Die Stadt ist groß und laut. Überall fahren Autos und U-Bahnen. Amira schaut auf ihr Handy. Sie sucht den Weg zur Universität.\n\nAn der U-Bahn-Station kauft Amira ein Ticket. Sie fragt einen Mann: 'Entschuldigung, welche Linie fährt zur Universität?' Der Mann lächelt. 'Die U3', sagt er. 'In zehn Minuten.'\n\nAn der Universität trifft Amira viele andere Studenten. Einige kommen auch aus anderen Ländern. Sie sitzen zusammen in der Mensa und essen Mittagessen. Das Essen ist nicht wie zu Hause, aber es schmeckt gut.\n\nAm Abend schreibt Amira ihrer Mutter eine Nachricht: 'Mama, alles gut. Deutschland ist kalt, aber die Menschen sind freundlich. Ich glaube, ich werde mich hier wohl fühlen.'\n\nIhre Mutter antwortet schnell: 'Das freut mich. Bleib gesund und lern viel!'",
  "vocabHints":[
    {"word":"packen","definition":"to pack (a bag)"},
    {"word":"laut","definition":"loud / noisy"},
    {"word":"lächeln","definition":"to smile"},
    {"word":"die Mensa","definition":"university cafeteria"},
    {"word":"sich wohl fühlen","definition":"to feel comfortable / at home"}
  ],
  "questions":[
    {"question":"Woher kommt Amira?","options":["Aus der Türkei","Aus Marokko","Aus Ägypten","Aus Syrien"],"correctIndex":1,"explanation":"Der Text: 'Amira kommt aus Marokko.'"},
    {"question":"Wie fährt Amira zur Universität?","options":["Mit dem Bus","Mit dem Fahrrad","Mit der U-Bahn","Zu Fuß"],"correctIndex":2,"explanation":"Sie nimmt die U3."},
    {"question":"Wie findet Amira das Essen in der Mensa?","options":["Schlecht","Wie zu Hause","Gut, obwohl anders als zu Hause","Zu teuer"],"correctIndex":2,"explanation":"'Das Essen ist nicht wie zu Hause, aber es schmeckt gut.'"},
    {"question":"Was schreibt Amira ihrer Mutter?","options":["Dass sie Deutschland nicht mag","Dass die Menschen freundlich sind","Dass das Essen schlecht ist","Dass sie zurückkommen möchte"],"correctIndex":1,"explanation":"'die Menschen sind freundlich'"}
  ]
}
```

---

### Story 2 (A2): "Das Vorstellungsgespräch"

```json
{
  "id":"s2","title":"Das Vorstellungsgespräch","level":"A2","readingTimeMinutes":3,"topic":"Arbeit",
  "body":"Klaus ist 28 Jahre alt und sucht seit drei Monaten Arbeit. Er hat Informatik studiert und möchte als Programmierer arbeiten. Heute hat er ein Vorstellungsgespräch bei einer IT-Firma in Hamburg.\n\nKlaus steht um sechs Uhr auf. Er duscht, frühstückt und zieht seinen besten Anzug an. Er ist sehr nervös. Er wiederholt die möglichen Fragen: 'Warum möchten Sie für uns arbeiten?' 'Was sind Ihre Stärken?'\n\nIm Büro wartet er in einem hellen Raum. Es gibt Kaffee und Wasser. Eine freundliche Sekretärin sagt: 'Die Chefin kommt gleich.'\n\nDas Gespräch dauert eine Stunde. Die Chefin stellt viele Fragen über seine Erfahrungen und Projekte. Klaus antwortet ruhig und ehrlich. Am Ende fragt die Chefin: 'Wann können Sie anfangen?' Klaus lächelt. Das ist ein gutes Zeichen.\n\nDrei Tage später kommt eine E-Mail: 'Herzlichen Glückwunsch! Wir freuen uns, Ihnen die Stelle anzubieten.' Klaus ruft sofort seine Mutter an. 'Mama! Ich habe den Job!'",
  "vocabHints":[
    {"word":"das Vorstellungsgespräch","definition":"job interview"},
    {"word":"nervös","definition":"nervous"},
    {"word":"die Stärke","definition":"strength (here: skill/quality)"},
    {"word":"ruhig","definition":"calm / quietly"},
    {"word":"ehrlich","definition":"honest"}
  ],
  "questions":[
    {"question":"Wie lange sucht Klaus schon Arbeit?","options":["Seit einer Woche","Seit einem Monat","Seit drei Monaten","Seit einem Jahr"],"correctIndex":2,"explanation":"'seit drei Monaten'"},
    {"question":"Um wie viel Uhr steht Klaus auf?","options":["Um 5 Uhr","Um 6 Uhr","Um 7 Uhr","Um 8 Uhr"],"correctIndex":1,"explanation":"'Klaus steht um sechs Uhr auf.'"},
    {"question":"Was ist ein gutes Zeichen im Gespräch?","options":["Die Chefin lächelt","Die Chefin fragt wann er anfangen kann","Er bekommt Kaffee","Die Sekretärin ist freundlich"],"correctIndex":1,"explanation":"'Wann können Sie anfangen?' zeigt Interesse."},
    {"question":"Wie erfährt Klaus, dass er den Job bekommt?","options":["Durch einen Anruf","Durch einen Brief","Durch eine E-Mail","Durch die Sekretärin"],"correctIndex":2,"explanation":"'kommt eine E-Mail: Herzlichen Glückwunsch!'"}
  ]
}
```

---

### Story 3 (B1): "Zwischen zwei Welten"

```json
{
  "id":"s3","title":"Zwischen zwei Welten","level":"B1","readingTimeMinutes":5,"topic":"Integration",
  "body":"Yusuf lebt seit fünf Jahren in Deutschland. Er kam aus der Türkei, als er 22 Jahre alt war — mit einem Koffer, 200 Euro und dem festen Entschluss, sein Leben zu verändern.\n\nDie ersten Monate waren schwierig. Deutsch zu lernen war eine riesige Herausforderung. Yusuf besuchte täglich den Sprachkurs und las abends Kinderbücher, um seinen Wortschatz zu erweitern. Er schämte sich manchmal, wenn er Fehler machte — aber sein Lehrer sagte: 'Fehler machen ist der einzige Weg, besser zu werden.'\n\nNach zwei Jahren fand er eine Stelle in einem Restaurant als Kellner. Die Arbeit war hart, aber er sparte Geld und begann einen Abendkurs in Betriebswirtschaft. Heute arbeitet er als Assistent in einem kleinen Unternehmen und träumt davon, eines Tages sein eigenes Café zu eröffnen.\n\nDoch das Leben in zwei Kulturen ist manchmal kompliziert. Wenn er in die Türkei fährt, fühlt er sich zu deutsch. Wenn er in Deutschland ist, fühlt er sich manchmal zu türkisch. 'Ich bin beides', sagt er mit einem Lächeln. 'Und das ist eigentlich eine Stärke, kein Problem.'\n\nSeine Geschichte ist nicht einzigartig. Millionen von Menschen leben in Deutschland mit mehreren kulturellen Identitäten. Sie bereichern das Land mit ihren Sprachen, Traditionen und Perspektiven.",
  "vocabHints":[
    {"word":"der Entschluss","definition":"decision / resolution"},
    {"word":"sich schämen","definition":"to be ashamed / embarrassed"},
    {"word":"die Betriebswirtschaft","definition":"business administration"},
    {"word":"einzigartig","definition":"unique"},
    {"word":"bereichern","definition":"to enrich"}
  ],
  "questions":[
    {"question":"Wann kam Yusuf nach Deutschland?","options":["Vor zwei Jahren","Vor fünf Jahren","Vor zehn Jahren","Vor einem Jahr"],"correctIndex":1,"explanation":"'seit fünf Jahren in Deutschland'"},
    {"question":"Was machte Yusuf abends, um Deutsch zu lernen?","options":["Er schaute Fernsehen","Er las Kinderbücher","Er sprach mit Nachbarn","Er hörte Radio"],"correctIndex":1,"explanation":"'las abends Kinderbücher, um seinen Wortschatz zu erweitern'"},
    {"question":"Was ist Yusufs Traum für die Zukunft?","options":["Zurück in die Türkei gehen","Lehrer werden","Ein eigenes Café eröffnen","Eine große Firma gründen"],"correctIndex":2,"explanation":"'träumt davon, eines Tages sein eigenes Café zu eröffnen'"},
    {"question":"Wie sieht Yusuf seine zwei kulturellen Identitäten?","options":["Als Problem","Als Schwäche","Als Stärke","Als Belastung"],"correctIndex":2,"explanation":"'Ich bin beides... das ist eigentlich eine Stärke, kein Problem.'"}
  ]
}
```

---

### Stories 4–10: Titles + Topics (full content in assets/geschichten/stories.json)

| ID | Title | Level | Topic | Word Count |
|---|---|---|---|---|
| s4 | "Die WG-Suche" | A2 | Wohnen | ~250 |
| s5 | "Ein Missverständnis in der Apotheke" | A2 | Gesundheit | ~220 |
| s6 | "Die Umwelt-AG" | B1 | Umwelt/Schule | ~350 |
| s7 | "Fernweh" | B1 | Reisen/Träume | ~380 |
| s8 | "Der Nachbar" | B1 | Alltag/Konflikt | ~400 |
| s9 | "Prüfungsangst" | B1 | Bildung/Stress | ~350 |
| s10 | "Das Café am Fluss" | B1 | Arbeit/Traum | ~420 |

---

## 🎮 PHASE 0-E — SPIELEN CONTENT (3 Game Types)

### Game 1: Wortpaar-Match (Word Pair Matching)

Store as `assets/spiele/wortpaare.json`

```json
{"gameId":"wortmatch","title":"Wortpaar-Match","description":"Verbinde deutsche Wörter mit ihren englischen Bedeutungen","sets":[
  {"setId":"wm1","title":"Arbeit & Alltag","pairs":[
    {"german":"die Bewerbung","english":"job application"},
    {"german":"das Gehalt","english":"salary"},
    {"german":"die Überstunden","english":"overtime"},
    {"german":"der Urlaub","english":"vacation"},
    {"german":"die Ausbildung","english":"apprenticeship"},
    {"german":"kündigen","english":"to quit"},
    {"german":"das Praktikum","english":"internship"},
    {"german":"die Rente","english":"pension"},
    {"german":"der Feierabend","english":"end of workday"},
    {"german":"befördern","english":"to promote"}
  ]},
  {"setId":"wm2","title":"Gesundheit","pairs":[
    {"german":"die Krankenkasse","english":"health insurance"},
    {"german":"das Rezept","english":"prescription"},
    {"german":"die Apotheke","english":"pharmacy"},
    {"german":"die Impfung","english":"vaccination"},
    {"german":"der Blutdruck","english":"blood pressure"},
    {"german":"das Fieber","english":"fever"},
    {"german":"die Allergie","english":"allergy"},
    {"german":"der Verband","english":"bandage"},
    {"german":"überweisen","english":"to refer to specialist"},
    {"german":"die Ernährung","english":"nutrition/diet"}
  ]},
  {"setId":"wm3","title":"Reisen & Verkehr","pairs":[
    {"german":"der Reisepass","english":"passport"},
    {"german":"das Gepäck","english":"luggage"},
    {"german":"die Verspätung","english":"delay"},
    {"german":"der Bahnsteig","english":"platform"},
    {"german":"umsteigen","english":"to transfer/change"},
    {"german":"die Zollkontrolle","english":"customs control"},
    {"german":"das Ticket","english":"ticket"},
    {"german":"buchen","english":"to book"},
    {"german":"die Unterkunft","english":"accommodation"},
    {"german":"abfliegen","english":"to depart (by plane)"}
  ]},
  {"setId":"wm4","title":"Wohnen","pairs":[
    {"german":"die Miete","english":"rent"},
    {"german":"die Kaution","english":"security deposit"},
    {"german":"der Vermieter","english":"landlord"},
    {"german":"der Mieter","english":"tenant"},
    {"german":"die Nebenkosten","english":"additional costs/utilities"},
    {"german":"kündigen","english":"to give notice"},
    {"german":"renovieren","english":"to renovate"},
    {"german":"der Keller","english":"basement/cellar"},
    {"german":"die Wohnfläche","english":"living area"},
    {"german":"einziehen","english":"to move in"}
  ]},
  {"setId":"wm5","title":"Umwelt & Natur","pairs":[
    {"german":"der Klimawandel","english":"climate change"},
    {"german":"das Recycling","english":"recycling"},
    {"german":"erneuerbar","english":"renewable"},
    {"german":"der CO₂-Fußabdruck","english":"carbon footprint"},
    {"german":"nachhaltig","english":"sustainable"},
    {"german":"die Treibhausgase","english":"greenhouse gases"},
    {"german":"sparen","english":"to save/conserve"},
    {"german":"der Müll","english":"waste/garbage"},
    {"german":"umweltfreundlich","english":"eco-friendly"},
    {"german":"die Solarenergie","english":"solar energy"}
  ]}
]}
```

---

### Game 2: Lückentext (Fill in the Blank)

Store as `assets/spiele/lueckentext.json`

```json
{"gameId":"luecken","title":"Lückentext","description":"Ergänze die Lücken mit dem richtigen Wort","sets":[
  {"setId":"lt1","title":"Grammatik: Präpositionen","sentences":[
    {"template":"Ich warte ___ den Bus.","answer":"auf","distractors":["an","in","mit"],"explanation":"'warten auf' ist eine feste Verbindung (Akkusativ)."},
    {"template":"Er fährt ___ der Arbeit nach Hause.","answer":"von","distractors":["aus","bei","mit"],"explanation":"'von ... nach Hause' = von der Arbeit nach Hause."},
    {"template":"Sie interessiert sich ___ Musik.","answer":"für","distractors":["an","über","mit"],"explanation":"'sich interessieren für' (Akkusativ)."},
    {"template":"Ich freue mich ___ deinen Besuch.","answer":"auf","distractors":["über","für","an"],"explanation":"'sich freuen auf' = looking forward to (Zukunft)."},
    {"template":"Wir sprechen ___ dem Problem.","answer":"über","distractors":["von","an","auf"],"explanation":"'sprechen über' (Akkusativ)."},
    {"template":"Das Buch liegt ___ dem Tisch.","answer":"auf","distractors":["an","in","über"],"explanation":"'auf dem Tisch' — Dativ (Lage/Position)."},
    {"template":"Er arbeitet ___ fünf Jahren in dieser Firma.","answer":"seit","distractors":["vor","für","in"],"explanation":"'seit' + Dativ für laufende Zeitdauer."},
    {"template":"Ich bin ___ meiner Freundin ins Kino gegangen.","answer":"mit","distractors":["bei","von","zu"],"explanation":"'mit jemandem gehen' (Begleitung)."},
    {"template":"Das Paket kommt ___ drei Tagen.","answer":"in","distractors":["nach","seit","vor"],"explanation":"'in drei Tagen' = in drei Tagen (Zukunft)."},
    {"template":"Ich denke oft ___ meine Heimat.","answer":"an","distractors":["über","von","bei"],"explanation":"'denken an' (Akkusativ)."}
  ]},
  {"setId":"lt2","title":"Konjunktiv II – Wünsche","sentences":[
    {"template":"Wenn ich mehr Zeit ___, würde ich mehr reisen.","answer":"hätte","distractors":["habe","hatte","haben"],"explanation":"Konjunktiv II von 'haben': hätte."},
    {"template":"Ich ___ gern Arzt, wenn ich Medizin studiert hätte.","answer":"wäre","distractors":["bin","war","sei"],"explanation":"Konjunktiv II von 'sein': wäre."},
    {"template":"Wenn es nicht regnen ___, würden wir wandern gehen.","answer":"würde","distractors":["wird","soll","kann"],"explanation":"Konjunktiv II mit 'würde'."},
    {"template":"An deiner Stelle ___ ich mehr üben.","answer":"würde","distractors":["werde","will","soll"],"explanation":"'An deiner Stelle würde ich...' — Konjunktiv II."},
    {"template":"Wenn ich du ___, würde ich das akzeptieren.","answer":"wäre","distractors":["bin","sei","war"],"explanation":"Konjunktiv II: 'Wenn ich du wäre...'"}
  ]},
  {"setId":"lt3","title":"Wortschatz: Arbeit","sentences":[
    {"template":"Er hat seine ___ zum 1. März eingereicht.","answer":"Kündigung","distractors":["Bewerbung","Einladung","Rechnung"],"explanation":"'Kündigung einreichen' = to hand in notice."},
    {"template":"Die ___ dauert normalerweise sechs Monate.","answer":"Probezeit","distractors":["Urlaubszeit","Arbeitszeit","Freizeit"],"explanation":"'Probezeit' = probationary period."},
    {"template":"Ich mache ein ___ in einer Marketingfirma.","answer":"Praktikum","distractors":["Studium","Seminar","Diplom"],"explanation":"'Praktikum' = internship."},
    {"template":"Mein Brutto-___ ist 3.500 Euro pro Monat.","answer":"Gehalt","distractors":["Preis","Betrag","Konto"],"explanation":"'Gehalt' = salary."},
    {"template":"Die ___ findet nächste Woche im Konferenzraum statt.","answer":"Besprechung","distractors":["Feier","Prüfung","Veranstaltung"],"explanation":"'Besprechung' = business meeting."}
  ]},
  {"setId":"lt4","title":"Relativsätze","sentences":[
    {"template":"Das ist das Buch, ___ ich dir empfohlen habe.","answer":"das","distractors":["der","die","dem"],"explanation":"'das Buch' (Neutrum Akkusativ) → Relativpronomen 'das'."},
    {"template":"Die Frau, ___ neben mir sitzt, ist meine Kollegin.","answer":"die","distractors":["der","das","den"],"explanation":"'die Frau' (Femininum Nominativ) → 'die'."},
    {"template":"Der Mann, ___ ich gestern getroffen habe, ist Arzt.","answer":"den","distractors":["der","dem","das"],"explanation":"'der Mann' (Maskulinum Akkusativ) → 'den'."},
    {"template":"Das ist die Stadt, in ___ ich geboren wurde.","answer":"der","distractors":["die","das","dem"],"explanation":"'in + Dativ' von 'die Stadt' (Femininum Dativ) → 'der'."},
    {"template":"Die Studenten, ___ den Test bestanden haben, feiern heute.","answer":"die","distractors":["der","den","denen"],"explanation":"'die Studenten' (Plural Nominativ) → 'die'."}
  ]},
  {"setId":"lt5","title":"Konnektoren","sentences":[
    {"template":"Ich lerne Deutsch, ___ ich in Deutschland studieren möchte.","answer":"weil","distractors":["obwohl","damit","wenn"],"explanation":"'weil' = because (Grund/Ursache, Verb am Ende)."},
    {"template":"___ es regnete, gingen wir trotzdem spazieren.","answer":"Obwohl","distractors":["Weil","Damit","Als"],"explanation":"'obwohl' = although/even though (Konzessiv)."},
    {"template":"Ich lerne früh, ___ ich abends Zeit für Familie habe.","answer":"damit","distractors":["weil","obwohl","wenn"],"explanation":"'damit' = so that (Zweck/Absicht)."},
    {"template":"___ ich in Deutschland ankam, sprach ich kaum Deutsch.","answer":"Als","distractors":["Wenn","Obwohl","Damit"],"explanation":"'als' = when (einmalige Vergangenheit)."},
    {"template":"Er arbeitet viel, ___ er wenig verdient.","answer":"obwohl","distractors":["weil","damit","als"],"explanation":"'obwohl' drückt einen Widerspruch aus."}
  ]}
]}
```

---

### Game 3: Satzordnung (Sentence Ordering)

Store as `assets/spiele/satzordnung.json`

```json
{"gameId":"satzordnung","title":"Satzordnung","description":"Bringe die Wörter in die richtige Reihenfolge","sets":[
  {"setId":"so1","title":"Hauptsätze","sentences":[
    {"words":["Ich","esse","jeden","Tag","Frühstück","morgens"],"correctOrder":[0,1,2,3,5,4],"correctSentence":"Ich esse morgens jeden Tag Frühstück.","explanation":"Zeitangabe (morgens) kommt vor andere Angaben."},
    {"words":["Er","hat","gestern","sein","Fahrrad","repariert"],"correctOrder":[0,1,2,3,4,5],"correctSentence":"Er hat gestern sein Fahrrad repariert.","explanation":"Perfekt: Hilfsverb (hat) an Position 2, Partizip am Ende."},
    {"words":["Wir","fahren","nächsten","Sommer","nach","Spanien"],"correctOrder":[0,1,2,3,4,5],"correctSentence":"Wir fahren nächsten Sommer nach Spanien.","explanation":"Zeitangabe vor Ortsangabe."}
  ]},
  {"setId":"so2","title":"Nebensätze mit 'weil'","sentences":[
    {"words":["Ich","bin","müde","weil","ich","gestern","nicht","geschlafen","habe"],"correctOrder":[0,1,2,3,4,5,6,8,7],"correctSentence":"Ich bin müde, weil ich gestern nicht geschlafen habe.","explanation":"Im Nebensatz steht das Verb ganz am Ende: 'geschlafen habe'."},
    {"words":["Sie","lernt","Deutsch","weil","sie","in","Deutschland","arbeiten","möchte"],"correctOrder":[0,1,2,3,4,5,6,8,7],"correctSentence":"Sie lernt Deutsch, weil sie in Deutschland arbeiten möchte.","explanation":"Modalverb (möchte) steht am Ende des Nebensatzes."}
  ]}
]}
```
