package com.example.atracker.ui.home

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
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

import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.atracker.model.dto.HomeAddProgress
import com.example.atracker.model.dto.Stage


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

    private var count = 0

    private val checkedChipIdList by lazy {
        arrayListOf<Int>()
    }

    override fun onStart() {
        super.onStart()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val numberDrawable1 = ContextCompat.getDrawable(lazyContext, R.drawable.ic_icon_1_raw)
        val numberDrawable2 = ContextCompat.getDrawable(lazyContext, R.drawable.ic_icon_2_raw)
        val numberDrawable3 = ContextCompat.getDrawable(lazyContext, R.drawable.ic_icon_3_raw)
        val numberDrawable4 = ContextCompat.getDrawable(lazyContext, R.drawable.ic_icon_4_raw)
        val numberDrawable5 = ContextCompat.getDrawable(lazyContext, R.drawable.ic_icon_5_raw)
        val numberDrawable6 = ContextCompat.getDrawable(lazyContext, R.drawable.ic_icon_6_raw)
        val numberDrawable7 = ContextCompat.getDrawable(lazyContext, R.drawable.ic_icon_7_raw)

        numberDrawableList = arrayListOf(
            numberDrawable1,
            numberDrawable2,
            numberDrawable3,
            numberDrawable4,
            numberDrawable5,
            numberDrawable6,
            numberDrawable7,
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentHomeAddBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.homeVM = homeViewModel
        var spinnerSelectedPosition : Int = -1


        Log.d("HomeAddFragment", "${args.progressIndex}")


        binding.homeAddNext.setOnClickListener { view ->
            Log.d("home_add_value_check", "${homeViewModel.companyWord.value!!}, ${homeViewModel.positionWord.value!!}")

            var checkNext = true

            if (homeViewModel.companyWord.value.isNullOrBlank()) {
                showAlert(AlertType.TYPE5)
                checkNext = false
            }
            if (homeViewModel.positionWord.value.isNullOrBlank()) {
                showAlert(AlertType.TYPE6)
                checkNext = false
            }
            if (homeViewModel.workTypeSelection.value == -1) {
                showAlert(AlertType.TYPE9)
                checkNext = false
            }
            if (checkedChipIdList.size < 2) {
                showAlert(AlertType.TYPE7)
                checkNext = false
            } else {
                val checkedChipStage = arrayListOf<Stage>()
                val checkedChipAddProgress = arrayListOf<HomeAddProgress>()

                for (idx in 0 until checkedChipIdList.size) {
                    val checkedChip = binding.homeAddTypeSelectChipGroup.findViewById<Chip>(checkedChipIdList[idx])
                    checkedChipAddProgress.add(HomeAddProgress(checkedChip.text.toString(), null))

                    val stageId = homeViewModel.homeAddStagesContent.value!!.find{ it.title == checkedChip.text.toString() }!!.id
                    checkedChipStage.add(Stage(event_at = "", order = idx, stage_id = stageId))
                }

                homeViewModel.setSelectedChipStage(checkedChipStage) //// not fix!
                homeViewModel.setSelectedChipName(checkedChipAddProgress)
            }


            if (checkNext) {

                val action = HomeAddFragmentDirections.actionNavigationHomeAddToNavigationHomeAddCalendar(args.progressIndex)
                view.findNavController().navigate(action)

                //view.findNavController().navigate(R.id.action_navigation_home_add_to_navigation_home_add_calendar)
                checkedChipIdList.clear()
            }

        }


        binding.homeAddBackButton.setOnClickListener { view ->
            parentActivity.mainBottomNavigationAppear()
            val navController = view.findNavController()
            navController.popBackStack()
            homeViewModel.clearHomeAddText()

        }


        binding.homeAddRefreshIV.setOnClickListener {
            val copyCheckedChipIdList = ArrayList(checkedChipIdList)
            for (idx in 0 until copyCheckedChipIdList.size) {
                val checkedChip = binding.homeAddTypeSelectChipGroup.findViewById<Chip>(copyCheckedChipIdList[idx])
                checkedChip.isChecked = false
            }
        }

        binding.homeAddTypeSelectChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            Log.d("checkedChipIdList","${checkedChipIdList}")
            for (idx in 0 until checkedChipIdList.size) {
                val checkedChip =
                    binding.homeAddTypeSelectChipGroup.findViewById<Chip>(checkedChipIdList[idx])
                checkedChip.checkedIcon = numberDrawableList[idx]
            }

        }

        val homeAddStagesName = homeViewModel.homeAddStagesContent.value!!

        binding.homeAddTypeSelect1.text = homeAddStagesName[0].title
        binding.homeAddTypeSelect2.text = homeAddStagesName[1].title
        binding.homeAddTypeSelect3.text = homeAddStagesName[2].title
        binding.homeAddTypeSelect4.text = homeAddStagesName[3].title
        binding.homeAddTypeSelect5.text = homeAddStagesName[4].title
        binding.homeAddTypeSelect6.text = homeAddStagesName[5].title
        binding.homeAddTypeSelect7.text = homeAddStagesName[6].title
        binding.homeAddTypeSelect8.text = homeAddStagesName[7].title


        val chipsMap = mapOf<Int, Chip>(
            homeAddStagesName[0].id to binding.homeAddTypeSelect1,
            homeAddStagesName[1].id to binding.homeAddTypeSelect2,
            homeAddStagesName[2].id to binding.homeAddTypeSelect3,
            homeAddStagesName[3].id to binding.homeAddTypeSelect4,
            homeAddStagesName[4].id to binding.homeAddTypeSelect5,
            homeAddStagesName[5].id to binding.homeAddTypeSelect6,
            homeAddStagesName[6].id to binding.homeAddTypeSelect7,
            homeAddStagesName[7].id to binding.homeAddTypeSelect8,
        )

        binding.homeAddTypeSelect1.setOnCheckedChangeListener { compoundButton, checked ->
            setOnCheckedChip(compoundButton, checked, binding.homeAddTypeSelect1)
        }

        binding.homeAddTypeSelect2.setOnCheckedChangeListener { compoundButton, checked ->
            setOnCheckedChip(compoundButton, checked, binding.homeAddTypeSelect2)
        }

        binding.homeAddTypeSelect3.setOnCheckedChangeListener { compoundButton, checked ->
            setOnCheckedChip(compoundButton, checked, binding.homeAddTypeSelect3)
        }

        binding.homeAddTypeSelect4.setOnCheckedChangeListener { compoundButton, checked ->
            setOnCheckedChip(compoundButton, checked, binding.homeAddTypeSelect4)
        }

        binding.homeAddTypeSelect5.setOnCheckedChangeListener { compoundButton, checked ->
            setOnCheckedChip(compoundButton, checked, binding.homeAddTypeSelect5)
        }

        binding.homeAddTypeSelect6.setOnCheckedChangeListener { compoundButton, checked ->
            setOnCheckedChip(compoundButton, checked, binding.homeAddTypeSelect6)
        }

        binding.homeAddTypeSelect7.setOnCheckedChangeListener { compoundButton, checked ->
            setOnCheckedChip(compoundButton, checked, binding.homeAddTypeSelect7)
        }

        binding.homeAddTypeSelect8.setOnCheckedChangeListener { compoundButton, checked ->
            setOnCheckedChip(compoundButton, checked, binding.homeAddTypeSelect8)
        }


        if (args.progressIndex != 0) { // ????????? ??????
            binding.homeAddHeaderTitle.text = "?????? ?????? ??????"
            spinnerSelectedPosition = homeViewModel.setWorkTypeSpinnerPosition()
            homeViewModel.setHomeEdit()

            for ( editSelectedStage in homeViewModel.homeAddSelectedStage.value!!) {
                val stageId = editSelectedStage.stage_id
                val selectedChip = chipsMap[stageId]
                selectedChip!!.isChecked = true
            }

        }



        val workTypeItems: List<String> = listOf("?????????", "?????????", "??????")
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
                //????????? ???????????? ?????????????????? ???????????? ????????? getCount??? 1??? ????????????.
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
        workTypeAdapter.add("?????? ????????? ??????????????????.")
        binding.homeAddWorkTypeSpinner.adapter = workTypeAdapter

        if (args.progressIndex != 0) {
            binding.homeAddWorkTypeSpinner.setSelection(spinnerSelectedPosition)
        } else {
            binding.homeAddWorkTypeSpinner.setSelection(workTypeAdapter.count)
        }



        binding.homeAddWorkTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                //???????????? ?????? ?????? ??? ????????? position 0????????? ???????????? ???????????? ?????????.

                Log.d("test22222", "${position}")

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


        binding.homeAddACTV.setOnFocusChangeListener { view, isFocuse ->
            if (isFocuse)
                binding.homeAddView1.background = ContextCompat.getDrawable(lazyContext, R.color.progress_color_7)
            else
                binding.homeAddView1.background = ContextCompat.getDrawable(lazyContext, R.color.atracker_gray_4)
        }

        binding.homeAddApplyFieldET.setOnFocusChangeListener { view, isFocuse ->
            if (isFocuse)
                binding.homeAddView2.background = ContextCompat.getDrawable(lazyContext, R.color.progress_color_7)
            else
                binding.homeAddView2.background = ContextCompat.getDrawable(lazyContext, R.color.atracker_gray_4)
        }


        binding.homeAddACTV.setOnScrollChangeListener(object : View.OnScrollChangeListener{
            override fun onScrollChange(p0: View?, p1: Int, p2: Int, p3: Int, p4: Int) {
                Log.d("test2222", "test2222")
            }
        })


        binding.homeAddACTV.setDropDownBackgroundDrawable(ContextCompat.getDrawable(lazyContext, R.drawable.button_round))

        binding.homeAddACTV.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Log.d("qqq_test1111", "${charSequence},${p1},${p2},${p3}")
            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("qqq_test2222", "${charSequence},${p1},${p2},${p3}")
                if (charSequence!!.length >= 2) {
                    homeViewModel.getCompanyTitle(charSequence.toString())
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.d("qqq_test3333", "${p0}")
            }
        })

        homeViewModel.companyList.observe(viewLifecycleOwner, Observer {

            val companyNameList = homeViewModel.companyList.value!!.map { it.name}

            Log.d("companyTitleList", "${it}")
            val companyTitleAdapter = ArrayAdapter<String>(lazyContext,R.layout.item_auto_complete_text_view, companyNameList.toTypedArray())

            binding.homeAddACTV.setAdapter(companyTitleAdapter)
            binding.homeAddACTV.showDropDown()
        })


//        binding.homeAddACTV.setOnScrollChangeListener { view, i, i2, i3, i4 ->
//            Log.d("test333333", "${view}, ${i}, ${i2}")
//
//        }

        ////////////////////////////
        binding.homeAddACTV.setOnItemClickListener { adapterView, view, i, l ->
            Log.d("tmp123123", "${adapterView}, ${view}, ${i}, ${l}")
            homeViewModel.setCompanyNameID(i)
        }


        return root
    }

    private fun setOnCheckedChip(compoundButton : CompoundButton, checked : Boolean, chip : Chip) {
        if (checkedChipIdList.size >= 7 && checked) {
            showAlert(AlertType.TYPE8)
            chip.isCheckable = false
            binding.homeAddRefreshIV.callOnClick()
            chip.isCheckable = true
        }
        Log.d("count123123", "${count}")

        if (checked) {
            checkedChipIdList.add(compoundButton.id)
            chip.chipStrokeWidth = 4f
            chip.setChipStrokeColorResource(R.color.progress_color_7)
            //Log.d("count123123", "${count}")

            //chip.checkedIcon = numberDrawableList[checkedChipIdList.size - 1]

            count += 1

        } else {
            checkedChipIdList.remove(compoundButton.id)
            chip.chipStrokeWidth = 0f

            count -= 1
        }

        //checkedChip.checkedIcon = numberDrawableList[idx]


    }


    private fun showAlert(alertType: AlertType ){
        val alertDialogFragment = AlertDialogFragment.instance(
            object : AlertDialogListener {
                override fun onLeftClick() {
                }

                override fun onCenterClick() {
                    when (alertType) {
                        AlertType.TYPE8 -> {
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


}