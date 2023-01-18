package com.bigoloo.lettersfall.ui.question

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bigoloo.lettersfall.ui.base.ui.ComposableLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuestionScreen(modifier: Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Hello")
        val questionViewModel = koinViewModel<QuestionViewModel>()

        ComposableLifecycle { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> questionViewModel.dispatch(QuestionAction.ViewStart)
                Lifecycle.Event.ON_PAUSE -> questionViewModel.dispatch(QuestionAction.ViewStop)
                else -> {}
            }
        }
        LaunchedEffect(key1 = Unit, block = {
            questionViewModel.effect.collect {
                when (it) {
                    QuestionEffect.QuestionsFinished -> navController.navigate("gameResultScreen")
                }
            }
        })

        val state = questionViewModel.viewState.collectAsStateWithLifecycle()
        (state.value as? QuestionViewState.State)?.let { state ->
            Text(text = "current Text: ${state.word.english}")

            Text(text = "translate Text: ${state.word.spanish}")

            Text(text = "current: ${(state.currentQuestionIndex)}/${state.totalWordCount}")

            Text(text = "counter: ${(state.remainTimeInSecond)}")
            Button(onClick = {
                questionViewModel.dispatch(QuestionAction.TranslateIsCorrect)
            }) {
                Text(text = "Correct")
            }
            Button(onClick = {
                questionViewModel.dispatch(QuestionAction.TranslateIsWrong)
            }) {
                Text(text = "Wrong")
            }
        }
    }
}