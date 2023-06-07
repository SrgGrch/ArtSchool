package com.longterm.artschools.ui.components.map.dialog

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.longterm.artschools.R
import com.longterm.artschools.domain.models.LatLng
import com.longterm.artschools.domain.models.points.Point
import com.longterm.artschools.ui.core.findActivity
import com.longterm.artschools.ui.core.theme.ArtTextStyle
import com.longterm.artschools.ui.core.theme.Colors
import com.longterm.artschools.ui.core.utils.PreviewContext
import org.koin.androidx.compose.getViewModel

@Composable
fun MapPointInfoBottomSheet(close: () -> Unit) {
    val vm: MapPointInfoVm = getViewModel()
    val state by vm.state.collectAsState()

    Content(state = state, close = close)
}

@Composable
private fun Content(state: MapPointInfoVm.State, close: () -> Unit) {
    when (state) {
        MapPointInfoVm.State.Error -> {
            LaunchedEffect(key1 = state, block = {
                close()
            })

            return
        }

        is MapPointInfoVm.State.Data -> {
            val point = state.point

            val activity = LocalContext.current.findActivity()

            Column(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(
                        top = 14.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 28.dp
                    )
            ) {
                Box(
                    Modifier
                        .size(42.dp, 4.dp)
                        .clip(RoundedCornerShape(50f))
                        .background(Colors.GreenMain)
                        .align(CenterHorizontally)
                )
                Spacer(Modifier.height(24.dp))
                Text(text = point.name, style = ArtTextStyle.H3)
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Colors.Orange)
                            .align(CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = point.address, style = ArtTextStyle.Body)
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    text = point.description,
                    style = ArtTextStyle.Body
                )
                point.phoneNumber?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            activity?.startActivity(Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:${point.phoneNumber}")))
                        },
                        Modifier
                            .defaultMinSize(minHeight = 52.dp)
                            .fillMaxWidth()
                    ) {
                        Icon(painter = painterResource(id = R.drawable.ic_phone), contentDescription = null)
                        Text(text = "Запись и подробности", style = ArtTextStyle.Button, color = Color.White)
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        Content(MapPointInfoVm.State.Data(
            Point(
                LatLng(1.0, 1.0),
                "ДМШ №8 Островского",
                "Обучение по классам:\n" +
                        "сольфеджио\n" +
                        "музыкальная литература\n" +
                        "фортепиано\n" +
                        "скрипка\n" +
                        "гитара",
                "Академическая (Ленинский просп., 45)",
                "+34567890",
                "1-1"
            )
        ), {})
    }
}