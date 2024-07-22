package com.example.book.data.repository

import com.example.book.data.repository.source.BookDataSource

class BookRepository(
    private val remote: BookDataSource.Remote,
    private val local: BookDataSource.Local
) : BookDataSource.Local, BookDataSource.Remote {}
