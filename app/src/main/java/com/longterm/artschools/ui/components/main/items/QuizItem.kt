package com.longterm.artschools.ui.components.main.items

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.longterm.artschools.R
import com.longterm.artschools.ui.components.common.preview
import com.longterm.artschools.ui.components.main.models.MainListItem
import com.longterm.artschools.ui.core.theme.ArtTextStyle
import com.longterm.artschools.ui.core.theme.Colors
import com.longterm.artschools.ui.core.utils.PreviewContext

@Composable
fun QuizItem(
    position: Int,
    data: MainListItem.QuizItem,
    onAnswerSelected: (quizId: Int, answer: MainListItem.QuizItem.Answer) -> Unit
) {
    Column(
        Modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
    ) {

        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = data.imageUrl)
                    .apply(block = {
//                        crossfade(true)
                        preview()
                    }).build()
            ),
            contentDescription = "Картина",
            Modifier
                .fillMaxWidth()
                .aspectRatio(1.6f),
            contentScale = ContentScale.FillBounds
        )

        Column(
            Modifier
                .offset(y = (-16).dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Colors.GreyLight3)
                .padding(ItemsPaddingValues)
                .fillMaxWidth()
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "⭐️ Квиз",
                    Modifier
                        .clip(CircleShape)
                        .background(Colors.Blue)
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    style = ArtTextStyle.tab,
                    color = Color.White
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_reward),
                        contentDescription = "Награда",
                        tint = Colors.GreenMain
                    )
                    Text(text = "${data.reward} балла", style = ArtTextStyle.tab)
                }
            }
            Spacer(Modifier.height(8.dp))
            Column {
                Text(data.title, style = ArtTextStyle.H3)
                Spacer(Modifier.height(8.dp))
                Text(data.description, style = ArtTextStyle.Body)
                Spacer(Modifier.height(20.dp))
                Column {
                    data.answers.forEach { answer ->
                        Chip(answer.text, answer.selected, data.isCorrectAnswerSelected) {
                            onAnswerSelected(data.id, answer)
                        }
                    }
                }
            }

            AnimatedVisibility(visible = data.answerDescription != null) {
                Column {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = data.answerDescription ?: "", style = ArtTextStyle.Body)
                }
            }
        }
    }
}

@Composable
private fun Chip(
    text: String,
    selected: Boolean,
    correct: Boolean?,
    onClick: () -> Unit
) {
    val color = if (correct == true) Colors.GreenMain else Colors.Red

    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Text(text = text, style = ArtTextStyle.Chips)
        },
        shape = CircleShape,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = color,
            selectedLabelColor = Color.White,
            disabledSelectedContainerColor = color,
            disabledLabelColor = if (selected) Color.White else Color.Black
        ),
        border = FilterChipDefaults.filterChipBorder(
            borderColor = Colors.GreenMain,
            borderWidth = 2.dp
        ),
        enabled = correct == null
    )
}

@Preview
@Composable
private fun Preview() {
    PreviewContext {
        QuizItem(
            1,
            MainListItem.QuizItem(
                1,
                3,
                "#АВыЗнаете как зовут автора этой картины?",
                "В 1880 году в Санкт-Петербурге была впервые выставлена картина «Лунная ночь на Днепре». Удивительным было то, что она была единственной на выставке. Необыкновенная реалистичность лунного света поразила публику",
                "",
                listOf(
                    MainListItem.QuizItem.Answer(1, "\uD83D\uDE31 Эдвард Мунк", false),
                    MainListItem.QuizItem.Answer(1, "\uD83C\uDF33 Архип Куинджи ", selected = true),
                    MainListItem.QuizItem.Answer(1, "\uD83C\uDF0A Иван Айвазовский", false),
                ),
                true,
                "✅ Правильный ответ, так держать! «Лунная ночь на Днепре» — одна из самых известных картин Архипа Куинджи."
            )
        ) { _, _ ->

        }
    }
}