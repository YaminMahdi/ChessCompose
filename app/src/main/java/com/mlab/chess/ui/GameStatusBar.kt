package com.mlab.chess.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mlab.chess.model.GameStatus
import com.mlab.chess.model.PieceColor

/**
 * A composable that displays the current game status including player turn and game state
 * 
 * @param currentPlayer The color of the player whose turn it is
 * @param gameStatus The current status of the game (ACTIVE, CHECK, CHECKMATE, STALEMATE)
 * @param modifier Modifier for the composable
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GameStatusBar(
    currentPlayer: PieceColor,
    gameStatus: GameStatus,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(.2f)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Player turn indicator with animation
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Color indicator
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(if (currentPlayer == PieceColor.WHITE) Color.White else Color.Black)
                        .border(1.dp, Color.Gray, CircleShape)
                ) 
                
                Spacer(modifier = Modifier.width(8.dp))
                
                // Animated text for current player
                AnimatedContent(
                    targetState = currentPlayer,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(300)).togetherWith(fadeOut(animationSpec = tween(300)))
                    },
                    label = "Player Turn Animation"
                ) { player ->
                    Text(
                        text = "${player.name.lowercase().capitalize()}'s turn",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Spacer(modifier = Modifier.weight(1f))
                
                // Game status indicator
                AnimatedContent(
                    targetState = gameStatus,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(300)).togetherWith(fadeOut(animationSpec = tween(300)))
                    },
                    label = "Game Status Animation"
                ) { status ->
                    val statusColor = when (status) {
                        GameStatus.ACTIVE -> MaterialTheme.colorScheme.primary
                        GameStatus.CHECK -> MaterialTheme.colorScheme.error
                        GameStatus.CHECKMATE -> MaterialTheme.colorScheme.error
                        GameStatus.STALEMATE -> MaterialTheme.colorScheme.tertiary
                    }
                    
                    if (status != GameStatus.ACTIVE) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(statusColor.copy(alpha = 0.1f))
                                .border(1.dp, statusColor, RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = status.name,
                                style = MaterialTheme.typography.bodyMedium,
                                color = statusColor,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
            
            // Additional status message for check/checkmate/stalemate
            if (gameStatus != GameStatus.ACTIVE) {
                Spacer(modifier = Modifier.height(8.dp))
                
                val statusMessage = when (gameStatus) {
                    GameStatus.CHECK -> "${currentPlayer.name.lowercase().capitalize()}'s king is in check!"
                    GameStatus.CHECKMATE -> "${currentPlayer.name.lowercase().capitalize()} is in checkmate! Game over."
                    GameStatus.STALEMATE -> "Game ended in stalemate (draw)."
                    else -> ""
                }
                
                Text(
                    text = statusMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = when (gameStatus) {
                        GameStatus.CHECK, GameStatus.CHECKMATE -> MaterialTheme.colorScheme.error
                        GameStatus.STALEMATE -> MaterialTheme.colorScheme.tertiary
                        else -> MaterialTheme.colorScheme.onSurface
                    }
                )
            }
        }
    }
}

/**
 * Extension function to capitalize the first letter of a string
 */
private fun String.capitalize(): String {
    return this.replaceFirstChar { it.uppercase() }
}

@Preview(showBackground = true)
@Composable
fun GameStatusBarPreview() {
    MaterialTheme {
        Column {
            GameStatusBar(
                currentPlayer = PieceColor.WHITE,
                gameStatus = GameStatus.ACTIVE
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            GameStatusBar(
                currentPlayer = PieceColor.BLACK,
                gameStatus = GameStatus.CHECK
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            GameStatusBar(
                currentPlayer = PieceColor.WHITE,
                gameStatus = GameStatus.CHECKMATE
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            GameStatusBar(
                currentPlayer = PieceColor.BLACK,
                gameStatus = GameStatus.STALEMATE
            )
        }
    }
}