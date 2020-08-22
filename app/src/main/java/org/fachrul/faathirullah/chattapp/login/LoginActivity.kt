package org.fachrul.faathirullah.chattapp.login

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.fachrul.faathirullah.chattapp.MainActivity
import org.fachrul.faathirullah.chattapp.R
import org.fachrul.faathirullah.chattapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    //inisialiasasi viewModel :
    private val viewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    private val dialogBuilder by lazy {AlertDialog.Builder(this)}
    private lateinit var dialogLoading: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initLoadingDialog()
        viewCicked()
        subscribeVm()
    }

    override fun onStart() {
        super.onStart()
        checkSessionUser()
    }

    private  fun initLoadingDialog(){
        dialogBuilder.setView(R.layout.dialog_loading)
        dialogLoading = dialogBuilder.create()
        dialogLoading.setCancelable(false)
    }

    private  fun showLoading(){
        dialogLoading.show()
    }

    private  fun hideLoading(){
        dialogLoading.dismiss()
    }

    private fun viewCicked() {
        binding.btnLogin.setOnClickListener {
            validation()
        }
    }

    private fun validation() {
        if (binding.textEmail.text.toString().isEmpty()) {
            binding.textEmail.error = "Invalid Email"
        } else if (binding.textPassword.text.toString().isEmpty()) {
            binding.textPassword.error = "Inavalid Password"
        } else {
            viewModel.doLogin(
                binding.textEmail.text.toString(),
                binding.textPassword.text.toString()
            )
        }
    }

    private fun checkSessionUser(){
        if (viewModel.isLogin()){
            Intent(this,MainActivity::class.java).also { intent ->
                startActivity(intent)
                finish()
            }
        }
    }

    private fun subscribeVm() {
        //bentuknya status enum
        viewModel.LoginResult.observe(this, Observer { status ->
            Log.e("Status", "$status")
            status?.let { stats ->
                when (stats) {
                    Status.LOADING -> {
                        showLoading()
                    }
                    Status.ERROR -> {
                        hideLoading()
                        Toast.makeText(this,"Wrong password or Email", Toast.LENGTH_SHORT).show()
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        Intent(this,MainActivity::class.java).also {intent ->
                            startActivity(intent)
                        }
                    }
                }
            }
        })
    }
}
