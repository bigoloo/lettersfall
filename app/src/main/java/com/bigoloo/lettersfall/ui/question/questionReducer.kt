package com.bigoloo.lettersfall.ui.question

import com.bigoloo.lettersfall.models.GameStatus

fun reducer(
    currentGameStatus: GameStatus.Started,
    action: QuestionAction
): GameStatus {
    val unAnsweredQuestionCount = (action as? QuestionAction.TimerFinish)?.let {
        currentGameStatus.unAnsweredQuestionCount + 1
    } ?: currentGameStatus.unAnsweredQuestionCount

    val wrongAnswerCount = (action as? QuestionAction.TranslateIsWrong)?.let {
        currentGameStatus.wrongAnswerCount + 1
    } ?: currentGameStatus.wrongAnswerCount


    val correctAnswerCount = (action as? QuestionAction.TranslateIsCorrect)?.let {
        currentGameStatus.correctAnswerCount + 1
    } ?: currentGameStatus.correctAnswerCount

    return when (action) {
        is QuestionAction.TimerCount -> {
            currentGameStatus.copy(
                currentTimerInSecond = action.currentTimerInSecond,
            )
        }
        QuestionAction.TimerFinish, QuestionAction.TranslateIsCorrect, QuestionAction.TranslateIsWrong -> {
            generateNextStatus(
                currentGameStatus,
                wrongAnswerCount,
                correctAnswerCount,
                unAnsweredQuestionCount
            )
        }
        else -> {
            currentGameStatus
        }
    }
}

fun generateNextStatus(
    currentGameStatus: GameStatus.Started,
    wrongAnswerCount: Int,
    correctAnswerCount: Int,
    unAnsweredQuestionCount: Int
) = if (currentGameStatus.currentIndex == currentGameStatus.words.lastIndex) {
    GameStatus.Finished(
        wrongAnswerCount = wrongAnswerCount,
        correctAnswerCount = correctAnswerCount,
        unAnsweredQuestionCount = unAnsweredQuestionCount,
        chosenLanguage = currentGameStatus.chosenLanguage
    )
} else {
    currentGameStatus.copy(
        currentWord = currentGameStatus.words[currentGameStatus.currentIndex + 1],
        wrongAnswerCount = wrongAnswerCount,
        correctAnswerCount = correctAnswerCount,
        unAnsweredQuestionCount = unAnsweredQuestionCount,
        currentIndex = currentGameStatus.currentIndex + 1,
        currentTimerInSecond = 0,
    )
}