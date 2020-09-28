package com.example.mvvmproject.widget

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.to.aboomy.banner.Banner
import java.util.jar.Attributes

class MyNestScrollView : NestedScrollView {

    private var mRecyclerView: RecyclerView? = null
    private var mBanner: Banner? = null
    private var mConstraintLayout: ConstraintLayout? = null
    constructor(context: Context): this(context,null)

    constructor(context: Context, attributes: AttributeSet?): this(context,attributes,0)

    constructor(context: Context, attributes: AttributeSet?, def: Int) : super(context,attributes,def){

    }

    //在加载完成后回调
    override fun onFinishInflate() {
        super.onFinishInflate()
        mConstraintLayout = getChildAt(0) as ConstraintLayout?
        mBanner = mConstraintLayout?.getChildAt(0) as Banner?
        mRecyclerView = mConstraintLayout?.getChildAt(1) as RecyclerView?
    }

    /**
     * 测量的时候手动设置RecyclerView的大小
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val mBannerLp = mBanner?.layoutParams

        val mRvLp = mRecyclerView?.layoutParams
        mRvLp?.height = height - mBannerLp?.height!!
        mRecyclerView?.layoutParams = mRvLp
    }
}