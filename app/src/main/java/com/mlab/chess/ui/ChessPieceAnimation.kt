package com.mlab.chess.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import com.mlab.chess.model.ChessPiece
import com.mlab.chess.model.Position
import kotlin.math.roundToInt

/**
 * Data class to hold animation state for a chess piece
 */
data class PieceAnimationState(
    val sourcePosition: Position,
    val targetPosition: Position,
    val isAnimating: Boolean = false,
    val isCapturing: Boolean = false
)

/**
 * Composable that renders a chess piece with movement animation
 * @param piece The chess piece to render
 * @param animationState The animation state for this piece
 * @param squareSize The size of a board square
 * @param isSelected Whether the piece is selected
 * @param onAnimationComplete Callback when animation completes
 */
@Composable
fun AnimatedChessPiece(
    piece: ChessPiece,
    animationState: PieceAnimationState?,
    squareSize: Dp,
    isSelected: Boolean = false,
    onAnimationComplete: () -> Unit = {}
) {
    val density = LocalDensity.current
    
    // Convert board positions to pixel offsets
    val startX = with(density) { (animationState?.sourcePosition?.col?.times(squareSize.toPx()) ?: 0f) }
    val startY = with(density) { (animationState?.sourcePosition?.row?.times(squareSize.toPx()) ?: 0f) }
    val endX = with(density) { (animationState?.targetPosition?.col?.times(squareSize.toPx()) ?: 0f) }
    val endY = with(density) { (animationState?.targetPosition?.row?.times(squareSize.toPx()) ?: 0f) }
    
    // Animation parameters
    val animationSpec = tween<Float>(
        durationMillis = 300,
        easing = FastOutSlowInEasing
    )
    
    // Animation progress
    val animationProgress = remember { Animatable(0f) }
    
    // Scale animation for captures
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.1f else 1.0f,
        animationSpec = tween(durationMillis = 200),
        label = "piece_selection_scale"
    )
    
    // Alpha animation for captures
    val alpha by animateFloatAsState(
        targetValue = if (animationState?.isCapturing == true && animationProgress.value > 0.8f) 
            (1f - (animationProgress.value - 0.8f) * 5f).coerceIn(0f, 1f) 
        else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "piece_capture_alpha"
    )
    
    // Run the animation when animationState changes
    LaunchedEffect(animationState) {
        if (animationState?.isAnimating == true) {
            // Reset and run the animation
            animationProgress.snapTo(0f)
            animationProgress.animateTo(
                targetValue = 1f,
                animationSpec = animationSpec
            )
            onAnimationComplete()
        }
    }
    
    // Calculate current position based on animation progress
    val currentX = if (animationState?.isAnimating == true) {
        startX + (endX - startX) * animationProgress.value
    } else {
        with(density) { piece.position.col * squareSize.toPx() }
    }
    
    val currentY = if (animationState?.isAnimating == true) {
        startY + (endY - startY) * animationProgress.value
    } else {
        with(density) { piece.position.row * squareSize.toPx() }
    }
    
    // Render the piece at the calculated position
    Box(
        modifier = Modifier
            .offset { IntOffset(currentX.roundToInt(), currentY.roundToInt()) }
            .alpha(alpha)
    ) {
        ChessPieceIcon(
            piece = piece,
            size = squareSize * 0.8f,
            isSelected = isSelected,
            modifier = Modifier.scale(scale)
        )
    }
}
