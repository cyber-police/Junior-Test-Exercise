package com.example.testcase.feature_user.presentation.users

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.testcase.TestApp
import com.example.testcase.feature_user.domain.model.User
import com.example.testcase.feature_user.data.repository.UserRepository

class UsersViewModel : ViewModel() {

    val loading = mutableStateOf(true)
    val pageSize = mutableStateOf(0)
    val rowCount = 5
    val columnCount = 4
    val gridSize = rowCount * columnCount

    val items: MutableState<List<User>> = mutableStateOf(ArrayList())
    private val userRepository = UserRepository(TestApp.applicationContext())

    init {
        getUsersCall()
    }

    private fun getUsersCall() {
        loading.value = true
        userRepository.getUsersFromAPI { userList ->
            items.value = userList!!
            loading.value = false
        }
    }
}