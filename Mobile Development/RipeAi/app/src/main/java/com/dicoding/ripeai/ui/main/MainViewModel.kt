package com.dicoding.ripeai.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.ripeai.datastore.repository.UserRepository
import kotlinx.coroutines.launch

class MainViewModel (private val userRepo: UserRepository) :
    ViewModel() {

    fun getToken(): LiveData<String> {
        return userRepo.getToken().asLiveData()
    }

    fun isLogin(): LiveData<Boolean> {
        return userRepo.isLogin().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            userRepo.logout()
        }
    }

//    fun getHistory(email: String) = userRepo.getHistory(email)
}