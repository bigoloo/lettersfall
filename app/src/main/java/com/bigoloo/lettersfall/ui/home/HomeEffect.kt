package com.bigoloo.lettersfall.ui.home

import com.bigoloo.lettersfall.ui.base.ViewEffect

sealed interface HomeEffect : ViewEffect {
    object NavigateToQuestionScreen : HomeEffect
}