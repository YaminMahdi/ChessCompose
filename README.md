# Chess Game - Android Jetpack Compose

A modern, animated chess game implementation using Jetpack Compose with Material 3 design principles. This project demonstrates how to build a complete chess game with smooth animations, proper game state management, and clean architecture.

## Features

- Complete chess game with standard rules implementation
- Modern UI with Material 3 design
- Smooth animations for piece movement and selection
- Visual feedback for valid moves and captures
- Turn-based gameplay with clear player indicators
- Fully offline functionality with no external dependencies
- MVVM architecture with clean separation of concerns

## Implementation Details

### Architecture

The project follows the MVVM (Model-View-ViewModel) architecture pattern:

- **Model**: Data classes and game logic (ChessPiece, Position, GameState)
- **View**: Compose UI components (ChessBoard, ChessPiece, GameStatusBar)
- **ViewModel**: State management and business logic (ChessGameViewModel)

### Key Components

#### Data Models
- `ChessPiece`: Represents a chess piece with type, color, and position
- `Position`: Represents a position on the chess board with row and column
- `GameState`: Holds the complete state of the game including board, current player, and valid moves

#### Chess Logic
- `ChessRules`: Interface defining the rules of chess
- `StandardChessRules`: Implementation of standard chess rules
- `GameStateValidator`: Validates game state and enforces rules

#### UI Components
- `ChessBoard`: Renders the 8x8 chess board with pieces
- `ChessPieceIcon`: Renders individual chess pieces using ImageVectors
- `ValidMoveIndicator`: Shows visual indicators for valid moves
- `GameStatusBar`: Displays game status and current player turn

#### Animations
- Smooth piece movement animations
- Selection highlighting with scale/color animations
- Valid move indicators with subtle animations
- Visual feedback for invalid moves

### Technical Highlights

- **Immutable State**: All game state changes create new state objects for predictable updates
- **Pure Functions**: Chess logic implemented as pure functions for easy testing
- **Composable UI**: Fully built with Jetpack Compose for modern, declarative UI
- **Material 3**: Following Material 3 design principles for colors and components
- **Accessibility**: Content descriptions for pieces and board positions

## Project Structure

- `model/`: Data classes and game state definitions
- `model/rules/`: Chess rules implementation
- `ui/`: Compose UI components
- `ui/theme/`: Material 3 theme definitions
- `viewmodel/`: Game state management

## Getting Started

1. Clone the repository
2. Open the project in Android Studio
3. Build and run on an emulator or physical device

## Requirements

- Android Studio Arctic Fox or newer
- Kotlin 1.5.0 or newer
- Android 6.0 (API level 23) or higher

## Future Improvements

- Save/load game functionality
- Undo/redo moves
- AI opponent
- Online multiplayer
- Game analysis tools
- Custom themes and board styles

## License

This project is open source and available under the MIT License.