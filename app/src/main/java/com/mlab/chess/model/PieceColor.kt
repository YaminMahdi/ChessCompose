package com.mlab.chess.model

/**
 * Represents the color of a chess piece
 */
enum class PieceColor {
    WHITE,
    BLACK;
    
    /**
     * Returns the opposite color
     * @return BLACK if this is WHITE, WHITE if this is BLACK
     */
    fun opposite(): PieceColor = if (this == WHITE) BLACK else WHITE
}