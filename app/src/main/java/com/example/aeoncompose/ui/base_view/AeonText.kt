package com.example.aeoncompose.ui.base_view

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AeonTextField(
    label: String, value: String, onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    keyboardActions: KeyboardActions = KeyboardActions(onDone = { }),
    inputType: VisualTransformation = VisualTransformation.None
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    TextField(
        value = value,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        onValueChange = onValueChange,
        placeholder = {
            Text(label)
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        modifier = modifier.drawWithContent {
            drawContent()
            if (isFocused) {
                val strokeWidth = 2.dp.value * density
                val y = size.height - strokeWidth / 2
                drawLine(
                    Color.White,
                    Offset((15.dp).toPx(), y),
                    Offset(size.width - 15.dp.toPx(), y),
                    strokeWidth
                )
            } else {
                val strokeWidth = 1.dp.value * density
                val y = size.height - strokeWidth / 2
                drawLine(
                    Color.White,
                    Offset((15.dp).toPx(), y),
                    Offset(size.width - 15.dp.toPx(), y),
                    strokeWidth
                )
            }
        },
        visualTransformation = inputType,
        singleLine = true
    )
}