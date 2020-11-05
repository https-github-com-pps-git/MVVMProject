package com.example.mvvmproject.fragment.search

data class SearchAllBean(
    val `data`: List<KeyData>,
    val errorCode: Int,
    val errorMsg: String
)

data class KeyData(
    val id: Int,
    val link: String,
    val name: String,
    val order: Int,
    val visible: Int
)

data class CommonWebBean(
    val `data`: List<CommonWebData>,
    val errorCode: Int,
    val errorMsg: String
)

data class CommonWebData(
    val icon: String,
    val id: Int,
    val link: String,
    val name: String,
    val order: Int,
    val visible: Int
)

class HotSearchBean{
    var commonWebBean: CommonWebBean? = null
    var searchAllBean: SearchAllBean? = null
}