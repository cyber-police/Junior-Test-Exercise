package com.example.testcase.feature_user.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.testcase.feature_user.presentation.tap_user.components.VerticalLazyRepos
import com.example.testcase.feature_user.presentation.users.components.ViewPager
import com.example.testcase.feature_user.presentation.ui.theme.TestCaseTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalPagerApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestCaseTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            ViewPager(navController)
                        }
                    }
                    composable(
                        "profile/{userLogin}",
                        arguments = listOf(navArgument("userLogin") { type = NavType.StringType })
                    ) { backStackEntry ->
                        VerticalLazyRepos(
                            navController,
                            backStackEntry.arguments?.getString("userLogin")
                        )
                    }
                }
            }
        }
    }
}