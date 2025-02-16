package com.rajesh.advancearchitecture.data.source.remote

import retrofit2.http.GET

interface DeviceObjectService {
    @GET("objects")//https://api.restful-api.dev/objects
    suspend fun getDevices(): DeviceObjects

//    @GET("device/{id}")
//    suspend fun getDeviceDetail(@Path("id") id:String): DeviceDao
}