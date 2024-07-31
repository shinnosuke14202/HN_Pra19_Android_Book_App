package com.example.book.screen

import androidx.fragment.app.Fragment
import com.example.book.R
import com.example.book.screen.favorite.FavoriteFragment
import com.example.book.screen.home.HomeFragment
import com.example.book.utils.base.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity() {
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        setNextFragment(HomeFragment.newInstance())
        setNavigation()
    }

    private fun setNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.miHome -> setNextFragment(HomeFragment.newInstance())
                R.id.miFavorite -> setNextFragment(FavoriteFragment.newInstance())
            }
            true
        }
    }

    private fun setNextFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(fragment::javaClass.name)
            .replace(R.id.flContainer, fragment)
            .commit()
    }
}
