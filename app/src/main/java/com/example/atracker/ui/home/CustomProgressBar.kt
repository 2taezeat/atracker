//package com.example.atracker.ui.home
//
//import android.content.Context
//import android.graphics.Rect
//import android.graphics.Paint
//import android.opengl.ETC1.getHeight
//import android.opengl.ETC1.getWidth
//import android.graphics.Canvas
//import java.util.ArrayList
//import android.util.AttributeSet
//import android.widget.ProgressBar
//import android.widget.SeekBar
//
//
//class CustomProgressBar : ProgressBar {
//    private var mProgressItemsList: ArrayList<ProgressItem>? = null
//
//    constructor(context: Context?) : super(context!!) {
//        mProgressItemsList = ArrayList<ProgressItem>()
//    }
//
//    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}
//    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context!!,
//        attrs,
//        defStyle) {
//    }
//
//    fun initData(progressItemsList: ArrayList<ProgressItem>?) {
//        mProgressItemsList = progressItemsList
//    }
//
//    @Synchronized
//    override fun onMeasure(
//        widthMeasureSpec: Int,
//        heightMeasureSpec: Int,
//    ) {
//        // TODO Auto-generated method stub
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//    }
//
//    override fun onDraw(canvas: Canvas) {
//        if (mProgressItemsList!!.size > 0) {
//            val progressBarWidth = width
//            val progressBarHeight = height
//            val thumboffset = thumbOffset
//            var lastProgressX = 0
//            var progressItemWidth: Int
//            var progressItemRight: Int
//            for (i in mProgressItemsList!!.indices) {
//                val progressItem: ProgressItem = mProgressItemsList!![i]
//                val progressPaint = Paint()
//                progressPaint.color = resources.getColor(
//                    progressItem.color)
//                progressItemWidth = (progressItem.progressItemPercentage * progressBarWidth / 100).toInt()
//                progressItemRight = lastProgressX + progressItemWidth
//
//                // for last item give right to progress item to the width
//                if (i == mProgressItemsList!!.size - 1
//                    && progressItemRight != progressBarWidth
//                ) {
//                    progressItemRight = progressBarWidth
//                }
//                val progressRect = Rect()
//                progressRect[lastProgressX, thumboffset / 2, progressItemRight] =
//                    progressBarHeight - thumboffset / 2
//                canvas.drawRect(progressRect, progressPaint)
//                lastProgressX = progressItemRight
//            }
//            super.onDraw(canvas)
//        }
//    }
//}