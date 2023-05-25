package com.longterm.artschools.ui.components.auth

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.longterm.artschools.R
import com.longterm.artschools.ui.core.theme.Colors

@Composable
fun AuthEmailTextField(viewModel: AuthViewModel, state: AuthViewModel.State) {
    val focusRequester = remember { FocusRequester() }
//    val st by viewModel.state.collectAsState()
    val value = (state as? AuthViewModel.State.InternalAuth)?.email ?: ""
    val textState by remember { mutableStateOf(TextFieldValue(value, TextRange(value.length))) }

    TextField(
        value = textState,
        onValueChange = viewModel::emailChanged,
        label = { Text(text = stringResource(id = R.string.yourEmail)) },
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
        modifier = Modifier
            .padding(horizontal = hPadding.dp)
            .fillMaxWidth()
//            .focusRequester(focusRequester)
    )

    if (state is AuthViewModel.State.InternalAuth) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}