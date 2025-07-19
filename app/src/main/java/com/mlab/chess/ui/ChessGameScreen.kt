package com.mlab.chess.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mlab.chess.viewmodel.ChessGameViewModel

/**
 * Main chess game screen that integrates all components
 * @param modifier Modifier for the composable
 * @param viewModel The ChessGameViewModel to use
 */
@Composable
fun ChessGameScreen(
    modifier: Modifier = Modifier,
    viewModel: ChessGameViewModel = viewModel()
) {
    // Collect state from ViewModel
    val gameState by viewModel.gameState.collectAsState()
    val pieceAnimationState by viewModel.pieceAnimationState.collectAsState()
    val invalidMovePosition by viewModel.invalidMovePosition.collectAsState()
    val hoverPosition by viewModel.hoverPosition.collectAsState()
    val isAnimating by viewModel.isAnimating.collectAsState()
    
    // Main layout
    Column(
        modifier = modifier
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
        
        // Chess board in the center
        Box(
            modifier = Modifier
                .fillMaxWidth(),
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
                squareSize = 40.dp,
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