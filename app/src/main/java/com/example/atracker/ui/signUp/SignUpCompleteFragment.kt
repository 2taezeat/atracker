package com.example.atracker.ui.signUp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.atracker.R
import com.example.atracker.databinding.FragmentSignUpCompleteBinding
import com.example.atracker.ui.MainActivity
import com.example.atracker.ui.home.HomeViewModel
import com.example.atracker.ui.login.LoginActivity
import com.example.atracker.utils.StartActivityUtil


class SignUpCompleteFragment : Fragment() {

    private val lazyContext by lazy {
        requireContext()
    }

    private val parentActivity by lazy {
        activity as SignUpActivity
    }

    private var _binding: FragmentSignUpCompleteBinding? = null
    private val binding get() = _binding!!

    private val signUpViewModel: SignUpViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = DataBindingUtil.inflate<FragmentSignUpCompleteBinding>(inflater, R.layout.fragment_sign_up_complete, container, false)
        binding.signUpVM = signUpViewModel

        binding.signUpCompleteNickNameTV.text = signUpViewModel.signUpNickName.value


        val mHandler = Handler(Looper.getMainLooper())
        mHandler.postDelayed({
            StartActivityUtil.callActivity(parentActivity, LoginActivity())
            parentActivity.finish()

            //startMain()
        }, 3000)


        return binding.root
    }

    private fun startMain() {
        val intent = Intent(parentActivity, MainActivity::class.java)
        ContextCompat.startActivity(parentActivity, intent, null)
        parentActivity.finish()

    }
}