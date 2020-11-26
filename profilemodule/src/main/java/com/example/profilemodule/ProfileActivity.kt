package com.example.profilemodule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.example.base.activity.BaseActivity
import com.example.profilemodule.databinding.ActivityProfileBinding

class ProfileActivity : BaseActivity<ActivityProfileBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_profile
    }

    override fun initData() {
        super.initData()

        initPermissions()

        mDataBinding.mBtn.setOnClickListener {
            startActivity(Intent(mContext,TestActivity::class.java))
        }
    }

    private fun initPermissions() {

        PermissionUtils.permission(PermissionConstants.STORAGE)
            .callback(object : PermissionUtils.SingleCallback {
                override fun callback(
                    isAllGranted: Boolean,
                    granted: MutableList<String>,
                    deniedForever: MutableList<String>,
                    denied: MutableList<String>
                ) {
                    Log.e("PPS", " $isAllGranted       ")
                    granted.forEach {
                        //这个是权限通过
                        Log.e("PPS", "  granted  $it")
                    }

                    denied.forEach {
                        //这个是权限未通过
                        Log.e("PPS", "  denied  $it")
                    }
                }

            }).request()

    }
}