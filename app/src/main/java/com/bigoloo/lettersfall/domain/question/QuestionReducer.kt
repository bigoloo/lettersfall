package com.bigoloo.lettersfall.domain.question

import androidx.annotation.VisibleForTesting
import com.bigoloo.lettersfall.domain.redux.Reducer
import com.bigoloo.lettersfall.models.GameStatus
import com.bigoloo.lettersfall.ui.question.QuestionAction

class QuestionReducer : Reducer<GameStatus, QuestionAction> {

    override fun reduce(
        currentState: GameStatus,
        action: QuestionAction
    ): GameStatus {
        if (currentState !is GameStatus.Started) return currentState
        val unAnsweredQuestionCount = (action as? QuestionAction.TimerFinish)?.let {
            currentState.unAnsweredQuestionCount + 1
        } ?: currentState.unAnsweredQuestionCount

        val wrongAnswerCount = (action as? QuestionAction.TranslateIsWrong)?.let {
            currentState.wrongAnswerCount + 1
        } ?: currentState.wrongAnswerCount


        val correctAnswerCount = (action as? QuestionAction.TranslateIsCorrect)?.let {
            currentState.correctAnswerCount + 1
        } ?: currentState.correctAnswerCount

        return when (action) {
            is QuestionAction.TimerCount -> {
                currentState.copy(
                    currentTimerInSecond = action.currentTimerInSecond,
                )
            }
            QuestionAction.TimerFinish, QuestionAction.TranslateIsCorrect, QuestionAction.TranslateIsWrong -> {
                generateNextStatus(
                    currentState,
                    wrongAnswerCount,
                    correctAnswerCount,
                    unAnsweredQuestionCount
                )
            }
            else -> {
                currentState
            }
        }
    }

    @VisibleForTesting
    fun generateNextStatus(
        currentState: GameStatus.Started,
        wrongAnswerCount: Int,
        correctAnswerCount: Int,
        unAnsweredQuestionCount: Int
    ) = if (currentState.currentIndex == currentState.words.lastIndex) {
        GameStatus.Finished(
            wrongAnswerCount = wrongAnswerCount,
            correctAnswerCount = correctAnswerCount,
            unAnsweredQuestionCount = unAnsweredQuestionCount,
            chosenLanguage = currentState.chosenLanguage,
            timePerQuestionInSecond = currentState.timePerQuestionInSecond
        )
    } else {
        currentState.copy(
            currentWord = currentState.words[currentState.currentIndex + 1],
            wrongAnswerCount = wrongAnswerCount,
            correctAnswerCount = correctAnswerCount,
            unAnsweredQuestionCount = unAnsweredQuestionCount,
            currentIndex = currentState.currentIndex + 1,
            currentTimerInSecond = 0,
        )
    }
}