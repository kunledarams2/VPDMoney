package com.e.vpdmoney.db


data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?, msg: String? ): Resource<T> {
            return Resource(Status.SUCCESS, data, msg)
        }

        fun <T> error(msg: String?, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T? = null, msg: String?=""): Resource<T> {
            return Resource(Status.LOADING, data, msg)
        }
    }
}