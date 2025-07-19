package com.mlab.chess.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mlab.chess.model.ChessPiece
import com.mlab.chess.model.PieceColor

/**
 * Composable function to display a chess piece with selection highlighting and animations
 * @param piece The chess piece to display
 * @param modifier Modifier for the composable
 * @param size The size of the chess piece
 * @param isSelected Whether the piece is currently selected
 * @param tint Optional tint color for the piece
 */
@Composable
fun ChessPieceIcon(
    piece: ChessPiece,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    isSelected: Boolean = false,
    tint: Color = Color.Unspecified
) {
    val imageVector = ChessPieceImageVectors.getImageVector(piece.type, piece.color)
    val colorName = if (piece.color == PieceColor.WHITE) "White" else "Black"
    val contentDesc = buildString {
        append("$colorName ${piece.type.name.lowercase().capitalize()}")
        if (isSelected) append(", Selected")
    }
    
    // Animation for selection highlighting
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.1f else 1.0f,
        animationSpec = tween(durationMillis = 200),
        label = "piece_selection_scale"
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (isSelected) 0.9f else 1.0f,
        animationSpec = tween(durationMillis = 200),
        label = "piece_selection_alpha"
    )
    
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDesc,
            modifier = Modifier
                .size(size)
                .scale(scale)
                .graphicsLayer(alpha = alpha)
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