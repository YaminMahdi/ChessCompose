package com.mlab.chess.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mlab.chess.model.ChessPiece
import com.mlab.chess.model.PieceColor
import com.mlab.chess.model.PieceType
import com.mlab.chess.model.Position

/**
 * A showcase composable that displays all chess pieces with their respective ImageVectors
 */
@Composable
fun ChessPieceShowcase() {
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
                text = "Chess Pieces",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            // White pieces row
            PieceRow(color = PieceColor.WHITE)
            
            // Black pieces row
            PieceRow(color = PieceColor.BLACK)
        }
    }
}

/**
 * A row of chess pieces of a specific color
 * @param color The color of the chess pieces
 */
@Composable
private fun PieceRow(color: PieceColor) {
    val backgroundColor = if (color == PieceColor.WHITE) 
        Color(0xFF4B4B4B) else Color(0xFFE0E0E0)
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "${color.name.lowercase().capitalize()} Pieces",
            style = MaterialTheme.typography.titleMedium
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Create a piece for each piece type
            PieceType.values().forEach { pieceType ->
                val piece = ChessPiece(
                    type = pieceType,
                    color = color,
                    position = Position(0, 0) // Dummy position
                )
                
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    ChessPieceIcon(
                        piece = piece,
                        size = 48.dp
                    )
                    
                    Text(
                        text = pieceType.name.lowercase().capitalize(),
                        style = MaterialTheme.typography.bodySmall,
                        color = if (color == PieceColor.WHITE) Color.White else Color.Black
                    )
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