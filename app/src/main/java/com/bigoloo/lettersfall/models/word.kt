package com.bigoloo.lettersfall.models

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Word(
    @SerialName("text_eng")
    val english: String,
    @SerialName("text_spa")
    val spanish: String
)