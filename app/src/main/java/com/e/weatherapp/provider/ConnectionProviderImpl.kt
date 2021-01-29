package com.e.weatherapp.provider

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.annotation.StringDef
import javax.inject.Inject

class ConnectionProviderImpl @Inject constructor(connectivityManager: ConnectivityManager) :
    ConnectionProvider {

    private var netWorkStateHolder = NetWorkStateHolder(UNAVAILABLE)

    private val networkStateObject = object : ConnectivityManager.NetworkCallback() {

        //Called when a network disconnects or otherwise no longer satisfies this request or callback.
        override fun onLost(network: Network) {
            super.onLost(network)
            netWorkStateHolder = NetWorkStateHolder(LOST)
        }

        // Called if no network is found.
        override fun onUnavailable() {
            super.onUnavailable()
            netWorkStateHolder = NetWorkStateHolder(UNAVAILABLE)
        }

        //Called when the framework connects and has declared a new network ready for use.
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            netWorkStateHolder = NetWorkStateHolder(CONNECTED)
        }
    }

    init {
        val netWorkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        connectivityManager.registerNetworkCallback(netWorkRequest, networkStateObject)
    }

    override fun isConnected(): Boolean {
        return netWorkStateHolder.state == CONNECTED
    }
}

private class NetWorkStateHolder(
    @NetworkState val state: String
)

@Retention(AnnotationRetention.SOURCE)
@StringDef(LOST, CONNECTED, UNAVAILABLE)
annotation class NetworkState

// Network state
const val LOST = "LOST"
const val CONNECTED = "CONNECTED"
const val UNAVAILABLE = "UNAVAILABLE"