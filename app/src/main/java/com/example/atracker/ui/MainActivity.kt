package com.example.atracker.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.atracker.R
import com.example.atracker.databinding.ActivityMainBinding
import com.example.atracker.ui.calendar.CalendarViewModel
import com.example.atracker.ui.home.HomeViewModel

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




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this,R.color.background_gray)


        val navView: BottomNavigationView = binding.navView

        //val navController = findNavController(R.id.navHostFragmentActivityMain)
        navDisplayController = supportFragmentManager.findFragmentById(R.id.navHostFragmentActivityMain)?.findNavController()!!
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


//        val appBarConfiguration = AppBarConfiguration(setOf(
//            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))


        //setupActionBarWithNavController(navController, appBarConfiguration)

        navDisplayController.navigate(R.id.navigation_home)

        navView.setupWithNavController(navDisplayController)


        homeViewModel.getStage()
        myPageViewModel.getMyPage()

    }


    override fun onBackPressed() {
        navDisplayController.addOnDestinationChangedListener{ _, destination, _ ->
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








}