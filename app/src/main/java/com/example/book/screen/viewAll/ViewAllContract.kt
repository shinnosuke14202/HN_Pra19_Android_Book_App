package com.example.book.screen.viewAll

import com.example.book.data.model.Book
import com.example.book.utils.base.BasePresenter

interface ViewAllContract {
    interface Presenter : BasePresenter<View> {
        fun getPopularBooks(
            year: String,
            number: Int,
        )

        fun getNovels(genre: String)
    }

    interface View {
        fun onGetBooksSuccess(books: List<Book>)

        fun onError(exception: Exception)
    }
}
