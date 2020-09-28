package com.example.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

open abstract class BaseFragment<V : ViewDataBinding> : Fragment() {
    protected lateinit var mContext: Context
    protected lateinit var mDataBinding: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return mDataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }


    protected open fun initData(){

    }

    protected abstract fun getLayoutId(): Int

    protected open fun toast(msg: String){
        Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show()
    }
}