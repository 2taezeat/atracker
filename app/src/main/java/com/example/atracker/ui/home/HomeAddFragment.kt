package com.example.atracker.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.atracker.R
import com.example.atracker.databinding.FragmentHomeDisplayBinding
import com.example.atracker.ui.MainActivity


class HomeAddFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var _binding: FragmentHomeDisplayBinding? = null
    private val parentActivity by lazy {
        activity as MainActivity
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {


        return inflater.inflate(R.layout.fragment_home_add, container, false)



    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeAddFragment().apply {
                arguments = Bundle().apply {



                }
            }
    }
}