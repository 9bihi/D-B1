package com.deutschb1.data

object GeschichtenData {
    data class VocabHint(
        val word: String,
        val definition: String
    )

    data class Geschichte(
        val id: String,
        val title: String,
        val level: String,
        val bodyText: String,
        val vocabHints: List<VocabHint>,
        val questions: List<MultipleChoiceQuestion>,
        val estimatedReadTimeMin: Int
    )

    val stories = listOf(
        Geschichte(
            id = "story_1",
            title = "Der neue Nachbar",
            level = "A2",
            bodyText = """
                An einem sonnigen Montagmorgen zog ein neuer Nachbar in das Haus gegenüber ein. 
                Er hieß Herr Müller und kam aus Hamburg. Er hatte viele große Kisten und einen kleinen, braunen Hund. 
                Ich beobachtete ihn vom Fenster aus. Herr Müller sah freundlich aus, aber er wirkte auch ein bisschen müde. 
                Am Nachmittag ging ich hinüber und begrüßte ihn. „Guten Tag, ich bin Ihre Nachbarin“, sagte ich. 
                Er lächelte und antwortete: „Guten Tag! Ich freue mich, Sie kennenzulernen. Das ist mein Hund, Waldi.“ 
                Waldi bellte leise und wedelte mit dem Schwanz. Wir sprachen ein wenig über die Nachbarschaft und die besten Supermärkte in der Nähe. 
                Es war ein schöner Anfang für eine neue Nachbarschaft.
            """.trimIndent(),
            vocabHints = listOf(
                VocabHint("gegenüber", "across/opposite"),
                VocabHint("beobachtete", "watched/observed"),
                VocabHint("wirkte", "seemed/appeared"),
                VocabHint("wedelte", "wagged")
            ),
            questions = listOf(
                MultipleChoiceQuestion(1, "Woher kommt Herr Müller?", listOf("Berlin", "Hamburg", "München"), 1),
                MultipleChoiceQuestion(2, "Was hat Herr Müller dabei?", listOf("Einen Vogel", "Einen Hund", "Eine Katze"), 1),
                MultipleChoiceQuestion(3, "Wann hat die Nachbarin Herrn Müller begrüßt?", listOf("Am Morgen", "Am Mittag", "Am Nachmittag"), 2)
            ),
            estimatedReadTimeMin = 3
        ),
        Geschichte(
            id = "story_2",
            title = "Ein Tag am See",
            level = "B1",
            bodyText = """
                Letzten Samstag beschlossen meine Freunde und ich, einen Ausflug zum Wannsee zu machen. 
                Das Wetter war herrlich, die Sonne schien und es gab kaum Wolken am Himmel. 
                Wir packten unsere Rucksäcke mit Picknickdecken, Wasserflaschen und jeder Menge Obst. 
                Als wir am See ankamen, war es schon ziemlich voll, aber wir fanden trotzdem ein schönes Plätzchen im Schatten eines großen Baumes. 
                Zuerst gingen wir alle ins Wasser. Es war erfrischend und klar. 
                Nach dem Schwimmen hatten wir großen Hunger und machten unser Picknick. 
                Wir unterhielten uns über unsere Pläne für den Sommer und lachten viel. 
                Plötzlich zog ein Gewitter auf, und wir mussten schnell unsere Sachen packen. 
                Obwohl der Ausflug früher endete als geplant, hatten wir einen wunderbaren Tag.
            """.trimIndent(),
            vocabHints = listOf(
                VocabHint("beschlossen", "decided"),
                VocabHint("herrlich", "wonderful/splendid"),
                VocabHint("erfrischend", "refreshing"),
                VocabHint("Gewitter", "thunderstorm")
            ),
            questions = listOf(
                MultipleChoiceQuestion(1, "Wohin sind die Freunde gefahren?", listOf("In die Berge", "Zum Wannsee", "In den Wald"), 1),
                MultipleChoiceQuestion(2, "Was haben sie für das Picknick mitgebracht?", listOf("Brot und Käse", "Wasser und Obst", "Pizza und Cola"), 1),
                MultipleChoiceQuestion(3, "Warum mussten sie den Ausflug früher beenden?", listOf("Weil sie müde waren", "Wegen eines Gewitters", "Weil es zu heiß war"), 1)
            ),
            estimatedReadTimeMin = 4
        ),
        Geschichte(
            id = "story_3",
            title = "Das Vorstellungsgespräch",
            level = "B1",
            bodyText = """
                Thomas war sehr nervös. Heute hatte er sein erstes großes Vorstellungsgespräch bei einer internationalen Firma in Frankfurt. 
                Er hatte sich wochenlang darauf vorbereitet, seine Antworten geübt und seinen besten Anzug gereinigt. 
                Als er das moderne Bürogebäude betrat, war er beeindruckt von der Architektur. 
                Pünktlich um 10 Uhr wurde er in den Konferenzraum gerufen. 
                Dort saßen drei Personen: die Personalleiterin, der Abteilungsleiter und eine Sekretärin. 
                Die Fragen waren schwierig, aber Thomas blieb ruhig und antwortete präzise. 
                Er sprach über seine Erfahrungen im Marketing und seine Ziele für die Zukunft. 
                Am Ende des Gesprächs lächelte die Personalleiterin und sagte: „Vielen Dank, Herr Schmidt. Wir melden uns bis Ende der Woche bei Ihnen.“ 
                Thomas verließ das Gebäude mit einem guten Gefühl.
            """.trimIndent(),
            vocabHints = listOf(
                VocabHint("Vorstellungsgespräch", "job interview"),
                VocabHint("beeindruckt", "impressed"),
                VocabHint("präzise", "precise/accurately"),
                VocabHint("Erfahrungen", "experiences")
            ),
            questions = listOf(
                MultipleChoiceQuestion(1, "In welcher Stadt war das Vorstellungsgespräch?", listOf("Berlin", "Frankfurt", "Hamburg"), 1),
                MultipleChoiceQuestion(2, "Wie viele Personen saßen im Konferenzraum?", listOf("Zwei", "Drei", "Vier"), 1),
                MultipleChoiceQuestion(3, "Wann bekommt Thomas eine Rückmeldung?", listOf("Morgen", "Bis Ende der Woche", "In zwei Wochen"), 1)
            ),
            estimatedReadTimeMin = 5
        )
    )
}
