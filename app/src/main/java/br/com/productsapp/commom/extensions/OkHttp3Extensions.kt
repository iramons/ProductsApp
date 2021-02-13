package br.com.productsapp.commom.extensions

import br.com.productsapp.data.model.ErrorResponse
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Response

object Okhttp3Extensions {

    fun <T> Response<T>?.toErrorResult(): ErrorResponse? {
        if (this == null)
            return this

        return this.errorBody()?.let {
            it.toErrorResult()
        }
    }

    fun ResponseBody?.toErrorResult(): ErrorResponse? {
        if (this == null)
            return this

        return Gson().fromJson(this.charStream(), ErrorResponse::class.java)
    }
}