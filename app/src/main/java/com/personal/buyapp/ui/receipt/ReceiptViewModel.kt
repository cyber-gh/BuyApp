package com.personal.buyapp.ui.receipt

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.buyapp.ifrastructure.*
import com.personal.buyapp.utils.execptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ReceiptViewModel : ViewModel() {
    lateinit var receiptData: ReceiptData
    var receiptId : Long = 0

    val generatedreceipt = MutableLiveData<GeneratedReceipt>()

    val paymentIsFinished = MutableLiveData<Boolean>()

    var checkingWasLauchedFlag = false

    fun getGeneratedReceipt() {
        viewModelScope.launch (execptionHandler){
            val receipt = AppClient.getGeneratedReceipt(Repository.token, receiptId)

            generatedreceipt.postValue(receipt)
        }
    }

    fun confirmPayment() {
        viewModelScope.launch(execptionHandler) {
            val status = AppClient.confirmPayment(Repository.token, receiptId, Repository.sellerUserName, Repository.userName)
//            infoAlert("Request done")
            paymentIsFinished.postValue(true)
        }
    }

    fun checkForStatusComplete() {
        if (checkingWasLauchedFlag) return
        checkingWasLauchedFlag = true
        viewModelScope.launch(execptionHandler) {
            while ( true ) {
                val receipt = AppClient.getGeneratedReceipt(Repository.token, receiptId)
                if (receipt.status == 1.toLong()) break;
                delay(1000)
            }
            paymentIsFinished.postValue(true)
        }
    }
}
