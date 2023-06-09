package com.longterm.artschools.ui.components.coursesList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.longterm.artschools.R
import com.longterm.artschools.ui.components.common.SearchToolbar
import com.longterm.artschools.ui.core.theme.Colors
import com.longterm.artschools.ui.core.utils.PreviewContext
import org.koin.androidx.compose.getViewModel

@Composable
fun CoursesListScreen(navigateToCourse: (id: Int) -> Unit) {
    val vm: CoursesListViewModel = getViewModel()
    val state by vm.state.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .background(Colors.GreyLight3)
    ) {
        SearchToolbar(
            title = {
                Text(
                    text = stringResource(id = R.string.cousres_title),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W700,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 16.dp)
                )

            },
            searchValue = (state as? CoursesListViewModel.State.Data)?.searchQuery ?: "",
            onSearchValueChanged = vm::onSearchValueChanged,
            onClear = {
                vm.onSearchValueChanged("")
            }
        )
        when (val st = state) {
            CoursesListViewModel.State.Error -> Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Ошибка, попробуйте еще раз")
                Button(onClick = vm::retry) {
                    Text(text = "Обновить")
                }
            }

            CoursesListViewModel.State.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is CoursesListViewModel.State.Data -> {
                LazyColumn {
                    itemsIndexed(st.items) { _, item ->
                        CoursesListItem(data = item, onItemClicked = { navigateToCourse(item.id) })
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        CoursesListScreen({})
    }
}