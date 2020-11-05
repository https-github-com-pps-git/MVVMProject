package com.example.mvvmproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.base.listener.IOnItemClickListener
import com.example.mvvmproject.R
import com.to.aboomy.banner.HolderCreator

class BannerAdapter : HolderCreator {
    private var mListener: IOnItemClickListener<Any>? = null
    override fun createView(context: Context?, index: Int, o: Any?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.banner_item,null)
        val imageView = view.findViewById<ImageView>(R.id.mItem)
        Glide.with(context).load(o).into(imageView)
        view.setOnClickListener {
            mListener?.itemClickListener(index,o)
        }
        return view
    }

    fun setItemClickListener(mListener: IOnItemClickListener<Any>){
        this.mListener = mListener
    }
}