package com.longterm.artschools.ui.components.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.longterm.artschools.R
import com.longterm.artschools.ui.core.theme.Colors
import com.longterm.artschools.ui.core.utils.PreviewContext
import org.koin.androidx.compose.getViewModel


@Composable
fun AuthInternalStateView(viewModel: AuthViewModel, state: AuthViewModel.State) {
    Column {
        IconButton(
            onClick = viewModel::close,
            modifier = Modifier
                .align(Alignment.End)
                .padding(horizontal = 4.dp),
        ) {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = null,
                tint = Colors.Green,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = stringResource(id = R.string.enterPassword),
            color = Color.Black,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = hPadding.dp, vertical = 10.dp)
        )
        Spacer(modifier = Modifier.size(spacerSize.dp))
        AuthEmailTextField(viewModel, state)
        Spacer(modifier = Modifier.size(spacerSize.dp))
        PasswordTextField(viewModel, state)
        Spacer(modifier = Modifier.weight(1f))
        AuthDoneButton(viewModel = viewModel, state = state, bottomPadding = 10.dp)
        TextButton(
            onClick = viewModel::doNotRememberPasswordClicked,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(id = R.string.doNotRememberPassword),
                color = Colors.Green,
                fontSize = 18.sp,
                fontWeight = FontWeight.W700
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PasswordTextField(viewModel: AuthViewModel, state: AuthViewModel.State) {
    val value = (state as? AuthViewModel.State.InternalAuth)?.password ?: ""
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }

    TextField(
        value = TextFieldValue(value, TextRange(value.length)),
        onValueChange = viewModel::passwordChanged,
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
        label = { Text(text = stringResource(id = R.string.password)) },
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
    )
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        AuthInternalStateView(
            viewModel = getViewModel(),
            state = AuthViewModel.State.InternalAuth(email = "", password = ""),
        )
    }
}