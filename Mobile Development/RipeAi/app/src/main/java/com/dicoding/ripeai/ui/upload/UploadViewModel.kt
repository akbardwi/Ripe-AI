package com.dicoding.ripeai.ui.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.ripeai.datastore.repository.UserRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadViewModel(
    private val userRepo: UserRepository,
//    private val storyRepo: StoryRepository
) : ViewModel() {

    fun getToken(): LiveData<String> {
        return userRepo.getToken().asLiveData()
    }

    fun uploadStory(
        token: String,
        imageMultipart: MultipartBody.Part
    ) = userRepo.uploadStory(token, imageMultipart)
}