package com.longterm.artschools.ui.components.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.longterm.artschools.ui.core.utils.PreviewContext
import org.koin.androidx.compose.getViewModel

@Composable
fun MainScreen() {
    val viewModel: MainViewModel = getViewModel()
    val state by viewModel.state.collectAsState()
    Text(text = state, fontSize = 32.sp)
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        MainScreen()
    }
}