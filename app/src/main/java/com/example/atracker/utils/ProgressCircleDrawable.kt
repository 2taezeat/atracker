package com.example.atracker.utils

import android.content.Context
import android.graphics.*

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.atracker.R


internal class ProgressCircleDrawable(val context: Context, private val progress1 : Int, private val progress2 : Int, private val progress3 : Int) : Drawable() {
    private val mPaint = Paint()
    private val mSegment = RectF()
    private val mSegmentMiniCircle = RectF()
    private val mBackground = ContextCompat.getColor(context, R.color.grey_progress)
    private val mForegroundSuccess1 = ContextCompat.getColor(context, R.color.progress_color_1)
    private val mForegroundSuccess2 = ContextCompat.getColor(context, R.color.progress_color_2)
    private val mForegroundSuccess3 = ContextCompat.getColor(context, R.color.progress_color_3)
    private val mForegroundSuccess4 = ContextCompat.getColor(context, R.color.progress_color_4)
    private val mForegroundSuccess5 = ContextCompat.getColor(context, R.color.progress_color_5)
    private val mForegroundSuccess6 = ContextCompat.getColor(context, R.color.progress_color_6)
    private val mForegroundSuccess7 = ContextCompat.getColor(context, R.color.progress_color_7)

    var mForegroundColorList : ArrayList<Int>? = null

    companion object {
    }

    override fun onLevelChange(level: Int): Boolean {
        invalidateSelf()
        return true
    }

    override fun setAlpha(alpha: Int) {}
    override fun setColorFilter(cf: ColorFilter?) {}
    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }
    init {
//        when (numSegments) {
//            1 -> mForegroundColorList = arrayListOf<Int>(mBackground, mForegroundSuccess7 )
//            2 -> mForegroundColorList = arrayListOf<Int>(mBackground, mForegroundSuccess1, mForegroundSuccess7 )
//            3 -> mForegroundColorList = arrayListOf<Int>(mBackground, mForegroundSuccess1, mForegroundSuccess4, mForegroundSuccess7 )
//            4 -> mForegroundColorList = arrayListOf<Int>(mBackground, mForegroundSuccess1, mForegroundSuccess3, mForegroundSuccess5, mForegroundSuccess7 )
//            5 -> mForegroundColorList = arrayListOf<Int>(mBackground, mForegroundSuccess1, mForegroundSuccess2, mForegroundSuccess4, mForegroundSuccess5, mForegroundSuccess7 )
//            6 -> mForegroundColorList = arrayListOf<Int>(mBackground, mForegroundSuccess1, mForegroundSuccess2, mForegroundSuccess3, mForegroundSuccess4, mForegroundSuccess5, mForegroundSuccess7 )
//            7 -> mForegroundColorList = arrayListOf<Int>(mBackground, mForegroundSuccess1, mForegroundSuccess2, mForegroundSuccess3, mForegroundSuccess4, mForegroundSuccess5, mForegroundSuccess6, mForegroundSuccess7 )
//        }
    }

//    private val m2Paint = Paint()
//    private val mSegment = RectF()



    override fun draw(canvas: Canvas) {
        val b = bounds

//        val segmentWidth = (b.width() - (numSegments - 1)).toFloat() / numSegments
//        mSegment[0f, 0f, segmentWidth] = b.height().toFloat()
//        mSegmentMiniCircle.left = mSegment.left
//        mSegmentMiniCircle.right = mSegment.right / 5
//        mSegmentMiniCircle.top = mSegment.top
//        mSegmentMiniCircle.bottom = mSegment.bottom
//
//        val miniOffset = mSegment.width() / (numSegments + 4)
//        Log.d("test123", "${level}, ${miniOffset}")

        val segmentWidth = (b.width() - (progress1 - 1)).toFloat() / progress2
        mSegment[0f, 0f, segmentWidth] = b.height().toFloat()


        mPaint.color = Color.GRAY
        mPaint.style = Paint.Style.STROKE

        canvas.drawArc(0f,0f,0f,0f,270f,360f,false, mPaint)


        mPaint.strokeWidth = 10f
        mPaint.isAntiAlias = true
//        canvas.drawArc(200f, 200f, 700f, 700f, 0f, 360f, false, paint)
//        paint.color = Color.GREEN
//        paint.strokeCap = Paint.Cap.ROUND
//        canvas.drawArc (200f, 200f, 700f, 700f, -90f, 180f, false, paint)
//        paint.color = Color.RED
//        paint.strokeCap = Paint.Cap.ROUND
//        canvas.drawArc (200f, 200f, 700f, 700f, -180f, 200f, false, paint)
//        paint.color = Color.BLUE
//        paint.strokeCap = Paint.Cap.ROUND
//        canvas.drawArc (200f, 200f, 700f, 700f, -270f, 40f, false, paint)
//        paint.color = Color.BLACK
//        paint.strokeCap = Paint.Cap.ROUND
//        canvas.drawArc (200f, 200f, 700f, 700f, -300f, 40f, false, paint)




    }
}