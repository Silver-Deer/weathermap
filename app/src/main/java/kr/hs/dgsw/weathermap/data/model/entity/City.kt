package kr.hs.dgsw.weathermap.data.model.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["city"], unique = true)])
data class City(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val city: String
)
