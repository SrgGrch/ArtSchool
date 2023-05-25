package com.longterm.artschools.ui.components.onboarding.register


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.longterm.artschools.ui.components.onboarding.register.components.RegisterInitialStateView
import com.longterm.artschools.ui.components.onboarding.register.components.RegisterInternalStateView
import com.longterm.artschools.ui.core.theme.Dimens.horizontalPadding
import com.longterm.artschools.ui.core.utils.PreviewContext
import org.koin.androidx.compose.getViewModel

@Composable
fun RegisterScreen() {
    val viewModel: RegisterViewModel = getViewModel()
    val state by viewModel.state.collectAsState()

    val errors = (state as? RegisterViewModel.State.InternalRegister)?.errors

    Column(modifier = Modifier.padding(horizontal = horizontalPadding)) {
        Text(
            text = stringResource(id = R.string.login),
            color = Color.Black,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = horizontalPadding, vertical = 10.dp)
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = stringResource(id = R.string.enterEmailOrLoginViaVk),
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(horizontal = horizontalPadding)
        )
        Spacer(modifier = Modifier.size(12.dp))
        RegisterEmailTextField(
            value = state.email,
            hint = stringResource(id = R.string.yourEmail),
            onValueChange = viewModel::onEmailChanged,
            error = errors?.email
        )
        Spacer(modifier = Modifier.size(12.dp))

        when (val st = state) {
            is RegisterViewModel.State.Initial -> RegisterInitialStateView(viewModel)
            is RegisterViewModel.State.InternalRegister -> RegisterInternalStateView(
                st,
                viewModel::onPasswordChanged,
                viewModel::onRepeatChanged
            )

            is RegisterViewModel.State.Vk -> Unit // todo
            is RegisterViewModel.State.Done -> Unit // todo
        }

        Spacer(modifier = Modifier.weight(1f))

        RegisterDoneButton(
            enabled = state.isDoneButtonEnabled,
            onClick = viewModel::onDoneClicked
        )

        Spacer(Modifier.height(24.dp))
    }
}


@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        RegisterScreen()
    }
}