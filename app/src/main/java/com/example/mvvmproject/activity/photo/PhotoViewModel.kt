package com.example.mvvmproject.activity.photo

import android.util.Log
import com.example.base.model.IModelCallBack
import com.example.base.viewmodel.BaseViewModel
import com.example.mvvmproject.entity.PhotoBean

class PhotoViewModel : BaseViewModel<PhotoBean,PhotoModel>() {

    private var mPageSize = 20
    private var mPage = 1

    /**
     * 第一次获取图片的数据
     */
    fun getPhotoData() {
        mPage = 1
        getPhotoDataP(mPageSize, mPage)
    }


    private fun getPhotoDataP(mPageSize: Int, mPage: Int) {
        Log.e("PPS","获取数据  ${mModel == null}")
        mModel?.getPhotoData(mPageSize, mPage, object : IModelCallBack<PhotoBean> {
            override fun getDataSuccess(data: PhotoBean) {
                Log.e("PPS", "${(mDataBean == null)} getDataSuccess ${data.results.size}")
                mDataBean.value = data
            }

            override fun getDataFailed(msg: String) {
                Log.e("PPS", " 请求错误 $msg")
                mFailedData.value = msg
            }

        })

    }

    /**
     * 上啦更多
     */
    fun loadMoreData() {
        Log.e("PPS"," 加载下一页数据 ")
        mPage++
        getPhotoDataP(mPageSize, mPage)
    }

    override fun initModel(): PhotoModel {
        return PhotoModel()
    }

}