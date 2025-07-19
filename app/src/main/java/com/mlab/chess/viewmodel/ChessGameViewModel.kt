package com.mlab.chess.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlab.chess.model.ChessPiece
import com.mlab.chess.model.GameState
import com.mlab.chess.model.Position
import com.mlab.chess.model.rules.GameStateValidator
import com.mlab.chess.model.rules.StandardChessRules
import com.mlab.chess.ui.PieceAnimationState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for managing chess game state and user interactions
 */
class ChessGameViewModel : ViewModel() {

    private val chessRules = StandardChessRules()
    private val gameStateValidator = GameStateValidator(chessRules)

    // MutableStateFlow to hold the current game state
    internal val _gameState = MutableStateFlow(GameState.initial())

    // Expose as immutable StateFlow
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    // Track captured pieces
    private val _capturedPieces = MutableStateFlow<List<ChessPiece>>(emptyList())
    val capturedPieces: StateFlow<List<ChessPiece>> = _capturedPieces.asStateFlow()

    // Animation state for piece movement
    private val _pieceAnimationState = MutableStateFlow<PieceAnimationState?>(null)
    val pieceAnimationState: StateFlow<PieceAnimationState?> = _pieceAnimationState.asStateFlow()

    // Flag to indicate if animation is in progress
    private val _isAnimating = MutableStateFlow(false)
    val isAnimating: StateFlow<Boolean> = _isAnimating.asStateFlow()

    // Track invalid move attempts for feedback
    private val _invalidMovePosition = MutableStateFlow<Position?>(null)
    val invalidMovePosition: StateFlow<Position?> = _invalidMovePosition.asStateFlow()

    // Track hover position for valid move indicators
    private val _hoverPosition = MutableStateFlow<Position?>(null)
    val hoverPosition: StateFlow<Position?> = _hoverPosition.asStateFlow()

    /**
     * Selects a chess piece at the given position
     * @param position The position to select
     */
    fun selectPiece(position: Position) {
        val currentState = _gameState.value

        // If the position is already selected, deselect it
        if (currentState.selectedPiece == position) {
            _gameState.update { it.copy(selectedPiece = null, validMoves = emptySet()) }
            return
        }

        // If the position contains a piece of the current player's color
        val piece = currentState.getPieceAt(position)
        if (piece != null && piece.color == currentState.currentPlayer) {
            // Update state with the selected piece and its valid moves
            viewModelScope.launch {
                val newState = currentState.selectPiece(position, chessRules)
                _gameState.emit(newState)
            }
        } else if (currentState.selectedPiece != null && currentState.validMoves.contains(position)) {
            // If a piece is already selected and the new position is a valid move, execute the move
            movePiece(currentState.selectedPiece, position)
        } else if (currentState.selectedPiece != null && !currentState.validMoves.contains(position)) {
            // If trying to move to an invalid position, show invalid move feedback
            showInvalidMoveFeedback(position)
            // Keep the piece selected
        } else if (piece != null) {
            // If trying to select opponent's piece, show invalid selection feedback
            showInvalidMoveFeedback(position)
        } else {
            // If clicking on an empty square with no piece selected, just deselect
            _gameState.update { it.copy(selectedPiece = null, validMoves = emptySet()) }
        }
    }

    /**
     * Moves a piece from one position to another with animation
     * @param from The starting position
     * @param to The destination position
     */
    fun movePiece(from: Position, to: Position) {
        viewModelScope.launch {
            val currentState = _gameState.value

            // Don't allow moves during animation
            if (_isAnimating.value) return@launch

            // Check if this is a capture move and store the captured piece if it exists
            val capturedPiece = currentState.getPieceAt(to)

            // Validate the move
            val newState = gameStateValidator.validateAndMove(from, to, currentState)

            // Only proceed if the move was valid (state changed)
            if (newState != currentState) {
                // Set animation state before updating game state
                _isAnimating.value = true
                _pieceAnimationState.value = PieceAnimationState(
                    sourcePosition = from,
                    targetPosition = to,
                    isAnimating = true,
                    isCapturing = capturedPiece != null
                )

                // If a piece was captured, add it to the captured pieces list
                if (capturedPiece != null) {
                    _capturedPieces.update { currentCaptured ->
                        currentCaptured + capturedPiece
                    }
                }

                // Wait for animation to complete (animation duration + small buffer)
                delay(350)

                // Update game state after animation
                _gameState.emit(newState)

                // Reset animation state
                _isAnimating.value = false
                _pieceAnimationState.value = null
            }
        }
    }

    /**
     * Attempts to move the currently selected piece to the target position
     * @param to The destination position
     */
    fun moveSelectedPiece(to: Position) {
        val currentState = _gameState.value
        val selectedPiece = currentState.selectedPiece

        if (selectedPiece != null && currentState.validMoves.contains(to)) {
            movePiece(selectedPiece, to)
        }
    }

    /**
     * Resets the game to its initial state
     */
    fun resetGame() {
        _gameState.value = GameState.initial()
        _capturedPieces.value = emptyList()
    }

    /**
     * Updates the game status (check, checkmate, etc.)
     */
    fun updateGameStatus() {
        val currentState = _gameState.value
        val updatedState = gameStateValidator.updateGameStatus(currentState)
        _gameState.value = updatedState
    }

    /**

     * Shows feedback for invalid move attempts
     * @param position The position where the invalid move was attempted
     */
    private fun showInvalidMoveFeedback(position: Position) {
        viewModelScope.launch {
            _invalidMovePosition.value = position

            // Auto-clear the feedback after a short delay
            delay(500)
            _invalidMovePosition.value = null
        }
    }

    /**
     * Updates the hover position for showing hover effects on valid move squares
     * @param position The position being hovered, or null if not hovering
     */
    fun updateHoverPosition(position: Position?) {
        _hoverPosition.value = position
    }

    /**
     * Clears the hover position
     */
    fun clearHoverPosition() {
        _hoverPosition.value = null
    }
}