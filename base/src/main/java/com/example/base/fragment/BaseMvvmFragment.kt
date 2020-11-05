package com.example.base.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import com.example.base.viewmodel.BaseViewModelListener

abstract class BaseMvvmFragment<V : ViewDataBinding,VM : BaseViewModelListener> : BaseFragment<V>() {
    protected var mViewModel: VM? = null
    protected var isFirstCreate: Boolean = false
    protected var isVisibleToUser: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = initViewModel()

        lifecycle.addObserver(mViewModel!!)

        initData()

    }

    abstract fun initViewModel(): VM

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
    }

}