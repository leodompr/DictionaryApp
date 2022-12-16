package com.uruklabs.dictionaryapp.models.respondeModels

data class Definition(
    val antonyms: List<Any>,
    val definition: String,
    val example: String,
    val synonyms: List<Any>
)