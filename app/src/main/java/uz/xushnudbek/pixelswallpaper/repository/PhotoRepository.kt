package uz.xushnudbek.pixelswallpaper.repository

import uz.xushnudbek.pixelswallpaper.network.ApiService
import uz.xushnudbek.pixelswallpaper.utils.constants.CONSTANTS.API_KEY

class PhotoRepository(private val apiService: ApiService) {

    suspend fun getPhotoList(ct:String) = apiService.getPhotoList(
        "5",
        "40",
        ct
    )
}