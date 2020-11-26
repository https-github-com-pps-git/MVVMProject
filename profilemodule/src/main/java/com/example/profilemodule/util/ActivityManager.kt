package com.example.profilemodule.util

import android.app.Activity

class ActivityManager {
    private constructor(){}

    companion object{
        private var mInstance: ActivityManager? = null
        var mActivity = mutableListOf<Activity>()
        var mCurrentActivity: Activity? = null
        fun getInstance():ActivityManager{
            if (mInstance == null){
                synchronized(ActivityManager::class){
                    if (mInstance == null){
                        mInstance = ActivityManager()
                    }
                }
            }
            return mInstance!!
        }

    }

    fun test(activity: Activity){
        mActivity.add(activity)
        mCurrentActivity = activity
    }

}