package com.uruklabs.dictionaryapp.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uruklabs.dictionaryapp.data.daos.WordDao
import com.uruklabs.dictionaryapp.models.uiModels.Word


@Database(entities = [Word::class], version = 2, exportSchema = false)
abstract class WordRoomDB : RoomDatabase() {

    abstract fun wordDao(): WordDao

    companion object {
        const val DATABASE_NAME = "word_database"

        @Volatile
        private var INSTANCE: WordRoomDB? = null

        fun getWordDatabase(context: Context): WordRoomDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDB::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }

}