package com.bigoloo.lettersfall.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bigoloo.lettersfall.ui.home.HomeScreen
import com.bigoloo.lettersfall.ui.question.QuestionScreen
import com.bigoloo.lettersfall.ui.result.GameResultScreen


@Composable
fun MainNavigation(
    modifier: Modifier,
    navController: NavHostController,
    startDestination: String = "home"
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("home") { HomeScreen(modifier, navController) }
        composable("questionScreen") {
            BackHandler(true) {
            }
            QuestionScreen(modifier, navController)
        }
        composable("gameResultScreen") {
            BackHandler(true) {
                navController.popBackStack("home", false)
            }
            GameResultScreen(modifier, navController)
        }
    }
}