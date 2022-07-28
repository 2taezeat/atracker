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
import com.example.atracker.utils.AlertType
import android.text.Spannable

import android.text.style.ForegroundColorSpan

import android.text.SpannableStringBuilder




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
        savedInstanceState: Bundle?,
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

                val testString1 = "회사명을 선택하셔야"
                val ssb = SpannableStringBuilder(testString1)
                ssb.setSpan(ForegroundColorSpan(Color.parseColor("#7EFBDC")), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.alertDialogTV1.text = ssb
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

                val testString1 = "포지션을 선택하셔야"
                val ssb = SpannableStringBuilder(testString1)
                ssb.setSpan(ForegroundColorSpan(Color.parseColor("#7EFBDC")), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.alertDialogTV1.text = ssb
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

                val testString1 = "지원단계를 2개 이상 순서대로 입력하셔야"
                val ssb = SpannableStringBuilder(testString1)
                ssb.setSpan(ForegroundColorSpan(Color.parseColor("#7EFBDC")), 6, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.alertDialogTV1.text = ssb
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

            AlertType.TYPE8 -> {
                binding.alertDialogIV.setImageResource(R.drawable.ic_exclamation_icon_raw)
                binding.alertDialogTV0.visibility = View.INVISIBLE

                val testString1 = "지원단계는 최대 7개까지 선택가능합니다."
                val ssb = SpannableStringBuilder(testString1)
                ssb.setSpan(ForegroundColorSpan(Color.parseColor("#7EFBDC")), 6, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.alertDialogTV1.text = ssb
                binding.alertDialogTV1.setTextColor(ContextCompat.getColor(lazyContext, R.color.atracker_white))

                binding.alertDialogTV2.visibility = View.INVISIBLE
                binding.alertDialogTV3.text = "중요도에 따라 선택해주세요."

                binding.alertDialogButton3.text = "확인"
                binding.alertDialogButton1.visibility = View.INVISIBLE
                binding.alertDialogButton2.visibility = View.INVISIBLE

                binding.alertDialogButton3.setOnClickListener {
                    alertDialogListener.onCenterClick()
                    dismiss()
                }
            }

            AlertType.TYPE9 -> {
                binding.alertDialogIV.setImageResource(R.drawable.ic_exclamation_icon_raw)
                binding.alertDialogTV0.visibility = View.INVISIBLE

                val testString1 = "근무형태를 선택하셔야"
                val ssb = SpannableStringBuilder(testString1)
                ssb.setSpan(ForegroundColorSpan(Color.parseColor("#7EFBDC")), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.alertDialogTV1.text = ssb
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


            AlertType.TYPE10 -> { // 로그 아웃
                binding.alertDialogIV.setImageResource(R.drawable.ic_exclamation_icon_raw)


                val testString1 = "로그아웃 하시겠습니까?"
                val ssb = SpannableStringBuilder(testString1)
                ssb.setSpan(ForegroundColorSpan(Color.parseColor("#7EFBDC")), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.alertDialogTV1.text = ssb
                binding.alertDialogTV1.setTextColor(ContextCompat.getColor(lazyContext, R.color.atracker_white))

                binding.alertDialogTV2.visibility = View.INVISIBLE
                binding.alertDialogTV3.visibility = View.INVISIBLE

                binding.alertDialogButton1.text = "취소"
                binding.alertDialogButton2.text = "확인"
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


            AlertType.TYPE11 -> { // 회원 탈퇴 부분
                binding.alertDialogIV.setImageResource(R.drawable.ic_exclamation_icon_raw)

                val testString1 = "정말 탈퇴 하시겠습니까?"
                val ssb = SpannableStringBuilder(testString1)
                ssb.setSpan(ForegroundColorSpan(Color.parseColor("#7EFBDC")), 3, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.alertDialogTV1.text = ssb
                binding.alertDialogTV1.setTextColor(ContextCompat.getColor(lazyContext, R.color.atracker_white))


                binding.alertDialogTV3.visibility = View.INVISIBLE
                binding.alertDialogTV2.text = "이 작업은 취소하실 수 없습니다."

                binding.alertDialogButton1.text = "취소"
                binding.alertDialogButton2.text = "탈퇴"
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


            AlertType.TYPE12 -> {
                binding.alertDialogIV.setImageResource(R.drawable.ic_exclamation_icon_raw)

                val testString1 = "후기를 정말 삭제하시겠습니까?"
                val ssb = SpannableStringBuilder(testString1)
                ssb.setSpan(ForegroundColorSpan(Color.parseColor("#7EFBDC")), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.alertDialogTV1.text = ssb
                binding.alertDialogTV1.setTextColor(ContextCompat.getColor(lazyContext, R.color.atracker_white))


                binding.alertDialogTV3.visibility = View.INVISIBLE
                binding.alertDialogTV2.text = "이 작업은 취소하실 수 없습니다."

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


            AlertType.TYPE13 -> {
                binding.alertDialogIV.setImageResource(R.drawable.ic_check_icon_raw)

                binding.alertDialogTV0.visibility = View.INVISIBLE
                binding.alertDialogTV1.text = "삭제가 완료되었습니다"
                binding.alertDialogTV1.setTextColor(mForegroundSuccess7)

                binding.alertDialogTV3.visibility = View.INVISIBLE
                binding.alertDialogTV2.text = "홈 화면에서 해당 지원 후기가 삭제되었습니다."

                binding.alertDialogButton3.text = "확인"
                binding.alertDialogButton1.visibility = View.INVISIBLE
                binding.alertDialogButton2.visibility = View.INVISIBLE

                binding.alertDialogButton3.setOnClickListener {
                    alertDialogListener.onCenterClick()
                    dismiss()
                }
            }

            AlertType.TYPE14 -> {
                binding.alertDialogIV.setImageResource(R.drawable.ic_exclamation_icon_raw)
                binding.alertDialogTV0.visibility = View.INVISIBLE


                binding.alertDialogTV1.visibility = View.INVISIBLE
                binding.alertDialogTV2.visibility = View.INVISIBLE
                binding.alertDialogTV3.text = "다시 한번 시도 해주세요!"

                binding.alertDialogButton3.text = "확인"
                binding.alertDialogButton1.visibility = View.INVISIBLE
                binding.alertDialogButton2.visibility = View.INVISIBLE

                binding.alertDialogButton3.setOnClickListener {
                    alertDialogListener.onCenterClick()
                    dismiss()
                }
            }
            AlertType.TYPE15 -> { // 편집이 완료 되었습니다. apply Edit, 오직, 확인
                binding.alertDialogIV.setImageResource(R.drawable.ic_check_icon_raw)

                binding.alertDialogTV0.visibility = View.INVISIBLE
                binding.alertDialogTV1.text = "지현 현황 편집이 완료되었습니다!"
                binding.alertDialogTV1.setTextColor(mForegroundSuccess7)

                binding.alertDialogTV3.visibility = View.INVISIBLE
                binding.alertDialogTV2.text = "지원현황에서 확인해 보세요."

                binding.alertDialogButton3.text = "확인"
                binding.alertDialogButton1.visibility = View.INVISIBLE
                binding.alertDialogButton2.visibility = View.INVISIBLE

                binding.alertDialogButton3.setOnClickListener {
                    alertDialogListener.onCenterClick()
                    dismiss()
                }
                this.isCancelable = false

            }
            AlertType.TYPE16 -> { // 추가가 완료 되었습니다. apply Add, 오직, 확인
                binding.alertDialogIV.setImageResource(R.drawable.ic_check_icon_raw)

                binding.alertDialogTV0.visibility = View.INVISIBLE
                binding.alertDialogTV1.text = "지원 현황 추가가 완료되었습니다!"
                binding.alertDialogTV1.setTextColor(mForegroundSuccess7)

                binding.alertDialogTV3.visibility = View.INVISIBLE
                binding.alertDialogTV2.text = "지원현황에서 확인해 보세요."

                binding.alertDialogButton3.text = "확인"
                binding.alertDialogButton1.visibility = View.INVISIBLE
                binding.alertDialogButton2.visibility = View.INVISIBLE

                binding.alertDialogButton3.setOnClickListener {
                    alertDialogListener.onCenterClick()
                    dismiss()
                }
                this.isCancelable = false

            }
            AlertType.TYPE17 -> { // 취소하고 나가시겠습니까?. apply Edit, Add 둘다 적용
                binding.alertDialogIV.setImageResource(R.drawable.ic_exclamation_icon_raw)

                binding.alertDialogTV0.visibility = View.INVISIBLE
                binding.alertDialogTV1.text = "취소하고 나가시겠습니까?"
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
            }
        }

        return root
    }


    override fun dismiss() {
        super.dismiss()
    }

}