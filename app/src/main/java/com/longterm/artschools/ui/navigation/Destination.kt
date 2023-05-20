package com.longterm.artschools.ui.navigation

import androidx.compose.runtime.Composable
import com.longterm.artschools.ui.components.auth.AuthScreen
import com.longterm.artschools.ui.components.main.MainScreen
import com.longterm.artschools.ui.components.onboarding.OnboardingRootScreen

sealed interface Destination {
    val name: String
        get() = this::class.simpleName!!

    @Composable
    fun GetComposable()

    object Main : Destination {
        @Composable
        override fun GetComposable() {
            return MainScreen()
        }
    }

    object Auth : Destination {
        @Composable
        override fun GetComposable() {
            return AuthScreen()
        }
    }

    object Onboarding : Destination {
        @Composable
        override fun GetComposable() {
            return OnboardingRootScreen()
        }
    }
}