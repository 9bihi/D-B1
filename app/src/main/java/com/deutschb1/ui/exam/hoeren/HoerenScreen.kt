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
import com.deutschb1.ui.theme.IosBlue
import com.deutschb1.ui.theme.IosGreen
import com.deutschb1.ui.theme.IosRed
import com.deutschb1.ui.theme.IosTeal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HoerenScreen(exam: ExamContent, navController: NavController) {
    var currentPartIndex by remember { mutableStateOf(0) }
    var showTranscript by remember { mutableStateOf(false) }
    var showResults by remember { mutableStateOf(false) }
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
        if (showResults) {
            HoerenResults(parts = parts, answers = answers, onRestart = {
                answers.clear(); currentPartIndex = 0; showResults = false; showTranscript = false
            }, navController = navController)
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                LinearProgressIndicator(
                    progress = { (currentPartIndex + 1).toFloat() / parts.size },
                    modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)),
                    color = IosTeal, trackColor = IosTeal.copy(alpha = 0.2f)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Title card
                Card(
                    modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(currentPart.title, fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.headlineSmall, color = IosTeal)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(currentPart.instruction, style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Audio Player Mock
                AudioPlayerMock(partTitle = currentPart.title)

                Spacer(modifier = Modifier.height(12.dp))

                // Toggle transcript
                OutlinedButton(
                    onClick = { showTranscript = !showTranscript },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(if (showTranscript) "📄 Transkript ausblenden" else "📄 Transkript anzeigen")
                }

                AnimatedVisibility(visible = showTranscript, enter = fadeIn() + expandVertically(), exit = fadeOut() + shrinkVertically()) {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
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

                currentPart.questions.forEach { q ->
                    HoerenQuestionCard(q, answers[q.id]) { answers[q.id] = it }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    if (currentPartIndex < parts.size - 1) {
                        Button(onClick = { currentPartIndex++; showTranscript = false },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = IosTeal)) {
                            Text("Weiter")
                        }
                    } else {
                        Button(onClick = { showResults = true },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = IosGreen)) {
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
fun AudioPlayerMock(partTitle: String) {
    var isPlaying by remember { mutableStateOf(false) }
    val infiniteTransition = rememberInfiniteTransition(label = "audio")
    val wave by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(600), RepeatMode.Reverse), label = "wave"
    )

    Card(
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = IosTeal.copy(alpha = 0.1f)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(IosTeal)
                        .clickable { isPlaying = !isPlaying },
                    contentAlignment = Alignment.Center
                ) {
                    Text(if (isPlaying) "⏸" else "▶", fontSize = 22.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("🎧 Audio-Simulation", fontWeight = FontWeight.Bold, color = IosTeal)
                    Text(
                        if (isPlaying) "Wird abgespielt..." else "Zum Abspielen tippen",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            if (isPlaying) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(3.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    repeat(20) { i ->
                        val h = (20 + (i % 5) * 8) * wave
                        Box(modifier = Modifier.width(4.dp).height(h.dp).clip(RoundedCornerShape(2.dp)).background(IosTeal.copy(alpha = 0.7f)))
                    }
                }
            }
        }
    }
}

@Composable
fun HoerenQuestionCard(question: MultipleChoiceQuestion, selectedIndex: Int?, onSelect: (Int) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(question.text, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(12.dp))
            question.options.forEachIndexed { index, option ->
                val isSelected = selectedIndex == index
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isSelected) IosTeal.copy(alpha = 0.12f) else Color.Transparent)
                        .border(
                            if (isSelected) 1.5.dp else 1.dp,
                            if (isSelected) IosTeal else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                            RoundedCornerShape(10.dp)
                        )
                        .clickable { onSelect(index) }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(22.dp).clip(CircleShape)
                            .background(if (isSelected) IosTeal else Color.Transparent)
                            .border(1.5.dp, if (isSelected) IosTeal else MaterialTheme.colorScheme.outline.copy(0.5f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) { if (isSelected) Box(Modifier.size(8.dp).clip(CircleShape).background(Color.White)) }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(option, style = MaterialTheme.typography.bodyMedium)
                }
                if (index < question.options.size - 1) Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}

@Composable
fun HoerenResults(parts: List<HoerenPart>, answers: Map<Int, Int>, onRestart: () -> Unit, navController: NavController) {
    val allQ = parts.flatMap { it.questions }
    val correct = allQ.count { answers[it.id] == it.correctIndex }
    val total = allQ.size
    val pct = if (total > 0) (correct * 100) / total else 0
    val passed = pct >= 60

    Column(
        Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))
        Text(if (passed) "🎉" else "📚", fontSize = 64.sp)
        Spacer(Modifier.height(12.dp))
        Text(if (passed) "Bestanden!" else "Weiter üben!", style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold, color = if (passed) IosGreen else IosRed)
        Text("$correct / $total richtig ($pct%)", style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick = { navController.popBackStack() }, shape = RoundedCornerShape(12.dp)) { Text("Zurück") }
            Button(onClick = onRestart, shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = IosTeal)) { Text("Nochmal") }
        }
        Spacer(Modifier.height(100.dp))
    }
}
