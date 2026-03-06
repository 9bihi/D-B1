package com.deutschb1.ui.exam.sprechen

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deutschb1.data.ExamContent
import com.deutschb1.data.SprechenTask
import com.deutschb1.ui.theme.IosPurple
import com.deutschb1.ui.theme.IosGreen
import com.deutschb1.ui.theme.IosOrange
import kotlinx.coroutines.delay

enum class SprechenPhase { PREP, SPEAKING, DONE }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SprechenScreen(exam: ExamContent, navController: NavController) {
    var taskIndex by remember { mutableStateOf(0) }
    val tasks = exam.sprechenTasks
    val task = tasks.getOrNull(taskIndex) ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Sprechen", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium)
                        Text(
                            "${exam.provider.displayName} • Aufgabe ${taskIndex + 1}/${tasks.size}",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Task selector tabs
            if (tasks.size > 1) {
                ScrollableTabRow(
                    selectedTabIndex = taskIndex,
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = IosPurple,
                    edgePadding = 0.dp
                ) {
                    tasks.forEachIndexed { index, t ->
                        Tab(
                            selected = taskIndex == index,
                            onClick = { taskIndex = index },
                            text = { Text("Aufgabe ${t.taskNumber}") }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            SprechenTaskContent(task = task)
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun SprechenTaskContent(task: SprechenTask) {
    var phase by remember(task.taskNumber) { mutableStateOf(SprechenPhase.PREP) }
    var secondsLeft by remember(task.taskNumber) { mutableStateOf(task.prepTimeSec) }
    var timerRunning by remember(task.taskNumber) { mutableStateOf(false) }
    var tipsExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(timerRunning, phase) {
        if (timerRunning && secondsLeft > 0) {
            delay(1000)
            secondsLeft--
            if (secondsLeft == 0) {
                if (phase == SprechenPhase.PREP) {
                    phase = SprechenPhase.SPEAKING
                    secondsLeft = task.speakTimeSec
                } else if (phase == SprechenPhase.SPEAKING) {
                    phase = SprechenPhase.DONE
                    timerRunning = false
                }
            }
        }
    }

    // Topic card
    Card(
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = IosPurple.copy(alpha = 0.1f)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(task.title, fontWeight = FontWeight.Bold, color = IosPurple,
                style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "📌 Anweisung",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(task.instruction, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = IosPurple.copy(alpha = 0.12f)),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Text(
                    task.topics.joinToString(separator = "\n") { "• $it" },
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Timer section
    val timerColor = when (phase) {
        SprechenPhase.PREP -> IosOrange
        SprechenPhase.SPEAKING -> IosPurple
        SprechenPhase.DONE -> IosGreen
    }

    Card(
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = timerColor.copy(alpha = 0.08f)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (phase) {
                SprechenPhase.PREP -> {
                    Text("⏱ Vorbereitungszeit", style = MaterialTheme.typography.headlineSmall,
                        color = IosOrange, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    TimerCircle(seconds = secondsLeft, totalSeconds = task.prepTimeSec, color = IosOrange)
                    Spacer(modifier = Modifier.height(16.dp))
                    if (!timerRunning) {
                        Button(
                            onClick = { timerRunning = true },
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = IosOrange)
                        ) { Text("⏱ Vorbereitung starten") }
                    } else {
                        Text("Jetzt vorbereiten...", color = IosOrange,
                            style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                    }
                }
                SprechenPhase.SPEAKING -> {
                    Text("🎤 Sprechzeit", style = MaterialTheme.typography.headlineSmall,
                        color = IosPurple, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    TimerCircle(seconds = secondsLeft, totalSeconds = task.speakTimeSec, color = IosPurple)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Jetzt sprechen!", color = IosPurple,
                        style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                }
                SprechenPhase.DONE -> {
                    Text("✅ Fertig!", fontSize = 40.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Gut gemacht!", style = MaterialTheme.typography.headlineMedium,
                        color = IosGreen, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedButton(
                        onClick = {
                            phase = SprechenPhase.PREP
                            secondsLeft = task.prepTimeSec
                            timerRunning = false
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) { Text("Nochmal") }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Tips expand
    Card(
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("💡 Sprechtipps", fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.headlineSmall)
                IconButton(onClick = { tipsExpanded = !tipsExpanded }) {
                    Icon(if (tipsExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown, null)
                }
            }
            if (tipsExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                task.tips.forEach { tip ->
                    Row(modifier = Modifier.padding(vertical = 3.dp)) {
                        Text("•", color = IosPurple, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(tip, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Composable
fun TimerCircle(seconds: Int, totalSeconds: Int, color: Color) {
    val progress = seconds.toFloat() / totalSeconds.toFloat()
    val minutes = seconds / 60
    val secs = seconds % 60

    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(140.dp)) {
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxSize(),
            color = color,
            strokeWidth = 8.dp,
            trackColor = color.copy(alpha = 0.15f)
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "%d:%02d".format(minutes, secs),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text("min", style = MaterialTheme.typography.labelSmall, color = color.copy(0.7f))
        }
    }
}
