package com.example.testcase.feature_user.presentation.tap_user.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.testcase.feature_user.presentation.users.UsersViewModel
import com.example.testcase.feature_user.presentation.users.components.CircularIndeterminateProgressBar

@Composable
fun VerticalLazyRepos(
    navController: NavController,
    string: String?,
    usersViewModel: UsersViewModel = hiltViewModel()
) {
    string?.let { usersViewModel.getReposCall(it) }

    val isLoading by usersViewModel.loading
    val repos by usersViewModel.repos

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (!isLoading) {
            LazyColumn {
                repeat(repos.size) {
                    item {
                        UserInfo(
                            repo = usersViewModel.repos.value[it],
                            navController = navController
                        )
                    }
                }
            }
        } else {
            CircularIndeterminateProgressBar()
        }
    }
}