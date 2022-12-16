package com.uruklabs.dictionaryapp.models.uiModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.uruklabs.dictionaryapp.data.databases.WordRoomDB
import com.uruklabs.dictionaryapp.models.respondeModels.DicionaryReponseItem

@Entity(tableName = "word_table")
data class Word(
    @PrimaryKey val word: String,
    val pronunciation: String ? = null,
    val audio : String ? = null,
    val definitions : String ? = null,
    var isFavorite : Boolean = false
    )


