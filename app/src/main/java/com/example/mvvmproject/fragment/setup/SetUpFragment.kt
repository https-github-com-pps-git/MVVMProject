package com.example.mvvmproject.fragment.setup

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.base.fragment.BaseFragment
import com.example.base.fragment.BaseMvvmFragment
import com.example.mvvmproject.R
import com.example.mvvmproject.adapter.StateUpAdapter
import com.example.mvvmproject.databinding.HomeFragmentBinding
import com.example.mvvmproject.databinding.MineFragmentBinding
import com.example.mvvmproject.databinding.SetupFragmentBinding
import java.util.ArrayList

class SetUpFragment : BaseMvvmFragment<SetupFragmentBinding,SetUpViewModel>() {

    private var mAdapter: StateUpAdapter? = null
    private var mDatas: List<SetUpData> = ArrayList()
    companion object{
        fun getInstance(): SetUpFragment{
            val mFragment = SetUpFragment()
            return mFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.setup_fragment
    }

    override fun initViewModel(): SetUpViewModel {
        return SetUpViewModel()
    }

    override fun initData() {
        super.initData()
        initAdapter()

        if (isVisibleToUser) {
            isFirstCreate = true
            mViewModel?.loadSetUpData()
        }
        mViewModel?.mStateData?.observe(this, Observer {

        })

        mViewModel?.mDataBean?.observe(this, Observer {
            mDatas = it.data as MutableList<SetUpData>
            mAdapter?.setList(mDatas)
        })
    }

    private fun initAdapter() {
        mAdapter = StateUpAdapter(mContext)
        mAdapter?.setList(mDatas)
        mDataBinding.mRecycler.layoutManager = LinearLayoutManager(mContext)
        mDataBinding.mRecycler.adapter = mAdapter
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && !isFirstCreate){
            isFirstCreate = true
            mViewModel?.loadSetUpData()
        }
    }
}