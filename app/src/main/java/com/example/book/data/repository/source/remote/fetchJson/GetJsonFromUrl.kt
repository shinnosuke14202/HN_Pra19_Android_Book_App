package com.example.book.data.repository.source.remote.fetchJson

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.book.data.repository.OnResultListener
import com.example.book.utils.BASE_API_KEY
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class GetJsonFromUrl<T>(
    private val urlString: String,
    private val keyEntity: String,
    private val listener: OnResultListener<T>,
    private val getDataFromJson: (String, String) -> T?,
) {
    private val executor: Executor = Executors.newSingleThreadExecutor()
    private val mainHandler = Handler(Looper.getMainLooper())
    private var data: T? = null

    fun getBooks(haveParams: Boolean) {
        executor.execute {
            val responseJson =
                getJsonStringFromUrl(urlString + (if (haveParams) "&" else "?") + "api-key=$BASE_API_KEY")
            data = getDataFromJson(responseJson, keyEntity)
            mainHandler.post {
                data?.let { listener.onSuccess(it) }
            }
        }
    }

    fun getDetailBook() {
        executor.execute {
            val responseJson =
                getJsonStringFromUrl("$urlString?api-key=$BASE_API_KEY")
            data = getDataFromJson(responseJson, keyEntity)
            mainHandler.post {
                data?.let { listener.onSuccess(it) }
            }
        }
    }

    private fun getJsonStringFromUrl(urlString: String): String {
        val url = URL(urlString)
        val httpURLConnection = url.openConnection() as? HttpURLConnection
        httpURLConnection?.run {
            connectTimeout = TIME_OUT
            readTimeout = TIME_OUT
            requestMethod = METHOD_GET
            doOutput = true
            connect()
        }

        val bufferedReader = BufferedReader(InputStreamReader(url.openStream()))
        val stringBuilder = StringBuilder()
        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            stringBuilder.append(line)
        }
        bufferedReader.close()
        httpURLConnection?.disconnect()
        return stringBuilder.toString()
    }

    companion object {
        private const val TIME_OUT = 15000
        private const val METHOD_GET = "GET"
        private val instanceMap = mutableMapOf<String, GetJsonFromUrl<*>>()

        fun <T> getInstance(
            urlString: String,
            keyEntity: String,
            listener: OnResultListener<T>,
            getDataFromJson: (String, String) -> T?,
        ): GetJsonFromUrl<T> {
            val key = "$urlString-$keyEntity-$listener"
            Log.i("key", key)
            return instanceMap.getOrPut(key) {
                GetJsonFromUrl(urlString, keyEntity, listener, getDataFromJson)
            } as GetJsonFromUrl<T>
        }
    }
}
