package com.example.atracker.ui.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.atracker.R
import com.example.atracker.databinding.FragmentHomeAddBinding
import com.example.atracker.ui.MainActivity
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

        val numberDrawable1 =
            ContextCompat.getDrawable(lazyContext, R.drawable.ic_baseline_looks_one_24)
        val numberDrawable2 =
            ContextCompat.getDrawable(lazyContext, R.drawable.ic_baseline_looks_two_24)
        val numberDrawable3 =
            ContextCompat.getDrawable(lazyContext, R.drawable.ic_baseline_looks_3_24)
        val numberDrawable4 =
            ContextCompat.getDrawable(lazyContext, R.drawable.ic_baseline_looks_4_24)
        val numberDrawable5 =
            ContextCompat.getDrawable(lazyContext, R.drawable.ic_baseline_looks_5_24)

        numberDrawableList = arrayListOf(numberDrawable1,
            numberDrawable2,
            numberDrawable3,
            numberDrawable4,
            numberDrawable5)
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
                val checkedChip =
                    binding.homeAddTypeSelectChipGroup.findViewById<Chip>(copyCheckedChipIdList[idx])
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
            if (checkedChipIdList.size >= 3 && checked) {
                binding.homeAddTypeSelect1.isCheckable = false
                binding.homeAddRefreshIV.callOnClick()
                binding.homeAddTypeSelect1.isCheckable = true
            }

            if (checked) {
                checkedChipIdList.add(compoundButton.id)
                binding.homeAddTypeSelect1.chipStrokeWidth = 4f
                binding.homeAddTypeSelect1.setChipStrokeColorResource(R.color.progress_color_7)
            } else {
                checkedChipIdList.remove(compoundButton.id)
                binding.homeAddTypeSelect1.chipStrokeWidth = 0f
            }

        }

        binding.homeAddTypeSelect2.setOnCheckedChangeListener { compoundButton, checked ->
            if (checkedChipIdList.size >= 3 && checked) {
                binding.homeAddTypeSelect2.isCheckable = false
                binding.homeAddRefreshIV.callOnClick()
                binding.homeAddTypeSelect2.isCheckable = true
            }

            if (checked) {
                checkedChipIdList.add(compoundButton.id)
                binding.homeAddTypeSelect2.chipStrokeWidth = 4f
                binding.homeAddTypeSelect2.setChipStrokeColorResource(R.color.progress_color_7)

            } else {
                checkedChipIdList.remove(compoundButton.id)
                binding.homeAddTypeSelect2.chipStrokeWidth = 0f
            }
        }

        binding.homeAddTypeSelect3.setOnCheckedChangeListener { compoundButton, checked ->
            if (checkedChipIdList.size >= 3 && checked) {
                binding.homeAddTypeSelect3.isCheckable = false
                binding.homeAddRefreshIV.callOnClick()
                binding.homeAddTypeSelect3.isCheckable = true
            }


            if (checked) {
                checkedChipIdList.add(compoundButton.id)
                binding.homeAddTypeSelect3.chipStrokeWidth = 4f
                binding.homeAddTypeSelect3.setChipStrokeColorResource(R.color.progress_color_7)

            } else {
                checkedChipIdList.remove(compoundButton.id)
                binding.homeAddTypeSelect3.chipStrokeWidth = 0f
            }
        }

        binding.homeAddTypeSelect4.setOnCheckedChangeListener { compoundButton, checked ->
            if (checkedChipIdList.size >= 3 && checked) {
                binding.homeAddTypeSelect4.isCheckable = false
                binding.homeAddRefreshIV.callOnClick()
                binding.homeAddTypeSelect4.isCheckable = true
            }

            if (checked) {
                checkedChipIdList.add(compoundButton.id)
                binding.homeAddTypeSelect4.chipStrokeWidth = 4f
                binding.homeAddTypeSelect4.setChipStrokeColorResource(R.color.progress_color_7)

            } else {
                checkedChipIdList.remove(compoundButton.id)
                binding.homeAddTypeSelect4.chipStrokeWidth = 0f
            }
        }



        return root
    }


}