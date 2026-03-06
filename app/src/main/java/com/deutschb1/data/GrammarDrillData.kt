package com.deutschb1.data

object GrammarDrillData {
    data class GrammarDrill(
        val id: String,
        val type: DrillType,
        val question: String,
        val options: List<String>,
        val correctIndex: Int,
        val explanation: String
    )

    enum class DrillType {
        FILL_IN_THE_BLANK,
        MULTIPLE_CHOICE
    }

    data class GrammarTopic(
        val id: String,
        val title: String,
        val explanation: String,
        val examples: List<String>,
        val drills: List<GrammarDrill>
    )

    val topics = listOf(
        GrammarTopic(
            id = "konjunktiv_2",
            title = "Konjunktiv II",
            explanation = "Der Konjunktiv II wird für Wünsche, Träume, höfliche Fragen und irreale Bedingungen verwendet.",
            examples = listOf(
                "Ich hätte gerne einen Kaffee.",
                "Wenn ich reich wäre, würde ich eine Weltreise machen.",
                "Könnten Sie mir bitte helfen?"
            ),
            drills = listOf(
                GrammarDrill(
                    id = "k2_1",
                    type = DrillType.FILL_IN_THE_BLANK,
                    question = "Wenn ich Zeit ____, würde ich dich besuchen.",
                    options = listOf("habe", "hätte", "hatte"),
                    correctIndex = 1,
                    explanation = "Für irreale Bedingungen im Präsens verwenden wir 'hätte' (von haben)."
                ),
                GrammarDrill(
                    id = "k2_2",
                    type = DrillType.FILL_IN_THE_BLANK,
                    question = "Ich ____ gerne Millionär.",
                    options = listOf("bin", "war", "wäre"),
                    correctIndex = 2,
                    explanation = "Wünsche werden mit 'wäre' (von sein) ausgedrückt."
                )
            )
        ),
        GrammarTopic(
            id = "passiv",
            title = "Passiv",
            explanation = "Im Passiv ist die Handlung wichtig, nicht die Person, die sie ausführt. Bildung: werden + Partizip II.",
            examples = listOf(
                "Das Haus wird gebaut.",
                "Der Brief wurde gestern geschrieben.",
                "Die Hausaufgaben müssen gemacht werden."
            ),
            drills = listOf(
                GrammarDrill(
                    id = "passiv_1",
                    type = DrillType.FILL_IN_THE_BLANK,
                    question = "Der Kuchen ____ von meiner Mutter gebacken.",
                    options = listOf("wird", "ist", "hat"),
                    correctIndex = 0,
                    explanation = "Passiv Präsens wird mit 'werden' + Partizip II gebildet."
                ),
                GrammarDrill(
                    id = "passiv_2",
                    type = DrillType.FILL_IN_THE_BLANK,
                    question = "Die Tür ____ abgeschlossen.",
                    options = listOf("wurde", "war", "hatte"),
                    correctIndex = 0,
                    explanation = "Passiv Präteritum: 'wurde' + Partizip II."
                )
            )
        ),
        GrammarTopic(
            id = "relativsaetze",
            title = "Relativsätze",
            explanation = "Relativsätze geben zusätzliche Informationen über ein Nomen. Das Relativpronomen richtet sich nach Genus und Numerus des Nomens.",
            examples = listOf(
                "Das ist der Mann, der neben mir wohnt.",
                "Das Buch, das ich lese, ist spannend.",
                "Die Frau, die ich kenne, ist nett."
            ),
            drills = listOf(
                GrammarDrill(
                    id = "rel_1",
                    type = DrillType.FILL_IN_THE_BLANK,
                    question = "Der Hund, ____ dort läuft, gehört meinem Nachbarn.",
                    options = listOf("der", "die", "das"),
                    correctIndex = 0,
                    explanation = "Hund ist maskulin (der Hund), daher Relativpronomen 'der' im Nominativ."
                ),
                GrammarDrill(
                    id = "rel_2",
                    type = DrillType.FILL_IN_THE_BLANK,
                    question = "Das Haus, in ____ ich wohne, ist alt.",
                    options = listOf("das", "dem", "den"),
                    correctIndex = 1,
                    explanation = "Nach der Präposition 'in' (wo?) folgt der Dativ: das Haus -> in dem Haus."
                )
            )
        )
    )
}
