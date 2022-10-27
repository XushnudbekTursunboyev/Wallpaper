package uz.xushnudbek.pixelswallpaper.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.xushnudbek.pixelswallpaper.network.models.PhotosApi
import uz.xushnudbek.pixelswallpaper.repository.PhotoRepository
import uz.xushnudbek.pixelswallpaper.utils.network.Resource

class PhotoViewModel(
    private val photoRepository: PhotoRepository,
) : ViewModel() {

    private val photoList = MutableLiveData<Resource<PhotosApi>>()

    init {
        getPhotosList("All")
    }

    private fun getPhotosList(ct:String):LiveData<Resource<PhotosApi>> {
        viewModelScope.launch {
            photoList.postValue(Resource.loading(null))
                val remotePhoto = photoRepository.getPhotoList(ct)
                if (remotePhoto.isSuccessful)
                    photoList.value = Resource.success(remotePhoto.body())
                else
                    photoList.postValue(Resource.error(remotePhoto.errorBody()?.string() ?: "", null))
            }
        return photoList
        }

   fun getPhotos(ct:String) = getPhotosList(ct)

}