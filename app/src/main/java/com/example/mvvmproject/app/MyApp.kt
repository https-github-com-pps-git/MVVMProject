package com.example.mvvmproject.app

import android.os.Build
import android.os.Debug
import android.os.Environment
import android.os.StrictMode
import androidx.annotation.RequiresApi
import com.example.base.app.BaseApplication
import com.example.mvvmproject.BuildConfig
import java.io.File

class MyApp : BaseApplication {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor() {
        //用来抓取启动的耗时
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()//读写操作
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    //.penaltyDeath()//违规崩溃
                    .build()
            )
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()//sqlite对象泄露
                .detectLeakedClosableObjects() //流为关闭的泄露
                .penaltyLog()//违规日志
                //.penaltyDeath()//违规崩溃
                .build())

            /*Debug.startMethodTracingSampling(
                Environment.getExternalStorageState() + ("PPS"),
                8 * 1024 * 1024,
                1_000
            )*/

        }

    }

}