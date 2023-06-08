package com.longterm.artschools.ui.components.achievements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import com.longterm.artschools.R
import com.longterm.artschools.ui.core.theme.ArtTextStyle
import com.longterm.artschools.ui.core.theme.Colors
import com.longterm.artschools.ui.core.utils.PreviewContext
import com.longterm.artschools.ui.core.utils.dpf
import org.koin.androidx.compose.getViewModel
import android.graphics.Paint as OgPaint

@Composable
fun AchievementsScreen(back: () -> Unit) {
    val vm: AchievementsVm = getViewModel()
    val state by vm.state.collectAsState()
    Content(state, back, vm::retry)
}

@Composable
private fun Content(state: AchievementsVm.State, back: () -> Unit, retry: () -> Unit) {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .background(Color.White)
            .verticalScroll(scrollState)
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

        when (state) {
            AchievementsVm.State.Error -> Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Ошибка, попробуйте еще раз")
                Button(onClick = retry) {
                    Text(text = "Обновить")
                }
            }

            AchievementsVm.State.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

            is AchievementsVm.State.Data -> {
                Column(Modifier.padding(horizontal = 16.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Ваши баллы", style = ArtTextStyle.Button)
                        Text(text = state.score.toString(), style = ArtTextStyle.Button)
                    }
                    Spacer(modifier = Modifier.height(22.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Место среди других\nпользовтелей", style = ArtTextStyle.Button)
                        Text(text = "${state.ratingPosition} из ${state.userCount}", style = ArtTextStyle.Button)
                    }
                    Spacer(modifier = Modifier.height(22.dp))
                    Text(
                        text = pluralStringResource(
                            id = R.plurals.score,
                            count = state.remainingToLevelUp,
                            state.remainingToLevelUp
                        ), style = ArtTextStyle.Body
                    )
                    Spacer(modifier = Modifier.height(22.dp))
                    Text(
                        text = state.status, style = ArtTextStyle.Body
                    )
                    Spacer(modifier = Modifier.height(36.dp))

                    LevelGraphics(state.currentLevel, state.levelCount)
                }
            }
        }
    }
}

@Composable
fun LevelGraphics(
    currentLevel: Int,
    _levelCount: Int
) {
    val levelCount = _levelCount.takeIf { it > currentLevel } ?: (currentLevel + 3)

    val userPic = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_profile))
    val prize = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_prize))

    val paintActive = OgPaint().apply {
        textAlign = OgPaint.Align.CENTER
        textSize = 18f.dpf
        color = Colors.Black.toArgb()
        typeface = ResourcesCompat.getFont(LocalContext.current, R.font.deedeebold)
    }

    val paintDisabled = OgPaint().apply {
        textAlign = OgPaint.Align.CENTER
        textSize = 18f.dpf
        color = Colors.GreyLight2.toArgb()
        typeface = ResourcesCompat.getFont(LocalContext.current, R.font.deedeebold)
    }

    val path = Path()

    Canvas(
        modifier = Modifier
            .padding(start = 56.dp, end = 34.dp)
            .fillMaxWidth()
            .height(102.dp * levelCount)
    ) {
        repeat(levelCount) {
            val textPaint = if (currentLevel >= it + 1) paintActive else paintDisabled
            val color = if (currentLevel >= it + 1) Colors.GreenMain else Colors.GreyLight2

            val yOffset = 72f + (it * 102f.dpf)
            val xOffset = if (it % 2 == 0) 28f.dpf else size.width - 28f.dpf

            drawCircle(
                SolidColor(color),
                radius = 28f.dpf,
                style = Stroke(2f.dpf),
                center = Offset(xOffset, yOffset)
            )

            if (currentLevel >= it + 1) {
                translate(left = xOffset - 33f.dpf, top = yOffset - 33f.dpf) {
                    with(userPic) {
                        draw(userPic.intrinsicSize, colorFilter = ColorFilter.tint(Colors.GreenMain))
                    }
                }
            } else {
                translate(left = xOffset - 12f.dpf, top = yOffset - 12f.dpf) {
                    with(prize) {
                        draw(prize.intrinsicSize)
                    }
                }
            }

            if (it % 2 == 0) drawContext.canvas.nativeCanvas.drawText("${it + 1} уровень", xOffset, yOffset + 50f.dpf, textPaint)
            else drawContext.canvas.nativeCanvas.drawText("${it + 1} уровень", xOffset, yOffset + 50f.dpf, textPaint)

            if (it != levelCount - 1) {
                path.apply {
                    reset()
                    if (it % 2 == 0) {
                        moveTo(
                            xOffset + 40f.dpf,
                            yOffset
                        )
                        cubicTo(
                            xOffset + 52f.dpf,
                            yOffset,
                            size.width - 28f.dpf - xOffset,
                            yOffset,
                            size.width - 50f.dpf,
                            yOffset + 102f.dpf - 34f.dpf
                        )
                    } else {
                        moveTo(
                            size.width - 74f.dpf,
                            yOffset
                        )
                        cubicTo(
                            size.width - 74f.dpf,
                            yOffset,
                            0 + 74f.dpf,
                            yOffset,
                            52f.dpf,
                            yOffset + 102f.dpf - 34f.dpf
                        )
                    }
                }.let { path ->
                    drawPath(
                        path = path,
                        color = if (currentLevel == it + 1) Colors.GreyLight2 else color,
                        style = Stroke(2f.dpf, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f))
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        val state = AchievementsVm.State.Data(
            5,
            4,
            300,
            2,
            10,
            "Вы в начале вашего пути познания мира искусств, продолжайте зарабатывать баллы и открывайте новые уровни"
        )

        Content(state, {}, {})
    }
}

