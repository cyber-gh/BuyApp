package com.personal.buyapp.ui.login.ui.login

import com.personal.buyapp.ifrastructure.UserType

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val displayName: String,
    val userType: UserType = UserType.SELLER
    //... other data fields that may be accessible to the UI
)
