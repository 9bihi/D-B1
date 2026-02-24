package com.deutschb1.ui.exams

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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

data class ProviderItem(
    val provider: ExamProvider,
    val name: String,
    val description: String,
    val iconRes: Int,
    val gradientColors: List<Color>
)

@Composable
fun ExamsHomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Text(
                text = "Prüfungen",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 34.sp
            )
            Text(
                text = "Wähle einen Anbieter",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray,
                fontSize = 17.sp
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(providers) { provider ->
                GlassProviderCard(
                    provider = provider,
                    onClick = {
                        navController.navigate(Screen.ProviderSkillSelector.createRoute(provider.provider))
                    }
                )
            }
            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}

val providers = listOf(
    ProviderItem(
        ExamProvider.GOETHE,
        "Goethe-Institut",
        "Offizielles Goethe-Zertifikat B1",
        R.drawable.ic_goethe,
        listOf(Color(0xFF2ECC71), Color(0xFF27AE60))
    ),
    ProviderItem(
        ExamProvider.OESD,
        "ÖSD",
        "Österreichisches Sprachdiplom",
        R.drawable.ic_osd,
        listOf(Color(0xFF007AFF), Color(0xFF0056B3))
    ),
    ProviderItem(
        ExamProvider.TELC,
        "TELC",
        "The European Language Certificates",
        R.drawable.ic_telc,
        listOf(Color(0xFFFF3B30), Color(0xFFD32F2F))
    )
)

@Composable
fun GlassProviderCard(provider: ProviderItem, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = tween(100),
        label = "scale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp)
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1C1C1E).copy(alpha = 0.65f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.15f),
                            Color.Transparent
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon circle with gradient background
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Brush.linearGradient(provider.gradientColors)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = provider.iconRes),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = provider.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        fontSize = 17.sp,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = provider.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        fontSize = 13.sp,
                        maxLines = 1
                    )
                }

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.3f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}