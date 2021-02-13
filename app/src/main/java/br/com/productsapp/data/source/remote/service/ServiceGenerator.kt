package br.com.productsapp.data.source.remote.service

import br.com.productsapp.BuildConfig
import br.com.productsapp.TAG
import br.com.productsapp.data.source.remote.Resource
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.IOException
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("UNREACHABLE_CODE")
@Singleton
class ServiceGenerator @Inject constructor() {

    companion object {
        //Network constants
        const val TIMEOUT_CONNECT = 30 // seconds
        const val TIMEOUT_WRITE = 30 // seconds
        const val TIMEOUT_READ = 30 // seconds
        const val CONTENT_TYPE = "Content-Type"
        const val CONTENT_TYPE_VALUE = "application/json"
        const val AUTH_TYPE = "Authorization"
        const val AUTH_TYPE_VALUE = "Bearer"
    }

    private var retrofit: Retrofit? = null

    fun okhttpClient(): OkHttpClient {

        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)

        val okHttpBuilder = OkHttpClient.Builder()

        okHttpBuilder.cookieJar(JavaNetCookieJar(cookieManager))
        if (BuildConfig.DEBUG) {
            okHttpBuilder.addNetworkInterceptor(StethoInterceptor())
        }
        okHttpBuilder.readTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.writeTimeout(TIMEOUT_WRITE.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.connectTimeout(TIMEOUT_CONNECT.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.retryOnConnectionFailure(true)

        return okHttpBuilder.build()
    }

    fun <S> createService(serviceClass: Class<S>): S {

        val gson: Gson = GsonBuilder().create()

        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.BASE_URL)
            .client(okhttpClient())
            .build()
        return retrofit!!.create(serviceClass)
    }


    /**
     * Util para qualquer comunicacao com servidor
     * OBS: Jamar chamar essa funcao no Dispatchers.Main
     */
    suspend fun <T> runAsync(
        call: Call<T>,
        onResponse: (resource: Resource<T>) -> Unit
    ): Resource<T> {
        return this.runAsync(call, onResponse, onResponse)
    }

    /**
     * Util para qualquer comunicacao com servidor
     * OBS: Jamar chamar essa funcao no Dispatchers.Main
     */
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

    /**
     * Para enviar dados ao servidor
     */
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

    /**
     * Para recuperar dados do servidor
     */
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

    /**
     * Para recuperar dados do servidor
     * 'await' já processado
     */
    suspend fun <T> runAsyncDeferredAwaited(call: Call<T>): Resource<T> {
        val deferred = runAsyncDeferred(call)
        return deferred.await()
    }
}
