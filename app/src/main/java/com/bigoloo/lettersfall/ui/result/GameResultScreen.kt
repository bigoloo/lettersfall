package com.bigoloo.lettersfall.ui.result

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bigoloo.lettersfall.R
import com.bigoloo.lettersfall.models.ChosenLanguage
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameResultScreen(modifier: Modifier, navController: NavController) {

    val gameResultViewModel = koinViewModel<GameResultViewModel>()

    val state = gameResultViewModel.viewState.collectAsStateWithLifecycle()
    state.value?.let {
        GameResultContent(modifier, it)
    }
}

@Composable
fun GameResultContent(modifier: Modifier, viewState: GameResultViewState) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val statistics = viewState.statistics.toTable()
        Spacer(modifier = modifier.height(24.dp))
        LazyColumn(
            modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            items(statistics) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = modifier.weight(0.5f),
                        text = stringResource(
                            id = when (it.first) {
                                "correctAnswerCount" -> R.string.correct_answers_count
                                "wrongAnswerCount" -> R.string.wrong_answers_count
                                "UnAnsweredCount" -> R.string.un_answered_count
                                "chosenLanguage" -> R.string.chosen_language
                                else -> throw Exception("Wrong Resource")
                            }
                        )
                    )
                    Text(
                        modifier = modifier.weight(0.5f),
                        text = when (it.first) {
                            "correctAnswerCount", "wrongAnswerCount", "UnAnsweredCount" -> it.second.toString()
                            "chosenLanguage" -> {
                                stringResource(
                                    id = when (it.second as ChosenLanguage) {
                                        ChosenLanguage.English -> R.string.english
                                        ChosenLanguage.Spanish -> R.string.spanish
                                    }
                                )
                            }
                            else -> throw Exception("Wrong Resource")
                        }
                    )
                }
            }
        }
    }
}
