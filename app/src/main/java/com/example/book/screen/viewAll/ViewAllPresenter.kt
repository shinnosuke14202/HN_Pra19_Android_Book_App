package com.example.book.screen.viewAll

import com.example.book.data.model.Book
import com.example.book.data.repository.BookRepository
import com.example.book.data.repository.OnResultListener

class ViewAllPresenter(private val repository: BookRepository) : ViewAllContract.Presenter {
    private var view: ViewAllContract.View? = null

    override fun getPopularBooks(
        year: String,
        number: Int,
    ) {
        repository.getTopBooksByYear(
            object : OnResultListener<List<Book>> {
                override fun onSuccess(data: List<Book>) {
                    view?.onGetBooksSuccess(data)
                }

                override fun onError(exception: Exception) {
                    view?.onError(exception)
                }
            },
            year,
            number,
        )
    }

    override fun getNovels(genre: String) {
        repository.getBooksByGenres(
            object : OnResultListener<List<Book>> {
                override fun onSuccess(data: List<Book>) {
                    view?.onGetBooksSuccess(data)
                }

                override fun onError(exception: java.lang.Exception) {
                    view?.onError(exception)
                }
            },
            genre,
        )
    }

    override fun onStart() {
        // todo
    }

    override fun onStop() {
        // todo
    }

    override fun setView(view: ViewAllContract.View?) {
        this.view = view
    }
}
