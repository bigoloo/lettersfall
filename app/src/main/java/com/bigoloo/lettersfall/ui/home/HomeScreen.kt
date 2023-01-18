package com.bigoloo.lettersfall.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bigoloo.lettersfall.models.GameStatus
import com.bigoloo.lettersfall.ui.base.Actionable
import com.bigoloo.lettersfall.ui.home.HomeAction
import com.bigoloo.lettersfall.ui.home.HomeEffect
import com.bigoloo.lettersfall.ui.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier,
    navController: NavHostController
) {
    val wordFallViewModel: HomeViewModel = koinViewModel()
    val state = wordFallViewModel.viewState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(text = "LetterFull Game")
        val currentGameStatus = state.value.asLoaded()
        LaunchedEffect(key1 = Unit, block = {
            wordFallViewModel.effect.collect {
                when (it) {
                    HomeEffect.NavigateToQuestionScreen -> navController.navigate("questionScreen")
                }
            }
        })
        if (currentGameStatus != null) {
            when (currentGameStatus.startGameActionable) {
                is Actionable.Failed -> {
                    Toast.makeText(
                        LocalContext.current,
                        "Failed to Start Try Again",
                        Toast.LENGTH_LONG
                    ).show()
                }
                Actionable.InProgress -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(
                            48.dp
                        )
                    )
                }
                else -> {}
            }
            when (currentGameStatus.gameState) {
                GameStatus.NotStarted -> {
                    Button(onClick = {
                        wordFallViewModel.dispatch(HomeAction.InitiateGame)
                    }) {
                        Text(text = "New Game")
                    }
                }
                is GameStatus.Started -> {
                    Button(onClick = {
                        wordFallViewModel.dispatch(HomeAction.InitiateGame)
                    }) {
                        Text(text = "Continue")
                    }
                }
                else -> {}
            }
        }
    }
}