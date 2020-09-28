package com.example.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class BaseNetWork {

    //Retrofit 必要的参数
    private lateinit var mUrl: String
    //定义一个MAP来缓存Retrofit
    private var mCacheRetrofit = HashMap<String,Retrofit>()
    //Okhttp实；列
    private var okHttpClient: OkHttpClient? = null
    protected constructor(url: String){
        this.mUrl = url
    }

    fun setBaseUrl(url: String): BaseNetWork{
        this.mUrl = url
        return this
    }

    protected fun <T> getRetrofit(clazz: Class<T>): Retrofit{
        var retrofit = mCacheRetrofit[clazz.name + mUrl]
        if (retrofit != null){
            //判断之前有没有声明这个实列对象
            return retrofit
        }
        if (retrofit == null) {
            synchronized(BaseNetWork::class.java){
                if (retrofit == null){
                    retrofit = Retrofit.Builder()
                        .baseUrl(mUrl)
                        .client(getOkhttpClient())
                        //设置GSON解析
                        .addConverterFactory(GsonConverterFactory.create())
                        //设置返回的适配器  用的是携程
                        .addCallAdapterFactory(CoroutineCallAdapterFactory())
                        .build()
                }
            }

        }

        return retrofit!!
    }

    private fun getOkhttpClient(): OkHttpClient {
        if (okHttpClient == null){
            synchronized(BaseNetWork::class.java){
                if (okHttpClient == null){
                    val mOkHttpClient = OkHttpClient.Builder()
                        .retryOnConnectionFailure(true)
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(45, TimeUnit.SECONDS)
                        .writeTimeout(55, TimeUnit.SECONDS)

                    if (requestInterceptor() != null){
                        mOkHttpClient.addInterceptor(requestInterceptor()!!)
                    }

                    if (responseInterceptor() != null){
                        mOkHttpClient.addInterceptor(responseInterceptor()!!)
                    }
                    okHttpClient =  mOkHttpClient.build()
                }
            }
        }
        return okHttpClient!!
    }


    //OKhttp请求拦截器
    protected fun requestInterceptor(): Interceptor?{
        return null
    }


    //OKhttp返回拦截器
    protected fun responseInterceptor(): Interceptor?{
        return null
    }
}