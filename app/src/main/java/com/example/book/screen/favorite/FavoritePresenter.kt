package com.example.book.screen.favorite

import com.example.book.data.model.Book

class FavoritePresenter : FavoriteContract.Presenter {
    private var view: FavoriteContract.View? = null

    override fun onStart() {
        val books = getDummyBooks()
        view?.onGetBooksSuccess(books)
    }

    override fun onStop() {
    }

    override fun setView(view: FavoriteContract.View?) {
        this.view = checkNotNull(view)
    }

    private fun getDummyBooks(): List<Book> {
        val books =
            listOf(
                Book(
                    1,
                    "Title",
                    "Author",
                    "Description",
                    "https://www.wilsoncenter.org/sites/default/files/media/images/person/james-person-1.jpg",
                    4.5,
                ),
                Book(
                    2,
                    "Title",
                    "Author",
                    "Description",
                    "https://www.wilsoncenter.org/sites/default/files/media/images/person/james-person-1.jpg",
                    4.5,
                ),
                Book(
                    3,
                    "Title",
                    "Author",
                    "Description",
                    "https://www.wilsoncenter.org/sites/default/files/media/images/person/james-person-1.jpg",
                    4.5,
                ),
                Book(
                    4,
                    "Title",
                    "Author",
                    "Description",
                    "https://www.wilsoncenter.org/sites/default/files/media/images/person/james-person-1.jpg",
                    4.5,
                ),
                Book(
                    5,
                    "Title",
                    "Author",
                    "Description",
                    "https://www.wilsoncenter.org/sites/default/files/media/images/person/james-person-1.jpg",
                    4.5,
                ),
                Book(
                    6,
                    "Title",
                    "Author",
                    "Description",
                    "https://www.wilsoncenter.org/sites/default/files/media/images/person/james-person-1.jpg",
                    4.5,
                ),
                Book(
                    7,
                    "Title",
                    "Author",
                    "Description",
                    "https://www.wilsoncenter.org/sites/default/files/media/images/person/james-person-1.jpg",
                    4.5,
                ),
                Book(
                    8,
                    "Title",
                    "Author",
                    "Description",
                    "https://www.wilsoncenter.org/sites/default/files/media/images/person/james-person-1.jpg",
                    4.5,
                ),
                Book(
                    9,
                    "Title",
                    "Author",
                    "Description",
                    "https://www.wilsoncenter.org/sites/default/files/media/images/person/james-person-1.jpg",
                    4.5,
                ),
                Book(
                    10,
                    "Title",
                    "Author",
                    "Description",
                    "https://www.wilsoncenter.org/sites/default/files/media/images/person/james-person-1.jpg",
                    4.5,
                ),
            )
        return books
    }
}
