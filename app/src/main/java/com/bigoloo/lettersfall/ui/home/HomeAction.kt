package com.bigoloo.lettersfall.ui.home

import com.bigoloo.lettersfall.ui.base.ViewAction

sealed interface HomeAction : ViewAction {

    object InitiateGame : HomeAction
}