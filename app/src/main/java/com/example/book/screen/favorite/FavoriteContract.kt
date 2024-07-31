package com.example.book.screen.favorite

import com.example.book.data.model.Book
import com.example.book.utils.base.BasePresenter

interface FavoriteContract {
    interface Presenter : BasePresenter<View>

    interface View {
        fun onGetBooksSuccess(books: List<Book>)

        fun onError(exception: Exception?)
    }
}
