package com.longterm.artschools.ui.components.course


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.longterm.artschools.R
import com.longterm.artschools.ui.components.course.models.Course
import com.longterm.artschools.ui.core.theme.ArtTextStyle
import com.longterm.artschools.ui.core.theme.Colors
import com.longterm.artschools.ui.core.utils.PreviewContext

@Composable
fun LessonCard(
    data: Course.Lesson,
    onItemClicked: (Course.Lesson) -> Unit,
    number: Int
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .shadow(6.dp, shape = RoundedCornerShape(16.dp))
            .clickable { onItemClicked(data) }
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        Row(Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "$number урок",
                Modifier
                    .clip(CircleShape)
                    .background(Colors.Green)
                    .padding(vertical = 4.dp, horizontal = 8.dp)
                    .align(Alignment.CenterVertically),
                style = ArtTextStyle.tab,
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.ic_time),
                contentDescription = "",
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = data.duration,
                fontSize = 12.sp,
                fontWeight = FontWeight.W400,
                color = Colors.Black,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = data.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.W700,
            color = Colors.Black,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = data.cost,
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.size(20.dp))
    }
}

@Preview
@Composable
private fun Preview() {
    PreviewContext {
        LessonCard(
            data = Course.Lesson(0, "3 основные группы инструментов", "бесплатно", true, "5 мин", true),
            onItemClicked = {},
            number = 1
        )
    }
}