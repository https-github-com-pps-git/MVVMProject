package com.example.mvvmproject.widget

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.example.mvvmproject.R
import java.util.*


/**
 * 自定义TagLayout
 */
class TagView : View {

    //自定义控件的宽度和高度
    private var mWidth: Int = 0
    private var mHeight: Int = 0

    //文字的内边距 padding
    private var padding: Int = 15

    //文字的大小
    private var testSize: Float = 18f

    //绘制的文字
    private var drawText: String = "Hello Word"

    //绘制文字的颜色
    private var mTextColor: Int = Color.parseColor("#FFFFFF")

    //默认文字的颜色
    private var mDefaultTextColor: Int =  Color.parseColor("#FFFFFF")

    //绘制背景颜色
    private var mBgColor: Int = 0

    //设置文字的颜色是否是随机
    private var isRandomColor = true

    //边框颜色
    private var mBorderColor: Int = 0

    //定义画笔
    private var mPaint: Paint? = null
    private var mTextRect: Rect? = null

    //绘制边框的大小
    private var mBorderSize: Int = 4

    //边框的样式
    private var mBorderStyle: Int

    //背景是否随机变化
    private var mBgAutoChange: Boolean = false

    //随机变化的颜色
    private var mRandomColor: Int = 0

    //记录传递的下标
    private var mIndex: Int = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, def: Int) : super(context, attrs, def) {

        val obtain = context.obtainStyledAttributes(attrs, R.styleable.TagView)
        isRandomColor = obtain?.getBoolean(R.styleable.TagView_textColorAutoChange, true)
        padding = obtain?.getDimension(
            R.styleable.TagView_textPadding,
            context.resources.getDimension(R.dimen.dp_14)
        ).toInt()
        mBgColor = obtain?.getColor(
            R.styleable.TagView_tagBackgroundColor,
            context.resources.getColor(R.color.white)
        )
        if (isRandomColor || mBgAutoChange) {
            mRandomColor = randomColor()
        }
        if (isRandomColor) {
            mTextColor = mRandomColor
        } else {
            mDefaultTextColor = obtain?.getColor(
                R.styleable.TagView_defaultColor,
                context.resources.getColor(R.color.white)
            )
            mTextColor = mDefaultTextColor
        }
        mBorderColor = obtain.getColor(
            R.styleable.TagView_borderColor,
            context.resources.getColor(R.color.default_color)
        )
        testSize = obtain.getDimension(
            R.styleable.TagView_textSize,
            context.resources.getDimension(R.dimen.sp_14)
        )
        mBgAutoChange = obtain.getBoolean(R.styleable.TagView_backgroundColorAutoChange, false)
        mBorderStyle = obtain.getInt(R.styleable.TagView_borderStyle, 0)
        obtain.recycle()
        mPaint = Paint()
        mPaint?.isAntiAlias = true
        mPaint?.textSize = testSize


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightMode = MeasureSpec.getMode(heightMeasureSpec)

        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        mTextRect = getTextRect(drawText)

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize
        } else {
            mWidth = 2 * padding + mTextRect!!.width()
        }

        /**
         * 应为英文的高度 有的会比中文的低 所以我们默认以中文的高度来测量  宽度就不受影响
         */
        mTextRect = getTextRect("大家好") //高度的时候采用中文的高度来测量
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize
        } else {
            mHeight = 2 * padding + mTextRect!!.height()
        }
        setMeasuredDimension(mWidth, mHeight)
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //清楚画布的抗拒
        canvas!!.drawFilter = PaintFlagsDrawFilter(
            0,
            Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG
        )
        //绘制背景矩形的样式
        drawBackRect(canvas)

        //绘制文字
        drawText(canvas)

        //绘制边框的样式
        drawBorderRect(canvas)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun drawBorderRect(canvas: Canvas?) {
        //绘制圆角矩形
        mPaint?.color = mBorderColor
        mPaint?.style = Paint.Style.STROKE
        mPaint?.strokeWidth = mBorderSize.toFloat()

        if (mBorderStyle == 0) {
            canvas?.drawRoundRect(
                0f + mBorderSize, 0f + mBorderSize, mWidth.toFloat() - mBorderSize,
                mHeight.toFloat() - mBorderSize, (mHeight / 2).toFloat(), (mHeight / 2).toFloat()
                , mPaint!!
            )
        } else {
            canvas?.drawRect(
                0f + mBorderSize, 0f + mBorderSize, mWidth.toFloat() - mBorderSize,
                mHeight.toFloat() - mBorderSize, mPaint!!
            )
        }
    }

    private fun drawText(canvas: Canvas?) {
        //绘制文字
        mPaint?.reset()
        mPaint?.textSize = testSize
        mPaint?.color = mTextColor
        //获取文字的长度
        var mTextWidth = mPaint?.measureText(drawText)
        //获取文字的高度

        //获取文字的高度的一半  (fontMetrics.descent - fontMetrics.ascent)/ 2 - fontMetrics.descent
        //计算在屏幕的中心位置 getHeight() / 2 + mTextHeight / 2 - fontMetrics.descent
        //      == getHeight() / 2 - fontMetrics.descent / 2 - fontMetrics.ascent /2
        //      == (getHeight() - fontMetrics.descent - fontMetrics.ascent) /2
        val fontMetrics = mPaint?.fontMetrics
        var mTextHeight = fontMetrics?.descent!! - fontMetrics?.ascent!! //正常文字的高度
        var top = (mHeight - fontMetrics?.descent!! - fontMetrics?.ascent!!) / 2
        canvas?.drawText(drawText, (mWidth - mTextWidth!!) / 2, top, mPaint!!)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun drawBackRect(canvas: Canvas?) {
        if (mBorderStyle == 0) {
            //这个表示是圆角背景
            mPaint?.style = Paint.Style.FILL
            if (mBgAutoChange) {
                mPaint?.color = mRandomColor
            } else {
                mPaint?.color = mBgColor
            }

            //绘制左边一个180°的圆弧
            canvas?.drawArc(
                0f + mBorderSize,
                0f + mBorderSize, (height).toFloat() - mBorderSize, mHeight.toFloat() - mBorderSize,
                90f, 180f, false, mPaint!!
            )

            //绘制中间的矩形
            canvas?.drawRect(
                (height / 2).toFloat(),
                mBorderSize.toFloat(),
                mWidth - (height / 2).toFloat(),
                (mHeight - mBorderSize).toFloat(),
                mPaint!!
            )

            //绘制右边一个180°的圆弧
            canvas?.drawArc(
                width - (height).toFloat(),
                0f + mBorderSize, (width).toFloat() - mBorderSize, (height).toFloat() - mBorderSize,
                270f, 180f, true, mPaint!!
            )
        } else {
            //绘制背景
            mPaint?.style = Paint.Style.FILL
            if (mBgAutoChange) {
                mPaint?.color = mRandomColor
            } else {
                mPaint?.color = mBgColor
            }
            canvas?.drawRect(
                mBorderSize.toFloat(),
                mBorderSize.toFloat(), (mWidth - mBorderSize).toFloat(),
                (mHeight - mBorderSize).toFloat(), mPaint!!
            )

        }
    }

    fun setDrawText(text: String) {
        drawText = text
        invalidate()
    }

    fun getDrawText(): String {
        return drawText
    }

    //获取文字的宽和高
    private fun getTextRect(text: String): Rect {
        var rect = Rect()
        mPaint?.getTextBounds(text, 0, text.length, rect)
        return rect
    }

    //随机产生一个颜色
    private fun randomColor(): Int {
        var random: Random = Random()
        val r: Int = random.nextInt(256)
        val g: Int = random.nextInt(256)
        val b: Int = random.nextInt(256)
        return Color.argb(255, r, g, b)
    }


    fun setBorderStyle(style: Int){
        this.mBorderStyle = style
        invalidate()
    }

    fun setTextAutoChange(flag: Boolean){
        this.isRandomColor = flag
        if (isRandomColor){
            mTextColor = mRandomColor
        }else {
            mTextColor = mDefaultTextColor
        }
        invalidate()
    }

    fun setBgAutoChange(flag: Boolean){
        this.mBgAutoChange = flag
        invalidate()
    }

    fun setIndex(index: Int){
        this.mIndex = index
    }

    fun getIndex(): Int{
        return mIndex
    }

    fun setBgColor(color: Int){
        this.mBgColor = color
        invalidate()
    }

    fun setTextColor(color: Int){
        this.mTextColor = color
        invalidate()
    }

}