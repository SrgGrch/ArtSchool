package com.longterm.artschools.ui.components.coursesList


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.longterm.artschools.ui.components.common.preview
import com.longterm.artschools.ui.components.coursesList.models.CoursePreview
import com.longterm.artschools.ui.components.main.items.ItemsPaddingValues
import com.longterm.artschools.ui.core.theme.ArtTextStyle
import com.longterm.artschools.ui.core.utils.PreviewContext

@Composable
fun CoursesListItem(
    data: CoursePreview,
    onItemClicked: (id: Int) -> Unit
) {
    Column(
        Modifier
            .clickable {
                onItemClicked(data.id)
            }
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = data.imageUrl)
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

        Column(
            Modifier
                .offset(y = (-32).dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(ItemsPaddingValues)
                .fillMaxWidth()
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FlowRow(Modifier.weight(1f)) {
                    data.tags.forEach {
                        Text(
                            text = it.text,
                            Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(Color(it.color))
                                .padding(vertical = 4.dp, horizontal = 8.dp),
                            style = ArtTextStyle.tab,
                            color = Color.White
                        )
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Column {
                Text(data.title, style = ArtTextStyle.H3)
                Spacer(Modifier.height(8.dp))
                Text(data.description, style = ArtTextStyle.Body)
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PreviewContext {
        CoursesListItem(
            CoursePreview(
                1,
                "Екатерина Ганелина",
                "Известный российский концертмейстер, постоянный концертмейстер народной артистки России Хиблы Герзмава. «Главное — любить музыку, много заниматься и стремиться продолжать великие традиции!»",
                "https://static.tildacdn.com/tild6338-3363-4137-b534-663038303161/DSCF0696.jpg",
                3,
                listOf(
                    CoursePreview.Tag("Интервью", 0xFF8E75A8),
                    CoursePreview.Tag("🎶 Музыка", 0xFFDBB0C2),
                    CoursePreview.Tag("Интервью", 0xFF8E75A8),
                    CoursePreview.Tag("🎶 Музыка", 0xFFDBB0C2),
                    CoursePreview.Tag("Интервью", 0xFF8E75A8),
                    CoursePreview.Tag("🎶 Музыка", 0xFFDBB0C2),
                )
            )
        ) {

        }
    }
}