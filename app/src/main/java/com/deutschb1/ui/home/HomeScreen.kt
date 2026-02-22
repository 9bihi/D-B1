package com.deutschb1.ui.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.deutschb1.navigation.Screen
import com.deutschb1.ui.theme.*

data class CategoryCard(
    val title: String,
    val subtitle: String,
    val emoji: String,
    val gradient: List<Color>,
    val route: String
)

@Composable
fun HomeScreen(navController: NavController) {
    val categories = listOf(
        CategoryCard(
            "Goethe-Institut",
            "Offizielles Goethe-Zertifikat B1",
            "🏛️",
            listOf(Color(0xFF003F7F), Color(0xFF0066CC)),
            Screen.GoetheSkills.route
        ),
        CategoryCard(
            "ÖSD",
            "Österreichisches Sprachdiplom",
            "🇦🇹",
            listOf(Color(0xFFAA0000), Color(0xFFDD3333)),
            Screen.OesdSkills.route
        ),
        CategoryCard(
            "TELC",
            "The European Language Certificates",
            "🇪🇺",
            listOf(Color(0xFF005500), Color(0xFF228822)),
            Screen.TelcSkills.route
        ),
        CategoryCard(
            "Learn",
            "B1 Phrases & Vocabulary",
            "📚",
            listOf(Color(0xFF5856D6), Color(0xFF8E8CE1)),
            Screen.LearnHome.route
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // Header
        Text(
            text = "Deutsch B1",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Prüfungsvorbereitung",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Quote card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            ),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("💡", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        "Tipp des Tages",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        "Übe täglich 30 Minuten – Kontinuität schlägt Intensität!",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Prüfungsanbieter",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(12.dp))

        categories.forEach { category ->
            AnimatedCategoryCard(category = category, onClick = { navController.navigate(category.route) })
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun AnimatedCategoryCard(category: CategoryCard, onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = tween(100),
        label = "scale"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.linearGradient(category.gradient)
            )
            .clickable {
                pressed = true
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = category.emoji + "  " + category.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = category.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.85f)
                )
            }
            Text("›", fontSize = 28.sp, color = Color.White.copy(alpha = 0.8f))
        }
    }
}
