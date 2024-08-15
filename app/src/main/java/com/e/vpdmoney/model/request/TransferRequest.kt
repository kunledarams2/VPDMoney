package com.e.vpdmoney.model.request

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Transfer", indices = [Index(value = ["reference"], unique = true)])
@Parcelize
data class TransferRequest(
    @PrimaryKey
    val reference:String,
    val receiver:String,
    val receiver_account_number :String,
    val receiver_bank :String,
    val amount :Int,
    val sender:String,
    val sender_account_number:String,
    val narration:String,
    val payment_method:String,
    val transaction_date:String,
    val transaction_status:String
): Parcelable
