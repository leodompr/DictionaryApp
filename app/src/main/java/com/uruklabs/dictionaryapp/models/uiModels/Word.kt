package com.uruklabs.dictionaryapp.models.uiModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class Word(
    @PrimaryKey val word: String,
    val pronunciation: String ? = null,
    val audio : String ? = null,
    val definitions : String ? = null,
    var isFavorite : Boolean = false
    )


