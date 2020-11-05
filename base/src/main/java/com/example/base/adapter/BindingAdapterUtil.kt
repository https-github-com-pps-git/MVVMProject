package com.example.base.adapter

import android.R
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


class BindingAdapterUtil {



    companion object{

        @BindingAdapter("imageUrl")
        @JvmStatic
        fun setSrc(imageView: ImageView, url: String?) {
            Glide.with(imageView.context).load(url)
                .error(com.example.base.R.mipmap.ic_launcher)
                .into(imageView)
        }


        @BindingAdapter("setVisibility")
        @JvmStatic
        fun setVisibility(view: View, state: Int,type: Int){
            if (state == type){
                view.visibility = View.VISIBLE
            }else{
                view.visibility = View.GONE
            }
        }
    }
}