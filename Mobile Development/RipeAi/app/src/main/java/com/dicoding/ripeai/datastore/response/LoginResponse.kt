package com.dicoding.ripeai.datastore.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class LoginResponse(
    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String
)

@Parcelize
data class Data (
    @field:SerializedName("id")
    val id: String ? = null,

    @field:SerializedName("firstname")
    val firstname: String ? = null,

    @field:SerializedName("lastname")
    val lastname: String ? = null,

    @field:SerializedName("phone")
    val phone: String ? = null,

    @field:SerializedName("email")
    val email: String ? = null,

    @field:SerializedName("password")
    val password: String ? = null
): Parcelable