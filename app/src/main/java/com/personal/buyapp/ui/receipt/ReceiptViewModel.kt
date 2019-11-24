package com.personal.buyapp.ui.receipt

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.buyapp.ifrastructure.*
import com.personal.buyapp.utils.execptionHandler
import kotlinx.coroutines.launch

class ReceiptViewModel : ViewModel() {
    lateinit var receiptData: ReceiptData
    var receiptId : Long = 0

    val generatedreceipt = MutableLiveData<GeneratedReceipt>()

    fun getGeneratedReceipt() {
        viewModelScope.launch (execptionHandler){
            val receipt = AppClient.getGeneratedReceipt(Repository.token, receiptId)

            generatedreceipt.postValue(receipt)
        }
    }

    fun confirmPayment() {
        viewModelScope.launch(execptionHandler) {
            val status = AppClient.confirmPayment(Repository.token, receiptId, Repository.sellerUserName, Repository.userName)
            infoAlert("Request done")
        }
    }
}
