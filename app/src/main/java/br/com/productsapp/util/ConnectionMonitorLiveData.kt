package br.com.productsapp.util

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import br.com.productsapp.commom.extensions.isAtLeastLollipop
import br.com.productsapp.commom.extensions.isAtLeastNougat
import br.com.productsapp.commom.mvvm.SingleLiveEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectionMonitorLiveData @Inject constructor(private val appContext: Context) : SingleLiveEvent<Boolean>() {

    internal var networkUtils : NetworkUtils = NetworkUtils(appContext)

    private var connectivityManager: ConnectivityManager = networkUtils.getConnectivityManager()
    private lateinit var networkCallback : NetworkCallback


    init {

        if (isAtLeastLollipop()) {
            networkCallback = NetworkCallback(this)
        }
    }

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            updateConnectionByIntent(intent)
        }
    }

    override fun onActive() {
        super.onActive()
        when {
            isAtLeastNougat()
            -> connectivityManager.registerDefaultNetworkCallback(networkCallback)
            isAtLeastLollipop()
            -> lollipopNetworkAvailableRequest()
            else
            -> appContext.registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)) // android.net.ConnectivityManager.CONNECTIVITY_ACTION
        }
        updateConnection()
    }

    override fun onInactive() {
        super.onInactive()
        if (isAtLeastLollipop()) {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } else {
            appContext.unregisterReceiver(networkReceiver)
        }
    }

    private fun updateConnection() {
        val isConnected: Boolean = networkUtils.hasNetworkConnection()
        postValue(isConnected)
    }

    private fun updateConnectionByIntent(intent: Intent) {
        val disconnected = intent.getBooleanExtra(
            ConnectivityManager.EXTRA_NO_CONNECTIVITY, false
        )
        postValue(!disconnected)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private val lollipopNetworkTypes = arrayOf(
        NetworkCapabilities.TRANSPORT_CELLULAR,
        NetworkCapabilities.TRANSPORT_WIFI
    )

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun lollipopNetworkAvailableRequest() {
        val builder = NetworkRequest.Builder()
        lollipopNetworkTypes.forEach {
            builder.addTransportType(it)
        }
        connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    class NetworkCallback(val liveData : ConnectionMonitorLiveData) : ConnectivityManager.NetworkCallback() {
        private fun changeState(state: Boolean) {
            if (state != liveData.value) {
                liveData.postValue(state)
            }
        }
        override fun onAvailable(network: Network) {
            changeState(liveData.networkUtils.hasNetworkConnection())
        }
        override fun onLost(network: Network) {
            changeState(liveData.networkUtils.hasNetworkConnection())
        }
    }
}