package com.longterm.artschools.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.longterm.artschools.ui.core.theme.Colors

@Composable
fun ScrollIndicator(
    pageCount: Int,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .height(50.dp)
            .wrapContentWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { iteration ->
            if (pagerState.currentPage == iteration) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clip(CircleShape)
                        .background(Colors.MainGreen)
                        .size(42.dp, 4.dp)
                )

            } else {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clip(CircleShape)
                        .background(Colors.LightGrey)
                        .size(4.dp)

                )
            }
        }
    }
}