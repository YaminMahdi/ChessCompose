package com.mlab.chess.chess

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.mlab.chess.Chess

val Chess.`Knight-b`: ImageVector
    get() {
        if (`_knight-b` != null) {
            return `_knight-b`!!
        }
        `_knight-b` = Builder(name = "Knight-b", defaultWidth = 128.0.dp, defaultHeight = 128.0.dp,
                viewportWidth = 45.0f, viewportHeight = 45.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(22.0f, 10.0f)
                curveTo(32.5f, 11.0f, 38.5f, 18.0f, 38.0f, 39.0f)
                lineTo(15.0f, 39.0f)
                curveTo(15.0f, 30.0f, 25.0f, 32.5f, 23.0f, 18.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(24.0f, 18.0f)
                curveTo(24.38f, 20.91f, 18.45f, 25.37f, 16.0f, 27.0f)
                curveTo(13.0f, 29.0f, 13.18f, 31.34f, 11.0f, 31.0f)
                curveTo(9.958f, 30.06f, 12.41f, 27.96f, 11.0f, 28.0f)
                curveTo(10.0f, 28.0f, 11.19f, 29.23f, 10.0f, 30.0f)
                curveTo(9.0f, 30.0f, 5.997f, 31.0f, 6.0f, 26.0f)
                curveTo(6.0f, 24.0f, 12.0f, 14.0f, 12.0f, 14.0f)
                curveTo(12.0f, 14.0f, 13.89f, 12.1f, 14.0f, 10.5f)
                curveTo(13.27f, 9.506f, 13.5f, 8.5f, 13.5f, 7.5f)
                curveTo(14.5f, 6.5f, 16.5f, 10.0f, 16.5f, 10.0f)
                lineTo(18.5f, 10.0f)
                curveTo(18.5f, 10.0f, 19.28f, 8.008f, 21.0f, 7.0f)
                curveTo(22.0f, 7.0f, 22.0f, 10.0f, 22.0f, 10.0f)
            }
            path(fill = SolidColor(Color(0xFFFFFFFF)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Butt, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(24.55f, 10.4f)
                lineTo(24.1f, 11.85f)
                lineTo(24.6f, 12.0f)
                curveTo(27.75f, 13.0f, 30.25f, 14.49f, 32.5f, 18.75f)
                curveTo(34.75f, 23.01f, 35.75f, 29.06f, 35.25f, 39.0f)
                lineTo(35.2f, 39.5f)
                lineTo(37.45f, 39.5f)
                lineTo(37.5f, 39.0f)
                curveTo(38.0f, 28.94f, 36.62f, 22.15f, 34.25f, 17.66f)
                curveTo(31.88f, 13.17f, 28.46f, 11.02f, 25.06f, 10.5f)
                lineTo(24.55f, 10.4f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFFFFFFFF)),
                    strokeLineWidth = 1.5f, strokeLineCap = Butt, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(9.5f, 25.5f)
                arcTo(0.5f, 0.5f, 0.0f, true, true, 8.5f, 25.5f)
                arcTo(0.5f, 0.5f, 0.0f, true, true, 9.5f, 25.5f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFFFFFFFF)),
                    strokeLineWidth = 1.5f, strokeLineCap = Butt, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(15.25f, 14.2f)
                arcTo(0.5f, 1.5f, 30.0f, true, true, 13.75f, 16.8f)
                arcTo(0.5f, 1.5f, 30.0f, true, true, 15.25f, 14.2f)
                close()
            }
        }
        .build()
        return `_knight-b`!!
    }

private var `_knight-b`: ImageVector? = null
