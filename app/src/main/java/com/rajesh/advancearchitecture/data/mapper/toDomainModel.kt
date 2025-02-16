package com.rajesh.advancearchitecture.data.mapper

import com.rajesh.advancearchitecture.data.source.local.DeviceDao
import com.rajesh.advancearchitecture.data.source.local.DeviceEntity
import com.rajesh.advancearchitecture.data.source.remote.DetailDto
import com.rajesh.advancearchitecture.data.source.remote.DeviceDto
import com.rajesh.advancearchitecture.data.source.remote.DeviceObjects
import com.rajesh.advancearchitecture.domain.model.MobileData
import com.rajesh.advancearchitecture.domain.model.MobileDetail

// from database to ui data
fun DeviceEntity.toDomainModel(): MobileDetail {
    return MobileDetail(
        id = this.id,
        name = this.name,
        data = MobileData(
            generation = this.brand,
            price = this.price,
            capacity = this.capacity,
            description = this.description
        )
    )
}

// from server to ui
fun DeviceObjects.DeviceObjectsItem.toDomainModel(): MobileDetail {
    return MobileDetail(
        id = this.id,
        name = this.name,
        data = MobileData(
            generation = this.data?.generation,
            price = this.data?.price,
            capacity = this.data?.capacity,
            description = this.data?.description
        )
    )
}

// from server to database
fun DeviceObjects.DeviceObjectsItem.toEntity(): DeviceEntity {
    return DeviceEntity(
        id = this.id,
        name = this.name,
        brand = this.data?.generation,
        price = this.data?.price,
        capacity = this.data?.capacity,
        description = this.data?.description
    )
}