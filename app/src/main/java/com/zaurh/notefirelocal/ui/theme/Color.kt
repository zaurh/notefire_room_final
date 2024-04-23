package com.zaurh.notefirelocal.ui.theme

import androidx.compose.ui.graphics.Color

sealed class ThemeColors(
    val background: Color,
    val surface: Color,
    val title: Color,
    val text: Color,
    val secondBackground: Color
) {
    object Night : ThemeColors(
        background = Color(0xFF272727),
        surface = Color(0xFF212121),
        title = Color(0xFFFFFFFF),
        text = Color.Gray,
        secondBackground = Color(0xFF313131),
    )

    object Day : ThemeColors(
        background = Color(0xFFFF8D29),
        surface = Color(0xFFEBFFF9),
        title = Color(0xFF000000),
        text = Color(0xFFB3B3B3),
        secondBackground = Color(0xFFE3F2FD)
    )
}