package com.example.testcase.feature_user.domain.repository

import com.example.testcase.feature_user.domain.model.Repository
import com.example.testcase.feature_user.domain.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("users")
    fun getUsers(): Call<List<User>>

    @GET("users/{login}/repos")
    fun getUserRepos(@Path("login") login: String): Call<List<Repository>>
}