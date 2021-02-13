package br.com.productsapp.data.source.remote

import android.content.Context
import br.com.productsapp.R
import br.com.productsapp.commom.extensions.Okhttp3Extensions.toErrorResult
import br.com.productsapp.commom.extensions.isNotNullOrEmpty
import br.com.productsapp.data.model.ErrorResponse
import br.com.productsapp.data.model.JsonModel
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val stringResource: Int? = null,
    val stringMessage: String? = null,
    val code: Int? = 0,
    val errorResult: ErrorResponse? = null
): JsonModel() {

    constructor(status: Status, response: Response<T>?) : this(
        status = status,
        data = response?.body(),
        code = response?.code(),
        stringMessage = response?.message(),
        errorResult = response?.toErrorResult()
    )

    val isSuccessful: Boolean
        get() = code in 200..299

    val hasDefault : Boolean
        get() = (status == Status.DEFAULT)

    val hasLoading : Boolean
        get() = (status == Status.LOADING)

    val hasSuccess : Boolean
        get() = (status == Status.SUCCESS)

    val hasEmpty : Boolean
        get() = (status == Status.EMPTY)

    val hasError : Boolean
        get() = (status == Status.ERROR)

    fun message(context: Context?): String {
        stringMessage?.let {
            if ((hasLoading || hasDefault) && stringMessage.isNotNullOrEmpty())
                return stringMessage
        }
        var msg: String? = null

        if (errorResult != null) {
            msg = errorResult.message
            if (msg.isNotNullOrEmpty()) {
                return msg!!
            }
        }
        if (context != null) {
            var errCode = this.code ?: 0

            if (hasEmpty) {
                return context.resources.getString(messageByCode(NO_DATA_FOUND))
            }
            this.data.let {
                if (it == null)
                    errCode = NO_DATA_FOUND
                else if (it  == "[]") {
                    errCode = NO_DATA_FOUND
                } else if (it is Array<*>) {
                    val dataAsArray = (it as? Array<*>)?.size
                    if (dataAsArray == 0)
                        errCode = NO_DATA_FOUND
                }
            }
            msg = if (errCode > 0 && errCode != 200)
                context.resources.getString(messageByCode(errCode))
            else
                this.stringMessage
        }
        return msg ?: ""
    }

    companion object {

        const val NO_INTERNET_CONNECTION = 1
        const val NO_DATA_FOUND = 2

        fun messageByCode(code: Int?): Int {
            return when(code) {
                NO_INTERNET_CONNECTION -> R.string.error_no_internet_connection
                NO_DATA_FOUND -> R.string.http_code_204
                202 -> R.string.http_code_202
                201 -> R.string.http_code_201
                204 -> R.string.http_code_204
                401 -> R.string.http_code_401
                404 -> R.string.http_code_404
                500 -> R.string.http_code_500
                10012 -> R.string.error_default
                else -> R.string.error_default
            }
        }

        fun messageByThrowable(throwable: Throwable?): Int {
            return when (throwable) {
                null -> R.string.error_default
                is IOException -> R.string.error_no_internet_connection
                is SocketTimeoutException -> R.string.error_timeout_connection
                else -> R.string.error_default
            }
        }

        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null,
                null
            )
        }

        fun <T> success(response: Response<T>): Resource<T> {
            return Resource(
                status = Status.SUCCESS,
                response = response
            )
        }

        fun <T> empty(response: Response<T>): Resource<T> {
            return Resource(
                status = Status.EMPTY,
                response = response
            )
        }

        fun <T> error(response: Response<T>): Resource<T> {
            return Resource(
                status = Status.ERROR,
                response = response
            )
        }

        fun <T> error(throwable: Throwable, data: T?): Resource<T> {
            val msg = messageByThrowable(throwable)
            return Resource(
                Status.ERROR,
                data,
                msg,
                null
            )
        }

        fun <T> error(exception: Exception?, data: T? = null): Resource<T> {
            val msg = messageByThrowable(exception)
            return Resource(
                Status.ERROR,
                data,
                msg,
                null
            )
        }

        fun <T> error(errorCode: Int, data: T? = null): Resource<T> {
            val msg = messageByCode(errorCode)
            return Resource(
                Status.ERROR,
                data,
                msg,
                null,
                errorCode
            )
        }

        fun <T> error(message: String? = null, data: T? = null, code: Int? = 0): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                null,
                message,
                code
            )
        }

        fun <T> loading(message: String? = null, data: T? = null): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null,
                message
            )
        }
    }
}