package com.example.book.screen.home

import android.view.LayoutInflater
import com.example.book.databinding.FragmentHomeBinding
import com.example.book.utils.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeContract.View {

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    override fun initView() {

    }

    override fun initData() {

    }


    companion object {
        fun newInstance() = HomeFragment()
    }
}
