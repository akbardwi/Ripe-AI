package com.dicoding.ripeai.datastore.response

import com.google.gson.annotations.SerializedName

class UploadResponse(
    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String
)

