package com.longterm.artschools.ui.components.onboarding.register


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.longterm.artschools.di.OnboardingScope
import com.longterm.artschools.domain.usecase.RegisterUseCase
import com.longterm.artschools.ui.components.onboarding.register.components.RegisterDoneButton
import com.longterm.artschools.ui.components.onboarding.register.components.RegisterEmailTextField
import com.longterm.artschools.ui.components.onboarding.register.components.RegisterInitialStateView
import com.longterm.artschools.ui.components.onboarding.register.components.RegisterInternalStateView
import com.longterm.artschools.ui.components.vkauth.components.OnVkAuthResult
import com.longterm.artschools.ui.core.theme.Dimens.horizontalPadding
import com.longterm.artschools.ui.core.utils.PreviewContext
import org.koin.androidx.compose.getViewModel
import org.koin.compose.koinInject
import org.koin.compose.scope.KoinScope
import org.koin.core.parameter.parametersOf

@Composable
fun RegisterScreen(
    onNavigateToVk: (OnVkAuthResult) -> Unit,
    navigateToMainScreen: () -> Unit
) {
    val registerUseCase: RegisterUseCase = koinInject()
    val vm: RegisterViewModel = getViewModel { parametersOf(registerUseCase) }
    val state by vm.state.collectAsState()

    val errors = (state as? RegisterViewModel.State.InternalRegister)?.errors

    Column(
        modifier = Modifier
            .padding(horizontal = horizontalPadding)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(id = R.string.onboarding_register_title),
            color = Color.Black,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = horizontalPadding, vertical = 10.dp)
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = stringResource(id = R.string.onboarding_register_subtitle),
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(horizontal = horizontalPadding)
        )
        Spacer(modifier = Modifier.size(12.dp))
        RegisterEmailTextField(
            value = state.email,
            hint = stringResource(id = R.string.yourEmail),
            onValueChange = vm::onEmailChanged,
            error = errors?.email
        )
        Spacer(modifier = Modifier.size(12.dp))

        when (val st = state) {
            is RegisterViewModel.State.Initial -> RegisterInitialStateView(vm, onNavigateToVk)
            is RegisterViewModel.State.InternalRegister -> RegisterInternalStateView(
                st,
                vm::onPasswordChanged,
                vm::onRepeatChanged
            )

            is RegisterViewModel.State.Done -> LaunchedEffect(key1 = state, block = { navigateToMainScreen() })
        }

        Spacer(modifier = Modifier.weight(1f))

        RegisterDoneButton(
            enabled = state.isDoneButtonEnabled,
            onClick = vm::onDoneClicked
        )

        Spacer(Modifier.height(24.dp))
    }
}


@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        KoinScope(scopeDefinition = { createScope<OnboardingScope>() }) {
            RegisterScreen({}, {})
        }
    }
}