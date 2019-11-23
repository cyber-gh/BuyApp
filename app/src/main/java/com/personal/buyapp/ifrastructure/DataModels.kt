package com.personal.buyapp.ifrastructure

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class UserType {
    BUYER, SELLER
}

data class TestResponse(val name: String?)

data class LoggedInUser(val token: String?, val username: String?, val profile: Number?)

data class LoginRequest(val username: String?, val password: String?, val profile: Number?)


data class ProductRegisterParams(val token: String?, val product_stock: Product_stock?)

data class Product_stock(val id: String?, val name: String?, val price: Number?, val total_available: Number?)


@Serializable
data class WarehouseProduct (
    val id: String,
    val name: String,
    val price: Long,

    @SerialName("total_available")
    val totalAvailable: Long,

    @SerialName("total_sold")
    val totalSold: Long,

    val status: Long? = null
)


@Serializable
data class GetProductParams (
    val token: String,
    val id: String
)

data class ProductWrapper(val warehouseProduct: WarehouseProduct,
                          var quantity: Int = 1)

fun ProductWrapper.getTotalPrice() = warehouseProduct.price * quantity;


@Serializable
data class ReceiptParams (
    val token: String,
    val products: List<ProductMinimal>
)

@Serializable
data class ProductMinimal (
    val id: String,
    val quantity: Double
)

@Serializable
data class ReceiptData (
    val id: Long,
    val products: List<ProductMinimal>,
    val total: Long,
    val status: Long
)

