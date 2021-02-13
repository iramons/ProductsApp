package br.com.productsapp.data.source.remote.service

import br.com.productsapp.BuildConfig
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class APIService @Inject constructor()  {

        val api : APIClient by lazy {

            val retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(NetworkResponseAdapterFactory())
                    .build()

            return@lazy retrofit.create(APIClient::class.java)
        }

}