package com.bigoloo.lettersfall.data.repository

import com.bigoloo.lettersfall.data.api.WordApi
import com.bigoloo.lettersfall.domain.repository.WordRepository
import com.bigoloo.lettersfall.models.ChosenLanguage
import com.bigoloo.lettersfall.models.GameStatus
import com.bigoloo.lettersfall.models.Word
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RemoteWordRepository(private val wordApi: WordApi) : WordRepository {

    private val _gameState = MutableStateFlow<GameStatus>(GameStatus.NotStarted(10))

    override suspend fun getWordList(): List<Word> {
        return wordApi.getWordList()
    }

    override fun getGameStatus(): StateFlow<GameStatus> {
        return _gameState
    }

    override fun setGameStatus(status: GameStatus) {
        _gameState.value = status
    }

    override fun setCurrentTimerInSecond(currentTimerInSecond: Int) {
        (_gameState.value as? GameStatus.Started)?.let {
            setGameStatus(it.copy(currentTimerInSecond = currentTimerInSecond))
        }
    }

    override suspend fun startGame(chosenLanguage: ChosenLanguage) {
        wordApi.getWordList().take(5).also {
            startTheGame(it, chosenLanguage)
        }
    }

    private fun startTheGame(words: List<Word>, chosenLanguage: ChosenLanguage) {
        _gameState.value = GameStatus.Started(
            currentWord = words.first(),
            currentIndex = 0,
            correctAnswerCount = 0,
            wrongAnswerCount = 0,
            currentTimerInSecond = 0,
            unAnsweredQuestionCount = 0,
            timePerQuestionInSecond = _gameState.value.timePerQuestionInSecond,
            chosenLanguage = chosenLanguage,
            words = words
        )
    }
}