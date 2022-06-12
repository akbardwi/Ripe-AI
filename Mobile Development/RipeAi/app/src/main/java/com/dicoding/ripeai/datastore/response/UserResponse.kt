package com.dicoding.ripeai.datastore.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserResponse(

	@field:SerializedName("data")
	val data: ArrayList<DataUser>,

	@field:SerializedName("success")
	val success: Boolean
) : Parcelable

@Parcelize
data class DataUser(

	@field:SerializedName("firstname")
	val firstname: String,

	@field:SerializedName("lastname")
	val lastname: String,

	@field:SerializedName("phone")
	val phone: String,

	@field:SerializedName("email")
	val email: String,

) : Parcelable
