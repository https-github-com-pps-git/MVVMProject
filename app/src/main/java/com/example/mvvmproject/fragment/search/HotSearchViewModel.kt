package com.example.mvvmproject.fragment.search

import android.util.Log
import com.example.base.model.IModelCallBack
import com.example.base.viewmodel.BaseViewModel
import com.example.base.viewmodel.Cont

class HotSearchViewModel : BaseViewModel<HotSearchBean,HotSearchModel>(){

    override fun initModel(): HotSearchModel {
        return HotSearchModel()
    }


    /**
     * 获取数据
     *
     */
    fun getSearchData(){
        Log.e("PPS"," HOT SEARCH 开始获取数据 ")
        setStateBean(Cont.ONSTART, " HOT SEARCH 开始获取数据")
        mModel?.getSearchData(object : IModelCallBack<HotSearchBean>{
            override fun getDataSuccess(data: HotSearchBean) {
                if (data.searchAllBean?.errorCode == 0 && data.commonWebBean?.errorCode == 0) {
                    mDataBean.value = data
                } else {
                    setStateBean(Cont.ONFAILED, data.searchAllBean!!.errorMsg, true)
                }
            }

            override fun getDataFailed(msg: String) {
                setStateBean(Cont.ONFAILED, msg, true)
            }

        })
    }

}