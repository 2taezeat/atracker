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
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.atracker.R
import com.example.atracker.model.dto.*
import com.example.atracker.ui.AlertDialogFragment
import com.example.atracker.ui.AlertDialogListener
import com.example.atracker.utils.AlertApiObject
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

    private val overAllRemoveLayoutListMap by lazy { // 종합 후기는 절대 삭제 될 수 없음. 고로 쓰이지 않음. // 근데 ,mvp 에는 추가함, 안드로이드
        mutableMapOf<String, ArrayList<ConstraintLayout>>()
    }

    private val chipLayoutListMap by lazy {
        mutableMapOf<String, ArrayList<Chip>>()
    }


    fun parsedQna(q : String, a : String, f : String) : String {
        var formedString = "{"
        formedString += '\"'
        formedString += 'q'
        formedString += '\"'

        formedString += ':'

        formedString += '\"'
        formedString += q
        formedString += '\"'

        formedString += ','

        formedString += '\"'
        formedString += 'a'
        formedString += '\"'

        formedString += ':'

        formedString += '\"'
        formedString += a
        formedString += '\"'

        formedString += ','

        formedString += '\"'
        formedString += 'f'
        formedString += '\"'

        formedString += ':'

        formedString += '\"'
        formedString += f
        formedString += '\"'

        formedString += "}"
        return formedString
    }

    fun parsedFree(t: String, b: String) : String {
        var formedString = "{"
        formedString += '\"'
        formedString += 't'
        formedString += '\"'

        formedString += ':'

        formedString += '\"'
        formedString += t
        formedString += '\"'

        formedString += ','

        formedString += '\"'
        formedString += 'b'
        formedString += '\"'

        formedString += ':'

        formedString += '\"'
        formedString += b
        formedString += '\"'

        formedString += "}"
        return formedString
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

            showAlert(AlertType.TYPE18, homeWriteTabLayout.getTabAt(previousTabPosition), null)
        }

        binding.homeWriteBottomCL.setOnClickListener {
            val homeProgressNameWrite = homeViewModel.homeProgressNameWrite.value!!

            for (progressName in homeProgressNameWrite) {
                Log.d("qwe_freeLayoutListMap", "${freeLayoutListMap[progressName]}" )
                Log.d("qwe_qnaLayoutListMap", "${qnaLayoutListMap[progressName]}" )
                Log.d("qwe_overAllLayoutListMap", "${overAllLayoutListMap[progressName]}" )

                val fm = freeLayoutListMap[progressName]?.let {it}
                val qm = qnaLayoutListMap[progressName]?.let {it}
                val om = overAllLayoutListMap[progressName]?.let {it}

                om?.let { it
                    for (oll in it) {
                        val homeWriteReviewET = oll.findViewById<EditText>(R.id.homeWriteReviewET)
                        val overAllReviewBodyString = homeWriteReviewET.text.toString()
                        Log.d("qwe_final_oll", "${overAllReviewBodyString}, ${oll.tag}")

                        val contentId = oll.tag.toString().toInt()
                        if (contentId == 123456789) { // 새로 추가되는 것
                            val newContent = NewContent(content = overAllReviewBodyString, content_type = "OVERALL", order = oll.id)
                            homeViewModel.newContentUpdate(progressName = progressName, newContent = newContent)

                        } else { // 서버에 있는 것을 클라에서 업데이트
                            val updatedContent = UpdatedContent(content = overAllReviewBodyString, id = contentId, order = oll.id)
                            homeViewModel.updateContentsUpdate(progressName = progressName, updatedContent = updatedContent)
                        }
                    }
                }

                fm?.let { it
                    for (fll in it) {
                        val freeTitleET = fll.findViewById<EditText>(R.id.homeWriteFreeTitle)
                        val freeBodyET = fll.findViewById<EditText>(R.id.homeWriteFreeET)
                        val freeTitleString = freeTitleET.text.toString()
                        val freeBodyString = freeBodyET.text.toString()

                        val freeFinalParsedString = parsedFree(freeTitleString, freeBodyString)
                        Log.d("qwe_final_fll", "${freeFinalParsedString}, ${fll.tag}")

                        val contentId = fll.tag.toString().toInt()
                        if (contentId == 123456789) { // 새로 추가되는 것
                            val newContent = NewContent(content = freeFinalParsedString, content_type = "FREE_FORM", order = fll.id)
                            homeViewModel.newContentUpdate(progressName = progressName, newContent = newContent)

                        } else { // 서버에 있는 것을 클라에서 업데이트
                            val updatedContent = UpdatedContent(content = freeFinalParsedString, id = contentId, order = fll.id)
                            homeViewModel.updateContentsUpdate(progressName = progressName, updatedContent = updatedContent)
                        }
                    }
                }

                qm?.let { it
                    for (qll in it) {
                        val qnaQuestionET = qll.findViewById<EditText>(R.id.homeWriteQuestionTV)
                        val qnaAnswerET = qll.findViewById<EditText>(R.id.homeWriteAnswerET)
                        val qnaReviewET = qll.findViewById<EditText>(R.id.homeWriteQnaOfReviewET)

                        val qnaQuestionString = qnaQuestionET.text.toString()
                        val qnaAnswerString = qnaAnswerET.text.toString()
                        val qnaReviewString = qnaReviewET.text.toString() // feedback, f

                        val qnaFinalParsedString = parsedQna(qnaQuestionString, qnaAnswerString, qnaReviewString)

                        Log.d("qwe_final_qll", "${qnaFinalParsedString}, ${qll.tag}")
                        val contentId = qll.tag.toString().toInt()
                        if (contentId == 123456789) { // 새로 추가되는 것
                            val newContent = NewContent(content = qnaFinalParsedString, content_type = "QNA", order = qll.id)
                            homeViewModel.newContentUpdate(progressName = progressName, newContent = newContent)

                        } else { // 서버에 있는 것을 클라에서 업데이트
                            val updatedContent = UpdatedContent(content = qnaFinalParsedString, id = contentId, order = qll.id)
                            homeViewModel.updateContentsUpdate(progressName = progressName, updatedContent = updatedContent)
                        }
                    }
                }

                val cl = chipLayoutListMap[progressName]!!
                homeViewModel.isPassingUpdate(progressName = progressName, inProgressIsChecked = cl[0].isChecked, failIsChecked = cl[1].isChecked, passIsChecked = cl[2].isChecked)
                Log.d("qwe_final_arrayListStageProgresse", "${homeViewModel.arrayListStageProgresse.value}")
                Log.d("qwe_", "-------------------------------------------------------------" )

            }
            homeViewModel.setStageProgressRequest()
            homeViewModel.updateStageProgress()


            homeViewModel.stageProgressesPutFail.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let { boolean ->
                    Log.d("stageProgressesPutFail", "${boolean}")
                    if (boolean) { // 실패
                        parentActivity.showAlertInstance(AlertApiObject.alertDialogFragment)
                    } else { // 성공
                        homeViewModel.clearStageProgress()
                        homeViewModel.getApplyDisplay(applyIds = null, includeContent = false)
                        homeViewModel.getMyApplyPfratio()
                        showAlert(AlertType.TYPE4, homeWriteTabLayout.getTabAt(previousTabPosition), null)
                    }
                }
            })
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
                        showAlert(AlertType.TYPE2 , tab,null)
                        binding.homeWriteTabLayout.getTabAt(previousTabPosition)!!.select()
                        // **************************************************************************
//                        previousTabPosition = tab.position
//                        previousTabName = tab.tag.toString()
//                        changeView(tab.tag.toString())
                        // **************************************************************************

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

        return root

    }

    private fun addTabItem(progressIndex: Int, container: ViewGroup?) {
        val homeWriteProgressSelected = homeViewModel.homeProgressNameWrite.value
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
            overAllRemoveLayoutListMap[progressName] = arrayListOf()

            chipLayoutListMap[progressName] = arrayListOf()

//            val contentIdForTag = homeDetailContents!!.find {it.progressName == progressName}!!.contentId
//            val contentType = homeDetailContents!!.find {it.progressName == progressName}!!.itemType
//
//            val contentIdForTagList = homeDetailContents!!.filter {it.progressName == progressName}
//            val contentTypeList = homeDetailContents!!.filter {it.progressName == progressName}
//
//            val homeDetail = homeDetailContents.filter {it.progressName == progressName}
//            //val homeDetailItemBodyType = homeDetail!!.itemType
//
//            Log.d("contentIdForTag", "${contentIdForTag}, ${contentType}")
//            Log.d("contentIdForTagList", "${contentIdForTagList} ")
//            Log.d("contentIdForTag--", "=====================================================")


            val homeWriteNestedSV = homeWriteContentLayout.getViewById(R.id.homeWriteNestedSV)
            val homeWriteMainCL = homeWriteNestedSV.findViewById<ConstraintLayout>(R.id.homeWriteMainCL)
            val homeWriteLL = homeWriteNestedSV.findViewById<LinearLayout>(R.id.homeWriteLL)
            val homeWritePlusButton1 = homeWriteMainCL.findViewById<TextView>(R.id.homeWritePlusButton1)
            val homeWritePlusButton2 = homeWriteMainCL.findViewById<TextView>(R.id.homeWritePlusButton2)
            val homeWritePlusButton3 = homeWriteMainCL.findViewById<TextView>(R.id.homeWritePlusButton3)

            val homeWriteTypeSelectChipGroup = homeWriteMainCL.findViewById<ChipGroup>(R.id.homeWriteTypeSelectChipGroup)
            val homeWriteEditButton = homeWriteMainCL.findViewById<TextView>(R.id.homeWriteEditButton)
            val homeWriteEditCompleteButton = homeWriteMainCL.findViewById<TextView>(R.id.homeWriteEditCompleteButton)
            val homeWriteDeleteChip = homeWriteMainCL.findViewById<Chip>(R.id.homeWriteDeleteChip)
            val homeWriteTypeSelect1 = homeWriteTypeSelectChipGroup.findViewById<Chip>(R.id.homeWriteTypeSelect1)
            val homeWriteTypeSelect2 = homeWriteTypeSelectChipGroup.findViewById<Chip>(R.id.homeWriteTypeSelect2)
            val homeWriteTypeSelect3 = homeWriteTypeSelectChipGroup.findViewById<Chip>(R.id.homeWriteTypeSelect3)

//            val homeWriteReviewWholeCL = homeWriteMainCL.findViewById<ConstraintLayout>(R.id.homeWriteReviewWholeCL)
//            val homeWriteReviewMainCL = homeWriteMainCL.findViewById<ConstraintLayout>(R.id.homeWriteReviewMainCL)
//            val homeWriteReviewCheckBox = homeWriteReviewWholeCL.findViewById<CheckBox>(R.id.homeWriteReviewCheckBox)
//            val homeWriteReviewET = homeWriteReviewWholeCL.findViewById<EditText>(R.id.homeWriteReviewET)

            homeWriteTypeSelect1.tag = progressName
            homeWriteTypeSelect2.tag = progressName
            homeWriteTypeSelect3.tag = progressName

            chipLayoutListMap[progressName]!!.add(homeWriteTypeSelect1)
            chipLayoutListMap[progressName]!!.add(homeWriteTypeSelect2)
            chipLayoutListMap[progressName]!!.add(homeWriteTypeSelect3)

            homeWriteContentLayout.layoutParams = params

            setChipListener(chip1 = homeWriteTypeSelect1, chip2 = homeWriteTypeSelect2, chip3 = homeWriteTypeSelect3, colorValueWhite = R.color.atracker_white, colorValueGreen = R.color.progress_color_7 )

            setPlusButton(homeWritePlusButton1, R.layout.home_write_qna_layout, homeWriteLL, progressName)
            setPlusButton(homeWritePlusButton2, R.layout.home_write_free_layout, homeWriteLL, progressName)
            setPlusButton(homeWritePlusButton3, R.layout.home_write_review_layout, homeWriteLL, progressName)

            homeWriteEditButton.setOnClickListener { // '삭제' 버튼 클릭시
                homeWriteTypeSelectChipGroup.visibility = View.INVISIBLE
                homeWriteEditButton.visibility = View.INVISIBLE
                homeWriteEditCompleteButton.visibility = View.VISIBLE
                homeWriteDeleteChip.visibility = View.VISIBLE
                homeWritePlusButton1.visibility = View.INVISIBLE
                homeWritePlusButton2.visibility = View.INVISIBLE
                homeWritePlusButton3.visibility = View.INVISIBLE

                saveViewDisappear()

                editBooleanMap[progressName] = true

                val freeLayoutList = freeLayoutListMap[progressName]
                val qnaLayoutList = qnaLayoutListMap[progressName]
                val overAllLayoutList = overAllLayoutListMap[progressName]

                for (freeLayout in freeLayoutList.orEmpty()) {
                    freeLayout.getViewById(R.id.homeWriteFreeCheckBox).visibility = View.VISIBLE
                    val homeWriteFreeMainCL = freeLayout.findViewById<ConstraintLayout>(R.id.homeWriteFreeMainCL)
                    val freeDeleteCheckBox = freeLayout.findViewById<CheckBox>(R.id.homeWriteFreeCheckBox)
                    val set = ConstraintSet()
                    set.clone(freeLayout)
                    set.connect(homeWriteFreeMainCL.id,ConstraintSet.START, freeDeleteCheckBox.id , ConstraintSet.END, 20)
                    set.applyTo(freeLayout)
                    freeDeleteCheckBox.isChecked = false

//                    if (contentType == ProgressItemBodyType.NOT_DEFINED) {
//                        freeLayout.tag = 123456789
//                    } else {
//                        freeLayout.tag = contentIdForTag
//                    }

                    freeDeleteCheckBox.setOnCheckedChangeListener { compoundButton, boolean ->
                        if (boolean) {
                            freeRemoveLayoutListMap[progressName]!!.add(freeLayout)
                            freeLayoutListMap[progressName] = freeLayoutListMap[progressName].orEmpty().minus(freeLayout)
                        } else {
                            freeRemoveLayoutListMap[progressName]!!.remove(freeLayout)
                            freeLayoutListMap[progressName] = freeLayoutListMap[progressName].orEmpty().plus(freeLayout)
                        }

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

//                    if (contentType == ProgressItemBodyType.NOT_DEFINED) {
//                        qnaLayout.tag = 123456789
//                    } else {
//                        qnaLayout.tag = contentIdForTag
//                    }

                    qnaDeleteCheckBox.setOnCheckedChangeListener { compoundButton, boolean ->
                        if (boolean) {
                            qnaRemoveLayoutListMap[progressName]!!.add(qnaLayout)
                            qnaLayoutListMap[progressName] = qnaLayoutListMap[progressName].orEmpty().minus(qnaLayout)
                        } else {
                            qnaRemoveLayoutListMap[progressName]!!.remove(qnaLayout)
                            qnaLayoutListMap[progressName] = qnaLayoutListMap[progressName].orEmpty().plus(qnaLayout)
                        }
                    }
                }
                for (overAllLayout in overAllLayoutList.orEmpty()) {
                    overAllLayout.getViewById(R.id.homeWriteReviewCheckBox).visibility = View.VISIBLE
                    val homeWriteOverAllMainCL = overAllLayout.findViewById<ConstraintLayout>(R.id.homeWriteReviewMainCL)
                    val overAllDeleteCheckBox = overAllLayout.findViewById<CheckBox>(R.id.homeWriteReviewCheckBox)
                    val set = ConstraintSet()
                    set.clone(overAllLayout)
                    set.connect(homeWriteOverAllMainCL.id,ConstraintSet.START, overAllDeleteCheckBox.id , ConstraintSet.END, 20)
                    set.applyTo(overAllLayout)
                    overAllDeleteCheckBox.isChecked = false

//                    if (contentType == ProgressItemBodyType.NOT_DEFINED) {
//                        overAllLayout.tag = 123456789
//                    } else {
//                        overAllLayout.tag = contentIdForTag
//                    }

                    overAllDeleteCheckBox.setOnCheckedChangeListener { compoundButton, boolean ->
                        if (boolean) {
                            overAllRemoveLayoutListMap[progressName]!!.add(overAllLayout)
                            overAllLayoutListMap[progressName] = overAllLayoutListMap[progressName].orEmpty().minus(overAllLayout)
                        } else {
                            overAllRemoveLayoutListMap[progressName]!!.remove(overAllLayout)
                            overAllLayoutListMap[progressName] = overAllLayoutListMap[progressName].orEmpty().plus(overAllLayout)
                        }
                    }
                }
            }

            homeWriteDeleteChip.setOnClickListener {
                showAlert(AlertType.TYPE1, homeWriteTabLayout.getTabAt(previousTabPosition), homeWriteLL)
            }


            homeWriteEditCompleteButton.setOnClickListener {
                freeRemoveLayoutListMap[progressName]!!.clear()
                qnaRemoveLayoutListMap[progressName]!!.clear()
                overAllRemoveLayoutListMap[progressName]!!.clear()

                homeWriteTypeSelectChipGroup.visibility = View.VISIBLE
                homeWriteEditCompleteButton.visibility = View.INVISIBLE
                homeWriteDeleteChip.visibility = View.INVISIBLE
                homeWriteEditButton.visibility = View.VISIBLE
                homeWritePlusButton1.visibility = View.VISIBLE
                homeWritePlusButton2.visibility = View.VISIBLE
                homeWritePlusButton3.visibility = View.VISIBLE

                saveViewAppear()

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

                for (overAllLayout in overAllLayoutList.orEmpty()) {
                    overAllLayout.getViewById(R.id.homeWriteReviewCheckBox).visibility = View.GONE
                    val homeWriteOverAllMainCL = overAllLayout.findViewById<ConstraintLayout>(R.id.homeWriteReviewMainCL)
                    val set = ConstraintSet()
                    set.clone(overAllLayout)
                    set.connect(homeWriteOverAllMainCL.id,ConstraintSet.START, ConstraintSet.PARENT_ID , ConstraintSet.START, 0 )
                    set.connect(homeWriteOverAllMainCL.id,ConstraintSet.END, ConstraintSet.PARENT_ID , ConstraintSet.END, 0)
                    set.applyTo(overAllLayout)
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
            val totalReviewBody = homeDetailItem.totalReviewBody // overAll, 종합 후기
            val questionBody = homeDetailItem.questionBody
            val answerBody = homeDetailItem.answerBody
            val qnaReviewBody = homeDetailItem.qnaReviewBody
            val progressIsPassing = homeDetailItem.progressIsPassing
            /////////////////////////////////////////////////////////////////////
            val contentOrder = homeDetailItem.contentOrder
            val contentId = homeDetailItem.contentId
            val stageRealId = homeDetailItem.stageRealId
            ////////////////////////////////////////////////////////////////////

            Log.d("qwe_contentId_forfor", "${itemType},${contentId}")

            progressIsPassingMap[progressType] = progressIsPassing
            val dynamicHomeWriteCL = dynamicLayoutMap[progressName]

            val homeWriteNestedSV = dynamicHomeWriteCL!!.getViewById(R.id.homeWriteNestedSV)
            val homeWriteMainCL = homeWriteNestedSV.findViewById<ConstraintLayout>(R.id.homeWriteMainCL)

//            val homeWriteReviewWholeCL = homeWriteMainCL.findViewById<ConstraintLayout>(R.id.homeWriteReviewWholeCL)
//            val homeWriteReviewMainCL = homeWriteMainCL.findViewById<ConstraintLayout>(R.id.homeWriteReviewMainCL)
//            val homeWriteReviewCheckBox = homeWriteReviewWholeCL.findViewById<CheckBox>(R.id.homeWriteReviewCheckBox)
//            val homeWriteReviewET = homeWriteReviewWholeCL.findViewById<EditText>(R.id.homeWriteReviewET)

            val homeWriteLL = homeWriteNestedSV.findViewById<LinearLayout>(R.id.homeWriteLL)
            val homeWriteTypeSelectChipGroup = homeWriteMainCL.findViewById<ChipGroup>(R.id.homeWriteTypeSelectChipGroup)
            val homeWriteTypeSelect1 = homeWriteTypeSelectChipGroup.findViewById<Chip>(R.id.homeWriteTypeSelect1)
            val homeWriteTypeSelect2 = homeWriteTypeSelectChipGroup.findViewById<Chip>(R.id.homeWriteTypeSelect2)
            val homeWriteTypeSelect3 = homeWriteTypeSelectChipGroup.findViewById<Chip>(R.id.homeWriteTypeSelect3)


            when (progressIsPassing) {
                IsPassing.NOT_STARTED -> {
                    homeWriteTypeSelect1.isChecked = false
                    homeWriteTypeSelect2.isChecked = false
                    homeWriteTypeSelect3.isChecked = false
//                    homeWriteTypeSelect1.chipStrokeWidth = 4f
//                    homeWriteTypeSelect1.setChipStrokeColorResource(R.color.atracker_white)
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
                    //addLayout.id = View.generateViewId()
                    addLayout.id = contentOrder
                    homeWriteLL.addView(addLayout)

                    val homeWriteFreeET = addLayout.findViewById<EditText>(R.id.homeWriteFreeET)
                    homeWriteFreeET.setText(freeBody)
                    val homeWriteFreeTitle = addLayout.findViewById<EditText>(R.id.homeWriteFreeTitle)
                    homeWriteFreeTitle.setText(freeTitle)


                    addLayout.tag = contentId

                    freeLayoutListMap[progressName] = freeLayoutListMap[progressName].orEmpty().plus(addLayout)
                }
                ProgressItemBodyType.QNA -> { // qna
                    val addLayout = this.layoutInflater.inflate(R.layout.home_write_qna_layout, null) as ConstraintLayout // inflating view from xml
                    val params : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT, // This will define text view width
                        ConstraintLayout.LayoutParams.WRAP_CONTENT // This will define text view height
                    )
                    params.setMargins(0,20,0,10)
                    //addLayout.layoutParams = params
                    addLayout.id = contentOrder
                    homeWriteLL.addView(addLayout)

                    val homeWriteQuestionTV = addLayout.findViewById<EditText>(R.id.homeWriteQuestionTV)
                    homeWriteQuestionTV.setText(questionBody)
                    val homeWriteAnswerET = addLayout.findViewById<EditText>(R.id.homeWriteAnswerET)
                    homeWriteAnswerET.setText(answerBody)
                    val homeWriteQnaOfReviewET = addLayout.findViewById<EditText>(R.id.homeWriteQnaOfReviewET)
                    homeWriteQnaOfReviewET.setText(qnaReviewBody)


                    addLayout.tag = contentId


                    qnaLayoutListMap[progressName] = qnaLayoutListMap[progressName].orEmpty().plus(addLayout)
                }
                ProgressItemBodyType.OVERALL -> {
                    val addLayout = this.layoutInflater.inflate(R.layout.home_write_review_layout, null) as ConstraintLayout // inflating view from xml
                    val params : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT, // This will define text view width
                        ConstraintLayout.LayoutParams.WRAP_CONTENT // This will define text view height
                    )
                    params.setMargins(0,20,0,10)
                    addLayout.layoutParams = params
                    //addLayout.id = View.generateViewId()
                    addLayout.id = contentOrder
                    homeWriteLL.addView(addLayout)

                    val homeWriteReviewET = addLayout.findViewById<EditText>(R.id.homeWriteReviewET)
                    homeWriteReviewET.setText(totalReviewBody)

                    addLayout.tag = contentId
                    overAllLayoutListMap[progressName] = overAllLayoutListMap[progressName].orEmpty().plus(addLayout)
                }
                ProgressItemBodyType.NOT_DEFINED -> {

                }
            }
        }
    }


    private fun setChipListener(chip1 : Chip, chip2 : Chip, chip3 : Chip, colorValueWhite: Int, colorValueGreen : Int){
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

            addLayout.tag = 123456789
            linearLayout.addView(addLayout)

            when (layoutInt) {
                R.layout.home_write_free_layout -> {
                    freeLayoutListMap[progressName] = freeLayoutListMap[progressName].orEmpty().plus(addLayout)
                }
                R.layout.home_write_qna_layout -> {
                    qnaLayoutListMap[progressName] = qnaLayoutListMap[progressName].orEmpty().plus(addLayout)
                }
                R.layout.home_write_review_layout -> { // overAll
                    overAllLayoutListMap[progressName] = overAllLayoutListMap[progressName].orEmpty().plus(addLayout)
                }
            }
        }
    }



    private fun changeView(layoutTagName : String) {
        Log.d("onTabSelected_changeView", "${layoutTagName}")
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
                            findNavController().navigate(R.id.action_navigation_home_write_to_navigation_home)
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
                            for (l in overAllRemoveLayoutListMap[progressName].orEmpty()) {
                                deleteHomeWriteLL!!.removeView(l)
                            }

                            val frll = freeRemoveLayoutListMap[progressName]!!
                            val qrll = qnaRemoveLayoutListMap[progressName]!!
                            val orll = overAllRemoveLayoutListMap[progressName]!!

                            for (cf in frll) { // stage for 문
                                Log.d("qwe_frll", "${frll}, ${cf.tag.toString().toInt()}")
                                val deletedContentId = cf.tag.toString().toInt()
                                if (deletedContentId != 123456789) {
                                    homeViewModel.deletedContentUpdate(progressName, DeletedContent(deletedContentId))
                                }
                            }

                            for (cq in qrll) { // stage for 문
                                Log.d("qwe_qrll", "${qrll}, ${cq.tag.toString().toInt()}")
                                val deletedContentId = cq.tag.toString().toInt()
                                if (deletedContentId != 123456789) {
                                    homeViewModel.deletedContentUpdate(progressName, DeletedContent(deletedContentId))
                                }
                            }

                            for (co in orll) { // stage for 문
                                Log.d("qwe_orll", "${orll}, ${co.tag.toString().toInt()}")
                                val deletedContentId = co.tag.toString().toInt()
                                if (deletedContentId != 123456789) {

                                    Log.d("qwe_delete", "${deletedContentId}")
                                    homeViewModel.deletedContentUpdate(progressName, DeletedContent(deletedContentId))
                                }
                            }

                            freeRemoveLayoutListMap[progressName]!!.clear()
                            qnaRemoveLayoutListMap[progressName]!!.clear()
                            overAllRemoveLayoutListMap[progressName]!!.clear()
                        }
                        AlertType.TYPE3 -> {
                            previousTabPosition = tab!!.position
                            previousTabName = tab!!.tag.toString()
                            changeView(tab!!.tag.toString())
                        }

                        AlertType.TYPE18 -> {
                            findNavController().popBackStack()
                            //homeViewModel.clearStageProgress()
                        }
                    }
                }
            },
            alertType,
            freeRemoveLayoutListMap[tab!!.tag.toString()].orEmpty().size + qnaRemoveLayoutListMap[tab!!.tag.toString()].orEmpty().size + overAllRemoveLayoutListMap[tab!!.tag.toString()].orEmpty().size
        )

        alertDialogFragment.show(childFragmentManager, AlertDialogFragment.TAG)
    }


    fun saveViewDisappear() {
        binding.homeWriteBottomCL.visibility = View.INVISIBLE
        binding.homeWriteBottomView.visibility = View.INVISIBLE
//        val lp = binding.homeWriteNestedSV.layoutParams
//        lp.height = ViewGroup.LayoutParams.MATCH_PARENT
//        binding.homeWriteNestedSV.layoutParams = lp

    }

    fun saveViewAppear() {
        binding.homeWriteBottomCL.visibility = View.VISIBLE
        binding.homeWriteBottomView.visibility = View.VISIBLE
//        val lp = binding.homeWriteNestedSV.layoutParams
//        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
//        binding.homeWriteNestedSV.layoutParams = lp
    }


}