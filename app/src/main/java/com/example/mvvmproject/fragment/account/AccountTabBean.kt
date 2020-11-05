package com.example.mvvmproject.fragment.account

data class AccountTabBean(
    val `data`: List<AccountTabData>,
    val errorCode: Int,
    val errorMsg: String
)

data class AccountTabData(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)