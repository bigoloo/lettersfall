package com.bigoloo.lettersfall.ui.question

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.bigoloo.lettersfall.models.ChosenLanguage
import com.bigoloo.lettersfall.models.Word
import com.bigoloo.lettersfall.models.flyingWord
import com.bigoloo.lettersfall.models.mainWord
import org.junit.Rule
import org.junit.Test

class QuestionScreenTest {


    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun when_state_is_emmit_then_view_should_exist() {
        val word = Word(english = "english", spanish = "Spanish")
        val viewState = QuestionViewState.State(
            word = word,
            remainTimeInSecond = 10,
            currentQuestionIndex = 12,
            timePerQuestionInSecond = 20,
            totalWordCount = 20,
            chosenLanguage = ChosenLanguage.English
        )
        composeTestRule.setContent {
            QuestionContent(modifier = Modifier, state = viewState) {

            }
        }

        composeTestRule.onNodeWithText(word.mainWord(viewState.chosenLanguage)).assertIsDisplayed()
        composeTestRule.onNodeWithText(word.flyingWord(viewState.chosenLanguage))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(viewState.remainTimeInSecond.toString())
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(viewState.currentQuestionIndex.toString().plus(" / ").plus(viewState.totalWordCount.toString()))
            .assertIsDisplayed()
    }
}