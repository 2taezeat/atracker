package com.example.atracker.ui.home

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.TextView
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
import com.google.android.material.chip.ChipGroup

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer


class HomeAddFragment : Fragment() {
    // TODO: Rename and change types of parameters

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

    private lateinit var chipGroup: ChipGroup


    private val checkedChipIdList by lazy {
        arrayListOf<Int>()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeAddFragment().apply {
                arguments = Bundle().apply {
                }
            }
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
    ): View? {

        _binding = FragmentHomeAddBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.homeVM = homeViewModel


        chipGroup = binding.homeAddTypeSelectChipGroup


        binding.homeAddBackButton.setOnClickListener { view ->
            val navController = view.findNavController()
            navController.popBackStack()
        }


        binding.homeAddRefreshIV.setOnClickListener {
            val copyCheckedChipIdList = ArrayList(checkedChipIdList)
            for (idx in 0 until copyCheckedChipIdList.size) {
                val checkedChip = binding.homeAddTypeSelectChipGroup.findViewById<Chip>(copyCheckedChipIdList[idx])
                checkedChip.isChecked = false
            }
        }

        binding.homeAddTypeSelectChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            for (idx in 0 until checkedChipIdList.size) {
                val checkedChip =
                    binding.homeAddTypeSelectChipGroup.findViewById<Chip>(checkedChipIdList[idx])
                checkedChip.checkedIcon = numberDrawableList[idx]
            }

        }


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



        val workTypeList: List<String> = listOf("asd", "qwe", "ter")
        val adapter = ArrayAdapter<String>(lazyContext,R.layout.support_simple_spinner_dropdown_item,workTypeList)
        binding.homeAddWorkTypeSpinner.adapter = adapter


        var spinnerSelectedPosition : Int = -1
        val workTypeitems: List<String> = listOf("정규직", "계약직", "인턴")
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


        workTypeAdapter.addAll(workTypeitems.toMutableList())
        workTypeAdapter.add("근무 형태를 선택해주세요.")
        binding.homeAddWorkTypeSpinner.adapter = workTypeAdapter
        binding.homeAddWorkTypeSpinner.setSelection(workTypeAdapter.count)

//        binding.homeAddWorkTypeSpinner.setOnTouchListener { view, motionEvent ->
//
//        }


//        binding.homeAddWorkTypeSpinner.setOnTouchListener { v, event ->
//            if (event.action == MotionEvent.ACTION_DOWN) {
//
//            } else {
//
//            }
//            false
//        }

        binding.homeAddWorkTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                when (position) {
                    0 -> {
                        spinnerSelectedPosition = position
                    }
                    1 -> {
                        spinnerSelectedPosition = position
                    }
                    2 -> {
                        spinnerSelectedPosition = position
                    }
                    else -> {

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.d("MyTag", "onNothingSelected")
            }
        }


        binding.homeAddCompanyTitleET.setOnFocusChangeListener { view, isFocuse ->
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


        val items = arrayOf("aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", )

        val adapterTmp = ArrayAdapter<String>(lazyContext,R.layout.support_simple_spinner_dropdown_item,items)
        binding.homeAddACTV.setAdapter(adapterTmp)



        binding.homeAddACTV.addTextChangedListener(object : TextWatcher{

            override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("qqq_test1111", "${charSequence},${p1},${p2},${p3}")
            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("qqq_test2222", "${charSequence},${p1},${p2},${p3}")
                if (charSequence!!.length >= 2) {
                    homeViewModel.getCompanyTitle(charSequence.toString())

                    homeViewModel.companyTitleList.observe(viewLifecycleOwner, Observer {
                        Log.d("companyTitleList", "${it}")

//                        val adapterTmp2 = ArrayAdapter<String>(lazyContext,R.layout.support_simple_spinner_dropdown_item,it.toTypedArray())
//                        binding.homeAddACTV.setAdapter(adapterTmp2)

                    })
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.d("qqq_test3333", "${p0}")

                val adapterTmp2 = ArrayAdapter<String>(lazyContext,R.layout.support_simple_spinner_dropdown_item,homeViewModel.companyTitleList.value!!.toTypedArray())
                binding.homeAddACTV.setAdapter(adapterTmp2)
            }
        }
        )


        homeViewModel.companyTitleList.observe(viewLifecycleOwner, Observer {
            Log.d("companyTitleList", "${it}")
            val adapterTmp2 = ArrayAdapter<String>(lazyContext,R.layout.support_simple_spinner_dropdown_item,homeViewModel.companyTitleList.value!!.toTypedArray())
            binding.homeAddACTV.setAdapter(adapterTmp2)
            binding.homeAddACTV.showDropDown()
        })








        return root
    }

    private fun setOnCheckedChip(compoundButton : CompoundButton, checked : Boolean, chip : Chip) {
        if (checkedChipIdList.size >= 7 && checked) {
            showAlert(AlertType.TYPE8)
            chip.isCheckable = false
            binding.homeAddRefreshIV.callOnClick()
            chip.isCheckable = true
        }

        if (checked) {
            checkedChipIdList.add(compoundButton.id)
            chip.chipStrokeWidth = 4f
            chip.setChipStrokeColorResource(R.color.progress_color_7)

        } else {
            checkedChipIdList.remove(compoundButton.id)
            chip.chipStrokeWidth = 0f
        }

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