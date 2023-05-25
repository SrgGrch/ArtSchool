package com.longterm.artschools.ui.components.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.longterm.artschools.ui.core.theme.ArtTextStyle
import com.longterm.artschools.ui.core.utils.PreviewContext

@Composable
fun ButtonGroup(
    primaryText: String,
    secondaryText: String,
    primaryButtonClicked: () -> Unit = {},
    secondaryButtonClicked: () -> Unit = {},
    primaryEnabled: Boolean = true,
    secondaryEnabled: Boolean = true,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 10.dp)
    ) {
        Button(
            onClick = primaryButtonClicked,
            Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 20.dp),
            enabled = primaryEnabled
        ) {
            Text(
                text = primaryText,
                style = ArtTextStyle.Button
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(
            onClick = secondaryButtonClicked,
            Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 20.dp),
            enabled = secondaryEnabled
        ) {
            Text(
                text = secondaryText,
                fontSize = 18.sp,
                style = ArtTextStyle.Button
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        ButtonGroup("asdasd", "asdasd")
    }
}