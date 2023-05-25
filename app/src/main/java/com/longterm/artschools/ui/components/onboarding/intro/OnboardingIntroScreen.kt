package com.longterm.artschools.ui.components.onboarding.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.longterm.artschools.R
import com.longterm.artschools.ui.components.common.ButtonGroup
import com.longterm.artschools.ui.core.theme.ArtTextStyle
import com.longterm.artschools.ui.core.utils.PreviewContext

@Composable
fun OnboardingIntroScreen(
    nextPage: () -> Unit = {},
    skip: () -> Unit = {}
) {
    Column(
        Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Box(Modifier.aspectRatio(1.77f), Alignment.Center) {
            Image(
                painter = painterResource(R.drawable.ic_intro),
                contentDescription = "Intro",
                Modifier
                    .fillMaxHeight()
                    .wrapContentWidth(),
                contentScale = ContentScale.FillHeight
            )
        }

        Text(
            text = stringResource(id = R.string.onboarding_intro_title),
            Modifier.padding(top = 22.dp),
            style = ArtTextStyle.H1
        )

        Text(
            text = stringResource(id = R.string.onboarding_intro_description),
            Modifier.padding(top = 22.dp),
            style = ArtTextStyle.Body
        )

        Spacer(modifier = Modifier.weight(1.0f))

        ButtonGroup(
            primaryText = stringResource(id = R.string.onboarding_intro_start_button),
            secondaryText = stringResource(id = R.string.onboarding_intro_login),
            primaryButtonClicked = nextPage,
            secondaryButtonClicked = skip
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        Column {
            Spacer(modifier = Modifier.size(48.dp))
            OnboardingIntroScreen { }
        }
    }
}