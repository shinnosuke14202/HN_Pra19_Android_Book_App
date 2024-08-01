package com.example.book.screen

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.example.book.R
import com.example.book.databinding.ActivityMainBinding
import com.example.book.screen.favorite.FavoriteFragment
import com.example.book.screen.home.HomeFragment
import com.example.book.utils.ENG
import com.example.book.utils.SELECTED_LANGUAGE
import com.example.book.utils.SETTINGS
import com.example.book.utils.base.BaseActivity
import java.util.Locale

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun inflateBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        setNextFragment(HomeFragment.newInstance())
        setNavigation()
        sharedPreferences = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
        val language = sharedPreferences.getString(SELECTED_LANGUAGE, ENG)
        if (language != null) {
            setLocale(language)
        }
    }

    private fun setLocale(localeName: String) {
        val locale = Locale(localeName)
        val config = Configuration(baseContext.resources.configuration)
        val currentLocale = config.locales.get(0)

        if (currentLocale.language != locale.language) {
            Locale.setDefault(locale)
            config.setLocale(locale)
            baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
            sharedPreferences.edit()?.putString(SELECTED_LANGUAGE, localeName)?.apply()
            recreate()
        }
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
