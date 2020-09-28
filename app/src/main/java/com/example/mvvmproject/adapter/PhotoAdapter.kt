package com.example.mvvmproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.base.listener.IOnItemClickListener
import com.example.base.viewholder.BaseViewHolder
import com.example.mvvmproject.R
import com.example.mvvmproject.databinding.PhotoAdpterBinding
import com.example.mvvmproject.entity.PhotoBean
import com.example.mvvmproject.entity.Result

class PhotoAdapter : RecyclerView.Adapter<BaseViewHolder<PhotoAdpterBinding>> {

    private var mContext: Context? = null
    private var mList: MutableList<Result>? = null
    private var mListener: IOnItemClickListener<Result>? = null
    constructor(mContext: Context?){
        this.mContext = mContext
    }

    fun setList(mList: MutableList<Result>?){
        this.mList = mList
        notifyDataSetChanged()
    }

    fun setListener(mListener: IOnItemClickListener<Result>?){
        this.mListener = mListener
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<PhotoAdpterBinding> {

        var mDataBinding = DataBindingUtil.inflate<PhotoAdpterBinding>(
            LayoutInflater.from(mContext), R.layout.photo_adpter,parent,false)
        return BaseViewHolder(mDataBinding)
    }

    override fun getItemCount(): Int {
        return if (mList == null){
            0
        }else{
            mList!!.size
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<PhotoAdpterBinding>, position: Int) {
        holder.mDataBinding.photoBean = mList?.get(position)
        holder.mDataBinding.executePendingBindings()

        holder.itemView.setOnClickListener {
            mListener?.itemClickListener(position,mList?.get(position))
        }
    }
}