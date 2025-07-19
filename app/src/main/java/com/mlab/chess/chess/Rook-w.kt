package com.mlab.chess.chess

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.StrokeJoin.Companion.Round
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.mlab.chess.Chess

public val Chess.`Rook-w`: ImageVector
    get() {
        if (`_rook-w` != null) {
            return `_rook-w`!!
        }
        `_rook-w` = Builder(name = "Rook-w", defaultWidth = 128.0.dp, defaultHeight = 128.0.dp,
                viewportWidth = 45.0f, viewportHeight = 45.0f).apply {
            path(fill = SolidColor(Color(0xFFFFFFFF)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Butt, strokeLineJoin = Round,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(11.0f, 14.0f)
                lineTo(11.0f, 9.0f)
                lineTo(15.0f, 9.0f)
                lineTo(15.0f, 11.0f)
                lineTo(20.0f, 11.0f)
                lineTo(20.0f, 9.0f)
                lineTo(25.0f, 9.0f)
                lineTo(25.0f, 11.0f)
                lineTo(30.0f, 11.0f)
                lineTo(30.0f, 9.0f)
                lineTo(34.0f, 9.0f)
                lineTo(34.0f, 14.0f)
                lineTo(31.0f, 17.0f)
                lineTo(31.0f, 29.5f)
                lineTo(33.0f, 32.0f)
                lineTo(33.0f, 36.0f)
                lineTo(12.0f, 36.0f)
                lineTo(12.0f, 32.0f)
                lineTo(14.0f, 29.5f)
                lineTo(14.0f, 17.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFFFFFFF)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Butt, strokeLineJoin = Round,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(9.0f, 39.0f)
                lineTo(36.0f, 39.0f)
                lineTo(36.0f, 36.0f)
                lineTo(9.0f, 36.0f)
                lineTo(9.0f, 39.0f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.2f, strokeLineCap = StrokeCap.Companion.Round,
                    strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(11.0f, 14.0f)
                lineTo(34.0f, 14.0f)
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.8f, strokeLineCap = StrokeCap.Companion.Round,
                    strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(14.0f, 17.0f)
                lineTo(31.0f, 17.0f)
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.8f, strokeLineCap = StrokeCap.Companion.Round,
                    strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(14.0f, 29.5f)
                lineTo(31.0f, 29.5f)
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.2f, strokeLineCap = StrokeCap.Companion.Round,
                    strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(12.0f, 32.0f)
                lineTo(33.0f, 32.0f)
            }
        }
        .build()
        return `_rook-w`!!
    }

private var `_rook-w`: ImageVector? = null
