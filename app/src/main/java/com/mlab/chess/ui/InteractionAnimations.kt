package com.mlab.chess.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

/**
 * Enum defining different types of interaction feedback
 */
enum class InteractionFeedbackType {
    SELECTION,
    VALID_MOVE,
    INVALID_MOVE
}

/**
 * Composable that provides visual feedback for piece selection
 * @param isSelected Whether the piece is currently selected
 * @param modifier Modifier for the composable
 * @param size The size of the highlight effect
 * @param highlightColor The color for the highlight effect
 */
@Composable
fun SelectionHighlight(
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    highlightColor: Color = Color(0x80FFFF00)
) {
    // Scale animation for selection
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.1f else 1.0f,
        animationSpec = tween(durationMillis = 200),
        label = "selection_scale"
    )
    
    // Pulsating animation for selected pieces
    val infiniteTransition = rememberInfiniteTransition(label = "selection_pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )
    
    // Only show if selected
    if (isSelected) {
        Box(
            modifier = modifier
                .size(size)
                .scale(scale)
                .alpha(pulseAlpha)
                .border(width = 2.dp, color = highlightColor)
                .testTag("selection_highlight")
        )
    }
}

/**
 * Composable that provides visual feedback for valid move hover effects
 * @param isHovered Whether the square is currently hovered
 * @param modifier Modifier for the composable
 * @param size The size of the hover effect
 * @param hoverColor The color for the hover effect
 */
@Composable
fun ValidMoveHoverEffect(
    isHovered: Boolean,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    hoverColor: Color = Color(0x40FFFF00)
) {
    // Alpha animation for hover
    val alpha by animateFloatAsState(
        targetValue = if (isHovered) 0.7f else 0.0f,
        animationSpec = tween(durationMillis = 150),
        label = "hover_alpha"
    )
    
    Box(
        modifier = modifier
            .size(size)
            .alpha(alpha)
            .background(hoverColor)
            .testTag("hover_effect")
    )
}

/**
 * Composable that provides visual feedback for invalid move attempts
 * @param showFeedback Whether to show the invalid move feedback
 * @param modifier Modifier for the composable
 * @param size The size of the feedback effect
 * @param errorColor The color for the error effect
 * @param onFeedbackComplete Callback when feedback animation completes
 */
@Composable
fun InvalidMoveFeedback(
    showFeedback: Boolean,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    errorColor: Color = Color(0x80FF0000),
    onFeedbackComplete: () -> Unit = {}
) {
    // State to control the animation
    var isAnimating by remember { mutableStateOf(false) }
    
    // Start animation when showFeedback changes to true
    LaunchedEffect(showFeedback) {
        if (showFeedback) {
            isAnimating = true
            // Auto-dismiss after animation
            delay(500)
            isAnimating = false
            onFeedbackComplete()
        }
    }
    
    // Alpha animation for the error effect
    val alpha by animateFloatAsState(
        targetValue = if (isAnimating) 1f else 0f,
        animationSpec = tween(
            durationMillis = if (isAnimating) 100 else 300,
            easing = if (isAnimating) FastOutSlowInEasing else LinearOutSlowInEasing
        ),
        label = "error_alpha"
    )
    
    // Scale animation for the error effect
    val scale by animateFloatAsState(
        targetValue = if (isAnimating) 1.2f else 1f,
        animationSpec = tween(
            durationMillis = if (isAnimating) 100 else 300,
            easing = if (isAnimating) FastOutSlowInEasing else LinearOutSlowInEasing
        ),
        label = "error_scale"
    )
    
    Box(
        modifier = modifier
            .size(size)
            .scale(scale)
            .alpha(alpha)
            .padding(3.dp)
            .background(errorColor)
            .testTag("invalid_feedback")
    )
}
