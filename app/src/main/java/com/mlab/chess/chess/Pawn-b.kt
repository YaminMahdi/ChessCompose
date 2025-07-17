package com.mlab.chess.chess

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.mlab.chess.Chess

public val Chess.`Pawn-b`: ImageVector
    get() {
        if (`_pawn-b` != null) {
            return `_pawn-b`!!
        }
        `_pawn-b` = Builder(name = "Pawn-b", defaultWidth = 128.0.dp, defaultHeight = 128.0.dp,
                viewportWidth = 45.0f, viewportHeight = 45.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(22.5f, 9.0f)
                curveTo(20.29f, 9.0f, 18.5f, 10.79f, 18.5f, 13.0f)
                curveTo(18.5f, 13.89f, 18.79f, 14.71f, 19.28f, 15.38f)
                curveTo(17.33f, 16.5f, 16.0f, 18.59f, 16.0f, 21.0f)
                curveTo(16.0f, 23.03f, 16.94f, 24.84f, 18.41f, 26.03f)
                curveTo(15.41f, 27.09f, 11.0f, 31.58f, 11.0f, 39.5f)
                lineTo(34.0f, 39.5f)
                curveTo(34.0f, 31.58f, 29.59f, 27.09f, 26.59f, 26.03f)
                curveTo(28.06f, 24.84f, 29.0f, 23.03f, 29.0f, 21.0f)
                curveTo(29.0f, 18.59f, 27.67f, 16.5f, 25.72f, 15.38f)
                curveTo(26.21f, 14.71f, 26.5f, 13.89f, 26.5f, 13.0f)
                curveTo(26.5f, 10.79f, 24.71f, 9.0f, 22.5f, 9.0f)
                close()
            }
        }
        .build()
        return `_pawn-b`!!
    }

private var `_pawn-b`: ImageVector? = null
