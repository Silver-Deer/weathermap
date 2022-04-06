package kr.hs.dgsw.weathermap.data.data_source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kr.hs.dgsw.weathermap.data.api.WeatherClient
import kr.hs.dgsw.weathermap.data.model.entity.City
import kr.hs.dgsw.weathermap.data.model.response_model.ErrorResponse
import kr.hs.dgsw.weathermap.data.model.response_model.WeatherResponse
import retrofit2.HttpException

class WeatherRemoteDataSource {
    private val _weatherResponse = MutableLiveData<WeatherResponse>()
    val weatherResponse: LiveData<WeatherResponse>
        get() = _weatherResponse

    private val _weatherResponseList = MutableLiveData<ArrayList<WeatherResponse>>()
    private val __weatherResponseList = MutableLiveData<List<WeatherResponse>>()
    val weatherResponseList: LiveData<List<WeatherResponse>>
        get() = __weatherResponseList

    private val _isValidCity = MutableLiveData<Boolean>()
    val isValidCity: LiveData<Boolean>
        get() = _isValidCity

    fun resetList() {
        _weatherResponseList.value = ArrayList()
    }

    private val compositeDisposable = CompositeDisposable()

    fun getWeather(city: String) {
        try {
            compositeDisposable.add(
                    WeatherClient.getClient().getWeather(city)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    {
                                        _weatherResponse.value = it
                                        _isValidCity.value = true
                                    },
                                    {
                                        if (it is HttpException) {
                                            val error =
                                                    WeatherClient.retrofit.responseBodyConverter<ErrorResponse>(
                                                            ErrorResponse::class.java,
                                                            ErrorResponse::class.java.annotations
                                                    ).convert(it.response()?.errorBody())
                                            Log.d("Weather Error", "${error?.cod}:${error?.message}")
                                            _isValidCity.value = false
                                        } else Log.d("Weather Error", "${it.message}")
                                    }
                            )
            )
        } catch (e: Exception) {
            Log.d("Weather Error", "${e.message}")
        }
    }

    fun getWeatherList(cities: List<City>) {
        cities.forEach { city ->
            try {
                compositeDisposable.add(
                        WeatherClient.getClient().getWeather(city.city)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        {
                                            it.id = city.id
                                            _weatherResponseList.value?.add(it)
                                            __weatherResponseList.value = _weatherResponseList.value?.toList()
                                        },
                                        {
                                            if (it is HttpException) {
                                                val error =
                                                        WeatherClient.retrofit.responseBodyConverter<ErrorResponse>(
                                                                ErrorResponse::class.java,
                                                                ErrorResponse::class.java.annotations
                                                        ).convert(it.response()?.errorBody())
                                                Log.d("Weather Error", "${error?.cod}:${error?.message}")
                                            } else Log.d("Weather Error", "${it.message}")
                                        }
                                )
                )
            } catch (e: Exception) {
            }

        }
    }

    fun resetValidValue() {
        _isValidCity.value = null
    }


}