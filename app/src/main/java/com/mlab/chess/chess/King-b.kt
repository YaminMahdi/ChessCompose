package com.mlab.chess.chess

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeCap.Companion.Square
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.mlab.chess.Chess

public val Chess.`King-b`: ImageVector
    get() {
        if (`_king-b` != null) {
            return `_king-b`!!
        }
        `_king-b` = Builder(name = "King-b", defaultWidth = 128.0.dp, defaultHeight = 128.0.dp,
                viewportWidth = 45.0f, viewportHeight = 45.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(22.5f, 11.63f)
                lineTo(22.5f, 6.0f)
                moveTo(20.0f, 8.0f)
                lineTo(25.0f, 8.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Butt, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(22.5f, 25.0f)
                curveTo(22.5f, 25.0f, 27.0f, 17.5f, 25.5f, 14.5f)
                curveTo(25.5f, 14.5f, 24.5f, 12.0f, 22.5f, 12.0f)
                curveTo(20.5f, 12.0f, 19.5f, 14.5f, 19.5f, 14.5f)
                curveTo(18.0f, 17.5f, 22.5f, 25.0f, 22.5f, 25.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
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
                    strokeLineWidth = 1.5f, strokeLineCap = Square, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(22.5f, 20.0f)
                curveTo(22.5f, 20.0f, 25.75f, 17.0f, 24.5f, 14.5f)
                curveTo(24.5f, 14.5f, 24.0f, 13.0f, 22.5f, 13.0f)
                curveTo(21.0f, 13.0f, 20.5f, 14.5f, 20.5f, 14.5f)
                curveTo(19.25f, 17.0f, 22.5f, 20.0f, 22.5f, 20.0f)
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(32.0f, 29.5f)
                curveTo(32.0f, 29.5f, 40.5f, 25.5f, 38.03f, 19.85f)
                curveTo(34.15f, 14.0f, 25.0f, 18.0f, 22.5f, 24.5f)
                lineTo(22.51f, 26.6f)
                lineTo(22.5f, 24.5f)
                curveTo(20.0f, 18.0f, 9.906f, 14.0f, 6.997f, 19.85f)
                curveTo(4.5f, 25.5f, 11.85f, 28.85f, 11.85f, 28.85f)
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(11.5f, 30.0f)
                curveTo(17.0f, 27.0f, 27.0f, 27.0f, 32.5f, 30.0f)
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(11.5f, 33.5f)
                curveTo(17.0f, 30.5f, 27.0f, 30.5f, 32.5f, 33.5f)
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(11.5f, 37.0f)
                curveTo(17.0f, 34.0f, 27.0f, 34.0f, 32.5f, 37.0f)
            }
        }
        .build()
        return `_king-b`!!
    }

private var `_king-b`: ImageVector? = null
