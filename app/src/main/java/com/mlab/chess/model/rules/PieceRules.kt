package com.mlab.chess.model.rules

import com.mlab.chess.model.ChessPiece
import com.mlab.chess.model.PieceColor
import com.mlab.chess.model.PieceType
import com.mlab.chess.model.Position

/**
 * Interface for piece-specific movement rules
 */
interface PieceRules {
    /**
     * Gets all valid moves for a specific piece on the current board
     * @param piece The chess piece to get moves for
     * @param board The current board state
     * @return A set of valid positions the piece can move to
     */
    fun getValidMoves(piece: ChessPiece, board: Map<Position, ChessPiece>): Set<Position>
}

/**
 * Factory for creating piece-specific rule implementations
 */
object PieceRulesFactory {
    /**
     * Creates a PieceRules implementation for the specified piece type
     * @param pieceType The type of piece to create rules for
     * @return A PieceRules implementation for the specified piece type
     */
    fun createRules(pieceType: PieceType): PieceRules {
        return when (pieceType) {
            PieceType.PAWN -> PawnRules()
            PieceType.ROOK -> RookRules()
            PieceType.KNIGHT -> KnightRules()
            PieceType.BISHOP -> BishopRules()
            PieceType.QUEEN -> QueenRules()
            PieceType.KING -> KingRules()
        }
    }
}

/**
 * Implementation of movement rules for pawns
 */
class PawnRules : PieceRules {
    override fun getValidMoves(piece: ChessPiece, board: Map<Position, ChessPiece>): Set<Position> {
        if (piece.type != PieceType.PAWN) return emptySet()
        
        val validMoves = mutableSetOf<Position>()
        val direction = if (piece.color == PieceColor.WHITE) -1 else 1
        val startingRow = if (piece.color == PieceColor.WHITE) 6 else 1
        val enPassantRow = if (piece.color == PieceColor.WHITE) 3 else 4
        
        // Forward move (one square)
        val forwardPos = piece.position.offset(direction, 0)
        if (forwardPos.isValid() && !board.containsKey(forwardPos)) {
            validMoves.add(forwardPos)
            
            // First move can be two squares
            if (!piece.hasMoved && piece.position.row == startingRow) {
                val doubleForwardPos = piece.position.offset(direction * 2, 0)
                if (doubleForwardPos.isValid() && !board.containsKey(doubleForwardPos)) {
                    validMoves.add(doubleForwardPos)
                }
            }
        }
        
        // Diagonal captures
        val capturePositions = listOf(
            piece.position.offset(direction, -1),
            piece.position.offset(direction, 1)
        )
        
        for (pos in capturePositions) {
            if (pos.isValid()) {
                val targetPiece = board[pos]
                if (targetPiece != null && targetPiece.color != piece.color) {
                    validMoves.add(pos)
                }
                
                // En passant capture logic
                if (targetPiece == null && piece.position.row == enPassantRow) {
                    // Check for adjacent enemy pawn that just moved two squares
                    val adjacentPos = Position(piece.position.row, pos.col)
                    val adjacentPiece = board[adjacentPos]
                    
                    if (adjacentPiece != null && 
                        adjacentPiece.type == PieceType.PAWN && 
                        adjacentPiece.color != piece.color && 
                        adjacentPiece.justMovedTwoSquares) {
                        
                        validMoves.add(pos)
                    }
                }
            }
        }
        
        return validMoves
    }
}

/**
 * Implementation of movement rules for rooks
 */
class RookRules : PieceRules {
    override fun getValidMoves(piece: ChessPiece, board: Map<Position, ChessPiece>): Set<Position> {
        if (piece.type != PieceType.ROOK) return emptySet()
        
        return PositionUtils.getPositionsInDirections(
            piece.position,
            PositionUtils.STRAIGHT_DIRECTIONS,
            board,
            piece.color
        )
    }
}

/**
 * Implementation of movement rules for bishops
 */
class BishopRules : PieceRules {
    override fun getValidMoves(piece: ChessPiece, board: Map<Position, ChessPiece>): Set<Position> {
        if (piece.type != PieceType.BISHOP) return emptySet()
        
        return PositionUtils.getPositionsInDirections(
            piece.position,
            PositionUtils.DIAGONAL_DIRECTIONS,
            board,
            piece.color
        )
    }
}

/**
 * Implementation of movement rules for queens
 */
class QueenRules : PieceRules {
    override fun getValidMoves(piece: ChessPiece, board: Map<Position, ChessPiece>): Set<Position> {
        if (piece.type != PieceType.QUEEN) return emptySet()
        
        return PositionUtils.getPositionsInDirections(
            piece.position,
            PositionUtils.ALL_DIRECTIONS,
            board,
            piece.color
        )
    }
}

/**
 * Implementation of movement rules for knights
 */
class KnightRules : PieceRules {
    override fun getValidMoves(piece: ChessPiece, board: Map<Position, ChessPiece>): Set<Position> {
        if (piece.type != PieceType.KNIGHT) return emptySet()
        
        return PositionUtils.getSingleStepPositions(
            piece.position,
            PositionUtils.KNIGHT_MOVES,
            board,
            piece.color
        )
    }
}

/**
 * Implementation of movement rules for kings
 */
class KingRules : PieceRules {
    override fun getValidMoves(piece: ChessPiece, board: Map<Position, ChessPiece>): Set<Position> {
        if (piece.type != PieceType.KING) return emptySet()
        
        val validMoves = PositionUtils.getSingleStepPositions(
            piece.position,
            PositionUtils.ALL_DIRECTIONS,
            board,
            piece.color
        ).toMutableSet()
        
        // Add castling logic
        if (!piece.hasMoved) {
            // Check kingside castling
            val kingsideCastlingMove = getCastlingMove(piece, board, true)
            if (kingsideCastlingMove != null) {
                validMoves.add(kingsideCastlingMove)
            }
            
            // Check queenside castling
            val queensideCastlingMove = getCastlingMove(piece, board, false)
            if (queensideCastlingMove != null) {
                validMoves.add(queensideCastlingMove)
            }
        }
        
        return validMoves
    }
    
    /**
     * Gets castling move if castling is possible
     * @param king The king piece
     * @param board The current board state
     * @param kingSide Whether to check kingside (true) or queenside (false) castling
     * @return The castling move position if castling is possible, null otherwise
     */
    private fun getCastlingMove(
        king: ChessPiece,
        board: Map<Position, ChessPiece>,
        kingSide: Boolean
    ): Position? {
        val row = king.position.row
        val rookCol = if (kingSide) 7 else 0
        val rookPosition = Position(row, rookCol)
        
        // Check if rook exists and hasn't moved
        val rook = board[rookPosition]
        if (rook?.type != PieceType.ROOK || rook.color != king.color || rook.hasMoved) {
            return null
        }
        
        // Check if squares between king and rook are empty
        val direction = if (kingSide) 1 else -1
        val steps = if (kingSide) 2 else 3
        
        for (i in 1..steps) {
            val checkPos = Position(row, king.position.col + direction * i)
            if (board.containsKey(checkPos)) {
                return null
            }
        }
        
        // Check if king is in check or if squares king moves through are under attack
        val attackerColor = king.color.opposite()
        if (PositionUtils.isPositionUnderAttack(king.position, board, attackerColor)) {
            return null
        }
        
        // Check if squares king moves through are under attack
        for (i in 1..2) {
            val checkPos = Position(row, king.position.col + direction * i)
            if (PositionUtils.isPositionUnderAttack(checkPos, board, attackerColor)) {
                return null
            }
        }
        
        // Return castling move position
        val castlingCol = king.position.col + direction * 2
        return Position(row, castlingCol)
    }
}