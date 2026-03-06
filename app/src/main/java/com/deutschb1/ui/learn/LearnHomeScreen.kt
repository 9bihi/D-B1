package com.deutschb1.ui.learn

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deutschb1.data.LearnCategory
import com.deutschb1.data.allCategories
import com.deutschb1.navigation.Screen

// Predefined gradient pairs for icon circles
val categoryGradients = listOf(
    listOf(Color(0xFF667EEA), Color(0xFF764BA2)), // Purple
    listOf(Color(0xFFF0933B), Color(0xFFF5576C)), // Orange-Red
    listOf(Color(0xFF4CAF50), Color(0xFF2E7D32)), // Green
    listOf(Color(0xFF00BCD4), Color(0xFF3F51B5)), // Cyan-Blue
    listOf(Color(0xFFFF6B6B), Color(0xFF556270))  // Red-Gray
)

// Emoji mapping for categories
val categoryEmoji = mapOf(
    "vocab_phrases" to "📚",
    "schreiben_b1" to "✍️",
    "konnektoren" to "🔗",
    "grammatik" to "📐"
)

@Composable
fun LearnHomeScreen(navController: NavController) {
    val totalPhrases = allCategories.sumOf { cat ->
        cat.themes.sumOf { it.phrases.size }
    }
    val totalThemes = allCategories.sumOf { it.themes.size }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 60.dp, bottom = 80.dp, start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header with glass card
        item {
            Column {
                Text(
                    "Learn",
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    "Deutsch B1 lernen",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                GlassStatCard(
                    totalPhrases = totalPhrases,
                    totalThemes = totalThemes
                )
            }
        }

        // Study Tools
        item {
            Spacer(Modifier.height(8.dp))
            Text(
                "🔥 Study Tools",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                "Master vocabulary with active recall",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Spacer(Modifier.height(10.dp))

            ApiToolCard(
                emoji = "🗂️",
                title = "Flashcards",
                subtitle = "Spaced Repetition · 10 Topics",
                gradient = listOf(Color(0xFFFF5F6D), Color(0xFFFFC371)),
                onClick = { navController.navigate(Screen.FlashcardDecks.route) }
            )
            Spacer(Modifier.height(10.dp))
            ApiToolCard(
                emoji = "🔖",
                title = "Word Vault",
                subtitle = "Saved Vocabulary",
                gradient = listOf(Color(0xFF11998E), Color(0xFF38EF7D)),
                onClick = { navController.navigate(Screen.WordVault.route) }
            )
            Spacer(Modifier.height(10.dp))
            ApiToolCard(
                emoji = "📖",
                title = "Geschichten",
                subtitle = "Graded Stories · A2-B1",
                gradient = listOf(Color(0xFF4AC1FF), Color(0xFF7956FF)),
                onClick = { navController.navigate(Screen.GeschichtenList.route) }
            )
            Spacer(Modifier.height(10.dp))
            ApiToolCard(
                emoji = "📐",
                title = "Grammatik Drills",
                subtitle = "Interactive Exercises",
                gradient = listOf(Color(0xFFF093FB), Color(0xFFF5576C)),
                onClick = { navController.navigate(Screen.GrammarDrillTopics.route) }
            )
            Spacer(Modifier.height(10.dp))
            ApiToolCard(
                emoji = "🎮",
                title = "Spiele",
                subtitle = "Gamified Practice",
                gradient = listOf(Color(0xFF84FAB0), Color(0xFF8FD3F4)),
                onClick = { navController.navigate(Screen.SpieleMenu.route) }
            )
        }

        // Categories Header
        item {
            Spacer(Modifier.height(8.dp))
            Text(
                "📚 Lernmaterial",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                "Structured phrases and grammar",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Spacer(Modifier.height(4.dp))
        }

        // Category cards (glass style)
        itemsIndexed(allCategories) { index, category ->
            val gradient = categoryGradients[index % categoryGradients.size]
            val emoji = categoryEmoji[category.id] ?: "📘"

            GlassCategoryCard(
                category = category,
                gradient = gradient,
                emoji = emoji,
                onClick = {
                    navController.navigate(Screen.LearnCategory.createRoute(category.id))
                }
            )
        }

    }
}

@Composable
fun GlassStatCard(totalPhrases: Int, totalThemes: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1C1C1E).copy(alpha = 0.65f)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        "Lernstatistik",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Text(
                        "$totalPhrases Phrasen • $totalThemes Themen",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun GlassCategoryCard(
    category: LearnCategory,
    gradient: List<Color>,
    emoji: String,
    onClick: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.98f else 1f,
        animationSpec = tween(100),
        label = "scale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable {
                pressed = true
                onClick()
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1C1C1E).copy(alpha = 0.65f)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // Emoji in colored circle
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(gradient)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = emoji,
                            fontSize = 24.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = category.title,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = category.subtitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.3f),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun ApiToolCard(
    emoji: String,
    title: String,
    subtitle: String,
    gradient: List<Color>,
    onClick: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.98f else 1f,
        animationSpec = tween(100),
        label = "apiCardScale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable {
                pressed = true
                onClick()
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1C1C1E).copy(alpha = 0.65f)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Brush.linearGradient(gradient)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(emoji, fontSize = 24.sp)
                    }
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(
                            title,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.3f),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}