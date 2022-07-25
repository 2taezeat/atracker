package com.example.atracker.ui

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.atracker.R
import com.example.atracker.databinding.FragmentMyPageDisplayBinding
import com.example.atracker.ui.login.LoginActivity
import com.example.atracker.ui.signUp.SignUpViewModel
import com.example.atracker.utils.AlertType
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth




class MyPageDisplayFragment : Fragment() {

    private val lazyContext by lazy {
        requireContext()
    }
    private val parentActivity by lazy {
        activity as MainActivity
    }

    private val myPageViewModel: MyPageViewModel by activityViewModels()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private lateinit var binding: FragmentMyPageDisplayBinding
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        val binding = DataBindingUtil.inflate<FragmentMyPageDisplayBinding>(inflater,
            com.example.atracker.R.layout.fragment_my_page_display,
            container,
            false)

        if (myPageViewModel.userNickName.value == "") {
            val navController = parentActivity.findNavController(R.id.navHostFragmentActivityMain)
            navController.navigate(R.id.navigation_home)
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            //.requestIdToken(getString(com.example.atracker.R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(lazyContext, gso)



        binding.myPageLogoutTV.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            googleSignInClient.signOut().addOnCompleteListener {
                //activity!!.finish()
                //startLogin()

                showAlert(AlertType.TYPE10)
            }
        }

        binding.myPageNickNameTV.text = myPageViewModel.userNickName.value
        binding.myPagePositionTV.text = myPageViewModel.userJobPosition.value
        binding.myPageCareerAgeTV.text = myPageViewModel.userExperienceType.value


//        binding.myPageBackButton.setOnClickListener { view ->
//            val navController = view.findNavController()
//            navController.popBackStack()
//        }

        val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(
            ContextCompat.getColor(lazyContext, com.example.atracker.R.color.progress_color_1),
            ContextCompat.getColor(lazyContext, com.example.atracker.R.color.progress_color_2),
            ContextCompat.getColor(lazyContext, com.example.atracker.R.color.progress_color_3),
            ContextCompat.getColor(lazyContext, com.example.atracker.R.color.progress_color_4),
            ContextCompat.getColor(lazyContext, com.example.atracker.R.color.progress_color_5),
            ContextCompat.getColor(lazyContext, com.example.atracker.R.color.progress_color_6),
            ContextCompat.getColor(lazyContext, com.example.atracker.R.color.progress_color_7)
        )
        )

        //findViewById(com.example.atracker.R.id.background).setBackground(gradientDrawable)
        binding.myPageView1.background = gradientDrawable



        return binding.root
    }

    fun startLogin() {
        val intent = Intent(lazyContext, LoginActivity::class.java)
        ContextCompat.startActivity(lazyContext, intent, null)
        activity!!.finish()
    }


    private fun showAlert(alertType: AlertType) {
        val alertDialogFragment = AlertDialogFragment.instance(
            object : AlertDialogListener {
                override fun onLeftClick() {
                }

                override fun onCenterClick() {

                }

                override fun onRightClick() {
                    when (alertType) {
                        AlertType.TYPE10 -> {
                            startLogin()
                        }
                    }
                }
            },
            alertType,
            null
        )

        alertDialogFragment.show(childFragmentManager, AlertDialogFragment.TAG)
    }

}