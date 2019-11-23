package com.personal.buyapp.ifrastructure

enum class UserType {
    BUYER, SELLER
}

data class TestResponse(val name: String?)

data class LoggedInUser(val token: String?, val username: String?, val profile: Number?)

data class LoginRequest(val username: String?, val password: String?, val profile: Number?)