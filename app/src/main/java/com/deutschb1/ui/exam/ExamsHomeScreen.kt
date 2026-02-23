package com.deutschb1.ui.exams

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.deutschb1.R
import com.deutschb1.data.ExamProvider
import com.deutschb1.navigation.Screen

data class ProviderItem(
    val provider: ExamProvider,
    val name: String,
    val description: String,
    val iconRes: Int,
    val color: Color
)

@Composable
fun ExamsHomeScreen(navController: NavController) {
    val providers = listOf(
        ProviderItem(
            ExamProvider.GOETHE,
            "Goethe-Institut",
            "Offizielles Goethe-Zertifikat B1",
            R.drawable.ic_goethe,
            Color(0xFF003F7F)
        ),
        ProviderItem(
            ExamProvider.OESD,
            "ÖSD",
            "Österreichisches Sprachdiplom",
            R.drawable.ic_osd,
            Color(0xFFAA0000)
        ),
        ProviderItem(
            ExamProvider.TELC,
            "TELC",
            "The European Language Certificates",
            R.drawable.ic_telc,
            Color(0xFF005500)
        )
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
        contentPadding = PaddingValues(top = 60.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                "Prüfungen",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Wähle einen Anbieter",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        items(providers) { provider ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate(Screen.ProviderSkillSelector.createRoute(provider.provider)) },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = provider.color.copy(alpha = 0.1f))
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = provider.iconRes),
                        contentDescription = null,
                        tint = provider.color,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(provider.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = provider.color)
                        Text(provider.description, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}