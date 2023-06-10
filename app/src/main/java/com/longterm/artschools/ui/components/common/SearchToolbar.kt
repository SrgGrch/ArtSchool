package com.longterm.artschools.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.longterm.artschools.R
import com.longterm.artschools.ui.core.theme.Colors
import com.longterm.artschools.ui.core.utils.PreviewContext

@Composable
fun SearchToolbar(
    title: @Composable RowScope.() -> Unit,
    searchValue: String,
    onSearchValueChanged: (String) -> Unit,
    onClear: () -> Unit,
    leftIcon: @Composable RowScope.() -> Unit = {},
) {
    var showSearch by remember {
        mutableStateOf(false)
    }

    Row(
        Modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showSearch) {
            TextField(
                value = searchValue,
                onValueChange = onSearchValueChanged,
                Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    IconButton(onClick = {
                        onClear()
                        showSearch = false
                    }) {
                        Icon(imageVector = Icons.Rounded.Close, contentDescription = "Закрыть поиск")
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    containerColor = Color.White,
                    focusedIndicatorColor = Colors.Green,
                    unfocusedIndicatorColor = Colors.Green,
                    disabledIndicatorColor = Colors.Green,
                    unfocusedLabelColor = Color.Gray,
                    disabledLabelColor = Color.Gray,
                    focusedLabelColor = Color.Gray
                )
            )
        } else {
            leftIcon()

            title()

            IconButton(onClick = { showSearch = true }, Modifier.padding(top = 6.dp)) {
                Icon(painter = painterResource(id = R.drawable.ic_search), contentDescription = "Поиск")
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PreviewContext {
        var asd by remember {
            mutableStateOf("")
        }
        SearchToolbar(
            {
                Text("Test title")
            },
            asd,
            { asd = it },
            {},
            {
                TextButton(onClick = {}, Modifier.padding(top = 6.dp)) {
                    Icon(painter = painterResource(id = R.drawable.ic_cup), contentDescription = "Уровень")
                    Text(text = "1")
                }
            },
        )
    }
}