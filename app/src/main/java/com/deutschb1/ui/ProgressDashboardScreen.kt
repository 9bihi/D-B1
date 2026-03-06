package com.deutschb1.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deutschb1.data.db.DatabaseProvider
import com.deutschb1.data.db.entities.StudySession
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressDashboardScreen(navController: NavController) {
    val context = LocalContext.current
    val db = DatabaseProvider.getDatabase(context)
    val sessionDao = db.studySessionDao()
    
    val sessions by sessionDao.getAllSessions().collectAsState(initial = emptyList())
    val totalTimeSeconds by sessionDao.getTotalStudyTime().collectAsState(initial = 0)
    val uniqueDays by sessionDao.getUniqueStudyDays().collectAsState(initial = 0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lernfortschritt", color = Color.White) },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Text(
                    "Meine Statistik",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        label = "Lern-Tage",
                        value = "$uniqueDays",
                        icon = "🔥",
                        color = Color(0xFFFF9F43)
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        label = "Gesamtzeit",
                        value = "${(totalTimeSeconds ?: 0) / 60}m",
                        icon = "⏱️",
                        color = Color(0xFF54A0FF)
                    )
                }
            }

            item {
                Text(
                    "Letzte Aktivitäten",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            if (sessions.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Noch keine Aktivitäten aufgezeichnet.", color = Color.Gray)
                    }
                }
            } else {
                items(sessions) { session ->
                    SessionItem(session = session)
                }
            }
        }
    }
}

@Composable
fun StatCard(modifier: Modifier = Modifier, label: String, value: String, icon: String, color: Color) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(icon, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = color)
            Text(label, style = MaterialTheme.typography.labelMedium, color = Color.Gray)
        }
    }
}

@Composable
fun SessionItem(session: StudySession) {
    val dateFormat = SimpleDateFormat("dd. MMM, HH:mm", Locale.getDefault())
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF1C1C1E).copy(alpha = 0.6f))
            .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(
                        listOf(Color(0xFF667EEA), Color(0xFF764BA2))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            val emoji = when (session.moduleType) {
                "Exam" -> "📝"
                "Flashcard" -> "🗂️"
                "Story" -> "📖"
                "Grammar" -> "📐"
                "Game" -> "🎮"
                else -> "✨"
            }
            Text(emoji, fontSize = 20.sp)
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = session.moduleType,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = dateFormat.format(Date(session.completedAt)),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
        
        Column(horizontalAlignment = Alignment.End) {
            if (session.itemsCompleted > 0) {
                Text(
                    text = "${session.itemsCompleted} Items",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF00C853)
                )
            }
            Text(
                text = "${session.durationSeconds / 60}m ${session.durationSeconds % 60}s",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}
