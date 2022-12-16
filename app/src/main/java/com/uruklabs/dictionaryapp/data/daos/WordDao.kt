package com.uruklabs.dictionaryapp.data.daos

import androidx.room.*
import com.uruklabs.dictionaryapp.models.uiModels.Word
import kotlinx.coroutines.flow.Flow


@Dao
interface WordDao {

    @Query("SELECT * FROM word_table")
    fun getAllWords(): Flow<List<Word>>

    @Query("SELECT * FROM word_table WHERE word = :word")
    suspend fun getWord(word: String): Word?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word)

    @Delete
    suspend fun delete(word: Word)

}