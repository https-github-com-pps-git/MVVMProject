package com.example.mvvmproject.activity.login

import android.content.Context
import android.text.TextUtils
import com.example.base.model.IModelCallBack
import com.example.base.viewmodel.BaseViewModel
import com.example.base.viewmodel.Cont
import com.example.base.viewmodel.StateBean
import com.example.mvvmproject.R
import com.example.mvvmproject.entity.LoginUser
import com.example.mvvmproject.entity.User

class LoginViewModel : BaseViewModel<User,LoginModel>() {

    fun login(context: Context,username: String,password: String){
        if (TextUtils.isEmpty(username)){
            //用户名为null
            mFailedData.value = context.resources.getString(R.string.username_empty)
            return
        }
        if (TextUtils.isEmpty(password)){
            //密码为null
            mFailedData.value = context.resources.getString(R.string.password_empty)
            return
        }

        //更新网络请求的状态
        setStateBean(Cont.ONSTART,"正在登录")
        mModel?.login(username,password,object : IModelCallBack<LoginUser>{
            override fun getDataSuccess(data: LoginUser) {
                if (data.errorCode == 0){
                    var mUser =  User(data.data.username,data.data.password,data.data.id,data.data.token)
                    mDataBean.value = mUser
                    setStateBean(Cont.ONCOMPLETE,null)
                } else {

                    setStateBean(Cont.ONFAILED,data.errorMsg)
                }

            }

            override fun getDataFailed(msg: String) {
                setStateBean(Cont.ONFAILED,msg)
            }

        })
    }



    override fun initModel(): LoginModel {
        return LoginModel()
    }

}