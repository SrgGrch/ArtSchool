package com.longterm.artschools.ui.components.common

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import coil.request.ImageRequest
import com.longterm.artschools.R

@Composable
fun ImageRequest.Builder.preview(@DrawableRes resId: Int = R.drawable.ill_mock_photo) =
    if (LocalView.current.isInEditMode) {
        placeholder(resId)
    } else this