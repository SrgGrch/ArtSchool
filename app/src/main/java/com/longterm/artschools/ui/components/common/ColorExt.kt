package com.longterm.artschools.ui.components.common

import androidx.compose.ui.graphics.Color

fun Color.Companion.fromHex(color: String): Color = Color(android.graphics.Color.parseColor(color))