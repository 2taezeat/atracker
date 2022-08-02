package com.example.atracker.ui

import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.atracker.BuildConfig
import com.example.atracker.R
import com.example.atracker.databinding.FragmentMyPageDisplayBinding
import com.example.atracker.model.local.App
import com.example.atracker.ui.login.LoginActivity
import com.example.atracker.ui.signUp.SignUpViewModel
import com.example.atracker.utils.AlertApiObject
import com.example.atracker.utils.AlertType
import com.example.atracker.utils.StartActivityUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions



class MyPageDisplayFragment : Fragment() {

    private val lazyContext by lazy {
        requireContext()
    }
    private val parentActivity by lazy {
        activity as MainActivity
    }

    private val myPageViewModel: MyPageViewModel by activityViewModels()
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

        if (myPageViewModel.userNickName.value == " ") {
            parentActivity.showAlertInstance(AlertApiObject.alertDialogFragment)

            val navController = parentActivity.findNavController(R.id.navHostFragmentActivityMain)
            navController.navigate(R.id.navigation_home)
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            //.requestIdToken(getString(com.example.atracker.R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(lazyContext, gso)



        binding.myPageLogoutTV.setOnClickListener {
            //FirebaseAuth.getInstance().signOut()
            googleSignInClient.signOut().addOnCompleteListener {
                //activity!!.finish()
                //startLogin()

                showAlert(AlertType.TYPE10)
            }
        }


        binding.myPageAccountWithdrawalTV.setOnClickListener {
//            FirebaseAuth.getInstance().signOut()
//            googleSignInClient.signOut().addOnCompleteListener {
//                //activity!!.finish()
//                //startLogin()
//                showAlert(AlertType.TYPE10)
//            }
            showAlert(AlertType.TYPE11)
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


        binding.myPageTermsServiceTV.setOnClickListener {
            showWebViewDialog(BuildConfig.TERSMS_SERVICE_WEB_URL, getString(R.string.terms_service_confirm_title_ori))
        }

        binding.myPageTermsPrivacyTV.setOnClickListener {
            showWebViewDialog(BuildConfig.TERSMS_PRIVACY_WEB_URL, getString(R.string.terms_privacy_title_ori))
        }



        return binding.root
    }

    fun startLogin() {
        StartActivityUtil.callActivity(parentActivity, LoginActivity())
        parentActivity.finish()
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
                        AlertType.TYPE10 -> { // 로그 아웃
                            // * at, rt 로컬에서 삭제해야 함. 비동기 처리 finish(), ondestory 처리 해야됨 수도.

                            App.prefs.removeValue(BuildConfig.ACCESS_LOCAL_TOKEN)
                            App.prefs.removeValue(BuildConfig.REFRESH_LOCAL_TOKEN)

                            startLogin()
                        }
                        AlertType.TYPE11 -> { // 회원 탈퇴

                            // at, rt 로컬에서 삭제해야 함.
                            App.prefs.removeValue(BuildConfig.ACCESS_LOCAL_TOKEN)
                            App.prefs.removeValue(BuildConfig.REFRESH_LOCAL_TOKEN)

                            App.prefs.removeValue(BuildConfig.EMAIL)

                            myPageViewModel.signOutPost()
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

    private fun showWebViewDialog(url: String?, headTitle : String) {
        val webView = WebView(lazyContext).apply {
            loadUrl(url!!)
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view!!.loadUrl(url!!)
                    return true
                }
            }.apply {
                settings.javaScriptEnabled = true
                settings.cacheMode = WebSettings.LOAD_DEFAULT
            }
        }
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(lazyContext, R.style.AppCompatAlertDialog)
        alertDialogBuilder.apply {
            setTitle(headTitle)
            setView(webView)
            setPositiveButton("확인") { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }

    }

}