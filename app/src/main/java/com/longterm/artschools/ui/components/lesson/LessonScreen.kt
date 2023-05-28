package com.longterm.artschools.ui.components.lesson

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.longterm.artschools.domain.ImagePathResolver
import com.longterm.artschools.ui.components.common.preview
import com.longterm.artschools.ui.core.theme.Colors
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun LessonScreen(id: Int, goBack: () -> Unit) {
    val vm: LessonViewModel = getViewModel { parametersOf(id) }
    val state by vm.state.collectAsState()

    when (val st = state) {
        LessonViewModel.State.Error -> Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Ошибка, попробуйте еще раз")
            Button(onClick = { vm.retry() }) {
                Text(text = "Обновить")
            }
        }

        LessonViewModel.State.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is LessonViewModel.State.Data -> {
            LazyColumn {
                item {
                    LessonInfo(st = st) {
                        goBack()
                    }
                }
            }
        }
    }
}

@Composable
private fun LessonInfo(st: LessonViewModel.State.Data, goBack: () -> Unit) {
    Column {
        Row {
            IconButton(onClick = { goBack() }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "назад",
                    tint = Colors.GreenMain
                )
            }
        }

        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = st.lesson.name,
            fontSize = 26.sp,
            fontWeight = FontWeight.W800,
            color = Colors.Black,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
        )
        Spacer(modifier = Modifier.size(12.dp))
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = ImagePathResolver.resolve(st.lesson.image))
                    .apply(block = {
                        preview()
                    }).build()
            ),
            contentDescription = "Картина",
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .aspectRatio(1.6f)
                .fillMaxWidth(),
            contentScale = ContentScale.FillBounds
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = st.lesson.description,
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            color = Colors.Black,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
    }
}