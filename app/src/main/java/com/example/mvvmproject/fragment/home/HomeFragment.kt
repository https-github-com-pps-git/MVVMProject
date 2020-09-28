package com.example.mvvmproject.fragment.home

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.Observer
import com.example.base.fragment.BaseMvvmFragment
import com.example.base.viewmodel.Cont
import com.example.base.viewmodel.StateBean
import com.example.mvvmproject.R
import com.example.mvvmproject.adapter.BannerAdapter
import com.example.mvvmproject.databinding.HomeFragmentBinding
import com.example.mvvmproject.entity.HomeBean
import com.to.aboomy.banner.IndicatorView
import com.to.aboomy.banner.ScaleInTransformer


class HomeFragment : BaseMvvmFragment<HomeFragmentBinding,HomeViewModel>() {

    private var mBanners = mutableListOf<String>()

    companion object{
        fun getInstance(): HomeFragment{
            val mFragment = HomeFragment()
            return mFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.home_fragment
    }

    override fun initMVVMData() {
        super.initMVVMData()
        //初始化banner
        initBanner()

        //第一次加载数据
        mViewModel?.refreshHomeData()

        mViewModel?.mFailedData?.observe(this,Observer<String>{
            toast(it)
        })

        mViewModel?.mStateData?.observe(this, Observer<StateBean> {
            when (it?.state) {
                Cont.ONSTART -> {
                    //开始获取数据

                }
                Cont.ONCOMPLETE -> {
                    //获取数据完成

                }
                Cont.ONFAILED -> {
                    //获取网络数据失败

                }
            }
        })

        //上拉更多获取成功的回调
        mViewModel?.mDataBean?.observe(this, Observer<HomeBean> {

        })

        //第一次加载数据的回调
        mViewModel?.mFirstData?.observe(this, Observer<HomeFragmentData> {
            it.mBanner.data.forEach {
                mBanners.add(it.imagePath)
            }
            mDataBinding.mBanner.setPages(mBanners)

        })
    }

    private fun initBanner() {

        //使用内置Indicator
        val qyIndicator = IndicatorView(mContext)
            .setIndicatorColor(Color.DKGRAY)
            .setIndicatorSelectorColor(Color.WHITE)
        mDataBinding.mBanner.setIndicator(qyIndicator).setHolderCreator(BannerAdapter())
            //设置左右页面露出来的宽度及item与item之间的宽度
            .setPageMargin(20, 10)
            //内置ScaleInTransformer，设置切换缩放动画
            .setPageTransformer(true, ScaleInTransformer())
    }


    override fun onResume() {
        super.onResume()
        mDataBinding.mBanner.startTurning()
    }

    override fun onStop() {
        super.onStop()
        mDataBinding.mBanner.stopTurning()
    }

    override fun initViewModel(): HomeViewModel {
        return HomeViewModel()
    }
}