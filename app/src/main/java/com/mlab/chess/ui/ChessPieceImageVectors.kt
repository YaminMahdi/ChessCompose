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
            PieceType.KING -> Chess.`King-w`
            PieceType.QUEEN -> Chess.`Queen-w`
            PieceType.ROOK -> Chess.`Rook-w`
            PieceType.BISHOP -> Chess.`Bishop-w`
            PieceType.KNIGHT -> Chess.`Knight-w`
            PieceType.PAWN -> Chess.`Pawn-w`
        }
    }
    
    /**
     * Get the ImageVector for a black chess piece
     * @param type The type of the chess piece
     * @return The ImageVector representing the black chess piece
     */
    private fun getBlackPieceImageVector(type: PieceType): ImageVector {
        return when (type) {
            PieceType.KING -> Chess.`King-b`
            PieceType.QUEEN -> Chess.`Queen-b`
            PieceType.ROOK -> Chess.`Rook-b`
            PieceType.BISHOP -> Chess.`Bishop-b`
            PieceType.KNIGHT -> Chess.`Knight-b`
            PieceType.PAWN -> Chess.`Pawn-b`
        }
    }
}