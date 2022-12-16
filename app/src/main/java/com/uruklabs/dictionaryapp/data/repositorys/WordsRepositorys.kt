package com.uruklabs.dictionaryapp.data.repositorys

import com.uruklabs.dictionaryapp.data.daos.WordDao
import com.uruklabs.dictionaryapp.helper.FirebaseHelper
import com.uruklabs.dictionaryapp.models.uiModels.Word
import com.uruklabs.dictionaryapp.services.network.DicionatyAPI
import kotlinx.coroutines.flow.Flow

class WordsRepositorys(private val wordsDao: WordDao, private val api : DicionatyAPI) {

    val allWords : Flow<List<Word>> = wordsDao.getAllWords()

    suspend fun getWord(word: String) : Word? = wordsDao.getWord(word)

    suspend fun insertWord(word: Word) = wordsDao.insert(word)

   suspend fun getWordFromAPI(word: String) = api.getWord(word)




}
