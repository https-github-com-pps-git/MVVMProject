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


    fun refreshHomeData(page: Int,callBack: IModelCallBack<HomeFragmentData>) {
        //第一次加载需要加载2件事情 加载Banner  加载首页得到数据
        GlobalScope.launch(Dispatchers.IO) {
            try {
                //这个就是协程的并行 2个接口同时请求
                val banner = async { loadBanner() }

                val data = async { loadHomeData(page) }

                var bannerBean = banner.await()
                var dataBean = data.await()
                withContext(Dispatchers.Main) {
                    val mHomeBean = HomeFragmentData(bannerBean,dataBean)
                    callBack.getDataSuccess(mHomeBean)
                }

            }catch (e: Exception){
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
    private suspend fun loadBanner(): BannerBean {
        Log.e("PPS","loadBanner")
        return AppApi.getInstance().getServiceApi(IAppApi::class.java).loadBannerData().await()
    }


    private suspend fun loadHomeData(page: Int): HomeBean {
        Log.e("PPS","loadHomeData")
        return AppApi.getInstance().getServiceApi(IAppApi::class.java).loadHomeData(page).await()
    }

}