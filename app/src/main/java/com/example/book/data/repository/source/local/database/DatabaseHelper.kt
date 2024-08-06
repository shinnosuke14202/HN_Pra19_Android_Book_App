package com.example.book.data.repository.source.local.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.book.data.model.Book

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "favorites.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_FAVORITE = "favorites"
        const val TABLE_BOOKS = "books"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_IMAGE = "image"
        const val COLUMN_RATING = "rating"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createFavoritesTable = (
            "CREATE TABLE $TABLE_FAVORITE (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," + "$COLUMN_TITLE TEXT," + "$COLUMN_AUTHOR TEXT," +
                "$COLUMN_DESCRIPTION TEXT," + "$COLUMN_IMAGE TEXT," + "$COLUMN_RATING REAL)"
        )
        db.execSQL(createFavoritesTable)

        val createBooksTable = (
            "CREATE TABLE $TABLE_BOOKS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," + "$COLUMN_TITLE TEXT," + "$COLUMN_AUTHOR TEXT," +
                "$COLUMN_DESCRIPTION TEXT," + "$COLUMN_IMAGE TEXT," + "$COLUMN_RATING REAL)"
        )
        db.execSQL(createBooksTable)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FAVORITE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BOOKS")
        onCreate(db)
    }

    fun addFavorite(book: Book) {
        val db = this.writableDatabase
        val values =
            ContentValues().apply {
                put(COLUMN_ID, book.id)
                put(COLUMN_TITLE, book.title)
                put(COLUMN_AUTHOR, book.author)
                put(COLUMN_DESCRIPTION, book.description)
                put(COLUMN_IMAGE, book.image)
                put(COLUMN_RATING, book.rating)
            }
        db.insert(TABLE_FAVORITE, null, values)
        db.close()
    }

    fun deleteFavorite(id: Long) {
        val db = this.writableDatabase
        db.delete(TABLE_FAVORITE, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
    }

    fun getAllFavorites(): List<Book> {
        val favoritesList = mutableListOf<Book>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_FAVORITE", null)
        if (cursor.moveToFirst()) {
            do {
                val book =
                    Book(
                        id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                        author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR)),
                        description =
                            cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                    COLUMN_DESCRIPTION,
                                ),
                            ),
                        image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)),
                        rating = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_RATING)),
                    )
                favoritesList.add(book)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return favoritesList
    }

    fun addBooks(books: List<Book>) {
        val db = this.writableDatabase
        db.beginTransaction()
        try {
            for (book in books) {
                val values =
                    ContentValues().apply {
                        put(COLUMN_ID, book.id)
                        put(COLUMN_TITLE, book.title)
                        put(COLUMN_AUTHOR, book.author)
                        put(COLUMN_DESCRIPTION, book.description)
                        put(COLUMN_IMAGE, book.image)
                        put(COLUMN_RATING, book.rating)
                    }
                db.insert(TABLE_BOOKS, null, values)
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
            db.close()
        }
    }

    fun deleteAllBooks() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_BOOKS")
        db.close()
    }

    fun getAllBooks(): List<Book> {
        val booksList = mutableListOf<Book>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_BOOKS", null)
        if (cursor.moveToFirst()) {
            do {
                val book =
                    Book(
                        id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                        author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR)),
                        description =
                            cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                    COLUMN_DESCRIPTION,
                                ),
                            ),
                        image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)),
                        rating = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_RATING)),
                    )
                booksList.add(book)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return booksList
    }

    fun isFavorite(id: Long): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_FAVORITE WHERE $COLUMN_ID = ?", arrayOf(id.toString()))
        val isFavorite = cursor.count > 0
        cursor.close()
        db.close()
        return isFavorite
    }
}
