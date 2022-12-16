package com.uruklabs.dictionaryapp.models.respondeModels

import androidx.room.Entity



data class DicionaryReponseItem(
    val license: License,
    val meanings: List<Meaning>,
    val phonetics: List<Phonetic>,
    val sourceUrls: List<String>,
    val word: String
)