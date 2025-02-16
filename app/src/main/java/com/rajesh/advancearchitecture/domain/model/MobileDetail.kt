package com.rajesh.advancearchitecture.domain.model

import android.os.Parcelable

data class MobileDetail(
    val id: String,
    val name: String?,
    val data: MobileData?
)
data class MobileData(
    val generation: String?,
    val price: Double?,
    val capacity: String?,
    val description: String?
)
