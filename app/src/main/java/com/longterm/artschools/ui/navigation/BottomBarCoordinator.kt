package com.longterm.artschools.ui.navigation

class BottomBarCoordinator {
//    fun needToShowBottomBar(route: String?): Boolean {
//        return when (route) {
//            Destination.VkAuth.route,
//            Destination.Onboarding.route,
//            Destination.Auth.route,
//            Destination.Splash.route -> false
//
//            null -> false
//            else -> true
//        }
//    }

    fun needToShowBottomBar(route: String?): Boolean {
        return when (route) {
            BottomBarDestination.Main.route,
            BottomBarDestination.Courses.route,
            BottomBarDestination.Map.route -> true

            else -> false
        }
    }

}