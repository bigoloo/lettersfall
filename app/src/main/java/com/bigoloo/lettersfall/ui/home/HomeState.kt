package com.bigoloo.lettersfall.ui.home

import com.bigoloo.lettersfall.models.GameStatus
import com.bigoloo.lettersfall.ui.base.Actionable
import com.bigoloo.lettersfall.ui.base.ViewState


sealed interface HomeState : ViewState {
    fun asLoaded(): Loaded? = this as? Loaded

    object InitialState : HomeState
    data class Loaded(
        val gameState: GameStatus,
        val startGameActionable: Actionable
    ) : HomeState
}
