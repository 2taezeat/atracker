package com.example.atracker.ui.signUp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.atracker.R
import com.example.atracker.databinding.FragmentSignUpNickNameBinding
import com.example.atracker.R.id.itemSpinnerTV
import com.example.atracker.databinding.FragmentHomeDisplayBinding
import com.example.atracker.ui.MainActivity

class SignUpNickNameFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignUpNickNameFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
    private val lazyContext by lazy {
        requireContext()
    }



    private var _binding: FragmentSignUpNickNameBinding? = null
    private val binding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = DataBindingUtil.inflate<FragmentSignUpNickNameBinding>(inflater, R.layout.fragment_sign_up_nick_name, container, false)


        binding.signUpNickNamePositionET.setOnFocusChangeListener { view, isFocuse ->
            if (isFocuse)
                binding.signUpNickNameView1.background = ContextCompat.getDrawable(lazyContext, R.color.progress_color_7)
            else
                binding.signUpNickNameView1.background = ContextCompat.getDrawable(lazyContext, R.color.atracker_gray_4)
        }



        var spinnerSelectedPosition : Int = -1
        val careerAgeItems: List<String> = listOf("신입", "경력")
        val careerAgeAdapter = object : ArrayAdapter<String>(lazyContext, R.layout.item_spinner_text_view) {

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


        careerAgeAdapter.addAll(careerAgeItems.toMutableList())
        careerAgeAdapter.add("경력을 선택해주세요.")
        binding.signUpNickNameCareerAgeSpinner.adapter = careerAgeAdapter
        binding.signUpNickNameCareerAgeSpinner.setSelection(careerAgeAdapter.count)


        binding.signUpNickNameCareerAgeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        spinnerSelectedPosition = position
                    }
                    1 -> {
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




        return binding.root
    }

}