package com.longterm.artschools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.longterm.artschools.ui.core.theme.ArtSchoolsTheme
import com.longterm.artschools.ui.navigation.ArtBottomBar
import com.longterm.artschools.ui.navigation.BottomBarCoordinator
import com.longterm.artschools.ui.navigation.CustomNavHost
import com.longterm.artschools.ui.navigation.destination.Destination
import com.yandex.mapkit.MapKitFactory
import org.koin.compose.koinInject


@UnstableApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSchoolsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main()
                }
            }
        }
    }

    override fun onStop() {
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
    }
}

@Composable
fun Main() {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarCoordinator: BottomBarCoordinator = koinInject()
    var bottomBarState by rememberSaveable { (mutableStateOf(true)) }

    val currentRoute = navBackStackEntry?.destination?.route
    bottomBarState = bottomBarCoordinator.needToShowBottomBar(currentRoute)

    ModalBottomSheetLayout(bottomSheetNavigator, sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)) {
        Scaffold(bottomBar = {
            AnimatedVisibility(
                visible = bottomBarState,
                enter = fadeIn() + expandVertically(),
                exit = shrinkVertically() + fadeOut(),
            ) {
                ArtBottomBar(navController)
            }
        }) {
            CustomNavHost(navController, Destination.Splash, it)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ArtSchoolsTheme {
        Main()
    }
}