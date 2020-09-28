package com.example.mvvmproject.activity.login

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.Observer
import com.example.base.activity.BaseMvvmActivity
import com.example.base.dialog.StateDialog
import com.example.base.viewmodel.Cont
import com.example.base.viewmodel.StateBean
import com.example.mvvmproject.R
import com.example.mvvmproject.activity.register.RegisterActivity
import com.example.mvvmproject.databinding.ActivityLoginBinding
import com.example.mvvmproject.entity.User

class LoginActivity : BaseMvvmActivity<ActivityLoginBinding,LoginViewModel>() {
    private var mStateDialog: StateDialog? = null
    private var name: String? = "AAAA"
    override fun initViewModel(): LoginViewModel {
        return LoginViewModel()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initMVVMData() {
        super.initMVVMData()
        //初始化弹窗
        mStateDialog = StateDialog(mContext)

        mDataBinding.mTopLayout.mTitleTv.text = resources.getString(R.string.login)

        mDataBinding.loginBtn.setOnClickListener {
            mViewModel?.login(mContext,mDataBinding.userNameEdit.text.trim().toString()
                ,mDataBinding.passwordEdit.text.trim().toString())
        }


        mViewModel?.mDataBean?.observe(this, Observer<User> {
            toast("登录成功")
        })


        mViewModel?.mFailedData?.observe(this,Observer<String>{

            toast(it)
        })

        mDataBinding.registerTv.setOnClickListener {
            startActivityForResult(Intent(mContext,RegisterActivity::class.java),0x123)
        }



        //当前网络请求的状态
        mViewModel?.mStateData?.observe(this, Observer<StateBean> {
            when (it?.state) {
                Cont.ONSTART -> {
                    //开始获取数据
                    mStateDialog?.showDialog(it)
                }
                Cont.ONCOMPLETE -> {
                    //获取数据完成
                    mStateDialog?.dismiss()
                }
                Cont.ONFAILED -> {
                    //获取网络数据失败
                    mStateDialog?.showDialog(it)
                    mHandler.postDelayed({
                        mStateDialog?.dismiss()
                    },1000)
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("PPS","  requestCode  $requestCode    resultCode   $resultCode  ")
        if (resultCode == 0x123){
            val user = data?.getSerializableExtra("user") as User
            mDataBinding.userNameEdit.setText(user.name)
        }
    }

    private val mHandler = object : Handler(Looper.getMainLooper()){}

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null)
    }
}