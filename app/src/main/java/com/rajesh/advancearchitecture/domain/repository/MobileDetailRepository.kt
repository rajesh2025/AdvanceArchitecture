package com.rajesh.advancearchitecture.domain.repository

import com.rajesh.advancearchitecture.domain.model.MobileDetail

interface MobileDetailRepository {
    suspend fun getAllMobileDetailList() : List<MobileDetail>
}