package com.personal.buyapp.ifrastructure

import androidx.lifecycle.MutableLiveData

object Repository {

    var token: String = ""
    var userName: String = ""
    var userType: UserType = UserType.SELLER
    var userTypeLiveData = MutableLiveData<UserType>()

    var followRedirect = true



    var currentReceipt: ReceiptData? = null

    fun refreshNfc() {
        userTypeLiveData.postValue(userType)
    }

    fun generateMessage() = "${currentReceipt!!.id}.${userName}"


    var currentBalance = MutableLiveData<Int>().apply { value = (1500..4500).random() }

    //MArk bullshit below
    var sellerUserName: String = ""

    var sellDaataready = MutableLiveData<Boolean>()


    fun updateBalance(diffValue: Long?) {
        val currentBalance = currentBalance.value
        if (diffValue == null || currentBalance == null) return;

        val newValue = if (userType == UserType.BUYER) {
            currentBalance - diffValue
        } else {
            currentBalance + diffValue
        }

        this.currentBalance.postValue(newValue.toInt())
    }
}