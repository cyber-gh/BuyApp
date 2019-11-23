package com.personal.buyapp.ui.warehouse

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.buyapp.ifrastructure.AppClient
import com.personal.buyapp.ifrastructure.ProductRegisterParams
import com.personal.buyapp.ifrastructure.Product_stock
import com.personal.buyapp.ifrastructure.Repository
import kotlinx.coroutines.launch

class ProductDetailsViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    var barcode: String = ""

    val mutableCallFinished = MutableLiveData<Boolean>()

    fun addProduct(
        productBarcode : String = barcode,
        productPrice: Number,
        productStock: Number,
        productName : String
    ) {
        viewModelScope.launch {
            val requestData = ProductRegisterParams(
                token = Repository.token,
                product_stock = Product_stock(
                    id = productBarcode,
                    name = productName,
                    price = productPrice,
                    total_available = productStock

                )
            )
            AppClient.registerProduct(
                requestData
            )
            mutableCallFinished.postValue(true)

        }
    }
}
