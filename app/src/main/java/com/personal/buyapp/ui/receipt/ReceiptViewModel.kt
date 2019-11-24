package com.personal.buyapp.ui.receipt

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.buyapp.ifrastructure.AppClient
import com.personal.buyapp.ifrastructure.GeneratedReceipt
import com.personal.buyapp.ifrastructure.ReceiptData
import com.personal.buyapp.ifrastructure.Repository
import com.personal.buyapp.utils.execptionHandler
import kotlinx.coroutines.launch

class ReceiptViewModel : ViewModel() {
    lateinit var receiptData: ReceiptData

    val generatedreceipt = MutableLiveData<GeneratedReceipt>()

    fun getGeneratedReceipt() {
        viewModelScope.launch (execptionHandler){
            val receipt = AppClient.getGeneratedReceipt(Repository.token, receiptData.id)

            generatedreceipt.postValue(receipt)
        }
    }
}
