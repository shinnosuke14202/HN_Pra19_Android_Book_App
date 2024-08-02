package com.example.book.screen.search

import android.util.Log
import com.example.book.R
import com.example.book.data.model.Book
import com.example.book.data.repository.BookRepository
import com.example.book.data.repository.OnResultListener
import java.lang.Exception

class SearchPresenter(private val repository: BookRepository) : SearchContract.Presenter {
    private var view: SearchContract.View? = null

    override fun onStart() {}

    override fun onStop() {}

    override fun setView(view: SearchContract.View?) {
        this.view = checkNotNull(view)
    }

    override fun getDataSearchBook(
        textSearch: String,
        typeSearch: Int,
    ) {
        when (typeSearch) {
            R.id.rb_title -> searchTitleBook(textSearch)
            R.id.rb_author -> searchAuthorBook(textSearch)
            R.id.rb_genres -> searchGenresBook(textSearch)
        }
    }

    private fun searchTitleBook(textSearch: String) {
        repository.getBooksByTitle(
            object : OnResultListener<List<Book>> {
                override fun onSuccess(data: List<Book>) {
                    Log.d("SearchPresenter", "searchTitleBook: onSuccess: data = $textSearch")
                    Log.d("SearchPresenter", "searchTitleBook: onSuccess: data = $data")
                    view?.onGetBookSuccess(data)
                }

                override fun onError(exception: Exception) {
                    view?.onError(exception)
                }
            },
            textSearch,
        )
    }

    private fun searchAuthorBook(textSearch: String) {
        repository.getBooksByAuthor(
            object : OnResultListener<List<Book>> {
                override fun onSuccess(data: List<Book>) {
                    view?.onGetBookSuccess(data)
                }

                override fun onError(exception: Exception) {
                    view?.onError(exception)
                }
            },
            textSearch,
        )
    }

    private fun searchGenresBook(textSearch: String) {
        repository.getBooksByGenres(
            object : OnResultListener<List<Book>> {
                override fun onSuccess(data: List<Book>) {
                    view?.onGetBookSuccess(data)
                }

                override fun onError(exception: Exception) {
                    view?.onError(exception)
                }
            },
            textSearch,
        )
    }
}
