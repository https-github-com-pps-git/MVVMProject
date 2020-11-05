package com.example.mvvmproject.fragment.setup

import android.util.Log
import com.example.base.model.IModelCallBack
import com.example.base.viewmodel.BaseViewModel
import com.example.base.viewmodel.Cont

class SetUpViewModel : BaseViewModel<SetUpBean, SetUpModel>() {
    override fun initModel(): SetUpModel {
        return SetUpModel()
    }


    fun loadSetUpData(){
        Log.e("PPS"," SETUP 开始获取数据 ")
        setStateBean(Cont.ONSTART, " SETUP 开始获取数据")
        mModel?.loadSetUpData(object : IModelCallBack<SetUpBean>{
            override fun getDataSuccess(data: SetUpBean) {
                if (data?.errorCode == 0 ) {
                    mDataBean.value = data
                } else {
                    setStateBean(Cont.ONFAILED, data.errorMsg, true)
                }
            }

            override fun getDataFailed(msg: String) {
                setStateBean(Cont.ONFAILED, msg, true)
            }

        })
    }
}