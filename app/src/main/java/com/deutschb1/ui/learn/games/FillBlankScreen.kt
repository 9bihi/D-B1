package com.deutschb1.ui.learn.games

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deutschb1.data.SpielData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FillBlankScreen(gameId: String, navController: NavController) {
    val sentences = SpielData.fillSentences[gameId] ?: return
    var currentIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var showExplanation by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var gameFinished by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lückentext", color = Color.White) },
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
        if (gameFinished) {
            GameResult(score = score, total = sentences.size, onBack = { navController.popBackStack() })
        } else {
            val sentence = sentences[currentIndex]
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp)
            ) {
                Text(
                    "Frage ${currentIndex + 1} von ${sentences.size}",
                    color = Color.Gray,
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = sentence.question,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(48.dp))

                sentence.options.forEach { option ->
                    val isSelected = selectedOption == option
                    val isCorrect = option == sentence.answer
                    
                    val borderColor = when {
                        showExplanation && isCorrect -> Color(0xFF00C853)
                        showExplanation && isSelected && !isCorrect -> Color.Red
                        isSelected -> Color(0xFF667EEA)
                        else -> Color.White.copy(alpha = 0.1f)
                    }

                    val backgroundColor = when {
                        showExplanation && isCorrect -> Color(0xFF00C853).copy(alpha = 0.1f)
                        showExplanation && isSelected && !isCorrect -> Color.Red.copy(alpha = 0.1f)
                        else -> Color(0xFF1C1C1E)
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable(enabled = !showExplanation) { selectedOption = option },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = backgroundColor)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                                .padding(20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(option, color = Color.White, fontSize = 18.sp)
                        }
                    }
                }

                if (showExplanation) {
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = sentence.explanation,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        if (!showExplanation) {
                            if (selectedOption == sentence.answer) score++
                            showExplanation = true
                        } else {
                            if (currentIndex < sentences.size - 1) {
                                currentIndex++
                                showExplanation = false
                                selectedOption = null
                            } else {
                                gameFinished = true
                            }
                        }
                    },
                    enabled = selectedOption != null,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        if (!showExplanation) "Prüfen" else "Weiter",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun GameResult(score: Int, total: Int, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF00C853), modifier = Modifier.size(100.dp))
        Spacer(modifier = Modifier.height(24.dp))
        Text("Spiel beendet!", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, color = Color.White)
        Text("Du hast $score von $total Punkten erreicht.", color = Color.Gray, modifier = Modifier.padding(top = 8.dp))
        Spacer(modifier = Modifier.height(48.dp))
        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text("Hauptmenü", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}
