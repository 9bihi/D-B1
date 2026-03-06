package com.deutschb1.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.deutschb1.data.ExamProvider
import com.deutschb1.data.ExamSkill
import com.deutschb1.data.allExams
import com.deutschb1.data.allLearnContent
import com.deutschb1.data.allCategories
import com.deutschb1.ui.exam.hoeren.HoerenScreen
import com.deutschb1.ui.exam.lesen.LesenScreen
import com.deutschb1.ui.exam.schreiben.SchreibenScreen
import com.deutschb1.ui.exam.SkillSelectorScreen
import com.deutschb1.ui.exam.ModelltestSelectorScreen
import com.deutschb1.ui.exam.sprechen.SprechenScreen
import com.deutschb1.ui.home.HomeScreen
import com.deutschb1.ui.exams.ExamsHomeScreen
import com.deutschb1.ui.learn.LearnHomeScreen
import com.deutschb1.ui.learn.CategoryDetailScreen
import com.deutschb1.ui.learn.ThemePhraseListScreen
import com.deutschb1.ui.learn.ApiListScreen
import com.deutschb1.ui.translation.TranslationScreen
import com.deutschb1.ui.exam.ResultSummaryScreen
import com.deutschb1.ui.exam.LastResultsProvider
import com.deutschb1.ui.learn.flashcards.FlashcardDeckScreen
import com.deutschb1.ui.learn.flashcards.FlashcardStudyScreen
import com.deutschb1.data.FlashcardData
import com.deutschb1.ui.learn.GeschichtenListScreen
import com.deutschb1.ui.learn.GeschichteReaderScreen
import com.deutschb1.ui.learn.GrammarDrillTopicsScreen
import com.deutschb1.ui.learn.GrammarDrillScreen
import com.deutschb1.ui.learn.WordVaultScreen
import com.deutschb1.ui.learn.SpielMenuScreen
import com.deutschb1.ui.learn.games.WordMatchScreen
import com.deutschb1.ui.learn.games.FillBlankScreen
import com.deutschb1.ui.ProgressDashboardScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ExamsHome : Screen("exams")
    object ProviderSkillSelector : Screen("exam/{provider}/skills") {
        fun createRoute(provider: ExamProvider) = "exam/${provider.name}/skills"
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
    object ApiTools : Screen("learn/api/{type}") {
        fun createRoute(type: String) = "learn/api/$type"
    }
    object FlashcardDecks : Screen("learn/flashcards")
    object FlashcardStudy : Screen("learn/flashcards/{deckId}") {
        fun createRoute(deckId: String) = "learn/flashcards/$deckId"
    }

    // Results
    object ResultSummary : Screen("exam/results/{score}/{total}/{skillName}") {
        fun createRoute(score: Int, total: Int, skill: ExamSkill) = "exam/results/$score/$total/${skill.name}"
    }

    // Translation
    object Translation : Screen("translation")

    // Geschichten
    object GeschichtenList : Screen("learn/geschichten")
    object GeschichteReader : Screen("learn/geschichten/{storyId}") {
        fun createRoute(storyId: String) = "learn/geschichten/$storyId"
    }

    // Grammar Drills
    object GrammarDrillTopics : Screen("learn/grammar")
    object GrammarDrill : Screen("learn/grammar/{topicId}") {
        fun createRoute(topicId: String) = "learn/grammar/$topicId"
    }

    // Word Vault
    object WordVault : Screen("learn/word-vault")

    // Spiele
    object SpieleMenu : Screen("learn/spiele")
    object WordMatch : Screen("learn/spiele/match/{gameId}") {
        fun createRoute(gameId: String) = "learn/spiele/match/$gameId"
    }
    object FillBlank : Screen("learn/spiele/fill/{gameId}") {
        fun createRoute(gameId: String) = "learn/spiele/fill/$gameId"
    }

    // Stats
    object Stats : Screen("stats")
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
            val provider = providerStr?.let { ExamProvider.valueOf(it) } ?: return@composable
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
            val allThemes = allCategories.flatMap { category -> category.themes }
            val content = allThemes.getOrNull(themeIndex)
            if (content != null) {
                ThemePhraseListScreen(content = content, navController = navController)
            }
        }
        composable(Screen.ApiTools.route) { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type") ?: "dictionary"
            ApiListScreen(navController = navController, type = type)
        }
        composable(Screen.FlashcardDecks.route) {
            FlashcardDeckScreen(navController = navController)
        }
        composable(Screen.FlashcardStudy.route) { backStackEntry ->
            val deckId = backStackEntry.arguments?.getString("deckId") ?: ""
            val deck = FlashcardData.decks.find { it.id == deckId }
            if (deck != null) {
                FlashcardStudyScreen(deck = deck, navController = navController)
            }
        }

        // Results
        composable(Screen.ResultSummary.route) { backStackEntry ->
            val score = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
            val total = backStackEntry.arguments?.getString("total")?.toIntOrNull() ?: 0
            val skillName = backStackEntry.arguments?.getString("skillName") ?: ""
            
            // For now, we'll use the last results stored in a singleton or just show the score
            // Implementation of this screen usually needs the question list
            // Since we can't easily pass the list, we'll use a static reference or similar for this demo
            ResultSummaryScreen(
                score = score,
                total = total,
                questions = LastResultsProvider.lastResults,
                onRetry = { navController.popBackStack() },
                onBackToExams = { 
                    navController.popBackStack(Screen.ExamsHome.route, inclusive = false)
                }
            )
        }

        // Translation
        composable(Screen.Translation.route) {
            TranslationScreen(navController = navController)
        }

        // Geschichten
        composable(Screen.GeschichtenList.route) {
            GeschichtenListScreen(navController = navController)
        }
        composable(Screen.GeschichteReader.route) { backStackEntry ->
            val storyId = backStackEntry.arguments?.getString("storyId") ?: ""
            GeschichteReaderScreen(storyId = storyId, navController = navController)
        }

        // Grammar Drills
        composable(Screen.GrammarDrillTopics.route) {
            GrammarDrillTopicsScreen(navController = navController)
        }
        composable(Screen.GrammarDrill.route) { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId") ?: ""
            GrammarDrillScreen(topicId = topicId, navController = navController)
        }

        // Word Vault
        composable(Screen.WordVault.route) {
            WordVaultScreen(navController = navController)
        }

        // Spiele
        composable(Screen.SpieleMenu.route) {
            SpielMenuScreen(navController = navController)
        }
        composable(Screen.WordMatch.route) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getString("gameId") ?: ""
            WordMatchScreen(gameId = gameId, navController = navController)
        }
        composable(Screen.FillBlank.route) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getString("gameId") ?: ""
            FillBlankScreen(gameId = gameId, navController = navController)
        }

        // Stats
        composable(Screen.Stats.route) {
            ProgressDashboardScreen(navController = navController)
        }
    }
}