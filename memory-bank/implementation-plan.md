# Implementation Plan - Deutsch B1 Exam
## Version 2.2 — Icon Fix + API Complete Rewrite

---

## 🔥 PHASE 0 — CURRENT SPRINT: Fix Icons + Fix All APIs

---

### Fix 0.A — Provider Icon on Skill Selector Screen

**File to edit**: `ui/exams/ProviderSkillSelectorScreen.kt` (the screen that shows a provider card at the top + 4 skill buttons below)

**The Problem**: This screen renders the provider header card using a broken image source (URL or wrong drawable ID), producing a white square. The provider list screen works correctly.

**Step 1 — Add icon + color mapping to your `ExamProvider` enum or data class**:

In whatever file defines `ExamProvider` (likely `data/ExamData.kt` or `navigation/AppNavGraph.kt`), add two extension functions:

```kotlin
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

@DrawableRes
fun ExamProvider.toIconRes(): Int = when (this) {
    ExamProvider.GOETHE -> R.drawable.ic_goethe    // your existing Goethe drawable
    ExamProvider.OESD   -> R.drawable.ic_oesd      // your existing ÖSD drawable
    ExamProvider.TELC   -> R.drawable.ic_telc      // your existing TELC drawable
}

fun ExamProvider.toBrandColor(): Color = when (this) {
    ExamProvider.GOETHE -> Color(0xFF00A550)   // Goethe green
    ExamProvider.OESD   -> Color(0xFF0070C0)   // ÖSD blue
    ExamProvider.TELC   -> Color(0xFFE2001A)   // TELC red
}
```

**Note**: The drawable names (`ic_goethe`, `ic_oesd`, `ic_telc`) must match EXACTLY what exists in `res/drawable/`. Check the actual names used in `ExamProviderListScreen.kt` — use those same names here.

**Step 2 — Fix the header card in `ProviderSkillSelectorScreen.kt`**:

Find the provider header card composable (the card at top of the skill selector screen). It currently has something like:
```kotlin
// BROKEN — probably something like this:
AsyncImage(model = provider.iconUrl, ...)
// OR
Image(painter = painterResource(R.drawable.ic_placeholder), ...)
```

Replace with:
```kotlin
// FIXED
Box(
    modifier = Modifier
        .size(52.dp)
        .clip(RoundedCornerShape(14.dp))
        .background(provider.toBrandColor()),
    contentAlignment = Alignment.Center
) {
    Image(
        painter = painterResource(provider.toIconRes()),
        contentDescription = provider.displayName,
        modifier = Modifier.size(36.dp)
    )
}
```

**Step 3 — Verify** by running the app, tapping Goethe → should show green background with Goethe logo. Tap ÖSD → blue + ÖSD logo. Tap TELC → red + TELC logo.

---

### Fix 0.B — Translation: Replace with Google Translate gtx Endpoint

**Why**: MyMemory URL construction was failing due to Retrofit base URL conflict. The Google Translate `gtx` (guest) endpoint requires NO API key, is used by hundreds of open-source Android apps, and is extremely reliable.

**Endpoint**:
```
GET https://translate.googleapis.com/translate_a/single?client=gtx&sl={sourceLang}&tl={targetLang}&dt=t&q={urlEncodedText}
```

**Response format** (raw JSON array — NOT a JSON object, so standard Gson won't work directly):
```json
[[["Day","Tag",null,null,10]],null,"de"]
```
The translated text is at `response[0][0][0]`.

**Step 1 — Use OkHttpClient directly, NOT Retrofit interface for this call**:

The problem with the previous implementation is that Retrofit was built with a fixed base URL (e.g. `https://api.mymemory.translated.net/`) and using `@Url` with a completely different domain (translate.googleapis.com) can be unreliable depending on Retrofit version. Use raw OkHttp instead:

```kotlin
// In ApiRepository.kt — add this:

private val httpClient = OkHttpClient.Builder()
    .connectTimeout(15, TimeUnit.SECONDS)
    .readTimeout(15, TimeUnit.SECONDS)
    .build()

suspend fun translate(text: String, sourceLang: String, targetLang: String): ApiResult<String> {
    return withContext(Dispatchers.IO) {
        try {
            val encoded = URLEncoder.encode(text, "UTF-8")
            val url = "https://translate.googleapis.com/translate_a/single" +
                      "?client=gtx&sl=$sourceLang&tl=$targetLang&dt=t&q=$encoded"
            
            val request = Request.Builder().url(url).get().build()
            val response = httpClient.newCall(request).execute()
            
            if (!response.isSuccessful) {
                return@withContext ApiResult.Error("Übersetzung fehlgeschlagen (HTTP ${response.code}).")
            }
            
            val body = response.body?.string()
                ?: return@withContext ApiResult.Error("Leere Antwort vom Server.")
            
            // Parse the nested JSON array manually
            val jsonArray = JSONArray(body)
            val translationsArray = jsonArray.getJSONArray(0)
            val sb = StringBuilder()
            for (i in 0 until translationsArray.length()) {
                val part = translationsArray.getJSONArray(i)
                if (!part.isNull(0)) sb.append(part.getString(0))
            }
            
            val result = sb.toString().trim()
            if (result.isBlank()) ApiResult.Error("Keine Übersetzung gefunden.")
            else ApiResult.Success(result)
            
        } catch (e: Exception) {
            ApiResult.Error("Keine Verbindung. Bitte erneut versuchen.")
        }
    }
}
```

**Step 2 — Update TranslationScreen.kt**:

```kotlin
// Language pair data
data class LangPair(val label: String, val source: String, val target: String, val emoji: String)

val langPairs = listOf(
    LangPair("Deutsch → English", "de", "en", "🇩🇪→🇬🇧"),
    LangPair("English → Deutsch", "en", "de", "🇬🇧→🇩🇪"),
    LangPair("Deutsch → العربية", "de", "ar", "🇩🇪→🇸🇦"),
    LangPair("Deutsch → Français", "de", "fr", "🇩🇪→🇫🇷"),
    LangPair("Deutsch → Türkçe", "de", "tr", "🇩🇪→🇹🇷"),
)

// In the composable:
var selectedPair by remember { mutableStateOf(langPairs[0]) }
var inputText by remember { mutableStateOf("") }
var result by remember { mutableStateOf<ApiResult<String>>(ApiResult.Loading) }
var hasSearched by remember { mutableStateOf(false) }
val scope = rememberCoroutineScope()
val clipboardManager = LocalClipboardManager.current
val context = LocalContext.current

// Language pair selector — horizontal scrollable row of chips
LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
    items(langPairs) { pair ->
        FilterChip(
            selected = pair == selectedPair,
            onClick = { selectedPair = pair },
            label = { Text(pair.emoji + " " + pair.label, fontSize = 12.sp) }
        )
    }
}

// Input TextField
OutlinedTextField(
    value = inputText,
    onValueChange = { if (it.length <= 300) inputText = it },
    placeholder = { Text("Text eingeben…") },
    modifier = Modifier.fillMaxWidth(),
    minLines = 3,
    maxLines = 6
)
Text("${inputText.length}/300", modifier = Modifier.align(Alignment.End), style = MaterialTheme.typography.labelSmall)

// Translate button
Button(
    onClick = {
        hasSearched = true
        result = ApiResult.Loading
        scope.launch {
            result = apiRepository.translate(inputText, selectedPair.source, selectedPair.target)
        }
    },
    enabled = inputText.isNotBlank()
) { Text("Übersetzen") }

// Result display
if (hasSearched) {
    when (val r = result) {
        is ApiResult.Loading -> ShimmerEffect()
        is ApiResult.Success -> {
            GlassCard {
                Row(verticalAlignment = Alignment.Top) {
                    Text(r.data, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
                    IconButton(onClick = {
                        clipboardManager.setText(AnnotatedString(r.data))
                        Toast.makeText(context, "Kopiert!", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Kopieren")
                    }
                }
            }
        }
        is ApiResult.Error -> ErrorStateCard(r.message) {
            hasSearched = true; result = ApiResult.Loading
            scope.launch { result = apiRepository.translate(inputText, selectedPair.source, selectedPair.target) }
        }
    }
}
```

---

### Fix 0.C — Verb Conjugation: Full Offline Replacement

**Why**: The Render.com free tier server sleeps after 15 minutes of inactivity. It takes 30–60 seconds to cold-start — longer than OkHttp's default timeout. The API CANNOT be relied upon for a production app on a free tier. The solution is to go **fully offline**.

**Approach**: Bundle a JSON asset `assets/verbs/conjugations.json` containing the 200 most commonly used German B1 verbs (all verbs that appear in B1 exams) with their complete conjugations. This is ~300 KB — well within acceptable asset size.

**Step 1 — Create `assets/verbs/conjugations.json`**:

This is the single most important file to get right. Below is the full schema and a sample of the data. The complete file must include all 200 verbs.

```json
{
  "verbs": [
    {
      "infinitiv": "gehen",
      "auxiliary": "sein",
      "praesens": {"ich":"gehe","du":"gehst","er/sie/es":"geht","wir":"gehen","ihr":"geht","sie/Sie":"gehen"},
      "praeteritum": {"ich":"ging","du":"gingst","er/sie/es":"ging","wir":"gingen","ihr":"gingt","sie/Sie":"gingen"},
      "perfekt": {"ich":"bin gegangen","du":"bist gegangen","er/sie/es":"ist gegangen","wir":"sind gegangen","ihr":"seid gegangen","sie/Sie":"sind gegangen"},
      "konjunktiv2": {"ich":"ginge","du":"gingest","er/sie/es":"ginge","wir":"gingen","ihr":"ginget","sie/Sie":"gingen"},
      "imperativ": {"du":"geh!","ihr":"geht!","Sie":"Gehen Sie!"}
    },
    {
      "infinitiv": "sein",
      "auxiliary": "sein",
      "praesens": {"ich":"bin","du":"bist","er/sie/es":"ist","wir":"sind","ihr":"seid","sie/Sie":"sind"},
      "praeteritum": {"ich":"war","du":"warst","er/sie/es":"war","wir":"waren","ihr":"wart","sie/Sie":"waren"},
      "perfekt": {"ich":"bin gewesen","du":"bist gewesen","er/sie/es":"ist gewesen","wir":"sind gewesen","ihr":"seid gewesen","sie/Sie":"sind gewesen"},
      "konjunktiv2": {"ich":"wäre","du":"wärst","er/sie/es":"wäre","wir":"wären","ihr":"wäret","sie/Sie":"wären"},
      "imperativ": {"du":"sei!","ihr":"seid!","Sie":"Seien Sie!"}
    },
    {
      "infinitiv": "haben",
      "auxiliary": "haben",
      "praesens": {"ich":"habe","du":"hast","er/sie/es":"hat","wir":"haben","ihr":"habt","sie/Sie":"haben"},
      "praeteritum": {"ich":"hatte","du":"hattest","er/sie/es":"hatte","wir":"hatten","ihr":"hattet","sie/Sie":"hatten"},
      "perfekt": {"ich":"habe gehabt","du":"hast gehabt","er/sie/es":"hat gehabt","wir":"haben gehabt","ihr":"habt gehabt","sie/Sie":"haben gehabt"},
      "konjunktiv2": {"ich":"hätte","du":"hättest","er/sie/es":"hätte","wir":"hätten","ihr":"hättet","sie/Sie":"hätten"},
      "imperativ": {"du":"hab!","ihr":"habt!","Sie":"Haben Sie!"}
    },
    {
      "infinitiv": "werden",
      "auxiliary": "sein",
      "praesens": {"ich":"werde","du":"wirst","er/sie/es":"wird","wir":"werden","ihr":"werdet","sie/Sie":"werden"},
      "praeteritum": {"ich":"wurde","du":"wurdest","er/sie/es":"wurde","wir":"wurden","ihr":"wurdet","sie/Sie":"wurden"},
      "perfekt": {"ich":"bin geworden","du":"bist geworden","er/sie/es":"ist geworden","wir":"sind geworden","ihr":"seid geworden","sie/Sie":"sind geworden"},
      "konjunktiv2": {"ich":"würde","du":"würdest","er/sie/es":"würde","wir":"würden","ihr":"würdet","sie/Sie":"würden"},
      "imperativ": {"du":"werde!","ihr":"werdet!","Sie":"Werden Sie!"}
    },
    {
      "infinitiv": "können",
      "auxiliary": "haben",
      "praesens": {"ich":"kann","du":"kannst","er/sie/es":"kann","wir":"können","ihr":"könnt","sie/Sie":"können"},
      "praeteritum": {"ich":"konnte","du":"konntest","er/sie/es":"konnte","wir":"konnten","ihr":"konntet","sie/Sie":"konnten"},
      "perfekt": {"ich":"habe gekonnt","du":"hast gekonnt","er/sie/es":"hat gekonnt","wir":"haben gekonnt","ihr":"habt gekonnt","sie/Sie":"haben gekonnt"},
      "konjunktiv2": {"ich":"könnte","du":"könntest","er/sie/es":"könnte","wir":"könnten","ihr":"könntet","sie/Sie":"könnten"},
      "imperativ": {"du":"-","ihr":"-","Sie":"-"}
    },
    {
      "infinitiv": "müssen",
      "auxiliary": "haben",
      "praesens": {"ich":"muss","du":"musst","er/sie/es":"muss","wir":"müssen","ihr":"müsst","sie/Sie":"müssen"},
      "praeteritum": {"ich":"musste","du":"musstest","er/sie/es":"musste","wir":"mussten","ihr":"musstet","sie/Sie":"mussten"},
      "perfekt": {"ich":"habe gemusst","du":"hast gemusst","er/sie/es":"hat gemusst","wir":"haben gemusst","ihr":"habt gemusst","sie/Sie":"haben gemusst"},
      "konjunktiv2": {"ich":"müsste","du":"müsstest","er/sie/es":"müsste","wir":"müssten","ihr":"müsstet","sie/Sie":"müssten"},
      "imperativ": {"du":"-","ihr":"-","Sie":"-"}
    },
    {
      "infinitiv": "wollen",
      "auxiliary": "haben",
      "praesens": {"ich":"will","du":"willst","er/sie/es":"will","wir":"wollen","ihr":"wollt","sie/Sie":"wollen"},
      "praeteritum": {"ich":"wollte","du":"wolltest","er/sie/es":"wollte","wir":"wollten","ihr":"wolltet","sie/Sie":"wollten"},
      "perfekt": {"ich":"habe gewollt","du":"hast gewollt","er/sie/es":"hat gewollt","wir":"haben gewollt","ihr":"habt gewollt","sie/Sie":"haben gewollt"},
      "konjunktiv2": {"ich":"wollte","du":"wolltest","er/sie/es":"wollte","wir":"wollten","ihr":"wolltet","sie/Sie":"wollten"},
      "imperativ": {"du":"-","ihr":"-","Sie":"-"}
    },
    {
      "infinitiv": "sollen",
      "auxiliary": "haben",
      "praesens": {"ich":"soll","du":"sollst","er/sie/es":"soll","wir":"sollen","ihr":"sollt","sie/Sie":"sollen"},
      "praeteritum": {"ich":"sollte","du":"solltest","er/sie/es":"sollte","wir":"sollten","ihr":"solltet","sie/Sie":"sollten"},
      "perfekt": {"ich":"habe gesollt","du":"hast gesollt","er/sie/es":"hat gesollt","wir":"haben gesollt","ihr":"habt gesollt","sie/Sie":"haben gesollt"},
      "konjunktiv2": {"ich":"sollte","du":"solltest","er/sie/es":"sollte","wir":"sollten","ihr":"solltet","sie/Sie":"sollten"},
      "imperativ": {"du":"-","ihr":"-","Sie":"-"}
    },
    {
      "infinitiv": "dürfen",
      "auxiliary": "haben",
      "praesens": {"ich":"darf","du":"darfst","er/sie/es":"darf","wir":"dürfen","ihr":"dürft","sie/Sie":"dürfen"},
      "praeteritum": {"ich":"durfte","du":"durftest","er/sie/es":"durfte","wir":"durften","ihr":"durftet","sie/Sie":"durften"},
      "perfekt": {"ich":"habe gedurft","du":"hast gedurft","er/sie/es":"hat gedurft","wir":"haben gedurft","ihr":"habt gedurft","sie/Sie":"haben gedurft"},
      "konjunktiv2": {"ich":"dürfte","du":"dürftest","er/sie/es":"dürfte","wir":"dürften","ihr":"dürftet","sie/Sie":"dürften"},
      "imperativ": {"du":"-","ihr":"-","Sie":"-"}
    },
    {
      "infinitiv": "machen",
      "auxiliary": "haben",
      "praesens": {"ich":"mache","du":"machst","er/sie/es":"macht","wir":"machen","ihr":"macht","sie/Sie":"machen"},
      "praeteritum": {"ich":"machte","du":"machtest","er/sie/es":"machte","wir":"machten","ihr":"machtet","sie/Sie":"machten"},
      "perfekt": {"ich":"habe gemacht","du":"hast gemacht","er/sie/es":"hat gemacht","wir":"haben gemacht","ihr":"habt gemacht","sie/Sie":"haben gemacht"},
      "konjunktiv2": {"ich":"machte","du":"machtest","er/sie/es":"machte","wir":"machten","ihr":"machtet","sie/Sie":"machten"},
      "imperativ": {"du":"mach!","ihr":"macht!","Sie":"Machen Sie!"}
    },
    {
      "infinitiv": "kommen",
      "auxiliary": "sein",
      "praesens": {"ich":"komme","du":"kommst","er/sie/es":"kommt","wir":"kommen","ihr":"kommt","sie/Sie":"kommen"},
      "praeteritum": {"ich":"kam","du":"kamst","er/sie/es":"kam","wir":"kamen","ihr":"kamt","sie/Sie":"kamen"},
      "perfekt": {"ich":"bin gekommen","du":"bist gekommen","er/sie/es":"ist gekommen","wir":"sind gekommen","ihr":"seid gekommen","sie/Sie":"sind gekommen"},
      "konjunktiv2": {"ich":"käme","du":"kämest","er/sie/es":"käme","wir":"kämen","ihr":"kämet","sie/Sie":"kämen"},
      "imperativ": {"du":"komm!","ihr":"kommt!","Sie":"Kommen Sie!"}
    },
    {
      "infinitiv": "sagen",
      "auxiliary": "haben",
      "praesens": {"ich":"sage","du":"sagst","er/sie/es":"sagt","wir":"sagen","ihr":"sagt","sie/Sie":"sagen"},
      "praeteritum": {"ich":"sagte","du":"sagtest","er/sie/es":"sagte","wir":"sagten","ihr":"sagtet","sie/Sie":"sagten"},
      "perfekt": {"ich":"habe gesagt","du":"hast gesagt","er/sie/es":"hat gesagt","wir":"haben gesagt","ihr":"habt gesagt","sie/Sie":"haben gesagt"},
      "konjunktiv2": {"ich":"sagte","du":"sagtest","er/sie/es":"sagte","wir":"sagten","ihr":"sagtet","sie/Sie":"sagten"},
      "imperativ": {"du":"sag!","ihr":"sagt!","Sie":"Sagen Sie!"}
    },
    {
      "infinitiv": "wissen",
      "auxiliary": "haben",
      "praesens": {"ich":"weiß","du":"weißt","er/sie/es":"weiß","wir":"wissen","ihr":"wisst","sie/Sie":"wissen"},
      "praeteritum": {"ich":"wusste","du":"wusstest","er/sie/es":"wusste","wir":"wussten","ihr":"wusstet","sie/Sie":"wussten"},
      "perfekt": {"ich":"habe gewusst","du":"hast gewusst","er/sie/es":"hat gewusst","wir":"haben gewusst","ihr":"habt gewusst","sie/Sie":"haben gewusst"},
      "konjunktiv2": {"ich":"wüsste","du":"wüsstest","er/sie/es":"wüsste","wir":"wüssten","ihr":"wüsstet","sie/Sie":"wüssten"},
      "imperativ": {"du":"wisse!","ihr":"wisst!","Sie":"Wissen Sie!"}
    },
    {
      "infinitiv": "sehen",
      "auxiliary": "haben",
      "praesens": {"ich":"sehe","du":"siehst","er/sie/es":"sieht","wir":"sehen","ihr":"seht","sie/Sie":"sehen"},
      "praeteritum": {"ich":"sah","du":"sahst","er/sie/es":"sah","wir":"sahen","ihr":"saht","sie/Sie":"sahen"},
      "perfekt": {"ich":"habe gesehen","du":"hast gesehen","er/sie/es":"hat gesehen","wir":"haben gesehen","ihr":"habt gesehen","sie/Sie":"haben gesehen"},
      "konjunktiv2": {"ich":"sähe","du":"sähest","er/sie/es":"sähe","wir":"sähen","ihr":"sähet","sie/Sie":"sähen"},
      "imperativ": {"du":"sieh!","ihr":"seht!","Sie":"Sehen Sie!"}
    },
    {
      "infinitiv": "sprechen",
      "auxiliary": "haben",
      "praesens": {"ich":"spreche","du":"sprichst","er/sie/es":"spricht","wir":"sprechen","ihr":"sprecht","sie/Sie":"sprechen"},
      "praeteritum": {"ich":"sprach","du":"sprachst","er/sie/es":"sprach","wir":"sprachen","ihr":"spracht","sie/Sie":"sprachen"},
      "perfekt": {"ich":"habe gesprochen","du":"hast gesprochen","er/sie/es":"hat gesprochen","wir":"haben gesprochen","ihr":"habt gesprochen","sie/Sie":"haben gesprochen"},
      "konjunktiv2": {"ich":"spräche","du":"sprächest","er/sie/es":"spräche","wir":"sprächen","ihr":"sprächet","sie/Sie":"sprächen"},
      "imperativ": {"du":"sprich!","ihr":"sprecht!","Sie":"Sprechen Sie!"}
    },
    {
      "infinitiv": "arbeiten",
      "auxiliary": "haben",
      "praesens": {"ich":"arbeite","du":"arbeitest","er/sie/es":"arbeitet","wir":"arbeiten","ihr":"arbeitet","sie/Sie":"arbeiten"},
      "praeteritum": {"ich":"arbeitete","du":"arbeitetest","er/sie/es":"arbeitete","wir":"arbeiteten","ihr":"arbeitetet","sie/Sie":"arbeiteten"},
      "perfekt": {"ich":"habe gearbeitet","du":"hast gearbeitet","er/sie/es":"hat gearbeitet","wir":"haben gearbeitet","ihr":"habt gearbeitet","sie/Sie":"haben gearbeitet"},
      "konjunktiv2": {"ich":"arbeitete","du":"arbeitetest","er/sie/es":"arbeitete","wir":"arbeiteten","ihr":"arbeitetet","sie/Sie":"arbeiteten"},
      "imperativ": {"du":"arbeite!","ihr":"arbeitet!","Sie":"Arbeiten Sie!"}
    },
    {
      "infinitiv": "lernen",
      "auxiliary": "haben",
      "praesens": {"ich":"lerne","du":"lernst","er/sie/es":"lernt","wir":"lernen","ihr":"lernt","sie/Sie":"lernen"},
      "praeteritum": {"ich":"lernte","du":"lerntest","er/sie/es":"lernte","wir":"lernten","ihr":"lerntet","sie/Sie":"lernten"},
      "perfekt": {"ich":"habe gelernt","du":"hast gelernt","er/sie/es":"hat gelernt","wir":"haben gelernt","ihr":"habt gelernt","sie/Sie":"haben gelernt"},
      "konjunktiv2": {"ich":"lernte","du":"lerntest","er/sie/es":"lernte","wir":"lernten","ihr":"lerntet","sie/Sie":"lernten"},
      "imperativ": {"du":"lern!","ihr":"lernt!","Sie":"Lernen Sie!"}
    },
    {
      "infinitiv": "fahren",
      "auxiliary": "sein",
      "praesens": {"ich":"fahre","du":"fährst","er/sie/es":"fährt","wir":"fahren","ihr":"fahrt","sie/Sie":"fahren"},
      "praeteritum": {"ich":"fuhr","du":"fuhrst","er/sie/es":"fuhr","wir":"fuhren","ihr":"fuhrt","sie/Sie":"fuhren"},
      "perfekt": {"ich":"bin gefahren","du":"bist gefahren","er/sie/es":"ist gefahren","wir":"sind gefahren","ihr":"seid gefahren","sie/Sie":"sind gefahren"},
      "konjunktiv2": {"ich":"führe","du":"führest","er/sie/es":"führe","wir":"führen","ihr":"führet","sie/Sie":"führen"},
      "imperativ": {"du":"fahr!","ihr":"fahrt!","Sie":"Fahren Sie!"}
    },
    {
      "infinitiv": "wohnen",
      "auxiliary": "haben",
      "praesens": {"ich":"wohne","du":"wohnst","er/sie/es":"wohnt","wir":"wohnen","ihr":"wohnt","sie/Sie":"wohnen"},
      "praeteritum": {"ich":"wohnte","du":"wohntest","er/sie/es":"wohnte","wir":"wohnten","ihr":"wohntet","sie/Sie":"wohnten"},
      "perfekt": {"ich":"habe gewohnt","du":"hast gewohnt","er/sie/es":"hat gewohnt","wir":"haben gewohnt","ihr":"habt gewohnt","sie/Sie":"haben gewohnt"},
      "konjunktiv2": {"ich":"wohnte","du":"wohntest","er/sie/es":"wohnte","wir":"wohnten","ihr":"wohntet","sie/Sie":"wohnten"},
      "imperativ": {"du":"wohn!","ihr":"wohnt!","Sie":"Wohnen Sie!"}
    },
    {
      "infinitiv": "kaufen",
      "auxiliary": "haben",
      "praesens": {"ich":"kaufe","du":"kaufst","er/sie/es":"kauft","wir":"kaufen","ihr":"kauft","sie/Sie":"kaufen"},
      "praeteritum": {"ich":"kaufte","du":"kauftest","er/sie/es":"kaufte","wir":"kauften","ihr":"kauftet","sie/Sie":"kauften"},
      "perfekt": {"ich":"habe gekauft","du":"hast gekauft","er/sie/es":"hat gekauft","wir":"haben gekauft","ihr":"habt gekauft","sie/Sie":"haben gekauft"},
      "konjunktiv2": {"ich":"kaufte","du":"kauftest","er/sie/es":"kaufte","wir":"kauften","ihr":"kauftet","sie/Sie":"kauften"},
      "imperativ": {"du":"kauf!","ihr":"kauft!","Sie":"Kaufen Sie!"}
    }
  ]
}
```

The full file must contain at minimum these 200 verbs:
sein, haben, werden, können, müssen, wollen, sollen, dürfen, mögen, möchten, gehen, kommen, machen, sagen, wissen, sehen, sprechen, arbeiten, lernen, fahren, wohnen, kaufen, lesen, schreiben, hören, verstehen, denken, glauben, finden, zeigen, brauchen, helfen, geben, nehmen, bringen, stellen, legen, setzen, fragen, antworten, erklären, besuchen, reisen, warten, bleiben, heißen, leben, kennen, laufen, schlafen, essen, trinken, kochen, spielen, studieren, bezahlen, öffnen, schließen, anfangen, aufhören, einladen, empfehlen, vergessen, erinnern, passieren, gefallen, interessieren, entscheiden, versuchen, schaffen, erreichen, abholen, anrufen, aufstehen, ausgehen, fernsehen, mitbringen, nachdenken, teilnehmen, vorbereiten, weitermachen, zurückkommen, aussehen, bedeuten, beschreiben, bestellen, bewerben, diskutieren, einsteigen, erhalten, erlauben, erzählen, existieren, feiern, folgen, funktionieren, gewinnen, informieren, kontrollieren, lösen, mieten, organisieren, planen, reparieren, reservieren, sammeln, schicken, tragen, übersetzen, unterschreiben, verbessern, verbinden, vergleichen, verlieren, verstehen, verwenden, vorstellen, wechseln, wiederholen, zeigen, zuhören, zustimmen, ablehnen, abmachen, ankommen, aufnehmen, aufräumen, ausfüllen, aussteigen, beachten, beantragen, beenden, begegnen, beginnen, begrüßen, benutzen, berichten, beschweren, bestätigen, bezahlen, darstellen, durchführen, einigen, einladen, empfangen, entspannen, entwickeln, erledigen, erscheinen, erwarten, finanzieren, fortsetzen, gewöhnen, hinweisen, kennenlernen, klingen, konzentrieren, lächeln, nachfragen, nennen, notieren, nutzen, packen, passieren, probieren, prüfen, rechnen, reichen, richten, rufen, scheitern, schützen, suchen, tauschen, teilen, überlegen, überprüfen, umziehen, unterhalten, unterstützen, verabreden, verändern, vereinbaren, verpassen, versprechen, vortragen, wählen, wegfahren, weiterleiten

**Step 2 — VerbRepository.kt** (new file):

```kotlin
// data/VerbRepository.kt
object VerbRepository {
    private var verbs: List<VerbConjugation>? = null

    fun loadVerbs(context: Context): List<VerbConjugation> {
        if (verbs != null) return verbs!!
        val json = context.assets.open("verbs/conjugations.json").bufferedReader().readText()
        val type = object : TypeToken<VerbDataWrapper>() {}.type
        verbs = Gson().fromJson<VerbDataWrapper>(json, type).verbs
        return verbs!!
    }

    fun searchVerb(context: Context, query: String): VerbConjugation? {
        val q = query.trim().lowercase()
        return loadVerbs(context).find { it.infinitiv.lowercase() == q }
    }

    fun getSuggestions(context: Context, prefix: String): List<String> {
        val p = prefix.trim().lowercase()
        if (p.length < 2) return emptyList()
        return loadVerbs(context)
            .filter { it.infinitiv.startsWith(p) }
            .map { it.infinitiv }
            .take(8)
    }
}

data class VerbDataWrapper(val verbs: List<VerbConjugation>)

data class VerbConjugation(
    val infinitiv: String,
    val auxiliary: String,
    val praesens: Map<String, String>,
    val praeteritum: Map<String, String>,
    val perfekt: Map<String, String>,
    val konjunktiv2: Map<String, String>,
    val imperativ: Map<String, String>
)
```

**Step 3 — Update VerbConjugationScreen.kt**:

```kotlin
@Composable
fun VerbConjugationScreen(context: Context = LocalContext.current) {
    var inputText by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<VerbConjugation?>(null) }
    var notFound by remember { mutableStateOf(false) }
    var suggestions by remember { mutableStateOf<List<String>>(emptyList()) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Verbkonjugation", style = MaterialTheme.typography.headlineMedium)
        Text("Alle Zeitformen · 200 B1-Verben (offline)", style = MaterialTheme.typography.bodySmall,
             color = Color.White.copy(alpha = 0.5f))

        Spacer(Modifier.height(16.dp))

        // Search row
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = inputText,
                onValueChange = {
                    inputText = it
                    notFound = false
                    suggestions = VerbRepository.getSuggestions(context, it)
                },
                placeholder = { Text("Verb eingeben (z.B. gehen)") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    result = VerbRepository.searchVerb(context, inputText)
                    notFound = result == null
                    suggestions = emptyList()
                })
            )
            Button(
                onClick = {
                    result = VerbRepository.searchVerb(context, inputText)
                    notFound = result == null
                    suggestions = emptyList()
                },
                enabled = inputText.isNotBlank()
            ) { Text("Konjugieren") }
        }

        // Autocomplete suggestions
        if (suggestions.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxWidth().heightIn(max = 200.dp)) {
                items(suggestions) { suggestion ->
                    Text(
                        text = suggestion,
                        modifier = Modifier.fillMaxWidth().clickable {
                            inputText = suggestion
                            result = VerbRepository.searchVerb(context, suggestion)
                            notFound = result == null
                            suggestions = emptyList()
                        }.padding(horizontal = 16.dp, vertical = 10.dp)
                    )
                    Divider(color = Color.White.copy(alpha = 0.1f))
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Results
        when {
            result != null -> {
                val verb = result!!
                // Auxiliary badge
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(verb.infinitiv, style = MaterialTheme.typography.headlineSmall)
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (verb.auxiliary == "sein") Color(0xFF0A84FF).copy(alpha=0.2f) else Color(0xFF30D158).copy(alpha=0.2f)
                    ) {
                        Text(
                            "+ ${verb.auxiliary}",
                            modifier = Modifier.padding(horizontal=8.dp, vertical=2.dp),
                            style = MaterialTheme.typography.labelMedium,
                            color = if (verb.auxiliary == "sein") Color(0xFF0A84FF) else Color(0xFF30D158)
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Tense tabs
                var selectedTenseIndex by remember { mutableStateOf(0) }
                val tenses = listOf("Präsens", "Präteritum", "Perfekt", "Konj. II", "Imperativ")
                val tenseMaps = listOf(verb.praesens, verb.praeteritum, verb.perfekt, verb.konjunktiv2, verb.imperativ)

                ScrollableTabRow(selectedTabIndex = selectedTenseIndex, edgePadding = 0.dp) {
                    tenses.forEachIndexed { i, name ->
                        Tab(selected = selectedTenseIndex == i, onClick = { selectedTenseIndex = i }) {
                            Text(name, modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp), fontSize = 13.sp)
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Conjugation table
                val currentMap = tenseMaps[selectedTenseIndex]
                GlassCard {
                    Column {
                        currentMap.entries.forEachIndexed { index, (person, form) ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(person, color = Color.White.copy(alpha = 0.55f), style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(0.45f))
                                Text(form, color = Color.White, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold), modifier = Modifier.weight(0.55f))
                            }
                            if (index < currentMap.size - 1)
                                Divider(color = Color.White.copy(alpha = 0.07f), modifier = Modifier.padding(horizontal = 8.dp))
                        }
                    }
                }
            }
            notFound -> {
                ErrorStateCard(
                    message = "\"$inputText\" nicht gefunden.\nVersuche: gehen, machen, kommen, sein, haben",
                    buttonLabel = "Löschen"
                ) { inputText = ""; notFound = false }
            }
            else -> {
                // Initial empty state
                GlassCard {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("⚡", fontSize = 40.sp)
                        Spacer(Modifier.height(8.dp))
                        Text("Verb oben eingeben", style = MaterialTheme.typography.bodyLarge, color = Color.White.copy(alpha = 0.7f))
                        Text("z.B. gehen, machen, sein, haben, werden", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.4f), textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}
```

---

### Fix 0.D — Dictionary: Offline Browse + Online DictionaryAPI.dev

**Why**: Wiktionary's REST API returns complex nested HTML that requires additional parsing and can fail for compound German words (like "Abbaumaschinen"). Replace the online lookup with `api.dictionaryapi.dev` which is simpler to parse, though note it returns English-language definitions for German words (a definition of the German word in English). This is perfectly acceptable for B1 exam prep purposes.

**Alternative endpoint** (simpler, more reliable):
```
GET https://api.dictionaryapi.dev/api/v2/entries/de/{word}
```
Note: This API provides English definitions of German words. Response format is simple standard JSON.

**If dictionaryapi.dev also fails for rare words**, fall back to showing the word's entry from the offline 5000-word list with a "Wort gefunden — keine Online-Definition verfügbar" message.

**Data Classes**:
```kotlin
data class DictApiEntry(
    val word: String,
    val phonetic: String?,
    val meanings: List<DictMeaning>
)

data class DictMeaning(
    val partOfSpeech: String,
    val definitions: List<DictDefinition>
)

data class DictDefinition(
    val definition: String,
    val example: String?
)
```

**ApiRepository lookup function**:
```kotlin
suspend fun lookupWord(word: String): ApiResult<List<DictApiEntry>> {
    return withContext(Dispatchers.IO) {
        try {
            val encoded = URLEncoder.encode(word.lowercase().trim(), "UTF-8")
            val url = "https://api.dictionaryapi.dev/api/v2/entries/de/$encoded"
            val request = Request.Builder().url(url).get().build()
            val response = httpClient.newCall(request).execute()

            when (response.code) {
                200 -> {
                    val body = response.body?.string() ?: return@withContext ApiResult.Error("Leere Antwort.")
                    val type = object : TypeToken<List<DictApiEntry>>() {}.type
                    val entries: List<DictApiEntry> = Gson().fromJson(body, type)
                    ApiResult.Success(entries)
                }
                404 -> ApiResult.Error("\"$word\" nicht im Wörterbuch gefunden.")
                else -> ApiResult.Error("Fehler ${response.code}. Bitte erneut versuchen.")
            }
        } catch (e: Exception) {
            ApiResult.Error("Keine Verbindung. Bitte erneut versuchen.")
        }
    }
}
```

**DictionaryScreen.kt** — final structure:
```kotlin
// Tab 0: Browse (offline)
// - Load words_5000.json via AssetLoader on LaunchedEffect(Unit)
// - Show in LazyColumn with search TextField at top (filters in memory, instant)
// - Tapping a word → switches to Tab 1 (Lookup) and auto-searches that word

// Tab 1: Lookup (online)
// - TextField + Suchen button
// - 500ms debounce OR explicit button tap
// - ApiResult states: Loading → shimmer | Success → entry cards | Error → error card
// - Each entry card: partOfSpeech chip + definition list + example (if available)
// - 🔖 bookmark icon → SavedWordDao.insert()
```

---

## ✅ Validation Checklist for Phase 0

| Fix | Test |
|---|---|
| Provider icon fix | Tap Goethe → green logo in header card. Tap ÖSD → blue. Tap TELC → red. |
| Translation | Type "Guten Morgen" → tap Übersetzen → "Good Morning" appears. Switch to de→ar → Arabic result. Airplane mode → error card shown. Copy button → paste works. |
| Verb Conjugation | Type "gehen" → Konjugieren → Präsens: ich gehe, du gehst... Type "müssen" → umlaut handled. Type "xyz" → "nicht gefunden" message. |
| Dictionary Browse | Scroll Browse tab → German words shown alphabetically. Type "Buch" in search → filters correctly without network. |
| Dictionary Lookup | Search "machen" in Lookup tab → definition(s) in English shown. Search "Abbaumaschinen" → 404 error handled gracefully. |

---

## Phases 1–5
*(unchanged from implementation-plan v2.1 — phases 1–5 remain the same)*
