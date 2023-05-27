package com.longterm.artschools.ui.navigation

import androidx.annotation.DrawableRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.longterm.artschools.R
import com.longterm.artschools.ui.components.main.MainScreen

sealed class BottomBarDestination(
    val title: String,
    @DrawableRes val icon: Int
) : Destination {
    object Main : BottomBarDestination("Главный", R.drawable.ic_tab_main) {
        @Composable
        override fun GetComposable(navController: NavController) {
            MainScreen()
        }
    }

    object Courses : BottomBarDestination("Курсы", R.drawable.ic_tab_courses) {
        @Composable
        override fun GetComposable(navController: NavController) {
            Text(text = "Сourses") //todo
        }
    }

    object Map : BottomBarDestination("Карта", R.drawable.ic_tab_map) {
        @Composable
        override fun GetComposable(navController: NavController) {
            Text(text = "Map") //todo
        }
    }
}