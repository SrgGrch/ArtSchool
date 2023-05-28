package com.longterm.artschools.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.longterm.artschools.ui.components.auth.AuthScreen
import com.longterm.artschools.ui.components.course.CoursesScreen
import com.longterm.artschools.ui.components.lesson.LessonScreen
import com.longterm.artschools.ui.components.main.splash.SplashScreen
import com.longterm.artschools.ui.components.news.ArticleScreen
import com.longterm.artschools.ui.components.onboarding.OnboardingRootScreen
import com.longterm.artschools.ui.components.profile.ProfileScreen
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
                },
                back = {
                    navController.popBackStack()
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

    object Lesson : Destination {
        override val route: String
            get() = "Lesson/{$ARGUMENT}"

        operator fun invoke(lessonId: Int): String {
            return "Lesson/$lessonId"
        }

        @Composable
        override fun GetComposable(navController: NavController, navBackStackEntry: NavBackStackEntry) {
            val id = navBackStackEntry.arguments?.getString(ARGUMENT)
            LessonScreen(id?.toIntOrNull() ?: -1, goBack = { navController.popBackStack() })
        }

        const val ARGUMENT = "lessonId"
    }

    object Course : Destination {
        override val route: String
            get() = "Course/{$ARGUMENT}"

        operator fun invoke(courseId: Int): String {
            return "Course/$courseId"
        }

        @Composable
        override fun GetComposable(navController: NavController, navBackStackEntry: NavBackStackEntry) {
            val id = navBackStackEntry.arguments?.getString(ARGUMENT)
            CoursesScreen(id?.toIntOrNull() ?: -1, goBack = { navController.popBackStack() }) {
                navController.navigate(Lesson(it))
            }
        }

        const val ARGUMENT = "courseId"
    }

    object Splash : Destination {
        @Composable
        override fun GetComposable(navController: NavController, navBackStackEntry: NavBackStackEntry) {
            SplashScreen {
                navController.navigate(it) {
                    popUpTo(Splash.route) {
                        inclusive = true
                    }
                }
            }
        }
    }

    object Profile : Destination {
        @Composable
        override fun GetComposable(navController: NavController, navBackStackEntry: NavBackStackEntry) {
            ProfileScreen(
                back = { navController.popBackStack() },
                routeToOnboarding = {
                    navController.navigate(Onboarding) {
                        popUpTo(Onboarding.route) {
                            inclusive = true
                        }
                    }
                }
            )
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