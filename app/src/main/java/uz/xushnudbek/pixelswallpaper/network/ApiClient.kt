package uz.xushnudbek.pixelswallpaper.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.xushnudbek.pixelswallpaper.utils.constants.CONSTANTS.BASE_URL

object ApiClient {

    private var retrofit:Retrofit? = null

    fun apiService():ApiService{
        val client = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization","563492ad6f91700001000001e72a01726482470080fa34c18490c1e7")
                    .build()
                chain.proceed(request)
            })
            .build()

        if (retrofit == null){
            retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build()
        }

        return retrofit!!.create(ApiService::class.java)
    }

}
