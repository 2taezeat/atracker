package com.example.atracker.ui.signUp

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
import com.example.atracker.databinding.FragmentSignUpNickNameBinding

class SignUpNickNameFragment : Fragment() {

    private val lazyContext by lazy {
        requireContext()
    }



    private var _binding: FragmentSignUpNickNameBinding? = null
    private val binding get() = _binding!!
    private val signUpViewModel: SignUpViewModel by activityViewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = DataBindingUtil.inflate<FragmentSignUpNickNameBinding>(inflater, R.layout.fragment_sign_up_nick_name, container, false)
        binding.signUpVM = signUpViewModel



        binding.signUpNickNameNickNameET.setOnFocusChangeListener { view, isFocuse ->
            if (isFocuse)
                binding.signUpNickNameView1.background = ContextCompat.getDrawable(lazyContext, R.color.progress_color_7)
            else
                binding.signUpNickNameView1.background = ContextCompat.getDrawable(lazyContext, R.color.atracker_gray_4)
        }


        binding.signUpNickNameNext.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_navigation_sign_up_nick_name_to_navigation_sign_up_position)
        }

        binding.signUpNickNameBackIV.setOnClickListener { view ->
            view.findNavController().popBackStack()
        }





        return binding.root
    }

}