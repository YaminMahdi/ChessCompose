package com.mlab.chess.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

/**
 * Types of valid move indicators
 */
enum class MoveIndicatorType {
    EMPTY_SQUARE,  // For moves to empty squares
    CAPTURE,       // For moves that capture an opponent's piece
}

/**
 * Composable that displays an animated indicator for valid chess moves
 * 
 * @param type The type of indicator to display (empty square or capture)
 * @param size The size of the indicator
 * @param color The color of the indicator
 * @param modifier Modifier for the composable
 */
@Composable
fun ValidMoveIndicator(
    type: MoveIndicatorType,
    size: Dp = 40.dp,
    color: Color = Color(0x80FFFF00),
    modifier: Modifier = Modifier
) {
    // Create pulsating animation
    val infiniteTransition = rememberInfiniteTransition(label = "move_indicator_animation")
    
    val pulseAnimation by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_animation"
    )
    
    val rotationAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation_animation"
    )
    
    // Calculate sizes based on the indicator type
    val circleRadius = with(LocalDensity.current) {
        when (type) {
            MoveIndicatorType.EMPTY_SQUARE -> (size.toPx() / 6)
            MoveIndicatorType.CAPTURE -> (size.toPx() / 2) - 4
        }
    }
    
    val strokeWidth = with(LocalDensity.current) {
        when (type) {
            MoveIndicatorType.EMPTY_SQUARE -> 0.dp.toPx()  // Filled circle
            MoveIndicatorType.CAPTURE -> 2.5.dp.toPx()     // Stroke only
        }
    }
    
    // Apply the animation to the alpha and scale
    Box(
        modifier = modifier
            .fillMaxSize()
            .alpha(pulseAnimation * 0.9f)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.toPx() / 2, size.toPx() / 2)
            
            when (type) {
                MoveIndicatorType.EMPTY_SQUARE -> {
                    // Draw a filled circle for empty squares
                    drawCircle(
                        color = color,
                        radius = circleRadius * pulseAnimation,
                        center = center
                    )
                }
                MoveIndicatorType.CAPTURE -> {
                    // Draw a pulsating ring for captures
                    drawCircle(
                        color = color,
                        radius = circleRadius * pulseAnimation,
                        center = center,
                        style = Stroke(width = strokeWidth)
                    )
                    
                    // Draw dashed rotating ring for captures
                    drawDashedCircle(
                        center = center,
                        radius = circleRadius * 1.1f,
                        color = color.copy(alpha = 0.7f),
                        strokeWidth = strokeWidth * 0.7f,
                        dashCount = 8,
                        rotationDegrees = rotationAnimation
                    )
                }
            }
        }
    }
}

/**
 * Extension function to draw a dashed circle
 */
private fun DrawScope.drawDashedCircle(
    center: Offset,
    radius: Float,
    color: Color,
    strokeWidth: Float,
    dashCount: Int,
    rotationDegrees: Float
) {
    (2 * Math.PI * radius) / (dashCount * 2)
    
    for (i in 0 until dashCount) {
        val startAngle = (i * 2 * Math.PI / dashCount) + Math.toRadians(rotationDegrees.toDouble())
        val endAngle = startAngle + Math.PI / dashCount
        
        val startX = center.x + (radius * cos(startAngle)).toFloat()
        val startY = center.y + (radius * sin(startAngle)).toFloat()
        val endX = center.x + (radius * cos(endAngle)).toFloat()
        val endY = center.y + (radius * sin(endAngle)).toFloat()
        
        drawLine(
            color = color,
            start = Offset(startX, startY),
            end = Offset(endX, endY),
            strokeWidth = strokeWidth
        )
    }
}