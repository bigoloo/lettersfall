package com.bigoloo.lettersfall.ui.question

import com.bigoloo.lettersfall.domain.question.QuestionReducer
import com.bigoloo.lettersfall.fixtures.firstQuestionGameStatus
import com.bigoloo.lettersfall.fixtures.lastQuestionGameStatus
import com.bigoloo.lettersfall.models.GameStatus
import org.junit.Assert
import org.junit.Test


class QuestionReducerTest {

    @Test
    fun `when reducer with action TimerCount is called then currentTimer should be updated`() {

        val expectedTimeCount = QuestionAction.TimerCount(5)

        val reducer = QuestionReducer()
        val newStatus = reducer.reduce(
            lastQuestionGameStatus,
            expectedTimeCount
        )

        Assert.assertTrue(newStatus is GameStatus.Started)
        val startedStatus = newStatus as GameStatus.Started

        Assert.assertEquals(
            expectedTimeCount.currentTimerInSecond,
            startedStatus.currentTimerInSecond
        )
    }

    @Test
    fun `when reducer with action TimerFinish is called then UnAnsweredCount should be updated`() {

        val expectedUnAnswerCount = 2
        val reducer = QuestionReducer()
        val newStatus = reducer.reduce(
            firstQuestionGameStatus.copy(unAnsweredQuestionCount = expectedUnAnswerCount - 1),
            QuestionAction.TimerFinish
        )
        Assert.assertTrue(newStatus is GameStatus.Started)
        val startedStatus = newStatus as GameStatus.Started
        Assert.assertEquals(expectedUnAnswerCount, startedStatus.unAnsweredQuestionCount)
    }

    @Test
    fun `when reducer with action TranslateIsCorrect is called then CorrectAnswer should be updated`() {

        val expectedCorrectUnAnswerCount = 2
        val reducer = QuestionReducer()
        val newStatus = reducer.reduce(
            firstQuestionGameStatus.copy(correctAnswerCount = expectedCorrectUnAnswerCount - 1),
            QuestionAction.TranslateIsCorrect
        )
        Assert.assertTrue(newStatus is GameStatus.Started)
        val startedStatus = newStatus as GameStatus.Started
        Assert.assertEquals(expectedCorrectUnAnswerCount, startedStatus.correctAnswerCount)
    }

    @Test
    fun `when reducer with action TranslateIsWrong is called then wrongAnswerCount should be updated`() {

        val expectedWrongUnAnswerCount = 2
        val reducer = QuestionReducer()
        val newStatus = reducer.reduce(
            firstQuestionGameStatus.copy(correctAnswerCount = expectedWrongUnAnswerCount - 1),
            QuestionAction.TranslateIsWrong
        )

        Assert.assertTrue(newStatus is GameStatus.Started)
        val startedStatus = newStatus as GameStatus.Started
        Assert.assertEquals(expectedWrongUnAnswerCount, startedStatus.wrongAnswerCount)
    }

    @Test
    fun `when reducer with action ViewStart is called then state should be same`() {

        val reducer = QuestionReducer()
        val newStatus = reducer.reduce(
            firstQuestionGameStatus,
            QuestionAction.ViewStart
        )

        Assert.assertEquals(firstQuestionGameStatus, newStatus)
    }

    @Test
    fun `when reducer with action ViewStop is called then state should be same`() {
        val reducer = QuestionReducer()
        val newStatus = reducer.reduce(
            firstQuestionGameStatus,
            QuestionAction.ViewStop
        )

        Assert.assertEquals(firstQuestionGameStatus, newStatus)
    }
}