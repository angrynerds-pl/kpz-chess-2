package com.example.kpz_chess_2.utils

import com.netsensia.rivalchess.engine.search.Search
import com.netsensia.rivalchess.enums.SearchState

@ExperimentalUnsignedTypes
class ChessWrapper {
    lateinit var search: Search

    enum class Options{
        OWN_BOOK, HASH, CLEAR
        ;
        class OptionNotFoundException : Exception() {}
    }

    fun isReady(){}

    fun newGame(){
        waitForSearchToComplete()
        search.newGame()
    }

    fun position(){}

    fun go(){}

    fun setOption(option: Options, value: Any? = null) {
        when (option){
            Options.OWN_BOOK -> search.useOpeningBook = value as Boolean
            Options.HASH -> search.setHashSizeMB(value as Int)
            Options.CLEAR -> search.clearHash()
            else -> throw Options.OptionNotFoundException()
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
}