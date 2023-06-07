package com.longterm.artschools.ui.components.main.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.longterm.artschools.MainActivity
import com.longterm.artschools.R
import com.longterm.artschools.data.UserStorage
import com.longterm.artschools.data.repository.UserRepository
import com.longterm.artschools.ui.core.theme.ArtTextStyle
import com.longterm.artschools.ui.core.theme.Colors
import com.longterm.artschools.ui.core.utils.PreviewContext
import com.longterm.artschools.ui.navigation.destination.BottomBarDestination
import com.longterm.artschools.ui.navigation.destination.Destination
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@Composable
fun SplashScreen(close: (Destination) -> Unit) {
    val userStorage: UserStorage = koinInject()
    val userRepository: UserRepository = koinInject()
    val activity = LocalContext.current as MainActivity
    Row(
        Modifier
            .background(Colors.GreenMain)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val scope = rememberCoroutineScope()
        var state by remember { mutableStateOf(false) }

        LaunchedEffect(key1 = Unit, block = {
            scope.launch {
                delay(1.seconds)
                state = true
                delay(500.milliseconds)
//                activity.startActivity(Intent(activity, PlayerActivity3::class.java))

                val startDestination = if (userStorage.isLoggedIn) {
                    userRepository.updateUser()
                    BottomBarDestination.Main
                } else Destination.Onboarding
                close(startDestination)
            }
        })
        AnimatedVisibility(state) {
            Text(text = "МОСКВОСКИЕ\nШКОЛЫ\nИСКУССТВ", style = ArtTextStyle.H1, color = Color.White)
        }
        Image(
            painter = painterResource(id = R.drawable.ic_reward),
            contentDescription = null,
            Modifier.size(100.dp),
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        SplashScreen {}
    }
}