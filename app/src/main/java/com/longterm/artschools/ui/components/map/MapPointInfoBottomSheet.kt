package com.longterm.artschools.ui.components.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.longterm.artschools.R
import com.longterm.artschools.ui.core.theme.ArtTextStyle
import com.longterm.artschools.ui.core.theme.Colors
import com.longterm.artschools.ui.core.utils.PreviewContext

@Composable
fun MapPointInfoBottomSheet() {
    Column(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()
//            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
//            .background(Color.White)
            .padding(
                top = 14.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 28.dp
            )
    ) {
        Box(
            Modifier
                .size(42.dp, 4.dp)
                .clip(RoundedCornerShape(50f))
                .background(Colors.GreenMain)
                .align(CenterHorizontally)
        )
        Spacer(Modifier.height(24.dp))
        Text(text = "ДМШ №8 Островского", style = ArtTextStyle.H3)
        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(Colors.Orange)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Академическая (Ленинский просп., 45)", style = ArtTextStyle.Body)
        }
        Spacer(Modifier.height(16.dp))
        Text(
            text = """
            Обучение по классам:
            * сольфеджио
            * музыкальная литература
            * фортепиано
            * скрипка
            * гитара
        """.trimIndent(), style = ArtTextStyle.Body
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /*TODO*/ },
            Modifier
                .defaultMinSize(minHeight = 52.dp)
                .fillMaxWidth()
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_phone), contentDescription = null)
            Text(text = "Запись и подробности", style = ArtTextStyle.Button, color = Color.White)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        MapPointInfoBottomSheet()
    }
}