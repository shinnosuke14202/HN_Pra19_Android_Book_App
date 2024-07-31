package com.example.book.screen.home

import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.book.data.model.Book
import com.example.book.data.repository.BookRepository
import com.example.book.data.repository.source.local.BookLocalDataSourceImpl
import com.example.book.data.repository.source.remote.BookRemoteDataSourceImpl
import com.example.book.databinding.FragmentHomeBinding
import com.example.book.screen.home.adapter.HomeBooksAdapter
import com.example.book.utils.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeContract.View {
    private lateinit var homePresenter: HomePresenter
    private val homeBookAdapter: HomeBooksAdapter by lazy { HomeBooksAdapter() }
    private val novelsAdapter: HomeBooksAdapter by lazy { HomeBooksAdapter() }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    override fun initView() {
        viewBinding.apply {
            rvHomeBooks.apply {
                adapter = homeBookAdapter
                layoutManager = GridLayoutManager(requireContext(), 2)
            }
            rvNovels.apply {
                adapter = novelsAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }
        homeBookAdapter.onClick = {
            // todo
        }
        novelsAdapter.onClick = {
            // todo
        }
    }

    override fun initData() {
        homePresenter =
            HomePresenter(
                BookRepository.getInstance(
                    BookRemoteDataSourceImpl.getInstance(),
                    BookLocalDataSourceImpl.getInstance(),
                ),
            )
        homePresenter.setView(this)
        homePresenter.getPopularBooks("2022", 4)
        homePresenter.getNovels("novel")
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onGetPopularBooksSuccess(books: List<Book>) {
        homeBookAdapter.updateData(books)
    }

    override fun onError(exception: Exception) {
        Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
    }

    override fun onGetNovelsSuccess(books: List<Book>) {
        novelsAdapter.updateData(books)
    }

    override fun onGetNovelsError(exception: Exception) {
        Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
    }
}
