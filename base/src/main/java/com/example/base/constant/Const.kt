package com.example.base.constant

class Const {

    companion object {

        /**
         * 当前上拉更多的几种状态
         */

        const val LOADING: Int = 1  //正在加载

        const val LOAD_SUCCESS: Int = 2 //加载完成,加载到了数据

        const val LOAD_END: Int = 3 //没有更多的数据了

        const val LOAD_FAILED: Int = 4 //加载失败
    }

}