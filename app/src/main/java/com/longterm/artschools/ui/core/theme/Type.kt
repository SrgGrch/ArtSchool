package com.longterm.artschools.ui.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.longterm.artschools.R

// Set of Material typography styles to start with

val ArtFontFamily = FontFamily(
    Font(R.font.deedee),
    Font(R.font.deedeebold, FontWeight.Bold),
    Font(R.font.deedeebolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.deedeeheavy, FontWeight.ExtraBold),
    Font(R.font.deedeeheavyitalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.deedeeitalic, style = FontStyle.Italic),
    Font(R.font.deedeelight, FontWeight.Light),
    Font(R.font.deedeelightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.deedeethin, FontWeight.Thin),
    Font(R.font.deedeethinitalic, FontWeight.Thin, FontStyle.Italic)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = ArtFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

object ArtTextStyle {
    val H1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 30.sp
    )

    val H3 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 24.sp
    )

    val Body = TextStyle(
        fontSize = 16.sp,
        lineHeight = 22.sp
    )

    val Button = TextStyle(
        fontSize = 18.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight.Bold
    )

    val Chips = TextStyle(
        fontSize = 13.sp,
        lineHeight = 22.sp
    )

    val tab = TextStyle(
        fontSize = 12.sp,
        lineHeight = 14.sp
    )
}