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

interface APIService {

    @POST("/api/test")
    fun postUser(@Body userRequest: TestResponse ) : Call<TestResponse>

    @POST("/api/login")
    fun loginUser(@Body loginRequest: LoginRequest) : Call<LoggedInUser>

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
                it.resumeWith(Result.success(response.body()!!))
            }

        })
    }
}