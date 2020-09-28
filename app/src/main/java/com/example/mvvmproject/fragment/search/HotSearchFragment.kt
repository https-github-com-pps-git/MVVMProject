package com.example.mvvmproject.fragment.search

import androidx.fragment.app.Fragment
import com.example.base.fragment.BaseFragment
import com.example.mvvmproject.R
import com.example.mvvmproject.databinding.HomeFragmentBinding
import com.example.mvvmproject.databinding.HotSearchFragmentBinding
import com.example.mvvmproject.databinding.MineFragmentBinding

class HotSearchFragment : BaseFragment<HotSearchFragmentBinding>() {

    companion object{
        fun getInstance(): HotSearchFragment{
            val mFragment = HotSearchFragment()
            return mFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.hot_search_fragment
    }


}