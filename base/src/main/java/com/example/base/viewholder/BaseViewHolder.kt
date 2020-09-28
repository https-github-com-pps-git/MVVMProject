package com.example.base.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder<V : ViewDataBinding> : RecyclerView.ViewHolder{
    var mDataBinding: V
    constructor(mDataBinding: V): super(mDataBinding.root){
        this.mDataBinding = mDataBinding
    }


}