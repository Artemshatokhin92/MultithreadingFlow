package com.shatokhin.multithreadingflow.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class NumberPiObj(private val viewModelScope: CoroutineScope) {
    val numberPiFlow: MutableSharedFlow<String> = MutableSharedFlow(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private var numberPiString = "3,14"
        private set(value) {
            field = value
            viewModelScope.launch {
                numberPiFlow.emit(numberPiString)
            }
        }

    private var calculator: Calculator? = null

    suspend fun start(){
        calculator?.kill()
        calculator = Calculator()
        calculator?.start()
    }

    fun pause(){
        calculator?.kill()
    }

    suspend fun reset(){
        clearNumberPi()
        start()
    }

    private fun clearNumberPi(){
        numberPiString = "3,14"
    }

    inner class Calculator {
        private var isEnable = true

        fun kill(){
            isEnable = false
        }

        suspend fun start() {
            while ( isEnable ){
                delay(500)
                if ( isEnable ) {
                    numberPiString += getNextNumber()
                }
            }
        }
    }

    private fun calculateNumberInPosition(position: Int): Int {
        return (0..9).random()
    }

    fun getNextNumber(): Int {
        return calculateNumberInPosition(numberPiString.length)
    }

}