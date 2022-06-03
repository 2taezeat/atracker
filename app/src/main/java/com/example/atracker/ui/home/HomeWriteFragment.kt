package com.example.atracker.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.atracker.databinding.FragmentHomeWriteBinding
import com.example.atracker.ui.MainActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs

import com.google.android.material.tabs.TabLayout


class HomeWriteFragment : Fragment() {

    private var _binding: FragmentHomeWriteBinding? = null
    private val binding get() = _binding!!

    private val parentActivity by lazy {
        activity as MainActivity
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeWriteFragment().apply {
            }
    }

    private lateinit var homeWriteTabLayout : TabLayout
    private val args : HomeWriteFragmentArgs by navArgs()
    private val homeViewModel: HomeViewModel by activityViewModels()


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
        _binding = FragmentHomeWriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeWriteTabLayout = binding.homeWriteTabLayout

        addTabItem(args.progressIndex)

        binding.homeWriteBackButton.setOnClickListener { view ->
            val navController = view.findNavController()
            navController.popBackStack()
        }

        binding.homeWriteTypeSelect1.setOnClickListener {
            binding.homeWriteWholeCL.invalidate()
            binding.homeWriteNestedSV.visibility = View.GONE
            binding.homeWriteNestedSV.visibility = View.VISIBLE
        }




        binding.homeWritePlusButton1.setOnClickListener{
            val homeWriteQnaLayout = this.layoutInflater.inflate(com.example.atracker.R.layout.home_write_qna_layout, null) as ConstraintLayout // inflating view from xml
            var params : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, // This will define text view width
                ConstraintLayout.LayoutParams.WRAP_CONTENT // This will define text view height
            )
            params.setMargins(10,10,10,10)
            homeWriteQnaLayout.layoutParams = params
            homeWriteQnaLayout.id = View.generateViewId()
//            Log.d("test123_2", "${homeWriteQnaLayout.id}")
//
//            val tmp = homeWriteQnaLayout.findViewById<TextView>(com.example.atracker.R.id.homeWriteQuestionTV)
//            tmp.tag = "asd"



            binding.homeWriteLL.addView(homeWriteQnaLayout)
        }

        binding.homeWritePlusButton2.setOnClickListener{
            val homeWriteReviewLayout = this.layoutInflater.inflate(com.example.atracker.R.layout.home_write_review_layout, null) as ConstraintLayout // inflating view from xml
            var params : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, // This will define text view width
                ConstraintLayout.LayoutParams.WRAP_CONTENT // This will define text view height
            )
            params.setMargins(0,10,0,10)
            homeWriteReviewLayout.layoutParams = params
            homeWriteReviewLayout.id = View.generateViewId()


//            val name = thridlayoout.findViewById(com.example.atracker.R.id.homeWriteQuestionTV) as TextView
//
//
//
//            Log.d("test123_3", "${thridlayoout.id}")
//            name.id = View.generateViewId()
//            Log.d("test123_4", "${name.id}")


            binding.homeWriteLL.addView(homeWriteReviewLayout)
        }


        return root

    }

    private fun addTabItem(progressIndex: Int) {
        val homeWriteProgressSelectStringList = homeViewModel.homeWriteProgressSelectArrayList.value!![progressIndex]

        for (progressName in homeWriteProgressSelectStringList) {
            homeWriteTabLayout.addTab(homeWriteTabLayout.newTab().setText(progressName))
        }
    }






}