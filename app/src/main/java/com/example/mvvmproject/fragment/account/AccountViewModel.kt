package com.example.mvvmproject.fragment.account

import android.util.Log
import com.example.base.model.IModelCallBack
import com.example.base.viewmodel.BaseViewModel
import com.example.base.viewmodel.Cont
import com.example.mvvmproject.fragment.setup.SetUpBean

class AccountViewModel : BaseViewModel<AccountTabBean, AccountModel>() {
    override fun initModel(): AccountModel {
        return AccountModel()
    }


    fun loadTabData(){
        Log.e("PPS"," ACCOUNT 开始获取数据 ")
        setStateBean(Cont.ONSTART, " ACCOUNT 开始获取数据")
        mModel?.getTabData(object : IModelCallBack<AccountTabBean>{
            override fun getDataSuccess(data: AccountTabBean) {
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