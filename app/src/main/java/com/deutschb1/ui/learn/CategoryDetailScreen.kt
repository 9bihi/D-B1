package com.deutschb1.ui.learn

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.deutschb1.data.allCategories
import com.deutschb1.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)  // Add this line
@Composable
fun CategoryDetailScreen(navController: NavController, categoryId: String) {
    val category = allCategories.find { it.id == categoryId }

    if (category == null) return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(category.title) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Zurück")
                    }
                }
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(padding)
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