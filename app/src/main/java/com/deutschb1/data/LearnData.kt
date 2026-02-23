package com.deutschb1.data

import androidx.annotation.DrawableRes
import com.deutschb1.R
import androidx.compose.ui.graphics.painter.Painter
// OR if you use Icons instead of Painter
import androidx.compose.ui.graphics.vector.ImageVector
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
    SCHREIBEN("Schreiben & Textproduktion", "✍️", "E-Mails, Briefe, Aufsätze schreiben", "#FF9F43"),
    SPRECHEN("Sprechen & Kommunikation", "🗣️", "Präsentationen, Gespräche, Diskussionen", "#54A0FF"),
    GRAMMATIK("Grammatik & Struktur", "📖", "Zeiten, Fälle, Satzstruktur", "#5F27CD")
}

// ─── Phrase Model ─────────────────────────────────────────────────────────────

data class LearnPhrase(
    val german: String,
    val english: String,
    val exampleSentence: String,
    val exampleTranslation: String = "",  // ← Add "= "" " to make it optional
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

// ─── Schreiben Content ────────────────────────────────────────────────────────

val SchreibenLearnContent = LearnThemeContent(
    theme = LearnTheme.SCHREIBEN,
    phrases = listOf(
        // ─── TEMPLATES (Structure Guide) ───────────────────────────────────────
        LearnPhrase(
            german = "TEIL 1 Template: Erlebnisbeschreibung",
            english = "Part 1 Template: Describing an Experience",
            exampleSentence = """Struktur: 1. Einleitung (Wann/Wo/Mit wem?) → 2. Hauptteil (Was ist passiert?) → 3. Höhepunkt (Besonders ... hat mich ...) → 4. Gefühle (Ich habe mich ... gefühlt) → 5. Schluss (Empfehlung)""",
            exampleTranslation = """Structure: 1. Introduction (When/Where/With whom?) → 2. Main part (What happened?) → 3. Highlight (Especially ... me ...) → 4. Feelings (I felt ...) → 5. Conclusion (Recommendation)""",
            usageTip = "Wichtig: Ca. 80–100 Wörter schreiben."
        ),
        LearnPhrase(
            german = "TEIL 2 Template: Meinungsäußerung",
            english = "Part 2 Template: Expressing Opinion",
            exampleSentence = """Struktur: 1. Position (Ich bin dafür/dagegen, weil...) → 2. Argument 1 (Einerseits...) → 3. Argument 2 (Außerdem...) → 4. Einschränkung (Natürlich..., aber...) → 5. Fazit (Meiner Meinung nach...)""",
            exampleTranslation = """Structure: 1. Position (I am for/against because...) → 2. Argument 1 (On one hand...) → 3. Argument 2 (Furthermore...) → 4. Limitation (Of course..., but...) → 5. Conclusion (In my opinion...)""",
            usageTip = "Wichtig: Klare Meinung und mindestens 2 Argumente."
        ),
        LearnPhrase(
            german = "TEIL 3 Template: Brief / E-Mail",
            english = "Part 3 Template: Letter / Email",
            exampleSentence = """Struktur: 1. Anrede (Sehr geehrte... / Liebe...) → 2. Bezug (Ich schreibe bezüglich...) → 3. Vorstellung (Name, Alter) → 4. Anliegen (Fragen/Bewerbung) → 5. Abschluss (Ich würde mich freuen...)""",
            exampleTranslation = """Structure: 1. Salutation (Dear...) → 2. Reference (I am writing regarding...) → 3. Introduction (Name, Age) → 4. Purpose (Questions/Application) → 5. Closing (I would be happy...)""",
            usageTip = "Wichtig: Auf den Ton achten (formell vs. informell)."
        ),

        // ─── TEIL 1 EXAMPLES (1.1 - 1.10) ──────────────────────────────────────
        LearnPhrase(
            german = "1.1 Kino Film gesehen",
            english = "Write to a friend about a movie you saw.",
            exampleSentence = """Liebe Julia, ich möchte dir von einem tollen Kinoabend erzählen. Wir haben den Film "Das Lehrerzimmer" gesehen – ein spannendes deutsches Drama. Besonders beeindruckt hat mich das Ende des Films. Ich habe mich dabei bestens unterhalten. Ich würde den Film unbedingt empfehlen. Liebe Grüße, Mia""",
            exampleTranslation = """Dear Julia, I would like to tell you about a great cinema evening. We watched the film 'The Teachers' Lounge' – an exciting German drama. I was especially impressed by the ending. I felt greatly entertained. I would definitely recommend the film. Best regards, Mia""",
            usageTip = "Keywords: spannend, realistisch, beeindruckt, empfehlen"
        ),
        LearnPhrase(
            german = "1.2 Flohmarkt",
            english = "Write a blog post about a flea market visit.",
            exampleSentence = """Hallo zusammen! Ich möchte euch von einem tollen Erlebnis erzählen. Mit meiner Freundin habe ich stundenlang zwischen den Ständen gestöbert. Besonders schön war die Atmosphäre. Ich habe mich dabei entspannt, fröhlich und nostalgisch gefühlt. Ein Flohmarktbesuch ist wirklich empfehlenswert!""",
            exampleTranslation = """Hello everyone! I would like to tell you about a great experience. With my friend I browsed for hours between the stalls. The atmosphere was especially beautiful. I felt relaxed, cheerful and nostalgic. A flea market visit is really recommended!""",
            usageTip = "Keywords: stöbern, Schnäppchen, Atmosphäre, nostalgisch"
        ),
        LearnPhrase(
            german = "1.3 Kochkurs",
            english = "Write to an acquaintance about a cooking course.",
            exampleSentence = """Liebe Sandra, ich wollte dir von einem Kochkurs erzählen. Gemeinsam mit zehn anderen Teilnehmern habe ich ein italienisches Menü zubereitet. Besonders begeistert hat mich, wie viel besser die selbst gemachte Pasta schmeckt. Ich habe mich dabei sehr wohl und kreativ gefühlt.""",
            exampleTranslation = """Dear Sandra, I wanted to tell you about a cooking course. Together with ten other participants I prepared an Italian menu. I was especially excited about how much better the homemade pasta tastes. I felt very comfortable and creative.""",
            usageTip = "Keywords: zubereiten, geduldig, Tipps, inspiriert"
        ),
        LearnPhrase(
            german = "1.4 Rundfahrt",
            english = "Write to family about a bus/boat tour.",
            exampleSentence = """Liebe Familie, ich möchte euch von einer wunderschönen Schiffsrundfahrt erzählen. Die zweistündige Fahrt führte uns an malerischen Uferdörfern vorbei. Besonders atemberaubend war die Aussicht auf die Alpen. Ich habe mich dabei vollkommen entspannt gefühlt.""",
            exampleTranslation = """Dear Family, I would like to tell you about a wonderful boat tour. The two-hour trip led us past picturesque villages. The view of the Alps was especially breathtaking. I felt completely relaxed.""",
            usageTip = "Keywords: malerisch, atemberaubend, unvergesslich, es lohnt sich"
        ),
        LearnPhrase(
            german = "1.5 Klassentreffen",
            english = "Write to a former classmate about a reunion.",
            exampleSentence = """Lieber Marco, ich wollte dir von unserem Klassentreffen erzählen. Fast alle aus unserer alten Klasse sind zusammengekommen. Besonders lustig war der Moment, als jemand ein altes Schulfoto mitgebracht hat. Ich habe mich dabei warm und glücklich gefühlt.""",
            exampleTranslation = """Dear Marco, I wanted to tell you about our class reunion. Almost everyone from our old class came together. Especially funny was the moment when someone brought an old school photo. I felt warm and happy.""",
            usageTip = "Keywords: zusammenkommen, sich erinnern, kaum wiedererkennen"
        ),
        LearnPhrase(
            german = "1.6 Geschäft eröffnen",
            english = "Write a blog post about opening a shop.",
            exampleSentence = """Hallo zusammen! Ich möchte euch von einem aufregenden Tag berichten. Die Vorbereitungen haben Monate gedauert. Besonders berührt hat mich, wie begeistert alle reagiert haben. Ich habe mich dabei stolz und glücklich gefühlt. Ein Traum ist endlich wahr geworden!""",
            exampleTranslation = """Hello everyone! I would like to report about an exciting day. The preparations took months. I was especially touched by how enthusiastically everyone reacted. I felt proud and happy. A dream has finally come true!""",
            usageTip = "Keywords: aufregend, Vorbereitung, begeistert, stolz"
        ),
        LearnPhrase(
            german = "1.7 Neue Wohnung",
            english = "Write to a friend about moving to a new apartment.",
            exampleSentence = """Liebe Sophia, ich freue mich, dir von meiner neuen Wohnung erzählen zu können. Das Tragen der schweren Kartons war anstrengend. Die Wohnung ist hell und geräumig. Besonders gefällt mir die große Küche. Ich habe mich hier sofort wohl gefühlt.""",
            exampleTranslation = """Dear Sophia, I am happy to tell you about my new apartment. Carrying the heavy boxes was exhausting. The apartment is bright and spacious. I especially like the large kitchen. I felt comfortable here immediately.""",
            usageTip = "Keywords: anstrengend, geräumig, wohl fühlen, sich kümmern"
        ),
        LearnPhrase(
            german = "1.8 Schulbesichtigung",
            english = "Write to a relative about visiting a school with your child.",
            exampleSentence = """Liebe Tante Renate, ich möchte dir von der Schulbesichtigung erzählen. Wir haben eine moderne Grundschule besucht. Besonders schön war der Moment, als Leon andere Kinder kennenlernte. Ich habe mich dabei erleichtert und optimistisch gefühlt. Es ist die richtige Wahl!""",
            exampleTranslation = """Dear Aunt Renate, I would like to tell you about the school visit. We visited a modern primary school. Especially nice was the moment when Leon met other children. I felt relieved and optimistic. It is the right choice!""",
            usageTip = "Keywords: modern, freundlich, erleichtert, optimistisch"
        ),
        LearnPhrase(
            german = "1.9 Reise gemacht",
            english = "Write a blog post about a trip.",
            exampleSentence = """Hallo zusammen! Ich möchte euch von meiner unvergesslichen Reise nach Portugal erzählen. Wir sind durch bunte Gassen geschlendert. Besonders begeistert hat mich der Sonnenuntergang. Ich habe mich dabei frei und glücklich gefühlt. Absolut empfehlenswert!""",
            exampleTranslation = """Hello everyone! I would like to tell you about my unforgettable trip to Portugal. We strolled through colorful streets. I was especially excited about the sunset. I felt free and happy. Absolutely recommended!""",
            usageTip = "Keywords: schlendern, türkisfarben, Sonnenuntergang, atemberaubend"
        ),
        LearnPhrase(
            german = "1.10 Museum besuchen",
            english = "Tell a friend about a museum visit.",
            exampleSentence = """Lieber David, ich wollte dir von einem besonderen Museumsbesuch erzählen. Ich war im Kunsthistorischen Museum in Wien. Besonders fasziniert hat mich ein Gemälde von Rembrandt. Ich habe mich dabei neugierig gefühlt. Ein Museumsbesuch bildet wirklich!""",
            exampleTranslation = """Dear David, I wanted to tell you about a special museum visit. I was at the Art History Museum in Vienna. I was especially fascinated by a painting by Rembrandt. I felt curious. A museum visit is truly educational!""",
            usageTip = "Keywords: Skulpturen, Führerin, fasziniert, Maltechnik"
        ),

        // ─── TEIL 2 EXAMPLES (2.1 - 2.10) ──────────────────────────────────────
        LearnPhrase(
            german = "2.1 Ungesundes Essen am Arbeitsplatz",
            english = "Opinion: Healthy vs. Unhealthy food at work.",
            exampleSentence = """Zu diesem Thema habe ich eine klare Meinung: Ich bin dagegen. Einerseits schadet ungesunde Ernährung der Konzentration. Außerdem kostet Fast Food langfristig mehr Geld. Natürlich verstehe ich, dass Berufstätige oft wenig Zeit haben. Meiner Meinung nach sollten Arbeitgeber gesunde Optionen anbieten.""",
            exampleTranslation = """On this topic I have a clear opinion: I am against it. On one hand, unhealthy nutrition harms concentration. Furthermore, fast food costs more money in the long term. Of course I understand that workers often have little time. In my opinion, employers should offer healthy options.""",
            usageTip = "Keywords: Konzentration, unproduktiv, bewusste Ernährung"
        ),
        LearnPhrase(
            german = "2.2 Umweltschutz",
            english = "Opinion: What can we do for environmental protection?",
            exampleSentence = """Zu diesem Thema habe ich eine klare Meinung: Umweltschutz ist eine absolute Notwendigkeit. Einerseits sehen wir täglich die Folgen des Klimawandels. Außerdem ist die Umwelt das Erbe künftiger Generationen. Natürlich reichen individuelle Maßnahmen allein nicht aus. Meiner Meinung nach brauchen wir persönliches Engagement und strenge Gesetze.""",
            exampleTranslation = """On this topic I have a clear opinion: Environmental protection is an absolute necessity. On one hand, we see the consequences of climate change daily. Furthermore, the environment is the heritage of future generations. Of course, individual measures alone are not enough. In my opinion, we need personal commitment and strict laws.""",
            usageTip = "Keywords: überzeugt, Klimawandel, Erbe, Engagement"
        ),
        LearnPhrase(
            german = "2.3 Fernseher im Kinderzimmer",
            english = "Opinion: Should kids have a TV in their room?",
            exampleSentence = """Zu diesem Thema habe ich eine klare Meinung: Ich bin dagegen. Einerseits können Eltern den Medienkonsum nicht mehr kontrollieren. Außerdem leiden Schlaf und Schulleistung. Natürlich dürfen Kinder auch fernsehen. Meiner Meinung nach sollte das immer im gemeinsamen Wohnzimmer stattfinden.""",
            exampleTranslation = """On this topic I have a clear opinion: I am against it. On one hand, parents can no longer control media consumption. Furthermore, sleep and school performance suffer. Of course, children may watch TV. In my opinion, this should always happen in the shared living room.""",
            usageTip = "Keywords: Medienkonsum, Aufsicht, Schulleistung, gemeinsam"
        ),
        LearnPhrase(
            german = "2.4 Einkaufen im Internet",
            english = "Opinion: Advantages of online shopping.",
            exampleSentence = """Zu diesem Thema habe ich eine klare Meinung: Die Vorteile überwiegen. Einerseits ist es sehr bequem. Außerdem ist die Auswahl im Internet viel größer. Natürlich gibt es auch Risiken wie Betrug. Meiner Meinung nach sollte man Online-Shopping bewusst einsetzen.""",
            exampleTranslation = """On this topic I have a clear opinion: The advantages outweigh. On one hand, it is very convenient. Furthermore, the selection on the internet is much larger. Of course, there are also risks like fraud. In my opinion, one should use online shopping consciously.""",
            usageTip = "Keywords: überwiegen, mühelos, Auswahl, Betrug"
        ),
        LearnPhrase(
            german = "2.5 Führerschein ab 16 Jahre",
            english = "Opinion: Driving license at age 16?",
            exampleSentence = """Zu diesem Thema habe ich eine klare Meinung: Ich bin gegen den Führerschein ab 16 Jahren. Einerseits sind 16-Jährige oft noch nicht reif genug. Außerdem wäre die Kfz-Versicherung sehr teuer. Natürlich haben junge Menschen ein Mobilitätsproblem. Meiner Meinung nach ist begleitetes Fahren ab 17 ein guter Kompromiss.""",
            exampleTranslation = """On this topic I have a clear opinion: I am against the driving license at 16. On one hand, 16-year-olds are often not mature enough. Furthermore, car insurance would be very expensive. Of course, young people have a mobility problem. In my opinion, accompanied driving from 17 is a good compromise.""",
            usageTip = "Keywords: Verantwortung, finanzielle Belastung, Kompromiss"
        ),
        LearnPhrase(
            german = "2.6 Urlaub am Strand",
            english = "Opinion: Is beach vacation the best?",
            exampleSentence = """Zu diesem Thema habe ich eine klare Meinung: Strand ist wunderbar, aber nicht für jeden der beste. Einerseits bietet der Strand echte Erholung. Außerdem ist Schwimmen gesund. Natürlich bevorzugen manche Menschen Berge. Meiner Meinung nach ist es am wichtigsten, den Urlaub zu wählen, der Freude bringt.""",
            exampleTranslation = """On this topic I have a clear opinion: Beach is wonderful, but not the best for everyone. On one hand, the beach offers real relaxation. Furthermore, swimming is healthy. Of course, some people prefer mountains. In my opinion, it is most important to choose the vacation that brings joy.""",
            usageTip = "Keywords: Erholung, Verpflichtungen, abschalten, bevorzugen"
        ),
        LearnPhrase(
            german = "2.7 Essen in öffentlichen Verkehrsmitteln",
            english = "Opinion: Eating on buses/trains?",
            exampleSentence = """Zu diesem Thema habe ich eine klare Meinung: Ich bin gegen stark riechende Speisen. Einerseits ist der Geruch für andere Fahrgäste unangenehm. Außerdem kann Essen die Sitze schmutzig machen. Natürlich ist ein kleiner Keks in Ordnung. Meiner Meinung nach sollte man Rücksicht auf Mitreisende nehmen.""",
            exampleTranslation = """On this topic I have a clear opinion: I am against strongly smelling food. On one hand, the smell is unpleasant for other passengers. Furthermore, food can make seats dirty. Of course, a small cookie is okay. In my opinion, one should be considerate of fellow travelers.""",
            usageTip = "Keywords: Fahrgäste, störend, Rücksicht nehmen, intensiv"
        ),
        LearnPhrase(
            german = "2.8 Soll man Vegetarier werden",
            english = "Opinion: Should people become vegetarian?",
            exampleSentence = """Zu diesem Thema habe ich eine klare Meinung: Mehr Menschen sollten vegetarisch essen. Einerseits ist pflanzenbasierte Ernährung oft gesünder. Außerdem hat Fleischproduktion enorme Auswirkungen auf die Umwelt. Natürlich ist es eine persönliche Entscheidung. Meiner Meinung nach wäre weniger Fleisch essen schon ein großer Fortschritt.""",
            exampleTranslation = """On this topic I have a clear opinion: More people should eat vegetarian. On one hand, plant-based nutrition is often healthier. Furthermore, meat production has enormous impacts on the environment. Of course, it is a personal decision. In my opinion, eating less meat would already be a big progress.""",
            usageTip = "Keywords: pflanzenbasiert, Herzerkrankungen, Treibhausgase"
        ),
        LearnPhrase(
            german = "2.9 Dürfen Männer weinen",
            english = "Opinion: Should men cry in public?",
            exampleSentence = """Zu diesem Thema habe ich eine klare Meinung: Ja, Männer dürfen weinen. Einerseits ist Weinen eine natürliche menschliche Reaktion. Außerdem zeigen Studien, dass Unterdrücken von Gefühlen zu Depressionen führen kann. Natürlich gibt es kulturelle Unterschiede. Meiner Meinung nach sollten wir alte Rollenbilder überwinden.""",
            exampleTranslation = """On this topic I have a clear opinion: Yes, men may cry. On one hand, crying is a natural human reaction. Furthermore, studies show that suppressing feelings can lead to depression. Of course, there are cultural differences. In my opinion, we should overcome old role models.""",
            usageTip = "Keywords: natürliche Reaktion, Trauer, unterdrücken, Rollenbilder"
        ),
        LearnPhrase(
            german = "2.10 Telefonieren in öffentlichen Verkehrsmitteln",
            english = "Opinion: Loud phone calls on transport?",
            exampleSentence = """Zu diesem Thema habe ich eine klare Meinung: Lautes Telefonieren ist störend. Einerseits wollen viele Mitfahrer in Ruhe lesen. Außerdem werden private Details hörbar erzählt. Natürlich muss man manchmal dringend telefonieren. Meiner Meinung nach sollte man leise sprechen oder in den Vorraum gehen.""",
            exampleTranslation = """On this topic I have a clear opinion: Loud phone calls are disturbing. On one hand, many passengers want to read in peace. Furthermore, private details are told audibly. Of course, sometimes one must phone urgently. In my opinion, one should speak quietly or go to the vestibule.""",
            usageTip = "Keywords: rücksichtslos, Mitfahrer, hörbar, peinlich"
        ),

        // ─── TEIL 3 EXAMPLES (3.1 - 3.6) ───────────────────────────────────────
        LearnPhrase(
            german = "3.1 Fahrrad-Anzeige",
            english = "Email to seller: Ask about a used bike.",
            exampleSentence = """Sehr geehrte/r Verkäufer/in, ich schreibe Ihnen bezüglich Ihrer Anzeige für ein gebrauchtes Mountainbike. Das Fahrrad interessiert mich sehr, da ich ein zuverlässiges Rad suche. Ich würde gerne wissen, wie alt das Fahrrad ist und ob der Preis verhandelbar ist. Ich bitte um einen Besichtigungstermin. Mit freundlichen Grüßen, Max Müller""",
            exampleTranslation = """Dear Seller, I am writing to you regarding your advertisement for a used mountainbike. The bicycle interests me very much as I am looking for a reliable bike. I would like to know how old the bicycle is and if the price is negotiable. I request a viewing appointment. Sincerely, Max Müller""",
            usageTip = "Keywords: zuverlässig, Arbeitsweg, gewartet, verhandelbar"
        ),
        LearnPhrase(
            german = "3.2 Entschuldigung",
            english = "Email to friend: Apologize for missed appointment.",
            exampleSentence = """Lieber Jonas, ich schreibe dir, weil ich mich aufrichtig entschuldigen möchte. Leider habe ich unseren Termin völlig vergessen. Der Grund war ein unerwarteter Notfall bei der Arbeit. Ich hätte dir trotzdem rechtzeitig eine Nachricht schicken sollen. Als Zeichen meiner Entschuldigung lade ich dich zum Essen ein. Liebe Grüße, Sophia""",
            exampleTranslation = """Dear Jonas, I am writing to you because I want to apologize sincerely. Unfortunately, I completely forgot our appointment. The reason was an unexpected emergency at work. I should have sent you a message anyway in time. As a sign of my apology, I invite you to dinner. Best regards, Sophia""",
            usageTip = "Keywords: aufrichtig, übel nehmen, unerwarteter Notfall, rechtzeitig"
        ),
        LearnPhrase(
            german = "3.3 Job-Anzeige: Kassierer/in",
            english = "Application email for supermarket cashier job.",
            exampleSentence = """Sehr geehrte Damen und Herren, ich schreibe Ihnen bezüglich Ihrer Stellenanzeige. Mein Name ist Leila Bensalem, ich bin 28 Jahre alt. Ich habe bereits ein Jahr Erfahrung als Verkäuferin. Ich bin zuverlässig, pünktlich und lernbereit. Ich bitte um einen Vorstellungstermin. Mit freundlichen Grüßen, Leila Bensalem""",
            exampleTranslation = """Dear Sir or Madam, I am writing to you regarding your job advertisement. My name is Leila Bensalem, I am 28 years old. I already have one year of experience as a saleswoman. I am reliable, punctual and willing to learn. I request an interview appointment. Sincerely, Leila Bensalem""",
            usageTip = "Keywords: Stellenanzeige, geeignet, Erfahrung, lernbereit, Eignung"
        ),
        LearnPhrase(
            german = "3.4 Englisch-Kurs-Anzeige",
            english = "Email: Register for English course.",
            exampleSentence = """Sehr geehrte Damen und Herren, ich schreibe Ihnen bezüglich Ihres Englisch-Kurses für Anfänger. Mein Name ist Amira Hassan, ich benötige die Sprache dringend für meinen Beruf. Ich würde gerne wissen, ob noch freie Plätze verfügbar sind. Ich bitte um baldige Rückmeldung. Mit freundlichen Grüßen, Amira Hassan""",
            exampleTranslation = """Dear Sir or Madam, I am writing to you regarding your English course for beginners. My name is Amira Hassan, I urgently need the language for my profession. I would like to know if there are still free places available. I request prompt feedback. Sincerely, Amira Hassan""",
            usageTip = "Keywords: dringend, Abendkurs, verfügbar, Ratenzahlung"
        ),
        LearnPhrase(
            german = "3.5 Schauspieler-Anzeige",
            english = "Application email for theater role.",
            exampleSentence = """Sehr geehrte Damen und Herren, ich schreibe Ihnen bezüglich Ihrer Anzeige für Schauspieler/innen. Theater ist meine größte Leidenschaft. Ich habe drei Jahre lang in einer Laientheatergruppe mitgespielt. Ich bitte herzlich um einen Vorsprechtermin. Mit freundlichen Grüßen, Carlos Diaz""",
            exampleTranslation = """Dear Sir or Madam, I am writing to you regarding your advertisement for actors. Theater is my greatest passion. I have played in an amateur theater group for three years. I warmly request an audition appointment. Sincerely, Carlos Diaz""",
            usageTip = "Keywords: Leidenschaft, Laientheater, Hauptrolle, Vorsprechtermin"
        ),
        LearnPhrase(
            german = "3.6 Wohnungs-Anzeige",
            english = "Email to landlord: Request viewing.",
            exampleSentence = """Sehr geehrte Damen und Herren, ich schreibe Ihnen bezüglich Ihrer Anzeige für eine 2-Zimmer-Wohnung. Mein Name ist Nina Kowalski, ich bin berufstätig und zuverlässig. Ich würde gerne wissen, ob Haustiere erlaubt sind und wie hoch die Nebenkosten sind. Ich bitte um einen Besichtigungstermin. Mit freundlichen Grüßen, Nina Kowalski""",
            exampleTranslation = """Dear Sir or Madam, I am writing to you regarding your advertisement for a 2-room apartment. My name is Nina Kowalski, I am employed and reliable. I would like to know if pets are allowed and how high the additional costs are. I request a viewing appointment. Sincerely, Nina Kowalski""",
            usageTip = "Keywords: Immobilienportal, zentrale Lage, Nebenkosten, Kautionshöhe"
        ),

        // ─── USEFUL EXPRESSIONS ────────────────────────────────────────────────
        LearnPhrase(
            german = "Nützliche Ausdrücke: Einleitung",
            english = "Useful Expressions: Introduction",
            exampleSentence = """• ich möchte dir/Ihnen von ... erzählen • ich schreibe dir/Ihnen bezüglich • ich möchte meine Meinung zu ... äußern""",
            exampleTranslation = """• I would like to tell you about... • I am writing to you regarding... • I would like to express my opinion on...""",
            usageTip = "Start your text clearly."
        ),
        LearnPhrase(
            german = "Nützliche Ausdrücke: Argumente",
            english = "Useful Expressions: Arguments",
            exampleSentence = """• Einerseits ... • Außerdem / Darüber hinaus ... • Ein weiterer Punkt ist ... • Zum Beispiel ...""",
            exampleTranslation = """• On one hand... • Furthermore / Moreover... • Another point is... • For example...""",
            usageTip = "Connect your ideas."
        ),
        LearnPhrase(
            german = "Nützliche Ausdrücke: Einschränkung",
            english = "Useful Expressions: Limitation",
            exampleSentence = """• Natürlich ... , aber ... • Zwar ... , jedoch ... • Das stimmt, allerdings ...""",
            exampleTranslation = """• Of course ..., but... • Admittedly ..., however... • That is true, however...""",
            usageTip = "Show nuance in Part 2."
        ),
        LearnPhrase(
            german = "Nützliche Ausdrücke: Meinung",
            english = "Useful Expressions: Opinion",
            exampleSentence = """• Meiner Meinung nach ... • Ich bin überzeugt, dass ... • Ich finde, dass ...""",
            exampleTranslation = """• In my opinion... • I am convinced that... • I find that...""",
            usageTip = "State your conclusion."
        ),
        LearnPhrase(
            german = "Nützliche Ausdrücke: Schluss",
            english = "Useful Expressions: Conclusion",
            exampleSentence = """• Ich würde mich sehr freuen ... • Ich hoffe auf Ihre baldige Antwort • Ich empfehle es jedem!""",
            exampleTranslation = """• I would be very happy... • I hope for your prompt answer • I recommend it to everyone!""",
            usageTip = "Polite ending."
        )
    )
)

// ─── Sprechen Content ─────────────────────────────────────────────────────────

val SprechenContent = LearnThemeContent(
    theme = LearnTheme.SPRECHEN,
    phrases = listOf(
        LearnPhrase("sich vorstellen", "to introduce oneself", "Darf ich mich vorstellen? Ich heiße Anna.", "May I introduce myself? My name is Anna.", "Typisch am ersten Tag"),
        LearnPhrase("eine Präsentation halten", "to give a presentation", "Ich muss morgen eine Präsentation halten.", "I have to give a presentation tomorrow."),
        LearnPhrase("das Wort ergreifen", "to take the floor", "Darf ich das Wort ergreifen?", "May I take the floor?", "Formell in Meetings"),
        LearnPhrase("nachfragen", "to ask for clarification", "Könnten Sie das bitte wiederholen? Ich habe nicht verstanden.", "Could you please repeat that? I didn't understand."),
        LearnPhrase("zustimmen / widersprechen", "to agree / disagree", "Ich stimme Ihnen voll zu. / Da muss ich widersprechen.", "I fully agree with you. / I have to disagree with that."),
        LearnPhrase("eine Frage stellen", "to ask a question", "Darf ich eine Frage stellen?", "May I ask a question?"),
        LearnPhrase("laut und deutlich sprechen", "to speak loudly and clearly", "Bitte sprechen Sie laut und deutlich.", "Please speak loudly and clearly."),
        LearnPhrase("ins Stocken geraten", "to get stuck (while speaking)", "Ich bin beim Sprechen ins Stocken geraten.", "I got stuck while speaking."),
        LearnPhrase("ein Gespräch beginnen", "to start a conversation", "Wie kann ich ein Gespräch auf Deutsch beginnen?", "How can I start a conversation in German?"),
        LearnPhrase("das Gesprächsthema wechseln", "to change the topic", "Können wir das Gesprächsthema wechseln?", "Can we change the topic?"),
        LearnPhrase("ausdrücken", "to express", "Wie kann ich das auf Deutsch ausdrücken?", "How can I express that in German?"),
        LearnPhrase("unterbrechen", "to interrupt", "Entschuldigung, darf ich kurz unterbrechen?", "Excuse me, may I interrupt briefly?", "Höfliche Unterbrechung"),
        LearnPhrase("weitermachen", "to continue", "Bitte machen Sie weiter.", "Please continue."),
        LearnPhrase("etwas betonen", "to emphasize something", "Ich möchte diesen Punkt besonders betonen.", "I want to emphasize this point particularly."),
        LearnPhrase("eine Pause machen", "to take a break", "Können wir eine kurze Pause machen?", "Can we take a short break?"),
        LearnPhrase("zusammenfassen", "to summarize", "Lassen Sie mich das kurz zusammenfassen.", "Let me summarize that briefly."),
        LearnPhrase("Beispiele nennen", "to give examples", "Können Sie ein Beispiel nennen?", "Can you give an example?"),
        LearnPhrase("die Aussprache üben", "to practice pronunciation", "Ich muss meine Aussprache üben.", "I need to practice my pronunciation."),
        LearnPhrase("frei sprechen", "to speak freely", "Bei der Prüfung müssen Sie frei sprechen.", "In the exam, you have to speak freely."),
        LearnPhrase("Feedback geben", "to give feedback", "Können Sie mir Feedback zu meiner Präsentation geben?", "Can you give me feedback on my presentation?")
    )
)

// ─── Grammatik Content ────────────────────────────────────────────────────────

val GrammatikContent = LearnThemeContent(
    theme = LearnTheme.GRAMMATIK,
    phrases = listOf(
        LearnPhrase("der Nominativ", "nominative case", "Der Nominativ ist der erste Fall im Deutschen.", "The nominative is the first case in German.", "Subjekt-Fall"),
        LearnPhrase("der Akkusativ", "accusative case", "Den Akkusativ benutzt man für das direkte Objekt.", "The accusative is used for the direct object."),
        LearnPhrase("der Dativ", "dative case", "Der Dativ ist der dritte Fall.", "The dative is the third case.", "Indirektes Objekt"),
        LearnPhrase("der Genitiv", "genitive case", "Der Genitiv zeigt Besitz an.", "The genitive shows possession.", "Wessen-Fall"),
        LearnPhrase("die Konjugation", "conjugation", "Die Konjugation von Verben ist wichtig.", "Conjugating verbs is important."),
        LearnPhrase("das Präsens", "present tense", "Das Präsens beschreibt aktuelle Handlungen.", "The present tense describes current actions."),
        LearnPhrase("das Perfekt", "perfect tense", "Das Perfekt wird für vergangene Handlungen benutzt.", "The perfect tense is used for past actions.", "Gesprochene Sprache"),
        LearnPhrase("das Präteritum", "simple past", "Das Präteritum benutzt man oft in Geschichten.", "The simple past is often used in stories.", "Geschriebene Sprache"),
        LearnPhrase("das Futur I", "future tense", "Das Futur I beschreibt zukünftige Handlungen.", "The future tense describes future actions."),
        LearnPhrase("die Präpositionen", "prepositions", "Präpositionen stehen vor Nomen.", "Prepositions stand before nouns."),
        LearnPhrase("die Artikel (der/die/das)", "articles", "Im Deutschen gibt es drei Artikel.", "There are three articles in German."),
        LearnPhrase("die Pluralbildung", "plural formation", "Die Pluralbildung ist im Deutschen komplex.", "Plural formation is complex in German."),
        LearnPhrase("die Adjektivdeklination", "adjective declension", "Adjektive werden nach Fall und Artikel dekliniert.", "Adjectives are declined according to case and article."),
        LearnPhrase("die Nebensätze", "subordinate clauses", "Nebensätze haben das Verb am Ende.", "Subordinate clauses have the verb at the end."),
        LearnPhrase("die Konjunktionen", "conjunctions", "Konjunktionen verbinden Sätze.", "Conjunctions connect sentences."),
        LearnPhrase("weil / denn", "because", "Weil leitet einen Nebensatz ein, denn einen Hauptsatz.", "Weil introduces a subordinate clause, denn a main clause."),
        LearnPhrase("obwohl / trotzdem", "although / nevertheless", "Obwohl es regnet, gehe ich spazieren.", "Although it's raining, I'm going for a walk."),
        LearnPhrase("wenn / als", "when/if", "Wenn benutzt man für Gegenwart/Zukunft, als für Vergangenheit.", "Wenn is used for present/future, als for past."),
        LearnPhrase("die Relativsätze", "relative clauses", "Relativsätze geben mehr Informationen über ein Nomen.", "Relative clauses give more information about a noun."),
        LearnPhrase("Passiv bilden", "to form passive", "Das Passiv wird mit werden + Partizip II gebildet.", "The passive is formed with werden + past participle.")
    )
)

// ─── All Learn Content ────────────────────────────────────────────────────────

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

// Make sure this is only defined ONCE in the file
data class LearnCategory(
    val id: String,
    val title: String,
    val subtitle: String,
    val themes: List<LearnThemeContent> // Use the correct type from your existing code
)

// Replace the old allCategories with this:

val allCategories = listOf(
    LearnCategory(
        id = "vocab_phrases",
        title = "Vokabular & Phrasen",
        subtitle = "${allLearnContent.sumOf { it.phrases.size }} Phrasen • ${allLearnContent.size} Themen",
        themes = allLearnContent
    ),
    LearnCategory(
        id = "schreiben",
        title = "Schreiben B1",
        subtitle = "${SchreibenLearnContent.phrases.size} Phrasen • ÖSD Musterlösungen", // Updated subtitle
        themes = listOf(SchreibenLearnContent)
    ),
    LearnCategory(
        id = "sprechen",
        title = "Sprechen",
        subtitle = "${SprechenContent.phrases.size} Phrasen • Präsentation & Gespräche",
        themes = listOf(SprechenContent)
    ),
    LearnCategory(
        id = "grammatik",
        title = "Grammatik",
        subtitle = "${GrammatikContent.phrases.size} Phrasen • Zeiten, Fälle & Struktur",
        themes = listOf(GrammatikContent)
    )
)

fun hexToColor(hex: String): Color {
    val clean = hex.removePrefix("#")
    val r = clean.substring(0, 2).toInt(16) / 255f
    val g = clean.substring(2, 4).toInt(16) / 255f
    val b = clean.substring(4, 6).toInt(16) / 255f
    return Color(r, g, b)
}