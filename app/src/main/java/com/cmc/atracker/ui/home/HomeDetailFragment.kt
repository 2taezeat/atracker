package com.cmc.atracker.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmc.atracker.utils.ProgressBarDrawable
import com.cmc.atracker.databinding.FragmentHomeDetailBinding
import com.cmc.atracker.ui.MainActivity
import com.google.android.material.tabs.TabLayout


class HomeDetailFragment : Fragment() {


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

    private lateinit var homeDetailTabLayout : TabLayout


    private val homeDetailAdapter by lazy {
        HomeDetailAdapter()
    }

    private lateinit var progressNameList : ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HomeDetailFragment","onCreate")
        progressNameList = arrayListOf()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val homeProgress = homeViewModel.homeDetailArrayList.value!![args.displayListPosition]
        homeDetailTabLayout = binding.homeDetailTabLayout


//        if (homeViewModel.homeApplyIdContent.value!!.apply_id == -1) {
//            Log.d("test222", "test222")
//            parentActivity.onBackPressed()
//        }
//
////        homeViewModel.homeApplyIdContent.observe(viewLifecycleOwner, Observer {
////            Log.d("test222", "test222")
////            if (it.apply_id == -1) {
////                parentActivity.onBackPressed()
////            }
////        })


        homeViewModel.homeProgressNameDetail.observe(viewLifecycleOwner, Observer {
            val ori = it.getContentIfNotHandled()
            if (ori != null) {
                homeViewModel.setHomeProgressNameWrite(ori)
                addTabItem(ori)
            } else {
                addTabItem(progressNameList)
            }
        })




//        binding.homeDetailWriteIV.setOnClickListener { view ->
//            val action = HomeDetailFragmentDirections.actionNavigationHomeDetailToNavigationHomeWrite(args.progressIndex)
//            view.findNavController().navigate(action)
//        }

//        binding.homeDetailProgressView.secondaryProgress = 1
//        binding.homeDetailProgressView.max = 6


        binding.homeDetailBackButton.setOnClickListener { view ->
            val navController = view.findNavController()
            navController.popBackStack()
        }


        //homeDetailAdapter.submitList(homeViewModel.homeDetailContents.value!![args.progressIndex])


        homeViewModel.homeDetailContentForDisplay.observe(viewLifecycleOwner, Observer {
            Log.d("homeDetailContentForDisplay", "${homeViewModel.homeDetailContentForDisplay.value}")
            homeDetailAdapter.submitList(homeViewModel.homeDetailContentForDisplay.value!!)
        })


        binding.homeDetailRV.also{
            it.layoutManager = LinearLayoutManager(parentActivity, LinearLayoutManager.VERTICAL, false)
            it.setHasFixedSize(false)
            it.adapter = homeDetailAdapter
        }

        val bgProgress = ProgressBarDrawable( lazyContext,homeProgress.totalProgress,homeProgress.myProgress, true)
        binding.homeDetailProgressView.progressDrawable = bgProgress


        binding.homeDetailMoreIV.setOnClickListener {
            val homeBottomSheetFragment = HomeBottomSheetFragment(args.progressIndex, args.displayListPosition)
            homeBottomSheetFragment.show(parentFragmentManager, homeBottomSheetFragment.tag)
        }

        homeViewModel.homeApplyIdContent.observe(viewLifecycleOwner, Observer {
            binding.homeDetailCompanyTitle.text = homeViewModel.homeApplyIdContent.value!!.company_name
        })


        return root
    }

    override fun onResume() {
        super.onResume()
        Log.d("HomeDetailFragment","onResume")

    }

    override fun onStop() {
        super.onStop()
        Log.d("HomeDetailFragment","onStop")

        if (homeViewModel.homeApplyIdContent.value!!.apply_id != -1) {
            progressNameList = homeViewModel.homeProgressNameWrite.value!!
        }
    }


    private fun addTabItem(stageTitleList : ArrayList<String>) {
        for (progressName in stageTitleList) {
            homeDetailTabLayout.addTab(homeDetailTabLayout.newTab().setText(progressName).setId(View.generateViewId()).setTag(progressName))
        }
    }

}