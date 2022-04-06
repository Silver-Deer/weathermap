package kr.hs.dgsw.weathermap.utils

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.hs.dgsw.weathermap.presentation.view.MainWeatherAdapter

object DataBindingAdapters {
    @JvmStatic
    @BindingAdapter("adapter")
    fun setAdapter(recyclerView: RecyclerView, adapter: MainWeatherAdapter) {
        recyclerView.adapter = adapter
    }

}