package com.longterm.artschools.ui.navigation.destination

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.longterm.artschools.ui.components.map.MapPointInfoBottomSheet

sealed interface BottomSheetDestinations : Destination {
    object MapPointInfo : BottomSheetDestinations {
        @Composable
        override fun GetComposable(
            navController: NavController,
            navBackStackEntry: NavBackStackEntry
        ) {
            MapPointInfoBottomSheet()
        }
    }
}