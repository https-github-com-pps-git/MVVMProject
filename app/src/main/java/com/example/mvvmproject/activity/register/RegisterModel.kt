package com.example.mvvmproject.activity.register

import com.example.base.model.BaseModel
import com.example.base.model.IModelCallBack
import com.example.mvvmproject.api.AppApi
import com.example.mvvmproject.api.IAppApi
import com.example.mvvmproject.constant.Constant
import com.example.mvvmproject.entity.RegisterBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class RegisterModel : BaseModel{


    constructor(){
        AppApi.getInstance().setBaseUrl(Constant.REGISTER_URL)
    }

    fun registerUser(userName: String,password: String,rePassword: String,callBack: IModelCallBack<RegisterBean>){
        mJob = GlobalScope.launch(Dispatchers.IO) {

            try {
                val registerUser =
                    AppApi.getInstance()?.getServiceApi(IAppApi::class.java).registerUser(
                        userName, password, rePassword
                    ).await()
                withContext(Dispatchers.Main){
                    callBack.getDataSuccess(registerUser)
                }

            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    callBack.getDataFailed(e.message!!)
                }
            }

        }
    }
}