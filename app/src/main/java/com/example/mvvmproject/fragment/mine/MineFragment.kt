package com.example.mvvmproject.fragment.mine

import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.base.fragment.BaseFragment
import com.example.mvvmproject.R
import com.example.mvvmproject.activity.other.OtherActivity
import com.example.mvvmproject.databinding.HomeFragmentBinding
import com.example.mvvmproject.databinding.MineFragmentBinding

class MineFragment : BaseFragment<MineFragmentBinding>() {

    companion object{
        fun getInstance(): MineFragment{
            val mFragment = MineFragment()
            return mFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.mine_fragment
    }

    override fun initData() {
        super.initData()
        //startActivity(Intent(mContext,OtherActivity::class.java))
    }

}