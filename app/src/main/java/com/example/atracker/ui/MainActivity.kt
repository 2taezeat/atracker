package com.example.atracker.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.atracker.R
import com.example.atracker.databinding.ActivityMainBinding
import com.example.atracker.ui.calendar.CalendarViewModel
import com.example.atracker.ui.home.HomeViewModel
import com.example.atracker.utils.AlertType
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val homeViewModel: HomeViewModel by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }
    val calendarViewModel: CalendarViewModel by lazy { ViewModelProvider(this).get(CalendarViewModel::class.java) }
    val myPageViewModel: MyPageViewModel by lazy { ViewModelProvider(this).get(MyPageViewModel::class.java) }


//    private val navDisplayController: NavController by lazy {
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragmentActivityMain)
//        navHostFragment!!.findNavController()
//    }

    lateinit var navDisplayController: NavController
    lateinit var alertDialogFragment : AlertDialogFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this,R.color.background_gray)

        //datapicker 한글화
        val locale = Locale.KOREA
        Locale.setDefault(locale)
        val config: Configuration = baseContext.resources.configuration
        config.setLocale(locale)
        createConfigurationContext(config)


        val navView: BottomNavigationView = binding.navView

        //val navController = findNavController(R.id.navHostFragmentActivityMain)
        navDisplayController = supportFragmentManager.findFragmentById(R.id.navHostFragmentActivityMain)?.findNavController()!!

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

//        ApiExceptionUtil.apiExceptionFlag.observe(this, androidx.lifecycle.Observer {
//            it.getContentIfNotHandled()?.let { boolean ->
//                if (boolean) {
//                    showAlert(AlertType.TYPE14)
//                }
//            }
//        })


//        navDisplayController.addOnDestinationChangedListener{ _, destination, _ ->
//            when (destination.id) {
//                R.id.navigation_blog -> {
//                }
//                R.id.navigation_home -> {
//
//                    if ( checkApiException() ){
//                        showAlertInstance(alertDialogFragment)
//                    }
//
//                }
//                R.id.navigation_calendar -> {
//                }
//                R.id.navigation_home_detail -> {
//                }
//                R.id.navigation_my_page_display -> {
//                }
//                else -> {
//                }
//            }
//        }

        homeViewModel.getStage()
        myPageViewModel.getMyPage()
        homeViewModel.getApplyDisplay(applyIds = null, includeContent = false)

    }


    override fun onBackPressed() {
        navDisplayController.addOnDestinationChangedListener{ _, destination, _ ->
            when (destination.id) {
                R.id.navigation_blog -> {
                    mainBottomNavigationAppear()
                }
                R.id.navigation_home -> {
//                    if ( checkApiException() ){
//                        showAlertInstance(alertDialogFragment)
//                    }

                    mainBottomNavigationAppear()
                    homeViewModel.clearHomeAddText()
                }
                R.id.navigation_calendar -> {
                    mainBottomNavigationAppear()
                }
                R.id.navigation_home_detail -> {
                    mainBottomNavigationAppear()
                }
                R.id.navigation_my_page_display -> {
                    mainBottomNavigationAppear()
                }
                else -> {
                }
            }
        }


        super.onBackPressed()
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


    fun showAlertInstance(alert : AlertDialogFragment){
        if (!alert.isAdded) { // fragment already added 처리 해야됨
            alert.show(supportFragmentManager, AlertDialogFragment.TAG)
        }

    }



    private fun checkApiException() : Boolean{
        //return myPageViewModel.userNickName.value == "" || homeViewModel.homeAddStagesContent.value!!.isEmpty() || homeViewModel.homeApplyIdContent.value!!.apply_id == -1
        return  myPageViewModel.userNickName.value == "," || homeViewModel.homeAddStagesContent.value!!.isEmpty()
    }



}