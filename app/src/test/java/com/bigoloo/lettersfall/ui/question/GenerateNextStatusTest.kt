package com.bigoloo.lettersfall.ui.question

import com.bigoloo.lettersfall.fixtures.wordList
import com.bigoloo.lettersfall.models.ChosenLanguage
import com.bigoloo.lettersfall.models.GameStatus
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GenerateNextStatusTest {

    @Test
    fun `when question is last item generateNextStatus should return Finished status`() {

        val expectedWrongAnswerCount = 3
        val expectedCorrectAnswerCount = 4
        val expectedUnAnswerCount = 5
        val expectedChosenLanguage = ChosenLanguage.English

        val currentGameStatus = GameStatus.Started(
            currentIndex = wordList.lastIndex,
            currentWord = wordList.last(),
            wrongAnswerCount = 1,
            unAnsweredQuestionCount = 1,
            correctAnswerCount = 1,
            currentTimerInSecond = 0,
            timePerQuestionInSecond = 12,
            chosenLanguage = expectedChosenLanguage,
            words = wordList
        )
        val newStatus = generateNextStatus(
            currentGameStatus,
            expectedWrongAnswerCount,
            expectedCorrectAnswerCount,
            expectedUnAnswerCount
        )

        assertTrue(newStatus is GameStatus.Finished)
        val finishedStatus = newStatus as GameStatus.Finished

        assertEquals(expectedCorrectAnswerCount, finishedStatus.correctAnswerCount)
        assertEquals(expectedWrongAnswerCount, finishedStatus.wrongAnswerCount)
        assertEquals(expectedUnAnswerCount, finishedStatus.unAnsweredQuestionCount)
        assertEquals(expectedChosenLanguage, finishedStatus.chosenLanguage)
    }

    @Test
    fun `when question is not last item generateNextStatus should return Started status`() {

        val expectedWrongAnswerCount = 3
        val expectedCorrectAnswerCount = 4
        val expectedUnAnswerCount = 5
        val expectedChosenLanguage = ChosenLanguage.English

        val currentGameStatus = GameStatus.Started(
            currentIndex = 0,
            currentWord = wordList.first(),
            wrongAnswerCount = 1,
            unAnsweredQuestionCount = 1,
            correctAnswerCount = 1,
            currentTimerInSecond = 0,
            timePerQuestionInSecond = 12,
            chosenLanguage = expectedChosenLanguage,
            words = wordList
        )
        val newStatus = generateNextStatus(
            currentGameStatus,
            expectedWrongAnswerCount,
            expectedCorrectAnswerCount,
            expectedUnAnswerCount
        )
        assertTrue(newStatus is GameStatus.Started)
        val startedStatus = newStatus as GameStatus.Started
        assertEquals(1, startedStatus.currentIndex)
        assertEquals(wordList[1], startedStatus.currentWord)
        assertEquals(expectedCorrectAnswerCount, startedStatus.correctAnswerCount)
        assertEquals(expectedWrongAnswerCount, startedStatus.wrongAnswerCount)
        assertEquals(expectedUnAnswerCount, startedStatus.unAnsweredQuestionCount)
        assertEquals(expectedChosenLanguage, startedStatus.chosenLanguage)
        assertEquals(0, startedStatus.currentTimerInSecond)
    }
}