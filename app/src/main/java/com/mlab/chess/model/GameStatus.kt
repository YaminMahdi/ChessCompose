package com.mlab.chess.model

/**
 * Represents the current status of a chess game
 */
enum class GameStatus {
    /**
     * Game is in progress with no special conditions
     */
    ACTIVE,
    
    /**
     * Current player's king is in check
     */
    CHECK,
    
    /**
     * Current player's king is in checkmate (game over)
     */
    CHECKMATE,
    
    /**
     * Game is in stalemate (draw)
     */
    STALEMATE
}