package com.example.mvvmproject.fragment.home

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.base.constant.Const
import com.example.base.fragment.BaseMvvmFragment
import com.example.base.listener.EndlessRecyclerOnScrollListener
import com.example.base.listener.IOnItemClickListener
import com.example.base.viewmodel.Cont
import com.example.base.viewmodel.StateBean
import com.example.mvvmproject.R
import com.example.mvvmproject.activity.photo.PhotoActivity
import com.example.mvvmproject.activity.webview.WebViewActivity
import com.example.mvvmproject.adapter.BannerAdapter
import com.example.mvvmproject.adapter.HomeFragmentAdapter
import com.example.mvvmproject.databinding.HomeFragmentBinding
import com.example.mvvmproject.entity.BannerBean
import com.example.mvvmproject.entity.DataX
import com.example.mvvmproject.entity.HomeBean
import com.to.aboomy.banner.IndicatorView
import com.to.aboomy.banner.ScaleInTransformer


class HomeFragment : BaseMvvmFragment<HomeFragmentBinding, HomeViewModel>() {
    //Banner的数据集合
    private var mBanners = mutableListOf<String>()
    private var mBannerBean: BannerBean? = null
    //homefragment 界面的适配器
    private var mHomeAdapter: HomeFragmentAdapter? = null

    //home的数据集合
    private var mDatas = mutableListOf<DataX>()


    companion object {
        fun getInstance(): HomeFragment {
            val mFragment = HomeFragment()
            return mFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.home_fragment
    }



    override fun initData() {
        super.initData()

        //初始化banner
        initBanner()

        //设置适配器
        initAdapter()
        if (isVisibleToUser) {//只有在显示的时候才去获取数据
            //第一次加载数据
            isFirstCreate = true
            mViewModel?.firstHomeData()
        }
        //逻辑错误信息
        mViewModel?.mFailedData?.observe(this, Observer<String> {
            toast(it)
            mHomeAdapter?.setStatBean(Const.LOAD_FAILED, it)
        })

        //网络请求错误信息
        mViewModel?.mStateData?.observe(this, Observer<StateBean> {
            when (it?.state) {
                Cont.ONSTART -> {
                    //开始获取数据
                    if (it.isFirst) {
                        Log.e("PPS", "第一次获取数据")
                    }
                }
                Cont.ONCOMPLETE -> {
                    //获取数据完成

                }
                Cont.ONFAILED -> {
                    //获取网络数据失败
                    mHomeAdapter?.setStatBean(Const.LOAD_FAILED, it?.message)
                }
                Cont.LOAD_DATA_END -> {
                    //加载到底部了
                    mHomeAdapter?.setStatBean(Const.LOAD_END)
                }
            }
        })

        //上拉更多获取成功的回调
        mViewModel?.mDataBean?.observe(this, Observer<HomeBean> {
            mDatas.addAll(it.data.datas)
            mHomeAdapter?.setList(mDatas)
            mHomeAdapter?.setStatBean(Const.LOAD_SUCCESS)
        })

        //第一次加载数据的回调
        mViewModel?.mFirstData?.observe(this, Observer<HomeFragmentData> {
            mBannerBean = it.mBanner
            it.mBanner.data.forEach {
                mBanners.add(it.imagePath)
            }
            mDataBinding.mBanner.setPages(mBanners)

            mDatas.addAll(it.mHomeData.data.datas)
            mHomeAdapter?.setList(mDatas)
        })

    }

    @SuppressLint("RestrictedApi")
    private fun initAdapter() {
        mHomeAdapter = HomeFragmentAdapter(mContext)
        mHomeAdapter?.setList(mDatas)
        mDataBinding.mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mDataBinding.mRecyclerView.adapter = mHomeAdapter
        mDataBinding.mRecyclerView.isNestedScrollingEnabled = false
        /*
        //由于Recyclerview被添加到了NestScrollView中就禁用Recyclerview的滑动了
        mDataBinding.mRecyclerView.addOnScrollListener(object : EndlessRecyclerOnScrollListener(){
            override fun onLoadMore() {
                Log.e("PPS","加载更多")
            }

        })
        */

        mDataBinding.mNestScrollView
            .setOnScrollChangeListener(
                NestedScrollView.OnScrollChangeListener
                { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (v!!.scrollY + v!!.height == v!!.computeVerticalScrollRange()) {
                if (mHomeAdapter?.getLoadState() == Const.LOAD_SUCCESS) {
                    Log.e("PPS", "加载更多")
                    mHomeAdapter?.setStatBean(Const.LOADING)
                    mViewModel?.loadMoreData()
                }
            }
        })

        mHomeAdapter?.setListener(object : IOnItemClickListener<DataX> {
            override fun itemClickListener(position: Int, data: DataX?) {
                val intent = Intent(mContext, WebViewActivity::class.java)
                intent.putExtra("link",data!!.link)
                startActivity(intent)
            }

        })
    }

    var mBannerAdapter = BannerAdapter()
    private fun initBanner() {

        //使用内置Indicator
        val qyIndicator = IndicatorView(mContext)
            .setIndicatorColor(Color.DKGRAY)
            .setIndicatorSelectorColor(Color.WHITE)
        mDataBinding.mBanner.setIndicator(qyIndicator).setHolderCreator(mBannerAdapter)
            //设置左右页面露出来的宽度及item与item之间的宽度
            .setPageMargin(30, 20)
            //内置ScaleInTransformer，设置切换缩放动画
            .setPageTransformer(true, ScaleInTransformer())

        mBannerAdapter.setItemClickListener(object :IOnItemClickListener<Any>{
            override fun itemClickListener(position: Int, data: Any?) {
                val intent = Intent(mContext, WebViewActivity::class.java)
                intent.putExtra("link", mBannerBean!!.data[position].url)
                startActivity(intent)
            }

        })
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

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && !isFirstCreate){
            isFirstCreate = true
            mViewModel?.firstHomeData()
        }
    }

}