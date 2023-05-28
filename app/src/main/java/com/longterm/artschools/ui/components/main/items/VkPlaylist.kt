package com.longterm.artschools.ui.components.main.items

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.longterm.artschools.R
import com.longterm.artschools.ui.components.common.fromHex
import com.longterm.artschools.ui.components.common.preview
import com.longterm.artschools.ui.components.main.models.MainListItem
import com.longterm.artschools.ui.core.theme.ArtTextStyle
import com.longterm.artschools.ui.core.theme.Colors
import com.longterm.artschools.ui.core.utils.PreviewContext


@Composable
fun VkPlaylist(data: MainListItem.VkPlaylist) {
    val context = LocalContext.current

    Column(Modifier.padding(ItemsPaddingValues)) {
        FlowRow(Modifier.fillMaxWidth()) {
            data.tags.forEach {
                Text(
                    text = it.text,
                    Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(Color.fromHex(it.color))
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    style = ArtTextStyle.tab,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = data.imageUrl)
                        .apply(block = {
//                            crossfade(true)
                            preview()
                        }).build()
                ),
                contentDescription = "Картинка плейлиста",
                Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .size(82.dp),
                contentScale = ContentScale.FillBounds
            )

            Spacer(Modifier.width(12.dp))

            Column {
                Text(text = data.title, style = ArtTextStyle.H3)

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = data.description, style = ArtTextStyle.Body)
            }
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(data.linkToVk)))
            },
            colors = ButtonDefaults.buttonColors(containerColor = Colors.VkBlue),
            contentPadding = PaddingValues(vertical = 14.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_vk_logo), contentDescription = null)
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Слушать в VK",
                fontSize = 18.sp
            )
        }

    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        VkPlaylist(
            MainListItem.VkPlaylist(
                "https://vk.com/music/playlist/-117742128_84942446_d28fc5ffcfe8793c4f",
                "https://cdn-icons-png.flaticon.com/512/4091/4091355.png",
                "То, что слышал каждый!",
                "Собрали плейлист с 21 самыми известными произведениями классических композиторов",
                listOf(
                    MainListItem.Tag("Интервью", "#FF8E75A8"),
                    MainListItem.Tag("🎶 Музыка", "#FFDBB0C2"),
                )
            )
        )
    }
}