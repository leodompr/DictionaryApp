package com.uruklabs.dictionaryapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.uruklabs.dictionaryapp.data.repositorys.WordsRepositorys
import com.uruklabs.dictionaryapp.models.uiModels.Word
import kotlinx.coroutines.launch


class HistoryViewModel(private val repository : WordsRepositorys) : ViewModel() {

    val wordList = repository.allWords.asLiveData()
    val wordSearch = MutableLiveData<Word>()
    val error = MutableLiveData<String>()

    fun getWord(word: String) = viewModelScope.launch {
        if (repository.getWord(word) != null) {
            wordSearch.value = repository.getWord(word)
        } else {
           getWordAp(word)
        }

    }

    private fun getWordAp(word : String) = viewModelScope.launch {
        try {
            val response = repository.getWordFromAPI(word)
            val wordR = Word(word = response.first().word,
                pronunciation = response.first().phonetics.first().text,
                definitions = response.first().meanings.first().definitions.first().definition,
                audio = response.first().phonetics.first().audio)
                insertWord(wordR)

        } catch (e: Exception) {
            error.value = e.message
        }

    }

    fun insertWord(word : Word) = viewModelScope.launch {
        wordSearch.value = word
        repository.insertWord(word)
    }





}