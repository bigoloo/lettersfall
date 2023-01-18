package com.bigoloo.lettersfall.ui.question

import androidx.lifecycle.viewModelScope
import com.bigoloo.lettersfall.domian.repository.WordRepository
import com.bigoloo.lettersfall.models.ChosenLanguage
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
                        status.currentIndex + 1,
                        status.words.size
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
                wordRepository.setGameStatus(produceNextGameState(currentGameStatus, action))
            }
            QuestionAction.TimerFinish -> {
                wordRepository.setGameStatus(produceNextGameState(currentGameStatus, action))
                if (currentGameStatus.currentIndex != currentGameStatus.words.lastIndex) {
                    startTimer(
                        0,
                        currentGameStatus.timePerQuestionInSecond
                    )
                }
            }
            QuestionAction.TranslateIsCorrect -> {
                wordRepository.setGameStatus(produceNextGameState(currentGameStatus, action))
                if (currentGameStatus.currentIndex != currentGameStatus.words.lastIndex) {
                    startTimer(
                        0,
                        currentGameStatus.timePerQuestionInSecond
                    )
                }

            }
            QuestionAction.TranslateIsWrong -> {
                wordRepository.setGameStatus(produceNextGameState(currentGameStatus, action))
                if (currentGameStatus.currentIndex != currentGameStatus.words.lastIndex) {
                    startTimer(
                        0,
                        currentGameStatus.timePerQuestionInSecond
                    )
                }
            }
        }
        checkFinishGame(currentGameStatus, action)
    }


    private fun produceNextGameState(
        currentGameStatus: GameStatus.Started,
        action: QuestionAction
    ): GameStatus {
        val unAnsweredQuestionCount = (action as? QuestionAction.TimerFinish)?.let {
            currentGameStatus.unAnsweredQuestionCount + 1
        } ?: 1
        return when (action) {
            is QuestionAction.TimerCount -> {
                currentGameStatus.copy(
                    currentTimerInSecond = action.currentTimerInSecond,
                )
            }
            QuestionAction.TimerFinish -> {
                if (currentGameStatus.currentIndex == currentGameStatus.words.lastIndex) {
                    GameStatus.Finished(
                        wrongAnswerCount = currentGameStatus.wrongAnswerCount,
                        correctAnswerCount = currentGameStatus.correctAnswerCount,
                        unAnsweredQuestionCount = unAnsweredQuestionCount + 1,
                        chosenLanguage = ChosenLanguage.English
                    )
                } else {
                    currentGameStatus.copy(
                        currentWord = currentGameStatus.words[currentGameStatus.currentIndex + 1],
                        currentIndex = currentGameStatus.currentIndex + 1,
                        unAnsweredQuestionCount = unAnsweredQuestionCount + 1,
                        currentTimerInSecond = 0,
                    )
                }
            }
            QuestionAction.TranslateIsCorrect -> {
                if (currentGameStatus.currentIndex == currentGameStatus.words.lastIndex) {
                    GameStatus.Finished(
                        wrongAnswerCount = currentGameStatus.wrongAnswerCount,
                        correctAnswerCount = currentGameStatus.correctAnswerCount + 1,
                        unAnsweredQuestionCount = unAnsweredQuestionCount,
                        chosenLanguage = ChosenLanguage.English
                    )
                } else {
                    currentGameStatus.copy(
                        currentWord = currentGameStatus.words[currentGameStatus.currentIndex + 1],
                        correctAnswerCount = currentGameStatus.correctAnswerCount + 1,
                        currentIndex = currentGameStatus.currentIndex + 1,
                        currentTimerInSecond = 0,
                    )
                }
            }
            QuestionAction.TranslateIsWrong -> {
                if (currentGameStatus.currentIndex == currentGameStatus.words.lastIndex) {
                    GameStatus.Finished(
                        correctAnswerCount = currentGameStatus.correctAnswerCount,
                        wrongAnswerCount = currentGameStatus.wrongAnswerCount + 1,
                        unAnsweredQuestionCount = unAnsweredQuestionCount,
                        chosenLanguage = ChosenLanguage.English
                    )
                } else {
                    currentGameStatus.copy(
                        currentWord = currentGameStatus.words[currentGameStatus.currentIndex + 1],
                        wrongAnswerCount = currentGameStatus.wrongAnswerCount + 1,
                        currentIndex = currentGameStatus.currentIndex + 1,
                        currentTimerInSecond = 0,
                    )
                }
            }
            else -> {
                currentGameStatus
            }
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