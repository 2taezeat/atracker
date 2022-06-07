package com.example.atracker.ui.home

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import com.example.atracker.databinding.FragmentHomeWriteBinding
import com.example.atracker.ui.MainActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.atracker.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

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
    private lateinit var homeWriteContentLayout : ConstraintLayout
    private lateinit var dynamicLayoutList : ArrayList<ConstraintLayout>
    //private lateinit var tmpList : ArrayList<ConstraintLayout>

    val reviewLayoutList by lazy {
        arrayListOf<ConstraintLayout>()
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
        _binding = FragmentHomeWriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeWriteTabLayout = binding.homeWriteTabLayout

        addTabItem(args.progressIndex, container)

        binding.homeWriteBackButton.setOnClickListener { view ->
            val navController = view.findNavController()
            navController.popBackStack()
        }



        binding.homeWriteTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.d("onTabSelected","${tab!!.position}")
                changeView(tab.tag.toString())
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.d("onTabReselected","${tab!!.position}")
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
        dynamicLayoutList = arrayListOf<ConstraintLayout>()

        for (progressName in homeWriteProgressSelectStringList) {
            homeWriteTabLayout.addTab(homeWriteTabLayout.newTab().setText(progressName).setId(View.generateViewId()).setTag(progressName))

            homeWriteContentLayout = this.layoutInflater.inflate(R.layout.home_write_content_layout, null) as ConstraintLayout // inflating view from xml
            val params : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, // This will define text view width
                0 // This will define text view height
            )

            val homeWriteNestedSV = homeWriteContentLayout.getViewById(R.id.homeWriteNestedSV)
            val homeWriteMainCL = homeWriteNestedSV.findViewById<ConstraintLayout>(R.id.homeWriteMainCL)
            val homeWriteLL = homeWriteNestedSV.findViewById<LinearLayout>(R.id.homeWriteLL)
            val homeWritePlusButton1 = homeWriteMainCL.findViewById<TextView>(R.id.homeWritePlusButton1)
            val homeWritePlusButton2 = homeWriteMainCL.findViewById<TextView>(R.id.homeWritePlusButton2)
            val homeWriteTypeSelectChipGroup = homeWriteMainCL.findViewById<ChipGroup>(R.id.homeWriteTypeSelectChipGroup)
            val homeWriteEditButton = homeWriteMainCL.findViewById<TextView>(R.id.homeWriteEditButton)
            val homeWriteEditCompleteButton = homeWriteMainCL.findViewById<TextView>(R.id.homeWriteEditCompleteButton)
            val homeWriteDeleteChip = homeWriteMainCL.findViewById<Chip>(R.id.homeWriteDeleteChip)
            val homeWriteTypeSelect1 = homeWriteTypeSelectChipGroup.findViewById<Chip>(R.id.homeWriteTypeSelect1)
            val homeWriteTypeSelect2 = homeWriteTypeSelectChipGroup.findViewById<Chip>(R.id.homeWriteTypeSelect2)
            val homeWriteTypeSelect3 = homeWriteTypeSelectChipGroup.findViewById<Chip>(R.id.homeWriteTypeSelect3)

            homeWriteContentLayout.layoutParams = params

            setChip(chip1 = homeWriteTypeSelect1, chip2 = homeWriteTypeSelect2, chip3 = homeWriteTypeSelect3, colorStateList = resources.getColorStateList(R.color.atracker_white))
            setChip(chip1 = homeWriteTypeSelect2, chip2 = homeWriteTypeSelect1, chip3 = homeWriteTypeSelect3, colorStateList = resources.getColorStateList(R.color.atracker_white))
            setChip(chip1 = homeWriteTypeSelect3, chip2 = homeWriteTypeSelect2, chip3 = homeWriteTypeSelect1, colorStateList = resources.getColorStateList(R.color.progress_color_7))


            setPlusButton(homeWritePlusButton1, R.layout.home_write_qna_layout,homeWriteLL )
            setPlusButton(homeWritePlusButton2, R.layout.home_write_review_layout,homeWriteLL )


            homeWriteEditButton.setOnClickListener {
                homeWriteTypeSelectChipGroup.visibility = View.INVISIBLE
                homeWriteEditButton.visibility = View.INVISIBLE
                homeWriteEditCompleteButton.visibility = View.VISIBLE
                homeWriteDeleteChip.visibility = View.VISIBLE

                val addLayout = this.layoutInflater.inflate(R.layout.home_write_review_layout, null) as ConstraintLayout // inflating view from xml
                val tmp = addLayout.getViewById(R.id.homeWriteReviewCheckBox)
                tmp.visibility = View.VISIBLE

                for (layout in reviewLayoutList) {
                    layout.getViewById(R.id.homeWriteReviewCheckBox).visibility = View.VISIBLE
                    val tmp = layout.findViewById<ConstraintLayout>(R.id.homeWriteReviewMainCL)
                    val checkBox = layout.findViewById<CheckBox>(R.id.homeWriteReviewCheckBox)

                    val set = ConstraintSet()
                    set.clone(layout)
                    set.connect(tmp.id,ConstraintSet.START, checkBox.id , ConstraintSet.END, 10)
                    set.applyTo(layout)
                }

            }


            homeWriteEditCompleteButton.setOnClickListener {
                homeWriteTypeSelectChipGroup.visibility = View.VISIBLE
                homeWriteEditCompleteButton.visibility = View.INVISIBLE
                homeWriteDeleteChip.visibility = View.INVISIBLE
                homeWriteEditButton.visibility = View.VISIBLE

                for (layout in reviewLayoutList) {
                    layout.getViewById(R.id.homeWriteReviewCheckBox).visibility = View.GONE
//                    val tmp = layout.findViewById<ConstraintLayout>(R.id.homeWriteReviewMainCL)
//                    val checkBox = layout.findViewById<CheckBox>(R.id.homeWriteReviewCheckBox)
//
//                    val set = ConstraintSet()
//                    set.clone(layout)
//                    set.connect(tmp.id,ConstraintSet.START, checkBox.id , ConstraintSet.END)
//                    set.applyTo(layout)

                }

            }



            homeWriteContentLayout.id = View.generateViewId()
            homeWriteContentLayout.tag = progressName

            if (idx == 0)
                homeWriteContentLayout.visibility = View.VISIBLE
            else
                homeWriteContentLayout.visibility = View.INVISIBLE

            dynamicLayoutList.add(homeWriteContentLayout)

            binding.homeWriteContentCL.addView(homeWriteContentLayout)
            idx += 1
        }
    }

    private fun setChip(chip1 : Chip, chip2 : Chip, chip3 : Chip, colorStateList: ColorStateList){
        chip1.setOnClickListener {
            if (chip1.isChecked) {
                chip1.chipStrokeWidth = 4f
                chip1.chipStrokeColor = colorStateList
                chip2.chipStrokeWidth = 0f
                chip3.chipStrokeWidth = 0f
            } else {
                chip1.chipStrokeWidth = 0f
            }
        }
    }


    private fun setPlusButton(button: TextView, layoutInt : Int, linearLayout: LinearLayout){
        button.setOnClickListener{
            val addLayout = this.layoutInflater.inflate(layoutInt, null) as ConstraintLayout // inflating view from xml
            val params : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, // This will define text view width
                ConstraintLayout.LayoutParams.WRAP_CONTENT // This will define text view height
            )
            params.setMargins(0,20,0,10)
            addLayout.layoutParams = params
            addLayout.id = View.generateViewId()

            linearLayout.addView(addLayout)
            reviewLayoutList.add(addLayout)
        }

    }



    private fun changeView(layoutTagName : String) {
        for(layout in dynamicLayoutList){
            if (layoutTagName == layout.tag) {
                layout.visibility = View.VISIBLE
            } else {
                layout.visibility = View.INVISIBLE
            }

        }

    }


}