package com.example.mvvmproject.fragment.account.fragment

import com.example.base.model.IModelCallBack
import com.example.base.viewmodel.BaseViewModel
import com.example.base.viewmodel.Cont

class AccountDetailViewModel : BaseViewModel<AccountDetailBean, AccountDetailModel>() {
    override fun initModel(): AccountDetailModel {
        return AccountDetailModel()
    }

    private var page: Int = 1

    fun loadProjectData(cid: Int){
        setStateBean(Cont.ONSTART, "开始获取数据")
        page = 1
        mModel?.getProjectData(page,cid,object : IModelCallBack<AccountDetailBean>{
            override fun getDataSuccess(data: AccountDetailBean) {
                if (data.errorCode == 0 ) {
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


    fun loadMoreProjectData(cid : Int){
        page ++
        mModel?.getProjectData(page,cid,object : IModelCallBack<AccountDetailBean>{
            override fun getDataSuccess(data: AccountDetailBean) {
                if (data.errorCode == 0 ) {
                    mDataBean.value = data
                } else {
                    setStateBean(Cont.ONFAILED, data.errorMsg, false)
                }
            }

            override fun getDataFailed(msg: String) {
                setStateBean(Cont.ONFAILED, msg, true)
                page--
            }

        })
    }
}