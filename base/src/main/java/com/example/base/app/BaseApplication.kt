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

        Density.setDensity(this,(160f*2.2).toFloat())
    }
}