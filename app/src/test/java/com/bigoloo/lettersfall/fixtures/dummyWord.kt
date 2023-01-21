package com.bigoloo.lettersfall.fixtures

import com.bigoloo.lettersfall.data.api.WordApi
import com.bigoloo.lettersfall.models.ChosenLanguage
import com.bigoloo.lettersfall.models.GameStatus
import com.bigoloo.lettersfall.models.Word

val wordList = listOf<Word>(Word("en1", "es1"), Word("en2", "es2"), Word("en3", "es3"))

val dummyWordApi: WordApi = object : WordApi {
    override suspend fun getWordList(): List<Word> {
        return wordList
    }
}

val lastQuestionGameStatus = GameStatus.Started(
    currentIndex = wordList.lastIndex,
    currentWord = wordList.last(),
    wrongAnswerCount = 1,
    unAnsweredQuestionCount = 1,
    correctAnswerCount = 1,
    currentTimerInSecond = 3,
    timePerQuestionInSecond = 12,
    chosenLanguage = ChosenLanguage.English,
    words = wordList
)

val firstQuestionGameStatus = GameStatus.Started(
    currentIndex = 0,
    currentWord = wordList.first(),
    wrongAnswerCount = 1,
    unAnsweredQuestionCount = 1,
    correctAnswerCount = 1,
    currentTimerInSecond = 0,
    timePerQuestionInSecond = 12,
    chosenLanguage = ChosenLanguage.English,
    words = wordList
)