package com.example.atracker.ui.home

import android.content.res.List
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.atracker.databinding.FragmentHomeWriteBinding
import com.example.atracker.ui.MainActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.atracker.R
import com.example.atracker.model.dto.IsPassing
import com.example.atracker.model.dto.ProgressItemBodyType
import com.example.atracker.ui.AlertDialogFragment
import com.example.atracker.ui.AlertDialogListener
import com.example.atracker.utils.AlertType
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
    //private lateinit var dynamicLayoutList : ArrayList<ConstraintLayout>
    private lateinit var dynamicLayoutMap : MutableMap<String, ConstraintLayout>
    private var previousTabPosition = 0
    private var previousTabName = ""


    private var editBooleanMap = mutableMapOf<String, Boolean>()


    private lateinit var progressIsPassingMap : MutableMap<Int, IsPassing?>



    private val reviewLayoutListMap by lazy {
        mutableMapOf<String, List<ConstraintLayout>>()
    }

    private val qnaLayoutListMap by lazy {
        mutableMapOf<String, List<ConstraintLayout>>()
    }

    private val reviewRemoveLayoutList by lazy {
        arrayListOf<ConstraintLayout>()
    }

    private val qnaRemoveLayoutList by lazy {
        arrayListOf<ConstraintLayout>()
    }



    private val reviewRemoveLayoutList_map by lazy {
        mutableMapOf<String, List<ConstraintLayout>>()
    }

    private val qnaRemoveLayoutList_map by lazy {
        mutableMapOf<String, List<ConstraintLayout>>()
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
                Log.d("onTabSelected", "${previousTabPosition}, ${tab!!.position}")

                if (editBooleanMap[previousTabName] == true && previousTabPosition != tab!!.position) {
                    showAlert(AlertType.TYPE3, tab, null)

                    Log.d("test123123123", "test123123123")
                } else {
                    val previousState = progressIsPassingMap[previousTabPosition]
                    val selectedMinusOneState = progressIsPassingMap[tab.position - 1]

                    if (previousTabPosition < tab.position && (previousState != IsPassing.SUCCESS || selectedMinusOneState != IsPassing.SUCCESS)) {
                        showAlert(AlertType.TYPE2 , tab,null)
                        binding.homeWriteTabLayout.getTabAt(previousTabPosition)!!.select()
                    } else {
                        previousTabPosition = tab.position
                        previousTabName = tab.tag.toString()

                        changeView(tab.tag.toString())
                    }
                }
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

            binding.homeWriteLL.addView(homeWriteReviewLayout)
        }



        binding.homeWriteSaveTV.setOnClickListener {
            showAlert(AlertType.TYPE4, null, null)
        }


        return root

    }

    private fun addTabItem(progressIndex: Int, container: ViewGroup?) {
        val homeWriteProgressSelected = homeViewModel.homeWriteProgressSelectedMap.value!![progressIndex]
        val homeDetailContents = homeViewModel.homeDetailContents.value!![progressIndex]
        var idx = 0


        //dynamicLayoutList = arrayListOf<ConstraintLayout>()
        previousTabName = homeWriteProgressSelected!![0]

        dynamicLayoutMap = mutableMapOf()
        progressIsPassingMap = mutableMapOf()

        for (progressName in homeWriteProgressSelected!!) {
            homeWriteTabLayout.addTab(homeWriteTabLayout.newTab().setText(progressName).setId(View.generateViewId()).setTag(progressName))

            homeWriteContentLayout = this.layoutInflater.inflate(R.layout.home_write_content_layout, null) as ConstraintLayout // inflating view from xml
            val params : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, // This will define text view width
                0 // This will define text view height
            )

            editBooleanMap[progressName] = false


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

            setChip(chip1 = homeWriteTypeSelect1, chip2 = homeWriteTypeSelect2, chip3 = homeWriteTypeSelect3, colorValue = R.color.atracker_white)
            setChip(chip1 = homeWriteTypeSelect2, chip2 = homeWriteTypeSelect1, chip3 = homeWriteTypeSelect3, colorValue = R.color.atracker_white)
            setChip(chip1 = homeWriteTypeSelect3, chip2 = homeWriteTypeSelect2, chip3 = homeWriteTypeSelect1, colorValue = R.color.progress_color_7)

            setPlusButton(homeWritePlusButton1, R.layout.home_write_qna_layout, homeWriteLL, progressName)
            setPlusButton(homeWritePlusButton2, R.layout.home_write_review_layout, homeWriteLL, progressName)



            homeWriteEditButton.setOnClickListener {

                homeWriteTypeSelectChipGroup.visibility = View.INVISIBLE
                homeWriteEditButton.visibility = View.INVISIBLE
                homeWriteEditCompleteButton.visibility = View.VISIBLE
                homeWriteDeleteChip.visibility = View.VISIBLE
                homeWritePlusButton1.visibility = View.INVISIBLE
                homeWritePlusButton2.visibility = View.INVISIBLE

                editBooleanMap[progressName] = true

                val reviewLayoutList = reviewLayoutListMap[progressName]
                val qnaLayoutList = qnaLayoutListMap[progressName]

                for (reviewLayout in reviewLayoutList.orEmpty()) {
                    reviewLayout.getViewById(R.id.homeWriteReviewCheckBox).visibility = View.VISIBLE
                    val homeWriteReviewMainCL = reviewLayout.findViewById<ConstraintLayout>(R.id.homeWriteReviewMainCL)
                    val reviewDeleteCheckBox = reviewLayout.findViewById<CheckBox>(R.id.homeWriteReviewCheckBox)
                    val set = ConstraintSet()
                    set.clone(reviewLayout)
                    set.connect(homeWriteReviewMainCL.id,ConstraintSet.START, reviewDeleteCheckBox.id , ConstraintSet.END, 20)
                    set.applyTo(reviewLayout)
                    reviewDeleteCheckBox.isChecked = false

                    reviewDeleteCheckBox.setOnCheckedChangeListener { compoundButton, boolean ->
                        if (boolean)
                            reviewRemoveLayoutList.add(reviewLayout)
                        else
                            reviewRemoveLayoutList.remove(reviewLayout)
                    }
                }

                for (qnaLayout in qnaLayoutList.orEmpty()) {
                    qnaLayout.getViewById(R.id.homeWriteQnaCheckBox).visibility = View.VISIBLE
                    val homeWriteQnaMainCL = qnaLayout.findViewById<ConstraintLayout>(R.id.homeWriteQnaMainCL)
                    val qnaDeleteCheckBox = qnaLayout.findViewById<CheckBox>(R.id.homeWriteQnaCheckBox)
                    val set = ConstraintSet()
                    set.clone(qnaLayout)
                    set.connect(homeWriteQnaMainCL.id,ConstraintSet.START, qnaDeleteCheckBox.id , ConstraintSet.END, 20)
                    set.applyTo(qnaLayout)
                    qnaDeleteCheckBox.isChecked = false

                    qnaDeleteCheckBox.setOnCheckedChangeListener { compoundButton, boolean ->
                        if (boolean)
                            qnaRemoveLayoutList.add(qnaLayout)
                        else
                            qnaRemoveLayoutList.remove(qnaLayout)
                    }
                }
            }

            homeWriteDeleteChip.setOnClickListener {

                showAlert(AlertType.TYPE1, null, homeWriteLL)


//                for (l in reviewRemoveLayoutList) {
//                    homeWriteLL.removeView(l)
//                }
//
//                for (l in qnaRemoveLayoutList) {
//                    homeWriteLL.removeView(l)
//                }
            }


            homeWriteEditCompleteButton.setOnClickListener {
                reviewRemoveLayoutList.clear()
                qnaRemoveLayoutList.clear()

                homeWriteTypeSelectChipGroup.visibility = View.VISIBLE
                homeWriteEditCompleteButton.visibility = View.INVISIBLE
                homeWriteDeleteChip.visibility = View.INVISIBLE
                homeWriteEditButton.visibility = View.VISIBLE
                homeWritePlusButton1.visibility = View.VISIBLE
                homeWritePlusButton2.visibility = View.VISIBLE

                editBooleanMap[progressName] = false

                val reviewLayoutList = reviewLayoutListMap[progressName]
                val qnaLayoutList = qnaLayoutListMap[progressName]

                for (reviewLayout in reviewLayoutList.orEmpty()) {
                    reviewLayout.getViewById(R.id.homeWriteReviewCheckBox).visibility = View.GONE
                    val homeWriteReviewMainCL = reviewLayout.findViewById<ConstraintLayout>(R.id.homeWriteReviewMainCL)
                    val set = ConstraintSet()
                    set.clone(reviewLayout)
                    set.connect(homeWriteReviewMainCL.id,ConstraintSet.START, ConstraintSet.PARENT_ID , ConstraintSet.START, 0 )
                    set.connect(homeWriteReviewMainCL.id,ConstraintSet.END, ConstraintSet.PARENT_ID , ConstraintSet.END, 0)
                    set.applyTo(reviewLayout)
                }

                for (qnaLayout in qnaLayoutList.orEmpty()) {
                    qnaLayout.getViewById(R.id.homeWriteQnaCheckBox).visibility = View.GONE
                    val homeWriteQnaMainCL = qnaLayout.findViewById<ConstraintLayout>(R.id.homeWriteQnaMainCL)
                    val set = ConstraintSet()
                    set.clone(qnaLayout)
                    set.connect(homeWriteQnaMainCL.id,ConstraintSet.START, ConstraintSet.PARENT_ID , ConstraintSet.START, 0 )
                    set.connect(homeWriteQnaMainCL.id,ConstraintSet.END, ConstraintSet.PARENT_ID , ConstraintSet.END, 0)
                    set.applyTo(qnaLayout)
                }
            }

            homeWriteContentLayout.id = View.generateViewId()
            homeWriteContentLayout.tag = progressName

            if (idx == 0)
                homeWriteContentLayout.visibility = View.VISIBLE
            else
                homeWriteContentLayout.visibility = View.INVISIBLE

            //dynamicLayoutList.add(homeWriteContentLayout)

            homeWriteTypeSelectChipGroup.tag = progressName
            //chipGroupList.add(homeWriteTypeSelectChipGroup)
            dynamicLayoutMap[progressName] = homeWriteContentLayout

            binding.homeWriteContentCL.addView(homeWriteContentLayout)
            idx += 1
        }


        for (homeDetailItem in homeDetailContents!!) {
            val progressType = homeDetailItem.progressType
            val progressName = homeDetailItem.progressName
            val itemType = homeDetailItem.itemType
            val totalReviewBody = homeDetailItem.totalReviewBody
            val questionBody = homeDetailItem.questionBody
            val answerBody = homeDetailItem.answerBody
            val progressIsPassing = homeDetailItem.progressIsPassing

            progressIsPassingMap[progressType] = progressIsPassing
            val dynamicHomeWriteCL = dynamicLayoutMap[progressName]

            val homeWriteNestedSV = dynamicHomeWriteCL!!.getViewById(R.id.homeWriteNestedSV)
            val homeWriteMainCL = homeWriteNestedSV.findViewById<ConstraintLayout>(R.id.homeWriteMainCL)
            val homeWriteLL = homeWriteNestedSV.findViewById<LinearLayout>(R.id.homeWriteLL)
            val homeWriteTypeSelectChipGroup = homeWriteMainCL.findViewById<ChipGroup>(R.id.homeWriteTypeSelectChipGroup)
            val homeWriteTypeSelect1 = homeWriteTypeSelectChipGroup.findViewById<Chip>(R.id.homeWriteTypeSelect1)
            val homeWriteTypeSelect2 = homeWriteTypeSelectChipGroup.findViewById<Chip>(R.id.homeWriteTypeSelect2)
            val homeWriteTypeSelect3 = homeWriteTypeSelectChipGroup.findViewById<Chip>(R.id.homeWriteTypeSelect3)

            when (progressIsPassing) {
                IsPassing.WAITING -> {
                    homeWriteTypeSelect1.isChecked = true
                    homeWriteTypeSelect1.chipStrokeWidth = 4f
                    homeWriteTypeSelect1.setChipStrokeColorResource(R.color.atracker_white)

                }
                IsPassing.FAIL -> {
                    homeWriteTypeSelect2.isChecked = true
                    homeWriteTypeSelect2.chipStrokeWidth = 4f
                    homeWriteTypeSelect2.setChipStrokeColorResource(R.color.atracker_white)
                }
                IsPassing.SUCCESS -> {
                    homeWriteTypeSelect3.isChecked = true
                    homeWriteTypeSelect3.chipStrokeWidth = 4f
                    homeWriteTypeSelect3.setChipStrokeColorResource(R.color.progress_color_7)

                }
            }

            if (itemType == ProgressItemBodyType.REVIEW) {
                val addLayout = this.layoutInflater.inflate(R.layout.home_write_review_layout, null) as ConstraintLayout // inflating view from xml
                val params : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT, // This will define text view width
                    ConstraintLayout.LayoutParams.WRAP_CONTENT // This will define text view height
                )
                params.setMargins(0,20,0,10)
                addLayout.layoutParams = params
                addLayout.id = View.generateViewId()
                homeWriteLL.addView(addLayout)

                val homeWriteReviewET = addLayout.findViewById<EditText>(R.id.homeWriteReviewET)
                homeWriteReviewET.setText(totalReviewBody)
                reviewLayoutListMap[progressName] = reviewLayoutListMap[progressName].orEmpty().plus(addLayout)
            } else {
                val addLayout = this.layoutInflater.inflate(R.layout.home_write_qna_layout, null) as ConstraintLayout // inflating view from xml
                val params : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT, // This will define text view width
                    ConstraintLayout.LayoutParams.WRAP_CONTENT // This will define text view height
                )
                params.setMargins(0,20,0,10)
                addLayout.layoutParams = params
                addLayout.id = View.generateViewId()
                homeWriteLL.addView(addLayout)

                val homeWriteQuestionTV = addLayout.findViewById<EditText>(R.id.homeWriteQuestionTV)
                homeWriteQuestionTV.setText(questionBody)
                val homeWriteAnswerET = addLayout.findViewById<EditText>(R.id.homeWriteAnswerET)
                homeWriteAnswerET.setText(answerBody)
                qnaLayoutListMap[progressName] = qnaLayoutListMap[progressName].orEmpty().plus(addLayout)

            }
        }
    }

    private fun setChip(chip1 : Chip, chip2 : Chip, chip3 : Chip, colorValue: Int){
        chip1.setOnClickListener {
            if (chip1.isChecked) {
                chip1.chipStrokeWidth = 4f
                chip1.setChipStrokeColorResource(colorValue)
                chip2.chipStrokeWidth = 0f
                chip3.chipStrokeWidth = 0f
            } else {
                chip1.chipStrokeWidth = 0f
            }
        }
    }


    private fun setPlusButton(button: TextView, layoutInt : Int, linearLayout: LinearLayout, progressName : String){
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

            if (layoutInt == R.layout.home_write_review_layout) {
                reviewLayoutListMap[progressName] = reviewLayoutListMap[progressName].orEmpty().plus(addLayout)

            } else if (layoutInt == R.layout.home_write_qna_layout) {
                qnaLayoutListMap[progressName] = qnaLayoutListMap[progressName].orEmpty().plus(addLayout)
            }
        }
    }



    private fun changeView(layoutTagName : String) {
        for(layout in dynamicLayoutMap.values){
            if (layoutTagName == layout.tag) {
                layout.visibility = View.VISIBLE
            } else {
                layout.visibility = View.INVISIBLE
            }
        }
    }


    fun showAlert(alertType: AlertType, tab : TabLayout.Tab?, deleteHomeWriteLL: LinearLayout? ){
        val alertDialogFragment = AlertDialogFragment.instance(
            object : AlertDialogListener {

                override fun onLeftClick() {
                    when (alertType) {
                        AlertType.TYPE3 -> {
                            binding.homeWriteTabLayout.getTabAt(previousTabPosition)!!.select()
                        }
                        AlertType.TYPE4 -> {

                        }
                    }
                }

                override fun onCenterClick() {

                }

                override fun onRightClick() {
                    when (alertType) {
                        AlertType.TYPE1 -> {
                            for (l in reviewRemoveLayoutList) {
                                deleteHomeWriteLL!!.removeView(l)
                            }
                            for (l in qnaRemoveLayoutList) {
                                deleteHomeWriteLL!!.removeView(l)
                            }

                            reviewRemoveLayoutList.clear()
                            qnaRemoveLayoutList.clear()
                        }
                        AlertType.TYPE3 -> {
                            previousTabPosition = tab!!.position
                            previousTabName = tab!!.tag.toString()
                            changeView(tab!!.tag.toString())
                        }
                        AlertType.TYPE4 -> {
                            findNavController().popBackStack()
                        }
                    }
                }
            },
            alertType,
            reviewRemoveLayoutList.size + qnaRemoveLayoutList.size

        )

        alertDialogFragment.show(childFragmentManager, AlertDialogFragment.TAG)
    }


}