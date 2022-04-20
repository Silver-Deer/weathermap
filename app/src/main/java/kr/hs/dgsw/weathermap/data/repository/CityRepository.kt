package kr.hs.dgsw.weathermap.data.repository

import android.content.Context
import io.reactivex.Completable
import io.reactivex.Single
import kr.hs.dgsw.weathermap.data.data_source.CityLocalDataSource
import kr.hs.dgsw.weathermap.data.model.entity.City

class CityRepository(private val context: Context) {
    private val cityLocalDataSource = CityLocalDataSource(context)
    fun getMyCities(): Single<List<City>> {
        return cityLocalDataSource.getMyCities()
    }

    fun addCity(city: String): Completable {
        return cityLocalDataSource.addCity(city)
    }

    fun deleteCity(city: String): Completable {
        return cityLocalDataSource.deleteCity(city)
    }
}