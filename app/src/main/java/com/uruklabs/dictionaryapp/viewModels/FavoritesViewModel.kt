package com.uruklabs.dictionaryapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uruklabs.dictionaryapp.helper.FirebaseHelper
import com.uruklabs.dictionaryapp.models.uiModels.Word
import kotlinx.coroutines.launch

class FavoritesViewModel() : ViewModel() {

    val wordLiveData = MutableLiveData<List<Word>>()


    fun getFavoritesWords() = viewModelScope.launch {
        FirebaseHelper.getIdUser()?.let {
            FirebaseHelper.getDatabase().child("favorites").child(it).get().addOnSuccessListener {
                val list = mutableListOf<Word>()
                it.children.forEach { snapshot ->
                    val word = Word(
                        word = snapshot.child("word").value.toString(),
                        pronunciation = snapshot.child("pronunciation").value.toString(),
                        audio = snapshot.child("audio").value.toString(),
                        definitions = snapshot.child("definitions").value.toString(),
                        isFavorite = snapshot.child("favorite").value.toString().toBoolean(),
                    )

                    list.add(word)
                }
                wordLiveData.postValue(list)
            }
        }

    }



}