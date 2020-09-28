package com.example.mvvmproject.fragment.account

import androidx.fragment.app.Fragment
import com.example.base.fragment.BaseFragment
import com.example.mvvmproject.R
import com.example.mvvmproject.databinding.AccountFragmentBinding
import com.example.mvvmproject.databinding.HomeFragmentBinding

class AccountFragment : BaseFragment<AccountFragmentBinding>() {

    companion object{
        fun getInstance(): AccountFragment{
            val mFragment = AccountFragment()
            return mFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.account_fragment
    }


}