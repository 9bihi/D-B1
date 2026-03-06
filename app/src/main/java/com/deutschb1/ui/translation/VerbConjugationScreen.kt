package com.deutschb1.ui.translation

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
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
import com.deutschb1.network.VerbConjugation
import kotlinx.coroutines.launch

@Composable
fun VerbConjugationScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    var verbInput by remember { mutableStateOf("") }
    var apiResult by remember { mutableStateOf<ApiResult<VerbConjugation>?>(null) }
    var isFirstLoad by remember { mutableStateOf(true) }

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF0A0A0A), Color(0xFF111827))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
            .padding(horizontal = 16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(Modifier.height(56.dp))

            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(
                        "Verbkonjugation",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        "Alle Zeitformen · 8.000+ Verben",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // Input Area
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E).copy(alpha = 0.65f))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = verbInput,
                        onValueChange = { verbInput = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Verb eingeben (z.B. gehen)", color = Color.Gray) },
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
                    Button(
                        onClick = {
                            if (verbInput.isBlank()) return@Button
                            scope.launch {
                                apiResult = ApiResult.Loading
                                apiResult = ApiRepository.conjugateVerb(verbInput)
                                isFirstLoad = false
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5856D6))
                    ) {
                        Text("Konjugieren")
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Result Display
            when (val res = apiResult) {
                is ApiResult.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = Color(0xFF5856D6))
                        Spacer(Modifier.height(16.dp))
                        if (isFirstLoad) {
                            Card(
                                modifier = Modifier.padding(horizontal = 20.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF5856D6).copy(alpha = 0.1f))
                            ) {
                                Text(
                                    "🔄 Server startet… bitte ~30 Sekunden warten",
                                    modifier = Modifier.padding(16.dp),
                                    color = Color(0xFF8E8CE1),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
                is ApiResult.Success -> {
                    ConjugationTabs(res.data)
                }
                is ApiResult.Error -> {
                    com.deutschb1.ui.components.ApiErrorCard(res.message, onRetry = {
                        scope.launch {
                            apiResult = ApiResult.Loading
                            apiResult = ApiRepository.conjugateVerb(verbInput)
                        }
                    })
                }
                null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Geben Sie oben ein Verb ein, um zu beginnen", color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Composable
fun ConjugationTabs(conjugation: VerbConjugation) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tenses = listOf("Präsens", "Präteritum", "Perfekt", "Konjunktiv II", "Imperativ")
    
    Column(modifier = Modifier.fillMaxSize()) {
        // Verb Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E).copy(alpha = 0.8f))
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Brush.linearGradient(listOf(Color(0xFF5856D6), Color(0xFF8E8CE1)))),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Bolt, contentDescription = null, tint = Color.White)
                }
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(
                        conjugation.verb.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Surface(
                        color = Color.White.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            "Hilfsverb: ${conjugation.auxiliary}",
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            color = Color.LightGray,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.Transparent,
            contentColor = Color.White,
            edgePadding = 0.dp,
            divider = {},
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = Color(0xFF5856D6)
                )
            }
        ) {
            tenses.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        val currentForms = when (selectedTab) {
            0 -> conjugation.praesens
            1 -> conjugation.praeteritum
            2 -> conjugation.perfekt
            3 -> conjugation.konjunktiv2
            else -> conjugation.imperativ
        }

        ConjugationTable(currentForms)
    }
}

@Composable
fun ConjugationTable(forms: Map<String, String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E).copy(alpha = 0.65f))
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            forms.forEach { (pronoun, form) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        pronoun,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        form,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(2f)
                    )
                }
                HorizontalDivider(color = Color.White.copy(alpha = 0.05f))
            }
        }
    }
}
