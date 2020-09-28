package com.example.mvvmproject.fragment.setup

import androidx.fragment.app.Fragment
import com.example.base.fragment.BaseFragment
import com.example.mvvmproject.R
import com.example.mvvmproject.databinding.HomeFragmentBinding
import com.example.mvvmproject.databinding.MineFragmentBinding
import com.example.mvvmproject.databinding.SetupFragmentBinding

class SetUpFragment : BaseFragment<SetupFragmentBinding>() {

    companion object{
        fun getInstance(): SetUpFragment{
            val mFragment = SetUpFragment()
            return mFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.setup_fragment
    }


}