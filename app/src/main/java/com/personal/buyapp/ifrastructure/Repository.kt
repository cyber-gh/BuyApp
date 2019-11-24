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



    //MArk bullshit below
    var sellerUserName: String = ""

    var sellDaataready = MutableLiveData<Boolean>()
}