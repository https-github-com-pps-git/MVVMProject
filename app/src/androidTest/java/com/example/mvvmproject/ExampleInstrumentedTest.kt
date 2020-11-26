package com.example.mvvmproject

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.base.model.IModelCallBack
import com.example.mvvmproject.activity.login.LoginModel
import com.example.mvvmproject.entity.LoginUser
import com.example.mvvmproject.entity.User

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.text.ParseException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
  /*  @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.mvvmproject", appContext.packageName)
    }*/


    @Test(expected = ParseException::class)
    fun testLogin(){
        val loginModel = LoginModel()
        loginModel.login("1147123159","123456",object : IModelCallBack<LoginUser>{
            override fun getDataSuccess(data: LoginUser) {
                println("登录成功")
            }

            override fun getDataFailed(msg: String) {
                println("登录失败 $msg")
            }

        })
    }

}