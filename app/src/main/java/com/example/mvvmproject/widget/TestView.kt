package com.example.mvvmproject.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi

class TestView : View {
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, def: Int) : super(context, attrs, def) {

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.save()
        canvas?.drawColor(Color.CYAN)
        canvas?.clipRect(Rect(50,50,400,400))
        canvas?.drawColor(Color.RED)


        canvas?.restore()
        canvas?.drawColor(Color.YELLOW)
        canvas?.drawColor(Color.GRAY)


    }
}