package com.deutschb1.ui.learn

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deutschb1.data.ApiRepository
import com.deutschb1.data.ApiResult
import kotlinx.coroutines.launch

enum class ApiScreenType { DICTIONARY, VERBS }

val TENSE_OPTIONS = listOf(
    "PRASENS" to "Präsens",
    "PERFEKT" to "Perfekt",
    "PRATERITUM" to "Präteritum",
    "FUTUR1" to "Futur I",
    "KONJUNKTIV2" to "Konjunktiv II"
)

@Composable
fun ApiListScreen(navController: NavController, type: String) {
    val screenType = when (type) {
        "verbs" -> ApiScreenType.VERBS
        else -> ApiScreenType.DICTIONARY
    }

    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    // ── Shared state
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // ── Dictionary state
    var wordList by remember { mutableStateOf<List<String>>(emptyList()) }
    var filterQuery by remember { mutableStateOf("") }
    var selectedWord by remember { mutableStateOf<String?>(null) }
    var translatedWord by remember { mutableStateOf<String?>(null) }
    var definitionText by remember { mutableStateOf<String?>(null) }
    var isLoadingDefinition by remember { mutableStateOf(false) }

    // ── Verb state
    var verbInput by remember { mutableStateOf("") }
    var selectedTense by remember { mutableStateOf("PRASENS") }
    var verbResult by remember { mutableStateOf<Map<String, String>>(emptyMap()) }
    var selectedVerb by remember { mutableStateOf<String?>(null) }
    var verbFilterQuery by remember { mutableStateOf("") }
    var isLoadingConjugation by remember { mutableStateOf(false) }

    // Load word list on first composition in DICTIONARY mode
    LaunchedEffect(screenType) {
        if (screenType == ApiScreenType.DICTIONARY && wordList.isEmpty()) {
            isLoading = true
            errorMessage = null
            val result = ApiRepository.fetchB1WordList()
            when (result) {
                is ApiResult.Success -> { wordList = result.data }
                is ApiResult.Error -> { errorMessage = "Could not load word list: ${result.message}" }
                else -> {}
            }
            isLoading = false
        }
    }

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF0A0A0A), Color(0xFF111827))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 8.dp, bottom = 100.dp, start = 20.dp, end = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // ── Header
            item {
                Spacer(Modifier.height(52.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text(
                            if (screenType == ApiScreenType.DICTIONARY) "Online Dictionary" else "Verb Conjugation",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            if (screenType == ApiScreenType.DICTIONARY) "5,000 German B1 words" else "Common B1 Verbs · All Tenses",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
                Spacer(Modifier.height(8.dp))
            }

            // ── Error display
            errorMessage?.let { err ->
                item {
                    ApiGlassCard {
                        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text("⚠️", fontSize = 20.sp)
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text(err, color = Color(0xFFFF6B6B), style = MaterialTheme.typography.bodyMedium)
                                Spacer(Modifier.height(8.dp))
                                Button(
                                    onClick = {
                                        scope.launch {
                                            errorMessage = null
                                            isLoading = true
                                            if (screenType == ApiScreenType.DICTIONARY) {
                                                val res = ApiRepository.fetchB1WordList()
                                                if (res is ApiResult.Success) {
                                                    wordList = res.data
                                                } else if (res is ApiResult.Error) {
                                                    errorMessage = res.message
                                                }
                                            }
                                            isLoading = false
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4ECDC4))
                                ) { Text("Retry") }
                            }
                        }
                    }
                }
            }

            // ── Loading spinner
            if (isLoading) {
                item {
                    Box(Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = Color(0xFF5856D6), strokeWidth = 3.dp)
                            Spacer(Modifier.height(12.dp))
                            Text("Loading data…", color = Color.Gray)
                        }
                    }
                }
            }

            // ════════════════════════════════════════════
            // DICTIONARY MODE
            // ════════════════════════════════════════════
            if (screenType == ApiScreenType.DICTIONARY && !isLoading) {

                // Search bar
                item {
                    ApiGlassCard {
                        Row(
                            Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(8.dp))
                            TextField(
                                value = filterQuery,
                                onValueChange = {
                                    filterQuery = it
                                    selectedWord = null
                                    translatedWord = null
                                    definitionText = null
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    cursorColor = Color(0xFF5856D6)
                                ),
                                placeholder = { Text("Suchen…", color = Color.Gray) },
                                singleLine = true
                            )
                        }
                    }
                }

                // Definition card for selected word
                if (selectedWord != null) {
                    item {
                        DefinitionCard(
                            word = selectedWord!!,
                            englishWord = translatedWord,
                            definition = definitionText,
                            isLoading = isLoadingDefinition
                        )
                    }
                }

                // Word list
                val filtered = if (filterQuery.isBlank()) wordList.take(80)
                else wordList.filter { it.contains(filterQuery, ignoreCase = true) }.take(80)

                items(filtered) { word ->
                    WordRow(
                        word = word,
                        isSelected = word == selectedWord,
                        onClick = {
                            if (selectedWord == word) {
                                selectedWord = null
                                translatedWord = null
                                definitionText = null
                            } else {
                                selectedWord = word
                                translatedWord = null
                                definitionText = null
                                scope.launch {
                                    isLoadingDefinition = true
                                    // Step 1: translate German word to English
                                    val transResult = ApiRepository.translateText(word, "de", "en")
                                    if (transResult is ApiResult.Success) {
                                        val englishWord = transResult.data.trim().lowercase()
                                        translatedWord = transResult.data

                                        // Step 2: look up English definition
                                        if (englishWord.isNotBlank()) {
                                            val defResult = ApiRepository.fetchWordDefinition(englishWord)
                                            definitionText = when (defResult) {
                                                is ApiResult.Success -> defResult.data
                                                is ApiResult.Error -> "Definition error: ${defResult.message}"
                                                else -> "No definition found."
                                            }
                                        } else {
                                            definitionText = "Definition unavailable."
                                        }
                                    } else if (transResult is ApiResult.Error) {
                                        translatedWord = "Translation Error"
                                        definitionText = transResult.message
                                    }
                                    isLoadingDefinition = false
                                }
                            }
                        }
                    )
                }

                if (filtered.isEmpty() && filterQuery.isNotBlank()) {
                    item {
                        Box(Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                            Text("No words matching \"$filterQuery\"", color = Color.Gray)
                        }
                    }
                }
            }

            // ════════════════════════════════════════════
            // VERBS MODE
            // ════════════════════════════════════════════
            if (screenType == ApiScreenType.VERBS && !isLoading) {

                // Tense selector at top
                item {
                    Text("Zeitform", style = MaterialTheme.typography.labelMedium, color = Color.White.copy(alpha = 0.6f))
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TENSE_OPTIONS.take(3).forEach { (code, label) ->
                            TenseChip(
                                label = label,
                                isSelected = selectedTense == code,
                                onClick = {
                                    selectedTense = code
                                    verbResult = emptyMap()
                                    // Re-fetch conjugation for selected verb with new tense
                                    selectedVerb?.let { verb ->
                                        scope.launch {
                                            isLoadingConjugation = true
                                            val result = ApiRepository.fetchVerbConjugation(verb, code)
                                            if (result is ApiResult.Success) {
                                                val v = result.data
                                                verbResult = buildMap {
                                                    if (v.ich != null) put("ich", v.ich)
                                                    if (v.du != null) put("du", v.du)
                                                    if (v.er != null) put("er/sie/es", v.er)
                                                    if (v.wir != null) put("wir", v.wir)
                                                    if (v.ihr != null) put("ihr", v.ihr)
                                                    if (v.sie != null) put("sie/Sie", v.sie)
                                                }
                                            } else if (result is ApiResult.Error) {
                                                errorMessage = "Error: ${result.message}"
                                            }
                                            isLoadingConjugation = false
                                        }
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TENSE_OPTIONS.drop(3).forEach { (code, label) ->
                            TenseChip(
                                label = label,
                                isSelected = selectedTense == code,
                                onClick = {
                                    selectedTense = code
                                    verbResult = emptyMap()
                                    selectedVerb?.let { verb ->
                                        scope.launch {
                                            isLoadingConjugation = true
                                            val result = ApiRepository.fetchVerbConjugation(verb, code)
                                            if (result is ApiResult.Success) {
                                                val v = result.data
                                                verbResult = buildMap {
                                                    if (v.ich != null) put("ich", v.ich)
                                                    if (v.du != null) put("du", v.du)
                                                    if (v.er != null) put("er/sie/es", v.er)
                                                    if (v.wir != null) put("wir", v.wir)
                                                    if (v.ihr != null) put("ihr", v.ihr)
                                                    if (v.sie != null) put("sie/Sie", v.sie)
                                                }
                                            } else if (result is ApiResult.Error) {
                                                errorMessage = "Error: ${result.message}"
                                            }
                                            isLoadingConjugation = false
                                        }
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // Verb search + manual input bar
                item {
                    ApiGlassCard {
                        Column(Modifier.padding(16.dp)) {
                            Text("Verb suchen oder eingeben", style = MaterialTheme.typography.labelMedium, color = Color.White.copy(alpha = 0.5f))
                            Spacer(Modifier.height(8.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                TextField(
                                    value = verbFilterQuery,
                                    onValueChange = { verbFilterQuery = it },
                                    modifier = Modifier.weight(1f),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.White.copy(alpha = 0.06f),
                                        unfocusedContainerColor = Color.White.copy(alpha = 0.06f),
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        focusedTextColor = Color.White,
                                        unfocusedTextColor = Color.White,
                                        cursorColor = Color(0xFF5856D6)
                                    ),
                                    placeholder = { Text("z.B. gehen, sein…", color = Color.Gray) },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                                    keyboardActions = KeyboardActions(onSearch = {
                                        focusManager.clearFocus()
                                        val query = verbFilterQuery.trim().lowercase()
                                        if (query.isNotBlank()) {
                                            selectedVerb = query
                                            verbResult = emptyMap()
                                            scope.launch {
                                                isLoadingConjugation = true
                                                errorMessage = null
                                                val result = ApiRepository.fetchVerbConjugation(query, selectedTense)
                                                if (result is ApiResult.Success) {
                                                    val v = result.data
                                                    verbResult = buildMap {
                                                        if (v.ich != null) put("ich", v.ich)
                                                        if (v.du != null) put("du", v.du)
                                                        if (v.er != null) put("er/sie/es", v.er)
                                                        if (v.wir != null) put("wir", v.wir)
                                                        if (v.ihr != null) put("ihr", v.ihr)
                                                        if (v.sie != null) put("sie/Sie", v.sie)
                                                    }
                                                    if (verbResult.isEmpty()) errorMessage = "No conjugation found for \"$query\""
                                                } else if (result is ApiResult.Error) {
                                                    errorMessage = "Error: ${result.message}"
                                                }
                                                isLoadingConjugation = false
                                            }
                                        }
                                    })
                                )
                            }
                        }
                    }
                }

                // Conjugation loading
                if (isLoadingConjugation) {
                    item {
                        Box(Modifier.fillMaxWidth().padding(20.dp), contentAlignment = Alignment.Center) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color(0xFF5856D6), strokeWidth = 2.dp)
                                Spacer(Modifier.width(12.dp))
                                Text("Loading conjugation…", color = Color.Gray)
                            }
                        }
                    }
                }

                // Conjugation result table
                if (verbResult.isNotEmpty() && selectedVerb != null) {
                    item {
                        ConjugationCard(verb = selectedVerb!!, tense = selectedTense, conjugations = verbResult)
                    }
                }

                // Section header for verb list
                item {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Alle Verben",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        "Tippe auf ein Verb um die Konjugation zu sehen",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Spacer(Modifier.height(4.dp))
                }

                // Verb list from ApiRepository.commonB1Verbs (filtered by search query)
                val filteredVerbs = if (verbFilterQuery.isBlank()) {
                    ApiRepository.commonB1Verbs
                } else {
                    ApiRepository.commonB1Verbs.filter {
                        it.contains(verbFilterQuery.trim(), ignoreCase = true)
                    }
                }

                items(filteredVerbs) { verb ->
                    WordRow(
                        word = verb,
                        isSelected = verb == selectedVerb,
                        onClick = {
                            selectedVerb = verb
                            verbFilterQuery = verb
                            verbResult = emptyMap()
                            focusManager.clearFocus()
                            scope.launch {
                                isLoadingConjugation = true
                                errorMessage = null
                                val result = ApiRepository.fetchVerbConjugation(verb, selectedTense)
                                if (result is ApiResult.Success) {
                                    val v = result.data
                                    verbResult = buildMap {
                                        if (v.ich != null) put("ich", v.ich)
                                        if (v.du != null) put("du", v.du)
                                        if (v.er != null) put("er/sie/es", v.er)
                                        if (v.wir != null) put("wir", v.wir)
                                        if (v.ihr != null) put("ihr", v.ihr)
                                        if (v.sie != null) put("sie/Sie", v.sie)
                                    }
                                    if (verbResult.isEmpty()) errorMessage = "No conjugation found for \"$verb\""
                                } else if (result is ApiResult.Error) {
                                    errorMessage = "Error: ${result.message}"
                                }
                                isLoadingConjugation = false
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun WordRow(word: String, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                Color(0xFF5856D6).copy(alpha = 0.25f)
            else
                Color(0xFF1C1C1E).copy(alpha = 0.65f)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    if (isSelected) Color(0xFF8E8CE1) else Color.White.copy(alpha = 0.1f),
                    RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(word, color = Color.White, fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal)
        }
    }
}

@Composable
fun DefinitionCard(word: String, englishWord: String?, definition: String?, isLoading: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E).copy(alpha = 0.9f))
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFF5856D6).copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            if (isLoading) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(modifier = Modifier.size(18.dp), color = Color(0xFF5856D6), strokeWidth = 2.dp)
                    Spacer(Modifier.width(12.dp))
                    Text("Translating & looking up…", color = Color.Gray)
                }
            } else {
                Column {
                    // German word
                    Text(word, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 18.sp)
                    // English translation
                    if (!englishWord.isNullOrBlank()) {
                        Spacer(Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🇬🇧 ", fontSize = 14.sp)
                            Text(
                                englishWord,
                                color = Color(0xFF8E8CE1),
                                fontWeight = FontWeight.SemiBold,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    // English definition
                    if (!definition.isNullOrBlank()) {
                        Spacer(Modifier.height(8.dp))
                        HorizontalDivider(color = Color.White.copy(alpha = 0.08f))
                        Spacer(Modifier.height(8.dp))
                        Text(definition, color = Color.LightGray, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@Composable
fun ConjugationCard(verb: String, tense: String, conjugations: Map<String, String>) {
    val tenseName = TENSE_OPTIONS.find { it.first == tense }?.second ?: tense
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E).copy(alpha = 0.9f))
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFF5856D6).copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("⚡", fontSize = 22.sp)
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(verb, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 22.sp)
                    Text(tenseName, color = Color(0xFF8E8CE1), style = MaterialTheme.typography.bodySmall)
                }
            }
            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = Color.White.copy(alpha = 0.08f))
            Spacer(Modifier.height(12.dp))
            conjugations.forEach { (pronoun, form) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(pronoun, color = Color.Gray, modifier = Modifier.weight(1f))
                    Text(form, color = Color.White, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(2f))
                }
            }
        }
    }
}

@Composable
fun TenseChip(label: String, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .background(
                if (isSelected) Color(0xFF5856D6) else Color(0xFF1C1C1E).copy(alpha = 0.65f)
            )
            .border(
                1.dp,
                if (isSelected) Color(0xFF8E8CE1) else Color.White.copy(alpha = 0.1f),
                RoundedCornerShape(10.dp)
            )
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            label,
            color = if (isSelected) Color.White else Color.Gray,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun ApiGlassCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E).copy(alpha = 0.65f)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(20.dp))
        ) {
            content()
        }
    }
}
