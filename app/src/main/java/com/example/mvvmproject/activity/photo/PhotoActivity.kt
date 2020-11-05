package com.example.mvvmproject.activity.photo

import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.base.activity.BaseMvvmActivity
import com.example.base.app.BaseApplication
import com.example.base.listener.IOnItemClickListener
import com.example.mvvmproject.R
import com.example.mvvmproject.adapter.PhotoAdapter
import com.example.mvvmproject.databinding.ActivityPhotoBinding
import com.example.mvvmproject.entity.PhotoBean
import com.example.mvvmproject.entity.Result

class PhotoActivity : BaseMvvmActivity<ActivityPhotoBinding,PhotoViewModel>() {

    private var mAdapter: PhotoAdapter? = null
    private var mList = mutableListOf<Result>()
    override fun initViewModel(): PhotoViewModel {
        return PhotoViewModel()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_photo
    }

    override fun initData() {
        super.initData()

        mViewModel?.getPhotoData()

        mViewModel?.mDataBean?.observe(this,
            Observer<PhotoBean> {
               // mDataBinding.photoBean = it
                mList.addAll(it.results)
                mAdapter?.setList(mList)
            })


        initAdapter()
    }

    private fun initAdapter() {

        mDataBinding.mRecycler.layoutManager = GridLayoutManager(mContext,4)
        mAdapter = PhotoAdapter(mContext)
        mAdapter?.setList(mList)
        mDataBinding.mRecycler.adapter = mAdapter


        mAdapter?.setListener(object : IOnItemClickListener<Result>{
            override fun itemClickListener(position: Int, data: Result?) {
                mViewModel?.loadMoreData()
            }
        })
    }
}