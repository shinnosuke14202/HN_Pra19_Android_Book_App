package com.example.book.screen.favorite

import android.view.LayoutInflater
import android.widget.Toast
import com.example.book.R
import com.example.book.data.model.Book
import com.example.book.data.repository.BookRepository
import com.example.book.data.repository.source.local.BookLocalDataSourceImpl
import com.example.book.data.repository.source.remote.BookRemoteDataSourceImpl
import com.example.book.databinding.FragmentFavoriteBinding
import com.example.book.screen.favorite.adapter.FavoriteAdapter
import com.example.book.screen.favorite.adapter.OnClickFavoriteBook
import com.example.book.utils.base.BaseFragment
import java.lang.Exception

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(), FavoriteContract.View {
    private val presenter: FavoritePresenter by lazy {
        FavoritePresenter(
            BookRepository.getInstance(
                BookRemoteDataSourceImpl.getInstance(),
                BookLocalDataSourceImpl.getInstance(requireContext()),
            ),
        )
    }
    private val mBookAdapter: FavoriteAdapter by lazy { FavoriteAdapter() }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentFavoriteBinding {
        return FragmentFavoriteBinding.inflate(inflater)
    }

    override fun initView() {
        viewBinding.rvFavoriteBook.adapter = mBookAdapter
        presenter.setView(this)

        mBookAdapter.setOnClick(
            object : OnClickFavoriteBook {
                override fun onClickUnMarkdown(book: Book) {
                    presenter.deleteFavoriteBook(book.id)
                }

                override fun onClickToDetailBook(book: Book) {
                    // to do: navigate to detail screen
                }
            },
        )
    }

    override fun initData() {
        presenter.onStart()
        presenter.loadFavoriteBooks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onStop()
    }

    override fun onGetBooksSuccess(books: List<Book>) {
        mBookAdapter.submitList(books)
    }

    override fun onAddBookToFavorites(book: Book) {
        Toast.makeText(requireContext(), "Added ${book.title} to favorites", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onDeleteBookFromFavorites(id: Long) {
        Toast.makeText(requireContext(), "Delete $id from favorites", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onCheckFavoriteBook(isFavorite: Boolean) {
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(
            requireContext(),
            exception?.message ?: getString(R.string.error_occurred),
            Toast.LENGTH_SHORT,
        ).show()
    }

    companion object {
        fun newInstance() = FavoriteFragment()
    }
}
