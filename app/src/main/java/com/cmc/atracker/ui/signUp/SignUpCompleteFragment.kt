package com.cmc.atracker.ui.signUp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.cmc.atracker.R
import com.cmc.atracker.databinding.FragmentSignUpCompleteBinding
import com.cmc.atracker.ui.MainActivity
import com.cmc.atracker.utils.StartActivityUtil


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


        signUpViewModel.postSignUpFail.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { boolean ->
                if (!boolean) { // postSignUp 성공
                    val mHandler = Handler(Looper.getMainLooper())
                    mHandler.postDelayed({
                        StartActivityUtil.callActivity(parentActivity, MainActivity())
                        parentActivity.finish()

                    }, 3000)
                }
            }
        })




        return binding.root
    }


}