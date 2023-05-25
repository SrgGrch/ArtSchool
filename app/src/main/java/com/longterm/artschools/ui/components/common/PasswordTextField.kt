package com.longterm.artschools.ui.components.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.longterm.artschools.R
import com.longterm.artschools.ui.core.theme.ArtTextStyle
import com.longterm.artschools.ui.core.theme.Colors
import com.longterm.artschools.ui.core.utils.PreviewContext

@Composable
fun PasswordTextField(
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    error: String? = null
) {
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }) {
                Icon(
                    if (passwordVisibility) painterResource(id = R.drawable.ic_password_not_hidden)
                    else painterResource(id = R.drawable.ic_password_hidden),
                    contentDescription = null
                )
            }
        },
        label = { Text(text = hint) },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            containerColor = Color.White,
            focusedIndicatorColor = Colors.Green,
            unfocusedIndicatorColor = Colors.Green,
            disabledIndicatorColor = Colors.Green,
            unfocusedLabelColor = Color.Gray,
            disabledLabelColor = Color.Gray,
            focusedLabelColor = Color.Gray
        ),
        isError = error != null,
        supportingText = {
            if (error != null)
                Text(error, style = ArtTextStyle.Chips)
        },
        modifier = modifier
            .fillMaxWidth()
    )
}

@Preview
@Composable
private fun Preview() {
    PreviewContext {
        PasswordTextField("asdasd", "asdasd", { })
    }
}