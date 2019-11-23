package com.personal.buyapp.ui.login.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.personal.buyapp.R
import com.personal.buyapp.ifrastructure.AppClient
import com.personal.buyapp.ifrastructure.LoggedInUser
import com.personal.buyapp.ifrastructure.UserType
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {

    val loggedInUserLiveData = MutableLiveData<LoggedInUser>()

    fun loginUser(login: String, password: String, userType: UserType = UserType.SELLER) {
        viewModelScope.launch {
            val usr = AppClient.loginUser(login, password, userType)
            loggedInUserLiveData.postValue(usr)
        }
    }

}
