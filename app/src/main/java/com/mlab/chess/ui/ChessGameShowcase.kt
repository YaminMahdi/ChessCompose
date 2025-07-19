package com.mlab.chess.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mlab.chess.model.*
import com.mlab.chess.viewmodel.ChessGameViewModel

/**
 * Showcase for the complete chess game with all components integrated
 */
@Preview(showBackground = true)
@Composable
fun ChessGameShowcase() {
    // Create a ViewModel for the showcase
    val viewModel = remember { ChessGameViewModel() }
    
    // Collect state from ViewModel
    val gameState by viewModel.gameState.collectAsState()
    val pieceAnimationState by viewModel.pieceAnimationState.collectAsState()
    val invalidMovePosition by viewModel.invalidMovePosition.collectAsState()
    val hoverPosition by viewModel.hoverPosition.collectAsState()
    val isAnimating by viewModel.isAnimating.collectAsState()
    val capturedPieces by viewModel.capturedPieces.collectAsState()
    
    // Main layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Game status bar at the top
        GameStatusBar(
            currentPlayer = gameState.currentPlayer,
            gameStatus = gameState.gameStatus,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Captured pieces display
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // White's captured pieces
            CapturedPiecesRow(
                pieces = capturedPieces.filter { it.color == PieceColor.BLACK },
                modifier = Modifier.weight(1f)
            )
            
            // Black's captured pieces
            CapturedPiecesRow(
                pieces = capturedPieces.filter { it.color == PieceColor.WHITE },
                modifier = Modifier.weight(1f)
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Chess board in the center
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            ChessBoard(
                gameState = gameState,
                onSquareClicked = { position ->
                    if (!isAnimating) {
                        viewModel.selectPiece(position)
                    }
                },
                animationState = pieceAnimationState,
                invalidMovePosition = invalidMovePosition,
                hoverPosition = hoverPosition,
                onSquareHovered = { position ->
                    viewModel.updateHoverPosition(position)
                },
                squareSize = 48.dp,
                onAnimationComplete = {
                    // Update game status after animation completes
                    viewModel.updateGameStatus()
                }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Game controls at the bottom
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { viewModel.resetGame() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Reset Game")
            }
        }
    }
}

/**
 * Displays a row of captured pieces
 */
@Composable
fun CapturedPiecesRow(
    pieces: List<ChessPiece>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        pieces.forEach { piece ->
            ChessPieceIcon(
                piece = piece,
                size = 24.dp,
                modifier = Modifier.padding(2.dp)
            )
        }
    }
}