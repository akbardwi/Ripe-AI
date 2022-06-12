package com.dicoding.ripeai.datastore.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class RegisterResponse(

    @field:SerializedName("data")
    val data: Datareg? =null,

    @field:SerializedName("success")
    val success: Boolean

)
@Parcelize
data class Datareg (
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("firstname")
    val firstname: String,

    @field:SerializedName("lastname")
    val lastname: String,

    @field:SerializedName("phone")
    val phone: String,

    @field:SerializedName("email")
    val email: String
):Parcelable
