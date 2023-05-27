package com.longterm.artschools.ui.components.onboarding.userInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.longterm.artschools.R
import com.longterm.artschools.domain.usecase.RegisterUseCase
import com.longterm.artschools.ui.components.common.ButtonGroup
import com.longterm.artschools.ui.core.theme.ArtTextStyle
import com.longterm.artschools.ui.core.theme.Colors
import com.longterm.artschools.ui.core.utils.PreviewContext
import org.koin.androidx.compose.getViewModel
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun OnboardingUserInfoScreen(nextPage: () -> Unit = {}, skip: () -> Unit = {}) {
    val registerUseCase: RegisterUseCase = koinInject()
    val vm: OnboardingUserInfoViewModel = getViewModel { parametersOf(registerUseCase) }
    val state by vm.state.collectAsState()

    if (state.navigateNext) {
        nextPage()
        vm.onNavigateNext()
    }

    if (state.skip) {
        skip()
        vm.onSkip()
    }

    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .aspectRatio(1.6f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_select_photo),
                contentDescription = null,
                tint = Colors.GreenMain
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = stringResource(id = R.string.onboarding_user_info_select_photo),
                style = ArtTextStyle.Button,
                color = Colors.GreenMain
            )
        }

        Column(
            Modifier
                .background(
                    Color.White,
                    RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                )
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.onboarding_user_info_title),
                style = ArtTextStyle.H1
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = stringResource(id = R.string.onboarding_user_info_subtitle),
                style = ArtTextStyle.Body
            )
            Spacer(modifier = Modifier.size(30.dp))
            TextField(
                value = state.name,
                onValueChange = vm::onNameChanged,
                Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    containerColor = Color.White,
                    focusedIndicatorColor = Colors.GreenMain,
                    unfocusedIndicatorColor = Colors.GreenMain,
                    disabledIndicatorColor = Colors.GreenMain,
                    unfocusedLabelColor = Colors.GreyLight,
                    disabledLabelColor = Colors.GreyLight,
                    focusedLabelColor = Colors.GreyLight
                ),
                label = {
                    Text("Ваше имя", style = ArtTextStyle.Body, color = Colors.GreyLight)
                }
            )
            Spacer(modifier = Modifier.size(12.dp))
            TextField(
                value = state.age?.toString() ?: "",
                onValueChange = vm::onAgeChanged,
                Modifier.fillMaxWidth(),
                label = {
                    Text("Сколько вам лет", style = ArtTextStyle.Body, color = Colors.GreyLight)
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    containerColor = Color.White,
                    focusedIndicatorColor = Colors.GreenMain,
                    unfocusedIndicatorColor = Colors.GreenMain,
                    disabledIndicatorColor = Colors.GreenMain,
                    unfocusedLabelColor = Colors.GreyLight,
                    disabledLabelColor = Colors.GreyLight,
                    focusedLabelColor = Colors.GreyLight
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                isError = state.ageError != null,
                supportingText = {
                    val error = state.ageError

                    if (error != null)
                        Text(text = error)
                },
            )
            Spacer(modifier = Modifier.weight(1f))
            ButtonGroup(
                primaryText = stringResource(id = R.string.onboarding_user_info_next_button),
                secondaryText = stringResource(id = R.string.onboarding_user_info_cancel_button),
                primaryButtonClicked = {
                    vm.navigateNext()
                },
                secondaryButtonClicked = {
                    vm.skip()
                },
                primaryEnabled = state.nextButtonEnabled
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        OnboardingUserInfoScreen()
    }
}