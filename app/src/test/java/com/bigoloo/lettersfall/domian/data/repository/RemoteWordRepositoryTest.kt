package com.bigoloo.lettersfall.domian.data.repository

import com.bigoloo.lettersfall.fixtures.dummyWordApi
import com.bigoloo.lettersfall.fixtures.wordList
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class RemoteWordRepositoryTest {

    @Test
    fun `when repository getWordList is called data should be returned`() = runBlocking {
        val wordRepository = RemoteWordRepository(dummyWordApi)
        val data = wordRepository.getWordList()
        Assert.assertEquals(wordList, data)
    }
}