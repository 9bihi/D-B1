# TELC ExamData — Complete Integration
## Source: alejandrob-dev/telc-b1-trainer + Official TELC B1 Format
## Ready to paste into ExamData.kt

---

## Confirmed TELC B1 Exam Structure (Official)

```
Leseverstehen + Sprachbausteine — 90 min combined
  ├── Lesen Teil 1: 5 Kurztexte → 5 passende Überschriften aus 10 (global comprehension)
  ├── Lesen Teil 2: 1–2 longer texts → 5 questions, each has 3 sentences, pick which is correct
  ├── Lesen Teil 3: 12 Anzeigen → 10 Situationen → which ad matches which situation
  ├── Sprachbausteine Teil 1: letter with 10 gaps → 3 MC options each (grammar/vocab)
  └── Sprachbausteine Teil 2: letter with 10 gaps → choose from 15 given words

Hörverstehen — ~30 min
  ├── Hören Teil 1: 5 short dialogues → R/F per dialogue (5 questions)
  ├── Hören Teil 2: 1 longer interview → 10 R/F questions
  └── Hören Teil 3: 5 short announcements → R/F per (5 questions)

Schriftlicher Ausdruck — 30 min
  └── 1 personal or semi-formal letter, react to prompt, 4 bullet points, ~100 words

Mündliche Prüfung — ~15 min
  ├── Teil 1: Kontaktaufnahme (introduce yourself, ask partner questions)
  ├── Teil 2: Gespräch über ein Thema (discuss topic using 2 images/graphics)
  └── Teil 3: Gemeinsam eine Aufgabe lösen (plan something together)
```

---

## Complete Kotlin Code — Add to ExamData.kt

```kotlin
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
                explanation = "„rund 28 Millionen... etwa ein Drittel der Bevölkerung""
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
                explanation = "„Besonders beliebt ist das Ehrenamt bei Menschen über 60 Jahren.""
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
                explanation = "„Die häufigste Hürde für mehr Engagement ist Zeitmangel.""
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
                explanation = "„...dass Arbeitgeber ihren Mitarbeitern bezahlte Freistellungen für ehrenamtliche Tätigkeiten ermöglichen.""
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
                explanation = "„Wer sich engagiert, knüpft Kontakte, verbessert seine Deutschkenntnisse und lernt die Gesellschaft besser kennen.""
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


// ═══════════════════════════════════════════════════════════════════════
// REGISTER ALL TELC EXAMS — Add to your Exam list in ExamData.kt
// ═══════════════════════════════════════════════════════════════════════

/*
val allExams = listOf(
    // ... existing Goethe and OESD exams ...

    Exam(
        provider = ExamProvider.TELC,
        modelltestNumber = 1,
        lesenParts = TelcExam1LesenParts,
        hoerenParts = TelcExam1HoerenParts,
        schreibenTasks = TelcExam1SchreibenTasks,
        sprechenTasks = TelcExam1SprechenTasks
    ),
    Exam(
        provider = ExamProvider.TELC,
        modelltestNumber = 2,
        lesenParts = TelcExam2LesenParts,
        hoerenParts = TelcExam2HoerenParts,
        schreibenTasks = TelcExam2SchreibenTasks,
        sprechenTasks = TelcExam2SprechenTasks
    )
)
*/
```
