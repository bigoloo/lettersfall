package com.bigoloo.lettersfall.models

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Word(
    @SerialName("text_eng")
    val english: String,
    @SerialName("text_spa")
    val spanish: String
)

enum class ChosenLanguage {
    English,
    Spanish
}

sealed interface GameStatus {
    object NotStarted : GameStatus
    data class Started(
        @SerialName("currentWord")
        val currentWord: Word,

        @SerialName("currentTimerInSecond")
        val currentTimerInSecond: Int,

        @SerialName("currentIndex")
        val currentIndex: Int,

        @SerialName("timePerQuestion")
        val timePerQuestionInSecond: Int,

        @SerialName("correctAnswerCount")
        val correctAnswerCount: Int,

        @SerialName("wrongAnswerCount")
        val wrongAnswerCount: Int,

        @SerialName("UnAnsweredCount")
        val unAnsweredQuestionCount: Int,

        @SerialName("chosenLanguage")
        val chosenLanguage: ChosenLanguage,


        @SerialName("words")
        val words: List<Word>
    ) : GameStatus

    data class Finished(
        @SerialName("correctAnswerCount")
        val correctAnswerCount: Int,

        @SerialName("wrongAnswerCount")
        val wrongAnswerCount: Int,

        @SerialName("UnAnsweredCount")
        val unAnsweredQuestionCount: Int,

        @SerialName("chosenLanguage")
        val chosenLanguage: ChosenLanguage,
    ) : GameStatus
}