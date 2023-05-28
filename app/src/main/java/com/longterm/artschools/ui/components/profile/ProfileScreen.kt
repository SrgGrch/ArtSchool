package com.longterm.artschools.ui.components.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.longterm.artschools.R
import com.longterm.artschools.ui.core.theme.ArtTextStyle
import com.longterm.artschools.ui.core.theme.Colors
import com.longterm.artschools.ui.core.theme.Dimens.horizontalPadding
import com.longterm.artschools.ui.core.utils.PreviewContext
import org.koin.androidx.compose.getViewModel

@Composable
fun ProfileScreen(back: () -> Unit, routeToOnboarding: () -> Unit) {
    val vm: ProfileVm = getViewModel()
    val state by vm.state.collectAsState()
    val context = LocalContext.current

    Column(
        Modifier.padding(horizontal = horizontalPadding)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = back) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "назад",
                    tint = Colors.GreenMain
                )
            }

            Text(text = "Профиль", style = ArtTextStyle.Body)

            Spacer(modifier = Modifier.size(48.dp))
        }

        when (val st = state) {
            ProfileVm.State.Error -> Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Ошибка, попробуйте еще раз")
                Button(onClick = vm::retry) {
                    Text(text = "Обновить")
                }
            }

            ProfileVm.State.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

            ProfileVm.State.SignOut -> LaunchedEffect(key1 = Unit, block = {
                routeToOnboarding()
            })

            is ProfileVm.State.Data -> {
                Image(
                    painter = painterResource(id = R.drawable.ic_intro), // todo
                    contentDescription = "Аватар",
                    Modifier
                        .align(CenterHorizontally)
                        .clip(CircleShape)
                        .height(108.dp)
                        .aspectRatio(1f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = { Toast.makeText(context, "Работа в прогрессе ;)", Toast.LENGTH_SHORT).show() }) {
                    Text(text = "Мои данные", style = ArtTextStyle.Button, color = Color.Black)
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = { Toast.makeText(context, "Работа в прогрессе ;)", Toast.LENGTH_SHORT).show() }) {
                    Text(text = "Мои курсы и ответы", style = ArtTextStyle.Button, color = Color.Black)
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = { Toast.makeText(context, "Работа в прогрессе ;)", Toast.LENGTH_SHORT).show() }) {
                    Text(text = "Достижения", style = ArtTextStyle.Button, color = Color.Black)
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = { vm.signOut() }) {
                    Text(text = "Выход", style = ArtTextStyle.Button, color = Color.Black)
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Версия 0.1.1",
                    Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = ArtTextStyle.Body
                )

                Spacer(modifier = Modifier.height(26.dp))
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        ProfileScreen({}, {})
    }
}