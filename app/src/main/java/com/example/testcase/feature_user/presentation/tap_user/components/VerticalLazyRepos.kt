package com.example.testcase.feature_user.presentation.tap_user.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.testcase.feature_user.presentation.tap_user.ReposViewModel
import com.example.testcase.feature_user.presentation.users.components.CircularIndeterminateProgressBar

@Composable
fun VerticalLazyRepos(
    navController: NavController,
    reposViewModel: ReposViewModel
) {

    val isLoading by reposViewModel.loading
    val repos by reposViewModel.repos

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        if (!isLoading) {
            LazyColumn {
                repeat(repos.size) {
                    item {
                        UserInfo(
                            repo = reposViewModel.repos.value[it]
                        )
                    }
                }
            }
        }
        CircularIndeterminateProgressBar(isLoading)
    }
}