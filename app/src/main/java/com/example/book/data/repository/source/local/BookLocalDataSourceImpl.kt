package com.example.book.data.repository.source.local

import android.content.Context
import com.example.book.data.model.Book
import com.example.book.data.repository.OnResultListener
import com.example.book.data.repository.source.BookDataSource
import com.example.book.data.repository.source.local.database.DatabaseHelper

class BookLocalDataSourceImpl(private val dbHelper: DatabaseHelper) : BookDataSource.Local {
    override fun getFavorites(listener: OnResultListener<List<Book>>) {
        val books = dbHelper.getAllFavorites()
        listener.onSuccess(books)
    }

    override fun addToFavorites(
        book: Book,
        listener: OnResultListener<Book>,
    ) {
        try {
            dbHelper.addFavorite(book)
            listener.onSuccess(book)
        } catch (e: Exception) {
            listener.onError(e)
        }
    }

    override fun deleteFromFavorites(
        id: Long,
        listener: OnResultListener<String>,
    ) {
        try {
            dbHelper.deleteFavorite(id)
            listener.onSuccess("Deleted successfully")
        } catch (e: Exception) {
            listener.onError(e)
        }
    }

    override fun getAllBooks(listener: OnResultListener<List<Book>>) {
        val books = dbHelper.getAllBooks()
        listener.onSuccess(books)
    }

    override fun deleteAllBooks(listener: OnResultListener<String>) {
        try {
            dbHelper.deleteAllBooks()
            listener.onSuccess("All books deleted")
        } catch (e: Exception) {
            listener.onError(e)
        }
    }

    override fun addBooks(
        books: List<Book>,
        listener: OnResultListener<String>,
    ) {
        try {
            dbHelper.addBooks(books)
            listener.onSuccess("Books added successfully")
        } catch (e: Exception) {
            listener.onError(e)
        }
    }

    companion object {
        private var instance: BookLocalDataSourceImpl? = null

        fun getInstance(context: Context) =
            synchronized(this) {
                instance ?: BookLocalDataSourceImpl(DatabaseHelper(context)).also { instance = it }
            }
    }
}
