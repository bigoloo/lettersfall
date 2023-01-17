package com.bigoloo.lettersfall.di

import com.bigoloo.lettersfall.domian.data.api.WordApi
import com.bigoloo.lettersfall.domian.data.repository.RemoteWordRepository
import com.bigoloo.lettersfall.domian.repository.WordRepository
import com.bigoloo.lettersfall.ui.WordFallViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory(contentType))
            .baseUrl("https://gist.githubusercontent.com/")
            .client(OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }).build())

            .build()
    }
    single<WordApi> {
        get<Retrofit>().create(WordApi::class.java)
    }
    single<WordRepository> {
        RemoteWordRepository(get())
    }

    viewModel {
        WordFallViewModel(get())
    }
}