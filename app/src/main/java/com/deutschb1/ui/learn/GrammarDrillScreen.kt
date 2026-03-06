package com.deutschb1.ui.learn

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
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
import com.deutschb1.data.GrammarDrillData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GrammarDrillScreen(topicId: String, navController: NavController) {
    val topic = GrammarDrillData.topics.find { it.id == topicId } ?: return
    var currentStep by remember { mutableStateOf(0) } // 0 = Explanation, 1..N = Drills, N+1 = Results
    val totalSteps = topic.drills.size + 2
    var score by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(topic.title, color = Color.White) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
        ) {
            // Progress Bar
            LinearProgressIndicator(
                progress = { currentStep.toFloat() / (totalSteps - 1) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .animateContentSize(),
                color = Color(0xFF667EEA),
                trackColor = Color.White.copy(alpha = 0.1f)
            )
            Spacer(modifier = Modifier.height(24.dp))

            AnimatedContent(
                targetState = currentStep,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut()
                    } else {
                        slideInHorizontally { -it } + fadeIn() togetherWith slideOutHorizontally { it } + fadeOut()
                    }
                },
                label = "drillStepAnimation"
            ) { step ->
                when {
                    step == 0 -> {
                        GrammarExplanation(
                            topic = topic,
                            onNext = { currentStep++ }
                        )
                    }
                    step <= topic.drills.size -> {
                        val drill = topic.drills[step - 1]
                        GrammarDrillItem(
                            drill = drill,
                            onAnswered = { isCorrect ->
                                if (isCorrect) score++
                                currentStep++
                            }
                        )
                    }
                    else -> {
                        DrillResult(
                            score = score,
                            total = topic.drills.size,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GrammarExplanation(topic: GrammarDrillData.GrammarTopic, onNext: () -> Unit) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E))
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFF667EEA))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Regel", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.White)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    topic.explanation,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text("Beispiele:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFF667EEA))
                topic.examples.forEach { example ->
                    Text(
                        "• $example",
                        modifier = Modifier.padding(top = 8.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF667EEA)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Übung starten", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun GrammarDrillItem(drill: GrammarDrillData.GrammarDrill, onAnswered: (Boolean) -> Unit) {
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var showExplanation by remember { mutableStateOf(false) }

    Column {
        Text(
            text = drill.question,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))

        drill.options.forEachIndexed { index, option ->
            val isSelected = selectedOption == index
            val isCorrect = index == drill.correctIndex
            
            val borderColor = when {
                showExplanation && isCorrect -> Color.Green
                showExplanation && isSelected && !isCorrect -> Color.Red
                isSelected -> Color(0xFF667EEA)
                else -> Color.White.copy(alpha = 0.1f)
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable(enabled = !showExplanation) { selectedOption = index },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = option,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        if (showExplanation) {
            Column(modifier = Modifier.padding(vertical = 24.dp)) {
                Text(
                    "Erklärung:",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color(0xFF667EEA),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    drill.explanation,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (!showExplanation) {
                    showExplanation = true
                } else {
                    onAnswered(selectedOption == drill.correctIndex)
                }
            },
            enabled = selectedOption != null,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF667EEA)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(if (!showExplanation) "Prüfen" else "Weiter")
        }
    }
}

@Composable
fun DrillResult(score: Int, total: Int, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.CheckCircle,
            contentDescription = null,
            tint = Color.Green,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "Übung abgeschlossen!",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Du hast $score von $total Fragen richtig beantwortet.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        
        val percentage = (score.toFloat() / total) * 100
        Text(
            "${percentage.toInt()}%",
            style = MaterialTheme.typography.displayMedium,
            color = if (percentage >= 80) Color.Green else Color.Yellow,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Zurück zum Lernen", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}
