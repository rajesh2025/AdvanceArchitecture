package com.rajesh.advancearchitecture

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class SumService : Service() {

    private val binder = object : ISumService.Stub() {
        override fun sum(a: Int, b: Int): Int {
            Log.d("SumService", "Calculating sum: $a + $b")
            return a + b
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }
}