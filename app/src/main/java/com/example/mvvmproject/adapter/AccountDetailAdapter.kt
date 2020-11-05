package com.example.mvvmproject.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.base.bean.LoadStateBean
import com.example.base.constant.Const
import com.example.base.databinding.RecyclerviewLoadMoreBinding
import com.example.base.listener.IOnItemClickListener
import com.example.base.viewholder.BaseViewHolder
import com.example.mvvmproject.R
import com.example.mvvmproject.databinding.AccountDetailAdapterBinding
import com.example.mvvmproject.databinding.HomeFragmentAdapterBinding
import com.example.mvvmproject.fragment.account.fragment.DataX

class AccountDetailAdapter : RecyclerView.Adapter<BaseViewHolder<ViewDataBinding>> {
    private var context: Context? = null
    private var mList : MutableList<DataX>? = null
    constructor(context: Context){
        this.context = context
    }
    private var mListener: IOnItemClickListener<DataX>? = null
    //item的样式
    private var mFootItem: Int = 0
    private var mDataItem: Int = 1

    //当前的状态
    private var mStateBean = LoadStateBean(Const.LOAD_SUCCESS, "")

    fun setListener(mListener: IOnItemClickListener<DataX>){
        this.mListener = mListener
    }
    fun setList(mList : MutableList<DataX>){
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ViewDataBinding> {
        if(viewType == mDataItem){
            var binding: AccountDetailAdapterBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.account_detail_adapter,parent,false)
            return BaseViewHolder(binding)
        }else{
            var mFootBinding: RecyclerviewLoadMoreBinding =
                DataBindingUtil.inflate(
                    LayoutInflater.from(context),
                    R.layout.recyclerview_load_more,
                    parent,
                    false
                )
            return BaseViewHolder(mFootBinding)
        }
    }


    override fun onBindViewHolder(holder: BaseViewHolder<ViewDataBinding>, position: Int) {
        if (holder.mDataBinding is AccountDetailAdapterBinding) {
            (holder.mDataBinding as AccountDetailAdapterBinding).dataBean = mList!![position]
            (holder.mDataBinding as AccountDetailAdapterBinding).executePendingBindings()
            holder.itemView.setOnClickListener {
                mListener?.itemClickListener(position,mList!![position])
            }
        } else if (holder.mDataBinding is RecyclerviewLoadMoreBinding) {
            Log.e("PPS"," mStateBean  ${mStateBean.state}" )
            (holder.mDataBinding as RecyclerviewLoadMoreBinding).stateBean = mStateBean
            (holder.mDataBinding as RecyclerviewLoadMoreBinding).executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return if (mList != null) {
            mList!!.size + 1
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == itemCount - 1) {
            return mFootItem
        } else {
            return mDataItem
        }
    }

    fun setStatBean(state: Int,msg: String = ""){
        mStateBean.message = msg
        mStateBean.state = state
        Log.e("PPS"," 刷新最后一个item  ${itemCount - 1}")
        notifyItemChanged(mList!!.size,0)
        //notifyDataSetChanged()
    }

    fun getLoadState(): Int{
        return mStateBean.state
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            val gridManager = manager
            gridManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    // 如果当前是footer的位置，那么该item占据2个单元格，正常情况下占据1个单元格
                    return if (getItemViewType(position) == mFootItem) gridManager.spanCount else 1
                }
            }
        }
    }
}