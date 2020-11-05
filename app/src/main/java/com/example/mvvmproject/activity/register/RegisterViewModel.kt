package com.example.mvvmproject.activity.register

import android.content.Context
import android.text.TextUtils
import com.example.base.model.IModelCallBack
import com.example.base.viewmodel.BaseViewModel
import com.example.base.viewmodel.Cont
import com.example.base.viewmodel.StateBean
import com.example.mvvmproject.R
import com.example.mvvmproject.constant.Constant
import com.example.mvvmproject.entity.RegisterBean
import com.example.mvvmproject.entity.User

class RegisterViewModel : BaseViewModel<User, RegisterModel>() {

    private var mUser: User? = null
    /**
     * 注册
     */
    fun registerUser(context: Context, username: String, password: String, rePassword: String) {
        if (TextUtils.isEmpty(username)) {
            //用户名为null
            mFailedData.value = context.resources.getString(R.string.username_empty)
            return
        }
        if (TextUtils.isEmpty(password)) {
            //密码为null
            mFailedData.value = context.resources.getString(R.string.password_empty)
            return
        }
        if (TextUtils.isEmpty(rePassword)) {
            //确认密码为null
            mFailedData.value = context.resources.getString(R.string.repassword_empty)
            return
        }

        //更新网络请求的状态
        setStateBean(Cont.ONSTART,"正在注册")


        mModel?.registerUser(username, password, rePassword, object : IModelCallBack<RegisterBean> {
            override fun getDataSuccess(data: RegisterBean) {
                if (data.errorCode == 0) {

                    mUser =
                        User(data.data.username, data.data.password, data.data.id, data.data.token)

                    mDataBean.value = mUser

                    setStateBean(Cont.ONCOMPLETE,"")
                } else {

                    setStateBean(Cont.ONFAILED,data.errorMsg)
                }

            }

            override fun getDataFailed(msg: String) {
                setStateBean(Cont.ONFAILED,msg)
            }

        })
    }




    override fun initModel(): RegisterModel {
        return RegisterModel()
    }
}