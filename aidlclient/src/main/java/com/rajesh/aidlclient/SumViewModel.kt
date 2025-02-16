package com.rajesh.aidlclient

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rajesh.advancearchitecture.ISumService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SumViewModel : ViewModel() {
    private var sumservice: ISumService? = null
     var isBound = false
    private var _sumResult = MutableStateFlow<String>("Not calculated")
    private var sumResult: StateFlow<String> = _sumResult

     val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
            sumservice = ISumService.Stub.asInterface(service)
            isBound = true
            calculateSum()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            sumservice = null
            isBound = false
        }
    }

    private fun calculateSum() {
        viewModelScope.launch {
            if (sumservice != null && isBound) {
                val result = sumservice?.sum(5, 3)
                _sumResult.value = "Result: $result"
            }
        }
    }



    init {
//        bindService()
    }


}