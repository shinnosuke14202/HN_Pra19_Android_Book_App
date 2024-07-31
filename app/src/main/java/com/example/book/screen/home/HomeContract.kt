package com.example.book.screen.home

import com.example.book.data.model.Book
import com.example.book.utils.base.BasePresenter

interface HomeContract {
    interface Presenter : BasePresenter<View> {
        fun getPopularBooks(
            year: String,
            number: Int,
        )
    }

    interface View {
        fun onGetPopularBooksSuccess(books: List<Book>)

        fun onError(exception: Exception)
    }
}
