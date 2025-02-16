package com.rajesh.advancearchitecture.domain.usecases

import com.rajesh.advancearchitecture.domain.model.MobileDetail
import com.rajesh.advancearchitecture.domain.repository.MobileDetailRepository

class GetMobileDeviceUseCase( private val repository: MobileDetailRepository) {
    suspend operator fun invoke() : List<MobileDetail> = repository.getAllMobileDetailList()
}