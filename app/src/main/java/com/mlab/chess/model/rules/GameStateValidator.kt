package com.mlab.chess.model.rules

import com.mlab.chess.model.GameState
import com.mlab.chess.model.GameStatus
import com.mlab.chess.model.PieceColor
import com.mlab.chess.model.Position

/**
 * Validator for game state and move legality
 * @param chessRules The chess rules implementation to use for validation
 */
class GameStateValidator(private val chessRules: ChessRules) {
    
    /**
     * Validates if it's the correct player's turn to move the piece at the given position
     * @param position The position of the piece to check
     * @param gameState The current game state
     * @return true if it's the correct player's turn, false otherwise
     */
    fun isValidTurn(position: Position, gameState: GameState): Boolean {
        val piece = gameState.getPieceAt(position) ?: return false
        return piece.color == gameState.currentPlayer
    }
    
    /**
     * Validates if a move is legal according to chess rules
     * @param from The starting position
     * @param to The destination position
     * @param gameState The current game state
     * @return true if the move is valid, false otherwise
     */
    fun isValidMove(from: Position, to: Position, gameState: GameState): Boolean {
        // Check if it's the correct player's turn
        if (!isValidTurn(from, gameState)) {
            return false
        }
        
        // Check if the move is valid according to chess rules
        return chessRules.isValidMove(from, to, gameState.board)
    }
    
    /**
     * Checks if the king of the specified color is in check
     * @param color The color of the king to check
     * @param gameState The current game state
     * @return true if the king is in check, false otherwise
     */
    fun isInCheck(color: PieceColor, gameState: GameState): Boolean {
        return chessRules.isInCheck(color, gameState.board)
    }
    
    /**
     * Checks if the king of the specified color is in checkmate
     * @param color The color of the king to check
     * @param gameState The current game state
     * @return true if the king is in checkmate, false otherwise
     */
    fun isCheckmate(color: PieceColor, gameState: GameState): Boolean {
        return chessRules.isCheckmate(color, gameState.board)
    }
    
    /**
     * Checks if the game is in stalemate for the specified color
     * @param color The color to check for stalemate
     * @param gameState The current game state
     * @return true if the game is in stalemate, false otherwise
     */
    fun isStalemate(color: PieceColor, gameState: GameState): Boolean {
        return chessRules.isStalemate(color, gameState.board)
    }
    
    /**
     * Updates the game status based on the current board state
     * @param gameState The current game state
     * @return A new GameState with updated game status
     */
    fun updateGameStatus(gameState: GameState): GameState {
        val currentPlayer = gameState.currentPlayer
        
        // Check for checkmate
        if (isCheckmate(currentPlayer, gameState)) {
            return gameState.copy(gameStatus = GameStatus.CHECKMATE)
        }
        
        // Check for stalemate
        if (isStalemate(currentPlayer, gameState)) {
            return gameState.copy(gameStatus = GameStatus.STALEMATE)
        }
        
        // Check for check
        if (isInCheck(currentPlayer, gameState)) {
            return gameState.copy(gameStatus = GameStatus.CHECK)
        }
        
        // Otherwise, game is active
        return gameState.copy(gameStatus = GameStatus.ACTIVE)
    }
    
    /**
     * Validates a move and executes it if valid
     * @param from The starting position
     * @param to The destination position
     * @param gameState The current game state
     * @return A new GameState with the move executed, or the original state if invalid
     */
    fun validateAndMove(from: Position, to: Position, gameState: GameState): GameState {
        if (!isValidMove(from, to, gameState)) {
            return gameState
        }
        
        // Execute the move, handling captures if needed
        val newGameState = if (gameState.isOccupied(to)) {
            gameState.movePieceWithCapture(from, to)
        } else {
            gameState.movePiece(from, to)
        }
        
        // Update game status
        return updateGameStatus(newGameState)
    }
}