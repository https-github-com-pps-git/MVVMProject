package com.example.mvvmproject.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.example.base.bean.LoadStateBean
import com.example.base.constant.Const
import com.example.base.databinding.RecyclerviewLoadMoreBinding
import com.example.base.listener.IOnItemClickListener
import com.example.base.viewholder.BaseViewHolder
import com.example.mvvmproject.R
import com.example.mvvmproject.databinding.HomeFragmentAdapterBinding
import com.example.mvvmproject.entity.DataX


class HomeFragmentAdapter : RecyclerView.Adapter<BaseViewHolder<ViewDataBinding>> {

    private var mContext: Context? = null
    private var mList: MutableList<DataX>? = null

    //item的样式
    private var mFootItem: Int = 0
    private var mDataItem: Int = 1

    //当前的状态
    private var mStateBean = LoadStateBean(Const.LOAD_SUCCESS, "")

    private var mListener: IOnItemClickListener<DataX>? = null

    constructor(mContext: Context?) {
        this.mContext = mContext
    }

    fun setList(mList: MutableList<DataX>?) {
        this.mList = mList
        notifyDataSetChanged()
    }

    fun setStatBean(state: Int,msg: String = ""){
        mStateBean.message = msg
        mStateBean.state = state
        Log.e("PPS"," 刷新最后一个item  ${itemCount - 1}")
        notifyItemChanged(mList!!.size,0)
        //notifyDataSetChanged()
    }

    fun setListener(mListener: IOnItemClickListener<DataX>?){
        this.mListener = mListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ViewDataBinding> {
        if (viewType == mDataItem) {
            var mDataBinding: HomeFragmentAdapterBinding =
                DataBindingUtil.inflate(
                    LayoutInflater.from(mContext),
                    R.layout.home_fragment_adapter,
                    parent,
                    false
                )
            return BaseViewHolder(mDataBinding)
        } else if (viewType == mFootItem) {
            var mFootBinding: RecyclerviewLoadMoreBinding =
                DataBindingUtil.inflate(
                    LayoutInflater.from(mContext),
                    R.layout.recyclerview_load_more,
                    parent,
                    false
                )
            return BaseViewHolder(mFootBinding)
        } else {
            return null!!
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

    override fun onBindViewHolder(holder: BaseViewHolder<ViewDataBinding>, position: Int) {
        if (holder.mDataBinding is HomeFragmentAdapterBinding) {
            (holder.mDataBinding as HomeFragmentAdapterBinding).data = mList!![position]
            (holder.mDataBinding as HomeFragmentAdapterBinding).executePendingBindings()
            holder.itemView.setOnClickListener {
                mListener?.itemClickListener(position,mList!![position])
            }

        } else if (holder.mDataBinding is RecyclerviewLoadMoreBinding) {
            Log.e("PPS"," mStateBean  ${mStateBean.state}" )
            (holder.mDataBinding as RecyclerviewLoadMoreBinding).stateBean = mStateBean
            (holder.mDataBinding as RecyclerviewLoadMoreBinding).executePendingBindings()
        }

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            val gridManager = manager
            gridManager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    // 如果当前是footer的位置，那么该item占据2个单元格，正常情况下占据1个单元格
                    return if (getItemViewType(position) == mFootItem) gridManager.spanCount else 1
                }
            }
        }
    }

    fun getLoadState(): Int{
        return mStateBean.state
    }

}