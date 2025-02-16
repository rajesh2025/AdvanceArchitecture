package com.rajesh.advancearchitecture.domain.mapper

import com.rajesh.advancearchitecture.data.source.local.DeviceEntity
import com.rajesh.advancearchitecture.data.source.remote.DetailDto
import com.rajesh.advancearchitecture.data.source.remote.DeviceDto
import com.rajesh.advancearchitecture.domain.model.MobileData
import com.rajesh.advancearchitecture.domain.model.MobileDetail

//fun DeviceEntity.toDomainModel(): MobileDetail {
//    return MobileDetail(
//        id = this.id,
//        name = this.name,
//        data = MobileData(
//            generation = this.brand,
//            price = this.price,
//            capacity = this.capacity
//        )
//    )
//}
//
//fun DetailDto.toDetailModel(): MobileData {
//    return MobileData(
//        generation = this.genDetail,
//        price = this.priceDetail,
//        capacity = this.capacityDetail
//
//    )
//}