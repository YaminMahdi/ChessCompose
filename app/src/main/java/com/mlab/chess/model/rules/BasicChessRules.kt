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
    
    override fun isInCheck(kingPosition: Position, board: Map<Position, ChessPiece>, kingColor: PieceColor): Boolean {
        // Check if the king's position is under attack by any opponent piece
        return PositionUtils.isPositionUnderAttack(
            kingPosition,
            board,
            kingColor.opposite()
        )
    }
    
    override fun isMoveLegal(from: Position, to: Position, board: Map<Position, ChessPiece>): Boolean {
        val piece = board[from] ?: return false
        
        // Simulate the move
        val simulatedBoard = PositionUtils.simulateMove(from, to, board)
        
        // Find the king's position after the move
        val kingPosition = if (piece.type == PieceType.KING) {
            // If we're moving the king, use the destination position
            to
        } else {
            // Otherwise, find the king of the same color
            PositionUtils.findKingPosition(simulatedBoard, piece.color) ?: return false
        }
        
        // Check if the king would be in check after the move
        return !isInCheck(kingPosition, simulatedBoard, piece.color)
    }
    
    override fun isCheckmate(color: PieceColor, board: Map<Position, ChessPiece>): Boolean {
        // Find the king
        val kingPosition = PositionUtils.findKingPosition(board, color) ?: return false
        
        // Check if the king is in check
        if (!isInCheck(kingPosition, board, color)) {
            return false // Not in check, so not checkmate
        }
        
        // Check if any piece of this color has a legal move
        return !hasAnyLegalMove(color, board)
    }
    
    override fun isStalemate(color: PieceColor, board: Map<Position, ChessPiece>): Boolean {
        // Find the king
        val kingPosition = PositionUtils.findKingPosition(board, color) ?: return false
        
        // Check if the king is in check
        if (isInCheck(kingPosition, board, color)) {
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
                if (isMoveLegal(piece.position, move, board)) {
                    return true // Found a legal move
                }
            }
        }
        
        return false // No legal moves found
    }
}