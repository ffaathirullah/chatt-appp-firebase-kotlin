package org.fachrul.faathirullah.chattapp.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import org.fachrul.faathirullah.chattapp.R
import org.fachrul.faathirullah.chattapp.databinding.ActivityMainBinding
import org.fachrul.faathirullah.chattapp.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding
    private val viewModel by lazy{
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_logout -> {
                viewModel.doLogout()
                Intent(this,LoginActivity::class.java).also{intent ->
                    startActivity(intent)
                    finish()
                }
                true
            }else -> super.onOptionsItemSelected(item)
        }
    }
}