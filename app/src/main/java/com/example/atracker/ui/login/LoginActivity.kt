package com.example.atracker.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.atracker.R
import com.example.atracker.databinding.ActivityLoginBinding
import com.example.atracker.databinding.ActivityMainBinding
import com.example.atracker.databinding.ActivitySplashBinding
import com.example.atracker.ui.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()


        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        binding.loginButton.setOnClickListener {
            startMain()
        }
    }



    fun startMain() {
        val intent = Intent(this, MainActivity::class.java)
        ContextCompat.startActivity(this, intent, null)
        finish()
    }
}