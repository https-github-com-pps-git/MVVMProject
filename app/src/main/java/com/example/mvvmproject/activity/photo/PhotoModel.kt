package com.example.mvvmproject.activity.photo

import com.example.base.model.BaseModel
import com.example.base.model.IModelCallBack
import com.example.mvvmproject.api.IAppApi
import com.example.mvvmproject.api.AppApi
import com.example.mvvmproject.entity.PhotoBean
import kotlinx.coroutines.*
import java.lang.Exception

class PhotoModel : BaseModel{


    constructor(){

    }


    fun getPhotoData(pageSize: Int, page: Int,callBack: IModelCallBack<PhotoBean>){

        mJob = GlobalScope.launch(Dispatchers.IO) {
            //切换到IO线程
            try {
                val photoBean = AppApi.getInstance().getServiceApi(IAppApi::class.java)
                    .getPhotoData(pageSize,page).await()
                withContext(Dispatchers.Main){
                    //切换到主线程
                    callBack.getDataSuccess(photoBean)
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    callBack.getDataFailed(e.message!!)
                }
            }
        }
    }


}