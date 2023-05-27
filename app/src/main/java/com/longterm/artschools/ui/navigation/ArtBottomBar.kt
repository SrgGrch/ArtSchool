package com.longterm.artschools.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.longterm.artschools.ui.core.utils.PreviewContext
import org.koin.compose.koinInject

@Composable
fun ArtBottomBar(navController: NavController) {
    val items = listOf(
        BottomBarDestination.Main,
        BottomBarDestination.Courses,
        BottomBarDestination.Map,
    )

    val bottomBarCoordinator: BottomBarCoordinator = koinInject()

    var visibilityState by remember {
        mutableStateOf(navController.currentDestination?.route.let {
            bottomBarCoordinator.needToShowBottomBar(
                it
            )
        })
    }

    if (visibilityState)
        NavigationBar(
//        backgroundColor = colorResource(id = Color.White),
            contentColor = Color.Black
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            visibilityState = navController.currentDestination?.route.let { bottomBarCoordinator.needToShowBottomBar(it) }

            items.forEach { item ->
                NavigationBarItem(
                    icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                    label = {
                        Text(
                            text = item.title,
                            fontSize = 12.sp
                        )
                    },
                    alwaysShowLabel = true,
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {

                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
}

@Preview
@Composable
private fun Preview() {
    PreviewContext {
        ArtBottomBar(rememberNavController())
    }
}