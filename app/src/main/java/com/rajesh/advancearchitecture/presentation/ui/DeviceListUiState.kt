package com.rajesh.advancearchitecture.presentation.ui

import com.rajesh.advancearchitecture.domain.model.MobileDetail


data class DeviceListUiState(
    val isLoading : Boolean = false,
    val devices : List<MobileDetail> = emptyList(),
    val errorMessage : String? = null
)
