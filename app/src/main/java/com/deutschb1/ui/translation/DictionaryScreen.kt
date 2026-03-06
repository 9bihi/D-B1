package com.deutschb1.ui.translation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deutschb1.data.ApiRepository
import com.deutschb1.data.ApiResult
import com.deutschb1.network.WiktEntryDetail
import com.deutschb1.network.WiktionaryDefinition
import com.deutschb1.util.AssetLoader
import kotlinx.coroutines.launch

@Composable
fun DictionaryScreen(navController: NavController) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()
    
    // Offline Browsing State
    var fullWordList by remember { mutableStateOf<List<String>>(emptyList()) }
    var searchQuery by remember { mutableStateOf("") }
    val filteredList = remember(searchQuery, fullWordList) {
        if (searchQuery.isBlank()) fullWordList.take(100)
        else fullWordList.filter { it.contains(searchQuery, ignoreCase = true) }.take(100)
    }

    // Lookup State
    var lookupWord by remember { mutableStateOf("") }
    var lookupResult by remember { mutableStateOf<ApiResult<WiktionaryDefinition>?>(null) }

    // Load word list
    val context = androidx.compose.ui.platform.LocalContext.current
    LaunchedEffect(Unit) {
        fullWordList = AssetLoader.loadWordList(context)
    }

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF0A0A0A), Color(0xFF111827))
    )

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(Color.Black)) {
                Spacer(Modifier.height(48.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                    Text(
                        "Wörterbuch",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.Transparent,
                    contentColor = Color.White,
                    divider = {},
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = Color(0xFF5856D6)
                        )
                    }
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("📚 Wörterbuch") }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("🔍 Nachschlagen") }
                    )
                }
            }
        },
        containerColor = Color.Transparent
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush)
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> BrowseContent(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    words = filteredList,
                    onWordClick = { word ->
                        lookupWord = word
                        selectedTab = 1
                        scope.launch {
                            lookupResult = ApiResult.Loading
                            lookupResult = ApiRepository.lookupWord(word)
                        }
                    }
                )
                1 -> LookupContent(
                    word = lookupWord,
                    onWordChange = { lookupWord = it },
                    result = lookupResult,
                    onSearch = {
                        scope.launch {
                            lookupResult = ApiResult.Loading
                            lookupResult = ApiRepository.lookupWord(lookupWord)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BrowseContent(
    query: String,
    onQueryChange: (String) -> Unit,
    words: List<String>,
    onWordClick: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White.copy(alpha = 0.05f)),
            placeholder = { Text("Search 5,000 words...", color = Color.Gray) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            singleLine = true
        )
        Spacer(Modifier.height(16.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(words) { word ->
                DictionaryWordRow(word, onClick = { onWordClick(word) })
            }
        }
    }
}

@Composable
fun LookupContent(
    word: String,
    onWordChange: (String) -> Unit,
    result: ApiResult<WiktionaryDefinition>?,
    onSearch: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = word,
                onValueChange = onWordChange,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White.copy(alpha = 0.05f)),
                placeholder = { Text("Enter word (z.B. arbeiten)", color = Color.Gray) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                singleLine = true
            )
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = onSearch,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5856D6))
            ) {
                Text("Suchen")
            }
        }
        
        Spacer(Modifier.height(20.dp))

        when (result) {
            is ApiResult.Loading -> {
                repeat(3) {
                    com.deutschb1.ui.components.ApiShimmerCard()
                    Spacer(Modifier.height(12.dp))
                }
            }
            is ApiResult.Success -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    item {
                        Text(
                            text = result.data.word.replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    items(result.data.entries) { entry ->
                        WiktEntryCard(entry)
                    }
                }
            }
            is ApiResult.Error -> {
                com.deutschb1.ui.components.ApiErrorCard(result.message, onRetry = onSearch)
            }
            null -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Enter a word to see its definition", color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun DictionaryWordRow(word: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E).copy(alpha = 0.65f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(word, color = Color.White, fontSize = 16.sp)
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
        }
    }
}

@Composable
fun WiktEntryCard(entry: WiktEntryDetail) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E).copy(alpha = 0.8f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(20.dp))
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = Color(0xFF5856D6).copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp),
                    border = border(width = 0.5.dp, color = Color(0xFF5856D6).copy(alpha = 0.5f), shape = RoundedCornerShape(8.dp))
                ) {
                    Text(
                        entry.partOfSpeech,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = Color(0xFF8E8CE1),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { /* TODO: Bookmark */ }) {
                    Icon(Icons.Default.BookmarkBorder, contentDescription = "Save", tint = Color.Gray)
                }
            }
            
            Spacer(Modifier.height(12.dp))
            
            entry.definitions.forEachIndexed { index, def ->
                Row {
                    Text("${index + 1}.", color = Color(0xFF5856D6), fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(8.dp))
                    Text(def, color = Color.White)
                }
                Spacer(Modifier.height(8.dp))
            }

            if (entry.examples.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(color = Color.White.copy(alpha = 0.05f))
                Spacer(Modifier.height(8.dp))
                entry.examples.take(2).forEach { ex ->
                    Text(
                        "\"$ex\"",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                    Spacer(Modifier.height(4.dp))
                }
            }
        }
    }
}

