package com.longterm.artschools.ui.components.auth


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.longterm.artschools.R
import com.longterm.artschools.ui.components.onboarding.register.components.RegisterDoneButton
import com.longterm.artschools.ui.components.onboarding.register.components.RegisterEmailTextField
import com.longterm.artschools.ui.core.theme.Colors
import com.longterm.artschools.ui.core.theme.Dimens
import com.longterm.artschools.ui.core.utils.PreviewContext
import org.koin.androidx.compose.getViewModel

@Composable
fun AuthScreen() {
    val viewModel: AuthViewModel = getViewModel()
    val state by viewModel.state.collectAsState()

    val errors = (state as? AuthViewModel.State.InternalAuth)?.errors

    Column(modifier = Modifier.padding(horizontal = Dimens.horizontalPadding)) {
        IconButton(
            onClick = viewModel::onCloseClicked,
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
            text = if (state is AuthViewModel.State.Initial) stringResource(id = R.string.login)
            else stringResource(id = R.string.enterPassword),
            color = Color.Black,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = Dimens.horizontalPadding, vertical = 10.dp)
        )
        Spacer(modifier = Modifier.size(12.dp))
        if (state is AuthViewModel.State.Initial) {
            Text(
                text = stringResource(id = R.string.enterEmailOrLoginViaVk),
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(horizontal = Dimens.horizontalPadding)
            )
        }

        Spacer(modifier = Modifier.size(12.dp))
        RegisterEmailTextField(
            value = state.email,
            hint = stringResource(id = R.string.yourEmail),
            onValueChange = viewModel::onEmailChanged,
            error = errors?.email
        )
        Spacer(modifier = Modifier.size(12.dp))

        when (val st = state) {
            is AuthViewModel.State.Initial -> AuthInitialStateView(viewModel)
            is AuthViewModel.State.InternalAuth -> AuthInternalStateView(st, viewModel::onPasswordChanged)
            is AuthViewModel.State.Vk -> Unit // todo
            is AuthViewModel.State.Done -> Unit // todo
            is AuthViewModel.State.Close -> Unit // todo
        }

        Spacer(modifier = Modifier.weight(1f))

        RegisterDoneButton(
            enabled = state.isDoneButtonEnabled,
            onClick = viewModel::onDoneClicked
        )
        if (state is AuthViewModel.State.InternalAuth) {
            Spacer(modifier = Modifier.size(12.dp))
            DoNotRememberPasswordButton(viewModel::onDontRememberPasswordClicked)
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun ColumnScope.DoNotRememberPasswordButton(onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
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

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        AuthScreen()
    }
}