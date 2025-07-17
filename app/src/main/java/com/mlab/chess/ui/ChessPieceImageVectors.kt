package com.mlab.chess.ui

import androidx.compose.ui.graphics.vector.ImageVector
import com.mlab.chess.Chess
import com.mlab.chess.chess.*
import com.mlab.chess.model.PieceColor
import com.mlab.chess.model.PieceType

/**
 * Utility object to get ImageVector resources for chess pieces
 */
object ChessPieceImageVectors {
    
    /**
     * Get the ImageVector for a specific piece type and color
     * @param type The type of the chess piece
     * @param color The color of the chess piece
     * @return The ImageVector representing the chess piece
     */
    fun getImageVector(type: PieceType, color: PieceColor): ImageVector {
        return when (color) {
            PieceColor.WHITE -> getWhitePieceImageVector(type)
            PieceColor.BLACK -> getBlackPieceImageVector(type)
        }
    }
    
    /**
     * Get the ImageVector for a white chess piece
     * @param type The type of the chess piece
     * @return The ImageVector representing the white chess piece
     */
    private fun getWhitePieceImageVector(type: PieceType): ImageVector {
        return when (type) {
            PieceType.KING -> `King-w`
            PieceType.QUEEN -> `Queen-w`
            PieceType.ROOK -> `Rook-w`
            PieceType.BISHOP -> `Bishop-w`
            PieceType.KNIGHT -> `Knight-w`
            PieceType.PAWN -> `Pawn-w`
        }
    }
    
    /**
     * Get the ImageVector for a black chess piece
     * @param type The type of the chess piece
     * @return The ImageVector representing the black chess piece
     */
    private fun getBlackPieceImageVector(type: PieceType): ImageVector {
        return when (type) {
            PieceType.KING -> `King-b`
            PieceType.QUEEN -> `Queen-b`
            PieceType.ROOK -> `Rook-b`
            PieceType.BISHOP -> `Bishop-b`
            PieceType.KNIGHT -> `Knight-b`
            PieceType.PAWN -> `Pawn-b`
        }
    }
}