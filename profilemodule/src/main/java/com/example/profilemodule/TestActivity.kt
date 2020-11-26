package com.example.profilemodule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.example.base.activity.BaseActivity
import com.example.profilemodule.databinding.ActivityTestBinding
import com.example.profilemodule.util.ActivityManager

class TestActivity : BaseActivity<ActivityTestBinding>() {

    private var mHandler = object : Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0x123){
                mDataBinding.mHintText.text = "123456"
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_test
    }

    override fun initData() {
        super.initData()

        var name: String = ""
        for (i in 0 .. 800){
            name = name + "aaaaa" + i
        }



        ActivityManager.getInstance()?.test(this)
        mHandler.sendEmptyMessageDelayed(0x123,1_0000)
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages("")
    }
}