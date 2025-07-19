package com.mlab.chess.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mlab.chess.model.GameState
import com.mlab.chess.model.Position
import com.mlab.chess.ui.theme.ChessColor

/**
 * Composable function to display a chess board with pieces
 * @param gameState The current state of the chess game
 * @param onSquareClicked Callback for when a square is clicked, with the position as parameter
 * @param animationState The current animation state for piece movement
 * @param invalidMovePosition Position where an invalid move was attempted, for feedback
 * @param hoverPosition Position currently being hovered, for hover effects
 * @param onSquareHovered Callback for when a square is hovered, with the position as parameter
 * @param modifier Modifier for the composable
 * @param lightSquareColor The color for light squares
 * @param darkSquareColor The color for dark squares
 * @param highlightColor The color for highlighting selected pieces and valid moves
 * @param errorColor The color for invalid move feedback
 * @param showCoordinates Whether to show board coordinates (ranks and files)
 * @param onAnimationComplete Callback when animation completes
 */
@Composable
fun ChessBoard(
    gameState: GameState,
    onSquareClicked: (Position) -> Unit,
    animationState: PieceAnimationState? = null,
    invalidMovePosition: Position? = null,
    hoverPosition: Position? = null,
    onSquareHovered: ((Position?) -> Unit)? = null,
    modifier: Modifier = Modifier,
//    lightSquareColor: Color = Color(0xFF778BCE),
//    darkSquareColor: Color = Color(0xFF3252BD),
//    highlightColor: Color = Color(0x80FFFF00),
//    errorColor: Color = Color(0x80FF0000),
    lightSquareColor: Color = ChessColor.lightSquare,
    darkSquareColor: Color = ChessColor.darkSquare,
    highlightColor: Color = ChessColor.highlight,
    errorColor: Color = ChessColor.error,
    showCoordinates: Boolean = false,
    onAnimationComplete: () -> Unit = {}
) {
    val coordinateSize = if (showCoordinates) 20.dp else 0.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val squareSize = screenWidth / 9

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(BorderStroke(2.dp, MaterialTheme.colorScheme.outline), RoundedCornerShape(10.dp))
    ) {
        // Render animated piece if there is one
        if (animationState?.isAnimating == true) {
            val movingPiece = gameState.getPieceAt(animationState.sourcePosition)
            movingPiece?.let { piece ->
                AnimatedChessPiece(
                    piece = piece,
                    animationState = animationState,
                    squareSize = squareSize,
                    isSelected = false,
                    onAnimationComplete = onAnimationComplete
                )
            }
        }
        // Draw the board with coordinates
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        )
        {
            // Top coordinates (files: a-h)
            if (showCoordinates) {
                Row(
                    modifier = Modifier
                        .height(coordinateSize)
                        .fillMaxWidth()
                        .padding(start = coordinateSize)
                ) {
                    for (col in 0..7) {
                        val file = ('a' + col).toString()
                        Box(
                            modifier = Modifier
                                .size(squareSize)
                                .padding(2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = file,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }
            
            // Board rows with side coordinates
            for (row in 0..7) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left coordinates (ranks: 8-1)
                    if (showCoordinates) {
                        Box(
                            modifier = Modifier
                                .size(coordinateSize)
                                .padding(2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = (8 - row).toString(),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                    
                    // Chess squares for this row
                    for (col in 0..7) {
                        val position = Position(row, col)
                        val isLightSquare = (row + col) % 2 == 0
                        val squareColor = if (isLightSquare) lightSquareColor else darkSquareColor
                        val piece = gameState.getPieceAt(position)
                        val isSelected = gameState.selectedPiece == position
                        val isValidMove = position in gameState.validMoves
                        
                        // Build accessibility description
                        val squareDescription = buildString {
                            append(position.toChessNotation())
                            if (piece != null) {
                                val colorName = if (piece.color.name == "WHITE") "White" else "Black"
                                append(", $colorName ${piece.type.name.lowercase().capitalize()}")
                            } else {
                                append(", Empty")
                            }
                            if (isSelected) append(", Selected")
                            if (isValidMove) append(", Valid move")
                        }
                        
                        // Create interaction source to detect hover
                        val interactionSource = remember { MutableInteractionSource() }
                        val isHovered by interactionSource.collectIsHoveredAsState()
                        
                        // Notify parent about hover state changes
                        LaunchedEffect(isHovered) {
                            if (isHovered && isValidMove) {
                                onSquareHovered?.invoke(position)
                            } else if (!isHovered && hoverPosition == position) {
                                onSquareHovered?.invoke(null)
                            }
                        }
                        
                        Box(
                            modifier = Modifier
                                .size(squareSize)
                                .background(squareColor)
                                .border(
                                    width = if (isSelected) 2.dp else 0.dp,
                                    color = if (isSelected) highlightColor else Color.Transparent
                                )
                                .clickable{
                                    onSquareClicked(position) 
                                }
                                .semantics { contentDescription = squareDescription },
                            contentAlignment = Alignment.Center
                        ) {
                            // Show hover effect for valid moves
                            if (isValidMove && hoverPosition == position) {
                                ValidMoveHoverEffect(
                                    isHovered = true,
                                    size = squareSize,
                                    hoverColor = highlightColor.copy(alpha = 0.4f)
                                )
                            }
                            
                            // Show invalid move feedback
                            if (invalidMovePosition == position) {
                                InvalidMoveFeedback(
                                    showFeedback = true,
                                    size = squareSize,
                                    errorColor = errorColor
                                )
                            }
                            // Draw the piece if there is one at this position and not being animated
                            piece?.let {
                                // Check if this piece is being animated
                                val isPieceAnimating = animationState?.isAnimating == true && 
                                                      animationState.sourcePosition == position
                                
                                // Only render the piece normally if it's not being animated
                                if (!isPieceAnimating) {
                                    ChessPieceIcon(
                                        piece = it,
                                        size = squareSize * 0.8f,
                                        isSelected = isSelected
                                    )
                                }
                            }
                            
                            // Show valid move indicators with animation
                            if (isValidMove) {
                                // Use AnimatedVisibility for smooth appearance/disappearance
                                if (piece == null) {
                                    // Empty square indicator
                                    ValidMoveIndicator(
                                        type = MoveIndicatorType.EMPTY_SQUARE,
                                        size = squareSize,
                                        color = highlightColor
                                    )
                                } else {
                                    // Capture indicator
                                    ValidMoveIndicator(
                                        type = MoveIndicatorType.CAPTURE,
                                        size = squareSize,
                                        color = highlightColor
                                    )
                                }
                            }
                            
                            // Show position notation in small text at corner for debugging
                            // Uncomment if needed during development
                            /*
                            Text(
                                text = position.toChessNotation(),
                                fontSize = 8.sp,
                                color = Color.Gray,
                                modifier = Modifier.align(Alignment.TopStart)
                            )
                            */
                        }
                    }
                    
                    // Right coordinates (ranks: 8-1)
                    if (showCoordinates) {
                        Box(
                            modifier = Modifier
                                .size(coordinateSize)
                                .padding(2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = (8 - row).toString(),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }
            
            // Bottom coordinates (files: a-h)
            if (showCoordinates) {
                Row(
                    modifier = Modifier
                        .height(coordinateSize)
                        .fillMaxWidth()
                        .padding(start = coordinateSize)
                ) {
                    for (col in 0..7) {
                        val file = ('a' + col).toString()
                        Box(
                            modifier = Modifier
                                .size(squareSize)
                                .padding(2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = file,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
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