package com.cmc.atracker.ui.signUp

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.cmc.atracker.R
import com.cmc.atracker.databinding.FragmentSignUpPositionBinding
import com.cmc.atracker.utils.ChangeUIState


class SignUpPositionFragment : Fragment() {
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



    private var _binding: FragmentSignUpPositionBinding? = null
    private val binding get() = _binding!!
    private val signUpViewModel: SignUpViewModel by activityViewModels()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = DataBindingUtil.inflate<FragmentSignUpPositionBinding>(inflater, R.layout.fragment_sign_up_position, container, false)
        binding.signUpVM = signUpViewModel
        val careerAgeItems = signUpViewModel.careerAgeItems.value


        binding.signUpPositionPositionET.setOnFocusChangeListener { view, isFocuse ->
            if (isFocuse)
                binding.signUpPositionView1.background = ContextCompat.getDrawable(lazyContext, R.color.progress_color_7)
            else
                binding.signUpPositionView1.background = ContextCompat.getDrawable(lazyContext, R.color.atracker_gray_4)
        }

        binding.signUpPositionNext.setOnClickListener { view ->
            signUpViewModel.postSignUp()

            view.findNavController().navigate(R.id.action_navigation_sign_up_position_to_navigation_sign_up_complete)
        }

        binding.signUpPositionBackIV.setOnClickListener { view ->
            view.findNavController().popBackStack()
        }

        var spinnerSelectedPosition : Int = -1



        binding.signUpPositionPositionET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                ChangeUIState(lazyContext).viewEnable(binding.signUpPositionNext, charSequence!!.isNotBlank(), spinnerSelectedPosition > -1)

                if (charSequence.toString().isNotBlank())
                    binding.signUpPositionCancelIV.visibility = View.VISIBLE
                else
                    binding.signUpPositionCancelIV.visibility = View.INVISIBLE
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })


        binding.signUpPositionCancelIV.setOnClickListener {
            binding.signUpPositionPositionET.setText("")
        }



        val careerAgeAdapter = object : ArrayAdapter<String>(lazyContext, R.layout.item_spinner_text_view) {

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent)
                if (position == count) {
                    val hintTextView = v.findViewById<TextView>(R.id.itemSpinnerTV)
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


        careerAgeAdapter.addAll(careerAgeItems!!.toMutableList())
        careerAgeAdapter.add("경력을 선택해주세요.")
        binding.signUpPositionCareerAgeSpinner.adapter = careerAgeAdapter
        binding.signUpPositionCareerAgeSpinner.setSelection(careerAgeAdapter.count)


        binding.signUpPositionCareerAgeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        spinnerSelectedPosition = position
                        signUpViewModel.setUserCareerPosition(spinnerSelectedPosition)
                        ChangeUIState(lazyContext).viewEnable(binding.signUpPositionNext, signUpViewModel.signUpPosition.value!!.isNotBlank(), true)
                    }
                    1 -> {
                        spinnerSelectedPosition = position
                        signUpViewModel.setUserCareerPosition(spinnerSelectedPosition)
                        ChangeUIState(lazyContext).viewEnable(binding.signUpPositionNext, signUpViewModel.signUpPosition.value!!.isNotBlank(), true)
                    }
                    else -> {
                        ChangeUIState(lazyContext).viewEnable(binding.signUpPositionNext, signUpViewModel.signUpPosition.value!!.isNotBlank(), false)
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