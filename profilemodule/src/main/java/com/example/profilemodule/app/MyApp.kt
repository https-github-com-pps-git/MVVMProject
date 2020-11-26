package com.example.profilemodule.app

import android.app.Application
import com.example.base.app.BaseApplication
import leakcanary.LeakCanary

class MyApp : BaseApplication(){
    override fun onCreate() {
        super.onCreate()
    }
}