package com.longterm.artschools.ui.components.onboarding.register.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.longterm.artschools.R
import com.longterm.artschools.ui.core.theme.Colors
import com.longterm.artschools.ui.core.utils.PreviewContext

@Composable
fun RegisterDoneButton(enabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Colors.Green,
            disabledContainerColor = Colors.GreenDisabled
        ),
        enabled = enabled,
        contentPadding = PaddingValues(vertical = 14.dp),
        modifier = Modifier
            .fillMaxWidth()
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
        RegisterDoneButton(true) {}
    }
}

@Preview
@Composable
private fun Preview2() {
    PreviewContext {
        RegisterDoneButton(false) {}
    }
}