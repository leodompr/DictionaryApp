package com.uruklabs.dictionaryapp.models.respondeModels

data class Phonetic(
    val audio: String,
    val license: License,
    val sourceUrl: String,
    val text: String
)