package com.bigoloo.lettersfall.fixtures

import com.bigoloo.lettersfall.domian.data.api.WordApi
import com.bigoloo.lettersfall.models.Word

val wordList = listOf<Word>(Word("en1", "es1"), Word("en2", "es2"), Word("en3", "es3"))

val dummyWordApi: WordApi = object : WordApi {
    override suspend fun getWordList(): List<Word> {
        return wordList
    }
}