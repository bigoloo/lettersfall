package com.bigoloo.lettersfall.fixtures

import com.bigoloo.lettersfall.domain.repository.WordRepository
import com.bigoloo.lettersfall.models.ChosenLanguage
import com.bigoloo.lettersfall.models.GameStatus
import com.bigoloo.lettersfall.models.Word
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class InitialStateWordRepository : WordRepository {
    override suspend fun getWordList(): List<Word> {
        return wordList
    }

    override fun getGameStatus(): StateFlow<GameStatus> {
        return MutableStateFlow(firstQuestionGameStatus)
    }

    override fun setGameStatus(status: GameStatus) {
        TODO("Not yet implemented")
    }

    override fun setCurrentTimerInSecond(currentTimerInSecond: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun startGame(chosenLanguage: ChosenLanguage) {
        TODO("Not yet implemented")
    }
}

class FirstQuestionWordRepository : WordRepository {
    val currentState = MutableStateFlow<GameStatus>(firstQuestionGameStatus)
    override suspend fun getWordList(): List<Word> {
        return wordList
    }

    override fun getGameStatus(): StateFlow<GameStatus> {
        return currentState
    }

    override fun setGameStatus(status: GameStatus) {
        currentState.value = status
    }

    override fun setCurrentTimerInSecond(currentTimerInSecond: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun startGame(chosenLanguage: ChosenLanguage) {
        TODO("Not yet implemented")
    }
}

class LastQuestionWordRepository : WordRepository {
    val currentState = MutableStateFlow<GameStatus>(lastQuestionGameStatus)
    override suspend fun getWordList(): List<Word> {
        return wordList
    }

    override fun getGameStatus(): StateFlow<GameStatus> {
        return currentState
    }

    override fun setGameStatus(status: GameStatus) {
        currentState.value = status
    }

    override fun setCurrentTimerInSecond(currentTimerInSecond: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun startGame(chosenLanguage: ChosenLanguage) {
        TODO("Not yet implemented")
    }
}