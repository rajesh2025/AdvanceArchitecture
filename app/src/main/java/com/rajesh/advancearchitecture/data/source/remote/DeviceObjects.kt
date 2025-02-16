package com.rajesh.advancearchitecture.data.source.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

class DeviceObjects : ArrayList<DeviceObjects.DeviceObjectsItem>(){
    @Keep
    @Serializable
    @Parcelize
    data class DeviceObjectsItem(
        @SerialName("data")
        @Expose val `data`: Data?,
        @SerialName("id")
        @Expose val id: String, // 1
        @SerialName("name")
        @Expose val name: String? // Google Pixel 6 Pro
    ) : Parcelable {
        @Keep
        @Serializable
        @Parcelize
        data class Data(
            @SerialName("CPU model")
            @Expose val cPUModel: String?, // Intel Core i9
            @SerialName("capacity")
            @Expose val capacity: String?, // 128 GB
            @SerialName("capacity GB")
            @Expose val capacityGB: Int?, // 512
            @SerialName("Case Size")
            @Expose val caseSize: String?, // 41mm
            @SerialName("color")
            @Expose val color: String?, // Cloudy White
            @SerialName("Description")
            @Expose val description: String?, // High-performance wireless noise cancelling headphones
            @SerialName("generation")
            @Expose val generation: String?, // 3rd
            @SerialName("Hard disk size")
            @Expose val hardDiskSize: String?, // 1 TB
            @SerialName("price")
            @Expose val price: Double?, // 389.99
            @SerialName("Screen size")
            @Expose val screenSize: Double?, // 7.9
            @SerialName("Strap Colour")
            @Expose val strapColour: String?, // Elderberry
            @SerialName("year")
            @Expose val year: Int? // 2019
        ) : Parcelable
    }
}