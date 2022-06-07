package com.example.atracker.ui.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.atracker.databinding.FragmentHomeWriteBinding
import com.example.atracker.ui.MainActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.atracker.R
import com.example.atracker.databinding.ActivitySplashBinding
import com.example.atracker.databinding.Test2Binding
import com.example.atracker.databinding.TestBinding

import com.google.android.material.tabs.TabLayout


class HomeWriteFragment : Fragment() {

    private var _binding: FragmentHomeWriteBinding? = null
    private val binding get() = _binding!!
    private val lazyContext by lazy {
        requireContext()
    }

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
    private lateinit var tmp : ConstraintLayout
    private lateinit var tmpList : ArrayList<ConstraintLayout>


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

        addTabItem(args.progressIndex, container)

        binding.homeWriteBackButton.setOnClickListener { view ->
            val navController = view.findNavController()
            navController.popBackStack()
        }


        binding.homeWriteTypeSelect1.setOnClickListener {
            if (binding.homeWriteTypeSelect1.isChecked) {
                binding.homeWriteTypeSelect1.chipStrokeWidth = 4f
                binding.homeWriteTypeSelect1.chipStrokeColor = resources.getColorStateList(R.color.atracker_white)
                binding.homeWriteTypeSelect2.chipStrokeWidth = 0f
                binding.homeWriteTypeSelect3.chipStrokeWidth = 0f
            } else {
                binding.homeWriteTypeSelect1.chipStrokeWidth = 0f
            }
        }


        binding.homeWriteTypeSelect2.setOnClickListener {
            if (binding.homeWriteTypeSelect2.isChecked) {
                binding.homeWriteTypeSelect2.chipStrokeWidth = 4f
                binding.homeWriteTypeSelect2.chipStrokeColor = resources.getColorStateList(R.color.atracker_white)
                binding.homeWriteTypeSelect1.chipStrokeWidth = 0f
                binding.homeWriteTypeSelect3.chipStrokeWidth = 0f
            } else {
                binding.homeWriteTypeSelect2.chipStrokeWidth = 0f
            }
        }


        binding.homeWriteTypeSelect3.setOnClickListener {
            if (binding.homeWriteTypeSelect3.isChecked) {
                binding.homeWriteTypeSelect3.chipStrokeWidth = 4f
                binding.homeWriteTypeSelect3.chipStrokeColor = resources.getColorStateList(R.color.progress_color_7)
                binding.homeWriteTypeSelect1.chipStrokeWidth = 0f
                binding.homeWriteTypeSelect2.chipStrokeWidth = 0f
            } else {
                binding.homeWriteTypeSelect3.chipStrokeWidth = 0f
            }
        }


        binding.homeWriteTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.d("onTabSelected","${tab!!.position}")
                val pos = tab!!.position
                changeView(tab.tag.toString())

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.d("onTabReselected","${tab!!.position}")
                val pos = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.d("onTabUnselected","${tab!!.position}")
            }
        })



        binding.homeWritePlusButton1.setOnClickListener{
            val homeWriteQnaLayout = this.layoutInflater.inflate(R.layout.home_write_qna_layout, null) as ConstraintLayout // inflating view from xml
            val params : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, // This will define text view width
                ConstraintLayout.LayoutParams.WRAP_CONTENT // This will define text view height
            )
            params.setMargins(0,20,0,10)
            homeWriteQnaLayout.layoutParams = params
            homeWriteQnaLayout.id = View.generateViewId()

//            val tmp = homeWriteQnaLayout.findViewById<TextView>(com.example.atracker.R.id.homeWriteQuestionTV)
//            tmp.tag = "asd"



            binding.homeWriteLL.addView(homeWriteQnaLayout)
        }

        binding.homeWritePlusButton2.setOnClickListener{
            val homeWriteReviewLayout = this.layoutInflater.inflate(R.layout.home_write_review_layout, null) as ConstraintLayout // inflating view from xml
            val params : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, // This will define text view width
                ConstraintLayout.LayoutParams.WRAP_CONTENT // This will define text view height
            )
            params.setMargins(0,20,0,10)
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

    private fun addTabItem(progressIndex: Int, container: ViewGroup?) {
        val homeWriteProgressSelectStringList = homeViewModel.homeWriteProgressSelectArrayList.value!![progressIndex]
        var idx = 0

        tmpList = arrayListOf<ConstraintLayout>()
        for (progressName in homeWriteProgressSelectStringList) {
            homeWriteTabLayout.addTab(homeWriteTabLayout.newTab().setText(progressName).setId(View.generateViewId()).setTag(progressName))

            tmp = this.layoutInflater.inflate(R.layout.test, null) as ConstraintLayout // inflating view from xml
            val params : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, // This will define text view width
                ConstraintLayout.LayoutParams.MATCH_PARENT // This will define text view height
            )

            tmp.layoutParams = params

            //val button = tmp.getViewById(R.id.testButton)

            val homeWriteNestedSV = tmp.getViewById(R.id.homeWriteNestedSV)
            val homeWriteMainCL = homeWriteNestedSV.findViewById<ConstraintLayout>(R.id.homeWriteMainCL)
            val homeWriteLL = homeWriteNestedSV.findViewById<LinearLayout>(R.id.homeWriteLL)
            val homeWritePlusButton1 = homeWriteMainCL.findViewById<TextView>(R.id.homeWritePlusButton1)
            val homeWritePlusButton2 = homeWriteMainCL.findViewById<TextView>(R.id.homeWritePlusButton2)

            Log.d("testButton", "${homeWritePlusButton1.tag}")


            homeWritePlusButton1.setOnClickListener{
                Log.d("test123123", "test123123123")
                val homeWriteQnaLayout = this.layoutInflater.inflate(R.layout.home_write_qna_layout, null) as ConstraintLayout // inflating view from xml
                val params : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT, // This will define text view width
                    ConstraintLayout.LayoutParams.WRAP_CONTENT // This will define text view height
                )
                params.setMargins(0,20,0,10)
                homeWriteQnaLayout.layoutParams = params
                homeWriteQnaLayout.id = View.generateViewId()

                binding.homeWriteLL.addView(homeWriteQnaLayout)
            }



            tmp.id = View.generateViewId()
            tmp.tag = progressName
            Log.d("test123", "${tmp.tag}")

            if (idx == 0)
                tmp.visibility = View.VISIBLE
            else
                tmp.visibility = View.INVISIBLE

            tmpList.add(tmp)

//            button.setOnClickListener {
//                Log.d("test345", "test345")
//            }




//            button.setOnClickListener {
//                val homeWriteQnaLayout = this.layoutInflater.inflate(R.layout.home_write_qna_layout, null) as ConstraintLayout // inflating view from xml
//                val params : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
//                    ConstraintLayout.LayoutParams.MATCH_PARENT, // This will define text view width
//                    ConstraintLayout.LayoutParams.WRAP_CONTENT // This will define text view height
//                )
//                params.setMargins(0,20,0,10)
//                homeWriteQnaLayout.layoutParams = params
//                homeWriteQnaLayout.id = View.generateViewId()
//
//            }




            binding.homeWriteWholeCL.addView(tmp)
            idx += 1
        }
    }



    private fun changeView(layoutTagName : String) {
//        when (index) {
//            0 -> {
//                binding.dashboardMainAddCl.visibility = View.VISIBLE
//                binding.dashboardMainEditCl.visibility = View.INVISIBLE
//            }
//            1 -> {
//                binding.dashboardMainAddCl.visibility = View.INVISIBLE
//                binding.dashboardMainEditCl.visibility = View.VISIBLE
//            }
//        }

        //val homeWriteProgressSelectStringList = homeViewModel.homeWriteProgressSelectArrayList.value!![progressIndex]

        for(t in tmpList){
            if (layoutTagName == t.tag) {
                t.visibility = View.VISIBLE
            } else {
                t.visibility = View.INVISIBLE
            }

        }



    }







}