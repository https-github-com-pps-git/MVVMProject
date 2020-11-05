package com.example.base.viewmodel

class Cont {

    companion object{
        //正在获取数据
        val ONSTART = 1

        //获取数据完成
        val ONCOMPLETE = 2

        //获取网络数据错误
        val ONFAILED = 3

        //加载数据到底部了
        val LOAD_DATA_END = 4
    }
}