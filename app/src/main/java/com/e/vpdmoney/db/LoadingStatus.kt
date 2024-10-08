package com.e.vpdmoney.db

sealed class LoadingStatus {

    object Success : LoadingStatus()
    class Loading(val message: String) : LoadingStatus()
    data class Error(val errorCode: Int, val errorMessage: String) : LoadingStatus()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success"
            is Error -> "Error[$errorCode: $errorMessage]"
            is Loading -> "Loading"
        }
    }
}
