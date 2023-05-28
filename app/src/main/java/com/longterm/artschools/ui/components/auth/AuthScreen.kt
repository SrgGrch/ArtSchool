package com.longterm.artschools.ui.components.auth


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.longterm.artschools.R
import com.longterm.artschools.ui.components.common.SimpleSnackBar
import com.longterm.artschools.ui.components.onboarding.register.components.RegisterDoneButton
import com.longterm.artschools.ui.components.onboarding.register.components.RegisterEmailTextField
import com.longterm.artschools.ui.components.vkauth.components.OnVkAuthResult
import com.longterm.artschools.ui.core.theme.Colors
import com.longterm.artschools.ui.core.theme.Dimens
import com.longterm.artschools.ui.core.utils.PreviewContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import kotlin.time.Duration.Companion.seconds

@Composable
fun AuthScreen(
    navigateToVkAuth: (OnVkAuthResult) -> Unit,
    navigateToMainScreen: () -> Unit,
    back: () -> Unit
) {
    val viewModel: AuthViewModel = getViewModel()
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    val errors = (state as? AuthViewModel.State.InternalAuth)?.errors

    Box {
        Column(modifier = Modifier.padding(horizontal = Dimens.horizontalPadding)) {
            IconButton(onClick = back) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "назад",
                    tint = Colors.GreenMain
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
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
                is AuthViewModel.State.Initial -> AuthInitialStateView(viewModel, navigateToVkAuth)
                is AuthViewModel.State.InternalAuth -> AuthInternalStateView(st, viewModel::onPasswordChanged)
                is AuthViewModel.State.Done -> LaunchedEffect(key1 = state, block = { navigateToMainScreen() })
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

        Column {
            Spacer(modifier = Modifier.weight(1f))
            if (state.showError) {
                LaunchedEffect(key1 = Unit) {
                    scope.launch {
                        delay(2.seconds)
                        viewModel.onErrorShowed()
                    }
                }

                SimpleSnackBar(text = stringResource(id = R.string.emailOrPasswordWrong))
            }
        }
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
        AuthScreen({}, {}, {})
    }
}