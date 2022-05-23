package com.example.atracker.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.atracker.utils.ProgressBarDrawable
import com.example.atracker.databinding.FragmentHomeDetailBinding
import com.example.atracker.ui.MainActivity


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeDetailFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private var _binding: FragmentHomeDetailBinding? = null
    private val binding get() = _binding!!
    private val parentActivity by lazy {
        activity as MainActivity
    }

    private val lazyContext by lazy {
        requireContext()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment


        _binding = FragmentHomeDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.homeDetailWriteIV.setOnClickListener { view ->
            view.findNavController().navigate(com.example.atracker.R.id.action_navigation_home_detail_to_navigation_home_write)
        }

        binding.homeDetailProgressView.secondaryProgress = 1
        binding.homeDetailProgressView.max = 6

        val bgProgress = ProgressBarDrawable( lazyContext,2,2, true)
        binding.homeDetailProgressView.progressDrawable = bgProgress


        return root
    }

//    private fun initDataToSeekbar() {
}