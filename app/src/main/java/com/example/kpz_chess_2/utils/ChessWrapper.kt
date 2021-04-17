package com.example.kpz_chess_2.utils

import com.netsensia.rivalchess.engine.search.Search
import com.netsensia.rivalchess.enums.SearchState

@ExperimentalUnsignedTypes
class ChessWrapper {
    lateinit var search: Search

    enum class Options(val value: Int){
        TEST(0),
        ;
        class OptionNotFoundException : Exception() {}
    }

    fun isReady(){}
    fun newGame(){}
    fun position(){}
    fun go(){}
    fun setOption(option: Options, value: String) {
        when (option){

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