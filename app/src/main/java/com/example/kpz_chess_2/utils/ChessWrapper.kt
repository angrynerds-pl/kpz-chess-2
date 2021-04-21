package com.example.kpz_chess_2.utils

import com.example.kpz_chess_2.utils.ChessWrapper.Option.SearchOption.*
import com.netsensia.rivalchess.config.MAX_SEARCH_DEPTH
import com.netsensia.rivalchess.config.MAX_SEARCH_MILLIS
import com.netsensia.rivalchess.engine.search.Search
import com.netsensia.rivalchess.enums.SearchState
import com.netsensia.rivalchess.model.Colour
import kotlin.jvm.internal.FunctionReference


@ExperimentalUnsignedTypes
class ChessWrapper(var updateCallback: ((board: Board)->Unit)? = null) {
    lateinit var search: Search
    var board = Board()

    var searchOptions: MutableMap<Option.SearchOption, Any?> = mutableMapOf(
            WHITE_TIME to WHITE_TIME.default,
            BLACK_TIME to BLACK_TIME.default,
            WHITE_INC to WHITE_INC.default,
            BLACK_INC to BLACK_INC.default,
            MOVES_TO_GO to MOVES_TO_GO.default,
            MAX_DEPTH to MAX_DEPTH.default,
            MAX_NODES to MAX_NODES.default,
            MOVE_TIME to MOVE_TIME.default,
            IS_INFINITE to IS_INFINITE.default
    )

    fun newGame(){
        waitForSearchToComplete()
        search.newGame()
    }

    fun position(){}

    fun move(piece: Piece, to: Field){

        piece.position = to
        update()
    }

    fun update(){ updateCallback?.invoke(board) }

    fun go(){
        search.go()

    }

    fun setOption(option: Option, value: Any? = null) = when (option){
        Option.OWN_BOOK -> search.useOpeningBook = value as Boolean
        Option.HASH -> search.setHashSizeMB(value as Int)
        Option.CLEAR -> search.clearHash()
        else -> throw Option.OptionNotFoundException()
    }

    fun setOption(option: Option.SearchOption, value: Any? = null, save: Boolean = false){
        searchOptions[option] = value
        if (save) saveSearchOptions()
    }

    fun saveSearchOptions() {
        if (searchOptions[IS_INFINITE] as Boolean) {
            search.setMillisToThink(MAX_SEARCH_MILLIS)
            search.setSearchDepth(MAX_SEARCH_DEPTH - 2)
            search.setNodesToSearch(Int.MAX_VALUE)
        } else if ((searchOptions[MOVE_TIME] as Int) != -1 || (searchOptions[MAX_NODES] as Int) != -1 || (searchOptions[MAX_DEPTH] as Int) != -1) {
            search.setMillisToThink(if ((searchOptions[MOVE_TIME] as Int) != -1) searchOptions[MOVE_TIME] as Int else MAX_SEARCH_MILLIS)
            search.setSearchDepth(if ((searchOptions[MAX_DEPTH] as Int) != -1) searchOptions[MAX_DEPTH] as Int else MAX_SEARCH_DEPTH - 2)
            search.setNodesToSearch(if ((searchOptions[MAX_NODES] as Int) > 0) searchOptions[MAX_NODES] as Int else Int.MAX_VALUE)
        } else if (searchOptions[WHITE_TIME] as Int != -1) {
            val calcTime: Int =
                (if (search.mover == Colour.WHITE) (searchOptions[WHITE_TIME] as Int) else (searchOptions[BLACK_TIME] as Int)) /
                        (if (searchOptions[MOVES_TO_GO] as Int == 0) (120) else (searchOptions[MOVES_TO_GO] as Int))
            val guaranteedTime: Int = if (search.mover == Colour.WHITE) (searchOptions[WHITE_INC] as Int) else (searchOptions[BLACK_INC] as Int)
            val timeToThink: Int = calcTime + guaranteedTime
            search.setMillisToThink(timeToThink)
        }
    }

    fun stop(){
        search.stopSearch()
        waitForSearchToComplete()
    }

    private fun waitForSearchToComplete(){
        var state: SearchState
        search.stopSearch()
        do{
            state = search.engineState
        } while (state != SearchState.READY && state != SearchState.COMPLETE)
    }

    //enums
    enum class Option{
        OWN_BOOK, HASH, CLEAR
        ;
        class OptionNotFoundException : Exception() {}
        enum class SearchOption(val default: Any){
            WHITE_TIME(1), BLACK_TIME(1), WHITE_INC(1), BLACK_INC(1),
            MOVES_TO_GO(1), MAX_DEPTH(1), MAX_NODES(1), MOVE_TIME(1),
            IS_INFINITE(false)
        }
    }

    enum class Color(val mask: Char){
        WHITE('W'), BLACK('B')
    }

    enum class Type(val mask: Char){
        PAWN('P'), ROOK('R'), BISHOP('B'), KNIGHT('N'), QUEEN('Q'),
        KING('K')
    }

    //data classes
    class Piece(var position: Field, var color: Color, var type: Type) {}

    class Field(var row: Char, var column: Int, var board: Board, var piece: Piece?) {}

    class Board(){
        var fields: MutableMap<Char, MutableList<Field>> = mutableMapOf()

        init{
            for(row in listOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H')){
                fields[row] = mutableListOf()
                for(i in 1..8){
                    fields[row]?.add(Field(row, i, this, null))
                }
            }
        }

        operator fun get(row: Char, column: Int): Field { return fields[row]!![column] }
    }
}