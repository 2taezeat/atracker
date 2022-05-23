package com.example.atracker.utils

import android.content.Context
import android.graphics.*

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.atracker.R
import kotlin.math.min


internal class ProgressBarDrawable(val context: Context, private val numSegments : Int, private val progressInt : Int, private val success : Boolean) : Drawable() {
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
        when (numSegments) {
            1 -> mForegroundColorList = arrayListOf<Int>(mBackground, mForegroundSuccess7 )
            2 -> mForegroundColorList = arrayListOf<Int>(mBackground, mForegroundSuccess1, mForegroundSuccess7 )
            3 -> mForegroundColorList = arrayListOf<Int>(mBackground, mForegroundSuccess1, mForegroundSuccess4, mForegroundSuccess7 )
            4 -> mForegroundColorList = arrayListOf<Int>(mBackground, mForegroundSuccess1, mForegroundSuccess3, mForegroundSuccess5, mForegroundSuccess7 )
            5 -> mForegroundColorList = arrayListOf<Int>(mBackground, mForegroundSuccess1, mForegroundSuccess2, mForegroundSuccess4, mForegroundSuccess5, mForegroundSuccess7 )
            6 -> mForegroundColorList = arrayListOf<Int>(mBackground, mForegroundSuccess1, mForegroundSuccess2, mForegroundSuccess3, mForegroundSuccess4, mForegroundSuccess5, mForegroundSuccess7 )
            7 -> mForegroundColorList = arrayListOf<Int>(mBackground, mForegroundSuccess1, mForegroundSuccess2, mForegroundSuccess3, mForegroundSuccess4, mForegroundSuccess5, mForegroundSuccess6, mForegroundSuccess7 )
        }
    }


    override fun draw(canvas: Canvas) {
        val b = bounds
        val segmentWidth = (b.width() - (numSegments - 1)).toFloat() / numSegments
        mSegment[0f, 0f, segmentWidth] = b.height().toFloat()
        mSegmentMiniCircle.left = mSegment.left / (numSegments )
        mSegmentMiniCircle.right = mSegment.right / (5 )
        mSegmentMiniCircle.top = mSegment.top
        mSegmentMiniCircle.bottom = mSegment.bottom

        val miniOffset = mSegment.width() / (numSegments + 4)



        for (i in 1 until progressInt + 1) {
            mPaint.color = mForegroundColorList!![i]
            Log.d("test123", "${miniOffset}")
            if (i == 1) {
                if (i == progressInt) {
                    canvas.drawRoundRect(mSegmentMiniCircle,50f,50f, mPaint)

                    mSegment.offset(miniOffset, 0f)
                    mSegmentMiniCircle.offset(miniOffset, 0f)

                    canvas.drawRect(mSegment.left, mSegment.top, mSegment.right - (miniOffset * 2), mSegment.bottom, mPaint)
                    canvas.drawRect(mSegment,mPaint)
                    mSegmentMiniCircle.offset(mSegment.width() - miniOffset, 0f)
                    canvas.drawRoundRect(mSegmentMiniCircle,50f,50f, mPaint)

                } else {
                    canvas.drawRoundRect(mSegmentMiniCircle,50f,50f, mPaint)
                    mSegment.offset(miniOffset, 0f)
                    mSegmentMiniCircle.offset(miniOffset, 0f)
                    canvas.drawRect(mSegment.left, mSegment.top, mSegment.right - miniOffset, mSegment.bottom, mPaint)
                    mSegment.offset(mSegment.width() - miniOffset, 0f)
                }


            } else if (i == progressInt) {
                if (progressInt == numSegments) {
                    canvas.drawRect(mSegment.left, mSegment.top, mSegment.right - miniOffset, mSegment.bottom, mPaint)
                        //Log.d("test456", "${miniOffset}, ${mSegment.width()}")
                    mSegment.offset(mSegment.width(), 0f)
                    mSegmentMiniCircle.offset(mSegment.width(), 0f)
                    mSegmentMiniCircle.offset(mSegment.width() - (miniOffset * 3.0f)  , 0f)

                    Log.d("test456", "${mSegment.left}, ${mSegment.right}")


                    canvas.drawRoundRect(mSegmentMiniCircle.left - (miniOffset * 1.2f) - numSegments  , mSegmentMiniCircle.top, mSegment.left + numSegments, mSegmentMiniCircle.bottom,50f,50f, mPaint)
                    //canvas.drawRoundRect(mSegmentMiniCircle.left - miniOffset * 1.4f  , mSegmentMiniCircle.top, mSegmentMiniCircle.right - miniOffset * 1.4f , mSegmentMiniCircle.bottom,50f,50f, mPaint)
                } else {
                    canvas.drawRect(mSegment.left, mSegment.top, mSegment.right - miniOffset, mSegment.bottom, mPaint)
                    mSegment.offset(mSegment.width(), 0f)
                    mSegmentMiniCircle.offset(mSegment.width(), 0f)
                    mSegmentMiniCircle.offset(mSegment.width() - (miniOffset) * 3 , 0f)
                    canvas.drawRoundRect(mSegmentMiniCircle,50f,50f, mPaint)
                }
            } else {
                canvas.drawRect(mSegment,mPaint)
                mSegment.offset(mSegment.width(), 0f)
                mSegmentMiniCircle.offset(mSegment.width(), 0f)
            }
        }



//        for (i in 0 until numSegments) {
//            val loLevel = i / numSegments.toFloat()
//            val hiLevel = (i + 1) / numSegments.toFloat()
//            Log.d("test123", "${loLevel}, ${hiLevel}, ${level}, ${segmentWidth}, ${mSegment}")
//            if (loLevel <= level && level <= hiLevel) {
////                val middle = mSegment.left + numSegments * segmentWidth * (level - loLevel)
////                Log.d("test123middle", "${middle}")
////                canvas.drawRoundRect(mSegment.left, mSegment.top, middle, mSegment.bottom,100f,100f, mPaint)
////                mPaint.color = mBackground
////                canvas.drawRoundRect(middle, mSegment.top, mSegment.right, mSegment.bottom,100f,100f, mPaint)
//            } else {
//                //canvas.drawRect(mSegment, mPaint)
//                canvas.drawRoundRect(mSegment,100f,100f,mPaint)
//                //canvas.drawArc(mSegment,270f,360f,false, mPaint)
//
//            }
//            mSegment.offset(mSegment.width(), 0f)
//        }


    }
}