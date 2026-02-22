package com.deutschb1.ui.exam.schreiben

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deutschb1.data.ExamContent
import com.deutschb1.data.SchreibenTask
import com.deutschb1.ui.theme.IosBlue
import com.deutschb1.ui.theme.IosGreen
import com.deutschb1.ui.theme.IosOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchreibenScreen(exam: ExamContent, navController: NavController) {
    var selectedTaskIndex by remember { mutableStateOf(0) }
    val tasks = exam.schreibenTasks
    val currentTask = tasks.getOrNull(selectedTaskIndex) ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Schreiben", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium)
                        Text(exam.provider.displayName, style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
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
            // Task tabs
            if (tasks.size > 1) {
                ScrollableTabRow(
                    selectedTabIndex = selectedTaskIndex,
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = IosOrange,
                    edgePadding = 0.dp
                ) {
                    tasks.forEachIndexed { index, task ->
                        Tab(
                            selected = selectedTaskIndex == index,
                            onClick = { selectedTaskIndex = index },
                            text = { Text("Aufgabe ${task.taskNumber}") }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            SchreibenTaskContent(task = currentTask)
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun SchreibenTaskContent(task: SchreibenTask) {
    var userText by remember(task.taskNumber) { mutableStateOf("") }
    var hintsExpanded by remember { mutableStateOf(false) }
    val wordCount = userText.trim().split("\\s+".toRegex()).count { it.isNotEmpty() }
    val progress = (wordCount.toFloat() / task.minWords.toFloat()).coerceIn(0f, 1f)
    val progressColor = when {
        wordCount < task.minWords * 0.5 -> IosOrange
        wordCount < task.minWords -> IosBlue
        else -> IosGreen
    }

    // Prompt card
    Card(
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = IosOrange.copy(alpha = 0.1f)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(task.title, fontWeight = FontWeight.Bold, color = IosOrange,
                style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(12.dp))
            Text("✍️ Aufgabenstellung", style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(6.dp))
            Text(task.prompt, style = MaterialTheme.typography.bodyMedium, lineHeight = 22.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Mindestanzahl Wörter: ${task.minWords}",
                style = MaterialTheme.typography.labelMedium,
                color = IosOrange, fontWeight = FontWeight.SemiBold
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Hints collapsible
    Card(
        modifier = Modifier.fillMaxWidth().animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("💡 Schreibtipps", fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.headlineSmall)
                IconButton(onClick = { hintsExpanded = !hintsExpanded }) {
                    Icon(
                        if (hintsExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        "Tipps"
                    )
                }
            }
            if (hintsExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                task.hints.forEach { hint ->
                    Row(modifier = Modifier.padding(vertical = 3.dp)) {
                        Text("•", color = IosOrange, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(hint, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Word counter
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Wörter: ", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(
            "$wordCount / ${task.minWords}",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = progressColor
        )
        Spacer(modifier = Modifier.width(12.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.weight(1f).height(6.dp).clip(RoundedCornerShape(3.dp)),
            color = progressColor,
            trackColor = progressColor.copy(alpha = 0.2f)
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = userText,
        onValueChange = { userText = it },
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 220.dp),
        placeholder = { Text("Hier schreiben...") },
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = IosOrange,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(0.3f),
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
        )
    )

    Spacer(modifier = Modifier.height(12.dp))
    if (wordCount >= task.minWords) {
        Card(
            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = IosGreen.copy(alpha = 0.1f)),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Text(
                "✅ Wortanzahl erreicht! Gut gemacht.",
                modifier = Modifier.padding(12.dp),
                color = IosGreen, fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
