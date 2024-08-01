package com.example.book.screen.home

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.book.R
import com.example.book.data.model.Book
import com.example.book.data.repository.BookRepository
import com.example.book.data.repository.source.local.BookLocalDataSourceImpl
import com.example.book.data.repository.source.remote.BookRemoteDataSourceImpl
import com.example.book.databinding.FragmentHomeBinding
import com.example.book.databinding.PopUpLanguagesBinding
import com.example.book.screen.MainActivity
import com.example.book.screen.home.adapter.HomeBooksAdapter
import com.example.book.utils.ENG
import com.example.book.utils.SELECTED_LANGUAGE
import com.example.book.utils.SETTINGS
import com.example.book.utils.VIET
import com.example.book.utils.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeContract.View {
    private lateinit var homePresenter: HomePresenter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var dialog: Dialog
    private var currentLanguage: String? = null
    private val homeBookAdapter: HomeBooksAdapter by lazy { HomeBooksAdapter() }
    private val novelsAdapter: HomeBooksAdapter by lazy { HomeBooksAdapter() }
    private val popUpBinding by lazy {
        PopUpLanguagesBinding.inflate(layoutInflater)
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    override fun initView() {
        sharedPreferences = requireContext().getSharedPreferences(SETTINGS, MODE_PRIVATE)
        currentLanguage = sharedPreferences.getString(SELECTED_LANGUAGE, ENG)
        editor = sharedPreferences.edit()
        viewBinding.apply {
            rvHomeBooks.adapter = homeBookAdapter
            rvHomeBooks.layoutManager = GridLayoutManager(requireContext(), 2)

            rvNovels.adapter = novelsAdapter
            rvNovels.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            ivLanguageFlag.setOnClickListener {
                createLanguagesDialog()
            }
            when (currentLanguage) {
                VIET -> ivLanguageFlag.setImageResource(R.drawable.vn)
                ENG -> ivLanguageFlag.setImageResource(R.drawable.en)
            }
        }
        homeBookAdapter.onClick = {
            // todo
        }
        novelsAdapter.onClick = {
            // todo
        }
    }

    private fun createLanguagesDialog() {
        dialog = Dialog(requireContext())
        dialog.apply {
            setContentView(popUpBinding.root)
            val layoutParams = window?.attributes
            layoutParams?.gravity = Gravity.CENTER
            window?.apply {
                attributes = layoutParams
                setLayout(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                )
            }
        }
        dialog.show()
        changeLanguage()
    }

    private fun changeLanguage() {
        when (sharedPreferences.getString(SELECTED_LANGUAGE, ENG)) {
            ENG -> {
                popUpBinding.ivVietnamTick.visibility = View.GONE
                popUpBinding.tvVietnam.typeface =
                    ResourcesCompat.getFont(requireContext(), R.font.worksans_regular)
                popUpBinding.ivEnglishTick.visibility = View.VISIBLE
                popUpBinding.tvEnglish.typeface =
                    ResourcesCompat.getFont(requireContext(), R.font.worksans_bold)
            }

            VIET -> {
                popUpBinding.ivEnglishTick.visibility = View.GONE
                popUpBinding.tvEnglish.typeface =
                    ResourcesCompat.getFont(requireContext(), R.font.worksans_regular)
                popUpBinding.ivVietnamTick.visibility = View.VISIBLE
                popUpBinding.tvVietnam.typeface =
                    ResourcesCompat.getFont(requireContext(), R.font.worksans_bold)
            }
        }
        popUpBinding.clVietnam.setOnClickListener {
            editor.putString(SELECTED_LANGUAGE, VIET)
            editor.apply()
            refreshApp()
        }
        popUpBinding.clEnglish.setOnClickListener {
            editor.putString(SELECTED_LANGUAGE, ENG)
            editor.apply()
            refreshApp()
        }
    }

    private fun refreshApp() {
        val fragmentManager = activity?.supportFragmentManager
        fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val refresh = Intent(activity, MainActivity::class.java)
        startActivity(refresh)
        activity?.finish()
    }

    override fun initData() {
        homePresenter =
            HomePresenter(
                BookRepository.getInstance(
                    BookRemoteDataSourceImpl.getInstance(),
                    BookLocalDataSourceImpl.getInstance(requireContext()),
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
