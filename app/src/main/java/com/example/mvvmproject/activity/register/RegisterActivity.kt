package com.example.mvvmproject.activity.register

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Observer
import com.example.base.activity.BaseMvvmActivity
import com.example.base.dialog.StateDialog
import com.example.base.viewmodel.Cont
import com.example.base.viewmodel.StateBean
import com.example.mvvmproject.R
import com.example.mvvmproject.constant.Constant
import com.example.mvvmproject.databinding.ActivityRegisterBinding
import com.example.mvvmproject.entity.User

/**
 * 注册Activity
 */
class RegisterActivity : BaseMvvmActivity<ActivityRegisterBinding, RegisterViewModel>() {

    private var mStateDialog: StateDialog? = null
    override fun initViewModel(): RegisterViewModel {
        return RegisterViewModel()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun initMVVMData() {
        super.initMVVMData()

        //初始化弹窗
        mStateDialog = StateDialog(mContext)

        //设置标题
        mDataBinding.mTopLayout.mTitleTv.text = resources.getString(R.string.register)

        //顶部箭头的返回键
        mDataBinding.mTopLayout.mBackIv.setOnClickListener {
            finish()
        }

        //注册按钮
        mDataBinding.registerBtn.setOnClickListener {
            mViewModel?.registerUser(
                mContext, mDataBinding.userNameEdit.text.trim().toString(),
                mDataBinding.passwordEdit.text.trim().toString(),
                mDataBinding.rePasswordEdit.text.trim().toString()
            )
        }

        //逻辑的错误的回调
        mViewModel?.mFailedData?.observe(this, Observer<String> {
            toast(it)
        })

        //正确的回调
        mViewModel?.mDataBean?.observe(this, Observer<User> {
            toast(mContext.resources.getString(R.string.register_success))
            val intent = Intent()
            intent.putExtra("user", it)
            setResult(0x123, intent)
            finish()
        })

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

    private val mHandler = object : Handler(Looper.getMainLooper()){}

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null)
    }
}