package com.example.pertemuan14

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pertemuan14.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //instance pref manager
        prefManager = PrefManager.getInstance(this)
        checkLoginStatus()

        with(binding) {
            txtUsername.text = prefManager.getUsername()
            btnLogout.setOnClickListener {
                prefManager.saveUsername("")
                checkLoginStatus()
            }

            btnClear.setOnClickListener {
                prefManager.clear()
                checkLoginStatus()
            }
        }
    }

    private fun checkLoginStatus() {
        if (prefManager.getUsername().isEmpty()) {
            startActivity(
                Intent(this@MainActivity, LoginActivity::class.java)
            )
            finish()
        }
    }
}