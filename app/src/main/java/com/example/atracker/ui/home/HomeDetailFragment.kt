package com.example.atracker.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.atracker.Utils.ProgressDrawable
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

        binding.homeDetailProgressView.secondaryProgress = 5
        binding.homeDetailProgressView.max = 6
        val bgProgress = ProgressDrawable( lazyContext,6,5)
        binding.homeDetailProgressView.setProgressDrawable(bgProgress)


        //binding.homeDetailProgressView.progressiveStart()

//        seekbar = binding.homeDetailProgressView
//        seekbar.thumb.mutate().alpha = 0
//        initDataToSeekbar()

        return root
    }

//    private fun initDataToSeekbar() {
//        val progressItemList = ArrayList<ProgressItem>()
//        // red span
//        var mProgressItem = ProgressItem()
//        mProgressItem.progressItemPercentage = 10.toFloat()
//        Log.d("test123", mProgressItem.progressItemPercentage.toString() + "")
//        mProgressItem.color = com.example.atracker.R.color.teal_200
//        progressItemList.add(mProgressItem)
//        // blue span
//        mProgressItem = ProgressItem()
//        mProgressItem.progressItemPercentage = 20.toFloat()
//        mProgressItem.color = com.example.atracker.R.color.purple_200
//        progressItemList.add(mProgressItem)
//        // green span
//        mProgressItem = ProgressItem()
//        mProgressItem.progressItemPercentage = 30.toFloat()
//        mProgressItem.color = com.example.atracker.R.color.purple_700
//        progressItemList.add(mProgressItem)
//
//        //white span
//        mProgressItem = ProgressItem()
//        mProgressItem.progressItemPercentage = 20.toFloat()
//        mProgressItem.color = com.example.atracker.R.color.white
//        progressItemList.add(mProgressItem)
//        //seekbar.initData(progressItemList)
//        seekbar.invalidate()
//    }

}