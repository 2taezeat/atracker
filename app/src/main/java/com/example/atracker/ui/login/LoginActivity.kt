package com.example.atracker.ui.login

import android.R.attr
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
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
import android.R.attr.data





class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val RC_SIGN_IN = 0
    private var googleSignInClient : GoogleSignInClient? = null

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


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            //.requestIdToken("937085987148-2gkapehmitc9fsl7seha2dduugg380jl.apps.googleusercontent.com")
            //.requestIdToken("937085987148-atb62pmmmb8o68rkcdfhroof5t5la4uf.apps.googleusercontent.com")
            //.requestIdToken("937085987148-aap5uf6v0g5e68nmdck4paalhle1h1ha.apps.googleusercontent.com")
            .requestIdToken("1078541661137-85s6qfa3ik8l30poccpuf3qr38jidtij.apps.googleusercontent.com")
            //.requestScopes(Scope(Scopes.DRIVE_APPFOLDER))
            //.requestServerAuthCode("937085987148-atb62pmmmb8o68rkcdfhroof5t5la4uf.apps.googleusercontent.com")
            //.requestServerAuthCode("937085987148-aap5uf6v0g5e68nmdck4paalhle1h1ha.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

//        binding.loginGoogleButton.setOnClickListener {
//            googleSignInClient!!.revokeAccess()
//
//            val signInIntent = googleSignInClient?.signInIntent
//            startActivityForResult(signInIntent, RC_SIGN_IN)
//
//
////            if (googleSignInClient != null) {
////                googleSignInClient!!.revokeAccess()
////            }
//
//            Log.d("google_serverClientId", "${gso.serverClientId}")
//            Log.d("google_isIdTokenRequested", "${gso.isIdTokenRequested}")
//
////            val signInIntent = googleSignInClient?.signInIntent
////            childForResult.launch(signInIntent)
////            Log.d("asd", "${gso.serverClientId}")
////            Log.d("asd2", "${gso.isIdTokenRequested}")
//        }


        binding.loginButtonFL.setOnClickListener { // real login button
            googleSignInClient!!.revokeAccess()

            if (at != "") { // 토큰이 있으면, case1
                StartActivityUtil.callActivity(this@LoginActivity, MainActivity())
                finish()
            } else { // 토큰이 없으면 회원 가입을 해야 됨.
                if (App.prefs.getValue(BuildConfig.EMAIL) != "") { // 토큰은 없는데 이메일은 있음, 로그아웃, case2
                    loginViewModel.testSignInLogin(App.prefs.getValue(BuildConfig.EMAIL)!!)

                    val mHandler = Handler(Looper.getMainLooper())
                    mHandler.postDelayed({
                        StartActivityUtil.callActivity(this@LoginActivity, MainActivity())
                        finish()
                    }, 300)

                } else { // 토큰은 없는데, 이메일도 없음, case3
                    val signInIntent = googleSignInClient?.signInIntent
                    startActivityForResult(signInIntent, RC_SIGN_IN)
                    Log.d("google_serverClientId", "${gso.serverClientId}")
                    Log.d("google_isIdTokenRequested", "${gso.isIdTokenRequested}")
                }
                //StartActivityUtil.callActivity(this@LoginActivity, SignUpActivity())
            }
            //finish()
        }

//        binding.loginTmpButton.setOnClickListener {
//            StartActivityUtil.callActivity(this@LoginActivity, SignUpActivity())
//            finish()
//        }

///////////////////////////////////////////////////////////////////////////////////
//        binding.testSignApiButton.setOnClickListener {
//            loginViewModel.testSign()
//        }


        ///////////
//        GoogleSignIn.silentSignIn()
//            .addOnCompleteListener(
//                this,
//                OnCompleteListener<GoogleSignInAccount?> { task -> handleSignInResult(task) })


    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        val account = completedTask.getResult(ApiException::class.java)
        val email = account.email

        App.prefs.setValue(BuildConfig.EMAIL, email)
        Log.d("google_handle", "${email}")


        StartActivityUtil.callActivity(this@LoginActivity, SignUpActivity())
        finish()


//        try {
//            val account = completedTask.getResult(ApiException::class.java)
//            val authCode = account.serverAuthCode
//            val idToken = account.idToken
//
//            Log.d("google_login_idToken","${idToken}")
//            Log.d("google_login_authCode","${authCode}")
//            Log.d("google_login_authCode","${account.email}")
//
//            App.prefs.setValue(BuildConfig.EMAIL, account.email)
//
////            val at = App.prefs.getValue(BuildConfig.ACCESS_LOCAL_TOKEN)
////            val rt = App.prefs.getValue(BuildConfig.REFRESH_LOCAL_TOKEN)
////
////
////            if (at != "") { // 토큰이 있으면
////                StartActivityUtil.callActivity(this@LoginActivity, MainActivity())
////            } else { // 토큰이 없으면 회원 가입을 해야 됨.
////                StartActivityUtil.callActivity(this@LoginActivity, SignUpActivity())
////            }
//
//            StartActivityUtil.callActivity(this@LoginActivity, SignUpActivity())
//            finish()
//
//        } catch (e: ApiException) {
//            Log.w("google_login", "handleSignInResult:error", e)
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }



    override fun onDestroy() {
        Log.d("test_onDestory", "LoginActivity")
        super.onDestroy()
    }




}