@file:OptIn(KoinExperimentalAPI::class)

package com.longterm.artschools.ui.components.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
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
import com.longterm.artschools.di.OnboardingScope
import com.longterm.artschools.ui.components.common.ScrollIndicator
import com.longterm.artschools.ui.components.onboarding.art.OnboardingArtScreen
import com.longterm.artschools.ui.components.onboarding.intro.OnboardingIntroScreen
import com.longterm.artschools.ui.components.onboarding.quizzes.OnboardingQuizScreen
import com.longterm.artschools.ui.components.onboarding.register.RegisterScreen
import com.longterm.artschools.ui.components.onboarding.target.OnboardingTargetScreen
import com.longterm.artschools.ui.components.onboarding.userInfo.OnboardingUserInfoScreen
import com.longterm.artschools.ui.components.vkauth.components.OnVkAuthResult
import com.longterm.artschools.ui.core.theme.Colors
import com.longterm.artschools.ui.core.utils.PreviewContext
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.koin.compose.scope.KoinScope
import org.koin.core.annotation.KoinExperimentalAPI

const val PAGE_COUNT = 6

@Composable
fun OnboardingRootScreen(
    navigateToVkAuth: (OnVkAuthResult) -> Unit,
    navigateToMainScreen: () -> Unit,
    navigateToLogin: () -> Unit
) {
    KoinScope(scopeDefinition = { createScope<OnboardingScope>() }) {
        val vm: OnboardingViewModel = getViewModel()
        val coroutineScope = rememberCoroutineScope()
        val state by vm.state.collectAsState()
        val pagerState = rememberPagerState()
        val systemUiController = rememberSystemUiController()

        SideEffect {
            coroutineScope.launch {
                pagerState.animateScrollToPage(state.currentPage)
            }
        }

        systemUiController.setStatusBarColor(getBackground(pagerState.currentPage))

        BackHandler(enabled = pagerState.currentPage != 0) {
            vm.prevPage()
        }

        Box(
            Modifier
                .fillMaxHeight()
        ) {
            HorizontalPager(pageCount = PAGE_COUNT, state = pagerState, userScrollEnabled = false) {
                PagerPage(
                    page = it,
                    nextPage = {
                        vm.nextPage()
                    },
                    skip = {
                        vm.skip()
                    },
                    navigateToVkAuth = navigateToVkAuth,
                    navigateToMainScreen = navigateToMainScreen,
                    navigateToLogin = navigateToLogin
                )
            }

            Row(
                Modifier
                    .fillMaxWidth(),
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
                            tint = Colors.GreenMain
                        )
                    }
                else Spacer(modifier = Modifier.size(48.dp))

                ScrollIndicator(PAGE_COUNT, pagerState, Modifier.wrapContentWidth())

                Spacer(modifier = Modifier.size(48.dp))
            }
        }
    }
}

private fun getBackground(currentPage: Int): Color {
    return if (currentPage == 4) Colors.VioletLite else Color.White
}

@Composable
private fun PagerPage(
    page: Int,
    nextPage: () -> Unit,
    skip: () -> Unit,
    navigateToVkAuth: (OnVkAuthResult) -> Unit,
    navigateToMainScreen: () -> Unit,
    navigateToLogin: () -> Unit
) {
    Column(
        Modifier
            .background(getBackground(page))
    ) {
        Spacer(modifier = Modifier.size(50.dp))

        when (page) {
            0 -> OnboardingIntroScreen(nextPage, navigateToLogin)
            1 -> OnboardingQuizScreen(nextPage)
            2 -> OnboardingArtScreen(nextPage, skip)
            3 -> OnboardingTargetScreen(nextPage, skip)
            4 -> OnboardingUserInfoScreen(nextPage, skip)
            5 -> RegisterScreen(navigateToVkAuth, navigateToMainScreen)
            else -> error("No such page $page")
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        OnboardingRootScreen({}, {}, {})
    }
}