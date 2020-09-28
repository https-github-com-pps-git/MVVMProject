package com.example.base.model

import kotlinx.coroutines.Job

/**
 *
 * 应为这里采用的是协程来进行的IO线程和子线程的数据切换
 * 在OnDestroy的声明周期中假设本次网络请求没有执行完成就应该取消这次网络请求
 */
open class BaseModel: IBaseModel {

    protected var mJob: Job? = null


    fun cancel(){
        //取消网络请求
        if (mJob != null) {
            if (mJob?.isActive!!) {
                mJob?.cancel()
            }
        }
    }
}