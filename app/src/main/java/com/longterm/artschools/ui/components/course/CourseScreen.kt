package com.longterm.artschools.ui.components.course

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import org.koin.core.parameter.parametersOf

@Composable
fun CoursesScreen(id: Int, goBack: () -> Unit, navigateToLesson: (id: Int) -> Unit) {
    val vm: CourseViewModel = getViewModel { parametersOf(id) }
    val state by vm.state.collectAsState()

    var openDialog: Int? by remember { mutableStateOf(null) }

    if (openDialog != null) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                openDialog = null
            },
            confirmButton = {
                Button(onClick = {
                    vm.buy()
                    openDialog = null
                }) {
                    Text("Купить")
                }
            },
            dismissButton = {
                Button(onClick = {
                    openDialog = null
                }) {
                    Text("Отмена")
                }
            },
            title = {
                Text("Купить курс?")
            },
            text = {
                Text("Что бы продолжить нужно купить курс")
            }
        )
    }

    when (val st = state) {
        CourseViewModel.State.Error -> Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Ошибка, попробуйте еще раз")
            Button(onClick = { vm.retry() }) {
                Text(text = "Обновить")
            }
        }

        CourseViewModel.State.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is CourseViewModel.State.Data -> {
            LazyColumn {
                item {
                    CourseMainInfo(st = st) {
                        goBack()
                    }
                }
                itemsIndexed(st.course.lessons) { index, item ->
                    LessonCard(
                        data = item,
                        onItemClicked = {
                            if (item.unlocked) {
                                navigateToLesson(item.id)
                            } else {
                                openDialog = item.id
                            }
                        },
                        number = index + 1
                    )
                }
            }
        }
    }
}


@Composable
private fun CourseMainInfo(st: CourseViewModel.State.Data, goBack: () -> Unit) {
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
            text = st.course.title,
            fontSize = 26.sp,
            fontWeight = FontWeight.W800,
            color = Colors.Black,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = stringResource(id = R.string.cousres_course_reading),
            fontSize = 16.sp,
            fontWeight = FontWeight.W600,
            color = Colors.Black,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Row(Modifier.padding(horizontal = 16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_intro), // todo
                contentDescription = "Аватар",
                Modifier
                    .clip(CircleShape)
                    .size(60.dp)
                    .aspectRatio(1f)
            )
            Text(
                text = st.course.authorName,
                fontSize = 14.sp,
                fontWeight = FontWeight.W700,
                color = Colors.Black,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = st.course.authorDescription,
            fontWeight = FontWeight.W400,
            fontSize = 13.sp,
            color = Colors.Black,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = stringResource(id = R.string.cousres_lessons),
            fontWeight = FontWeight.W600,
            fontSize = 16.sp,
            color = Colors.Black,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        CoursesScreen(1, {}, {})
    }
}
