package com.mlab.chess.chess

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.mlab.chess.Chess

val Chess.`King-w`: ImageVector
    get() {
        if (`_king-w` != null) {
            return `_king-w`!!
        }
        `_king-w` = Builder(name = "King-w", defaultWidth = 128.0.dp, defaultHeight = 128.0.dp,
                viewportWidth = 45.0f, viewportHeight = 45.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(22.5f, 11.63f)
                lineTo(22.5f, 6.0f)
                moveTo(20.0f, 8.0f)
                lineTo(25.0f, 8.0f)
            }
            path(fill = SolidColor(Color(0xFFFFFFFF)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Butt, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(22.5f, 25.0f)
                curveTo(22.5f, 25.0f, 27.0f, 17.5f, 25.5f, 14.5f)
                curveTo(25.5f, 14.5f, 24.5f, 12.0f, 22.5f, 12.0f)
                curveTo(20.5f, 12.0f, 19.5f, 14.5f, 19.5f, 14.5f)
                curveTo(18.0f, 17.5f, 22.5f, 25.0f, 22.5f, 25.0f)
            }
            path(fill = SolidColor(Color( 0xFFFFFFFF)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Butt, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(11.5f, 37.0f)
                curveTo(17.0f, 40.5f, 27.0f, 40.5f, 32.5f, 37.0f)
                lineTo(32.5f, 30.0f)
                curveTo(32.5f, 30.0f, 41.5f, 25.5f, 38.5f, 19.5f)
                curveTo(34.5f, 13.0f, 25.0f, 16.0f, 22.5f, 23.5f)
                lineTo(22.5f, 27.0f)
                lineTo(22.5f, 23.5f)
                curveTo(19.0f, 16.0f, 9.5f, 13.0f, 6.5f, 19.5f)
                curveTo(3.5f, 25.5f, 11.5f, 29.5f, 11.5f, 29.5f)
                lineTo(11.5f, 37.0f)
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(11.5f, 30.0f)
                curveTo(17.0f, 27.0f, 27.0f, 27.0f, 32.5f, 30.0f)
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(11.5f, 33.5f)
                curveTo(17.0f, 30.5f, 27.0f, 30.5f, 32.5f, 33.5f)
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(11.5f, 37.0f)
                curveTo(17.0f, 34.0f, 27.0f, 34.0f, 32.5f, 37.0f)
            }
        }
        .build()
        return `_king-w`!!
    }

private var `_king-w`: ImageVector? = null