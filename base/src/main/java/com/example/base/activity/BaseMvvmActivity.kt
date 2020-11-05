package com.example.base.activity

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.example.base.viewmodel.BaseViewModelListener

/**
 *
 * @param V 这个表示是那个视图
 * @param VM 表示是V 调用那个VM来获取数据
 *
 */

open abstract class BaseMvvmActivity<V : ViewDataBinding,VM : BaseViewModelListener> : BaseActivity<V>(){

    protected var mViewModel: VM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //对VM进行初始化
        mViewModel = initViewModel()

        //给VM添加声明周期的感应
        lifecycle.addObserver(mViewModel!!)

        //获取MVVM的数据
        initData()

    }

    protected abstract fun initViewModel(): VM


}