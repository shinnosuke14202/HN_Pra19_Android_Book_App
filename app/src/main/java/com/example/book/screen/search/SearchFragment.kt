package com.example.book.screen.search

import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.book.R
import com.example.book.data.model.Book
import com.example.book.data.repository.BookRepository
import com.example.book.data.repository.source.local.BookLocalDataSourceImpl
import com.example.book.data.repository.source.remote.BookRemoteDataSourceImpl
import com.example.book.databinding.FragmentSearchBinding
import com.example.book.screen.detail.DetailFragment
import com.example.book.screen.search.adapter.SearchAdapter
import com.example.book.utils.base.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>(), SearchContract.View {
    private var isSearching = false

    private val presenter: SearchPresenter by lazy {
        SearchPresenter(
            BookRepository.getInstance(
                BookRemoteDataSourceImpl.getInstance(),
                BookLocalDataSourceImpl.getInstance(requireContext()),
            ),
        )
    }

    private val searchAdapter: SearchAdapter by lazy {
        SearchAdapter(listBook = mutableListOf(), onItemClick = this::onItemClicked)
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater)
    }

    override fun initView() {
        presenter.setView(this)
        initSearchView()
        listenSearchView()
    }

    override fun initData() {
        presenter.onStart()
        viewBinding.rvSearch.layoutManager = GridLayoutManager(requireContext(), 3)
        viewBinding.rvSearch.adapter = searchAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onStop()
    }

    fun initSearchView() {
        val searchView = viewBinding.searchView
        val searchText = searchView.findViewById<TextView>(androidx.appcompat.R.id.search_src_text)
        searchText.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.color_search_hint))
        searchText.setTextSize(TypedValue.COMPLEX_UNIT_SP, resources.getDimension(R.dimen.sp_5))
        searchText.typeface = ResourcesCompat.getFont(requireContext(), R.font.montserrat_black_italic)
        searchText.hint = getString(R.string.search_hint)
    }

    fun listenSearchView() {
        viewBinding.searchView.setOnQueryTextListener(
            object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (!isSearching) {
                        isSearching = true
                        var typeSearch = viewBinding.rgSearch.checkedRadioButtonId
                        presenter.getDataSearchBook(query.orEmpty(), typeSearch)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            },
        )
    }

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onGetBookSuccess(book: List<Book>) {
        isSearching = false
        searchAdapter.updateData(book)
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(requireContext(), exception?.message, Toast.LENGTH_SHORT).show()
    }

    fun onItemClicked(book: Book) {
        val context = requireContext()
        if (context is FragmentActivity) {
            val detailFragment = DetailFragment.newInstance(book.id.toInt())
            context.supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, detailFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
