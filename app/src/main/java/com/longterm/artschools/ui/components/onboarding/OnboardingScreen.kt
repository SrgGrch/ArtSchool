@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.longterm.artschools.ui.components.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.longterm.artschools.ui.components.onboarding.intro.OnboardingIntroScreen
import com.longterm.artschools.ui.core.theme.MainGreen
import com.longterm.artschools.ui.core.utils.PreviewContext
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

const val PAGE_COUNT = 7

@Composable
fun OnboardingRootScreen() {
    val coroutineScope = rememberCoroutineScope()
    val viewModel: OnboardingViewModel = getViewModel()
    val state by viewModel.state.collectAsState()
    val pagerState = rememberPagerState(state.currentPage)

    Column {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (state.isPrevButtonVisible)
                IconButton(onClick = {
                    viewModel.prevPage()
                    coroutineScope.launch {
                        pagerState.prevPage()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "назад",
                        tint = MainGreen
                    )
                }
            else Spacer(modifier = Modifier.size(48.dp))

            ScrollIndicator(PAGE_COUNT, pagerState, Modifier.wrapContentWidth())

            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "назад",
                    tint = MainGreen
                )
            }
        }

        HorizontalPager(pageCount = PAGE_COUNT, state = pagerState, userScrollEnabled = false) {
            GetPage(page = it) {
                coroutineScope.launch {
                    viewModel.nextPage()
                    pagerState.nextPage(PAGE_COUNT)
                }
            }
        }
    }
}

@Composable
private fun GetPage(page: Int, nextPage: () -> Unit) = when (page) {
    0 -> OnboardingIntroScreen(nextPage)
    in 1..6 -> OnboardingIntroScreen(nextPage)
    else -> error("No such page $page")
}

private suspend fun PagerState.nextPage(pageCount: Int): Boolean {
    return if (currentPage < pageCount - 1) {
        animateScrollToPage(currentPage + 1)
        true
    } else false
}

private suspend fun PagerState.prevPage(): Boolean {
    return if (currentPage > 0) {
        animateScrollToPage(currentPage - 1)
        true
    } else false
}


@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        OnboardingRootScreen()
    }
}