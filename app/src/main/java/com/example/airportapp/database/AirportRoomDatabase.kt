package com.example.airportapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

private lateinit var INSTANCE: AirportDatabase


@Database(entities = [AirportEntity::class], version = 1)
abstract class AirportDatabase: RoomDatabase() {
    abstract val airportDao: AirportDao
}

@Dao
interface AirportDao {
    @Query("select * from airportentity")
    fun getAirports(): LiveData<List<AirportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( airports: List<AirportEntity>)
}

fun getAirportDatabase(context: Context): AirportDatabase {
    synchronized(AirportDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                AirportDatabase::class.java,
                "airports").build()
        }
    }
    return INSTANCE
}