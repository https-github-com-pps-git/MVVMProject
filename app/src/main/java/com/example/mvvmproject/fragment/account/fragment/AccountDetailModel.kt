package com.example.mvvmproject.fragment.account.fragment

import com.example.base.model.BaseModel
import com.example.base.model.IModelCallBack
import com.example.mvvmproject.api.AppApi
import com.example.mvvmproject.api.IAppApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class AccountDetailModel : BaseModel() {

    fun getProjectData(page : Int , cid: Int , listener: IModelCallBack<AccountDetailBean>){
        mJob = GlobalScope.launch(Dispatchers.IO) {
            try {
                val data = AppApi.getInstance()?.getServiceApi(IAppApi::class.java).loadProjectListById(page,cid).await()
                withContext(Dispatchers.Main){
                    listener.getDataSuccess(data)
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    listener.getDataFailed(e.message!!)
                }
            }
        }
    }
}