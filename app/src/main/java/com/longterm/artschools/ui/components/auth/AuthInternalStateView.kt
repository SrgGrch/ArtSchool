package com.longterm.artschools.ui.components.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.longterm.artschools.R
import com.longterm.artschools.ui.components.common.PasswordTextField
import com.longterm.artschools.ui.core.theme.Colors
import com.longterm.artschools.ui.core.utils.PreviewContext
import org.koin.androidx.compose.getViewModel


@Composable
fun AuthInternalStateView(viewModel: AuthViewModel, state: AuthViewModel.State.InternalAuth) {
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
            text = stringResource(id = R.string.enterPassword),
            color = Color.Black,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = hPadding.dp, vertical = 10.dp)
        )
        Spacer(modifier = Modifier.size(spacerSize.dp))
        AuthEmailTextField(viewModel, state)
        Spacer(modifier = Modifier.size(spacerSize.dp))
        PasswordTextField(
            state.password,
            stringResource(id = R.string.password),
            viewModel::passwordChanged,
            Modifier.padding(horizontal = hPadding.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        AuthDoneButton(viewModel = viewModel, state = state, bottomPadding = 10.dp)
        TextButton(
            onClick = viewModel::doNotRememberPasswordClicked,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(id = R.string.doNotRememberPassword),
                color = Colors.Green,
                fontSize = 18.sp,
                fontWeight = FontWeight.W700
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        AuthInternalStateView(
            viewModel = getViewModel(),
            state = AuthViewModel.State.InternalAuth(email = "", password = ""),
        )
    }
}