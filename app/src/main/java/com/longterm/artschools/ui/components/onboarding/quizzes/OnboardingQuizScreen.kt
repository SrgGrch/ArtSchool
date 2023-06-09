package com.longterm.artschools.ui.components.onboarding.quizzes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.longterm.artschools.R
import com.longterm.artschools.ui.core.theme.ArtTextStyle
import com.longterm.artschools.ui.core.utils.PreviewContext

@Composable
fun OnboardingQuizScreen(
    nextPage: () -> Unit = {}
) {
    Column(
        Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = "Играйте и получайте",
            Modifier.padding(top = 22.dp),
            style = ArtTextStyle.H1
        )
        Row {
            Text(
                text = "баллы",
                style = ArtTextStyle.H1
            )
            Image(
                painter = painterResource(id = R.drawable.ic_reward),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    Color.Black
                ),
                modifier = Modifier.size(30.dp)
            )
        }
        Spacer(Modifier.height(22.dp))
        Text(
            text = "Проходите квизы, изучайте познавательные статьи и получайте доступ к новым урокам и курсам",
            style = ArtTextStyle.Body
        )
        Spacer(Modifier.height(12.dp))
        Image(
            painter = painterResource(id = R.drawable.ill_quizes),
            contentDescription = null,
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(top = 40.dp, end = 12.dp)
                .scale(1.2f)
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = nextPage,
            Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 20.dp)
        ) {
            Text(
                text = "Супер",
                style = ArtTextStyle.Button
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        OnboardingQuizScreen({})
    }
}