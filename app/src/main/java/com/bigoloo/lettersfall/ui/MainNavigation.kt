package com.bigoloo.lettersfall.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bigoloo.lettersfall.ui.question.QuestionScreen


@Composable
fun MainNavigation(
    modifier: Modifier,
    navController: NavHostController,
    startDestination: String = "home"
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("home") { HomeScreen(modifier, navController) }
        composable("questionScreen") { QuestionScreen(modifier, navController) }
        composable("gameResultScreen") { GameResultScreen(modifier, navController) }

    }
}