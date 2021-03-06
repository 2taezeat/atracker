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
                binding.alertDialogTV2.text = "??? ????????? ????????? ??? ????????????."

                binding.alertDialogButton1.text = "??????"
                binding.alertDialogButton2.text = "??????"
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
                binding.alertDialogTV1.text = "?????? ????????? ????????? ???????????????"
                binding.alertDialogTV1.setTextColor(ContextCompat.getColor(lazyContext, R.color.atracker_white))

                binding.alertDialogTV2.visibility = View.INVISIBLE
                binding.alertDialogTV3.text = "?????? ???????????? ????????? ??? ????????????."

                binding.alertDialogButton3.text = "??????"
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
                binding.alertDialogTV1.text = "????????? ???????????? ??????????????????????"
                binding.alertDialogTV1.setTextColor(ContextCompat.getColor(lazyContext, R.color.atracker_white))

                binding.alertDialogTV3.visibility = View.INVISIBLE
                binding.alertDialogTV2.text = "???????????? ????????? ???????????? ????????????."

                binding.alertDialogButton1.text = "??????"
                binding.alertDialogButton2.text = "?????????"
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
                binding.alertDialogTV1.text = "????????? ?????????????????????!"
                binding.alertDialogTV1.setTextColor(mForegroundSuccess7)

                binding.alertDialogTV3.visibility = View.INVISIBLE
                binding.alertDialogTV2.text = "?????????????????? ????????? ?????????."

                binding.alertDialogButton1.text = "?????? ??????"
                binding.alertDialogButton2.text = "?????????"
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

                val testString1 = "???????????? ???????????????"
                val ssb = SpannableStringBuilder(testString1)
                ssb.setSpan(ForegroundColorSpan(Color.parseColor("#7EFBDC")), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.alertDialogTV1.text = ssb
                binding.alertDialogTV1.setTextColor(ContextCompat.getColor(lazyContext, R.color.atracker_white))

                binding.alertDialogTV2.visibility = View.INVISIBLE
                binding.alertDialogTV3.text = "?????? ????????? ????????? ??? ????????????."

                binding.alertDialogButton3.text = "??????"
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

                val testString1 = "???????????? ???????????????"
                val ssb = SpannableStringBuilder(testString1)
                ssb.setSpan(ForegroundColorSpan(Color.parseColor("#7EFBDC")), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.alertDialogTV1.text = ssb
                binding.alertDialogTV1.setTextColor(ContextCompat.getColor(lazyContext, R.color.atracker_white))

                binding.alertDialogTV2.visibility = View.INVISIBLE
                binding.alertDialogTV3.text = "?????? ????????? ????????? ??? ????????????."

                binding.alertDialogButton3.text = "??????"
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

                val testString1 = "??????????????? 2??? ?????? ???????????? ???????????????"
                val ssb = SpannableStringBuilder(testString1)
                ssb.setSpan(ForegroundColorSpan(Color.parseColor("#7EFBDC")), 6, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.alertDialogTV1.text = ssb
                binding.alertDialogTV1.setTextColor(ContextCompat.getColor(lazyContext, R.color.atracker_white))

                binding.alertDialogTV2.visibility = View.INVISIBLE
                binding.alertDialogTV3.text = "?????? ????????? ????????? ??? ????????????."

                binding.alertDialogButton3.text = "??????"
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

                val testString1 = "??????????????? ?????? 7????????? ?????????????????????."
                val ssb = SpannableStringBuilder(testString1)
                ssb.setSpan(ForegroundColorSpan(Color.parseColor("#7EFBDC")), 6, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.alertDialogTV1.text = ssb
                binding.alertDialogTV1.setTextColor(ContextCompat.getColor(lazyContext, R.color.atracker_white))

                binding.alertDialogTV2.visibility = View.INVISIBLE
                binding.alertDialogTV3.text = "???????????? ?????? ??????????????????."

                binding.alertDialogButton3.text = "??????"
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

                val testString1 = "??????????????? ???????????????"
                val ssb = SpannableStringBuilder(testString1)
                ssb.setSpan(ForegroundColorSpan(Color.parseColor("#7EFBDC")), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.alertDialogTV1.text = ssb
                binding.alertDialogTV1.setTextColor(ContextCompat.getColor(lazyContext, R.color.atracker_white))

                binding.alertDialogTV2.visibility = View.INVISIBLE
                binding.alertDialogTV3.text = "?????? ????????? ????????? ??? ????????????."

                binding.alertDialogButton3.text = "??????"
                binding.alertDialogButton1.visibility = View.INVISIBLE
                binding.alertDialogButton2.visibility = View.INVISIBLE

                binding.alertDialogButton3.setOnClickListener {
                    alertDialogListener.onCenterClick()
                    dismiss()
                }
            }


            AlertType.TYPE10 -> {
                binding.alertDialogIV.setImageResource(R.drawable.ic_exclamation_icon_raw)


                val testString1 = "???????????? ???????????????????"
                val ssb = SpannableStringBuilder(testString1)
                ssb.setSpan(ForegroundColorSpan(Color.parseColor("#7EFBDC")), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.alertDialogTV1.text = ssb
                binding.alertDialogTV1.setTextColor(ContextCompat.getColor(lazyContext, R.color.atracker_white))

                binding.alertDialogTV2.visibility = View.INVISIBLE
                binding.alertDialogTV3.visibility = View.INVISIBLE

                binding.alertDialogButton1.text = "??????"
                binding.alertDialogButton2.text = "??????"
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


            AlertType.TYPE11 -> { // ?????? ??????

            }


            AlertType.TYPE12 -> {
                binding.alertDialogIV.setImageResource(R.drawable.ic_exclamation_icon_raw)

                val testString1 = "????????? ?????? ?????????????????????????"
                val ssb = SpannableStringBuilder(testString1)
                ssb.setSpan(ForegroundColorSpan(Color.parseColor("#7EFBDC")), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.alertDialogTV1.text = ssb
                binding.alertDialogTV1.setTextColor(ContextCompat.getColor(lazyContext, R.color.atracker_white))


                binding.alertDialogTV3.visibility = View.INVISIBLE
                binding.alertDialogTV2.text = "??? ????????? ???????????? ??? ????????????."

                binding.alertDialogButton1.text = "??????"
                binding.alertDialogButton2.text = "??????"
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
                binding.alertDialogTV1.text = "????????? ?????????????????????"
                binding.alertDialogTV1.setTextColor(mForegroundSuccess7)

                binding.alertDialogTV3.visibility = View.INVISIBLE
                binding.alertDialogTV2.text = "??? ???????????? ?????? ?????? ????????? ?????????????????????."

                binding.alertDialogButton3.text = "??????"
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