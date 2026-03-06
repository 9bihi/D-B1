package com.deutschb1.data

object SpielData {
    data class WordPair(val german: String, val english: String)
    
    data class FillSentence(
        val question: String,
        val answer: String,
        val options: List<String>,
        val explanation: String
    )

    data class GameSet(
        val id: String,
        val title: String,
        val description: String,
        val emoji: String,
        val type: GameType
    )

    enum class GameType {
        WORD_MATCH,
        FILL_BLANK
    }

    val gameSets = listOf(
        GameSet(
            id = "match_daily",
            title = "Alltag Match",
            description = "Verbinde deutsche Wörter mit ihrer Übersetzung.",
            emoji = "🧩",
            type = GameType.WORD_MATCH
        ),
        GameSet(
            id = "fill_prepositions",
            title = "Präpositionen",
            description = "Fülle die Lücken im Satz aus.",
            emoji = "📝",
            type = GameType.FILL_BLANK
        ),
        GameSet(
            id = "match_travel",
            title = "Reise Vokabeln",
            description = "Finde das passende englische Wort für den Urlaub.",
            emoji = "🏖️",
            type = GameType.WORD_MATCH
        )
    )

    val wordPairs = mapOf(
        "match_daily" to listOf(
            WordPair("der Tisch", "the table"),
            WordPair("das Fenster", "the window"),
            WordPair("die Tür", "the door"),
            WordPair("der Stuhl", "the chair"),
            WordPair("das Buch", "the book"),
            WordPair("der Stift", "the pen"),
            WordPair("die Lampe", "the lamp"),
            WordPair("der Spiegel", "the mirror")
        ),
        "match_travel" to listOf(
            WordPair("der Flughafen", "the airport"),
            WordPair("das Flugzeug", "the airplane"),
            WordPair("der Bahnhof", "the train station"),
            WordPair("die Fahrkarte", "the ticket"),
            WordPair("das Gepäck", "the luggage"),
            WordPair("der Koffer", "the suitcase"),
            WordPair("das Hotel", "the hotel"),
            WordPair("der Strand", "the beach")
        )
    )

    val fillSentences = mapOf(
        "fill_prepositions" to listOf(
            FillSentence(
                question = "Ich warte ____ den Bus.",
                answer = "auf",
                options = listOf("auf", "an", "für", "bei"),
                explanation = "Warten + auf (Akkusativ)."
            ),
            FillSentence(
                question = "Er interessiert sich ____ Musik.",
                answer = "für",
                options = listOf("an", "von", "in", "für"),
                explanation = "Sich interessieren + für (Akkusativ)."
            ),
            FillSentence(
                question = "Denk bitte ____ deinen Schlüssel!",
                answer = "an",
                options = listOf("nach", "an", "von", "über"),
                explanation = "Denken + an (Akkusativ)."
            )
        )
    )
}
