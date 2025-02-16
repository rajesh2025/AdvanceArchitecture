package com.rajesh.advancearchitecture.data.repository

import com.rajesh.advancearchitecture.data.mapper.toDomainModel
import com.rajesh.advancearchitecture.data.mapper.toEntity
import com.rajesh.advancearchitecture.data.source.local.DeviceDao
import com.rajesh.advancearchitecture.data.source.remote.DeviceObjectService
import com.rajesh.advancearchitecture.domain.model.MobileDetail
import com.rajesh.advancearchitecture.domain.repository.MobileDetailRepository

class MobileDetailRepositoryImpl(
    private val deviceObjectService: DeviceObjectService,
    private val deviceDao: DeviceDao
) : MobileDetailRepository {


    override suspend fun getAllMobileDetailList(): List<MobileDetail> {
        val deviceDtos = deviceDao.getAllDevices()
        return if (deviceDtos.isNotEmpty()) {
            deviceDtos.map { it.toDomainModel()}
        } else {
            val apiDevices = deviceObjectService.getDevices()
            deviceDao.insertDevice(apiDevices.map { it.toEntity() })
            apiDevices.map { it.toDomainModel() }
        }
    }
}