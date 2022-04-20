package kr.hs.dgsw.weathermap.presentation.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kr.hs.dgsw.weathermap.data.model.entity.City
import kr.hs.dgsw.weathermap.data.model.entity.Weather
import kr.hs.dgsw.weathermap.data.model.response_model.WeatherResponse
import kr.hs.dgsw.weathermap.data.repository.CityRepository
import kr.hs.dgsw.weathermap.data.repository.WeatherRepository

class MainViewModel(private val context: Context) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val cityRepository = CityRepository(context)
    val weatherRepository = WeatherRepository()

    private val _weatherList = MutableLiveData<List<WeatherResponse>>()
    val weatherList: LiveData<List<WeatherResponse>>
        get() = _weatherList

    private val _isValidCity = MutableLiveData<Boolean>()
    val isValidCity: LiveData<Boolean>
        get() = _isValidCity

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    fun checkAndAddCity(city: String) {
        compositeDisposable.add(
            weatherRepository.getWeather(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _isValidCity.value = true
                    addCity(it.name)
                }, {
                    _isValidCity.value = false
                    _error.value = it
                })
        )

    }

    private fun addCity(cityName: String) {
        compositeDisposable.add(
            cityRepository.addCity(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    refreshCities()
                }, {
                    _error.value = it
                })
        )
    }

    private fun fetchWeatherList(myCities: List<City>) {
        compositeDisposable.add(
            Single.zip(weatherRepository.getWeathers(myCities)) {
                it.filterIsInstance<WeatherResponse>()
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _weatherList.value = it
                }, {
                    _error.value = it
                })
        )
    }

    fun refreshCities() {
        compositeDisposable.add(
            cityRepository.getMyCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    fetchWeatherList(it)
                }, {
                    _error.value = it
                })
        )
    }

    fun deleteCity(cityName: String) {
        compositeDisposable.add(
            cityRepository.deleteCity(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    refreshCities()
                }, {
                    _error.value = it
                })
        )
    }

}