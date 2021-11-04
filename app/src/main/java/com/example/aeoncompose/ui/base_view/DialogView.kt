package com.example.aeoncompose.ui.base_view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.aeoncompose.R
import com.example.aeoncompose.extensions.onClick


@Composable
fun DialogLoading() {
    Dialog(
        onDismissRequest = { },
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DialogSingle(
    message: String,
    onDismissRequest: (() -> Unit) = {}
) {

    Dialog(
        onDismissRequest = { onDismissRequest.invoke() },
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(White, shape = RoundedCornerShape(8.dp))
                .padding(10.dp)
        ) {
            Column {
                Image(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                    modifier = Modifier
                        .align(Alignment.End)
                        .onClick { onDismissRequest.invoke() }
                )

                Text(
                    text = "Thông báo",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.body1,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = message,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color(0xFF000000),
                    style = MaterialTheme.typography.button,
                )

                Spacer(modifier = Modifier.height(30.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(50.dp)
                        .clickable { onDismissRequest.invoke() }
                        .background(MaterialTheme.colors.primary, RoundedCornerShape(10.dp))
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Đóng",
                        style = MaterialTheme.typography.button,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}