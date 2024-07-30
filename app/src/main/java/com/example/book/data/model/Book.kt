package com.example.book.data.model

data class Book(
    var id: Long = 0L,
    var title: String = "",
    var author: String = "",
    var description: String = "",
    var image: String = "",
    var rating: Double = 0.0,
)
