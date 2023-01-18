package com.bigoloo.lettersfall.ui.home

import androidx.lifecycle.viewModelScope
import com.bigoloo.lettersfall.domian.repository.WordRepository
import com.bigoloo.lettersfall.ui.base.Actionable
import com.bigoloo.lettersfall.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(private val wordRepository: WordRepository) :
    BaseViewModel<HomeAction, HomeState, HomeEffect>(HomeState.InitialState) {

    init {
        getGameStatus()
    }

    private fun getGameStatus() {
        viewModelScope.launch {
            setState {
                HomeState.Loaded(
                    wordRepository.getGameStatus().value,
                    Actionable.NotStarted
                )
            }
        }
    }


    override fun dispatch(action: HomeAction) {
        when (action) {
            HomeAction.InitiateGame -> initiateGame()
        }
    }

    private fun initiateGame() {
        viewModelScope.launch {
            setState {
                asLoaded()!!.copy(startGameActionable = Actionable.InProgress)
            }

            runCatching {
                wordRepository.startGame()
            }.onSuccess {
                setState {
                    asLoaded()!!.copy(startGameActionable = Actionable.Success)
                }
                launchEffect(HomeEffect.NavigateToQuestionScreen)
            }.onFailure {
                setState {
                    asLoaded()!!.copy(
                        startGameActionable = Actionable.Failed(
                            it
                        )
                    )
                }
            }
        }
    }
}


