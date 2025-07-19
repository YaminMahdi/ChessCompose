package com.mlab.chess

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.mlab.chess.ui.ChessGameScreen
import com.mlab.chess.ui.theme.ChessTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChessTheme {
                // A surface container using the 'background' color from the theme
                Scaffold {
                    ChessGameScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                    )
                }
            }
        }
    }
}