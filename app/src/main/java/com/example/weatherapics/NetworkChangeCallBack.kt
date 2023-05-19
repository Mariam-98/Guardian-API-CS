package com.example.weatherapics

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.widget.Toast

class NetworkChangeCallBack(val context: Context): ConnectivityManager.NetworkCallback() {

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        Toast.makeText(context, "Internet connected", Toast.LENGTH_SHORT).show()
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()

    }
}