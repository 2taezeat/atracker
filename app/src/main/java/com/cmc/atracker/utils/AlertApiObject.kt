package com.cmc.atracker.utils

import com.cmc.atracker.ui.AlertDialogFragment
import com.cmc.atracker.ui.AlertDialogListener


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

