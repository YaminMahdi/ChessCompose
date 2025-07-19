package com.mlab.chess.chess

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.StrokeJoin.Companion.Round
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.mlab.chess.Chess

val Chess.`Bishop-w`: ImageVector
    get() {
        if (`_bishop-w` != null) {
            return `_bishop-w`!!
        }
        `_bishop-w` = Builder(name = "Bishop-w", defaultWidth = 128.0.dp, defaultHeight = 128.0.dp,
                viewportWidth = 45.0f, viewportHeight = 45.0f).apply {
            path(fill = SolidColor(Color(0xFFFFFFFF)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Butt, strokeLineJoin = Round,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(22.5f, 8.0f)
                moveToRelative(-2.5f, 0.0f)
                arcToRelative(2.5f, 2.5f, 0.0f, true, true, 5.0f, 0.0f)
                arcToRelative(2.5f, 2.5f, 0.0f, true, true, -5.0f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFFFFFFFF)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Butt, strokeLineJoin = Round,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(15.0f, 32.0f)
                curveTo(17.5f, 34.5f, 27.5f, 34.5f, 30.0f, 32.0f)
                curveTo(30.5f, 30.5f, 30.0f, 30.0f, 30.0f, 30.0f)
                curveTo(30.0f, 27.5f, 27.5f, 26.0f, 27.5f, 26.0f)
                curveTo(33.0f, 24.5f, 33.5f, 14.5f, 22.5f, 10.5f)
                curveTo(11.5f, 14.5f, 12.0f, 24.5f, 17.5f, 26.0f)
                curveTo(17.5f, 26.0f, 15.0f, 27.5f, 15.0f, 30.0f)
                curveTo(15.0f, 30.0f, 14.5f, 30.5f, 15.0f, 32.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFFFFFFF)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Butt, strokeLineJoin = Round,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(9.0f, 36.0f)
                curveTo(12.39f, 35.03f, 19.11f, 36.43f, 22.5f, 34.0f)
                curveTo(25.89f, 36.43f, 32.61f, 35.03f, 36.0f, 36.0f)
                curveTo(36.0f, 36.0f, 37.65f, 36.54f, 39.0f, 38.0f)
                curveTo(38.32f, 38.97f, 37.35f, 38.99f, 36.0f, 38.5f)
                curveTo(32.61f, 37.53f, 25.89f, 38.96f, 22.5f, 37.5f)
                curveTo(19.11f, 38.96f, 12.39f, 37.53f, 9.0f, 38.5f)
                curveTo(7.646f, 38.99f, 6.677f, 38.97f, 6.0f, 38.0f)
                curveTo(7.354f, 36.06f, 9.0f, 36.0f, 9.0f, 36.0f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = StrokeCap.Companion.Round,
                    strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(17.5f, 26.0f)
                lineTo(27.5f, 26.0f)
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = StrokeCap.Companion.Round,
                    strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(15.0f, 30.0f)
                lineTo(30.0f, 30.0f)
            }
            path(fill = SolidColor(Color(0x00FFFFFF)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = StrokeCap.Companion.Round,
                    strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(22.5f, 15.0f)
                lineTo(22.5f, 22.0f)
                moveTo(20.0f, 17.5f)
                lineTo(25.0f, 17.5f)
            }
        }
        .build()
        return `_bishop-w`!!
    }

private var `_bishop-w`: ImageVector? = null
