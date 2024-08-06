package com.example.book.screen.detail

import com.example.book.data.model.Book
import com.example.book.data.repository.BookRepository
import com.example.book.data.repository.OnResultListener

class DetailPresenter(private val repository: BookRepository) : DetailContract.Presenter {
    private var view: DetailContract.View? = null

    override fun addFavoriteBook(book: Book) {
        repository.addToFavorites(
            book,
            object : OnResultListener<Book> {
                override fun onSuccess(data: Book) {
                    view?.onAddBookToFavorites(book)
                }

                override fun onError(exception: Exception) {
                    view?.onError(exception)
                }
            },
        )
    }

    override fun deleteFavoriteBook(id: Long) {
        repository.deleteFromFavorites(
            id,
            object : OnResultListener<String> {
                override fun onSuccess(data: String) {
                    view?.onDeleteBookFromFavorites(id)
                }

                override fun onError(exception: Exception) {
                    view?.onError(exception)
                }
            },
        )
    }

    override fun checkIfFavorite(id: Long) {
        repository.isFavoriteBook(
            id,
            object : OnResultListener<Boolean> {
                override fun onSuccess(data: Boolean) {
                    view?.onCheckFavoriteBook(data)
                }

                override fun onError(exception: java.lang.Exception) {
                    view?.onError(exception)
                }
            },
        )
    }

    override fun getBookById(bookId: Long) {
        repository.getDetailBook(
            object : OnResultListener<Book> {
                override fun onSuccess(data: Book) {
                    view?.onGetDetailBookSuccess(data)
                }

                override fun onError(exception: Exception) {
                    view?.onError(exception)
                }
            },
            bookId.toString(),
        )
    }

    override fun getListBookSuggestions(
        bookId: Long,
        year: String,
        limit: Int,
    ) {
        repository.getTopBooksByYear(
            object : OnResultListener<List<Book>> {
                override fun onSuccess(data: List<Book>) {
                    view?.onGetSuggestionsBookSuccess(data)
                }

                override fun onError(exception: Exception) {
                    view?.onError(exception)
                }
            },
            year,
            limit,
        )
    }

    override fun onStart() {
        // todo
    }

    override fun onStop() {
        // todo
    }

    override fun setView(view: DetailContract.View?) {
        this.view = view
    }
}
