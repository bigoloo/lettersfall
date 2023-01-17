package com.bigoloo.lettersfall.domian.data.api

import com.bigoloo.lettersfall.models.Word
import retrofit2.http.GET

interface WordApi {
    @GET("DroidCoder/7ac6cdb4bf5e032f4c737aaafe659b33/raw/baa9fe0d586082d85db71f346e2b039c580c5804/words.json")
    suspend fun getWordList(): List<Word>
}