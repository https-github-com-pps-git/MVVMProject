package com.example.mvvmproject.fragment.setup

import com.example.base.model.BaseModel
import com.example.base.model.IModelCallBack
import com.example.mvvmproject.api.AppApi
import com.example.mvvmproject.api.IAppApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class SetUpModel : BaseModel() {


    fun loadSetUpData(listener: IModelCallBack<SetUpBean>){

        mJob = GlobalScope.launch(Dispatchers.IO) {
            try {
                var setUpBean: SetUpBean = AppApi.getInstance()?.getServiceApi(IAppApi::class.java).loadSetUp().await()
                withContext(Dispatchers.Main){
                    listener.getDataSuccess(setUpBean)
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    listener.getDataFailed(e.message!!)
                }
            }

        }
    }
}