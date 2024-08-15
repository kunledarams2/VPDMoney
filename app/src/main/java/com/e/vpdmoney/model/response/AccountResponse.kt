package com.e.vpdmoney.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class AccountResponse (
    val success: Boolean,
    val message: String,
    val data: List<AccountDatum>
): Parcelable

@Parcelize
data class AccountDatum (
    val id: Long,

    val account_name: String,

    val account_balance: String,

    val currency: String,

    val account_number: String,

    val bank_name: String,

    val account_holder: String,

    val account_status: String
):Parcelable