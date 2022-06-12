package com.dicoding.ripeai.datastore.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.ripeai.datastore.api.ApiService
import com.dicoding.ripeai.datastore.response.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.dicoding.ripeai.ui.Result
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call

class UserRepository private constructor(
    private val dataStore: DataStore<Preferences>,
    private val apiService: ApiService
) {
    fun getHistory(email: String): LiveData<Result<Call<HistoryResponse>>> = liveData{
        emit(Result.Loading)
        try {
            val client = apiService.getHistory(email)
            emit(Result.Success(client))
        }catch (e : java.lang.Exception){
            Log.d("UserRepository", "getHistory: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }
    fun uploadStory(
        token: String,
        imageMultipart: MultipartBody.Part
    ): LiveData<Result<UploadResponse>> = liveData {
        emit(Result.Loading)
        try {
            val client = apiService.uploadStory("Bearer $token", imageMultipart)
            emit(Result.Success(client))
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("StoryRepository", "uploadStory: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun register(
        firstname: String,
        lastname: String,
        phone: String,
        email: String,
        password: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val result = apiService.register(firstname,lastname,phone,email,password)
            emit(Result.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val result = apiService.login(email, password)
            emit(Result.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN] ?: ""
        }
    }

    fun isLogin(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[STATE_KEY] ?: false
        }
    }

    suspend fun setToken(token: String, isLogin: Boolean) {
        dataStore.edit { preferences ->
            preferences[TOKEN] = token
            preferences[STATE_KEY] = isLogin
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[TOKEN] = ""
            preferences[STATE_KEY] = false
        }
    }



    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        private val TOKEN = stringPreferencesKey("token")
        private val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(
            dataStore: DataStore<Preferences>,
            apiService: ApiService
        ): UserRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = UserRepository(dataStore, apiService)
                INSTANCE = instance
                instance
            }
        }
    }
}