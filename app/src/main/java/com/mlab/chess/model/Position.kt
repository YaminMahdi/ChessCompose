package com.mlab.chess.model

/**
 * Represents a position on the chess board
 * @param row The row (0-7, where 0 is the top row)
 * @param col The column (0-7, where 0 is the leftmost column)
 */
data class Position(val row: Int, val col: Int) {
    
    /**
     * Checks if this position is within the valid chess board bounds
     * @return true if position is valid (0-7 for both row and col), false otherwise
     */
    fun isValid(): Boolean = row in 0..7 && col in 0..7
    
    /**
     * Returns a new position offset by the given row and column deltas
     * @param deltaRow The row offset
     * @param deltaCol The column offset
     * @return A new Position offset by the given deltas
     */
    fun offset(deltaRow: Int, deltaCol: Int): Position = 
        Position(row + deltaRow, col + deltaCol)
    
    /**
     * Calculates the Manhattan distance to another position
     * @param other The other position
     * @return The Manhattan distance (sum of absolute differences in row and col)
     */
    fun manhattanDistanceTo(other: Position): Int = 
        kotlin.math.abs(row - other.row) + kotlin.math.abs(col - other.col)
    
    /**
     * Calculates the Chebyshev distance (max of row/col differences) to another position
     * @param other The other position
     * @return The Chebyshev distance (maximum of absolute differences in row and col)
     */
    fun chebyshevDistanceTo(other: Position): Int = 
        maxOf(kotlin.math.abs(row - other.row), kotlin.math.abs(col - other.col))
    
    /**
     * Checks if this position is on the same row as another position
     * @param other The other position
     * @return true if both positions have the same row
     */
    fun isSameRow(other: Position): Boolean = row == other.row
    
    /**
     * Checks if this position is on the same column as another position
     * @param other The other position
     * @return true if both positions have the same column
     */
    fun isSameColumn(other: Position): Boolean = col == other.col
    
    /**
     * Checks if this position is on the same diagonal as another position
     * @param other The other position
     * @return true if both positions are on the same diagonal
     */
    fun isSameDiagonal(other: Position): Boolean {
        val rowDiff = kotlin.math.abs(row - other.row)
        val colDiff = kotlin.math.abs(col - other.col)
        return rowDiff == colDiff
    }
    
    /**
     * Returns a string representation in chess notation (e.g., "a1", "h8")
     * @return Chess notation string
     */
    fun toChessNotation(): String {
        val file = ('a' + col).toString()
        val rank = (8 - row).toString()
        return file + rank
    }
    
    companion object {
        /**
         * Creates a Position from chess notation (e.g., "a1", "h8")
         * @param notation The chess notation string
         * @return Position corresponding to the notation, or null if invalid
         */
        fun fromChessNotation(notation: String): Position? {
            if (notation.length != 2) return null
            
            val file = notation[0].lowercaseChar()
            val rank = notation[1]
            
            if (file !in 'a'..'h' || rank !in '1'..'8') return null
            
            val col = file - 'a'
            val row = 8 - (rank - '0')
            
            return Position(row, col)
        }
    }
}