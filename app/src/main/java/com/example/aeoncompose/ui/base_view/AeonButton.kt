package com.example.aeoncompose.ui.base_view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AeonButtonText(text: String, clickable: (() -> Unit)) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable(onClick = clickable)
            .border(
                BorderStroke(width = 1.dp, color = Color.White),
                RoundedCornerShape(10.dp)
            )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.button,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}