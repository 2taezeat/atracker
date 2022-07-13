package com.example.atracker.ui.signUp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.atracker.R
import com.example.atracker.databinding.FragmentSignUpNickNameBinding
import com.example.atracker.databinding.FragmentSignUpTermsBinding
import com.example.atracker.ui.MainActivity
import com.example.atracker.ui.login.LoginActivity

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


        return binding.root
    }


    private fun startLogin() {
        val intent = Intent(parentActivity, LoginActivity::class.java)
        ContextCompat.startActivity(parentActivity, intent, null)
        parentActivity.finish()
    }

}