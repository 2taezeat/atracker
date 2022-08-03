package com.cmc.atracker.utils

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.cmc.atracker.R

class ChangeUIState(context : Context) {

    private var context: Context? = null

    init {
        this.context = context
    }

    fun viewEnable(inputTextView: TextView, vararg b0: Boolean) {
        var resultBoolean = true
        for (b in b0)
            resultBoolean = resultBoolean && b

        inputTextView.isEnabled = resultBoolean

        if (resultBoolean) {
            inputTextView.setTextColor(ContextCompat.getColor(context!!, R.color.progress_color_7))
        } else {
            inputTextView.setTextColor(ContextCompat.getColor(context!!, R.color.atracker_gray_3))
        }



    }


}

