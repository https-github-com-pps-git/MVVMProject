package com.example.profilemodule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class FirstActivity extends AppCompatActivity {

    private static float sNoncompatDensity;// 系统的Density
    private static float sNoncompatScaleDensity;// 系统的ScaledDensity

    private static void setCustomDensity(Activity activity, final Application application){
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();

        if(sNoncompatDensity == 0){
            // 系统的Density
            sNoncompatDensity = appDisplayMetrics.density;
            // 系统的ScaledDensity
            sNoncompatScaleDensity = appDisplayMetrics.scaledDensity;
            // 监听在系统设置中切换字体
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(@NonNull Configuration newConfig) {
                    if(newConfig != null && newConfig.fontScale > 0){
                        sNoncompatScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        // 此处以360dp的设计图作为例子
        final float targetDensity = appDisplayMetrics.widthPixels / 360;
        final float targetScaledDensity = targetDensity * (sNoncompatScaleDensity/sNoncompatDensity);
        final int targetDensityDpi = (int)(160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setCustomDensity(this,this.getApplication());
        setContentView(R.layout.activity_first);
    }
}