package com.example.mvvmproject.entity

import java.io.Serializable

/**
 * 登录成功的User 对象
 */
data class User(var name: String, var password: String, var id: Int, var token: String): Serializable