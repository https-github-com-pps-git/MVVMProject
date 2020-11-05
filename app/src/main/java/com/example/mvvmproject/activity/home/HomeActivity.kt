package com.example.mvvmproject.activity.home

import android.Manifest
import android.os.Bundle
import android.os.Debug
import android.os.Looper
import android.os.MessageQueue
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.example.base.activity.BaseActivity
import com.example.mvvmproject.R
import com.example.mvvmproject.databinding.ActivityHomeBinding
import com.example.mvvmproject.fragment.account.AccountFragment
import com.example.mvvmproject.fragment.home.HomeFragment
import com.example.mvvmproject.fragment.mine.MineFragment
import com.example.mvvmproject.fragment.search.HotSearchFragment
import com.example.mvvmproject.fragment.setup.SetUpFragment

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    private var mFragments = mutableListOf<Fragment>()
    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        //Debug.stopMethodTracing()
    }

    override fun initData() {
        super.initData()
        //隐藏返回键
        mDataBinding.mTopLayout.mBackIv.visibility = View.GONE

        //设置头部标题文字
        mDataBinding.mTopLayout.mTitleTv.text = resources.getString(R.string.home)

        //申请权限
        initPermissions()


        initFragment()
        mDataBinding.mViewpager.offscreenPageLimit = mFragments.size
        mDataBinding.mViewpager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return mFragments[position]
            }

            override fun getCount(): Int {
                return mFragments.size
            }

        }

        mDataBinding.mBottomNavigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.mHome -> {
                    //首页
                    mDataBinding.mTopLayout.mTitleTv.text = resources.getString(R.string.home)
                    mDataBinding.mViewpager.currentItem = 0
                }
                R.id.mSearch -> {
                    //热搜
                    mDataBinding.mTopLayout.mTitleTv.text = resources.getString(R.string.hot_search)
                    mDataBinding.mViewpager.currentItem = 1
                }
                R.id.mSetup -> {
                    //体系
                    mDataBinding.mTopLayout.mTitleTv.text = resources.getString(R.string.setup)
                    mDataBinding.mViewpager.currentItem = 2
                }
                R.id.mAccount -> {
                    //公众号
                    mDataBinding.mTopLayout.mTitleTv.text =
                        resources.getString(R.string.official_account)
                    mDataBinding.mViewpager.currentItem = 3
                }
                R.id.mMine -> {
                    //我的
                    mDataBinding.mTopLayout.mTitleTv.text = resources.getString(R.string.mine)
                    mDataBinding.mViewpager.currentItem = 4
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

        mDataBinding.mViewpager.offscreenPageLimit = mFragments.size

        mDataBinding.mViewpager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        //首页
                        mDataBinding.mTopLayout.mTitleTv.text = resources.getString(R.string.home)
                        mDataBinding.mBottomNavigation.selectedItemId = R.id.mHome
                    }
                    1 -> {
                        //热搜
                        mDataBinding.mTopLayout.mTitleTv.text =
                            resources.getString(R.string.hot_search)
                        mDataBinding.mBottomNavigation.selectedItemId = R.id.mSearch
                    }
                    2 -> {
                        //体系
                        mDataBinding.mTopLayout.mTitleTv.text = resources.getString(R.string.setup)
                        mDataBinding.mBottomNavigation.selectedItemId = R.id.mSetup
                    }
                    3 -> {
                        //公众号
                        mDataBinding.mTopLayout.mTitleTv.text =
                            resources.getString(R.string.official_account)
                        mDataBinding.mBottomNavigation.selectedItemId = R.id.mAccount
                    }
                    4 -> {
                        //我的
                        mDataBinding.mTopLayout.mTitleTv.text = resources.getString(R.string.mine)
                        mDataBinding.mBottomNavigation.selectedItemId = R.id.mMine
                    }
                }
            }
        })

        setBottomLongEnabled(R.id.mHome, R.id.mSearch, R.id.mSetup, R.id.mAccount, R.id.mMine)
    }

    private fun initPermissions() {

        PermissionUtils.permission(PermissionConstants.STORAGE)
            .callback(object : PermissionUtils.SingleCallback {
                override fun callback(
                    isAllGranted: Boolean,
                    granted: MutableList<String>,
                    deniedForever: MutableList<String>,
                    denied: MutableList<String>
                ) {
                    Log.e("PPS", " $isAllGranted       ")
                    granted.forEach {
                        //这个是权限通过
                        Log.e("PPS", "  granted  $it")
                    }

                    denied.forEach {
                        //这个是权限未通过
                        Log.e("PPS", "  denied  $it")
                    }
                }

            }).request()

    }

    private fun setBottomLongEnabled(vararg ids: Int) {
        // 遍历拦截 bottomnNavigatioonView 的长按吐司事件
        var menuView = mDataBinding.mBottomNavigation.getChildAt(0)//举个例子，第一个tab的拦截，其它的同理
        ids.forEach {
            menuView.findViewById<View>(it)
                .setOnLongClickListener {
                    //拦截长按事件
                    true
                }
        }

    }

    private fun initFragment() {
        mFragments.add(HomeFragment.getInstance())
        mFragments.add(HotSearchFragment.getInstance())
        mFragments.add(SetUpFragment.getInstance())
        mFragments.add(AccountFragment.getInstance())
        mFragments.add(MineFragment.getInstance())

        Looper.myQueue().addIdleHandler(object : MessageQueue.IdleHandler{
            override fun queueIdle(): Boolean {
                //在Handler空闲的时候执行
                return true
            }

        })
    }
}