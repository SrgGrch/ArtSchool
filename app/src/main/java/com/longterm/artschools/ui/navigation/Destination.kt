package com.longterm.artschools.ui.navigation

import androidx.compose.runtime.Composable
import com.longterm.artschools.ui.components.auth.AuthScreen
import com.longterm.artschools.ui.components.main.MainScreen

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
}