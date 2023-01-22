package com.bigoloo.lettersfall.ui.question

import androidx.lifecycle.viewModelScope
import com.bigoloo.lettersfall.domain.question.QuestionStore
import com.bigoloo.lettersfall.domain.repository.WordRepository
import com.bigoloo.lettersfall.models.GameStatus
import com.bigoloo.lettersfall.ui.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class QuestionViewModel(
    wordRepository: WordRepository
) :
    BaseViewModel<QuestionAction, QuestionViewState, QuestionEffect>(QuestionViewState.Initial) {

    private val questionStore =
        QuestionStore(viewModelScope, wordRepository, wordRepository.getGameStatus().value)

    init {
        listenToGameStatus()
    }

    private fun listenToGameStatus() {
        questionStore.state
            .onEach { gameStatus ->
                when (gameStatus) {
                    is GameStatus.Finished -> {
                        launchEffect(QuestionEffect.QuestionsFinished)
                    }
                    is GameStatus.Started -> produceViewState(gameStatus)
                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    private fun produceViewState(status: GameStatus.Started) {
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
        viewModelScope.launch {
            questionStore.dispatch(action)
        }
    }
}