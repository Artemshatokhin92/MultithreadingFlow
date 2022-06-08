package com.shatokhin.multithreadingflow.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shatokhin.multithreadingflow.domain.MyTimer
import com.shatokhin.multithreadingflow.domain.NumberPiObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelMain: ViewModel() {

    private lateinit var timer: MyTimer
    private lateinit var numberPiObj: NumberPiObj

    var isEnable = false
    private set

    init {
        initTimer()
        initNumberPiObj()
    }

    val timeInSecondFlow = timer.timeInSecondFlow

    val numberPiFlow = numberPiObj.numberPiFlow


    private fun initNumberPiObj() {
        numberPiObj = NumberPiObj(viewModelScope)
    }

    private fun initTimer() {
        timer = MyTimer(viewModelScope)
    }


    fun start(){
        isEnable = true

        viewModelScope.launch(Dispatchers.Default) {
            numberPiObj.start()
        }
        viewModelScope.launch(Dispatchers.Default) {
            timer.start()
        }

    }

    fun pause(){
        isEnable = false
        numberPiObj.pause()
        timer.pause()
    }

    fun reset(){
        isEnable = true

        viewModelScope.launch(Dispatchers.Default) {
            numberPiObj.reset()
        }
        viewModelScope.launch(Dispatchers.Default) {
            timer.reset()
        }
    }

    fun pauseForRestartActivity(){
        numberPiObj.pause()
        timer.pause()
    }
}