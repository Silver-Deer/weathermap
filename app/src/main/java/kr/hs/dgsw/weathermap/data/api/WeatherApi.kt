package kr.hs.dgsw.weathermap.data.api

import io.reactivex.Single
import kr.hs.dgsw.weathermap.data.model.response_model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    fun getWeather(@Query("q") city: String): Single<WeatherResponse>
}
