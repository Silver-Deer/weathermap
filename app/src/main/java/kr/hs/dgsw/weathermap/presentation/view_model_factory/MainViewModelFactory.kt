package kr.hs.dgsw.weathermap.presentation.view_model_factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.weathermap.presentation.view_model.MainViewModel

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(context) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}