package com.bigoloo.lettersfall.di

import com.bigoloo.lettersfall.data.api.WordApi
import com.bigoloo.lettersfall.data.repository.RemoteWordRepository
import com.bigoloo.lettersfall.domain.question.QuestionReducer
import com.bigoloo.lettersfall.domain.repository.WordRepository
import com.bigoloo.lettersfall.ui.home.HomeViewModel
import com.bigoloo.lettersfall.ui.question.QuestionViewModel
import com.bigoloo.lettersfall.ui.result.GameResultViewModel
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
    factory {
        QuestionReducer()
    }

    viewModel {
        HomeViewModel(get())
    }
    viewModel {
        QuestionViewModel(get())
    }
    viewModel {
        GameResultViewModel(get())
    }
}