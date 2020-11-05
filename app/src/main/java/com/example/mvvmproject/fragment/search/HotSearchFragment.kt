package com.example.mvvmproject.fragment.search

import android.util.Log
import androidx.lifecycle.Observer
import com.example.base.fragment.BaseMvvmFragment
import com.example.mvvmproject.R
import com.example.mvvmproject.databinding.HotSearchFragmentBinding
import com.example.mvvmproject.listener.ITagClickListener
import com.example.mvvmproject.widget.TagView

class HotSearchFragment : BaseMvvmFragment<HotSearchFragmentBinding, HotSearchViewModel>() {

    companion object {
        fun getInstance(): HotSearchFragment {
            val mFragment = HotSearchFragment()
            return mFragment
        }
    }
    private var mCommonWebName = mutableListOf<String>()
    private var mSearchAllName = mutableListOf<String>()
    override fun getLayoutId(): Int {
        return R.layout.hot_search_fragment
    }

    override fun initData() {
        super.initData()


        if (isVisibleToUser) {
            isFirstCreate = true
            mViewModel?.getSearchData()
        }
        //逻辑错误信息
        mViewModel?.mFailedData?.observe(this, Observer<String> {
            toast(it)
        })

        mViewModel?.mStateData?.observe(this, Observer {
            //toast(it.message)
        })

        mViewModel?.mDataBean?.observe(this, Observer {
            Log.e("PPS"," 热搜回调 ")
            var mSearchAllBean = it.searchAllBean!!
            var mCommonWebBean = it.commonWebBean!!
            mCommonWebName.clear()
            mSearchAllName.clear()
            mSearchAllBean.data.forEach {
                mSearchAllName.add(it.name)
            }
            mCommonWebBean.data.forEach {
                mCommonWebName.add(it.name)
            }
            mDataBinding.mFlowLayout2.setViews(mCommonWebName)
            mDataBinding.mFlowLayout.setViews(mSearchAllName)
        })


        mDataBinding.mFlowLayout.addTagItemListener(object : ITagClickListener {
            override fun onTagClickListener(text: String, position: Int) {
                toast("点击了第${position} 个下标  内容 = $text")
            }

        })

        mDataBinding.mFlowLayout2.addTagItemListener(object : ITagClickListener {
            override fun onTagClickListener(text: String, position: Int) {
                toast("点击了第${position} 个下标  内容 = $text")
            }

        })
    }

    override fun initViewModel(): HotSearchViewModel {
        return HotSearchViewModel()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.e("PPS"," isFirstCreate == $isFirstCreate ")
        if (isVisibleToUser && !isFirstCreate){
            isFirstCreate = true
            mViewModel?.getSearchData()
        }
    }
}