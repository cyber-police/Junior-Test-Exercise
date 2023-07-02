package com.example.testcase.feature_user.presentation.tap_user.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.testcase.feature_user.domain.model.Repository

@Composable
fun UserInfo(
    repo: Repository,
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .height(150.dp)
            .width(100.dp)
            .padding(10.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = repo.full_name,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
            Text(
                text = repo.visibility,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
        }
    }
}