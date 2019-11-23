package com.personal.buyapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.personal.buyapp.ifrastructure.APIUtils
import com.personal.buyapp.ifrastructure.TestResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun testAPI() {
        APIUtils.apiService.postUser(TestResponse("username_test")).enqueue(object :
            Callback<TestResponse> {
            override fun onFailure(call: Call<TestResponse>, t: Throwable) {
                Log.w("qwer", "request failed" + t.localizedMessage)
            }

            override fun onResponse(call: Call<TestResponse>, response: Response<TestResponse>) {
                Log.w("qwer", "reques done " + response.body())
            }

        })
    }
}