package com.e.vpdmoney.model.response

import com.google.gson.annotations.SerializedName

data class KeyValue <T>(
    @SerializedName("name")
    val name: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("id")
    val id: String,
    val `object`: T
) {
    override fun toString(): String = name
}

data class KeyValues<T>(
        val keyValues: List<KeyValue<T>>
)
