package com.example.testcase.feature_user.presentation.tap_user.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.testcase.feature_user.domain.model.Repository

@Composable
fun UserInfo(
    repo: Repository
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
            .padding(10.dp),
        color = MaterialTheme.colorScheme.inverseOnSurface
    ) {
        Column(
            horizontalAlignment = Alignment.End
        ) {
            if (repo.language != null) {
                Text(
                    text = repo.language,
                    style = MaterialTheme.typography.labelLarge,
                    fontStyle = FontStyle.Italic,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = repo.full_name,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = repo.visibility,
                style = MaterialTheme.typography.labelLarge,
                fontStyle = FontStyle.Italic,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 8.dp)
            )
        }
    }
}