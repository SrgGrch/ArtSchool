package com.longterm.artschools.ui.components.onboarding.register.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.longterm.artschools.R
import com.longterm.artschools.ui.components.common.PasswordTextField
import com.longterm.artschools.ui.components.onboarding.register.RegisterViewModel
import com.longterm.artschools.ui.core.utils.PreviewContext


@Composable
fun RegisterInternalStateView(
    state: RegisterViewModel.State.InternalRegister,
    onPasswordChanged: (String) -> Unit = {},
    onRepeatPasswordChanged: (String) -> Unit = {}
) {
    val errors = (state as? RegisterViewModel.State.InternalRegister)?.errors

    Column {
        PasswordTextField(
            state.password,
            stringResource(id = R.string.password),
            onPasswordChanged,
            error = errors?.password
        )
        Spacer(modifier = Modifier.size(12.dp))

        PasswordTextField(
            state.repeatPassword,
            stringResource(id = R.string.repeat_password),
            onRepeatPasswordChanged,
            error = errors?.repeatPassword
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        RegisterInternalStateView(
            state = RegisterViewModel.State.InternalRegister(email = "", password = ""),
        )
    }
}