package com.cmc.atracker.ui.signUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.cmc.atracker.R
import com.cmc.atracker.databinding.ActivitySignUpBinding
import com.cmc.atracker.ui.login.LoginActivity
import com.cmc.atracker.utils.StartActivityUtil

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    val signUpViewModel: SignUpViewModel by lazy { ViewModelProvider(this).get(SignUpViewModel::class.java) }
    lateinit var navDisplayController: NavController




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        Log.d("test_onCreate", "SignUpActivity")



        window.statusBarColor = ContextCompat.getColor(this,R.color.background_gray)

        //val navController = findNavController(R.id.nav_host_fragment_activity_sign_Up)
        navDisplayController = findNavController(R.id.nav_host_fragment_activity_sign_Up)


    }

    // id가 명시되어있지 않은 다른 부분을 터치했을 때 키보드가 보여져있는 상태면 키보드를 내림.
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val view = currentFocus
        if (view != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) && view is EditText && !view.javaClass.name.startsWith(
                "android.webkit."
            )
        ) {
            val scrcoords = IntArray(2)
            view.getLocationOnScreen(scrcoords)
            val x = ev.rawX + view.getLeft() - scrcoords[0]
            val y = ev.rawY + view.getTop() - scrcoords[1]
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) (this.getSystemService(
                INPUT_METHOD_SERVICE
            ) as InputMethodManager).hideSoftInputFromWindow(
                this.window.decorView.applicationWindowToken, 0
            )
        }
        return super.dispatchTouchEvent(ev)
    }


    override fun onBackPressed() {
        Log.d("onBackPresed_destination_signUp", "${navDisplayController.currentDestination}, ${navDisplayController.graph.startDestDisplayName}")
        if (navDisplayController.currentDestination!!.id == R.id.navigation_sign_up_terms) {
            super.onBackPressed()

            StartActivityUtil.callActivity( this@SignUpActivity, LoginActivity())
            this.finish()
        } else if (navDisplayController.currentDestination!!.id == R.id.navigation_sign_up_complete) { // signUp_complete 화면에서 onbackpressed 불가능하게함.
            return
        } else {
            super.onBackPressed()
        }
    }




    override fun onDestroy() {
        Log.d("test_onDestory", "SignUpActivity")
        super.onDestroy()
    }


}