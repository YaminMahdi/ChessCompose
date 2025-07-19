package com.mlab.chess.model.rules

import com.mlab.chess.model.ChessPiece
import com.mlab.chess.model.PieceColor
import com.mlab.chess.model.Position

/**
 * Interface defining the core validation methods for chess rules
 */
interface ChessRules {
    /**
     * Gets all valid moves for a specific piece on the current board
     * @param piece The chess piece to get moves for
     * @param board The current board state
     * @return A set of valid positions the piece can move to
     */
    fun getValidMoves(piece: ChessPiece, board: Map<Position, ChessPiece>): Set<Position>
    
    /**
     * Checks if a move is valid according to chess rules
     * @param from The starting position
     * @param to The destination position
     * @param board The current board state
     * @return true if the move is valid, false otherwise
     */
    fun isValidMove(from: Position, to: Position, board: Map<Position, ChessPiece>): Boolean
    
    /**
     * Checks if the king of the specified color is in check
     * @param kingColor The color of the king to check
     * @param board The current board state
     * @return true if the king is in check, false otherwise
     */
    fun isInCheck(kingColor: PieceColor, board: Map<Position, ChessPiece>): Boolean
    
    /**
     * Checks if the king of the specified color is in checkmate
     * @param kingColor The color of the king to check
     * @param board The current board state
     * @return true if the king is in checkmate, false otherwise
     */
    fun isCheckmate(kingColor: PieceColor, board: Map<Position, ChessPiece>): Boolean
    
    /**
     * Checks if the game is in stalemate for the specified color
     * @param color The color to check for stalemate
     * @param board The current board state
     * @return true if the game is in stalemate, false otherwise
     */
    fun isStalemate(color: PieceColor, board: Map<Position, ChessPiece>): Boolean
    
    /**
     * Validates that a move doesn't leave the player's king in check
     * @param from The starting position
     * @param to The destination position
     * @param board The current board state
     * @return true if the move is legal (doesn't leave own king in check), false otherwise
     */
    fun validateKingSafety(from: Position, to: Position, board: Map<Position, ChessPiece>): Boolean
}