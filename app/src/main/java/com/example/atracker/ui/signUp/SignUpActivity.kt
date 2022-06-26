package com.example.atracker.ui.signUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.atracker.R
import com.example.atracker.databinding.ActivityLoginBinding
import com.example.atracker.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)



        window.statusBarColor = ContextCompat.getColor(this,R.color.background_gray)


        val navController = findNavController(R.id.nav_host_fragment_activity_sign_Up)


    }
}