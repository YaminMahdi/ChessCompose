package com.mlab.chess.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mlab.chess.model.Position
import com.mlab.chess.viewmodel.ChessGameViewModel

/**
 * A demo screen that showcases the ChessBoard composable with interactive functionality
 */
@Composable
fun ChessBoardDemo() {
    // Use the ChessGameViewModel to manage game state and animations
    val viewModel: ChessGameViewModel = viewModel()
    val gameState by viewModel.gameState.collectAsState()
    val animationState by viewModel.pieceAnimationState.collectAsState()
    val isAnimating by viewModel.isAnimating.collectAsState()
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Chess Board Demo",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Display game status bar
            GameStatusBar(
                currentPlayer = gameState.currentPlayer,
                gameStatus = gameState.gameStatus
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Handle square clicks
            val onSquareClicked = { position: Position ->
                // Don't allow interactions during animations
                if (!isAnimating) {
                    viewModel.selectPiece(position)
                }
            }
            
            // Track hover position for hover effects
            val invalidMovePosition by viewModel.invalidMovePosition.collectAsState()
            val hoverPosition by viewModel.hoverPosition.collectAsState()
            
            // Display the chess board with animations
            ChessBoard(
                gameState = gameState,
                onSquareClicked = onSquareClicked,
                animationState = animationState,
                invalidMovePosition = invalidMovePosition,
                hoverPosition = hoverPosition,
                onSquareHovered = { position -> viewModel.updateHoverPosition(position) },
                squareSize = 48.dp,
                onAnimationComplete = { /* Animation completed */ }
            )
        }
    }
}