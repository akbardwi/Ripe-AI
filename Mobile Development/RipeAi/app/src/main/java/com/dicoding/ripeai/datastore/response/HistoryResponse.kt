package com.dicoding.ripeai.datastore.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HistoryResponse(

    @field:SerializedName("data")
    val data: ArrayList<DataItem>,

    @field:SerializedName("success")
    val success: Boolean
) : Parcelable

@Parcelize
data class DataItem(

    @field:SerializedName("img_url")
    val imgUrl: String,

    @field:SerializedName("fruit")
    val fruit: String,

    @field:SerializedName("ripeness")
    val ripeness: String
) : Parcelable
