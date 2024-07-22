package com.example.book.data.repository.source.remote.fetchJson

import android.util.Log
import com.example.book.data.model.Book
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ParseDataWithJson {
    fun parseJsonToListData(jsonObject: JSONObject?, keyEntity: String): List<Book> {
        val data = mutableListOf<Book>()
        try {
            val jsonArray = jsonObject?.getJSONArray(keyEntity)
            for (i in 0 until (jsonArray?.length() ?: 0)) {
                val item = jsonArray?.get(i)
                if (item is JSONArray) {
                    for (j in 0 until item.length()) {
                        val bookJson = item.getJSONObject(j)
                        val book = parseJsonToObject(bookJson)
                        book?.let {
                            data.add(it)
                        }
                    }
                } else if (item is JSONObject) {
                    val book = parseJsonToObject(item)
                    book?.let {
                        data.add(it)
                    }
                }
            }
        } catch (e: JSONException) {
            Log.e("ParseDataWithJson", "parseJsonToListData: ", e)
        }
        return data
    }

    fun parseJsonToDetailData(jsonObject: JSONObject?): Book? {
        var data: Book? = null
        try {
            data = parseJsonToObject(jsonObject)
        } catch (e: JSONException) {
            Log.e("ParseDataWithJson", "parseJsonDetailData: ", e)
        }
        return data
    }

    private fun parseJsonToObject(jsonObject: JSONObject?): Book? {
        try {
            jsonObject?.let {
                return ParseJson().bookParseJson(it)
            }
        } catch (e: JSONException) {
            Log.e("ParseDataWithJson", "parseJsonToData: ", e)
        }
        return null
    }
}
