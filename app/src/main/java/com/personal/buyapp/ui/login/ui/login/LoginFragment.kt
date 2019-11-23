package com.personal.buyapp.ui.login.ui.login

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.personal.buyapp.R
import com.personal.buyapp.ifrastructure.LoggedInUser
import com.personal.buyapp.ifrastructure.UserType
import com.personal.buyapp.ui.home.HomeViewModel
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

        login.setOnClickListener {
            val log = username.text.toString()
            val pass = password.text.toString()
            loginViewModel.loginUser(log, pass, UserType.SELLER)
        }

        loginViewModel.loggedInUserLiveData.observe(viewLifecycleOwner, Observer {
            if (it.profile!!.toInt() == UserType.SELLER.ordinal) {
                findNavController().navigate(R.id.action_loginFragment_to_seller_home_fragment)
            }
        })

    }

}
