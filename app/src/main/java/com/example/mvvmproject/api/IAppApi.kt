package com.example.mvvmproject.api

import com.example.mvvmproject.entity.*
import kotlinx.coroutines.Deferred
import retrofit2.http.*

/**
 *
 * Deferred 是 retrofit 对协程的支持
 *
 */
interface IAppApi {

    /**
     * 获取图片的APi接口
     */
    @GET("data/%E7%A6%8F%E5%88%A9/{pageSize}/{page}")
    fun getPhotoData(@Path("pageSize") pageSize: Int,
                     @Path("page") page: Int): Deferred<PhotoBean>

    /**
     * 注册的接口
     */
    @POST("user/register")
    fun registerUser(@Query("username") username: String,
                     @Query("password") password: String,
                     @Query("repassword") repassword: String): Deferred<RegisterBean>

    /**
     * 登录的接口
     */
    @POST("user/login")
    fun loginUser(@Query("username") username: String,
                     @Query("password") password: String): Deferred<LoginUser>

    /**
     * 首页的Banner
     */
    @GET("banner/json")
    fun loadBannerData(): Deferred<BannerBean>


    /**
     * 首页的数据
     */
    @GET("article/list/{page}/json")
    fun loadHomeData(@Path("page") page: Int): Deferred<HomeBean>

}