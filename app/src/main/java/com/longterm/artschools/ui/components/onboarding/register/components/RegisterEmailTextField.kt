package com.longterm.artschools.ui.components.onboarding.register.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.longterm.artschools.ui.core.theme.ArtTextStyle
import com.longterm.artschools.ui.core.theme.Colors

@Composable
fun RegisterEmailTextField(
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
    error: String? = null
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
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
        modifier = Modifier
            .fillMaxWidth()
    )
}