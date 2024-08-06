package com.example.book.data.repository.source.remote

import com.example.book.data.model.Book
import com.example.book.data.repository.OnResultListener
import com.example.book.data.repository.source.BookDataSource
import com.example.book.data.repository.source.remote.fetchJson.GetJsonFromUrl
import com.example.book.data.repository.source.remote.fetchJson.ParseDataWithJson
import com.example.book.utils.BASE_SEARCH
import com.example.book.utils.BASE_URL
import com.example.book.utils.BOOKS
import com.example.book.utils.SIMILAR_BOOKS
import org.json.JSONObject

class BookRemoteDataSourceImpl : BookDataSource.Remote {
    override fun getBooksByTitle(
        listener: OnResultListener<List<Book>>,
        title: String,
    ) {
        GetJsonFromUrl.getInstance(
            urlString = BASE_SEARCH + "query=$title",
            keyEntity = BOOKS,
            listener = listener,
            getDataFromJson = { response, keyEntity ->
                ParseDataWithJson().parseJsonToListData(JSONObject(response), keyEntity)
            },
        ).getBooks(true)
    }

    override fun getBooksByAuthor(
        listener: OnResultListener<List<Book>>,
        author: String,
    ) {
        GetJsonFromUrl.getInstance(
            urlString = BASE_SEARCH + "authors$author",
            keyEntity = BOOKS,
            listener = listener,
            getDataFromJson = { response, keyEntity ->
                ParseDataWithJson().parseJsonToListData(JSONObject(response), keyEntity)
            },
        ).getBooks(true)
    }

    override fun getBooksByGenres(
        listener: OnResultListener<List<Book>>,
        genres: String,
    ) {
        GetJsonFromUrl.getInstance(
            urlString = BASE_SEARCH + "genres=$genres",
            keyEntity = BOOKS,
            listener = listener,
            getDataFromJson = { response, keyEntity ->
                ParseDataWithJson().parseJsonToListData(JSONObject(response), keyEntity)
            },
        ).getBooks(true)
    }

    override fun getTopBooksByYear(
        listener: OnResultListener<List<Book>>,
        year: String,
        number: Int,
    ) {
        GetJsonFromUrl.getInstance(
            urlString = BASE_SEARCH + "earliest-publish-year=$year" + "&number=$number",
            keyEntity = BOOKS,
            listener = listener,
            getDataFromJson = { response, keyEntity ->
                ParseDataWithJson().parseJsonToListData(JSONObject(response), keyEntity)
            },
        ).getBooks(true)
    }

    override fun getSimilarBooks(
        listener: OnResultListener<List<Book>>,
        id: String,
    ) {
        GetJsonFromUrl.getInstance(
            urlString = "$BASE_URL$id/similar",
            keyEntity = SIMILAR_BOOKS,
            listener = listener,
            getDataFromJson = { response, keyEntity ->
                ParseDataWithJson().parseJsonToListData(JSONObject(response), keyEntity)
            },
        ).getBooks(false)
    }

    override fun getDetailBook(
        listener: OnResultListener<Book>,
        id: String,
    ) {
        GetJsonFromUrl.getInstance(
            urlString = BASE_URL + id,
            keyEntity = BOOKS,
            listener = listener,
            getDataFromJson = { response, _ ->
                ParseDataWithJson().parseJsonToDetailData(JSONObject(response))
            },
        ).getDetailBook()
    }

    companion object {
        private var instance: BookRemoteDataSourceImpl? = null

        fun getInstance() =
            synchronized(this) {
                instance ?: BookRemoteDataSourceImpl().also { instance = it }
            }
    }
}
