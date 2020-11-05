package com.example.mvvmproject.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.base.viewholder.BaseViewHolder
import com.example.mvvmproject.R
import com.example.mvvmproject.databinding.SetupAdapterBinding
import com.example.mvvmproject.fragment.setup.SetUpBean
import com.example.mvvmproject.fragment.setup.SetUpChildren
import com.example.mvvmproject.fragment.setup.SetUpData
import com.example.mvvmproject.listener.ITagClickListener
import java.util.*

class StateUpAdapter : RecyclerView.Adapter<BaseViewHolder<SetupAdapterBinding>> {
    private var context: Context? = null
    private var mList: List<SetUpData>? = null
    private var map: MutableMap<Int,MutableList<Int>> = mutableMapOf()
    constructor(context: Context) {
        this.context = context
    }

    fun setList(mList: List<SetUpData>) {
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<SetupAdapterBinding> {
        var binding: SetupAdapterBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.setup_adapter,parent,false)
        return BaseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if (mList != null){
            return mList!!.size
        }else{
            return 0
        }
    }
    var items = mutableListOf<String>()
    override fun onBindViewHolder(holder: BaseViewHolder<SetupAdapterBinding>, position: Int) {
        var hasSaveColor = true
        var colors = map[position]
        if (colors == null){
            colors = mutableListOf()
            hasSaveColor = false
        }
        items.clear()
        mList!![position].children.forEach {
            if (!hasSaveColor){
                colors.add(randomColor())
            }
            items.add(it.name)
        }
        if (!hasSaveColor){
            map[position] = colors
        }
        holder.mDataBinding.text1.text = mList!![position].name
        holder.mDataBinding.mFlowLayout.setViews(items,false,true,1,colors)
        holder.mDataBinding.mFlowLayout.addTagItemListener(object : ITagClickListener{
            override fun onTagClickListener(text: String, index: Int) {
                Toast.makeText(context,"第${position}下面的item中的第${index}个下标,内容是$text",Toast.LENGTH_LONG).show()
            }

        })
        holder.mDataBinding.executePendingBindings()
    }


    //随机产生一个颜色
    private fun randomColor(): Int {
        var random: Random = Random()
        val r: Int = random.nextInt(256)
        val g: Int = random.nextInt(256)
        val b: Int = random.nextInt(256)
        return Color.argb(255, r, g, b)
    }

}