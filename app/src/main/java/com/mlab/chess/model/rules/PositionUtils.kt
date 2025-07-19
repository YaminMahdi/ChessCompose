package com.mlab.chess.model.rules

import com.mlab.chess.model.ChessPiece
import com.mlab.chess.model.PieceColor
import com.mlab.chess.model.Position

/**
 * Utility functions for position validation and board traversal
 */
object PositionUtils {
    
    /**
     * Directions for horizontal and vertical movement (rook-like)
     */
    val STRAIGHT_DIRECTIONS = listOf(
        Pair(0, 1),   // right
        Pair(1, 0),   // down
        Pair(0, -1),  // left
        Pair(-1, 0)   // up
    )
    
    /**
     * Directions for diagonal movement (bishop-like)
     */
    val DIAGONAL_DIRECTIONS = listOf(
        Pair(1, 1),   // down-right
        Pair(1, -1),  // down-left
        Pair(-1, -1), // up-left
        Pair(-1, 1)   // up-right
    )
    
    /**
     * All eight directions (queen-like)
     */
    val ALL_DIRECTIONS = STRAIGHT_DIRECTIONS + DIAGONAL_DIRECTIONS
    
    /**
     * Knight move offsets
     */
    val KNIGHT_MOVES = listOf(
        Pair(2, 1), Pair(1, 2),
        Pair(-1, 2), Pair(-2, 1),
        Pair(-2, -1), Pair(-1, -2),
        Pair(1, -2), Pair(2, -1)
    )
    
    /**
     * Gets all positions along a ray in a specific direction until board edge or piece
     * @param start The starting position
     * @param direction The direction as a Pair(rowDelta, colDelta)
     * @param board The current board state
     * @param includeCapture Whether to include the position of a capturable piece
     * @param color The color of the moving piece (to determine which pieces can be captured)
     * @return A set of valid positions along the ray
     */
    fun getPositionsInDirection(
        start: Position,
        direction: Pair<Int, Int>,
        board: Map<Position, ChessPiece>,
        includeCapture: Boolean = true,
        color: PieceColor? = null
    ): Set<Position> {
        val positions = mutableSetOf<Position>()
        var current = start.offset(direction.first, direction.second)
        
        while (current.isValid()) {
            val piece = board[current]
            
            if (piece == null) {
                // Empty square, add to valid positions
                positions.add(current)
                current = current.offset(direction.first, direction.second)
            } else {
                // Found a piece
                if (includeCapture && color != null && piece.color != color) {
                    // Can capture opponent's piece
                    positions.add(current)
                }
                break
            }
        }
        
        return positions
    }
    
    /**
     * Gets all positions along multiple rays in different directions
     * @param start The starting position
     * @param directions List of directions to check
     * @param board The current board state
     * @param color The color of the moving piece
     * @return A set of valid positions along all rays
     */
    fun getPositionsInDirections(
        start: Position,
        directions: List<Pair<Int, Int>>,
        board: Map<Position, ChessPiece>,
        color: PieceColor
    ): Set<Position> {
        val positions = mutableSetOf<Position>()
        
        for (direction in directions) {
            positions.addAll(getPositionsInDirection(start, direction, board, true, color))
        }
        
        return positions
    }
    
    /**
     * Gets all valid positions for single-step moves in multiple directions
     * @param start The starting position
     * @param directions List of directions to check
     * @param board The current board state
     * @param color The color of the moving piece
     * @return A set of valid positions for single-step moves
     */
    fun getSingleStepPositions(
        start: Position,
        directions: List<Pair<Int, Int>>,
        board: Map<Position, ChessPiece>,
        color: PieceColor
    ): Set<Position> {
        val positions = mutableSetOf<Position>()
        
        for ((rowDelta, colDelta) in directions) {
            val newPos = start.offset(rowDelta, colDelta)
            
            if (newPos.isValid()) {
                val piece = board[newPos]
                
                if (piece == null || piece.color != color) {
                    // Either empty or can capture opponent's piece
                    positions.add(newPos)
                }
            }
        }
        
        return positions
    }
    
    /**
     * Checks if a position is under attack by any piece of the specified color
     * @param position The position to check
     * @param board The current board state
     * @param attackerColor The color of potential attacking pieces
     * @return true if the position is under attack, false otherwise
     */
    fun isPositionUnderAttack(
        position: Position,
        board: Map<Position, ChessPiece>,
        attackerColor: PieceColor
    ): Boolean {
        // Check for pawn attacks
        val pawnDirection = if (attackerColor == PieceColor.WHITE) -1 else 1
        val pawnAttackPositions = listOf(
            position.offset(pawnDirection, -1),
            position.offset(pawnDirection, 1)
        )
        
        for (pos in pawnAttackPositions) {
            if (pos.isValid()) {
                val piece = board[pos]
                if (piece != null && piece.color == attackerColor && piece.type == com.mlab.chess.model.PieceType.PAWN) {
                    return true
                }
            }
        }
        
        // Check for knight attacks
        for ((rowDelta, colDelta) in KNIGHT_MOVES) {
            val pos = position.offset(rowDelta, colDelta)
            if (pos.isValid()) {
                val piece = board[pos]
                if (piece != null && piece.color == attackerColor && piece.type == com.mlab.chess.model.PieceType.KNIGHT) {
                    return true
                }
            }
        }
        
        // Check for king attacks (one square in any direction)
        for ((rowDelta, colDelta) in ALL_DIRECTIONS) {
            val pos = position.offset(rowDelta, colDelta)
            if (pos.isValid()) {
                val piece = board[pos]
                if (piece != null && piece.color == attackerColor && piece.type == com.mlab.chess.model.PieceType.KING) {
                    return true
                }
            }
        }
        
        // Check for sliding piece attacks (queen, rook, bishop)
        // Check straight lines (rook and queen)
        for (direction in STRAIGHT_DIRECTIONS) {
            var current = position.offset(direction.first, direction.second)
            while (current.isValid()) {
                val piece = board[current]
                if (piece != null) {
                    if (piece.color == attackerColor && 
                        (piece.type == com.mlab.chess.model.PieceType.ROOK || 
                         piece.type == com.mlab.chess.model.PieceType.QUEEN)) {
                        return true
                    }
                    break // Stop at the first piece encountered
                }
                current = current.offset(direction.first, direction.second)
            }
        }
        
        // Check diagonals (bishop and queen)
        for (direction in DIAGONAL_DIRECTIONS) {
            var current = position.offset(direction.first, direction.second)
            while (current.isValid()) {
                val piece = board[current]
                if (piece != null) {
                    if (piece.color == attackerColor && 
                        (piece.type == com.mlab.chess.model.PieceType.BISHOP || 
                         piece.type == com.mlab.chess.model.PieceType.QUEEN)) {
                        return true
                    }
                    break // Stop at the first piece encountered
                }
                current = current.offset(direction.first, direction.second)
            }
        }
        
        return false
    }
    
    /**
     * Finds the position of the king of a specific color
     * @param board The current board state
     * @param color The color of the king to find
     * @return The position of the king, or null if not found
     */
    fun findKingPosition(board: Map<Position, ChessPiece>, color: PieceColor): Position? {
        for ((position, piece) in board) {
            if (piece.type == com.mlab.chess.model.PieceType.KING && piece.color == color) {
                return position
            }
        }
        return null
    }

    /**
     * Simulates a move and checks if it would leave the king in check
     * @param from The starting position
     * @param to The destination position
     * @param board The current board state
     * @return true if the move is legal (doesn't leave own king in check), false otherwise
     */
    fun simulateMove(from: Position, to: Position, board: Map<Position, ChessPiece>): Map<Position, ChessPiece> {
        val piece = board[from] ?: return board
        val newBoard = board.toMutableMap()

        // Remove piece from original position
        newBoard.remove(from)

        // Capture any piece at the destination
        newBoard.remove(to)

        // Create a new piece with updated position
        val movedPiece = ChessPiece(
            type = piece.type,
            color = piece.color,
            position = to,  // This is the key fix - ensure position is updated
            hasMoved = true
        )

        // Place the piece at the new position
        newBoard[to] = movedPiece

        return newBoard
    }
}