package com.example.book.screen.search

import com.example.book.data.model.Book
import com.example.book.utils.base.BasePresenter

interface SearchContract {
    interface Presenter : BasePresenter<View> {
        fun getDataSearchBook(
            textSearch: String,
            typeSearch: Int,
        )
    }

    interface View {
        fun onGetBookSuccess(book: List<Book>)

        fun onError(exception: Exception?)
    }
}
