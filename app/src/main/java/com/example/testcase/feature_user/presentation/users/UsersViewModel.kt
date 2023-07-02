package com.example.testcase.feature_user.presentation.users

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.testcase.feature_user.domain.model.User
import com.example.testcase.feature_user.data.repository.UserRepository
import com.example.testcase.feature_user.domain.model.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(application: Application) : ViewModel() {

    private val context = application.applicationContext
    val loading = mutableStateOf(true)
    val pageSize = mutableStateOf(0)
    val rowCount = 5
    val columnCount = 4
    val gridSize = rowCount * columnCount

    val items: MutableState<List<User>> = mutableStateOf(ArrayList())
    val repos: MutableState<List<Repository>> = mutableStateOf(ArrayList())
    private val userRepository = UserRepository(context)

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

    fun getReposCall(clickedUser: String) {
        loading.value = true
        userRepository.getRepositoriesFromAPI(clickedUser) { reposList ->
            repos.value = reposList!!
            loading.value = false
        }
    }
}