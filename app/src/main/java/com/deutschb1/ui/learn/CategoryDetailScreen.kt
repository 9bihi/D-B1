package com.deutschb1.ui.learn

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.deutschb1.data.allCategories
import com.deutschb1.navigation.Screen
import com.deutschb1.ui.learn.ThemeTile

@Composable
fun CategoryDetailScreen(navController: NavController, categoryId: String) {
    val category = allCategories.find { it.id == categoryId }
    
    if (category == null) return

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        IconButton(onClick = { navController.popBackStack() }) {
            Text("←", style = MaterialTheme.typography.titleLarge)
        }
        
        Text(category.title, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(category.themes) { index, content ->
                ThemeTile(
                    theme = content.theme,
                    phraseCount = content.phrases.size,
                    onClick = { 
                        navController.navigate(Screen.LearnTheme.createRoute(index)) 
                    }
                )
            }
        }
    }
}