package com.bigoloo.lettersfall.ui.question

import com.bigoloo.lettersfall.domain.redux.Action


sealed interface QuestionAction : Action {
    object TranslateIsCorrect : QuestionAction
    object TranslateIsWrong : QuestionAction
    object TimerFinish : QuestionAction
    data class TimerCount(val currentTimerInSecond: Int) : QuestionAction
    object ViewStart : QuestionAction
    object ViewStop : QuestionAction
}