package com.example.kpz_chess_2

import com.example.kpz_chess_2.utils.ChessWrapper
import com.netsensia.rivalchess.engine.board.getFen
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalUnsignedTypes
class ChessWrapperTest {

    var chess: ChessWrapper = ChessWrapper()

    @Test
    fun testNewGame() {
        chess.newGame()
        assert(chess.search.engineBoard.getFen() == "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
        chess.update()
        println(chess.board)
    }
}