package com.example.base.listener

public interface IOnItemClickListener<T> {

    fun itemClickListener(position: Int,data: T?)
}