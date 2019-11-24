package com.personal.buyapp.ui.login.ui.login

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import com.personal.buyapp.R
import com.personal.buyapp.ifrastructure.Repository
import com.personal.buyapp.ifrastructure.UserType
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel =
            ViewModelProviders.of(this).get(LoginViewModel::class.java)
        if (Repository.token != "" && Repository.userType == UserType.BUYER) {
            findNavController().navigate(R.id.action_loginFragment_to_buyerHomeFragment)
            return
        }
        login_buyer.setOnClickListener {
            val inputMethodManager = context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            val log = username.text.toString()
            val pass = password.text.toString()
            loginViewModel.loginUser(log, pass, UserType.BUYER)
        }


        login_seller.setOnClickListener {
            val inputMethodManager = context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            val log = username.text.toString()
            val pass = password.text.toString()
            loginViewModel.loginUser(log, pass, UserType.SELLER)
        }


        loginViewModel.loggedInUserLiveData.observe(viewLifecycleOwner, Observer {
            Repository.userName = it.username!!
            if (it.profile!!.toInt() == UserType.SELLER.ordinal) {
                Repository.userType = UserType.SELLER
                Repository.userTypeLiveData.postValue(UserType.SELLER)
                findNavController().navigate(R.id.action_loginFragment_to_seller_home_fragment)

            } else {
                Repository.userType = UserType.BUYER
                Repository.userTypeLiveData.postValue(UserType.BUYER)
                findNavController().navigate(R.id.action_loginFragment_to_buyerHomeFragment)
            }
        })

    }

    override fun onResume() {
        super.onResume()
    }

}
