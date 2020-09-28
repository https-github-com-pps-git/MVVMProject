package com.example.base.activity

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.base.R
import com.example.base.util.StatusBarUtil

open abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var mContext: Context
    protected lateinit var mDataBinding: V
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        mDataBinding = DataBindingUtil.setContentView(this,getLayoutId())

        initStatusBar()

        initData()
    }

    /**
     * 设置默认的沉浸式状态栏颜色
     */
    protected open fun initStatusBar() {
        StatusBarUtil.setColor(this,resources.getColor(R.color.default_color),0)
    }


    //用来绑定layout
    protected abstract fun getLayoutId(): Int


    //用来做数据的初始化
    protected open fun initData(){

    }

    protected open fun toast(msg: String){
        Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show()
    }
}