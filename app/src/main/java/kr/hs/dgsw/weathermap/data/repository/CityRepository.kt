package kr.hs.dgsw.weathermap.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.hs.dgsw.weathermap.data.data_source.CityLocalDataSource
import kr.hs.dgsw.weathermap.data.model.entity.City

class CityRepository(private val context: Context) {
    private val cityLocalDataSource = CityLocalDataSource(context)
    val signal = MutableLiveData<Unit>()
    fun getMyCities(): LiveData<List<City>> {
        cityLocalDataSource.getMyCities(signal)

        return cityLocalDataSource.myCities
    }

    fun addCity(city: String) {
        cityLocalDataSource.addCity(city, signal)
    }

    fun deleteCity(city: String) {
        cityLocalDataSource.deleteCity(city, signal)
    }
}