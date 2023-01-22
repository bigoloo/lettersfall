package com.bigoloo.lettersfall.ui.result

import com.bigoloo.lettersfall.domain.redux.Action
import com.bigoloo.lettersfall.models.GameStatus
import com.bigoloo.lettersfall.ui.base.ViewEffect
import com.bigoloo.lettersfall.ui.base.ViewState


sealed interface GameResultAction : Action
data class GameResultViewState(val statistics: GameStatus.Finished) : ViewState
sealed interface GameResultEffect : ViewEffect