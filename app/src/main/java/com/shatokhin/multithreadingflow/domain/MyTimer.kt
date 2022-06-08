package com.shatokhin.multithreadingflow.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class MyTimer(private val viewModelScope: CoroutineScope) {
    val timeInSecondFlow: MutableSharedFlow<Int> = MutableSharedFlow(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private var timeInSecond = 0
        private set(value) {
        field = value
            viewModelScope.launch {
                timeInSecondFlow.emit(timeInSecond)
            }
    }

    private var counter: Counter? = null

    fun increment(){
        timeInSecond++
    }

    suspend fun start(){
        counter?.kill()
        counter = Counter()
        counter?.start()
    }

    fun pause(){
        counter?.kill()
    }

    private fun clearCounter(){
        timeInSecond = 0
    }

    suspend fun reset(){
        clearCounter()
        start()
    }

    inner class Counter {
        private var isEnable = true

        fun kill(){
            isEnable = false
        }

        suspend fun start() {
            while ( isEnable ){
                delay(1000)
                if ( isEnable ) increment() // если во время sleep не вызвали pause(), то выполняем increment()
            }
        }

    }

}