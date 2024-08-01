package com.example.book.data.repository

import com.example.book.data.model.Book
import com.example.book.data.repository.source.BookDataSource

class BookRepository(
    private val remote: BookDataSource.Remote,
    private val local: BookDataSource.Local,
) : BookDataSource.Local, BookDataSource.Remote {
    /**
     * Remote
     */
    override fun getBooksByTitle(
        listener: OnResultListener<List<Book>>,
        title: String,
    ) {
        remote.getBooksByTitle(listener, title)
    }

    override fun getBooksByAuthor(
        listener: OnResultListener<List<Book>>,
        author: String,
    ) {
        remote.getBooksByAuthor(listener, author)
    }

    override fun getBooksByGenres(
        listener: OnResultListener<List<Book>>,
        genres: String,
    ) {
        remote.getBooksByGenres(listener, genres)
    }

    override fun getTopBooksByYear(
        listener: OnResultListener<List<Book>>,
        year: String,
        number: Int,
    ) {
        remote.getTopBooksByYear(listener, year, number)
    }

    override fun getSimilarBooks(
        listener: OnResultListener<List<Book>>,
        id: String,
    ) {
        remote.getSimilarBooks(listener, id)
    }

    override fun getDetailBook(
        listener: OnResultListener<Book>,
        id: String,
    ) {
        remote.getDetailBook(listener, id)
    }

    override fun sortBooksByRating(
        listener: OnResultListener<List<Book>>,
        title: String,
    ) {
        remote.sortBooksByRating(listener, title)
    }

    override fun sortBooksByYear(
        listener: OnResultListener<List<Book>>,
        year: String,
    ) {
        remote.sortBooksByYear(listener, year)
    }

    override fun sortBooksByRatingAndYear(
        listener: OnResultListener<List<Book>>,
        title: String,
        year: String,
    ) {
        remote.sortBooksByRatingAndYear(listener, title, year)
    }

    /**
     * Local
     */
    override fun getFavorites(listener: OnResultListener<List<Book>>) {
        local.getFavorites(listener)
    }

    override fun addToFavorites(
        book: Book,
        listener: OnResultListener<Book>,
    ) {
        local.addToFavorites(book, listener)
    }

    override fun deleteFromFavorites(
        id: Long,
        listener: OnResultListener<String>,
    ) {
        local.deleteFromFavorites(id, listener)
    }

    override fun getAllBooks(listener: OnResultListener<List<Book>>) {
        local.getAllBooks(listener)
    }

    override fun deleteAllBooks(listener: OnResultListener<String>) {
        local.deleteAllBooks(listener)
    }

    override fun addBooks(
        books: List<Book>,
        listener: OnResultListener<String>,
    ) {
        local.addBooks(books, listener)
    }

    companion object {
        private var instance: BookRepository? = null

        fun getInstance(
            remote: BookDataSource.Remote,
            local: BookDataSource.Local,
        ) = synchronized(this) {
            instance ?: BookRepository(remote, local).also { instance = it }
        }
    }
}
