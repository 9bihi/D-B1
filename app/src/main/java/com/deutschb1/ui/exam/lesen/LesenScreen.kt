package com.deutschb1.ui.exam.lesen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deutschb1.data.ExamContent
import com.deutschb1.data.MultipleChoiceQuestion
import com.deutschb1.data.ReadingPart
import com.deutschb1.ui.theme.IosBlue
import com.deutschb1.ui.theme.IosGreen
import com.deutschb1.ui.theme.IosRed
import com.deutschb1.navigation.Screen
import com.deutschb1.ui.exam.LastResultsProvider
import com.deutschb1.ui.exam.QuestionResult
import com.deutschb1.data.ExamSkill
import com.deutschb1.data.db.DatabaseProvider
import com.deutschb1.data.db.entities.UserExamResult
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LesenScreen(exam: ExamContent, navController: NavController) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var currentPartIndex by remember { mutableStateOf(0) }
    var showResults by remember { mutableStateOf(false) }
    val answers = remember { mutableStateMapOf<String, Int>() }

    val parts = exam.lesenParts
    val currentPart = parts.getOrNull(currentPartIndex) ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Lesen", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium)
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
            LesenResults(parts = parts, answers = answers, onRestart = {
                answers.clear()
                currentPartIndex = 0
                showResults = false
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
                    color = IosBlue,
                    trackColor = IosBlue.copy(alpha = 0.2f)
                )
                Spacer(modifier = Modifier.height(16.dp))

                ReadingPartContent(
                    part = currentPart,
                    answers = answers,
                    onAnswer = { questionId, optionIndex ->
                        answers[questionId] = optionIndex
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Navigation buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (currentPartIndex > 0) {
                        OutlinedButton(
                            onClick = { currentPartIndex-- },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Vorheriger")
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    if (currentPartIndex < parts.size - 1) {
                        Button(
                            onClick = { currentPartIndex++ },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = IosBlue)
                        ) {
                            Text("Weiter")
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, null, modifier = Modifier.size(16.dp))
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
                                            skill = ExamSkill.LESEN,
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
                                        skill = ExamSkill.LESEN
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
fun ReadingPartContent(
    part: ReadingPart,
    answers: Map<String, Int>,
    onAnswer: (String, Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(part.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineSmall, color = IosBlue)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                part.instruction,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
        }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Text passage
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "📄 Lesetext",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (!part.contextText.isNullOrBlank()) {
                Text(
                    part.contextText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 20.sp,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            Text(
                part.text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 24.sp
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
    Text("Fragen", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)
    Spacer(modifier = Modifier.height(8.dp))

    part.questions.forEach { question ->
        QuestionCard(
            question = question,
            selectedIndex = answers[question.id],
            onSelect = { onAnswer(question.id, it) }
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun QuestionCard(
    question: MultipleChoiceQuestion,
    selectedIndex: Int?,
    onSelect: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                question.text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(12.dp))
            question.options.forEachIndexed { index, option ->
                val isSelected = selectedIndex == index
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            if (isSelected) IosBlue.copy(alpha = 0.12f)
                            else Color.Transparent
                        )
                        .border(
                            width = if (isSelected) 1.5.dp else 1.dp,
                            color = if (isSelected) IosBlue else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable { onSelect(index) }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) IosBlue else Color.Transparent)
                            .border(
                                1.5.dp,
                                if (isSelected) IosBlue else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(option, style = MaterialTheme.typography.bodyMedium)
                }
                if (index < question.options.size - 1) Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}

@Composable
fun LesenResults(
    parts: List<ReadingPart>,
    answers: Map<String, Int>,
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

        // Per-question breakdown
        allQuestions.forEach { q ->
            val userAnswer = answers[q.id]
            val isCorrect = userAnswer == q.correctIndex
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
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
                        Text(q.text, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
                    }
                    if (!isCorrect) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            "Richtig: ${q.options[q.correctIndex]}",
                            style = MaterialTheme.typography.labelMedium,
                            color = IosGreen,
                            fontWeight = FontWeight.SemiBold
                        )
                        if (q.explanation.isNotEmpty()) {
                            Text(q.explanation, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick = { navController.popBackStack() }, shape = RoundedCornerShape(12.dp)) {
                Text("Zurück")
            }
            Button(onClick = onRestart, shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = IosBlue)) {
                Text("Nochmal")
            }
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}
