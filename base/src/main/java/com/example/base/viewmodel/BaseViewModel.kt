package com.example.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.base.model.BaseModel

/**
 *
 * ViewModel 的一个基类
 * @param T 这个表示的是数据源
 * @param M 这个表示是那个Model来负责获取这次数据
 *
 * BaseViewModelListener 这个接口是用来感知生命周期
 *
 * ViewModel 用来保存数据
 */
open abstract class BaseViewModel<T, M : BaseModel> : BaseViewModelListener, ViewModel {

    private var mStateBean: StateBean = StateBean()

    //错误的回调
    var mFailedData: MutableLiveData<String> = MutableLiveData()
    //获取数据成功的回调
    var mDataBean: MutableLiveData<T> = MutableLiveData()
    //当前的状态的回调
    var mStateData: MutableLiveData<StateBean> = MutableLiveData()

    //由那个model来获取数据
    var mModel: M? = null

    constructor() {
        //对model的一个初始化
        mModel = initModel()

    }


    fun setStateBean(state: Int,mes: String?) {
        mStateBean.state = state
        mStateBean.message = mes
        mStateData.value = mStateBean
    }

    //交给子类来实现
    abstract fun initModel(): M

    override fun onLifeCreate() {

    }

    override fun onLifeResume() {

    }

    override fun onLifeStop() {

    }

    /**
     *Activity / Fragment 在onDestroy的时候进行回调
     */
    override fun onLifeDestroy() {
        //在这里进行一些数据的销毁
        mModel?.cancel()
        mModel = null
    }


}