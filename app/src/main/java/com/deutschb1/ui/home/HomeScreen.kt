package com.deutschb1.ui.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deutschb1.R
import com.deutschb1.data.ExamProvider
import com.deutschb1.navigation.Screen
import com.deutschb1.ui.theme.*

data class CategoryCard(
    val title: String,
    val subtitle: String,
    val iconRes: Int,
    val gradient: List<Color>,
    val route: String
)

@Composable
fun HomeScreen(navController: NavController) {
    val categories = listOf(
        CategoryCard(
            "Goethe-Institut",
            "Offizielles Goethe-Zertifikat B1",
            R.drawable.ic_goethe,
            listOf(Color(0xFF2ECC71), Color(0xFF27AE60)),
            Screen.ProviderSkillSelector.createRoute(ExamProvider.GOETHE)
        ),
        CategoryCard(
            "ÖSD",
            "Österreichisches Sprachdiplom",
            R.drawable.ic_osd,
            listOf(Color(0xFF007AFF), Color(0xFF0056B3)),
            Screen.ProviderSkillSelector.createRoute(ExamProvider.OESD)
        ),
        CategoryCard(
            "TELC",
            "The European Language Certificates",
            R.drawable.ic_telc,
            listOf(Color(0xFFFF3B30), Color(0xFFD32F2F)),
            Screen.ProviderSkillSelector.createRoute(ExamProvider.TELC)
        ),
        CategoryCard(
            "Learn",
            "B1 Phrases & Vocabulary",
            R.drawable.ic_learn,
            listOf(Color(0xFF5856D6), Color(0xFF8E8CE1)),
            Screen.LearnHome.route
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = "Deutsch B1",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Prüfungsvorbereitung",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Tip card (glass style)
        TipCard()

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Prüfungsanbieter",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(12.dp))

        categories.forEach { category ->
            GlassHomeCategoryCard(category = category, onClick = { navController.navigate(category.route) })
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun TipCard() {
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
                Text("💡", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        "Tipp des Tages",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White.copy(alpha = 0.7f),
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        "Übe täglich 30 Minuten – Kontinuität schlägt Intensität!",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun GlassHomeCategoryCard(category: CategoryCard, onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
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
                    // Icon in colored circle
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(category.gradient)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = category.iconRes),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = category.title,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = category.subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.3f),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}