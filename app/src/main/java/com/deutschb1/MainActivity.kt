package com.deutschb1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.deutschb1.navigation.AppNavGraph
import com.deutschb1.navigation.Screen
import com.deutschb1.ui.theme.DeutschB1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DeutschB1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    DeutschB1App()
                }
            }
        }
    }
}

data class NavItem(
    val label: String,
    val route: String,
    val iconOutlined: ImageVector,
    val iconFilled: ImageVector
)

@Composable
fun DeutschB1App() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            FloatingGlassNavBar(navController = navController)
        }
    ) { innerPadding ->
        // Use fillMaxSize to let content go under the floating bar
        AppNavGraph(
            navController = navController,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun FloatingGlassNavBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navItems = listOf(
        NavItem("Home", Screen.Home.route, Icons.Outlined.Home, Icons.Filled.Home),
        NavItem("Exams", Screen.ExamsHome.route, Icons.Outlined.MenuBook, Icons.Filled.MenuBook),
        NavItem("Learn", Screen.LearnHome.route, Icons.Outlined.School, Icons.Filled.School)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp, start = 20.dp, end = 20.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .shadow(
                    elevation = 15.dp,
                    shape = RoundedCornerShape(35.dp),
                    ambientColor = Color.Black.copy(alpha = 0.5f),
                    spotColor = Color.Black.copy(alpha = 0.5f)
                ),
            shape = RoundedCornerShape(35.dp),
            color = Color(0xFF1C1C1E).copy(alpha = 0.85f),
            tonalElevation = 0.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(35.dp)
                    )
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    navItems.forEach { item ->
                        val isSelected = currentRoute == item.route ||
                                (item.route == Screen.ExamsHome.route && currentRoute?.startsWith("exam") == true) ||
                                (item.route == Screen.LearnHome.route && currentRoute?.startsWith("learn") == true)

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    if (isSelected) Color.White.copy(alpha = 0.15f)
                                    else Color.Transparent
                                )
                                .clickable {
                                    if (!isSelected) {
                                        // Navigate to the tab's root, clearing back stack up to it
                                        navController.navigate(item.route) {
                                            popUpTo(item.route) { inclusive = false }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    } else {
                                        // Already on this tab – pop back to its root
                                        navController.popBackStack(item.route, inclusive = false)
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                AnimatedContent(
                                    targetState = isSelected,
                                    transitionSpec = {
                                        (fadeIn(animationSpec = tween(300)) +
                                                scaleIn(initialScale = 0.8f, animationSpec = tween(300))) togetherWith
                                                (fadeOut(animationSpec = tween(300)) +
                                                        scaleOut(targetScale = 0.8f, animationSpec = tween(300)))
                                    },
                                    label = "icon_animation"
                                ) { selected ->
                                    Icon(
                                        imageVector = if (selected) item.iconFilled else item.iconOutlined,
                                        contentDescription = item.label,
                                        tint = if (selected) Color.White else Color.Gray,
                                        modifier = Modifier.size(26.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = item.label,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isSelected) Color.White else Color.Gray,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NavButton(
    item: NavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isSelected) Color.White.copy(alpha = 0.15f)
                else Color.Transparent
            )
            .clickable { onClick() }
    ) {
        AnimatedContent(
            targetState = isSelected,
            transitionSpec = {
                (fadeIn(animationSpec = tween(300)) +
                        scaleIn(initialScale = 0.8f, animationSpec = tween(300))) togetherWith
                        (fadeOut(animationSpec = tween(300)) +
                                scaleOut(targetScale = 0.8f, animationSpec = tween(300)))
            },
            label = "icon_animation"
        ) { selected ->
            Icon(
                imageVector = if (selected) item.iconFilled else item.iconOutlined,
                contentDescription = item.label,
                tint = if (selected) Color.White else Color.Gray,
                modifier = Modifier.size(26.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.label,
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) Color.White else Color.Gray,
            fontSize = 10.sp
        )
    }
}