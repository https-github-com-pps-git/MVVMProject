package com.example.mvvmproject.fragment.account

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import com.example.base.fragment.BaseFragment
import com.example.base.fragment.BaseMvvmFragment
import com.example.mvvmproject.R
import com.example.mvvmproject.databinding.AccountFragmentBinding
import com.example.mvvmproject.databinding.HomeFragmentBinding
import com.example.mvvmproject.fragment.account.fragment.AccountDetailFragment
import com.google.android.material.tabs.TabLayout

class AccountFragment : BaseMvvmFragment<AccountFragmentBinding,AccountViewModel>() {

    private var mTabs: List<AccountTabData> = ArrayList()
    private var mFragemnts = mutableListOf<Fragment>()
    companion object{
        fun getInstance(): AccountFragment{
            val mFragment = AccountFragment()
            return mFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.account_fragment
    }

    override fun initViewModel(): AccountViewModel {
        return AccountViewModel()
    }

    override fun initData() {
        super.initData()
        mDataBinding.mTabLayout.tabMode = TabLayout.MODE_AUTO
        if (isVisibleToUser) {
            isFirstCreate = true
            mViewModel?.loadTabData()
        }
        mViewModel?.mStateData?.observe(this, Observer {

        })

        mViewModel?.mDataBean?.observe(this, Observer {
            mTabs = it.data
            initViewpager()
        })
    }

    private fun initViewpager() {
        //创建fragment
        for (i in mTabs.indices){
            mFragemnts.add(AccountDetailFragment.getInstance(mTabs[i].id))
        }

        mDataBinding.mViewpager.adapter = object : FragmentPagerAdapter(childFragmentManager){
            override fun getItem(position: Int): Fragment {
                return mFragemnts[position]
            }

            override fun getCount(): Int {
                return mFragemnts.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return mTabs[position].name
            }

        }

        mDataBinding.mTabLayout.setupWithViewPager(mDataBinding.mViewpager)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && !isFirstCreate){
            isFirstCreate = true
            mViewModel?.loadTabData()
        }
    }
}