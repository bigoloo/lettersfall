package com.bigoloo.lettersfall.domian.data.repository

import com.bigoloo.lettersfall.domian.data.api.WordApi
import com.bigoloo.lettersfall.domian.repository.WordRepository
import com.bigoloo.lettersfall.models.ChosenLanguage
import com.bigoloo.lettersfall.models.GameStatus
import com.bigoloo.lettersfall.models.Word
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RemoteWordRepository(private val wordApi: WordApi) : WordRepository {

    private val _gameState = MutableStateFlow<GameStatus>(GameStatus.NotStarted)

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

    override suspend fun startGame() {
        wordApi.getWordList().take(5).also {
            startTheGame(it)
        }
    }

    private fun startTheGame(words: List<Word>) {
        _gameState.value = GameStatus.Started(
            currentWord = words.first(),
            currentIndex = 0,
            correctAnswerCount = 0,
            wrongAnswerCount = 0,
            currentTimerInSecond = 0,
            unAnsweredQuestionCount = 0,
            timePerQuestionInSecond = 10,
            chosenLanguage = ChosenLanguage.English,
            words = words
        )
    }
}