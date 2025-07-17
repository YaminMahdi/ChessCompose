package com.mlab.chess.model

/**
 * Enum representing the color of chess pieces
 */
enum class PieceColor {
    WHITE,
    BLACK;
    
    /**
     * Returns the opposite color
     */
    fun opposite(): PieceColor = when (this) {
        WHITE -> BLACK
        BLACK -> WHITE
    }
}