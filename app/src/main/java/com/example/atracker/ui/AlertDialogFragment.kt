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

class AlertDialogFragment(private val alertDialogListener: AlertDialogListener, private val alertType : AlertType, private val countNum : Int? = null ) : DialogFragment() {

    companion object{
        const val TAG = "AlertDialogFragment"
        fun instance(alertDialogListener: AlertDialogListener, alertType : AlertType, countNum: Int? ): AlertDialogFragment {
            return AlertDialogFragment(alertDialogListener, alertType, countNum)
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


//        binding.alertDialogButton1.setOnClickListener {
//            alertDialogListener.onLeftClick()
//        }
//
//        binding.alertDialogButton3.setOnClickListener {
//            alertDialogListener.onCenterClick()
//            dismiss()
//        }
//
//        binding.alertDialogButton2.setOnClickListener {
//            alertDialogListener.onRightClick()
//        }

        when (alertType) {
            AlertType.TYPE1 -> {
                binding.alertDialogIV.setImageResource(R.drawable.ic_exclamation_icon_raw)

                binding.alertDialogTV0.text = countNum.toString()
                binding.alertDialogButton3.visibility = View.INVISIBLE
                binding.alertDialogTV2.text = "이 작업은 취소할 수 없습니다."

                binding.alertDialogButton1.text = "취소"
                binding.alertDialogButton2.text = "삭제"
                binding.alertDialogButton3.visibility = View.INVISIBLE

                binding.alertDialogButton1.setOnClickListener {
                    alertDialogListener.onLeftClick()
                    dismiss()
                }

                binding.alertDialogButton2.setOnClickListener {
                    alertDialogListener.onRightClick()
                    dismiss()
                }
            }

            AlertType.TYPE2 -> {
                binding.alertDialogIV.setImageResource(R.drawable.ic_exclamation_icon_raw)

                binding.alertDialogTV0.visibility = View.INVISIBLE
                binding.alertDialogTV1.text = "이전 전형에 합격을 선택하셔야"
                binding.alertDialogTV1.setTextColor(ContextCompat.getColor(lazyContext, R.color.atracker_white))

                binding.alertDialogTV2.visibility = View.INVISIBLE
                binding.alertDialogTV3.text = "다음 전형으로 넘어갈 수 있습니다."

                binding.alertDialogButton3.text = "확인"
                binding.alertDialogButton1.visibility = View.INVISIBLE
                binding.alertDialogButton2.visibility = View.INVISIBLE

                binding.alertDialogButton3.setOnClickListener {
                    alertDialogListener.onCenterClick()
                    dismiss()
                }
            }

            AlertType.TYPE3 -> {
                binding.alertDialogIV.setImageResource(R.drawable.ic_exclamation_icon_raw)

                binding.alertDialogTV0.visibility = View.INVISIBLE
                binding.alertDialogTV1.text = "작성을 취소하고 나가시겠습니까?"
                binding.alertDialogTV1.setTextColor(ContextCompat.getColor(lazyContext, R.color.atracker_white))

                binding.alertDialogTV3.visibility = View.INVISIBLE
                binding.alertDialogTV2.text = "작성하던 내용이 저장되지 않습니다."

                binding.alertDialogButton1.text = "취소"
                binding.alertDialogButton2.text = "나가기"
                binding.alertDialogButton3.visibility = View.INVISIBLE

                binding.alertDialogButton1.setOnClickListener {
                    alertDialogListener.onLeftClick()
                    dismiss()
                }

                binding.alertDialogButton2.setOnClickListener {
                    alertDialogListener.onRightClick()
                    dismiss()
                }

                this.isCancelable = false

            }
            AlertType.TYPE4 -> {
                binding.alertDialogIV.setImageResource(R.drawable.ic_check_icon_raw)

                binding.alertDialogTV0.visibility = View.INVISIBLE
                binding.alertDialogTV1.text = "저장이 완료되었습니다!"
                binding.alertDialogTV1.setTextColor(mForegroundSuccess7)

                binding.alertDialogTV3.visibility = View.INVISIBLE
                binding.alertDialogTV2.text = "지원현황에서 확인해 보세요."

                binding.alertDialogButton1.text = "계속 수정"
                binding.alertDialogButton2.text = "나가기"
                binding.alertDialogButton3.visibility = View.INVISIBLE

                binding.alertDialogButton1.setOnClickListener {
                    alertDialogListener.onLeftClick()
                    dismiss()
                }

                binding.alertDialogButton2.setOnClickListener {
                    alertDialogListener.onRightClick()
                    dismiss()
                }
            }

            AlertType.TYPE5 -> {
                binding.alertDialogIV.setImageResource(R.drawable.ic_exclamation_icon_raw)

                binding.alertDialogTV0.visibility = View.INVISIBLE
                binding.alertDialogTV1.text = "회사명을 선택하셔야"
                binding.alertDialogTV1.setTextColor(ContextCompat.getColor(lazyContext, R.color.atracker_white))

                binding.alertDialogTV2.visibility = View.INVISIBLE
                binding.alertDialogTV3.text = "다음 단계로 넘어갈 수 있습니다."

                binding.alertDialogButton3.text = "확인"
                binding.alertDialogButton1.visibility = View.INVISIBLE
                binding.alertDialogButton2.visibility = View.INVISIBLE

                binding.alertDialogButton3.setOnClickListener {
                    alertDialogListener.onCenterClick()
                    dismiss()
                }
            }

            AlertType.TYPE6 -> {
                binding.alertDialogIV.setImageResource(R.drawable.ic_exclamation_icon_raw)

                binding.alertDialogTV0.visibility = View.INVISIBLE
                binding.alertDialogTV1.text = "지원분야를 선택하셔야"
                binding.alertDialogTV1.setTextColor(ContextCompat.getColor(lazyContext, R.color.atracker_white))

                binding.alertDialogTV2.visibility = View.INVISIBLE
                binding.alertDialogTV3.text = "다음 단계로 넘어갈 수 있습니다."

                binding.alertDialogButton3.text = "확인"
                binding.alertDialogButton1.visibility = View.INVISIBLE
                binding.alertDialogButton2.visibility = View.INVISIBLE

                binding.alertDialogButton3.setOnClickListener {
                    alertDialogListener.onCenterClick()
                    dismiss()
                }
            }

            AlertType.TYPE7 -> {
                binding.alertDialogIV.setImageResource(R.drawable.ic_exclamation_icon_raw)

                binding.alertDialogTV0.visibility = View.INVISIBLE
                binding.alertDialogTV1.text = "지원단계를 2개 이상 순서대로 입력하셔야"
                binding.alertDialogTV1.setTextColor(ContextCompat.getColor(lazyContext, R.color.atracker_white))

                binding.alertDialogTV2.visibility = View.INVISIBLE
                binding.alertDialogTV3.text = "다음 단계로 넘어갈 수 있습니다."

                binding.alertDialogButton3.text = "확인"
                binding.alertDialogButton1.visibility = View.INVISIBLE
                binding.alertDialogButton2.visibility = View.INVISIBLE

                binding.alertDialogButton3.setOnClickListener {
                    alertDialogListener.onCenterClick()
                    dismiss()
                }
            }

        }

        return root
    }


    override fun dismiss() {
        super.dismiss()
    }

}