package com.example.atracker.ui.signUp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.atracker.R
import com.example.atracker.databinding.FragmentSignUpNickNameBinding
import com.example.atracker.databinding.FragmentSignUpTermsBinding
import com.example.atracker.ui.MainActivity
import com.example.atracker.ui.login.LoginActivity
import com.example.atracker.utils.ChangeUIState

class SignUpTermsFragment : Fragment() {

    private val lazyContext by lazy {
        requireContext()
    }



    private val parentActivity by lazy {
        activity as SignUpActivity
    }

    private var _binding: FragmentSignUpTermsBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = DataBindingUtil.inflate<FragmentSignUpTermsBinding>(inflater, R.layout.fragment_sign_up_terms, container, false)

        binding.signUpTermsNext.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_navigation_sign_up_terms_to_navigation_sign_up_nick_name)
        }

        binding.signUpTermsBackIV.setOnClickListener { view ->
            startLogin()
        }


        binding.signUpTermsTotalCheckBox.setOnClickListener { view ->
            if (binding.signUpTermsTotalCheckBox.isChecked) {
                binding.signUpTermsServiceCheckBox.isChecked = true
                binding.signUpTermsPrivacyCheckBox.isChecked = true
                binding.signUpTermsMarketingCheckBox.isChecked = true
            } else {
                binding.signUpTermsServiceCheckBox.isChecked = false
                binding.signUpTermsPrivacyCheckBox.isChecked = false
                binding.signUpTermsMarketingCheckBox.isChecked = false
            }

        }




        binding.signUpTermsServiceCheckBox.setOnCheckedChangeListener { compoundButton, b ->
            ChangeUIState(lazyContext).viewEnable(binding.signUpTermsNext, b, binding.signUpTermsPrivacyCheckBox.isChecked )
            if (!b)
                binding.signUpTermsTotalCheckBox.isChecked = false
        }

        binding.signUpTermsPrivacyCheckBox.setOnCheckedChangeListener { compoundButton, b ->
            ChangeUIState(lazyContext).viewEnable(binding.signUpTermsNext, binding.signUpTermsServiceCheckBox.isChecked, b )
            if (!b)
                binding.signUpTermsTotalCheckBox.isChecked = false
        }

        binding.signUpTermsMarketingCheckBox.setOnCheckedChangeListener { compoundButton, b ->
            if (!b)
                binding.signUpTermsTotalCheckBox.isChecked = false
        }


//        binding.signUpTermsServiceIV.setOnClickListener {
//            val dialogView: View = layoutInflater.inflate(R.layout.activity_webview_modal, null)
//            val termsOfServiceURL = "https://sites.google.com/view/mateprivacyterms"
//            dialogView.termsWebView.apply {
//                webViewClient = WebViewClient()
//                settings.builtInZoomControls = true
//                settings.javaScriptEnabled = true
//                settings.cacheMode = WebSettings.LOAD_DEFAULT
//            }
//
//            dialogView.termsWebView.loadUrl(termsOfServiceURL)
//            val builder: AlertDialog.Builder = AlertDialog.Builder(lazyContext)
//            builder.setView(dialogView)
//            val alertDialog: AlertDialog = builder.create()
//            alertDialog.show()
//        }




        return binding.root
    }


    private fun startLogin() {
        val intent = Intent(parentActivity, LoginActivity::class.java)
        ContextCompat.startActivity(parentActivity, intent, null)
        parentActivity.finish()
    }

}