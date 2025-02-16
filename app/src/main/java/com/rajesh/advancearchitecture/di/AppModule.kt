package com.rajesh.advancearchitecture.di

import android.content.Context
import androidx.room.Room
import com.rajesh.advancearchitecture.data.repository.MobileDetailRepositoryImpl
import com.rajesh.advancearchitecture.data.source.local.AppDatabase
import com.rajesh.advancearchitecture.data.source.local.DeviceDao
import com.rajesh.advancearchitecture.data.source.remote.DeviceObjectService
import com.rajesh.advancearchitecture.domain.repository.MobileDetailRepository
import com.rajesh.advancearchitecture.domain.usecases.GetMobileDeviceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // Provide Retrofit Instance
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.restful-api.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //  Provide API Service
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): DeviceObjectService {
        return retrofit.create(DeviceObjectService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "device_db"
        ).build()
    }

    @Provides
    fun provideDeviceDao(database: AppDatabase): DeviceDao {
        return database.deviceDao()
    }

    @Provides
    @Singleton
    fun providesMobileDetailRepository(
        deviceObjectService: DeviceObjectService,
        deviceDao: DeviceDao
    ): MobileDetailRepository {
        return MobileDetailRepositoryImpl(deviceObjectService, deviceDao)
    }

    @Provides
    fun provideGetDevicesUseCase(repository: MobileDetailRepository): GetMobileDeviceUseCase {
        return GetMobileDeviceUseCase(repository)
    }

}