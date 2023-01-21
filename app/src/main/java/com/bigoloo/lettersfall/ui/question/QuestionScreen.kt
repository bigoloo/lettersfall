@file:OptIn(ExperimentalAnimationApi::class)

package com.bigoloo.lettersfall.ui.question

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bigoloo.lettersfall.R
import com.bigoloo.lettersfall.models.ChosenLanguage
import com.bigoloo.lettersfall.models.Word
import com.bigoloo.lettersfall.models.flyingWord
import com.bigoloo.lettersfall.models.mainWord
import com.bigoloo.lettersfall.ui.base.ui.ComposableLifecycle
import com.bigoloo.lettersfall.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuestionScreen(modifier: Modifier, navController: NavController) {

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
        QuestionContent(modifier, state) {
            questionViewModel.dispatch(it)
        }
    }
}

@Composable
fun QuestionContent(
    modifier: Modifier,
    state: QuestionViewState.State,
    onAction: (action: QuestionAction) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val configuration = LocalConfiguration.current
        val offsetAnimation: Dp by animateDpAsState(
            ((state.timePerQuestionInSecond - state.remainTimeInSecond).toFloat()
                .div(state.timePerQuestionInSecond)
                    * configuration.screenHeightDp).dp,
            animationSpec = tween(easing = LinearOutSlowInEasing)
        )
        Spacer(modifier = modifier.height(32.dp))
        Text(text = state.word.mainWord(state.chosenLanguage), style = Typography.headlineMedium)
        Text(
            modifier = modifier.absoluteOffset(y = offsetAnimation),
            style = Typography.headlineSmall,
            text = state.word.flyingWord(state.chosenLanguage)
        )

        Spacer(modifier = modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "${(state.currentQuestionIndex)} / ${state.totalWordCount}")

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = stringResource(id = R.string.remain_time))
                AnimatedContent(targetState = state.remainTimeInSecond, transitionSpec = {
                    addAnimation().using(
                        SizeTransform(clip = false)
                    )
                }) {
                    Text(text = "${(state.remainTimeInSecond)}")
                }
            }
        }
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(modifier = Modifier.testTag("correctButton"), onClick = {
                onAction(QuestionAction.TranslateIsCorrect)
            }) {
                Text(text = stringResource(id = R.string.btn_correct_text))
            }
            Button(modifier = Modifier.testTag("wrongButton"), onClick = {
                onAction(QuestionAction.TranslateIsWrong)
            }) {
                Text(text = stringResource(id = R.string.btn_wrong_text))
            }
        }
    }

}

@Preview
@Composable
fun QuestionContentPreview() {
    val viewState =
        QuestionViewState.State(
            Word("English", "Spanish"), 12,
            33, 3,
            12, ChosenLanguage.Spanish
        )

    QuestionContent(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(), state = viewState) {

    }
}

fun addAnimation(duration: Int = 1000): ContentTransform {
    return slideInVertically(animationSpec = tween(durationMillis = duration)) { height -> height } + fadeIn(
        animationSpec = tween(durationMillis = duration)
    ) with slideOutVertically(animationSpec = tween(durationMillis = duration)) { height -> -height } + fadeOut(
        animationSpec = tween(durationMillis = duration)
    )
}