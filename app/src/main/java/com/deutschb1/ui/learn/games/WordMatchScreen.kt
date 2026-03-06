package com.deutschb1.ui.learn.games

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deutschb1.data.SpielData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordMatchScreen(gameId: String, navController: NavController) {
    val wordPairs = SpielData.wordPairs[gameId] ?: return
    
    var selectedGerman by remember { mutableStateOf<String?>(null) }
    var selectedEnglish by remember { mutableStateOf<String?>(null) }
    var matchedWords by remember { mutableStateOf(setOf<String>()) }
    
    val germanList = remember { wordPairs.map { it.german }.shuffled() }
    val englishList = remember { wordPairs.map { it.english }.shuffled() }

    LaunchedEffect(selectedGerman, selectedEnglish) {
        if (selectedGerman != null && selectedEnglish != null) {
            val pair = wordPairs.find { it.german == selectedGerman && it.english == selectedEnglish }
            if (pair != null) {
                matchedWords = matchedWords + selectedGerman!!
            }
            // Add a small delay for the user to see the selection before clearing
            kotlinx.coroutines.delay(300)
            selectedGerman = null
            selectedEnglish = null
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Word Match", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Black
    ) { padding ->
        if (matchedWords.size == wordPairs.size) {
            MatchVictory(onBack = { navController.popBackStack() })
        } else {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                // German Column
                Column(modifier = Modifier.weight(1f)) {
                    Text("Deutsch", color = Color.Gray, fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                    germanList.forEach { word ->
                        val isMatched = matchedWords.contains(word)
                        val isSelected = selectedGerman == word
                        MatchCard(
                            word = word,
                            isSelected = isSelected,
                            isMatched = isMatched,
                            onClick = { if (!isMatched) selectedGerman = word }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(16.dp))

                // English Column
                Column(modifier = Modifier.weight(1f)) {
                    Text("English", color = Color.Gray, fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                    englishList.forEach { word ->
                        val originalGerman = wordPairs.find { it.english == word }?.german ?: ""
                        val isMatched = matchedWords.contains(originalGerman)
                        val isSelected = selectedEnglish == word
                        MatchCard(
                            word = word,
                            isSelected = isSelected,
                            isMatched = isMatched,
                            onClick = { if (!isMatched) selectedEnglish = word }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MatchCard(word: String, isSelected: Boolean, isMatched: Boolean, onClick: () -> Unit) {
    val backgroundColor by animateColorAsState(
        targetValue = when {
            isMatched -> Color(0xFF00C853).copy(alpha = 0.2f)
            isSelected -> Color(0xFF667EEA).copy(alpha = 0.3f)
            else -> Color(0xFF1C1C1E)
        },
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "color"
    )

    val borderColor by animateColorAsState(
        targetValue = when {
            isMatched -> Color(0xFF00C853)
            isSelected -> Color(0xFF667EEA)
            else -> Color.White.copy(alpha = 0.1f)
        },
        label = "border"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(enabled = !isMatched) { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = word,
                color = if (isMatched) Color(0xFF00C853) else Color.White,
                fontWeight = if (isSelected || isMatched) FontWeight.Bold else FontWeight.Normal,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
fun MatchVictory(onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF00C853), modifier = Modifier.size(100.dp))
        Spacer(modifier = Modifier.height(24.dp))
        Text("Ausgezeichnet!", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, color = Color.White)
        Text("Alle Wörter richtig gematcht.", color = Color.Gray, modifier = Modifier.padding(top = 8.dp))
        Spacer(modifier = Modifier.height(48.dp))
        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text("Zurück", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}
