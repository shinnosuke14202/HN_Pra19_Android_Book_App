package com.example.book.screen.favorite

import com.example.book.data.model.Book
import com.example.book.utils.base.BasePresenter

interface FavoriteContract {
    interface Presenter : BasePresenter<View> {
        fun loadFavoriteBooks()

        fun addFavoriteBook(book: Book)

        fun deleteFavoriteBook(id: Long)

        fun checkIfFavorite(id: Long)
    }

    interface View {
        fun onGetBooksSuccess(books: List<Book>)

        fun onAddBookToFavorites(book: Book)

        fun onDeleteBookFromFavorites(id: Long)

        fun onCheckFavoriteBook(isFavorite: Boolean)

        fun onError(exception: Exception?)
    }
}
