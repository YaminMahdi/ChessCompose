package com.mlab.chess.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mlab.chess.model.ChessPiece
import com.mlab.chess.model.PieceColor
import com.mlab.chess.model.PieceType

/**
 * Composable function to display a chess piece
 * @param piece The chess piece to display
 * @param modifier Modifier for the composable
 * @param size The size of the chess piece
 * @param tint Optional tint color for the piece
 */
@Composable
fun ChessPieceIcon(
    piece: ChessPiece,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    tint: Color = Color.Unspecified
) {
    val imageVector = ChessPieceImageVectors.getImageVector(piece.type, piece.color)
    val colorName = if (piece.color == PieceColor.WHITE) "White" else "Black"
    val contentDesc = "$colorName ${piece.type.name.lowercase().capitalize()}"
    
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDesc,
            modifier = Modifier
                .size(size)
                .semantics { contentDescription = contentDesc },
            tint = tint
        )
    }
}

/**
 * Extension function to capitalize the first letter of a string
 */
private fun String.capitalize(): String {
    return this.replaceFirstChar { it.uppercase() }
}