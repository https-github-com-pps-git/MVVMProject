package com.example.base.listener

interface IOnItemClickListener<T> {

    fun itemClickListener(position: Int,data: T?)
}