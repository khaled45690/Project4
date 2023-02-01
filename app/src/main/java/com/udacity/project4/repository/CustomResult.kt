package com.udacity.project4.repository


/**
 * A sealed class that encapsulates successful outcome with a value of type [T]
 * or a failure with message and statusCode
 */
sealed class CustomResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : CustomResult<T>()
    data class Error(val message: String?, val statusCode: Int? = null) :
        CustomResult<Nothing>()
}