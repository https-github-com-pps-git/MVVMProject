package com.example.mvvmproject.fragment.search

import android.util.Log
import com.example.base.model.BaseModel
import com.example.base.model.IModelCallBack
import com.example.mvvmproject.api.AppApi
import com.example.mvvmproject.api.IAppApi
import com.example.mvvmproject.fragment.home.HomeFragmentData
import kotlinx.coroutines.*
import java.lang.Exception

class HotSearchModel : BaseModel() {
    private var callBack: IModelCallBack<HotSearchBean>? = null
    fun getSearchData(callBack: IModelCallBack<HotSearchBean>){
        this.callBack = callBack

        mJob = GlobalScope.launch(Dispatchers.IO) {
            try {

                var searchAll = async {
                    getSearchAll()
                }

                var common = async {
                    getCommonWeb()
                }


                var commonWebBean: CommonWebBean? = common.await()
                var searchAllBean: SearchAllBean? = searchAll.await()
                var hotSearchBean = HotSearchBean()

                hotSearchBean.commonWebBean = commonWebBean
                hotSearchBean.searchAllBean = searchAllBean
                withContext(Dispatchers.Main){
                    callBack.getDataSuccess(hotSearchBean)
                }

                Log.e("PPS","获取数据成功")
            }catch (e: Exception){
                withContext(Dispatchers.Main) {
                    if(e?.message != null) {
                        callBack.getDataFailed(e?.message!!)
                    }
                }
            }

        }
    }

    //获取常用网站
    private suspend fun getCommonWeb(): CommonWebBean? {
        try {
            return AppApi.getInstance()?.getServiceApi(IAppApi::class.java).loadCommonWeb().await()
        }catch (e: Exception){
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                callBack?.getDataFailed(e.message!!)
            }
            return null
        }
    }

    //获取热搜
    private suspend fun getSearchAll(): SearchAllBean? {
        try {
            return AppApi.getInstance()?.getServiceApi(IAppApi::class.java).loadAllSearch().await()
        }catch (e: Exception){
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                callBack?.getDataFailed(e.message!!)
            }
            return null
        }
    }

}