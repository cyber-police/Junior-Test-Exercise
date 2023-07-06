package com.example.testcase.feature_user.presentation.tap_user

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.testcase.TestApp
import com.example.testcase.feature_user.data.repository.UserRepository
import com.example.testcase.feature_user.domain.model.Repository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class ReposViewModel @AssistedInject constructor(
    @Assisted private val clickedUser: String
) : ViewModel() {

    val loading = mutableStateOf(true)

    val repos: MutableState<List<Repository>> = mutableStateOf(ArrayList())
    private val userRepository = UserRepository(TestApp.applicationContext())

    init {
        getReposCall()
    }

    private fun getReposCall() {
        loading.value = true
        userRepository.getRepositoriesFromAPI(clickedUser) { reposList ->
            repos.value = reposList!!
            loading.value = false
        }
    }
}