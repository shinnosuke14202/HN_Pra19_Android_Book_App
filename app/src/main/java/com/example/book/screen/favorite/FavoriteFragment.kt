package com.example.book.screen.favorite

import android.view.LayoutInflater
import android.widget.Toast
import com.example.book.data.model.Book
import com.example.book.databinding.FragmentFavoriteBinding
import com.example.book.screen.favorite.adapter.FavoriteAdapter
import com.example.book.utils.base.BaseFragment
import java.lang.Exception

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(), FavoriteContract.View {
    private val presenter: FavoritePresenter by lazy { FavoritePresenter() }
    private val mBookAdapter: FavoriteAdapter by lazy { FavoriteAdapter() }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentFavoriteBinding {
        return FragmentFavoriteBinding.inflate(inflater)
    }

    override fun initView() {
        viewBinding.rvFavoriteBook.adapter = mBookAdapter
        presenter.setView(this)
    }

    override fun initData() {
        presenter.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onStop()
    }

    override fun onGetBooksSuccess(books: List<Book>) {
        mBookAdapter.submitList(books)
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(requireContext(), exception?.message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = FavoriteFragment()
    }
}
