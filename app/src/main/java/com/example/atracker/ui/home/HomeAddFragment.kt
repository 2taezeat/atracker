package com.example.atracker.ui.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.atracker.R
import com.example.atracker.databinding.FragmentHomeAddBinding
import com.example.atracker.ui.AlertDialogFragment
import com.example.atracker.ui.AlertDialogListener
import com.example.atracker.ui.MainActivity
import com.example.atracker.utils.AlertType
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


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