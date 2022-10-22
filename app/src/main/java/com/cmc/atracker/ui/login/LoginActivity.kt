package com.cmc.atracker.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.cmc.atracker.BuildConfig
import com.cmc.atracker.R
import com.cmc.atracker.databinding.ActivityLoginBinding
import com.cmc.atracker.model.local.App
import com.cmc.atracker.ui.MainActivity
import com.cmc.atracker.ui.signUp.SignUpActivity
import com.cmc.atracker.utils.StartActivityUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class LoginActivity : AppCompatActivity() {


    // 220802 14:30
    // 220803 18:03 배포 후 최종 커밋
    private lateinit var binding: ActivityLoginBinding

    private val RC_SIGN_IN = 0
    private var googleSignInClient: GoogleSignInClient? = null

    val loginViewModel: LoginViewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        window.statusBarColor = ContextCompat.getColor(this, R.color.background_gray)

        val at = App.prefs.getValue(BuildConfig.ACCESS_LOCAL_TOKEN)
        val rt = App.prefs.getValue(BuildConfig.REFRESH_LOCAL_TOKEN)

        Log.d("at_rt_token_login", "${at}, ${rt}")

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        Log.d("test_onCreate", "LoginActivity")


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            //.requestScopes(Scope(Scopes.DRIVE_APPFOLDER))
            //.requestIdToken("937085987148-n29vmraidvro4qd0nq5hfsvo4rn7jo9k.apps.googleusercontent.com")
            .requestServerAuthCode("937085987148-n29vmraidvro4qd0nq5hfsvo4rn7jo9k.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        binding.loginButtonFL.setOnClickListener { // real login button
            googleSignInClient!!.revokeAccess()

            if (at != "") { // 토큰이 있으면, case1 => 바로 Main
                StartActivityUtil.callActivity(this@LoginActivity, MainActivity())
                finish()
            } else { // just case 4
                val signInIntent = googleSignInClient?.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
                Log.d("google_serverClientId", "${gso.serverClientId}")
                Log.d("google_isIdTokenRequested", "${gso.isIdTokenRequested}")
                Log.d("google_account", "${gso.account}")

//                if (App.prefs.getValue(BuildConfig.EMAIL) != "") { // 토큰은 없는데 이메일은 있음, 로그아웃, case2 => testLogin 호출 후, Main
////                    loginViewModel.testSignInLogin(App.prefs.getValue(BuildConfig.EMAIL)!!)
////
////                    loginViewModel.testSignLoginFail.observe(this, Observer {
////                        it.getContentIfNotHandled()?.let { boolean ->
////                            Log.d("google_testSignLoginFail", "${boolean}")
////                            if (!boolean) { // postApply 실패
////                                StartActivityUtil.callActivity(this@LoginActivity, MainActivity())
////                                finish()
////                            }
////                        }
////                    })
//
//                    val signInIntent = googleSignInClient?.signInIntent
//                    startActivityForResult(signInIntent, RC_SIGN_IN)
//                    Log.d("google_serverClientId", "${gso.serverClientId}")
//                    Log.d("google_isIdTokenRequested", "${gso.isIdTokenRequested}")
//                    Log.d("google_account", "${gso.account}")
//
//
//                } else { // 토큰은 없는데, 이메일도 없음, case3 => 구글 찍고, => SignUp 호출
//                    val signInIntent = googleSignInClient?.signInIntent
//                    startActivityForResult(signInIntent, RC_SIGN_IN)
//                    Log.d("google_serverClientId", "${gso.serverClientId}")
//                    Log.d("google_isIdTokenRequested", "${gso.isIdTokenRequested}")
//                    Log.d("google_account", "${gso.account}")
//                }
                //StartActivityUtil.callActivity(this@LoginActivity, SignUpActivity())
            }
        }


    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account.idToken
            val authCode = account.serverAuthCode

            Log.d("google_login_idToken", "${idToken}")
            Log.d("google_login_authCode", "${authCode}")
            Log.d("google_login_email", "${account.email}")

            App.prefs.setValue(BuildConfig.EMAIL, account.email)

            loginViewModel.getAccessTokenFromGoogleByAuthCode(authCode!!)

            StartActivityUtil.callActivity(this@LoginActivity, SignUpActivity())
            finish()
        } catch (e: ApiException) {
            Log.w("google_login", "handleSignInResult:error", e)
        }
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