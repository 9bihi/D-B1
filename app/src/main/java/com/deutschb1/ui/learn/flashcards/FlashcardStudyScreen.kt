package com.deutschb1.ui.learn.flashcards

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deutschb1.data.Flashcard
import com.deutschb1.data.FlashcardDeck
import com.deutschb1.data.db.DatabaseProvider
import com.deutschb1.data.db.entities.FlashcardProgress
import com.deutschb1.ui.theme.IosGreen
import com.deutschb1.ui.theme.IosRed
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardStudyScreen(deck: FlashcardDeck, navController: NavController) {
    var currentIndex by remember { mutableStateOf(0) }
    var isFlipped by remember { mutableStateOf(false) }
    val currentCard = deck.cards.getOrNull(currentIndex)
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    if (currentCard == null) {
        // Study complete
        StudyResult(deck = deck, onBack = { navController.popBackStack() })
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(deck.name, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Zurück")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black, titleContentColor = Color.White)
            )
        },
        containerColor = Color.Black
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Karte ${currentIndex + 1} von ${deck.cards.size}",
                color = Color.Gray,
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Card
            AnimatedContent(
                targetState = isFlipped,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
                },
                label = "flip"
            ) { flipped ->
                StudyCardView(
                    card = currentCard,
                    isFlipped = flipped,
                    onClick = { isFlipped = !isFlipped }
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Action Buttons
            if (isFlipped) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Don't Know
                    IconButton(
                        onClick = {
                            isFlipped = false
                            currentIndex++
                        },
                        modifier = Modifier.size(64.dp).clip(CircleShape).background(IosRed)
                    ) {
                        Icon(Icons.Default.Close, null, tint = Color.White)
                    }

                    // Mastered
                    IconButton(
                        onClick = {
                            scope.launch {
                                DatabaseProvider.getFlashcardDao(context).updateProgress(
                                    FlashcardProgress(cardId = currentCard.id, isMastered = true)
                                )
                            }
                            isFlipped = false
                            currentIndex++
                        },
                        modifier = Modifier.size(64.dp).clip(CircleShape).background(IosGreen)
                    ) {
                        Icon(Icons.Default.Done, null, tint = Color.White)
                    }
                }
            } else {
                Text(
                    "Tippe auf die Karte zum Umdrehen",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun StudyCardView(card: Flashcard, isFlipped: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isFlipped) Color(0xFF2C2C2E) else Color(0xFF1C1C2E)
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isFlipped) {
                Text(
                    card.german,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    card.english,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = IosGreen,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    card.example,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )
            }
        }
    }
}

@Composable
fun StudyResult(deck: FlashcardDeck, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black).padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("✨", fontSize = 64.sp)
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "Sitzung beendet!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            "Du hast alle Karten in diesem Stapel durchgesehen.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onBack,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = IosGreen)
        ) {
            Text("Zurück zur Übersicht")
        }
    }
}
