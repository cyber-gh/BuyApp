package com.personal.buyapp.utils

import com.personal.buyapp.ifrastructure.EmptyResponse
import com.personal.buyapp.ifrastructure.errAlert
import kotlinx.coroutines.CoroutineExceptionHandler

val execptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
    println(throwable.localizedMessage)
    if (throwable is EmptyResponse) {
        errAlert(throwable.description)
    } else {
        errAlert(throwable.localizedMessage)
    }
}