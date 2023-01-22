package com.bigoloo.lettersfall.models

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Word(
    @SerialName("text_eng")
    val english: String,
    @SerialName("text_spa")
    val spanish: String
)

fun Word.mainWord(chosenLanguage: ChosenLanguage): String {
    return when (chosenLanguage) {
        ChosenLanguage.English -> {
            english
        }
        ChosenLanguage.Spanish ->
            spanish
    }
}

fun Word.flyingWord(chosenLanguage: ChosenLanguage): String {
    return when (chosenLanguage) {
        ChosenLanguage.English -> {
            spanish
        }
        ChosenLanguage.Spanish ->
            english
    }
}

enum class ChosenLanguage {
    English,
    Spanish
}

sealed class GameStatus(
    @SerialName("timePerQuestion")
    open val timePerQuestionInSecond: Int,
) : com.bigoloo.lettersfall.domain.redux.State {
    data class NotStarted(override val timePerQuestionInSecond: Int) :
        GameStatus(timePerQuestionInSecond)

    data class Started(
        @SerialName("currentWord")
        val currentWord: Word,

        @SerialName("currentTimerInSecond")
        val currentTimerInSecond: Int,

        @SerialName("currentIndex")
        val currentIndex: Int,

        @SerialName("timePerQuestion")
        override val timePerQuestionInSecond: Int,

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
    ) : GameStatus(timePerQuestionInSecond)

    data class Finished(
        @SerialName("correctAnswerCount")
        val correctAnswerCount: Int,

        @SerialName("wrongAnswerCount")
        val wrongAnswerCount: Int,

        @SerialName("timePerQuestion")
        override val timePerQuestionInSecond: Int,

        @SerialName("unAnsweredCount")
        val unAnsweredQuestionCount: Int,

        @SerialName("chosenLanguage")
        val chosenLanguage: ChosenLanguage
    ) : GameStatus(timePerQuestionInSecond) {
        fun toTable(): List<Pair<String, Any>> {
            return buildList {
                add("correctAnswerCount" to correctAnswerCount)
                add("wrongAnswerCount" to wrongAnswerCount)
                add("UnAnsweredCount" to unAnsweredQuestionCount)
                add("chosenLanguage" to chosenLanguage)
            }
        }
    }
}