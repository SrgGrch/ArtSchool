package com.longterm.artschools.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.google.accompanist.navigation.material.bottomSheet
import com.longterm.artschools.ui.navigation.destination.BottomBarDestination
import com.longterm.artschools.ui.navigation.destination.BottomSheetDestinations
import com.longterm.artschools.ui.navigation.destination.Destination

@Composable
fun CustomNavHost(
    navController: NavHostController,
    startDestination: Destination = BottomBarDestination.Main,
    paddingValues: PaddingValues
) {
    fun NavGraphBuilder.createDestination(
        destination: Destination,
        arguments: List<NamedNavArgument> = emptyList(),
        deepLinks: List<NavDeepLink> = emptyList()
    ) = composable(destination.route, arguments, deepLinks) { destination.GetComposable(navController, it) }

    fun NavGraphBuilder.createBottomSheet(
        destination: Destination,
        arguments: List<NamedNavArgument> = emptyList(),
        deepLinks: List<NavDeepLink> = emptyList()
    ) = bottomSheet(destination.route, arguments, deepLinks) { destination.GetComposable(navController, it) }


    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        Modifier.padding(paddingValues)
    ) {
        createDestination(BottomBarDestination.Main)
        createDestination(BottomBarDestination.Courses)
        createDestination(BottomBarDestination.Map)

        createDestination(Destination.Onboarding)
        createDestination(Destination.Auth)
        createDestination(Destination.VkAuth)
        createDestination(
            Destination.Article,
            arguments = listOf(navArgument(Destination.Article.ARGUMENT) { type = NavType.StringType })
        )
        createDestination(Destination.Splash)
        createDestination(
            Destination.Course,
            arguments = listOf(navArgument(Destination.Course.ARGUMENT) { type = NavType.StringType })
        )
        createDestination(
            Destination.Lesson,
            arguments = listOf(navArgument(Destination.Lesson.ARGUMENT) { type = NavType.StringType })
        )
        createDestination(Destination.Profile)
        createDestination(Destination.Achievements)
        createBottomSheet(BottomSheetDestinations.MapPointInfo)
    }
}

fun NavController.navigate(destination: Destination, builder: NavOptionsBuilder.() -> Unit) {
    navigate(destination, navOptions(builder))
}

fun NavController.navigate(
    route: Destination,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    navigate(route.route, navOptions, navigatorExtras)
}