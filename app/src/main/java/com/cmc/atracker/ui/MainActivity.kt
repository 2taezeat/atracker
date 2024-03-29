package com.cmc.atracker.ui

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cmc.atracker.R
import com.cmc.atracker.databinding.ActivityMainBinding
import com.cmc.atracker.ui.calendar.CalendarViewModel
import com.cmc.atracker.ui.home.HomeViewModel
import com.cmc.atracker.utils.AlertType
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    val homeViewModel : HomeViewModel by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }
    val calendarViewModel : CalendarViewModel by lazy {
        ViewModelProvider(this).get(
            CalendarViewModel::class.java
        )
    }
    val myPageViewModel : MyPageViewModel by lazy { ViewModelProvider(this).get(MyPageViewModel::class.java) }


//    private val navDisplayController: NavController by lazy {
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragmentActivityMain)
//        navHostFragment!!.findNavController()
//    }

    lateinit var navDisplayController : NavController
    lateinit var alertDialogFragment : AlertDialogFragment


    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.background_gray)

        Log.d("test_onCreate", "MainActivity")


        //datapicker 한글화
        val locale = Locale.KOREA
        Locale.setDefault(locale)
        val config : Configuration = baseContext.resources.configuration
        config.setLocale(locale)
        createConfigurationContext(config)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)


        val navView : BottomNavigationView = binding.navView

        //val navController = findNavController(R.id.navHostFragmentActivityMain)
        navDisplayController =
            supportFragmentManager.findFragmentById(R.id.navHostFragmentActivityMain)
                ?.findNavController()!!

//        val appBarConfiguration = AppBarConfiguration(setOf(
//            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        //setupActionBarWithNavController(navController, appBarConfiguration)

        navDisplayController.navigate(R.id.navigation_home)
        navView.setupWithNavController(navDisplayController)


        alertDialogFragment = AlertDialogFragment.instance(
            object : AlertDialogListener {
                override fun onLeftClick() {
                }

                override fun onCenterClick() {

                }

                override fun onRightClick() {
                }
            },
            AlertType.TYPE14,
            null
        )

        navDisplayController.addOnDestinationChangedListener { navController, destination, args -> // onBack 될때만 호출되는게 아닌거 같다  * Rna 필요
            when (destination.id) {
                R.id.navigation_blog -> {
                    mainBottomNavigationAppear()
                }
                R.id.navigation_home -> {
                    mainBottomNavigationAppear()
                    homeViewModel.clearHomeAddText()
                }
                R.id.navigation_calendar -> {
                    mainBottomNavigationAppear()
                }
                R.id.navigation_home_detail -> {
                    mainBottomNavigationDisappear() // display -> detail 시 disapper 시켜야 오류가 안난다고 판단
                }
                R.id.navigation_my_page_display -> {
                    mainBottomNavigationAppear()
                }
                R.id.navigation_home_add -> { //

                }
                else -> {

                }
            }
        }

        homeViewModel.getStage()
        myPageViewModel.getMyPage()
        homeViewModel.getApplyDisplay(applyIds = null, includeContent = false)
        homeViewModel.getMyApplyPfratio()
    }


    override fun onBackPressed() {
        Log.d(
            "onBackPresed_destination",
            "${navDisplayController.currentDestination}, ${navDisplayController.graph.startDestDisplayName}"
        )

        if (navDisplayController.currentDestination!!.id == R.id.navigation_home_add_calendar) {
            homeViewModel.switch(homeViewModel._addCalendarToAddFlag)
            super.onBackPressed()
        } else if (navDisplayController.currentDestination!!.id == R.id.navigation_home_add) {
            if (homeViewModel.homeAddStagesContent.value!!.isNotEmpty()) {
                showAlert(AlertType.TYPE17)
                return
            } else { // api 통신 실페 일때
                super.onBackPressed()
            }
        } else if (navDisplayController.currentDestination!!.id == R.id.navigation_home_write) {
            showAlert(AlertType.TYPE18)
            return
        } else {
            super.onBackPressed()
        }

//        navDisplayController.addOnDestinationChangedListener{ navController, destination, args -> // onBack 될때만 호출되는게 아닌거 같다  * Rna 필요
//        }

        //super.onBackPressed()
    }

    private fun showAlert(alertType : AlertType) {
        val alertDialogFragment = AlertDialogFragment.instance(
            object : AlertDialogListener {
                override fun onLeftClick() {
                }

                override fun onCenterClick() {
                }

                override fun onRightClick() {
                    when (alertType) {
                        AlertType.TYPE17 -> {
                            mainBottomNavigationAppear()
                            navDisplayController.popBackStack()
                            //navController.popBackStack()
                            homeViewModel.clearHomeAddText()
                        }

                        AlertType.TYPE18 -> {
                            navDisplayController.popBackStack()
                            //navController.popBackStack()
                        }
                    }
                }
            },
            alertType,
            null
        )
        alertDialogFragment.show(supportFragmentManager, AlertDialogFragment.TAG)
    }


    fun mainBottomNavigationDisappear() {
        binding.navView.visibility = View.GONE
        val lp = binding.navHostFragmentActivityMain.layoutParams
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT
        binding.navHostFragmentActivityMain.layoutParams = lp
    }

    fun mainBottomNavigationAppear() {
        binding.navView.visibility = View.VISIBLE

        val lp = binding.navHostFragmentActivityMain.layoutParams
        lp.height = 0
        binding.navHostFragmentActivityMain.layoutParams = lp
    }


    fun showAlertInstance(alert : AlertDialogFragment) {
        if (!alert.isAdded) { // fragment already added 처리 해야됨
            alert.show(supportFragmentManager, AlertDialogFragment.TAG)
        }

    }

    // id가 명시되어있지 않은 다른 부분을 터치했을 때 키보드가 보여져있는 상태면 키보드를 내림.
    override fun dispatchTouchEvent(ev : MotionEvent) : Boolean {
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

    override fun onDestroy() {
        Log.d("test_onDestory", "MainActivity")
        super.onDestroy()
    }


}