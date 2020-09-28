package com.example.base.fragment

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.example.base.viewmodel.BaseViewModelListener

abstract class BaseMvvmFragment<V : ViewDataBinding,VM : BaseViewModelListener> : BaseFragment<V>() {
    protected var mViewModel: VM? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = initViewModel()

        lifecycle.addObserver(mViewModel!!)

        initMVVMData()

    }

    abstract fun initViewModel(): VM

    protected open fun initMVVMData(){

    }

}