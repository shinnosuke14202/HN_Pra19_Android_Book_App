package com.example.book.data.repository.source.remote.fetchJson

import com.example.book.data.model.Book
import com.example.book.utils.AUTHORS
import com.example.book.utils.AUTHOR_NAME
import com.example.book.utils.AVERAGE
import com.example.book.utils.DESCRIPTION
import com.example.book.utils.ID
import com.example.book.utils.IMAGE
import com.example.book.utils.RATING
import com.example.book.utils.TITLE
import org.json.JSONArray
import org.json.JSONObject

class ParseJson {
    fun bookParseJson(jsonObject: JSONObject) = Book().apply {
        jsonObject.let {
            id = it.getLong(ID)
            title = it.getString(TITLE)
            image = it.getString(IMAGE)
            if (it.has(AUTHORS)) {
                author =
                    getDataFromJsonArray(it.getJSONArray(AUTHORS), AUTHOR_NAME)
            }
            if (it.has(RATING)) {
                rating = it.getJSONObject(RATING).getString(AVERAGE)
                    .toDouble() * 10.0
            }
            if (it.has(DESCRIPTION)) {
                description = it.getString(DESCRIPTION)
            }
        }
    }

    private fun getDataFromJsonArray(jsonArray: JSONArray, keyEntity: String): String {
        val data = StringBuilder()
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            data.append(obj.getString(keyEntity) + ", ")
        }
        return data.substring(0, data.length - 2)
    }
}
