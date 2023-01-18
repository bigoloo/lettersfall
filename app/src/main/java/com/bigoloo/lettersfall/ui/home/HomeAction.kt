package com.bigoloo.lettersfall.ui.home

import com.bigoloo.lettersfall.models.ChosenLanguage
import com.bigoloo.lettersfall.ui.base.ViewAction

sealed interface HomeAction : ViewAction {

    data class InitiateGame(val chosenLanguage: ChosenLanguage) : HomeAction
}