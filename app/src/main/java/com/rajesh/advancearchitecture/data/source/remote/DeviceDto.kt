package com.rajesh.advancearchitecture.data.source.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeviceDto(
    val id: Int,
    val deviceName: String?,
    val detail: DetailDto?,
) : Parcelable


@Parcelize
data class DetailDto(
    val genDetail: String?,
    val priceDetail: String?,
    val capacityDetail: String?
) : Parcelable

