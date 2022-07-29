package com.example.atracker.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.atracker.R
import com.example.atracker.databinding.ActivityLoginBinding
import com.example.atracker.ui.MainActivity
import com.example.atracker.ui.signUp.SignUpActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.atracker.BuildConfig
import com.example.atracker.model.local.App
import com.example.atracker.ui.signUp.SignUpViewModel
import com.example.atracker.utils.StartActivityUtil
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val RC_SIGN_IN = 1001
    private var googleSignInClient : GoogleSignInClient? = null
    //private var firebaseAuth : FirebaseAuth? = null

    val loginViewModel: LoginViewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        window.statusBarColor = ContextCompat.getColor(this,R.color.background_gray)

        val at = App.prefs.getValue(BuildConfig.ACCESS_LOCAL_TOKEN)
        val rt = App.prefs.getValue(BuildConfig.REFRESH_LOCAL_TOKEN)

        Log.d("at_rt_token_login", "${at}, ${rt}")

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        Log.d("test_onCreate", "LoginActivity")

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


        binding.loginButtonFL.setOnClickListener { // real login button

            if (at != "") { // 토큰이 있으면
                StartActivityUtil.callActivity(this@LoginActivity, MainActivity())
            } else { // 토큰이 없으면 회원 가입을 해야 됨.
                StartActivityUtil.callActivity(this@LoginActivity, SignUpActivity())

            }

            //StartActivityUtil.callActivity(this@LoginActivity, MainActivity())
            finish()
        }

        binding.loginTmpButton.setOnClickListener {
            StartActivityUtil.callActivity(this@LoginActivity, SignUpActivity())
            finish()
        }

///////////////////////////////////////////////////////////////////////////////////
        binding.testSignApiButton.setOnClickListener {
            loginViewModel.testSign()
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


    override fun onDestroy() {
        Log.d("test_onDestory", "LoginActivity")
        super.onDestroy()
    }



}