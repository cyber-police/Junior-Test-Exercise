package com.example.testcase.feature_user.presentation.users.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.testcase.feature_user.domain.model.User

@Composable
fun UserItem(
    user: User,
    navController: NavController
) {
    Surface(
        onClick = {
            navController.navigate("profile/${user.login}")
        },
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .height(150.dp)
            .width(100.dp)
            .padding(10.dp),
        color = MaterialTheme.colorScheme.inverseOnSurface
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(user.avatar_url),
                contentDescription = "User image",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Text(
                text = user.login,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                modifier = Modifier
                    .padding(
                        horizontal = 10.dp, vertical = 8.dp
                    )
            )
            Text(
                text = user.type,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
        }
    }
}