package com.example.book.screen.detail

import com.example.book.data.model.Book
import com.example.book.utils.base.BasePresenter

interface DetailContract {
    interface Presenter : BasePresenter<View> {
        fun addFavoriteBook(book: Book)

        fun deleteFavoriteBook(id: Long)

        fun checkIfFavorite(id: Long)

        fun getBookById(bookId: Long)

        fun getListBookSuggestions(
            bookId: Long,
            year: String,
            limit: Int,
        )
    }

    interface View {
        fun onAddBookToFavorites(book: Book)

        fun onDeleteBookFromFavorites(id: Long)

        fun onCheckFavoriteBook(isFavorite: Boolean)

        fun onGetDetailBookSuccess(book: Book)

        fun onGetSuggestionsBookSuccess(books: List<Book>)

        fun onError(exception: Exception?)
    }
}
