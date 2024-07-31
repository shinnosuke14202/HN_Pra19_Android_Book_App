package com.example.book.screen

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.example.book.R
import com.example.book.databinding.ActivityMainBinding
import com.example.book.screen.favorite.FavoriteFragment
import com.example.book.screen.home.HomeFragment
import com.example.book.utils.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun inflateBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        setNextFragment(HomeFragment.newInstance())
        setNavigation()
    }

    private fun setNavigation() {
        viewBinding.bottomNavigation.setOnItemSelectedListener {
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
