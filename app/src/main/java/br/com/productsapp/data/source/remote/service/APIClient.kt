package br.com.productsapp.data.source.remote.service

import br.com.productsapp.BuildConfig.BASE_URL
import br.com.productsapp.TAG
import br.com.productsapp.data.source.remote.Resource
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import kotlinx.coroutines.*
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.io.IOException
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class APIClient @Inject constructor() {

    val service: APIService by lazy {

        val retrofit = Retrofit.Builder()
            .client(createClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()

        return@lazy retrofit.create(APIService::class.java)
    }

    private fun createClient(): OkHttpClient {

        val logger = HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)

        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)

        return OkHttpClient.Builder()
                .addInterceptor(logger)
                .cookieJar(JavaNetCookieJar(cookieManager))
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .callTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
    }

    suspend fun <T> runAsync(
            call: Call<T>,
            onResponse: (resource: Resource<T>) -> Unit
    ): Resource<T> {
        return this.runAsync(call, onResponse, onResponse)
    }

    suspend fun <T> runAsync(
            call: Call<T>,
            onSuccess: (resource: Resource<T>) -> Unit,
            onFailure: (resource: Resource<T>) -> Unit
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            var result: Resource<T>
            try {
                val response: Response<T> = call.execute()
                when {
                    (response.isSuccessful) -> {
                        result = Resource.success(response)
                        onSuccess(result)
                        return@withContext result
                    }
                    else -> {
                        result = Resource.error(response)
                        onFailure(result)
                        return@withContext result
                    }
                }
            } catch (e: IOException) {
                Timber.tag(TAG).e(e)
                result = Resource.error(e)
                onFailure(result)
                return@withContext result
            }
        }
    }

    fun <T> runJob(
            call: Call<T>,
            onSuccess: (resource: Resource<T>) -> Unit,
            onFailure: (resource: Resource<T>) -> Unit
    ): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: Response<T> = call.execute()
                when {
                    (response.isSuccessful) -> {
                        onSuccess(Resource.success(response))
                    }
                    else -> {
                        onFailure(Resource.error(response))
                    }
                }
            } catch (e: IOException) {
                Timber.tag(TAG).e(e)
                onFailure(Resource.error(e))
            }
        }
    }

    fun <T> runAsyncDeferred(call: Call<T>): Deferred<Resource<T>> {
        return CoroutineScope(Dispatchers.IO).async {
            return@async try {
                val response: Response<T> = call.execute()

                when {
                    (response.isSuccessful) -> {
                        Resource.success(response)
                    }
                    else -> {
                        Resource.error(response)
                    }
                }
            } catch (e: IOException) {
                Timber.tag(TAG).e(e)
                Resource.error<T>(e)
            }
        }
    }

    suspend fun <T> runAsyncDeferredAwaited(call: Call<T>): Resource<T> {
        val deferred = runAsyncDeferred(call)
        return deferred.await()
    }

}