package com.example.mvvmproject.api

import com.example.mvvmproject.constant.Constant
import com.example.network.BaseNetWork

class AppApi : BaseNetWork {

    private constructor(): super(Constant.PHOTO_BASE_URL){

    }
    companion object{
        @Volatile
        private var mInstance: AppApi? = null

        fun getInstance(): AppApi{
            if (mInstance == null){
                synchronized(AppApi::class.java){
                    if (mInstance == null){
                        mInstance = AppApi()
                    }
                }
            }
            return mInstance!!
        }
    }



    fun <T> getServiceApi(clazz: Class<T>): T{
        return getInstance().getRetrofit(clazz).create(clazz)
    }

}