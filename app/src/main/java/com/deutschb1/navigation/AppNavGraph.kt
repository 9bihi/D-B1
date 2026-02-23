package com.deutschb1.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.deutschb1.data.ExamProvider
import com.deutschb1.data.ExamSkill
import com.deutschb1.data.allExams
import com.deutschb1.data.allCategories
import com.deutschb1.ui.exam.hoeren.HoerenScreen
import com.deutschb1.ui.exam.lesen.LesenScreen
import com.deutschb1.ui.exam.schreiben.SchreibenScreen
import com.deutschb1.ui.exam.SkillSelectorScreen
import com.deutschb1.ui.exam.ModelltestSelectorScreen
import com.deutschb1.ui.exam.sprechen.SprechenScreen
import com.deutschb1.ui.exams.ExamsHomeScreen
import com.deutschb1.ui.home.HomeScreen
import com.deutschb1.ui.learn.*

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ExamsHome : Screen("exams")
    object ProviderSkillSelector : Screen("exams/{provider}") {
        fun createRoute(provider: ExamProvider) = "exams/${provider.name}"
    }
    object ModelltestSelector : Screen("exam/{provider}/{skill}") {
        fun createRoute(provider: ExamProvider, skill: ExamSkill) = "exam/${provider.name}/${skill.name}"
    }
    object Lesen : Screen("exam/{provider}/lesen/{modelltestId}") {
        fun createRoute(provider: ExamProvider, id: String) = "exam/${provider.name}/lesen/$id"
    }
    object Hoeren : Screen("exam/{provider}/hoeren/{modelltestId}") {
        fun createRoute(provider: ExamProvider, id: String) = "exam/${provider.name}/hoeren/$id"
    }
    object Schreiben : Screen("exam/{provider}/schreiben/{modelltestId}") {
        fun createRoute(provider: ExamProvider, id: String) = "exam/${provider.name}/schreiben/$id"
    }
    object Sprechen : Screen("exam/{provider}/sprechen/{modelltestId}") {
        fun createRoute(provider: ExamProvider, id: String) = "exam/${provider.name}/sprechen/$id"
    }

    // Learn section
    object LearnHome : Screen("learn")
    object LearnCategory : Screen("learn/category/{categoryId}") {
        fun createRoute(id: String) = "learn/category/$id"
    }
    object LearnTheme : Screen("learn/theme/{themeIndex}") {
        fun createRoute(index: Int) = "learn/theme/$index"
    }
}

@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        // Exams home
        composable(Screen.ExamsHome.route) {
            ExamsHomeScreen(navController = navController)
        }

        // Provider skill selector
        composable(Screen.ProviderSkillSelector.route) { backStackEntry ->
            val providerStr = backStackEntry.arguments?.getString("provider")
            val provider = ExamProvider.valueOf(providerStr!!)
            SkillSelectorScreen(
                provider = provider,
                navController = navController,
                onSkillSelected = { skill ->
                    navController.navigate(Screen.ModelltestSelector.createRoute(provider, skill))
                }
            )
        }

        // Modelltest selector
        composable(Screen.ModelltestSelector.route) { backStackEntry ->
            val providerStr = backStackEntry.arguments?.getString("provider")
            val skillStr = backStackEntry.arguments?.getString("skill")
            val provider = ExamProvider.valueOf(providerStr!!)
            val skill = ExamSkill.valueOf(skillStr!!)
            val tests = allExams[provider] ?: emptyList()

            ModelltestSelectorScreen(
                provider = provider,
                skill = skill,
                modelltests = tests,
                navController = navController,
                onModelltestSelected = { test ->
                    val nextRoute = when (skill) {
                        ExamSkill.LESEN -> Screen.Lesen.createRoute(provider, test.id)
                        ExamSkill.HOEREN -> Screen.Hoeren.createRoute(provider, test.id)
                        ExamSkill.SCHREIBEN -> Screen.Schreiben.createRoute(provider, test.id)
                        ExamSkill.SPRECHEN -> Screen.Sprechen.createRoute(provider, test.id)
                    }
                    navController.navigate(nextRoute)
                }
            )
        }

        // Exam screens
        composable(Screen.Lesen.route) { backStackEntry ->
            val provider = ExamProvider.valueOf(backStackEntry.arguments?.getString("provider")!!)
            val id = backStackEntry.arguments?.getString("modelltestId")
            val exam = allExams[provider]?.find { it.id == id }!!
            LesenScreen(exam = exam, navController = navController)
        }
        composable(Screen.Hoeren.route) { backStackEntry ->
            val provider = ExamProvider.valueOf(backStackEntry.arguments?.getString("provider")!!)
            val id = backStackEntry.arguments?.getString("modelltestId")
            val exam = allExams[provider]?.find { it.id == id }!!
            HoerenScreen(exam = exam, navController = navController)
        }
        composable(Screen.Schreiben.route) { backStackEntry ->
            val provider = ExamProvider.valueOf(backStackEntry.arguments?.getString("provider")!!)
            val id = backStackEntry.arguments?.getString("modelltestId")
            val exam = allExams[provider]?.find { it.id == id }!!
            SchreibenScreen(exam = exam, navController = navController)
        }
        composable(Screen.Sprechen.route) { backStackEntry ->
            val provider = ExamProvider.valueOf(backStackEntry.arguments?.getString("provider")!!)
            val id = backStackEntry.arguments?.getString("modelltestId")
            val exam = allExams[provider]?.find { it.id == id }!!
            SprechenScreen(exam = exam, navController = navController)
        }

        // Learn section
        composable(Screen.LearnHome.route) {
            LearnHomeScreen(navController = navController)
        }
        composable(Screen.LearnCategory.route) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: return@composable
            CategoryDetailScreen(navController = navController, categoryId = categoryId)
        }
        composable(Screen.LearnTheme.route) { backStackEntry ->
            val themeIndex = backStackEntry.arguments?.getString("themeIndex")?.toIntOrNull() ?: 0
            val content = allCategories.flatMap { it.themes }.getOrNull(themeIndex)
            if (content != null) {
                ThemePhraseListScreen(content = content, navController = navController)
            }
        }
    }
}