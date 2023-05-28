package com.longterm.artschools.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.longterm.artschools.ui.components.auth.AuthScreen
import com.longterm.artschools.ui.components.news.ArticleScreen
import com.longterm.artschools.ui.components.onboarding.OnboardingRootScreen
import com.longterm.artschools.ui.components.vkauth.VkAuthScreen
import com.longterm.artschools.ui.components.vkauth.components.OnVkAuthResult

sealed interface Destination {
    val route: String
        get() = this::class.simpleName!!

    val isNavigationVisible: Boolean
        get() = true

    @Composable
    fun GetComposable(navController: NavController, navBackStackEntry: NavBackStackEntry)

    object Auth : Destination {
        @Composable
        override fun GetComposable(navController: NavController, navBackStackEntry: NavBackStackEntry) {
            return AuthScreen(
                navigateToVkAuth = { navController.navigate(VkAuth(it)) },
                navigateToMainScreen = {
                    navController.navigate(BottomBarDestination.Main) {
                        popUpTo(Auth.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }

    object Onboarding : Destination {
        @Composable
        override fun GetComposable(navController: NavController, navBackStackEntry: NavBackStackEntry) {
            return OnboardingRootScreen(
                navigateToVkAuth = { navController.navigate(VkAuth(it)) },
                navigateToMainScreen = {
                    navController.navigate(BottomBarDestination.Main) {
                        popUpTo(Onboarding.route) {
                            inclusive = true
                        }
                    }
                },
                navigateToLogin = { navController.navigate(Auth) }
            )
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
        override fun GetComposable(navController: NavController, navBackStackEntry: NavBackStackEntry) {
            VkAuthScreen(onResult) { navController.popBackStack() }
        }
    }

    object Article : Destination {
        override val route: String
            get() = "Article/{$ARGUMENT}"

        operator fun invoke(articleId: Int): String {
            return "Article/$articleId"
        }

        @Composable
        override fun GetComposable(navController: NavController, navBackStackEntry: NavBackStackEntry) {
            val id = navBackStackEntry.arguments?.getString(ARGUMENT)
            ArticleScreen(id?.toIntOrNull() ?: -1) {
                navController.popBackStack()
            }
        }

        const val ARGUMENT = "articleId"
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