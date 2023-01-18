package com.bigoloo.lettersfall.ui.question

import com.bigoloo.lettersfall.ui.base.ViewEffect

sealed interface QuestionEffect : ViewEffect {
    object QuestionsFinished : QuestionEffect
}