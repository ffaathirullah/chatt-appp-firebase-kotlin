package org.fachrul.faathirullah.chattapp.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.fachrul.faathirullah.chattapp.model.Chat

class  MainViewModel : ViewModel(){

    private  val firebaseAuth by lazy { FirebaseAuth.getInstance()}
    private  val databaseReference by lazy {FirebaseDatabase.getInstance().reference}

    private val Tag ="Main View Model"

    private val MESSAGE ="message"

    fun doLogout(){
        firebaseAuth.signOut()
    }

    fun getUsername(): String{
        val email = firebaseAuth.currentUser?.email.orEmpty()
        return email.split("@").first()
    }

    fun postMessage(mesaage :String){
        val chat = Chat(getUsername(), mesaage)
        Log.e(Tag, "$chat")
        databaseReference.child(MESSAGE).push().setValue(chat)
    }
}