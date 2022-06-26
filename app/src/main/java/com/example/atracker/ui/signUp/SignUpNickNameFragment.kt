package com.example.atracker.ui.signUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.atracker.R

class SignUpNickNameFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignUpNickNameFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up_nick_name, container, false)
    }




}