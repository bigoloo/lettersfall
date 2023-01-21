package com.bigoloo.lettersfall.ui.question

import com.bigoloo.lettersfall.fixtures.FirstQuestionWordRepository
import com.bigoloo.lettersfall.fixtures.InitialStateWordRepository
import com.bigoloo.lettersfall.fixtures.LastQuestionWordRepository
import com.bigoloo.lettersfall.fixtures.wordList
import com.bigoloo.lettersfall.models.GameStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class QuestionViewModelTest {

    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearUp() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewModel is created state should load from repository`() = runTest {
        val mockWordRepository = InitialStateWordRepository()
        val status = mockWordRepository.getGameStatus().value as GameStatus.Started

        val questionViewModel = QuestionViewModel(mockWordRepository)
        val actualViewState = questionViewModel.viewState.value

        val expectedViewState = QuestionViewState.State(
            status.currentWord,
            (status.timePerQuestionInSecond - status.currentTimerInSecond),
            status.timePerQuestionInSecond,
            status.currentIndex + 1,
            status.words.size,
            chosenLanguage = status.chosenLanguage
        )
        Assert.assertEquals(expectedViewState, actualViewState)
    }

    @Test
    fun `when action TimeCounter is triggered viewState should updated`() = runTest {
        val mockWordRepository = FirstQuestionWordRepository()
        val status = mockWordRepository.getGameStatus().value as GameStatus.Started
        val action = QuestionAction.TimerCount(7)
        val questionViewModel = QuestionViewModel(mockWordRepository)

        questionViewModel.dispatch(action)
        val actualViewState = questionViewModel.viewState.value

        val expectedViewState = QuestionViewState.State(
            status.currentWord,
            (status.timePerQuestionInSecond - action.currentTimerInSecond),
            status.timePerQuestionInSecond,
            status.currentIndex + 1,
            status.words.size,
            chosenLanguage = status.chosenLanguage
        )
        Assert.assertEquals(expectedViewState, actualViewState)
    }

    @Test
    fun `when action TimeFinish is triggered viewState should updated`() = runTest {
        val mockWordRepository = FirstQuestionWordRepository()
        val status = mockWordRepository.getGameStatus().value as GameStatus.Started
        val action = QuestionAction.TimerFinish
        val questionViewModel = QuestionViewModel(mockWordRepository)

        questionViewModel.dispatch(action)
        val actualViewState = questionViewModel.viewState.value

        val expectedViewState = QuestionViewState.State(
            word = wordList[1],
            remainTimeInSecond = (status.timePerQuestionInSecond),
            timePerQuestionInSecond = status.timePerQuestionInSecond,
            status.currentIndex + 2,
            status.words.size,
            chosenLanguage = status.chosenLanguage
        )
        Assert.assertEquals(expectedViewState, actualViewState)
    }

    @Test
    fun `when action TranslateIsCorrect is triggered viewState should updated`() = runTest {
        val mockWordRepository = FirstQuestionWordRepository()
        val status = mockWordRepository.getGameStatus().value as GameStatus.Started
        val action = QuestionAction.TranslateIsCorrect
        val questionViewModel = QuestionViewModel(mockWordRepository)

        questionViewModel.dispatch(action)
        val actualViewState = questionViewModel.viewState.value

        val expectedViewState = QuestionViewState.State(
            word = wordList[1],
            remainTimeInSecond = (status.timePerQuestionInSecond),
            timePerQuestionInSecond = status.timePerQuestionInSecond,
            status.currentIndex + 2,
            status.words.size,
            chosenLanguage = status.chosenLanguage
        )
        Assert.assertEquals(expectedViewState, actualViewState)
    }

    @Test
    fun `when action TranslateIsWrong is triggered viewState should updated`() = runTest {
        val mockWordRepository = FirstQuestionWordRepository()
        val status = mockWordRepository.getGameStatus().value as GameStatus.Started
        val action = QuestionAction.TranslateIsWrong
        val questionViewModel = QuestionViewModel(mockWordRepository)

        questionViewModel.dispatch(action)
        val actualViewState = questionViewModel.viewState.value

        val expectedViewState = QuestionViewState.State(
            word = wordList[1],
            remainTimeInSecond = (status.timePerQuestionInSecond),
            timePerQuestionInSecond = status.timePerQuestionInSecond,
            status.currentIndex + 2,
            status.words.size,
            chosenLanguage = status.chosenLanguage
        )
        Assert.assertEquals(expectedViewState, actualViewState)
    }

    @Test
    fun `when it is last question navigate effect should triggered`() = runTest {
        val mockWordRepository = LastQuestionWordRepository()

        val action = QuestionAction.TranslateIsWrong
        val questionViewModel = QuestionViewModel(mockWordRepository)
        val actualEffect = mutableListOf<QuestionEffect>()

        val job = launch(testDispatcher) {
            questionViewModel.effect.toList(actualEffect)
        }

        questionViewModel.dispatch(action)
        Assert.assertEquals(listOf(QuestionEffect.QuestionsFinished), actualEffect)
        job.cancel()
    }
}