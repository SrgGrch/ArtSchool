package com.longterm.artschools.ui.navigation

class BottomBarCoordinator {
    fun needToShowBottomBar(route: String?): Boolean {
        return when (route) {
            Destination.VkAuth.route,
            Destination.Onboarding.route,
            Destination.Auth.route -> false

            null -> false
            else -> true
        }
    }
}