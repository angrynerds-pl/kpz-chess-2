package com.example.kpz_chess_2.utils

import com.netsensia.rivalchess.engine.search.Search
import com.netsensia.rivalchess.enums.SearchState
import com.netsensia.rivalchess.model.Colour
import com.example.kpz_chess_2.utils.ChessWrapper.Option.SearchOption.*
import com.netsensia.rivalchess.config.MAX_SEARCH_DEPTH
import com.netsensia.rivalchess.config.MAX_SEARCH_MILLIS

@ExperimentalUnsignedTypes
class ChessWrapper {
    lateinit var search: Search
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

    fun newGame(){
        waitForSearchToComplete()
        search.newGame()
    }

    fun position(){}

    fun go(){}

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
//        if (searchOptions[IS_INFINITE] as Boolean) {
//            search.setMillisToThink(MAX_SEARCH_MILLIS)
//            search.setSearchDepth(MAX_SEARCH_DEPTH - 2)
//            search.setNodesToSearch(Int.MAX_VALUE)
//        } else if (moveTime !== -1 || maxNodes !== -1 || maxDepth !== -1) {
//            search.setMillisToThink(if (moveTime !== -1) moveTime else MAX_SEARCH_MILLIS)
//            search.setSearchDepth(if (maxDepth !== -1) maxDepth else MAX_SEARCH_DEPTH - 2)
//            search.setNodesToSearch(if (maxNodes > 0) maxNodes else Int.MAX_VALUE)
//        } else if (whiteTime !== -1) {
//            val calcTime: Int =
//                (if (search.mover == Colour.WHITE) whiteTime else blackTime) / if (movesToGo === 0) 120 else movesToGo
//            val guaranteedTime: Int = if (search.mover == Colour.WHITE) whiteInc else blackInc
//            val timeToThink: Int =
//                calcTime + guaranteedTime - Uci.UCI_TIMER_SAFTEY_MARGIN_MILLIS.getValue()
//            search.setMillisToThink(timeToThink)
//        }
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
}