package kr.hs.dgsw.weathermap.data.api

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kr.hs.dgsw.weathermap.data.model.entity.City

@Database(entities = [City::class], version = 2)
abstract class CityDatabase : RoomDatabase() {

    abstract fun getDao(): CityDao

    companion object {
        private var INSTANCE: CityDatabase? = null

        fun getInstance(context: Context): CityDatabase? {
            if (INSTANCE == null) {
                synchronized(CityDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        CityDatabase::class.java,
                        "CityDB"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }

    fun destroyInstance() {
        INSTANCE = null
    }
}