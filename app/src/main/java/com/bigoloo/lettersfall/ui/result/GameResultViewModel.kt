package com.bigoloo.lettersfall.ui.result

import androidx.lifecycle.viewModelScope
import com.bigoloo.lettersfall.domain.repository.WordRepository
import com.bigoloo.lettersfall.models.GameStatus
import com.bigoloo.lettersfall.ui.base.BaseViewModel
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class GameResultViewModel(private val wordRepository: WordRepository) :
    BaseViewModel<GameResultAction, GameResultViewState?, GameResultEffect>(null) {
    init {
        loadGameStatus()
    }

    private fun loadGameStatus() {
        wordRepository.getGameStatus().filterIsInstance<GameStatus.Finished>().onEach {
            setState {
                GameResultViewState(it)
            }
        }.launchIn(viewModelScope)
    }

    override fun dispatch(action: GameResultAction) {}
}