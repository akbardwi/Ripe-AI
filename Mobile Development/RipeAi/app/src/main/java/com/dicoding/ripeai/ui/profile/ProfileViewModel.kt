package com.dicoding.ripeai.ui.profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.dicoding.ripeai.datastore.api.ApiConfig
import com.dicoding.ripeai.datastore.repository.UserRepository
import com.dicoding.ripeai.datastore.response.DataUser
import com.dicoding.ripeai.datastore.response.UserResponse
import com.dicoding.ripeai.ui.signup.SignUpActivity.Companion.TAG
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(private val userRepo: UserRepository) : ViewModel() {

    private var viewModelJob = Job()

    fun getToken(): LiveData<String> {
        return userRepo.getToken().asLiveData()
    }
    fun logout() {
        viewModelScope.launch {
            userRepo.logout()
        }
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }

}