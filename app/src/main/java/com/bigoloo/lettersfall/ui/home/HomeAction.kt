package com.bigoloo.lettersfall.ui.home

import com.bigoloo.lettersfall.domain.redux.Action
import com.bigoloo.lettersfall.models.ChosenLanguage

sealed interface HomeAction : Action {

    data class InitiateGame(val chosenLanguage: ChosenLanguage) : HomeAction
}