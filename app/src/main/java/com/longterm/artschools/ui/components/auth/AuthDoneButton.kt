package com.longterm.artschools.ui.components.auth

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.longterm.artschools.R
import com.longterm.artschools.ui.core.theme.Colors
import com.longterm.artschools.ui.core.utils.PreviewContext
import org.koin.androidx.compose.getViewModel

@Composable
fun AuthDoneButton(viewModel: AuthViewModel, state: AuthViewModel.State, bottomPadding: Dp) {
    Button(
        onClick = viewModel::doneClicked,
        colors = ButtonDefaults.buttonColors(
            containerColor = Colors.Green,
            disabledContainerColor = Colors.GreenDisabled
        ),
        enabled = (state as? AuthViewModel.State.InternalAuth)?.isDoneButtonEnabled ?: false,
        contentPadding = PaddingValues(vertical = 14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = hPadding.dp, end = hPadding.dp, bottom = bottomPadding)
    ) {
        Text(
            text = stringResource(id = R.string.done),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.W700
        )
    }
}

@Preview
@Composable
private fun Preview() {
    PreviewContext {
        AuthDoneButton(getViewModel(), AuthViewModel.State.InternalAuth("", "", true), 0.dp)
    }
}