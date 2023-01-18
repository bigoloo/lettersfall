package com.bigoloo.lettersfall.domian.repository

import com.bigoloo.lettersfall.models.GameStatus
import com.bigoloo.lettersfall.models.Word
import kotlinx.coroutines.flow.StateFlow

interface WordRepository {
    suspend fun getWordList(): List<Word>
    fun getGameStatus(): StateFlow<GameStatus>
    fun setGameStatus(status: GameStatus)
    fun setCurrentTimerInSecond(currentTimerInSecond: Int)
    suspend fun startGame()
}