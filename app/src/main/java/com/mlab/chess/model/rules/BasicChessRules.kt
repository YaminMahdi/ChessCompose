package com.mlab.chess.model.rules

import com.mlab.chess.model.ChessPiece
import com.mlab.chess.model.PieceColor
import com.mlab.chess.model.PieceType
import com.mlab.chess.model.Position

/**
 * Basic implementation of chess rules
 */
class BasicChessRules : ChessRules {
    
    override fun getValidMoves(piece: ChessPiece, board: Map<Position, ChessPiece>): Set<Position> {
        // This will be implemented in task 3.2 for each piece type
        // For now, return an empty set as a placeholder
        return emptySet()
    }
    
    override fun isValidMove(from: Position, to: Position, board: Map<Position, ChessPiece>): Boolean {
        val piece = board[from] ?: return false
        
        // Get all valid moves for the piece
        val validMoves = getValidMoves(piece, board)
        
        // Check if the destination is in the valid moves
        return to in validMoves && validateKingSafety(from, to, board)
    }
    
    override fun isInCheck(kingColor: PieceColor, board: Map<Position, ChessPiece>): Boolean {
        // Find the king's position
        val kingPosition = PositionUtils.findKingPosition(board, kingColor) ?: return false
        
        // Check if the king's position is under attack by any opponent piece
        return PositionUtils.isPositionUnderAttack(
            kingPosition,
            board,
            kingColor.opposite()
        )
    }
    
    override fun validateKingSafety(from: Position, to: Position, board: Map<Position, ChessPiece>): Boolean {
        val piece = board[from] ?: return false
        
        // Simulate the move
        val simulatedBoard = PositionUtils.simulateMove(from, to, board)
        
        // Check if the king is in check after the move
        return !isInCheck(piece.color, simulatedBoard)
    }
    
    override fun isCheckmate(kingColor: PieceColor, board: Map<Position, ChessPiece>): Boolean {
        // Check if the king is in check
        if (!isInCheck(kingColor, board)) {
            return false // Not in check, so not checkmate
        }
        
        // Check if any piece of this color has a legal move
        return !hasAnyLegalMove(kingColor, board)
    }
    
    override fun isStalemate(color: PieceColor, board: Map<Position, ChessPiece>): Boolean {
        // Check if the king is in check
        if (isInCheck(color, board)) {
            return false // In check, so not stalemate
        }
        
        // Check if any piece of this color has a legal move
        return !hasAnyLegalMove(color, board)
    }
    
    /**
     * Checks if any piece of the specified color has a legal move
     * @param color The color to check
     * @param board The current board state
     * @return true if any piece has a legal move, false otherwise
     */
    private fun hasAnyLegalMove(color: PieceColor, board: Map<Position, ChessPiece>): Boolean {
        // Get all pieces of the specified color
        val pieces = board.values.filter { it.color == color }
        
        // Check each piece for legal moves
        for (piece in pieces) {
            // Get potential moves for this piece
            val potentialMoves = getValidMoves(piece, board)
            
            // Check if any of these moves are legal
            for (move in potentialMoves) {
                if (validateKingSafety(piece.position, move, board)) {
                    return true // Found a legal move
                }
            }
        }
        
        return false // No legal moves found
    }
}