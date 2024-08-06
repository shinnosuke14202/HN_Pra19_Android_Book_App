package com.example.book.screen.detail

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.book.R
import com.example.book.data.model.Book
import com.example.book.data.repository.BookRepository
import com.example.book.data.repository.source.local.BookLocalDataSourceImpl
import com.example.book.data.repository.source.local.database.DatabaseHelper
import com.example.book.data.repository.source.remote.BookRemoteDataSourceImpl
import com.example.book.databinding.FragmentDetailBinding
import com.example.book.screen.search.adapter.DetailAdapter
import com.example.book.utils.LIMIT
import com.example.book.utils.YEAR
import com.example.book.utils.base.BaseFragment

class DetailFragment : BaseFragment<FragmentDetailBinding>(), DetailContract.View {
    private var book = Book()
    private var checkFavorite = false
    private var bookId = 0
    private var db: DatabaseHelper? = null

    private val detailAdapter: DetailAdapter by lazy {
        DetailAdapter(listBook = mutableListOf(), onItemClick = this::onItemClick)
    }

    private val presenter: DetailPresenter by lazy {
        DetailPresenter(
            BookRepository.getInstance(
                BookRemoteDataSourceImpl.getInstance(),
                BookLocalDataSourceImpl.getInstance(requireContext()),
            ),
        )
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onStop()
    }

    override fun initView() {
        presenter.setView(this)
        lestenBack()
        listenFavorite(bookId.toLong())
        viewBinding.rvSearch.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        viewBinding.rvSearch.adapter = detailAdapter
    }

    private fun lestenBack() {
        viewBinding.imgBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    override fun initData() {
        bookId = arguments?.getInt("BOOK_ID") ?: 0
        checkFavorite(bookId.toLong())
        listenFavorite(bookId.toLong())
        presenter.getBookById(bookId.toLong())
        presenter.getListBookSuggestions(bookId.toLong(), YEAR, LIMIT)
    }

    fun listenFavorite(id: Long) {
        viewBinding.icFavorite.setOnClickListener {
            if (checkFavorite) {
                presenter.deleteFavoriteBook(id)
            } else {
                presenter.addFavoriteBook(book)
            }
        }
    }

    fun checkFavorite(id: Long) {
        presenter.checkIfFavorite(id)
    }

    companion object {
        fun newInstance(bookId: Int) =
            DetailFragment().apply {
                arguments =
                    Bundle().apply {
                        putInt("BOOK_ID", bookId)
                    }
            }
    }

    override fun onAddBookToFavorites(book: Book) {
        viewBinding.icFavorite.setImageResource(R.drawable.ic_favorite_red)
        checkFavorite = true
    }

    override fun onDeleteBookFromFavorites(id: Long) {
        viewBinding.icFavorite.setImageResource(R.drawable.ic_favorite_black)
        checkFavorite = false
    }

    override fun onCheckFavoriteBook(isFavorite: Boolean) {
        if (isFavorite) {
            viewBinding.icFavorite.setImageResource(R.drawable.ic_favorite_red)
        } else {
            viewBinding.icFavorite.setImageResource(R.drawable.ic_favorite_black)
        }
    }

    override fun onGetDetailBookSuccess(book: Book) {
        this.book = book
        viewBinding.tvTitle.text = book.title
        viewBinding.tvAuthor.text = book.author
        viewBinding.tvDescription.text = book.description
        viewBinding.tvRating.text = String.format("%.2f", book.rating)
        Glide.with(viewBinding.root.context)
            .load(book.image)
            .into(viewBinding.imgBookDetail)
    }

    override fun onGetSuggestionsBookSuccess(books: List<Book>) {
        detailAdapter.updateData(books)
    }

    override fun onError(exception: Exception?) {
    }

    fun onItemClick(book: Book) {
        val context = requireContext()
        if (context is FragmentActivity) {
            val detailFragment = newInstance(book.id.toInt())
            context.supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, detailFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
