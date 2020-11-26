package com.example.base.app

import android.app.Application
import com.example.base.util.Density
import com.kingja.loadsir.core.LoadSir

open class BaseApplication : Application() {

    companion object {
        public lateinit var mInstance: Application
    }


    override fun onCreate() {
        super.onCreate()
        mInstance = this

        //将美工给的设计图的宽度的dp设置上去就好了

        Density.setDensity(this,(380f).toFloat())
    }
}