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
import com.deutschb1.data.db.DatabaseProvider
import androidx.compose.ui.platform.LocalContext

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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
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
            }
            IconButton(
                onClick = { navController.navigate(Screen.Stats.route) },
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.1f))
            ) {
                Text("📊", fontSize = 24.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Dashboard (glass style)
        DashboardCard()

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
fun DashboardCard() {
    val context = LocalContext.current
    val results by DatabaseProvider.getResultDao(context).getAllResults().collectAsState(initial = emptyList())
    
    val distinctDays = results.map { 
        val cal = java.util.Calendar.getInstance()
        cal.timeInMillis = it.completedAt
        "${cal.get(java.util.Calendar.YEAR)}-${cal.get(java.util.Calendar.DAY_OF_YEAR)}"
    }.distinct().size

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1C1C1E).copy(alpha = 0.65f)
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🔥", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Lern-Streak",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White.copy(alpha = 0.7f),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Text(
                        "$distinctDays Tage gelernt",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                
                VerticalDivider(
                    modifier = Modifier.height(40.dp).padding(horizontal = 16.dp),
                    color = Color.White.copy(alpha = 0.1f)
                )

                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("✅", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Prüfungen",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White.copy(alpha = 0.7f),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Text(
                        "${results.size} Abgeschlossen",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = IosGreen
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
                    // Icon with built-in rounded-square background (no circle clip, no tint)
                    Icon(
                        painter = painterResource(id = category.iconRes),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(14.dp))
                    )
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