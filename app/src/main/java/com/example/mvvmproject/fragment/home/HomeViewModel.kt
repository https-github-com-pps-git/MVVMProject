package com.example.mvvmproject.fragment.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.base.model.IModelCallBack
import com.example.base.viewmodel.BaseViewModel
import com.example.base.viewmodel.Cont
import com.example.mvvmproject.api.AppApi
import com.example.mvvmproject.api.IAppApi
import com.example.mvvmproject.entity.HomeBean
import kotlinx.coroutines.*

class HomeViewModel : BaseViewModel<HomeBean, HomeModel>() {

    var mFirstData: MutableLiveData<HomeFragmentData> = MutableLiveData()

    private var mIndexPage: Int = 0

    fun firstHomeData() {
        Log.e("PPS"," HOME 开始获取数据 ")
        setStateBean(Cont.ONSTART, " HOME 开始获取数据")
        //第一次加载需要加载2件事情 加载Banner  加载首页得到数据
        mModel?.refreshHomeData(0, object : IModelCallBack<HomeFragmentData> {
            override fun getDataSuccess(data: HomeFragmentData) {
                if (data.mBanner.errorCode == 0 && data.mHomeData.errorCode == 0) {
                    mFirstData.value = data
                } else {
                    setStateBean(Cont.ONFAILED, data.mBanner.errorMsg, true)
                }
            }

            override fun getDataFailed(msg: String) {
                setStateBean(Cont.ONFAILED, msg, true)
            }

        })
    }


    fun loadMoreData() {
        mIndexPage++
        mModel?.loadMoreData(mIndexPage, object : IModelCallBack<HomeBean> {
            override fun getDataSuccess(data: HomeBean) {
                if (data.data.datas.isNotEmpty()) {
                    mDataBean.value = data
                } else {
                    setStateBean(Cont.LOAD_DATA_END, "", false)
                }
            }

            override fun getDataFailed(msg: String) {
                mFailedData.value = msg
                mIndexPage--
            }

        })

    }


    override fun initModel(): HomeModel {
        return HomeModel()
    }

}