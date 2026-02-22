package com.deutschb1.ui.exam

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deutschb1.data.ExamProvider
import com.deutschb1.data.ExamSkill
import com.deutschb1.ui.theme.*

data class SkillCardData(
    val skill: ExamSkill,
    val description: String,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillSelectorScreen(
    provider: ExamProvider,
    navController: NavController,
    onSkillSelected: (ExamSkill) -> Unit
) {
    val skillCards = listOf(
        SkillCardData(ExamSkill.LESEN, "Textverstehen & Lesekompetenz", IosBlue),
        SkillCardData(ExamSkill.HOEREN, "Hörverstehen & Audiokompetenz", IosGreen),
        SkillCardData(ExamSkill.SCHREIBEN, "Schreibkompetenz & Textproduktion", IosOrange),
        SkillCardData(ExamSkill.SPRECHEN, "Sprechkompetenz & Interaktion", IosPurple)
    )

    val providerColor = when (provider) {
        ExamProvider.GOETHE -> GoetheBlue
        ExamProvider.OESD -> OesdRed
        ExamProvider.TELC -> TelcGreen
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            provider.displayName,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            "Deutsch B1 – Wähle eine Fertigkeit",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Zurück")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Provider badge
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = providerColor.copy(alpha = 0.1f)),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        when (provider) {
                            ExamProvider.GOETHE -> "🏛️"
                            ExamProvider.OESD -> "🇦🇹"
                            ExamProvider.TELC -> "🇪🇺"
                        }, fontSize = 28.sp
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            provider.displayName,
                            fontWeight = FontWeight.Bold,
                            color = providerColor,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            provider.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Fertigkeiten",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(skillCards) { card ->
                    SkillCard(data = card, onClick = { onSkillSelected(card.skill) })
                }
            }
        }
    }
}

@Composable
fun SkillCard(data: SkillCardData, onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.95f else 1f,
        animationSpec = tween(100), label = "scale"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .scale(scale)
            .clip(RoundedCornerShape(20.dp))
            .background(data.color.copy(alpha = 0.12f))
            .clickable {
                pressed = true
                onClick()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(data.color.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(data.skill.icon, fontSize = 22.sp)
            }
            Column {
                Text(
                    data.skill.displayName,
                    fontWeight = FontWeight.Bold,
                    color = data.color,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    "${data.skill.durationMin} Min.",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
