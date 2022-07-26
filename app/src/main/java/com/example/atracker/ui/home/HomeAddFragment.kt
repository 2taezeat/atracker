package com.example.atracker.ui.home

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.atracker.R
import com.example.atracker.R.id.itemSpinnerTV
import com.example.atracker.databinding.FragmentHomeAddBinding
import com.example.atracker.ui.AlertDialogFragment
import com.example.atracker.ui.AlertDialogListener
import com.example.atracker.ui.MainActivity
import com.example.atracker.utils.AlertType
import com.google.android.material.chip.Chip

import android.widget.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.atracker.model.dto.HomeAddProgress
import com.example.atracker.model.dto.Stage
import com.example.atracker.utils.AlertApiObject
import java.time.ZonedDateTime

import kotlin.collections.ArrayList


class HomeAddFragment : Fragment() {
    private var _binding: FragmentHomeAddBinding? = null
    private val parentActivity by lazy {
        activity as MainActivity
    }

    private val lazyContext by lazy {
        requireContext()
    }

    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()

    lateinit var numberDrawableList: ArrayList<Drawable?>

    private val args : HomeAddFragmentArgs by navArgs()


    lateinit var homeCompanySearchFragment: HomeCompanySearchFragment


    lateinit var chipsMap : MutableMap<Int, Chip>
    lateinit var chipsList : ArrayList<Chip>

    var firstCreate = true



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val numberDrawable1 = ContextCompat.getDrawable(lazyContext, R.drawable.ic_icon_1_raw)
        val numberDrawable2 = ContextCompat.getDrawable(lazyContext, R.drawable.ic_icon_2_raw)
        val numberDrawable3 = ContextCompat.getDrawable(lazyContext, R.drawable.ic_icon_3_raw)
        val numberDrawable4 = ContextCompat.getDrawable(lazyContext, R.drawable.ic_icon_4_raw)
        val numberDrawable5 = ContextCompat.getDrawable(lazyContext, R.drawable.ic_icon_5_raw)
        val numberDrawable6 = ContextCompat.getDrawable(lazyContext, R.drawable.ic_icon_6_raw)
        val numberDrawable7 = ContextCompat.getDrawable(lazyContext, R.drawable.ic_icon_7_raw)
        val numberDrawable8 = ContextCompat.getDrawable(lazyContext, R.drawable.ic_icon_1_raw)

        numberDrawableList = arrayListOf(
            numberDrawable1,
            numberDrawable2,
            numberDrawable3,
            numberDrawable4,
            numberDrawable5,
            numberDrawable6,
            numberDrawable7,
            numberDrawable8
            )

        chipsMap = mutableMapOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentHomeAddBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.homeVM = homeViewModel
        var spinnerSelectedPosition : Int = -1
        homeCompanySearchFragment = HomeCompanySearchFragment()


        chipsList = arrayListOf(
            binding.homeAddTypeSelect1,
            binding.homeAddTypeSelect2,
            binding.homeAddTypeSelect3,
            binding.homeAddTypeSelect4,
            binding.homeAddTypeSelect5,
            binding.homeAddTypeSelect6,
            binding.homeAddTypeSelect7,
            binding.homeAddTypeSelect8,
        )

        Log.d("homeadd_onCreateView", "${firstCreate}")

        val homeAddStagesName = homeViewModel.homeAddStagesContent.value!!

        for (idx in homeAddStagesName.indices) {
            val h = homeAddStagesName[idx]
            val c = chipsList[idx]
            c.text = h.title
            c.tag = h.id
            homeViewModel.setOriChipId(c.id)
            chipsMap[h.id] = c

            c.setOnCheckedChangeListener { compoundButton, checked ->
                setOnCheckedChip(compoundButton, checked, c)
            }
        }

        binding.homeAddBackButton.setOnClickListener { view ->
            parentActivity.mainBottomNavigationAppear()
            val navController = view.findNavController()
            navController.popBackStack()
            homeViewModel.clearHomeAddText()
        }

        if (homeViewModel.homeAddStagesContent.value!!.isEmpty()){ // api 예외 처리
            parentActivity.showAlertInstance(AlertApiObject.alertDialogFragment)
            parentActivity.onBackPressed()
        }


        binding.homeAddNext.setOnClickListener { view ->
            Log.d("home_add_value_check", "${homeViewModel.companyWord.value!!}, ${homeViewModel.companyId.value!!}")

            var checkNext = true

            if (homeViewModel.companyWord.value.isNullOrBlank() || homeViewModel.companyId.value == 0) {
                showAlert(AlertType.TYPE5, null)
                checkNext = false
            }
            if (homeViewModel.positionWord.value.isNullOrBlank()) {
                showAlert(AlertType.TYPE6, null)
                checkNext = false
            }
            if (homeViewModel.workTypeSelection.value == -1) {
                showAlert(AlertType.TYPE9, null)
                checkNext = false
            }

            val posList = homeViewModel.trueChipSet.value!!.toList()

            if (posList.size < 2) {
                showAlert(AlertType.TYPE7, null)
                checkNext = false
            } else {
                val checkedChipStage = arrayListOf<Stage>()
                val checkedChipAddProgress = arrayListOf<HomeAddProgress>()

                for (idx in posList.indices) {
                    val checkedChip = binding.homeAddTypeSelectChipGroup.findViewById<Chip>(posList[idx])
                    var zonedDateTime : ZonedDateTime? = null

                    homeViewModel.homeAddSelectedStage.value?.let{ value ->
                        val eventDateString : String? = value.find { it.stage_id == checkedChip.tag }!!.event_at
                        eventDateString?.let {
                            zonedDateTime = ZonedDateTime.parse(eventDateString)
                        }

                        Log.d("eventDateString", "${value}, ${checkedChip.tag}, ${zonedDateTime}")
                    }


                    checkedChipAddProgress.add(HomeAddProgress(checkedChip.text.toString(), zonedDateTime))
                    val stageId = homeViewModel.homeAddStagesContent.value!!.find{ it.title == checkedChip.text.toString() }!!.id
                    checkedChipStage.add(Stage(event_at = zonedDateTime.toString(), order = idx, stage_id = stageId))
                }

                Log.d("asdasd", "${checkedChipStage}, ${checkedChipAddProgress}")

                if (args.progressIndex != 0) { // edit 일때
//                    homeViewModel.setSelectedChipStage(checkedChipStage) //// not fix!
//                    homeViewModel.setSelectedChipName(checkedChipAddProgress)
                }
                homeViewModel.setSelectedChipStage(checkedChipStage) //// not fix!
                homeViewModel.setSelectedChipName(checkedChipAddProgress)

            }

            if (checkNext) {
                val action = HomeAddFragmentDirections.actionNavigationHomeAddToNavigationHomeAddCalendar(args.progressIndex)
                view.findNavController().navigate(action)
                //checkedChipIdList.clear()
            }

        }



        binding.homeAddRefreshIV.setOnClickListener {
            homeViewModel.refreshChip()
        }



        if (args.progressIndex != 0 ) { // 편집인 경우, mvp 이후 fix 필요

            Log.d("homeAddSelectedStage", "${homeViewModel.homeAddSelectedStage.value} , ${spinnerSelectedPosition}")


            binding.homeAddHeaderTitle.text = "지원 현황 편집"
            spinnerSelectedPosition = homeViewModel.setWorkTypeSpinnerPosition()
            homeViewModel.setHomeEdit()

            for (c in chipsList) {
                c.visibility = View.GONE
            }

            for ( editSelectedStage in homeViewModel.homeAddSelectedStage.value!!) {
                val stageId = editSelectedStage.stage_id
                val selectedChip = chipsMap[stageId]!!

                selectedChip.visibility = View.VISIBLE
                selectedChip.isChecked = true
            }
        }



        val workTypeItems: List<String> = listOf("정규직", "계약직", "인턴")
        val workTypeAdapter = object : ArrayAdapter<String>(lazyContext, R.layout.item_spinner_text_view) {

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent)
                if (position == count) {
                    val hintTextView = v.findViewById<TextView>(itemSpinnerTV)
                    hintTextView.hint = getItem(count)
                    hintTextView.setTextColor(Color.parseColor("#565B6E"))
                    hintTextView.textSize = 16f
                }
                return v
            }

            override fun getCount(): Int {
                //마지막 아이템은 힌트용으로만 사용하기 때문에 getCount에 1을 빼줍니다.
                return super.getCount() - 1
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup,
            ): View {
                val view = super.getDropDownView(position, convertView, parent)
                val dropDownTextView = view as TextView
                dropDownTextView.setPadding(40,20,40,20)
                if (position == spinnerSelectedPosition) {
                    dropDownTextView.setTextColor(Color.parseColor("#7EFBDC"))
                }
                return view
            }
        }


        workTypeAdapter.addAll(workTypeItems.toMutableList())
        workTypeAdapter.add("근무 형태를 선택해주세요.")
        binding.homeAddWorkTypeSpinner.adapter = workTypeAdapter

        if (args.progressIndex != 0) {
            binding.homeAddWorkTypeSpinner.setSelection(spinnerSelectedPosition)
        } else {
            binding.homeAddWorkTypeSpinner.setSelection(workTypeAdapter.count)
        }



        binding.homeAddWorkTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.

                when (position) {
                    0 -> {
                        spinnerSelectedPosition = position
                        homeViewModel.setWorkTypePosition(spinnerSelectedPosition)
                    }
                    1 -> {
                        spinnerSelectedPosition = position
                        homeViewModel.setWorkTypePosition(spinnerSelectedPosition)
                    }
                    2 -> {
                        spinnerSelectedPosition = position
                        homeViewModel.setWorkTypePosition(spinnerSelectedPosition)
                    }
                    else -> {
//                        spinnerSelectedPosition = -1
//                        homeViewModel.setWorkTypePosition(spinnerSelectedPosition)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.d("MyTag", "onNothingSelected")
            }
        }


//        binding.homeAddACTV.setOnFocusChangeListener { view, isFocuse ->
//            if (isFocuse) {
//                binding.homeAddView1.background = ContextCompat.getDrawable(lazyContext, R.color.progress_color_7)
//                homeCompanySearchFragment.show(parentFragmentManager, homeCompanySearchFragment.tag)
//            } else {
//                binding.homeAddView1.background = ContextCompat.getDrawable(lazyContext, R.color.atracker_gray_4)
//                homeCompanySearchFragment.dialog!!.dismiss()
//            }
//        }


        binding.homeAddApplyFieldET.setOnFocusChangeListener { view, isFocuse ->
            if (isFocuse)
                binding.homeAddView2.background = ContextCompat.getDrawable(lazyContext, R.color.progress_color_7)
            else
                binding.homeAddView2.background = ContextCompat.getDrawable(lazyContext, R.color.atracker_gray_4)
        }

        binding.homeAddApplyFieldET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (charSequence.toString().isNotBlank())
                    binding.homeAddApplyFieldCancelIV.visibility = View.VISIBLE
                else
                    binding.homeAddApplyFieldCancelIV.visibility = View.INVISIBLE
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })



        binding.homeAddACTV.setOnClickListener {
            homeCompanySearchFragment.show(parentFragmentManager, homeCompanySearchFragment.tag)
            binding.homeAddView1.background = ContextCompat.getDrawable(lazyContext, R.color.progress_color_7)
        }




        homeViewModel.companyId.observe(viewLifecycleOwner, Observer {
            binding.homeAddACTV.setText(homeViewModel.companyWord.value)
        })


        homeViewModel.trueChipSet.observe(viewLifecycleOwner, Observer {
            val chipCheckIdList = homeViewModel.trueChipSet.value!!.toList()
            Log.d("chipCheckIdList", "${homeViewModel.trueChipSet.value}, ${homeViewModel.falseChipSet.value}")

            for (idx in chipCheckIdList.indices) {
                val chipId = chipCheckIdList[idx]
                val checkedChip = binding.homeAddTypeSelectChipGroup.findViewById<Chip>(chipId)
                checkedChip.checkedIcon = numberDrawableList[idx]
                checkedChip.chipStrokeWidth = 4f
                checkedChip.setChipStrokeColorResource(R.color.progress_color_7)
            }

        })

        homeViewModel.falseChipSet.observe(viewLifecycleOwner, Observer {
            for (chipId in homeViewModel.falseChipSet.value!!.toList()) {
                val notCheckedChip = binding.homeAddTypeSelectChipGroup.findViewById<Chip>(chipId)
                notCheckedChip.isChecked = false
                notCheckedChip.chipStrokeWidth = 0f
            }
        })


        binding.homeAddApplyFieldCancelIV.setOnClickListener {
            binding.homeAddApplyFieldET.setText("")
        }

        return root
    }



    private fun setOnCheckedChip(compoundButton : CompoundButton, checked : Boolean, chip : Chip) { // edit 시 fix 필요, mvp 이후, 로직 변경 필요
        Log.d("setOnCheckedChip", "${firstCreate}, ${homeViewModel.trueChipSet.value!!.size}")

        if (firstCreate) {
            if (homeViewModel.trueChipSet.value!!.size >= 7 && checked ) {
                showAlert(AlertType.TYPE8, compoundButton.id)
                binding.homeAddRefreshIV.callOnClick()
            } else {
                homeViewModel.setChipSet(checked, compoundButton.id)
            }
        } else {
            if (homeViewModel.trueChipSet.value!!.size >= 7 && checked) { // fix 해야 됨, 7개 이상이고 뒤로 가기 일때,
                showAlert(AlertType.TYPE8, compoundButton.id)
                binding.homeAddRefreshIV.callOnClick()
            } else {
                homeViewModel.setChipSet(checked, compoundButton.id)
            }

        }
    }


    private fun showAlert(alertType: AlertType, chipId: Int? ){
        val alertDialogFragment = AlertDialogFragment.instance(
            object : AlertDialogListener {
                override fun onLeftClick() {
                }

                override fun onCenterClick() {
                    when (alertType) {
                        AlertType.TYPE8 -> {
                            homeViewModel.setChipSet(false, chipId!!)
                        }
                    }
                }

                override fun onRightClick() {
                }
            },
            alertType,
            null
        )
        alertDialogFragment.show(childFragmentManager, AlertDialogFragment.TAG)
    }


    fun setViewGray(){
        binding.homeAddView1.background = ContextCompat.getDrawable(lazyContext, R.color.atracker_gray_4)
    }

    override fun onStop() {
        super.onStop()
        Log.d("homeadd_onStop","onStop")

        firstCreate = false
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("homeadd_onDestory","onDestory")
    }


}