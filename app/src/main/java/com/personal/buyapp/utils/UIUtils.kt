package com.personal.buyapp.utils

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toastl(message: String) {
    Toast.makeText(context!!, message, Toast.LENGTH_LONG).show()
}