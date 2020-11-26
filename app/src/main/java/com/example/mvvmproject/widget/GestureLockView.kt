package com.example.mvvmproject.widget

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.mvvmproject.widget.entity.LinePoint
import com.example.mvvmproject.widget.entity.PointBean

/**
 * 自定义手势解锁的9宫格View
 *
 * 这9个点的坐标顺序
 * 1  2  3
 * 4  5  6
 * 7  8  9
 */
class GestureLockView : View {

    /**
     *空间的宽和高
     */
    private var mWidth: Int = 0
    private var mHeight: Int = 0;

    //设置9个点的最大的半径
    private var mPointR: Float = 50f

    //每个点之间的间距
    private var mPointMargin: Float = 50f

    //每个点中间的小点的半径
    private var mSmallPointR: Float = 20f

    //定义每个点连线的宽度
    private var mPointLineHeight: Float = 10f

    //定义大点点的默认的颜色
    private var mPointDefaultColor: Int = 0

    //定义大点点的选中的颜色
    private var mPointSelectColor: Int = Color.parseColor("#3399CC")
    //定义大点点错误状态下的颜色
    private var mPointErrorColor: Int = Color.parseColor("#CC3333")

    private var mPointColor = mPointSelectColor

    //定义小圆圈选中的颜色
    private var mSmallPointColor: Int = Color.parseColor("#0033FF")
    //定义小圆圈错误状态的颜色
    private var mSmallErrorColor: Int = Color.parseColor("#FF00FF")

    private var mSmallColor = mSmallPointColor

    //定义9个点坐标的集合
    private var mPointList = mutableListOf<PointBean>()

    //定义一根画笔 绘制外面的大圆
    private lateinit var mPaint: Paint

    //定义一根画笔 绘制里面的小圆
    private var mSmallPaint: Paint

    //左右和上下的间隔
    private var mSurplusWidth: Int = 0
    private var mSurplusHeight: Int = 0

    //创建一个集合用来装载手指滑动的路径
    private var mLinePointLists = mutableListOf<LinePoint>()

    //定义一根画笔 绘制 线条
    private var mPathPaint: Paint
    private var mLinePath: Path = Path()
    private var mSumSelectSize = 0;

    //选中的点点的 集合
    private var mSelectIndexList = mutableListOf<Int>()
    //裁剪的path
    private var mClipPath: Path = Path()

    private var mLineMaxSize = 4

    //最少选中几个按钮
    private var mMinSelectSize = 3

   constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, def: Int) : super(context, attrs, def) {
        mPaint = Paint()
        mPaint.color = Color.WHITE
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 2f
        mPaint.isAntiAlias = true

        mSmallPaint = Paint()
        mSmallPaint.color = mSmallColor
        mSmallPaint.style = Paint.Style.FILL
        mSmallPaint.isAntiAlias = true

        mPathPaint = Paint()
        mPathPaint.color = mSmallColor//Color.parseColor("#FF9999")
        mPathPaint.style = Paint.Style.STROKE
        mPathPaint.isAntiAlias = true
        mPathPaint.strokeWidth = mSmallPointR / 2
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        //手动计算这个空间实际的宽高
        var mSumWidth = (mLineMaxSize * mPointR * 2 + mPointMargin * 2).toInt()
        if (widthMode == MeasureSpec.EXACTLY) {
            //表示精确模式
            mWidth = widthSize
        } else {
            mWidth = mSumWidth
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize
        } else {
            mHeight = mSumWidth
        }


        /**
         * 这里是已经测量完成
         * 定义9个点的坐标
         */
        //求手机屏幕的宽度 和高度 减去总长还剩下的距离
        mSurplusWidth = mWidth - mSumWidth
        mSurplusHeight = mHeight - mSumWidth

        mPointList.clear()
        //这里就相当于你把它看作成一个二维数组
        for (i in 0 until mLineMaxSize) {
            for (j in 0 until mLineMaxSize) {
                /**
                 * 这里的x = 剩下的间距/2 + 一个圆的半径 + (圆的直径 + 每个圆的边距) * j
                 * 这里的y = 剩下的间距/2 + 一个圆的半径 + (圆的直径 + 每个圆的边距) * i
                 */
                var x = mSurplusWidth / 2 + mPointR + (mPointR * 2 + mPointMargin) * j
                var y = mSurplusHeight / 2 + mPointR + (mPointR * 2 + mPointMargin) * i
                mPointList.add(
                    PointBean(
                        x,
                        y
                    )
                )
            }
        }

        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //清楚画布的抗拒
        canvas!!.drawFilter = PaintFlagsDrawFilter(
            0,
            Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG
        )

        canvas!!.drawColor(Color.parseColor("#000033"))


        for (i in 0 until mPointList.size) {
            var it = mPointList[i]
            if (it.isSelect) {
                mPaint.color = mPointColor
                mPaint.style = Paint.Style.FILL
                //mClipPath.moveTo(it.xPoint,it.yPoint)
                //mClipPath.addCircle(it.xPoint,it.yPoint,mSmallPointR, Path.Direction.CCW)
                //canvas?.save()
                //canvas?.clipPath(mClipPath)
                //canvas?.restore()
                Log.e("PPS","手指选中了的下表 $i")
            } else {
                mPaint.color = Color.WHITE
                mPaint.style = Paint.Style.STROKE
            }
            canvas?.drawCircle(it.xPoint, it.yPoint, mPointR, mPaint)
        }

        mLinePath.reset()
        //Log.e("PPS", " 划线的坐标大小 ${mLinePointLists.size} ")
        mPathPaint.color = mSmallColor
        for (i in 0 until mLinePointLists.size) {
            var it = mLinePointLists[i]
            //Log.e("PPS", " 划线的坐标 ${it.xPoint}   :  ${it.yPoint} ")
            if (i == 0) {
                mLinePath.moveTo(it.xPoint, it.yPoint)
            } else {
                mLinePath.lineTo(it.xPoint, it.yPoint)
            }
        }

        canvas?.drawPath(mLinePath, mPathPaint)

        for (i in 0 until mPointList.size) {
            var it = mPointList[i]
            if (it.isSelect) {
                mSmallPaint.color = mSmallColor
                mSmallPaint.style = Paint.Style.FILL
                canvas?.drawCircle(it.xPoint, it.yPoint, mSmallPointR, mSmallPaint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        //重点 在这里 怎么判断手指按下的位置是否在这个9个点上
        /**
         * 其实就根据勾股定理来说 在圆弧上面的点 x*x + y*y = r*r 最大的时候
         *
         * 也就是说判断一个点是否在某个圆内 x*x + y*y <= r*r
         *
         * 我们在简化一下 就用作一个正方形来判断
         */
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                mHandler.removeCallbacksAndMessages(null)//清除所有的消息
                rest()
                var downX = event?.x
                var downY = event?.y
                var flag = isInPoint(downX, downY, false)
                if (flag) {
                    invalidate()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                parent.requestDisallowInterceptTouchEvent(false)
                if (mLinePointLists.size > 0) {
                    if (isInPoint(event?.x, event?.y, true)) {
                        //表示选中了一个
                    } else {
                        if (mLinePointLists.size == mSumSelectSize) {
                            mLinePointLists.add(LinePoint(event?.x, event?.y))
                        } else {
                            mLinePointLists[mSumSelectSize] = LinePoint(event?.x, event?.y)
                        }
                    }
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                //判断抬起的这个点是否在这9个点的坐标上面 不在的话把最后一个删除掉
                if (!isInPoint(event?.x, event?.y, true)) {
                    var index = mLinePointLists.size - 1
                    if (index > 0) {
                        mLinePointLists.removeAt(index)
                    }
                    invalidate()
                }

                var password: String = ""
                var length = mSelectIndexList.size
                for (i in 0 until length){
                    if (i == length - 1){
                        password += "${mSelectIndexList[i]}"
                    }else{
                        password += "${mSelectIndexList[i]}-"
                    }
                }
                if (mSelectIndexList.size < mMinSelectSize + 1){
                    //表示选中的个数小于三
                    mSmallColor = mSmallErrorColor
                    mPointColor = mPointErrorColor
                }
                Log.e("PPS"," 密码 + ${password} ")
                //抬起的时候判断密码是否正确 并且1秒后恢复原样
                mHandler.removeCallbacksAndMessages(null)//清除所有的消息
                var msg = Message.obtain()
                msg.what = 0x123
                mHandler.sendMessageDelayed(msg, 1000)
            }
        }
        return true
    }


    /**
     * 判断手指的位置是否在点点上面
     * @param x X轴坐标
     * @param y Y轴坐标
     * @param isMove 是否是处于移动的状态
     */
    fun isInPoint(x: Float, y: Float, isMove: Boolean): Boolean {
        //1.判断手指按下的位置是在 这个九宫格内
        if (isMove) {
            //手指处于移动状态
            var length = mPointList.size
            for (i in 0 until length){
                var it = mPointList[i]
                if ((x > it.xPoint - mPointR + 10 && x < it.xPoint + mPointR - 10) &&
                    (y > it.yPoint - mPointR + 10 && y < it.yPoint + mPointR - 10)
                ) {
                    if (!it.isSelect) {
                        it.isSelect = true
                        //表示手指按下正在在某个点点的位置  把这个点点的位置设置为path的起点

                        //已知2个点的坐标 求某个点是否在这条直线上面的公式 (x-xA)(yB-yA)=(y-yA)(xB-xA)
                        for (j in 0 until length) {
                            val pointBean = mPointList[j]
                            if (mSumSelectSize == 0){
                                break
                            }
                            var lastPoint = mLinePointLists[mSumSelectSize - 1]
                            var indexPoint = LinePoint(it.xPoint, it.yPoint)
                            //把本身的2个点除掉
                            if ((pointBean.xPoint == lastPoint.xPoint &&
                                        pointBean.yPoint == lastPoint.yPoint) ||
                                (pointBean.xPoint == indexPoint.xPoint &&
                                        pointBean.yPoint == indexPoint.yPoint)){
                                continue
                            }else{

                                if ((((pointBean.xPoint - lastPoint.xPoint) *
                                            (indexPoint.yPoint - lastPoint.yPoint) ==
                                            (pointBean.yPoint - lastPoint.yPoint) *
                                            (indexPoint.xPoint - lastPoint.xPoint))) &&
                                    (pointBean.xPoint >= Math.min(indexPoint.xPoint,lastPoint.xPoint)
                                            && pointBean.xPoint <= Math.max(indexPoint.xPoint,lastPoint.xPoint)
                                            && pointBean.yPoint >= Math.min(indexPoint.yPoint,lastPoint.yPoint)
                                            &&  pointBean.yPoint <= Math.max(indexPoint.yPoint,lastPoint.yPoint))
                                ) {
                                    if(!pointBean.isSelect) {
                                        mSelectIndexList.add(j)
                                        pointBean.isSelect = true
                                    }
                                }
                            }
                        }
                        mSelectIndexList.add(i)
                        if (mSumSelectSize == 0){
                            mLinePointLists.add(LinePoint(it.xPoint, it.yPoint))
                        }else {
                            mLinePointLists[mSumSelectSize] = LinePoint(it.xPoint, it.yPoint)
                        }
                        mSumSelectSize++

                        return true
                    } else {
                        return false
                    }
                }
            }
        } else {
            //手指处于按下的状态
            if ((x > mSurplusWidth / 2 && x < mWidth - mSurplusWidth / 2)
                && (y > mSurplusHeight / 2 && y < mHeight - mSurplusHeight / 2)
            ) {
                Log.e("PPS"," 手指按下 ")
                var length = mPointList.size
                for (i in 0 until length){
                    var it = mPointList[i]
                    if ((x > it.xPoint - mPointR && x < it.xPoint + mPointR) &&
                        (y > it.yPoint - mPointR && y < it.yPoint + mPointR)
                    ) {
                        Log.e("PPS"," 手指按下  判定完成")
                        mSelectIndexList.add(i)
                        it.isSelect = true
                        //表示手指按下正在在某个点点的位置  把这个点点的位置设置为path的起点
                        mSumSelectSize++
                        mLinePointLists.add(LinePoint(it.xPoint, it.yPoint))
                        return true
                    }
                }
            }
        }
        return false
    }

    private var mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0x123) {
                Log.e("PPS", "重置")
                rest()
            }
        }
    }

    private fun rest() {
        mSmallColor = mSmallPointColor
        mPointColor = mPointSelectColor
        mSumSelectSize = 0
        mLinePointLists.clear()

        mSelectIndexList.clear()
        mPointList.forEach {
            it.isSelect = false
            invalidate()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mHandler.removeCallbacksAndMessages("")
    }
}