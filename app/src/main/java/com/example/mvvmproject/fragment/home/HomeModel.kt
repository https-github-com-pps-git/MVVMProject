package com.example.mvvmproject.fragment.home

import android.util.Log
import com.example.base.model.BaseModel
import com.example.base.model.IModelCallBack
import com.example.mvvmproject.api.AppApi
import com.example.mvvmproject.api.IAppApi
import com.example.mvvmproject.constant.Constant
import com.example.mvvmproject.entity.BannerBean
import com.example.mvvmproject.entity.HomeBean
import kotlinx.coroutines.*
import java.lang.Exception

class HomeModel : BaseModel {
    constructor() {
        AppApi.getInstance().setBaseUrl(Constant.REGISTER_URL)
    }
    private var callBack: IModelCallBack<HomeFragmentData>? = null

    fun refreshHomeData(page: Int,callBack: IModelCallBack<HomeFragmentData>) {
        //第一次加载需要加载2件事情 加载Banner  加载首页得到数据
        this.callBack = callBack
        //1.先判断网络是否可用

        mJob = GlobalScope.launch(Dispatchers.IO) {
            try {
                //这个就是协程的并行 2个接口同时请求
                val banner = async { loadBanner() }

                val data = async { loadHomeData(page) }

                var bannerBean = banner.await()
                var dataBean = data.await()
                withContext(Dispatchers.Main) {
                    if (bannerBean != null && dataBean != null) {
                        val mHomeBean = HomeFragmentData(bannerBean, dataBean)
                        callBack.getDataSuccess(mHomeBean)
                    }
                }

            }catch (e: Exception){
                Log.e("PPS","出现了异常 ${e.message}")
                withContext(Dispatchers.Main) {
                    //出现了异常
                    callBack.getDataFailed(e?.message!!)
                }
            }


        }
    }

    /**
     * 获取Banner
     */
    private suspend fun loadBanner(): BannerBean? {
        Log.e("PPS","loadBanner")
        try {
            return AppApi.getInstance().getServiceApi(IAppApi::class.java).loadBannerData().await()
        }catch (e: Exception){
            withContext(Dispatchers.Main) {
                callBack?.getDataFailed(e?.message!!)
            }
        }
        return null
    }


    private suspend fun loadHomeData(page: Int): HomeBean?  {
        Log.e("PPS","loadHomeData")
        try {
            return AppApi.getInstance().getServiceApi(IAppApi::class.java).loadHomeData(page).await()
        }catch (e: Exception){
            withContext(Dispatchers.Main) {
                callBack?.getDataFailed(e?.message!!)
            }
        }
        return null

    }

    fun loadMoreData(page: Int , callBack: IModelCallBack<HomeBean>){

        mJob = GlobalScope.launch(Dispatchers.IO) {
            try {
                val data = loadHomeData(page)
                withContext(Dispatchers.Main){
                    if (data != null) {
                        callBack.getDataSuccess(data)
                    }
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main) {
                    //出现了异常
                    callBack.getDataFailed(e?.message!!)
                }
            }

        }
    }

}