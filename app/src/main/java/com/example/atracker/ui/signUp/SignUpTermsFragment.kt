package com.example.atracker.ui.signUp

import android.app.AlertDialog
import android.content.Intent
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


        binding.signUpTermsServiceIV.setOnClickListener {
            showWebViewDialog("https://atracker-web.netlify.app/terms/user", getString(R.string.terms_service_confirm_title), binding.signUpTermsServiceCheckBox)
        }

        binding.signUpTermsPrivacyIV.setOnClickListener {
            showWebViewDialog("https://atracker-web.netlify.app/terms", getString(R.string.terms_privacy_title), binding.signUpTermsPrivacyCheckBox)
        }




        return binding.root
    }


    private fun startLogin() {
        val intent = Intent(parentActivity, LoginActivity::class.java)
        ContextCompat.startActivity(parentActivity, intent, null)
        parentActivity.finish()
    }


    private fun showWebViewDialog(url: String?, headTitle : String, checkBox: CheckBox) {
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
            setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
                checkBox.isChecked = false
            }
            setPositiveButton("동의") { dialog, _ ->
                dialog.dismiss()
                checkBox.isChecked = true
            }
            show()
        }



    }
}