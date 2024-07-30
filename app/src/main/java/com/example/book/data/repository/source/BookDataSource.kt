package com.example.book.data.repository.source

import com.example.book.data.model.Book
import com.example.book.data.repository.OnResultListener

interface BookDataSource {
    /**
     * Local
     */
    interface Local {
        fun getFavorites(listener: OnResultListener<List<Book>>)

        fun addToFavorites(listener: OnResultListener<Book>)

        fun deleteFromFavorites(listener: OnResultListener<String>)
    }

    /**
     * Remote
     */
    interface Remote {
        fun getBooksByTitle(
            listener: OnResultListener<List<Book>>,
            title: String,
        )

        fun getBooksByAuthor(
            listener: OnResultListener<List<Book>>,
            author: String,
        )

        fun getBooksByGenres(
            listener: OnResultListener<List<Book>>,
            genres: String,
        )

        fun getTopBooksByYear(
            listener: OnResultListener<List<Book>>,
            year: String,
            number: Int,
        )

        fun getSimilarBooks(
            listener: OnResultListener<List<Book>>,
            id: String,
        )

        fun getDetailBook(
            listener: OnResultListener<Book>,
            id: String,
        )

        fun sortBooksByRating(
            listener: OnResultListener<List<Book>>,
            title: String,
        )

        fun sortBooksByYear(
            listener: OnResultListener<List<Book>>,
            year: String,
        )

        fun sortBooksByRatingAndYear(
            listener: OnResultListener<List<Book>>,
            title: String,
            year: String,
        )
    }
}
