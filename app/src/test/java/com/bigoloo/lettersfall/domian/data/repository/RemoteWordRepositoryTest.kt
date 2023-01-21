package com.bigoloo.lettersfall.domian.data.repository

import com.bigoloo.lettersfall.data.repository.RemoteWordRepository
import com.bigoloo.lettersfall.fixtures.dummyWordApi
import com.bigoloo.lettersfall.fixtures.wordList
import com.bigoloo.lettersfall.models.ChosenLanguage
import com.bigoloo.lettersfall.models.GameStatus
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RemoteWordRepositoryTest {

    @Test
    fun `when repository getWordList is called data should be returned`() = runBlocking {
        val wordRepository = RemoteWordRepository(dummyWordApi)
        val data = wordRepository.getWordList()
        assertEquals(wordList, data)
    }

    @Test
    fun `when startGame is called then gamestate should be started`() = runBlocking {
        val wordRepository = RemoteWordRepository(dummyWordApi)
        wordRepository.startGame(ChosenLanguage.English)
        val gameStatus = wordRepository.getGameStatus().value

        assertTrue(gameStatus is GameStatus.Started)
        val started = gameStatus as GameStatus.Started
        assertEquals(ChosenLanguage.English, started.chosenLanguage)
        assertEquals(wordList, started.words)
        assertEquals(0, started.unAnsweredQuestionCount)
        assertEquals(0, started.wrongAnswerCount)
        assertEquals(0, started.correctAnswerCount)
        assertEquals(10, started.timePerQuestionInSecond)
        assertEquals(0, started.currentTimerInSecond)
        assertEquals(wordList.first(), started.currentWord)
        assertEquals(0, started.currentIndex)

    }
}