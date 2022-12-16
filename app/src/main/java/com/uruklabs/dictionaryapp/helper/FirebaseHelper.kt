package com.uruklabs.dictionaryapp.helper

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.uruklabs.dictionaryapp.models.uiModels.Word
import com.uruklabs.dictionaryapp.views.adapters.ListWordsAdapter

class FirebaseHelper {


    companion object{


        fun getDatabase() = FirebaseDatabase.getInstance().reference

        private fun getAuth() = FirebaseAuth.getInstance()

        fun getIdUser() = getAuth().uid

        fun chekAutenticated() = getAuth().currentUser != null

        fun validError(error : String) : String {
            return when{
                error.contains("There is no user record corresponding to this identifier") ->{
                    "Nenhum usuario encontrado com esse e-mail."
                }
                error.contains("The email address is badly formatted") ->{
                    "Formato de e-mail inválido."
                }
                error.contains("The password is invalid or the user") ->{
                    "Senha incorreta."
                }
                error.contains("The email address is already in use by another account") ->{
                    "Esse e-mail já esta em uso."
                }
                error.contains("The given password is invalid. [ Password should be at least 6 characters ]") ->{
                    "A senha deve possuir mais de 6 dígitos."
                }
                error.contains("There is no user record corresponding to this identifier") ->{
                    "E-mail não encontrado."
                }
                else -> {
                    "Ocorreu um erro, verifique os dados e tente novamente"
                }
            }


        }



    }

}