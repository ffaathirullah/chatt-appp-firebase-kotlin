package org.fachrul.faathirullah.chattapp.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.fachrul.faathirullah.chattapp.model.Chat

class  MainViewModel : ViewModel(){

    private  val firebaseAuth by lazy { FirebaseAuth.getInstance()}

    private  val databaseReference by lazy {FirebaseDatabase.getInstance().reference}

    private val _chatList by lazy {MutableLiveData<List<Chat>>()}
    val chatList : LiveData<List<Chat>>
        get() = _chatList

    private val Tag ="Main View Model"

    private val MESSAGE ="message"

    private val _chat by lazy { MutableLiveData<Chat>() }
    val chat : LiveData<Chat>
     get() = _chat

    private lateinit var valueEventListener: ValueEventListener

    fun doLogout(){
        firebaseAuth.signOut()
    }

    fun getUsername(): String{
        val email = firebaseAuth.currentUser?.email.orEmpty()
        return email.split("@").first()
    }

    fun onChatLongPress(chat: Chat){
        _chat.value = chat
    }

    fun postMessage(mesaage :String){
        val chat = Chat(getUsername(), mesaage)
        Log.e(Tag, "$chat")
        databaseReference.child(MESSAGE).push().setValue(chat)
    }

    fun readMessageFromFirebase(){
        valueEventListener = object : ValueEventListener{

            override fun onCancelled(error: DatabaseError) {
               Log.e(TAG, error.message)
            }


            override fun onDataChange(snapshot: DataSnapshot) {
                val dataChat = snapshot.children.toMutableList().map {child ->
                    val chat = child.getValue(Chat::class.java)
                    chat?.fireBaseKey = child.key
                    chat ?: Chat()
                }
                _chatList.value = dataChat
            }

        }

        databaseReference.child(MESSAGE).addValueEventListener(valueEventListener)
    }

    fun updateChat(chat:Chat){
        val childUpdater = hashMapOf<String, Any>(
            "/$MESSAGE/${chat.fireBaseKey}" to chat.toMap()
        )
        databaseReference.updateChildren(childUpdater)
    }

}