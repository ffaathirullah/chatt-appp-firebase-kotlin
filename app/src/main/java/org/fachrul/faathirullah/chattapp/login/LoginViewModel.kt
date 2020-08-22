package org.fachrul.faathirullah.chattapp.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class  LoginViewModel : ViewModel(){
    private  val firebaseAuth by lazy {FirebaseAuth.getInstance()}
    private  val _LoginResult by lazy {MutableLiveData<Status>()}
    val LoginResult: LiveData<Status> get() = _LoginResult

    fun isLogin(): Boolean {
        val session = firebaseAuth.currentUser
        return session != null
    }
    fun doLogin(email:String, password:String){
        _LoginResult.value = Status.LOADING
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { result ->
                if (result.isSuccessful){
                        _LoginResult.value = Status.SUCCESS
                }else{
                    _LoginResult.value = Status.ERROR
                }
            }
    }
}
