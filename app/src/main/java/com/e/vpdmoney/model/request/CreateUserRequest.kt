package com.e.vpdmoney.model.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreateUserRequest(

 val full_name:String,
 val organization_name :String,
 val email :String,
 val phone_number :String,
 val password :String,
 val country :String,
 val business_category :String,
 val accepted_terms : Boolean,
):Parcelable
