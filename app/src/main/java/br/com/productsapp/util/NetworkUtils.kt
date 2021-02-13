package br.com.productsapp.util

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

open class NetworkUtils @Inject constructor(private val appContext: Context) {

    open fun getConnectivityManager(): ConnectivityManager {
        return appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    open fun hasNetworkConnection(): Boolean {
        return getConnectivityManager().hasNetworkConnection()
    }

    open fun ConnectivityManager.hasNetworkConnection(): Boolean {
        val activeNetworkInfo = this.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
}