package com.longterm.artschools.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.longterm.artschools.ui.components.auth.AuthScreen
import com.longterm.artschools.ui.components.onboarding.OnboardingRootScreen
import com.longterm.artschools.ui.components.vkauth.VkAuthScreen
import com.longterm.artschools.ui.components.vkauth.components.OnVkAuthResult

sealed interface Destination {
    val route: String
        get() = this::class.simpleName!!

    val isNavigationVisible: Boolean
        get() = true

    @Composable
    fun GetComposable(navController: NavController)

    object Auth : Destination {
        @Composable
        override fun GetComposable(navController: NavController) {
            return AuthScreen()
        }
    }

    object Onboarding : Destination {
        @Composable
        override fun GetComposable(navController: NavController) {
            return OnboardingRootScreen(
                navigateToVkAuth = { navController.navigate(VkAuth(it)) },
                navigateToMainScreen = {
                    navController.navigate(BottomBarDestination.Main) {
                        popUpTo(BottomBarDestination.Main.route)
                    }
                },
                navigateToLogin = { navController.navigate(BottomBarDestination.Main) })//todo
        }
    }

    object VkAuth : Destination {
        override val route: String
            get() = "VkAuth"

        private lateinit var onResult: OnVkAuthResult
        operator fun invoke(callback: OnVkAuthResult): VkAuth {
            this.onResult = callback

            return this
        }

        @Composable
        override fun GetComposable(navController: NavController) {
            VkAuthScreen(onResult) { navController.popBackStack() }
        }
    }

//    object Register : Destination {
//        @Composable
//        override fun GetComposable(navController: NavController) {
//            KoinScope(scopeDefinition = { createScope<OnboardingScope>() }) {
//                RegisterScreen {
//                    navController.navigate(VkAuth(it))
//                }
//            }
//        }
//    }
}