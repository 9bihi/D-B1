package com.deutschb1.data

data class Flashcard(
    val id: Int,
    val german: String,
    val english: String,
    val example: String = "",
    val topic: String
)

data class FlashcardDeck(
    val id: String,
    val name: String,
    val emoji: String,
    val cards: List<Flashcard>
)

object FlashcardData {
    val decks = listOf(
        FlashcardDeck(
            id = "familie",
            name = "Familie & Alltag",
            emoji = "👨‍👩‍👧‍👦",
            cards = listOf(
                Flashcard(1, "die Verwandtschaft", "relatives", "Meine Verwandtschaft ist sehr groß.", "Familie"),
                Flashcard(2, "sich vertragen", "to get along", "Wir vertragen uns gut.", "Familie"),
                Flashcard(3, "alleinerziehend", "single parent", "Sie ist eine alleinerziehende Mutter.", "Familie"),
                Flashcard(4, "das Sorgerecht", "custody", "Wer hat das Sorgerecht?", "Familie"),
                Flashcard(5, "aufwachsen", "to grow up", "Ich bin in Berlin aufgewachsen.", "Familie"),
                Flashcard(6, "die Erziehung", "upbringing / education", "Die Erziehung der Kinder ist wichtig.", "Familie"),
                Flashcard(7, "der Haushalt", "household", "Wer macht bei euch den Haushalt?", "Familie"),
                Flashcard(8, "sich scheiden lassen", "to get divorced", "Die Eltern lassen sich scheiden.", "Familie"),
                Flashcard(9, "die Beziehung", "relationship", "Sie haben eine gute Beziehung.", "Familie"),
                Flashcard(10, "der Lebensgefährte", "partner / companion", "Das ist mein Lebensgefährte.", "Familie")
            )
        ),
        FlashcardDeck(
            id = "arbeit",
            name = "Arbeit & Beruf",
            emoji = "💼",
            cards = listOf(
                Flashcard(11, "der Lebenslauf", "CV / Resume", "Schicken Sie uns Ihren Lebenslauf.", "Arbeit"),
                Flashcard(12, "das Vorstellungsgespräch", "job interview", "Morgen habe ich ein Vorstellungsgespräch.", "Arbeit"),
                Flashcard(13, "unbefristet", "permanent", "Ich habe einen unbefristeten Vertrag.", "Arbeit"),
                Flashcard(14, "kündigen", "to quit / resign", "Er hat gestern gekündigt.", "Arbeit"),
                Flashcard(15, "die Probezeit", "probationary period", "Die Probezeit dauert 6 Monate.", "Arbeit"),
                Flashcard(16, "das Gehalt", "salary", "Das Einstiegsgehalt ist gut.", "Arbeit"),
                Flashcard(17, "die Überstunde", "overtime", "Ich muss heute Überstunden machen.", "Arbeit"),
                Flashcard(18, "die Abteilung", "department", "Er arbeitet in der Marketingabteilung.", "Arbeit"),
                Flashcard(19, "verantwortlich", "responsible", "Wer ist dafür verantwortlich?", "Arbeit"),
                Flashcard(20, "die Beförderung", "promotion", "Sie hat eine Beförderung bekommen.", "Arbeit")
            )
        ),
        FlashcardDeck(
            id = "reisen",
            name = "Reisen & Verkehr",
            emoji = "✈️",
            cards = listOf(
                Flashcard(21, "die Sehenswürdigkeit", "sight / attraction", "Paris hat viele Sehenswürdigkeiten.", "Reisen"),
                Flashcard(22, "der Anschluss", "connection", "Ich habe den Anschluss verpasst.", "Reisen"),
                Flashcard(23, "umsteigen", "to change (trains)", "Sie müssen in Köln umsteigen.", "Reisen"),
                Flashcard(24, "die Unterkunft", "accommodation", "Haben wir schon eine Unterkunft?", "Reisen"),
                Flashcard(25, "verspätet", "delayed", "Der Flug ist leider verspätet.", "Reisen"),
                Flashcard(26, "die Grenz", "border", "Wir überqueren gleich die Grenze.", "Reisen"),
                Flashcard(27, "der Stau", "traffic jam", "Wir stehen seit einer Stunde im Stau.", "Reisen"),
                Flashcard(28, "das Reisebüro", "travel agency", "Wir buchen im Reisebüro.", "Reisen"),
                Flashcard(29, "die Fahrkarte", "ticket", "Haben Sie Ihre Fahrkarte?", "Reisen"),
                Flashcard(30, "der Bahnsteig", "platform", "Der Zug fährt an Bahnsteig 3 ab.", "Reisen")
            )
        ),
        FlashcardDeck(
            id = "gesundheit",
            name = "Gesundheit",
            emoji = "🏥",
            cards = listOf(
                Flashcard(31, "das Rezept", "prescription", "Der Arzt schreibt ein Rezept.", "Gesundheit"),
                Flashcard(32, "die Nebenwirkung", "side effect", "Hat das Medikament Nebenwirkungen?", "Gesundheit"),
                Flashcard(33, "sich erholen", "to recover", "Ich muss mich vom Stress erholen.", "Gesundheit"),
                Flashcard(34, "allergisch", "allergic", "Ich bin allergisch gegen Nüsse.", "Gesundheit"),
                Flashcard(35, "die Sprechstunde", "consultation hours", "Die Sprechstunde ist ab 8 Uhr.", "Gesundheit"),
                Flashcard(36, "die Untersuchung", "examination", "Die Untersuchung tat nicht weh.", "Gesundheit"),
                Flashcard(37, "das Symptom", "symptom", "Fieber ist ein typisches Symptom.", "Gesundheit"),
                Flashcard(38, "die Apotheke", "pharmacy", "Holen Sie das Medikament in der Apotheke.", "Gesundheit"),
                Flashcard(39, "der Schmerz", "pain", "Der Schmerz ist unerträglich.", "Gesundheit"),
                Flashcard(40, "krankgeschrieben", "on sick leave", "Ich bin bis Freitag krankgeschrieben.", "Gesundheit")
            )
        ),
        FlashcardDeck(
            id = "wohnen",
            name = "Wohnen",
            emoji = "🏠",
            cards = listOf(
                Flashcard(41, "die Kaution", "security deposit", "Die Kaution sind 3 Monatsmieten.", "Wohnen"),
                Flashcard(42, "einziehen", "to move in", "Wann können wir einziehen?", "Wohnen"),
                Flashcard(43, "der Mietvertrag", "rental contract", "Wir unterschreiben den Mietvertrag.", "Wohnen"),
                Flashcard(44, "die Nebenkosten", "utilities / extra costs", "Wie hoch sind die Nebenkosten?", "Wohnen"),
                Flashcard(45, "renovieren", "to renovate", "Wir müssen die Küche renovieren.", "Wohnen"),
                Flashcard(46, "möbliert", "furnished", "Die Wohnung ist komplett möbliert.", "Wohnen"),
                Flashcard(47, "der Immobilienmakler", "real estate agent", "Wir haben einen Makler engagiert.", "Wohnen"),
                Flashcard(48, "das Erdgeschoss", "ground floor", "Die Wohnung liegt im Erdgeschoss.", "Wohnen"),
                Flashcard(49, "kündigen", "to terminate (lease)", "Ich habe die Wohnung gekündigt.", "Wohnen"),
                Flashcard(50, "der Aufzug", "elevator", "Leider ist der Aufzug kaputt.", "Wohnen")
            )
        ),
        FlashcardDeck(
            id = "umwelt",
            name = "Umwelt & Natur",
            emoji = "🌿",
            cards = listOf(
                Flashcard(51, "der Klimawandel", "climate change", "Der Klimawandel betrifft uns alle.", "Umwelt"),
                Flashcard(52, "Müll trennen", "to separate trash", "Hier trennen wir den Müll.", "Umwelt"),
                Flashcard(53, "recyceln", "to recycle", "Glas kann man gut recyceln.", "Umwelt"),
                Flashcard(54, "nachhaltig", "sustainable", "Wir brauchen nachhaltige Lösungen.", "Umwelt"),
                Flashcard(55, "die Energie", "energy", "Wir müssen Energie sparen.", "Umwelt"),
                Flashcard(56, "der Umweltschutz", "environmental protection", "Umweltschutz ist wichtig.", "Umwelt"),
                Flashcard(57, "das Kraftwerk", "power plant", "Dieses Kraftwerk nutzt Kohle.", "Umwelt"),
                Flashcard(58, "verschmutzen", "to pollute", "Plastik verschmutzt das Meer.", "Umwelt"),
                Flashcard(59, "der Mülleimer", "trash can", "Wirf das in den Mülleimer.", "Umwelt"),
                Flashcard(60, "alternativ", "alternative", "Wir nutzen alternative Energien.", "Umwelt")
            )
        ),
        FlashcardDeck(
            id = "bildung",
            name = "Bildung",
            emoji = "📚",
            cards = listOf(
                Flashcard(61, "bestehen", "to pass (exam)", "Ich habe die Prüfung bestanden!", "Bildung"),
                Flashcard(62, "durchfallen", "to fail (exam)", "Er ist leider durchgefallen.", "Bildung"),
                Flashcard(63, "das Stipendium", "scholarship", "Sie hat ein Stipendium bekommen.", "Bildung"),
                Flashcard(64, "sich bewerben", "to apply", "Ich bewerbe mich um einen Studienplatz.", "Bildung"),
                Flashcard(65, "auswendig lernen", "to learn by heart", "Ich muss die Verben auswendig lernen.", "Bildung"),
                Flashcard(66, "das Semester", "semester", "Das neue Semester beginnt bald.", "Bildung"),
                Flashcard(67, "die Vorlesung", "lecture", "Die Vorlesung ist sehr interessant.", "Bildung"),
                Flashcard(68, "prüfen", "to test / examine", "Der Lehrer prüft die Schüler.", "Bildung"),
                Flashcard(69, "der Abschluss", "degree / graduation", "Welchen Abschluss hast du?", "Bildung"),
                Flashcard(70, "die Hausarbeit", "term paper", "Ich muss eine Hausarbeit schreiben.", "Bildung")
            )
        )
    )
}
