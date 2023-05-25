package com.longterm.artschools.ui.components.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.longterm.artschools.R
import com.longterm.artschools.ui.core.theme.Colors
import com.longterm.artschools.ui.core.utils.PreviewContext
import org.koin.androidx.compose.getViewModel


@Composable
fun AuthInitialStateView(viewModel: AuthViewModel, state: AuthViewModel.State) {
    Column {
        IconButton(
            onClick = viewModel::close,
            modifier = Modifier
                .align(Alignment.End)
                .padding(horizontal = 4.dp),
        ) {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = null,
                tint = Colors.Green,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = stringResource(id = R.string.login),
            color = Color.Black,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = hPadding.dp, vertical = 10.dp)
        )
        Spacer(modifier = Modifier.size(spacerSize.dp))
        Text(
            text = stringResource(id = R.string.enterEmailOrLoginViaVk),
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(horizontal = hPadding.dp)
        )
        Spacer(modifier = Modifier.size(spacerSize.dp))
        AuthEmailTextField(viewModel, state)
        Spacer(modifier = Modifier.size(spacerSize.dp))
        Text(
            text = stringResource(id = R.string.or),
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.size(spacerSize.dp))
        Button(
            onClick = viewModel::loginViaVk,
            colors = ButtonDefaults.buttonColors(containerColor = Colors.VkBlue),
            contentPadding = PaddingValues(vertical = 14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = hPadding.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_vk_logo), contentDescription = null)
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = stringResource(id = R.string.loginViaVk),
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        AuthDoneButton(viewModel = viewModel, state = state, bottomPadding = 24.dp)

    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        AuthInitialStateView(viewModel = getViewModel(), state = AuthViewModel.State.Initial)
    }
}