package kr.hs.dgsw.weathermap.data.data_source

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kr.hs.dgsw.weathermap.data.api.CityDatabase
import kr.hs.dgsw.weathermap.data.model.entity.City
import java.lang.RuntimeException
import java.sql.SQLException

class CityLocalDataSource(private val context: Context) {
    fun getMyCities(): Single<List<City>> {
            return CityDatabase.getInstance(context.applicationContext)?.getDao()?.getMyCities() ?: throw RuntimeException("Null Point Exception")
    }

    fun addCity(s: String): Completable {
        return CityDatabase.getInstance(context.applicationContext)?.getDao()?.insertCity(City(0, s)) ?: throw RuntimeException("Null Point Exception")
    }

    fun deleteCity(city: String): Completable {
         return CityDatabase.getInstance(context.applicationContext)?.getDao()?.deleteCityByName(city) ?: throw RuntimeException("Null Point Exception")
    }

}