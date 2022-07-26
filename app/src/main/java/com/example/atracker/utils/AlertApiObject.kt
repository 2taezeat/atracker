package com.example.atracker.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.atracker.ui.AlertDialogFragment
import com.example.atracker.ui.AlertDialogListener


object AlertApiObject {
    val alertDialogFragment = AlertDialogFragment.instance(
    object : AlertDialogListener {
        override fun onLeftClick() {
        }

        override fun onCenterClick() {

        }

        override fun onRightClick() {
        }
    },
    AlertType.TYPE14,
    null
    )


}

