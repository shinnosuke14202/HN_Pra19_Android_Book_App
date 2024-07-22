package com.example.book.data.repository.source.local

import com.example.book.data.model.Book
import com.example.book.data.repository.OnResultListener
import com.example.book.data.repository.source.BookDataSource

class BookLocalDataSourceImpl : BookDataSource.Local {
    override fun getFavorites(listener: OnResultListener<List<Book>>) {
        TODO("Not yet implemented")
    }

    override fun addToFavorites(listener: OnResultListener<Book>) {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorites(listener: OnResultListener<String>) {
        TODO("Not yet implemented")
    }

    companion object {
        private var instance: BookLocalDataSourceImpl? = null

        fun getInstance() = synchronized(this) {
            instance ?: BookLocalDataSourceImpl().also { instance = it }
        }
    }
}
