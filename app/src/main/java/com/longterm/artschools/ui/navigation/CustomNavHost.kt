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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

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
    ) = composable(destination.route, arguments, deepLinks) { destination.GetComposable(navController) }

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
//        createDestination(Destination.Register)
    }
}

fun NavController.navigate(destination: Destination) {
    navigate(destination.route)
}