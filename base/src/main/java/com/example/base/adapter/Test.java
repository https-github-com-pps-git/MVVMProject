package com.example.base.adapter;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.base.R;

public class Test {
    @BindingAdapter("imageUrl")
    public static void setSrc(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }

}
