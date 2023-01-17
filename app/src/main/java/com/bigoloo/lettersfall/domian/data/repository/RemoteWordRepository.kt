package com.bigoloo.lettersfall.domian.data.repository

import com.bigoloo.lettersfall.domian.data.api.WordApi
import com.bigoloo.lettersfall.domian.repository.WordRepository
import com.bigoloo.lettersfall.models.Word

class RemoteWordRepository(private val wordApi: WordApi) : WordRepository {
    override suspend fun getWordList(): List<Word> {
        return wordApi.getWordList()
    }
}