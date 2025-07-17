package com.mlab.chess.model.rules

import com.mlab.chess.model.ChessPiece
import com.mlab.chess.model.PieceColor
import com.mlab.chess.model.Position

/**
 * Interface defining the core chess rules and movement validation
 */
interface ChessRules {
    /**
     * Gets all valid moves for a specific piece on the current board
     * @param piece The chess piece to check moves for
     * @param board The current board state as a map of positions to pieces
     * @return A set of valid positions the piece can move to
     */
    fun getValidMoves(piece: ChessPiece, board: Map<Position, ChessPiece>): Set<Position>
    
    /**
     * Checks if a king is in check
     * @param kingPosition The position of the king to check
     * @param board The current board state
     * @param kingColor The color of the king
     * @return true if the king is in check, false otherwise
     */
    fun isInCheck(kingPosition: Position, board: Map<Position, ChessPiece>, kingColor: PieceColor): Boolean
    
    /**
     * Checks if a move would put or leave the player's own king in check
     * @param from The starting position of the piece to move
     * @param to The destination position
     * @param board The current board state
     * @return true if the move is legal (doesn't put/leave own king in check), false otherwise
     */
    fun isMoveLegal(from: Position, to: Position, board: Map<Position, ChessPiece>): Boolean
    
    /**
     * Checks if the game is in checkmate for a specific color
     * @param color The color to check for checkmate
     * @param board The current board state
     * @return true if the specified color is in checkmate
     */
    fun isCheckmate(color: PieceColor, board: Map<Position, ChessPiece>): Boolean
    
    /**
     * Checks if the game is in stalemate for a specific color
     * @param color The color to check for stalemate
     * @param board The current board state
     * @return true if the specified color is in stalemate
     */
    fun isStalemate(color: PieceColor, board: Map<Position, ChessPiece>): Boolean
}