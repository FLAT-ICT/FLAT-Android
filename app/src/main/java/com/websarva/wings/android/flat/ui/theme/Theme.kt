package com.websarva.wings.android.flat.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

//private val DarkColorPalette = darkColors(
//    primary = PrimarySolid,
//    primaryVariant = PrimaryPale,
//    secondary =
//)
//
//private val LightColorPalette = lightColors(
//    primary = PrimarySolid,
//    primaryVariant = PrimaryPale,
//    secondary = Teal200

/* Other default colors to override
background = Color.White,
surface = Color.White,
onPrimary = Color.White,
onSecondary = Color.Black,
onBackground = Color.Black,
onSurface = Color.Black,
*/

@Immutable
data class FLATColors(
    val primary: Color,
    val primaryVariant: Color
)

val LocalFLATColors = staticCompositionLocalOf {
    FLATColors(
        primary = PrimarySolid,
        primaryVariant = PrimaryPale
    )
}

@Composable
fun FLATTheme(
    /* ... */
    content: @Composable () -> Unit
) {
    val FLATColors = FLATColors(
        primary = PrimarySolid,
        primaryVariant = PrimaryPale
    )
    CompositionLocalProvider(LocalFLATColors provides FLATColors) {
        MaterialTheme(
            /* colors = ..., typography = ..., shapes = ... */
            content = content
        )
    }
}

// Use with eg. FLATTheme.colors.primary
object FLATTheme {
    val colors: FLATColors
        @Composable
        get() = LocalFLATColors.current
}