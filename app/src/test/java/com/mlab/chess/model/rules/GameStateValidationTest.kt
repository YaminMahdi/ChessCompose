package com.mlab.chess.model.rules

import com.mlab.chess.model.ChessPiece
import com.mlab.chess.model.GameState
import com.mlab.chess.model.GameStatus
import com.mlab.chess.model.PieceColor
import com.mlab.chess.model.PieceType
import com.mlab.chess.model.Position
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests for game state validation logic
 */
class GameStateValidationTest {
    
    private lateinit var chessRules: ChessRules
    
    @Before
    fun setup() {
        chessRules = StandardChessRules()
    }
    
    @Test
    fun `test turn validation - correct player`() {
        // Create a game state with white's turn
        val whiteKing = ChessPiece(PieceType.KING, PieceColor.WHITE, Position(7, 4))
        val whitePawn = ChessPiece(PieceType.PAWN, PieceColor.WHITE, Position(6, 4))
        val blackKing = ChessPiece(PieceType.KING, PieceColor.BLACK, Position(0, 4))
        
        val board = mapOf(
            whiteKing.position to whiteKing,
            whitePawn.position to whitePawn,
            blackKing.position to blackKing
        )
        
        val gameState = GameState(
            board = board,
            currentPlayer = PieceColor.WHITE
        )
        
        val gameValidator = GameStateValidator(chessRules)
        
        // White piece should be allowed to move on white's turn
        assertTrue(gameValidator.isValidTurn(whitePawn.position, gameState))
        
        // Black piece should not be allowed to move on white's turn
        assertFalse(gameValidator.isValidTurn(blackKing.position, gameState))
    }
    
    @Test
    fun `test turn validation - wrong player`() {
        // Create a game state with black's turn
        val whiteKing = ChessPiece(PieceType.KING, PieceColor.WHITE, Position(7, 4))
        val whitePawn = ChessPiece(PieceType.PAWN, PieceColor.WHITE, Position(6, 4))
        val blackKing = ChessPiece(PieceType.KING, PieceColor.BLACK, Position(0, 4))
        
        val board = mapOf(
            whiteKing.position to whiteKing,
            whitePawn.position to whitePawn,
            blackKing.position to blackKing
        )
        
        val gameState = GameState(
            board = board,
            currentPlayer = PieceColor.BLACK
        )
        
        val gameValidator = GameStateValidator(chessRules)
        
        // White piece should not be allowed to move on black's turn
        assertFalse(gameValidator.isValidTurn(whitePawn.position, gameState))
        
        // Black piece should be allowed to move on black's turn
        assertTrue(gameValidator.isValidTurn(blackKing.position, gameState))
    }
    
    @Test
    fun `test move validation - valid move`() {
        // Create a game state with a pawn that can move forward
        val whitePawn = ChessPiece(PieceType.PAWN, PieceColor.WHITE, Position(6, 4))
        val whiteKing = ChessPiece(PieceType.KING, PieceColor.WHITE, Position(7, 4))
        val blackKing = ChessPiece(PieceType.KING, PieceColor.BLACK, Position(0, 4))
        
        val board = mapOf(
            whitePawn.position to whitePawn,
            whiteKing.position to whiteKing,
            blackKing.position to blackKing
        )
        
        val gameState = GameState(
            board = board,
            currentPlayer = PieceColor.WHITE
        )
        
        val gameValidator = GameStateValidator(chessRules)
        
        // Pawn should be able to move forward one square
        assertTrue(gameValidator.isValidMove(whitePawn.position, Position(5, 4), gameState))
        
        // Pawn should be able to move forward two squares on first move
        assertTrue(gameValidator.isValidMove(whitePawn.position, Position(4, 4), gameState))
    }
    
    @Test
    fun `test move validation - invalid move`() {
        // Create a game state with a pawn that cannot move diagonally without capture
        val whitePawn = ChessPiece(PieceType.PAWN, PieceColor.WHITE, Position(6, 4))
        val whiteKing = ChessPiece(PieceType.KING, PieceColor.WHITE, Position(7, 4))
        val blackKing = ChessPiece(PieceType.KING, PieceColor.BLACK, Position(0, 4))
        
        val board = mapOf(
            whitePawn.position to whitePawn,
            whiteKing.position to whiteKing,
            blackKing.position to blackKing
        )
        
        val gameState = GameState(
            board = board,
            currentPlayer = PieceColor.WHITE
        )
        
        val gameValidator = GameStateValidator(chessRules)
        
        // Pawn should not be able to move diagonally without capture
        assertFalse(gameValidator.isValidMove(whitePawn.position, Position(5, 5), gameState))
        
        // Pawn should not be able to move horizontally
        assertFalse(gameValidator.isValidMove(whitePawn.position, Position(6, 5), gameState))
    }
    
    @Test
    fun `test move validation - blocked move`() {
        // Create a game state with a pawn that is blocked
        val whitePawn = ChessPiece(PieceType.PAWN, PieceColor.WHITE, Position(6, 4))
        val blockingPiece = ChessPiece(PieceType.PAWN, PieceColor.BLACK, Position(5, 4))
        val whiteKing = ChessPiece(PieceType.KING, PieceColor.WHITE, Position(7, 4))
        val blackKing = ChessPiece(PieceType.KING, PieceColor.BLACK, Position(0, 4))
        
        val board = mapOf(
            whitePawn.position to whitePawn,
            blockingPiece.position to blockingPiece,
            whiteKing.position to whiteKing,
            blackKing.position to blackKing
        )
        
        val gameState = GameState(
            board = board,
            currentPlayer = PieceColor.WHITE
        )
        
        val gameValidator = GameStateValidator(chessRules)
        
        // Pawn should not be able to move forward when blocked
        assertFalse(gameValidator.isValidMove(whitePawn.position, Position(5, 4), gameState))
        assertFalse(gameValidator.isValidMove(whitePawn.position, Position(4, 4), gameState))
    }
    
    @Test
    fun `test check detection`() {
        // Create a game state with white king in check
        val whiteKing = ChessPiece(PieceType.KING, PieceColor.WHITE, Position(7, 4))
        val blackRook = ChessPiece(PieceType.ROOK, PieceColor.BLACK, Position(0, 4))
        
        val board = mapOf(
            whiteKing.position to whiteKing,
            blackRook.position to blackRook
        )
        
        val gameState = GameState(
            board = board,
            currentPlayer = PieceColor.WHITE
        )
        
        val gameValidator = GameStateValidator(chessRules)
        
        // White king should be in check
        assertTrue(gameValidator.isInCheck(PieceColor.WHITE, gameState))
        
        // Black king is not in check
        assertFalse(gameValidator.isInCheck(PieceColor.BLACK, gameState))
    }
    
    @Test
    fun `test game status update`() {
        // Create a game state with white king in check
        val whiteKing = ChessPiece(PieceType.KING, PieceColor.WHITE, Position(7, 4))
        val blackRook = ChessPiece(PieceType.ROOK, PieceColor.BLACK, Position(0, 4))
        val blackKing = ChessPiece(PieceType.KING, PieceColor.BLACK, Position(0, 0))
        
        val board = mapOf(
            whiteKing.position to whiteKing,
            blackRook.position to blackRook,
            blackKing.position to blackKing
        )
        
        val gameState = GameState(
            board = board,
            currentPlayer = PieceColor.WHITE
        )
        
        val gameValidator = GameStateValidator(chessRules)
        
        // Game status should be updated to CHECK
        val updatedGameState = gameValidator.updateGameStatus(gameState)
        assertEquals(GameStatus.CHECK, updatedGameState.gameStatus)
    }
    
    @Test
    fun `test validate and move - valid move`() {
        // Create a game state with a pawn that can move forward
        val whitePawn = ChessPiece(PieceType.PAWN, PieceColor.WHITE, Position(6, 4))
        val whiteKing = ChessPiece(PieceType.KING, PieceColor.WHITE, Position(7, 4))
        val blackKing = ChessPiece(PieceType.KING, PieceColor.BLACK, Position(0, 4))
        
        val board = mapOf(
            whitePawn.position to whitePawn,
            whiteKing.position to whiteKing,
            blackKing.position to blackKing
        )
        
        val gameState = GameState(
            board = board,
            currentPlayer = PieceColor.WHITE
        )
        
        val gameValidator = GameStateValidator(chessRules)
        
        // Pawn should be able to move forward one square
        val newGameState = gameValidator.validateAndMove(whitePawn.position, Position(5, 4), gameState)
        
        // Move should be executed
        assertNotEquals(gameState, newGameState)
        assertEquals(PieceColor.BLACK, newGameState.currentPlayer) // Turn should switch
        assertNull(newGameState.board[whitePawn.position]) // Pawn should be removed from original position
        assertNotNull(newGameState.board[Position(5, 4)]) // Pawn should be at new position
    }
    
    @Test
    fun `test validate and move - invalid move`() {
        // Create a game state with a pawn that cannot move diagonally without capture
        val whitePawn = ChessPiece(PieceType.PAWN, PieceColor.WHITE, Position(6, 4))
        val whiteKing = ChessPiece(PieceType.KING, PieceColor.WHITE, Position(7, 4))
        val blackKing = ChessPiece(PieceType.KING, PieceColor.BLACK, Position(0, 4))
        
        val board = mapOf(
            whitePawn.position to whitePawn,
            whiteKing.position to whiteKing,
            blackKing.position to blackKing
        )
        
        val gameState = GameState(
            board = board,
            currentPlayer = PieceColor.WHITE
        )
        
        val gameValidator = GameStateValidator(chessRules)
        
        // Pawn should not be able to move diagonally without capture
        val newGameState = gameValidator.validateAndMove(whitePawn.position, Position(5, 5), gameState)
        
        // Move should not be executed
        assertEquals(gameState, newGameState)
    }
}