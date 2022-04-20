package kr.hs.dgsw.weathermap.presentation.view

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
            binding.root.findViewById<TextView>(R.id.tv_item_main_temp).text = this.itemView.resources.getString(R.string.temp, temp)
            binding.root.findViewById<TextView>(R.id.tv_item_current_weather).text = weather.weather[0].description
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