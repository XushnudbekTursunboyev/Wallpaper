package uz.xushnudbek.pixelswallpaper.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.xushnudbek.pixelswallpaper.repository.PhotoRepository
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val fbRepository: PhotoRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoViewModel::class.java)) {
            return PhotoViewModel(fbRepository) as T
        }
        throw IllegalArgumentException("Error")
    }
}