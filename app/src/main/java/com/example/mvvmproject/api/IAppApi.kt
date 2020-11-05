package com.example.mvvmproject.api

import com.example.mvvmproject.entity.*
import com.example.mvvmproject.fragment.account.AccountTabBean
import com.example.mvvmproject.fragment.account.fragment.AccountDetailBean
import com.example.mvvmproject.fragment.search.CommonWebBean
import com.example.mvvmproject.fragment.search.SearchAllBean
import com.example.mvvmproject.fragment.setup.SetUpBean
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
    fun getPhotoData(
        @Path("pageSize") pageSize: Int,
        @Path("page") page: Int
    ): Deferred<PhotoBean>

    /**
     * 注册的接口
     */
    @POST("user/register")
    fun registerUser(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("repassword") repassword: String
    ): Deferred<RegisterBean>

    /**
     * 登录的接口
     */
    @POST("user/login")
    fun loginUser(
        @Query("username") username: String,
        @Query("password") password: String
    ): Deferred<LoginUser>

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

    /**
     * 大家都在搜
     * https://www.wanandroid.com/hotkey/json
     */
    @GET("hotkey/json")
    fun loadAllSearch(): Deferred<SearchAllBean>

    /**
     * 常用网站
     */
    @GET("friend/json")
    fun loadCommonWeb(): Deferred<CommonWebBean>

    /**
     * 体系
     */
    @GET("tree/json")
    fun loadSetUp(): Deferred<SetUpBean>

    /**
     * 公众号 tablayout的地址
     */
    @GET("project/tree/json")
    fun loadProjectTabLayoutData(): Deferred<AccountTabBean>


    /**
     * 项目详情
     */
    @GET("project/list/{page}/json")
    fun loadProjectListById(@Path("page") page: Int,@Query("cid") pid: Int): Deferred<AccountDetailBean>

}