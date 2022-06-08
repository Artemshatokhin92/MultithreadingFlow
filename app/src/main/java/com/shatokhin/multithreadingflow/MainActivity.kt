package com.shatokhin.multithreadingflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.shatokhin.multithreadingflow.presentation.viewmodel.ViewModelMain

class MainActivity : AppCompatActivity() {

    private val viewModelMain: ViewModelMain by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStop() {
        super.onStop()
        if (viewModelMain.isEnable) viewModelMain.pauseForRestartActivity()
    }

    override fun onRestart() {
        super.onRestart()
        if (viewModelMain.isEnable) viewModelMain.start()
    }
}