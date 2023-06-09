package com.longterm.artschools.ui.navigation.destination

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.longterm.artschools.R
import com.longterm.artschools.ui.components.coursesList.CoursesListScreen
import com.longterm.artschools.ui.components.main.MainScreen
import com.longterm.artschools.ui.components.map.MapScreen
import com.longterm.artschools.ui.navigation.navigate

sealed class BottomBarDestination(
    val title: String,
    @DrawableRes val icon: Int
) : Destination {
    object Main : BottomBarDestination("Главный", R.drawable.ic_tab_main) {
        @Composable
        override fun GetComposable(
            navController: NavController,
            navBackStackEntry: NavBackStackEntry
        ) {
            MainScreen(
                navigateToArticle = {
                    navController.navigate(Destination.Article(it))
                },
                navigateToProfile = {
                    navController.navigate(Destination.Profile) {
                        launchSingleTop = true
                    }
                },
                navigateToAchievements = {
                    navController.navigate(Destination.Achievements) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }

    object Courses : BottomBarDestination("Курсы", R.drawable.ic_tab_courses) {
        @Composable
        override fun GetComposable(
            navController: NavController,
            navBackStackEntry: NavBackStackEntry
        ) {
            CoursesListScreen {
                navController.navigate(Destination.Course(it))
            }
        }
    }

    object Map : BottomBarDestination("Карта", R.drawable.ic_tab_map) {
        @Composable
        override fun GetComposable(
            navController: NavController,
            navBackStackEntry: NavBackStackEntry
        ) {

            val d by navController.currentBackStackEntryAsState()
            MapScreen(openPointInfo = {
                navController.navigate(BottomSheetDestinations.MapPointInfo)
            }, d?.destination?.route ?: "")
        }
    }
}