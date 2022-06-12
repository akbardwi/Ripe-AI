package com.dicoding.ripeai.ui.signup

import androidx.lifecycle.ViewModel
import com.dicoding.ripeai.datastore.repository.UserRepository

class SignupViewModel(private val repo: UserRepository) : ViewModel() {

    fun register(firstname: String,lastname: String, phone: String, email: String, password: String) = repo.register(firstname,lastname,phone, email, password)
}