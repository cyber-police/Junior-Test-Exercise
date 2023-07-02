package com.example.testcase.feature_user.presentation.users.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.testcase.feature_user.presentation.users.UsersViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.touchlane.gridpad.GridPad
import com.touchlane.gridpad.GridPadCells
import kotlin.math.ceil

@ExperimentalPagerApi
@Composable
fun ViewPager(
    navController: NavController,
    usersViewModel: UsersViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(
        initialPage = 0
    )

    val isLoading by usersViewModel.loading
    var currentElement: Int
    val count = ceil(usersViewModel.items.value.size / usersViewModel.gridSize.toDouble()).toInt()

    if (!isLoading) {
        HorizontalPager(
            count = count,
            state = pagerState
        ) { page ->
            GridPad(
                cells = GridPadCells(
                    rowCount = usersViewModel.rowCount,
                    columnCount = usersViewModel.columnCount
                )
            ) {
                currentElement = page * usersViewModel.gridSize - 1
                if (usersViewModel.items.value.size < usersViewModel.gridSize) {
                    usersViewModel.pageSize.value = usersViewModel.items.value.size
                } else if (usersViewModel.items.value.size / usersViewModel.gridSize.toDouble() > usersViewModel.items.value.size / usersViewModel.gridSize && page == usersViewModel.items.value.size / usersViewModel.gridSize) {
                    usersViewModel.pageSize.value =
                        usersViewModel.items.value.size % usersViewModel.gridSize
                } else {
                    usersViewModel.pageSize.value = usersViewModel.gridSize
                }
                repeat(usersViewModel.pageSize.value) {
                    item {
                        UserItem(
                            user = usersViewModel.items.value[++currentElement],
                            navController = navController
                        )
                    }
                }
            }
        }
        Row(
            Modifier
                .height(50.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            repeat(count) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)

                )
            }
        }
    } else {
        CircularIndeterminateProgressBar()
    }
}