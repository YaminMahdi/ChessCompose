package com.mlab.chess

import androidx.compose.ui.graphics.vector.ImageVector
import com.mlab.chess.chess.`Bishop-b`
import com.mlab.chess.chess.`Bishop-w`
import com.mlab.chess.chess.`King-b`
import com.mlab.chess.chess.`King-w`
import com.mlab.chess.chess.`Knight-b`
import com.mlab.chess.chess.`Knight-w`
import com.mlab.chess.chess.`Pawn-b`
import com.mlab.chess.chess.`Pawn-w`
import com.mlab.chess.chess.`Queen-b`
import com.mlab.chess.chess.`Queen-w`
import com.mlab.chess.chess.`Rook-b`
import com.mlab.chess.chess.`Rook-w`
import kotlin.collections.List as ____KtList

object Chess

private var __Chess: ____KtList<ImageVector>? = null

val Chess.Chess: ____KtList<ImageVector>
  get() {
    if (__Chess != null) {
      return __Chess!!
    }
    __Chess= listOf(`Bishop-b`, `Bishop-w`, `King-b`, `King-w`, `Knight-b`, `Knight-w`, `Pawn-b`,
        `Pawn-w`, `Queen-b`, `Queen-w`, `Rook-b`, `Rook-w`)
    return __Chess!!
  }
