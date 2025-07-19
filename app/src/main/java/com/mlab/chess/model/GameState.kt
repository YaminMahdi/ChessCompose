package com.mlab.chess.model

import com.mlab.chess.model.rules.ChessRules
import com.mlab.chess.model.rules.GameStateValidator
import com.mlab.chess.model.rules.StandardChessRules

/**
 * Represents the complete state of a chess game
 * @param board Map of positions to chess pieces on the board
 * @param currentPlayer The color of the player whose turn it is
 * @param selectedPiece Position of the currently selected piece, or null if none selected
 * @param validMoves Set of valid positions the selected piece can move to
 * @param gameStatus Current status of the game (ACTIVE, CHECK, CHECKMATE, STALEMATE)
 */
data class GameState(
    val board: Map<Position, ChessPiece>,
    val currentPlayer: PieceColor = PieceColor.WHITE,
    val selectedPiece: Position? = null,
    val validMoves: Set<Position> = emptySet(),
    val gameStatus: GameStatus = GameStatus.ACTIVE
) {
    /**
     * Checks if a position on the board is occupied by a piece
     * @param position The position to check
     * @return true if a piece exists at the position, false otherwise
     */
    fun isOccupied(position: Position): Boolean = board.containsKey(position)
    
    /**
     * Gets the piece at the specified position, if any
     * @param position The position to check
     * @return The ChessPiece at the position, or null if empty
     */
    fun getPieceAt(position: Position): ChessPiece? = board[position]
    
    /**
     * Checks if a position contains a piece of the specified color
     * @param position The position to check
     * @param color The color to check for
     * @return true if a piece of the specified color exists at the position
     */
    fun isOccupiedByColor(position: Position, color: PieceColor): Boolean {
        val piece = board[position]
        return piece != null && piece.color == color
    }
    
    /**
     * Creates a new GameState with a piece moved from one position to another
     * @param from The starting position
     * @param to The destination position
     * @return A new GameState with the piece moved and turn switched
     */
    fun movePiece(from: Position, to: Position): GameState {
        if (!from.isValid() || !to.isValid()) return this
        
        val piece = board[from] ?: return this
        
        // Create new board with the piece moved
        val newBoard = board.toMutableMap()
        newBoard.remove(from)
        newBoard[to] = piece.moveTo(to)
        
        return copy(
            board = newBoard,
            currentPlayer = currentPlayer.opposite(),
            selectedPiece = null,
            validMoves = emptySet()
        )
    }
    
    /**
     * Creates a new GameState with a piece moved from one position to another,
     * capturing any piece at the destination
     * @param from The starting position
     * @param to The destination position
     * @return A new GameState with the piece moved, any captured piece removed, and turn switched
     */
    fun movePieceWithCapture(from: Position, to: Position): GameState {
        if (!from.isValid() || !to.isValid()) return this
        
        val piece = board[from] ?: return this
        
        // Create new board with the piece moved
        val newBoard = board.toMutableMap()
        
        // Remove the piece from its original position
        newBoard.remove(from)
        
        // If there's a piece at the destination, it's captured (removed)
        val capturedPiece = board[to]
        
        // Place the moved piece at the destination
        newBoard[to] = piece.moveTo(to)
        
        return copy(
            board = newBoard,
            currentPlayer = currentPlayer.opposite(),
            selectedPiece = null,
            validMoves = emptySet()
        )
    }
    
    /**
     * Selects a piece and calculates its valid moves
     * @param position The position of the piece to select
     * @param chessRules The chess rules to use for move validation
     * @return A new GameState with the piece selected and valid moves calculated
     */
    fun selectPiece(position: Position, chessRules: ChessRules = StandardChessRules()): GameState {
        val piece = getPieceAt(position) ?: return this
        
        // Can only select pieces of the current player's color
        if (piece.color != currentPlayer) {
            return this
        }
        
        // Calculate valid moves for the selected piece
        val validMoves = chessRules.getValidMoves(piece, board).filter { 
            chessRules.validateKingSafety(position, it, board) 
        }.toSet()
        
        return copy(
            selectedPiece = position,
            validMoves = validMoves
        )
    }
    
    /**
     * Attempts to move a piece with validation
     * @param from The starting position
     * @param to The destination position
     * @param validator The game state validator to use
     * @return A new GameState with the move executed if valid, or the original state if invalid
     */
    fun tryMove(
        from: Position, 
        to: Position, 
        validator: GameStateValidator = GameStateValidator(StandardChessRules())
    ): GameState {
        return validator.validateAndMove(from, to, this)
    }
    
    /**
     * Updates the game status based on the current board state
     * @param validator The game state validator to use
     * @return A new GameState with updated game status
     */
    fun updateGameStatus(
        validator: GameStateValidator = GameStateValidator(StandardChessRules())
    ): GameState {
        return validator.updateGameStatus(this)
    }
    
    companion object {
        /**
         * Creates a new game state with pieces in their starting positions
         * @return A new GameState with the initial chess setup
         */
        fun initial(): GameState {
            val board = mutableMapOf<Position, ChessPiece>()
            
            // Set up pawns
            for (col in 0..7) {
                board[Position(1, col)] = ChessPiece(PieceType.PAWN, PieceColor.BLACK, Position(1, col))
                board[Position(6, col)] = ChessPiece(PieceType.PAWN, PieceColor.WHITE, Position(6, col))
            }
            
            // Set up back rows
            val backRowPieces = listOf(
                PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, PieceType.QUEEN,
                PieceType.KING, PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK
            )
            
            for (col in 0..7) {
                board[Position(0, col)] = ChessPiece(backRowPieces[col], PieceColor.BLACK, Position(0, col))
                board[Position(7, col)] = ChessPiece(backRowPieces[col], PieceColor.WHITE, Position(7, col))
            }
            
            return GameState(board)
        }
    }
}