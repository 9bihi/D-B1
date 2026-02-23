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
    ESSEN("Essen & Trinken", "🍽️", "Lebensmittel, Kochen, Restaurant, Rezepte", "#FAD7A0")
}

// ─── Phrase Model ─────────────────────────────────────────────────────────────

data class LearnPhrase(
    val german: String,
    val english: String,
    val exampleSentence: String,
    val exampleTranslation: String,
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

// Add this list at the end of the file
val allCategories = listOf(
    LearnCategory(
        id = "vocab_phrases",
        title = "Vokabular & Phrasen",
        subtitle = "${allLearnContent.sumOf { it.phrases.size }} Phrasen • ${allLearnContent.size} Themen",
        themes = allLearnContent
    )
)

fun hexToColor(hex: String): Color {
    val clean = hex.removePrefix("#")
    val r = clean.substring(0, 2).toInt(16) / 255f
    val g = clean.substring(2, 4).toInt(16) / 255f
    val b = clean.substring(4, 6).toInt(16) / 255f
    return Color(r, g, b)
}