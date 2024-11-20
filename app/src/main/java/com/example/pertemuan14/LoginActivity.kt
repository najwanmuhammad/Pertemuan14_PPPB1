package com.example.pertemuan14

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pertemuan14.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: PrefManager
    private var usernameAccount = "papb"
    private var passwordAccount = "123456"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        prefManager = PrefManager.getInstance(this)
        with(binding) {
            btnLogin.setOnClickListener {
                val usernameInput = editUsername.text.toString()
                val passwordInput = editPassword.text.toString()

                if (usernameInput == usernameAccount && passwordInput == passwordAccount) {
                    prefManager.saveUsername(usernameInput)
                    checkLoginStatus()
                } else {
                    Toast.makeText(this@LoginActivity, "Userename atau Password salah", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun checkLoginStatus() {
        val username = prefManager.getUsername()
        if (username.isNotEmpty()) {
            startActivity(
                Intent(this@LoginActivity, MainActivity::class.java)
            )
            finish()
        }
    }
}