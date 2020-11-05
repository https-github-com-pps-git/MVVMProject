package com.example.mvvmproject.fragment.account

import com.example.base.model.BaseModel
import com.example.base.model.IModelCallBack
import com.example.mvvmproject.api.AppApi
import com.example.mvvmproject.api.IAppApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class AccountModel : BaseModel() {


    fun getTabData(listener: IModelCallBack<AccountTabBean>){
        mJob = GlobalScope.launch (Dispatchers.IO) {

            try {
                var data = AppApi.getInstance()?.getServiceApi(IAppApi::class.java).loadProjectTabLayoutData().await()
                withContext(Dispatchers.Main){
                    listener.getDataSuccess(data)
                }
            }catch (e :Exception){
                withContext(Dispatchers.Main){
                    listener.getDataFailed(e.message!!)
                }
            }


        }
    }
}