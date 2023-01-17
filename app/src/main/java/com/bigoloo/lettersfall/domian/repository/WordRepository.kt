package com.bigoloo.lettersfall.domian.repository

import com.bigoloo.lettersfall.models.Word

interface WordRepository {
    suspend fun getWordList(): List<Word>
}