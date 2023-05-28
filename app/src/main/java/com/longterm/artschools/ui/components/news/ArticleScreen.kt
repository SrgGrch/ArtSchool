package com.longterm.artschools.ui.components.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.longterm.artschools.domain.models.Tag
import com.longterm.artschools.ui.components.common.fromHex
import com.longterm.artschools.ui.components.common.preview
import com.longterm.artschools.ui.components.main.items.ItemsPaddingValues
import com.longterm.artschools.ui.core.theme.ArtTextStyle
import com.longterm.artschools.ui.core.theme.Colors
import com.longterm.artschools.ui.core.utils.PreviewContext
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ArticleScreen(id: Int, back: () -> Unit) {
    val vm: ArticleVm = getViewModel { parametersOf(id) }
    val state by vm.state.collectAsState()
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 4.dp)
        ) {
            IconButton(onClick = back) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "назад",
                    tint = Colors.GreenMain
                )
            }
        }

        when (val st = state) {
            is ArticleVm.State.Data -> Data(st)
            ArticleVm.State.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

            ArticleVm.State.Error -> Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Ошибка, попробуйте еще раз")
                Button(onClick = vm::retry) {
                    Text(text = "Обновить")
                }
            }

            ArticleVm.State.WTF -> Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Ошибка загрузки статьи, попробуйте позже")
                Button(onClick = back) {
                    Text(text = "Выйти")
                }
            }
        }
    }
}

@Composable
private fun Data(state: ArticleVm.State.Data) {
    Column(Modifier.verticalScroll(rememberScrollState())) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = state.imageUrl)
                    .apply(block = {
                        preview()
                    }).build()
            ),
            contentDescription = "Картинка",
            Modifier
                .aspectRatio(1f)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Column(
            Modifier
                .offset(y = (-16).dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(ItemsPaddingValues)
                .fillMaxWidth()
        ) {
            FlowRow {
                state.tags.forEach {
                    Text(
                        text = it.text,
                        Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(Color.fromHex(it.color))
                            .padding(vertical = 4.dp, horizontal = 8.dp),
                        style = ArtTextStyle.tab,
                        color = Color.White
                    )
                }
            }

            Text(state.title, style = ArtTextStyle.H3)
            Spacer(Modifier.height(8.dp))
            Text(state.description, style = ArtTextStyle.Body)
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        ArticleScreen(1) {}
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewData() {
    PreviewContext {
        Data(
            ArticleVm.State.Data(
                1,
                "Екатерина Ганелина",
                "Известный российский концертмейстер, постоянный концертмейстер народной артистки России Хиблы Герзмава. «Главное — любить музыку, много заниматься и стремиться продолжать великие традиции!»",
                "https://static.tildacdn.com/tild6338-3363-4137-b534-663038303161/DSCF0696.jpg",
                3,
                listOf(
                    Tag("Интервью", "#FF8E75A8"),
                    Tag("🎶 Музыка", "#FFDBB0C2"),
                )
            )
        )
    }
}