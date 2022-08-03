package com.cmc.atracker.ui.signUp

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.cmc.atracker.BuildConfig
import com.cmc.atracker.R
import com.cmc.atracker.databinding.FragmentSignUpTermsBinding
import com.cmc.atracker.ui.login.LoginActivity
import com.cmc.atracker.utils.ChangeUIState
import com.cmc.atracker.utils.StartActivityUtil

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
            StartActivityUtil.callActivity( parentActivity, LoginActivity())
            parentActivity.finish()
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
            showWebViewDialog(BuildConfig.TERSMS_SERVICE_WEB_URL, getString(R.string.terms_service_confirm_title), binding.signUpTermsServiceCheckBox)
        }

        binding.signUpTermsPrivacyIV.setOnClickListener {
            showWebViewDialog(BuildConfig.TERSMS_PRIVACY_WEB_URL, getString(R.string.terms_privacy_title), binding.signUpTermsPrivacyCheckBox)
        }


        return binding.root
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