package com.longterm.artschools.ui.components.auth


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.tooling.preview.Preview
import com.longterm.artschools.ui.core.utils.PreviewContext
import org.koin.androidx.compose.getViewModel

@Composable
fun AuthScreen() {
    val viewModel: AuthViewModel = getViewModel()
    val state by viewModel.state.collectAsState()

    when (state) {
        AuthViewModel.State.Initial -> AuthInitialStateView(viewModel, state)
        is AuthViewModel.State.InternalAuth -> AuthInternalStateView(viewModel, state)
        AuthViewModel.State.Vk -> {}
        AuthViewModel.State.Close -> {}
    }
}


@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        AuthScreen()
    }
}

const val hPadding = 16
const val spacerSize = 12