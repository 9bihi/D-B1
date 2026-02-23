package com.deutschb1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.deutschb1.navigation.AppNavGraph
import com.deutschb1.navigation.Screen
import com.deutschb1.ui.theme.DeutschB1Theme

data class BottomNavItem(
    val label: String,
    val icon: Painter,
    val route: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DeutschB1Theme {
                DeutschB1App()
            }
        }
    }
}

@Composable
fun DeutschB1App() {
    val navController = rememberNavController()

    val homeIcon = rememberVectorPainter(Icons.Filled.Home)
    val examsIcon = rememberVectorPainter(Icons.Filled.MenuBook)
    val learnIcon = rememberVectorPainter(Icons.Filled.School)

    val bottomNavItems = listOf(
        BottomNavItem("Home", homeIcon, Screen.Home.route),
        BottomNavItem("Exams", examsIcon, Screen.ExamsHome.route),
        BottomNavItem("Learn", learnIcon, Screen.LearnHome.route),
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            DeutschBottomBar(navController = navController, items = bottomNavItems)
        }
    ) { innerPadding ->
        AppNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun DeutschBottomBar(navController: NavController, items: List<BottomNavItem>) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route ||
                    (item.route == Screen.ExamsHome.route && currentRoute?.startsWith("exams/") == true) ||
                    (item.route == Screen.LearnHome.route && currentRoute?.startsWith("learn/") == true)

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(item.route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { Icon(painter = item.icon, contentDescription = item.label) },
                label = { Text(item.label, style = MaterialTheme.typography.labelSmall) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}