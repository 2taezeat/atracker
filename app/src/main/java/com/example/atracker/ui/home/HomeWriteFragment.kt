package com.example.atracker.ui.home

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
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.atracker.R
import com.example.atracker.model.dto.DeletedContent
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


    private val freeLayoutListMap by lazy {
        mutableMapOf<String, List<ConstraintLayout>>()
    }

    private val qnaLayoutListMap by lazy {
        mutableMapOf<String, List<ConstraintLayout>>()
    }

    private val overAllLayoutListMap by lazy {
        mutableMapOf<String, List<ConstraintLayout>>()
    }

    private val freeRemoveLayoutListMap by lazy {
        mutableMapOf<String, ArrayList<ConstraintLayout>>()
    }

    private val qnaRemoveLayoutListMap by lazy {
        mutableMapOf<String, ArrayList<ConstraintLayout>>()
    }

    private val overAllRemoveLayoutListMap by lazy { // 종합 후기는 절대 삭제 될 수 없음. 고로 쓰이지 않음. // 근데 ,mvp 라면?
        mutableMapOf<String, ArrayList<ConstraintLayout>>()
    }

    private val chipLayoutListMap by lazy {
        mutableMapOf<String, ArrayList<Chip>>()
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeWriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeWriteTabLayout = binding.homeWriteTabLayout
        Log.d("args.progressIndex","${args.progressIndex}")

        addTabItem(args.progressIndex, container)

        binding.homeWriteBackButton.setOnClickListener { view ->
            val navController = view.findNavController()
            navController.popBackStack()
            parentActivity.mainBottomNavigationAppear()
        }


        binding.homeWriteBottomCL.setOnClickListener {
            showAlert(AlertType.TYPE4, homeWriteTabLayout.getTabAt(previousTabPosition), null)

            val homeProgressNameWrite = homeViewModel.homeProgressNameWrite.value!!

            for (progressName in homeProgressNameWrite) {
                Log.d("qwe_progressName", "${progressName}")
                Log.d("qwe_freeLayoutListMap", "${freeLayoutListMap[progressName]}" )
                Log.d("qwe_qnaLayoutListMap", "${qnaLayoutListMap[progressName]}" )
                Log.d("qwe_overAllLayoutListMap", "${overAllLayoutListMap[progressName]}" )
                Log.d("qwe_freeRemoveLayoutListMap", "${freeRemoveLayoutListMap[progressName]}" )
                Log.d("qwe_qnaRemoveLayoutListMap", "${qnaRemoveLayoutListMap[progressName]}" )

                val cl = chipLayoutListMap[progressName]!!
                Log.d("qwe_chip", "${cl[0].isChecked} , ${cl[1].isChecked}, ${cl[2].isChecked}" )


                Log.d("qwe_", "-------------------------------------------------------------" )



                homeViewModel.isPassingFun(progressName = progressName, inProgressIsChecked = cl[0].isChecked, failIsChecked = cl[1].isChecked, passIsChecked = cl[2].isChecked)
            }

        }

        binding.homeWriteCompanyTitle.text = homeViewModel.homeApplyIdContent.value!!.company_name



        binding.homeWriteTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.d("onTabSelected", "${previousTabPosition}, ${tab!!.position}")

                if (editBooleanMap[previousTabName] == true && previousTabPosition != tab!!.position) {
                    showAlert(AlertType.TYPE3, tab, null)

                } else {
                    val previousState = progressIsPassingMap[previousTabPosition]
                    val selectedMinusOneState = progressIsPassingMap[tab.position - 1]

                    if (previousTabPosition < tab.position && (previousState != IsPassing.PASS || selectedMinusOneState != IsPassing.PASS)) {
                        //showAlert(AlertType.TYPE2 , tab,null)
                        //binding.homeWriteTabLayout.getTabAt(previousTabPosition)!!.select()
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
            val homeWriteFreeLayout = this.layoutInflater.inflate(R.layout.home_write_free_layout, null) as ConstraintLayout // inflating view from xml
            val params : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, // This will define text view width
                ConstraintLayout.LayoutParams.WRAP_CONTENT // This will define text view height
            )
            params.setMargins(0,20,0,10)
            homeWriteFreeLayout.layoutParams = params
            homeWriteFreeLayout.id = View.generateViewId()

            binding.homeWriteLL.addView(homeWriteFreeLayout)
        }


        return root

    }

    private fun addTabItem(progressIndex: Int, container: ViewGroup?) {
        val homeWriteProgressSelected = homeViewModel.homeProgressNameWrite.value

        //val homeDetailContents = homeViewModel.homeDetailContents.value!![progressIndex]
        val homeDetailContents = homeViewModel.homeDetailContentForDisplay.value
        var idx = 0


        //dynamicLayoutList = arrayListOf<ConstraintLayout>()
        previousTabName = homeWriteProgressSelected!![0]
        dynamicLayoutMap = mutableMapOf()
        progressIsPassingMap = mutableMapOf()

        for (progressName in homeWriteProgressSelected) {
            homeWriteTabLayout.addTab(homeWriteTabLayout.newTab().setText(progressName).setId(View.generateViewId()).setTag(progressName))


            homeWriteContentLayout = this.layoutInflater.inflate(R.layout.home_write_content_layout, null) as ConstraintLayout // inflating view from xml
            val params : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, // This will define text view width
                0 // This will define text view height
            )

            editBooleanMap[progressName] = false

            freeRemoveLayoutListMap[progressName] = arrayListOf()
            qnaRemoveLayoutListMap[progressName] = arrayListOf()
            chipLayoutListMap[progressName] = arrayListOf()

            val contentIdForTag = homeDetailContents!!.find {it.progressName == progressName}!!.contentId
            Log.d("contentIdForTag", "${contentIdForTag}")


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

            val homeWriteReviewWholeCL = homeWriteMainCL.findViewById<ConstraintLayout>(R.id.homeWriteReviewWholeCL)
            val homeWriteReviewMainCL = homeWriteMainCL.findViewById<ConstraintLayout>(R.id.homeWriteReviewMainCL)
            val homeWriteReviewCheckBox = homeWriteReviewWholeCL.findViewById<CheckBox>(R.id.homeWriteReviewCheckBox)
            val homeWriteReviewET = homeWriteReviewWholeCL.findViewById<EditText>(R.id.homeWriteReviewET)


            homeWriteTypeSelect1.tag = progressName
            homeWriteTypeSelect2.tag = progressName
            homeWriteTypeSelect3.tag = progressName


            chipLayoutListMap[progressName]!!.add(homeWriteTypeSelect1)
            chipLayoutListMap[progressName]!!.add(homeWriteTypeSelect2)
            chipLayoutListMap[progressName]!!.add(homeWriteTypeSelect3)



            homeWriteContentLayout.layoutParams = params

//            setChip(chip1 = homeWriteTypeSelect1, chip2 = homeWriteTypeSelect2, chip3 = homeWriteTypeSelect3, colorValue = R.color.atracker_white)
//            setChip(chip1 = homeWriteTypeSelect2, chip2 = homeWriteTypeSelect1, chip3 = homeWriteTypeSelect3, colorValue = R.color.atracker_white)
//            setChip(chip1 = homeWriteTypeSelect3, chip2 = homeWriteTypeSelect2, chip3 = homeWriteTypeSelect1, colorValue = R.color.progress_color_7)

            setChiptmp(chip1 = homeWriteTypeSelect3, chip2 = homeWriteTypeSelect2, chip3 = homeWriteTypeSelect1, colorValueWhite = R.color.atracker_white, colorValueGreen = R.color.progress_color_7 )


            setPlusButton(homeWritePlusButton1, R.layout.home_write_qna_layout, homeWriteLL, progressName)
            setPlusButton(homeWritePlusButton2, R.layout.home_write_free_layout, homeWriteLL, progressName)


            homeWriteEditButton.setOnClickListener {
                homeWriteTypeSelectChipGroup.visibility = View.INVISIBLE
                homeWriteEditButton.visibility = View.INVISIBLE
                homeWriteEditCompleteButton.visibility = View.VISIBLE
                homeWriteDeleteChip.visibility = View.VISIBLE
                homeWritePlusButton1.visibility = View.INVISIBLE
                homeWritePlusButton2.visibility = View.INVISIBLE

                editBooleanMap[progressName] = true

                val freeLayoutList = freeLayoutListMap[progressName]
                val qnaLayoutList = qnaLayoutListMap[progressName]
                val overAllLayoutList = overAllLayoutListMap[progressName]

//                homeWriteReviewCheckBox.visibility = View.VISIBLE
//                val set = ConstraintSet()
//                set.clone(homeWriteReviewWholeCL)
//                set.connect(homeWriteReviewMainCL.id,ConstraintSet.START, homeWriteReviewCheckBox.id , ConstraintSet.END, 20)
//                set.applyTo(homeWriteReviewWholeCL)


                for (freeLayout in freeLayoutList.orEmpty()) {
                    freeLayout.getViewById(R.id.homeWriteFreeCheckBox).visibility = View.VISIBLE
                    val homeWriteFreeMainCL = freeLayout.findViewById<ConstraintLayout>(R.id.homeWriteFreeMainCL)
                    val freeDeleteCheckBox = freeLayout.findViewById<CheckBox>(R.id.homeWriteFreeCheckBox)
                    val set = ConstraintSet()
                    set.clone(freeLayout)
                    set.connect(homeWriteFreeMainCL.id,ConstraintSet.START, freeDeleteCheckBox.id , ConstraintSet.END, 20)
                    set.applyTo(freeLayout)
                    freeDeleteCheckBox.isChecked = false

                    freeLayout.tag = contentIdForTag

                    freeDeleteCheckBox.setOnCheckedChangeListener { compoundButton, boolean ->
                        if (boolean)
                            freeRemoveLayoutListMap[progressName]!!.add(freeLayout)
                        else
                            freeRemoveLayoutListMap[progressName]!!.remove(freeLayout)
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

                    qnaLayout.tag = contentIdForTag

                    qnaDeleteCheckBox.setOnCheckedChangeListener { compoundButton, boolean ->
                        if (boolean)
                            qnaRemoveLayoutListMap[progressName]!!.add(qnaLayout)
                        else
                            qnaRemoveLayoutListMap[progressName]!!.remove(qnaLayout)
                    }
                }
            }

            homeWriteDeleteChip.setOnClickListener {
                showAlert(AlertType.TYPE1, homeWriteTabLayout.getTabAt(previousTabPosition), homeWriteLL)
            }


            homeWriteEditCompleteButton.setOnClickListener {
                freeRemoveLayoutListMap[progressName]!!.clear()
                qnaRemoveLayoutListMap[progressName]!!.clear()

                homeWriteTypeSelectChipGroup.visibility = View.VISIBLE
                homeWriteEditCompleteButton.visibility = View.INVISIBLE
                homeWriteDeleteChip.visibility = View.INVISIBLE
                homeWriteEditButton.visibility = View.VISIBLE
                homeWritePlusButton1.visibility = View.VISIBLE
                homeWritePlusButton2.visibility = View.VISIBLE

                editBooleanMap[progressName] = false

                val freeLayoutList = freeLayoutListMap[progressName]
                val qnaLayoutList = qnaLayoutListMap[progressName]
                val overAllLayoutList = overAllLayoutListMap[progressName]

                for (freeLayout in freeLayoutList.orEmpty()) {
                    freeLayout.getViewById(R.id.homeWriteFreeCheckBox).visibility = View.GONE
                    val homeWriteFreeMainCL = freeLayout.findViewById<ConstraintLayout>(R.id.homeWriteFreeMainCL)
                    val set = ConstraintSet()
                    set.clone(freeLayout)
                    set.connect(homeWriteFreeMainCL.id,ConstraintSet.START, ConstraintSet.PARENT_ID , ConstraintSet.START, 0 )
                    set.connect(homeWriteFreeMainCL.id,ConstraintSet.END, ConstraintSet.PARENT_ID , ConstraintSet.END, 0)
                    set.applyTo(freeLayout)
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
            val freeTitle =  homeDetailItem.freeTitle
            val freeBody = homeDetailItem.freeBody
            val totalReviewBody = homeDetailItem.totalReviewBody
            val questionBody = homeDetailItem.questionBody
            val answerBody = homeDetailItem.answerBody
            val qnaReviewBody = homeDetailItem.qnaReviewBody
            val progressIsPassing = homeDetailItem.progressIsPassing
            /////////////////////////////////////////////////////////////////////
            val contentOrder = homeDetailItem.contentOrder
            val contentId = homeDetailItem.contentId
            val stageRealId = homeDetailItem.stageRealId
            ////////////////////////////////////////////////////////////////////

            progressIsPassingMap[progressType] = progressIsPassing
            val dynamicHomeWriteCL = dynamicLayoutMap[progressName]

            val homeWriteNestedSV = dynamicHomeWriteCL!!.getViewById(R.id.homeWriteNestedSV)
            val homeWriteMainCL = homeWriteNestedSV.findViewById<ConstraintLayout>(R.id.homeWriteMainCL)


            val homeWriteReviewWholeCL = homeWriteMainCL.findViewById<ConstraintLayout>(R.id.homeWriteReviewWholeCL)
            val homeWriteReviewMainCL = homeWriteMainCL.findViewById<ConstraintLayout>(R.id.homeWriteReviewMainCL)
            val homeWriteReviewCheckBox = homeWriteReviewWholeCL.findViewById<CheckBox>(R.id.homeWriteReviewCheckBox)
            val homeWriteReviewET = homeWriteReviewWholeCL.findViewById<EditText>(R.id.homeWriteReviewET)

            val homeWriteLL = homeWriteNestedSV.findViewById<LinearLayout>(R.id.homeWriteLL)
            val homeWriteTypeSelectChipGroup = homeWriteMainCL.findViewById<ChipGroup>(R.id.homeWriteTypeSelectChipGroup)
            val homeWriteTypeSelect1 = homeWriteTypeSelectChipGroup.findViewById<Chip>(R.id.homeWriteTypeSelect1)
            val homeWriteTypeSelect2 = homeWriteTypeSelectChipGroup.findViewById<Chip>(R.id.homeWriteTypeSelect2)
            val homeWriteTypeSelect3 = homeWriteTypeSelectChipGroup.findViewById<Chip>(R.id.homeWriteTypeSelect3)


            when (progressIsPassing) {
                IsPassing.NOT_STARTED -> {
                    homeWriteTypeSelect1.isChecked = true
                    homeWriteTypeSelect1.chipStrokeWidth = 4f
                    homeWriteTypeSelect1.setChipStrokeColorResource(R.color.atracker_white)
                }
                IsPassing.IN_PROGRESS -> {
                    homeWriteTypeSelect1.isChecked = true
                    homeWriteTypeSelect1.chipStrokeWidth = 4f
                    homeWriteTypeSelect1.setChipStrokeColorResource(R.color.atracker_white)
                }
                IsPassing.FAIL -> {
                    homeWriteTypeSelect2.isChecked = true
                    homeWriteTypeSelect2.chipStrokeWidth = 4f
                    homeWriteTypeSelect2.setChipStrokeColorResource(R.color.atracker_white)
                }
                IsPassing.PASS -> {
                    homeWriteTypeSelect3.isChecked = true
                    homeWriteTypeSelect3.chipStrokeWidth = 4f
                    homeWriteTypeSelect3.setChipStrokeColorResource(R.color.progress_color_7)
                }
            }



            when (itemType) {
                ProgressItemBodyType.FREE_FORM -> { // free
                    val addLayout = this.layoutInflater.inflate(R.layout.home_write_free_layout, null) as ConstraintLayout // inflating view from xml
                    val params : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT, // This will define text view width
                        ConstraintLayout.LayoutParams.WRAP_CONTENT // This will define text view height
                    )
                    params.setMargins(0,20,0,10)
                    addLayout.layoutParams = params
                    addLayout.id = View.generateViewId()
                    homeWriteLL.addView(addLayout)

                    val homeWriteFreeET = addLayout.findViewById<EditText>(R.id.homeWriteFreeET)
                    homeWriteFreeET.setText(freeBody)
                    val homeWriteFreeTitle = addLayout.findViewById<EditText>(R.id.homeWriteFreeTitle)
                    homeWriteFreeTitle.setText(freeTitle)


                    freeLayoutListMap[progressName] = freeLayoutListMap[progressName].orEmpty().plus(addLayout)
                }
                ProgressItemBodyType.QNA -> { // qna
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
                    val homeWriteQnaOfReviewET = addLayout.findViewById<EditText>(R.id.homeWriteQnaOfReviewET)
                    homeWriteQnaOfReviewET.setText(qnaReviewBody)

                    qnaLayoutListMap[progressName] = qnaLayoutListMap[progressName].orEmpty().plus(addLayout)

                }
                ProgressItemBodyType.OVERALL -> {
                    homeWriteReviewET.setText(totalReviewBody)

                }
                ProgressItemBodyType.NOT_DEFINED -> {

                }
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

                //homeViewModel.isPassingFun()
            } else {
                chip1.chipStrokeWidth = 0f
            }
        }
    }

    private fun setChiptmp(chip1 : Chip, chip2 : Chip, chip3 : Chip, colorValueWhite: Int, colorValueGreen : Int){
        chip1.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                chip1.chipStrokeWidth = 4f
                chip1.setChipStrokeColorResource(colorValueWhite)
                chip2.chipStrokeWidth = 0f
                chip3.chipStrokeWidth = 0f
            } else {
                chip1.chipStrokeWidth = 0f
            }
        }

        chip2.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                chip2.chipStrokeWidth = 4f
                chip2.setChipStrokeColorResource(colorValueWhite)
                chip3.chipStrokeWidth = 0f
                chip1.chipStrokeWidth = 0f
            } else {
                chip2.chipStrokeWidth = 0f
            }
        }

        chip3.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                chip3.chipStrokeWidth = 4f
                chip3.setChipStrokeColorResource(colorValueGreen)
                chip2.chipStrokeWidth = 0f
                chip1.chipStrokeWidth = 0f
            } else {
                chip3.chipStrokeWidth = 0f
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

            addLayout.tag = 1234

            linearLayout.addView(addLayout)

            if (layoutInt == R.layout.home_write_free_layout) {
                freeLayoutListMap[progressName] = freeLayoutListMap[progressName].orEmpty().plus(addLayout)
            } else if (layoutInt == R.layout.home_write_qna_layout) {
                qnaLayoutListMap[progressName] = qnaLayoutListMap[progressName].orEmpty().plus(addLayout)
            } else if (layoutInt == R.layout.home_write_review_layout) { // overAll
                overAllLayoutListMap[progressName] = overAllLayoutListMap[progressName].orEmpty().plus(addLayout)
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

                    }
                }

                override fun onCenterClick() {
                    when (alertType) {
                        AlertType.TYPE4 -> {
                            findNavController().popBackStack()
                            parentActivity.mainBottomNavigationAppear()
                        }
                    }


                }

                override fun onRightClick() {
                    when (alertType) {
                        AlertType.TYPE1 -> {
                            val progressName = tab!!.tag.toString()

                            for (l in freeRemoveLayoutListMap[progressName].orEmpty()) {
                                deleteHomeWriteLL!!.removeView(l)
                            }
                            for (l in qnaRemoveLayoutListMap[progressName].orEmpty()) {
                                deleteHomeWriteLL!!.removeView(l)
                            }



                            val frll = freeRemoveLayoutListMap[progressName]!!
                            val qrll = qnaRemoveLayoutListMap[progressName]!!


                            for (cf in frll) { // stage for 문
                                Log.d("qwe_frll", "${frll}, ${cf.tag.toString().toInt()}")
                                homeViewModel.deleteFun(progressName, DeletedContent(cf.tag.toString().toInt()))
                            }

                            for (cq in frll) { // stage for 문
                                Log.d("qwe_qrll", "${frll}, ${cq.tag.toString().toInt()}")
                                homeViewModel.deleteFun(progressName, DeletedContent(cq.tag.toString().toInt()))
                            }

                            freeRemoveLayoutListMap[progressName]!!.clear()
                            qnaRemoveLayoutListMap[progressName]!!.clear()

                        }
                        AlertType.TYPE3 -> {
                            previousTabPosition = tab!!.position
                            previousTabName = tab!!.tag.toString()
                            changeView(tab!!.tag.toString())
                        }

                    }
                }
            },
            alertType,
            freeRemoveLayoutListMap[tab!!.tag.toString()].orEmpty().size + qnaRemoveLayoutListMap[tab!!.tag.toString()].orEmpty().size

        )

        alertDialogFragment.show(childFragmentManager, AlertDialogFragment.TAG)
    }


}