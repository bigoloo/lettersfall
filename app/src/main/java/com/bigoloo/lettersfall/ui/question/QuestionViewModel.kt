package com.bigoloo.lettersfall.ui.question

import androidx.lifecycle.viewModelScope
import com.bigoloo.lettersfall.domian.repository.WordRepository
import com.bigoloo.lettersfall.models.GameStatus
import com.bigoloo.lettersfall.ui.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class QuestionViewModel(private val wordRepository: WordRepository) :
    BaseViewModel<QuestionAction, QuestionViewState, QuestionEffect>(QuestionViewState.Initial) {

    init {
        loadCurrentState()
    }

    private var timerJob: Job? = null
    private fun startTimer(currentTimerInSecond: Int, timePerQuestionInSecond: Int) {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            repeat(times = timePerQuestionInSecond - currentTimerInSecond) {
                dispatch(QuestionAction.TimerCount(currentTimerInSecond + it))
                delay(1_000)
            }
            dispatch(QuestionAction.TimerFinish)
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
    }

    private fun loadCurrentState() {
        wordRepository.getGameStatus().filterIsInstance<GameStatus.Started>().onEach {
            mapGameStatusToViewState(it)
        }.launchIn(viewModelScope)
    }

    private fun mapGameStatusToViewState(status: GameStatus.Started) {
        viewModelScope.launch {
            if (status.timePerQuestionInSecond - status.currentTimerInSecond != 0) {
                setState {
                    QuestionViewState.State(
                        status.currentWord,
                        (status.timePerQuestionInSecond - status.currentTimerInSecond),
                        status.timePerQuestionInSecond,
                        status.currentIndex + 1,
                        status.words.size,
                        chosenLanguage = status.chosenLanguage
                    )
                }
            }
        }
    }

    override fun dispatch(action: QuestionAction) {

        val currentGameStatus = wordRepository.getGameStatus().value as? GameStatus.Started
            ?: return
        when (action) {
            QuestionAction.ViewStart -> {
                startTimer(
                    currentGameStatus.currentTimerInSecond,
                    currentGameStatus.timePerQuestionInSecond
                )
            }
            QuestionAction.ViewStop -> {
                stopTimer()
            }
            is QuestionAction.TimerCount -> {
                reducer(currentGameStatus, action).also {
                    wordRepository.setGameStatus(it)
                }
            }

            QuestionAction.TimerFinish, QuestionAction.TranslateIsCorrect, QuestionAction.TranslateIsWrong -> {
                reducer(currentGameStatus, action).also {
                    wordRepository.setGameStatus(it)
                }
                restartTimer(currentGameStatus)
            }
        }
        checkFinishGame(currentGameStatus, action)
    }

    private fun restartTimer(currentGameStatus: GameStatus.Started) {
        if (currentGameStatus.currentIndex != currentGameStatus.words.lastIndex) {
            startTimer(
                0,
                currentGameStatus.timePerQuestionInSecond
            )
        }
    }

    private fun checkFinishGame(currentGameStatus: GameStatus.Started, action: QuestionAction) {
        if (currentGameStatus.currentIndex == currentGameStatus.words.lastIndex &&
            (action is QuestionAction.TimerFinish || action is QuestionAction.TranslateIsWrong ||
                    action is QuestionAction.TranslateIsCorrect)
        ) {
            launchEffect(QuestionEffect.QuestionsFinished)
        }
    }
}