package com.longterm.artschools.ui.navigation

import com.longterm.artschools.ui.navigation.destination.BottomBarDestination
import com.longterm.artschools.ui.navigation.destination.BottomSheetDestinations

class BottomBarCoordinator {
    fun needToShowBottomBar(route: String?): Boolean {
        return when (route) {
            BottomBarDestination.Main.route,
            BottomBarDestination.Courses.route,
            BottomBarDestination.Map.route,
            BottomSheetDestinations.MapPointInfo.route -> true

            else -> false
        }
    }

}