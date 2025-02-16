package com.rajesh.advancearchitecture.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rajesh.advancearchitecture.domain.usecases.GetMobileDeviceUseCase
import com.rajesh.advancearchitecture.presentation.ui.DeviceListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class MobileDeviceViewModel @Inject constructor(
    private val getMobileDeviceUseCase: GetMobileDeviceUseCase
) : ViewModel() {

    private val _uiState = mutableStateOf(DeviceListUiState())
    val uiState: State<DeviceListUiState> = _uiState

    init {
        loadDevices()


    }

    private fun loadDevices() {
        viewModelScope.launch {
            _uiState.value = DeviceListUiState(isLoading = true)
            val devices = getMobileDeviceUseCase()
            _uiState.value = DeviceListUiState(devices = devices)
        }
    }
}