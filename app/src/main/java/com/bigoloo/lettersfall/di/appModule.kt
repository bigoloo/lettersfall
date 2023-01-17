package com.bigoloo.lettersfall.di

import com.bigoloo.lettersfall.domian.data.api.WordApi
import com.bigoloo.lettersfall.domian.data.repository.RemoteWordRepository
import com.bigoloo.lettersfall.domian.repository.WordRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single {
        Retrofit.Builder()
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
}