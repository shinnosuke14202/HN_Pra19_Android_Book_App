package com.example.book.screen.viewAll

import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.book.data.model.Book
import com.example.book.data.repository.BookRepository
import com.example.book.data.repository.source.local.BookLocalDataSourceImpl
import com.example.book.data.repository.source.remote.BookRemoteDataSourceImpl
import com.example.book.databinding.FragmentViewAllBinding
import com.example.book.screen.home.adapter.HomeBooksAdapter
import com.example.book.utils.NOVELS
import com.example.book.utils.POPULAR
import com.example.book.utils.PUBLISH_DATE
import com.example.book.utils.RATING
import com.example.book.utils.TYPE
import com.example.book.utils.base.BaseFragment

class ViewAllFragment : BaseFragment<FragmentViewAllBinding>(), ViewAllContract.View {
    private lateinit var viewAllPresenter: ViewAllPresenter
    private val booksAdapter: HomeBooksAdapter by lazy { HomeBooksAdapter() }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentViewAllBinding {
        return FragmentViewAllBinding.inflate(inflater)
    }

    override fun initView() {
        viewBinding.apply {
            rvDisplayBooks.apply {
                adapter = booksAdapter
                layoutManager = GridLayoutManager(requireContext(), 2)
            }
            imageView.setOnClickListener {
                activity?.supportFragmentManager?.popBackStack()
            }
        }
        booksAdapter.onClick = {
        }
    }

    override fun initData() {
        val type = arguments?.getString(TYPE)
        viewAllPresenter =
            ViewAllPresenter(
                BookRepository.getInstance(
                    BookRemoteDataSourceImpl.getInstance(),
                    BookLocalDataSourceImpl.getInstance(requireContext()),
                ),
            )
        viewAllPresenter.setView(this)
        when (type) {
            POPULAR -> viewAllPresenter.getPopularBooks("2022", 20)
            NOVELS -> viewAllPresenter.getNovels("novels")
        }
        viewBinding.btnSortByYear.setOnClickListener {
            when (type) {
                POPULAR -> viewAllPresenter.getPopularBooks("2022&sort=$PUBLISH_DATE", 20)
                NOVELS -> viewAllPresenter.getNovels("novels&sort=$PUBLISH_DATE")
            }
        }
        viewBinding.btnSortByRating.setOnClickListener {
            when (type) {
                POPULAR -> viewAllPresenter.getPopularBooks("2022&sort=$RATING", 20)
                NOVELS -> viewAllPresenter.getNovels("novels&sort=$RATING")
            }
        }
    }

    override fun onGetBooksSuccess(books: List<Book>) {
        booksAdapter.updateData(books)
    }

    override fun onError(exception: Exception) {
        Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = ViewAllFragment()
    }
}
