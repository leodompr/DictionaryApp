package com.uruklabs.dictionaryapp.viewModels

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uruklabs.dictionaryapp.helper.FirebaseHelper
import com.uruklabs.dictionaryapp.models.uiModels.Word
import com.uruklabs.dictionaryapp.utils.ListForNext

import kotlinx.coroutines.launch

class WordViewModel : ViewModel(), LifecycleObserver {

    val wordLiveData = MutableLiveData<Word>()

     private fun setWord(word: Word) {
         wordLiveData.value = word
         ListForNext.listForNext.add(word.word)
    }

    fun getWord() = viewModelScope.launch {
        FirebaseHelper.getDatabase().child("Words").get().addOnSuccessListener {
            val words = it.value as ArrayList<*>
            for (word in words) {
                if (word != null){
                val wordObject = Word(word.toString())
                    if (!wordObject.word.contains(" ") && !wordObject.word.contains("-")){
                        setWord(wordObject)
                    }
            }
            }
        } .addOnFailureListener {

        }
    }


}