package com.bigoloo.lettersfall.ui.result

import com.bigoloo.lettersfall.models.GameStatus
import com.bigoloo.lettersfall.ui.base.ViewAction
import com.bigoloo.lettersfall.ui.base.ViewEffect
import com.bigoloo.lettersfall.ui.base.ViewState


sealed interface GameResultAction : ViewAction
data class GameResultViewState(val statistics: GameStatus.Finished) : ViewState
sealed interface GameResultEffect : ViewEffect