package com.example.mvvmproject.app

import android.os.Build
import android.os.Debug
import android.os.Environment
import androidx.annotation.RequiresApi
import com.example.base.app.BaseApplication
import java.io.File

class MyApp : BaseApplication {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(){
        //Debug.startMethodTracingSampling(Environment.getExternalStorageState()+("PPS"),8*1024*1024,1_000)
    }

}