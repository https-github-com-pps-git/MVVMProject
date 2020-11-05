package com.example.base.util

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.annotation.NonNull
import androidx.annotation.Nullable


/**
 * 这个是头条的屏幕适配方案
 */
class Density {


    companion object {
        private var appDensity = 0f
        private var appScaledDensity = 0f
        private var appDisplayMetrics: DisplayMetrics? = null

        /**
         * 用来参照的的width 美工设计图的像素密度来的
         */
        private var WIDTH = 0f


        fun setDensity(@NonNull application: Application, width: Float) {
            appDisplayMetrics = application.resources.displayMetrics
            WIDTH = width
            registerActivityLifecycleCallbacks(application)
            if (appDensity == 0f) {
                //初始化的时候赋值
                appDensity = appDisplayMetrics?.density!!
                appScaledDensity = appDisplayMetrics?.scaledDensity!!

                //添加字体变化的监听
                application.registerComponentCallbacks(object : ComponentCallbacks {

                    override fun onConfigurationChanged(newConfig: Configuration) {
                        //字体改变后,将appScaledDensity重新赋值
                        if (newConfig != null && newConfig.fontScale > 0) {
                            appScaledDensity =
                                application.resources.displayMetrics.scaledDensity
                        }
                    }

                    override fun onLowMemory() {}

                })
            }
        }


        private fun setDefault(activity: Activity) {
            setAppOrientation(activity)
        }

        private fun setAppOrientation(@Nullable activity: Activity) {
            var targetDensity = 0f
            try {
                targetDensity = appDisplayMetrics!!.widthPixels / WIDTH
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
            val targetScaledDensity = targetDensity * (appScaledDensity / appDensity)
            val targetDensityDpi = (160 * targetDensity).toInt()

            /**
             *
             * 最后在这里将修改过后的值赋给系统参数
             *
             * 只修改Activity的density值
             */
            val activityDisplayMetrics = activity.resources.displayMetrics
            activityDisplayMetrics.density = targetDensity
            activityDisplayMetrics.scaledDensity = targetScaledDensity
            activityDisplayMetrics.densityDpi = targetDensityDpi
        }


        private fun registerActivityLifecycleCallbacks(application: Application) {
            application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
                override fun onActivityCreated(
                    activity: Activity,
                    savedInstanceState: Bundle?
                ) {
                    setDefault(activity)
                }

                override fun onActivityStarted(activity: Activity) {}
                override fun onActivityResumed(activity: Activity) {}
                override fun onActivityPaused(activity: Activity) {}
                override fun onActivityStopped(activity: Activity) {}
                override fun onActivitySaveInstanceState(
                    activity: Activity,
                    outState: Bundle
                ) {
                }

                override fun onActivityDestroyed(activity: Activity) {}
            })
        }
    }

}