package com.mlab.chess.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mlab.chess.model.GameStatus
import com.mlab.chess.model.PieceColor

/**
 * A showcase screen for the GameStatusBar component with interactive state changes
 */
@Composable
fun GameStatusBarShowcase() {
    var currentPlayer by remember { mutableStateOf(PieceColor.WHITE) }
    var gameStatus by remember { mutableStateOf(GameStatus.ACTIVE) }
    
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
                text = "Game Status Bar Showcase",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Display the GameStatusBar
            GameStatusBar(
                currentPlayer = currentPlayer,
                gameStatus = gameStatus
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Controls to change player
            Button(
                onClick = { 
                    currentPlayer = if (currentPlayer == PieceColor.WHITE) 
                        PieceColor.BLACK else PieceColor.WHITE 
                }
            ) {
                Text("Switch Player")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Controls to change game status
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Change Game Status:", style = MaterialTheme.typography.titleMedium)
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Button(onClick = { gameStatus = GameStatus.ACTIVE }) {
                    Text("Active")
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Button(onClick = { gameStatus = GameStatus.CHECK }) {
                    Text("Check")
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Button(onClick = { gameStatus = GameStatus.CHECKMATE }) {
                    Text("Checkmate")
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Button(onClick = { gameStatus = GameStatus.STALEMATE }) {
                    Text("Stalemate")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameStatusBarShowcasePreview() {
    MaterialTheme {
        GameStatusBarShowcase()
    }
}