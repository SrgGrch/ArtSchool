package com.longterm.artschools.ui.components.onboarding.target

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
fun OnboardingTargetScreen(
    nextPage: () -> Unit = {},
    skip: () -> Unit = {}
) {
    val registerUseCase: RegisterUseCase = koinInject()
    val vm: OnboardingTargetViewModel = getViewModel { parametersOf(registerUseCase) }
    val state by vm.state.collectAsState()

    when (state) {
        is OnboardingTargetViewModel.State.NextPage -> {
            LaunchedEffect(
                key1 = Unit,
                block = { nextPage() }
            )

            vm.nextPageInvoked()
        }

        is OnboardingTargetViewModel.State.NotNow -> {
            LaunchedEffect(
                key1 = Unit,
                block = { skip() }
            )

            vm.onSkip()
        }

        else -> Unit
    }

    Column(Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Text(
            text = stringResource(id = R.string.onboarding_target_title),
            Modifier.padding(vertical = 10.dp),
            style = ArtTextStyle.H1
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = stringResource(id = R.string.onboarding_target_description),
            style = ArtTextStyle.Body
        )
        Spacer(modifier = Modifier.size(12.dp))
        FlowRow(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            state.chips.forEach {
                FilterChip(
                    selected = it.isSelected,
                    onClick = { vm.chipClicked(it) },
                    label = {
                        Text(text = it.text, style = ArtTextStyle.Chips)
                    },
                    shape = CircleShape,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    colors = FilterChipDefaults.filterChipColors(selectedContainerColor = Colors.MainGreen),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = Colors.MainGreen,
                        borderWidth = 2.dp
                    )
                )
            }
        }
        Spacer(Modifier.weight(1f))
        ButtonGroup(
            stringResource(id = R.string.onboarding_target_accept_button),
            stringResource(id = R.string.onboarding_target_cancel_button),
            vm::nextPage,
            vm::skip,
            primaryEnabled = state.chips.any { it.isSelected }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        OnboardingTargetScreen()
    }
}