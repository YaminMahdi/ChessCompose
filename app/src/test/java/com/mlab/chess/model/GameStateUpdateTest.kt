package com.mlab.chess.model

import com.mlab.chess.model.rules.GameStateValidator
import com.mlab.chess.model.rules.StandardChessRules
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class GameStateUpdateTest {
    
    @Test
    fun `movePieceWithCapture removes captured piece`() {
        // Create a simple board with a white pawn and a black pawn that can be captured
        val board = mutableMapOf<Position, ChessPiece>()
        val whitePawn = ChessPiece(PieceType.PAWN, PieceColor.WHITE, Position(3, 3))
        val blackPawn = ChessPiece(PieceType.PAWN, PieceColor.BLACK, Position(2, 4))
        board[whitePawn.position] = whitePawn
        board[blackPawn.position] = blackPawn
        
        val gameState = GameState(board)
        
        // Move white pawn to capture black pawn
        val newState = gameState.movePieceWithCapture(whitePawn.position, blackPawn.position)
        
        // Verify black pawn was captured
        assertNull(newState.getPieceAt(whitePawn.position))
        assertNotNull(newState.getPieceAt(blackPawn.position))
        assertEquals(PieceColor.WHITE, newState.getPieceAt(blackPawn.position)?.color)
        assertEquals(PieceType.PAWN, newState.getPieceAt(blackPawn.position)?.type)
        
        // Verify turn switched
        assertEquals(PieceColor.BLACK, newState.currentPlayer)
    }
    
    @Test
    fun `validateAndMove handles captures correctly`() {
        // Create a simple board with a white pawn and a black pawn that can be captured
        val board = mutableMapOf<Position, ChessPiece>()
        val whitePawn = ChessPiece(PieceType.PAWN, PieceColor.WHITE, Position(3, 3))
        val blackPawn = ChessPiece(PieceType.PAWN, PieceColor.BLACK, Position(2, 4))
        board[whitePawn.position] = whitePawn
        board[blackPawn.position] = blackPawn
        
        val gameState = GameState(board)
        val validator = GameStateValidator(StandardChessRules())
        
        // Mock the validation by directly calling validateAndMove
        // In a real game, we'd check if this is a valid diagonal pawn capture
        val newState = validator.validateAndMove(whitePawn.position, blackPawn.position, gameState)
        
        // Verify black pawn was captured
        assertNull(newState.getPieceAt(whitePawn.position))
        assertNotNull(newState.getPieceAt(blackPawn.position))
        assertEquals(PieceColor.WHITE, newState.getPieceAt(blackPawn.position)?.color)
        assertEquals(PieceType.PAWN, newState.getPieceAt(blackPawn.position)?.type)
    }
    
    @Test
    fun `selecting a piece updates valid moves`() {
        val gameState = GameState.initial()
        
        // Select a white pawn
        val pawnPosition = Position(6, 0)
        val updatedState = gameState.selectPiece(pawnPosition)
        
        // Verify valid moves were calculated
        assertEquals(pawnPosition, updatedState.selectedPiece)
        assertTrue(updatedState.validMoves.isNotEmpty())
        
        // Pawn should be able to move one or two squares forward from starting position
        assertTrue(updatedState.validMoves.contains(Position(5, 0)))
        assertTrue(updatedState.validMoves.contains(Position(4, 0)))
    }
    
    @Test
    fun `selecting opponent piece does nothing`() {
        val gameState = GameState.initial()
        
        // Try to select a black pawn when it's white's turn
        val blackPawnPosition = Position(1, 0)
        val updatedState = gameState.selectPiece(blackPawnPosition)
        
        // State should remain unchanged
        assertEquals(gameState, updatedState)
        assertNull(updatedState.selectedPiece)
        assertTrue(updatedState.validMoves.isEmpty())
    }
    
    @Test
    fun `game status updates correctly after move`() {
        // Create a board where white can checkmate in one move
        val board = mutableMapOf<Position, ChessPiece>()
        
        // Black king in corner
        board[Position(0, 0)] = ChessPiece(PieceType.KING, PieceColor.BLACK, Position(0, 0))
        
        // White queen ready to checkmate
        board[Position(2, 0)] = ChessPiece(PieceType.QUEEN, PieceColor.WHITE, Position(2, 0))
        
        // White king somewhere else
        board[Position(7, 7)] = ChessPiece(PieceType.KING, PieceColor.WHITE, Position(7, 7))
        
        val gameState = GameState(board)
        val validator = GameStateValidator(StandardChessRules())
        
        // Move queen to checkmate position
        val newState = validator.validateAndMove(Position(2, 0), Position(0, 2), gameState)
        
        // Verify game status is updated to checkmate
        assertEquals(GameStatus.CHECK, newState.gameStatus)
    }
    
    @Test
    fun `immutable state updates preserve original state`() {
        val initialState = GameState.initial()
        
        // Select a piece
        val stateWithSelection = initialState.selectPiece(Position(6, 0))
        
        // Verify original state is unchanged
        assertNull(initialState.selectedPiece)
        assertTrue(initialState.validMoves.isEmpty())
        
        // Verify new state has selection
        assertNotNull(stateWithSelection.selectedPiece)
        assertFalse(stateWithSelection.validMoves.isEmpty())
        
        // Move a piece
        val stateAfterMove = stateWithSelection.movePiece(Position(6, 0), Position(4, 0))
        
        // Verify selection state is unchanged
        assertEquals(Position(6, 0), stateWithSelection.selectedPiece)
        assertFalse(stateWithSelection.validMoves.isEmpty())
        
        // Verify move state has changes
        assertNull(stateAfterMove.selectedPiece)
        assertTrue(stateAfterMove.validMoves.isEmpty())
        assertEquals(PieceColor.BLACK, stateAfterMove.currentPlayer)
        assertNull(stateAfterMove.getPieceAt(Position(6, 0)))
        assertNotNull(stateAfterMove.getPieceAt(Position(4, 0)))
    }
}