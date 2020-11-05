package com.example.mvvmproject

import android.util.Log
import kotlinx.coroutines.*

fun main(args: Array<String>) {
    GlobalScope.launch(Dispatchers.IO) {

        var a = async {
            System.out.println("A登陆开始  ${System.currentTimeMillis()}")
            loginA()

        }

        var b = async {
            System.out.println("B登陆开始  ${System.currentTimeMillis()}")
            loginB()

        }

    }
    Thread.sleep(10000)

    /*for (i in 0 ..1080){
        println("<dimen name=\"dp_${i}\">"+i+"dp"+"</dimen>")
    }*/

    /*for (i in 0 ..50 step 2){
        println("<dimen name=\"sp_${i}\">"+i+"sp"+"</dimen>")
    }*/
    var a = 1
    var b = 2
    if (a == 1){
       println("aaaaaaaaaa")
    }else if (b == 2){
        println("bbbbbbbbb")
    }
}


suspend fun loginA(): Int{
    delay(3000)
    System.out.println("A登陆完成  ${System.currentTimeMillis()}")
    return 2
}

suspend fun loginB(): Int{
    delay(4000)
    System.out.println("B开始执行第二个方法")
    delay(2000)
    System.out.println("B登陆完成  ${System.currentTimeMillis()}")
    return 5
}
