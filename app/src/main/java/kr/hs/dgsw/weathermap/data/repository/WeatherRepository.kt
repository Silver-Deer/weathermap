package kr.hs.dgsw.weathermap.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import kr.hs.dgsw.weathermap.data.data_source.WeatherRemoteDataSource
import kr.hs.dgsw.weathermap.data.model.entity.City
import kr.hs.dgsw.weathermap.data.model.response_model.WeatherResponse

class WeatherRepository {
    private val weatherRemoteDataSource = WeatherRemoteDataSource()

    fun getWeather(city: String): Single<WeatherResponse> {
        return weatherRemoteDataSource.getWeather(city)
    }

    fun getWeathers(cities: List<City>): List<Single<WeatherResponse>> {
        return weatherRemoteDataSource.getWeatherList(cities)
    }

}