package com.longterm.artschools.ui.components.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.longterm.artschools.R
import com.longterm.artschools.ui.components.common.PasswordTextField
import com.longterm.artschools.ui.core.utils.PreviewContext

@Composable
fun AuthInternalStateView(
    state: AuthViewModel.State.InternalAuth,
    onPasswordChanged: (String) -> Unit = {},
) {
    val errors = (state as? AuthViewModel.State.InternalAuth)?.errors

    Column {
        PasswordTextField(
            state.password,
            stringResource(id = R.string.password),
            onPasswordChanged,
            error = errors?.password
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        AuthInternalStateView(
            state = AuthViewModel.State.InternalAuth(email = "", password = ""),
        )
    }
}