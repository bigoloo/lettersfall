package com.bigoloo.lettersfall.domain.question

import com.bigoloo.lettersfall.domain.redux.BaseStore
import com.bigoloo.lettersfall.domain.repository.WordRepository
import com.bigoloo.lettersfall.models.GameStatus
import com.bigoloo.lettersfall.ui.question.QuestionAction
import kotlinx.coroutines.CoroutineScope

class QuestionStore(
    coroutineScope: CoroutineScope,
    private val wordRepository: WordRepository,
    initialState: GameStatus
) :
    BaseStore<GameStatus, QuestionAction>(
        initialState,
        QuestionReducer(),
        listOf(QuestionCountDownMiddleware(coroutineScope))
    ) {
    override suspend fun dispatch(action: QuestionAction) {
        super.dispatch(action)
        when (action) {
            is QuestionAction.TimerCount, QuestionAction.TimerFinish,
            QuestionAction.TranslateIsCorrect, QuestionAction.TranslateIsWrong -> {
                wordRepository.setGameStatus(state.value)
            }
            else -> {}
        }
    }
}