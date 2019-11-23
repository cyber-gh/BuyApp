package com.personal.buyapp.ifrastructure

import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resumeWithException

 class EmptyResponse(val description: String = "Test") : Throwable()

interface APIService {

    @POST("/api/test")
    fun postUser(@Body userRequest: TestResponse ) : Call<TestResponse>

    @POST("/api/login")
    fun loginUser(@Body loginRequest: LoginRequest) : Call<LoggedInUser>

    @POST("/api/product/add")
    fun addProduct(@Body productParams: ProductRegisterParams) : Call<Void>

    @POST("/api/product/get")
    fun getProduct(@Body getProductParams: GetProductParams) : Call<WarehouseProduct>

    @POST("/api/receipt/create")
    fun getReceipt(@Body receiptParams: ReceiptParams) : Call<ReceiptData>

}

object APIUtils {
    val BASE_URL = "http://192.168.1.147:8080"

    val apiService : APIService
        get() = RetrofitClient.getClient(BASE_URL)!!.create(APIService::class.java)
}

object RetrofitClient {

    var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit? {
        if (retrofit == null) {
            //TODO While release in Google Play Change the Level to NONE

            val client = OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build()

            retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit

    }
}

object AppClient {


    suspend fun loginUser(login: String, password: String, userType: UserType = UserType.SELLER) = suspendCancellableCoroutine<LoggedInUser> {
        APIUtils.apiService.loginUser(LoginRequest(login, password, userType.ordinal)).enqueue( object :
            Callback<LoggedInUser> {
            override fun onFailure(call: Call<LoggedInUser>, t: Throwable) {
                it.resumeWith(Result.failure(t))
            }

            override fun onResponse(call: Call<LoggedInUser>, response: Response<LoggedInUser>) {
                if (response.code() == 200) {
                    it.resumeWith(Result.success(response.body()!!))
                } else {
                    it.resumeWithException(EmptyResponse("Wrong Username or password"))
                }
            }

        })
    }

    suspend fun registerProduct(productParams: ProductRegisterParams) = suspendCancellableCoroutine<EmptyResponse> {
        APIUtils.apiService.addProduct(productParams).enqueue( object :
            Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                it.resumeWith(Result.failure(t))
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                it.resumeWith(Result.success(EmptyResponse("Unable to register product")))
            }

        })
    }

    suspend fun getProduct(token: String, id: String) = suspendCancellableCoroutine<WarehouseProduct> {
        APIUtils.apiService.getProduct(GetProductParams(token, id)).enqueue( object :
            Callback<WarehouseProduct> {
            override fun onFailure(call: Call<WarehouseProduct>, t: Throwable) {
                it.resumeWith(Result.failure(t))
            }

            override fun onResponse(call: Call<WarehouseProduct>, response: Response<WarehouseProduct>) {
                if (response.code() == 200) {
                    it.resumeWith(Result.success(response.body()!!))
                }else {
                    it.resumeWithException(EmptyResponse("Product not found"))
                }
            }

        })
    }

    suspend fun getReceipt(token: String, productList : List<ProductWrapper>) = suspendCancellableCoroutine<ReceiptData> {

        val receiptParams = ReceiptParams(token, productList.map { ProductMinimal(it.warehouseProduct.id, it.quantity.toDouble()) })

        APIUtils.apiService.getReceipt(receiptParams).enqueue( object :
            Callback<ReceiptData> {
            override fun onFailure(call: Call<ReceiptData>, t: Throwable) {
                it.resumeWith(Result.failure(t))
            }

            override fun onResponse(call: Call<ReceiptData>, response: Response<ReceiptData>) {
                if (response.code() == 200) {
                    it.resumeWith(Result.success(response.body()!!))
                }else {
                    it.resumeWithException(EmptyResponse("Can't create receipt"))
                }
            }

        })
    }

}