package kr.hs.dgsw.weathermap.data.data_source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kr.hs.dgsw.weathermap.data.api.WeatherClient
import kr.hs.dgsw.weathermap.data.model.entity.City
import kr.hs.dgsw.weathermap.data.model.response_model.ErrorResponse
import kr.hs.dgsw.weathermap.data.model.response_model.WeatherResponse
import retrofit2.HttpException

class WeatherRemoteDataSource {
    fun getWeather(city: String): Single<WeatherResponse> {
        return WeatherClient.getClient().getWeather(city)
    }

    fun getWeatherList(cities: List<City>): List<Single<WeatherResponse>> {
        return cities.map { city -> WeatherClient.getClient().getWeather(city.city) }
    }

}