package com.personal.buyapp.ui.receipt

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.buyapp.ifrastructure.*
import com.personal.buyapp.utils.execptionHandler
import kotlinx.coroutines.launch



class ScanProductViewModel: ViewModel() {

    val warehouseProductMutable = MutableLiveData<WarehouseProduct>()

    val productListMutableLiveData = MutableLiveData<List<ProductWrapper>>()

    var productList = mutableListOf<ProductWrapper>()

    var shouldGoBack = MutableLiveData<Boolean>()


    var receiptDataLive = MutableLiveData<ReceiptData>()


    fun getProductById(id: String) {

        viewModelScope.launch(execptionHandler) {
            val prod = AppClient.getProduct(Repository.token, id)
            warehouseProductMutable.postValue(prod)

        }
    }

    fun addProduct(newProductWrapper: ProductWrapper) {
        val element : ProductWrapper? = productList.firstOrNull { it.warehouseProduct.id == newProductWrapper.warehouseProduct.id }
        if (element == null) {
            productList.add(newProductWrapper)
        } else {
            val newList = mutableListOf<ProductWrapper>()
            for (el in 0 until productList.count()) {
                if (productList[el].warehouseProduct.id == newProductWrapper.warehouseProduct.id) {
                    productList[el].quantity += newProductWrapper.quantity
                }
            }

            //productList = newList
        }

        productListMutableLiveData.postValue(productList)
    }

    fun getReceipt() {
        viewModelScope.launch(execptionHandler) {
            val receipt = AppClient.createReceipt(Repository.token, productList)

            if (receipt.products.isEmpty()) return@launch
            infoAlert("Receipt created")
            Repository.currentReceipt = receipt
            receiptDataLive.postValue(receipt)

        }
    }

}