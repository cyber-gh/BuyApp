package com.personal.buyapp.ui.buyer

import androidx.lifecycle.ViewModel
import com.personal.buyapp.R
import java.util.Collections.swap

class BuyerHomeViewModel : ViewModel() {
    var halfViewColor : Int = R.color.colorPrimary
    var rootColor : Int = android.R.color.white

    fun swapState() {
        val tmp = halfViewColor
        halfViewColor = rootColor
        rootColor = tmp
    }
}
