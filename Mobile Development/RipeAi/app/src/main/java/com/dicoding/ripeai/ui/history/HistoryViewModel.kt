package com.dicoding.ripeai.ui.history

import android.util.Log
import androidx.lifecycle.*
import com.dicoding.ripeai.datastore.api.ApiConfig
import com.dicoding.ripeai.datastore.repository.UserRepository
import com.dicoding.ripeai.datastore.response.DataItem
import com.dicoding.ripeai.datastore.response.HistoryResponse
import com.dicoding.ripeai.ui.signup.SignUpActivity.Companion.TAG
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel(private val userRepo: UserRepository): ViewModel() {
    private val _history = MutableLiveData<HistoryResponse>()
    val history: LiveData<HistoryResponse> = _history
    fun getHistory(email: String) = userRepo.getHistory(email)
    fun getToken(): LiveData<String> {
        return userRepo.getToken().asLiveData()
    }
    fun logout() {
        viewModelScope.launch {
            userRepo.logout()
        }
    }

}