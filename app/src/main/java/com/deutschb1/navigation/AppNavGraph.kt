package com.deutschb1.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.deutschb1.data.ExamProvider
import com.deutschb1.data.ExamSkill
import com.deutschb1.data.LearnTheme
import com.deutschb1.data.allExams
import com.deutschb1.data.allLearnContent
import com.deutschb1.ui.exam.hoeren.HoerenScreen
import com.deutschb1.ui.exam.lesen.LesenScreen
import com.deutschb1.ui.exam.schreiben.SchreibenScreen
import com.deutschb1.ui.exam.SkillSelectorScreen
import com.deutschb1.ui.exam.sprechen.SprechenScreen
import com.deutschb1.ui.home.HomeScreen
import com.deutschb1.ui.learn.LearnHomeScreen
import com.deutschb1.ui.learn.ThemePhraseListScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object GoetheSkills : Screen("exam/goethe")
    object OesdSkills : Screen("exam/oesd")
    object TelcSkills : Screen("exam/telc")
    object GoetheLesen : Screen("exam/goethe/lesen")
    object GoetheHoeren : Screen("exam/goethe/hoeren")
    object GoetheSchreiben : Screen("exam/goethe/schreiben")
    object GoetheSprechen : Screen("exam/goethe/sprechen")
    object OesdLesen : Screen("exam/oesd/lesen")
    object OesdHoeren : Screen("exam/oesd/hoeren")
    object OesdSchreiben : Screen("exam/oesd/schreiben")
    object OesdSprechen : Screen("exam/oesd/sprechen")
    object TelcLesen : Screen("exam/telc/lesen")
    object TelcHoeren : Screen("exam/telc/hoeren")
    object TelcSchreiben : Screen("exam/telc/schreiben")
    object TelcSprechen : Screen("exam/telc/sprechen")
    object LearnHome : Screen("learn")
    object LearnTheme : Screen("learn/{themeIndex}") {
        fun createRoute(index: Int) = "learn/$index"
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

        // Goethe
        composable(Screen.GoetheSkills.route) {
            SkillSelectorScreen(
                provider = ExamProvider.GOETHE,
                navController = navController,
                onSkillSelected = { skill ->
                    when (skill) {
                        ExamSkill.LESEN -> navController.navigate(Screen.GoetheLesen.route)
                        ExamSkill.HOEREN -> navController.navigate(Screen.GoetheHoeren.route)
                        ExamSkill.SCHREIBEN -> navController.navigate(Screen.GoetheSchreiben.route)
                        ExamSkill.SPRECHEN -> navController.navigate(Screen.GoetheSprechen.route)
                    }
                }
            )
        }
        composable(Screen.GoetheLesen.route) {
            LesenScreen(exam = allExams[ExamProvider.GOETHE]!!, navController = navController)
        }
        composable(Screen.GoetheHoeren.route) {
            HoerenScreen(exam = allExams[ExamProvider.GOETHE]!!, navController = navController)
        }
        composable(Screen.GoetheSchreiben.route) {
            SchreibenScreen(exam = allExams[ExamProvider.GOETHE]!!, navController = navController)
        }
        composable(Screen.GoetheSprechen.route) {
            SprechenScreen(exam = allExams[ExamProvider.GOETHE]!!, navController = navController)
        }

        // ÖSD
        composable(Screen.OesdSkills.route) {
            SkillSelectorScreen(
                provider = ExamProvider.OESD,
                navController = navController,
                onSkillSelected = { skill ->
                    when (skill) {
                        ExamSkill.LESEN -> navController.navigate(Screen.OesdLesen.route)
                        ExamSkill.HOEREN -> navController.navigate(Screen.OesdHoeren.route)
                        ExamSkill.SCHREIBEN -> navController.navigate(Screen.OesdSchreiben.route)
                        ExamSkill.SPRECHEN -> navController.navigate(Screen.OesdSprechen.route)
                    }
                }
            )
        }
        composable(Screen.OesdLesen.route) {
            LesenScreen(exam = allExams[ExamProvider.OESD]!!, navController = navController)
        }
        composable(Screen.OesdHoeren.route) {
            HoerenScreen(exam = allExams[ExamProvider.OESD]!!, navController = navController)
        }
        composable(Screen.OesdSchreiben.route) {
            SchreibenScreen(exam = allExams[ExamProvider.OESD]!!, navController = navController)
        }
        composable(Screen.OesdSprechen.route) {
            SprechenScreen(exam = allExams[ExamProvider.OESD]!!, navController = navController)
        }

        // TELC
        composable(Screen.TelcSkills.route) {
            SkillSelectorScreen(
                provider = ExamProvider.TELC,
                navController = navController,
                onSkillSelected = { skill ->
                    when (skill) {
                        ExamSkill.LESEN -> navController.navigate(Screen.TelcLesen.route)
                        ExamSkill.HOEREN -> navController.navigate(Screen.TelcHoeren.route)
                        ExamSkill.SCHREIBEN -> navController.navigate(Screen.TelcSchreiben.route)
                        ExamSkill.SPRECHEN -> navController.navigate(Screen.TelcSprechen.route)
                    }
                }
            )
        }
        composable(Screen.TelcLesen.route) {
            LesenScreen(exam = allExams[ExamProvider.TELC]!!, navController = navController)
        }
        composable(Screen.TelcHoeren.route) {
            HoerenScreen(exam = allExams[ExamProvider.TELC]!!, navController = navController)
        }
        composable(Screen.TelcSchreiben.route) {
            SchreibenScreen(exam = allExams[ExamProvider.TELC]!!, navController = navController)
        }
        composable(Screen.TelcSprechen.route) {
            SprechenScreen(exam = allExams[ExamProvider.TELC]!!, navController = navController)
        }

        // Learn
        composable(Screen.LearnHome.route) {
            LearnHomeScreen(navController = navController)
        }
        composable(Screen.LearnTheme.route) { backStackEntry ->
            val themeIndex = backStackEntry.arguments?.getString("themeIndex")?.toIntOrNull() ?: 0
            val content = allLearnContent.getOrNull(themeIndex)
            if (content != null) {
                ThemePhraseListScreen(content = content, navController = navController)
            }
        }
    }
}
