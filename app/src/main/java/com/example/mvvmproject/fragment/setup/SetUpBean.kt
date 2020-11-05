package com.example.mvvmproject.fragment.setup

data class SetUpBean(
    val `data`: List<SetUpData>,
    val errorCode: Int,
    val errorMsg: String
)

data class SetUpData(
    val children: List<SetUpChildren>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)

data class SetUpChildren(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)