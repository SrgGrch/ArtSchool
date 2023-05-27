package com.longterm.artschools.ui.components.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SimpleSnackBar(text: String, dismissAction: @Composable (() -> Unit)? = null) {
    val hostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = Unit) {
        hostState.showSnackbar(message = text)
    }
    SnackbarHost(hostState = hostState) {
        Snackbar(dismissAction = dismissAction, modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
            Text(text = it.visuals.message)
        }
    }
}