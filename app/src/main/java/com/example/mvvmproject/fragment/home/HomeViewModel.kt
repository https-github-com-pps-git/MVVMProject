package com.example.mvvmproject.fragment.home

import androidx.lifecycle.MutableLiveData
import com.example.base.model.IModelCallBack
import com.example.base.viewmodel.BaseViewModel
import com.example.base.viewmodel.Cont
import com.example.mvvmproject.entity.HomeBean

class HomeViewModel : BaseViewModel<HomeBean, HomeModel>() {

    var mFirstData: MutableLiveData<HomeFragmentData> = MutableLiveData()

    fun refreshHomeData(){
        setStateBean(Cont.ONSTART,"开始获取数据")
        //第一次加载需要加载2件事情 加载Banner  加载首页得到数据
        mModel?.refreshHomeData(0,object :IModelCallBack<HomeFragmentData>{
            override fun getDataSuccess(data: HomeFragmentData) {
                if (data.mBanner.errorCode == 0 && data.mHomeData.errorCode == 0) {
                    mFirstData.value = data
                }else{
                    setStateBean(Cont.ONFAILED,data.mBanner.errorMsg)
                }
            }

            override fun getDataFailed(msg: String) {
                setStateBean(Cont.ONFAILED,msg)
            }

        })
    }



    override fun initModel(): HomeModel {
        return HomeModel()
    }

}