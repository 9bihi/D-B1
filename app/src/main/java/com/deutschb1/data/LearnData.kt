package com.deutschb1.data

import androidx.annotation.DrawableRes
import com.deutschb1.R
import androidx.compose.ui.graphics.Color

// ─── Theme Enum ───────────────────────────────────────────────────────────────

enum class LearnTheme(
    val displayName: String,
    val emoji: String,
    val description: String,
    val colorHex: String,
    @DrawableRes val imageRes: Int? = null
) {
    FAMILIE("Familie & Beziehungen", "👨‍👩‍👧‍👦", "Familienleben, Verwandtschaft, Freundschaft", "#FF6B6B", R.drawable.hero_familie),
    ARBEIT("Arbeit & Beruf", "💼", "Berufsleben, Bewerbung, Kollegen", "#4ECDC4", R.drawable.hero_arbeit),
    FREIZEIT("Freizeit & Hobbys", "🎨", "Sport, Kultur, Hobbys, Urlaub", "#45B7D1", R.drawable.hero_freizeit),
    REISEN("Reisen & Verkehr", "✈️", "Transport, Orientierung, Reisen", "#96CEB4", R.drawable.hero_reisen),
    GESUNDHEIT("Gesundheit & Körper", "🏥", "Arzt, Krankheit, Körperteile, Apotheke", "#FFEAA7", R.drawable.hero_gesundheit),
    WOHNEN("Wohnen & Haushalt", "🏠", "Wohnung, Möbel, Reparatur, Hausarbeit", "#DDA0DD", R.drawable.hero_wohnen),
    EINKAUFEN("Einkaufen & Konsum", "🛍️", "Shops, Preise, Reklamation, Trends", "#98D8C8"),
    BILDUNG("Bildung & Schule", "📚", "Schule, Studium, Lernen, Prüfungen", "#F7DC6F"),
    UMWELT("Umwelt & Natur", "🌿", "Klimawandel, Natur, Nachhaltigkeit", "#82E0AA"),
    ESSEN("Essen & Trinken", "🍽️", "Lebensmittel, Kochen, Restaurant, Rezepte", "#FAD7A0"),
    SCHREIBEN_TEIL1("Beschreibender Text", "✍️", "E-Mails oder Blog-Einträge über persönliche Erlebnisse", "#FF9F43"),
    SCHREIBEN_TEIL2("Meinungsäußerung", "🗣️", "Forumsbeiträge mit Argumenten", "#54A0FF"),
    SCHREIBEN_TEIL3("Privater oder formeller Brief", "📝", "Briefe und E-Mails zu Alltagssituationen", "#5F27CD"),
    // Connector themes
    KONJUNKTIONEN("Konjunktionen", "🔗", "Verbindungswörter für Hauptsätze", "#4A90E2"),
    SUBJUNKTIONEN("Subjunktionen", "🔀", "Nebensatzeinleiter", "#50E3C2"),
    KONJUNKTIONALADVERBIEN("Konjunktionaladverbien", "⚡", "Adverbien mit Inversion", "#F5A623"),
    // Grammar themes
    GRAM_PERFEKT("Perfekt", "⏱️", "Present Perfect Tense", "#4285F4"),
    GRAM_PRAETERITUM("Präteritum", "📖", "Simple Past Tense", "#EA4335"),
    GRAM_MODALVERBEN("Modalverben", "🎯", "Modal Verbs (können, müssen, ...)", "#FBBC05"),
    GRAM_PASSIV("Passiv", "🔄", "Passive Voice", "#34A853"),
    GRAM_KONJUNKTIV_II("Konjunktiv II", "✨", "Subjunctive II (würde, hätte, wäre)", "#FF6D00"),
    GRAM_RELATIVSATZ("Relativsätze", "🔗", "Relative Clauses", "#9C27B0"),
    GRAM_ADJEKTIVDEKLINATION("Adjektivdeklination", "📊", "Adjective Endings", "#00ACC1"),
    GRAM_PRAEPOSITIONEN("Präpositionen", "📍", "Prepositions and Cases", "#FFB300"),
    GRAM_WORTSTELLUNG("Wortstellung", "📐", "Word Order (Satzbau)", "#7B1FA2"),
    GRAM_INFINITIV_MIT_ZU("Infinitiv mit 'zu'", "📝", "Infinitive with 'zu', 'um...zu'", "#E67E22"),
    GRAM_KOMPARATIV("Komparativ & Superlativ", "📈", "Comparatives and Superlatives", "#2E7D32"),
    GRAM_NEGATION("Negation", "🚫", "nicht vs. kein", "#C62828"),
    GRAM_REFLEXIVE_VERBEN("Reflexive Verben", "🔄", "Reflexive Verbs", "#6A1B9A"),
    GRAM_FRAGEN("Fragen", "❓", "Questions (W‑Fragen, indirekte Fragen)", "#1565C0"),
    GRAM_TRENNBARE_VERBEN("Trennbare Verben", "✂️", "Separable and Inseparable Prefixes", "#EF6C00"),
    GRAM_GENITIV("Genitiv", "👑", "Genitive Case", "#4A148C"),
    GRAM_IMPERATIV("Imperativ", "❗", "Commands and Requests", "#B71C1C"),
    SPRECHEN_B1("Sprechen B1", "🗣️", "Dialoge und Präsentationen für die mündliche Prüfung", "#E67E22")
}


// ─── Phrase Model ─────────────────────────────────────────────────────────────

data class LearnPhrase(
    val german: String,
    val english: String,
    val exampleSentence: String,
    val exampleTranslation: String = "",
    val usageTip: String = ""
)

data class LearnThemeContent(
    val theme: LearnTheme,
    val phrases: List<LearnPhrase>
)

// ─── Phrases per Theme ────────────────────────────────────────────────────────

val FamilieContent = LearnThemeContent(
    theme = LearnTheme.FAMILIE,
    phrases = listOf(
        LearnPhrase("die Verwandtschaft", "the relatives/family", "Meine Verwandtschaft trifft sich jedes Jahr zu Weihnachten.", "My relatives meet every year at Christmas.", "Kollektiv für alle Familienmitglieder"),
        LearnPhrase("sich vertragen (mit)", "to get along (with)", "Ich vertrage mich gut mit meiner Schwiegermutter.", "I get along well with my mother-in-law.", "Reflexives Verb – immer mit 'sich'"),
        LearnPhrase("in Streit geraten", "to get into an argument", "Wir sind wegen Kleinigkeiten in Streit geraten.", "We got into an argument over small things.", "Idiom für Konflikte"),
        LearnPhrase("der/die Alleinerziehende", "single parent", "Alleinerziehende Mütter haben oft wenig Zeit.", "Single mothers often have little time.", "Adjektivisch dekliniert"),
        LearnPhrase("verlobt sein (mit)", "to be engaged (to)", "Sie ist seit einem Jahr mit ihm verlobt.", "She has been engaged to him for a year.", "Vor der Hochzeit"),
        LearnPhrase("das Sorgerecht", "custody", "Nach der Scheidung teilen sich die Eltern das Sorgerecht.", "After the divorce, the parents share custody."),
        LearnPhrase("aufwachsen (in/bei)", "to grow up (in/with)", "Ich bin auf dem Land aufgewachsen.", "I grew up in the countryside.", "Trennbares Verb"),
        LearnPhrase("die Geschwister (Pl.)", "siblings", "Hast du Geschwister? – Ja, einen Bruder und zwei Schwestern.", "Do you have siblings? – Yes, one brother and two sisters."),
        LearnPhrase("sich um jemanden kümmern", "to take care of someone", "Sie kümmert sich täglich um ihre kranke Mutter.", "She takes care of her sick mother every day.", "Typisch in Pflegekontexten"),
        LearnPhrase("gemeinsam Entscheidungen treffen", "to make decisions together", "Im Haushalt treffen wir alle Entscheidungen gemeinsam.", "In our household, we make all decisions together."),
        LearnPhrase("die Kindererziehung", "child-rearing / upbringing", "Über die Kindererziehung gibt es verschiedene Ansichten.", "There are different views on child-rearing."),
        LearnPhrase("jemanden unterstützen", "to support someone", "Meine Familie unterstützt mich immer.", "My family always supports me."),
        LearnPhrase("die Ehe eingehen", "to enter into marriage", "Sie haben die Ehe vor einem Jahr eingegangen.", "They entered into marriage a year ago.", "Formell / offiziell"),
        LearnPhrase("Verantwortung übernehmen", "to take responsibility", "Als Elternteil muss man viel Verantwortung übernehmen.", "As a parent, you have to take a lot of responsibility."),
        LearnPhrase("in einer WG wohnen", "to live in a shared flat", "Während des Studiums wohne ich in einer WG.", "During my studies, I live in a shared flat.", "WG = Wohngemeinschaft"),
        LearnPhrase("das Familientreffen", "family gathering", "Das jährliche Familientreffen ist immer lustig.", "The annual family gathering is always fun."),
        LearnPhrase("Rücksicht nehmen (auf)", "to be considerate (of)", "In einer Gemeinschaft muss man Rücksicht aufeinander nehmen.", "In a community, you have to be considerate of each other."),
        LearnPhrase("herzlich willkommen heißen", "to welcome warmly", "Wir heißen die Neuzugänge herzlich willkommen.", "We warmly welcome the newcomers."),
        LearnPhrase("der Lebenspartner / die Lebenspartnerin", "life partner", "Mein Lebenspartner und ich reisen gern zusammen.", "My life partner and I love to travel together."),
        LearnPhrase("sich einigen (auf)", "to agree (on)", "Die Familie hat sich auf ein gemeinsames Ziel geeinigt.", "The family agreed on a common goal.")
    )
)

val ArbeitContent = LearnThemeContent(
    theme = LearnTheme.ARBEIT,
    phrases = listOf(
        LearnPhrase("sich bewerben (um/bei)", "to apply (for/at)", "Ich habe mich um die Stelle als Buchhalter beworben.", "I applied for the position as an accountant.", "Reflexives Verb"),
        LearnPhrase("der Lebenslauf", "CV / résumé", "Ihr Lebenslauf war sehr beeindruckend.", "Her CV was very impressive.", "Wichtig: tabellarischer Lebenslauf"),
        LearnPhrase("das Vorstellungsgespräch", "job interview", "Ich bin sehr nervös vor meinem Vorstellungsgespräch.", "I'm very nervous before my job interview."),
        LearnPhrase("befristet / unbefristet", "temporary / permanent", "Ich habe einen unbefristeten Vertrag bekommen.", "I got a permanent contract.", "Wichtiger Unterschied im Jobmarkt"),
        LearnPhrase("das Gehalt verhandeln", "to negotiate salary", "Bei einem neuen Job sollte man das Gehalt immer verhandeln.", "When starting a new job, you should always negotiate salary."),
        LearnPhrase("in Elternzeit gehen", "to go on parental leave", "Er geht für drei Monate in Elternzeit.", "He is going on parental leave for three months.", "Sehr verbreitet in DE/AT"),
        LearnPhrase("die Überstunden", "overtime", "Ich mache regelmäßig Überstunden.", "I regularly work overtime."),
        LearnPhrase("kündigen / die Kündigung", "to quit / resignation", "Sie hat nach zehn Jahren gekündigt.", "She quit after ten years."),
        LearnPhrase("selbstständig / freiberuflich", "self-employed / freelance", "Als Fotograf arbeite ich selbstständig.", "As a photographer, I work freelance."),
        LearnPhrase("die Weiterbildung", "further training / professional development", "Das Unternehmen finanziert die Weiterbildung der Mitarbeiter.", "The company finances employee professional development."),
        LearnPhrase("teamfähig sein", "to be a team player", "In der Stellenanzeige steht: 'teamfähig erforderlich'.", "The job ad says: 'team skills required'."),
        LearnPhrase("das Arbeitszeugnis", "work reference/certificate", "Ich brauche ein gutes Arbeitszeugnis für die Bewerbung.", "I need a good work reference for the application."),
        LearnPhrase("Verantwortung tragen", "to bear responsibility", "Als Teamleiter trägt er viel Verantwortung.", "As team leader, he bears a lot of responsibility."),
        LearnPhrase("der Mindestlohn", "minimum wage", "In Deutschland gilt seit 2015 ein gesetzlicher Mindestlohn.", "Germany has had a statutory minimum wage since 2015."),
        LearnPhrase("auf Dienstreise gehen", "to go on a business trip", "Nächste Woche bin ich auf Dienstreise in München.", "Next week I'm on a business trip to Munich."),
        LearnPhrase("krankschreiben lassen", "to get a sick note", "Ich muss mich heute krankschreiben lassen.", "I have to get a sick note today."),
        LearnPhrase("die Probezeit", "probationary period", "Die Probezeit dauert in der Regel sechs Monate.", "The probationary period usually lasts six months."),
        LearnPhrase("Vollzeit / Teilzeit", "full-time / part-time", "Sie arbeitet seit der Geburt ihres Kindes nur noch Teilzeit.", "Since the birth of her child, she only works part-time."),
        LearnPhrase("das Betriebsklima", "workplace atmosphere", "Ein gutes Betriebsklima ist sehr wichtig für die Produktivität.", "A good workplace atmosphere is very important for productivity."),
        LearnPhrase("berufliche Ziele setzen", "to set professional goals", "Es ist wichtig, sich klare berufliche Ziele zu setzen.", "It is important to set clear professional goals.")
    )
)

val FreizeitContent = LearnThemeContent(
    theme = LearnTheme.FREIZEIT,
    phrases = listOf(
        LearnPhrase("einem Verein beitreten", "to join a club", "Ich bin einem Sportverein beigetreten.", "I joined a sports club.", "Verein = sehr typisch in Deutschland"),
        LearnPhrase("Ehrenamt / ehrenamtlich tätig sein", "to volunteer", "In meiner Freizeit bin ich ehrenamtlich tätig.", "In my free time, I do volunteer work."),
        LearnPhrase("entspannen / sich erholen", "to relax / to recover", "Am Wochenende möchte ich mich endlich erholen.", "This weekend I finally want to relax."),
        LearnPhrase("ein Instrument spielen", "to play an instrument", "Seit sechs Jahren spiele ich Gitarre.", "I've been playing guitar for six years."),
        LearnPhrase("ins Kino / Theater gehen", "to go to the cinema / theater", "Wir gehen jeden Freitag ins Kino.", "We go to the cinema every Friday."),
        LearnPhrase("ein Buch verschlingen", "to devour a book", "Ich habe das Buch an einem Wochenende verschlungen.", "I devoured the book in one weekend.", "Figurative Sprache"),
        LearnPhrase("die Ausstellung besuchen", "to visit an exhibition", "Das Stadtmuseum hat eine interessante Ausstellung.", "The city museum has an interesting exhibition."),
        LearnPhrase("wandern gehen", "to go hiking", "Jedes Wochenende gehen wir in den Bergen wandern.", "Every weekend we go hiking in the mountains."),
        LearnPhrase("ein Hobby aufgeben", "to give up a hobby", "Ich habe das Kochen als Hobby aufgegeben – keine Zeit.", "I gave up cooking as a hobby – no time."),
        LearnPhrase("Karten spielen", "to play cards", "Früher haben wir oft Karten gespielt.", "In the past, we often played cards."),
        LearnPhrase("die Urlaubsplanung", "holiday planning", "Die Urlaubsplanung macht mir großen Spaß.", "Holiday planning is a lot of fun for me."),
        LearnPhrase("Sport treiben", "to do sport", "Es ist wichtig, regelmäßig Sport zu treiben.", "It is important to do sport regularly."),
        LearnPhrase("sich für etwas interessieren", "to be interested in something", "Ich interessiere mich sehr für Fotografie.", "I am very interested in photography."),
        LearnPhrase("die Leidenschaft für etwas entdecken", "to discover a passion for something", "Erst im Alter entdeckte er seine Leidenschaft fürs Malen.", "Only in old age did he discover his passion for painting."),
        LearnPhrase("abschalten können", "to be able to switch off", "Nach der Arbeit kann ich gut abschalten.", "After work, I can switch off well.", "Umgangssprachlich"),
        LearnPhrase("die freie Zeit sinnvoll nutzen", "to make good use of free time", "Man sollte die freie Zeit sinnvoll nutzen.", "One should make good use of free time."),
        LearnPhrase("regelmäßige Routine", "regular routine", "Eine regelmäßige Routine hilft bei der Work-Life-Balance.", "A regular routine helps with work-life balance."),
        LearnPhrase("etwas Neues ausprobieren", "to try something new", "Ich möchte mal Töpfern ausprobieren.", "I'd like to try pottery sometime."),
        LearnPhrase("reisen / auf Reisen gehen", "to travel / to go on trips", "Im Urlaub gehen wir gerne auf Reisen.", "On holiday, we love to go on trips."),
        LearnPhrase("gesellig sein", "to be sociable", "Sie ist sehr gesellig und hat viele Freunde.", "She is very sociable and has many friends.")
    )
)

val ReisenContent = LearnThemeContent(
    theme = LearnTheme.REISEN,
    phrases = listOf(
        LearnPhrase("den Zug/Flug verpassen", "to miss the train/flight", "Ich habe meinen Zug verpasst!", "I missed my train!", "Häufige Situation beim Reisen"),
        LearnPhrase("buchen / eine Reservierung machen", "to book / to make a reservation", "Ich habe das Hotel für drei Nächte gebucht.", "I booked the hotel for three nights."),
        LearnPhrase("das Gepäck aufgeben", "to check in luggage", "Ich gebe nur ein Koffer auf.", "I'm only checking in one suitcase.", "Am Flughafen"),
        LearnPhrase("der Anschluss (Zug/Flug)", "connection (train/flight)", "Ich habe meinen Anschlussflug knapp erreicht.", "I barely made my connecting flight."),
        LearnPhrase("sich verfahren / sich verlaufen", "to get lost (in a car / on foot)", "Wir haben uns in der Altstadt verlaufen.", "We got lost in the old town."),
        LearnPhrase("die Sehenswürdigkeit", "sight / tourist attraction", "Die wichtigsten Sehenswürdigkeiten stehen auf meiner Liste.", "The most important sights are on my list."),
        LearnPhrase("der Reiseführer", "travel guide (book/person)", "Wir haben einen persönlichen Reiseführer gemietet.", "We hired a personal tour guide."),
        LearnPhrase("umsteigen", "to change (train/bus)", "Sie müssen zweimal umsteigen.", "You have to change twice.", "Trennbares Verb"),
        LearnPhrase("die Unterkunft", "accommodation", "Haben Sie günstige Unterkünfte empfehlen?", "Can you recommend affordable accommodation?"),
        LearnPhrase("einchecken / auschecken", "to check in / check out", "Der Check-in ist ab 15 Uhr.", "Check-in is from 3 pm."),
        LearnPhrase("den Paß / das Visum verlängern", "to renew the passport / visa", "Mein Pass muss bald verlängert werden.", "My passport needs to be renewed soon."),
        LearnPhrase("der Stau", "traffic jam", "Wegen eines Staus kamen wir zu spät an.", "Due to a traffic jam, we arrived late."),
        LearnPhrase("eine Stadtrundfahrt machen", "to take a city tour", "Wir haben eine Stadtrundfahrt mit dem Bus gemacht.", "We took a city bus tour."),
        LearnPhrase("hin und zurück / einfache Fahrt", "return / one-way", "Einmal nach Hamburg hin und zurück, bitte.", "A return ticket to Hamburg, please."),
        LearnPhrase("Verspätung haben", "to be delayed", "Der Zug hat leider 30 Minuten Verspätung.", "The train is unfortunately 30 minutes late."),
        LearnPhrase("den Zoll passieren", "to pass through customs", "Am Flughafen mussten wir den Zoll passieren.", "At the airport, we had to pass through customs."),
        LearnPhrase("die Reisekrankenversicherung", "travel health insurance", "Nehmen Sie immer eine Reisekrankenversicherung mit.", "Always take out travel health insurance."),
        LearnPhrase("an der Rezeption", "at the reception desk", "Bitte hinterlassen Sie Ihren Schlüssel an der Rezeption.", "Please leave your key at the reception desk."),
        LearnPhrase("der Ausflug", "day trip / excursion", "Wir machen morgen einen Ausflug ans Meer.", "Tomorrow we're taking a day trip to the sea."),
        LearnPhrase("Reisevorbereitungen treffen", "to make travel preparations", "Gute Reisevorbereitungen vermindern Stress.", "Good travel preparations reduce stress.")
    )
)

val GesundheitContent = LearnThemeContent(
    theme = LearnTheme.GESUNDHEIT,
    phrases = listOf(
        LearnPhrase("einen Termin beim Arzt machen", "to make a doctor's appointment", "Ich muss einen Termin beim Hausarzt machen.", "I need to make an appointment with my GP.", "Hausarzt = GP / family doctor"),
        LearnPhrase("Beschwerden haben", "to have complaints/symptoms", "Ich habe Beschwerden im Rücken.", "I have back complaints.", "Formal term for symptoms"),
        LearnPhrase("das Rezept einlösen", "to fill a prescription", "Bitte lösen Sie das Rezept in der Apotheke ein.", "Please fill the prescription at the pharmacy."),
        LearnPhrase("allergisch sein (gegen)", "to be allergic (to)", "Ich bin allergisch gegen Penicillin.", "I am allergic to penicillin.", "Wichtig für den Arztbesuch"),
        LearnPhrase("sich erholen (von)", "to recover (from)", "Sie erholt sich gut von der Operation.", "She is recovering well from the operation."),
        LearnPhrase("Fieber messen", "to take one's temperature", "Das Kind hat Fieber – ich messe es gleich.", "The child has a fever – I'll take it right away."),
        LearnPhrase("die Krankenversicherung", "health insurance", "In Deutschland ist die Krankenversicherung Pflicht.", "In Germany, health insurance is compulsory."),
        LearnPhrase("vorsorgen / Vorsorgeuntersuchung", "to take preventive measures / check-up", "Die jährliche Vorsorgeuntersuchung ist sehr wichtig.", "The annual check-up is very important."),
        LearnPhrase("sich verletzen", "to injure oneself", "Er hat sich beim Sport verletzt.", "He injured himself while playing sport.", "Reflexives Verb"),
        LearnPhrase("operiert werden", "to have an operation", "Er wurde erfolgreich am Knie operiert.", "He was successfully operated on his knee."),
        LearnPhrase("die Nebenwirkungen", "side effects", "Dieses Medikament hat kaum Nebenwirkungen.", "This medication has hardly any side effects."),
        LearnPhrase("auf Diät sein", "to be on a diet", "Sie ist seit drei Monaten auf Diät.", "She has been on a diet for three months."),
        LearnPhrase("sich gesund ernähren", "to eat healthily", "Man sollte sich so oft wie möglich gesund ernähren.", "One should eat as healthily as possible."),
        LearnPhrase("der Notarzt", "emergency doctor", "In einem Notfall ruft man den Notarzt.", "In an emergency, you call the emergency doctor."),
        LearnPhrase("die Immunität", "immunity", "Durch Impfungen kann man eine gute Immunität aufbauen.", "Through vaccinations, you can build good immunity."),
        LearnPhrase("schlecht schlafen", "to sleep poorly", "Seit Wochen schlafe ich schlecht.", "I've been sleeping poorly for weeks."),
        LearnPhrase("psychische Gesundheit", "mental health", "Psychische Gesundheit ist genauso wichtig wie körperliche.", "Mental health is just as important as physical."),
        LearnPhrase("die Sprechstunde", "consultation hours", "Die Sprechstunde beginnt um 8 Uhr.", "The consultation hours begin at 8 am."),
        LearnPhrase("Schmerzen lindern", "to relieve pain", "Das Medikament hilft, die Schmerzen zu lindern.", "The medication helps to relieve the pain."),
        LearnPhrase("sich körperlich fit halten", "to keep physically fit", "Regelmäßiger Sport hilft, sich körperlich fit zu halten.", "Regular sport helps to keep physically fit.")
    )
)

val WohnenContent = LearnThemeContent(
    theme = LearnTheme.WOHNEN,
    phrases = listOf(
        LearnPhrase("eine Wohnung mieten / kaufen", "to rent / buy an apartment", "Wir möchten eine 3-Zimmer-Wohnung mieten.", "We'd like to rent a 3-room apartment."),
        LearnPhrase("die Miete erhöhen", "to raise the rent", "Der Vermieter hat die Miete um 10% erhöht.", "The landlord raised the rent by 10%."),
        LearnPhrase("die Kaution", "security deposit", "Die Kaution beträgt normalerweise drei Monatsmieten.", "The deposit is usually three months' rent."),
        LearnPhrase("einziehen / ausziehen", "to move in / move out", "Wir ziehen nächsten Monat in die neue Wohnung ein.", "We're moving into the new apartment next month.", "Trennbare Verben"),
        LearnPhrase("renovieren / sanieren", "to renovate / refurbish", "Das Badezimmer muss dringend renoviert werden.", "The bathroom urgently needs to be renovated."),
        LearnPhrase("die Nebenkosten", "additional costs / utilities", "Die Nebenkosten sind im Mietpreis nicht inbegriffen.", "The utilities are not included in the rent."),
        LearnPhrase("der Mietvertrag", "rental contract", "Bitte lesen Sie den Mietvertrag sorgfältig durch.", "Please read the rental contract carefully."),
        LearnPhrase("der Vermieter / die Vermieterin", "landlord / landlady", "Der Vermieter ist für große Reparaturen zuständig.", "The landlord is responsible for major repairs."),
        LearnPhrase("die Eigentumswohnung", "owner-occupied apartment", "Sie hat eine Eigentumswohnung im Stadtzentrum.", "She has an owner-occupied apartment in the city centre."),
        LearnPhrase("der Grundriss", "floor plan", "Der Grundriss der Wohnung gefällt mir sehr.", "I really like the floor plan of the apartment."),
        LearnPhrase("die Wohnlage", "location / residential area", "Die Wohnlage ist entscheidend für den Mietpreis.", "The location is decisive for the rent price."),
        LearnPhrase("Möbel aufbauen", "to assemble furniture", "Wir haben das ganze Wochenende Möbel aufgebaut.", "We spent the whole weekend assembling furniture."),
        LearnPhrase("der Haushaltsplan", "household budget", "Ein guter Haushaltsplan hilft, Geld zu sparen.", "A good household budget helps save money."),
        LearnPhrase("Klingeln / Briefkasten", "doorbell / mailbox", "Bitte klingeln Sie, wenn Sie ankommen.", "Please ring the bell when you arrive."),
        LearnPhrase("die Hausordnung", "house rules", "Die Hausordnung verbietet laute Musik nach 22 Uhr.", "The house rules prohibit loud music after 10 pm."),
        LearnPhrase("heizen / die Heizung", "to heat / the heating", "Die Heizung funktioniert nicht – es ist sehr kalt.", "The heating isn't working – it's very cold."),
        LearnPhrase("der Keller / der Dachboden", "basement / attic", "Wir haben viele Kartons im Keller gelagert.", "We stored many boxes in the basement."),
        LearnPhrase("die Nachbarschaft", "neighbourhood", "Wir haben eine nette Nachbarschaft.", "We have a nice neighbourhood."),
        LearnPhrase("einen Handwerker rufen", "to call a tradesperson", "Das Rohr ist kaputt – ich muss einen Klempner rufen.", "The pipe is broken – I need to call a plumber."),
        LearnPhrase("gemütlich einrichten", "to furnish cosily", "Wir haben unsere Wohnung sehr gemütlich eingerichtet.", "We have furnished our apartment very cosily.")
    )
)

val EinkaufenContent = LearnThemeContent(
    theme = LearnTheme.EINKAUFEN,
    phrases = listOf(
        LearnPhrase("etwas reklamieren", "to make a complaint about something", "Ich möchte dieses Produkt reklamieren – es ist defekt.", "I'd like to make a complaint about this product – it's defective."),
        LearnPhrase("umtauschen / zurückgeben", "to exchange / return", "Kann ich das Hemd umtauschen? Es ist zu groß.", "Can I exchange the shirt? It's too big."),
        LearnPhrase("die Quittung / der Kassenbon", "receipt", "Heben Sie bitte die Quittung auf.", "Please keep the receipt.", "Wichtig für Reklamation"),
        LearnPhrase("im Angebot sein", "to be on offer / sale", "Diese Jacke ist diese Woche im Angebot.", "This jacket is on offer this week."),
        LearnPhrase("etwas auf Raten kaufen", "to buy something in installments", "Ich kaufe den Kühlschrank auf Raten.", "I'm buying the refrigerator in installments."),
        LearnPhrase("verhandeln", "to bargain / negotiate", "Auf dem Flohmarkt kann man gut verhandeln.", "You can bargain well at the flea market."),
        LearnPhrase("die Zahlungsweise", "payment method", "Welche Zahlungsweisen akzeptieren Sie?", "What payment methods do you accept?"),
        LearnPhrase("auf Qualität achten", "to pay attention to quality", "Man sollte beim Einkauf auf Qualität achten.", "You should pay attention to quality when shopping."),
        LearnPhrase("nachhaltig einkaufen", "to shop sustainably", "Immer mehr Menschen versuchen, nachhaltig einzukaufen.", "More and more people try to shop sustainably."),
        LearnPhrase("vergleichen", "to compare", "Ich vergleiche immer die Preise bevor ich kaufe.", "I always compare prices before buying."),
        LearnPhrase("der Einkaufswagen / -korb", "shopping cart / basket", "Haben Sie noch einen Einkaufswagen?", "Do you still have a shopping cart?"),
        LearnPhrase("die Öffnungszeiten", "opening hours", "Was sind Ihre Öffnungszeiten am Samstag?", "What are your opening hours on Saturday?"),
        LearnPhrase("online bestellen / liefern lassen", "to order online / have delivered", "Ich bestelle fast alles online.", "I order almost everything online."),
        LearnPhrase("ausverkauft sein", "to be sold out", "Die Größe 40 ist leider ausverkauft.", "Unfortunately, size 40 is sold out."),
        LearnPhrase("die Garantie", "guarantee / warranty", "Das Produkt hat zwei Jahre Garantie.", "The product has a two-year guarantee."),
        LearnPhrase("reduziert / rabattiert", "reduced / discounted", "Der Mantel ist um 30% reduziert.", "The coat is reduced by 30%."),
        LearnPhrase("der Bon / der Coupon", "voucher / coupon", "Ich habe einen Coupon für 10% Rabatt.", "I have a coupon for 10% off."),
        LearnPhrase("etwas vorbestellen", "to pre-order something", "Das neue iPhone kann man jetzt vorbestellen.", "You can pre-order the new iPhone now."),
        LearnPhrase("die Lieferzeit", "delivery time", "Die Lieferzeit beträgt 2-3 Werktage.", "The delivery time is 2-3 working days."),
        LearnPhrase("Geld sparen", "to save money", "Mit einem Budget-App kann man leichter Geld sparen.", "With a budget app, you can save money more easily.")
    )
)

val BildungContent = LearnThemeContent(
    theme = LearnTheme.BILDUNG,
    phrases = listOf(
        LearnPhrase("eine Prüfung ablegen / bestehen", "to take / pass an exam", "Ich habe die Prüfung mit Auszeichnung bestanden.", "I passed the exam with distinction.", "ablegen = take; bestehen = pass"),
        LearnPhrase("durchfallen / nicht bestehen", "to fail (an exam)", "Leider bin ich beim Führerschein durchgefallen.", "Unfortunately, I failed my driving license exam.", "Umgangssprachlich für 'nicht bestehen'"),
        LearnPhrase("der Bildungsweg", "educational path", "In Deutschland gibt es viele verschiedene Bildungswege.", "In Germany, there are many different educational paths."),
        LearnPhrase("die Berufsausbildung / Lehre", "vocational training / apprenticeship", "Er macht eine Berufsausbildung als Elektriker.", "He is doing vocational training as an electrician.", "Duales Ausbildungssystem = typisch deutsch"),
        LearnPhrase("studieren / ein Studium absolvieren", "to study / to complete a degree", "Sie hat Medizin an der Uni Wien studiert.", "She studied medicine at the University of Vienna."),
        LearnPhrase("das Stipendium", "scholarship / grant", "Er hat ein Stipendium für sein Masterstudium bekommen.", "He received a scholarship for his Master's degree."),
        LearnPhrase("nachsitzen / nachlernen", "to have detention / to restudy", "Der Schüler muss nachsitzen.", "The student has to stay after school."),
        LearnPhrase("die Hausaufgaben machen", "to do homework", "Jeden Abend mache ich zwei Stunden Hausaufgaben.", "Every evening I do two hours of homework."),
        LearnPhrase("sich auf eine Prüfung vorbereiten", "to prepare for an exam", "Ich bereite mich seit Wochen auf die B1-Prüfung vor.", "I've been preparing for the B1 exam for weeks."),
        LearnPhrase("einen Kurs belegen", "to enroll in a course", "Ich habe einen Online-Kurs für Grafikdesign belegt.", "I enrolled in an online course for graphic design."),
        LearnPhrase("der Lehrplan / Stundenplan", "curriculum / timetable", "Der neue Lehrplan umfasst auch Programmierung.", "The new curriculum also includes programming."),
        LearnPhrase("die Weiterbildung", "continuing education", "Weiterbildung ist im modernen Berufsleben unerlässlich.", "Continuing education is essential in modern professional life."),
        LearnPhrase("auswendig lernen", "to learn by heart", "Man muss viele Vokabeln auswendig lernen.", "You have to learn many vocabulary words by heart."),
        LearnPhrase("eine Seminararbeit schreiben", "to write a seminar paper", "Ich muss bis Ende des Semesters eine Seminararbeit abgeben.", "I have to submit a seminar paper by the end of the semester."),
        LearnPhrase("eine Fremdsprache erwerben", "to acquire a foreign language", "Kinder erwerben Fremdsprachen sehr schnell.", "Children acquire foreign languages very quickly."),
        LearnPhrase("das Abschlusszeugnis", "leaving certificate / diploma", "Mit einem guten Abschlusszeugnis findet man leichter Arbeit.", "With a good leaving certificate, it's easier to find work."),
        LearnPhrase("Noten / Schulnoten", "grades", "In Deutschland sind die Noten von 1 (sehr gut) bis 6 (ungenügend).", "In Germany, grades range from 1 (very good) to 6 (unsatisfactory)."),
        LearnPhrase("die Schulpflicht", "compulsory schooling", "In Deutschland gilt eine neunjährige Schulpflicht.", "In Germany, there is a nine-year compulsory schooling requirement."),
        LearnPhrase("die Elternsprechtag", "parent-teacher conference", "Der Lehrersprechtag ist nächsten Donnerstag.", "The parent-teacher conference is next Thursday."),
        LearnPhrase("kritisch denken / hinterfragen", "to think critically / to question", "Im Studium lernt man, kritisch zu denken.", "At university, you learn to think critically.")
    )
)

val UmweltContent = LearnThemeContent(
    theme = LearnTheme.UMWELT,
    phrases = listOf(
        LearnPhrase("der Klimawandel", "climate change", "Der Klimawandel ist eine der größten Herausforderungen unserer Zeit.", "Climate change is one of the greatest challenges of our time."),
        LearnPhrase("die erneuerbare Energie", "renewable energy", "Deutschland investiert stark in erneuerbare Energien.", "Germany invests heavily in renewable energies."),
        LearnPhrase("den Müll trennen", "to separate waste", "In Deutschland muss man Müll in verschiedene Tonnen trennen.", "In Germany, you have to separate waste into different bins.", "Sehr wichtig in Deutschland"),
        LearnPhrase("recyceln / wiederverwerten", "to recycle / reuse", "Altpapier und Glas sollte man immer recyceln.", "You should always recycle waste paper and glass."),
        LearnPhrase("den CO₂-Ausstoß reduzieren", "to reduce CO₂ emissions", "Jeder kann helfen, seinen CO₂-Ausstoß zu reduzieren.", "Everyone can help to reduce their CO₂ emissions."),
        LearnPhrase("nachhaltig handeln", "to act sustainably", "Nachhaltig handeln bedeutet, an die Zukunft zu denken.", "Acting sustainably means thinking about the future."),
        LearnPhrase("die Umweltverschmutzung", "environmental pollution", "Umweltverschmutzung schadet Tieren und Menschen.", "Environmental pollution harms animals and people."),
        LearnPhrase("biologische Vielfalt / Biodiversität", "biodiversity", "Der Schutz der Biodiversität ist lebenswichtig.", "Protecting biodiversity is vital."),
        LearnPhrase("das Treibhausgas", "greenhouse gas", "CO₂ ist das wichtigste Treibhausgas.", "CO₂ is the most important greenhouse gas."),
        LearnPhrase("Energie sparen", "to save energy", "Lichter ausschalten hilft, Energie zu sparen.", "Turning off lights helps save energy."),
        LearnPhrase("der Naturschutz", "nature conservation", "Der Naturschutz liegt mir sehr am Herzen.", "Nature conservation is very close to my heart."),
        LearnPhrase("die Erderwärmung", "global warming", "Die Erderwärmung führt zu extremen Wetterereignissen.", "Global warming leads to extreme weather events."),
        LearnPhrase("ein Bewusstsein entwickeln (für)", "to develop an awareness (of)", "Man muss ein Bewusstsein für Umweltprobleme entwickeln.", "You need to develop an awareness of environmental problems."),
        LearnPhrase("kompostieren", "to compost", "Küchenabfälle kann man gut kompostieren.", "Kitchen waste can be composted well."),
        LearnPhrase("der ökologische Fußabdruck", "ecological footprint", "Jeder sollte seinen ökologischen Fußabdruck kennen.", "Everyone should know their ecological footprint."),
        LearnPhrase("mit dem Fahrrad statt Auto fahren", "to cycle instead of driving", "Mit dem Fahrrad statt Auto zu fahren spart CO₂.", "Cycling instead of driving saves CO₂."),
        LearnPhrase("der Artenschutz", "species protection", "Artenschutz ist ein internationales Anliegen.", "Species protection is an international concern."),
        LearnPhrase("Plastik vermeiden", "to avoid plastic", "Immer mehr Menschen versuchen, Plastik zu vermeiden.", "More and more people try to avoid plastic."),
        LearnPhrase("die Solarenergie nutzen", "to use solar energy", "Auf dem Dach haben wir Solarpanele, die Solarenergie nutzen.", "On the roof, we have solar panels that use solar energy."),
        LearnPhrase("umweltbewusst leben", "to live in an environmentally conscious way", "Umweltbewusst zu leben ist ein Lebensstil.", "Living in an environmentally conscious way is a lifestyle.")
    )
)

val EssenContent = LearnThemeContent(
    theme = LearnTheme.ESSEN,
    phrases = listOf(
        LearnPhrase("die Speisekarte / Menükarte", "menu", "Könnten Sie mir bitte die Speisekarte bringen?", "Could you please bring me the menu?"),
        LearnPhrase("einen Tisch reservieren", "to reserve a table", "Ich möchte einen Tisch für vier Personen reservieren.", "I'd like to reserve a table for four people."),
        LearnPhrase("bestellen / die Bestellung aufgeben", "to order", "Darf ich bitte bestellen?", "May I order, please?"),
        LearnPhrase("das Gericht / das Hauptgericht", "dish / main course", "Das Hauptgericht des Tages ist Wiener Schnitzel.", "Today's main course is Wiener Schnitzel."),
        LearnPhrase("vegetarisch / vegan", "vegetarian / vegan", "Haben Sie auch vegetarische Gerichte?", "Do you also have vegetarian dishes?"),
        LearnPhrase("die Zutaten", "ingredients", "Was sind die Zutaten in diesem Gericht?", "What are the ingredients in this dish?"),
        LearnPhrase("kochen / braten / backen", "to cook / to fry / to bake", "Ich backe jeden Sonntag Brot.", "I bake bread every Sunday."),
        LearnPhrase("schmecken (nach)", "to taste (of)", "Das schmeckt nach Zimt und Vanille.", "That tastes of cinnamon and vanilla."),
        LearnPhrase("das Rezept", "recipe", "Kannst du mir das Rezept für diesen Kuchen geben?", "Can you give me the recipe for this cake?"),
        LearnPhrase("die Ernährung", "diet / nutrition", "Eine ausgewogene Ernährung ist sehr wichtig.", "A balanced diet is very important."),
        LearnPhrase("die Allergie / unverträglich", "allergy / intolerant", "Ich bin laktoseunverträglich.", "I am lactose intolerant."),
        LearnPhrase("frisch / bio / regional", "fresh / organic / regional", "Ich kaufe am liebsten frische, regionale Produkte.", "I prefer to buy fresh, regional products."),
        LearnPhrase("die Nachspeise / das Dessert", "dessert", "Als Nachspeise nehme ich Apfelstrudel.", "For dessert, I'll have apple strudel."),
        LearnPhrase("satt sein / Hunger haben", "to be full / to be hungry", "Ich bin schon satt, danke!", "I'm already full, thank you!"),
        LearnPhrase("die Rechnung bitte", "the bill, please", "Entschuldigung, könnten wir bitte die Rechnung haben?", "Excuse me, could we have the bill please?"),
        LearnPhrase("das Trinkgeld geben", "to give a tip", "In Deutschland ist Trinkgeld üblich, aber nicht Pflicht.", "In Germany, tipping is customary but not obligatory."),
        LearnPhrase("Lebensmittel einkaufen", "to buy groceries", "Ich muss noch schnell Lebensmittel einkaufen.", "I still need to quickly buy groceries."),
        LearnPhrase("der Geschmack", "taste / flavor", "Dieser Käse hat einen intensiven Geschmack.", "This cheese has an intense flavor."),
        LearnPhrase("das Frühstück / Mittagessen / Abendessen", "breakfast / lunch / dinner", "In Deutschland ist das Abendessen oft kalt.", "In Germany, dinner is often cold."),
        LearnPhrase("kochen lernen", "to learn to cook", "Ich möchte einen Kochkurs machen, um besser kochen zu lernen.", "I'd like to take a cooking class to learn to cook better.")
    )
)

// ─── NEW: Schreiben B1 Themes ─────────────────────────────────────────────────

// Part 1: Beschreibender Text
val SchreibenTeil1Content = LearnThemeContent(
    theme = LearnTheme.SCHREIBEN_TEIL1,
    phrases = listOf(
        // Template
        LearnPhrase(
            german = "📌 TEMPLATE: Beschreibender Text",
            english = "Template for descriptive texts",
            exampleSentence = """
                **Einleitung:** Anrede + wann/wo/mit wem das Erlebnis stattfand
                **Hauptteil:** Was passiert ist — 2 konkrete Details
                **Höhepunkt:** „Besonders ... hat mich ...“ → das schönste Moment
                **Gefühle:** „Ich habe mich dabei ... gefühlt“
                **Schluss:** Empfehlung oder ob man es wiederholen würde + Gruß
            """.trimIndent(),
            exampleTranslation = "Use this structure for all Part 1 tasks.",
            usageTip = "Achte auf eine klare zeitliche Reihenfolge und persönliche Gefühle."
        ),
        // Example 1.1
        LearnPhrase(
            german = "1.1 Kino Film gesehen",
            english = "Write to a friend about a movie you saw.",
            exampleSentence = """
                Liebe Julia,
                ich möchte dir von einem tollen Kinoabend erzählen, den ich gestern mit meiner Schwester erlebt habe.
                Wir haben den Film "Das Lehrerzimmer" gesehen – ein spannendes deutsches Drama über eine junge Lehrerin, die einen Diebstahl in ihrer Schule aufdecken will. Die Geschichte war sehr realistisch und die Hauptdarstellerin hat ihre Rolle fantastisch gespielt.
                Besonders beeindruckt hat mich das Ende des Films, das sehr überraschend und nachdenklich war.
                Ich habe mich dabei bestens unterhalten und gleichzeitig sehr berührt gefühlt.
                Ich würde den Film unbedingt empfehlen – vielleicht gehen wir nächste Woche gemeinsam hin?
                Liebe Grüße, Mia
            """.trimIndent(),
            exampleTranslation = "Keywords: spannend, realistisch, beeindruckt, berührt, empfehlen",
            usageTip = "Nutze Adjektive wie 'spannend', 'realistisch', 'berührend'."
        ),
        // Example 1.2
        LearnPhrase(
            german = "1.2 Flohmarkt",
            english = "Write a blog post about a flea market visit.",
            exampleSentence = """
                Hallo zusammen!
                ich möchte euch von einem tollen Erlebnis erzählen, das ich letzten Samstag auf dem Flohmarkt in der Innenstadt erlebt habe.
                Mit meiner Freundin habe ich stundenlang zwischen den Ständen gestöbert und viele interessante Dinge entdeckt: alte Bücher, bunte Vasen und originelle Kleidung. Für nur fünf Euro habe ich eine alte Schreibmaschine gekauft – ein echtes Schnäppchen!
                Besonders schön war die Atmosphäre: überall duftete es nach frischem Kaffee und alle Leute waren sehr freundlich.
                Ich habe mich dabei entspannt, fröhlich und ein bisschen nostalgisch gefühlt.
                Ein Flohmarktbesuch ist wirklich empfehlenswert – man findet immer etwas Besonderes!
                Euer Blog-Team
            """.trimIndent(),
            exampleTranslation = "Keywords: stöbern, Schnäppchen, Atmosphäre, nostalgisch, empfehlenswert",
            usageTip = "Beschreibe die Stimmung und besondere Funde."
        ),
        // Example 1.3
        LearnPhrase(
            german = "1.3 Kochkurs",
            english = "Write to an acquaintance about a cooking course.",
            exampleSentence = """
                Liebe Sandra,
                ich wollte dir von einem Kochkurs erzählen, den ich letzte Woche in der Volkshochschule besucht habe.
                Gemeinsam mit zehn anderen Teilnehmern habe ich ein italienisches Menü zubereitet: selbst gemachte Pasta, eine leckere Tomaten-Basilikum-Soße und zum Abschluss Tiramisu. Die Kursleiterin hat alles sehr geduldig erklärt und uns viele nützliche Tipps gegeben.
                Besonders begeistert hat mich, wie viel besser die selbst gemachte Pasta im Vergleich zur Variante aus dem Supermarkt schmeckt.
                Ich habe mich dabei sehr wohl und gleichzeitig kreativ und inspiriert gefühlt.
                Ich würde den Kurs auf jeden Fall wiederholen und kann ihn dir sehr empfehlen!
                Liebe Grüße, Anna
            """.trimIndent(),
            exampleTranslation = "Keywords: zubereiten, geduldig, Tipps, inspiriert, empfehlen",
            usageTip = "Vergleiche mit Alltagserfahrungen (z.B. Supermarkt)."
        ),
        // Example 1.4
        LearnPhrase(
            german = "1.4 Rundfahrt",
            english = "Write to family about a bus/boat tour.",
            exampleSentence = """
                Liebe Familie,
                ich möchte euch von einer wunderschönen Schiffsrundfahrt erzählen, die ich vorgestern auf dem Bodensee gemacht habe.
                Die zweistündige Fahrt führte uns an mehreren kleinen Städten und malerischen Uferdörfern vorbei. Ein freundlicher Kommentator hat uns die Geschichte der Region sehr interessant erklärt.
                Besonders atemberaubend war die Aussicht auf die schneebedeckten Alpen im Hintergrund – das war wirklich unvergesslich.
                Ich habe mich dabei vollkommen entspannt und sehr glücklich gefühlt.
                Diese Erfahrung möchte ich unbedingt mit euch gemeinsam wiederholen – es lohnt sich sehr!
                Viele Grüße, Thomas
            """.trimIndent(),
            exampleTranslation = "Keywords: malerisch, atemberaubend, unvergesslich, entspannt, es lohnt sich",
            usageTip = "Verwende starke Adjektive wie 'atemraubend', 'unvergesslich'."
        ),
        // Example 1.5
        LearnPhrase(
            german = "1.5 Klassentreffen",
            english = "Write to a former classmate about a reunion.",
            exampleSentence = """
                Lieber Marco,
                ich wollte dir von unserem Klassentreffen erzählen, das ich letztes Wochenende nach zehn Jahren endlich erlebt habe.
                Fast alle aus unserer alten Klasse sind in einem gemütlichen Restaurant in der Altstadt zusammengekommen. Wir haben alte Fotos angeschaut, gemeinsam gelacht und uns an viele gemeinsame Abenteuer erinnert.
                Besonders lustig war der Moment, als jemand ein altes Schulfoto mitgebracht hat – alle haben sich kaum wiedererkannt!
                Ich habe mich dabei warm, nostalgisch und sehr glücklich gefühlt.
                Es war ein wirklich unvergesslicher Abend – schade, dass du leider nicht dabei sein konntest!
                Liebe Grüße, Felix
            """.trimIndent(),
            exampleTranslation = "Keywords: zusammenkommen, sich erinnern, kaum wiedererkennen, nostalgisch, unvergesslich",
            usageTip = "Betone gemeinsame Erinnerungen und Emotionen."
        ),
        // Example 1.6
        LearnPhrase(
            german = "1.6 Geschäft eröffnen",
            english = "Write a blog post about opening a shop.",
            exampleSentence = """
                Hallo zusammen!
                ich möchte euch von einem aufregenden Tag berichten – der Eröffnung meines kleinen Buchladens letzte Woche.
                Die Vorbereitungen haben Monate gedauert: Regale aufbauen, Bücher sortieren, Flyer verteilen. Am Eröffnungstag kamen viele Gäste – Freunde, Nachbarn und sogar Fremde aus der ganzen Stadt.
                Besonders berührt hat mich, wie begeistert alle reagiert haben und wie schnell die ersten Bücher verkauft wurden.
                Ich habe mich dabei sehr aufgeregt, aber auch unglaublich stolz und glücklich gefühlt.
                Ein Traum ist endlich wahr geworden – ich freue mich auf alle, die meinen Laden besuchen möchten!
                Euer Blog
            """.trimIndent(),
            exampleTranslation = "Keywords: aufregend, Vorbereitung, begeistert, stolz, ein Traum wird wahr",
            usageTip = "Beschreibe die Vorbereitung und die Gefühle am großen Tag."
        ),
        // Example 1.7
        LearnPhrase(
            german = "1.7 Neue Wohnung",
            english = "Write to a friend about moving to a new apartment.",
            exampleSentence = """
                Liebe Sophia,
                ich freue mich, dir endlich von meiner neuen Wohnung erzählen zu können – der Umzug letzten Freitag ist gut gelungen!
                Das Tragen der schweren Kartons und Möbel über die Treppen war anstrengend, aber jetzt ist alles geschafft. Die Wohnung ist hell und geräumig mit einem wunderschönen Blick auf den Stadtpark.
                Besonders gefällt mir die große, moderne Küche – endlich habe ich genug Platz zum richtigen Kochen!
                Ich habe mich hier sofort wohl und zuhause gefühlt.
                Ein kleines Problem gibt es noch: Die Heizung macht manchmal Geräusche, aber der Vermieter kümmert sich darum. Du musst mich bald besuchen!
                Liebe Grüße, Marie
            """.trimIndent(),
            exampleTranslation = "Keywords: anstrengend, geräumig, Blick auf, wohl fühlen, sich kümmern",
            usageTip = "Erwähne sowohl positive Aspekte als auch kleine Probleme."
        ),
        // Example 1.8
        LearnPhrase(
            german = "1.8 Schulbesichtigung mit Kind",
            english = "Write to a relative about visiting a school with your child.",
            exampleSentence = """
                Liebe Tante Renate,
                ich möchte dir von der Schulbesichtigung erzählen, die ich gestern mit meinem Sohn Leon in unserem neuen Viertel erlebt habe.
                Wir haben eine moderne Grundschule besucht: helle Klassenzimmer, einen großen Spielplatz und eine sehr freundliche Direktorin. Die Lehrerin hat sich viel Zeit für unsere Fragen genommen.
                Besonders schön war der Moment, als Leon sofort andere Kinder kennenlernte und strahlend fragte: "Kann ich hier anfangen?"
                Ich habe mich dabei sehr erleichtert und optimistisch gefühlt.
                Wir haben entschieden, dass Leon im September in diese Schule gehen wird – ich bin sicher, es ist die richtige Wahl!
                Liebe Grüße, Katja
            """.trimIndent(),
            exampleTranslation = "Keywords: modern, freundlich, erleichtert, optimistisch, die richtige Wahl",
            usageTip = "Zeige die Perspektive des Kindes und deine eigenen Gefühle."
        ),
        // Example 1.9
        LearnPhrase(
            german = "1.9 Reise gemacht",
            english = "Write a blog post about a trip.",
            exampleSentence = """
                Hallo zusammen!
                ich möchte euch von meiner unvergesslichen Reise nach Portugal erzählen, die ich letzte Woche mit meinem Partner unternommen habe.
                Wir haben Lissabon und die Algarve besucht: Wir sind durch bunte Gassen geschlendert, haben frischen Fisch gegessen und im türkisfarbenen Meer geschwommen.
                Besonders begeistert hat mich der Sonnenuntergang am Cabo da Roca – dem westlichsten Punkt Europas. Das war wirklich atemberaubend!
                Ich habe mich dabei frei, glücklich und vollkommen entspannt gefühlt.
                Portugal ist wunderschön und ich würde auf jeden Fall wieder dorthin reisen – absolut empfehlenswert!
                Euer Blog
            """.trimIndent(),
            exampleTranslation = "Keywords: schlendern, türkisfarben, Sonnenuntergang, atemberaubend, absolut empfehlenswert",
            usageTip = "Nutze bildhafte Beschreibungen der Orte und Aktivitäten."
        ),
        // Example 1.10
        LearnPhrase(
            german = "1.10 Museum besuchen",
            english = "Tell a friend about a museum visit.",
            exampleSentence = """
                Lieber David,
                ich wollte dir von einem besonderen Museumsbesuch erzählen, den ich gestern zum ersten Mal erlebt habe.
                Ich war im Kunsthistorischen Museum in Wien: riesige Räume voller Gemälde, antiker Skulpturen und historischer Objekte. Eine freundliche Führerin hat uns alles sehr verständlich erklärt.
                Besonders fasziniert hat mich ein Gemälde von Rembrandt – die Maltechnik und die Geschichte dahinter waren einfach unglaublich.
                Ich habe mich dabei neugierig und sehr begeistert gefühlt – und viel Neues gelernt!
                Ein Museumsbesuch bildet wirklich und macht Spaß zugleich – ich empfehle es jedem!
                Liebe Grüße, Lena
            """.trimIndent(),
            exampleTranslation = "Keywords: Skulpturen, Führerin, fasziniert, Maltechnik, bilden und Spaß machen",
            usageTip = "Beschreibe konkrete Exponate und deine Reaktion darauf."
        )
    )
)

// Part 2: Meinungsäußerung
val SchreibenTeil2Content = LearnThemeContent(
    theme = LearnTheme.SCHREIBEN_TEIL2,
    phrases = listOf(
        // Template
        LearnPhrase(
            german = "📌 TEMPLATE: Meinungsäußerung",
            english = "Template for argumentative texts",
            exampleSentence = """
                **Position:** „Zu diesem Thema habe ich eine klare Meinung: Ich bin [dafür/dagegen], weil ...“
                **Argument 1:** „Einerseits ...“ → erstes Hauptargument + Alltagsbeispiel
                **Argument 2:** „Außerdem / Darüber hinaus ...“ → zweites Argument verstärken
                **Einschränkung:** „Natürlich / Zwar ..., aber ...“ → Gegenargument kurz anerkennen
                **Fazit:** „Meiner Meinung nach ...“ → eigene Schlussfolgerung / Appell
            """.trimIndent(),
            exampleTranslation = "Use this structure for Part 2.",
            usageTip = "Achte auf klare Argumente und ein erkennbares Fazit."
        ),
        // Example 2.1
        LearnPhrase(
            german = "2.1 Ungesundes Essen am Arbeitsplatz",
            english = "Opinion: Healthy vs. unhealthy food at work.",
            exampleSentence = """
                Zu diesem Thema habe ich eine klare Meinung: Ich bin dagegen, dass Menschen regelmäßig ungesund am Arbeitsplatz essen.
                Einerseits schadet ungesunde Ernährung auf Dauer der Gesundheit und der Konzentration – wer viel Zucker und Fett isst, fühlt sich nachmittags müde und unproduktiv.
                Außerdem kostet Fast Food langfristig mehr Geld als selbst mitgebrachte Mahlzeiten – ein Beispiel aus meinem Alltag zeigt das deutlich.
                Natürlich verstehe ich, dass Berufstätige oft wenig Zeit haben und Fast Food sehr praktisch ist.
                Meiner Meinung nach sollten Arbeitgeber gesunde Optionen in der Kantine anbieten und Mitarbeiter zu bewusster Ernährung motivieren.
            """.trimIndent(),
            exampleTranslation = "Keywords: Konzentration, unproduktiv, Mahlzeit, bewusste Ernährung, motivieren",
            usageTip = "Nenne konkrete Beispiele aus deinem Alltag."
        ),
        // Example 2.2
        LearnPhrase(
            german = "2.2 Umweltschutz",
            english = "Opinion: What can we do for environmental protection?",
            exampleSentence = """
                Zu diesem Thema habe ich eine klare Meinung: Ich bin überzeugt, dass Umweltschutz eine absolute Notwendigkeit ist.
                Einerseits sehen wir täglich die Folgen des Klimawandels – extremes Wetter, schmelzende Gletscher und das Aussterben vieler Tierarten.
                Außerdem ist die Umwelt das Erbe künftiger Generationen: Was wir heute zerstören, können unsere Kinder nicht zurückbekommen.
                Natürlich reichen individuelle Maßnahmen allein nicht aus, um das Problem vollständig zu lösen.
                Meiner Meinung nach brauchen wir sowohl persönliches Engagement – Fahrrad fahren, weniger Plastik – als auch strenge internationale Gesetze.
            """.trimIndent(),
            exampleTranslation = "Keywords: überzeugt, Notwendigkeit, Klimawandel, Erbe, Engagement",
            usageTip = "Verbinde persönliche Maßnahmen mit politischen Forderungen."
        ),
        // Example 2.3
        LearnPhrase(
            german = "2.3 Fernseher im Kinderzimmer",
            english = "Opinion: Should kids have a TV in their room?",
            exampleSentence = """
                Zu diesem Thema habe ich eine klare Meinung: Ich bin dagegen, dass Kinder einen Fernseher im eigenen Zimmer haben.
                Einerseits können Eltern den Medienkonsum nicht mehr kontrollieren – das Kind schaut dann alleine und ohne Aufsicht zu lange oder die falschen Inhalte.
                Außerdem leiden Schlaf und Schulleistung: Kinder, die abends lange fernsehen, sind am nächsten Tag müde und unkonzentriert.
                Natürlich dürfen Kinder auch fernsehen – das ist völlig in Ordnung.
                Meiner Meinung nach sollte das aber immer im gemeinsamen Wohnzimmer stattfinden, damit die Familie zusammen Zeit verbringt.
            """.trimIndent(),
            exampleTranslation = "Keywords: Medienkonsum, Aufsicht, Schulleistung, unkonzentriert, gemeinsam",
            usageTip = "Betone die Rolle der Familie."
        ),
        // Example 2.4
        LearnPhrase(
            german = "2.4 Einkaufen im Internet",
            english = "Opinion: Advantages of online shopping.",
            exampleSentence = """
                Zu diesem Thema habe ich eine klare Meinung: Ich finde, dass die Vorteile des Online-Shoppings überwiegen.
                Einerseits ist es sehr bequem – man kann rund um die Uhr einkaufen, ohne das Haus zu verlassen, und Preise mühelos vergleichen.
                Außerdem ist die Auswahl im Internet viel größer als in lokalen Geschäften – ich kaufe zum Beispiel Bücher und Elektronik fast immer online.
                Natürlich gibt es auch Risiken wie Betrug, schlechte Qualität oder umständliche Rücksendungen.
                Meiner Meinung nach sollte man Online-Shopping bewusst einsetzen und dabei trotzdem lokale Geschäfte regelmäßig unterstützen.
            """.trimIndent(),
            exampleTranslation = "Keywords: überwiegen, mühelos, Auswahl, Betrug, bewusst einsetzen",
            usageTip = "Erwähne sowohl Vor- als auch Nachteile, bevor du ein Fazit ziehst."
        ),
        // Example 2.5
        LearnPhrase(
            german = "2.5 Führerschein ab 16 Jahre",
            english = "Opinion: Driving license at age 16?",
            exampleSentence = """
                Zu diesem Thema habe ich eine klare Meinung: Ich bin gegen den Führerschein ab 16 Jahren.
                Einerseits sind 16-Jährige oft noch nicht reif genug für die große Verantwortung im Straßenverkehr – Studien zeigen, dass sehr junge Fahrer häufiger Unfälle verursachen.
                Außerdem wäre die Kfz-Versicherung für Jugendliche sehr teuer und eine zusätzliche finanzielle Belastung für die Familien.
                Natürlich haben junge Menschen in ländlichen Regionen ein echtes Mobilitätsproblem – das ist verständlich.
                Meiner Meinung nach ist das begleitete Fahren ab 17 Jahren ein guter Kompromiss, der Erfahrung und Sicherheit verbindet.
            """.trimIndent(),
            exampleTranslation = "Keywords: Verantwortung, verursachen, finanzielle Belastung, Mobilitätsproblem, Kompromiss",
            usageTip = "Erwähne eine Alternative, um deine Position zu stärken."
        ),
        // Example 2.6
        LearnPhrase(
            german = "2.6 Urlaub am Strand",
            english = "Opinion: Is beach vacation the best?",
            exampleSentence = """
                Zu diesem Thema habe ich eine klare Meinung: Urlaub am Strand ist wunderbar, aber nicht automatisch der beste Urlaub für jeden.
                Einerseits bietet der Strand echte Erholung – Sonne, Meeresluft und keine Verpflichtungen helfen, vollständig abzuschalten.
                Außerdem ist Schwimmen gesund und tut dem Körper gut – ich war letzten Sommer am Mittelmeer und habe mich perfekt erholt.
                Natürlich bevorzugen manche Menschen Berge, Städtereisen oder Abenteuerurlaub, und das ist völlig verständlich.
                Meiner Meinung nach ist es am wichtigsten, den Urlaub zu wählen, der einem persönlich am meisten Freude und Erholung bringt.
            """.trimIndent(),
            exampleTranslation = "Keywords: Erholung, Verpflichtungen, abschalten, bevorzugen, Freude bringen",
            usageTip = "Anerkenne, dass andere Vorlieben ebenso gültig sind."
        ),
        // Example 2.7
        LearnPhrase(
            german = "2.7 Essen in öffentlichen Verkehrsmitteln",
            english = "Opinion: Eating on buses/trains?",
            exampleSentence = """
                Zu diesem Thema habe ich eine klare Meinung: Ich bin dagegen, stark riechende Speisen in öffentlichen Verkehrsmitteln zu essen.
                Einerseits ist der Geruch für andere Fahrgäste sehr unangenehm – besonders Fisch, Zwiebeln oder Fast Food in einem geschlossenen Waggon sind störend.
                Außerdem kann Essen die Sitze und den Boden schmutzig machen, was für alle anderen unangenehm ist.
                Natürlich ist ein kleiner Keks oder eine Flasche Wasser völlig in Ordnung – das stört niemanden.
                Meiner Meinung nach sollte man Rücksicht auf Mitreisende nehmen und intensive Gerüche in gemeinsam genutzten Räumen vermeiden.
            """.trimIndent(),
            exampleTranslation = "Keywords: Fahrgäste, Waggon, störend, Rücksicht nehmen, intensiv",
            usageTip = "Differenziere zwischen störenden und akzeptablen Lebensmitteln."
        ),
        // Example 2.8
        LearnPhrase(
            german = "2.8 Soll man Vegetarier werden",
            english = "Opinion: Should people become vegetarian?",
            exampleSentence = """
                Zu diesem Thema habe ich eine klare Meinung: Ich denke, dass mehr Menschen vegetarisch essen sollten.
                Einerseits ist eine pflanzenbasierte Ernährung oft gesünder – weniger gesättigte Fette bedeuten ein geringeres Risiko für Herzerkrankungen.
                Außerdem hat die Fleischproduktion enorme Auswirkungen auf die Umwelt: Sie verbraucht viel Wasser und Energie und erzeugt viele Treibhausgase.
                Natürlich ist es eine persönliche Entscheidung und niemand sollte gezwungen werden, sein Essverhalten zu ändern.
                Meiner Meinung nach wäre es schon ein großer Fortschritt, wenn alle einfach weniger und bewusster Fleisch essen würden.
            """.trimIndent(),
            exampleTranslation = "Keywords: pflanzenbasiert, gesättigte Fette, Herzerkrankungen, Treibhausgase, Essverhalten",
            usageTip = "Schlage einen realistischen Kompromiss vor (z.B. weniger Fleisch)."
        ),
        // Example 2.9
        LearnPhrase(
            german = "2.9 Dürfen Männer weinen",
            english = "Opinion: Should men cry in public?",
            exampleSentence = """
                Zu diesem Thema habe ich eine klare Meinung: Ja, Männer dürfen und sollten weinen dürfen – genauso wie alle anderen Menschen.
                Einerseits ist Weinen eine natürliche menschliche Reaktion auf Trauer, Schmerz oder sogar Freude – das gilt für alle Menschen gleich.
                Außerdem zeigen Studien, dass das langfristige Unterdrücken von Gefühlen zu ernsten psychischen Problemen wie Depressionen führen kann.
                Natürlich gibt es kulturelle Unterschiede, und nicht jeder fühlt sich wohl damit, öffentlich Gefühle zu zeigen.
                Meiner Meinung nach sollten wir alte Rollenbilder über Männlichkeit überwinden und Emotionen bei allen Menschen akzeptieren.
            """.trimIndent(),
            exampleTranslation = "Keywords: natürliche Reaktion, Trauer, unterdrücken, Depressionen, Rollenbilder",
            usageTip = "Verweise auf psychologische Studien oder gesellschaftliche Veränderungen."
        ),
        // Example 2.10
        LearnPhrase(
            german = "2.10 Telefonieren in öffentlichen Verkehrsmitteln",
            english = "Opinion: Loud phone calls on transport?",
            exampleSentence = """
                Zu diesem Thema habe ich eine klare Meinung: Ich finde lautes Telefonieren in öffentlichen Verkehrsmitteln störend und rücksichtslos.
                Einerseits wollen viele Mitfahrer in Ruhe lesen, arbeiten oder entspannen – ein lautes Gespräch macht das unmöglich.
                Außerdem werden dabei oft private Details für alle hörbar erzählt, was peinlich und unangenehm für alle Beteiligten ist.
                Natürlich muss man manchmal dringend telefonieren – das ist verständlich und gehört zum Alltag.
                Meiner Meinung nach sollte man aber leise sprechen, kurze Gespräche führen oder in den Vorraum gehen. Rücksicht ist das Wichtigste!
            """.trimIndent(),
            exampleTranslation = "Keywords: rücksichtslos, Mitfahrer, hörbar, peinlich, in den Vorraum gehen",
            usageTip = "Gib praktische Tipps für rücksichtsvolles Verhalten."
        )
    )
)

// Part 3: Privater oder formeller Brief
val SchreibenTeil3Content = LearnThemeContent(
    theme = LearnTheme.SCHREIBEN_TEIL3,
    phrases = listOf(
        // Template
        LearnPhrase(
            german = "📌 TEMPLATE: Brief / E-Mail",
            english = "Template for letters and emails",
            exampleSentence = """
                **Anrede:** Sehr geehrte/r [Name/Damen und Herren] ODER Liebe/r [Vorname] (informell)
                **Bezug:** „Ich schreibe Ihnen bezüglich [Anzeige/Situation vom ...]“
                **Vorstellung:** Name, Alter, Situation (1 Satz)
                **Anliegen:** 2–3 konkrete Fragen oder Bewerbungsinhalt
                **Abschluss:** „Ich würde mich sehr freuen, von Ihnen zu hören, und bitte um ...“
                **Gruß:** Mit freundlichen Grüßen / Liebe Grüße + Name
            """.trimIndent(),
            exampleTranslation = "Use this structure for Part 3.",
            usageTip = "Achte auf den richtigen Ton (formell vs. informell)."
        ),
        // Example 3.1
        LearnPhrase(
            german = "3.1 Fahrrad-Anzeige",
            english = "Email to seller: Ask about a used bike.",
            exampleSentence = """
                Sehr geehrte/r Verkäufer/in,
                ich schreibe Ihnen bezüglich Ihrer Anzeige für ein gebrauchtes Mountainbike, die ich heute online gesehen habe.
                Das Fahrrad interessiert mich sehr, da ich ein zuverlässiges Rad für meinen täglichen Arbeitsweg suche. Mein Name ist Max Müller, ich bin 29 Jahre alt und auf ein gutes Fahrrad angewiesen.
                Ich würde gerne wissen, wie alt das Fahrrad ist und ob es kürzlich gewartet oder repariert wurde. Außerdem frage ich mich, ob der Preis noch verhandelbar ist.
                Ich würde mich sehr freuen, von Ihnen zu hören, und bitte um einen Besichtigungstermin – am liebsten an einem Wochenende.
                Mit freundlichen Grüßen,
                Max Müller
            """.trimIndent(),
            exampleTranslation = "Keywords: zuverlässig, Arbeitsweg, angewiesen auf, gewartet, verhandelbar",
            usageTip = "Stelle konkrete Fragen, die für dich wichtig sind."
        ),
        // Example 3.2
        LearnPhrase(
            german = "3.2 Entschuldigung",
            english = "Email to friend: Apologize for missed appointment.",
            exampleSentence = """
                Lieber Jonas,
                ich schreibe dir, weil ich mich aufrichtig bei dir entschuldigen möchte: Leider habe ich unseren gemeinsamen Termin am Mittwochabend völlig vergessen.
                Das tut mir sehr leid und ich hoffe wirklich, du nimmst es mir nicht übel. Der Grund war leider ein unerwarteter Notfall bei der Arbeit, der mich den ganzen Tag beschäftigt hat.
                Ich hätte dir aber trotzdem rechtzeitig eine Nachricht schicken sollen – das war nicht in Ordnung von mir.
                Als kleines Zeichen meiner Entschuldigung würde ich dich gerne auf ein Abendessen einladen. Wann hast du nächste Woche Zeit? Donnerstag oder Freitag würde mir gut passen.
                Liebe Grüße,
                Sophia
            """.trimIndent(),
            exampleTranslation = "Keywords: aufrichtig, übel nehmen, unerwarteter Notfall, rechtzeitig, Zeichen der Entschuldigung",
            usageTip = "Nenne den Grund, entschuldige dich und mache einen konkreten Wiedergutmachungsvorschlag."
        ),
        // Example 3.3
        LearnPhrase(
            german = "3.3 Job-Anzeige: Kassierer/in",
            english = "Application email for supermarket cashier job.",
            exampleSentence = """
                Sehr geehrte Damen und Herren,
                ich schreibe Ihnen bezüglich Ihrer Stellenanzeige für eine Aushilfskraft als Kassierer/in in Teilzeit, die ich diese Woche in Ihrer Filiale gesehen habe.
                Mein Name ist Leila Bensalem, ich bin 28 Jahre alt und derzeit auf der Suche nach einer geeigneten Teilzeitstelle. Ich habe bereits ein Jahr Erfahrung als Verkäuferin in einem Lebensmittelgeschäft und arbeite sehr gerne mit Menschen.
                Ich bin zuverlässig, pünktlich und lernbereit. An Wochentagen und am Wochenende stehe ich flexibel zur Verfügung.
                Ich würde mich sehr freuen, von Ihnen zu hören, und bitte um einen Vorstellungstermin, um meine Eignung persönlich vorstellen zu können.
                Mit freundlichen Grüßen,
                Leila Bensalem
            """.trimIndent(),
            exampleTranslation = "Keywords: Stellenanzeige, geeignet, Erfahrung, lernbereit, Eignung",
            usageTip = "Heb deine relevanten Erfahrungen und Soft Skills hervor."
        ),
        // Example 3.4
        LearnPhrase(
            german = "3.4 Englisch-Kurs-Anzeige",
            english = "Email: Register for English course.",
            exampleSentence = """
                Sehr geehrte Damen und Herren,
                ich schreibe Ihnen bezüglich Ihres Englisch-Kurses für Anfänger, den ich in Ihrer aktuellen Anzeige gesehen habe.
                Mein Name ist Amira Hassan, ich bin 34 Jahre alt und möchte Englisch lernen, da ich die Sprache dringend für meinen Beruf benötige. Ich interessiere mich sehr für Ihren Abendkurs.
                Ich würde gerne wissen, ob noch freie Plätze verfügbar sind und wann genau der nächste Kurs beginnt. Außerdem möchte ich fragen, ob eine Ratenzahlung möglich ist.
                Ich würde mich sehr freuen, von Ihnen zu hören, und bitte um baldige Rückmeldung sowie alle notwendigen Informationen zur Anmeldung.
                Mit freundlichen Grüßen,
                Amira Hassan
            """.trimIndent(),
            exampleTranslation = "Keywords: dringend, Abendkurs, verfügbar, Ratenzahlung, Rückmeldung",
            usageTip = "Stelle alle wichtigen Fragen, die du vor der Anmeldung klären musst."
        ),
        // Example 3.5
        LearnPhrase(
            german = "3.5 Schauspieler-Anzeige",
            english = "Application email for theater role.",
            exampleSentence = """
                Sehr geehrte Damen und Herren,
                ich schreibe Ihnen bezüglich Ihrer Anzeige, in der Sie Schauspieler/innen für ein Theaterstück suchen, die ich diese Woche entdeckt habe.
                Mein Name ist Carlos Diaz, ich bin 26 Jahre alt und bewerbe mich mit großer Freude für diese Rolle, da Theater meine größte Leidenschaft ist. Ich habe drei Jahre lang in einer Laientheatergruppe mitgespielt und dabei mehrere Hauptrollen übernommen.
                Außerdem habe ich an einem professionellen Schauspiel-Workshop teilgenommen und bin erfahren im Umgang mit Publikum. Ich bin flexibel und kann an den meisten Abenden für Proben erscheinen.
                Ich würde mich sehr freuen, von Ihnen zu hören, und bitte herzlich um einen Vorsprechtermin, um mein Können zeigen zu können.
                Mit freundlichen Grüßen,
                Carlos Diaz
            """.trimIndent(),
            exampleTranslation = "Keywords: Leidenschaft, Laientheater, Hauptrolle, Vorsprechtermin, Können zeigen",
            usageTip = "Zeige Begeisterung und nenne konkrete Erfahrungen."
        ),
        // Example 3.6
        LearnPhrase(
            german = "3.6 Wohnungs-Anzeige",
            english = "Email to landlord: Request viewing.",
            exampleSentence = """
                Sehr geehrte Damen und Herren,
                ich schreibe Ihnen bezüglich Ihrer Anzeige für eine 2-Zimmer-Wohnung (600 €/Monat), die ich heute auf Ihrem Immobilienportal gesehen habe.
                Mein Name ist Nina Kowalski, ich bin 31 Jahre alt, berufstätig und zuverlässig. Ich suche ab dem 1. März eine neue Unterkunft in zentraler Lage.
                Ich würde gerne wissen, ob die Wohnung noch verfügbar ist und ob Haustiere erlaubt sind. Außerdem bitte ich um Informationen zu den Nebenkosten und der Kautionshöhe.
                Ich würde mich sehr freuen, von Ihnen zu hören, und bitte um einen Besichtigungstermin in der nächsten Woche.
                Mit freundlichen Grüßen,
                Nina Kowalski
            """.trimIndent(),
            exampleTranslation = "Keywords: Immobilienportal, Unterkunft, zentrale Lage, Nebenkosten, Kautionshöhe",
            usageTip = "Frage nach allen wichtigen Details, die du vor der Besichtigung wissen musst."
        )
    )
)

// ─── NEW: Konnektoren ─────────────────────────────────────────────────

val KonjunktionenContent = LearnThemeContent(
    theme = LearnTheme.KONJUNKTIONEN,   // now correctly resolved
    phrases = listOf(
        LearnPhrase(
            german = "und",
            english = "Konjunktion – Addition (and)",
            exampleSentence = "Ich lerne Deutsch **und** Englisch.\n\n→ Verb in Position 2.\n\n*Beispiele:*\n• Ich trinke Wasser und Saft.\n• Er singt und tanzt gern.",
            exampleTranslation = "I learn German and English.",
            usageTip = "Kein Komma vor 'und' bei Aufzählungen. Bei ganzen Sätzen optional."
        ),
        LearnPhrase(
            german = "aber",
            english = "Konjunktion – Gegensatz (but)",
            exampleSentence = "Ich mag Fußball, **aber** ich spiele nicht gern.\n\n→ Verb in Position 2.\n\n*Beispiele:*\n• Sie ist müde, aber sie arbeitet weiter.\n• Das Wetter ist schlecht, aber wir gehen trotzdem raus.",
            exampleTranslation = "I like football, but I don't like playing it.",
            usageTip = "Komma vor 'aber' immer, wenn zwei Hauptsätze verbunden werden. ❌ Nicht mit 'obwohl' kombinieren!"
        ),
        LearnPhrase(
            german = "denn",
            english = "Konjunktion – Grund (because)",
            exampleSentence = "Ich bleibe zu Hause, **denn** ich bin müde.\n\n→ Verb in Position 2 (kein Nebensatz!).\n\n*Beispiele:*\n• Er kommt nicht, denn er ist krank.\n• Wir fahren mit dem Zug, denn das ist billiger.",
            exampleTranslation = "I stay home because I'm tired.",
            usageTip = "Anders als 'weil': Verb bleibt auf Position 2. Kein Komma? Doch, Komma vor 'denn' ist Pflicht."
        ),
        // Add more connectors as needed – here we show a representative sample.
        // From the JSON, all Konjunktionen: und, aber, denn, oder, sondern
        // You can add the remaining ones following the same pattern.
        LearnPhrase(
            german = "oder",
            english = "Konjunktion – Alternative (or)",
            exampleSentence = "Möchtest du Tee **oder** Kaffee?\n\n→ Verb in Position 2.\n\n*Beispiele:*\n• Wir fahren nach Berlin oder nach Hamburg.\n• Kannst du morgen oder übermorgen kommen?",
            exampleTranslation = "Would you like tea or coffee?",
            usageTip = "Kein Komma vor 'oder' bei einfachen Aufzählungen. Bei ganzen Sätzen optional."
        ),
        LearnPhrase(
            german = "sondern",
            english = "Konjunktion – Korrektur (but rather)",
            exampleSentence = "Er kommt nicht mit dem Auto, **sondern** er nimmt den Zug.\n\n→ Verb in Position 2.\n\n*Beispiele:*\n• Sie ist nicht müde, sondern sie hat keine Lust.\n• Das Buch ist nicht neu, sondern gebraucht.",
            exampleTranslation = "He's not coming by car, but rather he's taking the train.",
            usageTip = "Wird verwendet, um eine Verneinung zu korrigieren. Immer mit Komma."
        )
    )
)

val SubjunktionenContent = LearnThemeContent(
    theme = LearnTheme.SUBJUNKTIONEN,
    phrases = listOf(
        LearnPhrase(
            german = "weil",
            english = "Subjunktion – Grund (because)",
            exampleSentence = "Ich lerne, **weil** ich die Prüfung bestehen will.\n\n→ Verb am ENDE des Nebensatzes!\n\n*Beispiele:*\n• Er bleibt zu Hause, weil er krank ist.\n• Wir gehen spazieren, weil die Sonne scheint.",
            exampleTranslation = "I study because I want to pass the exam.",
            usageTip = "❌ Falsch: 'weil ich bin müde' → ✅ 'weil ich müde bin'"
        ),
        LearnPhrase(
            german = "obwohl",
            english = "Subjunktion – Gegensatz (although)",
            exampleSentence = "**Obwohl** es regnet, gehe ich spazieren.\n\n→ Verb am ENDE.\n\n*Beispiele:*\n• Obwohl er krank ist, arbeitet er.\n• Sie kommt, obwohl sie keine Zeit hat.",
            exampleTranslation = "Although it's raining, I'm going for a walk.",
            usageTip = "Nicht mit 'aber' kombinieren! ❌ 'Obwohl es regnet, aber ich gehe...'"
        ),
        LearnPhrase(
            german = "wenn",
            english = "Subjunktion – Bedingung / Wiederholung (if/when)",
            exampleSentence = "**Wenn** du Zeit hast, ruf mich an.\n\n**Wenn** ich müde bin, gehe ich früh schlafen.\n\n→ Verb am ENDE.",
            exampleTranslation = "If you have time, call me. / When I'm tired, I go to bed early.",
            usageTip = "Verwechsle nicht mit 'als': 'wenn' = wiederholt / Gegenwart / Zukunft; 'als' = einmalige Vergangenheit."
        ),
        LearnPhrase(
            german = "als",
            english = "Subjunktion – einmalige Vergangenheit (when)",
            exampleSentence = "**Als** ich Kind war, wohnte ich in Berlin.\n\n→ Verb am ENDE.",
            exampleTranslation = "When I was a child, I lived in Berlin.",
            usageTip = "Nur für ein einzelnes Ereignis in der Vergangenheit. Für wiederholte Handlungen in der Vergangenheit: 'wenn'."
        ),
        LearnPhrase(
            german = "damit",
            english = "Subjunktion – Zweck (so that) – verschiedene Subjekte",
            exampleSentence = "Ich lerne, **damit** ich gute Noten bekomme.\n\n→ Verb am ENDE.\n\n*Beispiele:*\n• Er erklärt es langsam, damit alle verstehen.\n• Wir sparen Geld, damit wir reisen können.",
            exampleTranslation = "I study so that I get good grades.",
            usageTip = "Wenn Hauptsatz‑ und Nebensatz‑Subjekt gleich sind, benutze 'um...zu' + Infinitiv."
        ),
        LearnPhrase(
            german = "nachdem",
            english = "Subjunktion – Vorzeitigkeit (after)",
            exampleSentence = "**Nachdem** ich gegessen hatte, ging ich spazieren.\n\n→ Verb am ENDE. Beachte die Zeitfolge!",
            exampleTranslation = "After I had eaten, I went for a walk.",
            usageTip = "Nachdem + Plusquamperfekt (Vorvergangenheit) im Nebensatz, dann Hauptsatz im Präteritum."
        )
        // Weitere Subjunktionen aus JSON: falls, bevor, während, seitdem, sobald, etc. – analog ergänzen
    )
)

val KonjunktionaladverbienContent = LearnThemeContent(
    theme = LearnTheme.KONJUNKTIONALADVERBIEN,
    phrases = listOf(
        LearnPhrase(
            german = "deshalb",
            english = "Konjunktionaladverb – Folge (therefore)",
            exampleSentence = "Es regnet, **deshalb** bleibe ich zu Hause.\n\n→ Verb direkt NACH dem Adverb (Inversion!).",
            exampleTranslation = "It's raining, therefore I'm staying home.",
            usageTip = "Position 1 im Satz – das Verb folgt sofort. ❌ 'deshalb ich bleibe' → ✅ 'deshalb bleibe ich'"
        ),
        LearnPhrase(
            german = "trotzdem",
            english = "Konjunktionaladverb – Gegensatz (nevertheless)",
            exampleSentence = "Es ist kalt, **trotzdem** gehe ich raus.\n\n→ Inversion!",
            exampleTranslation = "It's cold, nevertheless I'm going out.",
            usageTip = "Ähnlich wie 'obwohl', aber mit Inversion. Synonym: 'dennoch'."
        ),
        LearnPhrase(
            german = "außerdem",
            english = "Konjunktionaladverb – Addition (furthermore)",
            exampleSentence = "Er ist intelligent, **außerdem** ist er sehr fleißig.\n\n→ Inversion!",
            exampleTranslation = "He is intelligent, furthermore he's very diligent.",
            usageTip = "Wird oft verwendet, um ein Argument zu verstärken."
        ),
        LearnPhrase(
            german = "sonst",
            english = "Konjunktionaladverb – Alternative (otherwise)",
            exampleSentence = "Beeil dich, **sonst** verpassen wir den Zug.\n\n→ Inversion!",
            exampleTranslation = "Hurry up, otherwise we'll miss the train.",
            usageTip = "Drohende Konsequenz. Steht meist im zweiten Satz."
        )
        // Weitere: daher, darum, dennoch, folglich, etc.
    )
)

// ============================================================================
// GRAMMAR TOPICS (full list)
// ============================================================================

val PerfektContent = LearnThemeContent(
    theme = LearnTheme.GRAM_PERFEKT,
    phrases = listOf(LearnPhrase("Perfekt", "Present Perfect", """
        **Bildung:** haben/sein + Partizip II
        • Regelmäßig: ge‑ + Stamm + ‑t (gemacht)
        • Unregelmäßig: ge‑ + Stamm (geändert) + ‑en (gesehen)
        • Hilfsverb: 'haben' (meiste Verben), 'sein' (Bewegung, Zustandswechsel)
        
        **Wortstellung:** Hilfsverb Position 2, Partizip am Ende.
        → Ich habe gestern einen Film gesehen.
        
        **Beispiele:**
        • Ich bin nach Berlin gefahren. (Bewegung)
        • Wir haben uns getroffen. (reflexiv)
        
        **Häufige Fehler:**
        ❌ Ich habe gesehen den Film. → ✅ Ich habe den Film gesehen.
        ❌ Ich bin gegessen. → ✅ Ich habe gegessen.
        
        **Prüfungstipps:** In B1‑Sprechen bevorzugt; 15 häufige unregelmäßige Partizipien lernen.
    """.trimIndent())))

val PraeteritumContent = LearnThemeContent(
    theme = LearnTheme.GRAM_PRAETERITUM,
    phrases = listOf(LearnPhrase("Präteritum", "Simple Past", """
        **Bildung:** Regelmäßig: Stamm + -te + Endung (ich lernte). Unregelmäßig: Stammwechsel (ich ging).
        **Verwendung:** Schriftliche Erzählungen; Modalverben, sein, haben auch in gesprochener Sprache.
        **Beispiele:** Ich lernte Deutsch, als ich in Deutschland wohnte. Er konnte nicht kommen.
        **Häufige Fehler:** Übermäßiger Gebrauch in Sprache; falscher Stammwechsel.
        **Prüfungstipps:** In Schreibaufgaben Präteritum für Hintergrund, Perfekt für Hauptereignisse.
    """.trimIndent())))

val ModalverbenContent = LearnThemeContent(
    theme = LearnTheme.GRAM_MODALVERBEN,
    phrases = listOf(LearnPhrase("Modalverben", "können, müssen, ...", """
        **Präsens:** kann, musst, will, soll, darf, mag – Infinitiv am Ende.
        **Perfekt:** haben + Infinitiv + Infinitiv (Ich habe kommen können).
        **Bedeutungen:** Fähigkeit, Notwendigkeit, Wille, Empfehlung, Erlaubnis, Vorliebe.
        **Häufige Fehler:** 'zu' nach Modalverb (❌ muss zu gehen); falsches Perfekt (❌ habe gekonnt).
        **Prüfungstipps:** Modalverben für Meinungsäußerung; Präteritum (konnte, musste) für Texte.
    """.trimIndent())))

val PassivContent = LearnThemeContent(
    theme = LearnTheme.GRAM_PASSIV,
    phrases = listOf(LearnPhrase("Passiv (Vorgangspassiv)", "werden + Partizip II", """
        **Bildung:** werden (konjugiert) + Partizip II
        • Präsens: Das Buch wird gelesen.
        • Präteritum: Das Haus wurde 1990 gebaut.
        • Perfekt: ist ... worden (nicht geworden!)
        **Agent:** von + Dativ (Person), durch + Akkusativ (Mittel)
        **Häufige Fehler:** 'geworden' im Perfekt; Passiv bei intransitiven Verben.
        **Prüfungstipps:** Für Prozesse, Regeln, formale Texte; Aktiv ↔ Passiv üben.
    """.trimIndent())))

val KonjunktivIIContent = LearnThemeContent(
    theme = LearnTheme.GRAM_KONJUNKTIV_II,
    phrases = listOf(LearnPhrase("Konjunktiv II", "würde, hätte, wäre", """
        **Bildung:** würde + Infinitiv (meist); unregelmäßig: hätte, wäre, könnte, müsste, sollte, dürfte, wollte.
        **Verwendung:** Höfliche Bitten, irreale Bedingungen, Wünsche.
        **Beispiele:** Wenn ich Zeit hätte, würde ich kommen. Könnten Sie mir helfen?
        **Häufige Fehler:** Indikativ im wenn‑Satz (❌ wenn ich habe → ✅ hätte); Fehlendes würde im Hauptsatz.
        **Prüfungstipps:** B1‑Schwerpunkt: würde + Infinitiv + hätte/wäre/könnte.
    """.trimIndent())))

val RelativsatzContent = LearnThemeContent(
    theme = LearnTheme.GRAM_RELATIVSATZ,
    phrases = listOf(LearnPhrase("Relativsätze", "der, die, das ...", """
        **Relativpronomen:** richten sich nach Genus/Numerus des Bezugsworts und Fall im Relativsatz.
        • Nominativ: der, die, das, die
        • Akkusativ: den, die, das, die
        • Dativ: dem, der, dem, denen
        • Genitiv: dessen, deren, dessen, deren
        **Wortstellung:** Verb am Ende; Komma vor Relativsatz.
        **Beispiele:** Der Mann, der mir half, ... Die Frau, mit der ich sprach, ...
        **Häufige Fehler:** Falscher Fall (❌ der Mann, den mir half); Verb nicht am Ende.
        **Prüfungstipps:** B1 konzentriert sich auf Nom., Akk., Dat. Relativpronomen.
    """.trimIndent())))

val AdjektivdeklinationContent = LearnThemeContent(
    theme = LearnTheme.GRAM_ADJEKTIVDEKLINATION,
    phrases = listOf(LearnPhrase("Adjektivdeklination", "Endungen nach Artikel", """
        **Nach bestimmtem Artikel (der‑Wörter):**
        Nom: -e, -e, -e, -en
        Akk: -en, -e, -e, -en
        Dat: -en, -en, -en, -en
        Gen: -en, -en, -en, -en
        
        **Nach unbestimmtem Artikel (ein‑Wörter):**
        Nom: -er, -e, -es, -en
        Akk: -en, -e, -es, -en
        Dat: -en, -en, -en, -en
        Gen: -en, -en, -en, -en
        
        **Ohne Artikel:**
        Nom: -er, -e, -es, -e
        Akk: -en, -e, -es, -e
        Dat: -en, -en, -en, -en
        Gen: -en, -er, -en, -er
        
        **Häufige Fehler:** Gleiche Endung für alle Fälle; -en im Dativ vergessen.
        **Prüfungstipps:** Muster für bestimmte/unbestimmte Artikel getrennt lernen; im Zweifel -en.
    """.trimIndent())))

val PraepositionenContent = LearnThemeContent(
    theme = LearnTheme.GRAM_PRAEPOSITIONEN,
    phrases = listOf(LearnPhrase("Präpositionen", "mit Akkusativ, Dativ, Genitiv", """
        **Akkusativ:** durch, für, gegen, ohne, um (DOGFU)
        **Dativ:** aus, außer, bei, mit, nach, seit, von, zu, gegenüber
        **Wechselpräpositionen (Akkusativ = Bewegung, Dativ = Ort):** an, auf, hinter, in, neben, über, unter, vor, zwischen
        **Genitiv:** wegen, trotz, während, (an)statt
        **Häufige Fehler:** Falscher Fall bei Wechselpräpositionen; fehlende Kontraktionen (im, am, zur).
        **Prüfungstipps:** Wo? vs. Wohin? für Wechselpräpositionen; die 9 Dativ‑Präpositionen auswendig.
    """.trimIndent())))

val WortstellungContent = LearnThemeContent(
    theme = LearnTheme.GRAM_WORTSTELLUNG,
    phrases = listOf(LearnPhrase("Wortstellung (Satzbau)", "Verbposition, TeKaMoLo", """
        **Hauptsatz:** Verb in Position 2.
        • Zeit, Grund, Art, Ort (TeKaMoLo): Ich fahre morgen (T) wegen der Arbeit (K) mit dem Zug (M) nach Berlin (L).
        **Nebensatz:** Verb am Ende (weil ich müde bin).
        **Fragen:** Verb in Position 1 (Kommst du?) oder nach W‑Wort (Wann kommst du?).
        **Häufige Fehler:** Verb nicht in Position 2 (❌ Ich morgen gehe); Verb nicht am Ende im Nebensatz.
        **Prüfungstipps:** Immer Verbposition prüfen; TeKaMoLo für lange Sätze nutzen.
    """.trimIndent())))

val InfinitivMitZuContent = LearnThemeContent(
    theme = LearnTheme.GRAM_INFINITIV_MIT_ZU,
    phrases = listOf(LearnPhrase("Infinitiv mit 'zu' / 'um...zu'", "", """
        **zu + Infinitiv:** nach bestimmten Verben (versuchen, hoffen, planen) und Adjektiven (es ist wichtig, ...).
        → Ich versuche, Deutsch zu lernen.
        **um...zu:** Absicht, gleiches Subjekt → Ich lerne, um zu arbeiten.
        **ohne...zu / anstatt...zu:** ohne / anstatt + zu + Infinitiv.
        **Regeln:** Komma vor Infinitivgruppe; zu direkt vor Infinitiv; bei trennbaren Verben zwischen Präfix und Stamm (anzurufen).
        **Häufige Fehler:** Fehlendes Komma; um...zu bei verschiedenen Subjekten (dann 'damit').
        **Prüfungstipps:** Gleiches Subjekt → um...zu; verschiedenes → damit.
    """.trimIndent())))

val KomparativContent = LearnThemeContent(
    theme = LearnTheme.GRAM_KOMPARATIV,
    phrases = listOf(LearnPhrase("Komparativ & Superlativ", "", """
        **Komparativ:** Adjektiv + -er (schneller), oft Umlaut (älter), unregelmäßig: gut → besser, viel → mehr.
        **Vergleich:** als (größer als).
        **Superlativ:** am + -sten (am schnellsten) oder der/die/das + -ste (der schnellste).
        **Häufige Fehler:** 'wie' statt 'als' im Komparativ; Umlaut vergessen; falsche Superlativform (am größte).
        **Prüfungstipps:** Komparativ für Meinungen (Ich finde X besser als Y); Superlativ für Favoriten.
    """.trimIndent())))

val NegationContent = LearnThemeContent(
    theme = LearnTheme.GRAM_NEGATION,
    phrases = listOf(LearnPhrase("Negation: nicht vs. kein", "", """
        **kein:** verneint Nomen mit unbestimmtem Artikel oder ohne Artikel (Ich habe kein Auto).
        **nicht:** verneint Verben, Adjektive, bestimmte Nomen, etc. Position: vor dem verneinten Element oder am Ende.
        → Ich verstehe das nicht. (Verb)
        → Das ist nicht mein Buch. (Possessiv)
        → Ich gehe heute nicht ins Kino. (allgemein)
        **Häufige Fehler:** nicht bei Nomen ohne Artikel (❌ Ich habe nicht Geld); falsche Position.
        **Prüfungstipps:** Frage: Gibt es einen unbestimmten Artikel? → kein, sonst nicht.
    """.trimIndent())))

val ReflexiveVerbenContent = LearnThemeContent(
    theme = LearnTheme.GRAM_REFLEXIVE_VERBEN,
    phrases = listOf(LearnPhrase("Reflexive Verben", "", """
        **Reflexivpronomen:** Akkusativ (mich, dich, sich, uns, euch, sich) / Dativ (mir, dir, sich, uns, euch, sich).
        **Echte reflexive Verben:** immer mit Reflexivpronomen (sich freuen, sich erinnern).
        **Wechselnd:** manche Verben ändern Bedeutung (treffen → sich treffen).
        **Dativ‑reflexiv:** bei Körperteilen/Possessiv (Ich wasche mir die Hände).
        **Wortstellung:** Pronomen folgt direkt nach Verb im Hauptsatz.
        **Häufige Fehler:** Falscher Kasus (❌ Ich freue mir); 3. Person 'sich' vergessen.
        **Prüfungstipps:** Die 15 häufigsten reflexiven Verben mit Präposition lernen (sich freuen auf/über).
    """.trimIndent())))

val FragenContent = LearnThemeContent(
    theme = LearnTheme.GRAM_FRAGEN,
    phrases = listOf(LearnPhrase("Fragen (Fragesätze)", "", """
        **Ja/Nein‑Fragen:** Verb in Position 1 → Kommst du mit?
        **W‑Fragen:** W‑Wort + Verb + Subjekt → Wo wohnst du?
        **Indirekte Fragen:** Hauptsatz + ob/W‑Wort + Nebensatz (Verb am Ende).
        → Ich weiß nicht, ob er kommt.
        → Können Sie mir sagen, wo der Bahnhof ist?
        **Häufige Fehler:** Verb im indirekten Fragesatz nicht am Ende; 'ob' mit 'wenn' verwechselt.
        **Prüfungstipps:** Indirekte Fragen für höfliche Nachfragen nutzen; 10 W‑Wörter beherrschen.
    """.trimIndent())))

val TrennbareVerbenContent = LearnThemeContent(
    theme = LearnTheme.GRAM_TRENNBARE_VERBEN,
    phrases = listOf(LearnPhrase("Trennbare & untrennbare Verben", "", """
        **Trennbare Präfixe:** ab‑, an‑, auf‑, aus‑, ein‑, mit‑, nach‑, vor‑, zu‑, zurück‑.
        • Im Hauptsatz: Präfix trennt und geht ans Ende (Ich stehe auf).
        • Im Nebensatz: Präfix bleibt am Verb (..., weil ich aufstehe).
        • Perfekt: ge‑ zwischen Präfix und Stamm (aufgestanden).
        
        **Untrennbare Präfixe:** be‑, emp‑, ent‑, er‑, ge‑, miss‑, ver‑, zer‑.
        • Präfix trennt nie; kein ge‑ im Perfekt (besucht).
        
        **Häufige Fehler:** Trennbare Verben im Hauptsatz nicht getrennt; ge‑ bei untrennbaren.
        **Prüfungstipps:** 10 häufigste trennbare/untrennbare Präfixe auswendig; Wortstellung üben.
    """.trimIndent())))

val GenitivContent = LearnThemeContent(
    theme = LearnTheme.GRAM_GENITIV,
    phrases = listOf(LearnPhrase("Genitiv (Einführung)", "", """
        **Bildung:** des + (m/n) + -(e)s (des Mannes); der + (f/pl) (der Frau).
        **Verwendung:** Besitz (das Auto des Mannes), nach Präpositionen (wegen, trotz, während, statt).
        **Umgangssprache:** oft durch von + Dativ ersetzt (das Auto von dem Mann).
        **Häufige Fehler:** Fehlendes -s bei Maskulinum/Neutrum; Verwechslung mit Dativ.
        **Prüfungstipps:** Genitiv erkennen reicht für B1; in formellen Texten verwenden.
    """.trimIndent())))

val ImperativContent = LearnThemeContent(
    theme = LearnTheme.GRAM_IMPERATIV,
    phrases = listOf(LearnPhrase("Imperativ (Befehle, Bitten)", "", """
        **du‑Form:** Stamm (+e) → Lern(e)!; starke Verben mit Umlaut (fahr → fahr, aber: nimm, iss).
        **ihr‑Form:** ihr‑Form ohne ihr → Lernt!
        **Sie‑Form:** Infinitiv + Sie → Lernen Sie!
        **wir‑Form:** Infinitiv + wir (Vorschlag) → Gehen wir!
        **Reflexiv:** Wasch dich! / Wascht euch! / Waschen Sie sich!
        **Häufige Fehler:** du‑Imperativ ohne Stammänderung (nehm → nimm); falsches Reflexivpronomen.
        **Prüfungstipps:** Höfliche Bitten mit Sie‑Form + bitte; 10 unregelmäßige du‑Imperative lernen.
    """.trimIndent())))

// ─── NEW: Sprechen ─────────────────────────────────────────────────
val SprechenB1Content = LearnThemeContent(
    theme = LearnTheme.SPRECHEN_B1,
    phrases = listOf(
        LearnPhrase(
            german = "Goethe Teil 1 – Gemeinsam etwas planen: Kursausflug",
            english = "Plan a course trip: destination, food, transport",
            exampleSentence = """
                **A:** Also, ich finde die Idee mit dem Ausflug super! Wo sollen wir denn hinfahren? Ich würde gerne an einen See, weil das Wetter gerade so schön ist.
                
                **B:** Das klingt gut! Aber vielleicht wäre eine Stadtbesichtigung auch interessant – man lernt dabei etwas und es ist nicht so abhängig vom Wetter.
                
                **A:** Stimmt, aber ein See ist doch entspannender. Wir könnten zum Ammersee fahren – der ist gut erreichbar und hat einen schönen Badestrand.
                
                **B:** Okay, das überzeugt mich! Was sollten wir denn mitbringen? Vielleicht jeder etwas Essen oder Trinken?
                
                **A:** Ja, genau. Ich schlage vor, dass wir uns aufteilen: Ein paar bringen Getränke mit, andere kochen etwas oder kaufen Sandwiches. Und wir könnten auch Spiele mitbringen, wie Frisbee oder Volleyball.
                
                **B:** Gute Idee! Und wie kommen wir hin? Mit dem Bus wäre wohl am einfachsten, oder?
                
                **A:** Ich würde die Bahn vorschlagen – sie ist pünktlicher und wir können alle zusammen fahren. Fahrgemeinschaften wären auch möglich, aber nicht alle haben ein Auto.
                
                **B:** Stimmt, die Bahn ist besser. Dann können auch alle problemlos mitkommen. Sollen wir das so beschließen? See, Bahn, jeder bringt etwas mit?
                
                **A:** Ja, genau! Ich glaube, das wird ein super Ausflug. Wollen wir das dem Kurs heute noch vorstellen?
                
                **B:** Auf jeden Fall! Ich finde unseren Plan wirklich gut.
            """.trimIndent(),
            exampleTranslation = "",
            usageTip = "Teil 1: Gemeinsam planen – Vorschläge machen, zustimmen, ablehnen, Kompromiss finden."
        ),
        LearnPhrase(
            german = "Goethe Teil 2 – Thema präsentieren: Handynutzung in der Schule",
            english = "Presentation: mobile phone use in schools – experience, home country, pros/cons, opinion",
            exampleSentence = """
                **Präsentation:**
                Sehr geehrte Prüferin, liebe Teilnehmerin,
                
                ich möchte heute über das Thema Handynutzung in der Schule sprechen – ein Thema, das mich persönlich sehr beschäftigt.
                
                Ich erinnere mich noch gut an meine Schulzeit: Handys waren damals gerade erst verbreitet, und unsere Lehrer haben sie meist verboten. Trotzdem haben viele Schüler heimlich ihr Handy benutzt – meistens zum Schreiben von Nachrichten. Heute ist das noch viel aktueller.
                
                In meinem Heimatland gibt es unterschiedliche Regelungen. In manchen Schulen dürfen Schüler ihr Handy gar nicht benutzen, in anderen ist es in der Pause erlaubt. Es gibt sogar Schulen, die Handys komplett einsammeln. Die Diskussion darüber ist überall sehr lebhaft.
                
                Einerseits gibt es klare Vorteile: Schüler können Lernapps nutzen, schnell Informationen nachschlagen und digitale Kompetenzen entwickeln. Außerdem können Eltern ihre Kinder jederzeit erreichen, was die Sicherheit erhöht.
                
                Andererseits lenken Handys stark vom Unterricht ab. Viele Schüler schauen lieber auf Social Media als auf die Tafel. Außerdem kann exzessive Handynutzung zu Schlafmangel und Konzentrationsproblemen führen.
                
                Meine persönliche Meinung ist: Handys gehören zum modernen Leben und man kann sie nicht einfach ignorieren. Ich würde mir wünschen, dass Schulen klare Regeln einführen: Handys aus während des Unterrichts, aber erlaubt in den Pausen. So können wir die Vorteile nutzen ohne die Nachteile zu ignorieren.
                
                Vielen Dank für Ihre Aufmerksamkeit. Ich freue mich auf Ihre Fragen.
            """.trimIndent(),
            exampleTranslation = "",
            usageTip = "Teil 2: Struktur – Einleitung, persönliche Erfahrung, Heimatland, Vor-/Nachteile, eigene Meinung, Schluss."
        ),
        LearnPhrase(
            german = "Goethe Teil 3 – Diskussion: Reaktion auf Präsentation",
            english = "Discussion: follow-up questions on the presentation",
            exampleSentence = """
                **B:** Danke für Ihre interessante Präsentation! Sie haben gesagt, dass Handys im Unterricht ablenken. Aber glauben Sie nicht, dass Lern-Apps wie Duolingo oder Khan Academy wirklich nützlich sein können?
                
                **A:** Das ist ein guter Punkt! Ich denke, Lern-Apps haben definitiv ihren Wert. Das Problem ist aber, dass es schwer zu kontrollieren ist, ob ein Schüler gerade eine Lern-App benutzt oder auf Instagram schaut. Deshalb finde ich klare Regeln wichtig.
                
                **B:** Verstehe ich. Aber wäre es nicht besser, wenn die Schule selbst digitale Geräte – zum Beispiel Tablets – bereitstellt, die nur für Lernzwecke genutzt werden können?
                
                **A:** Das wäre ideal, aber leider haben nicht alle Schulen das Budget dafür. In meinem Heimatland gibt es große Unterschiede zwischen reichen und ärmeren Schulen. Handys zu nutzen ist oft die praktischste Lösung – solange man den richtigen Umgang damit lernt.
                
                **B:** Da haben Sie recht. Vielleicht ist digitale Medienerziehung genauso wichtig wie das Handy-Verbot selbst.
                
                **A:** Genau das meine ich! Man muss Schülern beibringen, verantwortungsvoll mit dem Handy umzugehen – das ist eine wichtige Kompetenz für das Leben.
            """.trimIndent(),
            exampleTranslation = "",
            usageTip = "Teil 3: Auf die Präsentation reagieren, Fragen stellen, Meinungen austauschen – höflich bleiben."
        ),
        LearnPhrase(
            german = "ÖSD Teil 1 – Kontaktaufnahme: Kennenlernen",
            english = "Casual conversation: introduce yourself, hobbies, work, family",
            exampleSentence = """
                **Prüfer/in:** Guten Tag! Bitte stellen Sie sich kurz vor. Wie heißen Sie, und woher kommen Sie?
                
                **A:** Guten Tag! Mein Name ist [Name], ich komme aus [Land], aber seit etwa einem Jahr wohne ich in Wien. Ich bin hierher gekommen, weil ich hier arbeite.
                
                **Prüfer/in:** Interessant! Und was machen Sie beruflich?
                
                **A:** Ich bin Informatiker. Ich arbeite für ein kleines Technologieunternehmen hier in Wien. Die Arbeit macht mir viel Spaß, auch wenn es manchmal stressig ist.
                
                **Prüfer/in:** Und was machen Sie in Ihrer Freizeit?
                
                **A:** In meiner Freizeit lese ich gerne, besonders Krimis. Außerdem gehe ich regelmäßig ins Fitnessstudio und entdecke die Stadt – Wien hat so viele schöne Parks und Museen, da gibt es immer etwas Neues zu entdecken!
                
                **Prüfer/in:** Das klingt sehr schön. Haben Sie schon Freunde in Wien gefunden?
                
                **A:** Ja, ein paar! Vor allem durch den Deutschkurs habe ich einige nette Leute kennengelernt. Und meine Kollegen bei der Arbeit sind auch sehr freundlich – wir machen manchmal nach der Arbeit etwas zusammen.
            """.trimIndent(),
            exampleTranslation = "",
            usageTip = "Teil 1: Lockeres Gespräch – auf Fragen antworten, eigene Erfahrungen einbringen."
        ),
        LearnPhrase(
            german = "ÖSD Teil 2 – Thema präsentieren: Soziale Netzwerke – Freund oder Feind?",
            english = "Presentation: social networks – own use, pros/cons, situation in home country",
            exampleSentence = """
                **Präsentation:**
                Sehr geehrte Prüferin, lieber Prüfer,
                
                ich möchte Ihnen heute meine Gedanken zum Thema soziale Netzwerke vorstellen – ein Thema, das unser aller Leben beeinflusst.
                
                Ich selbst nutze soziale Netzwerke täglich. Hauptsächlich verwende ich Instagram und WhatsApp, um mit Familie und Freunden in meinem Heimatland in Kontakt zu bleiben. Das ist für mich als Auswanderer besonders wichtig – ohne diese Plattformen wäre die Entfernung viel schwerer zu ertragen.
                
                In meinem Heimatland sind soziale Netzwerke extrem beliebt. Fast jeder hat ein Smartphone und ist auf mehreren Plattformen aktiv. Besonders junge Menschen verbringen viele Stunden täglich damit. Auch Unternehmen und Politiker nutzen diese Plattformen aktiv für ihre Kommunikation.
                
                Die Vorteile sind klar: Man kann schnell Informationen teilen, Kontakte pflegen und weltweit kommunizieren. Soziale Netzwerke helfen auch dabei, Gemeinschaften zu bilden – zum Beispiel Gruppen für Sprachlernende oder Hobbygruppen. Für kleine Unternehmen bieten sie außerdem kostengünstige Werbemöglichkeiten.
                
                Auf der anderen Seite gibt es ernste Nachteile: Fehlinformationen verbreiten sich sehr schnell. Viele Menschen vergleichen ihr Leben ständig mit den scheinbar perfekten Leben anderer, was zu Unzufriedenheit führen kann. Außerdem können soziale Netzwerke sehr zeitraubend sein – man scrollt manchmal stundenlang, ohne es zu merken.
                
                Mein Fazit: Soziale Netzwerke sind weder rein Freund noch rein Feind. Sie sind ein mächtiges Werkzeug – wie wir sie nutzen, liegt an uns. Mit Bewusstsein und Selbstdisziplin kann man die Vorteile genießen und die Nachteile minimieren.
                
                Danke für Ihre Aufmerksamkeit!
            """.trimIndent(),
            exampleTranslation = "",
            usageTip = "Teil 2: Struktur wie bei Goethe – eigene Nutzung, Heimatland, Vor-/Nachteile, Fazit."
        ),
        LearnPhrase(
            german = "ÖSD Teil 3 – Diskussion: Nachfragen zur Präsentation",
            english = "Discussion: questions about the presentation (e.g., how to reduce usage)",
            exampleSentence = """
                **Prüfer/in:** Sie haben in Ihrer Präsentation erwähnt, dass soziale Netzwerke sehr zeitraubend sein können. Haben Sie einen konkreten Tipp, wie man die eigene Nutzung reduzieren kann?
                
                **A:** Ja, auf jeden Fall! Ich finde, man sollte sich bewusst Zeitlimits setzen. Viele Smartphones haben heute eine eingebaute Funktion, mit der man die tägliche Nutzungszeit einer App begrenzen kann. Das hilft wirklich.
                
                **Prüfer/in:** Das ist ein guter Hinweis. Sie haben auch gesagt, dass Fehlinformationen ein großes Problem sind. Wer ist Ihrer Meinung nach für die Kontrolle verantwortlich – die Plattformen selbst oder der Staat?
                
                **A:** Das ist eine schwierige Frage! Ich denke, beide tragen Verantwortung. Die Plattformen müssen ihre Algorithmen so gestalten, dass Falschinformationen nicht bevorzugt werden. Aber der Staat muss auch rechtliche Rahmenbedingungen schaffen. Am Ende liegt jedoch die größte Verantwortung bei uns, den Nutzern – wir müssen lernen, Quellen kritisch zu hinterfragen.
                
                **Prüfer/in:** Sehr gut! Und würden Sie persönlich soziale Netzwerke komplett aufgeben können?
                
                **A:** Ehrlich gesagt – für mich wäre das gerade sehr schwer, weil meine Familie weit weg ist. Aber ich könnte mir vorstellen, meinen Konsum deutlich zu reduzieren und bewusster zu nutzen. Vielleicht eine Woche Digital-Detox wäre ein guter Anfang – das habe ich letzten Sommer schon einmal ausprobiert und es war überraschend erholsam!
            """.trimIndent(),
            exampleTranslation = "",
            usageTip = "Teil 3: Auf Nachfragen reagieren, eigene Meinung vertiefen, Beispiele nennen."
        )
    )
)

// ─── All Learn Content (only vocabulary themes) ────────────────────────────────────────

val allLearnContent: List<LearnThemeContent> = listOf(
    FamilieContent,
    ArbeitContent,
    FreizeitContent,
    ReisenContent,
    GesundheitContent,
    WohnenContent,
    EinkaufenContent,
    BildungContent,
    UmweltContent,
    EssenContent
)

// ─── Categories ───────────────────────────────────────────────────────────────

data class LearnCategory(
    val id: String,
    val title: String,
    val subtitle: String,
    val themes: List<LearnThemeContent>
)

val allCategories = listOf(
    LearnCategory(
        id = "vocab_phrases",
        title = "Vokabular & Phrasen",
        subtitle = "${allLearnContent.sumOf { it.phrases.size }} Phrasen • ${allLearnContent.size} Themen",
        themes = allLearnContent
    ),
    // NEW: Schreiben B1 category
    LearnCategory(
        id = "schreiben_b1",
        title = "Schreiben B1",
        subtitle = "3 Teile mit Musterlösungen • ${SchreibenTeil1Content.phrases.size + SchreibenTeil2Content.phrases.size + SchreibenTeil3Content.phrases.size} Beispiele",
        themes = listOf(SchreibenTeil1Content, SchreibenTeil2Content, SchreibenTeil3Content)
    ),
    // NEW: Konnektoren category
    LearnCategory(
        id = "konnektoren",
        title = "Konnektoren",
        subtitle = "Verbindungswörter für bessere Sätze",
        themes = listOf(KonjunktionenContent, SubjunktionenContent, KonjunktionaladverbienContent)
    ),
    // NEW: Grammatik category
    LearnCategory(
        id = "grammatik",
        title = "Grammatik",
        subtitle = "17 Themen mit Regeln, Beispielen und Tipps",
        themes = listOf(
            PerfektContent,
            PraeteritumContent,
            ModalverbenContent,
            PassivContent,
            KonjunktivIIContent,
            RelativsatzContent,
            AdjektivdeklinationContent,
            PraepositionenContent,
            WortstellungContent,
            InfinitivMitZuContent,
            KomparativContent,
            NegationContent,
            ReflexiveVerbenContent,
            FragenContent,
            TrennbareVerbenContent,
            GenitivContent,
            ImperativContent
        )
    ),
    // NEW: Sprechen category
    LearnCategory(
        id = "sprechen_b1",
        title = "Sprechen B1",
        subtitle = "6 Dialoge und Präsentationen für die mündliche Prüfung",
        themes = listOf(SprechenB1Content)
    )
)

fun hexToColor(hex: String): Color {
    val clean = hex.removePrefix("#")
    val r = clean.substring(0, 2).toInt(16) / 255f
    val g = clean.substring(2, 4).toInt(16) / 255f
    val b = clean.substring(4, 6).toInt(16) / 255f
    return Color(r, g, b)
}