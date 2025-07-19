package com.mlab.chess.model.rules

import com.mlab.chess.model.ChessPiece
import com.mlab.chess.model.PieceColor
import com.mlab.chess.model.Position

/**
 * Base implementation of ChessRules interface with common functionality
 */
class StandardChessRules : ChessRules {
    
    /**
     * Gets all valid moves for a specific piece on the current board
     * @param piece The chess piece to get moves for
     * @param board The current board state
     * @return A set of valid positions the piece can move to
     */
    override fun getValidMoves(piece: ChessPiece, board: Map<Position, ChessPiece>): Set<Position> {
        // Use the piece-specific rules to get valid moves
        val pieceRules = PieceRulesFactory.createRules(piece.type)
        return pieceRules.getValidMoves(piece, board)
    }
    
    /**
     * Checks if a move is valid according to chess rules
     * @param from The starting position
     * @param to The destination position
     * @param board The current board state
     * @return true if the move is valid, false otherwise
     */
    override fun isValidMove(from: Position, to: Position, board: Map<Position, ChessPiece>): Boolean {
        val piece = board[from] ?: return false
        
        // Get all valid moves for the piece
        val validMoves = getValidMoves(piece, board)
        
        // Check if the destination is in the valid moves
        return to in validMoves && validateKingSafety(from, to, board)
    }
    
    /**
     * Checks if the king of the specified color is in check
     * @param kingColor The color of the king to check
     * @param board The current board state
     * @return true if the king is in check, false otherwise
     */
    override fun isInCheck(kingColor: PieceColor, board: Map<Position, ChessPiece>): Boolean {
        val kingPosition = PositionUtils.findKingPosition(board, kingColor) ?: return false
        return PositionUtils.isPositionUnderAttack(kingPosition, board, kingColor.opposite())
    }
    
    /**
     * Checks if the king of the specified color is in checkmate
     * @param kingColor The color of the king to check
     * @param board The current board state
     * @return true if the king is in checkmate, false otherwise
     */
    override fun isCheckmate(kingColor: PieceColor, board: Map<Position, ChessPiece>): Boolean {
        // If the king is not in check, it can't be checkmate
        if (!isInCheck(kingColor, board)) {
            return false
        }
        
        // Check if any move can get the king out of check
        return !hasAnyLegalMove(kingColor, board)
    }
    
    /**
     * Checks if the game is in stalemate for the specified color
     * @param color The color to check for stalemate
     * @param board The current board state
     * @return true if the game is in stalemate, false otherwise
     */
    override fun isStalemate(color: PieceColor, board: Map<Position, ChessPiece>): Boolean {
        // If the king is in check, it's not stalemate
        if (isInCheck(color, board)) {
            return false
        }
        
        // If the player has no legal moves, it's stalemate
        return !hasAnyLegalMove(color, board)
    }
    
    /**
     * Validates that a move doesn't leave the player's king in check
     * @param from The starting position
     * @param to The destination position
     * @param board The current board state
     * @return true if the move is legal (doesn't leave own king in check), false otherwise
     */
    override fun validateKingSafety(from: Position, to: Position, board: Map<Position, ChessPiece>): Boolean {
        val piece = board[from] ?: return false
        
        // Simulate the move
        val simulatedBoard = PositionUtils.simulateMove(from, to, board)
        
        // Check if the king is in check after the move
        return !isInCheck(piece.color, simulatedBoard)
    }
    
    /**
     * Checks if the player of the specified color has any legal move
     * @param color The color to check
     * @param board The current board state
     * @return true if the player has at least one legal move, false otherwise
     */
    private fun hasAnyLegalMove(color: PieceColor, board: Map<Position, ChessPiece>): Boolean {
        // Get all pieces of the specified color
        val pieces = board.values.filter { it.color == color }
        
        // Check if any piece has a legal move
        for (piece in pieces) {
            val validMoves = getValidMoves(piece, board)
            
            // Check if any move is legal (doesn't leave king in check)
            for (move in validMoves) {
                if (validateKingSafety(piece.position, move, board)) {
                    return true
                }
            }
        }
        
        return false
    }
}