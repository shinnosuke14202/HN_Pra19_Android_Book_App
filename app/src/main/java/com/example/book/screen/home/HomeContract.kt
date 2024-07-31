package com.example.book.screen.home

import com.example.book.data.model.Book
import com.example.book.utils.base.BasePresenter

interface HomeContract {
    interface Presenter : BasePresenter<View> {
        fun getPopularBooks(
            year: String,
            number: Int,
        )

        fun getNovels(genres: String)
    }

    interface View {
        fun onGetPopularBooksSuccess(books: List<Book>)

        fun onError(exception: Exception)

        fun onGetNovelsSuccess(books: List<Book>)

        fun onGetNovelsError(exception: Exception)
    }
}
