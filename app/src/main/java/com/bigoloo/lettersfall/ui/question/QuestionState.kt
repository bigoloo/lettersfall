package com.bigoloo.lettersfall.ui.question

import com.bigoloo.lettersfall.models.ChosenLanguage
import com.bigoloo.lettersfall.models.Word
import com.bigoloo.lettersfall.ui.base.ViewState


sealed interface QuestionViewState : ViewState {
    object Initial : QuestionViewState
    data class State(
        val word: Word,
        val remainTimeInSecond: Int,
        val timePerQuestionInSecond: Int,
        val currentQuestionIndex: Int,
        val totalWordCount: Int,
        val chosenLanguage: ChosenLanguage
    ) : QuestionViewState
}