package com.longterm.artschools.ui.components.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.longterm.artschools.R
import com.longterm.artschools.ui.components.main.items.ArticleItem
import com.longterm.artschools.ui.components.main.items.QuizItem
import com.longterm.artschools.ui.components.main.items.VkPlaylist
import com.longterm.artschools.ui.components.main.models.MainListItem
import com.longterm.artschools.ui.core.utils.PreviewContext
import org.koin.androidx.compose.getViewModel

@Composable
fun MainScreen(navigateToArticle: (id: Int) -> Unit) {
    val vm: MainViewModel = getViewModel()
    val state by vm.state.collectAsState()

    Column(Modifier.fillMaxSize()) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
            TextButton(onClick = { /*todo*/ }, Modifier.padding(top = 6.dp)) {
                Icon(painter = painterResource(id = R.drawable.ic_cup), contentDescription = "Уровень")
                Text(text = state.level)
            }

            Image(
                painter = painterResource(id = R.drawable.ic_intro), // todo
                contentDescription = "Аватар",
                Modifier
                    .clip(CircleShape)
                    .height(52.dp)
                    .aspectRatio(1f)
            )

            IconButton(onClick = { /*TODO*/ }, Modifier.padding(top = 6.dp)) {
                Icon(painter = painterResource(id = R.drawable.ic_search), contentDescription = "Поиск")
            }
        }

        when (val st = state) {
            MainViewModel.State.Error -> Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Ошибка, попробуйте еще раз")
                Button(onClick = vm::retry) {
                    Text(text = "Обновить")
                }
            }

            MainViewModel.State.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is MainViewModel.State.Data -> {
                LazyColumn {
                    itemsIndexed(st.items) { position, item ->
                        when (item) {
                            is MainListItem.ArticleItem -> ArticleItem(data = item, onItemClicked = { navigateToArticle(it) })
                            is MainListItem.QuizItem -> QuizItem(
                                position = position,
                                data = item,
                                onAnswerSelected = { quizId, answer ->
                                    vm.onQuizAnswer(answer, quizId, position)
                                }
                            )

                            is MainListItem.VkPlaylist -> VkPlaylist(data = item)
                        }
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
        MainScreen(navigateToArticle = {})
    }
}