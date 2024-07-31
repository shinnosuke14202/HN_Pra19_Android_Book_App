package com.example.book.screen.home

import com.example.book.data.model.Book
import com.example.book.data.repository.BookRepository
import com.example.book.data.repository.OnResultListener
import java.lang.Exception

class HomePresenter(private val repository: BookRepository) : HomeContract.Presenter {
    private var view: HomeContract.View? = null

    override fun onStart() {
        // todo
    }

    override fun onStop() {
        // todo
    }

    override fun setView(view: HomeContract.View?) {
        this.view = view
    }

    override fun getPopularBooks(
        year: String,
        number: Int,
    ) {
        repository.getTopBooksByYear(
            object : OnResultListener<List<Book>> {
                override fun onSuccess(data: List<Book>) {
                    view?.onGetPopularBooksSuccess(data)
                }

                override fun onError(exception: Exception) {
                    view?.onError(exception)
                }
            },
            year,
            number,
        )
    }
}
