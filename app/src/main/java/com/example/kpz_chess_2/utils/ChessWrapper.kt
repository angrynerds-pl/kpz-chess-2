package com.example.kpz_chess_2.utils

import com.netsensia.rivalchess.engine.search.Search
import com.netsensia.rivalchess.enums.SearchState

@ExperimentalUnsignedTypes
class ChessWrapper {
    lateinit var search: Search

    fun isReady(){}
    fun newGame(){}
    fun position(){}
    fun go(){}
    fun setOption(){}
    fun stop(){
        search.stopSearch()
        waitForSearchToComplete()
    }

    fun waitForSearchToComplete(){
        var state: SearchState
        search.stopSearch()
        do{
            state = search.engineState
        } while (state != SearchState.READY && state != SearchState.COMPLETE)
    }
}