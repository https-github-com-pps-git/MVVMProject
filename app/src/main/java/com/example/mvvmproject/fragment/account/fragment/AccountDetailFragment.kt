package com.example.mvvmproject.fragment.account.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.base.constant.Const
import com.example.base.fragment.BaseFragment
import com.example.base.fragment.BaseMvvmFragment
import com.example.base.listener.EndlessRecyclerOnScrollListener
import com.example.base.listener.IOnItemClickListener
import com.example.mvvmproject.R
import com.example.mvvmproject.activity.webview.WebViewActivity
import com.example.mvvmproject.adapter.AccountDetailAdapter
import com.example.mvvmproject.databinding.AccountDetailFragmentBinding
import com.example.mvvmproject.fragment.account.AccountFragment
class AccountDetailFragment : BaseMvvmFragment<AccountDetailFragmentBinding,AccountDetailViewModel>() {
    companion object {
        fun getInstance(id: Int): AccountDetailFragment {
            var fragment = AccountDetailFragment()
            var bundle = Bundle()
            bundle.putInt("ID", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var pid: Int = 0
    private var mAdapter: AccountDetailAdapter? = null
    private var mDatas = mutableListOf<DataX>()
    override fun getLayoutId(): Int {
        return R.layout.account_detail_fragment
    }

    override fun initData() {
        super.initData()
        pid = arguments!!.getInt("ID",0)
        if (isVisibleToUser ) {
            isFirstCreate = true
            mViewModel?.loadProjectData(pid)
        }

        mViewModel?.mDataBean?.observe(this, Observer {
           /* mDatas = it.data.datas as MutableList<DataX>
            mAdapter?.setList(mDatas)*/
            mDatas.addAll(it.data.datas)
            mAdapter?.setList(mDatas)
            mAdapter?.setStatBean(Const.LOAD_SUCCESS)
        })

        initAdapter()
    }

    private fun initAdapter() {
        mAdapter = AccountDetailAdapter(mContext)
        mAdapter?.setList(mDatas)
        mDataBinding.mRecycler.layoutManager = LinearLayoutManager(mContext)
        mDataBinding.mRecycler.adapter = mAdapter

        mDataBinding.mRecycler.addOnScrollListener(object : EndlessRecyclerOnScrollListener(){
            override fun onLoadMore() {
                if (mAdapter?.getLoadState() == Const.LOAD_SUCCESS) {
                    Log.e("PPS", "加载更多")
                    mAdapter?.setStatBean(Const.LOADING)
                    mViewModel?.loadMoreProjectData(pid)
                }
            }
        })

        mAdapter?.setListener(object : IOnItemClickListener<DataX>{
            override fun itemClickListener(position: Int, data: DataX?) {
                val intent = Intent(mContext, WebViewActivity::class.java)
                intent.putExtra("link", mDatas[position].link)
                startActivity(intent)
            }

        })
    }

    override fun initViewModel(): AccountDetailViewModel {
        return AccountDetailViewModel()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && !isFirstCreate){
            isFirstCreate = true
            mViewModel?.loadProjectData(pid)
        }
    }
}