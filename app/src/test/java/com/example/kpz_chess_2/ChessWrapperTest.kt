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
        print(chess.board)
        assert(chess.board.toString() == "1={A=A1<WHITE ROOK>, B=B1<WHITE KNIGHT>, C=C1<WHITE BISHOP>, D=D1<WHITE QUEEN>, E=E1<WHITE KING>, F=F1<WHITE BISHOP>, G=G1<WHITE KNIGHT>, H=H1<WHITE ROOK>}\n" +
                "2={A=A2<WHITE PAWN>, B=B2<WHITE PAWN>, C=C2<WHITE PAWN>, D=D2<WHITE PAWN>, E=E2<WHITE PAWN>, F=F2<WHITE PAWN>, G=G2<WHITE PAWN>, H=H2<WHITE PAWN>}\n" +
                "3={A=A3<null null>, B=B3<null null>, C=C3<null null>, D=D3<null null>, E=E3<null null>, F=F3<null null>, G=G3<null null>, H=H3<null null>}\n" +
                "4={A=A4<null null>, B=B4<null null>, C=C4<null null>, D=D4<null null>, E=E4<null null>, F=F4<null null>, G=G4<null null>, H=H4<null null>}\n" +
                "5={A=A5<null null>, B=B5<null null>, C=C5<null null>, D=D5<null null>, E=E5<null null>, F=F5<null null>, G=G5<null null>, H=H5<null null>}\n" +
                "6={A=A6<null null>, B=B6<null null>, C=C6<null null>, D=D6<null null>, E=E6<null null>, F=F6<null null>, G=G6<null null>, H=H6<null null>}\n" +
                "7={A=A7<BLACK PAWN>, B=B7<BLACK PAWN>, C=C7<BLACK PAWN>, D=D7<BLACK PAWN>, E=E7<BLACK PAWN>, F=F7<BLACK PAWN>, G=G7<BLACK PAWN>, H=H7<BLACK PAWN>}\n" +
                "8={A=A8<BLACK ROOK>, B=B8<BLACK KNIGHT>, C=C8<BLACK BISHOP>, D=D8<BLACK QUEEN>, E=E8<BLACK KING>, F=F8<BLACK BISHOP>, G=G8<BLACK KNIGHT>, H=H8<BLACK ROOK>}\n")
        chess.move(chess.board['A', 2]?.piece!!, chess.board['A', 3]!!)
        chess.newGame()
        assert(chess.search.engineBoard.getFen() == "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
        assert(chess.board.toString() == "1={A=A1<WHITE ROOK>, B=B1<WHITE KNIGHT>, C=C1<WHITE BISHOP>, D=D1<WHITE QUEEN>, E=E1<WHITE KING>, F=F1<WHITE BISHOP>, G=G1<WHITE KNIGHT>, H=H1<WHITE ROOK>}\n" +
                "2={A=A2<WHITE PAWN>, B=B2<WHITE PAWN>, C=C2<WHITE PAWN>, D=D2<WHITE PAWN>, E=E2<WHITE PAWN>, F=F2<WHITE PAWN>, G=G2<WHITE PAWN>, H=H2<WHITE PAWN>}\n" +
                "3={A=A3<null null>, B=B3<null null>, C=C3<null null>, D=D3<null null>, E=E3<null null>, F=F3<null null>, G=G3<null null>, H=H3<null null>}\n" +
                "4={A=A4<null null>, B=B4<null null>, C=C4<null null>, D=D4<null null>, E=E4<null null>, F=F4<null null>, G=G4<null null>, H=H4<null null>}\n" +
                "5={A=A5<null null>, B=B5<null null>, C=C5<null null>, D=D5<null null>, E=E5<null null>, F=F5<null null>, G=G5<null null>, H=H5<null null>}\n" +
                "6={A=A6<null null>, B=B6<null null>, C=C6<null null>, D=D6<null null>, E=E6<null null>, F=F6<null null>, G=G6<null null>, H=H6<null null>}\n" +
                "7={A=A7<BLACK PAWN>, B=B7<BLACK PAWN>, C=C7<BLACK PAWN>, D=D7<BLACK PAWN>, E=E7<BLACK PAWN>, F=F7<BLACK PAWN>, G=G7<BLACK PAWN>, H=H7<BLACK PAWN>}\n" +
                "8={A=A8<BLACK ROOK>, B=B8<BLACK KNIGHT>, C=C8<BLACK BISHOP>, D=D8<BLACK QUEEN>, E=E8<BLACK KING>, F=F8<BLACK BISHOP>, G=G8<BLACK KNIGHT>, H=H8<BLACK ROOK>}\n")
    }

    @Test
    fun testMove(){
        chess.newGame()
        chess.move(chess.board['A', 2].piece!!, chess.board['A', 3])
        assert(chess.board['A', 2].piece == null)
        assert(chess.board['A', 3].piece!!.type == ChessWrapper.Type.PAWN &&
                chess.board['A', 3].piece!!.position!!.column == 'A' &&
                chess.board['A', 3].piece!!.position!!.row == 3)
        chess.move(chess.board['A', 7].piece!!, chess.board['A', 6])
        try {
            chess.move(chess.board['A', 6].piece!!, chess.board['A', 5])
        } catch (e: Exception) {
            assert(e is IllegalAccessException)
        }
        chess.move(chess.board['A', 1].piece!!, chess.board['B', 3])
    }

    @Test
    fun testGo(){
        chess.newGame(ChessWrapper.Color.WHITE)
        chess.go()
        chess.move(chess.board['A', 7].piece!!, chess.board['A', 6])
        chess.go()
    }
}