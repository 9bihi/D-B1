package com.deutschb1.ui.exam.hoeren

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deutschb1.data.ExamContent
import com.deutschb1.data.HoerenPart
import com.deutschb1.data.MultipleChoiceQuestion
import com.deutschb1.ui.theme.IosGreen
import com.deutschb1.ui.theme.IosRed
import com.deutschb1.ui.theme.IosTeal
import com.deutschb1.navigation.Screen
import com.deutschb1.ui.exam.LastResultsProvider
import com.deutschb1.ui.exam.QuestionResult
import com.deutschb1.data.ExamSkill
import com.deutschb1.data.db.DatabaseProvider
import com.deutschb1.data.db.entities.UserExamResult
import com.deutschb1.ui.components.AudioPlayerBar
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HoerenScreen(exam: ExamContent, navController: NavController) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    
    val player = remember {
        ExoPlayer.Builder(context).build()
    }

    DisposableEffect(Unit) {
        onDispose {
            player.release()
        }
    }

    var currentPartIndex by remember { mutableStateOf(0) }
    var showTranscript by remember { mutableStateOf(false) }
    var showResults by remember { mutableStateOf(false) }
    var showPartResults by remember { mutableStateOf(false) }
    val answers = remember { mutableStateMapOf<Int, Int>() }
    val parts = exam.hoerenParts
    val currentPart = parts.getOrNull(currentPartIndex) ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Hören", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium)
                        Text(
                            "${exam.provider.displayName} • Teil ${currentPartIndex + 1}/${parts.size}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Zurück")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        if (false) { // showResults is no longer used for inline display
            HoerenResults(parts = parts, answers = answers, onRestart = {
                answers.clear()
                currentPartIndex = 0
                showResults = false
                showTranscript = false
                showPartResults = false
            }, navController = navController)
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Progress bar
                LinearProgressIndicator(
                    progress = { (currentPartIndex + 1).toFloat() / parts.size },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = IosTeal,
                    trackColor = IosTeal.copy(alpha = 0.2f)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Title card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            currentPart.title,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.headlineSmall,
                            color = IosTeal
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            currentPart.instruction,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                LaunchedEffect(currentPartIndex) {
                    currentPart?.audioAssetPath?.let { path ->
                        if (path.isNotBlank()) {
                            val mediaItem = MediaItem.fromUri("asset:///$path")
                            player.setMediaItem(mediaItem)
                            player.prepare()
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                AudioPlayerBar(player = player)

                Spacer(modifier = Modifier.height(16.dp))

                // Transcript toggle button
                OutlinedButton(
                    onClick = { showTranscript = !showTranscript },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(if (showTranscript) "📄 Transkript ausblenden" else "📄 Transkript anzeigen")
                }

                AnimatedVisibility(
                    visible = showTranscript,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = IosTeal.copy(alpha = 0.08f)),
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Text(
                            currentPart.transcript,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodySmall,
                            lineHeight = 22.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text("Fragen", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))

                // Questions
                currentPart.questions.forEach { question ->
                    HoerenQuestionCard(
                        question = question,
                        selectedIndex = answers[question.id],
                        onSelect = { answers[question.id] = it },
                        showCorrect = showPartResults
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Bottom row: correction + next/evaluate
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Correction button
                    OutlinedButton(
                        onClick = { showPartResults = !showPartResults },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(if (showPartResults) "Korrektur ausblenden" else "Teil korrigieren")
                    }

                    if (currentPartIndex < parts.size - 1) {
                        Button(
                            onClick = {
                                currentPartIndex++
                                showTranscript = false
                                showPartResults = false
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = IosTeal)
                        ) {
                            Text("Weiter")
                        }
                    } else {
                        Button(
                            onClick = { 
                                val allQuestions = parts.flatMap { it.questions }
                                val correct = allQuestions.count { answers[it.id] == it.correctIndex }
                                
                                // Save to DB
                                scope.launch {
                                    val dao = DatabaseProvider.getResultDao(context)
                                    dao.insertResult(
                                        UserExamResult(
                                            examId = exam.id,
                                            provider = exam.provider,
                                            skill = ExamSkill.HOEREN,
                                            score = correct,
                                            totalQuestions = allQuestions.size,
                                            completedAt = System.currentTimeMillis()
                                        )
                                    )
                                }

                                LastResultsProvider.lastResults = allQuestions.map { q ->
                                    QuestionResult(
                                        questionText = q.text,
                                        userAnswer = q.options.getOrNull(answers[q.id] ?: -1),
                                        correctAnswer = q.options[q.correctIndex],
                                        isCorrect = answers[q.id] == q.correctIndex,
                                        explanation = q.explanation
                                    )
                                }
                                
                                navController.navigate(
                                    Screen.ResultSummary.createRoute(
                                        score = correct,
                                        total = allQuestions.size,
                                        skill = ExamSkill.HOEREN
                                    )
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = IosGreen)
                        ) {
                            Text("Auswerten")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun HoerenQuestionCard(
    question: MultipleChoiceQuestion,
    selectedIndex: Int?,
    onSelect: (Int) -> Unit,
    showCorrect: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(question.text, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(12.dp))

            question.options.forEachIndexed { index, option ->
                val isSelected = selectedIndex == index
                val isCorrect = showCorrect && index == question.correctIndex
                val isWrong = showCorrect && isSelected && index != question.correctIndex

                val backgroundColor = when {
                    isCorrect && isSelected -> IosGreen.copy(alpha = 0.2f)
                    isCorrect && !isSelected -> IosGreen.copy(alpha = 0.1f)
                    isWrong -> IosRed.copy(alpha = 0.2f)
                    else -> Color.Transparent
                }
                val borderColor = when {
                    isCorrect && isSelected -> IosGreen
                    isCorrect && !isSelected -> IosGreen.copy(alpha = 0.5f)
                    isWrong -> IosRed
                    isSelected -> IosTeal
                    else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(backgroundColor)
                        .border(1.5.dp, borderColor, RoundedCornerShape(10.dp))
                        .clickable { onSelect(index) }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) IosTeal else Color.Transparent)
                            .border(1.5.dp, if (isSelected) IosTeal else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isSelected) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.White))
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(option, style = MaterialTheme.typography.bodyMedium)

                    if (showCorrect && index == question.correctIndex) {
                        Spacer(modifier = Modifier.weight(1f))
                        Text("✓", color = IosGreen, fontWeight = FontWeight.Bold)
                    } else if (showCorrect && isSelected && index != question.correctIndex) {
                        Spacer(modifier = Modifier.weight(1f))
                        Text("✗", color = IosRed, fontWeight = FontWeight.Bold)
                    }
                }
                if (index < question.options.size - 1) Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}

@Composable
fun HoerenResults(
    parts: List<HoerenPart>,
    answers: Map<Int, Int>,
    onRestart: () -> Unit,
    navController: NavController
) {
    val allQuestions = parts.flatMap { it.questions }
    val correct = allQuestions.count { answers[it.id] == it.correctIndex }
    val total = allQuestions.size
    val percentage = if (total > 0) (correct * 100) / total else 0
    val passed = percentage >= 60

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(if (passed) "🎉" else "📚", fontSize = 64.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            if (passed) "Bestanden!" else "Weiter üben!",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = if (passed) IosGreen else IosRed
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "$correct / $total richtig ($percentage%)",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Per‑question breakdown
        allQuestions.forEach { question ->
            val userAnswer = answers[question.id]
            val isCorrect = userAnswer == question.correctIndex
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isCorrect) IosGreen.copy(alpha = 0.1f) else IosRed.copy(alpha = 0.1f)
                ),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(if (isCorrect) "✅" else "❌", fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            question.text,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (!isCorrect) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            "Richtig: ${question.options[question.correctIndex]}",
                            style = MaterialTheme.typography.labelMedium,
                            color = IosGreen,
                            fontWeight = FontWeight.SemiBold
                        )
                        if (question.explanation.isNotEmpty()) {
                            Text(
                                question.explanation,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(
                onClick = { navController.popBackStack() },
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Zurück")
            }
            Button(
                onClick = onRestart,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = IosTeal)
            ) {
                Text("Nochmal")
            }
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}