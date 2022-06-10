package com.example.atracker.ui.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.atracker.R
import com.example.atracker.databinding.FragmentHomeAddBinding
import com.example.atracker.databinding.FragmentHomeDisplayBinding
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

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by activityViewModels()


    lateinit var numberDrawableList : ArrayList<Drawable?>

    //lateinit var checkedChipIdList : ArrayList<Int>

    val checkedChipIdList by lazy {
        arrayListOf<Int>()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val numberDrawable1 = ContextCompat.getDrawable(lazyContext, R.drawable.ic_baseline_looks_one_24)
        val numberDrawable2 = ContextCompat.getDrawable(lazyContext, R.drawable.ic_baseline_looks_two_24)
        val numberDrawable3 = ContextCompat.getDrawable(lazyContext, R.drawable.ic_baseline_looks_3_24)
        val numberDrawable4 = ContextCompat.getDrawable(lazyContext, R.drawable.ic_baseline_looks_4_24)
        val numberDrawable5 = ContextCompat.getDrawable(lazyContext, R.drawable.ic_baseline_looks_5_24)

        numberDrawableList = arrayListOf(numberDrawable1, numberDrawable2, numberDrawable3, numberDrawable4, numberDrawable5)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentHomeAddBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.homeAddBackButton.setOnClickListener { view ->
            val navController = view.findNavController()
            navController.popBackStack()
        }


        binding.homeAddTypeSelectChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            for (idx in 0 until checkedChipIdList.size) {
                val checkedChip = binding.homeAddTypeSelectChipGroup.findViewById<Chip>(checkedChipIdList[idx])
                checkedChip.checkedIcon = numberDrawableList[idx]
            }
        }

        binding.homeAddTypeSelect1.setOnCheckedChangeListener { compoundButton, checked ->
            if (checked) checkedChipIdList.add(compoundButton.id)
            else checkedChipIdList.remove(compoundButton.id)
        }

        binding.homeAddTypeSelect2.setOnCheckedChangeListener { compoundButton, checked ->
            if (checked) checkedChipIdList.add(compoundButton.id)
            else checkedChipIdList.remove(compoundButton.id)
        }

        binding.homeAddTypeSelect3.setOnCheckedChangeListener { compoundButton, checked ->
            if (checked) checkedChipIdList.add(compoundButton.id)
            else checkedChipIdList.remove(compoundButton.id)
        }

        binding.homeAddTypeSelect4.setOnCheckedChangeListener { compoundButton, checked ->
            if (checked) checkedChipIdList.add(compoundButton.id)
            else checkedChipIdList.remove(compoundButton.id)
        }




        return root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeAddFragment().apply {
                arguments = Bundle().apply {



                }
            }
    }
}