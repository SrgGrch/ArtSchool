package com.longterm.artschools.ui.components.onboarding.register.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.longterm.artschools.R
import com.longterm.artschools.ui.components.onboarding.register.RegisterViewModel
import com.longterm.artschools.ui.core.theme.Colors
import com.longterm.artschools.ui.core.utils.PreviewContext
import org.koin.androidx.compose.getViewModel


@Composable
fun RegisterInitialStateView(viewModel: RegisterViewModel) {
    Column {
        Text(
            text = stringResource(id = R.string.or),
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.size(spacerSize))
        Button(
            onClick = viewModel::onLoginViaVkClicked,
            colors = ButtonDefaults.buttonColors(containerColor = Colors.VkBlue),
            contentPadding = PaddingValues(vertical = 14.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_vk_logo), contentDescription = null)
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = stringResource(id = R.string.loginViaVk),
                fontSize = 18.sp
            )
        }
    }
}

private val spacerSize = 12.dp

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        RegisterInitialStateView(viewModel = getViewModel())
    }
}