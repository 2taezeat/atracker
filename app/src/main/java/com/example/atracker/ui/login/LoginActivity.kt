package com.example.atracker.ui.login

import android.R.attr
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import com.example.atracker.R
import com.example.atracker.databinding.ActivityLoginBinding
import com.example.atracker.databinding.ActivityMainBinding
import com.example.atracker.databinding.ActivitySplashBinding
import com.example.atracker.ui.MainActivity
import com.example.atracker.ui.signUp.SignUpActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.tasks.Task

import com.google.android.gms.tasks.OnCompleteListener
import android.R.attr.data
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val RC_SIGN_IN = 1001
    private var googleSignInClient : GoogleSignInClient? = null
    //private var firebaseAuth : FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        window.statusBarColor = ContextCompat.getColor(this,R.color.background_gray)


//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
           // .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("937085987148-2gkapehmitc9fsl7seha2dduugg380jl.apps.googleusercontent.com")
            .requestScopes(Scope(Scopes.DRIVE_APPFOLDER))
            //.requestScopes(Scope(Scopes.EMAIL))
            .requestServerAuthCode("937085987148-2gkapehmitc9fsl7seha2dduugg380jl.apps.googleusercontent.com")
            //.requestServerAuthCode("683196079012-c7qhe8vef6i4a3ml09ttj3gvaa2fdhri.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        //firebaseAuth = FirebaseAuth.getInstance()

        binding.loginGoogleButton.setOnClickListener {
            val signInIntent = googleSignInClient?.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
            Log.d("asd", "${gso.serverClientId}")
            Log.d("asd2", "${gso.isIdTokenRequested}")
        }


        binding.loginButtonFL.setOnClickListener {
            startMain()
        }

        binding.loginTmpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            ContextCompat.startActivity(this, intent, null)
            //finish()

        }

        ///////////
//        GoogleSignIn.silentSignIn()
//            .addOnCompleteListener(
//                this,
//                OnCompleteListener<GoogleSignInAccount?> { task -> handleSignInResult(task) })


    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val authCode = account.serverAuthCode
            val idToken = account.idToken
            Log.d("google_login_id","${idToken}, ${authCode}")

            // todo
            val intent = Intent(this, SignUpActivity::class.java)
            ContextCompat.startActivity(this, intent, null)


            //updateUI(account)
        } catch (e: ApiException) {
            Log.w("google_login", "handleSignInResult:error", e)
            //updateUI(null)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
////            val account = task.getResult(ApiException::class.java)
////            firebaseAuthWithGoogle(account)

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)


//            try {
//                val account = task.getResult(ApiException::class.java)
//                firebaseAuthWithGoogle(account)
//
//            } catch (e: ApiException) {
//                Log.d("loginError", "${e}")
//            }

        }
    }

//    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
//        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
//        firebaseAuth!!.signInWithCredential(credential)
//            .addOnCompleteListener(this){
//                if (it.isSuccessful) {
//                    val user = firebaseAuth?.currentUser
//                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
//                    startMain()
//
//                    val email = user?.email
//
//                } else {
//                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
//                }
//
//            }
//
//    }


    private fun startMain() {
        val intent = Intent(this, MainActivity::class.java)
        ContextCompat.startActivity(this, intent, null)
        finish()
    }




    private fun signOut() {

    }




}