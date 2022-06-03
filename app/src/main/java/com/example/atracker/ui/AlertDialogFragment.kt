package com.example.atracker.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.atracker.R
import com.example.atracker.databinding.FragmentAlertDialogBinding
import com.example.atracker.databinding.FragmentHomeDisplayBinding
import com.example.atracker.utils.AlertType

class AlertDialogFragment(private val alertDialogListener: AlertDialogListener, private val alertType : AlertType ) : DialogFragment() {

    companion object{
        const val TAG = "AlertDialogFragment"
        fun instance(alertDialogListener: AlertDialogListener, alertType : AlertType): AlertDialogFragment {
            return AlertDialogFragment(alertDialogListener, alertType)
        }
    }
    private val lazyContext by lazy {
        requireContext()
    }




    private var _binding: FragmentAlertDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlertDialogBinding.inflate(inflater, container, false)
        val root: View = binding.root
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        val mForegroundSuccess7 = ContextCompat.getColor(lazyContext, R.color.progress_color_7)

        when (alertType) {
            AlertType.TYPE1 -> {
                binding.alertDialogIV.setImageResource(R.drawable.ic_exclamation_icon_raw)


                binding.alertDialogTV1.text = "3개의 후기를 정말 삭제하시겠습니까?"
                binding.alertDialogButton3.visibility = View.INVISIBLE
                binding.alertDialogTV2.text = "이 작업은 취소할 수 없습니다."

                binding.alertDialogButton1.text = "취소"
                binding.alertDialogButton2.text = "삭제"
                binding.alertDialogButton3.visibility = View.INVISIBLE
            }

            AlertType.TYPE2 -> {
                binding.alertDialogIV.setImageResource(R.drawable.ic_exclamation_icon_raw)

                binding.alertDialogTV1.text = "이전 전형에 합격을 선택하셔야"
                binding.alertDialogTV2.visibility = View.INVISIBLE
                binding.alertDialogTV3.text = "다음 전형으로 넘어갈 수 있습니다."

                binding.alertDialogButton3.text = "확인"
                binding.alertDialogButton1.visibility = View.INVISIBLE
                binding.alertDialogButton2.visibility = View.INVISIBLE
            }

            AlertType.TYPE3 -> {
                binding.alertDialogIV.setImageResource(R.drawable.ic_exclamation_icon_raw)

                binding.alertDialogTV1.text = "작성을 취소하고 나가시겠습니까?"
                binding.alertDialogTV3.visibility = View.INVISIBLE
                binding.alertDialogTV2.text = "작성하던 내용이 저장되지 않습니다."

                binding.alertDialogButton1.text = "취소"
                binding.alertDialogButton2.text = "나가기"
                binding.alertDialogButton3.visibility = View.INVISIBLE

            }
            AlertType.TYPE4 -> {
                binding.alertDialogIV.setImageResource(R.drawable.ic_check_icon_raw)

                binding.alertDialogTV1.text = "저장이 완료되었습니다!"
                binding.alertDialogTV1.setTextColor(mForegroundSuccess7)

                binding.alertDialogTV3.visibility = View.INVISIBLE
                binding.alertDialogTV2.text = "지원현황에서 확인해 보세요."

                binding.alertDialogButton1.text = "계속 수정"
                binding.alertDialogButton2.text = "나가기"
                binding.alertDialogButton3.visibility = View.INVISIBLE
            }

        }


        return root
    }




}