package com.mlab.chess.model

/**
 * Represents a chess piece on the board
 * @param type The type of piece (King, Queen, Rook, etc.)
 * @param color The color of the piece (White or Black)
 * @param position The current position of the piece on the board
 * @param hasMoved Whether the piece has moved (useful for castling and pawn first move)
 * @param justMovedTwoSquares Whether a pawn just moved two squares (useful for en passant)
 */
data class ChessPiece(
    val type: PieceType,
    val color: PieceColor,
    val position: Position,
    val hasMoved: Boolean = false,
    val justMovedTwoSquares: Boolean = false
) {
    /**
     * Creates a copy of this piece at a new position and marks it as moved
     * @param newPosition The new position for the piece
     * @return A new ChessPiece at the specified position with hasMoved set to true
     */
    fun moveTo(newPosition: Position): ChessPiece {
        val movedTwoSquares = type == PieceType.PAWN && 
                             !hasMoved && 
                             kotlin.math.abs(position.row - newPosition.row) == 2
        
        return copy(
            position = newPosition, 
            hasMoved = true,
            justMovedTwoSquares = movedTwoSquares
        )
    }
    
    /**
     * Returns a string representation of the piece
     * @return A string in format "Color Type at Position"
     */
    override fun toString(): String {
        return "$color $type at ${position.toChessNotation()}"
    }
}