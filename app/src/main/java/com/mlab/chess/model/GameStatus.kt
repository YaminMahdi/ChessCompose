package com.mlab.chess.model

/**
 * Enum representing the current status of the chess game
 */
enum class GameStatus {
    ACTIVE,
    CHECK,
    CHECKMATE,
    STALEMATE
}