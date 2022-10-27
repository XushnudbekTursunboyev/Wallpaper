package uz.xushnudbek.pixelswallpaper.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.xushnudbek.pixelswallpaper.network.models.PhotosApi

interface ApiService {

    @GET("/v1/search/?")
    suspend fun getPhotoList(
        @Query("page") page: String,
        @Query("per_page") per_page: String,
        @Query("query") query: String
    ): Response<PhotosApi>

}