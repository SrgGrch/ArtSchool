package com.longterm.artschools.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun CustomNavHost(
    navController: NavHostController,
    startDestination: Destination = Destination.Main
) {
    fun NavGraphBuilder.createDestination(destination: Destination) =
        composable(destination.name) { destination.GetComposable(navController) }

    NavHost(navController = navController, startDestination = startDestination.name) {
        createDestination(Destination.Main)
        createDestination(Destination.Onboarding)
        createDestination(Destination.Auth)
        createDestination(Destination.VkAuth)
//        createDestination(Destination.Register)
    }
}

fun NavController.navigate(destination: Destination) {
    navigate(destination.name)
}