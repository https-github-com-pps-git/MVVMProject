package com.example.mvvmproject.widget

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.mvvmproject.R
import com.example.mvvmproject.listener.ITagClickListener


/**
 * 自定义流式布局
 */
class FlowLayout : ViewGroup, View.OnClickListener {

    //这个viewGroup的宽度和高度
    private var mWidth: Float = 0f
    private var mHeight: Float = 0f

    //手机的宽高
    private var mPhoneWidth: Int = 0
    private var mPhoneHeight: Int = 0

    //定义一个集合装载每一行的子View的集合
    private var mLineList = mutableListOf<MutableList<View>>()
    private var mViewList = mutableListOf<View>()

    //记录每一行子view的最大的高度
    private var mLineViewHeight = mutableListOf<Int>()

    //设置每个子view之间的间隔
    private var mMarginW: Int = 10
    private var mMarginH: Int = 10

    //测量的高度
    private var mMeasureHeight: Int = 0

    //用来记录测量的宽度 来判断是否需要换行
    private var mMeasureWidth: Int = 0

    //每一行的最大高度
    private var mLineMaxHeight: Int = 0

    private var listener: ITagClickListener? = null

    //定义一个map集合来保存在recyclerview的时候颜色错乱的问题
    private var colorMap: LinkedHashMap<String,MutableList<Int>> = LinkedHashMap()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, def: Int) : super(context, attrs, def) {
        val obtain = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout)
        mMarginW = obtain.getDimension(R.styleable.FlowLayout_horizontalWidth, 10f).toInt()
        mMarginH = obtain.getDimension(R.styleable.FlowLayout_verticalHeight, 10f).toInt()

        obtain.recycle()
    }

    fun setMarginW(width: Int) {
        this.mMarginW = width
        invalidate()
    }

    fun setMarginH(height: Int) {
        this.mMarginH = height
        invalidate()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        //获取屏幕的大小

        val windowManager =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        mPhoneWidth = displayMetrics.widthPixels
        mPhoneHeight = displayMetrics.heightPixels

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mMeasureHeight = 0
        mLineList.clear()
        mLineViewHeight.clear()
        mViewList.clear()
        //获取父类传递过来的模式和大小
        val widthMode: Int = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode: Int = MeasureSpec.getMode(heightMeasureSpec)
        var widthSize: Int = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize: Int = MeasureSpec.getSize(heightMeasureSpec)

        //判断当前的模式
        if (widthMode === MeasureSpec.EXACTLY) {
            //表示精确模式
            mWidth = widthSize.toFloat()
        } else {
            //表示需要测量 应为这个是流式布局 宽度必须是知道的 所有在不知道的时候 我们默认给它屏幕的宽度
            mWidth = mPhoneWidth.toFloat()
        }

        //开始测量自View的宽度和高度
        for (i in 0 until childCount) {
            //便利子view
            var childView = getChildAt(i)
            //测量子view 这里我们不用考虑 外边距margin
            measureChild(childView, widthMeasureSpec, heightMeasureSpec)
            //获取这个View的宽度和高度
            var width = childView.measuredWidth
            var height = childView.measuredHeight
            //累加测量view的大小 在加每个view的水平间隔
            mMeasureWidth += width + mMarginW
            //应为第一次的时候不管他怎么样要为第一行
            if (mMeasureWidth > mWidth - paddingLeft - paddingRight) {
                //换行
                lineBreak(width)
            }
            //获取每一行的最大高度
            mLineMaxHeight = Math.max(mLineMaxHeight, height)
            mViewList.add(childView)
        }
        //测量结束之后呢 应为最后一行是始终不会大于mWidth 所以单独在把最后一行的高度加进去
        lineBreak(-1)

        if (heightMode === MeasureSpec.EXACTLY) {
            mHeight = heightSize.toFloat()
        } else {
            mHeight = mMeasureHeight.toFloat() + paddingTop + paddingBottom
        }
        setMeasuredDimension(mWidth.toInt(), mHeight.toInt())


    }

    private fun lineBreak(width: Int) {
        if (width != -1) {
            mMeasureHeight += mLineMaxHeight + mMarginH//换行的话就去累加高度
        } else {
            mMeasureHeight += mLineMaxHeight
        }
        mLineViewHeight.add(mLineMaxHeight)//讲上一行的高度保存起来
        mLineList.add(mViewList)//把这一行的所有view添加到一个集合中去


        //一行结束
        mMeasureWidth = width
        mLineMaxHeight = 0
        if (width != -1) {
            mViewList = mutableListOf<View>()
        }


    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {

        var lineSize: Int = mLineList.size
        var left: Int = paddingLeft
        var top: Int = paddingTop
        for (i in 0 until lineSize) {
            //获取每一行的高度
            val lineHeight = mLineViewHeight[i]
            val mutableList: MutableList<View> = mLineList[i]
            left = paddingLeft
            mutableList.forEach {
                //获取view的高度和宽度
                var viewWidth = it.measuredWidth
                var viewHeight = it.measuredHeight
                it.layout(left, top, left + viewWidth, top + viewHeight)
                left += viewWidth + mMarginW
            }
            top += lineHeight + mMarginH
        }
    }

    fun addTagItemListener(listener: ITagClickListener) {
        this.listener = listener
    }

    /**
     * 设置FlowLayout里面的子View
     */
     fun setViews(mList: MutableList<String>, textAutoChange: Boolean = true,
                  bgAutoChange: Boolean = false,style: Int = 0,mColors: List<Int>? = null) {
        if (mColors == null){
            //表示不是RecyclerView下面使用的FlowLayout
            for (i in 0 until mList.size) {
                var tagView = TagView(context)
                tagView.setDrawText(mList[i])
                tagView.setBorderStyle(style)
                tagView.setBgAutoChange(bgAutoChange)
                tagView.setTextAutoChange(textAutoChange)
                tagView.setIndex(i)
                tagView.setOnClickListener(this)
                addView(tagView)
            }
        }else{
            //表示是RecyclerView下面使用的FlowLayout
            removeAllViews()
            for (i in 0 until mList.size) {
                var tagView = TagView(context)
                tagView.setDrawText(mList[i])
                tagView.setBorderStyle(style)
                tagView.setBgAutoChange(false)
                tagView.setTextAutoChange(false)
                //只有在颜色需要自动改变的时候 使用外部提供的
                if (bgAutoChange) {
                    tagView.setBgColor(mColors[i])
                } else if (textAutoChange){
                    tagView.setTextColor(mColors[i])
                }
                tagView.setIndex(i)
                tagView.setOnClickListener(this)
                addView(tagView)
            }

        }


    }

    override fun onClick(p0: View?) {
        if (p0 is TagView) {
            listener?.onTagClickListener(p0.getDrawText(), p0.getIndex())
        }
    }

}