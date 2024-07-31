package com.example.book.screen.home

import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.book.data.model.Author
import com.example.book.data.model.Book
import com.example.book.data.repository.BookRepository
import com.example.book.data.repository.source.local.BookLocalDataSourceImpl
import com.example.book.data.repository.source.remote.BookRemoteDataSourceImpl
import com.example.book.databinding.FragmentHomeBinding
import com.example.book.screen.home.adapter.HomeAuthorsAdapter
import com.example.book.screen.home.adapter.HomeBooksAdapter
import com.example.book.utils.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeContract.View {
    private lateinit var homePresenter: HomePresenter
    private val homeBookAdapter: HomeBooksAdapter by lazy { HomeBooksAdapter() }
    private val homeAuthorAdapter: HomeAuthorsAdapter by lazy { HomeAuthorsAdapter() }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    override fun initView() {
        viewBinding.apply {
            rvHomeBooks.apply {
                adapter = homeBookAdapter
                layoutManager = GridLayoutManager(requireContext(), 2)
            }
            rvHomeAuthors.apply {
                adapter = homeAuthorAdapter
            }
        }
        homeBookAdapter.onClick = {
            // todo
        }
        homeAuthorAdapter.onClick = {
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
        homeAuthorAdapter.updateData(authorsData())
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

    private fun authorsData(): List<Author> {
        val authors =
            listOf(
                Author(
                    name = "J. K. Rowling",
                    image =
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5d/J._K._Rowling_2010.jpg/" +
                            "330px-J._K._Rowling_2010.jpg",
                ),
                Author(
                    name = "George R.R. Martin",
                    image = "https://cdn.britannica.com/05/223205-050-8931FF28/American-writer-George-RR-Martin-2011.jpg",
                ),
                Author(
                    name = "James Clear",
                    image =
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/1/17/James_Clear_in_2010.jpg/" +
                            "330px-James_Clear_in_2010.jpg",
                ),
            )
        return authors
    }
}
