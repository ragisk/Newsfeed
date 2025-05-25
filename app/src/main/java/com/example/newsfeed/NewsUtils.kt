package com.example.newsfeed

import android.content.Context
import android.net.ConnectivityManager

class NewsUtils {

    companion object{
        const val apiKey = "pub_5b30a022b0264b578ed92b5d770b4d90"

        fun isInternetAvailable(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }
}