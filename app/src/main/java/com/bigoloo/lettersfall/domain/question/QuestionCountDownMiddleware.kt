package com.bigoloo.lettersfall.domain.question

import com.bigoloo.lettersfall.domain.redux.Middleware
import com.bigoloo.lettersfall.domain.redux.Store
import com.bigoloo.lettersfall.models.GameStatus
import com.bigoloo.lettersfall.ui.question.QuestionAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuestionCountDownMiddleware(private val coroutineScope: CoroutineScope) :
    Middleware<GameStatus, QuestionAction> {
    override suspend fun process(
        action: QuestionAction,
        currentState: GameStatus,
        store: Store<GameStatus, QuestionAction>
    ) {

        when (action) {
            QuestionAction.ViewStart -> {
                startTimer(
                    store,
                    if (currentState !is GameStatus.Started) 0 else currentState.currentTimerInSecond,
                    currentState.timePerQuestionInSecond
                )
            }
            QuestionAction.ViewStop -> {
                stopTimer()
            }

            QuestionAction.TimerFinish, QuestionAction.TranslateIsCorrect, QuestionAction.TranslateIsWrong -> {
                restartTimer(store, currentState as GameStatus.Started)
            }
            else -> {}
        }
    }

    private var timerJob: Job? = null
    private fun startTimer(
        store: Store<GameStatus, QuestionAction>,
        currentTimerInSecond: Int,
        timePerQuestionInSecond: Int
    ) {
        timerJob?.cancel()
        timerJob = coroutineScope.launch {
            repeat(times = timePerQuestionInSecond - currentTimerInSecond) {
                store.dispatch(QuestionAction.TimerCount(currentTimerInSecond + it))
                delay(1_000)
            }
            store.dispatch(QuestionAction.TimerFinish)
        }
    }

    private fun restartTimer(
        store: Store<GameStatus, QuestionAction>,
        currentGameStatus: GameStatus.Started
    ) {
        if (currentGameStatus.currentIndex != currentGameStatus.words.lastIndex) {
            startTimer(
                store,
                0,
                currentGameStatus.timePerQuestionInSecond
            )
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
    }

}