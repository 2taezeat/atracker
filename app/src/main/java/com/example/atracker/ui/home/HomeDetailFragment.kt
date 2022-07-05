package com.example.atracker.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.atracker.utils.ProgressBarDrawable
import com.example.atracker.databinding.FragmentHomeDetailBinding
import com.example.atracker.ui.MainActivity


class HomeDetailFragment : Fragment() {


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeDetailFragment().apply {
                arguments = Bundle().apply {
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

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val args : HomeDetailFragmentArgs by navArgs()

    private val homeDetailAdapter by lazy {
        HomeDetailAdapter()
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


        _binding = FragmentHomeDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val homeProgress = homeViewModel.homeProgressArrayList.value!![args.progressIndex]



        binding.homeDetailWriteIV.setOnClickListener { view ->
            val action = HomeDetailFragmentDirections.actionNavigationHomeDetailToNavigationHomeWrite(args.progressIndex)

            view.findNavController().navigate(action)
        }

        binding.homeDetailProgressView.secondaryProgress = 1
        binding.homeDetailProgressView.max = 6


        binding.homeDetailBackButton.setOnClickListener { view ->
            val navController = view.findNavController()
            navController.popBackStack()
        }


        homeDetailAdapter.submitList(homeViewModel.homeDetailContents.value!![args.progressIndex])


        binding.homeDetailRV.also{
            it.layoutManager = LinearLayoutManager(parentActivity, LinearLayoutManager.VERTICAL, false)
            it.setHasFixedSize(false)
            it.adapter = homeDetailAdapter
        }



        val bgProgress = ProgressBarDrawable( lazyContext,homeProgress.totalProgress,homeProgress.myProgress, true)
        binding.homeDetailProgressView.progressDrawable = bgProgress


        return root
    }

//    private fun initDataToSeekbar() {
}