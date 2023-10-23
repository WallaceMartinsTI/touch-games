package com.wcsm.touchgames.memorygame

import android.os.Handler

abstract class CountUpTimer(private val countUpInterval: Long) {
    private var running = false
    private var startTime: Long = 0
    private val handler = Handler()
    private val updateTask = object : Runnable {
        override fun run() {
            if (running) {
                onTick(System.currentTimeMillis() - startTime)
                handler.postDelayed(this, countUpInterval)
            }
        }
    }

    fun start() {
        if (!running) {
            startTime = System.currentTimeMillis()
            running = true
            handler.removeCallbacks(updateTask)
            handler.post(updateTask)
        }
    }

    fun stop() {
        running = false
        handler.removeCallbacks(updateTask)
    }

    fun reset() {
        stop()
        onTick(0)
    }

    abstract fun onTick(elapsedTime: Long)
}