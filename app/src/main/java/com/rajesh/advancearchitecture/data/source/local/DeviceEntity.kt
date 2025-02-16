package com.rajesh.advancearchitecture.data.source.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "devices")
data class DeviceEntity(
    @PrimaryKey val id: String,
    val name: String?,
    val brand: String?,
    val price: Double?,
    val capacity: String?,
    val description: String?
)


@Dao
interface DeviceDao {

    @Query("SELECT * from devices")
    suspend fun getAllDevices(): List<DeviceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevice(devices: List<DeviceEntity>)
}