package com.example.mvvmproject.activity.login

import android.util.Log
import com.example.base.model.BaseModel
import com.example.base.model.IModelCallBack
import com.example.mvvmproject.api.AppApi
import com.example.mvvmproject.api.IAppApi
import com.example.mvvmproject.constant.Constant
import com.example.mvvmproject.entity.LoginUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginModel : BaseModel{


    constructor(){
        AppApi.getInstance().setBaseUrl(Constant.REGISTER_URL)
    }

    fun login(username: String, password: String,callBack: IModelCallBack<LoginUser>){
        mJob = GlobalScope.launch(Dispatchers.IO) {
            try {
                val loginUser =  AppApi.getInstance()?.getServiceApi(IAppApi::class.java)
                    .loginUser(username,password).await()
                withContext(Dispatchers.Main){
                    callBack.getDataSuccess(loginUser)
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    Log.e("PPS"," 错误  ${e.message}")
                    callBack.getDataFailed(e.message!!)
                }
            }

        }
    }
}