@file:OptIn(ExperimentalFoundationApi::class)

package com.longterm.artschools.ui.components.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.longterm.artschools.ui.components.common.ScrollIndicator
import com.longterm.artschools.ui.components.onboarding.art.OnboardingArtScreen
import com.longterm.artschools.ui.components.onboarding.intro.OnboardingIntroScreen
import com.longterm.artschools.ui.components.onboarding.target.OnboardingTargetScreen
import com.longterm.artschools.ui.components.onboarding.userInfo.OnboardingUserInfoScreen
import com.longterm.artschools.ui.core.theme.MainGreen
import com.longterm.artschools.ui.core.theme.VioletLite
import com.longterm.artschools.ui.core.utils.PreviewContext
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

const val PAGE_COUNT = 7

@Composable
fun OnboardingRootScreen() {
    val coroutineScope = rememberCoroutineScope()
    val vm: OnboardingViewModel = getViewModel()
    val state by vm.state.collectAsState()
    val pagerState = rememberPagerState()
    val systemUiController = rememberSystemUiController()

    SideEffect {
        coroutineScope.launch {
            pagerState.animateScrollToPage(state.currentPage)
        }
    }

    systemUiController.setStatusBarColor(getTopBarColor(pagerState.currentPage))

    BackHandler(enabled = pagerState.currentPage != 0) {
        vm.prevPage()
    }

    Column() {
        Row(
            Modifier
                .fillMaxWidth()
                .background(getTopBarColor(state.currentPage)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (state.isPrevButtonVisible)
                IconButton(onClick = {
                    vm.prevPage()
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
                vm.nextPage()
            }
        }
    }
}

private fun getTopBarColor(currentPage: Int): Color {
    return if (currentPage == 3) VioletLite else Color.White
}

@Composable
private fun GetPage(page: Int, nextPage: () -> Unit) = when (page) {
    0 -> OnboardingIntroScreen(nextPage)
    1 -> OnboardingArtScreen(nextPage)
    2 -> OnboardingTargetScreen(nextPage)
    3 -> OnboardingUserInfoScreen(nextPage)
    in 3..6 -> OnboardingIntroScreen(nextPage)
    else -> error("No such page $page")
}


@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        OnboardingRootScreen()
    }
}