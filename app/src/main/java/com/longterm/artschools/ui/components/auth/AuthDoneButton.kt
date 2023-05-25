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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.longterm.artschools.R
import com.longterm.artschools.ui.core.theme.Colors

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