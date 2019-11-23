package com.personal.buyapp.ifrastructure

import android.app.Activity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.personal.buyapp.R

object Router {
    lateinit var acitivity: Activity

}


fun errAlert(message: String = "Server error") {
    MaterialAlertDialogBuilder(Router.acitivity, R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
        .setTitle("Error")
        .setMessage(message)
        .setPositiveButton("Ok", null)
        .show()
}

fun infoAlert(message: String = "Info") {
    MaterialAlertDialogBuilder(Router.acitivity, R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
        .setTitle("Info")
        .setMessage(message)
        .setPositiveButton("Ok", null)
        .show()
}