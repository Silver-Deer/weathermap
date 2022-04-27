package kr.hs.dgsw.weathermap.presentation.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.hs.dgsw.weathermap.R
import kr.hs.dgsw.weathermap.data.model.response_model.WeatherResponse
import kr.hs.dgsw.weathermap.databinding.ItemWeatherBinding
import kotlin.math.roundToInt

class MainWeatherAdapter : ListAdapter<WeatherResponse, MainWeatherAdapter.WeatherViewHolder>(WeatherDiffUtilCallback) {
    class WeatherViewHolder(private val binding: ItemWeatherBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            val clickEvent = MutableLiveData<WeatherResponse>()
        }

        fun bind(weather: WeatherResponse) {
            binding.weather = weather
            val temp = (((weather.main.temp - 273.15) * 100).roundToInt() / 100F).toString()
            binding.tvItemMainTemp.text = this.itemView.resources.getString(R.string.temp, temp)
            binding.tvItemCurrentWeather.text = weather.weather[0].description
            val value = ((weather.main.temp - 273.15) * 100).roundToInt() / 100F
            val color = when {
                value >= 30 -> "#964130"
                value < 30 && value >= 25 -> "#ccbb66"
                value < 25 && value >= 15 -> "#9bc9b8"
                value < 15 && value >= 0 -> "#9bb8c9"
                value < 0 -> "#ffffff"
                else -> "#0xF0F0F0"
            }
            binding.layoutCard.setBackgroundColor(Color.parseColor(color))
            binding.root.setOnLongClickListener {
                clickEvent.value = weather
                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = DataBindingUtil.inflate<ItemWeatherBinding>(LayoutInflater.from(parent.context), R.layout.item_weather, parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object WeatherDiffUtilCallback : DiffUtil.ItemCallback<WeatherResponse>() {
    override fun areItemsTheSame(oldItem: WeatherResponse, newItem: WeatherResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WeatherResponse, newItem: WeatherResponse): Boolean {
        return oldItem == newItem
    }

}