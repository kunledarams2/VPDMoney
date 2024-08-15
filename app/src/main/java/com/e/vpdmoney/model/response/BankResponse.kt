package com.e.vpdmoney.model.response

data class BankResponse (
    val success: Boolean,
    val message: String,
    val data: List<BankDatum>
)


data class BankDatum (
    val id: String,
    val name: String,
    val code: String
)