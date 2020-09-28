package com.example.base.model

/**
 * 获取数据的接口
 */
interface IModelCallBack<T> {

    fun getDataSuccess(data: T)

    fun getDataFailed(msg: String)

}