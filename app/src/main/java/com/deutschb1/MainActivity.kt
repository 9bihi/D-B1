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
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.deutschb1.navigation.AppNavGraph
import com.deutschb1.navigation.Screen
import com.deutschb1.ui.theme.DeutschB1Theme
import kotlin.math.roundToInt

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
        AppNavGraph(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
        NavItem("Learn", Screen.LearnHome.route, Icons.Outlined.School, Icons.Filled.School),
        NavItem("Translate", Screen.Translation.route, Icons.Outlined.Translate, Icons.Filled.Translate)
    )

    val selectedIndex = navItems.indexOfFirst { item ->
        currentRoute == item.route ||
                (item.route == Screen.ExamsHome.route && currentRoute?.startsWith("exam") == true) ||
                (item.route == Screen.LearnHome.route && currentRoute?.startsWith("learn") == true) ||
                (item.route == Screen.Translation.route && currentRoute == "translation")
    }.coerceAtLeast(0)

    // Track x-offset and width of each tab for the sliding indicator
    val tabOffsets = remember { mutableStateListOf(0f, 0f, 0f, 0f) }
    val tabWidths = remember { mutableStateListOf(0f, 0f, 0f, 0f) }

    val indicatorX by animateFloatAsState(
        targetValue = tabOffsets.getOrElse(selectedIndex) { 0f },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "indicatorX"
    )
    val indicatorWidth by animateFloatAsState(
        targetValue = tabWidths.getOrElse(selectedIndex) { 0f },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "indicatorWidth"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp, start = 16.dp, end = 16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Outer glass pill
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .shadow(
                    elevation = 24.dp,
                    shape = RoundedCornerShape(36.dp),
                    ambientColor = Color.Black.copy(alpha = 0.6f),
                    spotColor = Color.Black.copy(alpha = 0.6f)
                )
                .clip(RoundedCornerShape(36.dp))
                .background(Color(0xFF1C1C1E).copy(alpha = 0.88f))
                .border(
                    width = 0.8.dp,
                    color = Color.White.copy(alpha = 0.18f),
                    shape = RoundedCornerShape(36.dp)
                )
        ) {
            // Sliding pill indicator behind the items
            if (indicatorWidth > 0f) {
                Box(
                    modifier = Modifier
                        .offset { IntOffset(indicatorX.roundToInt(), 0) }
                        .width(with(androidx.compose.ui.platform.LocalDensity.current) { indicatorWidth.toDp() })
                        .fillMaxHeight()
                        .padding(6.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color.White.copy(alpha = 0.14f))
                        .border(
                            width = 0.6.dp,
                            color = Color.White.copy(alpha = 0.22f),
                            shape = RoundedCornerShape(30.dp)
                        )
                )
            }

            // Tab items
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                navItems.forEachIndexed { index, item ->
                    val isSelected = index == selectedIndex

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .onGloballyPositioned { coords ->
                                tabOffsets[index] = coords.positionInParent().x
                                tabWidths[index] = coords.size.width.toFloat()
                            }
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                if (!isSelected) {
                                    navController.navigate(item.route) {
                                        popUpTo(item.route) { inclusive = false }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                } else {
                                    navController.popBackStack(item.route, inclusive = false)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            AnimatedContent(
                                targetState = isSelected,
                                transitionSpec = {
                                    (fadeIn(tween(220)) + scaleIn(initialScale = 0.75f, animationSpec = tween(220))) togetherWith
                                            (fadeOut(tween(180)) + scaleOut(targetScale = 0.75f, animationSpec = tween(180)))
                                },
                                label = "icon_$index"
                            ) { selected ->
                                Icon(
                                    imageVector = if (selected) item.iconFilled else item.iconOutlined,
                                    contentDescription = item.label,
                                    tint = if (selected) Color.White else Color.White.copy(alpha = 0.45f),
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(3.dp))
                            // Label with animated alpha
                            val labelAlpha by animateFloatAsState(
                                targetValue = if (isSelected) 1f else 0.45f,
                                animationSpec = tween(200),
                                label = "labelAlpha_$index"
                            )
                            Text(
                                text = item.label,
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White.copy(alpha = labelAlpha),
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }
    }
}