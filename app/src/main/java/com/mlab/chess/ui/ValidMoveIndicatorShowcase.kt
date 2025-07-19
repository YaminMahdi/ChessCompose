package com.mlab.chess.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * A showcase screen that demonstrates the ValidMoveIndicator composable
 */
@Composable
fun ValidMoveIndicatorShowcase() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Valid Move Indicators",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            // Empty square indicator
            IndicatorCard(
                title = "Empty Square Indicator",
                description = "Used for valid moves to empty squares",
                indicatorType = MoveIndicatorType.EMPTY_SQUARE,
                backgroundColor = Color(0xFF769656) // Dark square color
            )
            
            // Capture indicator
            IndicatorCard(
                title = "Capture Indicator",
                description = "Used for valid moves that capture opponent pieces",
                indicatorType = MoveIndicatorType.CAPTURE,
                backgroundColor = Color(0xFFEEEED2) // Light square color
            )
        }
    }
}

/**
 * A card that showcases a specific move indicator type
 */
@Composable
private fun IndicatorCard(
    title: String,
    description: String,
    indicatorType: MoveIndicatorType,
    backgroundColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Square with indicator
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                ValidMoveIndicator(
                    type = indicatorType,
                    size = 80.dp,
                    color = Color(0x80FFFF00)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ValidMoveIndicatorShowcasePreview() {
    ValidMoveIndicatorShowcase()
}